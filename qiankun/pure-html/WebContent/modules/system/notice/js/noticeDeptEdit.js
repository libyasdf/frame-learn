var fileTypeId = "a24cdd321f5448fba53503e2759c58c2";
var workFlowId = "a24cdd321f5448fba53503e2759c58c2";

$(function(){
	
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#oper").val(theRequest.oper);
	$("#noticeType").val(regVlaue(theRequest.noticeType));
    $("#filetypeid").val(fileTypeId);
    $("#workflowid").val(workFlowId);
    $("#workitemid").val(theRequest.workItemId);
    $("#opertation").val(theRequest.oper);
    debugger;

	if($("#oper").val() != "VIEW"){
		//参考：http://www.layui.com/laydate/
	    //日期范围
	    laydate.render({
	        elem: '#timeRange',
	        range: true,
	        done: function(value, datas){
 				$('#timeRange').val(value).change();
 			}
	    });
	    
	}

/*	if($("#oper").val() == "NEW" || $("#oper").val() == "EDIT"){
        var datas = {"resId":$("#resId").val()};
        httpRequest("get","/system/notice/queryNotice",datas,function (data){
        	debugger;
            if ('1' == data.flag) {
                if('1'== data.isFbfw){
                    $("#noticeBtn").show();
                }
                if('1' == data.fbButton){
                    $("#publishBtn").show();
                }
                if('1' == data.sendButton){
                    $("#sendButton").show();
                }
                if('1' == data.passButton){
                    $("#passButton").show();
                }
                if('1' == data.noPassButton){
                    $("#noPassButton").show();
                }
            }
        });
	}*/
	
	
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		//表单数据渲染
		var datas = {"noticeId":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/system/notice/view",datas,function (data){
			DisplayData.playData({data:data});
			//隐藏发布按钮
			var flag = data.data.subflag;
			if(flag == "1" || flag =="5" || flag =="6"){
				$("#sendFLow").css("display","none");
                $("#noticeBtn").css("display","none");
			}
			//起始日期
			if($("#oper").val() != "VIEW"){
			    if(data.data.startTime!=null && data.data.startTime !=""){
			        $("#timeRange").val(data.data.startTime+" - "+data.data.endTime);
                }
			}else{
                if(data.data.startTime!=null && data.data.startTime !=""){
                    $("#timeRange").text(data.data.startTime + " - " + data.data.endTime);
                }
				//是否需要反馈
				$("#isBack").text(data.data.isBack=="0"?"否":data.data.isBack=="1"?"是":"");
			}
			if($("#noticeWorkFlow").val()=="1"){
                //是否需要反馈
                $("#isBack").text(data.data.isBack=="0"?"否":data.data.isBack=="1"?"是":"");
                if(data.data.startTime!=null && data.data.startTime !=""){
                    $("#timeRange").text(data.data.startTime + " - " + data.data.endTime);
                }
            }
		});
        //流程相关（按钮、控件等）的渲染
        if($("#workitemid").val()!=""){
            if($("#opertation").val()=="EDIT"){
                var datas = {
                    workflowid:$("#workflowid").val(),
                    filetypeid:$("#filetypeid").val(),
                    workitemid:$("#workitemid").val(),
                    pkValue:$("#id").val(),
                    userid:getcookie("userid"),
                    deptid:getcookie("deptid"),
                    oper:$("#opertation").val(),
                    ideaArea:$("#ideaArea").val()
                };
                //流转中不可修给表单--开始
                //$('form').find('input,textarea,select').not('#idea').prop('readonly',true);
                //流转中不可修给表单--结束
                DisplayData.playWorkFlow("/flowService/getFlowData",datas,$("#opertation").val(),callback,
                    function returnData(data){
                        wfleveid = data.flowData.wfleveid;//节点id
                        if(wfleveid =="1550544406195" || wfleveid =="1550593447050"){
                            $("#saveForm").css("display","none");
                            $("#noticeBtn").show();
                            $("#editForm").show();
                            $("#sendFlow").show();
                        }
                    });
            }else{//oper='VIEW'
                var datas = {
                    type:"1",
                    oper:$("#opertation").val(),
                    workitemid:$("#workitemid").val()
                };
                DisplayData.playWorkFlow("/workflow/getYiBanData",datas,$("#opertation").val());
            }

        }else if($("#opertation").val()=="EDIT"){
            getStartWf();
        }
	}else{
		$("#creUserId").val(getcookie("userid"));
		$("#creUserName").val(getcookie("usernm"));
		$("#creDeptId").val(getcookie("deptid"));
		$("#creDeptName").val(getcookie("deptname"));
        getStartWf();
	}
	
	//初始化文件上传
	iniFileUpload();
})


/**
 * 部门级提交表单
 * @returns
 */
function commitFormDept(){
    var flag = saveForm1();
    if(flag){
        if(flag == "1"){
            layer.msg("保存成功！", {icon: 1});
        }else{
            layer.msg("保存失败！", {icon: 2});
        }
    }
}

/**
 * 提交表单
 * @returns
 */
