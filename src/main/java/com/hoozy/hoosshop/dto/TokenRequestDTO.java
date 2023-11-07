package com.hoozy.hoosshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 토큰 유효기간이 지났을 때 refresh token을 가지고 access token을 다시 받아가기 위한 요청 dto

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequestDTO { 
	private String accessToken;
	private String refreshToken;
}	
