layui.config({
  version: '20180325',
  base : '/assets/plugins/'
});
layui.define(['form','jeesuitelayui', 'layer', 'tree','element','table'], function(exports){

  var $ = layui.jquery
  ,layer = layui.layer 
  ,form = layui.form
  ,table = layui.table 
  ,jeesuitelayui = layui.jeesuitelayui;
  

if(pageType === 'list'){
  var $table = table.render({
	    elem: '#tablecont'
	    ,height: 465
	    ,width:1150
	    ,url: '/api/perm/permgroup/list'
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
	      ,{field: 'name', title: '组名称', width: 120}
	      ,{field: 'platformType', title: '所属平台', width:130}
	      ,{field: 'menuName', title: '关联菜单', width:180}
	      ,{field: 'apis', title: '关联接口', width:550,templet: '#apiListTpl'} 
	      ,{fixed: 'right', width: 170, align:'center', toolbar: '#toolBar'}
	    ]]
	  });
  
    $('.J_search').on('click',function(){
	   $table.reload({
		  where: jeesuitelayui.serializeJson($('#searchform'))
		  ,page: {
		    curr: 1
		  }
		});
     });
	  
	  //监听工具条
	  table.on('tool(table)', function(obj){ 
		  var data = obj.data,layEvent = obj.event; 
		  if(layEvent === 'switch'){
		      layer.confirm('确认'+(data.enabled ? '禁用' : '启用' )+'么?', function(index){
		    	    var url = '/api/perm/status/switch?type=permgroup&id=' + data.id;
			    	jeesuitelayui.post(url,{},function(data){
				        layer.close(index);
				        window.location.reload();
				        jeesuitelayui.success('操作成功');
			    	});
			       
			      });
			}else if(layEvent === 'del'){
		      layer.confirm('确认删除么?', function(index){
		    	jeesuitelayui.post('/api/perm/permgroup/delete',{id:data.id},function(data){
		    		obj.del(); 
			        layer.close(index);
			        jeesuitelayui.success('删除成功');
		    	});
		       
		      });
		    }else if(layEvent === 'edit'){
		    	jeesuitelayui.iframeDialog('编辑模块','./edit.html?id='+data.id);
		    }
	  });
 }else if(pageType === 'add'){
	 $('#batchCheck').click(function(){
		 var checkboxs = $('#apiList').find(':checkbox');
		 if(checkboxs.length == 0)return;
		 var $this = $(this);
		 if($this.text() === '全选'){
			 checkboxs.prop("checked", true);
			 $this.text('反选');
		 }else{
			 checkboxs.prop("checked", false);
			 $this.text('全选');
		 }
		 form.render('checkbox');
	 });
 }else if(pageType === 'edit'){
	  var id = jeesuitelayui.getQueryParams('id');
	  jeesuitelayui.get('/api/perm/permgroup/details/'+id,function(data){
		  data.menuName = data.menuName + '[' + data.menuUri + ']';
		  var menuApis = jeesuitelayui.getDataTryCache('/api/perm/menu/apis?menuId=' + data.menuId);
		  var bindApis = data.apis;
		  for(var i in menuApis){
			  inner:for(var i2 in bindApis){
				  if(menuApis[i].value === bindApis[i2].id+''){
					  menuApis[i].checked = true;
					  break inner;
				  }
			  } 
		  }
		  data.apis = menuApis;
		  jeesuitelayui.dataRender($('form'),data);
	  });
  }

});