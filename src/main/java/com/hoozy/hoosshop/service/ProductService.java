package com.hoozy.hoosshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hoozy.hoosshop.config.CustomException;
import com.hoozy.hoosshop.config.ErrorCode;
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

	@Transactional
	public long getCount() {
		return productRepository.count();
	}

	@Transactional
	public List<ProductListResponseDTO> getList(PageInfo pageInfo) {
		List<ProductListResponseDTO> list = new ArrayList<>();
		
		for(int i = pageInfo.getStartList(); i <= pageInfo.getEndList(); i++) {
			// 복합키 생성
			ProductID key = new ProductID(Long.valueOf(i), Long.valueOf(i)); 
			list.add(ProductListResponseDTO.toProductResponse(productRepository.findById(key)
					.orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND))));
		}
		
		return list;
	}
	
	@Transactional
	public ProductListResponseDTO getDetail(Long pId) {
		ProductID key = ProductID.toProductID(pId);
		
		return ProductListResponseDTO.toProductResponse(productRepository.findById(key)
					.orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND)));
	}

	@Transactional
	public Product findById(Long pId) {
		ProductID key = ProductID.toProductID(pId);
		return productRepository.findById(key)
				.orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
	}

	@Transactional
	public void changeStock(Long id, int count) {
		ProductID key = ProductID.toProductID(id);
		Product product = productRepository.findById(key)
				.orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
		product.setStock(product.getStock() - count); // 재고에서 구매한 개수만큼 빼기
		save(product);
	}
}
