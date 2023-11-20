package com.hoozy.hoosshop.service;

import org.springframework.stereotype.Service;

import com.hoozy.hoosshop.entity.Img;
import com.hoozy.hoosshop.repository.ImgRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImgService {
	
	private final ImgRepository imgRespository;
	
	@Transactional
	public Img save(Img img) {
		return imgRespository.save(img);
	}
}
