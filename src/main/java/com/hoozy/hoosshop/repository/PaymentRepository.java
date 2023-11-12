package com.hoozy.hoosshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoozy.hoosshop.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	List<Payment> findByUserIdAndIdBetweenOrderByPaymentDateDesc(Long id, Long start, Long end);
}
