package com.oneplatform.modules.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.common.model.IdParam;
import com.jeesuite.common.model.Page;
import com.jeesuite.common.model.PageParams;
import com.jeesuite.common.util.AssertUtil;
import com.jeesuite.common.util.BeanUtils;
import com.jeesuite.mybatis.plugin.pagination.PageExecutor;
import com.oneplatform.modules.system.dao.entity.ApiResourceEntity;
import com.oneplatform.modules.system.dao.mapper.ApiResourceEntityMapper;
import com.oneplatform.modules.system.dto.ApiResource;
import com.oneplatform.modules.system.dto.param.ApiResourceParam;
import com.oneplatform.modules.system.dto.param.ApiResourceQueryParam;

/**
 * <br>
 * Class Name : ApiResourceService
 *
 * @author jiangwei
 * @version 1.0.0
 * @date 2019年1月4日
 */
@Service
public class ApiResourceService {

	@Autowired
	private ApiResourceEntityMapper apiResourceMapper;
    /**
     * add
     * @param param
     * @return
     */
    public IdParam<Integer> addApiResource(ApiResourceParam param){

        if(apiResourceMapper.findByRequestUri(param.getAppId(), param.getUri(), param.getHttpMethod()) != null){
            throw new JeesuiteBaseException("接口已经存在["+param.getUri()+"]");
        }

        ApiResourceEntity entity = BeanUtils.copy(param, ApiResourceEntity.class);
        entity.build();
        apiResourceMapper.insertSelective(entity);

        return new IdParam<>(entity.getId());
    }

    /**
     * 根据id删除apiResource
     * @param id
     */
    public void deleteApiResource(Integer id){

        AssertUtil.notNull(id,"参数缺失[id]");
        ApiResourceEntity oldEntity = apiResourceMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(oldEntity,"删除接口不存在");
        apiResourceMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新apiResource
     * @param param
     */
    public void updateApiResource(ApiResourceParam param){

        AssertUtil.notNull(param.getId(),"参数缺失[id]");
        ApiResourceEntity oldEntity = apiResourceMapper.selectByPrimaryKey(param.getId());
        AssertUtil.notNull(oldEntity,"更新接口不存在");

        ApiResourceEntity sameUriEntity = apiResourceMapper.findByRequestUri(param.getAppId(), param.getUri(), param.getHttpMethod());
        if(sameUriEntity != null && !sameUriEntity.getId().equals(param.getId())){
            throw new JeesuiteBaseException("接口已经存在["+param.getUri()+"]");
        }

        ApiResourceEntity entity = BeanUtils.copy(param,oldEntity);
        entity.build();
        apiResourceMapper.updateByPrimaryKey(entity);
    }

    /**
     * 接口激活状态变更
     * @param id
     */
    public void switchApiResource(Integer id){

        AssertUtil.notNull(id,"参数缺失[id]");

        ApiResourceEntity entity = apiResourceMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(entity,"激活/禁用接口不存在");

        entity.setEnabled(!entity.getEnabled());
        entity.build();
        apiResourceMapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 根据id查询接口信息
     * @param id
     * @return
     */
    public ApiResource getApiResourceById(Integer id){

        AssertUtil.notNull(id,"参数缺失[id]");
        return BeanUtils.copy(apiResourceMapper.selectByPrimaryKey(id),ApiResource.class);
    }

    /**
     * 根据查询参数查询接口列表
     * @param queryParam
     * @return
     */
    public List<ApiResource> listByQueryParam(ApiResourceQueryParam queryParam){
        return BeanUtils.copy(apiResourceMapper.findByQueryParam(queryParam),ApiResource.class);
    }


    public List<ApiResourceEntity> findByModuleId(Integer moduleId){
        ApiResourceQueryParam queryParam = new ApiResourceQueryParam();
        queryParam.setModuleId(moduleId);
        queryParam.setEnabled(true);
        //
        List<ApiResourceEntity> list = apiResourceMapper.findByQueryParam(queryParam);
        return list;
    }

    /**
     * 分页查询api资源列表
     * @param pageParams
     * @param example
     * @return
     */
    public Page<ApiResource> pageQry(PageParams pageParams, ApiResourceQueryParam example) {
    
        return PageExecutor.pagination(pageParams, new PageExecutor.ConvertPageDataLoader<ApiResourceEntity, ApiResource>() {
            @Override
            public ApiResource convert(ApiResourceEntity apiResourceEntity) {
                return BeanUtils.copy(apiResourceEntity,ApiResource.class);
            }

            @Override
            public List<ApiResourceEntity> load() {
                return apiResourceMapper.findByQueryParam(example == null ? new ApiResourceQueryParam() : example);
            }
        });
    }


}
