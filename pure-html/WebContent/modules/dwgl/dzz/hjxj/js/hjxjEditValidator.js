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
            sessionNumber: {
                message: '届数验证失败！',
                validators: {
                    stringLength: {
                        max: 50,
                        message: '届数长度不能超过50！'
                    }
                }
            },
            limitYear: {
                message: '任期年限验证失败！',
                validators: {
                    notEmpty: {
                        message: '任期年限不能为空！'
                    }
                }
            },
            changeTime: {
                message: '换届时间验证失败！',
                trigger:'change',
                validators: {
                    notEmpty: {
                        message: '换届时间不能为空！'
                    }
                }
            },
            modality: {
                message: '换届选举形式验证失败！',
                validators: {
                    notEmpty: {
                        message: '换届选举形式不能为空！'
                    }
                }
            },
            shouldNumber: {
                validators: {
                    regexp: {
                        regexp:/^[+]{0,1}(\d+)$/,
                        message: '应到会人数不能为非正整数！'
                    },
                    stringLength: {
                        max:5,
                        message: '应到会人数长度不能超过5！'
                    }
                }
            },
            actualNumber: {
                validators: {
                    regexp: {
                        regexp:/^[+]{0,1}(\d+)$/,
                        message: '实到会人数不能为非正整数！'
                    },
                    stringLength: {
                        max:5,
                        message: '实到会人数长度不能超过5！'
                    }
                }
            },
            situation: {
                message: '选举情况验证失败！',
                validators: {
                    stringLength: {
                        max: 500,
                        message: '选举情况长度不能超过500！'
                    }
                }
            }

        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});