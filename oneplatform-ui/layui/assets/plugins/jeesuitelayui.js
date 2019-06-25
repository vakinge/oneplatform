var API_BASE_PATH = (typeof(moduleName) !="undefined") ? window.top.basePathMap[moduleName] : null;
var CACHE = {};
layui.define(['layer', 'laytpl', 'form', 'element'], function(exports){
  var $ = layui.jquery
  ,layer = layui.layer
  ,laytpl = layui.laytpl
  ,form = layui.form
  ,element = layui.element
  ,device = layui.device()
  ,DISABLED = 'layui-btn-disabled';
  
  //阻止IE7以下访问
  if(device.ie && device.ie < 8){
    layer.alert('不支持该浏览器');
  }
  
  layui.focusInsert = function(obj, str){
    var result, val = obj.value;
    obj.focus();
    if(document.selection){ //ie
      result = document.selection.createRange(); 
      document.selection.empty(); 
      result.text = str; 
    } else {
      result = [val.substring(0, obj.selectionStart), str, val.substr(obj.selectionEnd)];
      obj.focus();
      obj.value = result.join('');
    }
  };


  //数字前置补零
  layui.laytpl.digit = function(num, length, end){
    var str = '';
    num = String(num);
    length = length || 2;
    for(var i = num.length; i < length; i++){
      str += '0';
    }
    return num < Math.pow(10, length) ? str + (num|0) : num;
  };
  
//  $.ajaxSetup({ 
//	  contentType: "application/json",
//      dataType: "json"
//  });
  
  var jeesuitelayui = {
		    //Ajax
		  get: function(url,success) {
			  url = jeesuitelayui.buildPath(url);
			  $.getJSON(url,function(res){
				    if(res.code == 401){top.location.href = "/login";return;}
					if(res.code == 403){jeesuitelayui.error('无接口权限');return;}
	                if (res.code == 200) {
	                	if(success === 'alert'){
	                		jeesuitelayui.success(res.msg || '操作成功');
	                	}else if(typeof(success) === 'function'){
	                		success(res.data);
	                	}
	                } else {
	                	jeesuitelayui.error(res.msg || res.code);
	                }
	            });
		   },
		    post: function(url, data, success) {
		    	data = data || {};
		    	success = success || 'alert';
				url = jeesuitelayui.buildPath(url);
		        return $.ajax({
		        	dataType:"json",
				    type: "POST",
			        url: url,
			        contentType: "application/json",
		            data: JSON.stringify(data),
		            success: function(res) {
		                if (res.code == 200) {
		                	if(success === 'alert'){
		                		jeesuitelayui.success(res.msg || '操作成功');
		                	}else if(typeof(success) === 'function'){
		                		success(res.data);
		                	}
		                } else {
		                	jeesuitelayui.error(res.msg || res.code);
		                }
		            },
		            error: function(xhr, type){
						jeesuitelayui.error('系统错误');
					}
		        });
		    },
		    setCookies: function(name,value,expireDays){
		    	expireDays = expireDays || 1; 
		        var exp = new Date(); 
		        exp.setTime(exp.getTime() + expireDays*24*60*60*1000); 
		        document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
		    },
		    getCookies: function(name){
		    	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		        if(arr=document.cookie.match(reg))
		            return unescape(arr[2]); 
		        else 
		            return null; 
		    },
		    getDataTryCache: function(url){
		    	var data = CACHE[url];
		    	if(data)return data;
		    	$.ajax({
		  	      type: "GET",
		  	      async: false,
		  	      url: url,
		  	      dataType: "json",
		  	      success: function(json) {
		  	      	if(json.code != 200){
		  	      		jeesuitelayui.error(json.msg);
		  	      		return;
		  	      	}
		  	        data = json.data;
		  	        CACHE[url] = data;
		  	      },
		  	      error: function(a, b, c) {}
		  	  });
		      return data;
		    },
		    success: function(msg) {
				layer.msg(msg || '操作成功', {icon: 6});
				return;
			},
			error: function(msg) {
				layer.msg(msg, {shift: 5});
			},
			iframeDialog: function(title,url,scrollDisabled){
				if(scrollDisabled)url = [url, 'no'];
				var index = layer.open({
					  type: 2,
					  title: title,
					  shadeClose: true,
					  shade: 0.8,
					  area: ['893px', '600px'],
					  maxmin: false,
					  content: url
					}); 
				layer.full(index);
			},
			
			dataRender: function($container,data){
				var rendForm = false;
				$('[bindAttr]',$container).each(function(){
					var self = $(this),bindAttr = self.attr('bindAttr');
					var value;
					if(bindAttr.indexOf('{')==0){
						var attrs = bindAttr.substring(1,bindAttr.length - 1).split(',');
						var jsonObj = {};
						for(var i in attrs){
							jsonObj[attrs[i]] = eval('data.'+attrs[i]);
						}
						self.attr('data',JSON.stringify( jsonObj ));
					}else if(bindAttr.indexOf('template:')==0){
						var templateId = bindAttr.replace(/template:/, '');
						laytpl($('#'+templateId).html()).render(data, function(html){
							self.html(html);
							form.render();
						});
					}else{
						try{value = eval('data.'+bindAttr);}catch(e){}
						if(!value)return;
						if(self.is('input')){
							var type = self.attr('type');
							if(type == 'radio'){
								if(self.attr('value') == value+"")self.attr('checked',true);
								rendForm = true;
							}else if(type == 'checkbox'){
								if(self.attr('value') == value+"")self.attr('checked',true);
								var switchElm;
								if(switchElm = self.next('.layui-form-switch')){
									switchElm.addClass('layui-form-onswitch');
								}
								rendForm = true;
							}else{
								self.val(value);
						    }
						}else if(self.is('textArea')){
							self.val(value);
						}else if(self.is('img')){
							self.attr('src',APP.attachRoot + value);
						}else if(self.is('a')){
							self.attr('href',value);
						}else if(self.is('select')){
							self.val(value);
							rendForm = true;
						}else{
							self.html(value);
						}
					}
					//self.removeAttr('bindAttr');
				});
				if(rendForm){
					form.render();
				}
			},
			getQueryParams: function(name){
				var search = location.search;
				if(!search || search.lengh <=1)return null;
              if(name){
              	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
 			        var r = search.substr(1).match(reg);
 			        if(r!=null)return  unescape(r[2]); return null;
              }else{
              	var params = {};
              	var arrs = search.substr(1).split('&');
			    	arrs.forEach(function(item){
			    		var arrs2  = item.split('=');
			    		if(arrs2.length == 2 && arrs2[1]){
			    			params[arrs2[0]] = arrs2[1];
			    		}
			    	});
			    	return params;
              }
			},
			validatorRules: {
				    required : {expr:/.+/,tip:"必填",errorTip:"该字段不能为空"},
				    email : {expr:/^\w+([-+.]\w+)*@\w+([-.]\\w+)*\.\w+([-.]\w+)*$/,tip:"电子邮箱",errorTip:"Email格式不正确"},
				    mobile:{expr:/^(1[3|4|5|7|8]{1}\d{9})$/,tip:"手机号码",errorTip:"手机格式不正确"},
				    telePhone:{expr:/^(((0\d{2,3}-)?\d{7,8}(-\d{4})?))$/,tip:"电话号码",errorTip:"电话号码格式不正确"},
				    idCard:{expr:/^\d{15}(\d{2}[A-Za-z0-9])?$/,tip:"身份证号码",errorTip:"身份证号码格式不正确"},
				    integer:{expr:/^\d+$/,tip:"正数",errorTip:"仅支持整数"},
				    number:{expr:/^-?(\d+|[1-9]\d*\.\d+|0\.\d*[1-9]\d*|0?\.0+|0)$/,tip:"数字字符",errorTip:"仅支持数字"},
				    english:{expr:/^[A-Za-z]+$/,tip:"英文字符",errorTip:"仅支持英文字符"},
				    zipCode:{expr:/^[1-9]\d{5}(?!\d)$/,tip:"邮政编码",errorTip:"邮政编码格式不正确"},
				    chinese:{expr:/^[\u0391-\uFFE5]+$/,tip:"中文字符",errorTip:"仅支持中文字符"},
				    url:{expr:/^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/,tip:"网址URL",errorTip:"URL地址格式不正确"},
				    regex:{tip:"",errorTip:"格式不正确"},
			        compareEquals:function($obj){
			        	var _compareWith = $obj.attr('compareWith');
			        	return $obj.val() != '' && $obj.val() == $(_compareWith).val();
		            }
			},
			validateForm: function($form){
				var settings = jeesuitelayui.validatorRules,$fileds = $('*[lay-verify]',$form); 
				if($fileds.length == 0)return true;
				var result = false;
				$fileds.each(function(index){
					result = doValidateSingleInput(this,index);
		            return result;
				});
				return result;
				
				function doValidateSingleInput(formElement,index) {
					var _this = $(formElement), _dataType = _this.attr('lay-verify'),
					    define = eval('settings.'+_dataType),
					    val = _this.val(), result = false;
					if(formElement.disabled)return true;
					if(_this.attr("require") == "false" && val == "")return true;
					switch (_dataType) {
					case "Date":
						result = eval('settings.' + _dataType + '(_this)');
						break;
					default:
						result = define.expr.test(val);
						break;
					}
					if(!result){
						var errorTip = _this.attr('verify-error-msg') || define.errorTip;
			          	getTipElement(_this,index).addClass('layui-form-danger').focus();
			          	jeesuitelayui.error(errorTip);
			        }else{
			        	getTipElement(_this,index).removeClass('layui-form-danger');
			        }
					return result;
				}
				
				function getTipElement($obj,index){
					var id = $obj.attr('id');
					if(!id){
						id = $obj.attr('name') ||  "obj"+index;
						id = id.replace(/[^0-9a-zA-Z]/g, "");
						$obj.attr('id',id);
					}
					return $('#'+id);
				}
			},
			serializeJson: function($form){
				var params = {};
				var dataArrays = $form.serializeArray();
				if(dataArrays){
					var firstSubFieldName;
					$.each( dataArrays, function(i, field){
						var isCheckbox = $("input[name='"+field.name+"']",$form).is(':checkbox');
						var fieldName = field.name,fieldValue = field.value === '' ? null : field.value,subFieldName;
						if(fieldName.indexOf('.') > 0){
							var fieldNameArr = fieldName.split('.');
							fieldName = fieldNameArr[0];
							subFieldName = fieldNameArr[1];
							if(!firstSubFieldName)firstSubFieldName = subFieldName;
						}else{
							subFieldName = null;
						}
						if(fieldValue === ''){
							fieldValue = null;
						}
						if(params[fieldName]){
							if(!Array.isArray(params[fieldName])){
								var origin = params[fieldName];
								params[fieldName] = new Array();
								params[fieldName].push(origin);
							}
							if(subFieldName){
								var innerObj;
								if(firstSubFieldName === subFieldName){
									innerObj = {};
								}else{
									innerObj = params[fieldName].pop();
								}
								innerObj[subFieldName] = fieldValue;
								params[fieldName].push(innerObj);
							}else{
								if(fieldValue)params[fieldName].push(fieldValue);
							}
						}else{
							if(subFieldName){
								var innerObj = {};
								innerObj[subFieldName] = fieldValue;
								params[fieldName] = innerObj;
							}else{
								if(fieldValue){
									if(isCheckbox){
										params[fieldName] = new Array();
										params[fieldName].push(fieldValue);
									}else{
										params[fieldName] = fieldValue;
									}
								}
							}
						}
					});
				}
				
//				for(var field in params){
//					if(Array.isArray(params[field]) && Object.prototype.toString.call(params[field][0]) === '[Object Object]'){
//						for(var i in params[field]){
//							if(!jeesuitelayui.objectNotBlank(params[field][i])){
//								params[field].splice(i,1);
//							}
//						}
//					}
//				}
				return params;
			},
			serializeQueryParams: function($form){
				var params = '';
				var dataArrays = $form.serializeArray();
				if(dataArrays){
					$.each( dataArrays, function(i, field){
						if(field.value && field.value != ''){	
							params = params + field.name + '=' + field.value + '&';
						}
					});
				}
				return params;
			},
			redirct:function(url){
				url = url || location.href;
				url = url.replace("?__rnd=","__rnd=").replace("&__rnd=","__rnd=").split("__rnd=")[0];
				var rnd = (url.indexOf("?")>0 ? "&" : "?") + "__rnd=" + new Date().getTime();
				window.location.href = url + rnd;
			},
			buildPath:function(url){
				if(!API_BASE_PATH){
					return url;
				}
				if(url.indexOf('/') == 1){
					return API_BASE_PATH + url;
				}
				return API_BASE_PATH + '/' + url;
			},
			insertAfterFocus:function(domId,str){//光标出插入文字
				var obj = document.getElementById(domId);
				if (document.selection) {
			        var sel = document.selection.createRange();
			        sel.text = str;
			    } else if (typeof obj.selectionStart === 'number' && typeof obj.selectionEnd === 'number') {
			        var startPos = obj.selectionStart,
			            endPos = obj.selectionEnd,
			            cursorPos = startPos,
			            tmpStr = obj.value;
			        obj.value = tmpStr.substring(0, startPos) + str + tmpStr.substring(endPos, tmpStr.length);
			        cursorPos += str.length;
			        obj.selectionStart = obj.selectionEnd = cursorPos;
			    } else {
			        obj.value += str;
			    }
			},
			formatJson: function (json) {
		        var formatted = '',     //转换后的json字符串
		            padIdx = 0,         //换行后是否增减PADDING的标识
		            PADDING = '    ';   //4个空格符
		        if (typeof json !== 'string') {
		            json = JSON.stringify(json);
		        }
		        json = json.replace(/([\{\}])/g, '\r\n$1\r\n')
		                    .replace(/([\[\]])/g, '\r\n$1\r\n')
		                    .replace(/(\,)/g, '$1\r\n')
		                    .replace(/(\r\n\r\n)/g, '\r\n')
		                    .replace(/\r\n\,/g, ',');
		       (json.split('\r\n')).forEach(function (node, index) {
		            var indent = 0,
		                padding = '';
		            if (node.match(/\{$/) || node.match(/\[$/)) indent = 1;
		            else if (node.match(/\}/) || node.match(/\]/))  padIdx = padIdx !== 0 ? --padIdx : padIdx;
		            else    indent = 0;
		            for (var i = 0; i < padIdx; i++)    padding += PADDING;
		            formatted += padding + node + '\r\n';
		            padIdx += indent;
		            console.log('index:'+index+',indent:'+indent+',padIdx:'+padIdx+',node-->'+node);
		        });
		        return formatted;
		    },
		    objectNotBlank:function(obj){
		    	for(var key in obj){
		    		if(!obj[key] && obj[key].trim() !== '') return true;
		    	}
		    	return false;
		    },
		    blockUtil: function(millisecond,func){
		    	var count = millisecond / 100, result;
		    	while(count > 0){
		    		setTimeout(function(){ result = func}, 100);
		    		if(result)break;
		    		count--;
		    	}
		    }
};

exports('jeesuitelayui', jeesuitelayui);

//template
 $('*[dataLoad]').each(function(){
		var $this = $(this),url = jeesuitelayui.buildPath($this.attr('dataLoad')),
		    renderTemplate = $this.attr('template-id'),
		    method=$this.attr('ajax-method') || 'GET',
		    callback = $this.attr('onFinishCallback'),
		    templateExtends=$this.attr('template-ext');	
		if(!url || url == ''){jeesuitelayui.error('dataLoadURL不能为空');return;}
		var dataParams = jeesuitelayui.getQueryParams();
		
		for(var name in dataParams){
			var replace = '{' + name + '}';
			url = url.replace(replace,dataParams[name]);
		}
		
		var _global = {};//全局拓展只需要加载一次
		var extendsCallback = {};
		//
		renderPage();
		
		function renderPage(params){
			params = params || {};
			if($this.parent().is('form')){
				var query = $this.parent().serializeArray();
				if(query){
					$.each( query, function(i, field){
						params[field.name] = field.value;
					});
				}
			}
			var loading = layer.load();
			var postdata = (params.length && params.length > 0) ? JSON.stringify(params) : (method == 'GET' ? '' : '{}');
			$.ajax({
				dataType:"json",
			    type: method,
		        url: url,
		        contentType: "application/json",
		        data:postdata,
				complete: function(){layer.close(loading);},
				success: function(result){
					if(result.code && result.code==401){top.location.href = "/login";return;}
					if(result.code && result.code==403){jeesuitelayui.error(result.msg);return;}
					if(!result.code || result.code == 200){
						var renderData = result.data ? result.data : result;
						if(renderData == null || renderData.length == 0){
							return;
						}
						if(renderTemplate && renderTemplate != ''){
							var tpl = $('#'+renderTemplate).html();
							laytpl(tpl).render(renderData, function(html){
								$this.html(html);
							});
						}else{
							jeesuitelayui.dataRender($this,renderData);
						}
						//拓展执行
						for(var index in extendsCallback){
							if(!_global[index]){
								extendsCallback[index](renderData);
							}
						}
						//
						if(callback){
						    eval(callback+"(renderData)");
			             }
					}else{
						jeesuitelayui.error("无数据或者加载错误");
					}
				},
				error: function(xhr, type){
					if(xhr.status == 401){top.location.href = "/login";return;}
					if(xhr.status == 403){$this.html('无接口权限');return;}
					$this.html('系统错误');
				}
			});
		}
	});
	
  //submit
	$('body').on('click','.J_ajaxSubmit',function(){
		var $this = $(this),$form = $this.parent(),callback = $this.attr('onSuccessCallback');
		while(!$form.is('form')){
			$form = $form.parent();
		}
		//验证
		if(!jeesuitelayui.validateForm($form)){
		  return;
		}
		var params = jeesuitelayui.serializeJson($form);
		$this.attr('disabled',true);
		var loading = layer.load();
		var requestURI = jeesuitelayui.buildPath($form.attr('action'));
		$.ajax({
			dataType:"json",
		    type: "POST",
	        url: requestURI,
	        contentType: "application/json",
	        data:JSON.stringify(params) ,
			complete: function(){layer.close(loading);},
			success: function(data){
				if(data.code==401){top.location.href = "/login";return;}
				if(data.code==403){jeesuitelayui.error(data.msg || '无权限');return;}
		        if(data.code==200){
		        	 jeesuitelayui.success(data.msg || '操作成功');
		             data = data.data;
		             if(callback != undefined){
		            	 if(callback === 'reload'){
		            		 setTimeout(function(){parent.window.location.reload();},1000);
		            	 }else if(callback.indexOf('jumpTo:') == 0){
		            		 setTimeout(function(){window.location.href = callback.substr(7);},1000);
		            	 }else{
		            		 eval(callback+"(data)");
		            	 }
		             }else{
		            	 try {
		            		 var index = parent.layer.getFrameIndex(window.name);
		            		 setTimeout(function(){ parent.layer.close(index);},1000);
						} catch (e) {}
		             }
		          }else{
		        	 $this.removeAttr('disabled');
		        	 jeesuitelayui.error(data.msg);
		          }
		        },
			error: function(xhr, type){
				$this.removeAttr('disabled');
				if(xhr.status == 401){top.location.href = "/login";return;}
				if(xhr.status == 403){jeesuitelayui.error('无接口权限');return;}
				jeesuitelayui.error('系统错误');
			}
		});
	});
	//
	$('body').on('click','.J_iframe_dialog', function(){
		var self = $(this),
	     url = self.attr('data-url'),
	     dataId = self.attr('data-id'),
	     title = self.attr('data-title') || '弹窗',
	     width = self.attr('data-width') || 0,
	     height = self.attr('data-height') || 0,
	     area = ['893px', '600px'];
		
		var autoFull = width == 0 || height == 0;
		if(!autoFull){
			area = [width + 'px', height + 'px'];
		}
		
		if(dataId)url=url.replace('{id}',dataId);
		if(self.attr('data-scroll') === '0')url = [url, 'no'];
		 var index = layer.open({
			  type: 2,
			  title: title,
			  shadeClose: true,
			  shade: 0.8,
			  area: area,
			  content: url
			}); 
		 if(autoFull)layer.full(index);
	});
	
	//
	$('body').on('click','.J_form_dialog', function(){
		var self = $(this),
		     templdateurl = self.attr('data-templdate'),
		     dataurl = jeesuitelayui.buildPath(self.attr('data-url')),
		     dialogTitle = self.attr('data-title') || '表单',
		     onLoadFinished = self.attr('onLoadFinishedCallback');
		var addBoxIndex = -1;
		$.get(templdateurl, null, function(f) {
			addBoxIndex = layer.open({
				type: 1,
				title: dialogTitle,
				content: f,
				btn: ['提交', '取消'],
				shade: false,
				offset: ['100px', '30%'],
				area: ['600px', '400px'],
				zIndex: 99999,
				maxmin: true,
				yes: function(index) {
					//触发表单的提交事件
					$('form.layui-form').find('button[lay-filter=submitbtn]').click();
				},
				full: function(elem) {
					var win = window.top === window.self ? window : parent.window;
					$(win).on('resize', function() {
						var $this = $(this);
						elem.width($this.width()).height($this.height()).css({
							top: 0,
							left: 0
						});
						elem.children('div.layui-layer-content').height($this.height() - 95);
					});
				},
				success: function(layero, index) {
					//弹出窗口成功后渲染表单
					//form.render();
					if(onLoadFinished){
						eval(onLoadFinished+"(form)");
					}
					var $form = $('form.layui-form');
					//
					if(dataurl && dataurl != ""){
						var loading = layer.load();
						$.getJSON(dataurl,function(result){
							layer.close(loading);
							jeesuitelayui.dataRender($form,result.data);
						});
					}
					form.on('submit(submitbtn)', function(data) {
						var loading = layer.load();
						$.ajax({
							dataType:"json",
						    type: "POST",
						    contentType: "application/json",
					        url: $form.attr('action'),
					        data:JSON.stringify(data.field),
							complete: function(){layer.close(loading);},
							success: function(data){
								if(data.code==401){top.location.href = "/login.html";return;}
						        if(data.code == 200){
						        	jeesuitelayui.success(data.msg || "操作成功");
						        	setTimeout(function(){layer.close(index);},500);
						        	 window.location.reload() ;
						        }else{
						        	jeesuitelayui.error(data.msg || "操作失败");
						        }
						   },
							error: function(xhr, type){
								$this.removeAttr('disabled');
								jeesuitelayui.error('系统错误');
							}
						});
						return false; 									
					});
				},
				end: function() {
					addBoxIndex = -1;
				}
			});
		});
	});
	//
	$('body').on('click','.J_confirm', function(){
		var self = $(this),
			url = jeesuitelayui.buildPath(self.attr('data-url')),
			method=self.attr('ajax-method') || 'GET',
			msg = self.attr('data-msg') || '您确认该操作吗',
			data = self.attr('data'),
			dataId = self.attr('data-id'),
			callback = self.attr('onSuccessCallback');
		
		if(dataId){
		   url=url.replace('{id}',dataId);	
		   if(data)data=data.replace('{id}',dataId);
		}
		if(!data && method.toUpperCase() === 'POST'){
			data = '{}';
		}
		layer.confirm(msg, {
		    btn: ['确定','取消'], //按钮
		    shade: false //不显示遮罩

		}, function(index){
			$.ajax({
				dataType:"json",
			    type: method,
		        url: url,
		        data:data,
		        contentType: "application/json",
				complete: function(){layer.close(index);},
				success: function(result){
		        	if(result.code==401){top.location.href = "/login.html";return;}
					if(result.code == 200){
						jeesuitelayui.success(result.msg);
						setTimeout(function(){
							if(callback){
								if(callback === 'reload'){
									window.location.reload();
								}else{
									eval(callback);
								}
							}
						},500);
					}else{
						jeesuitelayui.error(result.msg);
					}
				}
			});
	        return false;
	    }, function(index){
	    	layer.close(index); 
		});
	});
	
	$('select[asnycSelect]').each(function(){
		var $this = $(this),url=jeesuitelayui.buildPath($this.attr("asnycSelect"))
		   ,lazySelectRef = $this.attr('lazy-select-ref')
		   ,onLoadFinished = $this.attr('onDataLoadCallback'); 
		
		$.getJSON(url,function(data){
			if(data.code && data.code==401){top.location.href = "/login";return;}
			if(data.code && data.code!=200){jeesuitelayui.error(data.msg);return;}
			if(data.code)data = data.data;
			if(onLoadFinished){
				eval(onLoadFinished+"(data)");
			}
			
			var defValue;
			if(lazySelectRef){
				defValue = eval(lazySelectRef);
				jeesuitelayui.blockUtil(3000,function(){
					return defValue;
				});
			}
			var opthtml;
			for(var index in data){
				if(data[index].label){
					$this.append('<optgroup label="'+data[index].label+'">');
					var options = data[index].options;
					for(var index2 in options){
						opthtml = '<option value="'+options[index2].value+'">'+options[index2].text+'</option>';
						$this.append(opthtml);
					}
				}else{
					var selected = '';
					if(data[index].selected || defValue + '' === data[index].value){
						selected = 'selected="selected"';
					}
					opthtml = '<option value="'+data[index].value+'" '+selected+'>'+data[index].text+'</option>';
					$this.append(opthtml);
				}
			}
			form.render('select');
		});
	});
	
	$('select[changeJoinEvent]').each(function(){
		var $this = $(this), 
		    eventOpts = JSON.parse($(this).attr("changeJoinEvent")),
		    filterName = $this.attr('lay-filter'),
		    $target = $(eventOpts.target);
		
		var defaultOptionHtml;
		form.on('select('+filterName+')',function(data) {
			if(data.value === '')return;
			var url = eventOpts.dataUrl + data.value;
			$.getJSON(url,function(result){
				if(result.code && result.code==401){top.location.href = "/login";return;}
				if(result.code && result.code!=200){jeesuitelayui.error(result.msg);return;}
				var data = result.code ? result.data : result;
				if(!data || data.length == 0){jeesuitelayui.error(eventOpts.noneMessage || '无关联数据');return;}
				//
				if(eventOpts.renderType === 'select'){
					if(!defaultOptionHtml){
						var defaultText = "请选择..";
						if($target.find("option")){
							defaultText = $target.find("option").eq(0).text();
						}
						defaultOptionHtml = '<option value="">'+defaultText+'</option>';
					}
					var opthtml = defaultOptionHtml;
					for(var index in data){
						if(data[index].label){
							opthtml = opthtml + '<optgroup label="'+data[index].label+'">';
							var options = data[index].options;
							for(var index2 in options){
								opthtml = opthtml + '<option value="'+options[index2].value+'">'+options[index2].text+'</option>';
							}
						}else{
							var selected = data[index].selected ? 'selected="selected"' : '';
							opthtml = opthtml + '<option value="'+data[index].value+'" '+selected+'>'+data[index].text+'</option>';
						}
					}
					$target.html(opthtml);
					form.render('select');
				}else if(eventOpts.renderType === 'checkbox'){
					var html = "";
					var attrName = eventOpts.attrName;
					for(var index in data){
						html = html + '<input type="checkbox" name="'+attrName+'" value="'+data[index].value+'" lay-skin="primary" title="'+data[index].text+'">';
					}
					$target.html(html);
					layui.form.render('checkbox');
				}
			});
		});
	});
	
});
