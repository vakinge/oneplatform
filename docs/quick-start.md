### 基础环境安装(最好是Linux环境，没有请安装虚拟机)
 - mysql
 - kafka
 - zookeeper
 - redis 
 
使用docker：构建了一个基础环境docker（jdk8，redis，kafka，zookeeper）
```
mkdir -p /datas/redis
mkdir -p /datas/kafka
mkdir -p /datas/zookeeper
mkdir -p /datas/logs
mkdir -p /datas/config
# /datas/config文件夹 包含：kafka.properties,zookeeper.properties,redis.conf
#拉取并运行
docker run -it --net="host" -p 6379:6379 -p 2181:2181 -p 9092:9092 -m 1024M --memory-swap=1024M -v /datas:/datas vakinge/centos_dev
```

#### 注
 1. mysql配置的的账号密码为：root/123456,redis密码:123456
  
### clone项目到本地
git clone https://gitee.com/vakinge/oneplatform.git

### 创建数据库
```
CREATE DATABASE IF NOT EXISTS `oneplatform` DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
```

### 创建表
分别导入`oneplatform-platform/db.sql`和`oneplatform-services/common-service/db.sql`


 ### 配置host
```
127.0.0.1 www.oneplatform.com
```

 ### 配置Nginx
 ```
  server {
        listen       80;
        server_name  www.oneplatform.com;

        charset utf-8;

        location / {
            #这里改成你项目本地路径
            root /Users/jiangwei/project/oneplatform/oneplatform-ui-layui;
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
 
 ### 启动基础平台服务
 入口：oneplatform-platform/src/main/java/com/oneplatform/platform/ApplicationStarter.java
 
  ### 启动common服务
 入口：oneplatform-services/common-service/src/main/java/com/oneplatform/common/ApplicationStarter.java
 
 
 >启动完成，访问：http://www.oneplatform.com 
 
 
 ---
 ### 默认使用阿里云部署的配置中心和注册中心，如果需要本地启动
 #### eureka服务
 
  1. 测试直接在IDE通过main方法启动即可，入口：springcloud-eureka/src/main/java/com/jeesuite/springcloud/EurekaServerApplication.java
  2. 启动成功。通过访问：http://127.0.0.1:19991 访问。账号密码：oneplatform/oneplatform2018

#### 配置中心
[配置中心部署文档](http://www.jeesuite.com/docs/quickstart/confcenter.html) 


