/**
 * notEmpty：非空验证 stringLength：字符串长度验证 regexp：正则表达式验证；
 * emailAddress：邮箱地址验证（都不用我们去写邮箱的正则了~~） between：验证输入值必须在某一个范围值以内，比如大于10小于100；
 * creditCard：身份证验证； date：日期验证； ip：IP地址验证； numeric：数值验证； phone：电话号码验证；
 * uri：url验证；
 * 
 * 注：对于类似选择人员部门的控件进行校验，需要添加trigger:"change"监听事件，同时，在选择回填的时候需手动触发onchange事件；
 */
$(function () {
    $('#form').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        group:".row-group",
      // excluded:[":hidden",":disabled",":not(visible)"]
		// ,//bootstrapValidator的默认配置（对这三种元素不进行校验）
        fields: {
        	attendPersonNum: {
				message : '参会人数验证失败',
				validators : {
					notEmpty : {
						message : '参会人数不能为空'
					},
					regexp: {
                        regexp: /^[0-9]{0,3}$/,
                        message: '参会人数是整数且不能超过3位数'
                    }
				}
        	},
        	shouldAttendNum: {
        		message : '应参会人数验证失败',
        		validators : {
        			notEmpty : {
        				message : '应参会人数不能为空'
        			},
        			regexp: {
        				regexp: /^[0-9]{0,3}$/,
        				message: '参会人数是整数且不能超过3位数'
        			}
        		}
        	},
        	realAttendNum: {
        		trigger:"change",
        		message : '实参会人数验证失败',
        		validators : {
        			notEmpty : {
        				message : '实参会人数不能为空'
        			},
        			regexp: {
        				// ([0-9]{1,}[.]?[0-9]*)
        				regexp: /^[0-9]{0,3}$/,
        				message: '实参会人数是整数且不能超过3位数'
        			}
        		}
        	},
        	/*attendPersonName: {
	        	trigger:"change", // 监听onchange事件
				message : '参会人验证失败',
				validators : {
					callback: {
			            callback : function(value, validator, $field) {
			            	if($("#attendPersonChuId").val()){
			            		if($("#attendPersonId").val().indexOf($("#attendPersonChuId").val())>=0){
			                		return {valid:false,message:"参会人不能跟处长参会人重复"}
			                	}else{
			                		return {valid:true,message:""}	
			                	}
			
			            	}else{
			            		return {valid:true,message:""}	
			            	}
			            	                
			            }
					}
				}
        	}*/
        	/*attendPersonName: {
	        	trigger:"change", // 监听onchange事件
				message : '参会人验证失败',
				validators : {
					notEmpty : {
        				message : '至少添加一项正常参会人员'
        			},
				}
        	},*/
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});