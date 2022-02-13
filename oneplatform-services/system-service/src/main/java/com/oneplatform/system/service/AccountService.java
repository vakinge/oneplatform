package com.oneplatform.system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.common.model.Page;
import com.jeesuite.common.model.PageParams;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.common.util.FormatValidateUtils;
import com.jeesuite.mybatis.plugin.pagination.PageExecutor;
import com.oneplatform.system.dao.entity.AccountEntity;
import com.oneplatform.system.dao.entity.AccountScopeEntity;
import com.oneplatform.system.dao.mapper.AccountEntityMapper;
import com.oneplatform.system.dao.mapper.AccountScopeEntityMapper;
import com.oneplatform.system.dto.Account;
import com.oneplatform.system.dto.AccountScope;
import com.oneplatform.system.dto.param.AccountQueryParam;

@Service
public class AccountService {

	@Autowired
	private AccountEntityMapper  accountMapper;
	
	@Autowired
	private AccountScopeEntityMapper accountScopeMapper;
	
	public void addAccount(AccountEntity entity){
		accountMapper.insertSelective(entity);
	}
	
	public void updateAccount(AccountEntity entity){
		accountMapper.updateByPrimaryKeySelective(entity);
	}
	

	public AccountEntity findById(String id){
		return accountMapper.selectByPrimaryKey(id);
	}


	public List<AccountEntity> findListByParam(AccountQueryParam param){
		return accountMapper.findListByParam(param);
	}
	
	public Page<Account> pageQuery(PageParams pageParams, AccountQueryParam example) {
	    
        return PageExecutor.pagination(pageParams, new PageExecutor.ConvertPageDataLoader<AccountEntity, Account>() {
            @Override
            public Account convert(AccountEntity apiResourceEntity) {
                return BeanUtils.copy(apiResourceEntity,Account.class);
            }

            @Override
            public List<AccountEntity> load() {
                return accountMapper.findListByParam(example == null ? new AccountQueryParam() : example);
            }
        });
    }
	
	public Account findByAccount(String account) {
		AccountEntity entity;
		if(FormatValidateUtils.isMobile(account)) {
			entity = accountMapper.findByMobile(account);
		}else if(FormatValidateUtils.isEmail(account)) {
			entity = accountMapper.findByEmail(account);
		}else {
			entity = accountMapper.findByName(account);
		}
		if(entity == null || !entity.getEnabled()) {
			throw new JeesuiteBaseException("账号不存在或者已停用");
		}
		
		Account acountDto = BeanUtils.copy(entity, Account.class);

		return acountDto;
	}
	
	public Account validateAccount(String account,String password) {
		Account acountDto = findByAccount(account);
		if(!BCrypt.checkpw(password, acountDto.getPassword())) {
			throw new JeesuiteBaseException("账号不存在或密码错误");
		}
		return acountDto;
	}
	
	public List<AccountScope> findAccountScopes(String accountId){
		List<AccountScopeEntity> scopes = accountScopeMapper.findByAccountId(accountId);
		return scopes.stream().map(o -> {
			AccountScope scope = new AccountScope();
			scope.setPrincipalType(o.getPrincipalType());
			scope.setPrincipalId(o.getPrincipalId());
			scope.setTenantId(o.getTenantId());
			scope.setAdmin(o.getIsAdmin());
			return scope;
		}).collect(Collectors.toList());
	}
}
