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
            startTime: {
                validators : {
                    notEmpty : {
                        message : '开始时间不能为空！'
                    }
                },
            },
            endTime: {
                validators : {
                    notEmpty : {
                        message : '结束时间不能为空！'
                    }
                },
            },
            reviewResults: {
                validators: {
                    notEmpty: {
                        message: '评议结果不能为空！'
                    }
                }
            },
            partyOrganizationOpinion: {
                validators: {
                    stringLength: {
                        max:500,
                        message: '党组织意见长度不能超过500！'
                    }
                }
            },
            reviewReason: {
                validators: {
                    stringLength: {
                        max:500,
                        message: '评议原因长度不能超过500！'
                    }
                }
            },
            handleReasonType: {
                validators: {
                    stringLength: {
                        max:500,
                        message: '处理原因类型长度不能超过50！'
                    }
                }
            },
            handleReason: {
                validators: {
                    stringLength: {
                        max:500,
                        message: '处理原因长度不能超过500！'
                    }
                }
            },
            handleSituation: {
                validators: {
                    stringLength: {
                        max:500,
                        message: '处理情况长度不能超过500！'
                    }
                }
            },
            memo: {
                validators: {
                    stringLength: {
                        max:500,
                        message: '备注长度不能超过500！'
                    }
                }
            }
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});