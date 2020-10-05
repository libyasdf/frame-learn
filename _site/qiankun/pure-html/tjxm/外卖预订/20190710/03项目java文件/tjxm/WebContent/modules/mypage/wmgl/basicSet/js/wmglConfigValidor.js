$(function () {
    $('#form').bootstrapValidator({
        message: 'This value is not valid',
        group:".rowGroup",
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	isvalid: {
                message: '是否有效验证失败！',
                validators: {
                    notEmpty: {
                        message: '是否有效不能为空'
                    }
                }
            },
            lostCreditLimt: {
                message: '失信限制验证失败！',
                validators: {
                    notEmpty: {
                        message: '失信限制不能为空'
                    },
                    stringLength: {
                        max: 2,
                        message: '失信限制不能超过2位'
                    },
					regexp: {
                        regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
                        message: '必须是数字'
                    },
                    greaterThan: {
                        value: 1,
                        message:'必须大于0'
                    },

                }
            },
            lockTime: {
            	trigger:'change',
                message: '锁定时长验证失败！',
                validators: {
                    notEmpty: {
                        message: '锁定时长不能为空'
                    },
                    stringLength: {
                        max: 2,
                        message: '锁定时长不能超过2位'
                    },
					regexp: {
                        regexp: /^[0-9]+([.]{1}[0-9]+){0,1}$/,
                        message: '必须是数字'
                    }
                }
            },
            attentionItme: {
            	trigger:"change", 
                message: '注意事项验证失败！',
                validators: {
                	notEmpty: {
                        message: '注意事项不能为空'
                    },
                    stringLength: {
                        max: 200,
                        message: '注意事项不能超过200位'
                    }
                }
            }
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});
