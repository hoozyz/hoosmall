package com.hoozy.hoosshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "img")
public class Img {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMG_SEQ_GENERATOR")
	@SequenceGenerator(name = "IMG_SEQ_GENERATOR", sequenceName = "IMG_SEQ", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(nullable = false)
	private String link;
}
