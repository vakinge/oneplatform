package com.oneplatform.organisation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.oneplatform.base.dao.CustomBaseMapper;
import com.oneplatform.organisation.dao.entity.EmployeeDepartmentEntity;

public interface EmployeeDepartmentEntityMapper extends CustomBaseMapper<EmployeeDepartmentEntity> {
	
	@Select("select * from employee_departments where employee_id=#{employeeId} and in_active=1 order by id")
	@ResultMap("BaseResultMap")
	List<EmployeeDepartmentEntity> findActiveByEmployeeId(int employeeId );
}