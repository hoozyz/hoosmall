package com.hoozy.hoosshop.entity;

import java.sql.Timestamp;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payment")
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYMENT_SEQ_GENERATOR")
	@SequenceGenerator(name = "PAYMENT_SEQ_GENERATOR", sequenceName = "PAYMENT_SEQ", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(nullable = false, name = "imp_uid")
	private String impUid; // 결제 고유 번호
	
	@Column(nullable = false, name = "merchant_uid")
	private String merchantUid; // 상품 고유 번호
	
	@Column(nullable = false)
	private int price; // 결제한 금액
	
	@Column(nullable = false)
	private int count; // 구매한 상품 개수
	
	@Column(nullable = false)
	private int coupon; // 사용한 쿠폰 개수
	
	@Column(nullable = false)
	private String method; // 결제 수단
	
	@Column(nullable = false, name = "payment_date")
	private Timestamp paymentDate; // 결제 날짜
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") // manytomany 는 중간에 mapper를 둬서 onetomany - mapper - manytoone 으로 해야한다
	private Users user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id") // 복합키를 pk로 가지는 product여서 payment의 외래키 또한 2개여서 2개를 적어줘야한다.
	@JoinColumn(name = "img_id")
	private Product product;
	
	// 단방향
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cancel_id")
	private PayCancel payCancel;
}
