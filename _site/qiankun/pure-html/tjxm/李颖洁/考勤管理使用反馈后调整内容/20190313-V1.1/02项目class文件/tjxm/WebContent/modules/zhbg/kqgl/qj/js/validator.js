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
        	/*leaveTitle: {
                message: '标题验证失败',
                validators: {
                    notEmpty: {
                        message: '标题不能为空'
                    },
                    stringLength: {
                        max: 18,
                        message: '标题长度必须不能超过18位'
                    }
                }
            },*/
            leaveType: {
                message: '请假类型验证失败！',
                validators: {
                    notEmpty: {
                        message: '请假类型不能为空'
                    }
                }
            },
           leaveStartDate: {
        	   trigger:"change", //监听onchange事件
             	 message: '请假开始日期验证失败',
                 validators: {
                     notEmpty: {
                         message: '请假开始日期不能为空'
                     }
                 }
            },
            startAmOrPm: {
				message: '请假开始上午下午验证失败',
				validators: {
					notEmpty: {
						message: '请假开始上午下午不能为空'
					}
				}
          	},
	        leaveEndDate: {
	        	trigger:'change',
	        	message: '请假结束日期验证失败',
	            validators: {
	            	notEmpty: {
	                    message: '请假结束日期不能为空'
	                }
	            }
	        },
            endAmOrPm: {
              	 message: '请假结束上午下午验证失败',
                  validators: {
                      notEmpty: {
                          message: '请假结束上午下午不能为空'
                      }
                  }
             },
             leaveStartTime: {
              	 message: '请假开始时间验证失败',
                  validators: {
                      notEmpty: {
                          message: '请假开始时间不能为空'
                      }
                  }
             },
             leaveEndTime: {
              	 message: '请假结束时间验证失败',
                  validators: {
                      notEmpty: {
                          message: '请假结束时间不能为空'
                      }
                  }
             },
             leaveLongTime: {
            	 trigger:"change",
              	 message: '请假时长验证失败',
                  validators: {
                      notEmpty: {
                          message: '请假时长不能为空'
                      },
                      regexp: {
                          regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
                          message: '必须是数字'
                      }
                  }
             },
             leaveReason: {
              	 message: '请假事由验证失败',
                  validators: {
                	  notEmpty: {
                          message: '请假事由不能为空'
                      },
                	  stringLength: {
                          max: 200,
                          message: '请假事由长度不能超过200'
                      }
                  }
             },
             idea: {
            	 trigger:"change",
              	 message: '意见验证失败',
                  validators: {
                	  stringLength: {
                          max: 200,
                          message: '意见长度不能超过200'
                      }
                  }
             }
             
        },
       
          
       
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});