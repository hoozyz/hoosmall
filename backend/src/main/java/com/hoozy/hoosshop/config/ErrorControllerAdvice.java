package com.hoozy.hoosshop.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.log4j.Log4j2;

// 전역적으로 에러를 처리할 수 있는 어노테이션 -> rest가 붙으면 응답을 JSON으로 내려준다.
// @RestControllerAdvice는 서블릿에서 적용되는 advice로 요청을 서블릿보다 먼저 걸러내는 필터에서 에러는 캐치하지 못한다.
@Log4j2
@RestControllerAdvice
public class ErrorControllerAdvice {
	
	// CustomException으로 custom error 만 핸들러해서 응답보내기
	@ExceptionHandler(value = CustomException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
		ErrorResponse response = ErrorResponse.of(e.getErrorCode());
		log.info("error message : " + response.getMessage());
		return ResponseEntity.ok(response);
	}
}
