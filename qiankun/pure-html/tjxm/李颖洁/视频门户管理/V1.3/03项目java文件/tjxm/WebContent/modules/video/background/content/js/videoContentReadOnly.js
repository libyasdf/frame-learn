$(function(){
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
    $("#opertation").val(regVlaue(theRequest.oper));
    /**
     * 初始化页面，数据加载、渲染
     */
    if($("#id").val() != ""){
        //表单数据渲染
        var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
        httpRequest("get","/video/content/edit",datas,function (data){
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
    }
    iniFileUpload();
  //传第一个参数0表示只读页面，1表示可以删除文件；第三个参数0表示不将学习记录存入数据库，1表示需要存入数据库
	showFiles("0",$("#id").val(),"0");
})

function iniFileUpload() {
    MyFileUpload.init({
        viewParams: {"tableId": $("#id").val(), "tableName": "video_content"},
        editOrView: $("#opertation").val(),
        read:true,
        download:false
    });
    ImgMyFileUpload.init({
        viewParams: {"tableId": $("#id").val(), "tableName": "video_content"},
        editOrView: $("#opertation").val(),
        maxFileSize: 150 * 1024,//150K
        domId: "imgfileupload",
        fileListId:"image",
        imageId:"imageId",
        path:Config.path
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
