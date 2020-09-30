function formValidator(){
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
        	newMaintitle: {
                validators: {
                    notEmpty: {
                        message: '新主管人不能为空！'
                    }
                }
            },
            changeDate: {
				trigger:"change",//监听onchange事件
				validators: {
					notEmpty: {
						message: '变更日期不能为空！'
					}
				}
			},
			liJuanDanWei: {
				trigger:"change",//监听onchange事件
				validators: {
					notEmpty: {
						message: '新主管单位不能为空！'
					}
				}
			},
			changeNo: {
				validators: {
					notEmpty: {
						message: '变更单号不能为空！'
					}
				}
			},
			registerDate: {
				trigger:"change",//监听onchange事件
				validators: {
					notEmpty: {
						message: '登记日期不能为空！'
					}
				}
			},
			remark: {
				validators: {
					notEmpty: {
						message: '变更缘由不能为空！'
					},
					stringLength:{
						max:1000,
						message:'变更缘由不能超过1000字！'
					}
				}
			},
	        submitHandler: function (validator, form, submitButton) {
	        	alert('submit');
	        }
        }
    });
	
}
