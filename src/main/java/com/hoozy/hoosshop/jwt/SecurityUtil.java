package com.hoozy.hoosshop.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
	
	private SecurityUtil() {}
	
	// SecurityContext에 유저 정보가 저장되는 시점
	// Request가 들어올 때 JwtFilter의 doFilter에서 저장
	public static Long getCurrentUserId() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication == null || authentication.getName() == null) {
			throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
		}
		
		// JwtFilter에서 SecurityContext에 세팅한 유저 정보를 꺼낸다.
		// 이 프로젝트에서는 무조건 user의 id를 저장하게 했으므로 꺼내서 Long 타입으로 변환하여 반환한다.
		return Long.parseLong(authentication.getName());
	}
}
