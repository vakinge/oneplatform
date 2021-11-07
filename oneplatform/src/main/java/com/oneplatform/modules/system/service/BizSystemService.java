package com.oneplatform.modules.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.zuul.SystemMgrApi;
import com.jeesuite.zuul.model.BizSystemModule;
import com.oneplatform.modules.system.dao.entity.BizSystemModuleEntity;
import com.oneplatform.modules.system.dao.mapper.BizSystemModuleEntityMapper;

@Service
public class BizSystemService implements SystemMgrApi {

	@Autowired
	private BizSystemModuleEntityMapper systemModuleMapper;
	 
	@Override
	public List<BizSystemModule> getSystemModules() {
		BizSystemModuleEntity example = new BizSystemModuleEntity();
		example.setEnabled(true);
		List<BizSystemModuleEntity> list = systemModuleMapper.selectByExample(example);
		return BeanUtils.copy(list, BizSystemModule.class);
	}

}
