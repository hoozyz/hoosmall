package com.hoozy.hoosshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoozy.hoosshop.dto.TokenDTO;
import com.hoozy.hoosshop.dto.TokenRequestDTO;
import com.hoozy.hoosshop.dto.UserRequestDTO;
import com.hoozy.hoosshop.dto.UserResponseDTO;
import com.hoozy.hoosshop.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Log4j2
public class UserController {
	
	private final UserService userService;
//	private final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO userRequestDTO) {
		log.info("register : "+userRequestDTO.getEmail());
		
		return ResponseEntity.ok(userService.register(userRequestDTO));
	}
	
	@PostMapping("/login")
	public ResponseEntity<TokenDTO> login(@RequestBody UserRequestDTO userRequestDTO) {
		log.info("login : " + userRequestDTO.getEmail());
		
		return ResponseEntity.ok(userService.login(userRequestDTO));
	}
	
	// access token이 만료되었을 때 refresh token으로 access token 다시 받기
	@PostMapping("/refresh")
	public ResponseEntity<TokenDTO> refresh(@RequestBody TokenRequestDTO tokenRequestDTO) {
		log.info("refresh : " + tokenRequestDTO.getRefreshToken());
		
		return ResponseEntity.ok(userService.refresh(tokenRequestDTO));
	}
}
