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
            unitName: {
                message: '单位名称验证失败！',
                validators: {
                    notEmpty: {
                        message: '单位名称不能为空！'
                    },
                    stringLength: {
                        max: 50,
                        message: '单位名称长度不能超过50！'
                    }
                }
            },
            unitBelongText: {
                message: '单位隶属关系验证失败！',
                validators: {
                    notEmpty: {
                        message: '单位隶属关系不能为空！'
                    }
                }
            },
            unitAttrText: {
                message: '单位属性验证失败！',
                validators: {
                    notEmpty: {
                        message: '单位属性不能为空！'
                    }
                }
            },
            tradeText: {
                message: '所属行业验证失败！',
                validators: {
                    notEmpty: {
                        message: '所属行业不能为空！'
                    }
                }
            },
            grassrootOrg: {
                message: '法人单位建立党的基层组织验证失败！',
                validators: {
                    stringLength: {
                        max:50,
                        message: '法人单位建立党的基层组织长度不能超过50！'
                    }
                }
            },
            orgUnitCode: {
                message: '党组织所在单位代码验证失败！',
                validators: {
                    stringLength: {
                        max:50,
                        message: '党组织所在单位代码长度不能超过50！'
                    }
                }
            },
            workersNumber: {
                message: '在岗职工数验证失败！',
                validators: {
                    stringLength: {
                        max:6,
                        message: '在岗职工数长度不能超过6！'
                    },
                    regexp: {
                        regexp:/^[+]{0,1}(\d+)$/,
                        message: '在岗职工数不能为非正整数！'
                    }
                }
            },
            youngWorkersNumber: {
                message: '35岁以下在岗职工数验证失败！',
                validators: {
                    stringLength: {
                        max:6,
                        message: '35岁以下在岗职工数长度不能超过6！'
                    },
                    regexp: {
                        regexp:/^[+]{0,1}(\d+)$/,
                        message: '35岁以下在岗职工数不能为非正整数！'
                    }
                }
            },
            skillWorkers: {
                message: '在岗职工中工勤技能人员数验证失败！',
                validators: {
                    stringLength: {
                        max:6,
                        message: '在岗职工中工勤技能人员数长度不能超过6！'
                    },
                    regexp: {
                        regexp:/^[+]{0,1}(\d+)$/,
                        message: '在岗职工中工勤技能人员数不能为非正整数！'
                    }
                }
            },
            majorWorkers: {
                message: '在岗专业技术人员数验证失败！',
                validators: {
                    stringLength: {
                        max:6,
                        message: '在岗专业技术人员数长度不能超过6！'
                    },
                    regexp: {
                        regexp:/^[+]{0,1}(\d+)$/,
                        message: '在岗专业技术人员数不能为非正整数！'
                    }
                }
            },
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});