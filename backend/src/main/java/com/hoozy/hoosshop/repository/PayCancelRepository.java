package com.hoozy.hoosshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoozy.hoosshop.entity.PayCancel;

@Repository
public interface PayCancelRepository extends JpaRepository<PayCancel, Long> {
}	
