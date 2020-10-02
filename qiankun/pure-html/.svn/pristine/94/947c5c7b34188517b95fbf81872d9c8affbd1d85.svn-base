var testUsers = {};
//考试人员（发考试待办做准备）
var person = [];
//考试人员id(保存人员试卷信息参数)
var userId = "";
//角色序号
var roleNo = "D221,C221";
//本次考试是否需要上报
var count = 0;
var chushi = [];
$(function() {
	var theRequest = GetRequest();
	//资源id
	var resId = theRequest.resId;
	$("#resId").val(theRequest.resId);
	//考试基本信息id
	var id = theRequest.id;
	$("#id").val(theRequest.id);
	//考试树类型(type:大类；nodeId小类)
	$("#type").val(theRequest.treeType);
	$("#nodeId").val(theRequest.nodeId);
	console.log("nodeId："+theRequest.nodeId+",treeType类型："+theRequest.treeType);
	//当前用户的juid
	var juId = getcookie("juId");
	$("#juId").val(juId);
	//职务职级数据渲染
	var res = getposition();
	if(id !="" && id!= null){
		//渲染考试基本信息
		if(res){
			EditFormEcho.echo({
				domId: "id" //ID回显存储的隐藏域ID
					, recordId: id//业务ID
					, url: "/zhbg/xxkh/testmanage/edit"
			});
		}
	}else{
		$("#paperOne").show();//新增显示选择试卷按钮
	}
});

/**
 * 保存表单
 */
function saveForm(){
	var res = false;
	if($("#id").val()==""){
		var bootstrapValidator = $("#form").data('bootstrapValidator');
	    //手动触发验证
		bootstrapValidator.enableFieldValidators('passScore', false);
		bootstrapValidator.enableFieldValidators('full', false);
	    bootstrapValidator.validate();
	    if(bootstrapValidator.isValid()){
	    	var data = saveTestInfo();
	    	if(data){
	    		res = true;
	    		layer.msg("保存成功！", {icon: 1});
	    		//刷新列表
	    		parent.TableInit.refTable('right_table');
	    	}
	    }
	}else{
		var bootstrapValidator3 = $("#form3").data('bootstrapValidator');
		var bootstrapValidator = $("#form").data('bootstrapValidator');
		bootstrapValidator.enableFieldValidators('passScore', true);
		bootstrapValidator.enableFieldValidators('full', true);
	    //手动触发验证
	    bootstrapValidator.validate();
	    bootstrapValidator3.validate();
	    if(bootstrapValidator.isValid()&&bootstrapValidator3.isValid()){
	    	//保存考试信息
	    	var data = saveTestInfo();
	    	console.log("保存基本信息："+data);
	    	if(data){
	    		/*if($("input[name='testToDepartment']").val()!=""||$("input[name='testToDepartment']").val()!=null){
	    			if($("input[name='testToDepartment']:checked").val()=="0"){
	    				//保存人员信息
	    				//var result = saveTestPerson();
	    				//console.log("保存考试人员："+result);
	    				if(result){
	    					res = true;
	    					layer.msg("保存成功！", {icon: 1});
	    					//刷新列表
	    					parent.TableInit.refTable('right_table');
	    				}else{
	    					layer.msg("保存失败！", {icon: 2});
	    				}
	    			}else{
	    				layer.msg("保存成功！", {icon: 1});
		    			//刷新列表
		    			parent.TableInit.refTable('right_table');
		    			
		    			
	    			}
	    		}else{
	    			layer.msg("保存成功！", {icon: 1});
	    			//刷新列表
	    			parent.TableInit.refTable('right_table');
	    		}*/
	    		layer.msg("保存成功！", {icon: 1});
    			//刷新列表
    			parent.TableInit.refTable('right_table');
	    	}else{
	    		layer.msg("保存失败！", {icon: 2});
	    	}
	    }
	}
	return res;
}

/**
 * 保存考试基本信息
 */
function saveTestInfo(){
	var res = false;
	var params = getTestInfo();
	params.timeRange = $("#timeRange").val();
	$.ajax({
		type: "POST",
		url:"/zhbg/xxkh/testmanage/save",
		data:params,
		async: false,
		success:function(json){
			if (json.flag == '1') {
				res = true;
				//保存考试id
				$("#id").val(json.data.id);
				//保存考试名称
				$("#testName").val(json.data.name);
			}else {
				
			}
		},
		error:function(){
		}
	});
    return res;
}

