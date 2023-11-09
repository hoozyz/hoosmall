package com.hoozy.hoosshop.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ProductID implements Serializable {
	private Long id;
	private Long img; // PFK여서 테이블을 참조하므로 참조하는 테이블의 PK타입과 참조하는 필드명으로 적어야한다.
	
	// long 타입을 Long으로 변환시켜 같은 값으로 넣기
	public static ProductID toProductID(long id) {
		return ProductID.builder()
				.id(Long.valueOf(id))
				.img(Long.valueOf(id))
				.build();
	}
}
