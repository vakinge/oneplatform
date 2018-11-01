**交流群(提供开发视频教程)**：230192763🈵 ，61859839

#### 项目介绍
 
`oneplatform`是一个企业级服务应用网关+可插拔模块化服务体系。应用网关提供服务分发、SSO、统一认证、统一日志、API文档聚合、流量控制、访问频率控制、接口粒度跨域控制以及可插拔模块化服务管理等基础能力。服务模块已实现:文件服务、智能表单、智能API、工作流、组织架构。

### 在线演示
http://oneplatform.jeesuite.com/

#### 优势
 - 模块化设计：真正意义实现平台与业务分离，业务开发者只需关心业务功能且无需额外学习成本。
 - 底层框架稳定：底层框架基于[jeesuite-libs](http://git.oschina.net/vakinge/jeesuite-libs)增强框架、经过4年数十家公司生产环境实践。
 - 无历史负担：兼容历史项目，可无缝对接其他非springboot项目。
 - 开放性：提供模块接入指南。可以无缝对接任意第三方提供模块。
 - 标准化：提供快速业务模块生成工具[在线生成](http://www.jeesuite.com/tool/genProject.html) ，简化开发入门门槛。
 
 
#### 亮点
 - 更加简洁更加灵活的权限认证体系(不使用shiro等其他安全框架)
 - 自研轻量级业务流程引擎，代码简洁定制化高
 - 智能表单+智能接口通过配置方式搞定60%的接口

#### 插件及其服务列表
 - 认证授权
   - 普通登录✅
   - 功能(接口/按钮)权限✅
   - 数据权限
   - 第三方账号登录：微信、QQ、微博等
   - 外部合作系统登录：oauth2服务
   - 内部跨系统登录：SSO统一认证服务
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
   - redis缓存管理
   - zookeeper监控管理
   - 操作日志✅
 - 通用服务
   - 文件服务:七牛、阿里云OSS✅
   - 消息服务：短信、邮件
 - 组织架构
  - 部门管理
  - 员工管理
 - 工作流
  - 流程设计
  - 流程表单设计
 - 高级特性
  - 智能表单
  - 智能API✅
  - 通用查询
  - 代码生成器

   

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