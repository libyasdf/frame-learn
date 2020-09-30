
/**
*封装临时存储用于html之间传递参数
*/
 var TempCache = {
     cache:function(value){  
         sessionStorage.setItem("EasyWayTempCache",value);  
     },  
     getCache:function(){  
         return sessionStorage.getItem("EasyWayTempCache");  
     },  
     setItem:function(key,value){ 
     	 //防止iPhone/iPad上有时设置setItem()时会出现诡异的QUOTA_EXCEEDED_ERR错误，固先remove
     	 sessionStorage.removeItem(key); 
         sessionStorage.setItem(key,value);  
     },  
     getItem:function(key){  
         return sessionStorage.getItem(key);  
     },  
     removeItem:function(key){  
         return sessionStorage.removeItem(key);  
     },
     clear:function(){
     	 return sessionStorage.clear(); 
     }
 };  
 
 /**
*持久化存储用于存储部分数据
*/
 var PermanentCache = {  
     cache:function(value){  
         localStorage.setItem("EasyWayPermanentCache",value);  
     },  
     getCache:function(){  
         return localStorage.getItem("EasyWayPermanentCache");  
     },  
     setItem:function(key,value){ 
         localStorage.setItem(key,value);  
     },  
     getItem:function(key){  
         return localStorage.getItem(key);  
     },  
     removeItem:function(key){  
         return localStorage.removeItem(key);  
     },
     clear:function(){
     	 return localStorage.clear(); 
     }
 }; 
 
 /**AIP
  * 用[layer]打开流程页面
  * <H2>提交工作流</H2>
  * @param oper:NEW or EDIT(新建or修改)
  * @param ele 按钮DOM对象
  * @param title 标题
  * @param idea 意见
  * @param pkValue 业务主键ID
  * @param attr 
  * @param attr1
  * @param workitemid 待办工作项ID（workitemid，一般与workflowid一致）
  * @param workflowid 流程类型ID
  */
 function saveFormAndToFlow(oper,ele,title,idea,pkValue,attr,attr1,workitemid,workflowid,extendattr,isMulti) {
	 submitToFlow(oper,ele,title,idea,pkValue,attr,attr1,workitemid,workflowid,extendattr,isMulti);
 }


 /**用[layer]打开流程页面
  * <H2>提交工作流</H2>
  * @param oper:NEW or EDIT(新建or修改)
  * @param ele 按钮DOM对象
  * @param title 标题
  * @param idea 意见
  * @param pkValue 业务主键ID
  * @param attr
  * @param attr1
  * @param workitemid 待办工作项ID（workitemid，一般与workflowid一致）
  * @param workflowid 流程类型ID
  * @param extendattr 流程扩展参数，格式：“sex=1,length=3,...”
  * @param isMulti 节点是否可选择多人：0单选；1可多选（默认）
  */
 function submitToFlow(oper,ele,title,idea,pkValue,attr,attr1,workitemid,workflowid,extendattr,isMulti) {
	 $("#referBtn").attr("disabled","disabled");
	 $(ele).attr('disabled', 'disabled');
	 var subId = getcookie("sysid");
	 var deptid = getcookie("deptid");
	 var userid = getcookie("userid");
	 var attr = $("#"+attr).val();
	 var attr1 = $("#"+attr1).val();
	 var formData={};
	 formData["idea"]=idea;
	 formData["pkValue"]=pkValue;
	 idVal = pkValue;
     formData["recordid"]=pkValue;
	 formData["subId"]=subId;
	 formData["sysid"]=subId;
	 formData["filetypeid"]=workflowid;
	 formData["workflowid"]=workflowid;
	 formData["workitemid"]=workitemid;
	 formData["deptid"]=deptid;
	 formData["userid"]=userid;
	 formData["title"]=title;
	 formData["attr"]=attr;
	 formData["attr1"]=attr1;
	 formData["extendattr"]=extendattr;
	 var isMulti = isMulti || "1";	//0单选；1可多选（默认）
	 TempCache.setItem("formData",JSON.stringify(formData));
	 TempCache.setItem("isMulti",isMulti);
	 if(isAip()){
         hideAip();
	 }
	 layer.confirm('确定要提交吗？', function(index){
		 Loading.open();
         layer.close(index);
		 $.ajax({
			url : '/flowService/toFlow',
			type : "post",
			data : {
				"oper" : oper,
				"pkValue" : pkValue,
				"sysid":subId,
				"filetypeid" : workflowid,
				"workflowid" : workflowid,
				"workitemid" : workitemid,
				"deptid":deptid,
				"userid" : userid,
				"title":title,
				"idea":idea,
				"attr":attr,
				"attr1":attr1,
				"remindtype":"1",
				"extendattr":extendattr
			},
			async : true,
			cache : true,
			dataType : "json",
			success : function(data) {
				Loading.clear();
				if (data["result"] == "0") {
					layer.alert("操作失败：" + data["messages"],{icon:0,title:'警告'});
				} else {
					// 主键值渲染到前台
					if ("pkWidget" in data) {
						rendPk(data["pkWidget"]);
					}
					if(data.result=="failed"){
						layer.msg("提交失败，请先检查之后，重新提交！", {icon: 5});
					}else{
						data['idea'] = idea;
						branchFun(data);
					}
				}
			},
			error : function(error) {
				console.log(error);
			}
		});
	}, function(){
		$("#referBtn").removeAttr("disabled");
		if(isAip()){
            showAip();
		}
	});
}

