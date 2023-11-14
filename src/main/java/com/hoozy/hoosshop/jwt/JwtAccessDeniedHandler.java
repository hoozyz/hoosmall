package com.hoozy.hoosshop.jwt;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.hoozy.hoosshop.config.ErrorCode;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
	
	@Override
	public void handle(HttpServletRequest requ, HttpServletResponse res,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// 필요한 권한이 없어 접근하려 할 때 403
		log.error("접근 권한 없음.");
		res.sendError(HttpServletResponse.SC_FORBIDDEN);
	
//		ErrorCode code = ErrorCode.ACCESS_DENIED;
//		res.setStatus(code.getStatus());
//		res.setContentType("application/json");
//		res.setCharacterEncoding("UTF-8");
//		
//		String json = new ObjectMapper().writeValueAsString(ErrorResponse.of(code));
//		res.getWriter().write(json);
	}
}
