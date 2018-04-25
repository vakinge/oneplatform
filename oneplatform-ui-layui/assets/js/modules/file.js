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
      {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left'}
      ,{field: 'fileName', title: '文件名', width:150, sort: true}
      ,{field: 'group', title: '所属组', width:90}
      ,{field: 'provider', title: '服务商', width:100} 
      ,{field: 'fileUrl', title: 'url', width: 380}
      ,{field: 'mimeType', title: 'mimeType', width: 80}
      ,{field: 'createdAt', title: '创建时间', width: 170}
      ,{fixed: 'right', width: 70, align:'center', toolbar: '#toolBar'}
    ]]
  });
  
  //监听工具条
  table.on('tool(table)', function(obj){ 
	  var data = obj.data,layEvent = obj.event; 
	  if(layEvent === 'acess'){
		  
	  }else if(layEvent === 'del'){
		  
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