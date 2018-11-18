layui.config({
  version: '20180325',
  base : '../../assets/js/'
});
layui.define(['form','oneplatform', 'layer', 'tree','element'], function(exports){

  var $ = layui.jquery
  ,layer = layui.layer 
  ,tree = layui.tree 
  ,form = layui.form
  ,oneplatform = layui.oneplatform
  ,element = layui.element;

  var nodeDatas;
  $.ajax({
      type: "GET",
      async: false,
      url: '/api/company/structure',
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
  if(nodeDatas && $('#tree_cont')[0]){
	  layui.tree({
		    elem: '#tree_cont'
		    ,click: function(item){ //点击节点回调
		    	var type;
		    	if(item.extraAttr === 'company'){
		    		$('.company-filter',$form).show();
		    		$form.attr('action','/api/company/save');
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
		    		$form.attr('action','/api/department/save');
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

  exports('organisation', null);
  
});