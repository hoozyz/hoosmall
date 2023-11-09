package com.hoozy.hoosshop.config;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hoozy.hoosshop.jwt.JwtFilter;
import com.hoozy.hoosshop.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

// 직접 만든 TokenProvider와 JwtFilter를 SecurityConfig에 적용할 때 사용
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
	private final TokenProvider tokenProvider;
	
	// TokenProvider 를 주입 받아서 JwtFilter를 통해 Spring Security 로직에 필터를 등록
	// Spring Security의 전반적인 필터에 적용된다.
	@Override
	public void configure(HttpSecurity http) {
		JwtFilter customFilter = new JwtFilter(tokenProvider);
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