/**
 * 保存考试人员信息（不需要上报考试人员时）
 */
function saveTestPerson(url){
	var res = false;
	var par = getPar();
	var params = {
			id:$("#id").val(),
			name:$("#testName").val(),
			deptId:$("#unitId").val(),
			positions:par.position,
			positionLevels:par.positionLevel
			};
	var _url = url;
		$.ajax({
			url:_url,
			data:params,
			dataType:"json",
			async: false,
			success:function(json){
				if (json.flag == '1') {
					res = true;
					//保存
					//layer.msg('保存成功！',{icon:1});
				}else {
					layer.close(index);
					layer.msg(json.msg+'选择考试人员后再发布！',{icon:2});
				}
			},
			error:function(){
			}
		});
	return res;
}

/**
 * 考试人员列表初始化
 */
function tableInit(){
	$("#right_table").bootstrapTable("destroy");
	index = layer.load(1,{shade: [0.5, '#393D49'],content: '请稍候',success: function(layero){
		layero.find('.layui-layer-content').css({'padding-top':'50px','font-size':'16px','color':'#000000','width':'200px'});
		setTimeout("loadPersonCount()",1000)
	} });
	
}
/**
 * 加载考试人数
 */
function loadPersonCount(){
	$('#right_table').bootstrapTable({
	 	url: "/zhbg/xxkh/testmanage/getTestPersonCount?rdm="+Math.random(),
        sidePagination:"client",
        pagination:true,
        striped:false,
        classes: 'table table-hover',
        fixedColumns: true,//是否固定列
        fixedNumber: "", //固定列数
        pageList: "[10, 25, 50, 100]",
        columns: right_table_col,
        queryParams: function (params) {
        	params =  getPar();
            return params;
        },
        rowStyle: function (row, index) {	//默认样式：居中，
        	
            return {
                classes: 'readOnly'
                ,css: {"text-align":"center","cursor":"default"}
            };
        },
        onLoadSuccess: function () {
        	$("#right_table thead").addClass(".fixed-table-header");
			//保存人员信息
			saveTestPerson("/zhbg/xxkh/testmanage/savePerson");
			//保存考试处室和职务、职级等考试信息
			saveTestInfo();
			var data = $("#right_table").bootstrapTable("getData");
			if(data.length==0){
				layer.close(index);
				layer.msg('没有匹配的人员！',{icon:2});
			}else{
				layer.close(index);
				/*chushi = $("#unitName").val().split(",");
				if(chushi.length!=data.length){
					for(var i in data){
						if($.inArray(data[i].candidatechushiname,chushi)!= -1){
							chushi.splice( chushi.indexOf(data[i].candidatechushiname),1);
						}
					}
					if(chushi.length!=0){
						layer.alert(chushi+'，没有匹配的人员，请删除此处室！',{icon:0});
					}
				}*/
			}
        }
	});
}
/**
 * 刷新列表(从关联表查人数)
 */
function refreshTable(tableId){
	$("#"+tableId).bootstrapTable("destroy");
	$.ajax({
		url:"/zhbg/xxkh/testmanage/getTestPersonCountInUserPaper?testId="+$("#id").val()+"&resId="+$("#resId").val()+"&rdm="+Math.random(),
		async:false,
		success:function(json){
			if (json.flag == '1') {
				$('#'+tableId).bootstrapTable({
			        sidePagination:"client",
			        pagination:true,
			        striped:false,
			        classes: 'table table-hover',
			        fixedColumns: true,//是否固定列
			        fixedNumber: "", //固定列数
			        pageList: "[10, 25, 50, 100]",
			        columns: right_table_echo_col,
			        responseHandler:function(res){
						return responseHandler(res);
					},
			        rowStyle: function (row, index) {	//默认样式：居中，
			        	
			            return {
			                classes: 'readOnly'
			                ,css: {"text-align":"center","cursor":"default"}
			            };
			        },
			        onLoadSuccess: function () {
			        }
				});
				$('#'+tableId).bootstrapTable('load',json.data); 
				if($("#inlineRadio2").is(":checked")){
					//获取已选择部门
					var optChushi = $("#unitName2").val().split(",");
					console.log($("#unitName2").val());
					for(var i in json.data){
						var index = $.inArray(json.data[i].candidateChushiName,optChushi);
						if(index!=-1){
							optChushi.splice(index, 1);
						}
					}
					console.log("chushsi");
					if(optChushi.length ==0){
						$("#noReport").val("");
					}else{
						var notReportChushi = optChushi.join(",");
						$("#noReport").val(notReportChushi);
					}
				}
			}else {
				
			}
		}
	});  
}

