package com.hoozy.hoosshop.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoozy.hoosshop.dto.PageInfo;
import com.hoozy.hoosshop.dto.ProductListResponseDTO;
import com.hoozy.hoosshop.jwt.SecurityUtil;
import com.hoozy.hoosshop.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;

	// 0초 0분 0시 모든일 모든월 월요일
	@Scheduled(cron = "0 0 0 * * 1") // 매주 월요일 00시 상품 개수 초기화
	public void resetStock() {
		log.info("상품 재고 초기화");
		productService.resetStock();
	}

	@GetMapping("/list/{currentPage}") // 홈페이지 페이징
	public ResponseEntity<Map<String, Object>> getList(@PathVariable int currentPage) {
		
		// 현재페이지, 페이지 당 최대 페이지수, 전체 게시글 수, 페이지 당 게시글 수 로 페이징 정보 생성
		int productCount = (int) productService.getCount();
		PageInfo pageInfo = new PageInfo(currentPage, 5, productCount, 6);

		// 상품 리스트와 페이지 정보 같이 보내기
		Map<String, Object> map = new HashMap<>();
		map.put("list", productService.getList(pageInfo));
		map.put("pageInfo", pageInfo);

		return ResponseEntity.ok(map);
	}

	@GetMapping("/detail/{pId}")
	public ResponseEntity<ProductListResponseDTO> getDetail(@PathVariable Long pId) {
		log.info("pId : " + pId);
		return ResponseEntity.ok(productService.getDetail(pId));
	}
}