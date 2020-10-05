$(function() {
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#workingId").val(regVlaue(theRequest.workingId));
    $("#workingSuperId").val(regVlaue(theRequest.superId));
    $("#opertation").val(theRequest.opertation);
    if($("#opertation").val() != "VIEW"){
        //初始化字典项--个人身份
        Dictionary.init({position:"identity",mark:"dwgl_dygl_ryjbqk_grsf",type:"1",name:"identity",domType:"select"});
        //初始化字典项--机关第一线
        Dictionary.init({position:"workFrontlineSituation",mark:"dwgl_dygl_ryjbqk_yxqk",type:"1",name:"workFrontlineSituation",domType:"select"});
    }
    /**
     * 初始化页面，数据加载、渲染
     */
    if($("#workingId").val() != ""){

        //表单数据渲染
        var datas = {"id":$("#workingId").val(),"resId":$("#resId").val()};
        httpRequest("get","/djgl/ddjs/dygl/dyxx/workEdit",datas,function (data){
            DisplayData.playData({data:data});
        });

    }


})

function commitForm(flag) {
//	$("#subflag").val(flag);
    //console.log($("#subflag").val()+"a"+flag)
    var data = saveForm();
    if (data) {
        layer.msg("保存成功！", {
            icon : 1
        });
        parent.LoadFormpage("formpage4");
    }
}


/**
 * 保存
 */
function saveForm(){
    var res = "";
    var record = "";
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
        $.ajax({
            type: "POST",
            url: "/djgl/ddjs/dygl/dyxx/workSave",
            data:$("#form").serialize(),
            async: false,
            success:function(json){
                $("#workingId").val(json.data.workingId);
                record = json.data.workingId;
            },
            error:function(){
            }
        });
    }
    return record;
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