/**
 * 根据条件查询考试人员和保存考试人员信息（确定按钮事件）
 */
function queryTestPerson(){
	if($("#id").val()=="" ||$("#id").val()==null ){
		layer.msg("请先保存考试基本信息！", {
			icon : 0
		});
	}else{
		$("#form3").bootstrapValidator("addField", "unitName", {  
			validators: {  
				notEmpty : {
					message : '选择处室不能为空！'
				} 
			}  
		});
		var bootstrapValidator = $("#form3").data('bootstrapValidator');
		//手动触发验证
		bootstrapValidator.validate();
		if(bootstrapValidator.isValid()){
			//刷新人数列表(根据选择的条件查)
			$("#right_table").bootstrapTable("destroy");
			tableInit();
			$("#inlineRadio2").prop("disabled",true);
		}
	}
}

/**
 * 判断是否有上报员
 */
function hasReportUser(par){
	var res = {};
	var params = {
			"deptNames":par.deptNames,//处室名称
			"deptIds":par.deptIds,//处室id
			"roleNo":par.roleNo,//上报员角色序号
			};
	$.ajax({
		url:"/zhbg/xxkh/testmanage/hasReportUser",
		data:params,
		dataType:"json",
		async: false,
		success:function(json){
			res =  json;
		}
	});
	return res;
}

/**
 * 提交（上报人员待办）
 */
function commitReportNotice(){
	var count = 0;
	$("#form3").bootstrapValidator("addField", "unitName2", {  
		validators: {  
			notEmpty : {
				message : '选择处室不能为空！'
			} 
		}  
	});
	$("#form3").bootstrapValidator("removeField", "unitName");
	var bootstrapValidator3 = $("#form3").data('bootstrapValidator');
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	bootstrapValidator.enableFieldValidators('passScore', true);
	bootstrapValidator.enableFieldValidators('full', true);
	//手动触发验证
	bootstrapValidator.validate();
	bootstrapValidator3.validate();
	if(bootstrapValidator.isValid() && bootstrapValidator3.isValid()){
		//提示是否确定要提交，不能再更改
		layer.alert('确定要提交吗？点击确定按钮后将不能再修改考试人员范围和试卷信息！',
				{icon:0,title:'警告'},
				function(){
					//是:上报考试人员
					var par = {
							"deptNames":$('#unitName2').val(),//处室名称
							"deptIds":$('#unitId2').val(),//处室id
							"roleNo":"D221,C221",//上报员角色序号
					};
					var json = hasReportUser(par);
					//判断是否有上报员
					if(json.flag=="1"){
	                    layer.close(layer.index);//再执行关闭
						if(count==0){
							count = count +1;
							var res = saveTestInfo();
							if(res){
								var user = json.data;
								var index = 1;
								//发送待办
								var params = {
										"user":JSON.stringify(user),
										"id":$("#id").val(),//考试id
										"subject":$("#name").val(),//消息标题
										"content":"",//消息内容
										"messageURL":"",//消息的URL
										"daibanURL":"/modules/zhbg/xxkh/testManage/reportPersonsEditForm.html",//待办url
										"opName":"上报考试人员通知"//操作类型
								};
								$.ajax({
									url:"/zhbg/xxkh/testmanage/sendWaitNoflow",
									data:params,
									dataType:"json",
									success:function(json){
										if(json.flag=="1"){
											//刷新列表
											layer.close(index);
											parent.TableInit.refTable('right_table');
											MyLayer.closeOpen();
											window.parent.layer.msg('提交成功！',{icon:1});
							  			}else{
							  				layer.msg("提交失败！", {icon: 2});
							  			}
									},
									beforeSend:function(xhr){
										//加载动画
									index = layer.load(1,{shade: [0.5, '#393D49'],content: '请稍候',success: function(layero){
										layero.find('.layui-layer-content').css({'padding-top':'50px','font-size':'16px','color':'#000000','width':'200px'});
									} }); 
									}
									
								});
							}
						}else{
							layer.msg("已经提交，不可重复提交！", {icon: 0});
						}
					}else{
						var names = json.data;
						console.log(json.data);
						layer.alert('提交失败！'+names+'等处室没有配置考试人员上报员，请联系系统管理员进行配置后再试！',
								{icon:0,title:'警告'});
					}
		});
	}
}


/**
 * 选择试卷
 */
