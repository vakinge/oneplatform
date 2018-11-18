package com.oneplatform.organisation.service.remote;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.oneplatform.base.model.IdNamePair;

@FeignClient(value = "COMMON-SERVICE",path="/region",fallback = CommonApiClientFallback.class)
public interface CommonApiFeignClient {

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<IdNamePair> findRegionByParentId(@RequestParam(name="pid",required = false ,defaultValue = "0") int pid);
	
	
}
