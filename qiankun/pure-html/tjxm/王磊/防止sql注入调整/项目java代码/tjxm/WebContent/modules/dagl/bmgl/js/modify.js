var categoryCode = "" // 当前门类
var tName = ""; // 当前表名
var id = ""; // 系统唯一关键字

var all = 0; // 标签页总数
var ranking = 0; // 当前标签页层级

var form = []; // 表单数据
var formDefault = {}; // 表单默认值

var fileNumberRule = null; // 档号规则
var mColNameCHN = ''; // 父表中文名
var sColNameCHN = ''; // 子表中文名
var mColName = ''; // 父表关联字段
var sColName = ''; // 子表关联字段
var hiddenDataArray = []; // 保存那些字段需要取隐藏域的数据

var treePid = "";

$(function () {
	// 获取处室编号
	rolesNo= getcookie("rolesNo");
    // 获取门类
    categoryCode = GetRequest().categoryCode;
    treePid = window.parent.getCurrentRoot();//获取全宗的code和name
    // 获取表名，当前标签页层级，标签页总数，系统唯一关键字
    tName = $('.tab-active', window.parent.document).attr('data-TABLE_NAME');
    all = $('.tab', window.parent.document).length;
    ranking = $('.tab-active', window.parent.document).index() + 1;
    id = $('.tab-panel table tbody tr td .modify', window.parent.document).parent().siblings().eq(0).find('[type="checkbox"]').val();

    // 获取表单默认值
    getFormDefault();
});

/**
 * 获取表单默认值
 */
