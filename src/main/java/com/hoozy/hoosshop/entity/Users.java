package com.hoozy.hoosshop.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "users")
public class Users {
	
	@Id // 기본키가 될 변수를 의미
	// generator는 시퀀스 생성하는 제너레이터 이름, IDENTITY 전략 : 키본 키 생성을 DB에 위임하는 전략
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "USERS_SEQ_GENERATOR")
	// 오라클은 Sequence 전략을 지원하기 때문에, 오라클 DBMS에서 sequence를 생성하고 똑같은 이름, 설정으로 sequence를 생성하는 제너레이터를 설정해야 한다.
	// sequenceName : 오라클에서 생성한 sequence 이름, initialValue : 초기값, allocationSize : 호출 시 증가하는 수
	@SequenceGenerator(name = "USERS_SEQ_GENERATOR", sequenceName = "USERS_SEQ", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(nullable = false) // 이 필드명과 컬럼명이 다르다거나 특징을 명시
	private String email;
	
	@Column(nullable = false)
	private String pwd;
	
	@Enumerated(EnumType.STRING)
	private Auth auth;
	
	@OneToMany(mappedBy = "user") // 참조되는 필드명
	private List<Payment> payments = new ArrayList<>(); 
	
	@Builder
	public Users(Long id, String email, String pwd, Auth auth) {
		this.id = id;
		this.email = email;
		this.pwd = pwd;
		this.auth = auth;
	}
}
