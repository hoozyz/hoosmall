package com.hoozy.hoosshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoozy.hoosshop.entity.Product;
import com.hoozy.hoosshop.entity.ProductID;

@Repository
public interface ProductRepository extends JpaRepository<Product, ProductID> {
	// start,end 사이로 랜덤 쇼핑 가져오기 -> 페이징
	// Product의 기본키인 id 중 id
	Optional<Product> findById(ProductID key); // id가 a, b 사이인
}
