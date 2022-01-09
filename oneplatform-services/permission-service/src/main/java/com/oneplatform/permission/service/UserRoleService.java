package com.oneplatform.permission.service;

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
import com.oneplatform.permission.dao.entity.UserRoleEntity;
import com.oneplatform.permission.dao.mapper.SubordinateRelationEntityMapper;
import com.oneplatform.permission.dao.mapper.UserRoleEntityMapper;
import com.oneplatform.permission.dto.UserRole;
import com.oneplatform.permission.dto.param.GrantRelationParam;
import com.oneplatform.permission.dto.param.UserRoleParam;
import com.oneplatform.permission.dto.param.UserRoleQueryParam;


@Service
public class UserRoleService {

    @Autowired private UserRoleEntityMapper userRoleMapper;
    @Autowired private SubordinateRelationEntityMapper subordinateRelationMapper;
    @Autowired private InternalRelationService relationService;

    /**
     * 新增按钮信息
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public IdParam<Integer> addUserRole(UserRoleParam param){

        if(isExisted(param.getName())){
            throw new JeesuiteBaseException("用户组名称已经存在["+param.getName()+"]");
        }
        UserRoleEntity entity = BeanUtils.copy(param, UserRoleEntity.class);
        userRoleMapper.insertSelective(entity);

        return new IdParam<>(entity.getId());
    }

    /**
     * 删除用户组
     * @param id 用户组id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRole(Integer id){
        AssertUtil.notNull(id,"参数缺失[id]");
        UserRoleEntity oldEntity = userRoleMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(oldEntity,"删除用户组不存在");
        userRoleMapper.deleteByPrimaryKey(id);
        subordinateRelationMapper.deleteByParentId(id.toString());

    }

    /**
     * 更新用户组信息
     * @param param 用户组param
     */
    public void updateUserRole(UserRoleParam param){
        AssertUtil.notNull(param.getId(),"参数缺失[id]");
        UserRoleEntity oldEntity = userRoleMapper.selectByPrimaryKey(param.getId());
        AssertUtil.notNull(oldEntity,"更新用户组不存在");

        if(isExisted(param.getName()) && !param.getName().equals(oldEntity.getName())){
            throw new JeesuiteBaseException("用户组名称已经存在["+param.getName()+"]");
        }
        UserRoleEntity entity = BeanUtils.copy(param,oldEntity);
        userRoleMapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 切换用户组激活状态
     * @param id 用户组id
     */
    public void toggleUserRole(Integer id){
        AssertUtil.notNull(id,"参数缺失[id]");
        UserRoleEntity entity = userRoleMapper.selectByPrimaryKey(id);

        entity.setEnabled(!entity.getEnabled());
        userRoleMapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 获取用户组信息
     * @param id 用户组
     * @return
     */
    public UserRole getUserRole(Integer id){
        AssertUtil.notNull(id,"参数缺失[id]");
        UserRoleEntity entity = userRoleMapper.selectByPrimaryKey(id);
        if(entity == null){
            return null;
        }
        UserRole userGroup =  BeanUtils.copy(entity,UserRole.class);
        return userGroup;
    } 


    /**
     * 查询用户组列表
     * @param queryParam 查询参数
     * @return
     */
    public List<UserRole> listByQueryParam(UserRoleQueryParam queryParam){
    	List<UserRoleEntity> entities = userRoleMapper.findByQueryParam(queryParam);
        return BeanUtils.copy(entities, UserRole.class);
    }

    /**
     * 分页查询用户组列表
     * @param pageParams 分页参数
     * @param example 查询参数
     * @return
     */
    public Page<UserRole> pageQryUserRole(PageParams pageParams,UserRoleQueryParam example){
        return PageExecutor.pagination(pageParams, new PageExecutor.ConvertPageDataLoader<UserRoleEntity,UserRole>() {

			@Override
			public List<UserRoleEntity> load() {
				return userRoleMapper.findByQueryParam(example);
			}

			@Override
			public UserRole convert(UserRoleEntity e) {
				return BeanUtils.copy(e, UserRole.class);
			}
        	
        });
    }

    
    @Transactional
    public void updateGrantedPermissions(List<GrantRelationParam> relations){
    	for (GrantRelationParam relation : relations) {
    		relationService.updateGrantRelations(relation);
		}
    }

    private Boolean isExisted(String name){
        UserRoleQueryParam queryParam = new UserRoleQueryParam();
        queryParam.setName(name);
        return !CollectionUtils.isEmpty(userRoleMapper.findByQueryParam(queryParam));
    }

}
