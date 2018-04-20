package com.oneplatform.demo.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.common2.lock.redis.RedisDistributeLock;
import com.oneplatform.base.exception.ExceptionCode;
import com.oneplatform.demo.dto.Product;


/**
 * 这是一个虚拟服务
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年3月11日
 */
@Service
public class ProductVirtualService {

	/**
	 * 检查库存并获取商品明细
	 * @param productId
	 * @return
	 */
	public Product findIfStockEnough(Integer productId){
		
		//这里查库存需要加一个分布式锁
		RedisDistributeLock lock = new RedisDistributeLock("stock:"+productId);
		try {
			lock.lock();
			Product product = new Product();
			
			if(productId <= 100)throw new JeesuiteBaseException(ExceptionCode.RECORD_NOT_EXIST.code,"商品不存在");
			//这里数据库查询
			product.setId(productId);
			product.setName("演示商品");
			product.setOwnId(1000);
			product.setPrice(new BigDecimal("19.9"));
			product.setStock(1000);
			return product;
		} finally {
			lock.unlock();
		}

	}
}
