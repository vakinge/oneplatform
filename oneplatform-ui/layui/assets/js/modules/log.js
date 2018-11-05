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
    ,height: 430
    ,width: 1150
    ,url: '/api/log/list'
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
      ,{field: 'module', title: '模块', width:120, sort: true}
      ,{field: 'name', title: '操作名', width:90}
      ,{field: 'uri', title: '请求地址', width:150} 
      ,{field: 'requestIp', title: '请求IP', width: 100}
      ,{field: 'requestUname', title: '操作人', width: 75}
      ,{field: 'requestAt', title: '请求时间', width: 162}
      ,{field: 'responseAt', title: '响应时间', width: 162}
      ,{field: 'useTime', title: '耗时', width: 60}
      ,{field: 'status', title: '状态', width: 70,templet: '#statusTpl'}
      ,{fixed: 'right', width: 70, align:'center', toolbar: '#toolBar'}
    ]]
  });
  
  //监听工具条
  table.on('tool(table)', function(obj){ 
	  var data = obj.data,layEvent = obj.event; 
	  oneplatform.iframeDialog('查看详情','/modules/log/detail.html?id='+data.id);
  });
  
  $('.J_search').on('click',function(){
	  $table.reload({
		  where: oneplatform.serializeJson($('#searchform'))
		  ,page: {
		    curr: 1
		  }
		});
  });

  exports('log', null);
  
});