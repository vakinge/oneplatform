package com.oneplatform.demo.dto;

import java.math.BigDecimal;

public class Product {

	private Integer id;
	private String name;
	private Integer ownId;
	private BigDecimal price;
	private int stock;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOwnId() {
		return ownId;
	}
	public void setOwnId(Integer ownId) {
		this.ownId = ownId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	
}
