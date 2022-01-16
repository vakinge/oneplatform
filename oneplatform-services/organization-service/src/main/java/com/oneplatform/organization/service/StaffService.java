package com.oneplatform.organization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.common.model.Page;
import com.jeesuite.common.model.PageParams;
import com.jeesuite.mybatis.plugin.pagination.PageExecutor;
import com.jeesuite.mybatis.plugin.pagination.PageExecutor.PageDataLoader;
import com.oneplatform.organization.dao.entity.StaffEntity;
import com.oneplatform.organization.dao.mapper.DepartmentEntityMapper;
import com.oneplatform.organization.dao.mapper.PositionEntityMapper;
import com.oneplatform.organization.dao.mapper.StaffEntityMapper;
import com.oneplatform.organization.dto.StaffDetails;
import com.oneplatform.organization.dto.param.StaffQueryParam;


/**
 * <br>
 * Class Name   : StaffService
 */
@Service
public class StaffService {

    private @Autowired
    StaffEntityMapper staffMapper;
    private @Autowired
    DepartmentEntityMapper departmentMapper;
    private @Autowired
    PositionEntityMapper positionMapper;

    public String addStaff(StaffEntity entity) {
        staffMapper.insertSelective(entity);
        return entity.getId();
    }

    public void updateStaff(StaffEntity entity) {
        staffMapper.updateByPrimaryKeySelective(entity);
    }

    public StaffEntity findById(String id) {
    	StaffEntity entity = staffMapper.selectByPrimaryKey(id);
    	return entity;
    }

    public Page<StaffDetails> pageQuery(PageParams pageParams, StaffQueryParam queryParam) {

        Page<StaffDetails> page = PageExecutor.pagination(pageParams, new PageDataLoader<StaffDetails>() {
            @Override
            public List<StaffDetails> load() {
                return staffMapper.findDetailsListByParam(queryParam);
            }
        });
        return page;
    }


}
