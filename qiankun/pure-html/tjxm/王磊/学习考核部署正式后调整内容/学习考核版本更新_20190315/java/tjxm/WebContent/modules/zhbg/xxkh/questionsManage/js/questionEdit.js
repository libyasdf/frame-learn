
$(function(){
	
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(theRequest.id);
	var opertation  = theRequest.opertation;
	
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val(),"opertation":opertation};
		httpRequest("get","/zhbg/xxkh/question/edit",datas,function (data){
			DisplayData.playData({data:data});
			if(opertation == "VIEW"){
				//答案选项回显
				if(data.data.questionType==Config.danXuan){
					$("#one").show();
					$("table td").css("border","none");
					//设置选项内容并设置正确选项
					for(var i=0;i<data.data.list.length;i++){
						$("#one").find("input[name='danx']").attr("disabled","disabled");
						$("#danxtable").find("span:eq("+parseInt(data.data.list[i].sequence-1)+")").text(data.data.list[i].content);						//设置正确答案
						if(data.data.list[i].isRight=="1"){
							$("#one").find("input[name=danx]:eq("+parseInt(data.data.list[i].sequence-1)+")").attr("checked","checked");
						}
					}
				}else if(data.data.questionType==Config.duoXuan){
					$("#two").show();
					//设置选项内容并设置正确选项
					for(var i=0;i<data.data.list.length;i++){
						addDuoXuanAnswer();
						$("#duoxDiv").find("span[name='duoxc']:eq("+parseInt(data.data.list[i].sequence-1)+")").text(String.fromCharCode(parseInt(data.data.list[i].sequence)+64)+"."+data.data.list[i].content);
						if(data.data.list[i].isRight=="1"){
							//网上说所有的jquery都可以使用$("#cb1").attr("checked","checked");这种方式
							//jquery1.6+可以使用$("#cb1″).prop("checked",true);这种方式，但是这里用上一种竟然不行！！！
							$("#duoxAnswerDiv").find("input[name='duox']:visible:eq("+parseInt(data.data.list[i].sequence-1)+")").prop("checked",true);
						}
						$("#duoxAnswerDiv").find("input[name='duox']:visible").attr("disabled","disabled");
					}
				}else if(data.data.questionType==Config.panDuan){
					$("#tre").show();
					$("table td").css("border","none");
					//设置正确选项被选中
					for(var i=0;i<data.data.list.length;i++){
						if(data.data.list[i].isRight=="1"){
							$("#pandtable").find("td").find("input[value='"+data.data.list[i].sequence+"']").attr("checked","checked");
						}
					}
				}else if(data.data.questionType==Config.tianKong){
					$("#for").show();
					for(var i=0;i<data.data.list.length;i++){
						//添加答案位置
						addAnswer();
						//回显答案
						$("#tk").find("span[name='tiankc']:eq("+parseInt(data.data.list[i].sequence-1)+")").text(data.data.list[i].sequence+"."+data.data.list[i].content);
					}
				}else if(data.data.questionType==Config.jianDa){
					$("#fiv").show();
				}
				//难易程度
				var val = "";
				if(data.data.difficultyLevel == '1'){val = "简单";}
				if(data.data.difficultyLevel == '2'){val = "一般";}
				if(data.data.difficultyLevel == '3'){val = "困难";}
				$("[name='difficultyLevel']").text(val);
				
				//题型
				if(data.data.questionType == '1'){val = "单选";}
				if(data.data.questionType == '2'){val = "多选";}
				if(data.data.questionType == '3'){val = "判断";}
				if(data.data.questionType == '4'){val = "填空";}
				if(data.data.questionType == '5'){val = "简答";}
				$("[name='questionType']").text(val);
				//试题描述
				$("[name='describe']").text(data.data.describe);
				//答案解析
				$("[name='analysis']").text(data.data.analysis);
			}else if(opertation == "UPDATE"){
				//显示发布按钮
				$("#publish").show();
				//答案选项回显
				if(data.data.questionType==Config.danXuan){
					$("#one").show();
					//设置选项内容并设置正确选项
					for(var i=0;i<data.data.list.length;i++){
						//设置选项内容
						$("#danxtable").find("input:eq("+parseInt(data.data.list[i].sequence-1)+")").val(data.data.list[i].content);						//设置正确答案
						if(data.data.list[i].isRight=="1"){
							$("#one").find("input[name=danx]:eq("+parseInt(data.data.list[i].sequence-1)+")").attr("checked","checked");
						}
					}
				}else if(data.data.questionType==Config.duoXuan){
					$("#two").show();
					//设置选项内容并设置正确选项
					for(var i=0;i<data.data.list.length;i++){
						//增加多选选项
						addDuoXuanOption();
						//回显选项内容并选中上次设置的正确答案
						//设置选项
						$("#duoxDiv").find("input[name='duoxc']:eq("+parseInt(data.data.list[i].sequence-1)+")").val(data.data.list[i].content);
						if(data.data.list[i].isRight=="1"){
							//网上说所有的jquery都可以使用$("#cb1").attr("checked","checked");这种方式
							//jquery1.6+可以使用$("#cb1″).prop("checked",true);这种方式，但是这里用上一种竟然不行！！！
							$("#duoxAnswerDiv").find("input[name='duox']:visible:eq("+parseInt(data.data.list[i].sequence-1)+")").prop("checked",true);
						}
					}
				}else if(data.data.questionType==Config.panDuan){
					$("#tre").show();
					//设置正确选项被选中
					for(var i=0;i<data.data.list.length;i++){
						if(data.data.list[i].isRight=="1"){
							$("#pandtable").find("td").find("input[value='"+data.data.list[i].sequence+"']").attr("checked","checked");
						}
					}
				}else if(data.data.questionType==Config.tianKong){
					$("#for").show();
					for(var i=0;i<data.data.list.length;i++){
						add();
						$("#tk").find("input#tiankc:eq("+parseInt(data.data.list[i].sequence-1)+")").val(data.data.list[i].content);
					}
				}else if(data.data.questionType==Config.jianDa){
					$("#fiv").show();
				}
				//试题描述
				$("[name='describe']").text(data.data.describe);
				//答案解析
				$("[name='analysis']").text(data.data.analysis);
			}
		});
	}else{
		//如果id为空，默认显示单选的div
		$("#one").show();
	}
})

