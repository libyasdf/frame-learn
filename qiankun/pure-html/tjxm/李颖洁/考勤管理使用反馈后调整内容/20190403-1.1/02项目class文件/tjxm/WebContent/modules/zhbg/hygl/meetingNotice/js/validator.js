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
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
      //excluded:[":hidden",":disabled",":not(visible)"] ,//bootstrapValidator的默认配置（对这三种元素不进行校验）
        fields: {
        	meetingName: {
        		trigger:'change',
	        	message: '会议名称验证失败',
	            validators: {
	            	notEmpty: {
	                    message: '会议名称不能为空'
	                }
	            }
	        },
	       /* attendDeptJu: {
	        	trigger : "change",
	        	message: '参会单位(部门)验证失败',
	            validators: {
	            	notEmpty: {
	                    message: '参会部门不能为空'
	                }
	            	//参会单位（部门）和参会单位（处） 一个不为空就可以
            	 callback: {
	                callback : function(value, validator, $field) {
	                	if($("#attendDeptJu").val()!='' ||$("#attendDept").val()!=''){
	                		return {valid:true,message:""}
	                	}else{
	                		return {valid:false,message:"参会单位（部门）或 参会单位（处）不能为空！"}	
	                	}
                        }
                  }
	            }
	        },*/
	        /*attendDept: {
	        	trigger : "change",
	        	message: '参会单位（处）验证失败',
	            validators: {
	            	notEmpty: {
	                    message: '参会单位（处）不能为空'
	                }
	            }
	        },*/
	        fankuiType : {
				trigger : "change", // 监听onchange事件
				message : '反馈类别验证失败',
				validators : {
					/*notEmpty : {
						message : '接待费用不能为空'
					},*/
					/*regexp: {
						//([0-9]{1,}[.]?[0-9]*)
                        regexp: /^([1-9]\d*(\.\d*[1-9])?)|(0\.\d*[1-9])$/,
                        message: '必须是大于0的数字'
                    },*/
                    callback: {
                        callback : function(value, validator, $field) {
                        	var aa= $("input[name='isFankui']:checked").val();
                        	if(aa=='1'){
                        		if(value){
                        			return {valid:true,message:""}	
                        		}else{
                        			return {valid:false,message:"反馈类别不能为空"}
                        		}
                        	}
                        }
                  }
				}
			},
			responseTime: {
				trigger:'change',
	        	message: '反馈时间验证失败',
	            validators: {
	            	callback: {
                        callback : function(value, validator, $field) {
                        	var aa= $("input[name='isFankui']:checked").val();
                        	if(aa=='1'){
                        		if(value){
                        			return {valid:true,message:""}	
                        		}else{
                        			return {valid:false,message:"最晚反馈时间不能为空"}
                        		}
                        	}
                        }
                  }
	            }
	        },
	        contents: {
              	 message: '会议通知验证失败',
                  validators: {
                      notEmpty: {
                          message: '会议通知不能为空'
                      },
                      stringLength: {
                          max: 2000,
                          message: '会议通知内容不能超过2000位'
                      }
                  }
             },
             remark: {
              	 message: '备注验证失败',
                  validators: {
                      stringLength: {
                          max: 200,
                          message: '备注不能超过200位'
                      }
                  }
             }
        },
       
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});