package com.hoozy.hoosshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CART_SEQ_GENERATOR")
	@SequenceGenerator(name = "CART_SEQ_GENERATOR", sequenceName = "CART_SEQ", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(nullable = false)
	private int count;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") // manytomany 는 중간에 mapper를 둬서 onetomany - mapper - manytoone 으로 해야한다
	private Users user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id") // 복합키를 pk로 가지는 product여서 payment의 외래키 또한 2개여서 2개를 적어줘야한다.
	@JoinColumn(name = "img_id")
	private Product product;
}
