layui.config({
  version: '20180325',
  base : '/assets/js/'
});
layui.define(['oneplatform',['laytpl'], 'form'], function(exports){

  var $ = layui.jquery
  ,form = layui.form 
  ,laytpl = layui.laytpl
  oneplatform = layui.oneplatform;
  
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
  
  var dbaction,tableName;
  form.on('select(dbaction)',function(data) {
	  dbaction = data.value;
	  $('#action_config_line').find('[show-on]').each(function(index){
		  var $this = $(this);
		  if($this.attr('show-on').indexOf(dbaction) >=0){
			  $this.show();
		  }else{
			  $this.hide();
		  }
	  });
  });
  
  var tpl = $('#configDialogTpl').html();
  var selectAddOpts = '<fieldset id="select_add_opt" class="layui-elem-field site-demo-button" style="margin-top: 10px;"><legend>附件查询条件</legend><div class="layui-input-inline"><input type="checkbox" name="like1[write]" lay-skin="primary" title="仅查一条"><input type="checkbox" name="like1[read]" lay-skin="primary" title="分页"></div></fieldset>';
  form.on('select(table)',function(data) {
	  tableName = data.value;
	  var tableData = tables[tableName];
	  laytpl(tpl).render(tableData, function(html){
			$('#configForm').html(html + selectAddOpts);
	  });  
  });
  
  $('#action_config_line').find('[action]').click(function(){
	  if(!dbaction){oneplatform.error('请选择操作类型');return;}
	  if(!tableName){oneplatform.error('请选择数据表');return;}
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
	        ,btn: ['关闭']
	        ,moveType: 1 
	        ,content:panel.html()
	        ,success: function(layero){
	          form.render(null,'configForm');
	          var btn = layero.find('.layui-layer-btn');
	          btn.css('text-align', 'center');
	        }
	      });
  });

  exports('smartapi', null);
  
});