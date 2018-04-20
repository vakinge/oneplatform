package com.oneplatform.base.model;

import java.io.Serializable;

public class IdNamePair implements Serializable{

	private static final long serialVersionUID = 1L;
	private Serializable id;
	private String name;
		
	public IdNamePair() {}
	
	public IdNamePair(Serializable id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Object getId() {
		return id;
	}
	public void setId(Serializable id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
