layui.config({
  version: '20180325',
  base : '../../assets/js/'
});
layui.define(['oneplatform','layer', 'laytpl', 'form'], function(exports){
    var $ = layui.jquery,
    laytpl = layui.laytpl,
    oneplatform = layui.oneplatform,
    form = layui.form;
    //
    form.on('submit(search)',function(data) {
        var loading = layer.load();
        $.ajax({
            dataType: "json",
            type: "GET",
            url: '/api/scm/schedule/jobs?group=' + data.field.groupName,
            contentType: "application/json",
            complete: function() {
                layer.close(loading);
            },
            success: function(data) {
                if (data.code == 200) {
                    var tpl = $('#task_list_tpl').html();
                    laytpl(tpl).render(data,
                    function(html) {
                        $("#content").html(html);
                    });
                    tpl = $('#node_list_tpl').html();
                    laytpl(tpl).render(data,
                    function(html) {
                        $("#node_content").html(html);
                    });
                } else {
                    oneplatform.error(data.msg);
                }
            },
            error: function(xhr, type) {
            	oneplatform.error("系统错误"+type);
            }
        });
        return false;
    });
    //
    $("#content").on('click', ".J_commond_btn",function() {
    	var cmd = $(this).attr('data-cmd'),dataref = $(this).attr('data-ref');
    	var  params = {};
    	params.group = $('#group_select').val();
    	params.job = $(this).attr('data-id');
    	
    	var data = dataref ? $(dataref).val() : $(this).attr('data');
    	if(data)params.data = data;
    	
    	layer.confirm('确认执行该操作么？', {
		    btn: ['确定','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(index){
			$.ajax({
	            dataType: "json",
	            type: "POST",
	            url: '/api/scm/schedule/job/'+cmd,
	            contentType: "application/json",
	            data: JSON.stringify(params),
	            complete: function() {
	                layer.close(index); 
	            },
	            success: function(data) {
	                if (data.code == 200) {
	                	layer.msg(data.msg  || '操作成功', {icon: 6});
	                	//setTimeout(function(){window.location.reload();},500);
	                	$('.J_search').click();
	                }else{
	                	layer.msg(data.msg || '操作失败' , {icon: 5});
	                }
	            },
	            error: function(xhr, type) {
	                layer.msg('系统错误', {
	                    icon: 5
	                });
	            }
	        });
		}, function(index){
	    	layer.close(index); 
		});
        
    });
    
    exports('schedule', null);

});