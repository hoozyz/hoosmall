package com.hoozy.hoosshop.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoozy.hoosshop.config.CustomException;
import com.hoozy.hoosshop.config.ErrorCode;
import com.hoozy.hoosshop.entity.Cart;
import com.hoozy.hoosshop.jwt.SecurityUtil;
import com.hoozy.hoosshop.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
	
	private final CartService cartService;
	
	@PostMapping("/save/{pId}") // 장바구니에 넣기
	public ResponseEntity<Cart> save(@PathVariable Long pId) {
		System.out.println(pId);
		if(cartService.count() == 6) {
			throw new CustomException(ErrorCode.CART_EXISTS_SIX);
		} else if (cartService.isExist(pId)) { 
			throw new CustomException(ErrorCode.CART_EXISTS_ONE);
		} else {
			return ResponseEntity.ok().body(cartService.save(pId));
		}
	}
	
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> getList() {
		return ResponseEntity.ok(cartService.getList(Long.valueOf(SecurityUtil.getCurrentUserId())));
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteCart(@PathVariable Long id) {
		cartService.deleteCart(id);
	}
}
