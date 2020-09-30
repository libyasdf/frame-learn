var fileTypeId = "b66ac93172e344ee8e74e6a16922778c";
var workFlowId = "b66ac93172e344ee8e74e6a16922778c";

$(function(){
    debugger;
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#oper").val(theRequest.oper);
    $("#id").val(regVlaue(theRequest.id));
    $("#filetypeid").val(fileTypeId);
    $("#workflowid").val(workFlowId);
    $("#workitemid").val(theRequest.workItemId);
    $("#opertation").val(theRequest.oper);
    $("#title").val(theRequest.dwSurveyName);
    $("#surveyId").val(theRequest.surveyId);


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
	
	
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if(theRequest.surveyId != "" && theRequest.surveyId!= undefined ){
		//表单数据渲染
		var datas = {"surveyId":$("#surveyId").val(),"resId":$("#resId").val()};
		httpRequest("get","/survey/surveyDesign/view",datas,function (data){
		    if(data.data.id!=null && data.data.id!=""){
		        if($("#title").val()!=""){
                    data.data.title =  $("#title").val();
                }
                DisplayData.playData({data:data});
                //隐藏发布按钮
                var flag = data.data.subflag;
                $("#id").val(data.data.id);
               /* if(flag == "1" || flag =="5" || flag =="6"){
                    $("#sendFLow").css("display","none");
                    $("#wenjuanBtn").css("display","none");
                }*/

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
                                if(wfleveid =="af7f3ea835b142b48765b55ac92cee69" ){
                                    $("#wenjuanBtn").css("display","none");
                                    $("#querySurveyBtn").show();
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
                    getStartWf();
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
		});
	}else{
	    if(regVlaue(theRequest.id)!="" && regVlaue(theRequest.id)!= undefined){
            //表单数据渲染
            var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
            httpRequest("get","/survey/surveyDesign/getIdView",datas,function (data){
                    if($("#title").val()!=""){
                        data.data.title =  $("#title").val();
                    }
                    DisplayData.playData({data:data});
                    //隐藏发布按钮
                    var flag = data.data.subflag;
                    $("#id").val(data.data.id);
                 /*   if(flag == "1" || flag =="5" || flag =="6"){
                        $("#sendFLow").css("display","none");
                        $("#wenjuanBtn").css("display","none");
                    }*/

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
                                    if(wfleveid =="af7f3ea835b142b48765b55ac92cee69" ){
                                        $("#wenjuanBtn").css("display","none");
                                        $("#querySurveyBtn").show();
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
                        getStartWf();
                    }else if($("#opertation").val()=="EDIT"){
                        getStartWf();
                    }
            });
        }else{
            $("#creUserId").val(getcookie("userid"));
            $("#creUserName").val(getcookie("usernm"));
            $("#creDeptId").val(getcookie("deptid"));
            $("#creDeptName").val(getcookie("deptname"));
            getStartWf();
        }
	}
	
	/*//初始化文件上传
	iniFileUpload();*/
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
 * 部门级保存
 */
function saveForm1(){
    debugger;
    var res = "";
    $("#wenjuanDeptId").val("1");
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
        Loading.open();
        $.ajax({
            type: "POST",
            url:"/survey/surveyDesign/save",
            data:$("#form").serialize(),
            async: false,
            success:function(json){
                Loading.clear();
                debugger;
                res = json.flag;
                if (json.flag == '1') {
                    $("#id").val(json.data.id);
                    if($("#workItemId").val() !="" && $("#workItemId").val() !=null && $("#workItemId").val() !=undefined){

                    }else{
                        //刷新列表
                        if(parent.TableInit != undefined){
                            parent.TableInit.refTable('right_table1');
                        }else{
                            //parent.location.reload();
                        }

                    }
                    //初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
                /*    iniFileUpload();
                    MyFileUpload.saveDocIdToAffix({docId:json.data.id,fileListId: "files,files2"});*/
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

/*function iniFileUpload(){
	MyFileUpload.init({
		viewParams: {"tableId":$("#id").val(),"tableName":"sys_notice"},
		editOrView:$("#oper").val(),
        maxFileSize:10*1024*1024 //10M
    });
}*/


/**
 * 部门级打开权限选择窗口
 */
function authorityDept(){
    var flag = saveForm1();
    debugger;
    if(flag){
        if(flag == "1"){
            var id = $("#surveyId").val();
            MyLayer.layerOpenUrl({
                url: "/modules/wenjuan/authority/deptAuthorityInfo.html?contentId=" + id,
                title: "选择通知范围"
            })
        }else{
            layer.msg("基本信息保存失败，请刷新重试", {icon: 2});
        }
    }
}


/**
 * 保存权限范围后，回调置成功状态位
 */
function putStatus(status){
	if(status){
		$("#status").val("1");
        $.ajax({
            type: "POST",
            url:"/survey/surveyDesign/save",
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


//部门级发送流程
function  commitFlowDept() {
    var status = $("#status").val();
    if(status =="1"){
        if($("#wenjuanTitle").val() ==""){
            $("#wenjuanTitle").val(getCurrentDate("yyyy年MM月dd日")+getcookie("usernm")+"部门调查问卷审批");
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
                
                submitToFlow(oper, this, $("#wenjuanTitle").val(), idea, $("#id").val(), "attr", "attr1", $("#workitemid").val(), $("#workflowid").val());

            }
        }

    }else{
        Loading.clear();
        layer.msg("请选择通知范围",{icon:0});
    }

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
        url: basePath + "/survey/surveyDesign/updateFlag?resId=" + resId,
        data: {
            id: $("#id").val(),
            subflag: subflag
        },
        async: false,
        success: function (json) {
            debugger;
            var flag = json.flag;
            if(flag ==1){
                //刷新列表
                // opener.TableInit.refTable('right_table1');
            }
        }
    });
}

function querySurvey() {
    var params="surveyId=" + $("#surveyId").val()+"&surveyState=1";
    //window.location.href="/wenjuan/surveyDesign/intoDesignPage?"+params;
    //window.open("/wenjuan/surveyDesign/intoDesignPage?"+params,'','width=1500,height=700')
    //跳转列表页
    $("#searchA").prop("href","/wenjuan/surveyDesign/intoDesignPage?" + params);
    $("#searchA")[0].click();	//触发a标签
}

