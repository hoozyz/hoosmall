package com.hoozy.hoosshop.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hoozy.hoosshop.entity.Payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDTO { // 결제내역 응답 dto
	private Long id; // 결제내역 id
	@JsonProperty
	private Long pId; // 결제한 상품 id
	private String productTitle; // 주문 제품명
	private String impUid; // 결제 고유 번호
	private String merchantUid; // 제품고유번호
	private int price; // 결제 금액
	private String method; // 결제수단
	private Timestamp paymentDate; // 결제 날짜
	private String failMassage; // 결제 실패 메시지
	private String link; // 이미지 링크
	private int cancel; // 취소 여부
	
	public static PaymentResponseDTO toPaymentResponseDTO(Payment payment, int totalPrice) {
		return PaymentResponseDTO.builder()
				.id(payment.getId())
				.pId(payment.getProduct().getId())
				.impUid(payment.getImpUid())
				.merchantUid(payment.getMerchantUid())
				.paymentDate(payment.getPaymentDate())
				.method(payment.getMethod())
				.price(totalPrice)
				.productTitle(payment.getProduct().getTitle())
				.link(payment.getProduct().getImg().getLink())
				.cancel(payment.getPayCancel().getStatus())
				.build();
	}
}
