package com.oneplatform.permission.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 功能权限资源类型
 * <br>
 * Class Name   : FunctionResourceType
 *
 * @author jiangwei
 * @version 1.0.0
 * @date 2019年12月25日
 */
public enum FunctionResourceType {
	catalog,menu,button;

    @JsonCreator
    public static FunctionResourceType getEnum(String key){
        for(FunctionResourceType item : values()){
            if(item.name().equals(key)){
                return item;
            }
        }
        return null;
    }
}