/**
 * 保存并新增试题
 */
function saveForm(isNew){
	var formData = getFormData();
	var theRequest = GetRequest();
	if(formData != false){
		//alert(formData);
		$.ajax({
			type: "post",
			url:"/zhbg/xxkh/question/saveQuestion?resId="+theRequest.resId,
			data:formData,
			contentType:"application/json",
			async: false,
			dataType:"json",
			success:function(data){
				if(data.flag=='1'){
					layer.msg("保存成功！", {icon: 1});
					//刷新列表
					parent.TableInit.refTable('right_table');
					if(isNew){
						initCreatePage();
						//隐藏发布按钮
						$("#publish").hide();
					}else{
						$("#id").val(data.questionId);
						//显示发布按钮
						$("#publish").show();
					}
				}else{
					layer.msg("保存失败！", {icon: 0});
				}
			},
			error:function(data){
				layer.msg("请稍后再试！", {icon: 0});
			}
		});
	}
}	
	
/**
 * 切换试题类型
 * @returns {Boolean}
 */
function chan() {
	//试题描述、答案分析在切换时置空
	$('[name="describe"]').val("");
	$('[name="analysis"]').val("");
	//恢复单选设置
    var val = $("#sel").val();
    if(val == 4){
        $("#one").css("display","none");
        $("#two").css("display","none");
        $("#tre").css("display","none");
        $("#fiv").css("display","none");
        $("#for").css("display","block");
    }else if(val == 1){
        $("#one").css("display","block");
        $("#two").css("display","none");
        $("#tre").css("display","none");
        $("#fiv").css("display","none");
        $("#for").css("display","none");
    }else if(val == 2){
        $("#one").css("display","none");
        $("#two").css("display","block");
        $("#tre").css("display","none");
        $("#fiv").css("display","none");
        $("#for").css("display","none");
    }else if(val == 3){
        $("#one").css("display","none");
        $("#two").css("display","none");
        $("#tre").css("display","block");
        $("#fiv").css("display","none");
        $("#for").css("display","none");
    }else if(val == 5){
        $("#one").css("display","none");
        $("#two").css("display","none");
        $("#tre").css("display","none");
        $("#fiv").css("display","block");
        $("#for").css("display","none");
    }else{
        return false;
    }
}

