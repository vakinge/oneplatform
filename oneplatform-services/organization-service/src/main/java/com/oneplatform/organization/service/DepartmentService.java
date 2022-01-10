package com.oneplatform.organization.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.common.util.DigestUtils;
import com.oneplatform.organization.dao.entity.DepartmentEntity;
import com.oneplatform.organization.dao.entity.StaffEntity;
import com.oneplatform.organization.dao.mapper.DepartmentEntityMapper;
import com.oneplatform.organization.dao.mapper.StaffEntityMapper;
import com.oneplatform.organization.dto.Department;
import com.oneplatform.organization.dto.param.DepartmentQueryParam;

/**
 * 
 * <br>
 * Class Name   : DepartmentService
 */
@Service
public class DepartmentService {

	private @Autowired DepartmentEntityMapper departmentMapper;
	private @Autowired StaffEntityMapper staffMapper;

	public String addDepartment(DepartmentEntity entity){
		
		String code = DigestUtils.md5Short(UUID.randomUUID().toString());
		entity.setCode(code);
		if(StringUtils.isNotBlank(entity.getParentId())) {
			DepartmentEntity parent = departmentMapper.selectByPrimaryKey(entity.getParentId());
			if(parent == null || !parent.getEnabled()) {
				throw new JeesuiteBaseException("父级部门不存在或已禁用");
			}
			entity.setFullCode(String.format("%s_%s", parent.getCode(),code));
			entity.setFullName(String.format("%s_%s", parent.getName(),entity.getName()));
		}else {
			entity.setFullCode(code);
			entity.setFullName(entity.getName());
		}
		
		if(StringUtils.isNotBlank(entity.getLeaderId())) {
			StaffEntity staffEntity = staffMapper.selectByPrimaryKey(entity.getLeaderId());
			if(staffEntity == null || !staffEntity.getEnabled()) {
				throw new JeesuiteBaseException("选择负责人不存在或已禁用");
			}
			staffEntity.setIsLeader(true);
			staffMapper.updateByPrimaryKeySelective(staffEntity);
		}
		
		departmentMapper.insertSelective(entity);
		return entity.getId();
	}
	
	public void updateDepartment(DepartmentEntity entity){
		departmentMapper.updateByPrimaryKeySelective(entity);
	}
	
	public DepartmentEntity findById(String id) {
		return departmentMapper.selectByPrimaryKey(id);
	}

	public List<Department> findDepartmentList(DepartmentQueryParam param){
		List<DepartmentEntity> list = departmentMapper.findListByQueryParam(param);
		return list.stream().map(o -> {
			Department department = BeanUtils.copy(o, Department.class);
			return department;
		}).collect(Collectors.toList());
	}

}