function getFormDefault() {
    $.ajax({
        url: '/dagl/bmgl/findById',
        data: {
            tName: tName, // 表名
            id: id
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {

            // 解析 data 并给全局变量赋值
            formDefault = data[0];
            console.log("获取表单默认值", formDefault);

            // 获取档号规则
            getfileNumberRule();
        },
        error: function (err) {
            layer.msg('加载失败，请刷新页面',{icon: 2,time:1000});
            console.log("加载表单失败，错误信息：\n", err);
        }
    });
}

/**
 * 获取档号规则
 */
function getfileNumberRule() {

    console.log('DHGZ\n', {
        categoryCode: categoryCode, // 门类
        tName: tName, // 表名
        all: all, // 标签页总数
        ranking: ranking // 当前标签页层级
    });

    $.ajax({
        url: '/dagl/bmgl/findDHGZ',
        data: {
            categoryCode: categoryCode, // 门类
            tName: tName, // 表名
            all: all, // 标签页总数
            ranking: ranking // 当前标签页层级
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {

            // 获取 data
            var data = data.data;
            console.log('获取档号规则返回的数据：\n', data);

            // 获取档号相关属性
            getFileNumberAttr(data);

            // 加载表单
            initForm();
        },
        error: function (err) {
            layer.msg('加载失败，请刷新页面',{icon: 2,time:1000});
            console.log("加载档号规则失败，错误信息：\n", err);
        }
    });
}

/**
 * 获取档号相关属性
 */
function getFileNumberAttr(data) {

    // 获取档号规则
    fileNumberRule = data.rows.sort(sortByNumber('ORDER_NUM'));
    console.log('获取档号规则：\n', fileNumberRule);

    // 获取父表，子表中文名
    if (ranking === 1) {
        mColNameCHN = data.colsf.ZH;
        mColName = data.colsf.M_COL_NAME;
    } else if (ranking === 2 && all === 2) {
        mColNameCHN = data.colsf.ZH;
        sColNameCHN = data.colsz.archive_no;
        mColName = data.colsf.M_COL_NAME;
        sColName = 'archive_no';
    } else if (ranking === 2) {
        mColNameCHN = data.colsf.ZH;
        sColNameCHN = data.colsz.ZH;
        mColName = data.colsf.M_COL_NAME;
        sColName = data.colsz.M_COL_NAME;
    } else if (ranking === 3) {
        mColNameCHN = data.colsf.ZH;
        sColNameCHN = data.colsz.archive_no;
        mColName = data.colsf.M_COL_NAME;
        sColName = 'archive_no';
    }
/*    if (ranking === 1) {
        mColNameCHN = data.colsf.ZH;
    } else if (ranking === 2 && all === 2) {
        mColNameCHN = data.colsf.ZH;
        sColNameCHN = data.colsz.archive_no;
    } else if (ranking === 2) {
        mColNameCHN = data.colsf.ZH;
        sColNameCHN = data.colsz.ZH;
    } else if (ranking === 3) {
        mColNameCHN = data.colsf.ZH;
        sColNameCHN = data.colsz.archive_no;
    }*/

    // 父表，子表中文名
    console.log('父表中文名：', mColNameCHN);
    console.log('子表中文名：', sColNameCHN);
}

// 初始化表单
function initForm() {

    $.ajax({
        url: '/dagl/bmgl/findAdd',
        data: {
            tName: tName // 表名
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {

            // 解析数据，并根据 COLUMN_ORDER 对数据进行排序，并修改数据中的默认值
            var usefullData = [];

            for (var i = 0; i < data.length; ++i) {
                if (data[i].COLUMN_VISIBLE === "T") {
                	//if(ranking!=1){
                		var ii = (fileNumberRule.length-0-(all-ranking))-1;//当前第几个档号字段
                		if(data[i].COLUMN_NAME==fileNumberRule[ii].RULE_FIELD){
                			if(fileNumberRule[ii].NUMB_LENGTH>(formDefault[data[i].COLUMN_NAME.toUpperCase('utf8')]+"").length){
	                			var longdu = fileNumberRule[ii].NUMB_LENGTH-0-(formDefault[data[i].COLUMN_NAME.toUpperCase('utf8')]+"").length;
	                			for(var l = 1;l<=longdu;l++){
	                				formDefault[data[i].COLUMN_NAME.toUpperCase('utf8')] = "0"+formDefault[data[i].COLUMN_NAME.toUpperCase('utf8')];
	                			}
	                		}
	                		usefullData.push(data[i]);
	                        usefullData[usefullData.length - 1].COLUMN_VALUE_DEFAULT = formDefault[data[i].COLUMN_NAME.toUpperCase('utf8')];
	                        continue;
                		}
                	//}
                		
                    usefullData.push(data[i]);
                    usefullData[usefullData.length - 1].COLUMN_VALUE_DEFAULT = formDefault[data[i].COLUMN_NAME.toUpperCase('utf8')];
                }
            }

            usefullData.sort(sortByNumber('COLUMN_ORDER'));
            console.log("加载表单有效的数据\n", usefullData);

            // 给全局变量赋值
            form = usefullData;

            // 渲染表单控件
            for (var i = 0; i < usefullData.length; i++) {
                loadInput(usefullData[i],i);
            }

            // 让 iframe 自适应高度，但最高 500px
            // $('iframe', window.parent.document).height($(document).height()).css({
            //     'max-height': 'none',
            //     overflow: 'auto'
            // });

            // 给保存按钮绑定方法
            $('.app .btn.btn-primary').click(submitForm);

            // 根据档号规则添加事件
            for (var i = 0; i < fileNumberRule.length; ++i) {

                var $input = $('.form-group label:contains(' + fileNumberRule[i].RULE_NAME + ')').parent().next().children().eq(0);

                console.log('拼接档号控件名', fileNumberRule[i].RULE_NAME);

                // 给文本框绑定事件
                if (!$input.hasClass('dropdown')) {
                    $input.change({ length: fileNumberRule[i].NUMB_LENGTH }, checkInputLength);
                    $input.attr("disabled",true);
                }
            }
            quanzong();//全宗和类别号（门类的回填）
            //changeMSValue();
            //设置只读
            fileNumberRuleToDis();
        },
        error: function (err) {
            layer.msg('加载失败，请刷新页面',{icon: 2,time:1000});
            console.log("加载表单失败，错误信息：\n", err);
        },
        complete:function(){//查询自定义样式执行
            $.ajax({
                'url' :'/dagl/bmgl/findColumnWidth',
                'type':'POST',
                'dataType':'text',
                'async': false,//无此,样式会被看到
                'data' : {
                	tName:tName
                }
            }).done(function( data, textStatus, jqXHR ) {
            	data =$.parseJSON(data);
            	
                //返回值格式
                //var data=[{"key":"basefolder_unit","value":"col-xs-4"},{"key":"folder_no","value":"col-xs-4"},{"key":"year_folder_no","value":"col-xs-3"},{"key":"maintitle","value":"col-xs-6"},{"key":"piece_num","value":"col-xs-6"},{"key":"page_num","value":"col-xs-6"},{"key":"filing_year","value":"col-xs-6"},{"key":"retention","value":"col-xs-6"},{"key":"doc_start_time","value":"col-xs-6"},{"key":"doc_end_time","value":"col-xs-6"},{"key":"sbt_word","value":"col-xs-6"},{"key":"pigeonhole_date","value":"col-xs-6"},{"key":"note","value":"col-xs-4"},{"key":"leibiehao","value":"col-xs-4"},{"key":"object_quantity","value":"col-xs-6"},{"key":"fonds_no","value":"col-xs-4"},{"key":"ad_folderno","value":"col-xs-6"},{"key":"temp_no","value":"col-xs-6"},{"key":"archive_entity_status","value":"col-xs-6"},{"key":"archive_ctg_no","value":"col-xs-6"},{"key":"quantity","value":"col-xs-6"},{"key":"folder_location","value":"col-xs-5"},{"key":"security_class","value":"col-xs-6"},{"key":"content_no","value":"col-xs-6"},{"key":"barcode","value":"col-xs-6"},{"key":"swlx","value":"col-xs-6"},{"key":"classification","value":"col-xs-6"}]
                for (var intt = data.length; intt >0; intt--){
                    if(null != data[intt-1]){//防止前台报错key为null
                        var key=data[intt-1].key;
                        var value=data[intt-1].value;
                        var form_name_class=$("[name='"+key+"']").parents(".form-group").attr("class").replace("form-group ","");
                        $("[name='"+key+"']").parents(".form-group").removeClass(form_name_class).addClass(value);
                        $("form").prepend($("[name='"+key+"']").parents(".form-group")[0]);

                        //全局数据调整
                        for(var j=0;j<form.length;j++){
                            if(form[j].COLUMN_NAME == key){//如果样式调整的话，把该字段存到第一个
                                toFirst(form,j);
                                console.log(form);
                            }
                        }

                    }

                }

            })}
    });
}

/**
 * 校验输入是否合法，若输入长度不够，补0，超出则提示错误信息
 * @param event event 对象，用于获得参数 length
 */
function checkInputLength(event) {

    var length = event.data.length;

    if (Boolean(length)) {
        if ($(this).val().length < length) {
            $(this).next().html('');
            for (var i = $(this).val().length; i < length; ++i) {
                $(this).val("0" + $(this).val());
            }
        } else if ($(this).val().length > length) {
            $(this).next().html($(this).parent().prev().find('label').html()+' 长度不超过'+length+'位！').css('color', 'red');
        } else {
            $(this).next().html('');
        }
    }

    // 拼接父子表 input 的值
    changeMSValue();
}

/**
 * 拼接父子表 input 的值
 */
function changeMSValue() {
    var $input = null; // 组成档号的表单控件
    var $inputM = $('.form-group label:contains(' + mColNameCHN + ')').parent().next().children().eq(0); // 父表控件
    var $inputS = $('.form-group label:contains(' + sColNameCHN + ')').parent().next().children().eq(0); // 子表控件
    var val = ""; // 组成档号的表单控件输入值
    var valM = ""; // 最终父表控件的组成值
    var valS = ""; // 最终子表控件的组成值
    if (ranking === 1) { // 一级

        for (var i = 0; i < fileNumberRule.length; ++i) {
            $input = $('.form-group label:contains(' + fileNumberRule[i].RULE_NAME + ')').parent().next().children().eq(0);

            if ($input.hasClass('dropdown')) { // 如果是下拉菜单
                val = $input.find('.btn').attr('data-value');
            } else { // 如果是文本框
                val = $input.val();
            }

            if (i === fileNumberRule.length - 1) {
                valM += val;
            } else {
                valM += val + fileNumberRule[i].CONNECTOR;
            }
        }

        $inputM.val(valM);

    } else { // 其他
    	
    	if(all==3&&ranking==2){
   		 for (var i = 0; i < fileNumberRule.length; ++i) {
	            $input = $('.form-group label:contains(' + fileNumberRule[i].RULE_NAME + ')').parent().next().children().eq(0);
	
	            if ($input.hasClass('dropdown')) { // 如果是下拉菜单
	                val = $input.find('.btn').attr('data-value');
	            } else { // 如果是文本框
	                val = $input.val();
	            }
	            valS += val + fileNumberRule[i].CONNECTOR;
	
	            if (i < fileNumberRule.length - 1) {
	                if (i === fileNumberRule.length - 2) {
	                    valM += val;
	                } else {
	                    valM += val + fileNumberRule[i].CONNECTOR;
	                }
	            }
	        }
	        $inputM.val(valM);
	        $inputS.val(valS.substring(0,valS.length-1));
   	}else{
	        for (var i = 0; i < fileNumberRule.length; ++i) {
	            $input = $('.form-group label:contains(' + fileNumberRule[i].RULE_NAME + ')').parent().next().children().eq(0);
	
	            if ($input.hasClass('dropdown')) { // 如果是下拉菜单
	                val = $input.find('.btn').attr('data-value');
	            } else { // 如果是文本框
	                val = $input.val();
	            }
	            valS += val + fileNumberRule[i].CONNECTOR;
	
	            if (i < fileNumberRule.length - 1) {
	                if (i === fileNumberRule.length - 2) {
	                    valM += val;
	                } else {
	                    valM += val + fileNumberRule[i].CONNECTOR;
	                }
	            }
	        }
	
	        $inputM.val(valM);
	        $inputS.val(valS);
   	}
    }
    $inputM.attr("disabled",true);
    $inputS.attr("disabled",true);
}
/**
 * 加载表单控件
 * @param data 加载表单控件所需要的数据对象
 */
function loadInput(data,index) {
    // T text S select A textarea(只会占一行) D treelayer F 
    switch (data.COLUMN_INPUT_TYPE) {
        case "T":
            loadTextInput(data,index);
            break;
        case "S":
            loadDropdown(data,index);
            break;
        case "A":
            loadTextArea(data,index);
            break;
        case "D":
            loadTreeLayer(data,index);
            break;
        default:
            break;
    }
}

/**
 * 加载文本框
 * @param data 加载文本框所需要的数据对象
 */
function loadTextInput(data,index) {
	var blur=""
		if(data.COLUMN_TYPE==5){
			blur=" onblur=bu0(this) "
		}
    $('.app form.row').append($(''
        + '<div class="form-group col-xs-6">'
        + '<div class="col-xs-3">'
        + (data.COLUMN_CAN_NULL === "F" ? '<span class="text-red">* </span>' : '')
        + '<label>' + data.COLUMN_CHN_NAME + '</label>'
        + '</div>'
        + '<div class="col-xs-8">'
        + '<input '+(data.COLUMN_NAME=="barcode" ? "readonly":"")+' onkeyup="InputChange('+ index +')" type="text" class="form-control" '+blur+' value="'
        + (data.COLUMN_VALUE_DEFAULT  != null? data.COLUMN_VALUE_DEFAULT : "")
        + '" name="' + data.COLUMN_NAME + '">'
        + '<em class="input-em text-info"></em>'
        + '</div>'
        + '</div>')
    );
}

/**
 * 加载下拉菜单
 * @param data 加载下拉菜单所需要的数据对象
 */
function loadDropdown(data,index) {

    if (data.COLUMN_NAME === "basefolder_unit") {
//        var $input = $(''
//            + '<div class="form-group col-xs-6">'
//            + '<div class="col-xs-3">'
//            + (data.COLUMN_CAN_NULL === "F" ? '<span class="text-red">* </span>' : '')
//            + '<label>' + data.COLUMN_CHN_NAME + '</label>'
//            + '</div>'
//            + '<div class="col-xs-8">'
//            + '<input type="text" class="form-control" onchange="InputChange('+ index +')" value="'
//            + (data.COLUMN_VALUE_DEFAULT ? data.COLUMN_VALUE_DEFAULT : "")
//            + '" name="' + data.COLUMN_NAME + '" id="cre_chushi_id1" readonly  />'
//            + '<em class="input-em text-info"></em>'
//            + '<input type="hidden" id="cre_chushi_id" value="'+(formDefault.CRE_CHUSHI_ID ? formDefault.CRE_CHUSHI_ID : "")+'" />'
//            + '</div>'
//            + '</div>'
//        );
        var $dropdownDom = $(''
                + '<div class="form-group col-xs-6">'
                + '<div class="col-xs-3">'
                + (data.COLUMN_CAN_NULL === "F" ? '<span class="text-red">* </span>' : '')
                + '<label>' + data.COLUMN_CHN_NAME + '</label>'
                + '</div>'
                + '<div class="col-xs-9">'
                + '<div class="select-container dropdown">'
                + '<button name="'+data.COLUMN_NAME+'" class="btn btn-default dropdown-toggle text-left" data-toggle="dropdown" data-value="'+formDefault.CRE_CHUSHI_ID+'">'
                + (data.COLUMN_VALUE_DEFAULT!=null && data.COLUMN_VALUE_DEFAULT.trim() !=""? data.COLUMN_VALUE_DEFAULT : "请选择")
                + '<span class="caret frcaret"></span>'
                + '</button>'
                + '<ul class="dropdown-menu">'
                + '</ul>'
                + '</div>'
                + '<em class="input-em text-info"></em>'
                + '</div>'
                + '</div>'
            );
        if(rolesNo.indexOf("D702")>-1){
//        	$input.find("#cre_chushi_id1").removeAttr("readonly"); 
//	        $input.find('#cre_chushi_id1').css('cursor', 'pointer').click(function () {
//	            openSelectZtree({
//	                "id": "cre_chushi_id",
//	                "name": "cre_chushi_id1",
//	                "isMulti": 1,
//	                "type": 2,
//	                "asyn": false,
//	                "level": 2,
//	                "url": "/system/component/tree/deptTree"
//	            });
//	        });
        	$.ajax({
	            url: "/dagl/xtpz/category/LJDWmark?code="+categoryCode,
	            type: 'get',
	            dataType: 'json',
	            asyn:true,
	            success: function (data) {
	            	var zujuan=data.COLUMN_NAME;
	                // 解析 data
	                var data = data.data;
                    if("q2" == categoryCode){
                        var daglLjdw = Dictionary.getNameAndCode({mark: "dagl_ljdw", type: "1"});
                        data = daglLjdw;
                    }
	                var j=0;
	                for (var i in data) {
	                    $dropdownDom.find('ul.dropdown-menu')
	                        .append($('<li class="dropdown-item" onclick="InputChange('+ index +')" data-value="' + i + '">' + data[i] + '</li>'));
	                   
	                    if(zujuan=='archive_flag'&&j==0){
	                    	$dropdownDom.find('button').html(data[i]+'<span class="caret frcaret"></span>').attr("data-value",i)
	                    	 j++;
	                    }
	                }
	                
	                var text = $dropdownDom.find('button').html().split('<')[0];
	                for(var i = 0; i < $dropdownDom.find('.dropdown-item').length; ++i) {
	                	if($dropdownDom.find('.dropdown-item:eq('+i+')').html() === text) {
	                		$dropdownDom.find('button').attr('data-value', $dropdownDom.find('.dropdown-item:eq('+i+')').attr('data-value'));
	                		break;
	                	}
	                }

	                $dropdownDom.find('.dropdown-item').click(function () {

	                    // 改变按钮的值
	                    $dropdownDom.find('[data-toggle="dropdown"]')
	                        .html($(this).html() + '<span class="caret frcaret"></span>')
	                        .attr('data-value', $(this).attr('data-value'));
	                    if(JSON.stringify(fileNumberRule).indexOf($dropdownDom.find("button").attr("name"))>-1){
	                    	// 拼接字符串
	                    changeMSValue();
	                    }
	                });
	            },
	            error: function (err) {
	                layer.msg('加载下拉菜单失败，请稍后再试',{icon: 2,time:1000});
	                console.log("加载下拉菜单失败，错误信息：\n", err);
	            }
	        });
        }else{
        	$dropdownDom.find("button").attr("disabled",true);
        }
        $('.app form.row').append($dropdownDom);

        // 添加到数组中
        hiddenDataArray.push(data.COLUMN_CHN_NAME);
    } else {
        var $dropdownDom = $(''
            + '<div class="form-group col-xs-6">'
            + '<div class="col-xs-3">'
            + (data.COLUMN_CAN_NULL === "F" ? '<span class="text-red">* </span>' : '')
            + '<label>' + data.COLUMN_CHN_NAME + '</label>'
            + '</div>'
            + '<div class="col-xs-9">'
            + '<div class="select-container dropdown">'
            + '<button name="'+data.COLUMN_NAME+'" class="btn btn-default dropdown-toggle text-left" data-toggle="dropdown" data-value="">'
            + (data.COLUMN_VALUE_DEFAULT!=null && data.COLUMN_VALUE_DEFAULT.trim() !=""? data.COLUMN_VALUE_DEFAULT : "请选择")
            + '<span class="caret frcaret"></span>'
            + '</button>'
            + '<ul class="dropdown-menu">'
            + '</ul>'
            + '</div>'
            + '<em class="input-em text-info"></em>'
            + '</div>'
            + '</div>'
        );

        // 请求菜单项
        $.ajax({
            url: "/system/config/dictionary/getMapByMark?mark=" + data.COLUMN_SELECT_NO + "&type=1",
            type: 'get',
            dataType: 'json',
            success: function (data) {

                // 解析 data
                var data = data.data;
                console.log('加载下拉菜单的数据\n', data);

                for (var i in data) {
                    $dropdownDom.find('ul.dropdown-menu')
                        .append($('<li class="dropdown-item" onclick="InputChange('+ index +')" data-value="' + i + '">' + data[i] + '</li>'));
                }

                var text = $dropdownDom.find('button').html().split('<')[0];
                for(var i = 0; i < $dropdownDom.find('.dropdown-item').length; ++i) {
                	if($dropdownDom.find('.dropdown-item:eq('+i+')').html() === text) {
                		$dropdownDom.find('button').attr('data-value', $dropdownDom.find('.dropdown-item:eq('+i+')').attr('data-value'));
                		break;
                	}
                }

                
                $dropdownDom.find('.dropdown-item').click(function () {

                    // 改变按钮的值
                    $dropdownDom.find('[data-toggle="dropdown"]')
                        .html($(this).html() + '<span class="caret frcaret"></span>')
                        .attr('data-value', $(this).attr('data-value'));
                    if(JSON.stringify(fileNumberRule).indexOf($dropdownDom.find("button").attr("name"))>-1){
                    	// 拼接字符串
                    changeMSValue();
                    }
                });
            },
            error: function (err) {
                layer.msg('加载下拉菜单失败，请稍后再试',{icon: 2,time:1000});
                console.log("加载下拉菜单失败，错误信息：\n", err);
            }
        });

        $('.app form.row').append($dropdownDom);

        // $.get("/system/config/dictionary/getListByMark", { mark: "kqcx_lczt", type=1 }, function (o) {
        //     $dropdownDom.find('.dropdown-menu')
    }
}

/**
 * 加载文本域
 * @param data 加载文本域所需要的数据对象
 */
function loadTextArea(data,index) {
    $('.app form.row').append($(''
        + '<div class="form-group col-sm-12">'
        + (data.COLUMN_CAN_NULL === "F" ? '<span class="text-red">* </span>' : '')
        + '<label>' + data.COLUMN_CHN_NAME + '</label>'
        + '<textarea onkeyup="InputChange('+ index +')">' + (data.COLUMN_VALUE_DEFAULT ? data.COLUMN_VALUE_DEFAULT : "") + '</textarea>'
        + '<em class="input-em text-info"></em>'
        + '</div>'
    ));
}

/**
 * 加载树的对话框
 * @param data 加载树的对话框所需要的数据对象
 */
function loadTreeLayer(data,index) {
    $('.app form.row').append($(''
        + '<div class="form-group col-sm-6">'
        + (data.COLUMN_CAN_NULL === "F" ? '<span class="text-red">* </span>' : '')
        + '<label>' + data.COLUMN_CHN_NAME + '</label>'
        + '<input type="text" class="form-control treelayer" onkeyup="InputChange('+ index +')" value="' + (data.COLUMN_VALUE_DEFAULT ? data.COLUMN_VALUE_DEFAULT : "") + '">'
        + '<em class="input-em text-info"></em>'
        + '</div>')
    );
}

/**
 * 显示树的弹框
 */
function showTreeLayer() {
    layer.open({
        title: '树'
    });
}

/** 
 * 校验表单数据
 */
function checkFormData() {
    var isLegal = true;
    for (var i = 0; i < $('.app form.row .form-group').length; ++i) {
        if ($('.app form.row .form-group:eq(' + i + ') input[type="text"]').length) {
            if (!checkTextInput(i)) isLegal = false;
        } else if ($('.app form.row .form-group:eq(' + i + ') .dropdown-toggle').length) {
            if (!checkDropDown(i)) isLegal = false;
        } else if ($('.app form.row .form-group:eq(' + i + ') textarea').length) {
            if (!checkTextarea(i)) isLegal = false;
        }
    }

    return isLegal;
}

/**
 * 校验值类型
 * @param value 用户输入的值
 * @param data 数据中规定的值类型
 * @return 是否合法
 */
function checkValueByType(index, value, data) {
    switch (data.COLUMN_TYPE) {
        case "1":
            return checkStringType(index, value, data);
        case "2":
            return checkIntType(index, value, data);
        case "3":
            return checkFloatType(index, value, data);
        case "4":
            return checkTimeType(index, value, data);
        case "5":
            return checkDateType(index, value, data);
        default:
            return false;
    }
}

/**
 * 校验字符串类型
 * @param index 表单控件的索引值
 * @param value 需要校验的值
 * @param data 校验合法性的数据对象
 * @return 是否合法
 */
function checkStringType(index, value, data) {

    logColorStr("字符串校验", "#333");

    // 非必填字段，用户输入为空
    if (data.COLUMN_CAN_NULL === "T" && value.length === 0) {
        // showTipEm(index, form[index].COLUMN_CHN_NAME + ' 字符串校验 通过 非必填字段，用户输入为空', 'green');
        return true;
    }

    // 必填字段
    if (data.COLUMN_CAN_NULL === "F" && value.length === 0) {
        showTipEm(index, form[index].COLUMN_CHN_NAME + ' 不能为空！', 'red');
        return false;
    }

    // 长度验证
    if (value.length > data.COLUMN_WIDTH) {
        showTipEm(index, form[index].COLUMN_CHN_NAME + ' 长度不超过'+ data.COLUMN_WIDTH+'位！', 'red');
        return false;
    }

    // 根据档号规则校验
    for (var i = 0; i < fileNumberRule.length; ++i) {

        $input = $('.form-group label:contains(' + fileNumberRule[i].RULE_NAME + ')').parent().next().children().eq(0);

        if (!$input.hasClass('dropdown')){ // 如果是文本框
            if(fileNumberRule[i].RULE_NAME==data.COLUMN_CHN_NAME && value.length > fileNumberRule[i].NUMB_LENGTH){
                showTipEm(index, form[index].COLUMN_CHN_NAME + ' 长度不超过'+ fileNumberRule[i].NUMB_LENGTH+'位！', 'red');//长度校验 未通过
                return false;
            }
        }

    }

    // showTipEm(index, form[index].COLUMN_CHN_NAME + ' 字符类型 校验通过', 'green');
    return true;
}

/**
 * 校验整数类型
 * @param index 表单控件的索引值
 * @param value 需要校验的值
 * @param data 校验合法性的数据对象
 * @return 是否合法
 */
function checkIntType(index, value, data) {

    logColorStr("整数校验", "#08c");

    // 非必填字段，用户输入为空
    if (data.COLUMN_CAN_NULL === "T" && value.length === 0) {
        // showTipEm(index, form[index].COLUMN_CHN_NAME + ' 整数校验 通过 非必填字段，用户输入为空', 'green');
        return true;
    }

    // 必填字段
    if (data.COLUMN_CAN_NULL === "F" && value.length === 0) {
        showTipEm(index, form[index].COLUMN_CHN_NAME + ' 不能为空！', 'red');
        return false;
    }

    // 是否为正整数
    if (!/^[0-9]+$/.test(value)) {
        showTipEm(index, form[index].COLUMN_CHN_NAME + ' 必须为正整数！', 'red');
        return false;
    }

    // 根据档号规则校验
    for (var i = 0; i < fileNumberRule.length; ++i) {

        $input = $('.form-group label:contains(' + fileNumberRule[i].RULE_NAME + ')').parent().next().children().eq(0);

        if (!$input.hasClass('dropdown')){ // 如果是文本框
            if(fileNumberRule[i].RULE_NAME==data.COLUMN_CHN_NAME && value.length > fileNumberRule[i].NUMB_LENGTH){
                showTipEm(index, form[index].COLUMN_CHN_NAME + ' 长度不超过'+ fileNumberRule[i].NUMB_LENGTH+'位！', 'red');//长度校验 未通过
                return false;
            }
        }
    }

    // showTipEm(index, form[index].COLUMN_CHN_NAME + ' 整数校验 通过', 'green');
    return true;
}

/**
 * 校验小数类型
 * @param index 表单控件的索引值
 * @param value 需要校验的值
 * @param data 校验合法性的数据对象
 * @return 是否合法
 */
function checkFloatType(index, value, data) {

    logColorStr("小数校验", "#08c");

    // 非必填字段，用户输入为空
    if (data.COLUMN_CAN_NULL === "T" && value.length === 0) {
        // showTipEm(index, form[index].COLUMN_CHN_NAME + ' 小数校验 通过 非必填字段，用户输入为空', 'green');
        return true;
    }

    // 必填字段
    if (data.COLUMN_CAN_NULL === "F" && value.length === 0) {
        showTipEm(index, form[index].COLUMN_CHN_NAME + ' 不能为空！，', 'red');
        return false;
    }

    // 如果有小数点
    if (value.split('.').length) {

        // 获取整数部分和小数部分
        integralPart = value.split('.')[0];
        decimalPart = value.split('.')[1];

        // 是否为正整数
        if (!/^[0-9]+$/.test(integralPart)) {
            showTipEm(index, form[index].COLUMN_CHN_NAME + ' 小数必须为正整数！', 'red');
            return false;
        }

        // 小数位数
        if (decimalPart.length > form[index].COLUMN_POINT) {
            showTipEm(index, form[index].COLUMN_CHN_NAME + ' 小数位数不超过'+form[index].COLUMN_POINT+'位！', 'red');
            return false;
        }

    } else {
        // 是否为正整数
        if (!/^[0-9]+$/.test(value)) {
            showTipEm(index, form[index].COLUMN_CHN_NAME + ' 小数必须为正整数！', 'red');
            return false;
        }
    }

    // 根据档号规则校验
    for (var i = 0; i < fileNumberRule.length; ++i) {

        $input = $('.form-group label:contains(' + fileNumberRule[i].RULE_NAME + ')').parent().next().children().eq(0);

        if (!$input.hasClass('dropdown')){ // 如果是文本框
            if(fileNumberRule[i].RULE_NAME==data.COLUMN_CHN_NAME && value.length > fileNumberRule[i].NUMB_LENGTH){
                showTipEm(index, form[index].COLUMN_CHN_NAME + ' 长度不超过'+ fileNumberRule[i].NUMB_LENGTH+'位！', 'red');//长度校验 未通过
                return false;
            }
        }
    }

    // showTipEm(index, form[index].COLUMN_CHN_NAME + ' 小数校验 通过', 'green');
    return true;
}

/**
 * 校验时间类型
 * @param index 表单控件的索引值
 * @param value 需要校验的值
 * @param data 校验合法性的数据对象
 * @return 是否合法
 */
function checkTimeType(index, value, data) {

}

/**
 * 校验日期类型
 * @param index 表单控件的索引值
 * @param value 需要校验的值
 * @param data 校验合法性的数据对象
 * @return 是否合法
 */
function checkDateType(index, value, data) {

    logColorStr("日期校验", "#f00");

    // 非必填字段，用户输入为空
    if (data.COLUMN_CAN_NULL === "T" && value.length === 0) {
        // showTipEm(index, form[index].COLUMN_CHN_NAME + ' 日期校验 通过 非必填字段，用户输入为空', 'green');
        return true;
    }

    var isLegal = true;
    var yearStr = value.substr(0, 4);
    var monthStr = value.substr(4, 2);
    var dayStr = value.substr(6, 2);

    // 必填字段
    if (data.COLUMN_CAN_NULL === "F" && value.length === 0) {
        showTipEm(index, form[index].COLUMN_CHN_NAME + ' 不能为空！', 'red');
        return false;
    }

    // 长度校验
    /*if (value.length > data.COLUMN_WIDTH) {
        showTipEm(index, form[index].COLUMN_CHN_NAME + ' 长度不超过'+ data.COLUMN_WIDTH+'位！', 'red');
        return false;
    }*/
    if (value.length != 8) {
    	showTipEm(index, form[index].COLUMN_CHN_NAME + ' 长度必须为8位！', 'red');
    	return false;
    }

    // 是否为正整数
    if (!/^[0-9]+$/.test(value) && value.length !== 0) {
        showTipEm(index, form[index].COLUMN_CHN_NAME + ' 必须为正整数，日期格式应为 YYYYMMDD！', 'red');
        return false;
    }

    // 日期格式 YYYY
    if (value.length === 4) {
        isLegal = checkDateStr(yearStr, "no", "no")
    } else if (value.length === 6) {
        isLegal = checkDateStr(yearStr, monthStr, "no")
    } else if (value.length === 8) {
        isLegal = checkDateStr(yearStr, monthStr, dayStr)
    } else {
        showTipEm(index, form[index].COLUMN_CHN_NAME + '日期格式应为 YYYYMMDD', 'red');
        return false;
    }

    /**
     * 检验日期字符串格式和日期合法性
     * @param {*} yearStr 年字符串 如果传参为"no"则代表不检验
     * @param {*} monthStr 月份字符串
     * @param {*} dayStr 日期字符串
     * @return 是否合法
     */
    function checkDateStr(yearStr, monthStr, dayStr) {

        var year = Number(yearStr);
        var month = Number(monthStr);
        var day = Number(dayStr);

        if ("no" !== year) {
            // 不得为 0000 年
            /*if (yearStr === "0000") {
                showTipEm(index, form[index].COLUMN_CHN_NAME + ' 日期格式校验 未通过 不得为 0000 年！', 'red');
                return false;
            }*/
        }

        if ("no" !== month) {
            // 不得为 00 月
            /*if (monthStr === "00") {
                showTipEm(index, form[index].COLUMN_CHN_NAME + ' 日期格式校验 未通过 不得为 0000 年！', 'red');
                return false;
            }*/

            // 一年只有12个月
            if (month > 12) {
                showTipEm(index, form[index].COLUMN_CHN_NAME + ' 日期格式校验 未通过 一年只有12个月！', 'red');
                return false;
            }
        }

        if ("no" !== day) {

            // 没有 00 日
            /*if (dayStr === "00") {
                showTipEm(index, form[index].COLUMN_CHN_NAME + ' 日期格式校验 未通过 没有 00 日！', 'red');
                return false;
            }*/

            // 日期范围
            if (month === 2) {
                if (((year % 4) === 0) && ((year % 100) !== 0) || ((year % 400) === 0)) { // 闰年
                    if (day > 28) {
                        showTipEm(index, form[index].COLUMN_CHN_NAME + ' 日期格式校验 未通过 闰年2月28！', 'red');
                        return false;
                    }
                } else {
                    if (day > 29) {
                        showTipEm(index, form[index].COLUMN_CHN_NAME + ' 日期格式校验 未通过 2月最多29天！', 'red');
                        return false;
                    }
                }
            } else if (month === 1 || month ===  3 || month ===  5 || month ===  7 || month ===  8 || month ===  10 ||month ===  12) {
                if (day > 31) {
                    showTipEm(index, form[index].COLUMN_CHN_NAME + ' 日期格式校验 未通过 不能比31天多！', 'red');
                    return false;
                }
            } else {
                if (day > 30) {
                    showTipEm(index, form[index].COLUMN_CHN_NAME + ' 日期格式校验 未通过 不能比30天多！', 'red');
                    return false;
                }
            }
        }

        // showTipEm(index, form[index].COLUMN_CHN_NAME + ' 日期校验 通过', 'green');
        return true;
    }

    return isLegal;
}

/**
 * 校验文本框的合法性
 * @param index 文本框容器在表单中的索引
 * @return  是否合法
 */
function checkTextInput(index) {

    // 获取元素和值
    var $input = $('.app form.row .form-group:eq(' + index + ') input[type="text"]');
    var val = $input.val();
    var label = $('.app form.row .form-group:eq(' + index + ') label').html();
    var validatorData = form[index];

    if (label !== validatorData.COLUMN_CHN_NAME) {
        console.log(index + '.' + label + ' COLUMN_CHN_NAME %c 校验失败 ' + validatorData.COLUMN_CHN_NAME, "color:red;");
        return false;
    }

    // 分类校验
    return checkValueByType(index, val, validatorData);
}

/**
 * 校验下拉菜单的合法性
 * @param index 下拉菜单容器在表单中的索引
 * @return  是否合法
 */
function checkDropDown(index) {
    // 获取元素和值
    var $dropdown = $('.app form.row .form-group:eq(' + index + ') [data-toggle="dropdown"]');
    var val = $dropdown.html().split("<")[0];
    var label = $('.app form.row .form-group:eq(' + index + ') label').html();
    var validatorData = form[index];

    if (val === "请选择") {
        val = "";
    }

    if (label !== validatorData.COLUMN_CHN_NAME) {
        console.log(index + '.' + label + ' COLUMN_CHN_NAME %c 校验失败 ' + validatorData.COLUMN_CHN_NAME, "color:red;");
        return false;
    }

    // 分类校验
    return checkValueByType(index, val, validatorData);
}

/**
 * 校验文本域的合法性
 * @param index 文本域容器在表单中的索引
 * @return  是否合法
 */
function checkTextarea(index) {
    // 获取元素和值
    var $textarea = $('.app form.row .form-group:eq(' + index + ') textarea');
    var val = $textarea.val();
    var label = $('.app form.row .form-group:eq(' + index + ') label').html();
    var validatorData = form[index];

    if (label !== validatorData.COLUMN_CHN_NAME) {
        console.log(index + '.' + label + ' COLUMN_CHN_NAME %c 校验失败 ' + validatorData.COLUMN_CHN_NAME, "color:red;");
        return false;
    }

    // 分类校验
    return checkValueByType(index, val, validatorData);
}

/**
 * 显示验证提示信息
 * @param { Number } index 该表单控件在有效数据中的索引号
 * @param { String } text 提示信息文本
 * @param { String } color 提示信息文字颜色
 */
function showTipEm(index, text, color) {
    $('form.row .input-em:eq(' + index + ')').html(text).css('color', color);
}
/**
 * 隐藏验证提示信息
 * @param { Number } index 该表单控件在有效数据中的索引号
 */
function hideTipEm(index){
    $('form.row .input-em:eq(' + index + ')').html("")
}

/**
 * 保存表单内容并提交
 */
function submitForm() {

    // basefolder_unit 特殊字段

    // 清除错误提示
    $('em.input-em').html('');

    // 进行表单校验
    if (!checkFormData()) {
        layer.msg("表单数据不合法，请修改后重试！",{icon:0,time:1000});
        return;
    }

    // 声明表单提交的数据
    var formData = {};

    // 判断控件类型并获取控件名和值
    for (var i = 0; i < $('.app form.row .form-group').length; ++i) {

        if (hiddenDataArray.indexOf($('.app form.row .form-group:eq(' + i + ') label').html()) !== -1) {
            if (form[i].COLUMN_NAME === 'basefolder_unit') {
                formData.basefolder_unit = $('.app form.row .form-group:eq(' + i + ') button').html().split('<')[0];
                formData.cre_chushi_id = $('.app form.row .form-group:eq(' + i + ') button').attr("data-value");
            }
        } else {
            if ($('.app form.row .form-group:eq(' + i + ') input[type="text"]').length) {
                getTextInputValue(i);
            } else if ($('.app form.row .form-group:eq(' + i + ') .dropdown-toggle').length) {
                getDropDownValue(i);
            } else if ($('.app form.row .form-group:eq(' + i + ') textarea').length) {
                getTextareaValue(i);
            } else {
                console.log("未找到控件！");
            }
        }
    }

    console.log("表单提交的数据：\n", formData);
    if (ranking == 1) {
        sColName = mColName;
        sColNameCHN = mColNameCHN;
    }

     $.ajax({
         url: "/dagl/bmgl/isRepetition",
         type: 'post',
         data: {
             column: sColName,
             tName: tName,
             relevancyId: $("input[name='" + sColName.toLowerCase() + "']").val(),
             chushiId: getcookie("userid"),
             recId:formDefault.RECID
         },
         dataType: 'json',
         success: function (data) {
             if (data.flag == 1) {
				    // 发送保存请求
				    $.ajax({
				        url: "/dagl/bmgl/dynamicUpdate",
				        type: 'post',
				        data: {
				            jsonStr: JSON.stringify(formData), // 表单数据
				            tName: tName, // 表名
				            key: 'recid', // 系统唯一关键字字段名
				            value: id // 系统唯一关键字字段值
				        },
				        dataType: 'json',
				        success: function (data) {
				            layer.msg("修改成功！", {
				                icon: 1,
				                time: 1000
				            }, function (index) {
				                //当你在iframe页面关闭自身时
				                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				                parent.layer.close(index); //再执行关闭
				            });
				        },
				        error: function (err) {
				            layer.msg('保存失败，请稍后再试',{icon: 2,time:1000});
				            console.log("保存表单失败，错误信息：\n", err);
				        }
				    });
             } else {
                 $("input[name='" + sColName.toLowerCase() + "']").focus();
                 layer.msg("已存在" + sColNameCHN + ",请重新输入",{icon: 0,time:2000})
             }
         }
     });
    /**
     * 获取文本框的值
     * @param index 文本框容器在表单中的索引
     */
    function getTextInputValue(index) {
        formData[form[index].COLUMN_NAME] = $('.app form.row .form-group:eq(' + index + ') input[type="text"]').val()
    }

    /**
     * 获取下拉菜单的值
     * @param index 下拉菜单容器在表单中的索引
     */
    function getDropDownValue(index) {
        if("fonds_no" == form[index].COLUMN_NAME){
            //全宗号存code
            formData[form[index].COLUMN_NAME] = (($('.app form.row .form-group:eq(' + index + ') .dropdown-toggle').html().split('<')[0] === "请选择") ? "" : $('.app form.row .form-group:eq(' + index + ') .dropdown-toggle').attr("data-value").split('<')[0]);
        }else{
            formData[form[index].COLUMN_NAME] = (($('.app form.row .form-group:eq(' + index + ') .dropdown-toggle').html().split('<')[0] === "请选择") ? "" : $('.app form.row .form-group:eq(' + index + ') .dropdown-toggle').html().split('<')[0]);
        }
    }

    /**
     * 获取文本域的值
     * @param index 文本域容器在表单中的索引
     */
    function getTextareaValue(index) {
        formData[form[index].COLUMN_NAME] = $('.app form.row .form-group:eq(' + index + ') textarea').val();
    }
}
function bu0(e) {
	if(/^\d*$/.test($(e).val())){
		var val=$(e).val();
		var valLength=val.length;
		if(valLength<8){
			for(var i = 1;i<=8-valLength-0;i++){
				val+=0+"";
			}
			$(e).val(val);
		}
	}
}

/**
 * @method
 * @param index 在表单中的索引
 */
function InputChange(index){
    if ($('.app form.row .form-group:eq(' + index + ') input[type="text"]').length) {
        !checkTextInput(index) ? isLegal = false : hideTipEm(index);
    } else if ($('.app form.row .form-group:eq(' + index + ') .dropdown-toggle').length) {
        setTimeout(function(){
            !checkDropDown(index) ? isLegal = false : hideTipEm(index);
        },0)
    } else if ($('.app form.row .form-group:eq(' + index + ') textarea').length) {
        !checkTextarea(index) ? isLegal = false : hideTipEm(index);
    }
}

/**
 * 全宗，门类的回显
 * @returns
 */
function quanzong(){
    //回显全宗

    var jsontree = $.parseJSON(treePid);
    if($("[name= 'fonds_no']")[0].tagName=="INPUT"){
        $("[name= 'fonds_no']").val(jsontree['quanzongName']);
        $("[name= 'fonds_no']").attr("readonly","readonly");
    }else{
        $("[name= 'fonds_no']").attr("disabled","disabled");
        $("[name= 'fonds_no']").html(jsontree['quanzongName']+"<span class='caret frcaret'></span>");
        $("[name= 'fonds_no']").css({"border-color":"rgb(204, 204, 204)","background-color":"rgb(238, 238, 238)"})
        //$("[name= 'fonds_no']").next("ul").find("li").each(function(){
        //if($(this).html()==jsontree['quanzongName']){
        //不考虑下拉内容，直接赋值key和value
        $("[name= 'fonds_no']").attr("data-value",jsontree['quanzongCode']);
        //}
        //})

    }

    //回显门类，类别号
    if($("[name= 'leibiehao']").length>0){
        if($("[name= 'leibiehao']")[0].tagName=="INPUT"){
            $("[name= 'leibiehao']").val(jsontree['leibiehaoName']);
            $("[name= 'leibiehao']").attr("readonly","readonly");
        }else{
            $("[name= 'leibiehao']").attr("disabled","disabled");
            $("[name= 'leibiehao']").html(jsontree['leibiehaoName']+"<span class='caret frcaret'></span>");
            $("[name= 'leibiehao']").css({"border-color":"rgb(204, 204, 204)","background-color":"rgb(238, 238, 238)"})
            //$("[name= 'leibiehao']").next("ul").find("li").each(function(){
            //if($(this).html()==jsontree['leibiehaoName']){
            //不考虑下拉内容，直接赋值key和value
            $("[name= 'leibiehao']").attr("data-value",jsontree['leibiehaoCode']);
            //}
            //})

        }
    }
}

//数组移动置顶
function toFirst(fieldData,index) {

    if(index!=0){

        // fieldData[index] = fieldData.splice(0, 1, fieldData[index])[0]; 这种方法是与另一个元素交换了位子，

        fieldData.unshift(fieldData.splice(index , 1)[0]);

    }

}

//档号规则，及父子档号字段不可修改
function fileNumberRuleToDis() {
    debugger;

    //档号规则
    for (var i = 0; i < fileNumberRule.length; ++i) {
        $('.form-group label:contains(' + fileNumberRule[i].RULE_NAME + ')').parent().next().children().eq(0).find("button").attr("disabled",true);
        $('.form-group label:contains(' + fileNumberRule[i].RULE_NAME + ')').parent().next().children().eq(0).find("button").attr("readonly",true);
    }

    //父子档号字段
    $('.form-group label:contains(' + mColNameCHN + ')').parent().next().children().eq(0).attr("disabled",true); // 父表控件
    $('.form-group label:contains(' + sColNameCHN + ')').parent().next().children().eq(0).attr("disabled",true); // 子表控件


}