/**
 * 生成试题、选项的json串
 * @returns {Boolean}
 */
function getFormData(){
	var isPassCheck = true;
	//从参数中取出试题所在类型
	var theRequest = GetRequest();
	var deviceInfo = {
			id : $("#id").val(),//试题id
	    	difficultyLevel : $('[name="difficultyLevel"]').val(),//主表难易程度属性
	    	questionType : $('[name="questionType"]').val(),//主表题型属性
	    	type : theRequest.treeType,//试题所在大类：法制(fzst)、保密(bmst)、政治理论(zzllst)
	    	nodeId : theRequest.nodeId,//试题所在类型
	        list : [],//子表信息
	    };
	
	if($("select[name='questionType']").val()==Config.danXuan){
		//单选的处理方式
		//设置主表的属性
		deviceInfo.describe=$("div#one [name='describe']").val();
		deviceInfo.analysis=$("div#one [name='analysis']").val();
		
		//校验试题必填项
		if($.trim($("div#one [name='describe']").val())==""){
			layer.msg('试题描述不能为空！',{icon:0});
			$("div#one [name='describe']").focus();
			isPassCheck = false;
			return false;
		}
		//选项内容校验
		var inputs = $("#danxtable input[name='danxc']");
		var selRadio = $("input[name='danx']:checked").val();
		inputs.each(function(index,element){
			var order = index + 1;
			var isRight = order==selRadio ? '1':'0';
			var content = $(element).val();
			//校验试题选项
			if($.trim(content)==""){
				layer.msg('答案选项不能为空！',{icon:0});
				$(element).focus();
				isPassCheck = false;
				return false;
			}
			var disk = {
					content : content,
					isRight : isRight,
					sequence : order
	            };
			deviceInfo.list.push(disk);
		});
		
		//如果之前校验都通过，并且没有设置正确答案才会提示
		if($("input[name='danx']:checked").length == 0 && isPassCheck){
			layer.msg('请设置正确答案！',{icon:0});
			isPassCheck = false;
			return false;
		}
	}else if($("select[name='questionType']").val()==Config.duoXuan){
		//多选的处理方式
		//设置主表的属性
		deviceInfo.describe=$("div#two [name='describe']").val();
		deviceInfo.analysis=$("div#two [name='analysis']").val();
		//校验试题必填项
		if($.trim($("div#two [name='describe']").val())==""){
			layer.msg('试题描述不能为空！',{icon:0});
			$("div#two [name='describe']").focus();
			isPassCheck = false;
			return false;
		}
		//所有被选中的多选选项
		var selChecks = $("#duoxAnswerDiv input[name='duox']:checked");
		//获取所有多选选项内容
		var inputs = $("#duoxDiv input[name='duoxc']");
		if(inputs.length==0){
			layer.msg('请添加答案选项！',{icon:0});
			isPassCheck = false;
			return false;
		}
		//遍历所有多选选项内容并附带校验
		inputs.each(function(index,element){
			var order = index + 1;//此序号为选项内容的顺序，1/2/3/4...
			var isRight = "0";//默认不是正确答案
			//判断跟选项内容对应的复选框是否选中
			if($("#duoxAnswerDiv").find("input:eq("+order+")").is(':checked')){
				isRight = "1";
			}
			var content = $(element).val();
			//校验试题选项
			if($.trim(content)==""){
				layer.msg('答案选项不能为空！',{icon:0});
				$(element).focus();
				isPassCheck = false;
				return false;
			}
			var disk = {
					content : content,
					isRight : isRight,
					sequence : order
	            };
			deviceInfo.list.push(disk);
		});
		
		//校验多选选项是否有勾选
		if(selChecks.length == 0 && isPassCheck){
			layer.msg('请设置正确答案！',{icon:0});
			isPassCheck = false;
			return false;
		}else if(selChecks.length == 1 && isPassCheck){
			layer.msg('多选题的正确答案至少为两个！',{icon:0});
			isPassCheck = false;
			return false;
		}
	}else if($("select[name='questionType']").val()==Config.panDuan){
		//判断的处理方式
		//设置主表的属性
		deviceInfo.describe=$("div#tre [name='describe']").val();
		deviceInfo.analysis=$("div#tre [name='analysis']").val();
		//校验试题必填项
		if($.trim($("div#tre [name='describe']").val())==""){
			layer.msg('试题描述不能为空！',{icon:0});
			$("div#tre [name='describe']").focus();
			isPassCheck = false;
			return false;
		}
		//获取被选中的值。A:0:错误,B:1:正确
		var selectValue = $("#pandtable input[name='pandr']:checked").val();
		//获取所有选项
		var inputs = $("#pandtable input[name='pandr']");
		inputs.each(function(index,element){
			var order = index + 1;
			var content = "错误";
			//默认是错误选项
			var isRight = "0";
			if(selectValue == $(element).val()){
				isRight = "1";
			}
			if(order == 2){
				content = "正确";
			}
			var disk = {
					content : content,
					isRight : isRight,
					sequence : order
	            };
			deviceInfo.list.push(disk);
		});
	}else if($("select[name='questionType']").val()==Config.tianKong){
		//填空的处理方式
		//设置主表的属性
		var describe = $("div#for [name='describe']").val();
		deviceInfo.describe=describe;
		deviceInfo.analysis=$("div#for [name='analysis']").val();
		//判断试题描述是否填写
		if($.trim(describe)==""){
			layer.msg('试题描述不能为空！',{icon:0});
			$("div#for [name='describe']").focus();
			isPassCheck = false;
			return false;
		}
		/*//取填空题描述里的“（）”个数
		var count = patch(describe,'（）');
		//判断是否有中文括号
		if(count==0){
			layer.msg('试题中没有中文括号！',{icon:0});
			isPassCheck = false;
			return false;
		}*/
		//获取所有的答案
		var inputs = $("div[id='for'] input[name='tiankc']");
		/*//比较答案个数和试题里中文空格个数是否相等
		if(inputs.length != count){
			layer.msg('答案个数跟试题描述不符！',{icon:0});
			isPassCheck = false;
			return false;
		}*/
		if(inputs.length == 0){
			layer.msg('请添加答案！',{icon:0});
			isPassCheck = false;
			return false;
		}
		inputs.each(function(index,element){
			if($.trim($(element).val())==""){
				layer.msg('答案内容不能为空！',{icon:0});
				$(element).focus();
				isPassCheck = false;
				return false;
			}
			var order = index + 1;
			var disk = {
					content : $(element).val(),
					isRight : "1",
					sequence : order
	            };
			deviceInfo.list.push(disk);
		});
	}else{
		//设置主表的属性
		deviceInfo.describe=$("div#fiv [name='describe']").val();
		deviceInfo.analysis=$("div#fiv [name='analysis']").val();
		//校验试题必填项
		if($.trim($("div#fiv [name='describe']").val())==""){
			layer.msg('试题描述不能为空！',{icon:0});
			$("div#fiv [name='describe']").focus();
			isPassCheck = false;
			return false;
		}
	}
	if(!isPassCheck){
		return isPassCheck;
	}
	//alert(JSON.stringify(deviceInfo));
	//判断试题描述长度是否大于1000
	if(deviceInfo.describe.length > 1000){
		layer.msg('试题描述长度不能大于1000',{icon:0});
		return false;
	}
	//判断答案解析长度是否大于1000
	if(deviceInfo.analysis.length > 1000){
		layer.msg('答案解析长度不能大于1000',{icon:0});
		return false;
	}
	return JSON.stringify(deviceInfo);
}

