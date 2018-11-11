oneplatfrom支持单机版和集群版部署(修改配置即可，单机版只需安装mysql。集群版本需要依赖配置中心、redis，kafka，zookeeper。

 **以下快速基于单机版。**  
### 步骤一:clone项目到本地
git clone https://gitee.com/vakinge/oneplatform.git

### 步骤二:创建数据库
```
CREATE DATABASE IF NOT EXISTS `oneplatform` DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
```

### 步骤三:导入数据库表
导入`oneplatform-platform/db.sql`


### 步骤四:配置host
```
127.0.0.1 www.oneplatform.com
```

 ### 步骤五:配置Nginx
 ```
  server {
        listen       80;
        server_name  www.oneplatform.com;

        charset utf-8;

        location / {
            root {你项目的路径}/oneplatform/oneplatform-ui/layui;
            index index.html;
        }

        location /api {
          proxy_pass http://127.0.0.1:8001/api;
          proxy_set_header Host $host;
          proxy_set_header X-Real-IP $remote_addr;
          proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        }
    }

 ```
 ### 配置完成，启动Nginx


### 步骤四:修改项目配置
需要开启或者修改的内容如下：
```
# application.properties
spring.profiles.active=simple

#application-simple.properties
master.db.username=账号
master.db.password=密码

eureka.client.service-url.defaultZone=http://jeesuite:jeesuite2018@127.0.0.1:19991/eureka/
```

 ### 启动eureka注册中心
 入口：springcloud-eureka/src/main/java/com/jeesuite/springcloud/EurekaServerApplication.java

 ### 启动基础平台服务
 入口：oneplatform-platform/src/main/java/com/oneplatform/platform/ApplicationStarter.java
 
 
 >启动完成，访问：http://www.oneplatform.com 
 
超级管理员账号密码:sa / 123456
 
 ---

