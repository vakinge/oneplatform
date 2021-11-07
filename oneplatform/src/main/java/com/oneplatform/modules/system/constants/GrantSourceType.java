package com.oneplatform.modules.system.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 权限资源类型
 * <br>
 * Class Name   : GrantSourceType
 *
 * @author jiangwei
 * @version 1.0.0
 * @date 2019年12月25日
 */
public enum GrantSourceType {
    system,function,api;

    @JsonCreator
    public static GrantSourceType getEnum(String key){
        for(GrantSourceType item : values()){
            if(item.name().equals(key)){
                return item;
            }
        }
        return null;
    }
}
