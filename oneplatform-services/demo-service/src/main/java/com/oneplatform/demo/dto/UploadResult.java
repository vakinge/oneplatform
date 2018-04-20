package com.oneplatform.demo.dto;

public class UploadResult {

	private String url;
	private String name;
	
	
	public UploadResult() {}
	
	
	public UploadResult(String url, String name) {
		super();
		this.url = url;
		this.name = name;
	}


	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
