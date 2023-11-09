package com.hoozy.hoosshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
@IdClass(ProductID.class) // 복합키 매핑
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_SEQ_GENERATOR")
	@SequenceGenerator(name = "PRODUCT_SEQ_GENERATOR", sequenceName = "PRODUCT_SEQ", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private int price;
	
	// 단방향
	@Id // PFK는 PK임과 동시에 외래키이므로, 연관관계를 맺음과 동시에 @Id 어노테이션으로 기본키임을 명시해야한다.
	@OneToOne
	@JoinColumn(name = "img_id")
	public Img img;
	
	// 단방향
	@OneToOne
	@JoinColumn(name = "cate_id")
	private Category category;
}
