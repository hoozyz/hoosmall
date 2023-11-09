package com.hoozy.hoosshop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hoozy.hoosshop.dto.PageInfo;
import com.hoozy.hoosshop.dto.ProductListResponseDTO;
import com.hoozy.hoosshop.entity.Product;
import com.hoozy.hoosshop.entity.ProductID;
import com.hoozy.hoosshop.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;
	
	@Transactional
	public void save(Product product) {
		productRepository.save(product);
	}

	public long getCount() {
		return productRepository.count();
	}

	public List<ProductListResponseDTO> getList(PageInfo pageInfo) {
		List<ProductListResponseDTO> list = new ArrayList<>();
		
		for(int i = pageInfo.getStartList(); i <= pageInfo.getEndList(); i++) {
			// 복합키 생성
			ProductID key = new ProductID(Long.valueOf(i), Long.valueOf(i)); 
			list.add(ProductListResponseDTO.toProductResponse(productRepository.findById(key)
					.orElseThrow(() -> new RuntimeException("데이터가 없습니다."))));
		}
		
		return list;
	}
	
	public ProductListResponseDTO getDetail(long pId) {
		ProductID key = ProductID.toProductID(pId);
		
		return ProductListResponseDTO.toProductResponse(productRepository.findById(key)
					.orElseThrow(() -> new RuntimeException("데이터가 없습니다.")));
	}
}
