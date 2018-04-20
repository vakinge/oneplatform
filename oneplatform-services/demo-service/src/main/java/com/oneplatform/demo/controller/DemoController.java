package com.oneplatform.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jeesuite.cache.command.RedisString;
import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.filesystem.FileSystemClient;
import com.jeesuite.mybatis.plugin.pagination.Page;
import com.jeesuite.springweb.model.WrapperResponse;
import com.oneplatform.base.LoginContext;
import com.oneplatform.base.exception.ExceptionCode;
import com.oneplatform.base.kit.KafkaProduceClient;
import com.oneplatform.base.log.LogOption;
import com.oneplatform.base.model.IdNamePair;
import com.oneplatform.base.model.LoginUserInfo;
import com.oneplatform.demo.AppConstants;
import com.oneplatform.demo.dao.entity.DemoOrderEntity;
import com.oneplatform.demo.dto.UploadResult;
import com.oneplatform.demo.dto.params.OrderAddParam;
import com.oneplatform.demo.dto.params.OrderPageQueryParams;
import com.oneplatform.demo.dto.params.OrderProcessParam;
import com.oneplatform.demo.service.DemoService;
import com.oneplatform.demo.service.remote.CommonApiFeignClient;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Controller
@RequestMapping("/")
public class DemoController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
    private DemoService demoService;

    @Autowired
    private CommonApiFeignClient commonApiFeignClient;	
	
	private static List<String> allow_upload_suffix = new ArrayList<>(Arrays.asList("png","jpg","zip"));

	
	@ApiOperation(value="演示方法")
    @ApiImplicitParam(name = "name",paramType="query", value = "用户名", required = true, dataType = "String")
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public @ResponseBody String hello(@RequestParam("name") String name) {
		logger.info("param is:{}",name);
		return "hello," + name;
	}
	
	@ApiOperation(value = "创建订单")
	@RequestMapping(value = "/order/create", method = RequestMethod.POST)
	@ResponseBody
	@LogOption(actionName="创建订单")
	public WrapperResponse<String> create(@RequestBody OrderAddParam param) {
		LoginUserInfo operator = LoginContext.getAndValidateLoginUser();
		demoService.addOrder(operator, param);
		return new WrapperResponse<>();
	}
	
	
	@ApiOperation(value = "处理订单")
	@RequestMapping(value = "/order/process", method = RequestMethod.POST)
	@ResponseBody
	public WrapperResponse<String> process(@RequestBody OrderProcessParam param) {
		LoginUserInfo operator = LoginContext.getAndValidateLoginUser();
		demoService.processOrder(operator, param);
		return new WrapperResponse<>();
	}
	
	
	@ApiOperation(value = "分页查询订单列表")
	@RequestMapping(value = "/order/list", method = RequestMethod.POST)
	@LogOption(skip=false)
	public @ResponseBody WrapperResponse<Page<DemoOrderEntity>> pageQueryOrder(@RequestBody OrderPageQueryParams param) {
		LoginUserInfo operator = LoginContext.getAndValidateLoginUser();
		//这里为了演示获取登录信息做数据权限
		if("seller".equals(operator.getUserType())){
			param.setSellerId(operator.getId());
		}else{
			param.setBuyerId(operator.getId());
		}
		
		Page<DemoOrderEntity> page = demoService.pageQueryOrder(operator, param);
		
		return new WrapperResponse<>(page);
	}
	
	//======================以下是一些纯测试接口=============================
	
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
        KafkaProduceClient.send(AppConstants.TOPIC_DEMO, idNamePair);
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
	

	@ApiOperation(value="上传附件", notes="测试通过后台上传附件到七牛")
	@RequestMapping(value = "test/upload", method = RequestMethod.POST)
	@ApiImplicitParams({
		 @ApiImplicitParam(paramType="form",name="type",dataType="int",required=true,value="附件类型(1:物流单,2:评论附件)"),
		 @ApiImplicitParam(paramType="form",name="fileName",dataType="String",required=true,value="文件名"),
		 @ApiImplicitParam(paramType="form",name="file",dataType="File",required=true,value="文件流")
	})
	public @ResponseBody WrapperResponse<UploadResult> uploadConfigFile(HttpServletRequest request,@RequestParam("type") int type,@RequestParam("fileName") String fileName,@RequestParam("file") MultipartFile file){
		try {
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			if(!allow_upload_suffix.contains(suffix.toLowerCase())){
				throw new JeesuiteBaseException(9999, "不允许上传该文件类型");
			}
			
			String savepath = UUID.randomUUID().toString() + "."+suffix;
			//file.transferTo(new File("/Users/jiangwei/"+savepath));
			//上传到七牛
			byte[] bytes = file.getBytes();
			String url = FileSystemClient.getPublicClient().upload(savepath, bytes);
			return new WrapperResponse<UploadResult>(new UploadResult(url, fileName));
		} catch (Exception e) {
			e.printStackTrace();
			throw new JeesuiteBaseException(ExceptionCode.SYSTEM_ERROR.code, "上传失败");
		}
	}
	

}
