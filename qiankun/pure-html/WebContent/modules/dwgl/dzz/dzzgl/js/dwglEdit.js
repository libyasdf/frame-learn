$(function(){
	
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.oper);
    $("#dwxtOrgId").val(theRequest.dwxtOrgId);

    if(regVlaue(theRequest.orgType)!="631"){
        var div1 = document.getElementById("yc1");
        div1.style.display = "none";
        var div2 = document.getElementById("yc2");
        div2.style.display = "none";
    }
  

    //初始化字典项
    Dictionary.init({position:"grassrootOrg",mark:"dwgl_grassrootOrg",type:"1",name:"grassrootOrg",domType:"select"});

    var unitBelong = {
        mark:"dwgl_unitBelong",        //字典类型唯一码值
        type:"1"		//要初始化的数据类型：1字典项；0字典类型
    };
    var  unitBelongMap = Dictionary.getNameAndCode(unitBelong);
    for(var key in unitBelongMap){
        $("#unitBelongText").val(unitBelongMap[key]);
        $("#unitBelong").val(key);
        break;
    }

    var unitAttr = {
        mark:"dwgl_unitAttr",        //字典类型唯一码值
        type:"1"		//要初始化的数据类型：1字典项；0字典类型
    };
    var  unitAttrMap = Dictionary.getNameAndCode(unitAttr);
    for(var key in unitAttrMap){
        $("#unitAttrText").val(unitAttrMap[key]);
        $("#unitAttr").val(key);
        break;
    }

    var trade = {
        mark:"dwgl_trade",        //字典类型唯一码值
        type:"1"		//要初始化的数据类型：1字典项；0字典类型
    };
    var  tradeMap = Dictionary.getNameAndCode(trade);
    for(var key in tradeMap){
        $("#tradeText").val(tradeMap[key]);
        $("#trade").val(key);
        break;
    }

    /**
     * 初始化页面，数据加载、渲染
     */
    if($("#id").val() != ""){
        var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
        httpRequest("get","/djgl/ddjs/dzz/dzzgl/findOneUnit",datas,function (data){
            if($("#opertation").val() == "VIEW"){
                data.data.unitBelong = Dictionary.getNameAndCode({mark:"dwgl_unitBelong",type:"1"})[data.data.unitBelong];
                data.data.unitAttr = Dictionary.getNameAndCode({mark:"dwgl_unitAttr",type:"1"})[data.data.unitAttr];
                data.data.trade = Dictionary.getNameAndCode({mark:"dwgl_trade",type:"1"})[data.data.trade];
                data.data.grassrootOrg = Dictionary.getNameAndCode({mark:"dwgl_grassrootOrg",type:"1"})[data.data.grassrootOrg];
            }
            DisplayData.playData({data:data});
        });
    }


})

function save() {
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
        $.ajax({
            type: "POST",
            url:"/djgl/ddjs/dzz/dzzgl/saveUnit",
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