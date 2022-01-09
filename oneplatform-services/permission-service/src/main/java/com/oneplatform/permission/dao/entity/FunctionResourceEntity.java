package com.oneplatform.permission.dao.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.common.util.JsonUtils;
import com.oneplatform.permission.dao.StandardBaseEntity;
import com.oneplatform.permission.dto.MenuItem;
import com.oneplatform.permission.dto.ResourceTreeModel;


@Table(name = "function_resource")
public class FunctionResourceEntity extends StandardBaseEntity {

    /**
     * 父ID
     */
    @Column(name = "parent_id")
    private Integer parentId;
    
    private String type;

    /**
     * 资源名称
     */
    private String name;

    private String code;
    
    @Column(name = "item_content")
    private String itemContent;

    @Column(name = "client_type",updatable = false)
    private String clientType;
    
    @Column(name = "is_display")
    private Boolean isDisplay = Boolean.TRUE;

    /**
     * 排序
     */
    private Integer sort;


	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getItemContent() {
		return itemContent;
	}

	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	
	

	public Boolean getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public static List<ResourceTreeModel> toTreeModels(List<FunctionResourceEntity> entities,String clientType) {
        List<ResourceTreeModel> result =  new ArrayList<>(entities.size());
        
        ResourceTreeModel model;
        for (FunctionResourceEntity entity : entities) {
        	model = BeanUtils.copy(entity, ResourceTreeModel.class);
        	
        	MenuItem menuItem = null;
        	if(StringUtils.isNotBlank(entity.getItemContent())) {
        		List<MenuItem> items = JsonUtils.toList(entity.getItemContent(), MenuItem.class);
        		if(StringUtils.isNotBlank(clientType)) {
        			menuItem = items.stream().filter(o -> clientType.equals(o.getClientType())).findFirst().orElse(null);
        		}
        		
        		if(menuItem == null && !CollectionUtils.isEmpty(items)) {
        			menuItem = items.get(0);
        		}
        	}
        	if(menuItem != null) {
    			model.setClientType(menuItem.getClientType());
				model.setRouter(menuItem.getRouter());
				model.setViewPath(menuItem.getViewPath());
				model.setIcon(menuItem.getIcon());
			}
        	
        	result.add(model);
		}
        
        return result;
    }
    
    
}