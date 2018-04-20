layui.config({
  version: '20180325',
  base : '../../assets/js/'
});
layui.define(['laypage','form','oneplatform', 'layer', 'tree', 'treeselect','element'], function(exports){

  var $ = layui.jquery
  ,laypage = layui.laypage 
  ,layer = layui.layer 
  ,tree = layui.tree 
  ,form = layui.form
  ,oneplatform = layui.oneplatform
  ,element = layui.element;

  var nodeDatas;
  if(typeof(type) != "undefined"){
	  var url = "/api/resource/" + type;
	  if(typeof(containLeaf) != "undefined")url = url + "?containLeaf=" + containLeaf;
	  $.ajax({
	      type: "GET",
	      async: false,
	      url: url,
	      dataType: "json",
	      success: function(json) {
	      	if(json.code != 200){
	      		oneplatform.error(json.msg);
	      		return;
	      	}
	      	nodeDatas = json.data;
	      	
	      },
	      error: function(a, b, c) {
	      	sessionId = null;
	      }
	  }); 
  }
  
  if(nodeDatas && $('#tree_cont')[0]){
	  layui.tree({
		    elem: '#tree_cont'
		    ,click: function(item){ //点击节点回调
		    	if(type === 'permissions' && !item.leaf)return;
		    	var $form = $('#form_cont');
		    	if(item.leaf){
		    		$('.leaf-filter',$form).show();
		    	}else{
		    		$('.leaf-filter',$form).hide();
		    		$('.leaf-filter input',$form).val("");
		    	}
		    	oneplatform.dataRender($form,item);
		    	//
		    	$('.J_confirm',$form).attr('data-id',item.id);
		    }
		    ,nodes: nodeDatas || []
	  });
  }
  
  form.on('select(module_select)',function(data) {
      var dataurl = '/api/module/apis/' + data.value;
      $.getJSON(dataurl,
      function(result) {
      	if(result.code && result.code != 200){
      		layer.msg(result.msg, { icon: 5});
      		return;
      	}
          var opthtml;
          var data = result.data;
          for (var index in data) {
              opthtml = '<option value="'+data[index].name+'" data="'+data[index].url+'" >'+data[index].name+'</option>';
              $('#name_select').append(opthtml);
          }
          form.render('select');
      });
  });
  
  $('#name_select').on('change',function(){
	  var url = $(this).find('option:selected').attr('data');
  });
  
  form.on('select(name_select)',function(data) {
	  $(data.elem).children().each(function(index,item){
		  if($(this).val() == data.value){
			  $('#codeinput').val($(this).attr('data'));
			  return false;
		  }
		 
	  });
  });
  
  if($('#treeSelect')[0]){
	  layui.treeselect({
		elem: "#treeSelect",
		valueField:"#parentIdVal",
	    data: nodeDatas
	  });
  }

  exports('resource', null);
  
});