
$(function(){

})





/**
 * 保存
 */
function saveForm(){
	var info = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	
    //手动触发验证
    bootstrapValidator.validate();
    
    if(bootstrapValidator.isValid()){
        layer.confirm("确定要添加工作计划", {
            btn: ['确定', '取消'] //按钮
        }, function () {
        	 $.ajax({
 				type: "POST",
 				url:"/mypage/workplan/saveForm",
 				data:$("#form").serialize(),
 				async: false,
 				success:function(json){
 					if (json) {
 						info = json.id;
 						$("#id").val(json.id);
 						if(json.id){
 							window.parent.colseLayer();
 						}
 					}
 				}
 		     });
        }, function(){
        	
        });
    	
    	
    }
	
	
	return info;
}



/**
 * 保存表单数据
 */
function save(){

	var data = saveForm();
	/*alert(data)
	if(data){
		
		//关闭弹框
		window.parent.colseLayer();
     }*/
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



