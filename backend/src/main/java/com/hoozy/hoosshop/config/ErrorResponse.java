package com.hoozy.hoosshop.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse {
	
	private String message; // 에러메시지
	private String code; // 에러 코드
	private int status; // http.status
	
	public ErrorResponse(ErrorCode code) {
		this.message = code.getMessage();
		this.code = code.getCode();
		this.status = code.getStatus();
	}
	
	public static ErrorResponse of(ErrorCode code) {
		return new ErrorResponse(code);
	}
}
