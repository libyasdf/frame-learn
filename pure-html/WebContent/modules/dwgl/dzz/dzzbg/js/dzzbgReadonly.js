$(function(){
	
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.oper);


    /**
     * 初始化页面，数据加载、渲染
     */
    if($("#id").val() != ""){
        var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
        httpRequest("get","/djgl/ddjs/dzz/dzzbg/findOne",datas,function (data){
            DisplayData.playData({data:data});
        });
    }


})


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