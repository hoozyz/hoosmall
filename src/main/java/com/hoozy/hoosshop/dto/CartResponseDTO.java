package com.hoozy.hoosshop.dto;

import com.hoozy.hoosshop.entity.Cart;
import com.hoozy.hoosshop.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {
	private Long id; // cart id
	private int coupon; // 회원이 가지고 있는 쿠폰 수
	private int useCoupon; // 사용한 쿠폰수
	private Product product;
	private int count; // 상품 선택 개수 default 1
	
	public static CartResponseDTO toCartResponseDTO(Cart cart) {
		return CartResponseDTO.builder()
					.count(cart.getCount())
					.coupon(cart.getUser().getCouponCount())
					.useCoupon(0) // 처음에는 사용 쿠폰 수 0
					.id(cart.getId())
					.product(cart.getProduct())
					.build();
	}
}
