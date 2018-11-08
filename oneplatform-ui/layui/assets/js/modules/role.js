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
    ,url: '/api/role/list'
    ,page: false 
    ,response: {
    	  statusName: 'code' 
    	  ,statusCode: 200
    	  ,dataName: 'data'
    	}
    ,cols: [[ //表头
      {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left'}
      ,{field: 'name', title: '角色名', width:150,sort: true}
      ,{field: 'enabled', title: '是否启用', width: 120,templet: '#enabledTpl'}
      ,{field: 'memo', title: '备注', width:445}
      ,{fixed: 'right', width: 350, align:'center', toolbar: '#toolBar'}
    ]],
  });
  
  //监听工具条
  table.on('tool(table)', function(obj){ 
	  var data = obj.data,layEvent = obj.event; 
	  if(layEvent === 'switch'){
	      layer.confirm('确认'+(data.enabled ? '禁用' : '启用' )+'么?', function(index){
	    	    var param = {};
	    	    param.id = data.id;
	    	    param.value = !data.enabled;
		    	oneplatform.post('/api/role/switch',param,function(data){
			        layer.close(index);
			        window.location.reload();
			        oneplatform.success('操作成功');
		    	});
		       
		      });
	    }else if(layEvent === 'assign-menu'){
	    	oneplatform.iframeDialog('分配菜单','/modules/role/assign-menu.html?id='+data.id);
	    }else if(layEvent === 'assign-perms'){
	    	oneplatform.iframeDialog('分配权限','/modules/role/assign-perms.html?id='+data.id);
	    }else if(layEvent === 'del'){
	      layer.confirm('确认删除么?', function(index){
	    	oneplatform.post('/api/role/delete/'+data.id,null,function(data){
	    		obj.del(); 
		        layer.close(index);
		        oneplatform.success('删除成功');
	    	});
	       
	      });
	    }else if(layEvent === 'edit'){
	    	oneplatform.iframeDialog('编辑模块','/modules/role/edit.html?id='+data.id);
	    }
  });
  
  exports('role', null);
  
});