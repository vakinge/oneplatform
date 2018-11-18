/**
 * 
 */
package com.oneplatform.base.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年3月24日
 */
public class PageQueryParam {

	private int pageNo;
	private int pageSize;
	private String orderBy;
	private Map<String, Object> conditions;
	
	public PageQueryParam() {}
	
	public PageQueryParam(int pageNo, int pageSize, String orderBy) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.orderBy = orderBy;
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

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Map<String, Object> getConditions() {
		return conditions == null ? (conditions = new HashMap<>()) : conditions;
	}

	public void setConditions(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

	
}
