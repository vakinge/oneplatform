layui.config({
  version: '20180325',
  base : '/assets/js/'
});
layui.define(['form','oneplatform', 'layer', 'tree','element', 'table'], function(exports){

  var $ = layui.jquery
  ,layer = layui.layer 
  ,tree = layui.tree 
  ,table = layui.table 
  ,form = layui.form
  ,oneplatform = layui.oneplatform
  ,element = layui.element;

  var nodeDatas;
  if(actionType === 'structure'){
	  $.ajax({
	      type: "GET",
	      async: false,
	      url: API_BASE_PATH + '/company/structure',
	      dataType: "json",
	      success: function(json) {
	      	if(json.code != 200){
	      		oneplatform.error(json.msg);
	      		return;
	      	}
	      	nodeDatas = json.data;
	      },
	      error: function(a, b, c) {
	    	  oneplatform.error('加载失败');
	      }
	  });

	  var $form = $('#dataform');
	  if(nodeDatas){
		  layui.tree({
			    elem: '#tree_cont'
			    ,click: function(item){ //点击节点回调
			    	var type;
			    	if(item.extraAttr === 'company'){
			    		$('.company-filter',$form).show();
			    		$form.attr('action',API_BASE_PATH + '/company/save');
			    		$('#add_depart_btn',$form).show();
			    		$('#add_depart_btn',$form).attr('data-id',item.id+'');
			    		if(!item.originData || item.originData.isBranch == false){
			    			$('#add_branch_btn',$form).show();
			    			type = '总公司';
			    		}else{
			    			type = '分公司';
			    		}
			    	}else{
			    		type = '部门';
			    		$('.company-filter',$form).hide();
			    		$('#add_depart_btn',$form).hide();
			    		$('#add_branch_btn',$form).hide();
			    		$('.company-filter input',$form).val("");
			    		$form.attr('action',API_BASE_PATH + '/department/save');
			    	}
			    	var formdata = item.originData ? item.originData : item;
			    	if(formdata.id == 0){
			    		delete formdata.id;
			    		formdata.isBranch = 'true';
			    	}
			    	formdata.type = type;
			    	oneplatform.dataRender($form,formdata);
			    	
			    	$('.J_confirm',$form).attr('data-id',item.id);
			    }
			    ,nodes: nodeDatas || []
		  });
	  } 
  }
  
  if(actionType === 'employee-list'){
	  table.render({
		    elem: '#tablecont'
		    ,height: 430
		    ,width:1150
		    ,url: API_BASE_PATH + '/employee/list'
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
		      ,{field: 'name', title: '姓名', width:120, sort: true}
		      ,{field: 'email', title: '邮箱', width:140}
		      ,{field: 'mobile', title: '手机', width:110} 
		      ,{field: 'companyName', title: '公司', width: 170}
		      ,{field: 'departmentInfo', title: '部门信息', width: 180}
		      ,{field: 'inActive', title: '是否在职', width: 100,templet: '#inActiveTpl'}
		      ,{fixed: 'right', width: 240, align:'center', toolbar: '#toolBar'}
		    ]]
		  });
		  
		  //监听工具条
		  table.on('tool(table)', function(obj){ 
			  var data = obj.data,layEvent = obj.event; 
			  if(layEvent === 'leave'){
			      layer.confirm('确认设置为离职状态么?', function(index){
				    	oneplatform.post(API_BASE_PATH + '/employee/leave/'+data.id,null,function(data){
				    		obj.del(); 
					        layer.close(index);
					        oneplatform.success('设置为离职状态成功');
				    	});
				       
				      });
				    }
			  else if(layEvent === 'del'){
			      layer.confirm('确认删除么?', function(index){
			    	oneplatform.post(API_BASE_PATH + '/employee/delete/'+data.id,null,function(data){
			    		obj.del(); 
				        layer.close(index);
				        oneplatform.success('删除成功');
			    	});
			       
			      });
			    }else if(layEvent === 'edit'){
			    	oneplatform.iframeDialog('编辑员工','./edit.html?id='+data.id);
			    }
		  });
  }
  
  if(actionType === 'employee-add'){}
  
  exports('organisation', null);
  
});