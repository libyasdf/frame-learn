var fileTypeId = "8a595090141f42a4a862bfd491877a73";
var workFlowId = "8a595090141f42a4a862bfd491877a73";

$(function(){
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
    $("#opertation").val(regVlaue(theRequest.oper));
    $("#filetypeid").val(fileTypeId);
    $("#workflowid").val(workFlowId);
    $("#workitemid").val(theRequest.workItemId);
    $("#columnCode").val(theRequest.columnCode);
    /**
     * 初始化页面，数据加载、渲染
     */
    if($("#columnCode").val() =="JNXW" || $("#columnName").val() =="XCY"){
        $("#imageIds").attr("style","block");
    }

    if($("#id").val() != ""){
        //表单数据渲染
        var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
        httpRequest("get","/info/content/edit",datas,function (data){
            $("#showTime").val(data.data.showStartTime+" - "+data.data.showEndTime);
            $("#id").val(data.data.id);
            /*$("#title").val(data.data.title);*/
            $("#title").html(data.data.title);
            $("#author").html("作者："+data.data.author);
            $("#source").html("来源："+data.data.source);
            $("#creDeptName").html("创建部门："+data.data.creDeptName);
            $("#fbTime").html("发布时间："+data.data.fbTime);
            $("#content").html(data.data.content);
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
                DisplayData.playWorkFlow("/flowService/getFlowData",datas,$("#opertation").val());
            }else{//oper='VIEW'
                var datas = {
                    type:"1",
                    oper:$("#opertation").val(),
                    workitemid:$("#workitemid").val()
                };
                DisplayData.playWorkFlow("/workflow/getYiBanData",datas,$("#opertation").val());
            }

        }
    }
    iniFileUpload();
})

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
        maxFileSize: 150 * 1024,//150K
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

function regVlaue(val){
    if(!val){
        val = "";
    }
    return val;
}
