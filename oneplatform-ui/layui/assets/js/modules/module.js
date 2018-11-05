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
    ,width: 1150
    ,url: '/api/module/list'
    ,page: false //开启分页
    ,response: {
    	  statusName: 'code' 
    	  ,statusCode: 200
    	  ,dataName: 'data'
    	}
    ,cols: [[ //表头
      {field: 'id', title: 'ID', width:60, sort: true, fixed: 'left'}
      ,{field: 'name', title: '模块名', width:120}
      ,{field: 'moduleType', title: '模块类型', width:100} 
      ,{field: 'serviceId', title: '模块标识', width:150, sort: true}
      ,{field: 'routeName', title: '模块路由', width:100} 
      ,{field: 'enabled', title: '是否启用', width: 90,templet: '#enabledTpl'}
      ,{field: 'runStatus', title: '运行节点信息', width: 300,templet: '#nodesTpl'}
      ,{fixed: 'right', width: 250, align:'center', toolbar: '#toolBar'}
    ]],
  });
  
  //监听工具条
  table.on('tool(table)', function(obj){ 
	  var data = obj.data,layEvent = obj.event; 
	  if(layEvent !== 'apidoc' && data.id == 1){
		  oneplatform.error('基础平台不能该操作');
		  return;
	  }
	  if(layEvent === 'switch'){
	      layer.confirm('确认'+(data.enabled ? '禁用' : '启用' )+'么?', function(index){
	    	    var param = {};
	    	    param.id = data.id;
	    	    param.value = !data.enabled;
		    	oneplatform.post('/api/module/switch',param,function(data){
			        layer.close(index);
			        window.location.reload();
			        oneplatform.success('操作成功');
		    	});
		       
		      });
	    }else if(layEvent === 'apidoc'){
	    	oneplatform.iframeDialog('API文档',data.apidocUrl);
	    }else if(layEvent === 'del'){
	      layer.confirm('确认删除么?', function(index){
	    	oneplatform.post('/api/module/delete/'+data.id,null,function(data){
	    		obj.del(); 
		        layer.close(index);
		        oneplatform.success('删除成功');
	    	});
	       
	      });
	    }else if(layEvent === 'edit'){
	    	oneplatform.iframeDialog('编辑模块','/modules/module/edit.html?id='+data.id);
	    }
  });
  
  exports('module', null);
  
});