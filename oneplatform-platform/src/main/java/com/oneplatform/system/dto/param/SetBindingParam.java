package com.oneplatform.system.dto.param;

import java.util.List;

public class SetBindingParam {

	private List<Integer> soureIds;
	private Integer targetId;
	public List<Integer> getSoureIds() {
		return soureIds;
	}
	public void setSoureIds(List<Integer> soureIds) {
		this.soureIds = soureIds;
	}
	public Integer getTargetId() {
		return targetId;
	}
	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}
	
	
}
