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
			goodsName : {
				trigger : "change", // 监听onchange事件
				validators : {
					notEmpty : {
						message : '商品名称不能为空'
					},
					 stringLength: {
	                        max: 20,
	                        message: '商品名称不能超过20位'
	                 }
				}
			},
			price : {
				trigger : "change", // 监听onchange事件
				validators : {
					regexp: {
	                        regexp: /^\d*(\.\d{1})?$/,
	                        message: '单价必须为数字且最多保留一位小数'
	                 },
					notEmpty : {
						message : '单价不能为空'
					},
					stringLength: {
                        max: 10,
                        message: '单价不能超过10位'
                 }
				}
			},
			valuationUnit : {
				trigger : "change", // 监听onchange事件
				validators : {
					notEmpty : {
						message : '计价单位不能为空'
					},
					stringLength: {
                        max: 5,
                        message: '计价单位不能超过5位'
                 }
				}
			},
			buyUnit : {
				trigger : "change", // 监听onchange事件
				validators : {
					notEmpty : {
						message : '购买单位不能为空'
					},
					stringLength: {
                        max: 5,
                        message: '购买单位不能超过5位'
                 }
				}
			},
			describe: {
                validators: {
                    stringLength: {
                        max: 200,
                        message: '描述不能超过200字'
                    }
                }
            },
			mark: {
	                validators: {
	                    stringLength: {
	                        max: 400,
	                        message: '备注不能超过400字'
	                    }
	                }
	          },
          
          amountNum: {
        	  trigger : "change", 
				validators : {
					notEmpty: {
            			message: '库存数量不能为空'
            		},
					regexp: {
                        regexp: /^[0-9]{0,10}$/,
                        message: '库存数量必须是整数且不能超过10位'
	                 },
				}
          },
          buyNum: {
        	  trigger : "change", 
				validators : {
					notEmpty: {
            			message: '最多购买数量不能为空'
            		},
					regexp: {
                        regexp: /^[0-9]{0,10}$/,
                        message: '最多购买数量必须是整数且不能超过10位'
	                 },
				}
          }
		},
		submitHandler : function(validator, form, submitButton) {
			comitForm();
		}
	});
});