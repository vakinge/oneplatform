**交流群(提供开发视频教程)**：230192763🈵 ，61859839

#### 项目介绍
 
`oneplatform`是一个企业级服务应用网关+可插拔模块化服务运行框架。应用网关提供服务分发、SSO、统一认证、统一日志、API文档聚合、流量控制、访问频率控制、接口粒度跨域控制以及可插拔模块化服务管理等基础能力;服务运行框架提供插件+微服务两种接入方式。这是基于Springcloud微服务体系的一种全新的实践方式。


### 在线演示
http://oneplatform.jeesuite.com/

#### 优势
 - 模块化设计：真正意义实现平台与业务分离，平台提供插件机制(jar包)和微服务(注册中心)两种服务运行机制。
 - 底层框架稳定：底层框架基于[jeesuite-libs](http://git.oschina.net/vakinge/jeesuite-libs)增强框架(天然支持一切特性)、经过4年数十家公司生产环境实践。
 - 无历史负担：兼容历史项目，可无缝对接其他非springboot项目。
 - 标准化：提供快速业务模块生成工具[在线生成](http://www.jeesuite.com/tool/genProject.html) ，简化开发入门门槛。
 
#### 其他亮点
 - 更加简洁更加灵活的权限认证体系(不使用shiro等其他安全框架)
 - 智能表单+智能接口通过配置方式搞定60%的接口(注：不是代码生成器，我们追求的是所见即所得)
 - 自研轻量级业务流程引擎，代码简洁定制化高（内测中）

#### 功能列表(✅表示已经开发完成,📌表示规划中)
 - 服务网关
   - 服务分发✅
   - 动态路由✅
   - 流量控制✅
 - 认证授权
   - 普通登录✅
   - 功能(接口/按钮)权限✅
   - 数据权限📌
   - 第三方账号登录：微信、QQ、微博等📌
   - 外部合作系统登录：oauth2服务📌
   - 内部跨系统登录：SSO统一认证服务📌
 - 系统管理
   - 用户管理✅
   - 角色管理✅
   - 权限管理✅
   - 菜单管理✅
   - 服务路由管理✅
 - 系统监控
   - 定时任务监控管理✅
   - kafka监控✅
   - 服务模块监控管理✅
   - redis缓存管理📌
   - zookeeper监控管理📌
   - 操作日志✅
 - 通用服务
   - 文件服务:七牛、阿里云OSS✅
   - 消息服务：短信、邮件📌
 - 组织架构
   - 部门管理📌
   - 员工管理📌
 - 工作流
   - 流程设计📌
   - 流程表单设计📌
 - SAAS支持
   - 多租户体系📌
   - 开放平台📌
 - 高级特性
   - 读写分离
   - 自动缓存、二级缓存(jeesuite-libs提供)✅
   - 分布式锁(jeesuite-libs提供)✅
   - 分布式定时任务(jeesuite-libs提供)✅
   - 智能API✅
   - 智能表单📌
   - 通用查询📌
   - 项目初始化工具📌
   - 全局事件📌
   - 分布式事务📌

   

### 文档
 - [快速开始](./docs/quick-start.md) 
 - [模块开发指南](./docs/dev-guide.md) 
 - [历史遗留系统接入指南](./docs/old-system-guide.md) 


### 界面预览
![image](http://ojmezn0eq.bkt.clouddn.com/oneplatform/module.png)
![image](http://ojmezn0eq.bkt.clouddn.com/oneplatform/menu.png)
![image](http://ojmezn0eq.bkt.clouddn.com/oneplatform/log.png)
![image](http://ojmezn0eq.bkt.clouddn.com/oneplatform/schedule.png)
![image](http://ojmezn0eq.bkt.clouddn.com/oneplatform/kafka.png)