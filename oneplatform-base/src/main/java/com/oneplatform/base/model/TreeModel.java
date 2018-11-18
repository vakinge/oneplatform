package com.oneplatform.base.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TreeModel {

	private Integer id;
	private String name;
	private String value;
	private String icon;
	private Integer pid;
	private String pName;
	private boolean leaf;
	private boolean spread;
	private String extraAttr;
	private Object originData;
	private List<TreeModel> children;
	

	public TreeModel() {}
	
	
	public TreeModel(Integer id, String name,String value, String icon, Integer pid, boolean leaf) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.icon = icon;
		this.pid = pid;
		this.leaf = leaf;
	}



	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getPid() {
		return pid == null ? 0 : pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}


	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	
	public boolean isSpread() {
		return spread;
	}

	public void setSpread(boolean spread) {
		this.spread = spread;
	}

	public String getExtraAttr() {
		return extraAttr;
	}

	public void setExtraAttr(String extraAttr) {
		this.extraAttr = extraAttr;
	}
	
	public Object getOriginData() {
		return originData;
	}

	public void setOriginData(Object originData) {
		this.originData = originData;
	}

	public void addChild(TreeModel child) {
		getChildren().add(child);
		child.setpName(name);
	}
	
	public List<TreeModel> getChildren() {
		return children == null ? (children = new ArrayList<>()) : children;
	}
	public void setChildren(List<TreeModel> children) {
		this.children = children;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeModel other = (TreeModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}


	public static TreeModel build(List<TreeModel> models){
		TreeModel root = new TreeModel();
		
		//按pid排序
		Collections.sort(models,new Comparator<TreeModel>() {
			@Override
			public int compare(TreeModel o1, TreeModel o2) {
				return o1.getPid().compareTo(o2.getPid());
			}
		});
		
		Map<Integer, TreeModel> modelMap = new HashMap<>();
		for (TreeModel model : models) {
			if(!model.isLeaf()){
				modelMap.put(model.getId(), model);
			}
		}
		for (TreeModel model : models) {
			if((model.getPid() == 0 && !model.isLeaf()) || !modelMap.containsKey(model.getPid())){
				root.addChild(model);
			}else{
				modelMap.get(model.getPid()).addChild(model);
			}
		}
		
		return root;
	}

}
