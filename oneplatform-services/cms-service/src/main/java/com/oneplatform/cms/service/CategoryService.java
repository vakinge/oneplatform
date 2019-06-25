package com.oneplatform.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.common.util.BeanUtils;
import com.oneplatform.cms.dao.entity.CategoryEntity;
import com.oneplatform.cms.dao.mapper.CategoryEntityMapper;
import com.oneplatform.cms.dto.param.CategoryParam;

/**
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @website <a href="http://www.jeesuite.com">vakin</a>
 * @date 2019年6月24日
 */
@Service
public class CategoryService {

	private @Autowired CategoryEntityMapper categoryMapper;
	
	public List<CategoryEntity> findAllCategories(){
		List<CategoryEntity> list = categoryMapper.selectAll();
		return list;
	}
	
	public void addCategory(CategoryParam param){
		CategoryEntity entity = BeanUtils.copy(param, CategoryEntity.class); 
		categoryMapper.insertSelective(entity);
	}
	
}
