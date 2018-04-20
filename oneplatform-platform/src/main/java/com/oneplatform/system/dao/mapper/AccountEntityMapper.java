package com.oneplatform.system.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.jeesuite.mybatis.plugin.pagination.annotation.Pageable;
import com.oneplatform.base.dao.CustomBaseMapper;
import com.oneplatform.system.dao.entity.AccountEntity;
import com.oneplatform.system.dto.param.AccountQueryParam;

public interface AccountEntityMapper extends CustomBaseMapper<AccountEntity> {
	
	@Select("select * from sys_account where username = #{name} or email = #{email} or mobile = #{mobile} limit 1")
	@ResultMap("BaseResultMap")
	AccountEntity findByLoginName(String name);
	
	@Pageable
	List<AccountEntity> findByParam(AccountQueryParam param);
}