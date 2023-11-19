package com.hoozy.hoosshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.hoozy.hoosshop.jwt.JwtAccessDeniedHandler;
import com.hoozy.hoosshop.jwt.JwtAuthenticationEntryPoint;
import com.hoozy.hoosshop.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final CorsConfig corsConfig;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // BCrypt 방식의 암호화
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http	// csrf 설정 disable
				.csrf(CsrfConfigurer::disable)
				// security는 기본적으로 세션을 사용한다.
				// 여기서는 세션을 사용하지 않기 떄문에 세션 설정을 Stateless로 설정
				.sessionManagement(Configurer -> Configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
										// 회원가입, 로그인, 홈페이지 상품리스트, 상품 상세정보만 인증 필요 없이 접근 가능
										.requestMatchers(new AntPathRequestMatcher("/api/**")).permitAll()
										.requestMatchers(new AntPathRequestMatcher("/user/register")).permitAll()
										.requestMatchers(new AntPathRequestMatcher("/user/register/**")).permitAll()
										.requestMatchers(new AntPathRequestMatcher("/user/login")).permitAll()
										.requestMatchers(new AntPathRequestMatcher("/product/**")).permitAll()
//										.requestMatchers(new AntPathRequestMatcher("/pay/**")).permitAll()
//										.requestMatchers(new AntPathRequestMatcher("/user/refresh")).permitAll()
//										.requestMatchers(new AntPathRequestMatcher("/cart/**")).permitAll()
										// 나머지는 인증 없이 접근 불가
										.anyRequest().authenticated())
				// CORS 리액트 포트인 3000포트 허용
				.addFilter(corsConfig.corsFilter())
				// exception 404를 401, 403으로 나누는 설정 클래스 추가
				.exceptionHandling(auth -> auth
									.authenticationEntryPoint(jwtAuthenticationEntryPoint)
									.accessDeniedHandler(jwtAccessDeniedHandler))
				.apply(new JwtSecurityConfig(tokenProvider));
		return http.build();	
	}
}
