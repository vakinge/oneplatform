### 使用案例
[一个传统jersey的web项目接入Springcloud例子](https://gitee.com/vakinge/jerseydemo) 

### 以下是详细步骤
### 新增依赖
```
<dependency>
	<groupId>com.jeesuite</groupId>
	<artifactId>oneplatform-base</artifactId>
	<version>1.0.0</version>
</dependency>
```

### 添加eureka配置

```
#全局配置
eureka.region=default
eureka.registration.enabled=true
eureka.preferIpAddress=true
eureka.preferSameZone=true
eureka.shouldUseDns=false
eureka.serviceUrl.default=http://superms:superms2018@eureka.jeesuite.com/eureka/
eureka.decoderName=JacksonJson


#应用配置
server.port=8080
eureka.name=jerseydemo
eureka.vipAddress=${eureka.name}
eureka.port=${server.port}
eureka.ipAddr=${spring.cloud.client.ipAddress}
eureka.instanceId=${spring.cloud.client.ipAddress}:${server.port}/${eureka.name}
eureka.homePageUrl=http://${spring.cloud.client.ipAddress}:${eureka.port}
eureka.healthCheckUrl=http://${spring.cloud.client.ipAddress}:${eureka.port}/service/health
eureka.statusPageUrl=http://${spring.cloud.client.ipAddress}:${eureka.port}/service/info

```

***说明***
 - `spring.cloud.client.ipAddress`:默认读取本机ip
 - 
 
### 配置web.xml

替换`ContextLoaderListener`（已经包含日志初始化等）
```
<listener>
  <listener-class>com.oneplatform.base.spring.ContextLoaderListener</listener-class>
</listener>
```
增加服务检查servlet
```
<servlet>
   <servlet-name>ServiceCheckServlet</servlet-name>
   <servlet-class>com.oneplatform.base.servlet.ServiceCheckServlet</servlet-class>
</servlet>
<servlet-mapping>
   <servlet-name>ServiceCheckServlet</servlet-name>
   <url-pattern>/service/*</url-pattern>
</servlet-mapping>
```

### 通过eureka调用服务
```

  private RestTemplate restTemplate = EurekaRestTemplateBuilder.build();

  public List<IdNamePair> getProvinces() {
    ParameterizedTypeReference<List<IdNamePair>> arearesponseType = new ParameterizedTypeReference<List<IdNamePair>>() {
    };
    List<IdNamePair> lists = restTemplate
        .exchange("http://COMMON-SERVICE/region/provinces", HttpMethod.GET, null, arearesponseType)
        .getBody();
    return lists;
  }
```

