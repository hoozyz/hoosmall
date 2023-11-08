package com.hoozy.hoosshop.dto;

import java.sql.Timestamp;

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
public class PaymentResponseDTO {
	private String productTitle; // 주문 제품명
	private String merchantUid; // 주문고유번호
	private int price; // 결제 금액
	private String method; // 결제수단
	private Timestamp paymentDate; // 결제 날짜
}
