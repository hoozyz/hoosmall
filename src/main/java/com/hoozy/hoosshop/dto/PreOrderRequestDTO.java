package com.hoozy.hoosshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreOrderRequestDTO { // 사전검증 dto
	private String merchantUid;
	private int amount;
}