/**
 * 获取启动节点信息（包括按钮，及节点提示信息等）
 * @param callback自定义数据处理方法
 * @returns 返回数据格式参照文件/mock/startWflevel.json
 */
function getStartWf(callback){
	getStartButton();
	getStartLevelMemo(callback);
}

 /**
  * 调用工作流配置的按钮
  * 渲染启动节点拥有的按钮
  * @returns
  */
function getStartButton(){
	var oper = "NEW";
 	var startdatas = {
     	workflowid:$("#workflowid").val(),
     	filetypeid:$("#filetypeid").val(),
     	oper:oper
	};
	DisplayData.playWorkFlow("/workflow/getStartWfButton",startdatas,oper);
}

/**
 * 获取启动节点操作提示的方法
 * @param callback自定义数据处理方法
 * @returns 返回数据格式参照文件/mock/startWflevel.json
 */
function getStartLevelMemo(callback){
	$.ajax({
		url:"/flowService/getStartWflevel",
		type:"get",
		dataType:"json",
		data:{
			fileTypeId:$("#filetypeid").val(),
			workflowId:$("#workflowid").val(),
			deptId:getcookie("deptid"),
			userId:getcookie("userid")
		},
		success: function(json){
			console.log(json);
			if(json.flag == "1"){
				var data = json.data;
				if($(data).size() > 1){
					var html = "";
					$.each(data, function(i,n){
						html += i + 1 + ". " + n.wflevename + "：" + n.memo;
						html += "<br>";
					})
					$("#flowMemo").html(html);
				}else if($(data).size() == 1){
					$("#flowMemo").html(data[0].memo);
				}
				//自定义数据处理回调函数
				if(callback){
					callback(json);
				}
			}
		},
		error:function(){}
	});
}

function toFlow() {
	var formData = JSON.parse(TempCache.getItem("formData"));
	$.ajax({
		url : '/flowService/toFlow',
		type : "post",
		data: { 'jsonPar' : encodeURI(TempCache.getItem("formData")) },
		async : true,
		cache : true,
		dataType : "json",
		beforeSend:function(){
			Loading.open();
		},
		success : function(data) {
			Loading.clear();
			data["idea"] = formData["idea"];
			var recordId = data['recordid'];
			if(recordId == ""){
				data['recordid'] = idVal;
			}
			branchFun(data);
		},
		error : function(error) {
			console.log(error);
		}
	});
}

 function branchFun(data) {
	if (typeof (data["flag"]) != "undefined") {
		var flag = data["flag"];
		switch (flag) {
		case 'selectoperate':
			toStartWf(data);
			break;
		case 'selectdept':
			data.routeType = 'selectdept';
			getUserAndDept(data);
			break;
		case 'flowinstance':
			data.routeType = 'flowinstance';
			getUserAndDept(data);
			break;
		default:
			showMassage(data);
			break;
		}
	}
}

/**
 * 提交流程时，选择启动节点
 * @param data
 */
