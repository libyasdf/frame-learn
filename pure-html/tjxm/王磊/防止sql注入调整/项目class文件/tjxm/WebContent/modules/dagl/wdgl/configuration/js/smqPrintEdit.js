
$(function() {
    getConfig();
    OpenPort();
});

function getConfig(){
    $.ajax({
        type: "POST",
        url:"/dagl/wdgl/receiveFile/editConfig",
        async:false,
        success:function(json){
            if(json.flag == 1){
                $("#comnum").val(json.data.comId);
            }
        },
        error:function(){
        }
    });
}

function checkcomId(comId){
    var locator = new ActiveXObject("WbemScripting.SWbemLocator");
    var service = locator.ConnectServer(".");
    var properties = service.ExecQuery("SELECT * FROM Win32_PnPEntity where Name like '%(COM%)'");
    var e = new Enumerator (properties);
    if(!e.atEnd()){
        var i=1;
        for (;!e.atEnd();e.moveNext ()){
            var p = e.item ();
            var num = ((p.Name).split("COM")[1]).split(")")[0];
            if(comId == num){
                return true;
            }
        }
    }
    return false;
}

//重写mscomm控件的唯一事件处理代码
function MSComm1_OnComm() {
    var len = 0;
    if (MSComm1.CommEvent == 1)//如果是发送事件
    {
        // window.alert("ok");//这句正常，说明发送成功了
    }
    else if (MSComm1.CommEvent == 2)//如果是接收事件
    {
        var result = MSComm1.Input;
        //输出扫描到的条码信息
        var sts = result.split("^");
        setEntity(sts);
    }

    return false;
}

function OpenPort() {
    if($("#comnum").val() == null || $("#comnum").val() == ""){
        // layer.alert("系统没有检测到串口信息，请到基础配置模块配置后再试！", {icon: 5});
        $("#mes").html("<font color='red'>*扫描枪连接失败！（系统没有检测到串口信息，请到基础配置模块配置后再试)</font>");
        return false;
    }else{
        if(checkcomId($("#comnum").val())){
            MSComm1.CommPort = $("#comnum").val();
        }else{
            $("#mes").html("<font color='red'>*请连接扫码设备！</font>");
            return false;
        }
    }
    if (MSComm1.PortOpen == false) {
        MSComm1.PortOpen = true;
        $("#mes").html("<font color='blue'>扫码枪连接成功!</font>");
    }else if(typeof(MSComm1.PortOpen) == "undefined" || MSComm1.PortOpen == null){
        $("#mes").html("<font color='red'>*请安装控件后再试！</font>");
    } else {
        $("#mes").html("<font color='blue'>已经开始接收数据!</font>");
    }
}

function ClosePort() {
    if (MSComm1.PortOpen == true) {
        MSComm1.PortOpen = false;
        $("#mes").html("<font color='red'>*已断开连接!</font>");
    }
    else {
        $("#mes").html("<font color='red'>*串口已经关闭!</font>");
    }
}






