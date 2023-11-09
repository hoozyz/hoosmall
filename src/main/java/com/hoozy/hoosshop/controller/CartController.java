package com.hoozy.hoosshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
	
	
//	@GetMapping("/") 카트에 저장하기 -> 상품아이디, 개수는 1개, securityutil에서 id 가져와서 넣기
}
