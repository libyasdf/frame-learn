var fileTypeId = "8a595090141f42a4a862bfd491877a73";
var workFlowId = "8a595090141f42a4a862bfd491877a73";

$(function(){
    scrollTop.init();
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
    $("#columnId").val(regVlaue(theRequest.columnId));
    $("#filetypeid").val(fileTypeId);
    $("#workflowid").val(workFlowId);
    $("#workitemid").val(theRequest.workItemId);
    $("#opertation").val(theRequest.oper);
    $("#columnCode").val(theRequest.columnCode);

    //判断是否显示上传图片
    if($("#columnCode").val() =="JNXW" || $("#columnCode").val() =="XCY"){
        $("#imageIds").attr("style","block");
    }

    if($("#columnCode").val() =="GWFB"){
        $("#chushiDiv").attr("style","block");
    }

    $.ajax({
        type: "POST",
        url:"/info/officeScope/findScopeOffice",
        data:$("#form").serialize(),
        async: false,
        success:function(json){
            if(json){
                var officeIdList = json.officeIdList;
                var officeNameList = json.officeNameList;
                for(var i=0;i<officeIdList.length;i++){
                    var officeId = officeIdList[i];
                    var officeName = officeNameList[i];
                    if(i ==0){
                        document.getElementById("deptId").options[0] = new Option("--请选择--","");
                    }
                    document.getElementById("deptId").options[i+1] = new Option(officeName,officeId);
                }
            }

        },
        error:function(data){

            layer.msg("保存失败!",{icon:2})
        }
    });

    /**
     * 初始化页面，数据加载、渲染
     */
    var columnId = $("#columnId").val();
    var contentId = $("#id").val();
    if(columnId != ""){
        var datas = {"columnId":columnId,"contentId":contentId,"math":Math.random(),"resId":$("#resId").val()};
        httpRequest("get","/info/content/queryQx",datas,function (data){
            if ('1' == data.flag) {
                $("#isFbfw").val(data.isFbfw);
                if('1'== data.isFbfw){
                    $("#fbfwShow").show();
                    $("input[type=radio][name='isFBContent'][value='1']").attr("checked",'checked');//设置选择值为1的
                    $("#status").val("1");
                }
                if('1' == data.fbButton){
                    $("#fbButton").show();
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
    }
    if($("#id").val() != ""){
        //表单数据渲染
        var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
        httpRequest("get","/info/content/edit",datas,function (data){
            $("#showTime").val(data.data.showStartTime+" - "+data.data.showEndTime);
            DisplayData.playData({data:data});

            if(data.data.deptId !=""){
                $("#deptId").val(data.data.deptId);
            }
            //修改时，如果有发布范围，则一定已经保存发布范围
            if(data.data.isFBContent == "1"){
                $("#status").val("1");
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
       /* $("#deptId").val(getcookie("chuShiId"));
        $("#deptName").val(getcookie("chuShiName"));*/
        $("#creDeptName").val(getcookie("deptnm"));
        $("#creDeptId").val(getcookie("deptid"));
        var curDate=getCurrentDate("yyyy-MM-dd HH:mm:ss");
        $("#creTime").val(curDate);
        $("#subflag").val("0");
        getStartWf();
    }
    iniFileUpload();
})
var resId = $("#resId").val();

//保存发送范围之前先保存下数据
function saveFbfw() {

    var isFbfw = $("#isFbfw").val();
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
        if(isFbfw =="1"){//存在发布范围
            var contentId = $("#id").val();
            if(contentId == "" || contentId == null || contentId == undefined){
                saveDictionary("",isFbfw,"0");//先保存主表数据
                contentId = $("#id").val();
            }
            var columnId = $("#columnId").val(); //打开权限选择窗口
            MyLayer.layerOpenUrl({
                url: "/modules/info/authority/authorityInfo.html?contentId=" + contentId+"&columnId="+columnId+"&typeCode=01",
                title: "选择发布范围"
            });
        }
    }
}

// 保存，发布
function saveDictionary(subflag,isTs,status) {
    debugger;
    $("#deptId").val($('#deptId option:selected') .val());
    var  aa = $("#deptId").val();
   var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
        var isFbfw = $("#isFbfw").val();
        if(status == null || status == undefined){
            status = $("#status").val();
        }
        var workItemId = $("#workItemId").val();
        if(isFbfw =='1' && status!='1' && status !='0' && (workItemId == null || workItemId == undefined || workItemId =="")){//有发布范围 并且没有保存过发布范围或者不是点击选择发布范围时
            layer.msg("请先保存发布范围",{icon:0});
            return false;
        }
        var html = editor.html();
        editor.sync();
        html = $('#editor_id').val();
        var showTime = $("#showTime").val();
        if(showTime !=""){
            var showTimeArray = showTime.split(" - ");
            $("#showStartTime").val(showTimeArray[0]);
            $("#showEndTime").val(showTimeArray[1]);
        }
        if(subflag != "" && subflag != "0" && subflag != null && subflag != undefined){
            var curDate=getCurrentDate("yyyy-MM-dd HH:mm:ss");
            $("#fbTime").val(curDate);
            $("#subflag").val(subflag);
        }
        Loading.open();
        $.ajax({
            type: "POST",
            url:"/info/content/saveFrom?resId="+resId,
            data:$("#form").serialize(),
            async: false,
            success:function(json){
                Loading.clear();
                if ('1' == json.flag) {
                    $("#id").val(json.data.id);
                    if($("#workItemId").val() !="" && $("#workItemId").val() !=null && $("#workItemId").val() !=undefined){

                    }else{
                        //刷新列表
                        if(parent.TableInit != undefined){
                            parent.TableInit.refTable('right_table1');
                            parent.TableInit.refTable('right_table2');
                        }else{
                            parent.location.reload();
                        }

                    }
                    //初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
                    iniFileUpload();
                    MyFileUpload.saveDocIdToAffix({docId:json.data.id,fileListId: "files,files2"});
                    ImgMyFileUpload.saveDocIdToAffix({docId:json.data.id,imageId:"imageId"});
                    layer.msg("保存成功！", {icon: 1});
                }
            },
            error:function(data){
                layer.msg("保存失败!",{icon:2})
            }
        });
    }
}


function  commitFlowDept() {
        debugger;
        if($("#infoTitle").val() ==""){
            $("#infoTitle").val(getCurrentDate("yyyy年MM月dd日")+getcookie("usernm")+"信息发布审核");
        }
        var bootstrapValidator = $("#form").data('bootstrapValidator');
        bootstrapValidator.validate();
        if(bootstrapValidator.isValid()){
            var flag = saveDictionary();
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
                if($("#deptId").val() ==""){
                    $("#scopeOffice").val(getcookie("chuShiId"));
                }else{
                    $("#scopeOffice").val($("#deptId").val());
                }
                submitToFlow(oper, this, $("#infoTitle").val(), idea, $("#id").val(), "scopeOffice", "attr1", $("#workitemid").val(), $("#workflowid").val());

            }
        }

}

/*function sendflow() {
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
        var isFbfw = $("#isFbfw").val();
        var status = $("#status").val();
        var workItemId = $("#workItemId").val();
        if(isFbfw =='1' && status!='1' && status !='0' && (workItemId == null || workItemId == undefined || workItemId =="")){//有发布范围 并且没有保存过发布范围或者不是点击选择发布范围时
            layer.msg("请先保存发布范围",{icon:0});
            return false;
        }
        var html = editor.html();
        editor.sync();
        html = $('#editor_id').val();
        var showTime = $("#showTime").val();
        if(showTime !=""){
            var showTimeArray = showTime.split(" - ");
            $("#showStartTime").val(showTimeArray[0]);
            $("#showEndTime").val(showTimeArray[1]);
        }
        Loading.open();
        $.ajax({
            type: "POST",
            url:"/info/content/sendFlow?resId="+resId,
            data:$("#form").serialize(),
            async: false,
            success:function(json){
                Loading.clear();
                if ('1' == json.flag) {
                    var msg = "发送成功,接收人:"+json.waitNoflowEntity.receiveUserName;
                    layer.msg(msg, {icon: 1});
                    $("#id").val(json.data.id);
                    $("#subflag").val(json.data.subflag);
                    parent.TableInit.refTable('right_table1');
                    parent.TableInit.refTable('right_table2');
                    //初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
                    iniFileUpload();
                    MyFileUpload.saveDocIdToAffix({docId:json.data.id,fileListId: "files,files2"});
                    ImgMyFileUpload.saveDocIdToAffix({docId:json.data.id,imageId:"imageId"});
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
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    },2000);

                }
            },
            error:function(data){
                layer.msg("发送失败!",{icon:2})
            }
        });
    }
}
/!**
 * 审核不通过
 *!/
function  noPass(subflag) {
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
        var html = editor.html();
        editor.sync();
        html = $('#editor_id').val();
        var showTime = $("#showTime").val();
        if(showTime !=""){
            var showTimeArray = showTime.split(" - ");
            $("#showStartTime").val(showTimeArray[0]);
            $("#showEndTime").val(showTimeArray[1]);
        }
        if(subflag != "" && subflag != null && subflag != undefined){
            $("#subflag").val(subflag);
        }
        Loading.open();
        $.ajax({
            type: "POST",
            url:"/info/content/noPass?resId="+resId,
            data:$("#form").serialize(),
            async: false,
            success:function(json){
                Loading.clear();
                if ('1' == json.flag) {
                    var msg = "退回成功,接收人:"+json.waitNoflowEntity.receiveUserName;
                    layer.msg(msg, {icon: 1});
                    $("#id").val(json.data.id);
                    $("#subflag").val(json.data.subflag);
                    parent.TableInit.refTable('right_table1');
                    parent.TableInit.refTable('right_table2');
                    //初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
                    iniFileUpload();
                    MyFileUpload.saveDocIdToAffix({docId:json.data.id,fileListId: "files,files2"});
                    ImgMyFileUpload.saveDocIdToAffix({docId:json.data.id,imageId:"imageId"});
                    setTimeout(function(){  //使用  setTimeout（）方法设定定时2000毫秒
                        parent.refreshPage("daiban");
                        var datas = {"id":$("#workItemId").val(),"resId":$("#resId").val()};
                        httpRequest("get","/sysWaitNoflowController/deleteWaitNoflow",datas,function (data){
                            if('1'==data.flag){
                                parent.refreshPage("daiban");
                                console.log("删除待办成功");
                            }
                        });
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
        url: basePath + "/info/content/updateFlag?resId=" + resId,
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


function iniFileUpload() {
    MyFileUpload.init({
        viewParams: {"tableId": $("#id").val(), "tableName": "info_content"},
        editOrView: $("#opertation").val(),
        read:true,
        download:false
    });
    ImgMyFileUpload.init({
        viewParams: {"tableId": $("#id").val(), "tableName": "info_content"},
        editOrView: $("#opertation").val(),
        //maxFileSize: 150 * 1024,//150K
        domId: "imgfileupload",
        fileListId:"image",
        imageId:"imageId"
    });
}
//关闭当前窗口
function closeIfram(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
/**
 * 保存权限范围后，回调置成功状态位
 */
function putStatus(status){
    if(status){
        $("#status").val("1");
    }
}

function showFbfw(value) {
    if(value.value=='1'){
        $("#fbfwShow").show();
    }else{
        $("#fbfwShow").hide();
    }
    $("#isFbfw").val(value.value);
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