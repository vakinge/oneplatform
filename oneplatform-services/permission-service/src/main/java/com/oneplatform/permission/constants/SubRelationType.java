package com.oneplatform.permission.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 
 * <br>
 * Class Name   : SubRelationType
 *
 * @author jiangwei
 * @version 1.0.0
 * @date 2019年12月23日
 */
public enum SubRelationType {
	userToGroup,apiToButton,apiToMenu;
	
	@JsonCreator
	public static SubRelationType getEnum(String key){
		for(SubRelationType item : values()){
			if(item.name().equals(key)){
				return item;
			}
		}
		return null;
	}
}
