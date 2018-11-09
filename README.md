**交流群(提供开发视频教程)**：230192763🈵 ，61859839

#### 项目介绍
 
`oneplatform`是一个企业级服务应用网关+可插拔模块化服务运行的基础能力平台，基于Springcloud 2.0与jeesuite-libs构建。应用网关提供服务分发、SSO、统一认证、统一日志、API文档聚合、流量控制、访问频率控制、接口粒度跨域控制以及可插拔模块化服务管理等基础能力;服务运行提供插件+微服务两种接入方式，按需加载省心省力。

### 在线演示
http://oneplatform.jeesuite.com/

#### 优势
 - 模块化设计：真正意义实现平台与业务分离，平台提供插件机制(jar包)和微服务(注册中心)两种服务运行机制。
 - 底层框架稳定：底层框架基于[jeesuite-libs](http://git.oschina.net/vakinge/jeesuite-libs)增强框架(天然支持一切特性)、经过4年数十家公司生产环境实践。
 - 无历史负担：兼容历史项目，可无缝对接其他非springboot项目。
 - 标准化：提供快速业务模块生成工具[在线生成](http://www.jeesuite.com/tool/genProject.html) ，简化开发入门门槛。
 
#### 其他亮点
 - 更加简洁更加灵活的权限认证体系(自研简易版shiro鉴权框架)
   - 可选是否开启单点登录
   - 可选session本地和redis存储
   - 跨应用安全访问控制
 
#### 重磅模块推荐：智能API:star::star::star::star::star:
 - 不是代码生成器，我一直认为在线生成代码这个功能就很搞笑。(不是一个脚本就能搞定么，还在线上放个菜单，难道在生产环境生成代码，然后再拿回本地修改编译重新发布么)
 - 倡导sql即API，只需要编写sql即可实时完成接口
 - 支持常规CRUD，分页，聚合查询，以及多步骤复杂接口
 - 结合智能表单，彻底解放前后端 （开发中）
 - 支持列表查询自动渲染界面及报表导出 （开发中）

#### 功能列表
 状态说明: :white_check_mark:表示已经开发完成 :recycle:表示内测中 :clock130:表示规划中
 - 服务网关
   - 服务分发:white_check_mark:
   - 动态路由:white_check_mark:
   - 流量控制:white_check_mark:
 - 认证授权
   - 普通登录:white_check_mark:
   - 功能(接口/按钮)权限:white_check_mark:
   - 数据权限:clock130:
   - 第三方账号登录：微信、QQ、微博等:clock130:
   - 外部合作系统登录：oauth2服务:clock130:
   - 内部跨系统登录：SSO统一认证服务:clock130:
 - 系统管理
   - 用户管理:white_check_mark:
   - 角色管理:white_check_mark:
   - 权限管理:white_check_mark:
   - 菜单管理:white_check_mark:
   - 服务路由管理:white_check_mark:
 - 系统监控
   - 定时任务监控管理:white_check_mark:
   - kafka监控:white_check_mark:
   - 服务模块监控管理:white_check_mark:
   - redis缓存管理:clock130:
   - zookeeper监控管理:clock130:
   - 操作日志:white_check_mark:
 - 通用服务
   - 文件服务:七牛、阿里云OSS:white_check_mark:
   - 消息服务：短信、邮件:clock130:
 - 组织架构
   - 部门管理:clock130:
   - 员工管理:clock130:
 - 工作流
   - 流程设计:clock130:
   - 流程表单设计:clock130:
 - SAAS支持
   - 多租户体系:clock130:
   - 开放平台:clock130:
 - 高级特性
   - 读写分离
   - 自动缓存、二级缓存(jeesuite-libs提供):white_check_mark:
   - 分布式锁(jeesuite-libs提供):white_check_mark:
   - 分布式定时任务(jeesuite-libs提供):white_check_mark:
   - 智能API:recycle:
   - 智能表单:clock130:
   - 通用查询:clock130:
   - 项目初始化工具:clock130:
   - 全局事件:clock130:
   - 分布式事务:clock130:

   

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