function selectPaper(){
	if($("#id").val()=="" ||$("#id").val()==null ){
		layer.msg("请先保存考试基本信息！", {
			icon : 0
		});
	}else{
		MyLayer.layerOpenUrl({
			url:'/modules/zhbg/xxkh/testManage/ifram.html?treeType='+type+'&id='+$("#id").val()+'&resId='+$("#resId").val(),
			title:'选择试卷',
			width:'98%',
			height:'90%',
			cancel:function(){
				//关闭页面后获取试卷列表
				getPapers();
			}
		});
	}
}

/**
 * 选择试卷后渲染试卷列表
 */
function getPapers(){
	var res = false;
	$("#files").html('');
	 res = MyPaperUpload.init({
		fileListId:"files",
		viewUrl:"/zhbg/xxkh/testmanage/getPapers?rdm="+Math.random(),
		viewParams:{testId:$("#id").val()},
		editOrView:"EDIT",
		isChange:"YES"
	});
	return res;
}

/**
 * 获取选择考试人员的参数(处室、职务、职级等)
 */
function getPar(){
	var params = {};
	params.deptId = $('#unitId').val();
	var position = "";
    var zw = $('#position input[name="dutyIds"]');
    $.each(zw,function(index,value){
    	if($(this).is(":checked")){
    		position += $(this).val();
    		position += ",";
    	}
    });
    position = position.substr(0,position.length-1);
    $("#positions").val(position);
    params.position = position;
    var positionLevel = "";
    var zj = $('#positionLevel input[name="levelIds"]');
    $.each(zj,function(index,value){
    	if($(this).is(":checked")){
    		positionLevel += $(this).val();
			positionLevel += ",";
    	}
    });
    positionLevel = positionLevel.substr(0,positionLevel.length-1);
    $("#positionLevels").val(positionLevel);
    params.positionLevel = positionLevel;
    console.log(params.positionLevel+params.position);
    return params;
}
/**
 * 职务和职级数据渲染
 */
function getposition(){
	var res =false;
	$.ajax({
		type: "get",
		url:"/zhbg/xxkh/testmanage/getPosition",
		async:false,
		success:function(json){
			if (json.flag == '1') {
				//职务数据
				var data1 = json.data;
				//职级数据
				var data2 = json.data2;
				//创建职务
				/*$('#position').append('<label  class="checkbox-inline"><input id="inlineCheckboxAll" type="checkbox" ">全选</label>');
				for(var i in data1){
					$('#position').append('<label  class="checkbox-inline"><input name="dutyIds" type="checkbox" value="'+data1[i].occupation_level+'">'+data1[i].occupation_name+'</label>');
				}*/
				//创建职级
				$('#positionLevel').append('<label  class="checkbox-inline"><input id="inlineCheckboxAll2" type="checkbox" ">全选</label>');
				for(var i in data2){
					$('#positionLevel').append('<label class="checkbox-inline"><input type="checkbox"  name="levelIds" value="'+data2[i].occupation_level+'">'+data2[i].occupation_name+'</label>');
				}
				 //点击全选改变所有复选框状态
				/*$("#inlineCheckboxAll").click(function(){
					var state = $("#inlineCheckboxAll").prop("checked");
					$("input[name='dutyIds']").prop("checked",state);
				});*/
				//给每一个部门复选框添加点击事件，改变全选状态
				/*$("input[name='dutyIds']").click(
							function(){
				   var flag= true;
				   $("input[name='dutyIds']").each(function(){
					   flag=flag&$(this).prop("checked");
				   });
				   $("#inlineCheckboxAll").prop("checked",flag);
				});*/
				//点击全选改变所有复选框状态
				$("#inlineCheckboxAll2").click(function(){
					var state = $("#inlineCheckboxAll2").prop("checked");
					$("input[name='levelIds']").prop("checked",state);
				});
				//给每一个部门复选框添加点击事件，改变全选状态
				$("input[name='levelIds']").click(
							function(){
				   var flag= true;
				   $("input[name='levelIds']").each(function(){
					   flag=flag&$(this).prop("checked");
				   });
				   $("#inlineCheckboxAll2").prop("checked",flag);
				});
				res = true;
			}else {
				
			}
		},
		error:function(){
		}
	});
	return res;
}
var	index ;
/**
 * 发布考试信息（发布时关联人员与试卷的关系）
 */
