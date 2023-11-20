package com.hoozy.hoosshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCancelRequestdto { // 클라이언트가 결제 취소를 누르면 
	private Long id; // 결제내역 아이디
	private String impUid;
	private String merchantUid;
	private int amount; // 부분 취소할 금액
	
	@Override
	public String toString() {
		return "PaymentCancelRequestdto [id=" + id + ", impUid=" + impUid + ", merchantUid=" + merchantUid + ", amount="
				+ amount + "]";
	}
}
