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
  
  if(pageType === 'list'){
	  var $table = table.render({
		    elem: '#tablecont'
		    ,height: 465
		    ,width:1150
		    ,url: '/api/article/list'
		    ,method: 'POST'
		    ,page: true
			,contentType: 'application/json'
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
		      ,{field: 'title', title: '标题', width: 150}
		      ,{field: 'categoryName', title: '分类名称', width:165}
		      ,{field: 'viewCount', title: '阅读量', width:60}
		      ,{field: 'uri', title: '是否审核', width:250, templet: '#auditTpl'} 
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


  if(pageType === "category"){
	  var $tree = eleTree.render({
		    elem: '#tree_cont',
		    url:'/api/article/category/tree',
			response: {
              statusName: "code",
              statusCode: 200,
              dataName: "data"
            },
		    showCheckbox: false,
		});
	  
	  eleTree.on("nodeClick(menus)",function(item) {
		  currentMenu = item.data.currentData;
	      var $form = $('#form_cont');	 
		  jeesuitelayui.dataRender($form,currentMenu);
	      $('.layui-btn',$form).attr('data-id',currentMenu.id);
		});
  }
  
});