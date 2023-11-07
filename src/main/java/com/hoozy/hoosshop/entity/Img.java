package com.hoozy.hoosshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
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
@Table(name = "img")
public class Img {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "IMG_SEQ_GENERATOR")
	@SequenceGenerator(name = "IMG_SEQ_GENERATOR", sequenceName = "IMG_SEQ", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(nullable = false)
	private String link;
	
	@OneToOne(mappedBy = "img")
	private Product product;
}