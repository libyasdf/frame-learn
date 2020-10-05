$(function(){
    scrollTop.init();
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
    $("#columnId").val(regVlaue(theRequest.columnId));
    $("#opertation").val(regVlaue(theRequest.oper));
    $("#workItemId").val(regVlaue(theRequest.workItemId1));
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
            //修改时，如果有发布范围，则一定已经保存发布范围
            if(data.data.isFBContent == "1"){
                $("#status").val("1");
            }
        });
    }else{
        $("#creDeptName").val(getcookie("deptnm"));
        $("#creDeptId").val(getcookie("deptid"));
        var curDate=getCurrentDate("yyyy-MM-dd HH:mm:ss");
        $("#creTime").val(curDate);
        $("#subflag").val("0");
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
                url: "/modules/info/authority/authorityInfo.html?contentId=" + contentId+"&columnId="+columnId,
                title: "选择发布范围"
            });
        }
    }
}

// 保存，发布
function saveDictionary(subflag,isTs,status) {
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
                    $("#subflag").val(json.data.subflag);
                    parent.TableInit.refTable('right_table1');
                    parent.TableInit.refTable('right_table2');
                    //初始化文件上传(为保存后，将tableId放入上传参数中),并保存业务ID到附件表
                    iniFileUpload();
                    MyFileUpload.saveDocIdToAffix({docId:json.data.id,fileListId: "files,files2"});
                    ImgMyFileUpload.saveDocIdToAffix({docId:json.data.id,imageId:"imageId"});
                    var msg = "保存成功! ";
                    if(subflag == "2"){//删除待办
                        msg = "发布成功！";
                        setTimeout(function(){  //使用  setTimeout（）方法设定定时2000毫秒
                            if(workItemId !="" && workItemId !=null && workItemId !=undefined){
                                var datas = {"id":workItemId,"resId":$("#resId").val()};
                                httpRequest("get","/sysWaitNoflowController/deleteWaitNoflow",datas,function (data){
                                    if('1'==data.flag){
                                        parent.refreshPage();
                                        parent.refreshPage("daiban");
                                        console.log("删除待办成功");
                                    }
                                });
                            }
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                        },500);
                    }
                    if(isTs !='1'){//点击选择发布范围的时候不弹出提示语句
                        layer.msg(msg, {icon: 1});
                    }
                }
            },
            error:function(data){
                layer.msg("保存失败!",{icon:2})
            }
        });
    }
}

function sendflow() {
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
/**
 * 审核不通过
 */
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