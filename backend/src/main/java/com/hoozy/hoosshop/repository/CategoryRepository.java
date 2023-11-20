package com.hoozy.hoosshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoozy.hoosshop.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	boolean existsByCate(String cate);
	Optional<Category> findByCate(String cate);
}
