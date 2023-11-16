package com.hoozy.hoosshop.service;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hoozy.hoosshop.config.CustomException;
import com.hoozy.hoosshop.config.ErrorCode;
import com.hoozy.hoosshop.dto.TokenDTO;
import com.hoozy.hoosshop.dto.TokenRequestDTO;
import com.hoozy.hoosshop.dto.UserRequestDTO;
import com.hoozy.hoosshop.dto.UserResponseDTO;
import com.hoozy.hoosshop.entity.Auth;
import com.hoozy.hoosshop.entity.RefreshToken;
import com.hoozy.hoosshop.entity.Users;
import com.hoozy.hoosshop.jwt.TokenProvider;
import com.hoozy.hoosshop.repository.RefreshTokenRepository;
import com.hoozy.hoosshop.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	
	// users에 변경이 있을 때 save
	public Users save(Users user) {
		return userRepository.save(user);
	}
	
	// pk로 user 가져오기
	public Users getUsers(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() ->  new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
	}
	
	// 토큰으로 이메일 응답하기
	public UserResponseDTO findById(Long userId) {
		return userRepository.findById(userId)
				.map(UserResponseDTO::toRequest) // 인스턴스::메소드
				.orElseThrow(() ->  new CustomException(ErrorCode.USERS_NOT_FOUND));
	}
	
	@Transactional
	public UserResponseDTO register(UserRequestDTO userRequestDTO) {
		
		if(userRepository.existsByEmail(userRequestDTO.getEmail())) {
			throw  new CustomException(ErrorCode.DUPLICATE_USER);
		}
		
		Users user = userRequestDTO.toUser(passwordEncoder); // 비밀번호 암호화
		user.setCouponCount(3); // 쿠폰 개수 3개 기본 제공
		
		// JPA는 간단한 CRUD는 메소드로 자동으로 처리해준다.
		return UserResponseDTO.toRequest(userRepository.save(user)); 
		// save 메소드는 리턴값이 있는 메소드로, 새로운 엔티티면 persist(새로 저장), 이미 있는거면 merge(병합)한다.
	}
	
	@Transactional
	public TokenDTO login(UserRequestDTO userRequestDTO) {
		
		// Login 한 id,pw를 기반으로 AuthenticationToken 생성
		UsernamePasswordAuthenticationToken authenticationToken = userRequestDTO.toAuthentication();
		
		// 실제로 검증(비밀번호 체크)
		// authenticate 메소드가 실행이 될 때 CustomUserDatailsService에서 만들었던 loadUserByUsername 메소드가 실행된다
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		
		// 인증 정보를 기반으로 JWT 생성
		TokenDTO tokenDTO = tokenProvider.createToken(authentication);
		
		// refresh token 저장
		RefreshToken refreshToken = RefreshToken.builder()
									.id(authentication.getName())
									.token(tokenDTO.getRefreshToken())
									.build();
		
		refreshTokenRepository.save(refreshToken);
		
		return tokenDTO;
	}
	
	@Transactional
	public TokenDTO refresh(TokenRequestDTO tokenRequestDTO) {
		
		// refresh token 검증 -> 만료 여부 검사
		tokenProvider.validateToken(tokenRequestDTO.getRefreshToken());
		
		// access token에서 member id 가져오기
		Authentication authentication = tokenProvider.getAuthetication(tokenRequestDTO.getAccessToken());
		
		// DB에서 위에 member id를 기반으로 refresh token 값 가져옴 (만료기간 비교)
		RefreshToken refreshToken = refreshTokenRepository.findById(authentication.getName())
				// optional은 결과가 안나올 시 예외를 thorw 해야한다.
				.orElseThrow(() ->  new CustomException(ErrorCode.UNKNOWN_ERROR));
		
		// Refresh Token 일치 검사
		if(!tokenRequestDTO.getRefreshToken().equals(refreshToken.getToken())) {
			throw  new CustomException(ErrorCode.WRONG_TYPE_ERROR);
		}
		
		// 새로운 토큰 생성
		TokenDTO tokenDTO = tokenProvider.createToken(authentication);
		
		// DB 정보 업데이트
		RefreshToken newRefreshToken = refreshToken.updateToken(tokenDTO.getRefreshToken());
		refreshTokenRepository.save(newRefreshToken);
		
		return tokenDTO;
	}

	public boolean findByEmail(String email) {
		if(userRepository.existsByEmail(email)) {
			return true;
		}
		return false; 
	}

	public void resetCoupon() {
		List<Users> userList = userRepository.findAll(); // 유저 전체 리스트 가져오기
		for(Users user : userList) {
			if(user.getAuth() == Auth.ROLE_TEST) { // 테스트는 매일 쿠폰 10개
				user.setCouponCount(10);
			} else { // 테스트 계정을 제외한 일반 계정은 3개
				user.setCouponCount(3);
			}
			userRepository.save(user);
		}
	}
}
