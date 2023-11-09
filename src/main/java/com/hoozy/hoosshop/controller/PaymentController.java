package com.hoozy.hoosshop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoozy.hoosshop.dto.PageInfo;
import com.hoozy.hoosshop.entity.Payment;
import com.hoozy.hoosshop.jwt.SecurityUtil;
import com.hoozy.hoosshop.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/pay")
public class PaymentController {

	private final PaymentService paymentService;

	@GetMapping("/mypage/{currentPage}") // security 전역에 있는 securityUtil에서 id 가져오기
	public ResponseEntity<List<Payment>> getPaymentList(@PathVariable int currentPage) {

		// 현재페이지, 페이지 당 최대 페이지수, 전체 게시글 수, 페이지 당 게시글 수 로 페이징 정보 생성
		int paymentCount = (int) paymentService.getCount();
		log.info("paymentCount : " + paymentCount);
		PageInfo pageInfo = new PageInfo(currentPage, 5, paymentCount, 6);

		return ResponseEntity.ok(paymentService.getPaymentList(SecurityUtil.getCurrentUserId(), pageInfo));
	}

//	@PostMapping("/preorder") // 결제 페이지 띄우기 전 결제정보 사전등록 // 금토일인지 확인하기 -> 30퍼 할인

//	// 프론트에서 결제 요청 건을 검증 -> imp_uid, merchat_uid, paid_amount를 프론트에서 받아서 DB에 중복된 결제가 없는지 확인 후 결제 상세내역 조회
//	// 쿠폰, 이벤트 적용 내역을 확인 후 DB에서 원래 가격에 할인된 최종 가격과 실제 결제내역의 결제값이 같으면 검증 완료
//	// 금액이 다르거나 에러가 생겨 결제가 안되면 결제 취소
//	@PostMapping("/validate") 

}
