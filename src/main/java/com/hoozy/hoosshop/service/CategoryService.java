package com.hoozy.hoosshop.service;

import org.springframework.stereotype.Service;

import com.hoozy.hoosshop.entity.Category;
import com.hoozy.hoosshop.repository.CategoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	@Transactional
	public Category save(Category category) {
		Category cate = Category.builder()
							.cate(category.getCate())
							.build();
		
		// 카테고리가 없을 때만 save
		if(!categoryRepository.existsByCate(category.getCate())) 
			return categoryRepository.save(category);
		
		// 카테고리가 있으면 카테고리를 DB에서 가져와 리턴
		else return categoryRepository.findByCate(cate.getCate())
			.orElseThrow(() -> new RuntimeException("DB에서 가져오지 못했습니다."));
	}
}
