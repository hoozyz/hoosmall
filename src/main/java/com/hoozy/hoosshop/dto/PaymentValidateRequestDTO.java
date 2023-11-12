package com.hoozy.hoosshop.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentValidateRequestDTO { // 결제 검증 요청 dto
	private String impUid; // 결제 고유 번호
	private String merchantUid; // 상품 고유 번호
	private int amount; // 상품 결제 가격
	private List<IdCouponDTO> idCoupon; // 상품 id와 쿠폰 개수
	@Override
	public String toString() {
		return "PaymentValidateRequestDTO [impUid=" + impUid + ", merchantUid=" + merchantUid + ", amount=" + amount
				+ ", idCoupon=" + idCoupon.toString() + "]";
	}
}
