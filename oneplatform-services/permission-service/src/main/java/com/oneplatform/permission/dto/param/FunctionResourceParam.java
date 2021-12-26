package com.oneplatform.permission.dto.param;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class FunctionResourceParam {

    private Integer id;

    /**
     * 关联系统
     */
    @ApiModelProperty(hidden = true)
    private Integer systemId;

    /**
     * 父ID
     */
    @ApiModelProperty("父ID")
    private Integer parentId;

    /**
     * 资源名称
     */
    @ApiModelProperty(value = "功能资源名称",required = true)
    @NotBlank(message = "功能资源名称不能为空")
    private String name;
    
    private String code;
    
    private String type;

    /**
     * 页面链接
     */
    @ApiModelProperty("页面链接")
    private String viewPath;

    /**
     * 资源图标
     */
    @ApiModelProperty("资源图标")
    private String icon;
    
    private String clientType;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   

	public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getViewPath() {
		return viewPath;
	}

	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	
}