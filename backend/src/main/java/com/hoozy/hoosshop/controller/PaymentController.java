package com.hoozy.hoosshop.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoozy.hoosshop.config.CustomException;
import com.hoozy.hoosshop.config.ErrorCode;
import com.hoozy.hoosshop.dto.IdCouponDTO;
import com.hoozy.hoosshop.dto.PageInfo;
import com.hoozy.hoosshop.dto.PaymentCancelRequestdto;
import com.hoozy.hoosshop.dto.PaymentResponseDTO;
import com.hoozy.hoosshop.dto.PaymentValidateRequestDTO;
import com.hoozy.hoosshop.dto.PreOrderRequestDTO;
import com.hoozy.hoosshop.entity.Payment;
import com.hoozy.hoosshop.entity.Product;
import com.hoozy.hoosshop.entity.Users;
import com.hoozy.hoosshop.jwt.SecurityUtil;
import com.hoozy.hoosshop.service.CartService;
import com.hoozy.hoosshop.service.PayCancelService;
import com.hoozy.hoosshop.service.PaymentService;
import com.hoozy.hoosshop.service.ProductService;
import com.hoozy.hoosshop.service.UserService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Prepare;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pay")
public class PaymentController {

	private final PaymentService paymentService;
	private final ProductService productService;
	private final UserService userService;
	private final PayCancelService payCancelService;
	private final CartService cartService;
	
	@Value("${imp.key}")
	private String impKey; // imp 키

	@Value("${imp.secret}")
	private String impSecret; // imp 시크릿 키
	
	private IamportClient iamportClient;
	
	@GetMapping("/mypage/{currentPage}") // security 전역에 있는 securityUtil에서 id 가져오기
	public ResponseEntity<Map<String, Object>> getPaymentList(@PathVariable int currentPage) {
		Long id = Long.valueOf(SecurityUtil.getCurrentUserId());
		
		// 페이징 정보 생성
		int totalCount = (int) paymentService.getCount(id);
		PageInfo pageInfo = new PageInfo(currentPage, 5, totalCount, 6);
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", paymentService.getPaymentList(id, pageInfo));
		map.put("pageInfo", pageInfo);

		return ResponseEntity.ok(map);
	}

	// 결제 페이지 띄우기 전 결제정보 사전등록 // 금토일인지 확인하기 -> 30퍼 할인
	@PostMapping("/preorder")
	public ResponseEntity<IamportResponse<Prepare>> preOrder(@RequestBody PreOrderRequestDTO preOrderRequestDTO) throws IOException, IamportResponseException {
		iamportClient = new IamportClient(impKey, impSecret);
		// 클라이언트로 사전 결제 요청 -> merchant_uid, amount(bigDecimal)
		log.info("merchant_uid : " + preOrderRequestDTO.getMerchantUid());
		log.info("amount : " + preOrderRequestDTO.getAmount());
		return ResponseEntity.ok(iamportClient.postPrepare(
				new PrepareData(preOrderRequestDTO.getMerchantUid(), new BigDecimal(preOrderRequestDTO.getAmount()))));
	}

