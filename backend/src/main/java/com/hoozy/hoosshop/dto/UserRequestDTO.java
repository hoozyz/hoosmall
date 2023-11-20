package com.hoozy.hoosshop.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hoozy.hoosshop.entity.Auth;
import com.hoozy.hoosshop.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO { // 클라이언트에서 사용자가 요청할 때 사용하는 dto
	private String email;
	private String pwd;
	
	// Spring Security에서 제공하는 비밀번호 암호화
	// encoding 때 마다 해시와 salt(바이트 단위 임의의 문자열)을 추가해 암호가 바뀌어 보안에 매우 좋다.
	public Users toUser(PasswordEncoder passwordEncoder) {
		return Users.builder()
				.email(email)
				.pwd(passwordEncoder.encode(pwd)) // passwordEncoder로 비밀번호 암호화
				.auth(Auth.ROLE_USER)
				.build();
	}
	
	// 요청한 아이디와 비밀번호가 맞는지 인증(Authentication)하기 위한 객체
	public UsernamePasswordAuthenticationToken toAuthentication() {
		return new UsernamePasswordAuthenticationToken(email, pwd);
	}
}
