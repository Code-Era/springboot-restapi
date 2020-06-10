package com.code.mobilewsrestapi.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders")
public class OrderEntity {



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String orderId;
	
	@Column
	private String orderDescription;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private UserEntity userEntity;


	public OrderEntity() {
		super();
	}




	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getOrderDescription() {
		return orderDescription;
	}


	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
	}




	public long getId() {
		return id;
	}




	public void setId(long id) {
		this.id = id;
	}




	public UserEntity getUserEntity() {
		return userEntity;
	}




	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}


	
	

}
