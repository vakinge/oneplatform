layui.config({
  version: '20180325',
  base : '../../assets/js/'
});
layui.define(['oneplatform', 'table'], function(exports){

  var $ = layui.jquery
  ,laypage = layui.laypage 
  ,layer = layui.layer 
  ,table = layui.table 
  ,element = layui.element
  oneplatform = layui.oneplatform;
  
  table.render({
    elem: '#tablecont'
    ,height: 430
    ,width:760
    ,url: '/api/account/list'
    ,method: 'POST'
    ,page: true //开启分页
    ,request: {
         pageName: 'pageNo' 
    	 ,limitName: 'pageSize' 
    } 
    ,response: {
    	  statusName: 'code' 
    	  ,statusCode: 200
    	  ,dataName: 'data'
    }
    ,cols: [[ //表头
      {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left'}
      ,{field: 'username', title: '用户名', width:120, sort: true}
      ,{field: 'email', title: '邮箱', width:180}
      ,{field: 'mobile', title: '手机', width:120} 
      ,{field: 'realname', title: '姓名', width: 100}
      ,{field: 'enabled', title: '是否启用', width: 100,templet: '#enabledTpl'}
      ,{fixed: 'right', width: 170, align:'center', toolbar: '#toolBar'}
    ]]
  });
  
  //监听工具条
  table.on('tool(table)', function(obj){ 
	  var data = obj.data,layEvent = obj.event; 
	  if(layEvent === 'switch'){
	      layer.confirm('确认'+(data.enabled ? '禁用' : '启用' )+'么?', function(index){
	    	    var param = {};
	    	    param.id = data.id;
	    	    param.value = !data.enabled;
		    	oneplatform.post('/api/account/switch',param,function(data){
			        layer.close(index);
			        window.location.reload();
			        oneplatform.success('操作成功');
		    	});
		       
		      });
		}else if(layEvent === 'del'){
	      layer.confirm('确认删除么?', function(index){
	    	oneplatform.post('/api/account/delete/'+data.id,null,function(data){
	    		obj.del(); 
		        layer.close(index);
		        oneplatform.success('删除成功');
	    	});
	       
	      });
	    }else if(layEvent === 'edit'){
	    	oneplatform.iframeDialog('编辑模块','/modules/account/edit.html?id='+data.id);
	    }
  });

  exports('account', null);
  
});