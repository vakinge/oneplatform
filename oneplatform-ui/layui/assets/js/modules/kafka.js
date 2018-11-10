layui.config({
  version: '20180325',
  base : '../../assets/js/'
});
layui.define(['oneplatform','layer', 'laytpl', 'form'],function(exports) {
    var $ = layui.jquery,
    laytpl = layui.laytpl,
    form = layui.form,
    oneplatform = layui.oneplatform;
    //
    form.on('submit(search)',function(data) {
    	if(!data.field.group){oneplatform.error("请选择组名");return;}
        var loading = layer.load();
        $.ajax({
            dataType: "json",
            type: "GET",
            url: '/api/scm/kafka/topicinfo/' + data.field.group,
            contentType: "application/json",
            complete: function() {
                layer.close(loading);
            },
            success: function(data) {
                if (data.code == 200) {
                	data = data.data;
                	var tpl;
                	
                	if(data.producer){
                		$('#producer_set').show();
                		var html = '';
                		for(var index in data.producer){
                			html = html + '<tr>';
                			html = html + '<td>'+data.producer[index].group+'</td> ';	
            				html = html + '<td>'+data.producer[index].topic+'</td> ';	
            				html = html + '<td>'+data.producer[index].successNums+'</td> ';	
            				html = html + '<td>'+data.producer[index].errorNums+'</td> ';	
            				html = html + '<td>'+data.producer[index].latestSuccessNums+'</td> ';	
            				html = html + '<td>'+data.producer[index].latestErrorNums+'</td> ';
            				html = html + '<td>'+data.producer[index].source+'</td> ';
            				html = html + '<td>'+data.producer[index].formatLastTime+'</td> ';
            				html = html + '</tr>';
                		}
                		$("#pd_topic_list").html(html);
                	}else{
                		$('#producer_set').hide();
                	}
                	
                	if(data.consumer){
                		$('#consumer_set').show();
                		var html = '';
                		for(var index in data.consumer){
                			var isFirstCol = true;
                			var partitions = data.consumer[index].partitions;
                			for(var index2 in partitions){
                				html = html + '<tr>';
                				if(isFirstCol){
                					html = html + '<td rowspan="'+partitions.length+'" colspan="1">'+partitions[index2].topic+'</td> ';	
                					isFirstCol = false;
                				}
                				html = html + '<td>'+partitions[index2].partition+'</td> ';	
                				html = html + '<td>'+partitions[index2].logSize+'</td> ';	
                				html = html + '<td>'+partitions[index2].offset+'</td> ';	
                				html = html + '<td>'+partitions[index2].lat+'</td> ';	
                				html = html + '<td>'+partitions[index2].owner+'</td> ';	
                				html = html + '<td>'+partitions[index2].createTime+'</td> ';
                				html = html + '<td>'+partitions[index2].formatLastTime+'</td> ';
                				html = html + '</tr>';
                			}
                		}
                		$("#cs_topic_list").html(html);
                	}else{
                		$('#consumer_set').hide();
                	}
                } else {
                	oneplatform.error(data.msg);
                }
            },
            error: function(xhr, type) {
            	oneplatform.error('系统错误'+type);
            }
        });
        return false;
    });

    exports('kafka', null);
});