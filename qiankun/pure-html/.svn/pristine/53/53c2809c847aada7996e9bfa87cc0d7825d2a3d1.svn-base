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
      //  excluded:[":hidden",":disabled",".isNotValidate"] ,//bootstrapValidator的默认配置（对这三种元素不进行校验）
        fields: {
            borrowUserName: {
                trigger:"change",
                message: '借阅人验证失败！',
                validators: {
                    notEmpty: {
                        message: '借阅人不能为空！'
                    },
                    stringLength: {
                        max: 50,
                        message: '借阅人长度不能超过50！'
                    }
                }
            },
            borrowUnitName: {
            	trigger:"change", //监听onchange事件
                message: '借阅单位验证失败！',
                validators: {
                    notEmpty: {
                        message: '借阅单位不能为空！'
                    },
                    stringLength: {
                        max: 50,
                        message: '借阅单位长度不能超过50！'
                    }
                }

            },
            usePurpose: {
                message: '利用目的验证失败！',
                validators: {
                    notEmpty: {
                        message: '利用目的不能为空！'
                    },
                    stringLength: {
                        max: 200,
                        message: '利用目的长度不能超过200！'
                    }
                }
            },
            phone: {
                message: '电话验证失败！',
                validators: {
                    notEmpty: {
                        message: '电话不能为空！'
                    },
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: '电话号码只能是数字'
                    },
                    stringLength: {
                        max: 50,
                        message: '电话长度不能超过50！'
                    }
                }
            },
            approveUnitName: {
                trigger:"change",
                message: '审批单位验证失败！',
                validators: {
                    notEmpty: {
                        message: '审批单位不能为空！'
                    },
                    stringLength: {
                        max: 50,
                        message: '审批单位长度不能超过50！'
                    }
                }
            },
            approveUserName: {
                trigger:"change",
                message: '审批人验证失败！',
                validators: {
                    notEmpty: {
                        message: '审批人不能为空！'
                    },
                    stringLength: {
                        max: 50,
                        message: '审批人长度不能超过50！'
                    }
                }
            },
            remark: {
                message: '借阅内容说明验证失败！',
                validators: {
                    stringLength: {
                        max: 1000,
                        message: '借阅内容说明长度不能超过1000！'
                    }
                }
            }
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});