>用传统前端依然能快速开发实践
 - 表单自动数据组装，表单验证
 - 表单数据绑定
 - 数据渲染
 - ....
#### 提交form表单，
- lay-verify：表单验证规则(沿用了layui的标签，自己实现验证逻辑)
- 表单提交：只需要一个样式名称`J_ajaxSubmit`搞定
```html
<form class="layui-form" action="/api/account/add">  
    <div class="layui-form-item">
		<label class="layui-form-label">用户名</label>
		<div class="layui-input-block">
		   <input type="text" name="username" placeholder="中文、字母、数字、下划线" lay-verify="required" class="layui-input">
		</div>
	</div> 
    <div class="layui-form-item">
		<label class="layui-form-label">手机</label>
		<div class="layui-input-block">
		   <input type="text" name="mobile" placeholder="手机号码" class="layui-input">
		</div>
	</div>
    <div class="layui-form-item"> 
     <div class="layui-input-block"> 
        <input type="button"  class="layui-btn layui-btn-normal J_ajaxSubmit" value="保存"/>
        <input type="reset" class="layui-btn layui-btn-primary" value="重置"/>
     </div> 
    </div> 
</form> 
```
#### 加载页面数据(如编辑页面)
 - dataLoad:指定加载数据路径（id参数通过页面传入，如:edit.html?id=x）
 - onFinishCallback：加载完成后回调
 - bindAttr：绑定加载数据字段
 ```html
 <div class="admin-main" dataLoad="/api/account/{id}" onFinishCallback="onDataLoadedCallback"> 
   <form class="layui-form" action="/api/account/update">  
    <input type="hidden" name="id" bindAttr="id" />
    <div class="layui-form-item">
		<label class="layui-form-label">用户名</label>
		<div class="layui-input-block">
		   <input type="text" bindAttr="username" name="username" placeholder="中文、字母、数字、下划线" lay-verify="required" class="layui-input">
		</div>
	</div> 
    <div class="layui-form-item">
		<label class="layui-form-label">手机</label>
		<div class="layui-input-block">
		   <input type="text" bindAttr="mobile" name="mobile" placeholder="手机号码" class="layui-input">
		</div>
	</div>
    <div class="layui-form-item"> 
     <div class="layui-input-block"> 
        <input type="button"  class="layui-btn layui-btn-normal J_ajaxSubmit" value="保存"/>
        <input type="reset" class="layui-btn layui-btn-primary" value="重置"/>
     </div> 
    </div> 
   </form> 
  </div>  
 ```
 ##### 结合layui的模板引擎绑定加载数据复杂字段
 
 ```html
  <ul bindAttr="template:parameters_tpl"> 
   <script type="text/html" id="parameters_tpl">
     {{# layui.each(d.parameters, function(index, item){ }}
      <li>{{ item.name }}</li>
     {{# }); }}
 </script> 
  </ul> 
 ```

##### 结合layui的模板引擎独立加载数据
 ```html
 <div class="layui-form-item">
		<label class="layui-form-label">角色</label>
		<div class="layui-input-block">
		   <ul dataLoad="/api/role/list" template-id="rolelist_tpl" onFinishCallback="onFinishCallback"></ul>
           <script type="text/html" id="rolelist_tpl">
	        {{# layui.each(d, function(index, item){ }}
            <li style="display: inline;padding-left: 5px;"><input type="checkbox" name="roleIds" lay-skin="primary" title="{{ item.name }}" value="{{ item.id }}"></li>
	        {{# }); }}
           </script>
		</div>
</div>
 ```
 
#### 动态下拉框
```html
<select asnycselect="/api/dsconfig/options"> 
  <option value="">请选择数据源</option> 
</select> 
```
#### 确认操作
 增加样式名`J_confirm`即可
 - act-url:确认操作的地址
 - ajax-method：请求方法 ，默认GET	
 - data-id：绑定的数据id(按需)
```html
<a href="javascript:;" class="J_confirm layui-btn layui-btn-danger layui-btn-mini" act-url="../admin/config/delete/{{ item.id }}">删除</a>
```

#### 其他还有很多，看`oneplatform.js`慢慢发现吧

