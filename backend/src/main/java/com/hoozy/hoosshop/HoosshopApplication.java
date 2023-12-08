package com.hoozy.hoosshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.hoozy.hoosshop.controller.ApiController;
import com.hoozy.hoosshop.service.ProductService;
import com.hoozy.hoosshop.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
public class HoosshopApplication {
	
	private final ProductService productService;
	private final UserService userService;
	private final ApiController apiController;
	
	// 0초 0분 0시 모든일 모든월 모든요일
	@Scheduled(cron = "0 0 0 * * *") // 매일 스케쥴러 실행 -> 매일 쿠폰 초기화
	public void resetCoupon() {
		log.info("유저 쿠폰 초기화");
		userService.resetCoupon();
	}

	// 0초 0분 0시 모든일 모든월 월요일
	@Scheduled(cron = "0 0 0 * * 1") // 매주 월요일 00시 상품 개수 초기화
	public void resetStock() {
		log.info("상품 재고 초기화");
		productService.resetStock();
	}

	public static void main(String[] args) {
		SpringApplication.run(HoosshopApplication.class, args);
	}
	
	public void parsing() {
		long count = productService.getCount();
		log.info("parsing : " + count);
		if(count == 0) {
			apiController.parsing();
		}
	}

}
