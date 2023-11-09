package com.hoozy.hoosshop.service;

import org.springframework.stereotype.Service;

import com.hoozy.hoosshop.entity.Category;
import com.hoozy.hoosshop.entity.Img;
import com.hoozy.hoosshop.repository.ImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImgService {
	
	private final ImgRepository imgRespository;
	
	public void save(Img img) {
		imgRespository.save(img);
	}
}
