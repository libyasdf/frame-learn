var categoryCode = "" // 当前门类
var tName = ""; // 当前表名
var fids = ""//上级表的ids
var form = []; // 表单数据
var parentData = null;//父级的数据
var all = 0; // 标签页总数
var ranking = 0; // 当前标签页层级
var thisTotal = "";//当前表数量
var fileNumberRule = null; // 档号规则
var mColNameCHN = ''; // 父表中文名
var sColNameCHN = ''; // 子表中文名

var mColName = ''; // 父表关联字段
var sColName = ''; // 子表关联字段

var hiddenDataArray = []; // 保存那些字段需要取隐藏域的数据
var rolesNo="";
var treeWhere = "";
$(function () {
	  //当前用户的角色编号
	rolesNo= getcookie("rolesNo");
	
		$(".btn-container").append('  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\
            <button class="btn btn-primary hp_form_ys">保存样式</button>')
	
	
    // 获取门类
    //categoryCode = GetRequest().categoryCode;
		var tName1 = GetRequest().tName;
   // treeWhere = window.parent.getTreeWhere();
    //fids = GetRequest().fids;
    // 获取表名，当前标签页层级，标签页总数
    tName =tName1; //$('.tab-active', window.parent.document).attr('data-TABLE_NAME');
    //all = $('.tab', window.parent.document).length;
    //ranking = $('.tab-active', window.parent.document).index() + 1;

    // 获取档号规则
   // getfileNumberRule();

   // getParentData();
    initForm();
});




/**
 * 初始化表单
 */
