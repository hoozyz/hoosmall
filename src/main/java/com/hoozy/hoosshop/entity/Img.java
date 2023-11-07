package com.hoozy.hoosshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
<<<<<<< HEAD
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
=======
import jakarta.persistence.GeneratedValue;import jakarta.persistence.GenerationType;
>>>>>>> 12a2e3897b23b7048f77d3886f35394840029100
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
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
