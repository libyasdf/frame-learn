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
			businessTripTitle : {
				message : '标题验证失败',
				validators : {
					notEmpty : {
						message : '标题不能为空'
					},
					stringLength : {
						max : 18,
						message : '标题长度必须不能超过18位'
					}
				}
			},
			startTime : {
				trigger : "change", // 监听onchange事件
				message : '出差日期验证失败！',
				validators : {
					notEmpty : {
						message : '出差日期不能为空'
					}
				}
			},
			destination : {
				//trigger : "change", // 监听onchange事件
				message : '目的地验证失败',
				validators : {
					notEmpty : {
						message : '目的地不能为空'
					},
					stringLength : {
						max : 20,
						message : '目的地不能超过20位'
					}
				}
			},
			vehicle : {
				//trigger : "change", // 监听onchange事件
				message : '交通工具验证失败',
				validators : {
					notEmpty : {
						message : '交通工具不能为空'
					},
					stringLength : {
						max : 20,
						message : '交通工具不能超过20位'
					}
				}
			},
			receptionFees : {
				trigger : "change", // 监听onchange事件
				message : '接待费用验证失败',
				validators : {
					/*notEmpty : {
						message : '接待费用不能为空'
					},*/
					/*regexp: {
						//([0-9]{1,}[.]?[0-9]*)
                        regexp: /^([1-9]\d*(\.\d*[1-9])?)|(0\.\d*[1-9])$/,
                        message: '必须是大于0的数字'
                    },*/
					stringLength : {
						max : 20,
						message : '接待费用不能超过20位'
					},
                    callback: {
                        callback : function(value, validator, $field) {
                        	var aa= $("input[name='isHaveReceptionFees']:checked").val();
                        	if($("input[name='isHaveReceptionFees']:checked").val()=='0'){
                        		return {valid:true,message:""}	
                        	}else{
                        		var reg=/^([1-9]\d*(\.\d*[1-9])?)|(0\.\d*[1-9])$/;
                        		if(!value.match(reg)){
                        			return {valid:false,message:"必须是大于0的数字"}
                        		}else{
                        			return {valid:true,message:""}
                        		}
                        	}
                        }
                  }
				}
			},
			reason:{
				message:'出差事由长度验证失败',
				validators:{
					stringLength : {
						max : 200,
						message : '出差事由不能超过200位'
					}
					
				}
			},
			tripColleague:{
				trigger : "change", 
				message:'同行人员长度验证失败',
				validators:{
					stringLength : {
						max : 50,
						message : '同行人员长度不能超过50'
					}
			
				}
			}
		},
		submitHandler : function(validator, form, submitButton) {
			comitForm();
		}
	});
});