package com.oneplatform.system.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.jeesuite.mybatis.core.BaseMapper;
import com.jeesuite.mybatis.plugin.cache.annotation.Cache;
import com.oneplatform.system.dao.entity.AccountScopeEntity;

public interface AccountScopeEntityMapper extends BaseMapper<AccountScopeEntity, String> {
	
	@Cache
	@Select("SELECT * FROM account_scope WHERE account_id=#{accountId} AND enabled=1")
	@ResultMap("BaseResultMap")
	List<AccountScopeEntity> findByAccountId(String accountId);
	
}