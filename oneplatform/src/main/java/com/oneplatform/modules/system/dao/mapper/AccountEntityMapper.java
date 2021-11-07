package com.oneplatform.modules.system.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.jeesuite.mybatis.core.BaseMapper;
import com.oneplatform.modules.system.dao.entity.AccountEntity;

public interface AccountEntityMapper extends BaseMapper<AccountEntity, Integer> {
	
	@Select("select * from account where name = #{account} or email = #{account} or mobile = #{account} limit 1")
	@ResultMap("BaseResultMap")
	AccountEntity findByLoginAccount(String account);
	
	
	@Select("select * from account a inner join account_scopes s on a.id = s.account_id where s.tenant_id=#{tenantId}")
	@ResultMap("BaseResultMap")
	List<AccountEntity> findByTenantId(String tenantId);
}