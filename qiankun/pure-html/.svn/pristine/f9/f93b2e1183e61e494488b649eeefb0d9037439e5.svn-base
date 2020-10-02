$(function(){
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.oper);
    $("#hjxjId").val(theRequest.hjxjId);
    $("#dwxtOrgId").val(theRequest.dwxtOrgId);
    $("#orgType").val(theRequest.orgType);
    //初始化字典项
    Dictionary.init({position:"duties",mark:"dwgl_duties",type:"1",name:"duties",domType:"select"});
    var mySelect=$("#duties option");
    mySelect.each(function (i,el) {
        if($("#orgType").val() == '611'){
            if($(el).val()== '09' || $(el).val()== '10'|| $(el).val()== '11'|| $(el).val()== '12'|| $(el).val()== '13'|| $(el).val()== '14') {
                $(this).remove();
            }
        }else if($("#orgType").val() == '621'){
            if($(el).val()!= '' && $(el).val()!= '09' && $(el).val()!= '10'&& $(el).val()!= '11'){
                $(this).remove();
            }
        }else if($("#orgType").val() == '631'){
            if($(el).val()!= '' && $(el).val()!= '12' && $(el).val()!= '13'&& $(el).val()!= '14'){
                $(this).remove();
            }
        }
    })

    /**
     * 初始化页面，数据加载、渲染
     */
    if($("#id").val() != ""){
        var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
        httpRequest("get","/djgl/ddjs/dzz/hjxj/findOneLdcy",datas,function (data){
            if($("#opertation").val() == "VIEW"){
                data.data.duties = Dictionary.getNameAndCode({mark:"dwgl_duties",type:"1"})[data.data.duties];
                $("#isTenure").val(data.data.isTenure);
            }
            DisplayData.playData({data:data});
        });
        if($("#opertation").val()=="VIEW"&&$("#isTenure").val()=="0"){
            var div = document.getElementById("jdwDate");
            div.style.display = "none";
            var div1 = document.getElementById("jdwNumber");
            div1.style.display = "none";
            var div2 = document.getElementById("jdwMode");
            div2.style.display = "none";
        }
    }


})

function save() {
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
        $.ajax({
            type: "POST",
            url:"/djgl/ddjs/dzz/hjxj/saveLdcy",
            data:$("#form").serialize(),
            dataType:"json",
            success:function(data){
                if ('1' == data.flag) {
                    if($("#id").val() == ""){
                        $("#id").val(data.data.id);
                    }
                    parent.refreshPage();
                    layer.msg("保存成功！", {icon: 1,time:3000}, function (index) {
                    });
                }
            },
            error:function(data){

            }
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