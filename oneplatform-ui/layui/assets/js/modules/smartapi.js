layui.config({
  version: '20180325',
  base : '/assets/js/'
});
layui.define(['oneplatform','laytpl', 'form', 'table'], function(exports){

  var $ = layui.jquery
  ,form = layui.form 
  ,laytpl = layui.laytpl
  ,table = layui.table
  oneplatform = layui.oneplatform
  ,sqlTemplates = {
		  'insert': 'INSERT INTO [table] ([columns]) VALUES ([values])',
		  'update': 'UPDATE [table] SET [columns] WHERE [where]',
	      'delete': 'DELETE FROM [table] WHERE [where]',
	      'select': 'SELECT  [columns] FROM [table] WHERE [where]',
  };
  //数据源列表
  table.render({
	    elem: '#ds_tablecont'
	    ,height: 430
	    ,width:1000
	    ,url: '/api/dsconfig/list'
	    ,method: 'POST'
	    ,page: false 
	    ,response: {
	    	  statusName: 'code' 
	    	  ,statusCode: 200
	    	  ,dataName: 'data'
	    }
	    ,cols: [[ //表头
	      {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left'}
	      ,{field: 'name', title: '名称', width:100, sort: true}
	      ,{field: 'dbType', title: '类型', width:80}
	      ,{field: 'connUrl', title: '数据库连接', width:200} 
	      ,{field: 'connUsername', title: '用户名', width: 100}
	      ,{field: 'includeTables', title: '包含表', width: 100}
	      ,{field: 'enabled', title: '是否启用', width: 100,templet: '#enabledTpl'}
	      ,{fixed: 'right', width: 170, align:'center', toolbar: '#toolBar'}
	    ]]
	  });
	  
	  //数据源列表工具条
	  table.on('tool(dstable)', function(obj){ 
		  var data = obj.data,layEvent = obj.event; 
		  if(layEvent === 'switch'){
		      layer.confirm('确认'+(data.enabled ? '禁用' : '启用' )+'么?', function(index){
		    	    var param = {};
		    	    param.id = data.id;
		    	    param.value = !data.enabled;
			    	oneplatform.post('/api/dsconfig/switch',param,function(data){
				        layer.close(index);
				        window.location.reload();
				        oneplatform.success('操作成功');
			    	});
			       
			      });
			}else if(layEvent === 'del'){
		      layer.confirm('确认删除么?', function(index){
		    	oneplatform.post('/api/dsconfig/delete/'+data.id,null,function(data){
		    		obj.del(); 
			        layer.close(index);
			        oneplatform.success('删除成功');
		    	});
		       
		      });
		    }else if(layEvent === 'edit'){
		    	oneplatform.iframeDialog('编辑数据源','./edit.html?id='+data.id);
		    }
	  });
	  
	  //api列表
	  table.render({
		    elem: '#api_tablecont'
		    ,height: 430
		    ,width:1150
		    ,url: '/api/apiconfig/list'
		    ,method: 'POST'
		    ,page: false 
		    ,response: {
		    	  statusName: 'code' 
		    	  ,statusCode: 200
		    	  ,dataName: 'data'
		    }
		    ,cols: [[ //表头
		      {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left'}
		      ,{field: 'name', title: '接口名称', width:160, sort: true}
		      ,{field: 'httpType', title: '请求方法', width:100} 
		      ,{field: 'uri', title: '请求uri', width: 180}
		      ,{field: 'datasource', title: '关联数据库', width: 120}
		      ,{field: 'enabled', title: '是否启用', width: 100,templet: '#enabledTpl'}
		      ,{field: 'published', title: '是否发布', width: 100,templet: '#publishedTpl'}
		      ,{fixed: 'right', width: 300, align:'center', toolbar: '#toolBar'}
		    ]]
		  });
		  
		  //数据源列表工具条
		  table.on('tool(apitable)', function(obj){ 
			  var data = obj.data,layEvent = obj.event; 
			  if(layEvent === 'switch'){
			      layer.confirm('确认'+(data.enabled ? '禁用' : '启用' )+'么?', function(index){
			    	    var param = {};
			    	    param.id = data.id;
			    	    param.value = !data.enabled;
				    	oneplatform.post('/api/apiconfig/switch',param,function(data){
					        layer.close(index);
					        window.location.reload();
					        oneplatform.success('操作成功');
				    	});
				       
				      });
				}else if(layEvent === 'del'){
			      layer.confirm('确认删除么?', function(index){
			    	oneplatform.post('/api/apiconfig/delete/'+data.id,null,function(data){
			    		obj.del(); 
				        layer.close(index);
				        oneplatform.success('删除成功');
			    	});
			       
			      });
			    }else if(layEvent === 'publish'){
				      layer.confirm('发布后将不能修改,确认发布么?', function(index){
					    	oneplatform.post('/api/apiconfig/delete/'+data.id,null,function(data){
					    		obj.del(); 
						        layer.close(index);
						        oneplatform.success('发布成功');
					    	});
					      });
				}else if(layEvent === 'edit'){
			    	oneplatform.iframeDialog('编辑接口','./edit.html?id='+data.id);
			    }else if(layEvent === 'details'){
			    	oneplatform.iframeDialog('接口详情','./details.html?id='+data.id);
			    }
		  });
  
  form.on('select(datasource)',function(data) {
      var dataurl = '/api/dsconfig/tables/' + data.value;
      $.getJSON(dataurl,
      function(result) {
      	if(result.code && result.code != 200){
      		layer.msg(result.msg, { icon: 5});
      		return;
      	}
      	  tables = {};
          var opthtml = '<option value="">选择表</option>';
          var data = result.data;
          for (var index in data) {
        	  tables[data[index].tableName]=data[index];
              opthtml = opthtml + '<option value="'+data[index].tableName+'" >'+data[index].tableName+'</option>';
          }
          $('#table_select').html(opthtml);
          form.render('select');
      });
  });
  
  var dbaction,tableName,selectColumns = {},actionConfigs = {},$sqlEditer=$('#sqlEditer'),apiActionList=new Array();
  form.on('select(dbaction)',function(data) {
	  dbaction = data.value;
	  actionConfigs = {};
	  $('#action_config_line').find('[show-on]').each(function(index){
		  var $this = $(this);
		  if($this.attr('show-on').indexOf(dbaction) >=0){
			  $this.show();
		  }else{
			  $this.hide();
		  }
	  });
	  $sqlEditer.val(buildActionSql());
  });
  
  var tpl = $('#configDialogTpl').html();
  var selectAddOpts = '<fieldset id="select_add_opt" class="layui-elem-field" style="margin-top: 5px;"><legend>附件查询条件</legend><div class="layui-input-inline" style="margin: 5px;"><input type="checkbox" lay-filter="selectOpt" value="limitOne" lay-skin="primary" title="仅查一条"><input type="checkbox" lay-filter="selectOpt" value="page" lay-skin="primary" title="分页"></div></fieldset>';
  form.on('select(table)',function(data) {
	  tableName = data.value;
	  var tableData = tables[tableName];
	  actionConfigs = {};
	  laytpl(tpl).render(tableData, function(html){
			$('#configForm').html(html + selectAddOpts);
	  });  
	  if(dbaction)$sqlEditer.val(buildActionSql());
  });
  
  $('#action_config_line').find('[action]').click(function(){
	  if(!dbaction){oneplatform.error('请选择操作类型');return;}
	  if(!tableName){oneplatform.error('请选择数据表');return;}
	  selectColumns = {};
	  var action = $(this).attr('action');
	  $('#configForm').find('[show-on]').each(function(index){
		  var $this = $(this);
		  if($this.attr('show-on').indexOf(action) >=0){
			  $this.show();
		  }else{
			  $this.hide();
		  }
	  });
	  
	  if(dbaction === 'select' && action === 'whereConfig'){
		  $('#select_add_opt').show();
	  }else{
		  $('#select_add_opt').hide(); 
	  }
	  var panel = $('#configdialog');
	  layer.open({
	        type: 1
	        ,title: '配置接口操作'
	        ,closeBtn: false
	        ,area: ['700px', '480px']
	        ,shade: 0.8
	        ,id: 'layer_editdialog' 
	        ,btn: ['确定', '取消']
	        ,content:panel.html()
	        ,success: function(layero){
	          form.render(null,'configForm');
	          var btn = layero.find('.layui-layer-btn');
	          btn.css('text-align', 'center');
	        }
	       ,yes: function(index, layero){
	    	   if(parseActionOptions(action)){
	    		   $sqlEditer.val(buildActionSql());
	    		  layer.close(index); 
	    	   }
            }
	       ,btn2: function(index, layero){
	    	   layer.close(index); 
	       }
	      });
  });
  
  form.on('checkbox(column)', function(data){
	  if(data.elem.checked){
		  selectColumns[data.value] = 'N';
	  }else{
		  delete selectColumns[data.value];
	  }
	});
  
  form.on('checkbox(requireOpt)', function(data){
	  if(!selectColumns[data.value])return;
	  if(data.elem.checked){
		  selectColumns[data.value] = 'Y';
	  }else{
		  selectColumns[data.value] = 'N';
	  }
	});
  
  form.on('checkbox(selectOpt)', function(data){
	  if(data.elem.checked){
		  actionConfigs[data.value] = 'Y';
	  }else{
		  delete actionConfigs[data.value];
	  }
	});
  
  
  $('#add_action_btn').click(function(){
	  if(!tableName){oneplatform.error('请先选择表名');return;}
	  var sql = $sqlEditer.val().trim();
	  if(sql === ''){oneplatform.error('SQL表达式不能为空2');return;}
	  var actionData = {};
	  actionData['comment'] = $('#actionName').val().trim();
	  actionData['dbOperateSql'] = sql;
	  actionData['resultName'] = $('#resultName').val().trim();
	  actionData['expectResult'] = $('#expectResult').val().trim();
	  actionData['unexpectMsg'] = $('#unexpectMsg').val().trim();
	  var trHtml = '<tr>';
	  trHtml = trHtml + '<td>'+actionData['comment']+'</td>';
	  trHtml = trHtml + '<td>'+sql+'</td>';
	  trHtml = trHtml + '<td>'+actionData['resultName']+'</td>';
	  trHtml = trHtml + '<td>'+actionData['expectResult']+'</td>';
	  trHtml = trHtml + '<td>'+actionData['unexpectMsg']+'</td>';
	  trHtml = trHtml + '</tr>';
	  $('#apiActionList').append(trHtml);
	  
	  apiActionList.push(actionData);
	  $sqlEditer.val('');
	  $('#resultName').val('');
	  $('#expectResult').val('');
	  $('#unexpectMsg').val('');
	  $('#actionName').val('');
	  form.render('select');
  });
  
  $('#save_btn').click(function(){
	  if(apiActionList.length == 0){oneplatform.error('请添加操作');return;}
	  var params = oneplatform.serializeJson($('#add_form'));
	  params.actions = apiActionList;
	  var requiredParams = new Array();
	  for(var column in selectColumns){
		  prefix = '#' + column + "_";
		  if('Y' === selectColumns[column]){
			  requiredParams.push(column);
		  }
	  }
	  if(requiredParams.length > 0){
		  params.requiredParameters = requiredParams;
	  }
	  oneplatform.post('/api/apiconfig/add',params,function(d){
		  oneplatform.success('新增接口成功');
		  //清空
		  apiActionList.splice(0,apiActionList.length); 
          setTimeout(function(){parent.window.location.reload();parent.layer.closeAll();},500);
	  });
  });
  
  $('#test_api_btn').click(function(){
	  var uri=$('#uri').val(),method=$('#httpType').val(), params;
	  if(method === 'GET'){
		  params = oneplatform.serializeQueryParams($('#testapiForm'));
		  if(params !== '')uri = uri + '?' + params;
		  $.getJSON(uri,function(json){
			  if(json.code != 200){oneplatform.error('执行错误:'+json.msg);return;}
			  var formatJson = oneplatform.formatJson(JSON.stringify(json));
			  $('#response_previve').text(formatJson);
		  });
	  }else{
		  params = oneplatform.serializeJson($('#testapiForm'));
		  $.ajax({
	        	dataType:"json",
			    type: "POST",
		        url: uri,
		        contentType: "application/json",
	            data: JSON.stringify(params),
	            success: function(json) {
	  			  if(json.code != 200){oneplatform.error('执行错误:'+json.msg);return;}
				  var formatJson = oneplatform.formatJson(JSON.stringify(json));
				  $('#response_previve').text(formatJson);
			  },
	            error: function(e) {
	                layer.msg('请求异常，请重试', {shift: 6});
	            }
	        });
	  }
  });
  
  function buildActionSql(){
	  var sql = sqlTemplates[dbaction];
	  if(tableName)sql= sql.replace('[table]',tableName);
	  if(actionConfigs['columns'])sql= sql.replace('[columns]',actionConfigs['columns']);
	  if(actionConfigs['values'])sql= sql.replace('[values]',actionConfigs['values']);
	  if(actionConfigs['where'])sql= sql.replace('[where]',actionConfigs['where']);
	  if(dbaction === 'select'){
		  if(actionConfigs['page']){
			  sql = sql + ' $pagePlaceholder';
		  }else if(actionConfigs['limitOne']){
			  sql = sql + ' $limitOnePlaceholder';
		  }
	  }
	  return sql;
  }
  
  function parseActionOptions(action){
	  var prefix,compare,valExpr,tmp1,tmp2;
	  
	  for(var column in selectColumns){
		  prefix = '#' + column + "_";
		  compare = $(prefix + "1").val();
		  valExpr = $(prefix + "2").val();
		  if(action === 'insertConfig'){
			  tmp1 = tmp1 ? (tmp1 + ','+column) : column;
			  tmp2 = tmp2 ? (tmp2 + ','+valExpr) : valExpr;
		  }else if(action === 'selectConfig'){
			  tmp1 = tmp1 ? (tmp1 + ','+column) : column;
		  }else if(action === 'updateConfig'){
			  tmp1 = tmp1 ? (tmp1 + ','+column + '=' + valExpr) : (column + '=' + valExpr );
		  }else if(action === 'whereConfig'){
			  tmp1 = tmp1 ? (tmp1 + ' AND '+column + compare + valExpr) : (column + compare + valExpr );
		  }
	  }
	  if(!tmp1){oneplatform.error('请选择操作列');return false;}
	  
	  if(action === 'insertConfig'){
		  actionConfigs['columns'] = tmp1;
		  actionConfigs['values'] = tmp2;
	  }else if(action === 'selectConfig'){
		  actionConfigs['columns'] = tmp1;
	  }else if(action === 'updateConfig'){
		  actionConfigs['columns'] = tmp1;
	  }else if(action === 'whereConfig'){
		  actionConfigs['where'] = tmp1;
	  }
	  
	  return true;
	  
  }

  exports('smartapi', null);
  
});