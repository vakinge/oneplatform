package com.oneplatform.modules.system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.common.util.AssertUtil;
import com.oneplatform.modules.system.dao.entity.AccountEntity;
import com.oneplatform.modules.system.dao.entity.AccountScopeEntity;
import com.oneplatform.modules.system.dao.mapper.AccountEntityMapper;
import com.oneplatform.modules.system.dao.mapper.AccountScopeEntityMapper;

@Service
public class AccountService {

	@Autowired
	private AccountEntityMapper mapper;
	
	@Autowired
	private AccountScopeEntityMapper accountScopeMapper;
	
	public AccountEntity findByAccount(String account) {
		AccountEntity entity = mapper.findByLoginAccount(account);
		if(entity == null)return null;
        //查询授权范围
        AccountScopeEntity example = new AccountScopeEntity();
        example.setAccountId(entity.getId());
        example.setEnabled(true);
        List<AccountScopeEntity> scopes = accountScopeMapper.selectByExample(example);
        entity.setAccountScopes(scopes);
        
		return entity;
	}


	public AccountEntity getById(Integer id) {
		AccountEntity entity = mapper.selectByPrimaryKey(id);
        AssertUtil.notNull(entity);
		return entity;
    }

}
