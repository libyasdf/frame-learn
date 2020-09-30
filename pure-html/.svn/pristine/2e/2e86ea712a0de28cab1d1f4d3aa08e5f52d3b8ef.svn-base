$(function(){
	
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(theRequest.id);

})
function commitForm(flag) {
    var data = saveForm();
    if (data) {
        layer.msg("保存成功！", {
            icon : 1
        });
        // 刷新列表
        parent.TableInit.refTable('right_table');

    }
}


/**
 * 保存
 */
function saveForm(){
    var res = "";
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
        $.ajax({
            type: "POST",
            url: "/djgl/ddjs/dzz/hjxj/updateLdcy",
            data:$("#form").serialize(),
            async: false,
            success:function(json){
                if (json.flag == '1') {
                    res = json.flag;
                    $("#id").val(json.data.id);
                   
                }
            },
            error:function(){
            }
        });
        //保存临时意见
        /*var tempIdea = $("#idea").val();
         saveIdeaTemp($("#workitemid").val(),tempIdea)*/;
    }
    console.log(res);
    return res;
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

//@sourceURL=AAA.JS