function initForm() {
    $.ajax({
        url: '/dagl/bmgl/findAdd',
        data: {
            tName: tName // 表名
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            // 解析数据，并根据 COLUMN_ORDER 对数据进行排序
            var usefullData = [];

            for (var i = 0; i < data.length; ++i) {
                if (data[i].COLUMN_VISIBLE === "T") {
                    usefullData.push(data[i]);
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
            // 让 iframe 自适应高度，但最高 600px
            // $('iframe', window.parent.document).height($(document).height()).css({
            //     'max-height': 'none',
            //     overflow: 'auto'
            // });

            // 给保存按钮绑定方法
            $('.app .btn.btn-primary.bc').click(submitForm);
            $('.app .btn.btn-primary.cj').click(addSubmitForm);
        },
        error: function (err) {
            layer.msg('加载失败，请刷新页面',{icon: 2,time:1000});
            console.log("加载表单失败，错误信息：\n", err);
        },
        complete: function () {

            if(rolesNo.indexOf("D702")>-1){//必须执行的
            var hp_form_js="<div class=\"hp_form_select\" style=\"width:24px;display: inline-block;position: absolute;right:3px;top: 5px;\">\n" +
                "        <div class=\"hp_lineheight_0\" style=\"text-align:center;\">\n" +
                "            <span class=\"glyphicon glyphicon-chevron-up hp_form_glyphicon\" style=\"\"></span>\n" +
                "        </div>\n" +
                "        <div class=\"hp_lineheight_0\">\n" +
                "            <span class=\"glyphicon glyphicon-chevron-left hp_form_glyphicon hp_float_left\" style=\"\"></span>\n" +
                "            <span class=\"glyphicon glyphicon-chevron-right hp_form_glyphicon hp_float_right\" style=\"\"></span>\n" +
                "        </div>\n" +
                "        <div class=\"hp_lineheight_0\" style=\"text-align:center;\">\n" +
                "            <span class=\"glyphicon glyphicon-chevron-down hp_form_glyphicon\"></span>\n" +
                "        </div>\n" +
                "    </div>";
            $(".form-group").each(function(index,element){//添加图标
                $(element).append(hp_form_js);
            });
            $(".glyphicon-chevron-right").click(function () {//点击右侧调整图标方法
                //var xzq_form=$(this).parents(".form-group").find("div:eq(1)");
                var xzq_form=$(this).parents(".form-group");
                var form_class=xzq_form.attr("class");
                var form_class_col=form_class.replace("form-group ","");
                var form_class_num=form_class_col.replace("col-xs-","");
                var form_class_num_new=1+parseInt(form_class_num);
                console.log(form_class_num_new);
                if(form_class_num_new>12){
                    form_class_num_new=12;
                }
                xzq_form.removeClass(form_class_col);
                xzq_form.addClass("col-xs-"+form_class_num_new);
            })
            $(".glyphicon-chevron-left").click(function () {
                //var xzq_form=$(this).parents(".form-group").find("div:eq(1)");
                var xzq_form=$(this).parents(".form-group");
                var form_class=xzq_form.attr("class");
                var form_class_col=form_class.replace("form-group ","");
                var form_class_num=form_class_col.replace("col-xs-","");
                var form_class_num_new=form_class_num-1;
                if(form_class_num_new<3){
                    form_class_num_new=3;
                }
                xzq_form.removeClass(form_class_col);
                xzq_form.addClass("col-xs-"+form_class_num_new);
            })
            $(".glyphicon-chevron-up").click(function () {
                var xzq_form=$(this).parents(".form-group");
                xzq_form.prev().before(xzq_form[0]);
            });
            $(".glyphicon-chevron-down").click(function () {
                var xzq_form=$(this).parents(".form-group");
                xzq_form.next().after(xzq_form[0]);
            });
            //保存按钮点击
            $(".hp_form_ys").click(function () {
                var ys=[];//元素顺序即排序顺序
                $(".form-group").each(function(index,element){
                    var key=$(element).children("div:eq(1)").find("input,button").attr("name");
                    var form_width_class=$(element).attr("class").replace("form-group ","");//宽度
                    var map={};
                    map.key=key;
                    map.value=form_width_class;
                    ys.push(map)
                });
                //保存当前样式数据到后台;ys值,例174行
                console.log("JSON.stringify(ys):"+JSON.stringify(ys));
                $.ajax({
                    'url' :'/dagl/bmgl/editStyle',
                    'type':'POST',
                    'dataType':'JSON',
                    'data' : {
                        'list':JSON.stringify(ys),
                        'tName':tName
                    }
                }).done(function( data, textStatus, jqXHR ) {
                    if(data>=1){
                    	layer.msg("保存成功！",{icon:1,time:2000})
                    }else{
                    	layer.msg("保存失败！",{icon:0,time:2000})
                    	
                    }
                })
            });
        }
            //查询自定义样式执行
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
                    if(null != data[intt-1]) {//防止前台报错key为null
                        var key = data[intt - 1].key;
                        var value = data[intt - 1].value;
                        var form_name_class = $("[name='" + key + "']").parents(".form-group").attr("class").replace("form-group ", "");
                        $("[name='" + key + "']").parents(".form-group").removeClass(form_name_class).addClass(value);
                        $("form").prepend($("[name='" + key + "']").parents(".form-group")[0]);

                        //全局数据调整
                        for(var j=0;j<form.length;j++){
                            if(form[j].COLUMN_NAME == key){//如果样式调整的话，把该字段存到第一个
                                toFirst(form,j);
                                console.log(form);
                            }
                        }
                    }
                }

            })
        }
    });
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
 * @param index 文本框的索引 
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
        + '<div class="col-xs-9">'
        + '<input '+(data.COLUMN_NAME=="barcode" ? "readonly":"")+' onkeyup="InputChange('+ index +')"  type="text" class="form-control" '+blur+' value="'
        + (data.COLUMN_VALUE_DEFAULT ? data.COLUMN_VALUE_DEFAULT : "")
        + '" name="' + data.COLUMN_NAME + '">'
        + '<em class="input-em text-info"></em>'
        + '</div>'
        + '</div>')
    );
}

/**
 * 加载下拉菜单
 * @param data 加载下拉菜单所需要的数据对象
 * @param index 下拉菜单的索引 
 */
