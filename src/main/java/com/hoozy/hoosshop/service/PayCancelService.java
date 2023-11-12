package com.hoozy.hoosshop.service;

import org.springframework.stereotype.Service;

import com.hoozy.hoosshop.entity.PayCancel;
import com.hoozy.hoosshop.repository.PayCancelRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayCancelService {
	
	private final PayCancelRepository payCancelRepository;
	
	@Transactional
	public PayCancel findById(Long id) {
		return payCancelRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("DB 정보가 없습니다."));
	}
}