function toStartWf(data) {
	if(isAip()){
        hideAip();
	}
	var contentUrl = '/product/workflow/html/html/select_startwf.html';
	//var param = "?node="+data.node+"&flag="+data.flag;
	layer.open({
		id:"selectoperate",
		type:2,
		title:'请选择启动角色',
		content:contentUrl,
		area: ['770px', '300px'],
        btn:["确认"],
        success:function(){
            // 通过iframe 操作
            var iframeId = $("#selectoperate").find('iframe').attr('id')
            var obj = $(window.frames[""+iframeId+""].document)
            $(obj).find('#data').text(JSON.stringify(data));
            document.getElementById(iframeId).contentWindow.fuzhi();
            //document.getElementById(iframeId).contentWindow.initIfram();
        },
		yes:function(index, layero){
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var wfleveid = iframeWin.getStartWf();
			if(wfleveid){
				toFlow();
				layer.close(index); //如果设定了yes回调，需进行手工关闭
			}else{
				layer.alert("请选择启动角色 ！",{icon:0,title:'警告'});
			}
		},
		cancel:function(){
			if(isAip()){
                showAip();
			}
		}
	});
}

 	/**
	 * 打开提交页面，选择办理人
	 * @param data
	 */
	function getUserAndDept(data) {
		if(isAip()){
            hideAip();
		}
		var contentUrl = '/product/workflow/html/html/select_user_dept.html';
		//var param = "?flag="+data.flag+"&routeType="+data.routeType+"&idea="+data.idea;
		layer.open({
			id:"selectdept",
			type:2,
			title:'请选择办理人',
			content:contentUrl,
			area: ['770px', '490px'],
			btn:["确认"],
			success:function(){
				// 通过iframe 操作
				var iframeId = $("#selectdept").find('iframe').attr('id')
				var obj = $(window.frames[""+iframeId+""].document)

				$(obj).find('#data').text(JSON.stringify(data));

		        document.getElementById(iframeId).contentWindow.fuzhi();
		        document.getElementById(iframeId).contentWindow.initIfram();
			},
			yes:function(index, layero){
				var iframeWin = window[layero.find('iframe')[0]['name']];
				iframeWin.fuzhi();
				iframeWin.toCreateWrite();
				var userSize = iframeWin.$(".choose_user_list").children().length;
				if(userSize != 0){
					if(TempCache.getItem("isMulti") == "0" && userSize > 1){		//单选
						layer.alert("只能选择一个办理人 ！",{icon:0,title:'警告'});
						return false;
					}
					toFlow();
				}else{
					layer.alert("请选择办理人 ！",{icon:0,title:'警告'});
					return false;
				}
				/*if(isAip()){
                    showAip();
				}*/
				layer.close(index); //如果设定了yes回调，需进行手工关闭
			},
			cancel:function(){
				if(isAip()){
                    showAip();
				}
			}
		});
	}

	/**
	 * 显示发送后的提示信息
	 * @param data
	 */
	function showMassage(data){
		if(isAip()){
            hideAip();
		}
		var contentUrl ;
		if(data.flag == '1'){
			contentUrl = '/product/workflow/html/html/correct_prompt.html';
		}else{
			contentUrl = '/product/workflow/html/html/error_prompt.html';
		}
		layer.open({
			id:"result",
			type:2,
			title:'提示信息',
			content:contentUrl,
			area: ['500px', '400px'],
			btn: ['关闭'],
			cancel: function(index, layero){	//右上角关闭按钮的回调
				if(isAip()){
                    showAip();
				}
				if(data.flag == '1'){
					if(parent.parent.refreshPage){
                        parent.parent.refreshPage();
					}
					layer.close(index); //如果设定了yes回调，需进行手工关闭
					//关闭当前层
					var indexSelf = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
					parent.layer.close(indexSelf); //再执行关闭
                    var indexSelf2 = parent.parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.parent.layer.close(indexSelf2); //再执行关闭
				}else{
					$("#referBtn").removeAttr("disabled");
					layer.close(index); //如果设定了yes回调，需进行手工关闭
				}
			},
			success: function(layero, index){
				updateData(data);
				// 通过iframe 操作
				var iframeId = $("#result").find('iframe').attr('id')
				var obj = $(window.frames[""+iframeId+""].document)

				$(obj).find('#data').text(JSON.stringify(data));

		        document.getElementById(iframeId).contentWindow.initIfram();
			},
			yes:function(index, layero){	//关闭按钮的回调
				if(data.flag == '1'){
                    if(parent.parent.refreshPage){
                        parent.parent.refreshPage();
                    }
					if(isAip()){
                        showAip();
					}
					layer.close(index); //如果设定了yes回调，需进行手工关闭
					//关闭当前层
					var indexSelf = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
					parent.layer.close(indexSelf); //再执行关闭
                    var indexSelf2 = parent.parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.parent.layer.close(indexSelf2); //再执行关闭
				}else{
					$("#referBtn").removeAttr("disabled");
					if(isAip()){
                        showAip();
					}
					layer.close(index); //如果设定了yes回调，需进行手工关闭
				}
			}
		});
	}

