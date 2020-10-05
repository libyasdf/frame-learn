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
        	jobSalary: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '职务工资必须为数字且最多保留两位小数'
                    },
                    stringLength: {
                        max: 11,
                        message: '职务工资长度不能超过11位'
                    }
                }
            },
            levelSalary: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '级别工资必须为数字且最多保留两位小数'
                    },
		            stringLength: {
		                max: 11,
		                message: '级别工资长度不能超过11位'
		            }
                }
            },
            policeRankSalary: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '警衔工资必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '警衔工资长度不能超过11位'
		            }
                }
            },
            workSalary: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '工作津贴必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '工作津贴长度不能超过11位'
		            }
                }
            },
            dutySalary: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '执勤工资必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '执勤工资长度不能超过11位'
		            }
                }
            },
            fooSalary: {
                validators: {
                    regexp: {
                        regexp:/^\d*(\.\d{1,2})?$/,
                        message: '副食补贴必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '副食补贴长度不能超过11位'
		            }
                }
            },huiSalary: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '回民补贴必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '回民补贴长度不能超过11位'
		            }
                }
            },
            houseSalary: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '房屋补贴必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '房屋长度不能超过11位'
		            }
                }
            },
            onlyChildSalary: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '独子费必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '独子费长度不能超过11位'
		            }
                }
            },
            dutyFee: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '值班费必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '值班费长度不能超过11位'
		            }
                }
            },
            policeOvertimePay: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '警察加班费必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '警察加班费长度不能超过11位'
		            }
                }
            },
            shouldSalary: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '应发工资必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '应发工资长度不能超过11位'
		            }
                }
            },
            accumulationSalary: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '公积金必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '公积金长度不能超过11位'
		            }
                }
            },
            medicalInsurance: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '医保必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '医保长度不能超过11位'
		            }
                }
            },
            bigMedicalInsurance: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '大额医保必须为数字且最多保留两位小数'
                    },
                    stringLength: {
                        max: 11,
                        message: '大额医保长度不能超过11位'
                    }
                }
            },
            deductSurcharges: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '专项附加扣除必须为数字且最多保留两位小数'
                    },
                    stringLength: {
                        max: 11,
                        message: '专项附加扣除长度不能超过11位'
                    }
                }
            },
            pensionAnnuity: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '养老年金必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '养老年金长度不能超过11位'
		            }
                }
            },
            rentSalary: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '房租必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '房租长度不能超过11位'
		            }
                }
            },
            buckleSalary: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '扣款必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '扣款长度不能超过11位'
		            }
                }
            },
            duesSalary: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '会费必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '会费长度不能超过11位'
		            }
                }
            },
            buckleLoan: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '扣贷款必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '扣贷款长度不能超过11位'
		            }
                }
            }
            ,
            loanInterest: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '贷款利息必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '贷款利息长度不能超过11位'
		            }
                }
            }
            ,
            personalIncomeTax: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '个人所得税必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '个人所得税长度不能超过11位'
		            }
                }
            },
            actualSalary: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '实发工资必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '实发工资长度不能超过11位'
		            }
                }
            },
            propertyFee: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '物业费必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '物业费长度不能超过11位'
		            }
                }
            },
            heatingSubsidies: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '采暖补贴必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '采暖补贴长度不能超过11位'
		            }
                }
            },
            travelAllowance: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '交通补贴必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '交通补贴长度不能超过11位'
		            }
                }
            },
            phoneAllowance: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '通讯补贴必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '通讯补贴长度不能超过11位'
		            }
                }
            },
            publicTransportation: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '公务交通必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '公务交通长度不能超过11位'
		            }
                }
            },
            unpaidLeaveAllowance: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '未休假补贴必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '未休假补贴长度不能超过11位'
		            }
                }
            },
            doublePay: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '双薪必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '双薪长度不能超过11位'
		            }
                }
            },
            meritPay: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '绩效奖必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '绩效奖长度不能超过11位'
		            }
                }
            },
            heatstrokePreventionSubsidy: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '防暑降温费必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '防暑降温费长度不能超过11位'
		            }
                }
            },
            winterHeatingSubsidy: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '冬季采暖补贴必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '冬季采暖补贴长度不能超过11位'
		            }
                }
            },
            lifeSalary: {
                validators: {
                    regexp: {
                        regexp: /^\d*(\.\d{1,2})?$/,
                        message: '生活补贴必须为数字且最多保留两位小数'
                    },
                    stringLength: {
		                max: 11,
		                message: '生活补贴长度不能超过11位'
		            }
                }
            },
            idCarNo: {
                validators: {
                	notEmpty : {
						message : '身份证号不能为空!'
					}
                  
                }
            }
            
            
        },
        submitHandler: function (validator, form, submitButton) {
            alert("submit");
        }
    });
});