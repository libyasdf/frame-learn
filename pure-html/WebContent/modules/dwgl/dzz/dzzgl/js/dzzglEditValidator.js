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
            jcOrgId: {
                message: '党组织编码验证失败！',
                validators: {
                    notEmpty: {
                        message: '党组织编码不能为空！'
                    },
                    stringLength: {
                        min: 3,
                        max: 3,
                        message: '党组织编码为3位！'
                    },
                    regexp: {
                        regexp:/^[a-zA-Z0-9_]{0,}$/,
                        message: '党组织编码不能为中文！'
                    },
                    callback: {
                        callback : function() {
                            return uniqCheck("党组织编码不能重复！");
                        }
                    }
                }
            },
            orgName: {
                message: '组织名称验证失败！',
                validators: {
                    notEmpty: {
                        message: '组织名称不能为空！'
                    },
                    stringLength: {
                        max: 50,
                        message: '组织名称长度不能超过50！'
                    }
                }
            },
            orgType:{
                message: '组织属性验证失败！',
                validators: {
                    notEmpty: {
                        message: '组织属性不能为空！'
                    }
                }
            },
            orgFullName: {
                message: '组织全称验证失败！',
                validators: {
                    notEmpty: {
                        message: '组织全称不能为空！'
                    },
                    stringLength: {
                        max: 50,
                        message: '组织全称长度不能超过50！'
                    }
                }
            },
            associativeUnitName: {
                message: '党组织关联单位验证失败！',
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '党组织关联单位不能为空！'
                    }
                }
            },
            associativeUserName: {
                message: '党组织管理员验证失败！',
                trigger:"change",
                validators: {
                    notEmpty: {
                        message: '党组织管理员不能为空！'
                    }
                }
            },
            contactMan: {
                message: '党组织联系人验证失败！',
                validators: {
                    notEmpty: {
                        message: '党组织联系人不能为空！'
                    },
                    stringLength: {
                        max: 50,
                        message: '党组织联系人长度不能超过50！'
                    }
                }
            },
            contactNumber: {
                message: '党组织联系电话验证失败！',
                validators: {
                    notEmpty: {
                        message: '党组织联系电话不能为空！'
                    },
                    stringLength: {
                        max: 20,
                        message: '党组织联系电话长度不能超过20！'
                    },
                    numeric: {
                        message: '党组织联系电话不能为非数字！'
                    }
                }
            },
            creDate: {
                message: '建立日期验证失败！',
                validators: {
                    stringLength: {
                        max: 30,
                        message: '建立文号长度不能超过30！'
                    }
                }
            },
            fileNumber: {
                message: '建立文号验证失败！',
                validators: {
                    stringLength: {
                        max: 10,
                        message: '建立文号长度不能超过10！'
                    }
                }
            },
            postalAddress: {
                message: '通讯地址验证失败！',
                validators: {
                    stringLength: {
                        max: 100,
                        message: '通讯地址长度不能超过100！'
                    }
                }
            },
            postalCode: {
                message: '邮政编码验证失败！',
                validators: {
                    stringLength: {
                        max: 6,
                        message: '邮政编码长度不能超过6！'
                    },
                    numeric: {
                        message: '邮政编码不能为非数字！'
                    }
                }
            },
            reason: {
                message: '建立原因验证失败！',
                validators: {
                    stringLength: {
                        max: 500,
                        message: '建立原因长度不能超过500！'
                    }
                }
            },
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});

function uniqCheck(msg){
    var json = {};
    var valid = true;
    if($("#jcOrgId").val().length == 3){
        var orgId = "";
        if($('#superOrgId').val()){
            orgId = $('#superOrgId').val() + $("#jcOrgId").val();
        }else{
            orgId = $('#orgId').val().substring(0,$('#orgId').val().length - 3)+$("#jcOrgId").val();
        }
        if($('#orgId').val().substring($('#orgId').val().length - 3) != $("#jcOrgId").val()) {
            $.ajax({
                type: "GET",
                url: "/djgl/ddjs/dzz/dzzgl/check",
                async: false,
                data: {
                    orgId: orgId
                },
                dataType: "json",
                success: function (res) {
                    valid = res.valid;
                }
            });
        }
    }
    json.valid = valid;
    json.message = msg;
    return json;
}