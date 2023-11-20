package com.hoozy.hoosshop.config;

public interface UserCouponMapping {
	// JPA는 일부 컬럼만 가져올 때 인터페이스에 컬럼값만 담아서 사용가능
	int getCouponCount();
}