/**
 * AIP用layer方式打开窗口
 * 打开流程记录、流程图、意见信息
 * @param type 1：流程记录；2:流程图，3:意见信息
 */
	function branchToCourse(type) {
		openLayer(type);
    }
	/**用layer方式打开窗口
	 * 打开流程记录、流程图、意见信息<br>
	 * @param type 1：流程记录；2：流程图；3：意见信息
	*/
	function openLayer(type){
		if (typeof (type) != "undefined") {
			var pageUrl ="";
			 var pageTitle = "";
			 switch (type) {
			 case '1':
				 pageUrl = "/product/workflow/html/html/flowCourse.html";
				 pageTitle = "流程记录";
				 break;
			 case '2':
				 pageUrl = "/product/workflow/html/html/flowStatus.html";
				 //pageUrl = Config.flowstatus_url + "/product/workflow/workFlowStatus/flowStatus.html";
				 pageTitle = "流程状态图";
				 break;
			 case '3':
				 pageUrl = "/product/workflow/html/html/showIdea.html";
				 pageTitle = "意见信息";
				 break;
			 default:
				 break;
			 }
			 if(pageUrl!=""){
				 if(isAip()){
                     hideAip();
				 }
				 data = [
						"recordid=" + $("#id").val(),
						"sysid=" + getcookie("sysid"),
						"filetypeid=" + $("#filetypeid").val(),
						"workflowid=" + $("#workflowid").val(),
						"workitemid=" + $("#workitemid").val(),
						"deptid=" + getcookie("deptid"),
						"userid=" + getcookie("userid")
					],
				statusData = [
						"sysId=" + Config.sysId,	//流程图使用系统ID
						"recordId=" + $("#id").val(),	//流程图使用业务ID
						"workflowId=" + $("#workflowid").val(),	//流程图使用流程ID
						"fileTypeId=" + $("#filetypeid").val()	//流程图使用流程类型ID
						//,"workflowId=1806221156168080192168901340000",
						//"fileTypeId=1806221156168080192168901340000"
				    ];
				var content = pageUrl + "?" + data.join("&");
				//var content = type == '2'?pageUrl + "?" + statusData.join("&"):pageUrl + "?" + data.join("&");
				layer.open({
					id:"workFlowInfo",
					type:2,
					title:pageTitle,
					content : content,
					area: ['80%', '80%'],
					cancel : function(){
						if(isAip()){
                            $("object").parent("div").parent("div").css("display","block");
						}
					}
				});
			 }
		}
	}
	
	/**
	 * 收回重办
	 */
	function takeBack(){
		if(isAip()){
			hideAip();
		}
		var workitemid = $("#workitemid").val();
        layer.confirm('确定要收回重办吗？', function(index){
            Loading.open();
            layer.close(index);
			if(workitemid){
				$.ajax({
					async : false,
					cache : false,
					url : '/flowService/takeBack',
					type : "post",
					data : {
						"workitemid":workitemid,
						"isBackIdea":"1"
					},
					dataType : "json",
					success : function(data) {
						Loading.clear();
						if(data.res){
							var datas = {
									type:"1",
									oper:"VIEW",
									workitemid:workitemid
							};
							$("#takeBack").css("display","none");
							DisplayData.playWorkFlow("/workflow/getYiBanData",datas,"VIEW");
							layer.msg("收回成功 ！",{icon:1});
							parent.refreshPage();	//刷新列表
							setTimeout(function(){
								$.ajax({
									type: "POST",
									url: "/workflow/getFlowWritUrl",
									data:{
										recordId:$("#id").val(),
										workFlowId:$("#workflowid").val()
									},
									async: false,
									success: function(data) {
										if(data){
											parent.MyLayer.layerOpenUrl({
												url: data.url +'?id=' + $("#id").val() + '&workItemId='+data.workitemId+'&workFlowId='+$("#workFlowId").val()+'&oper=EDIT',
												title: "待办详情"
											});
										}
									}
								});
								//关闭当前层
								var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
								parent.layer.close(index); //再执行关闭
							},2000);
						}else{
							layer.msg("收回失败 ！",{icon:2});
						}
					},
					error : function(data) {
                        Loading.clear();
						layer.msg("收回失败 ！",{icon:2});
					}
				});
			}else{
                Loading.clear();
				layer.msg("收回失败 ！",{icon:2});
			}
            if(isAip()){
                showAip();
            }
        }, function(){
            if(isAip()){
                showAip();
            }
        });
	}
	
	/**
	 * 撤办
	 * @returns
	 */
	function removeFlow(){
		if(isAip()){
            hideAip();
		}
		var workitemid = $("#workitemid").val();
        layer.confirm('确定要撤办吗？', function(index){
            Loading.open();
            layer.close(index);
			if(workitemid){
				$.ajax({
					async : false,
					cache : false,
					url : '/flowService/removeFlow',
					type : "post",
					data : {
						"workitemid":workitemid
					},
					dataType : "json",
					success : function(data) {
                        Loading.clear();
						if(data.res){
							var datas = {
									type:"1",
									oper:"VIEW",
									workitemid:workitemid
							};
							$("#remove").css("display","none");
							$("#takeBack").css("display","none");
							DisplayData.playWorkFlow("/workflow/getYiBanData",datas,"VIEW");
							updateData({removeflag:1});//修改业务表状态
							layer.msg("撤办成功！",{icon:1});
						}else{
							layer.msg("撤办失败！"+data.message,{icon:2});
						}
						parent.refreshPage();	//刷新列表
					},
					error : function(data) {
                        Loading.clear();
						layer.msg("撤办失败！"+data.message,{icon:2});
					}
				});
			}else{
                Loading.clear();
				layer.msg("撤办失败！",{icon:2});
			}
            if(isAip()){
                // setTimeout(function () {
                    showAip();
                // },1500);
            }
        }, function(){
            if(isAip()){
				// setTimeout(function () {
                    showAip();
                // },1500);
            }
        });
	}
	
	/**
	 * 重新启用
	 * @returns
	 */
	function resumeFlow(){
		if(isAip()){
            hideAip();
		}
		var workitemid = $("#workitemid").val();
        layer.confirm('确定要重新启用吗？', function(index){
            Loading.open();
            layer.close(index);
			if(workitemid){
				$.ajax({
					async : false,
					cache : false,
					url : '/flowService/resumeFlow',
					type : "post",
					data : {
						"workitemid":workitemid
					},
					dataType : "json",
					success : function(data) {
                        Loading.clear();
						if(data.res){
							var datas = {
									type:"1",
									oper:"VIEW",
									workitemid:workitemid
							};
							$("#resumeFlow").css("display","none");
							DisplayData.playWorkFlow("/workflow/getYiBanData",datas,"VIEW");
							updateData({resumeflag:1});//修改业务表状态
							layer.msg("重新启用成功 ！",{icon:1});
						}else{
							layer.msg("重新启用失败 ！",{icon:2});
						}
						parent.refreshPage();	//刷新列表
					},
					error : function(data) {
                        Loading.clear();
						layer.msg("重新启用失败 ！",{icon:2});
					}
				});
			}else{
                Loading.clear();
				layer.msg("重新启用失败 ！",{icon:2});
			}
            if(isAip()){
                // setTimeout(function () {
                    showAip();
                // },1500);
            }
        }, function(){
            if(isAip()){
                // setTimeout(function () {
                    showAip();
                // },1500);
            }
        });
	}
	
	
	/**
	 * 增加会签
	 * @returns
	 */
	function getIncreaseSignature(){
		if(isAip()){
            hideAip();
		}
		layer.open({
			id:"insertSignature",
			type:2,
			title:'增加会签分支',
			content:"/product/workflow/html/addSignBranch.html?workitemid="+$("#workitemid").val(),
			area: ['650px', '300px'],
			btn: ['确定'],
			yes:function(index, layero){	//确定按钮的回调
				var iframeId = $("#insertSignature").find('iframe').attr('id')
				var result = document.getElementById(iframeId).contentWindow.addDelSubmit();
				setTimeout(function(){
					layer.close(index);
				},2000);
				if(!result){
					layer.msg("操作成功 ！",{icon:1});
				}else{
					layer.msg("操作失败 ！",{icon:2});
				}
				if(isAip()){
                    showAip();
				}
			},
			cancel : function(){
				if(isAip()){
                    showAip();
				}
			}
		});
	}
	
	
	/**
	 * 删除会签
	 * @returns
	 */
	function DeletingSignature(){
		if(isAip()){
            hideAip();
		}
		layer.open({
			id:"deleteSignature",
			type:2,
			title:'删除会签分支',
			content:"/product/workflow/html/DelSignBranch.html?workitemid="+$("#workitemid").val(),
			area: ['650px', '300px'],
			btn: ['确定'],
			yes:function(index, layero){	//确定按钮的回调
				var iframeId = $("#deleteSignature").find('iframe').attr('id')
				var result = document.getElementById(iframeId).contentWindow.addDelSubmit();
				setTimeout(function(){
					layer.close(index);
				},2000);
				if(!result){
					layer.msg("操作成功 ！",{icon:1});
				}else{
					layer.msg("操作失败 ！",{icon:2});
				}
				if(isAip()){
                    showAip();
				}
			},
			cancel : function(){
				if(isAip()){
                    showAip();
				}
			}
		}); 
	}
	
	/**
	 * 更新业务表subflag状态值
	 * @param data
	 */
	function updateData(data){//updateData
		console.log("workflow:",data);
		var subflag = "";
		if(data.flag==1&&data.endflag==1){//办结
			updateBusiData(Config.endflag);
            subflag = Config.endflag;
		}else if(data.flag==1&&data.endflag==undefined){//发送流程
			updateBusiData(Config.subflag);
            subflag = Config.subflag;
		}else if(data.removeflag==1){		//撤办
			updateBusiData(Config.removeflag);
            subflag = Config.removeflag;
		}else if(data.resumeflag==1){		//重新启用
			updateBusiData(Config.subflag);
            subflag = Config.subflag;
		}
		//办结成功
		if(data.res!=undefined && data.res!="" &&data.res == "ok"){
			if(data.subflag == Config.approvalflag){//审批通过
				updateBusiData(Config.approvalflag);
                subflag = Config.approvalflag;
			}else if(data.subflag == Config.noApprovalflag){//审批不通过
				updateBusiData(Config.noApprovalflag);
                subflag = Config.noApprovalflag;
			}
		}
        //修改流程状态表
        updateFlowSubflag({
            fileTypeId:data.filetypeid,
            workFlowId:data.workflowid,
            recordId:data.recordid,
			workItemId:$("#workitemid").val(),
            subflag:subflag
        });
	}
	
