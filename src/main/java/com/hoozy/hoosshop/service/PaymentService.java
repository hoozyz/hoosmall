package com.hoozy.hoosshop.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hoozy.hoosshop.config.CustomException;
import com.hoozy.hoosshop.config.ErrorCode;
import com.hoozy.hoosshop.dto.PageInfo;
import com.hoozy.hoosshop.dto.PaymentResponseDTO;
import com.hoozy.hoosshop.entity.Payment;
import com.hoozy.hoosshop.repository.PayCancelRepository;
import com.hoozy.hoosshop.repository.PaymentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final PayCancelRepository payCancelRepository;

	// 마이페이지 결제내역
	@Transactional
	public List<PaymentResponseDTO> getPaymentList(Long id, PageInfo pageInfo) {
		List<Payment> paymentList = paymentRepository.findByUserId(id, pageInfo.getStartList(), pageInfo.getEndList());
		List<PaymentResponseDTO> list = new ArrayList<>();
		
		for (Payment pay : paymentList) {
			list.add(PaymentResponseDTO.toPaymentResponseDTO(pay, pay.getPrice()));
		}
		return list;
	}

	@Transactional
	public long getCount(Long id) {
		return paymentRepository.countByUserId(id);
	}

	@Transactional
	public Payment save(Payment pay) {
		return paymentRepository.save(pay);
	}

	@Transactional
	public Payment cancel(Long id) {
		Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
		payment.setPayCancel(payCancelRepository.findById(Long.valueOf(2))
				.orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND)));
		payment.setPaymentDate(new Timestamp(System.currentTimeMillis())); // 취소 날짜 현재시각으로 바꾸기
		return paymentRepository.save(payment);
	}

	public Payment findById(Long id) {
		return paymentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
	}
}
