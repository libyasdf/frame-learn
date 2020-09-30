$(function() {
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#administrativeId").val(regVlaue(theRequest.administrativeId));
    $("#administrativeSuperId").val(regVlaue(theRequest.administrativeSuperId));
    $("#opertation").val(theRequest.opertation);
    if($("#opertation").val() != "VIEW"){
        //初始化字典项--行政职务名称
        Dictionary.init({position:"administrativeDutiesName",mark:"dwgl_dygl_xzzw_xzzw",type:"1",name:"administrativeDutiesName",domType:"select"});
        //初始化字典项--个人排序
        Dictionary.init({position:"personalPositionOrder",mark:"dwgl_dygl_xzzw_grzwpx",type:"1",name:"personalPositionOrder",domType:"select"});
    }
    /**
     * 初始化页面，数据加载、渲染
     */
    if($("#administrativeId").val() != ""){

        //表单数据渲染
        var datas = {"id":$("#administrativeId").val(),"resId":$("#resId").val()};
        httpRequest("get","/djgl/ddjs/dygl/dyxx/dzzwEdit",datas,function (data){
            DisplayData.playData({data:data});
        });

    }

})

function commitForm(flag) {
    var data = saveForm();
    if (data) {
        layer.msg("保存成功！", {
            icon : 1
        });
        parent.LoadFormpage("formpage6");
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
            url: "/djgl/ddjs/dygl/dyxx/zzwSave",
            data:$("#form").serialize(),
            async: false,
            success:function(json){
                $("#administrativeId").val(json.data.administrativeId);
                record = json.data.administrativeId;
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