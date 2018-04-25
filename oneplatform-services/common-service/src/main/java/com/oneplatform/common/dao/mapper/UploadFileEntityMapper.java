package com.oneplatform.common.dao.mapper;

import java.util.List;

import com.jeesuite.mybatis.plugin.pagination.annotation.Pageable;
import com.oneplatform.base.dao.CustomBaseMapper;
import com.oneplatform.common.dao.entity.UploadFileEntity;
import com.oneplatform.common.dto.param.UploadQueryParam;

public interface UploadFileEntityMapper extends CustomBaseMapper<UploadFileEntity> {
	
	@Pageable
	List<UploadFileEntity> findByParam(UploadQueryParam param);
}