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
            name: {
                trigger:"change", //监听onchange事件
                validators : {
                   /* notEmpty : {
                        message : '姓名不能为空！'
                    },*/
                    callback: {
                        callback : function(value, validator, $field) {
                            if(value!=null&&value!=""){
                                return changeFin();
                            }else{
                                return changeFinFalse();
                            }
                        }
                    }
                }
            },
            pinCodes: {
                validators : {
                    notEmpty : {
                        message : '身份证号码不能为空！'
                    },
                    regexp: {
                        regexp: /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/,
                        message: '身份证号码格式不正确，为15位和18位身份证号码！'
                    },
                    callback: {/*自定义，可以在这里与其他输入项联动校验*/
                        message: '身份证号码无效！',
                        callback:function(value, validator,$field){
                            //15位和18位身份证号码的正则表达式
                            var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
                            //如果通过该验证，说明身份证格式正确，但准确性还需计算
                            var idCard = value;
                            if(idCard.length!=0){
                                if (regIdCard.test(idCard)) {
                                    if (idCard.length == 18) {
                                        var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); //将前17位加权因子保存在数组里
                                        var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
                                        var idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
                                        for (var i = 0; i < 17; i++) {
                                            idCardWiSum += idCard.substring(i, i + 1) * idCardWi[i];
                                        }
                                        var idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
                                        var idCardLast = idCard.substring(17);//得到最后一位身份证号码
                                        //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
                                        if (idCardMod == 2) {
                                            if (idCardLast == "X" || idCardLast == "x") {
                                                return true;
                                                //alert("恭喜通过验证啦！");
                                            } else {
                                                return false;
                                                //alert("身份证号码错误！");
                                            }
                                        } else {
                                            //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                                            if (idCardLast == idCardY[idCardMod]) {
                                                //alert("恭喜通过验证啦！");
                                                return true;
                                            } else {
                                                return false;
                                                //alert("身份证号码错误！");
                                            }
                                        }
                                    }
                                } else {
                                    //alert("身份证格式不正确!");
                                    return false;
                                }
                            }else{
                                //身份证号为空时，不做该验证
                                return true;
                            }
                        }
                    }
                }
            },
            registeredResidence: {
                validators: {
                    stringLength: {
                        max: 200,
                        message: '户口所在地长度不能超过200！'
                    }
                }
            },
            sex: {
            	trigger:"change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '性别不能为空！'
                    }
                }
            },
            nation: {
            	trigger:"change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '民族不能为空！'
                    }
                }
            },
            nativePlace: {
            	trigger:"change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '籍贯不能为空！'
                    }
                }
            },
            birthplace: {
                validators: {
                    stringLength: {
                        max: 200,
                        message: '出生地长度不能超过200！'
                    }
                }
            },
            healthStatus: {
                validators: {
                    stringLength: {
                        max: 50,
                        message: '健康状况长度不能超过50！'
                    }
                }
            },
            dateOfBirth: {
                trigger:"change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '出生日期不能为空！'
                    }
                }
            },
            maritalStatus: {
                validators: {
                    stringLength: {
                        max: 50,
                        message: '婚姻状况长度不能超过50！'
                    }
                }
            },
            workingUnit: {
                validators: {
                    notEmpty: {
                        message: '工作单位不能为空！'
                    },
                    stringLength: {
                        max: 50,
                        message: '工作单位长度不能超过50！'
                    }
                }
            },
            workingTime: {
                trigger:"change", //监听onchange事件
                validators: {
                    stringLength: {
                        max: 30,
                        message: '工作时间长度不能超过30！'
                    }
                }
            },
            ryIdentity: {
                trigger:"change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '个人身份不能为空！'
                    }
                }
            },
            frontlineSituation: {
                trigger:"change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '一线情况不能为空！'
                    }
                }
            },
            ryEducation: {
                trigger:"change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '学历不能为空！'
                    }
                }
            },
            ryGegree: {
                trigger:"change", //监听onchange事件
                validators: {
                    stringLength: {
                        max: 50,
                        message: '学位长度不能超过50！'
                    }
                }
            },
            ryMajor: {
                validators: {
                    stringLength: {
                        max: 50,
                        message: '专业长度不能超过50！'
                    }
                }
            },
            ryGraduationAcademy: {
                validators: {
                    stringLength: {
                        max: 100,
                        message: '毕业院校长度不能超过100！'
                    }
                }
            },
            technicalPost: {
                validators: {
                    stringLength: {
                        max: 50,
                        message: '技术职务长度不能超过50！'
                    }
                }
            },
            contactNumber: {
                validators: {
                    numeric: {
                        message: '联系电话(手机)不能为非数字！'
                    },
                    stringLength: {
                        max: 20,
                        message: '联系电话(手机)长度不能超过20！'
                    }
                }
            },
            jobLevel: {
                trigger:"change", //监听onchange事件
                validators: {
                    stringLength: {
                        max: 50,
                        message: '职务级别长度不能超过50！'
                    }
                }
            },
            homeAddress: {
                validators: {
                    stringLength: {
                        max: 500,
                        message: '家庭地址长度不能超过500！'
                    }
                }
            },
            localPoliceStation: {
                validators: {
                    stringLength: {
                        max: 200,
                        message: '户口所在派出所长度不能超过200！'
                    }
                }
            },
            serialNumber: {
                validators: {
                    stringLength: {
                        max: 50,
                        message: '序号长度不能超过50！'
                    }
                }
            },
            applicationTime: {
                trigger:"change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '入党申请时间不能为空！'
                    }
                }
            },
            trainingContacts: {
                validators: {
                    stringLength: {
                        max: 50,
                        message: '培养联系人长度不能超过50！'
                    }
                }
            },
            developmentType: {
                trigger:"change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '发展类型不能为空！'
                    }
                }
            },
            politicalOutlook: {
                trigger:"change", //监听onchange事件
                validators: {
                    stringLength: {
                        max: 50,
                        message: '政治面貌人长度不能超过50！'
                    }
                }
            },
            determineActivistTime: {
                trigger:"change", //监听onchange事件
                validators: {
                    stringLength: {
                        max: 10,
                        message: '确定为积极分子时间长度不能超过10！'
                    }
                }
            },
            rewardsAndPunishments: {
                validators: {
                    stringLength: {
                        max: 1000,
                        message: '奖惩情况长度不能超过1000！'
                    }
                }
            },
            presentPerformance: {
                validators: {
                    stringLength: {
                        max: 1000,
                        message: '现时表现长度不能超过1000！'
                    }
                }
            },
            memo: {
                validators: {
                    stringLength: {
                        max: 1000,
                        message: '备注长度不能超过1000！'
                    }
                }
            },
          /*  identity: {
                trigger:"change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '个人身份不能为空！'
                    }
                }
            },
            education: {
                trigger:"change", //监听onchange事件
                validators: {
                    notEmpty: {
                        message: '学历不能为空！'
                    }
                }
            }*/
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});