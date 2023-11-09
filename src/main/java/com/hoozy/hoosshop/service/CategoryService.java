package com.hoozy.hoosshop.service;

import org.springframework.stereotype.Service;

import com.hoozy.hoosshop.entity.Category;
import com.hoozy.hoosshop.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	public void save(Category category) {
		categoryRepository.save(category);
	}
}
