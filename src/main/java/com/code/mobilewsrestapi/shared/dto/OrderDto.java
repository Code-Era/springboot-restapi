package com.code.mobilewsrestapi.shared.dto;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.code.mobilewsrestapi.io.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderDto {
	
	private String orderId;
	
	private String orderDescription;

	public OrderDto() {
		super();
	}
}
