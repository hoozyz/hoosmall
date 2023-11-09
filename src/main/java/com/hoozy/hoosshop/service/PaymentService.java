package com.hoozy.hoosshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hoozy.hoosshop.dto.PageInfo;
import com.hoozy.hoosshop.entity.Payment;
import com.hoozy.hoosshop.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
	
	private final PaymentRepository paymentRepository;
	
	// 마이페이지 결제내역
	public List<Payment> getPaymentList(Long id, PageInfo pageInfo) {
		return paymentRepository.findByUserIdBetween(Long.valueOf(pageInfo.getStartList()), Long.valueOf(pageInfo.getEndList()));
	}

	public long getCount() {
		return paymentRepository.count();
	}
}
