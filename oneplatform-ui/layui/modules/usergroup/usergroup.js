layui.config({
  version: '20180325',
  base : '/assets/plugins/'
});
layui.define(['jeesuitelayui', 'table','form'], function(exports){

  var $ = layui.jquery
  ,laypage = layui.laypage 
  ,layer = layui.layer 
  ,table = layui.table 
  ,form = layui.form
  ,element = layui.element
  jeesuitelayui = layui.jeesuitelayui;
  
  if(moduleName === 'list'){
	  var $table =  table.render({
		    elem: '#tablecont'
		    ,height: 430
		    ,width: 1150
		    ,url: '/api/perm/usergroup/list'
		    ,method: 'POST'
		    ,page: true 
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
		      ,{field: 'name', title: '名称', width:150,sort: true}
		      ,{field: 'platformType', title: '所属平台', width:150}
		      ,{field: 'enabled', title: '是否启用', width: 120,templet: '#enabledTpl'}
		      ,{fixed: 'right', width: 300, align:'center', toolbar: '#toolBar'}
		    ]],
		  });
		  
		  //监听工具条
		  table.on('tool(table)', function(obj){ 
			  var data = obj.data,layEvent = obj.event; 
			  if(layEvent === 'switch'){
			      layer.confirm('确认'+(data.enabled ? '禁用' : '启用' )+'么?', function(index){
			    	    var url = '/api/perm/status/switch?type=usergroup&id=' + data.id;
				    	jeesuitelayui.post(url,{},function(data){
					        layer.close(index);
					        window.location.reload();
					        jeesuitelayui.success('操作成功');
				    	});
				       
				      });
			    }else if(layEvent === 'del'){
			      layer.confirm('确认删除么?', function(index){
			    	jeesuitelayui.post('/api/perm/usergroup/delete',{id:data.id},function(data){
			    		obj.del(); 
				        layer.close(index);
				        jeesuitelayui.success('删除成功');
			    	});
			       
			      });
			    }else if(layEvent === 'edit'){
			    	jeesuitelayui.iframeDialog('编辑用户组','./edit.html?id='+data.id);
			    }
		  });
		  
		  $('.J_search').on('click',function(){
			   $table.reload({
				  where: jeesuitelayui.serializeJson($('#searchform'))
				  ,page: {
				    curr: 1
				  }
				});
		  });
  }else if(moduleName === 'add'){
	  form.on('select(scope)',function(data) {
		  if(data.value === 'internal'){
			  $('#department_cont').show();
			  initInternalCompanyDepartMents();
		  }else{
			  $('#department_cont').hide();
		  }
	  });
  }else if(moduleName === 'edit'){
	  var id = jeesuitelayui.getQueryParams('id');
	  jeesuitelayui.get('/api/perm/permgroup/details/'+id,function(data){
		  var permGroups = jeesuitelayui.getDataTryCache('/api/perm/permgroup/options?platformType=' + data.platformType);
		  var bindPermGroupIds = data.bindPermGroupIds;
		  for(var i in permGroups){
			  inner:for(var i2 in bindPermGroupIds){
				  if(permGroups[i].value === bindPermGroupIds[i2]+''){
					  permGroups[i].checked = true;
					  break inner;
				  }
			  } 
		  }
		  data.permGroups = permGroups;
		  jeesuitelayui.dataRender($('form'),data);
	  });
  }
  
  function initInternalCompanyDepartMents(){
	   var $target = $('#departSelect'),defaultOptionHtml = $target.html();
		var opthtml = defaultOptionHtml;
		var data = jeesuitelayui.getDataTryCache('/common/department/options?companyId=-1');
		for(var index in data){
			var selected = data[index].selected ? 'selected="selected"' : '';
			opthtml = opthtml + '<option value="'+data[index].value+'" '+selected+'>'+data[index].text+'</option>';
		}
		$target.html(opthtml);
		form.render('select');
  }
});