/**
 * 办结
 * @flag 流程终止方式,默认为0
 * @returns
 */
function completeFlow(requestUrl,subflag,flag){
	if(isAip()){
        hideAip();
	}
	var idea = $("#idea").val();
    var IsNotionShow =  document.getElementById("ideaArea").style.display;
    if(IsNotionShow == "block"){
        if("2"==  document.getElementById("fillmode").value){
            if(idea == "" || idea == null){
                layer.msg("请填写意见！", {icon: 0})
                if(isAip()){
                    showAip();
				}
                return false;
            }
        }
    }
	var confirmstr = "确定要办结吗";
    var msgConfirmstr = "已办结";
	if (subflag == Config.approvalflag) {
		confirmstr = "确定审批通过吗？";
        msgConfirmstr="审批已通过";
	} else if (subflag == Config.noApprovalflag) {
		confirmstr = "确定审批不通过吗？"
        msgConfirmstr="审批不通过";
	}
	if(!flag){
		flag = "0";
	}
	layer.confirm(confirmstr, function(index) {
		Loading.open();
        layer.close(index);
		var url = "";
		if(requestUrl){
			url = requestUrl;
		}else{
			url = "/flowService/finishFlow";
		}
		debugger;
        var datas = {"workItemId":$("#workitemid").val(),"msgConfirmstr":msgConfirmstr,"resId":$("#resId").val()};
        httpRequest("get","/notity/finishFlowSendMessage",datas,function (data){
            if('1'==data.flag){
                //parent.refreshPage("daiban");
                console.log("生成办结提醒消息成功！");
            }
        });
		$.ajax({
			async : false,
			cache : false,
			url :  url,
			type : "post",
			data : { 
				"workitemid":$("#workitemid").val(),
				"flag":flag,
				"recordid":$('#id').val(),
				"subflag":subflag,
				"idea":$('#idea').val()
			},
			dataType : "json",
			success : function(data) {
				Loading.clear();
				if(data.res == "ok"){
					if(data){
						$("#preserveBtn").remove();	//移除保存按钮
                        $("#save").remove();	//移除保存按钮
						$("#subflow").remove();		//移除发送按钮
						$("#approval").remove();	//移除通过按钮
						$("#noApproval").remove();		//移除不通过按钮
                        $("#gd").remove();		//移除办结按钮
					}
					updateData(data);
					parent.refreshPage();
					layer.msg("办结成功 ！",{icon:1});

					setTimeout(function(){
						//关闭当前层
						var indexSelf = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
						parent.layer.close(indexSelf); //再执行关闭
					},2000);
				}else{
					layer.msg(data.res,{icon:2});
				}
			},
			error : function(data) {
				Loading.clear();
				layer.msg("办结失败 ！",{icon:2});
                if(isAip()){
                    showAip();
                }
			}

		});
	},function(){
		if(isAip()){
            showAip();
		}
	});
}

