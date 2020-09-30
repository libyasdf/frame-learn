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
            rewardTime: {
                trigger:"change", //监听onchange事件
                message: '奖励日期验证失败！',
                validators: {
                    notEmpty: {
                        message: '奖励日期不能为空！'
                    }
                }
            },
            rewardUnit:{
                message: '奖励机构验证失败！',
                validators: {
                    stringLength: {
                        max:50,
                        message: '奖励机构长度不能超过50！'
                    }
                }
            },
            rewardContent:{
                message: '奖励内容验证失败！',
                validators: {
                    stringLength: {
                        max:50,
                        message: '奖励内容长度不能超过50！'
                    }
                }
            },
            rewardReason:{
                message: '奖励原因验证失败！',
                validators: {
                    stringLength: {
                        max:50,
                        message: '奖励原因长度不能超过50！'
                    }
                }
            },
            approvalOrganLevel:{
                message: '批准机关级别验证失败！',
                validators: {
                    stringLength: {
                        max:50,
                        message: '批准机关级别长度不能超过50！'
                    }
                }
            },
            mainDeeds:{
                message: '主要事迹验证失败！',
                validators: {
                    stringLength: {
                        max:500,
                        message: '主要事迹长度不能超过500！'
                    }
                }
            },
            rewardRevokeTime:{
                message: '奖励撤销日期验证失败！',
                validators: {
                    stringLength: {
                        max:50,
                        message: '奖励撤销日期长度不能超过50！'
                    }
                }
            },
            remarks:{
                message: '备注验证失败！',
                validators: {
                    stringLength: {
                        max:500,
                        message: '备注长度不能超过500！'
                    }
                }
            },
            submitHandler: function (validator, form, submitButton) {
                alert("submit");
            }
        }
    });
});