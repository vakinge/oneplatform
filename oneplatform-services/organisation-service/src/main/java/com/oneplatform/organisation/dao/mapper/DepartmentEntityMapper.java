package com.oneplatform.organisation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.oneplatform.base.dao.CustomBaseMapper;
import com.oneplatform.organisation.dao.entity.DepartmentEntity;

public interface DepartmentEntityMapper extends CustomBaseMapper<DepartmentEntity> {
	
	@Select("select * from department where branch_id=#{branchId} order by id")
	@ResultMap("BaseResultMap")
	List<DepartmentEntity> findByBranchId(int branchId);
	
	@Select("select * from department where in_active=1 order by id")
	@ResultMap("BaseResultMap")
	List<DepartmentEntity> findAllActive();
}