function commitForm(){
    	//修改状态,先保存
    	if($("#id").val()!=""&&$("#id").val()!=null){
    		var bootstrapValidator = $("#form").data('bootstrapValidator');
    		var bootstrapValidator3 = $("#form3").data('bootstrapValidator');
    		bootstrapValidator.enableFieldValidators('passScore', true);
    		bootstrapValidator.enableFieldValidators('full', true);
    	    //手动触发验证
    	    bootstrapValidator.validate();
    	    bootstrapValidator3.validate();
    	    if(bootstrapValidator.isValid()&& bootstrapValidator3.isValid()){
    	    		if($("#inlineRadio2").is(":checked")){//需要上报考试人员
    	    			//判断是否已提交
    	    			if($("#state").val()=="1"){
    	    				//已提交：校验是否有未上报的处室
    	    				if($("#noReport").val()==""){
    	    					//没有未上报的处室则发布
    	    					index = layer.load(1,{shade: [0.5, '#393D49'],content: '请稍候',success: function(layero){
    	        					layero.find('.layui-layer-content').css({'padding-top':'50px','font-size':'16px','color':'#000000','width':'200px'});
    	        					setTimeout("aa('YES')",1000)
    	        				} });
    	    					
    	    				}else{
    	    					layer.alert('发布失败！'+$("#noReport").val()+'，未上报考试人员，不能发布考试通知!',
    	    							{icon:0,title:'警告'});
    	    				}
    	    			}else{
    	    				layer.alert('发布失败！请先提交需要上报考试人员的处室，上报人员后再发布！',
    	    						{icon:0,title:'警告'});
    	    			}
    	    		}else if($("#inlineRadio1").is(":checked")){//不需要上报考试人员
    	        		if(chushi.length ==0){
    	        			index = layer.load(1,{shade: [0.5, '#393D49'],content: '请稍候',success: function(layero){
    	        				layero.find('.layui-layer-content').css({'padding-top':'50px','font-size':'16px','color':'#000000','width':'200px'});
    	        				setTimeout("aa('NO')",1000)
    	        			} });
    	        			
    	        		}else{
    	        			var noPersonChushi = chushi.join(",");
    	        			layer.alert(noPersonChushi+'，没有符合条件的人员，请删除！',{icon:0,title:'警告'});
    	        		}
    	    		}
    	    }else{
    	    	layer.close(index);
    	    }
    	}else{
    		//未保存考试信息
    		layer.msg('请先保存考试信息！',{icon:0});
    	}
}
/**
 * 保存数据并发布
 */
function aa(data){
	var res1 = saveTestInfo();
	var res2;
	if(data=="NO"){
		if($('#right_table').find('tr.readOnly').length==0){
			res2 = saveTestPerson("/zhbg/xxkh/testmanage/saveTestPerson");
		}else{
			var data;
			data = $("#right_table").bootstrapTable("getData")
			if(data.length>0){
				res2 = updateTestPerson();
			}else{
				res2 = saveTestPerson("/zhbg/xxkh/testmanage/saveTestPerson");
			}
		} 
	}else{
		res2 = updateTestPerson();
	} 
	var par = getTestInfo();
	par.state = "2";
	if(res1&&res2){
		var par = getTestInfo();
		par.state = "2";
		$.ajax({
			url:"/zhbg/xxkh/testmanage/updateState",
			data:par,
			dataType:"json",
			success:function(json){
				if(json.flag=="1"){
					//刷新列表
					layer.close(index);
					parent.TableInit.refTable('right_table');
					MyLayer.closeOpen();
					window.parent.layer.msg('发布成功！',{icon:1});
				}else{
					layer.msg("发布失败！", {icon: 2});
				}
			}
		});
	}
}
/**
 * 获取考试信息参数
 */