/**
 * 跳点
 * @param workitemid
 */
function getJumpWflevePassed(){
	if(isAip()){
        hideAip();
	}
	var result = "";
	layer.open({
		id:"getJumpWflevePassed",
		type:2,
		title:'退回节点',
		content:"/product/workflow/html/getPassed.html?workitemid="+$("#workitemid").val(),
		area: ['450px', '450px'],
		btn: ['确定'],
		yes:function(index, layero){	//确定按钮的回调
			var iframeId = $("#deleteSignature").find('iframe').attr('id')
			var result = document.getElementById(iframeId).contentWindow.getwfleveId();
			result.filetypeid = $("#filetypeid").val();
			result.recordid = $("#id").val();
			var res;
			if(result.rolesId){
				Loading.open();
				getDataByAjax("/flowService/jumpToWfleve", result, function(data) {
					Loading.clear();
					if(data.res) {
						parent.refreshPage();
						layer.msg("退回成功！",{icon:2});
					} else {
						layer.msg("退回成功！",{icon:1});
					}
					res = data.res;
				});
				var loop = setInterval(function() {
					if(result) {
						Loading.clear();
						layer.msg("退回成功！",{icon:1});
						parent.refreshPage();
						clearInterval(loop);
						setTimeout(function(){
							layer.close(index);
						},1000);
						return true;
					}      
				}, 1000);
			}else{
				Loading.clear();
				layer.msg("请选择人员！",{icon:0});
			}
		},
		cancel : function(){
			if(isAip()){
                showAip();
			}
		},
	});
	if(isAip()){
        showAip();
	}
}

