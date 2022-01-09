package com.oneplatform.permission.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesuite.common.model.IdParam;
import com.jeesuite.common.model.Page;
import com.jeesuite.common.model.PageParams;
import com.jeesuite.common.util.AssertUtil;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.common.util.JsonUtils;
import com.jeesuite.mybatis.plugin.pagination.PageExecutor;
import com.oneplatform.permission.constants.ClientType;
import com.oneplatform.permission.constants.FunctionResourceType;
import com.oneplatform.permission.dao.entity.FunctionResourceEntity;
import com.oneplatform.permission.dao.mapper.FunctionResourceEntityMapper;
import com.oneplatform.permission.dao.mapper.SubordinateRelationEntityMapper;
import com.oneplatform.permission.dto.MenuItem;
import com.oneplatform.permission.dto.ResourceTreeModel;
import com.oneplatform.permission.dto.param.FunctionResourceParam;
import com.oneplatform.permission.dto.param.FunctionResourceQueryParam;


@Service
public class FunctionResourceService {

    @Autowired
    private FunctionResourceEntityMapper functionResourceMapper;
    @Autowired(required = false)
    private SubordinateRelationEntityMapper subordinateRelationMapper;
 
    /**
     * 新增菜单
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public IdParam<Integer> addFunctionResource(FunctionResourceParam param){

    	FunctionResourceEntity entity = BeanUtils.copy(param,FunctionResourceEntity.class);
    	entity.setIsDisplay(param.isDisplay());
    	FunctionResourceEntity parent = null;
    	if(param.getParentId() != null) {
    		parent = functionResourceMapper.selectByPrimaryKey(param.getParentId());
    		entity.setClientType(parent.getClientType());
    	}
    	
    	if(!FunctionResourceType.button.name().equals(param.getType())) {
    		buildMenuItems(param, entity);
    	}
    	
    	if(parent != null) {
    		entity.setCode(parent.getCode() + "/" + entity.getCode());
    	}
        
        functionResourceMapper.insertSelective(entity);

        return new IdParam<>(entity.getId());
    }

    /**
     * 根据id删除菜单
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteFunctionResource(Integer id){

        AssertUtil.notNull(id,"参数缺失[id]");
        FunctionResourceEntity entity = functionResourceMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(entity,"删除菜单不存在");
        functionResourceMapper.deleteByPrimaryKey(id);
        subordinateRelationMapper.deleteByParentId(id.toString());
    }

    /**
     * 更新菜单
     * @param param
     */
    public void updateFunctionResource(FunctionResourceParam param){

        AssertUtil.notNull(param.getId(),"参数缺失[id]");
        FunctionResourceEntity oldEntity = functionResourceMapper.selectByPrimaryKey(param.getId());
        AssertUtil.notNull(oldEntity,"更新菜单不存在");

        FunctionResourceEntity entity = BeanUtils.copy(param,oldEntity);
        functionResourceMapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 切换菜单激活状态
     * @param id
     */
    public void switchMenu(Integer id){

        AssertUtil.notNull(id,"参数缺失[id]");
        FunctionResourceEntity entity = functionResourceMapper.selectByPrimaryKey(id);

        entity.setEnabled(!entity.getEnabled());
        functionResourceMapper.updateByPrimaryKeySelective(entity);

    }

    /**
     * 根据id获取菜单
     * @param id
     * @return
     */
    public FunctionResourceEntity getFunctionResourceById(Integer id){
        AssertUtil.notNull(id,"参数缺失[id]");
        FunctionResourceEntity entity = functionResourceMapper.selectByPrimaryKey(id);
        return entity;
    }

    /**
     * 根据查询参数获取菜单列表
     * @param queryParam
     * @return
     */
    public List<FunctionResourceEntity> findByQueryParam(FunctionResourceQueryParam queryParam){
    	List<FunctionResourceEntity> list = functionResourceMapper.findByQueryParam(queryParam);
        return list;
    }

    /**
     * 分页查询业务系统列表
     * @param pageParams
     * @param example
     * @return
     */
    public Page<MenuItem> pageQryFunctionResource(PageParams pageParams, FunctionResourceQueryParam example){

        return PageExecutor.pagination(pageParams, new PageExecutor.ConvertPageDataLoader<FunctionResourceEntity,MenuItem>() {

			@Override
			public List<FunctionResourceEntity> load() {
				return functionResourceMapper.findByQueryParam(example);
			}

			@Override
			public MenuItem convert(FunctionResourceEntity e) {
				return BeanUtils.copy(e, MenuItem.class);
			}
        	
        });
        
    }

    public List<FunctionResourceEntity> findBySystemId(Integer systemId){
    	FunctionResourceQueryParam queryParam = new FunctionResourceQueryParam();
    	queryParam.setEnabled(true);
    	return functionResourceMapper.findByQueryParam(queryParam);
    }
    
    /**
     * 获取系统菜单树 
     * @param systemId
     * @param includeButton
     * @return
     */
    public List<ResourceTreeModel> getSystemMenuTree(boolean includeButton){
        // 1.查出系统的菜单列表
        FunctionResourceQueryParam queryParam = new FunctionResourceQueryParam();
        queryParam.setEnabled(true);
        List<FunctionResourceEntity> entities = functionResourceMapper.findByQueryParam(queryParam);
        if(entities.isEmpty()) {
            return new ArrayList<>(0);
        }
        
        List<ResourceTreeModel> items = entities.stream().map(o -> BeanUtils.copy(o, ResourceTreeModel.class)).collect(Collectors.toList());

        return items;
    }
    
    private void buildMenuItems(FunctionResourceParam param, FunctionResourceEntity entity) {

    	if(param.getItems() == null) {
    		return;
    	}
    	String pcRouter = null;
		String clientType;
		if(param.getItems().size() == 1) {
			pcRouter = param.getItems().get(0).getRouter();
			clientType = param.getItems().get(0).getClientType();
		}else {
			StringBuilder clientTypeBuilder = new StringBuilder();
			for (MenuItem item : param.getItems()) {
				if(ClientType.PC.name().equals(item.getClientType())) {
					pcRouter = item.getRouter();
				}
				clientTypeBuilder.append(item.getClientType()).append(",");
			}
			clientTypeBuilder.deleteCharAt(clientTypeBuilder.length() - 1);
			clientType = clientTypeBuilder.toString();
		}
		entity.setCode(pcRouter);
		entity.setClientType(clientType);
		entity.setItemContent(JsonUtils.toJson(param.getItems()));
	}

}
