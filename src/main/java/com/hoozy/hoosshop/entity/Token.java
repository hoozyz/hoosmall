package com.hoozy.hoosshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token { // 토큰 정보 담을 객체
	private String grantType; // 
	private String accessToken; // 액세스 토큰
	private Long tokenExpire; // 토큰 만료 시간
}
