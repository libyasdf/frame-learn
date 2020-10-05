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
        fields: {
            reportOverDate: {
                trigger:"change",
                message: '上报截止日期验证失败！',
                validators: {
                    notEmpty: {
                        message: '上报截止日期不能为空'
                    }
                }
            },
            promptMessageContent: {
                message: '上报催办内容验证失败！',
                validators: {
                    notEmpty: {
                        message: '上报催办内容不能为空！'
                    },stringLength: {
                        max: 500,
                        message: '上报催办内容不能超过500位'
                    }
                }
            },
            unitName: {
                trigger:"change",
                message: '上报单位验证失败！',
                validators: {
                    notEmpty: {
                        message: '请选择需要上报的单位！'
                    }
                }
            },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
        }
    });
});
