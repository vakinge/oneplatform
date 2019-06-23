package com.oneplatform.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneplatform.system.dao.entity.BizPlatformEntity;
import com.oneplatform.system.dao.mapper.BizPlatformEntityMapper;

@Service
public class BizPlatformService {

	private @Autowired BizPlatformEntityMapper bizPlatformMapper;
	
	List<BizPlatformEntity> findAllBizPlatforms(){
		return bizPlatformMapper.selectAll();
	}
	
}
