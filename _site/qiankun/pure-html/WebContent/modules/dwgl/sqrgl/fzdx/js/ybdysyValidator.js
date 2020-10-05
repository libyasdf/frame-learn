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
            validating: 'glyphicon gly                                      phicon-refresh'
        },
      //excluded:[":hidden",":disabled",":not(visible)"] ,//bootstrapValidator的默认配置（对这三种元素不进行校验）
        fields: {
            name: {
                validators: {
                    notEmpty: {
                        message: '姓名不能为空！'
                    },
                    stringLength: {
                        min: 2,
                        message: '用户名长度必须大于2！'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z\u4e00-\u9fa5]+$/,
                        message: '用户名不能有数字和字符！'
                    }


                },
            },
            partyOrganizationName: {
                validators: {
                    notEmpty: {
                        message: '党组织不能为空！'
                    },
                    stringLength: {
                        max: 50,
                        message: '党组织长度不能超过50！'
                    }
                }
            },
            expectedDevelopmentTime: {
                trigger: "change", //监听onchange事件
                validators: {
                    stringLength: {
                        max: 10,
                        message: '预计发展日期长度不能超过10！'
                    }
                }
            },
            actualDevelopmentTime: {
                trigger: "change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '实际发展日期不能为空！'
                    },
                    stringLength: {
                        max: 10,
                        message: '实际发展日期长度不能超过10！'
                    }
                }
            },
            introducer: {
                validators: {
                    stringLength: {
                        max: 50,
                        message: '入党介绍人长度不能超过50！'
                    }
                }
            },
            partyconsiderationTime: {
                trigger: "change", //监听onchange事件
                validators: {
                    stringLength: {
                        max: 10,
                        message: '党组织审议时间长度不能超过10！'
                    }
                }
            },
            organApprovalTime: {
                trigger: "change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '机关党委审批时间不能为空！'
                    },
                    stringLength: {
                        max: 10,
                        message: '机关党委审批时间长度不能超过10！'
                    }
                }
            }
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});