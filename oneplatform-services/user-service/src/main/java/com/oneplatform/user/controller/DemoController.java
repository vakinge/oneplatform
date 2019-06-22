package com.oneplatform.user.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesuite.cache.command.RedisString;
import com.jeesuite.springweb.model.WrapperResponse;
import com.oneplatform.base.kit.AsyncTaskProduceClient;
import com.oneplatform.base.model.IdNamePair;
import com.oneplatform.user.AppConstants;
import com.oneplatform.user.service.remote.CommonApiFeignClient;

import io.swagger.annotations.ApiOperation;


@Controller
@RequestMapping("/")
public class DemoController {

	private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private CommonApiFeignClient commonApiFeignClient;	
	
	private static List<String> allow_upload_suffix = new ArrayList<>(Arrays.asList("png","jpg","zip"));


	@ApiOperation(value="测试redis缓存")
	@RequestMapping(value = "/test/cache", method = RequestMethod.GET)
	public @ResponseBody WrapperResponse<Long> cacheTest() {
		//字符串
		RedisString redisString = new RedisString("User.id:1001");
		redisString.set("user1001",60);
		System.out.println("其他接口参考:http://www.jeesuite.com/docs/jeesuite-libs/cache.html");
		return new WrapperResponse<>(redisString.getTtl());
	}
	
	@ApiOperation(value="测试发送kafka消息",notes="生产一条kafka消息，由消费者处理")
	@RequestMapping(value = "/test/kafka", method = RequestMethod.GET)
	public @ResponseBody WrapperResponse<String> kafkaTest() {
		
        IdNamePair idNamePair = new IdNamePair(System.currentTimeMillis(), "kafka-"+System.currentTimeMillis());
        AsyncTaskProduceClient.send(AppConstants.TOPIC_DEMO, idNamePair);
		return new WrapperResponse<>(200,"发送成功");
	}
	
	@ApiOperation(value="获取省份列表",notes="测试通过Feign跨服务调用，调用common-service提供的接口")
	@RequestMapping(value = "test/provinces", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<IdNamePair>> getProvinces() {
		List<IdNamePair> list = commonApiFeignClient.findRegionByParentId(0);
        return new WrapperResponse<>(list);
    }
	
	@ApiOperation(value="获取城市列表",notes="测试通过Feign跨服务调用，调用common-service提供的接口")
	@RequestMapping(value = "test/citys/{provinceId}", method = RequestMethod.GET)
    public @ResponseBody WrapperResponse<List<IdNamePair>> getCitys(@PathVariable("provinceId") int provinceId) {
		List<IdNamePair> list = commonApiFeignClient.findRegionByParentId(provinceId);
        return new WrapperResponse<>(list);
    }
	

}
