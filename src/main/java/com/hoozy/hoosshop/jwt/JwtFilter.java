package com.hoozy.hoosshop.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoozy.hoosshop.config.CustomException;
import com.hoozy.hoosshop.config.ErrorResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter { // oncePerRequestFilter를 상속받아 요청할 떄 한번만 header에 토큰을 빼내어서 검증한다.

	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String BEARER_PREFIX = "Bearer";
	private final TokenProvider tokenProvider;

	private String resolveToken(HttpServletRequest req) {
		// 토큰에서 Bearer 값 뺴내기
		String bearerToken = req.getHeader(AUTHORIZATION_HEADER);
		
		// Request Header에서 토큰 정보만 빼내기
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(7); // bearer 뺴고 뒤에 토큰
		}
		return null;
	}

	// 실제 필터링 로직 doFilterInternal
	// JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {

		// Request Header에서 토큰 꺼내기
		String jwt = resolveToken(req);
		
		// validateToken 으로 토큰 유효성 검사
		// 정상 토큰이면 해당 토큰으로 Authentication을 가져와서 SecurityContext에 저장
		try {
			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				Authentication authentication = tokenProvider.getAuthetication(jwt);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			filterChain.doFilter(req, res);
			
		} catch (CustomException e) {
			log.error("CustomException : " + e.getMessage());
			res.setStatus(e.getErrorCode().getStatus());
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			
			String json = new ObjectMapper().writeValueAsString(ErrorResponse.of(e.getErrorCode()));
			res.getWriter().write(json);
			res.getWriter().flush();
		}
	}
}
