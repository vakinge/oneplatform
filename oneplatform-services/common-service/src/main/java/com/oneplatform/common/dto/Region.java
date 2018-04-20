package com.oneplatform.common.dto;

import io.swagger.annotations.ApiModelProperty;

public class Region {

	@ApiModelProperty(value = "省份Id")
	private Integer provinceId;
	@ApiModelProperty(value = "省份名")
	private String provinceName;
	@ApiModelProperty(value = "城市id")
	private Integer cityId;
	@ApiModelProperty(value = "城市名")
	private String cityName;
	@ApiModelProperty(value = "区域id")
	private Integer areaId;
	@ApiModelProperty(value = "区域名")
	private String areaName;
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	
}
