package com.hoozy.hoosshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdCouponDTO { // 상품 번호와 쿠폰 사용 개수 -> 상품 번호로 원가 가져오고 쿠폰 개수만큼 할인한 가격이 최종가격
	@JsonProperty
	private int pId; // 상품 번호
	private int count; // 상품 개수
	private int coupon; // 쿠폰 사용 개수
	@Override
	public String toString() {
		return "IdCouponDTO [pId=" + pId + ", count=" + count + ", coupon=" + coupon + "]";
	}
	
}
