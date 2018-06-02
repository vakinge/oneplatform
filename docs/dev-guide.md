### 模块开发指南

#### 初始化模块
[在线生成模块](http://www.jeesuite.com/tool/genProject.html) 
#### 获取登录用户上下文
```
//获取登录用户，未登录为空
LoginSession session = LoginContext.getLoginSession();
//获取并验证是否登录
LoginSession session = LoginContext.getAndValidateLoginUser();
//获取当前登录用户
Integer LoginContext.getLoginUserId();
```
#### 统一日志记录
 - 默认所有`POST`请求都会记录操作日志
 - 通过`@LogOption`注解自定义是否记录日志
```
	@LogOption(skip=false)
	public WrapperResponse<String> process(@RequestBody OrderProcessParam param) {
		LoginUserInfo operator = LoginContext.getAndValidateLoginUser();
		demoService.processOrder(operator, param);
		return new WrapperResponse<>();
	}
```
#### 异步消息（跨模块数据传递）
[参考文档](http://www.jeesuite.com/docs/jeesuite-libs/kafka.html) 

#### 全局事件通知
待规划

### 贡献模块
通过fork-pull方式贡献