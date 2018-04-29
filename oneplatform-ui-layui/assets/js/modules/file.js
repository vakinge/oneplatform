layui.config({
  version: '20180325',
  base : '../../assets/js/'
});
layui.define(['oneplatform', 'table','laydate'], function(exports){

  var $ = layui.jquery
  ,laypage = layui.laypage 
  ,table = layui.table 
  ,laydate = layui.laydate
  oneplatform = layui.oneplatform;
  
  laydate.render({ 
	  elem: '#start'
	  ,min: '2017-1-1'
	});
  laydate.render({ 
	  elem: '#end'
	  ,min: '2017-1-1'
	});
  
  var $table = table.render({
    elem: '#tablecont'
    ,height: 550
    ,width: 1160
    ,url: '/api/common/file/list'
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
      {field: 'id', title: 'ID', width:60, sort: true, fixed: 'left'}
      ,{field: 'fileName', title: '文件名', width:120, sort: true}
      ,{field: 'groupName', title: '所属组', width:75}
      ,{field: 'provider', title: '服务商', width:75} 
      ,{field: 'appId', title: '所属应用', width: 120}
      ,{field: 'fileUrl', title: 'url', width: 330}
      ,{field: 'mimeType', title: 'mimeType', width: 95}
      ,{field: 'createdAt', title: '创建时间', width: 170}
      ,{fixed: 'right', width: 115, align:'center', toolbar: '#toolBar'}
    ]]
  });
  
  //监听工具条
  table.on('tool(table)', function(obj){ 
	  var data = obj.data,layEvent = obj.event; 
	  if(layEvent === 'view'){
		  $.getJSON('/api/common/file/geturl/'+data.id,function(result){
			  if(result.code == 200){
				  if(data.mimeType.indexOf('image') >= 0){
					  layer.open({
						  type: 2,
						  title: '预览',
						  shadeClose: true,
						  shade: 0.8,
						  area: ['893px', '600px'],
						  content: [result.data, 'no']
						}); 
				  }else{
					  window.open(result.data); 
				  }
			  }else{
				  oneplatform.error(result.msg);
			  }
		  });
	  }else if(layEvent === 'del'){
	      layer.confirm('确认删除么?', function(index){
		    	oneplatform.post('/api/common/file/delete/'+data.id,null,function(data){
		    		obj.del(); 
			        layer.close(index);
			        oneplatform.success('删除成功');
		    	});
		       
		      });
		    }
  });
  
  $('.J_search').on('click',function(){
	  $table.reload({
		  where: oneplatform.serializeJson($('#searchform'))
		  ,page: {
		    curr: 1
		  }
		});
  });

  exports('file', null);
  
});