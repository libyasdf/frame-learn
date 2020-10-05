$(function() {
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#superId").val(regVlaue(theRequest.superId));
    $("#name").val(decodeURI(regVlaue(theRequest.name)));
    $("#partyOrganizationName").val(decodeURI(regVlaue(theRequest.partyOrganizationName)));

    $("#id").val(regVlaue(theRequest.id));
    $("#opertation").val(theRequest.opertation);

    /**
     * 初始化页面，数据加载、渲染
     */
    var id = $("#superId").val();
    if(id != ""){

        //表单数据渲染
        var datas = {"id":id,"resId":$("#resId").val()};
        httpRequest("get","/djgl/ddjs/sqrgl/fzdx/edit",datas,function (data){
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
        $('#id').val(data);
        //		var index = parent.layer.getFrameIndex(window.name);
//		parent.layer.close(index);
        // 刷新列表
        parent.parent.TableInit.refTable('right_table');

    }
}


/**
 * 保存
 */
function saveForm(){
    var theRequest = GetRequest();
    var typeOfPersonnel =regVlaue(theRequest.typeOfPersonnel);
    var record = "";
    var res = "";
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
        $.ajax({
            type: "POST",
            url: "/djgl/ddjs/sqrgl/fzdx/saveYbdysy?typeOfPersonnel="+typeOfPersonnel,
            data:$("#form").serialize(),
            async: false,
            success:function(json){
                record = json.data.id;
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