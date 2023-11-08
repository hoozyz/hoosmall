package com.hoozy.hoosshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pay")
public class PaymentController {
	
	// 금토일인지 확인하기 -> 30퍼 할인
	
//	@GetMapping("/mypage/{email}") // 가져온 이메일로 결제내역 가져오기
	
	
	
//	@PostMapping("/preorder") // 결제 페이지 띄우기 전 결제정보 사전등록
	
	
//	// 프론트에서 결제 요청 건을 검증 -> imp_uid, merchat_uid, paid_amount를 프론트에서 받아서 DB에 중복된 결제가 없는지 확인 후 결제 상세내역 조회
//	// 쿠폰, 이벤트 적용 내역을 확인 후 DB에서 원래 가격에 할인된 최종 가격과 실제 결제내역의 결제값이 같으면 검증 완료
//	// 금액이 다르거나 에러가 생겨 결제가 안되면 결제 취소
//	@PostMapping("/validate") 
	
}