	// 프론트에서 결제 요청 건을 검증 -> imp_uid, merchat_uid, paid_amount를 프론트에서 받아서 DB에 중복된 결제가
	// 없는지 확인 후 결제 상세내역 조회
	// 쿠폰, 이벤트 적용 내역을 확인 후 DB에서 원래 가격에 할인된 최종 가격과 실제 결제내역의 결제값이 같으면 검증 완료
	// 금액이 다르거나 에러가 생겨 결제가 안되면 결제 취소
	@PostMapping("/validate")
	public ResponseEntity<PaymentResponseDTO> validate(@RequestBody PaymentValidateRequestDTO paymentValidateRequestDTO)
			throws IOException, IamportResponseException {
		iamportClient = new IamportClient(impKey, impSecret);
		
		Calendar cal = Calendar.getInstance();
		
		String impUid = paymentValidateRequestDTO.getImpUid();
		String merchantUid = paymentValidateRequestDTO.getMerchantUid();
		log.info("paymentValidateRequestDTO : " + paymentValidateRequestDTO.toString());
		
		int day = cal.get(Calendar.DAY_OF_WEEK); // 요일 변환 -> 1부터 일요일
		boolean isEvent = false;
		if(day == 1 || day == 6 || day == 7) isEvent = true; // 금토일 이벤트이면 30퍼 할인

		// 결제 단건 조회해서 얻은 실제 결제된 금액
		int amount = 0;
		try {
			com.siot.IamportRestClient.response.Payment paymentResponse = 
					iamportClient.paymentByImpUid(impUid).getResponse();
			
			amount = paymentResponse.getAmount().intValue(); // bigDecimal to int
		} catch (IamportResponseException e) {
			throw new CustomException(ErrorCode.PAYMENT_NOT_FOUND); // 존재하지 않는 결제정보
		}
		
		// 결제 정보 DB에 넣기위한 결제할 상품 리스트
		List<Payment> paymentList = new ArrayList<>();
		Users user = userService.getUsers(Long.valueOf(SecurityUtil.getCurrentUserId())); // securityutil.getid로 가져오기
		
		// DB에서 상품 id로 원가를 가져오기 -> 결제한 건수만큼
		int totalPrice = 0; // 결제한 건수별로 금액을 가져와 합친 결제되어야 할 총 금액
		int couponCount = 0; // 총 사용한 쿠폰 수
		for(IdCouponDTO idCoupon : paymentValidateRequestDTO.getIdCoupon()) {
			Product prod = productService.findById(Long.valueOf(idCoupon.getPId()));
			int price = prod.getPrice() * idCoupon.getCount(); // 상품 원가에 개수 곱한게 세일 전 가격
			// 이벤트면 30퍼 세일
			int coupon = idCoupon.getCoupon();
			couponCount += coupon;
			// 쿠폰 수 만큼 15퍼 세일 -> 원가 * 쿠폰 수 * 0.15 만큼 빼기
			price = (int) Math.ceil(price * (1 - 0.15 * coupon));
			totalPrice += price;
			
			Payment payment = Payment.builder()
							.count(idCoupon.getCount())
							.impUid(impUid)
							.merchantUid(merchantUid)
							.method("카드")
							.payCancel(payCancelService.findById(Long.valueOf(1))) // 1이 취소 안한 결제 성공
							.paymentDate(new Timestamp(System.currentTimeMillis()))
							.price(price)
							.product(prod)
							.user(user)
							.coupon(coupon)
							.build();
			paymentList.add(payment);
		}
		if(isEvent) totalPrice = (int) Math.ceil(totalPrice * 0.7); // 이벤트 요일이면 30퍼 할인
		
		log.info("totalPrice : " + totalPrice + " ,      amount : " + amount);
		// 결제된 금액과 결제할 금액이 다르면 결제 취소
		if(totalPrice != amount) { 
			// CancelData : uid, boolean(true면 imp_uid, false면 merchant_uid), amount(bigDecimal)
			// 취소 요청 api
			iamportClient.cancelPaymentByImpUid(new CancelData(impUid, true, new BigDecimal(amount)));
			throw new CustomException(ErrorCode.PAYMENT_NOT_ACCORD); // 금액이 일치하지 않을 때
		}
		
		// 결제한 상품 개수 만큼 결제내역 DB에 넣기
		// 결제한 상품 개수 만큼 상품 재고에서 없애기
		for(Payment pay : paymentList) {
			Payment payment = paymentService.save(pay);
			productService.changeStock(payment.getProduct().getId(), payment.getCount());
		}
		
		// 장바구니에 상품이 있으면 제거
		if(cartService.getCount() > 0) {
			cartService.deleteAll();
		}
		
		// 쿠폰 개수만큼 회원 DB에서 빼기
		user.setCouponCount(user.getCouponCount() - couponCount);
		userService.save(user);
		
		return ResponseEntity.ok(PaymentResponseDTO.toPaymentResponseDTO(paymentList.get(0), totalPrice));
	}
	
	// 결제취소 -> 장바구니에서 여러개 한 번에 결제해도 하나씩 취소 가능 -> 일부 취소
	@PutMapping("/cancel")
	public ResponseEntity<PaymentResponseDTO> cancel(@RequestBody PaymentCancelRequestdto paymentCancelRequestDTO) throws IOException, IamportResponseException {
		iamportClient = new IamportClient(impKey, impSecret);
		
		log.info("paymentCancelRequestdto : " + paymentCancelRequestDTO.toString());
		// CancelData : uid, boolean(true면 imp_uid, false면 merchant_uid), amount(bigDecimal)
		// 취소 요청 api
		try {
			iamportClient.cancelPaymentByImpUid(new CancelData(paymentCancelRequestDTO.getImpUid(),
					true, new BigDecimal(paymentCancelRequestDTO.getAmount())));
		} catch (IamportResponseException e) {
			throw new CustomException(ErrorCode.PAYMENT_CANCEL_FAILED); // 취소 요청 실패
		}
		
		Payment payment = paymentService.findById(paymentCancelRequestDTO.getId());
		Users user = userService.getUsers(Long.valueOf(SecurityUtil.getCurrentUserId()));
		user.setCouponCount(user.getCouponCount() + payment.getCoupon()); // 쿠폰 쓴거 돌려놓기
		
		return ResponseEntity.ok(PaymentResponseDTO.toPaymentResponseDTO(
				paymentService.cancel(paymentCancelRequestDTO.getId()), paymentCancelRequestDTO.getAmount()));
	}
	
}