/**
 * 增加填空选项
 */
function add(){
	var tkNum = 1;
	if($("#tk").find("div").length != 0){
		tkNum = $("#tk").find("div").length+1;
	}
    var htm;
    var str="<div style='display: inline'> <span style='display:inline-block; width:10px;'>" + tkNum + "</span>." + "<input type='text' style='width: 60%;height: 30px;border-radius: 4px;border: 1px solid #ccc' id='tiankc' name='tiankc'> <i title='删除答案' class='glyphicon glyphicon-trash' style='cursor:pointer;' onclick='deleteTianKItem(this);'></i></div>";
    if(tkNum != 1){
        htm="<p></p>" + str;
    }else{
        htm=str;
    }
    $("#tk").append(htm);

}

/**
 * 删除填空选项
 */
function deleteTianKItem(obj){
	//删除当前选项
	//$(obj).parent("div").remove();
	if($("#tk").find("div").length == 1){
		//只有一个，直接删除就行
		$(obj).parent("div").remove();
	}else{
		if($(obj).parent("div").next("p").length==0){
			//最后一个，直接删除同时需要删除上一个p标签
			$(obj).parent("div").prev("p").remove();
			$(obj).parent("div").remove();
		}else{
			//不是最后一个，删除div和下一个p标签
			$(obj).parent("div").next("p").remove();
			$(obj).parent("div").remove();
		}
	}
	//给剩下的选项调整序号
	$("#tk").find("div").each(function (index,element){
		$(element).find("span").text(index+1);
	});
}

