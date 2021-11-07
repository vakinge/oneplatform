package com.oneplatform.modules.system.dto.param;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * <br>
 * Class Name   : RoleGrantPermissionParam
 *
 * @author jiangwei
 * @version 1.0.0
 * @date 2019年12月27日
 */
public class RoleGrantPermissionParam {
	/**
	 * 关联业务系统id
	 */
	@ApiModelProperty(value = "关联业务系统id")
	private Integer systemId;

	private String roleId;
	
    @ApiModelProperty(value="资源类型(菜单、接口、按钮)",allowableValues = "menu,button,api")
    private String sourceType;
	/**
     * 资源ID
     */
    @ApiModelProperty("资源ID列表")
    private List<String> sourceIds;
	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	/**
	 * @return the sourceType
	 */
	public String getSourceType() {
		return sourceType;
	}
	/**
	 * @param sourceType the sourceType to set
	 */
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	/**
	 * @return the sourceIds
	 */
	public List<String> getSourceIds() {
		return sourceIds;
	}
	/**
	 * @param sourceIds the sourceIds to set
	 */
	public void setSourceIds(List<String> sourceIds) {
		this.sourceIds = sourceIds;
	}


	public Integer getSystemId() {
		return systemId;
	}

	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}
}
