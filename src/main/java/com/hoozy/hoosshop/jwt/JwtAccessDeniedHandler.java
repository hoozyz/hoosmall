package com.hoozy.hoosshop.jwt;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
	
	@Override
	public void handle(HttpServletRequest requ, HttpServletResponse res,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// 필요한 권한이 없어 접근하려 할 때 403
		res.sendError(HttpServletResponse.SC_FORBIDDEN);
	}
}
