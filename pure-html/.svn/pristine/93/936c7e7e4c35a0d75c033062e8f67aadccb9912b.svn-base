$(function() {
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#degreeId").val(regVlaue(theRequest.degreeId));
    $("#degreeSuperId").val(regVlaue(theRequest.degreeSuperId));
    $("#opertation").val(theRequest.opertation);
    if($("#opertation").val() != "VIEW"){
        //初始化字典项--学历
        Dictionary.init({position:"education",mark:"dwgl_dygl_ryjbqk_xl",type:"1",name:"education",domType:"select"});
        //初始化字典项--学位
        Dictionary.init({position:"degree",mark:"dwgl_dygl_ryjbqk_xw",type:"1",name:"degree",domType:"select"});
    }
    /**
     * 初始化页面，数据加载、渲染
     */
    if($("#degreeId").val() != ""){

        //表单数据渲染
        var datas = {"id":$("#degreeId").val(),"resId":$("#resId").val()};
        httpRequest("get","/djgl/ddjs/dygl/dyxx/drgeeEdit",datas,function (data){
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
        parent.LoadFormpage("formpage7");
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
            url: "/djgl/ddjs/dygl/dyxx/drgeeSave",
            data:$("#form").serialize(),
            async: false,
            success:function(json){
                $("#degreeId").val(json.data.degreeId);
                record = json.data.degreeId;
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