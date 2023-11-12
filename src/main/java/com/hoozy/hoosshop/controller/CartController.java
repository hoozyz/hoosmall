package com.hoozy.hoosshop.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoozy.hoosshop.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
	
	private final CartService cartService;
	
	@PostMapping("/save/{id}") // 장바구니에 넣기
	public ResponseEntity<Map<String, Object>> save(@PathVariable Long id) {
		Map<String, Object> map = new HashMap<>();
		if(cartService.count() == 6) {
			map.put("errorMassage", "장바구니에 이미 6개의 상품이 있습니다.");
		} else if (cartService.isExist(id)) { 
			map.put("errorMassage", "이미 장바구니에 있는 제품입니다.");
		} else {
			map.put("cart", cartService.save(id));
		}
		
		return ResponseEntity.ok().body(map);
	}
	
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> getList() {
		return ResponseEntity.ok(cartService.getList(Long.valueOf(1)));
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteCart(@PathVariable Long id) {
		cartService.deleteCart(id);
	}
}
