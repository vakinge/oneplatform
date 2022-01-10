package com.oneplatform.organization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.common.model.Page;
import com.jeesuite.common.model.PageParams;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.mybatis.plugin.pagination.PageExecutor;
import com.oneplatform.organization.dao.entity.PositionEntity;
import com.oneplatform.organization.dao.mapper.PositionEntityMapper;
import com.oneplatform.organization.dto.Position;
import com.oneplatform.organization.dto.param.PositionQueryParam;

/**
 * 
 * <br>
 * Class Name   : PositionService
 */
@Service
public class PositionService {

	private @Autowired PositionEntityMapper positionMapper;
	
	public void addPosition(PositionEntity entity){
		positionMapper.insertSelective(entity);
	}
	
	public void updatePosition(PositionEntity entity){
		positionMapper.updateByPrimaryKeySelective(entity);
	}
	

	public PositionEntity findById(String id){
		return positionMapper.selectByPrimaryKey(id);
	}


	public List<PositionEntity> findListByParam(PositionQueryParam param){
		return positionMapper.findListByParam(param);
	}
	
	public Page<Position> pageQuery(PageParams pageParams, PositionQueryParam example) {
	    
        return PageExecutor.pagination(pageParams, new PageExecutor.ConvertPageDataLoader<PositionEntity, Position>() {
            @Override
            public Position convert(PositionEntity apiResourceEntity) {
                return BeanUtils.copy(apiResourceEntity,Position.class);
            }

            @Override
            public List<PositionEntity> load() {
                return positionMapper.findListByParam(example == null ? new PositionQueryParam() : example);
            }
        });
    }

}