function getDataByAjax(url,data,callback){
	var options = {
		async : false,  
        cache : false,  
		type: "post",  
		url : url,
		data:data,   
		dataType : 'json'
	};
	var returnVal = {};
	$.when(
		$.ajax(options)
	).then(function(data){
		returnVal = data;
		if(callback){
			callback(data);
		}
	},function(data){
		returnVal = data;
	});
	
	return returnVal;
}

/**
 * 判断当前页面是否是AIP页面
 */
function isAip(){
	var isAip = false;
	//判断是否是AIP页面
	var obj = $("object[aip=true]").size();
	if(obj > 0){
		isAip = true;
	}
	console.log("是否是AIP：" + isAip);
	return isAip;
}

/**
 * 隐藏AIP控件
 */
function hideAip(){
	$("object").parent("div").parent("div").css("display","none");
}

/**
 * 显示AIP控件
 */
function showAip(){
    setTimeout(function () {
        $("object").parent("div").parent("div").css("display","block");
    },1500);
}

/**
 * 更新流程状态表
 * @param json
 */
function updateFlowSubflag(json){
	var param = {
		fileTypeId:$('#filetypeid').val(),
		workFlowId:$('#workflowid').val(),
		recordId:$('#id').val(),
		workItemId:"",	//当起草发送时，workItemId为空，需要传上面三个参数，其他情况workItemId不为空，可不传上面三个参数
        subflag:""
	}
    var _param = $.extend(param, json);
	if (_param.subflag){
        $.ajax({
            type:"POST",
            url:"/workflow/subflag/saveSubflag",
            data : _param,
            dataType:"json",
            success: function(res){
				console.log(res);
            },
            error:function(){}
        })
	}
}
