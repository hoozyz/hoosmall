package com.hoozy.hoosshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken {
	
	@Id
	private String id;
	
	@Column(nullable = false)
	private String token;
	
	@Column(nullable = false, name = "expire_date")
	private Long expireDate;
	
	@Builder
	public RefreshToken(String id, String token, long expireDate) {
		this.id = id;
		this.token = token;
		this.expireDate = expireDate;
	}
	
	// 토큰 업데이트
	public RefreshToken updateToken(String token) {
		this.token = token;
		return this;
	}
}