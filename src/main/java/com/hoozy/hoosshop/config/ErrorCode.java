package com.hoozy.hoosshop.config;

import lombok.Getter;

@Getter
public enum ErrorCode { 
	
	// JWT
	UNKNOWN_ERROR(401, "J001", "인증 토큰이 존재하지 않습니다."),
	WRONG_TYPE_ERROR(401, "J002", "잘못된 토큰 정보입니다."),
	EXPIRED_TOKEN(401, "J003", "만료된 토큰 정보입니다."),
	UNSUPPORTED_TOKEN(401, "J004", "지원하지 않는 토큰 방식입니다."),
	ACCESS_DENIED(401, "J005", "알 수 없는 이유로 요청이 거절되었습니다."),
	CONTEXT_ACCESS_DENIED(401, "J006", "Security Context에 인증 정보가 없습니다."),
	
	// COMMON
	RESOURCE_NOT_FOUND(204, "C001", "DB 정보가 없습니다."),
	USERS_NOT_FOUND(204, "C002", "로그인 유저 정보가 없습니다."),
	DUPLICATE_USER(400, "C003", "이미 존재하는 유저입니다."),
	
	// PAYMENT
	PAYMENT_NOT_FOUND(400, "P001", "존재하지 않는 결제정보입니다."),
	PAYMENT_NOT_ACCORD(400, "P002", "금액이 일치하지 않습니다."),
	PAYMENT_CANCEL_FAILED(400, "P003", "취소 요청에 실패하였습니다"),
	
	// CART
	CART_EXISTS_SIX(400, "C001", "장바구니에 이미 6개의 상품이 있습니다."),
	CART_EXISTS_ONE(400, "C002", "이미 장바구니에 있는 제품입니다.");
	
	private int status;
	private String code;
	private String message;
	
	ErrorCode(int status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

}
