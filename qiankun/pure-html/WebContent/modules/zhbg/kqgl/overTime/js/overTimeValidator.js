/**
 * notEmpty：非空验证 stringLength：字符串长度验证 regexp：正则表达式验证；
 * emailAddress：邮箱地址验证（都不用我们去写邮箱的正则了~~） between：验证输入值必须在某一个范围值以内，比如大于10小于100；
 * creditCard：身份证验证； date：日期验证； ip：IP地址验证； numeric：数值验证； phone：电话号码验证；
 * uri：url验证；
 * 
 * 注：对于类似选择人员部门的控件进行校验，需要添加trigger:"change"监听事件，同时，在选择回填的时候需手动触发onchange事件；
 */
$(function() {
	$('#form').bootstrapValidator({
		message : 'This value is not valid',
		 group:".rowGroup",
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		// excluded:[":hidden",":disabled",":not(visible)"]
		// ,//bootstrapValidator的默认配置（对这三种元素不进行校验）
		fields : {
			overTimeDate : {
				trigger : "change", // 监听onchange事件
				validators : {
					notEmpty : {
						message : '加班日期不能为空'
					}
				}
			},
			startStopTime : {
				trigger : "change", // 监听onchange事件
				validators : {
					/*notEmpty : {
						message : '加班结束时间不能为空'
					}*/
					 callback: {
	                        callback : function(value, validator, $field) {
	                        	if(value!=''){
	                        		var date = value.split("-");
	                        		var oDate1 = new Date("2018-08-13 " + date[0]);
	                        		var oDate2 = new Date("2018-08-13 " + date[1]);
	 	                            if(oDate1>oDate2){
	 	                            	return {valid:false,message:'开始时间不能大于结束时间'}
	 	                            }else{
	 	                            	return {valid:true,message:'开始时间不能大于结束时间'}
	 	                            }
	                        	}else{
	                        		return {valid:false,message:'加班起止时间不能为空'}
	                        	}
	                        }
	                  }
				}
			},
			longTimeh : {
				trigger : "change", // 监听onchange事件
				validators : {
					notEmpty : {
						message : '加班时长不能为空'
					}
				}
			},
			longTimed : {
				trigger : "change", // 监听onchange事件
				validators : {
					notEmpty : {
						message : '加班时长不能为空'
					}
				}
			},
			destination : {
				trigger : "change", // 监听onchange事件
				validators : {
					notEmpty : {
						message : '目的地不能为空'
					}
				}
			},
			reason: {
	                validators: {
	                    stringLength: {
	                        max: 200,
	                        message: '加班事由不能超过200字'
	                    }
	                }
	          }
		},
		submitHandler : function(validator, form, submitButton) {
			comitForm();
		}
	});
});