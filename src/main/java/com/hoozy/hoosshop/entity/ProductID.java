package com.hoozy.hoosshop.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductID implements Serializable {
	private Long id;
	private Long imgId;
}
