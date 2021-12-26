package com.oneplatform.system.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.jeesuite.mybatis.core.BaseMapper;
import com.jeesuite.mybatis.plugin.cache.annotation.Cache;
import com.oneplatform.system.dao.entity.AccountEntity;

public interface AccountEntityMapper extends BaseMapper<AccountEntity, String> {
	
	@Cache(uniqueIndex = true)
    @Select("SELECT * FROM account WHERE mobile=#{mobile} LIMIT 1")
    @ResultMap("BaseResultMap")
    AccountEntity findByMobile(String mobile);

    @Cache(uniqueIndex = true)
    @Select("SELECT * FROM account WHERE email=#{email} LIMIT 1")
    @ResultMap("BaseResultMap")
    AccountEntity findByEmail(String email);

    @Cache(uniqueIndex = true)
    @Select("SELECT * FROM account WHERE name=#{name} LIMIT 1")
    @ResultMap("BaseResultMap")
    AccountEntity findByName(String name);

    @Cache
    @Select("SELECT a.*,b.union_id FROM account a LEFT JOIN open_account_binding b ON a.id = b.account_id WHERE a.enabled = 1 AND b.open_id = #{openId} AND b.open_type = #{openType} LIMIT 1 ")
    @ResultMap("BaseResultMap")
    AccountEntity findByOpenId(@Param("openType") String openType,@Param("openId") String openId);

    @Cache
    @Select("SELECT a.* FROM account a INNER JOIN account_scope s ON a.id = s.account_id WHERE AND s.principal_type = #{principalType} AND s.principal_id = #{principalId} LIMIT 1 ")
    @ResultMap("BaseResultMap")
    AccountEntity findBypPrincipal(@Param("principalType") String principalType,@Param("principalId") String principalId);
    
}