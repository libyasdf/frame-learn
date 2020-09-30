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
        	/*reDeptName: {
            	validators: {
                	notEmpty : {
						message : '部门名称不能为空!'
					},
					stringLength: {
						max: 20,
						message: '部门名称不能超过20字!'
					}
                  
                }
            },*/
            reUserName: {
            	trigger:'change',
                validators: {
                	notEmpty : {
						message : '人员姓名不能为空!'
					},
					stringLength: {
						max: 20,
						message: '人员姓名不能超过20字!'
					}
                  
                }
            },
            identity: {
                validators: {
                	notEmpty : {
						message : '身份证号不能为空!'
					},
					stringLength: {
						max: 20,
						message: '身份证号不能超过20字!'
					}
                  
                }
            },
            startDate: {
            	trigger : "change",
                validators: {
                	notEmpty : {
						message : '起始时间不能为空!'
					}
                  
                }
            },
            endDate: {
            	trigger : "change",
                validators: {
                	notEmpty : {
						message : '截止日期不能为空!'
					}
                  
                }
            },
            recordInfo: {
                validators: {
                	notEmpty : {
						message : '履历内容不能为空!'
					},
					stringLength: {
						max: 300,
						message: '履历内容不能超过300字!'
					}
                  
                }
            }
            
            
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});