package com.hoozy.hoosshop.service;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hoozy.hoosshop.entity.Users;
import com.hoozy.hoosshop.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username)
				.map(this::createUserDetails)
				.orElseThrow(() -> new UsernameNotFoundException(username + " -> DB에서 찾을 수 없습니다."));
	}
	
	// DB에 USER 가 존재한다면 UserDetails 객체로 만들어서 리턴
	private UserDetails createUserDetails(Users user) {
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getAuth().toString());
		
		return new User(
				String.valueOf(user.getId()),
				user.getPwd(),
				Collections.singleton(grantedAuthority));
	}
}
