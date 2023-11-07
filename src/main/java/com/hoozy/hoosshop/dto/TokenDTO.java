package com.hoozy.hoosshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDTO { // 토큰 값을 헤더에서 가져오거나 삽입할 때 사용하는 dto
	private String grantType; // bearer 타입
	private String accessToken; // 액세스 토큰
	private String refreshToken; // 리프레시 토큰
	private Long tokenExpire; // 토큰 만료 시간
}
