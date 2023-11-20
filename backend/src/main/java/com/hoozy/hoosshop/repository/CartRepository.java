package com.hoozy.hoosshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoozy.hoosshop.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	List<Cart> findByUserId(Long id); // 현재 회원의 장바구니 내역 리스트
	boolean existsByProductId(Long id);
}	
