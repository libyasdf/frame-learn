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
var notAdmin = "";
var treePid = "";
var chushiid = "";
$(function () {
	  //当前用户的角色编号
	rolesNo= getcookie("rolesNo");
//	if(rolesNo.indexOf("D702")>-1){
//		$(".btn-container").append('  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\
//            <button class="btn btn-primary hp_form_ys">保存样式</button>')
//	}
	
    // 获取门类

    categoryCode = GetRequest().categoryCode;
    treeWhere = window.parent.getTreeWhere();
    treePid = window.parent.getCurrentRoot();//获取全宗的code和name
    fids = GetRequest().fids;
    notAdmin = GetRequest().notAdmin;
    // 获取表名，当前标签页层级，标签页总数
    tName = $('.tab-active', window.parent.document).attr('data-TABLE_NAME');
    all = $('.tab', window.parent.document).length;
    ranking = $('.tab-active', window.parent.document).index() + 1;

    // 获取档号规则
    getfileNumberRule();

    getParentData();

    //如果是Q2天假批量添加按钮
    if("q2" == categoryCode){
        $("#buttons").append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button class=\"btn btn-primary plxz\">批量新增</button>");
        //初始化按钮
        $('.app .btn.btn-primary.plxz').click(PLSubmitForm);
    }
});

/**
 * 获取当前日期yyyymmdd
 */
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

/**
 * 获取父级的数据
 * @returns
 */
function getParentData() {
    $.ajax({
        url: '/dagl/bmgl/findParentData',
        data: {
            tName: tName, // 表名
            fids: fids // 
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            parentData = data;
            console.log("父级数据：\n" + JSON.stringify(parentData))
        },
        error: function (err) {
            layer.msg('加载失败，请刷新页面',{icon: 2,time:1000});
            console.log("加载父级数据失败，错误信息：\n", err);
        }
    });
}

/**
 * 获取档号规则
 */
function getfileNumberRule() {
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

    // 父表，子表中文名
    console.log('父表中文名：', mColNameCHN);
    console.log('子表中文名：', sColNameCHN);
}

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
                //档号规则加数据类型
                // 根据档号规则添加事件
                for (var j = 0; j < fileNumberRule.length; ++j) {
                    if(fileNumberRule[j].RULE_FIELD == usefullData[i].COLUMN_NAME){
                        fileNumberRule[j].COLUMN_TYPE = usefullData[i].COLUMN_TYPE
                    }
                }
            }
            // 让 iframe 自适应高度，但最高 600px
            // $('iframe', window.parent.document).height($(document).height()).css({
            //     'max-height': 'none',
            //     overflow: 'auto'
            // });

            // 给保存按钮绑定方法
            $('.app .btn.btn-primary.bc').click(submitForm);
            $('.app .btn.btn-primary.cj').click(addSubmitForm);

            // 根据档号规则添加事件
            for (var i = 0; i < fileNumberRule.length; ++i) {

                var $input = $('.form-group label:contains(' + fileNumberRule[i].RULE_NAME + ')').parent().next().children().eq(0);

                console.log('拼接档号控件名', fileNumberRule[i].RULE_NAME, $input);
                // 给文本框绑定事件
                if (!$input.hasClass('dropdown') ) {//&& fileNumberRule[i].COLUMN_TYPE =="2"
                    $input.change({ length: fileNumberRule[i].NUMB_LENGTH }, checkInputLength);
                }
            }
            xunifenlei();
            quanzong();//全宗和类别号（门类的回填）
            changeMSValue();//回填档号
            hxfd();
            //if(tName.indexOf("_dak")>-1){
            //归档库直接回填
            getLastNum();
            //数量的相关字段给默认值0，并且只读
            defaultNum();
            //}
        },
        error: function (err) {
            layer.msg('加载失败，请刷新页面',{icon: 2,time:1000});
            console.log("加载表单失败，错误信息：\n", err);
        },
        complete: function () {
//
//            if(rolesNo.indexOf("D702")>-1){//必须执行的
//            var hp_form_js="<div class=\"hp_form_select\" style=\"width:24px;display: inline-block;position: absolute;right:3px;top: 5px;\">\n" +
//                "        <div class=\"hp_lineheight_0\" style=\"text-align:center;\">\n" +
//                "            <span class=\"glyphicon glyphicon-chevron-up hp_form_glyphicon\" style=\"\"></span>\n" +
//                "        </div>\n" +
//                "        <div class=\"hp_lineheight_0\">\n" +
//                "            <span class=\"glyphicon glyphicon-chevron-left hp_form_glyphicon hp_float_left\" style=\"\"></span>\n" +
//                "            <span class=\"glyphicon glyphicon-chevron-right hp_form_glyphicon hp_float_right\" style=\"\"></span>\n" +
//                "        </div>\n" +
//                "        <div class=\"hp_lineheight_0\" style=\"text-align:center;\">\n" +
//                "            <span class=\"glyphicon glyphicon-chevron-down hp_form_glyphicon\"></span>\n" +
//                "        </div>\n" +
//                "    </div>";
//            $(".form-group").each(function(index,element){//添加图标
//                $(element).append(hp_form_js);
//            });
//            $(".glyphicon-chevron-right").click(function () {//点击右侧调整图标方法
//                //var xzq_form=$(this).parents(".form-group").find("div:eq(1)");
//                var xzq_form=$(this).parents(".form-group");
//                var form_class=xzq_form.attr("class");
//                var form_class_col=form_class.replace("form-group ","");
//                var form_class_num=form_class_col.replace("col-xs-","");
//                var form_class_num_new=1+parseInt(form_class_num);
//                console.log(form_class_num_new);
//                if(form_class_num_new>12){
//                    form_class_num_new=12;
//                }
//                xzq_form.removeClass(form_class_col);
//                xzq_form.addClass("col-xs-"+form_class_num_new);
//            })
//            $(".glyphicon-chevron-left").click(function () {
//                //var xzq_form=$(this).parents(".form-group").find("div:eq(1)");
//                var xzq_form=$(this).parents(".form-group");
//                var form_class=xzq_form.attr("class");
//                var form_class_col=form_class.replace("form-group ","");
//                var form_class_num=form_class_col.replace("col-xs-","");
//                var form_class_num_new=form_class_num-1;
//                if(form_class_num_new<3){
//                    form_class_num_new=3;
//                }
//                xzq_form.removeClass(form_class_col);
//                xzq_form.addClass("col-xs-"+form_class_num_new);
//            })
//            $(".glyphicon-chevron-up").click(function () {
//                var xzq_form=$(this).parents(".form-group");
//                xzq_form.prev().before(xzq_form[0]);
//            });
//            $(".glyphicon-chevron-down").click(function () {
//                var xzq_form=$(this).parents(".form-group");
//                xzq_form.next().after(xzq_form[0]);
//            });
//            //保存按钮点击
//            $(".hp_form_ys").click(function () {
//                var ys=[];//元素顺序即排序顺序
//                $(".form-group").each(function(index,element){
//                    var key=$(element).children("div:eq(1)").find("input,button").attr("name");
//                    var form_width_class=$(element).attr("class").replace("form-group ","");//宽度
//                    var map={};
//                    map.key=key;
//                    map.value=form_width_class;
//                    ys.push(map)
//                });
//                //保存当前样式数据到后台;ys值,例174行
//                console.log("JSON.stringify(ys):"+JSON.stringify(ys));
//                $.ajax({
//                    'url' :'/dagl/bmgl/editStyle',
//                    'type':'POST',
//                    'dataType':'JSON',
//                    'data' : {
//                        'list':JSON.stringify(ys),
//                        'tName':tName
//                    }
//                }).done(function( data, textStatus, jqXHR ) {
//                    if(data>=1){
//                    	layer.msg("保存成功",{icon:1,time:2000})
//                    }else{
//                    	layer.msg("保存失败",{icon:0,time:2000})
//                    	
//                    }
//                })
//            });
//        }
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

            })
        }
    })
}

