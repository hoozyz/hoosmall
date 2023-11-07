package com.hoozy.hoosshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pay_cancel")
public class PayCancel {
	
	@Id
	private Long id;
	
	@Column(nullable = false)
	private int status; // 1이면 취소, 0이면 결제 성공
	
	@OneToOne(mappedBy = "payCancel")
	private Payment payment;
}