function commitForm1(){
    var tempIdea = $("#idea").val();
    saveIdeaTemp($("#workitemid").val(),tempIdea);
    layer.msg("保存成功！", {icon: 1});
}


/**
 * 部门级保存
 */
function saveForm1(){
    var res = "";
    $("#noticeDeptId").val("1");
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
        Loading.open();
        $.ajax({
            type: "POST",
            url:"/system/notice/save",
            data:$("#form").serialize(),
            async: false,
            success:function(json){
                Loading.clear();
                res = json.flag;
                if (json.flag == '1') {
                    $("#id").val(json.data.id);
                    if($("#workItemId").val() !="" && $("#workItemId").val() !=null && $("#workItemId").val() !=undefined){

                    }else{
                        //刷新列表
                        if(parent.TableInit != undefined){
                            parent.TableInit.refTable('right_table1');
                        }else{
                            parent.location.reload();
                        }

                    }
                    //初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
                    iniFileUpload();
                    MyFileUpload.saveDocIdToAffix({docId:json.data.id,fileListId: "files,files2"});
                }
            },
            error:function(){
                Loading.clear();
                layer.msg("保存失败！", {icon: 2});
            }
        });
    }
    return res;
}

function iniFileUpload(){
	MyFileUpload.init({
		viewParams: {"tableId":$("#id").val(),"tableName":"sys_notice"},
		editOrView:$("#oper").val(),
        maxFileSize:10*1024*1024 //10M
    });
}


/**
 * 部门级打开权限选择窗口
 */
function authorityDept(){
    var flag = saveForm1();
    if(flag){
        if(flag == "1"){
            var id = $("#id").val();
            MyLayer.layerOpenUrl({
                url: "/modules/system/notice/noticeAuthorityInfo.html?contentId=" + id,
                title: "选择通知范围"
            })
        }else{
            layer.msg("基本信息保存失败，请刷新重试", {icon: 2});
        }
    }
}


function authority1(){
  var id = $("#id").val();
    MyLayer.layerOpenUrl({
        url: "/modules/info/authority/authorityInfo.html?contentId=" + id,
        title: "选择通知范围"
    })
}

/**
 * 保存权限范围后，回调置成功状态位
 */
function putStatus(status){
	if(status){
		$("#status").val("1");
        $.ajax({
            type: "POST",
            url:"/system/notice/save",
            data:$("#form").serialize(),
            async: false,
            success:function(json){
              
            },
            error:function(){
                Loading.clear();
                layer.msg("保存失败！", {icon: 2});
            }
        });
	}
}

/**
 * 发布
 */
/*function publish(subflag){
	var flag = saveForm();
	if(flag == "1" ){
		var status = $("#status").val();
		if(status == "1"){
			Loading.open();
			//直接发布
			$.ajax({
				url:"/system/notice/publish",
				data:{
					noticeId:$("#id").val()
				},
				dataType:"json",
				success: function(json){
					Loading.clear();
					if(json.flag == "1"){
						//隐藏发布按钮
						$("#publishBtn").css("display","none");
						layer.msg("发布成功",{icon:1});
                        setTimeout(function(){  //使用  setTimeout（）方法设定定时2000毫秒
                            if($("#workItemId").val() !="" && $("#workItemId").val() !=null && $("#workItemId").val() !=undefined){
                                var datas = {"id":$("#workItemId").val(),"resId":$("#resId").val()};
                                httpRequest("get","/sysWaitNoflowController/deleteWaitNoflow",datas,function (data){
                                    if('1'==data.flag){
                                        parent.refreshPage();
                                        parent.refreshPage("daiban");
                                        console.log("删除待办成功");
                                    }
                                });
                            }else{
                                if(subflag ==1){
                                    //刷新列表
                                    parent.TableInit.refTable('right_table1');
                                    parent.TableInit.refTable('right_table2');
                                }
                            }
                            if(subflag ==0){
                                parent.TableInit.refTable('right_table');
                            }
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                        },500);
					}else{
						layer.msg(json.msg,{icon:2});
					}
				},
				error: function(){
					Loading.clear();
					layer.msg("发布失败",{icon:2});
				}
			})
		}else{
			Loading.clear();
			layer.msg("请选择通知范围",{icon:0});
			//authority();
		}
	}
}*/

/**
 * 空值设置
 * @param val
 * @returns
 */
function regVlaue(val){
	if(!val){
		val = "";
	}
	return val;
}

