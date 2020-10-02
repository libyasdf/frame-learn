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
            grantTime: {
                trigger:"change", //监听onchange事件
                validators : {
                    notEmpty : {
                        message : '授予时间不能为空！'
                    }


                },
            },

            grantUnit:{
                validators: {
                    stringLength: {
                        max: 50,
                        message: '授予单位长度不能超过50！'
                    }
                }
            },

            mainDeeds: {
                validators: {
                    stringLength: {
                        max: 500,
                        message: '主要事迹长度不能超过500！'
                    }
                }
            },

            basicSituation: {
                validators: {
                    stringLength: {
                        max: 500,
                        message: '党组织（党员）基本情况长度不能超过500！'
                    }
                }
            },

            unitOpinion: {
                validators: {
                    stringLength: {
                        max: 50,
                        message: '推荐单位意见长度不能超过50！'
                    }
                }
            },

            approvalOpinion: {
                validators: {
                    stringLength: {
                        max: 500,
                        message: '上级党委审批意见长度不能超过500！'
                    }
                }
            },

            typeval: {
                validators: {
                    notEmpty: {
                        message: '类型不能为空！'
                    }
                }
            }
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});