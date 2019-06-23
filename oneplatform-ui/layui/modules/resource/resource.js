layui.config({
  version: '20180325',
  base : '/assets/plugins/'
});
layui.define(['form','jeesuitelayui', 'layer', 'eleTree','element','table'], function(exports){

  var $ = layui.jquery
  ,layer = layui.layer 
  ,tree = layui.tree 
  ,form = layui.form
  ,table = layui.table 
  ,jeesuitelayui = layui.jeesuitelayui
  ,eleTree = layui.eleTree
  ,element = layui.element;
  
  //------api列表-----
  if($('#tablecont')[0]){
	  var $table = table.render({
		    elem: '#tablecont'
		    ,height: 465
		    ,width:1150
		    ,url: '/api/perm/resources/apiList'
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
		      ,{field: 'moduleName', title: '所属模块', width: 150}
		      ,{field: 'name', title: '名称', width:165}
		      ,{field: 'type', title: '类型', width:60}
		      ,{field: 'uri', title: '接口地址', width:250} 
		      ,{field: 'httpMethod', title: '请求方法', width: 100}
		      ,{field: 'grantType', title: '授权方式', width: 120}
		      ,{fixed: 'right', width: 200, align:'center', toolbar: '#toolBar'}
		    ]]
		  });
		  
		  //监听工具条
		  table.on('tool(table)', function(obj){ 
			  var data = obj.data,layEvent = obj.event; 
			  if(layEvent === 'switch'){
			      layer.confirm('确认'+(data.enabled ? '禁用' : '启用' )+'么?', function(index){
			    	    var url = '/api/perm/status/switch?type=resource&id=' + data.id;
				    	jeesuitelayui.post(url,{},function(json){
					        layer.close(index);
					        window.location.reload();
					        jeesuitelayui.success('操作成功');
				    	});
				       
				      });
				}else if(layEvent === 'del'){
			      layer.confirm('确认删除么?', function(index){
			    	jeesuitelayui.post('/api/perm/resource/delete',{id:data.id},function(data){
			    		obj.del(); 
				        layer.close(index);
				        jeesuitelayui.success('删除成功');
			    	});
			       
			      });
			    }else if(layEvent === 'edit'){
			    	jeesuitelayui.iframeDialog('编辑模块','./api-edit.html?id='+data.id);
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
  }
  //-----------------
  var $menuTree;
  if($('#menu_tree_cont')[0]){
	  var platformType = $('#platformType').val();
	  var treeDatas = getTreeData('menu',platformType);
	  $menuTree = eleTree.render({
		    elem: '#menu_tree_cont',
		    data:treeDatas,
		    showCheckbox: false,
		});
	  
	  eleTree.on("nodeClick(menus)",function(item) {
		  currentMenu = item.data.currentData;
	      var $form = $('#form_cont');
	      if(currentMenu.leaf){
	    		$('.leaf-filter',$form).show();
	    		$('.noneleaf-filter',$form).hide();
	    	}else{
	    		$('.noneleaf-filter',$form).show();
	    		$('.leaf-filter',$form).hide();
	    		$('.leaf-filter input',$form).val("");
	    	}
	        if(currentMenu.leaf){
	        	selectData = {};
	        	$.getJSON('/api/perm/menu/apis?menuId='+currentMenu.id,function(json){
	        		if(json.code != 200){
	        			jeesuitelayui.error(json.msg);
	        			return;
	        		}
		        	currentMenu.apis = json.data;
		        	jeesuitelayui.dataRender($form,currentMenu);
		        	for(var i in json.data){
		        		selectData[json.data[i].value] = json.data[i];
		        	}
		        });
	        }else{
	        	jeesuitelayui.dataRender($form,currentMenu);
	        }
	    	//
	    	$('.layui-btn',$form).attr('data-id',currentMenu.id);
		});
		
	  form.on('select(platformType)', function(data){
		  treeDatas = getTreeData('menu',data.value);
		  $menuTree = $menuTree.reload({data:treeDatas});
	  });

  }
  
  var $apiTree;
  if($('#api_tree_cont')[0]){
	  var apiModules = getTreeData('module');
	  $apiTree=eleTree.render({
          elem: '#api_tree_cont',
          data: apiModules,
          showCheckbox: true,
          defaultExpandAll: false,
          highlightCurrent: true,
          lazy: true,
          load: function(data,callback) {
        	  var nodeData = getTreeData('api',null,data.id);
        	  for(var i in nodeData){
        		  nodeData[i].name  = nodeData[i].name + '(' + nodeData[i].value + ')';
        	  }
        	  callback(nodeData);
          }
      });
	  
	  eleTree.on("nodeChecked(apiTree)",function(item) {
		  var data = item.data.currentData;
		  if(!data.leaf){
			  jeesuitelayui.error('请选择子节点');
			  return;
		  }
		  if(item.isChecked){
			 parent.selectData[data.id] = {text:data.name,value:data.id};
		  }else{
			  delete parent.selectData[data.id];
		  }
	  });
	  
	  $('#selected_btn').click(function(){
		  if(parent.selectData.length == 0){
			  jeesuitelayui.error('请至少选择一个接口');
			  return;
		  }
		  parent.reloadApiList();
	  });
  }
 

  form.on('select(name_select)',function(data) {
	  $(data.elem).children().each(function(index,item){
		  if($(this).val() == data.value){
			  $('#nameinput').val(data.value);
			  $('#resourceinput').val($(this).attr('data'));
			  return false;
		  }
		 
	  });
  });
  //
  function getTreeData(type,platformType,moduleId){
	  var treeData;
	  var url = "/api/perm/resource/tree?type="+type;
	  if(platformType)url = url + "&platformType="+platformType;
	  if(moduleId)url = url + "&moduleId="+moduleId;
	  $.ajax({
	      type: "GET",
	      async: false,
	      url: url,
	      dataType: "json",
	      success: function(json) {
	      	if(json.code != 200){
	      		jeesuitelayui.error(json.msg);
	      		return;
	      	}
	      	treeData = json.data;
	      },
	      error: function(a, b, c) {
	    	  treeData = null;
	      }
	  }); 
	  
	  return treeData;
  }
  
});