function loadDropdown(data,index) {

    if (data.COLUMN_NAME === "basefolder_unit") {

        var $input = $(''
            + '<div class="form-group col-xs-6">'
            + '<div class="col-xs-3">'
            + (data.COLUMN_CAN_NULL === "F" ? '<span class="text-red">* </span>' : '')
            + '<label>' + data.COLUMN_CHN_NAME + '</label>'
            + '</div>'
            + '<div class="col-xs-9">'
            + '<input type="text" class="form-control" onchange="InputChange('+ index +')" value="'
            + (data.COLUMN_VALUE_DEFAULT ? data.COLUMN_VALUE_DEFAULT : "")
            + '" name="' + data.COLUMN_NAME + '" id="cre_chushi_id1"  />'
            + '<em class="input-em text-info"></em>'
            + '<input type="hidden" id="cre_chushi_id" />'
            + '</div>'
            + '</div>'
        );
        
      	if(rolesNo.indexOf("D702")>-1){
      		$input.find("#cre_chushi_id1").removeAttr("readonly"); 
    		if(fids!=""){
    			$input.find("#cre_chushi_id1").attr("readOnly",true);
    			$input.find("#cre_chushi_id1").val(parentData.BASEFOLDER_UNIT)
          		$input.find("#cre_chushi_id").val(parentData.CRE_CHUSHI_ID)
    		}else{
    			$input.find("#cre_chushi_id1").removeAttr("readonly"); 
	        $input.find('#cre_chushi_id1').css('cursor', 'pointer').click(function () {
	            openSelectZtree({
	                "id": "cre_chushi_id",
	                "name": "cre_chushi_id1",
	                "isMulti": 1,
	                "type": 2,
	                "asyn": false,
	                "level": 2,
	                "url": "/system/component/tree/deptTree"
	            });
	        });
    		}
      	}else{
      		$input.find("#cre_chushi_id1").attr("readOnly",true);
      		 $input.find("#cre_chushi_id1").val(getcookie("chuShiName"))
      		$input.find("#cre_chushi_id").val(getcookie("chuShiId"))
      	}
        $('.app form.row').append($input);

        // 添加到数组中
        hiddenDataArray.push(data.COLUMN_CHN_NAME);
        //获取当前盒内的档案数量
        if(fids!=""){
	        $.ajax({
	             url: "/dagl/bmgl/findThisTotal",
	             type: 'post',
	             data: {
	            	 columnValue:parentData[mColName.toUpperCase()],
	            	column:mColName,
	                 tName: tName,
	                 chushiid: $input.find("#cre_chushi_id").val(),
	                 hao:fileNumberRule[(fileNumberRule.length-0-(1))].RULE_FIELD//all-ranking
	             },
	             dataType: 'json',
	             success: function (data) {
	            	 thisTotal=(data.data-0+1)
	            	    if (ranking == 1) {
	            	    } else if (ranking == 2 && all == 2) {
	            	        if((thisTotal+"").length<fileNumberRule[fileNumberRule.length-1].NUMB_LENGTH){
	            	        	var wei =(fileNumberRule[fileNumberRule.length-1].NUMB_LENGTH-0) - ((thisTotal+"").length -0); 
	            	        	for(var j = 1;j<=wei;j++){
	            	        		thisTotal="0"+thisTotal;
	            	        	}
	            	        }
	            	        $("input[name='archive_no']").val(parentData[mColName.toUpperCase()]+fileNumberRule[fileNumberRule.length-2].CONNECTOR+thisTotal);
	            	    	$("input[name='"+fileNumberRule[fileNumberRule.length-1].RULE_FIELD+"']").val(thisTotal);
	            	    } else if (ranking == 2){
	            	    	if((thisTotal+"").length<fileNumberRule[fileNumberRule.length-2].NUMB_LENGTH){
	            	        	var wei =(fileNumberRule[fileNumberRule.length-1].NUMB_LENGTH-0) - ((thisTotal+"").length -0); 
	            	        	for(var j = 1;j<=wei;j++){
	            	        		thisTotal="0"+thisTotal;
	            	        	}
	            	        }
	            	        $("input[name='"+sColName.toLowerCase()+"']").val(parentData[mColName.toUpperCase()]+fileNumberRule[fileNumberRule.length-3].CONNECTOR+thisTotal);
	            	    	$("input[name='"+fileNumberRule[fileNumberRule.length-1].RULE_FIELD+"']").val(thisTotal);
	            	    } else if (ranking == 3){
	            	    	if((thisTotal+"").length<fileNumberRule[fileNumberRule.length-1].NUMB_LENGTH){
	            	        	var wei =(fileNumberRule[fileNumberRule.length-1].NUMB_LENGTH-0) - ((thisTotal+"").length -0); 
	            	        	for(var j = 1;j<=wei;j++){
	            	        		thisTotal="0"+thisTotal;
	            	        	}
	            	        }
	            	        $("input[name='archive_no']").val(parentData[mColName.toUpperCase()]+fileNumberRule[fileNumberRule.length-2].CONNECTOR+thisTotal);
	            	    	$("input[name='"+fileNumberRule[fileNumberRule.length-1].RULE_FIELD+"']").val(thisTotal);
	            	    } 
	             }
	             })
        }
    } else {
    	var zujuan=data.COLUMN_NAME;
        var $dropdownDom = $(''
            + '<div class="form-group col-xs-6">'
            + '<div class="col-xs-3">'
            + (data.COLUMN_CAN_NULL === "F" ? '<span class="text-red">* </span>' : '')
            + '<label>' + data.COLUMN_CHN_NAME + '</label>'
            + '</div>'
            + '<div class="col-xs-9">'
            + '<div class="select-container dropdown">'
            + '<button name="'+data.COLUMN_NAME+'"  class="btn btn-default dropdown-toggle text-left" data-toggle="dropdown" data-value="">'
            // + (data.COLUMN_VALUE_DEFAULT ? data.COLUMN_VALUE_DEFAULT : "请选择")
            + '请选择'
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
            asyn:true,
            success: function (data) {

                // 解析 data
                var data = data.data;
                // console.log('加载下拉菜单的数据\n', data);
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

        $('.app form.row').append($dropdownDom);

        // $.get("/system/config/dictionary/getListByMark", { mark: "kqcx_lczt", type=1 }, function (o) {
        //     $dropdownDom.find('.dropdown-menu')
    }
}

/**
 * 加载文本域
 * @param data 加载文本域所需要的数据对象
 * @param index 文本域的索引 
 */
function loadTextArea(data,index) {
    $('.app form.row').append($(''
        + '<div class="form-group col-xs-12">'
        + '<div class="col-xs-3">'
        + (data.COLUMN_CAN_NULL === "F" ? '<span class="text-red">* </span>' : '')
        + '<label>' + data.COLUMN_CHN_NAME + '</label>'
        + '</div>'
        + '<div class="col-xs-9">'
        + '<textarea onkeyup="InputChange('+ index +')">' + (data.COLUMN_VALUE_DEFAULT ? data.COLUMN_VALUE_DEFAULT : "") + '</textarea>'
        + '<em class="input-em text-info"></em>'
        + '</div>'
        + '</div>'
    ));
}

/**
 * 加载树的对话框
 * @param data 加载树的对话框所需要的数据对象
 * @param index 树的对话框的索引 
 */
function loadTreeLayer(data,index) {
    $('.app form.row').append($(''
        + '<div class="form-group col-xs-6">'
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
    console.error(data.COLUMN_TYPE)
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
    // logColorStr("字符串校验", "#333");

    var isRule = 0;
    // 非必填字段，用户输入为空
    if (data.COLUMN_CAN_NULL === "T" && value.length === 0) {
        // showTipEm(index, form[index].COLUMN_CHN_NAME + ' 字符串校验 通过 非必填字段，用户输入为空', 'green');
        return true;
    }

    // 必填字段
    if (data.COLUMN_CAN_NULL === "F" && value.length === 0) {
        showTipEm(index, form[index].COLUMN_CHN_NAME + ' 不能为空！', 'red');//必填字段校验 未通过
        return false;
    }


    // 根据档号规则校验
    for (var i = 0; i < fileNumberRule.length; ++i) {

        $input = $('.form-group label:contains(' + fileNumberRule[i].RULE_NAME + ')').parent().next().children().eq(0);

        if (!$input.hasClass('dropdown')){ // 如果是文本框
            if(fileNumberRule[i].RULE_NAME==data.COLUMN_CHN_NAME && value.length > fileNumberRule[i].NUMB_LENGTH){
                showTipEm(index, form[index].COLUMN_CHN_NAME + ' 长度不超过'+ fileNumberRule[i].NUMB_LENGTH+'位！', 'red');//长度校验 未通过
                isRule++;
                return false;
            }
        }
    }

    if(0 == isRule){//不是档号规则字段
        // 长度验证
        if (value.length > data.COLUMN_WIDTH) {
            showTipEm(index, form[index].COLUMN_CHN_NAME + ' 长度不超过'+ data.COLUMN_WIDTH+'位！', 'red');//长度校验 未通过
            return false;
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

    // logColorStr("整数校验", "#08c");

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
    	var $input = $('.form-group label:contains(' + fileNumberRule[i].RULE_NAME + ')').parent().next().children().eq(0);
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

    // logColorStr("小数校验", "#08c");

    // 非必填字段，用户输入为空
    if (data.COLUMN_CAN_NULL === "T" && value.length === 0) {
        // showTipEm(index, form[index].COLUMN_CHN_NAME + ' 小数校验 通过 非必填字段，用户输入为空', 'green');
        return true;
    }

    // 必填字段
    if (data.COLUMN_CAN_NULL === "F" && value.length === 0) {
        showTipEm(index, form[index].COLUMN_CHN_NAME + ' 不能为空！', 'red');
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
    	var $input = $('.form-group label:contains(' + fileNumberRule[i].RULE_NAME + ')').parent().next().children().eq(0);
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

    // logColorStr("日期校验", "#f00");
    console.log(data.COLUMN_CHN_NAME, value);

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
    if (value.length > 8) {
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

    console.log(label +"~~~~~~"+validatorData.COLUMN_CHN_NAME);
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
    $('form.row .input-em:eq(' + index + ')').html(text).css('color', color)
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
        layer.msg("表单数据不合法，请修改后重试！",{icon: 0,time:2000});
        return;
    }

    // 声明表单提交的数据
    var formData = {};

    // 判断控件类型并获取控件名和值
    for (var i = 0; i < $('.app form.row .form-group').length; ++i) {

        if (hiddenDataArray.indexOf($('.app form.row .form-group:eq(' + i + ') label').html()) !== -1) {
            if (form[i].COLUMN_NAME === 'basefolder_unit') {
                formData.basefolder_unit = $('.app form.row .form-group:eq(' + i + ') [type="text"]').val();
                formData.cre_chushi_id = $('.app form.row .form-group:eq(' + i + ') [type="hidden"]').val();
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
             chushiId: $("#cre_chushi_id").val()
         },
         dataType: 'json',
         success: function (data) {
             if (data.flag == 1) {
                 // 发送保存请求
                 $.ajax({
                     url: "/dagl/bmgl/dynamicAdd",
                     type: 'post',
                     data: {
                         jsonStr: JSON.stringify(formData),
                         tName: tName
                     },
                     dataType: 'json',
                     success: function (data) {
                         layer.msg("保存成功！", {
                             icon: 1,
                             time: 1000
                         }, function (index) {
                            // parent.dianjixiaoguo();
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

function addSubmitForm() {


    // basefolder_unit 特殊字段

    // 清除错误提示
    $('em.input-em').html('');

    // 进行表单校验
    if (!checkFormData()) {
        layer.msg("表单数据不合法，请修改后重试！",{icon: 0,time:2000});
        return;
    }

    // 声明表单提交的数据
    var formData = {};

    // 判断控件类型并获取控件名和值
    for (var i = 0; i < $('.app form.row .form-group').length; ++i) {

        if (hiddenDataArray.indexOf($('.app form.row .form-group:eq(' + i + ') label').html()) !== -1) {
        	if (form[i].COLUMN_NAME === 'basefolder_unit') {
                formData.basefolder_unit = $('.app form.row .form-group:eq(' + i + ') [type="text"]').val();
                formData.cre_chushi_id = $('.app form.row .form-group:eq(' + i + ') [type="hidden"]').val();
            }
            //formData[form[i].COLUMN_NAME] = $('.app form.row .form-group:eq(' + i + ') [type="hidden"]').val();
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
            chushiId: $("#cre_chushi_id").val()
        },
        dataType: 'json',
        success: function (data) {
            if (data.flag == 1) {
                // 添加载动画
                var index1 = layer.msg('保存中，请稍等...', {
                    icon: 16,
                    shade: [0.1, '#fff'],
                    time: false
                });
                // 发送保存请求
                $.ajax({
                    url: "/dagl/bmgl/dynamicAdd",
                    type: 'post',
                    data: {
                        jsonStr: JSON.stringify(formData),
                        tName: tName
                    },
                    dataType: 'json',
                    success: function (data) {
                        layer.msg("保存成功！", {
                            icon: 1,
                            time: 1000
                        }, function (index) {
                            layer.close(index1);
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

//数组移动置顶
function toFirst(fieldData,index) {

    if(index!=0){

        // fieldData[index] = fieldData.splice(0, 1, fieldData[index])[0]; 这种方法是与另一个元素交换了位子，

        fieldData.unshift(fieldData.splice(index , 1)[0]);

    }

}