/**
 * 只读增加填空答案
 */
function addAnswer(){
    var tkNum = 1;
	if($("#tk").find("div").length != 0){
		tkNum = $("#tk").find("div").length+1;
	}
    var htm;
    var str="<div style='display: inline;'><span style='display:inline-block; ' name='tiankc'></span></div>";
    if(tkNum != 1){
        htm="<p></p>" + str;
    }else{
        htm=str;
    }
    $("#tk").append(htm);

}

/**
 * 增加多选选项
 */
function addDuoXuanOption(){
	var htm;
	var duoxNum = 1;
	if($("#duoxDiv").find("div").length != 0){
		duoxNum = $("#duoxDiv").find("div").length+1;
	}
    var str=" <div style='display: inline'><span style='display:inline-block; width:10px;'>" + String.fromCharCode((duoxNum+64)) + "</span>." + "<input type='text' style='width: 60%;height: 30px;border-radius: 4px;border: 1px solid #ccc' id='duoxc' name='duoxc'> <i title='删除答案' class='glyphicon glyphicon-trash' style='cursor:pointer;' onclick='deleteDuoxItem(this);'></i></div>";
    if(duoxNum != 1){
        htm="<p></p>" + str;
    }else{
        htm=str;
    }
    $("#duoxDiv").append(htm);
    //对应的增加答案设置
    var aLabel = $("#duoxAnswerDiv").find("label:hidden").clone().show();
    aLabel.find("input").val(duoxNum);
    aLabel.find("span").html(String.fromCharCode((duoxNum+64)));
    $("#duoxAnswerDiv").append(aLabel);
}

/**
 * 删除多选选项
 */
function deleteDuoxItem(obj){
	//删除当前选项
	//alert($(obj).parent("div").next("p").length+"前一个："+$(obj).parent("div").prev("p").length);
	//删除对应答案选项
	var text = $(obj).parent("div").find("span").text();
	//alert($("#duoxAnswerDiv").find("span:contains('"+text+"')").parent("label").html());
	$("#duoxAnswerDiv").find("span:contains('"+text+"')").parent("label").remove();
	if($("#duoxDiv").find("div").length == 1){
		//只有一个，直接删除就行
		$(obj).parent("div").remove();
	}else{
		if($(obj).parent("div").next("p").length==0){
			//最后一个，直接删除同时需要删除上一个p标签
			$(obj).parent("div").prev("p").remove();
			$(obj).parent("div").remove();
		}else{
			//不是最后一个，删除div和下一个p标签
			$(obj).parent("div").next("p").remove();
			$(obj).parent("div").remove();
		}
	}
	//调整其他元素的字母标号
	$("#duoxDiv").find("div").each(function(index,element){
		$(element).find("span").html(String.fromCharCode((index+64+1)));
	});
	//调整答案的字母标号
	$("#duoxAnswerDiv").find("label:visible").each(function(index,element){
		$(element).find("span").text(String.fromCharCode((index+64+1)));
	});
}

/**
 * 只读增加多选答案
 */
function addDuoXuanAnswer(){
    var htm;
	var duoxNum = 1;
	if($("#duoxDiv").find("div").length != 0){
		duoxNum = $("#duoxDiv").find("div").length+1;
	}
    var str=" <div style='display: inline'><span style='display:inline-block;' id='duoxc' name='duoxc'></span></div>";
    if(duoxNum != 1){
        htm="<p></p>" + str;
    }else{
        htm=str;
    }
    $("#duoxDiv").append(htm);
    //对应的增加答案设置
    var aLabel = $("#duoxAnswerDiv").find("label:hidden").clone().show();
    aLabel.find("input").val(duoxNum);
    aLabel.find("span").html(String.fromCharCode((duoxNum+64)));
    $("#duoxAnswerDiv").append(aLabel);
}

