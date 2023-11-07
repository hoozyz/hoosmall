package com.hoozy.hoosshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoozy.hoosshop.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> { // 엔티티 객체 명, pk의 타입
	Optional<Users> findByEmail(String email); // email로 유저 찾기
	boolean existsByEmail(String email); // email 존재 유무
}
