/**
 * 先进行数据项的验证
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
            name: {
                validators: {
                    notEmpty: {
                        message: '姓名不能为空'
                    }
                }
            },
            unitName: {
                validators: {
                    notEmpty: {
                        message: '单位不能为空'
                    }
                }
            },
            partyGovernment: {
                validators: {
                    notEmpty: {
                        message: '党政不能为空'
                    }
                }
            },
            privateNetwork: {
                validators: {
                    notEmpty: {
                        message: '专网不能为空'
                    }
                }
            },
            universalNetwork: {
                validators: {
                    notEmpty: {
                        message: '普网不能为空'
                    }
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: '手机号码不能为空'
                    }
                }
            }
        },
        // form表单提交函数
        submitHandler: function (validator, form, submitButton) {
            // method="post" action="../../mock/save.json"
            console.log("abc");
            // alert("submit");
        }
    }).on('success.form.bv', function (e) {
        comitForm();
    });
});

/**
 * 全局变量，单例模式
 * @save 保存事件
 * @submit 提交流程事件
 * @flowEnd 办结流程
 * @flowChart 流程图
 * @flowRecord 流程记录
 * @flowIdea 流程意见
 */
var global = {
    save: function () {
        $("#form").submit();
    }
};