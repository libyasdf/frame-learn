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
            meetingTime: {
                trigger:"change", //监听onchange事件
                message: '时间验证失败！',
                validators: {
                    notEmpty: {
                        message: '时间不能为空！'
                    }
                }
            },
            meetingPlace: {
                validators: {
                    notEmpty: {
                        message: '地点不能为空！'
                    },
                    stringLength: {
                        max: 50,
                        message: '地点长度不能超过50！'
                    }
                }
            },
            compere: {
                validators: {
                    notEmpty: {
                        message: '主持人不能为空！'
                    },
                    stringLength: {
                        max: 50,
                        message: '主持人长度超过50！'
                    }
                }
            },
            noteTaker: {
                validators: {
                    notEmpty: {
                        message: '记录人不能为空！'
                    },
                    stringLength: {
                        max: 50,
                        message: '记录人长度不能超过50！'
                    }
                }
            },
            numberOfPeople: {
                validators: {
                    notEmpty: {
                        message: '应到人数不能为空！'
                    },
                    numeric: {
                        message: '应到人数不能为非数字！'
                    },
                    regexp: {
                        regexp:/^[+]{0,1}(\d+)$/,
                        message: '应到人数不能为非正整数！'
                    },
                    stringLength: {
                        max: 10,
                        message: '应到人数长度不能超过10！'
                    }
                }
            },
            actualNumber: {
                validators: {
                    notEmpty: {
                        message: '实到人数不能为空！'
                    },
                    numeric: {
                        message: '实到人数不能为非数字！'
                    },
                    regexp: {
                        regexp:/^[+]{0,1}(\d+)$/,
                        message: '实到人数不能为非正整数！'
                    },
                    stringLength: {
                        max: 10,
                        message: '实到人数长度不能超过10！'
                    }
                }
            },
            primaryCoverage: {
                validators: {
                    notEmpty: {
                        message: '主要内容不能为空！'
                    },
                    stringLength: {
                        max: 2000,
                        message: '主要内容长度不能超过2000！'
                    }

                }
            },
            attendants: {
                validators: {
                    notEmpty: {
                        message: '参加人员不能为空！'
                    },
                    stringLength: {
                        max: 2000,
                        message: '参加人员长度不能超过2000！'
                    }
                }
            },
            meetingSituation: {
                validators: {
                    notEmpty: {
                        message: '课程情况不能为空！'
                    },
                    stringLength: {
                        max: 2000,
                        message: '课程情况长度不能超过2000！'
                    }
                }
            },
            leaveAndReasons: {
                message: '请假人及原因验证失败！',
                validators: {
                    stringLength: {
                        max: 2000,
                        message: '请假人及原因长度不能超过2000！'
                    }
                }
            },
            seats: {
                message: '列席人验证失败！',
                validators: {
                    stringLength: {
                        max: 500,
                        message: '列席人长度不能超过500！'
                    }
                }
            }
        },
    /*    submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }*/
    });
});