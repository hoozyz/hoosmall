package com.hoozy.hoosshop.dto;

import com.hoozy.hoosshop.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponseDTO {
	private Long id;
	private String category;
	private String link;
	private String title;
	private int price;
	private int stock;
	
	// DB에서 가져온 product 중의 필요한 정보 클라이언트에 보낼 객체
	public static ProductListResponseDTO toProductResponse(Product product) {
		return ProductListResponseDTO.builder()
				.id(product.getId())
				.category(product.getCategory().getCate())
				.link(product.getImg().getLink())
				.title(product.getTitle())
				.price(product.getPrice())
				.stock(product.getStock())
				.build();
	}
}
