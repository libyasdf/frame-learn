var fileTypeId ="fafc5ae500b04a1e888b2d5c83559c5a";
var workFlowId ="fafc5ae500b04a1e888b2d5c83559c5a";
$(function() {
    var theRequest = GetRequest();
    $("#id").val(regVlaue(theRequest.id));
    $("#filetypeid").val(fileTypeId);
    $("#workflowid").val(workFlowId);
    $("#workitemid").val(theRequest.workItemId);
    $("#opertation").val(theRequest.oper);
    $("#resId").val(regVlaue(theRequest.resId));
    //消息提示
    $('[data-toggle="tooltip"]').tooltip();
    $('[data-toggle="popover"]').popover({trigger:"hover"})
    var myDate = new Date();
    $("#annual").val(myDate.getFullYear());
    if(regVlaue(theRequest.isAdd=="add")){
        $("#partyOrganizationId").val(theRequest.orgId);
        $("#partyOrganizationName").val(theRequest.orgName);
    }
    /**
     * 初始化页面，数据加载、渲染
     */
    if($("#id").val() != ""){
        //表单数据渲染
        var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
        httpRequest("get","/djgl/ddjs/shyk/ndjh/editVal",datas,function (data){
            DisplayData.playData({data:data});
        });

    }
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
           });
        }else{//oper='VIEW'
            var datas = {
                type:"1",
                oper:$("#opertation").val(),
                workitemid:$("#workitemid").val()
            };
            DisplayData.playWorkFlow("/workflow/getYiBanData",datas,$("#opertation").val());
        }
    //初始化意见域，回显临时意见
   initIdeaArea($("#workitemid").val());
    //获取正式意见，渲染页面
    getIdeas();
}

})

/**
 * 调用工作流配置的按钮
 * @returns
 */
function getStartButton(){
    var oper = "NEW";
    var startdatas = {
        workflowid:$("#workflowid").val(),
        filetypeid:$("#filetypeid").val(),
        oper:oper
    };
    editFunction(basePath+"/workflow/getStartWfButton",startdatas,"1",oper);
}
/**
 * 提交表单
 * */

function commitSave() {
    var data = saveForm();
    if (data) {
        layer.msg("保存成功！", {
            icon : 1
        });
        // 刷新列表
        parent.TableInit.refTable('right_table');

    }
}

/**
 * 保存并新增试题
 */
function saveForm(isNew){
    var form = $('#form').serializeJSON();
    var rows = $('#right_table').bootstrapTable("getData");
    $("#right_table textarea[name=modeval]").each(function (i) {
        $('#right_table').bootstrapTable('updateRow', {
            index: i,
            row: {
                modeval : $(this).val(),
                primaryCoverage:($(this).parent().next().children()).text()
            }
        });
    })
    form['list'] = rows;
    $.ajax({
            type: "POST",
            url:"/djgl/ddjs/shyk/ndjh/saveNdjh",
            data:JSON.stringify(form),
            contentType:"application/json",
            async: false,
            dataType:"json",
            success:function(data){
                if(data.flag=='1'){
                    $("#id").val(data.data.id);
                    layer.msg("保存成功！", {icon: 1});
                    //刷新列表
                    parent.TableInit.refTable('right_table');
                }else{
                    layer.msg("保存失败！", {icon: 1});
                }
            },
            error:function(data){
                layer.msg("请稍后再试！", {icon: 1});
            }
        });
    var tempIdea = $("#idea").val();
    saveIdeaTemp($("#workitemid").val(),tempIdea);
  //   }
}


/**
 * 空值设置
 * @param val
 * @returns
 */
function regVlaue(val){
    if(!val||val=="undefined"){
        val = "";
    }
    return val;
}

/**
 * 提交流程
 */
function commitFlow() {
        /*  var bootstrapValidator = $("#form").data('bootstrapValidator');
         //手动触发验证
         bootstrapValidator.validate();
         if(bootstrapValidator.isValid()){*/
        if ($("#title").val() == "") {
            $("#title").val(getCurrentDate("yyyy年MM月dd日") + decodeURI($("#partyOrganizationName").val()) + "年度计划")
        }
        if ($("#reportingTime").val() == "") {
            var myDate = new Date();
            $("#reportingTime").val(myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate());
        }
        var plan = saveForm();
        if (plan != "") {
            var oper = "";
            if ($("#workitemid").val() == "") {
                oper = "NEW";
            } else {
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

            submitToFlow(oper, this, $("#title").val(), idea, $("#id").val(), "attr", "attr1", $("#workitemid").val(), $("#workflowid").val());
        }
  /*  }*/

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
        url: basePath + "/djgl/ddjs/shyk/ndjh/updateFlag?resId=" + resId,
        data: {
            id: $("#id").val(),
            subflag: subflag
        },
        async: false,
        success: function (data) {
        }
    });
}
    /**
     * 获取正式意见、回显
     */
    function getIdeas() {
        // 返回数据格式参照：/mock/formalIdeas.json
        var res = getFormalIdeas($("#id").val(), fileTypeId);
        if (res.length > 0) {
            for (var i = 0; i < res.length; i++) {
                if (res[i].ideaList.length > 0) {
                    for (var j = 0; j < res[i].ideaList.length; j++) {
                        // var time = new Date(jsonArr[i].ideaList[j].ideatime);
                        var textarea = document.getElementsByName(res[i].name);
                        if (res[i].ideaList[j].userid.trim() != getcookie("userid").trim()) {
                            $(textarea).text(res[i].ideaList[j].idea);
                        }
                    }
                }
            }
        }
    }
