	//校验考试基本信息
$(function(){
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
            title: {
                validators: {
                    notEmpty: {
                        message: '文件标题不能为空！'
                    },
                    stringLength:{
						max:200,
						message:'文件标题不能超过200字！'
					}
                }
            },
            fileDept: {
            	message: '来文单位验证失败！',
				validators: {
					notEmpty: {
						message: '来文单位不能为空！'
					},
                    stringLength:{
						max:100,
						message:'来文单位不能超过100字！'
					}
				}
			},
			securityClass: {
				message: '密级验证失败！',
				validators: {
					notEmpty: {
						message: '密级不能为空！'
					}
				}
			},
			type: {
				message: '文件类型验证失败！',
				validators: {
					stringLength:{
						max:4,
						message:'文件类型不能超过4字！'
					}
				}
			},
			createdDate:{
				message: '成文日期验证失败！',
				validators: {
					notEmpty: {
						message: '成文日期不能为空！'
					},
					regexp: {
                        regexp: /^[0-9]+$/,
                        message: '必须是数字，例如：20180520！'
                    }
				}
			},
			quantity:{
				message: '份数验证失败！',
				validators: {
					regexp: {
                        regexp: /^[0-9]+$/,
                        message: '必须是数字！'
                    }
				}
			},
			pageNum:{
				message: '页数验证失败！',
				validators: {
					regexp: {
                        regexp: /^[0-9]+$/,
                        message: '必须是数字！'
                    }
				}
			},
			fileNum:{
				message: '文号验证失败！',
				validators: {
					stringLength:{
						max:100,
						message:'文号不能超过100字！'
					}
				}
			},
			note:{
				message: '备注验证失败！',
				validators: {
					stringLength:{
						max:200,
						message:'备注不能超过200字！'
					}
				}
			},
			fileTime:{
				message: '收文日期验证失败！',
				validators: {
					notEmpty: {
						message: '收文日期不能为空！'
					},
					regexp: {
                        regexp: /^[0-9]+$/,
                        message: '必须是数字，例如20180520！'
                    }
				}
			},
			serialNum:{
				message: '文件编号验证失败！',
				validators: {
					stringLength:{
						max:50,
						message:'文件编号不能超过50字！'
					}
				}
			},
			barcode:{
				message: '条码编号验证失败！',
				validators: {
					stringLength:{
						max:30,
						message:'条码编号不能超过30字！'
					}
				}
			},
			zhusUnit:{
				message: '主送单位验证失败！',
				validators: {
					stringLength:{
						max:100,
						message:'主送单位不能超过100字！'
					}
				}
			},
			fileType:{
				message: '文种验证失败！',
				validators: {
					stringLength:{
						max:10,
						message:'文种不能超过10字！'
					}
				}
			},
			urgencyDegree:{
				message: '紧急程度验证失败！',
				validators: {
					stringLength:{
						max:10,
						message:'紧急程度不能超过10字！'
					}
				}
			}
        },
        submitHandler: function (validator, form, submitButton) {
        	alert('submit');
        }
    });
});