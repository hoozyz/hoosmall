package com.hoozy.hoosshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment")
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "PAYMENT_SEQ_GENERATOR")
	@SequenceGenerator(name = "PAYMENT_SEQ_GENERATOR", sequenceName = "PAYMENT_SEQ", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(nullable = false, name = "merchant_id")
	private String merchantId;

	@Column(nullable = false)
	private int price;
	
	@ManyToOne
	@JoinColumn(name = "user_id") // manytomany 는 중간에 mapper를 둬서 onetomany - mapper - manytoone 으로 해야한다
	private Users user;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
}
