package com.hoozy.hoosshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoozy.hoosshop.config.CustomException;
import com.hoozy.hoosshop.config.ErrorCode;
import com.hoozy.hoosshop.config.ErrorResponse;
import com.hoozy.hoosshop.dto.TokenDTO;
import com.hoozy.hoosshop.dto.TokenRequestDTO;
import com.hoozy.hoosshop.dto.UserRequestDTO;
import com.hoozy.hoosshop.dto.UserResponseDTO;
import com.hoozy.hoosshop.jwt.SecurityUtil;
import com.hoozy.hoosshop.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Log4j2
public class UserController {
	
	private final UserService userService;

	@GetMapping("/me") // 로그인 한 유저의 이메일 가져오기
	public ResponseEntity<UserResponseDTO> findById() {
		// API 요청이 들어오면 필터에서 access token을 복호화해 유저 정보를 꺼내 SecurityContext 에 저장한다.
		// SecurityContext 내의 정보는 Secutiry 전역에서 언제든 꺼내서 사용할 수 있다. 
		// SecurityUtil은 SecurityContext에서 유저의 Authentication을 가져와 안에있는 user의 id를 저장한다.
		log.info("/me userId : "+SecurityUtil.getCurrentUserId());
		return ResponseEntity.ok(userService.findById(SecurityUtil.getCurrentUserId()));
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO userRequestDTO) {
		log.info("register : "+userRequestDTO.getEmail());
		
		return ResponseEntity.ok(userService.register(userRequestDTO));
	}
	
	@GetMapping("/register/duplicate/{email}")
	public ResponseEntity<Integer> registerDuplicate(@PathVariable String email) {
		
		if(userService.findByEmail(email)) { 
			return ResponseEntity.ok(1);
		} 
		return ResponseEntity.ok(0);
	}
	
	@PostMapping("/login")
	public ResponseEntity<TokenDTO> login(@RequestBody UserRequestDTO userRequestDTO) {
		log.info("login : " + userRequestDTO.getEmail());
		throw new CustomException(ErrorCode.ACCESS_DENIED);
//		return ResponseEntity.ok(userService.login(userRequestDTO));
	}
	
	// access token이 만료되었을 때 refresh token으로 access token 다시 받기
	@PostMapping("/refresh")
	public ResponseEntity<TokenDTO> refresh(@RequestBody TokenRequestDTO tokenRequestDTO) {
		log.info("refresh : " + tokenRequestDTO.getRefreshToken());
		
		return ResponseEntity.ok(userService.refresh(tokenRequestDTO));
	}
}