//数组移动置顶
function toFirst(fieldData,index) {

    if(index!=0){

        // fieldData[index] = fieldData.splice(0, 1, fieldData[index])[0]; 这种方法是与另一个元素交换了位子，

        fieldData.unshift(fieldData.splice(index , 1)[0]);

    }

}
/**
 * 带虚拟分类的数据
 * @returns
 */
function xunifenlei(){
	var jsontree = $.parseJSON(treeWhere);
	for(var i in jsontree){
		if($("[name="+i)[0].tagName=="INPUT"){
			$("[name="+i).val(jsontree[i]);
			$("[name="+i).attr("readonly","readonly");
		}else{
			$("[name="+i).attr("disabled","disabled");
			$("[name="+i).html(jsontree[i]+"<span class='caret frcaret'></span>");
			$("[name="+i).css({"border-color":"rgb(204, 204, 204)","background-color":"rgb(238, 238, 238)"})
			$("[name="+i).next("ul").find("li").each(function(){
				if($(this).html()==jsontree[i]){
					$("[name="+i).attr("data-value",$(this).attr("data-value"));
				}
			})
			
		}
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

/*
 * 回显父级的档号规则
 */
function hxfd() {
	if(fids=="" || fids.split(",").length != 1){
	    //未勾选，或者勾选了多个父节点的时候，不回填
		return;
	}
    if (ranking == 1) {
        $("input[name='" + mColName.toLowerCase() + "']").val(parentData[mColName.toUpperCase()]);
        $("input[name='" + mColName.toLowerCase() + "']").attr("disabled",true);
    } else if (ranking == 2 && all == 2) {
        $("input[name='" + mColName.toLowerCase() + "']").val(parentData[mColName.toUpperCase()]);
        $("input[name='" + mColName.toLowerCase() + "']").attr("disabled",true);
//        if(thisTotal.length<fileNumberRule[fileNumberRule.length-1].NUMB_LENGTH){
//        	var wei =(fileNumberRule[fileNumberRule.length-1].NUMB_LENGTH-0) - (thisTotal.length -0); 
//        	for(var j = 1;j<=wei;j++){
//        		thisTotal="0"+thisTotal;
//        	}
//        }
        //$("input[name='archive_no']").val(parentData[mColName]+fileNumberRule[fileNumberRule.length-2].CONNECTOR+thisTotal);
        //$("input[name='"+fileNumberRule[fileNumberRule.length-1].RULE_FIELD+"']").val(thisTotal);
        var s=0;
        for(var i in fileNumberRule){
        	s++;
        	if(s==fileNumberRule.length){
        		break;
        	}
        	
        	var name = fileNumberRule[i].RULE_FIELD;
        	var val = "111";//(((typeof(fileNumberRule[i].RULE_FIELD)==="undefined"||parentData[fileNumberRule[i].RULE_FIELD.toUpperCase()])==="")?"请选择":parentData[fileNumberRule[i].RULE_FIELD.toUpperCase()]);
        	if(typeof(fileNumberRule[i].RULE_FIELD)=='undefined'){
        		val="请选择";
        	}else{
        		val=parentData[fileNumberRule[i].RULE_FIELD.toUpperCase()];
        	}
        	if($("[name='" + name + "']").hasClass("dropdown-toggle")){
        		$("[name='" + name + "']").html(val+'<span class="caret frcaret"></span>')
        	}else{
        		$("[name='" + name + "']").val(val)
        	}
            $("[name='" + name + "']").attr("disabled",true);
        }
    } else if (ranking == 2) {
        $("input[name='" + mColName.toLowerCase() + "']").val(parentData[mColName.toUpperCase()]);
        $("input[name='" + mColName.toLowerCase() + "']").attr("disabled",true);
//        if(thisTotal.length<fileNumberRule[fileNumberRule.length-2].NUMB_LENGTH){
//        	var wei =(fileNumberRule[fileNumberRule.length-2].NUMB_LENGTH-0) - (thisTotal.length -0); 
//        	for(var j = 1;j<=wei;j++){
//        		thisTotal="0"+thisTotal;
//        	}
//        }
//        $("input[name='" + sColName.toLowerCase() + "']").val(parentData[mColName]+fileNumberRule[fileNumberRule.length-3].CONNECTOR+thisTotal);
//        $("input[name='"+fileNumberRule[fileNumberRule.length-2].RULE_FIELD+"']").val(thisTotal);
        var s=0;
        for(var i in fileNumberRule){
        	s++;
        	if(s==fileNumberRule.length){
        		break;
        	}
        	var name = fileNumberRule[i].RULE_FIELD;
        	var val = "111";//(((typeof(fileNumberRule[i].RULE_FIELD)==="undefined"||parentData[fileNumberRule[i].RULE_FIELD.toUpperCase()])==="")?"请选择":parentData[fileNumberRule[i].RULE_FIELD.toUpperCase()]);
        	if(typeof(fileNumberRule[i].RULE_FIELD)=='undefined'){
        		val="请选择";
        	}else{
        		val=parentData[fileNumberRule[i].RULE_FIELD.toUpperCase()];
        	}
        	if($("[name='" + name + "']").hasClass("dropdown-toggle")){
        		$("[name='" + name + "']").html(val+'<span class="caret frcaret"></span>')
        	}else{
        		$("[name='" + name + "']").val(val)
        	}
            $("[name='" + name + "']").attr("disabled",true);
        }
    } else if (ranking == 3) {
        $("input[name='" + mColName.toLowerCase() + "']").val(parentData[mColName.toUpperCase()]);
        $("input[name='" + mColName.toLowerCase() + "']").attr("disabled",true);
//        if(thisTotal.length<fileNumberRule[fileNumberRule.length-1].NUMB_LENGTH){
//        	var wei =(fileNumberRule[fileNumberRule.length-1].NUMB_LENGTH-0) - (thisTotal.length -0); 
//        	for(var j = 1;j<=wei;j++){
//        		thisTotal="0"+thisTotal;
//        	}
//        }
//        $("input[name='archive_no']").val(parentData[mColName]+fileNumberRule[fileNumberRule.length-2].CONNECTOR+thisTotal);
//        $("input[name='"+fileNumberRule[fileNumberRule.length-1].RULE_FIELD+"']").val(thisTotal);
       $.ajax({
    	   url:"/dagl/bmgl/findFguanlian",
    	   data:{tName:tName},
           type: 'post',
           dataType: 'text',
           success: function (data) {
        	   $("input[name='" + data.toLowerCase() + "']").val(parentData[data.toUpperCase()]);
               $("input[name='" + data.toLowerCase() + "']").attr("disabled",true);
    	   }
       })
        
        var s=0;
        for(var i in fileNumberRule){
        	s++;
        	if(s==fileNumberRule.length){
        		break;
        	}
        	var name = fileNumberRule[i].RULE_FIELD;
        	
        	var val = "111";//(((typeof(fileNumberRule[i].RULE_FIELD)==="undefined"||parentData[fileNumberRule[i].RULE_FIELD.toUpperCase()])==="")?"请选择":parentData[fileNumberRule[i].RULE_FIELD.toUpperCase()]);
        	if(typeof(fileNumberRule[i].RULE_FIELD)=='undefined'){
        		val="请选择";
        	}else{
        		val=parentData[fileNumberRule[i].RULE_FIELD.toUpperCase()];
        	}
        	if($("[name='" + name + "']").hasClass("dropdown-toggle")){
        		$("[name='" + name + "']").html(val+'<span class="caret frcaret"></span>')
        	}else{
        		$("[name='" + name + "']").val(val)
        	}
            $("[name='" + name + "']").attr("disabled",true);
        }
    }
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
        	//alert(($(this).parent().prev().find('label').html())
            $(this).next().html($(this).parent().prev().find('label').html()+' 长度不超过'+length+'位！').css('color', 'red');
        } else {
            $(this).next().html('');
        }
    }

    // 拼接父子表 input 的值
    changeMSValue();
    getLastNum();
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

        var qTwoFolderNo = "";
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
            if("q2" == categoryCode){
                if (i === fileNumberRule.length - 2) {
                    qTwoFolderNo += val;
                } else if(i < fileNumberRule.length - 2){
                    qTwoFolderNo += val + fileNumberRule[i].CONNECTOR;
                }
            }
        }
        if("q2" == categoryCode){
            //q2特殊处理，回填案卷级档号  全宗加类别号
            $("[name= 'folder_no']").val(qTwoFolderNo);
            $("[name= 'folder_no']").attr("readonly","readonly");
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
	            //yzx
	            if(i==fileNumberRule.length){
	            	
	            }else{
	            valS += val + fileNumberRule[i].CONNECTOR;
	            }
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
    if("" != sColNameCHN){
        //排除只有一层的时候子表的字段为空，会置灰表单的第一个元素
        $inputS.attr("disabled",true);
    }
    /*if("" != chushiid){
        //选择了立卷单位，校验档号是否重复
        $.ajax({
            url: "/dagl/bmgl/isRepetition",
            type: 'post',
            data: {
                column: sColName,
                tName: tName,
                relevancyId: $("input[name='" + sColName.toLowerCase() + "']").val(),
                chushiId: chushiid,
                ranking : ranking
            },
            dataType: 'json',
            success: function (data) {
                if (data.flag == 1) {
                    //不重复什么都不做
                } else {
                    if(("" == fids || fids.split(",").length != 1) && ranking>1){
                        //不做回填,不做回填的是为勾选父级的，新增的是游离的数据，可以提示
                        $("input[name='" + sColName.toLowerCase() + "']").focus();
                        layer.msg("已存在" + sColNameCHN + ",请修改档号组成项！",{icon: 0,time:2000})
                    }else{
                        //单层的和第一层，档号规则都会自动回填，不会出现重复，只有做了档号回填的第二层或者第三层会跟游离的有冲突
                        layer.alert("未组卷的档案已存在" + sColNameCHN + ",请及时对未组卷的数据做出对应的处理！",{icon: 2});
                    }
                }
            }
        });
    }*/
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
	var blur="";
	//如果是日期类型，默认设置为当前日期yyyymmdd 王磊 20190403
	var defaultDay="";
	if(data.COLUMN_TYPE==5){
		blur=" onblur=bu0(this) ";
		defaultDay = getNowFormatDate();
	}
    $('.app form.row').append($(''
        + '<div class="form-group col-xs-6">'
        + '<div class="col-xs-3">'
        + (data.COLUMN_CAN_NULL === "F" ? '<span class="text-red">* </span>' : '')
        + '<label>' + data.COLUMN_CHN_NAME + '</label>'
        + '</div>'
        + '<div class="col-xs-9">'
        + '<input '+(data.COLUMN_NAME=="barcode" ? "readonly":"")+' onkeyup="InputChange('+ index +')"  type="text" class="form-control" '+blur+' value="'
        + (data.COLUMN_VALUE_DEFAULT ? data.COLUMN_VALUE_DEFAULT : defaultDay)
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

//        var $input = $(''
//            + '<div class="form-group col-xs-6">'
//            + '<div class="col-xs-3">'
//            + (data.COLUMN_CAN_NULL === "F" ? '<span class="text-red">* </span>' : '')
//            + '<label>' + data.COLUMN_CHN_NAME + '</label>'
//            + '</div>'
//            + '<div class="col-xs-9">'
//            + '<input type="text" class="form-control" onchange="InputChange('+ index +')" value="'
//            + (data.COLUMN_VALUE_DEFAULT ? data.COLUMN_VALUE_DEFAULT : "")
//            + '" name="' + data.COLUMN_NAME + '" id="cre_chushi_id1"  />'
//            + '<em class="input-em text-info"></em>'
//            + '<input type="hidden" id="cre_chushi_id" />'
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
                + '<button name="'+data.COLUMN_NAME+'"  class="btn btn-default dropdown-toggle text-left" data-toggle="dropdown" data-value="">'
                // + (data.COLUMN_VALUE_DEFAULT ? data.COLUMN_VALUE_DEFAULT : "请选择")
                + '请选择'
                + '<span class="caret frcaret"></span>'
                + '</button>'
                + '<ul class="dropdown-menu" id="dropdown-menu">'
                + '</ul>'
                + '</div>'
                + '<em class="input-em text-info"></em>'
                + '</div>'
                + '</div>'
            );

      	if(rolesNo.indexOf("D702")>-1&&notAdmin!="ok"){
            //档案馆管理员
      		//$input.find("#cre_chushi_id1").removeAttr("readonly");
    		if(fids!="" && fids.split(",").length == 1){
    		    //勾选了父级，并且只勾选了一个的时候，回填父级的立卷单位
//    			$input.find("#cre_chushi_id1").attr("readOnly",true);
//    			$input.find("#cre_chushi_id1").val(parentData.BASEFOLDER_UNIT)
//          		$input.find("#cre_chushi_id").val(parentData.CRE_CHUSHI_ID)
    			$dropdownDom.find("button").attr("data-value",parentData.CRE_CHUSHI_ID);
        		$dropdownDom.find("button").html(parentData.BASEFOLDER_UNIT+"<span class='caret frcaret'></span>")
    			$dropdownDom.find("button").attr("disabled",true);
    		}else if ("q2" == categoryCode){
                //Q2展示所有的立卷单位
                var daglLjdw = Dictionary.getNameAndCode({mark: "dagl_ljdw", type: "1"});
                var j=0;
                for (var i in daglLjdw) {
                    $dropdownDom.find('ul.dropdown-menu')
                        .append($('<li class="dropdown-item" onclick="InputChange('+ index +')" data-value="' + i + '">' + daglLjdw[i] + '</li>'));

                    if(zujuan=='archive_flag'&&j==0){
                        $dropdownDom.find('button').html(daglLjdw[i]+'<span class="caret frcaret"></span>').attr("data-value",i)
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
                    chushiid = $(this).attr('data-value');
                    // 改变按钮的值
                    $dropdownDom.find('[data-toggle="dropdown"]')
                        .html($(this).html() + '<span class="caret frcaret"></span>')
                        .attr('data-value', $(this).attr('data-value'));
                    //单位预立库的立卷单位会影响到档号，所以重新进行查询最后一个档号，这里应该不会走，因为Q2档案值会在归档库中显示
                    if(tName.indexOf("_dak")==-1){
                        getLastNum();
                    }
                });
            }else{
    		    //勾选了父级节点，并且不是Q2档案的档案立卷单位应该回填父级的立卷单位
    			//$input.find("#cre_chushi_id1").removeAttr("readonly"); 
    	        // 请求菜单项
    			var zujuan=data.COLUMN_NAME;
    	        $.ajax({
    	            url: "/dagl/xtpz/category/LJDWmark?code="+categoryCode,
    	            type: 'get',
    	            dataType: 'json',
    	            asyn:true,
    	            success: function (data) {
                        var count = 0;
    	                // 解析 data
    	                var data = data.data;
    	                // console.log('加载下拉菜单的数据\n', data);
    	                var j=0;
    	                for (var i in data) {
                            count++;
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
                            chushiid = $(this).attr('data-value');
    	                    // 改变按钮的值
    	                    $dropdownDom.find('[data-toggle="dropdown"]')
    	                        .html($(this).html() + '<span class="caret frcaret"></span>')
    	                        .attr('data-value', $(this).attr('data-value'));
    	                    if(JSON.stringify(fileNumberRule).indexOf($dropdownDom.find("button").attr("name"))>-1){
    	                    	// 拼接字符串
    	                    changeMSValue();
    	                    }

                            //单位预立库进行查询最后一个档号
                            if(tName.indexOf("_dak")==-1){
                                getLastNum();
                            }

    	                });
    	            },
    	            error: function (err) {
    	                layer.msg('加载下拉菜单失败，请稍后再试',{icon: 2,time:1000});
    	                console.log("加载下拉菜单失败，错误信息：\n", err);
    	            }
    	        });

    		}
      	}else{
      	    //录入人，回填立卷单位
            if(fids!="" && fids.split(",").length == 1){
                //只勾选了一个父节点的时候回填立卷单位
//    			$input.find("#cre_chushi_id1").attr("readOnly",true);
//    			$input.find("#cre_chushi_id1").val(parentData.BASEFOLDER_UNIT)
//          		$input.find("#cre_chushi_id").val(parentData.CRE_CHUSHI_ID)
                /*$dropdownDom.find("button").attr("data-value",parentData.CRE_CHUSHI_ID);
                $dropdownDom.find("button").html(parentData.BASEFOLDER_UNIT+"<span class='caret frcaret'></span>")*/


                $dropdownDom.find('ul.dropdown-menu')
                    .append($('<li class="dropdown-item" onclick="InputChange('+ index +')" data-value="' + parentData.CRE_CHUSHI_ID + '">' + parentData.BASEFOLDER_UNIT + '</li>'));

                var text = $dropdownDom.find('button').html().split('<')[0];
                for(var i = 0; i < $dropdownDom.find('.dropdown-item').length; ++i) {
                    if($dropdownDom.find('.dropdown-item:eq('+i+')').html() === text) {
                        $dropdownDom.find('button').attr('data-value', $dropdownDom.find('.dropdown-item:eq('+i+')').attr('data-value'));
                        break;
                    }
                }

                $dropdownDom.find('.dropdown-item').click(function () {
                    chushiid = $(this).attr('data-value');
                    // 改变按钮的值
                    $dropdownDom.find('[data-toggle="dropdown"]')
                        .html($(this).html() + '<span class="caret frcaret"></span>')
                        .attr('data-value', $(this).attr('data-value'));
                    if(JSON.stringify(fileNumberRule).indexOf($dropdownDom.find("button").attr("name"))>-1){
                        // 拼接字符串
                        changeMSValue();
                    }
                    //单位预立库进行查询最后一个档号
                    getLastNum();
                });

                $dropdownDom.find('.dropdown-item').click();
                $dropdownDom.find("button").attr("disabled",true);

            }else{
                $.ajax({
                    url: "/dagl/xtpz/category/LJDWmark?code="+categoryCode+"&userId="+getcookie("userid"),
                    type: 'get',
                    dataType: 'json',
                    asyn:true,
                    success: function (data) {
                        var count = 0;
                        if(data.flag==1){

                            for(var i in data.data){
                                count++;
                                $dropdownDom.find('ul.dropdown-menu')
                                    .append($('<li class="dropdown-item" onclick="InputChange('+ index +')" data-value="' + i + '">' + data.data[i] + '</li>'));

                            }

                            var text = $dropdownDom.find('button').html().split('<')[0];
                            for(var i = 0; i < $dropdownDom.find('.dropdown-item').length; ++i) {
                                if($dropdownDom.find('.dropdown-item:eq('+i+')').html() === text) {
                                    $dropdownDom.find('button').attr('data-value', $dropdownDom.find('.dropdown-item:eq('+i+')').attr('data-value'));
                                    break;
                                }
                            }

                            $dropdownDom.find('.dropdown-item').click(function () {
                                chushiid = $(this).attr('data-value');
                                // 改变按钮的值
                                $dropdownDom.find('[data-toggle="dropdown"]')
                                    .html($(this).html() + '<span class="caret frcaret"></span>')
                                    .attr('data-value', $(this).attr('data-value'));
                                if(JSON.stringify(fileNumberRule).indexOf($dropdownDom.find("button").attr("name"))>-1){
                                    // 拼接字符串
                                    changeMSValue();
                                }
                                //单位预立库进行查询最后一个档号
                                getLastNum();
                            });
                        }
                        if(count == 1){
                            //只有一个立卷单位的时候，选中并置灰
                            $dropdownDom.find('.dropdown-item').click();
                            $dropdownDom.find("button").attr("disabled",true);
                        }else{
                            //多个立卷单位的时候，需要先选择立卷单位
                            if(("" == fids || fids.split(",").length != 1) && ranking>1){
                                //不做回填
                                //var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                //parent.layer.title('新增 <span style="color:red;font-size:13px;">* 请优先选择立卷单位，来判断当前档号是否可用</span>', index)  //再改变当前层的标题
                            }else{
                                var iframeIndex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                parent.layer.title('新增 <span style="color:red;font-size:13px;">* 选择立卷单位回填当前的档号</span>', iframeIndex)  //再改变当前层的标题
                            }

                            //layer.msg("请选择立卷单位回填当前的档号！",{icon:0,time:1000});
                        }
                    }
                })
            }
      	}
       // $('.app form.row').append($input);
      	$('.app form.row').append($dropdownDom);
        // 添加到数组中
        hiddenDataArray.push(data.COLUMN_CHN_NAME);
    } else {
        //立卷单位以外的下拉框的初始化
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
                        $dropdownDom.find("button").attr("disabled",true);
                    }
                }
                /*if(){
                    //档案实体状态的初始化
                }*/
                
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
                        getLastNum();
                    }
                    //单位预立库进行查询最后一个档号
                    if(tName.indexOf("_dak")==-1){
                        getLastNum();
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
                isRule++;//我是档号规则
                return false;
            }
        }
    }

    if(0 ==isRule){//不是档号规则字段
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
                    if (day > 29) {
                        showTipEm(index, form[index].COLUMN_CHN_NAME + ' 日期校验未通过 闰年2月不能超过29天！', 'red');
                        return false;
                    }
                } else {
                    if (day > 28) {
                        showTipEm(index, form[index].COLUMN_CHN_NAME + ' 日期校验未通过 平年2月不能超过28天！', 'red');
                        return false;
                    }
                }
            } else if (month === 1 || month ===  3 || month ===  5 || month ===  7 || month ===  8 || month ===  10 ||month ===  12) {
                if (day > 31) {
                    showTipEm(index, form[index].COLUMN_CHN_NAME + ' 日期校验未通过 不能超过31天！', 'red');
                    return false;
                }
            } else {
                if (day > 30) {
                    showTipEm(index, form[index].COLUMN_CHN_NAME + ' 日期校验未通过 不能超过30天！', 'red');
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
    //如果是案卷级档号，做一次校验，不可重复--王富康2019-03-18
    /*if (label == "案卷级档号") {
        console.log(index + '.' + label + ' COLUMN_CHN_NAME %c 校验失败 ' + validatorData.COLUMN_CHN_NAME, "color:red;");
        return false;
    }*/

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
    $('.app .btn.btn-primary.bc').attr("disabled",true);
    // basefolder_unit 特殊字段

    // 清除错误提示
    $('em.input-em').html('');

    // 进行表单校验
    if (!checkFormData()) {
        layer.msg("表单数据不合法，请修改后重试！",{icon: 0,time:2000});
        $('.app .btn.btn-primary.bc').attr("disabled",false);
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
             chushiId: chushiid,
             ranking : ranking
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
            	 var year_folder_no = formData.year_folder_no;
                 $.ajax({
                     url: "/dagl/bmgl/dynamicAdd",
                     type: 'post',
                     data: {
                         jsonStr: JSON.stringify(formData),
                         tName: tName,
                         fids:fids,
                         all:all,
                         ranking:ranking
                     },
                     dataType: 'json',
                     success: function (data) {
                         layer.msg("保存成功！", {
                             icon: 1,
                             time: 1000
                         }, function (index) {
//				                        	 if(rolesNo.indexOf("D702")>-1){
//					                        	 layer.confirm('亲爱的管理员您好。您可以将刚录入的数据的录入人设置成其他人员，是否要这样做吗？'
//					                        		,function(){
//					                        		 tabUserId(data.uuid,0);
//					                        	 },function(){
//					                        		 var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
//					                                  parent.layer.close(index); //再执行关闭
//					                        	 })
//				                        	 }else{
//				                        		  var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
//				                                  parent.layer.close(index); //再执行关闭
//				                        	 }

                            // parent.dianjixiaoguo();
                             //当你在iframe页面关闭自身时
                             var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                             parent.layer.close(index); //再执行关闭
                             layer.close(index1);
                            })
                     },
                     error: function (err) {
                         layer.msg('保存失败，请稍后再试',{icon: 2,time:1000});
                         console.log("保存表单失败，错误信息：\n", err);
                     }
                 });


             } else {
                 $('.app .btn.btn-primary.bc').attr("disabled",false);
                 if(("" == fids || fids.split(",").length != 1) && ranking>1){
                     //不做回填,不做回填的是为勾选父级的，新增的是游离的数据，可以提示
                     $("input[name='" + sColName.toLowerCase() + "']").focus();
                     layer.msg("已存在" + sColNameCHN + ",请修改档号组成项！",{icon: 0,time:2000})
                 }else{
                     //单层的和第一层，档号规则都会自动回填，不会出现重复，只有做了档号回填的第二层或者第三层会跟游离的有冲突
                     layer.alert("保存失败！未组卷的档案已存在" + sColNameCHN + ",请及时对未组卷的数据做出对应的处理！",{icon: 2});
                 }
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
    $('.app .btn.btn-primary.cj').attr("disabled",true);
//	   alert(fileNumberRule[fileNumberRule.length-1].NUMB_LENGTH)
//	   alert(fileNumberRule[fileNumberRule.length-1].RULE_FIELD)
//	   alert(mColName+sColName)

	   // basefolder_unit 特殊字段

    // 清除错误提示
    $('em.input-em').html('');

    // 进行表单校验
    if (!checkFormData()) {
        layer.msg("表单数据不合法，请修改后重试！",{icon: 0,time:2000});
        $('.app .btn.btn-primary.cj').attr("disabled",false);
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
//                formData.basefolder_unit = $('.app form.row .form-group:eq(' + i + ') [type="text"]').val();
//                formData.cre_chushi_id = $('.app form.row .form-group:eq(' + i + ') [type="hidden"]').val();
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
            chushiId: chushiid,
            ranking : ranking

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
                        tName: tName,
                        fids:fids,
                        all:all,
                        ranking:ranking
                    },
                    dataType: 'json',
                    success: function (data) {
                        layer.msg("保存成功！", {
                            icon: 1,
                            time: 1000
                        }, function (index) {
                        	//存加
                        	var danghao = $('[name="'+sColName+'"]').val()
                        	danghao=danghao.substring(0,danghao.length-(fileNumberRule[fileNumberRule.length-1].NUMB_LENGTH-0))
                        	var $hao = $('[name="'+fileNumberRule[fileNumberRule.length-1].RULE_FIELD+'"]');
                        	var haoResult = Number($hao.val())+1+"";
                        	var chazhi = fileNumberRule[fileNumberRule.length-1].NUMB_LENGTH-haoResult.length;
                        	if(chazhi>0){
                        		for(var i = 0;i<chazhi;i++){
                        			haoResult = '0' + haoResult;
                        		}
                        	}
                        	$hao.val(haoResult);
                        	$('[name="'+sColName+'"]').val(danghao+haoResult)
                        	//
                        	
                            layer.close(index1);
                            $('.app .btn.btn-primary.cj').attr("disabled",false);
                        	
//                        	 if(rolesNo.indexOf("D702")>-1){
//	                        	 layer.confirm('亲爱的管理员您好。您可以将刚录入的数据的录入人设置成其他人员，是否要这样做吗？',function(){
//	                        		 tabUserId(data.uuid,1);
//	                        	 })
//                        	 }
                        	
                        });
                    },
                    error: function (err) {
                        layer.msg('保存失败，请稍后再试',{icon: 2,time:1000});
                        console.log("保存表单失败，错误信息：\n", err);
                    }
                });
            } else {
                $('.app .btn.btn-primary.cj').attr("disabled",false);
                if(("" == fids || fids.split(",").length != 1) && ranking>1){
                    //不做回填,不做回填的是为勾选父级的，新增的是游离的数据，可以提示
                    $("input[name='" + sColName.toLowerCase() + "']").focus();
                    layer.msg("已存在" + sColNameCHN + ",请修改档号组成项！",{icon: 0,time:2000})
                }else{
                    //单层的和第一层，档号规则都会自动回填，不会出现重复，只有做了档号回填的第二层或者第三层会跟游离的有冲突
                    layer.alert("保存失败！未组卷的档案已存在" + sColNameCHN + ",请及时对未组卷的数据做出对应的处理！",{icon: 2});
                }

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

function tabUserId(id,type){//type =0为保存 1为存加
	var $selectUser =  $('<select class="form-control" id="select">\
		</select>')
		$.ajax({
			url:"/dagl/xtpz/category/queryLjUser",
			data:{code:categoryCode,chushi:$("[name='basefolder_unit']").attr("data-value")},
			async:false,
			dataType:"JSON",
			type:"get",
			success:function(o){
				for(var i =0;i<o.data.length;i++){
					$selectUser.append('<option value='+o.data[i].LRR_ID+'>'+o.data[i].LRR_NAME+'</option>')
				}
			}
		})
	layer.open({
		title:"选择用户",
		btn:['确认','取消'],
		content:"<div id='select'></div>",
		success: function(layero, index){
			layero.find('#select').append($selectUser)
		  },
		  yes: function(index, layero){
			    //按钮【按钮一】的回调
			  $.ajax({
				  url:"/dagl/bmgl/tabUser",
				  data:{tName:tName,id:id,userId:layero.find('#select').find('option:selected').val()},
				  type:"post",
				  async:false,
				  dataType:"json",
				  success:function(data){
					  if(data.flag==1){
						  layer.msg("保存成功！",{icon:1,time:1000},function(a){
							  layer.close(index)
							  if(type==0){
								  var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
					               parent.layer.close(index); //再执行关闭
							  }
						  })
						  
					  }else{
						  layer.msg("保存失败！",{icon:0,time:1000})
					  }
				  },
				  error:function(err){
					  layer.close(index)
				  }
			  })
			 },
			 btn2: function(index, layero){
				    //按钮【按钮二】的回调
				    //return false 开启该代码可禁止点击该按钮关闭
				 if(type==0){
					  var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		               parent.layer.close(index); //再执行关闭
				  }
			 }
			  
	})
}

/**
 * 保存表单内容并提交
 */
function PLSubmitForm() {

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
            chushiId: chushiid,
            ranking : ranking
        },
        dataType: 'json',
        success: function (data) {
            if (data.flag == 1) {
                // 发送保存请求

                var year_folder_no = formData.year_folder_no;
                    var url = "/modules/dagl/bmgl/plAddQTwo.html?year_folder_no="+year_folder_no;
                    var title = "批量新增";
                    layer.open({
                        type : 2,
                        shadeClose : true,
                        content : url,
                        area: ['40%', '35%'],
                        title: [title, 'font-size:16px;font-weight:bold;'],
                        end: function () {
                            // 添加载动画
                            var index1 = layer.msg('保存中，请稍等...', {
                                icon: 16,
                                shade: [0.1, '#fff'],
                                time: false
                            });
                            var addCount = getcookie("addCount");
                            //清空cookie
                            if("" == addCount){
                                //如果增加数量为“”，什么都不做，用于直接关闭弹框页面。
                                layer.close(index1);
                            }else if ("1" == addCount){
                                //取消
                                $.ajax({
                                    url: "/dagl/bmgl/dynamicAdd",
                                    type: 'post',
                                    data: {
                                        jsonStr: JSON.stringify(formData),
                                        tName: tName,
                                        fids:fids,
                                        all:all,
                                        ranking:ranking
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
                                            layer.close(index1);
                                        });
                                    },
                                    error: function (err) {
                                        layer.msg('保存失败，请稍后再试',{icon: 2,time:1000});
                                        console.log("保存表单失败，错误信息：\n", err);
                                    }
                                });
                            }else{
                                $.ajax({
                                    url: "/dagl/bmgl/dynamicPlAdd",
                                    type: 'post',
                                    data: {
                                        jsonStr: JSON.stringify(formData),
                                        tName: tName,
                                        addCount:addCount,
                                        categoryCode:categoryCode,
                                        fids:fids
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
                                            layer.close(index1);
                                        });
                                    },
                                    error: function (err) {
                                        layer.msg('保存失败，请稍后再试',{icon: 2,time:1000});
                                        console.log("保存表单失败，错误信息：\n", err);
                                    }
                                });
                            }
                        }
                    })

            } else {
                $("input[name='" + sColName.toLowerCase() + "']").focus();
                layer.msg("已存在" + sColNameCHN + ",请修改档号组成项！",{icon: 0,time:2000})
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
/**
 * @Author 王富康
 * @Description //TODO 获取最后一位，并回填。
 * @Date 2019/4/23 9:53
 **/
function getLastNum() {
    if(("" == fids || fids.split(",").length != 1) && ranking>1){
        //不做回填
        return;
    }
    //第一层，根据当前的档号规则查询数量
    //档号项的文本框name
    var columnName = "";
    //需要回填的文本框name
    var lastColumnName = "";
    //档号
    var danghao = "";
    if(ranking ==1){
        //获取档号字段
        if(all==1){
            //只有一层的
            columnName = "archive_no";
            lastColumnName = "year_folder_no";
        }else if(all==3){
            //三层的第一层
            columnName = "item_folder_no";
            lastColumnName = "item_id";
        }else{
            //其他的
            columnName = "folder_no"
            lastColumnName = "year_folder_no";
        }
        danghao = $("input[name='"+columnName+"']").val();

        if("" != $("input[name='"+lastColumnName+"']").val()){
            //如果已经有了最后一个号，那么就截取掉
            //也就是说，当档号项发生改变的时候，再次根据档号查询数量的时候，应该把最后的截取掉
            danghao = danghao.substring(0,danghao.length-$("input[name='"+lastColumnName+"']").val().length);
        }
        queryLastNum(danghao,columnName,lastColumnName,ranking);
    }else if(fids!="" && ranking ==2){
        //根据父级档号规则查询数量，这时应该是勾选了父级，并且只勾选了一个
        //获取档号字段
        if(ranking == all){
            //两层的第二层
            columnName = "folder_no";
            lastColumnName = "piece_no";
        }else if(all==3){
            //三层的第二层
            columnName = "item_folder_no";
            lastColumnName = "year_folder_no";
        }
        danghao = $("input[name='"+columnName+"']").val();
        queryLastNum(danghao,columnName,lastColumnName,ranking);
    }else if(fids!="" && ranking ==3){
        //根据父级档号规则查询数量，这时应该是勾选了父级，并且只勾选了一个
        //获取档号字段
        if(ranking == all){
            //三层层的第三层
            columnName = "folder_no";
            lastColumnName = "piece_no";
        }
        danghao = $("input[name='"+columnName+"']").val();
        queryLastNum(danghao,columnName,lastColumnName,ranking);
    }else{
        layer.msg("当前无法为您自动回填"+$("input[name='"+lastColumnName+"']").parents(".form-group").find("label").text(),{icon:0,time:1000});
    }


}

function queryLastNum(danghao,columnName,lastColumnName,ranking) {
    /*if(tName.indexOf("_dak") == -1){
        //单位预立库，需要考虑创建人处室，和创建人
        if("" == chushiid){
            var basefolderUnitName = $("button[name='basefolder_unit']").parents(".form-group").find("label").text();
            var lastName = $("input[name='"+lastColumnName+"']").parents(".form-group").find("label").text()
            layer.alert("请选择 "+basefolderUnitName+"<br/> 将自动回填 "+lastName);
            return;
        }
    }*/
    $.ajax({
        url: "/dagl/bmgl/getCountAdd",
        type: 'post',
        data: {
            danghao:danghao,
            columnName:columnName,
            tName: tName,
            chushiid: chushiid,
            lastColumnName:lastColumnName,
            ranking:ranking
        },
        dataType: 'json',
        success: function (data) {
            var thisTotal=(data.data-0+1);
            var length = 0;
            //查询该字段档号规则的长度，拼接0
            for(var i=0;i<fileNumberRule.length;i++){
                if(fileNumberRule[i].RULE_FIELD == lastColumnName){
                    length = fileNumberRule[i].NUMB_LENGTH;
                }
            }
            thisTotal = PrefixInteger(thisTotal,length);
            if(tName.indexOf("_dak") == -1 && "" !=chushiid){
                //单位预立库需要保证含有立卷单位，后台需要根据立卷单位和登录人过滤
                $("input[name='"+lastColumnName+"']").val(thisTotal);
                $("input[name='"+lastColumnName+"']").attr("disabled",true);
            }else if(tName.indexOf("_dak") > -1){
                //归档库不需要
                $("input[name='"+lastColumnName+"']").val(thisTotal);
                $("input[name='"+lastColumnName+"']").attr("disabled",true);
            }
            $("input[name='"+lastColumnName+"']").parent().find("em").html('');
            //重新回填档号规则
            changeMSValue();
        },
        error: function (err) {
            layer.msg('获取数据失败，请稍后再试',{icon: 2,time:1000});
            console.log("保存表单失败，错误信息：\n", err);
        }
    });
}

//num传入的数字，n需要的字符长度
function PrefixInteger(num, length) {
    for(var len = (num + "").length; len < length; len = num.length) {
        num = "0" + num;
    }
    return num;
}

//数量字段的默认值和只读
function defaultNum(){
    if(all !=ranking){
        $("input[name='page_num']").val("0");
        $("input[name='page_num']").attr("disabled",true);
        $("input[name='piece_num']").val("0");
        $("input[name='piece_num']").attr("disabled",true);
        $("input[name='item_piece_num']").val("0");
        $("input[name='item_piece_num']").attr("disabled",true);
        $("input[name='folder_num']").val("0");
        $("input[name='folder_num']").attr("disabled",true);
    }
}