package com.oneplatform.organisation.service.remote;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.oneplatform.base.model.IdNamePair;

@Component
public class CommonApiClientFallback implements CommonApiFeignClient {

	@Override
	public List<IdNamePair> findRegionByParentId(int pId) {
		return Collections.emptyList();
	}

}
