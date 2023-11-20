package com.hoozy.hoosshop.dto;

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
public class PaymentRequestDTO { // 결제 요청 dto
	private String impUid; // 결제고유번호
	private String merchantUid; // 상품고유번호
	private int amount; // 결제 금액
	private String coupon; // 쿠폰 내용
	private boolean isEvent; // 이벤트 적용 여부 (적용되면 30% 할인)
}
