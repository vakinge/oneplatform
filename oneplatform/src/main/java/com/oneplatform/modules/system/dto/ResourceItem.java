package com.oneplatform.modules.system.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.jeesuite.common.util.BeanUtils;
import com.oneplatform.modules.system.constants.FunctionResourceType;

@JsonInclude(Include.NON_NULL)
public class ResourceItem {

    private Integer id;
    private Integer parentId;
    private String type;
    private String name;
    private String value;
    private String icon;
    private Integer sort;
    
    private Boolean checked;
    private Boolean disabled;

    private List<ResourceItem> children;

    public ResourceItem() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public List<ResourceItem> getChildren() {
		return children;
	}

	public void setChildren(List<ResourceItem> children) {
		this.children = children;
	}
	
	public void addChild(ResourceItem item) {
		if(children == null)children = new ArrayList<>();
		children.add(item);
	}
	
	public static List<ResourceItem> buildTree(List<ResourceItem> items){

		items.sort(Comparator.comparingInt(ResourceItem::getSort));
        
        List<ResourceItem> result = new ArrayList<>();
        
        Map<Integer, ResourceItem> toMaps = new HashMap<>();
        for (ResourceItem item : items) {
        	if(item.getParentId() == null || item.getParentId() <= 0) {
        		result.add(item);
        	}
			if(!FunctionResourceType.button.name().equals(item.getType())) {
				toMaps.put(item.getId(), item);
			}
		}
        //
        for (ResourceItem item : items) {
        	if(item.getParentId() != null && toMaps.containsKey(item.getParentId())) {
        		toMaps.get(item.getParentId()).addChild(BeanUtils.copy(item, ResourceItem.class));
        	}
        }
        
        return result;
	}

}
