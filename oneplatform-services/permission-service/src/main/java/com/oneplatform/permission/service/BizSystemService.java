package com.oneplatform.permission.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.common.util.BeanUtils;
import com.oneplatform.permission.dao.entity.BizSystemModuleEntity;
import com.oneplatform.permission.dao.mapper.BizSystemModuleEntityMapper;
import com.oneplatform.permission.dto.BizSystemModule;

@Service
public class BizSystemService {

	@Autowired
	private BizSystemModuleEntityMapper systemModuleMapper;
	 
	public List<BizSystemModule> getSystemModules() {
		BizSystemModuleEntity example = new BizSystemModuleEntity();
		example.setEnabled(true);
		List<BizSystemModuleEntity> list = systemModuleMapper.selectByExample(example);
		return BeanUtils.copy(list, BizSystemModule.class);
	}

}
