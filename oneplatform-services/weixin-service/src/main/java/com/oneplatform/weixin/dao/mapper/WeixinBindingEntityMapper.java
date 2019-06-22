package com.oneplatform.weixin.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.jeesuite.mybatis.core.BaseMapper;
import com.jeesuite.mybatis.plugin.cache.annotation.Cache;
import com.oneplatform.weixin.dao.entity.WeixinBindingEntity;

public interface WeixinBindingEntityMapper extends BaseMapper<WeixinBindingEntity, Integer> {
	
	
	@Cache
	@Select("SELECT * FROM weixin_bindings where open_id=#{openId} limit 1")
	@ResultMap("BaseResultMap")
	WeixinBindingEntity findByOpenId(@Param("openId") String openId);
	
	@Cache
	@Select("SELECT * FROM weixin_bindings where union_id=#{unionId}")
	@ResultMap("BaseResultMap")
	List<WeixinBindingEntity> findByUnionId(@Param("unionId") String unionId);
	
	@Cache
	@Select("SELECT * FROM weixin_bindings where user_id=#{userId}")
	@ResultMap("BaseResultMap")
	List<WeixinBindingEntity> findByUserId(@Param("userId") int userId);
	
	@Cache
	@Select("SELECT union_id FROM weixin_bindings where user_id=#{userId} limit 1")
	@ResultType(String.class)
	String findWxUnionIdByUserId(@Param("userId") int userId);

}