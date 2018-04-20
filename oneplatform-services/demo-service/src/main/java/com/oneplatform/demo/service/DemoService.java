package com.oneplatform.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.confcenter.ConfigChangeHanlder;
import com.jeesuite.mybatis.plugin.pagination.Page;
import com.jeesuite.mybatis.plugin.pagination.PageExecutor;
import com.jeesuite.mybatis.plugin.pagination.PageExecutor.PageDataLoader;
import com.jeesuite.mybatis.plugin.pagination.PageParams;
import com.jeesuite.spring.ApplicationStartedListener;
import com.oneplatform.base.exception.AssertUtil;
import com.oneplatform.base.exception.ExceptionCode;
import com.oneplatform.base.model.LoginUserInfo;
import com.oneplatform.demo.dao.entity.DemoOrderEntity;
import com.oneplatform.demo.dao.mapper.DemoOrderEntityMapper;
import com.oneplatform.demo.dto.Product;
import com.oneplatform.demo.dto.params.OrderAddParam;
import com.oneplatform.demo.dto.params.OrderPageQueryParams;
import com.oneplatform.demo.dto.params.OrderProcessParam;


@Service
public class DemoService implements ConfigChangeHanlder,ApplicationStartedListener{
	
	@Autowired private DemoOrderEntityMapper dao;
	
	@Autowired private ProductVirtualService productService;
	
	/**
	 * 是否允许下单，默认为true
	 */
	@Value("${create.order.enabled:true}")
	private volatile boolean createOrderEnabled;
	
	public void addOrder(LoginUserInfo operator,OrderAddParam param){
		
		AssertUtil.isTrue(createOrderEnabled,ExceptionCode.ILLEGAL_STATE.code, "当前不允许下单");
		
		Product product = productService.findIfStockEnough(param.getProductId());
		
		DemoOrderEntity orderEntity = new DemoOrderEntity();
		//这里演示就参与这种方式生成，正式使用需要提供全局唯一保证的Orderno生成机制
		orderEntity.setOrderNo(System.currentTimeMillis() + RandomStringUtils.randomNumeric(5));
		orderEntity.setBuyerId(operator.getId());
		orderEntity.setBuyerName(operator.getUsername());
		orderEntity.setMemo(param.getMemo());
		orderEntity.setProductId(product.getId() + "");
		orderEntity.setProductName(product.getName());
		orderEntity.setAmount(product.getPrice());
		orderEntity.setSellerId(product.getOwnId());
		orderEntity.setStatus(DemoOrderEntity.OrderStatus.NEW.name());
		orderEntity.setCreatedAt(new Date());
		orderEntity.setCreatedBy(operator.getId());
		
		dao.insertSelective(orderEntity);
		
	}
	
	@Transactional
    public void processOrder(LoginUserInfo operator,OrderProcessParam param){
		DemoOrderEntity orderEntity = dao.selectByPrimaryKey(param.getOrderId());
		AssertUtil.notNull(orderEntity);
		
		if(orderEntity.getStatus().equals(DemoOrderEntity.OrderStatus.NEW.name())){
			orderEntity.setStatus(DemoOrderEntity.OrderStatus.PAID.name());
		}else if(orderEntity.getStatus().equals(DemoOrderEntity.OrderStatus.PAID.name())){
			orderEntity.setStatus(DemoOrderEntity.OrderStatus.DELIVERED.name());
		}else if(orderEntity.getStatus().equals(DemoOrderEntity.OrderStatus.DELIVERED.name())){
			orderEntity.setStatus(DemoOrderEntity.OrderStatus.RECEIVED.name());
		}else{
			throw new JeesuiteBaseException(ExceptionCode.ILLEGAL_STATE.code, "当前订单不允许该操作");
		}
		
		orderEntity.setUpdatedAt(new Date());
		orderEntity.setUpdatedBy(operator.getId());
		
		dao.updateByPrimaryKeySelective(orderEntity);
		
		//TODO 状态变更日志
	}
    
    public void cancelOrder(LoginUserInfo operator,OrderProcessParam param){
		DemoOrderEntity orderEntity = dao.selectByPrimaryKey(param.getOrderId());
		AssertUtil.notNull(orderEntity);
		AssertUtil.isTrue(!orderEntity.getStatus().equals(DemoOrderEntity.OrderStatus.CANCEL.name()),ExceptionCode.ILLEGAL_STATE.code,"该订单已取消");
		
		orderEntity.setStatus(DemoOrderEntity.OrderStatus.CANCEL.name());
		orderEntity.setUpdatedAt(new Date());
		orderEntity.setUpdatedBy(operator.getId());
		
		dao.updateByPrimaryKeySelective(orderEntity);
	}
	
    /**
     * 分页查询1（查询接口直接实现）
     * @param operator
     * @param param
     * @return
     */
	public Page<DemoOrderEntity> pageQueryOrder(LoginUserInfo operator,OrderPageQueryParams param){
		Page<DemoOrderEntity> page = dao.findByQueryParam((PageParams)param, param);
		
		//对当条对象封装处理
		for (DemoOrderEntity entity : page.getData()) {
			//TODO 	
		}
		return page;
	}
	
	/**
     * 分页查询2（通过@Pageable 实现）
     * @param operator
     * @param param
     * @return
     */
	public Page<DemoOrderEntity> pageQueryOrderByStatus(LoginUserInfo operator,OrderPageQueryParams param){
		
		Page<DemoOrderEntity> page = PageExecutor.pagination(new PageParams(param.getPageNo(), param.getPageSize()), new PageDataLoader<DemoOrderEntity>() {
			@Override
			public List<DemoOrderEntity> load() {
				return dao.findByStatus(param.getStatus());
			}
		});
		
		//对当条对象封装处理
		for (DemoOrderEntity entity : page.getData()) {
			//TODO 		
		}
		
		return page;
	}

	@Override
	public void onConfigChanged(Map<String, Object> changedConfigs) {
		//如果配置中心修改了某些配置需要实时生效，就在这里写逻辑
		if(changedConfigs.containsKey("create.order.enabled")){
			createOrderEnabled = Boolean.parseBoolean(changedConfigs.get("create.order.enabled").toString());
		}
	}

	@Override
	public void onApplicationStarted(ApplicationContext applicationContext) {
		System.out.println("=====>如果需要某些操作在应用完全启动完成后执行,可以在这里实现,继承[ApplicationStartedListener]即可");
	}

}