/*function sendflow() {
    var flag = saveForm();
    if(flag == "1"){
        var status = $("#status").val();
        if(status == "1"){
            Loading.open();
            //发送审核
            $.ajax({
                type: "POST",
                url:"/system/notice/sendFlow?resId="+$("#resId").val(),
                data:{
                    noticeId:$("#id").val()
                },
                async: false,
                success:function(json){
                    Loading.clear();
                    if ('1' == json.flag) {
                        var msg = "发送成功,接收人:"+json.waitNoflowEntity.receiveUserName;
                        layer.msg(msg, {icon: 1});
                        $("#id").val(json.data.id);
                        $("#flag").val(json.data.flag);
                        //初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
                        setTimeout(function(){  //使用  setTimeout（）方法设定定时2000毫秒
                            if($("#workItemId").val() !="" && $("#workItemId").val() !=null && $("#workItemId").val() !=undefined){
                                var datas = {"id":$("#workItemId").val(),"resId":$("#resId").val()};
                                httpRequest("get","/sysWaitNoflowController/deleteWaitNoflow",datas,function (data){
                                    if('1'==data.flag){
                                        parent.refreshPage("daiban");
                                        console.log("删除待办成功");
                                    }
                                });
                            }else{
                                parent.TableInit.refTable('right_table1');
                                parent.TableInit.refTable('right_table2');
                            }
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                        },2000);

                    }
                },
                error:function(data){
                    layer.msg("发送失败!",{icon:2})
                }
            });
        }else{
            Loading.clear();
            layer.msg("请选择通知范围",{icon:0});
            //authority();
        }
    }
}*/

/**
 * 审核不通过
 */
/*function  noPass() {
    var flag = saveForm();
    if(flag == "1"){
            Loading.open();
            //发送审核
            $.ajax({
                type: "POST",
                url:"/system/notice/noPass?resId="+$("#resId").val(),
                data:{
                    noticeId:$("#id").val(),
                    workItemId:$("#workItemId").val()
                },
                async: false,
                success:function(json){
                    Loading.clear();
                    if ('1' == json.flag) {
                        var msg = "退回成功,接收人:"+json.waitNoflowEntity.receiveUserName;
                        layer.msg(msg, {icon: 1});
                        $("#id").val(json.data.id);
                        $("#flag").val(json.data.flag);

                        //初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
                        setTimeout(function(){  //使用  setTimeout（）方法设定定时2000毫秒
                            if($("#workItemId").val() !="" && $("#workItemId").val() !=null && $("#workItemId").val() !=undefined){
                                var datas = {"id":$("#workItemId").val(),"resId":$("#resId").val()};
                                httpRequest("get","/sysWaitNoflowController/deleteWaitNoflow",datas,function (data){
                                    if('1'==data.flag){
                                        parent.refreshPage("daiban");
                                        console.log("删除待办成功");
                                    }
                                });
                            }
                            parent.TableInit.refTable('right_table');
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                        },500);
                    }
                },
                error:function(data){
                    layer.msg("发送失败!",{icon:2})
                }
            });
    }
}*/

//部门级发送流程
function  commitFlowDept() {
    var status = $("#status").val();
    if(status =="1"){
        if($("#noticeTitle").val() ==""){
            $("#noticeTitle").val(getCurrentDate("yyyy年MM月dd日")+getcookie("usernm")+"部门通知公告审批");
        }
        var bootstrapValidator = $("#form").data('bootstrapValidator');
        bootstrapValidator.validate();
        if(bootstrapValidator.isValid()){
            var flag = saveForm1();
            if(flag!=""){
                var oper ="";
                if($("#workflowid").val()==""){
                    oper = "NEW";
                }else{
                    oper = "EDIT";
                }
                // 获取意见
                var idea = $("#idea").val();
                var IsNotionShow = document.getElementById("ideaArea").style.display;
                if (IsNotionShow == "block") {
                    if ("2" == document.getElementById("fillmode").value) {
                        if (idea == "" || idea == null) {
                            layer.msg("请填写意见", {icon: 0})
                            return false;
                        }
                    }
                }
                
                submitToFlow(oper, this, $("#noticeTitle").val(), idea, $("#id").val(), "attr", "attr1", $("#workitemid").val(), $("#workflowid").val());

            }
        }

    }else{
        Loading.clear();
        layer.msg("请选择通知范围",{icon:0});
    }

}

function commitFlow1() {
    var tempIdea = $("#idea").val();
    saveIdeaTemp($("#workitemid").val(),tempIdea);
    var oper ="";
    if($("#workflowid").val()==""){
        oper = "NEW";
    }else{
        oper = "EDIT";
    }
    // 获取意见
    var idea = $("#idea").val();
    var IsNotionShow = document.getElementById("ideaArea").style.display;
    if (IsNotionShow == "block") {
        if ("2" == document.getElementById("fillmode").value) {
            if (idea == "" || idea == null) {
                layer.msg("请填写意见", {icon: 0})
                return false;
            }
        }
    }

    submitToFlow(oper, this, $("#noticeTitle").val(), idea, $("#id").val(), "attr", "attr1", $("#workitemid").val(), $("#workflowid").val());

}

/**
 * 工作流回调该方法，用于改变业务数据状态
 *
 * @param subflag 状态位
 * @returns
 */
function updateBusiData(subflag) {
    var resId = $("#resId").val();
    $.ajax({
        type: "POST",
        url: basePath + "/system/notice/updateFlag?resId=" + resId,
        data: {
            id: $("#id").val(),
            subflag: subflag
        },
        async: false,
        success: function (json) {
            var flag = json.flag;
            if(flag ==1){
                //刷新列表
                parent.TableInit.refTable('right_table1');
            }
        }
    });
}

