package com.oneplatform.system.dao.entity;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.jeesuite.mybatis.core.BaseEntity;
import com.oneplatform.base.model.TreeModel;

@Table(name = "sys_permission_resource")
public class PermissionResourceEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 父ID
     */
    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "module_id")
    private Integer moduleId;

    /**
     * 资源名称
     */
    private String name;

    private String type;

    /**
     * uri
     */
    private String uri;

    /**
     * 权限编码
     */
    @Column(name = "permission_code")
    private String permissionCode;

    /**
     * 关联按钮权限编码
     */
    @Column(name = "button_code")
    private String buttonCode;

    @Column(name = "http_method")
    private String httpMethod;

    /**
     * 授权类型
     */
    @Column(name = "grant_type")
    private String grantType;

    /**
     * 资源图标
     */
    private String icon;

    private Boolean enabled;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 平台类型
     */
    @Column(name = "platform_type")
    private String platformType;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private Integer updatedBy;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取父ID
     *
     * @return parent_id - 父ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父ID
     *
     * @param parentId 父ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * @return module_id
     */
    public Integer getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId
     */
    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * 获取资源名称
     *
     * @return name - 资源名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置资源名称
     *
     * @param name 资源名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取uri
     *
     * @return uri - uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * 设置uri
     *
     * @param uri uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * 获取权限编码
     *
     * @return permission_code - 权限编码
     */
    public String getPermissionCode() {
        return permissionCode;
    }

    /**
     * 设置权限编码
     *
     * @param permissionCode 权限编码
     */
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    /**
     * 获取关联按钮权限编码
     *
     * @return button_code - 关联按钮权限编码
     */
    public String getButtonCode() {
        return buttonCode;
    }

    /**
     * 设置关联按钮权限编码
     *
     * @param buttonCode 关联按钮权限编码
     */
    public void setButtonCode(String buttonCode) {
        this.buttonCode = buttonCode;
    }

    /**
     * @return http_method
     */
    public String getHttpMethod() {
        return httpMethod;
    }

    /**
     * @param httpMethod
     */
    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    /**
     * 获取授权类型
     *
     * @return grant_type - 授权类型
     */
    public String getGrantType() {
        return grantType;
    }

    /**
     * 设置授权类型
     *
     * @param grantType 授权类型
     */
    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    /**
     * 获取资源图标
     *
     * @return icon - 资源图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置资源图标
     *
     * @param icon 资源图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取平台类型
     *
     * @return platform_type - 平台类型
     */
    public String getPlatformType() {
        return platformType;
    }

    /**
     * 设置平台类型
     *
     * @param platformType 平台类型
     */
    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    /**
     * @return created_at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return created_by
     */
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return updated_at
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return updated_by
     */
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy
     */
    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public void buildPermssionCode(ModuleEntity module){
    	this.permissionCode = String.format("%s:%s:%s", getHttpMethod(),module.getServiceId().toLowerCase(),getUri());
    }
    
    public static TreeModel buildTree(List<PermissionResourceEntity> resoures){
    	List<TreeModel> treeModels = resoures.stream().map(e -> {
			return new TreeModel(e.getId(), e.getName(), e.getUri(), e.getIcon(), e.getParentId(), StringUtils.isNotBlank(e.getUri()));
		}).collect(Collectors.toList());
    	
    	return TreeModel.build(treeModels);
    }
}