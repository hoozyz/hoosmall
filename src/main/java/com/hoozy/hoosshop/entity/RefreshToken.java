package com.hoozy.hoosshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	
	@Builder
	public RefreshToken(String id, String token, long expireDate) {
		this.id = id;
		this.token = token;
	}
	
	// 토큰 업데이트
	public RefreshToken updateToken(String token) {
		this.token = token;
		return this;
	}
}
