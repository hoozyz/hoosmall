package com.hoozy.hoosshop.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
<<<<<<< HEAD
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
=======
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
>>>>>>> 12a2e3897b23b7048f77d3886f35394840029100
public class ProductID implements Serializable {
	private Long id;
	private Long imgId;
}
