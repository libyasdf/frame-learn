
var json =null;
var s_filelistid ="";
// 获取值班表的id
var theRequest = GetRequest();
var id  = theRequest.id ? theRequest.id : "";//编研id，业务id
var workItemId1 = theRequest.workItemId1 ? theRequest.workItemId1 : "";//待办id
if("" == workItemId1){
    workItemId1 = theRequest.workItemId ? theRequest.workItemId : "";//待办id(更多)
}

$("#id").val(id);
var chuShiId = getcookie("chuShiId");
var chuShiName = getcookie("chuShiName");

$(function () {

    /**
     * 初始化页面，数据加载、渲染
     */
    if ("" != id) {

        //表单数据渲染
        var datas = {"id": id,"chushiId":chuShiId, "resId": $("#resId").val()};
        httpRequest("get", "/dagl/bygl/studying/getStudyingById", datas, function (data) {
            json = data;
            DisplayData.playData({data: data});
            iniFileUpload(data);
        });
    } else {//id为空时，表示新建草稿状态，加载启动节点按钮及启动节点提示信息
        iniFileUpload();
    }


});

/**
 * 空值设置
 * @param val
 * @returns
 */
function regVlaue(val) {
    if (!val) {
        val = "";
    }
    return val;
}


/**
 * 初始化文件上传
 */
function iniFileUpload(data) {
    if (data != undefined) {
        if (!$.isEmptyObject(data.data.studyingSubs)) {
            for(var i=0;i<data.data.studyingSubs.length;i++){
                var id = data.data.studyingSubs[i].id;
                var filelistid = data.data.studyingSubs[i].filelistid;
                $("#p_affix").append("<div id="+filelistid+" class=\"col-md-10\" style=\"margin-top: 7px;\">\n" +
                    "                        </div>");
                s_filelistid = (parseInt(filelistid)+10000)+"";
                $("#s_affix_button").append("<span class=\"btn btn-primary fileinput-button\"> " +
                    "                           <i class=\"glyphicon glyphicon-plus\"></i>" +
                    "                           <span>选择文件...</span> " +
                    "                           <input id=\"fileupload"+(i+20000)+"\" type=\"file\" name=\"files[]\" multiple>\n" +
                    "                        </span>");
                $("#s_affix").append("<div id="+s_filelistid+" class=\"col-md-10\">\n" +
                    "                        </div>");
                MyFileUpload.init({
                    viewParams: { "tableId": id, "tableName": "studying" },
                    editOrView: "VIEW",
                    domId: "fileupload"+(i+10000),
                    fileListId: filelistid,  //文件列表显示的div
                    fileTypes:"docx",
                    maxFileCount:1
                });

                MyFileUpload.init({
                    viewParams: { "tableId": id, "tableName": "studying_bak" },
                    editOrView: "NEW",
                    domId: "fileupload"+(i+20000),
                    fileListId: s_filelistid,  //文件列表显示的div
                    fileTypes:"docx",
                    maxFileCount:1
                });
            }
        }
    }else{
    }

}

/**
 * 提交（上报人员待办）
 */
function fankui(){

    var fjId = $("#s_affix").find('a').attr("id");
    if(undefined == fjId){
        layer.msg('请上传附件，再进行反馈！', {icon: 0,time:1000});
    }else {
        layer.confirm('确定信息无误吗？',
            {icon: 3, title: '提示'},
            function (e) {
                layer.close(e);
                //保存附件业务id，因为放油业务id不需要保存业务id了
                /*MyFileUpload.saveDocIdToAffix({docId:json.data.studyingSubs[0].id,fileListId: s_filelistid});*/

                //修改子表的反馈状态
                $.ajax({
                    type: "POST",
                    url: "/dagl/bygl/studying/updateStudyingSubIsBack",
                    data: {"id": json.data.studyingSubs[0].id, "type": "back"},
                    async: false,
                    dataType: "json",
                    success: function (data) {

                        //删除代办
                        deleteWaitNoflow();

                        var msg = "已确认编研信息，请查看！";
                        //个档案馆管理员发送消息（谁创建，给谁发）
                        layer.prompt({
                                title: "反馈信息",
                                formType: 2,
                                value: "已确认编研信息，请查看！",
                                maxlength: 100,
                                btn2: function() {
                                    //发送消息
                                    MessageUtil.sendMsg({
                                        senderId: Config.sysId,	//系统ID
                                        subject: chuShiName + "编研反馈",		//消息主题
                                        content: msg,		//消息内容
                                        accepterId: json.data.studyingSubs[0].creUserId,	//接收人ID
                                        status: "0",		//0未读；1已读
                                        type: "3"		//3站内消息
                                    });
                                    closeForm();
                                    parent.layer.msg('反馈成功！', {icon: 1, time: 1000});
                                    parent.parent.layer.msg('反馈成功！', {icon: 1, time: 1000});
                                },
                            },
                            function (val, index) {
                                layer.close(index);
                                msg = val;
                                //发送消息
                                MessageUtil.sendMsg({
                                    senderId: Config.sysId,	//系统ID
                                    subject: chuShiName + "编研反馈",		//消息主题
                                    content: msg,		//消息内容
                                    accepterId: json.data.studyingSubs[0].creUserId,	//接收人ID
                                    status: "0",		//0未读；1已读
                                    type: "3"		//3站内消息
                                });
                                closeForm();
                                parent.layer.msg('反馈成功！', {icon: 1, time: 1000});
                                parent.parent.layer.msg('反馈成功！', {icon: 1, time: 1000});
                            });

                    },
                    error: function (data) {
                        layer.msg("反馈失败！", {icon: 0, time: 1000});
                    }
                });
            });
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

function deleteWaitNoflow() {
    var result = false;
    $.ajax({
        url:"/sysWaitNoflowController/deleteWaitNoflow",
        data:{"sourceId":id,receiveDeptId:getcookie("chuShiId")},//根据业务id删除
        dataType:"json",
        success:function(data){
            if(data.flag=="1"){
                parent.refreshPage("daiban");//刷新待办列表
                parent.parent.refreshPage("daiban");//从更多进入考试时刷新待办列表
                result = true;
            }else{
                result = false;
            }
        }
    });

    return result;
}

//绑定日期输入input
function closeForm(){
    MyLayer.closeOpen()
}