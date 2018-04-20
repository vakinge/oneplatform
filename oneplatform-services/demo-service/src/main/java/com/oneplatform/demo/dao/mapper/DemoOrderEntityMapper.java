package com.oneplatform.demo.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.jeesuite.mybatis.plugin.cache.annotation.Cache;
import com.jeesuite.mybatis.plugin.pagination.Page;
import com.jeesuite.mybatis.plugin.pagination.PageParams;
import com.jeesuite.mybatis.plugin.pagination.annotation.Pageable;
import com.oneplatform.base.dao.CustomBaseMapper;
import com.oneplatform.demo.dao.entity.DemoOrderEntity;
import com.oneplatform.demo.dto.params.OrderPageQueryParams;

public interface DemoOrderEntityMapper extends CustomBaseMapper<DemoOrderEntity> {
	
	@Cache
	@Pageable
	@Select("SELECT * FROM demo_order where status=#{status}")
	@ResultMap("BaseResultMap")
	List<DemoOrderEntity> findByStatus(String status);
	
	@ResultMap("BaseResultMap")
	Page<DemoOrderEntity> findByQueryParam(@Param("pageParam") PageParams page ,@Param("condition") OrderPageQueryParams conditions);
	

	@Cache
	@Select("SELECT * FROM demo_order where order_no=#{orderNo} limit 1")
	@ResultMap("BaseResultMap")
	DemoOrderEntity findByOrderNo(@Param("orderNo") String orderNo);
}