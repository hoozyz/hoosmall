package com.hoozy.hoosshop.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ProductID implements Serializable {
	private Long id;
	private Long img; // PFK여서 테이블을 참조하므로 참조하는 테이블의 PK타입과 참조하는 필드명으로 적어야한다.
	
	// id와 img 둘이 같은 값이므로 넣어서 반환
	public static ProductID toProductID(Long id) {
		return ProductID.builder()
				.id(id)
				.img(id)
				.build();
	}
}
