$(function(){
	
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#dwxtOrgId").val(theRequest.dwxtOrgId);

})

function save() {
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
        layer.confirm("确定撤销该组织及以下组织吗？",{
            btn : [ '确定', '取消' ]//按钮
        },function() {
            $.ajax({
                type: "POST",
                url:"/djgl/ddjs/dzz/dzzgl/saveRevoke",
                data:$("#form").serialize(),
                dataType:"json",
                success:function(data){
                    if ('1' == data.flag) {
                        layer.msg("撤销成功！", {icon: 1,time:3000});
                        if($("#id").val() == ""){
                            $("#id").val(data.data.id);
                        }
                        parent.dzzTree.delNode($("#dwxtOrgId").val());
                        parent.refreshPage();
                    }
                    setTimeout(function(){
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);

                    },2000);
                },
                error:function(data){

                }
            })
        });
    }

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