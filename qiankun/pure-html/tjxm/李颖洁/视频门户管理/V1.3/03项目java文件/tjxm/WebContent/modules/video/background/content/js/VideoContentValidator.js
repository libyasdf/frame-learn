/**
 * notEmpty：非空验证
 * stringLength：字符串长度验证
 * regexp：正则表达式验证；
 * emailAddress：邮箱地址验证（都不用我们去写邮箱的正则了~~）
 * between：验证输入值必须在某一个范围值以内，比如大于10小于100；
 * creditCard：身份证验证；
 * date：日期验证；
 * ip：IP地址验证；
 * numeric：数值验证；
 * phone：电话号码验证；
 * uri：url验证；
 * 
 * 注：对于类似选择人员部门的控件进行校验，需要添加trigger:"change"监听事件，同时，在选择回填的时候需手动触发onchange事件；
 */
$(function () {
    $('#form').bootstrapValidator({
        message: 'This value is not valid',
        group:".rowGroup",
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
      //excluded:[":hidden",":disabled",":not(visible)"] ,//bootstrapValidator的默认配置（对这三种元素不进行校验）
        fields: {
            title: {
                message: '标题验证失败！',
                validators: {
                    notEmpty: {
                        message: '标题不能为空'
                    },
                    stringLength: {
                        max: 30,
                        message: '标题长度不能超过30位'
                    }
                }
            },
            subtitle:{
                message:'副标题验证失败',
                validators: {
                    stringLength: {
                        max: 50,
                        message: '副标题长度不能超过50位'
                    }
                }
            },
            author:{
                message:'作者验证失败',
                validators: {
                    stringLength: {
                        max: 10,
                        message: '作者长度不能超过10位'
                    }
                }
            },
            source: {
                message:'来源验证失败',
                validators: {
                    stringLength: {
                        max: 20,
                        message: '来源长度不能超过20位'
                    }
                }
            },
            image: {
                message:'视频封面验证失败',
                validators: {
                	notEmpty: {
                        message: '视频封面不能为空'
                    }
                }
            },
            showTime : {
				trigger : "change", // 监听onchange事件
				validators : {
					/*notEmpty : {
						message : '加班结束时间不能为空'
					}*/
					 callback: {
	                        callback : function(value, validator, $field) {
								if(value!=''){
	                        		var date = value.split(" - ");
	 	                            if( date[0] > date[1]){
	 	                            	return {valid:false,message:'开始时间必须小于结束时间'}
	 	                            }else{
	 	                            	return {valid:true,message:'开始时间必须小于结束时间'}
	 	                            }
	                        	}else{
	                        		
	                        		return {valid:true,message:'开始时间必须小于结束时间'}
	                        	}
	                        }
	                  }
				}
			},
          
            submitHandler: function (validator, form, submitButton) {
                alert("submit");
            }
        }
    });
});