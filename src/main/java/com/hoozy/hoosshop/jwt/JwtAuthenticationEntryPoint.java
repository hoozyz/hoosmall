package com.hoozy.hoosshop.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoozy.hoosshop.config.ErrorCode;
import com.hoozy.hoosshop.config.ErrorResponse;

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
		log.error("권한이 없습니다.");
		ErrorCode code = ErrorCode.AUTHENTICATION_ERROR;
		res.setStatus(code.getStatus());
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		
		String json = new ObjectMapper().writeValueAsString(ErrorResponse.of(code));
		res.getWriter().write(json);
		res.getWriter().flush();
	}
}