function getTestInfo(){
	//获取职务职级信息
	var dutyPar = getPar();
	var params = {
			"id":$("#id").val(),//考试id
			"name":$("#name").val(),//考试名称
			"type":type,//考试大类
			"nodeId":$("#nodeId").val(),//所属小类的唯一标识
			"answerTime":$("#answerTime").val(),//答题时长
			"difficultyLevel":$("#difficultyLevel").val(),//考试难易程度1：简单；2：一般；3：困难
			"fullScore":$("#fullScore").val(),//满分分数
			"passScore":$("#passScore").val(),//及格分数
			"isShowAnswer":$("input[name='isShowAnswer']:checked").val(),//考试后显示答案1：是
			"testNotice":$("#testNotice").val(),//考试须知
			"testToDepartment":$("input[name='testToDepartment']:checked").val(),//是否上报考试人员1：是
			"isMoreChance":"", //是否多次考试1：是
			"testChuShiNames":"",//考试处室名称
			"testChuShiIds":"",//考试处室id
			"requirement":"",//人员要求
			"dutyIds":"",//职务
			"levelIds":"",//职级
			"markStatus":"0",//评卷状态0：未评卷
			"state":"0",//考试状态0:草稿1：已提交2：发布
			"isMoreChance":$("input[name='isMoreChance']:checked").val()//是否多次参加考试
			};
	if($("input[name='testToDepartment']:checked").val()=="1"){
		params.testChuShiNames = $("#unitName2").val(); 
		params.testChuShiIds = $("#unitId2").val(); 
		params.requirement = $("#requirement").val();
	}else if($("input[name='testToDepartment']:checked").val()=="0"){
		params.testChuShiNames = $("#unitName").val();
		params.testChuShiIds = $("#unitId").val();
		params.dutyIds  = dutyPar.position;
		params.levelIds = dutyPar.positionLevel;
	}
	//alert(JSON.stringify(params));
	return params;
}

//打开查看考试人员详情
function openTestPerson(id){
	var url = "";
	if($("input[name='testToDepartment']:checked").val()=="1"){
		url = '/modules/zhbg/xxkh/testManage/ksglPerson.html?chushiId='+id+'&testId='+$('#id').val()+"&resId="+$("#resId").val();
	}else if($("input[name='testToDepartment']:checked").val()=="0"){
		url = '/modules/zhbg/xxkh/testManage/ksglDelPerson.html?deptId='+id+'&testId='+$('#id').val()+"&resId="+$("#resId").val();
	}
	MyLayer.layerOpenUrl({
			url:url,
			title:'人员详情',
			width:'90%',
			height:'90%',
			cancel:function(){
				//刷新列表(从关联表查)
				refreshTable();
			}
	});
}

/**
 * 不上报考试人员
 * 选择处室（部门考试与其他三个打开的部门树不一样）
 */
function selectChushi(){
	var treeType = $("#type").val();
	if (treeType.indexOf("dept") != -1) {
		openSelectZtree({'id':'unitId','name':'unitName','type':'2','asyn':false,'level':1,'deptId':$("#juId").val(),'url':'/system/component/tree/deptTree'})
	}else{
		openSelectZtree({'id':'unitId','name':'unitName','type':'2','asyn':false,'level':2,'url':'/system/component/tree/deptTree'})
	}
}

/**
 * 上报考试人员
 * 选择处室（部门考试与其他三个打开的部门树不一样）
 */
function selectChushi2(){
	var treeType = $("#type").val();
	if (treeType.indexOf("dept") != -1) {
		openSelectZtree({'id':'unitId2','name':'unitName2','type':'2','asyn':false,'level':1,'deptId':$("#juId").val(),'url':'/system/component/tree/deptTree'})
	}else{
		openSelectZtree({'id':'unitId2','name':'unitName2','type':'2','asyn':false,'level':2,'url':'/system/component/tree/deptTree'})
	}
}

/**
 * 重置按钮清空数据
 */
function cleanData(){
	//删除考试人员
	/*$.ajax({
		url:"/zhbg/xxkh/testmanage/deleteUserPaper?rdm="+Math.random(),
		data:{testId:$("#id").val()},
		dataType:"json",
		success:function(json){
			if(json.flag=="1"){
				$("#right_table").bootstrapTable("destroy");
				$("#selectAre input[type='checkbox']").prop("checked",false);
				$("#selectAre input").val("").change();
			}else{
				
			}
		}
	});*/
	
	$("#right_table").bootstrapTable("destroy");
	$("#selectAre input[type='checkbox']").prop("checked",false);
	$("#selectAre input[name='unitName']").val("").change();
	$("#selectAre input[name='unitId']").val("");
}

/**
 * 修改考试人员信息（发布时保存不上报人员）
 */
function updateTestPerson(){
	var res = false;
	var par = getPar();
	var params = {
			id:$("#id").val(),
			name:$("#testName").val()
			};
		$.ajax({
			url:"/zhbg/xxkh/testmanage/updateTestPerson",
			data:params,
			dataType:"json",
			async: false,
			success:function(json){
				if (json.flag == '1') {
					res = true;
				}else {
					layer.close(index);
					layer.msg(json.msg+'选择考试人员后再发布！',{icon:2});
				}
			},
			error:function(){
			}
		});
	return res;
}





