
	//校验考试基本信息
	$('#form').bootstrapValidator({
        message: 'This value is not valid',
        group:".rowGroup",
        excluded:[":disabled"],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
            	trigger:"change",
                validators: {
                    notEmpty: {
                        message: '考试名称不能为空！'
                    },
                    stringLength:{
						max:200,
						message:'考试名称不能超过200字!'
					}
                }
            },
			timeRange: {
				trigger:"change",//监听onchange事件
				validators: {
					notEmpty: {
						message: '考试时间不能为空！'
					},
					callback: {
						message: '开始时间必须大于当天时间!',
						callback: function (value, validator, $field) {
							var start = value.substr(0, (value.length + 1) / 2 - 1).trim();
							var startDate = new Date(start.replace("-", "/").replace("-", "/"));
							var today = new Date();
							if (startDate > today) {
								return true;
							}
							return false;
						}

					}
				}
			},
			answerTime: {
				trigger:"change",//监听onchange事件
				validators: {
					notEmpty: {
						message: '答题时长不能为空！'
					},
					greaterThan: {
					    value : 1,
					    message : '最小输入1'
					},
					regexp: {
                        regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
                        message: '必须是数字!'
                    },
                    stringLength:{
						max:3,
						message:'答题时长不能超过3个字!'
					}
				}
			},
			difficultyLevel: {
				trigger:"change",
				validators: {
					notEmpty: {
						message: '难易程度不能为空！'
					}
				}
			},
			testNotice:{
				trigger:"change",
				validators: {
					stringLength:{
						max:1000,
						message:'考试须知不能超过1000字!'
					}
				}
			},
			passScore:{
				trigger:"change",
				validators: {  
					notEmpty: {
						message: '及格分数不能为空！'
					},
					regexp: {
						regexp: /^[0-9]+([.][0-9]+){0,1}$/,
						message: '必须是数字!'
					},
					callback: {
						message: '及格分数必须小于考试分数!',
						callback: function (value, validator, $field) {
							var fullScore = validator.getFieldElements('full').val();
							var full;
							if(fullScore!=""){
								full = parseInt(fullScore);
							}else{
								full = 0;
							}
							if (parseInt(value) <full ) {
								return true;
							}
							return false;
						}
					}
				}
			},
			full:{
				trigger:"change",
				validators: {  
					notEmpty: {
						message: '考试分数不能为空！'
					},
					regexp: {
						regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
						message: '必须是数字!'
					}
				}  
			},
	        submitHandler: function (validator, form, submitButton) {
	        	alert('submit');
	        }
        }
    });
	//校验考试基本信息
	$('#form3').bootstrapValidator({
		message: 'This value is not valid',
		group:'.rowGroup',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			requirement: {
				trigger:"change",
				validators: {
					stringLength:{
						max:1000,
						message:'人员要求不能超过1000字!'
					}
				}
			},
			unitName: {
				trigger:"change",
				validators: {
					notEmpty : {
						message : '选择处室不能为空！'
					} 
				}
			},
			testToDepartment: {
				validators: {
					notEmpty : {
						message : '是否上报考试人员为必选项！'
					} 
				}
			},
			submitHandler: function (validator, form, submitButton) {
				alert('submit');
			}
		}
	});
	


