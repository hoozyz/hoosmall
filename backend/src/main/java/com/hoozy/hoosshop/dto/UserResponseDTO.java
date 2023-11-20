package com.hoozy.hoosshop.dto;

import com.hoozy.hoosshop.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO { // 클라이언트가 토큰으로 유저의 이메일을 요청했을 때 이메일을 반환해주는 dto
	private String email;
	
	// 서버에서 클라이언트로 반환하기 위해 유저의 이메일을 넣어서 반환
	public static UserResponseDTO toRequest(Users user) {
		return UserResponseDTO.builder()
				.email(user.getEmail())
				.build();
	}
}
