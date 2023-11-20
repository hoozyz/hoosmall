package com.hoozy.hoosshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hoozy.hoosshop.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	// 결제 날짜를 기준으로 내림차순 하고, 회원의 모든 결제 정보(취소 포함)를 가져오기.
	@Query(value = "select id, user_id, product_id, img_id, cancel_id, imp_uid, merchant_uid, price, count, coupon, method, payment_date "
			+ "from "
			+ "(select * from "
			+ "(select p.*, row_number() over(order by p.payment_date desc) num "
			+ "from payment p where p.user_id = ?1) "
			+ "where num between ?2 and ?3)",
			  nativeQuery = true)
	List<Payment> findByUserId(Long id, int start, int end);

	long countByUserId(Long id);
}
