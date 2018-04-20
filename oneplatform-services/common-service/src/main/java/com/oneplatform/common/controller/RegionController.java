package com.oneplatform.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oneplatform.base.model.IdNamePair;
import com.oneplatform.common.dao.entity.RegionEntity;
import com.oneplatform.common.dao.mapper.RegionEntityMapper;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/region")
public class RegionController {

	@Autowired
	private RegionEntityMapper provinceMapper;
	
	@ApiOperation(value="获取城市列表", notes="获取指定省城市列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<IdNamePair> findRegionByParentId(@RequestParam(name="pid",required = false ,defaultValue = "0") int pid) {
		List<IdNamePair> result = new ArrayList<>();
		List<RegionEntity> regions = provinceMapper.findByParentId(pid);
		for (RegionEntity entity : regions) {
			result.add(new IdNamePair(entity.getId(), entity.getName()));
		}
		return result;
	}

}
