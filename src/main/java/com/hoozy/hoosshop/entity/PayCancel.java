package com.hoozy.hoosshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pay_cancel")
public class PayCancel {
	
	@Id
	private Long id;
	
	@Column(nullable = false)
	private int status; // 1이면 취소, 0이면 결제 성공
	
	@OneToOne(mappedBy = "pay_cancel")
	private Payment payment;
}
