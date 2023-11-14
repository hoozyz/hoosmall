package com.hoozy.hoosshop.config;

public class CustomException extends RuntimeException { 
	// RuntimeException은 실행단계에서 발생하는 에러로 해당 에러가 발생 시 Rollback을 수행한다.
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ErrorCode errorCode;
	
	public CustomException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public CustomException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return this.errorCode;
	}
}	
