package com.hoozy.hoosshop.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res,
			AuthenticationException authException) throws IOException, ServletException {
		// 유효한 자격증명을 제공하지 않고 접근하여 할 때 401
		log.error("유효한 자격 증명이 아님.");
		res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		
//		ErrorCode code = ErrorCode.ACCESS_DENIED;
//		res.setStatus(code.getStatus());
//		res.setContentType("application/json");
//		res.setCharacterEncoding("UTF-8");
//		
//		String json = new ObjectMapper().writeValueAsString(ErrorResponse.of(code));
//		log.info("json : " + json);
//		res.getWriter().write(json);
	}
}
