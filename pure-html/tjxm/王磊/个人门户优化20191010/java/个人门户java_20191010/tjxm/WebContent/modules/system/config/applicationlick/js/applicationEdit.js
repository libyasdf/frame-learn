$(function(){
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.oper);
	//渲染图片选择
	showImage("yydh",1);
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/system/config/application/edit",datas,function (data){
			DisplayData.playData({data: data});
			if($("#opertation").val() == "VIEW"){
				if(data.data.path){
					$("#showPath").attr("src",data.data.path);
				}
			}
		});
	}
})


/**
 * 提交表单
 * @returns
 */
function comitForm(){
	var data = saveForm();
	if(data){
		layer.msg("保存成功！", {icon: 1});
		//刷新列表
		parent.TableInit.refTable('right_table');
	}
}

/**
 * 保存
 */
function saveForm(){
	var application = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
    	//TODO 如果没有id
    	/*if(!$("#id").val()){
    		$("#subflag").val(Config.startflag);
    	}*/
    	$.ajax({
    		type: "POST",
    		url:"/system/config/application/saveForm",
    		data:$("#form").serialize(),
    		async: false,
    		success:function(json){
    			if (json) {
    				application = json.id;
    				$("#id").val(json.id);
    			}
    		}
    	});
    }
    return application;
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

/**
 * 获取应用导航在数据字典中
 * @param mark
 * @param type
 */
function showImage(mark,type){
	 $.ajax({
         type:"get",
         url:"/system/config/dictionary/getListByMark",
         data:{
         	"mark":mark,
         	"type":type
         },
         dataType:"json",
         async:false,
         success:function(data){
             if(data.flag == "1"){
            	 if(data.data){
            		 var imageList = data.data;
            		 if (imageList && imageList.length > 0) {
            			 for (var i = 0; i < imageList.length; i++) {
            				 $("#path").append("<label class='radio-inline'><input type='radio'  name='path' value='"
            						 +imageList[i].code+"'><img src='"
            						 +imageList[i].name+"' width='30%' /></label>");
                             // if (i % 2 != 0 && i != imageList.length - 1) {
                              //    $("#path").append("</br>");
							 // }
            			 }
            		 }
            	 }
             }
         },
         error:function(){
         	layer.msg("加载字典项异常！请刷新重试！",{icon:2});
         }
     })
}






