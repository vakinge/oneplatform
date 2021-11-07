package com.oneplatform.modules.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.common.model.IdParam;
import com.jeesuite.common.model.Page;
import com.jeesuite.common.model.PageParams;
import com.jeesuite.common.util.AssertUtil;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.mybatis.plugin.pagination.PageExecutor;
import com.oneplatform.modules.system.dao.entity.UserGroupEntity;
import com.oneplatform.modules.system.dao.mapper.SubordinateRelationEntityMapper;
import com.oneplatform.modules.system.dao.mapper.UserGroupEntityMapper;
import com.oneplatform.modules.system.dto.UserGroup;
import com.oneplatform.modules.system.dto.param.GrantRelationParam;
import com.oneplatform.modules.system.dto.param.UserGroupParam;
import com.oneplatform.modules.system.dto.param.UserGroupQueryParam;


@Service
public class UserGroupService {

    @Autowired private UserGroupEntityMapper userGroupMapper;
    @Autowired private SubordinateRelationEntityMapper subordinateRelationMapper;
    @Autowired private RelationInternalService relationInternalService;

    /**
     * 新增按钮信息
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public IdParam<Integer> addUserGroup(UserGroupParam param){

        if(isExisted(param.getName())){
            throw new JeesuiteBaseException("用户组名称已经存在["+param.getName()+"]");
        }
        UserGroupEntity entity = BeanUtils.copy(param, UserGroupEntity.class);
        entity.build();
        userGroupMapper.insertSelective(entity);

        return new IdParam<>(entity.getId());
    }

    /**
     * 删除用户组
     * @param id 用户组id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserGroup(Integer id){
        AssertUtil.notNull(id,"参数缺失[id]");
        UserGroupEntity oldEntity = userGroupMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(oldEntity,"删除用户组不存在");
        userGroupMapper.deleteByPrimaryKey(id);
        subordinateRelationMapper.deleteByParentId(id.toString());

    }

    /**
     * 更新用户组信息
     * @param param 用户组param
     */
    public void updateUserGroup(UserGroupParam param){
        AssertUtil.notNull(param.getId(),"参数缺失[id]");
        UserGroupEntity oldEntity = userGroupMapper.selectByPrimaryKey(param.getId());
        AssertUtil.notNull(oldEntity,"更新用户组不存在");

        if(isExisted(param.getName()) && !param.getName().equals(oldEntity.getName())){
            throw new JeesuiteBaseException("用户组名称已经存在["+param.getName()+"]");
        }
        UserGroupEntity entity = BeanUtils.copy(param,oldEntity);
        entity.build();
        userGroupMapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 切换用户组激活状态
     * @param id 用户组id
     */
    public void switchUserGroup(Integer id){
        AssertUtil.notNull(id,"参数缺失[id]");
        UserGroupEntity entity = userGroupMapper.selectByPrimaryKey(id);

        entity.setEnabled(!entity.getEnabled());
        entity.build();
        userGroupMapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 获取用户组信息
     * @param id 用户组
     * @return
     */
    public UserGroup getUserGroup(Integer id){
        AssertUtil.notNull(id,"参数缺失[id]");
        UserGroupEntity entity = userGroupMapper.selectByPrimaryKey(id);
        if(entity == null){
            return null;
        }
        UserGroup userGroup =  BeanUtils.copy(entity,UserGroup.class);
        return userGroup;
    } 

    public List<UserGroup> listByTenantId(String tenantId){
    	UserGroupQueryParam queryParam = new UserGroupQueryParam();
    	queryParam.setTenantId(tenantId);
        List<UserGroupEntity> list = userGroupMapper.findByQueryParam(queryParam);
        return BeanUtils.copy(list, UserGroup.class);
    }

    /**
     * 查询用户组列表
     * @param queryParam 查询参数
     * @return
     */
    public List<UserGroup> listByQueryParam(UserGroupQueryParam queryParam){
    	List<UserGroupEntity> entities = userGroupMapper.findByQueryParam(queryParam);
        return BeanUtils.copy(entities, UserGroup.class);
    }

    /**
     * 分页查询用户组列表
     * @param pageParams 分页参数
     * @param example 查询参数
     * @return
     */
    public Page<UserGroup> pageQryUserGroup(PageParams pageParams,UserGroupQueryParam example){
        return PageExecutor.pagination(pageParams, new PageExecutor.ConvertPageDataLoader<UserGroupEntity,UserGroup>() {

			@Override
			public List<UserGroupEntity> load() {
				return userGroupMapper.findByQueryParam(example);
			}

			@Override
			public UserGroup convert(UserGroupEntity e) {
				return BeanUtils.copy(e, UserGroup.class);
			}
        	
        });
    }

    
    @Transactional
    public void updateGrantedPermissions(List<GrantRelationParam> relations){
    	for (GrantRelationParam relation : relations) {
    		relationInternalService.updateGrantRelations(relation);
		}
    }

    private Boolean isExisted(String name){
        UserGroupQueryParam queryParam = new UserGroupQueryParam();
        queryParam.setName(name);
        return !CollectionUtils.isEmpty(userGroupMapper.findByQueryParam(queryParam));
    }

}
