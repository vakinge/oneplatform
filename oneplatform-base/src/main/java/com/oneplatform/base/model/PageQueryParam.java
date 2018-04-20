/**
 * 
 */
package com.oneplatform.base.model;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年3月24日
 */
public class PageQueryParam<T> {

	private int pageNo;
	private int pageSize;
	private String orderBy;
	private T example;
	
	public PageQueryParam() {}
	
	public PageQueryParam(int pageNo, int pageSize, String orderBy, T example) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.orderBy = orderBy;
		this.example = example;
	}

	public PageQueryParam(int pageNo, int pageSize, T example) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.example = example;
	}

	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public T getExample() {
		return example;
	}
	public void setExample(T example) {
		this.example = example;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	
}
