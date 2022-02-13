package com.oneplatform.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.logging.integrate.ActionLog;
import com.jeesuite.logging.integrate.LogStorageProvider;
import com.oneplatform.system.dao.entity.ActionLogEntity;
import com.oneplatform.system.dao.mapper.ActionLogEntityMapper;



@Service
public class LocalLogStorageProvider implements LogStorageProvider {

	@Autowired
	private ActionLogEntityMapper actionLogMapper;
	
	@Override
	public void storage(ActionLog actionLog) {
		ActionLogEntity entity = BeanUtils.copy(actionLog, ActionLogEntity.class);
		actionLogMapper.insertSelective(entity);
	}

}
