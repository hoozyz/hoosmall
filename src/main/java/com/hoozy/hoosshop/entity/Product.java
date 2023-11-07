package com.hoozy.hoosshop.entity;

import java.io.Serializable;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product")
@IdClass(ProductID.class) // 복합키 매핑
public class Product implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "PRODUCT_SEQ_GENERATOR")
	@SequenceGenerator(name = "PRODUCT_SEQ_GENERATOR", sequenceName = "PRODUCT_SEQ", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(name = "img_id")
	private Long imgId;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private int price;
	
	@OneToOne
	@JoinColumn(name = "img_id")
	private Img img;
	
	@OneToOne
	@JoinColumn(name = "cate_id")
	private Category category;
}
