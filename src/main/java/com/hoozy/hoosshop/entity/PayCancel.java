package com.hoozy.hoosshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
<<<<<<< HEAD
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
=======
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
>>>>>>> 12a2e3897b23b7048f77d3886f35394840029100
@Table(name = "pay_cancel")
public class PayCancel {
	
	@Id
	private Long id;
	
	@Column(nullable = false)
	private int status; // 1이면 취소, 0이면 결제 성공
	
<<<<<<< HEAD
	@OneToOne(mappedBy = "payCancel")
=======
	@OneToOne(mappedBy = "pay_cancel")
>>>>>>> 12a2e3897b23b7048f77d3886f35394840029100
	private Payment payment;
}