/**
 * 获得s在str中出现次数
 * @param str
 * @param s
 */
function patch(str,s){
	return (str.split(s)).length-1;
}

//关闭当前窗口
function closeIfram(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

/**
 * 点击保存并新增并保存完数据后，根据当前提醒和难度，初始化页面
 */
function initCreatePage(){
	//清除id
	$("#id").val("");
	//a=1,charn=65
	a=1;charn=65;
	if($("select[name='questionType']").val()==Config.danXuan){
		//清除试题描述和答案解析
		$("div#one").find("[name='describe']").text("");
		$("div#one").find("[name='analysis']").text("");
		//默认不选中任何选项、并清空选项内容
		$("div#one").find("input[name='danx']:checked").prop("checked", "");
		$("#danxtable").find("input[name='danxc']").each(function(){
			$(this).val("");
		});
	}else if($("select[name='questionType']").val()==Config.duoXuan){
		//清除试题描述和答案解析
		$("div#two").find("[name='describe']").text("");
		$("div#two").find("[name='analysis']").text("");
		//默认都不选中、并清空选项内容
		$("#duoxDiv").text("");
		//清空设置正确答案区域
		$("#duoxAnswerDiv").find("label:visible").remove();
	}else if($("select[name='questionType']").val()==Config.panDuan){
		//清除试题描述和答案解析
		$("div#tre").find("[name='describe']").text("");
		$("div#tre").find("[name='analysis']").text("");
		//默认选中正确
		$("#pandtable").find("input[name='pandr']:eq(1)").prop("checked",true);
	}else if($("select[name='questionType']").val()==Config.tianKong){
		//清除试题描述和答案解析
		$("div#for").find("[name='describe']").text("");
		$("div#for").find("[name='analysis']").text("");
		//清空填空
		$("#tk").html("");
	}else{
		//清除试题描述和答案解析
		$("div#fiv").find("[name='describe']").text("");
		$("div#fiv").find("[name='analysis']").text("");
	}
}

/**
 * 提交试题：修改试题状态，提交后不可以修改
 */
function publish(){
	//提交前先进行保存
	var formData = getFormData();
	var theRequest = GetRequest();
	var saveSuccess = false;
	if(formData != false){
		layer.confirm('确认提交吗？提交试题后将不可编辑！',{icon:3,title:'提交'},function(){
			$.ajax({
				type: "post",
				url:"/zhbg/xxkh/question/saveQuestion?resId="+theRequest.resId,
				data:formData,
				contentType:"application/json",
				async: false,
				dataType:"json",
				success:function(data){
					if(data.flag=='1'){
						//layer.msg("保存成功！", {icon: 1});
						//刷新列表
						parent.TableInit.refTable('right_table');
						$("#id").val(data.questionId);
						//显示发布按钮
						$("#publish").show();
						saveSuccess = true;
					}else{
						layer.msg("保存失败，请稍后再试！", {icon: 0});
					}
				},
				error:function(data){
					layer.msg("保存功能异常，请稍后再试！", {icon: 0});
				}
			});
			//保存成功后再进行提交
			if(saveSuccess == true){
				$.ajax({
					type: "post",
					url:"/zhbg/xxkh/question/commitById",
					data:{id:$("#id").val()},
					async: true,
					dataType:"json",
					success:function(data){
						if(data.flag=='1'){
							layer.confirm('提交成功！点击【确定】将跳转只读页面。',{
									icon:1
									,title:'信息'
									,btn: ['确定'] //按钮
									,btn1:function(index){
										//刷新列表
										parent.TableInit.refTable('right_table');
										closeIfram();
										window.parent.openReadonly($("#id").val(),$("#resId").val());
										//隐藏操作按钮
										$("#save").hide();
										$("#saveAndNew").hide();
										$("#publish").hide();
									},end:function(index){
										//刷新列表
										parent.TableInit.refTable('right_table');
										closeIfram();
										window.parent.openReadonly($("#id").val(),$("#resId").val());
										//隐藏操作按钮
										$("#save").hide();
										$("#saveAndNew").hide();
										$("#publish").hide();
									}
							});
						}else{
							layer.msg("提交失败，请稍后再试！", {icon: 0});
						}
					},
					error:function(data){
						layer.msg("提交功能异常，请稍后再试！", {icon: 0});
					}
				});
			}
		})
	}
}
