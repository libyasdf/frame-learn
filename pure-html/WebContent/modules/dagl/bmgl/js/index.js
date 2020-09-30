// 全局变量
var printerHtml ="";//条码打印页面用到的选中的值
var tableListData = "";//当前列表数据
var tHeadData; // 表头标签
var tabCurrent; // 记录是哪个当前标签页
var tabData = []; // 记录所有标签页的信息
var checkBoxData = {}; // 选中的复选框的信息
var nodes = null; // 树节点
var easyQueryP = ""//简单查询的参数
var complexQueryP = ""//组合查询参数
var index;
var parameter;
var num = 1;//显示第几页
var ud = 0;// window.scrollTop;判断上下滚动
var lr = 0; //= window.scrollLeft;判断左右滚动
var select_no={};//字典名称和mark值
var treeindex;//关闭加载框用到
var dataZiDian={};//所有的字典值
var danweiindex;//单位查询加载标签
var all = 0; // 标签页总数
var ranking = 0; // 当前标签页层级
var rolesNo="";
var deleteTip="";//删除提示
var isRefresh=true;//限制切换页面刷新页面
var orderName="";//排序字段
var loadedDataTimer=null;
var RollTop=0;
var pageSize = 20;

var fondsNo = Dictionary.getNameAndCode({mark: "fonds_no", type: "1"});

// 页面加载完成
$(function () {
    var url = $("#left_ul").find("a.active").attr("url");
    layer.closeAll();

    parameter = new GetParameter(url);
    // 初始化左侧树 isQ2,true显示Q2，false不显示；isAdmin用于区分虚拟分类显示，1档案馆管理员，2录入人
    typeTree.init({isQ2:false,isAdmin:2});
    try{
        nodes = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();
        //加载当前录入人的立卷单位
        basefolderUnitInit();

    }catch (e) {
        console.log(e)
        layer.close(index);
        layer.alert("尚未授权档案录入人，请联系档案馆管理员，授权您的需要录入的立卷单位！",{icon:5});
        return;
    }
  //当前用户的角色编号
	rolesNo = getcookie("rolesNo");
	if(rolesNo.indexOf("D702")>-1){
		$("#rolesNo").show();
	}

    // 激活操作按钮
     $('.dropdown-toggle').dropdown();


});

/**
 * 初始化页面
 */
function initPage() {
    // 删除 layer.js 额外引用的 link 标签
    $('#layuicss-layer').remove();

    nodes = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();

    // 解除绑定方法
    $('.dropdown-menu .dropdown-item').unbind();

    // 初始化标签
    initTabs();

    // 事件绑定可以放在下面
    $('.dropdown-menu .dropdown-item:contains(新增)').click(newlyAdded);
    $('.dropdown-menu .dropdown-item:contains(替换)').click(replacelyAdded);
    $('.dropdown-menu .dropdown-item:contains(简单查询)').click(easyFind);
    $('.dropdown-menu .dropdown-item:contains(档案调整)').click(adjust);
    $('.dropdown-menu .dropdown-item:contains(q2变更)').click(qTwo);
    $('.dropdown-menu .dropdown-item:contains(组合查询)').click(complexFind);
    $('.dropdown-menu .dropdown-item:contains(移交)').click(submitData);
    $('.dropdown-menu .dropdown-item:contains(删除)').click(dels);
    $('.dropdown-menu .dropdown-item:contains(生成条码号)').click(saveCode);
    $('.dropdown-menu .dropdown-item:contains(打印条形码)').click(printerCode);
    $('.dropdown-menu .dropdown-item:contains(汇总)').click(gatherData);
    //$('.dropdown-menu .dropdown-item:contains(排序)').click(orderBy);
    // 单位预立库添加第一层表顺序调整的功能 王磊 20190423
    $('.dropdown-menu .dropdown-item:contains(顺序调整)').click(adjustFirstLevel);
}

/**
 * 加载标签页
 */
function initTabs() {

    var LableUrl = "";
    if (parameter.dak == "null") {
        LableUrl = "/dagl/bmgl/findLabel";
    } else if (parameter.dak == "dak") {
        LableUrl = "/dagl/bmgl/findLabelDAK"
    }
    nodes = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();
    $.ajax({
        url: LableUrl,
        data: {
            tName: nodes[0].categoryCode
        },
        type: 'get',
        dataType: 'json',
        async: true,
        success: function (data) {
            // 清空标签
            $('.tab-container .tab').remove();

            // 解析 data
            var data = data;

            // 加载标签
            for (var i = 0; i < data.length; ++i) {
                $('.select-container').before($(''
                    + '<div class="tab" data-TABLE_NAME="' + data[i].TABLE_NAME + '" style="border-top-left-radius:15px;border-top-right-radius:15px;">'
                    + data[i].TABLE_CHN_NAME
                    + '</div>')
                );

                // 初始化 tabData
                tabData.push(data[i].TABLE_NAME);

                // 初始化 checkBoxData
                checkBoxData[data[i].TABLE_NAME] = [];
            }

            // 激活第一个标签
            $('.tab-container .tab:eq(0)').addClass('tab-active');
            tabCurrent = $('.tab-container .tab:eq(0)').attr('data-TABLE_NAME');
           
            // 初始化面板
            initPanel();
           
        },
        error: function (err) {
            layer.msg('加载失败，请刷新页面',{icon: 2,time:1000});
        }
    });
  
}

/**
 * 切换标签页
 */
var index2;
function switchTab() {
	if((indexEasyFind==1&&isRefresh)){
    	layer.msg("请关闭查询页面后，再切换/点击便签",{icon: 0,time:2000})
    	return;
    }
	if((complexFinds==1&&isRefresh)){
		layer.msg("请关闭查询页面后，再切换/点击便签",{icon: 0,time:2000})
		return;
	}
	isRefresh=true;
	orderName="";
    // 解绑 click 事件
    $('.tab-container .tab').unbind('click');

    // 切换激活状态
    $('.tab-container .tab').removeClass('tab-active');
    $(this).addClass('tab-active');

    // 修改 tabCurrent
    tabCurrent = $(this).attr('data-TABLE_NAME');

    // 修改 checkBoxData
    var index = $(this).index();
    for (var i = index; i < tabData.length; ++i) {
        checkBoxData[tabData[i]] = [];
    }

    // 初始化面板
    initPanel();

    all = $('.tab', window.document).length;
    ranking = $('.tab-active', window.document).index() + 1;
	if(all>=2){
		if(ranking==all){
			$("#dangantiaozheng").hide();
		}else{
			$("#dangantiaozheng").show();
		}
	}
	//添加第一层表顺序调整功能控制 王磊 20190423
	/*if(ranking==1){
		$("#adjustOrderForFirstLevel").show();
	}else{
		$("#adjustOrderForFirstLevel").hide();
	}*/
}
var easyIndex;
function easyQuery(pm) {
    easyQueryP = pm;
    initPanel();
}

var orderIndex;
function orderQuery(on) {
    orderName = on;
    initPanel();
}

/**
 * 初始化面板
 */
var maintitle ="";
function initPanel() {
	if(easyQueryP!=""){
	    var testJson = $.parseJSON(easyQueryP);
	    if(testJson.maintitle!=null&&testJson.maintitle!=""){
	    	maintitle=testJson.maintitle;
	    }
	}
    num = 1;

    nodes = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();
    // 删除旧的面板，新建新的面板
    $('.tab-panel').remove();
    $('.total').remove();

    all = $('.tab', window.document).length;
    ranking = $('.tab-active', window.document).index() + 1;
    if(ranking+1<=all){
        deleteTip="会同时删除关联数据，确定要删除所选择的数据吗？"
    }else{
        deleteTip="确定要删除所选择的数据吗？";
    }
    nodes = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();
    if(nodes[0].categoryCode=='q2'){
        if(rolesNo.indexOf("D702")>-1){
            $("#q2").show();
        }
    }else{
        $("#q2").hide();
    }
    //单位预立库不需要汇总功能 王磊 20190423
    /*if(rolesNo.indexOf("D702")>-1){
        $("#huizong").show();
    }else{
        $("#huizong").hide();
    }*/
    if(all>=2){
        if(ranking==all){
            $("#dangantiaozheng").hide();
        }else{
            $("#dangantiaozheng").show();
        }
    }else{
        $("#dangantiaozheng").hide();
    }

    function getTree() {
        var object = {};
        getKeyValue(object, nodes[0]);
        return JSON.stringify(object);
    }

    var getfids="";
    // 获取当前激活标签的索引
    var indexIndex1 = $('.tab-active').index();
    if (indexIndex1 == 0) {
        getfids= "";
    }else{
        getfids= checkBoxData[tabData[indexIndex1 - 1]].toString();
    }
    //获取当前登录人，和选中的立卷单位
    var object = {};
    object["LLR_ID"] = getcookie("userid");
    object["LJDW_MARK"] = $("#basefolderUnit").val();

    $.ajax({
        url: "/dagl/bmgl/findAdd",
        data: {
            tName: tabCurrent // 表名
        },
        type: "post",
        dataType: 'json',
        success: function (data) {
            // 获取数据，并根据 COLUMN_ORDER 字段进行排序
            var data = data.sort(sortByNumber('COLUMN_LIST_ORDER'));

            var columnsArray = [];
            columnsArray.push({
                field: "state",
                checkbox: true,
                formatter : function(value, row, index) {
                    var tabName = $('.tab-active').attr('data-TABLE_NAME');
                    if($.inArray(row.RECID,checkBoxData[tabName])!= -1){
                        return {
                            checked : true
                        };
                    }

            }});//选择框
            columnsArray.push({
                field: 'cz'
                , title: '操作'
                , width: '80px'
                , align: "center"
                , formatter: function (value, row, index) {  //直接定义function,return就是html代码
                    if(!(row.ARCHIVE_ENTITY_STATUS=="已移交待汇总" || row.ARCHIVE_ENTITY_STATUS=="已归档")){
                        var html = "";
                        html += "<i class='glyphicon glyphicon-edit' title='修改' onclick=\'list.editFun(\"" + row.RECID + "\",\"" + row.ARCHIVE_ENTITY_STATUS + "\",\""+row.FHR_ID+"\",\""+row.FHR_NAME+"\")\'></i>&nbsp;&nbsp;";
                        html += "<i class='glyphicon glyphicon-trash' title='删除' onclick=\'list.deleteFun(\"" + row.RECID + "\",\"" + row.ARCHIVE_ENTITY_STATUS + "\",\""+row.FHR_ID+"\",\""+row.FHR_NAME+"\")\'></i>";
                        return html;
                    }
                }
            });//操作
            columnsArray.push({
                field: 'RECID'
                , title: '序号'
                , width: '50px'
                , order: "desc"
                , align:"center"
                , formatter: function (value, row, index) {
                    //计算序号，并存放开关ID，及已办事项ID
                    var pageSize=$('#right_table').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
                    var pageNumber=$('#right_table').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
                    var orderNum = pageSize * (pageNumber - 1) + index + 1;//计算序号
                    return "<span data-id='"+row.RECID+"' >"+orderNum+"</span>";    //返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
                }
            });//序号
            columnsArray.push({
                field: 'FHR_ID'
                ,title: '封号状态'
                , width: '100px'
                , align:"center"
                ,formatter:function(value,row,index){
                    if("" == value){
                        return "<span style='color: green;'>未封号</span>";
                    }else{
                        return "<span style='color: red;'>已封号</span>";
                    }
                }
            });//封号状态


            for (var i = 0; i < data.length; i++) {//Object.keys(obj) 获取key名称

                if (data[i].COLUMN_LIST_ISSHOW === "T") {
                    var columnWidth = data[i].LIST_COLUMN_WIDTH==null?"200":data[i].LIST_COLUMN_WIDTH;
                    columnsArray.push({
                        "title": data[i].COLUMN_CHN_NAME,
                        "field": data[i].COLUMN_NAME.toUpperCase(),
                        width: columnWidth+'px',//LIST_COLUMN_WIDTH
                        align:"center",
                        formatter: function (value, row, index) {
                            //获取当前列的宽度
                            var maxValWidth = $(this)[0].width;
                            maxValWidth = maxValWidth.substring(0,maxValWidth.length-2);
                            if(value!=null){
                                value = value+"";
                                var val = '';
                                for(var i=0;i<value.length;i++){
                                    //拼接值
                                    val+=value[i];
                                    //计算值的长度
                                    var valWidth = getTextWidth(val);
                                    if(valWidth+20>maxValWidth){
                                        //超出列表长度，截取
                                        val = val.substring(0,i-3)+"...";
                                        break;
                                    }
                                }
                                return  "<span title='"+value+"'>"+val+"</span>";
                            }else{
                                return "";
                            }
                        }
                    });
                }
            }
            //先销毁之前的表格
            $("#right_table").bootstrapTable("destroy");
            //初始化表格
            TableInit.init({
                domId: "right_table",
                url: "/dagl/bmgl/dynamicList",
                columns: columnsArray,
                queryParams: function (params) {
                    //这里将各个查询项添加到要传递的参数中
                    //可以做一些校验
                    params.resId = $('#left_ul').find('a.active').attr("id");
                    params.tName=tabCurrent,
                    params.fRecIds=getfids,
                    params.conditions=getTree(),
                    params.danweihao=$("#danweihao").val(),
                    params.all=all,
                    params.ljdanweiAndRenyuan=JSON.stringify(object),
                    params.parameters=easyQueryP,
                    params.complexParam=complexQueryP
                    return params;
                },
                readOnlyFn: function () {
                    // 给标签绑定事件
                    $('.tab-container .tab').unbind('click');
                    $('.tab-container .tab').click(switchTab);

                    $('tr.readOnly').find('td:not(:first,:eq(1))').attr("title", "点击查看详情");
                    $('tr.readOnly').find('td:not(:first,:eq(1))').unbind('click').bind('click', function (e) {
                        //取消事件冒泡
                        var evt = e ? e : window.event;
                        if (evt.stopPropagation) {
                            evt.stopPropagation();
                        } else {
                            evt.cancelBubble = true;
                        }
                        //取消事件冒泡 end
                        var id = $(this).parent().find("span[data-id]").attr("data-id");
                        var workItemId = $(this).parent().find("span[data-id]").attr("data-workitemid");
                        var resId = $('#left_ul').find('a.active').attr("id");
                        MyLayer.layerOpenUrl({
                            url: '/modules/dagl/bmgl/readonly.html?id='+id,
                            title: "档案详情"
                        });
                    });
                },
                onCheck: function (row) {
                    // 当前激活的标签页
                    var tabName = $('.tab-active').attr('data-TABLE_NAME');
                    checkBoxData[tabName].push(row.RECID);
                    console.log(checkBoxData[tabName]);
                },
                onUncheck: function (row) {

                    //删除id
                    // 当前激活的标签页
                    var tabName = $('.tab-active').attr('data-TABLE_NAME');
                    var indexOfId = checkBoxData[tabName].indexOf(row.RECID);
                    if (indexOfId > -1) {
                        checkBoxData[tabName].splice(indexOfId, 1);
                    }
                    console.log(checkBoxData[tabName]);
                },
                onCheckAll: function (rows) {
                    var tabName = $('.tab-active').attr('data-TABLE_NAME');
                    for (var i= 0;i<rows.length;i++){
                        if(checkBoxData[tabName].indexOf(rows[i].RECID) == -1){
                            checkBoxData[tabName].push(rows[i].RECID);
                        }
                    }
                    console.log(checkBoxData[tabName]);
                },
                onUncheckAll: function (rows) {
                    for (var i= 0;i<rows.length;i++){
                        var tabName = $('.tab-active').attr('data-TABLE_NAME');
                        //删除id
                        var indexOfId = checkBoxData[tabName].indexOf(rows[i].RECID);
                        if (indexOfId > -1) {
                            checkBoxData[tabName].splice(indexOfId, 1);
                        }

                    }
                    console.log(checkBoxData[tabName]);
                },
                responseHandler:function (res) {//参数sidePagination为server时，该方法必须返回total和rows
                    if (res.flag === '1'){
                        if( data.length==1){
                            $("#sqlStr").val(res.sqlStr);
                        }
                        return {
                            "total":res.data.total
                            ,"rows":res.data.rows
                        }
                    }else {
                        layer.msg("加载列表发生错误!", {icon: 0});
                    }
                    return {};
                }
            });
            // 给全局变量赋值
            tHeadData = data;
        },
        error: function (err) {
            layer.msg('加载失败，请刷新页面',{icon: 2,time:1000});
        }
    });
}

//查数据字典的内容
function findAllZiDian(MS){
	$.ajax({
		url:"/dagl/bmgl/byDataDictionary",
		data:{marks:MS},
		type: "post",
        dataType: 'json',
        async:false,
        success: function (data) {
        	dataZiDian=data;
        }
		
	})
}

//递归获得树节点字段值
function getKeyValue(obj, treeObj) {
    if (treeObj == null) {
        return "";
    }

    if (treeObj.pId != "" && treeObj.pId != null) {
        if("" != treeObj.columnName){
            var filename = treeObj.columnName;
            obj[filename] = treeObj.name;
        }
    } else {
        //obj.categoryCode=treeObj.categoryCode;
    }
    var pNode = treeObj.getParentNode();
    if (pNode != null) {
        getKeyValue(obj, pNode);
    }
}


/**
 * 获取系统唯一关键字
 * @return {String} 加载表格内容所需的 fids 参数
 */
function getFids() {

    // 获取当前激活标签的索引
    var indexIndex = $('.tab-active').index();

    if (indexIndex === 0) {
        return "";
    }
    return checkBoxData[tabData[indexIndex - 1]].toString();
}

function getTreeWhere() {//获取虚拟分类
    var object = {};
    getKeyValue(object, nodes[0]);
    return JSON.stringify(object);
}


//获取子节点，所有父节点的name的拼接字符串
//获取当前节点的根节点(treeNode为当前节点)
//获取全宗的code和name
function getCurrentRoot(){
    var object = {};
    getQuanKeyValue(object, nodes[0]);
    return JSON.stringify(object);
}

function getQuanKeyValue(obj, treeObj){


    if (treeObj == null) {
        return "";
    }

    if (treeObj.pId != "" && treeObj.pId != null) {
        if("" == treeObj.columnName){
            var filename = "leibiehaoName";
            obj[filename] = treeObj.name;
            var filecode = "leibiehaoCode";
            obj[filecode] = treeObj.id;
        }
    } else {//父节点为空的时候，是全宗
        var filename = "quanzongName";
        obj[filename] = treeObj.name;
        var filecode = "quanzongCode";
        obj[filecode] = treeObj.id;
        //obj.categoryCode=treeObj.categoryCode;
    }
    var pNode = treeObj.getParentNode();
    if (pNode != null) {
        getQuanKeyValue(obj, pNode);
    }

    /*if(nodes[0].getParentNode()!=null){
        var parentNode = nodes[0].getParentNode();
        var RootNode = getCurrentRoot(parentNode);
        return RootNode;
    }else{
        return nodes[0].id;
    }*/
}

// 阻止事件冒泡
function stopBubble() {
    window.event ? window.event.cancelBubble = true : event.stopPropagation();
}
/**
 * 单位提交按钮事件
 * @returns
 */
function danweiE(){
	checkBoxData[tabCurrent]=[];
    initPanel();
}


/**
 * 改变复选框状态
 */
function changeCheckBoxState() {

    // 当前激活的标签页
    var tabName = $('.tab-active').attr('data-TABLE_NAME');

    // 给全局变量增加或删除复选框 fid
    if ($(this).prop('checked') === true || $(this).prop('checked') === 'checked') {
    	$(this).parent().parent().css('background-color', '#ccc');
        if(checkBoxData[tabName].indexOf($(this).val())<0){
            checkBoxData[tabName].push($(this).val());
        }
    } else {
    	$(this).parent().parent().css('background-color', '');
        checkBoxData[tabName].removeItem($(this).val());
        $("#all").prop("checked",false);//只要有一个未勾选，清除全选的选中状态
    }

    var dataNum = $("#tbody").find("tr").length;//列表总数
    var checkedNum = 0;//勾选的总数
    $("#tbody").find("tr").each(function(){
        var obj1 =$(this).find("td").eq(0).find("input");
        if(obj1.prop("checked")==true){//未勾选的勾选
            checkedNum++;
        }
    });

    //如果所有的数据被勾选，全选按钮勾选
    if(dataNum == checkedNum){
        $("#all").prop("checked",true);
    }

}

/**
 * 生成条码号
 */
function saveCode(){
    if(checkBoxData[tabCurrent].length < 1){
        layer.msg("请选择需要生成条码的信息",{icon:0});
        return fasle;
    }
    $.ajax({
        type: "GET",
        url: "/dagl/bmgl/saveCode?ids="+checkBoxData[tabCurrent].toString()+"&tableName="+tabCurrent,
        dataType: "json",
        async: false,
        success: function (data) {
            layer.msg("生成条码成功！", {
                icon: 1,
                time: 1000
            }, function (index) {
                initPanel();
            });
        },
        error: function (data) {
        }
    });
}

/**
 * 打印条码
 */
function printerCode(){
    clearPrinterHtml();
    if(checkBoxData[tabCurrent].length < 1){
        layer.msg("请选择需要打印条形码的信息",{icon:0})
        return false;
    }
    var selData = $("#right_table").bootstrapTable("getSelections");
    for(var n = 0;n<checkBoxData[tabCurrent].length;n++){
        for(var m = 0;m<selData.length;m++){
            if(selData[m].RECID == checkBoxData[tabCurrent][n] ){
                var recId = checkBoxData[tabCurrent][n];//档号数据的业务id
                var daCode = "";//档案号
                var code =selData[m].BARCODE;//条码号
                if(selData[m].ITEM_FOLDER_NO && selData[m].ITEM_FOLDER_NO != ""  ){
                    daCode = selData[m].ITEM_FOLDER_NO;
                }else if(selData[m].FOLDER_NO && selData[m].FOLDER_NO!= ""  ){
                    daCode = selData[m].FOLDER_NO;
                }else if(selData[m].ARCHIVE_NO && selData[m].ARCHIVE_NO != ""  ){
                    daCode = selData[m].ARCHIVE_NO;
                }
                if(daCode=="" || daCode == null){
                    layer.msg("选择数据没有档号！请重新选择",{icon:0});
                    return false;
                }
                if(code=="" || code == null){
                    layer.msg("档号为:"+daCode+"的数据没有条码号！请先生成条码后在打印",{icon:0});
                    return false;
                }
                //var idTd = "<td id = '"+recId+"' style='display: none'></td>" //数据id
                var xhTd = "<td style='text-align: center'>"+(n+1)+"</td>";//序号
                var daCodeTd = "<td style='text-align: center'>"+daCode+"</td>";//档案号
                var codeTd = "<td style='text-align: center'>"+code+"</td>";  //条码码号
                var tr = "<tr>"+xhTd+daCodeTd+codeTd+"</tr >";
            }
        }
        printerHtml +=tr;
    }
    layer.open({
        type: 2,
        title:[ '条码打印预览','font-size:16px;font-weight:bold;'],
        content: '/modules/dagl/printerRead/printerRead.html',
        area: ['800px', '600px'],
        offset: '30px',
        resize: false,
        maxmin: true,
        success: function (layero, index) {
        },
        full: function (layero) {
            // 让 iframe 高度自适应
            layero.find('iframe').css('max-height', 'none');
        },
    });
}

function clearPrinterHtml() {
    printerHtml = "";
}

/**
 * 新增操作
 */
function newlyAdded() {

    nodes = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();
    var pNode = nodes[0].getParentNode();
    if(pNode == null){
        layer.msg("请选择门类或虚拟分类进行新增！",{icon:0});
        return;
    }
    //立卷单位
    var basefolderUnit = $("#basefolderUnit").val();
    layer.open({
        type: 2,
        title:[ '新增','font-size:16px;font-weight:bold;'],
        content: '/modules/dagl/bmgl/form.html?categoryCode=' + $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes()[0].categoryCode+"&fids="+getFids()+"&notAdmin="+parameter.notAdmin+"&where=danweiyuliku&basefolderUnit="+basefolderUnit,
        area: ['1100px', '660px'],
        offset: '30px',
        resize: false,
        maxmin: true,
        success: function (layero, index) {

        },
        full: function (layero) {

            // 让 iframe 高度自适应
            layero.find('iframe').css('max-height', 'none');
        },
        restore: function (layero) {

        	
        	
            // 让 iframe 最高高度为 600px
            layero.find('iframe').css('max-height', '660px');
        },
        end: function () { isRefresh=false

            // 点击当前激活的标签页来刷新当前列表内容
            $('.tab-active').trigger('click');
        }
    });
}

/**
 * 批量删除操作
 * @returns
 */
function dels(event) {
    var getSelectRows = $("#right_table").bootstrapTable('getSelections');
    var length = $("#right_table").bootstrapTable('getData').length;
    var ruleIds = "";
    if(getSelectRows.length == 0){
        layer.msg("请选择一条数据！",{icon:7});
        return;
    }else{
        var ids = "";
        for(var i=0;i<getSelectRows.length;i++){
            if("" != getSelectRows[i].FHR_ID || "已移交待汇总" == getSelectRows[i].ARCHIVE_ENTITY_STATUS){
                layer.msg("无法删除：选中数据的档案实体状态为【已移交待汇总】或含有【已封号】的档案信息！", {
                    icon: 5,time:2000
                });
                ids = "";
                break;
            }
            ids += getSelectRows[i].RECID+",";
        }
        if(!(""==ids)){
            layer.confirm(deleteTip, { icon: 3 }, function () {
                // 添加动画
                var index_loading = layer.msg('处理中，请稍等...', {
                    icon: 16,
                    shade: [0.1, '#fff'],
                    time: false
                });
                $.ajax({
                    type: "POST",
                    url: "/dagl/bmgl/dynamicDeletes",
                    data: {
                        ids: ids,
                        tName: tabCurrent,
                        all:all,
                        ranking:ranking
                    },
                    dataType: "json",
                    success: function (data) {
                        if ('1' == data.flag) {
                            if(data.total==data.ids){
                                layer.msg(data.total+"条记录删除成功！", {
                                    icon: 1,
                                    time: 2000
                                }, function (index) {
                                    checkBoxData[tabCurrent] = [];
                                    initPanel();
                                });
                            }else{
                                layer.msg(data.total+"条数据删除成功！其中有"+(data.ids-0-data.total)+"条数据无法删除：该数据的档案实体状态为【已归档】或【已移交待汇总】。", {
                                    icon: 1,
                                    time: 2000
                                }, function (index) {
                                    checkBoxData[tabCurrent] = [];
                                    initPanel();
                                });
                            }
                        } else {
                            layer.msg("无法删除：选中数据的档案实体状态为【已归档】或【已移交待汇总】。", {
                                icon: 5,time:2000
                            });
                        }
                        //关掉动画
                        layer.close(index_loading);
                    },
                    error: function (data) {
                        //关掉动画
                        layer.close(index_loading);
                        layer.msg("删除功能异常，请稍后再试。", {
                            icon: 5,time:2000
                        });
                    }
                });
            });
        }
    }
}

//    表格数据项的操作
var list = {
    editFun:function(id,archiveEntityStatus,fhrId,fhrName){
        if ("" == fhrId && ("待移交" == archiveEntityStatus || "已退回待处置" == archiveEntityStatus)) {
            // 打开修改弹框
            var resId = $('#left_ul').find('a.active').attr("id");
            MyLayer.layerOpenUrl({
                url:'/modules/dagl/bmgl/modify.html?id='+id+'&categoryCode=' + $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes()[0].categoryCode+"&fids="+getFids()+"&notAdmin="+parameter.notAdmin+"&resId="+resId,
                area: ['1100px', '660px'],
                title:"修改",
                cancel:function () {
                    TableInit.refTable('right_table');//刷新列表
                }
            })
        } else {
            if("待移交" != archiveEntityStatus && "已退回待处置" != archiveEntityStatus){
                layer.msg("无法编辑：只能修改档案实体状态为【待移交】和【已退回待处置】的档案！", {
                    icon: 5
                });
            }else if(null != fhrId && ("待移交" == archiveEntityStatus || "已退回待处置" == archiveEntityStatus)){
                layer.msg("无法编辑：该项已被【"+fhrName+"】封号！", {
                    icon: 5
                });
            }
        }
    },
    deleteFun:function (id,archiveEntityStatus,fhrId,fhrName) {
        if ("" == fhrId) {
            layer.confirm(deleteTip, { icon: 3 }, function () {
                // 添加动画
                var index_loading = layer.msg('处理中，请稍等...', {
                    icon: 16,
                    shade: [0.1, '#fff'],
                    time: false
                });
                $.ajax({
                    type: "POST",
                    url: "/dagl/bmgl/dynamicDelete",
                    data: {
                        id: id,
                        tName: tabCurrent,
                        all:all,
                        ranking:ranking
                    },
                    dataType: "json",
                    success: function (data) {
                        if ('1' == data.flag) {
                            layer.msg("删除成功！", {
                                icon: 1,
                                time: 1000
                            }, function (index) {
                                TableInit.refTable('right_table');
                                checkBoxData[tabCurrent]=[];
                            });
                        } else {
                            layer.msg("无法删除：该项档案实体状态为【已归档】或【已移交待汇总】。", {
                                icon: 5
                            });
                        }
                        //关闭动画
                        layer.close(index_loading);
                    },
                    error: function (data) {
                        //关闭动画
                        layer.close(index_loading);
                        layer.msg("删除功能异常，请稍后再试。", {
                            icon: 5
                        });
                    }
                });
            });
        } else {
            layer.msg("无法删除：该项已被【"+fhrName+"】封号！", {
                icon: 5
            });
        }
    }

}

/**
 * 替换操作
 */
function replacelyAdded() {
    if (checkBoxData[tabCurrent].length < 1) {
        layer.msg("请选择需要替换的项再点击替换按钮",{icon: 0,time:2000})
        return;
    }
    $.ajax({
        type: "POST",
        url: "/dagl/bmgl/getSelectedData",
        data: {
            tName: tabCurrent,
            fids: checkBoxData[tabCurrent].toString(),
        },
        dataType: "json",
        success: function (result) {
            var canNotDelCount = 0;
            for( var i=0;i<result.data.rows.length;i++){
                if ("已移交待汇总" == result.data.rows[i].ARCHIVE_ENTITY_STATUS ) {
                    canNotDelCount++;
                }
            }

            if(0 == canNotDelCount){
                layer.open({
                    type: 2,
                    title:[ '替换','font-size:16px;font-weight:bold;'],
                    content: '/modules/dagl/bmgl/replace.html?fids=' + checkBoxData[tabCurrent].toString() + "&tName=" + tabCurrent + "&categoryCode=" + nodes[0].categoryCode+"&ranking="+ranking+"&all="+all,
                    area: ['700px', '450px'],
                    offset: '100px',
                    resize: false,
                    maxmin: true,
                    success: function (layero, index) {
                        // layer.iframeAuto(index);
                    },
                    full: function (layero) {

                        // 让 iframe 高度自适应
                        layero.find('iframe').css('max-height', '500px');
                    },
                    restore: function (layero) {

                        // 让 iframe 最高高度为 500px
                        layero.find('iframe').css('max-height', '500px');
                    }
                });
            }else{
                layer.msg("无法替换：选中数据的档案实体状态为【已移交待汇总】。", {
                    icon: 5,time:2000
                });
            }
        },
        error: function (data) {
            layer.msg("查询待替换数据异常，请稍后再试。", {
                icon: 5,time:2000
            });
        }
    });
}
var indexx;
function max() {
    layer.full(indexx);
}

/**
 * 简单查询
 */
var indexEasyFind=0;
var isEasyFind;//切换关闭
function easyFind() {
	checkBoxData[tabCurrent]=[];
	if(indexEasyFind==1){
		layer.msg("该查询页面已经打开，请勿再次操作！",{icon: 0,time:2000})
		return;
	}
    //$('.tab-container .tab').unbind('click');
	isEasyFind=layer.open({
        type: 2,
        title:[ '简单查询','font-size:16px;font-weight:bold;'],
        content: '/modules/dagl/bmgl/easyFind.html?tName=' + tabCurrent,
        area: ['300px', '500px'],
        offset: 'lb',
        resize: false,
        maxmin: true,
        shade: 0,
        success: function (layero, index) {
            // layer.iframeAuto(index);
            indexx = index;
            indexEasyFind = 1;
        },
        full: function (layero) {

            // 让 iframe 高度自适应
           // layero.find('iframe').css('max-height', '500px');
        	layero.find("iframe").css('max-height', '800px');
        },
        restore: function (layero) {

            // 让 iframe 最高高度为 500px
        	 layero.find("iframe").css('max-height', '454px');
            layero.css({'height':'500px','width':'300px','top':'calc(100% - 500px)'});
        },
        end: function () {
        	indexEasyFind=0;
        	easyQueryP="";
            //complexQuery("");
            //$('.tab-container .tab').click(switchTab);
        },cancel: function(indexa, layero){ 
        		    layer.close(indexa)
                   // easyQuery("");
                    indexEasyFind=0;
                    
        }   
    });
}


/**
 * 档案调整
 */
function adjust() {
   // $('.tab-container .tab').unbind('click');
	if(checkBoxData[tabCurrent].length==0){
		layer.msg('至少需要选择一条数据！',{
			icon:0,time:2000
		})
		return;
	}
    layer.open({
        type: 2,
        title: ['档案调整','font-size:16px;font-weight:bold;'],
        content: '/modules/dagl/bmgl/adjust.html?tName=' + tabCurrent + '&fids=' + checkBoxData[tabCurrent].toString()+ "&categoryCode=" + nodes[0].categoryCode+"&ranking="+ranking+"&all="+all+"&parameter_dak="+parameter.dak,
        area: ['1500px', '700px'],
        //offset: '10px',
        resize: false,
        maxmin: true,
        success: function (layero, index) {
            // layer.iframeAuto(index);
            indexx = index;
        },
        full: function (layero) {

            // 让 iframe 高度自适应
            layero.find('iframe').css('max-height', 'auto');
        },
        restore: function (layero) {

            // 让 iframe 最高高度为 500px
            layero.find('iframe').css('max-height', 'auto');
        },
        end: function () { isRefresh=false
            //easyQuery("");
            //complexQuery("");
            //$('.tab-container .tab').click(switchTab);
            // 点击当前激活的标签页来刷新当前列表内容
            $('.tab-active').trigger('click');
        }
    });
}

function qTwo() {
  //  $('.tab-container .tab').unbind('click');
	if(checkBoxData[tabCurrent].toString()==""){
		layer.msg("至少需要选择一条数据！",{icon: 0,time:2000})
		return;
	}
    layer.open({
        type: 2,
        content: '/modules/dagl/daly/qtwo/Q2EditForm.html?tName=' + tabCurrent + '&ids=' + checkBoxData[tabCurrent].toString(),
        area: ['1200px', '600px'],
        offset: '20px',
        title: ['q2变更', 'font-size:16px;font-weight:bold;'],
        resize: false,
        maxmin: true,
        success: function (layero, index) {
            // layer.iframeAuto(index);
            indexx = index;
        },
        full: function (layero) {

            // 让 iframe 高度自适应
            layero.find('iframe').css('max-height', '500px');
        },
        restore: function (layero) {

            // 让 iframe 最高高度为 500px
            layero.find('iframe').css('max-height', '500px');
        },
        end: function () { isRefresh=false
            //easyQuery("");
            //complexQuery("");
            //$('.tab-container .tab').click(switchTab);
        }
    });
}
//全选
function alll(){

    // 当前激活的标签页
    if($("#all").is(':checked')){
        $("#tbody").find("tr").each(function(){
            $(this).css('background-color', '#ccc');
            var obj1 =$(this).find("td").eq(0).find("input");
            if(obj1.prop("checked")==false){
                obj1.prop("checked",true)
            }
            if(checkBoxData[tabCurrent].indexOf(obj1.val())<0) {
                checkBoxData[tabCurrent].push(obj1.val());
            }
            return true;
        })
        $("#alll").prop("checked",false)//清除反选的选中状态
    }else{
        $("#tbody").find("tr").each(function(){
            $(this).css('background-color', '');
            $(this).find("td").eq(0).find("input").prop("checked",false)
            checkBoxData[tabCurrent]=[];
        })
    }
}

//反选
function allll(){

    var dataNum = $("#tbody").find("tr").length;
    var checkedNum = 0;
    $("#tbody").find("tr").each(function(){

        var obj1 =$(this).find("td").eq(0).find("input");
        if(obj1.prop("checked")==false){//未勾选的勾选
            $(this).css('background-color', '#ccc');
            obj1.prop("checked",true);
            //忘数组中添加数据
            if(checkBoxData[tabCurrent].indexOf(obj1.val())<0) {
                checkBoxData[tabCurrent].push(obj1.val());
            }
            checkedNum++;
        }else{//勾选的改为未勾选
            $(this).css('background-color', '');
            obj1.prop("checked",false);
            //去掉数组中的数据
            var index = checkBoxData[tabCurrent].indexOf(obj1.val());
            if(index != -1) {
                checkBoxData[tabCurrent].splice(index, 1);
            }
            $("#all").prop("checked",false);//只要有一个未勾选，清除全选的选中状态
        }
        return true;
    });
    //如果所有的数据被勾选，全选按钮勾选
    if(dataNum == checkedNum){
        $("#all").prop("checked",true);
    }
}
/**
 * 加载标签页表格
 */
// function loadPanelTable() {

//     $('.boostrap-table').before($(''
//         + '<table id="t' + $('.tab-panel-container .tab-panel').not('.hide').index() + '"></table>'
//     ));
//     $('.boostrap-table').remove();

//     var data = [];
//     for (var i = 0; i < 989; ++i) {
//         data.push({
//             id: i,
//             name: String(Math.ceil(Math.random() * i)),
//             price: '$' + (Math.ceil(Math.random() * i))
//         });
//     }

//     var index = $('.tab-panel-container .tab-panel').not('.hide').index();
//     $('#t' + (index + 1)).bootstrapTable({
//         striped: true, // 条纹样式
//         undefinedText: "", // 当数据为 undefined 时显示的字符
//         sortClass: undefined, // 被排序的td元素的类名
//         height: 500, // 表格高度，可用于固定表头
//         pagination: true, // 是否分页，默认为否
//         paginationLoop: false, // 分页无限循环，默认为是
//         onlyInfoPagination: false, // 设置为 true 只显示总数据数，而不显示分页按钮。需要设置 pagination='true'
//         sidePagination: 'client', // 设置在哪里进行分页，可选值为 'client' 或者 'server'。设置 'server'时，必须设置服务器数据地址（url）或者重写ajax方法
//         pageNumber: 1, // 如果设置了分页，首页页码
//         pageSize: 30, // 如果设置了分页，页面数据条数
//         pageList: [30, 50, 100, 'all'], // 如果设置了分页，设置可供选择的页面数据条数。设置为 All 或者 Unlimited，则显示所有记录
//         columns: [
//             {
//                 field: 'id',
//                 title: 'Item ID'
//             },
//             {
//                 field: 'name',
//                 title: 'Item Number'
//             },
//             {
//                 field: 'price',
//                 title: 'Item Price'
//             }],
//         data: data
//     });
// }

function complexQuery(pm) {
    complexQueryP = pm;
    initPanel();
}

/**
 * 组合查询
 */
var complexFinds=0;
function complexFind() {
	checkBoxData[tabCurrent]=[];
	if(complexFinds==1){
		layer.msg("该查询窗口已经打开，请勿重复操作！",{icon:0})
		return;
	}
   // $('.tab-container .tab').unbind('click');
    layer.open({
        type: 2,
        title: ['组合查询','font-size:16px;font-weight:bold;'],
        content: '/modules/dagl/bmgl/complexFind.html?tName=' + tabCurrent,
        area: ['700px', '500px'],
        offset: 'lb',
        resize: false,
        maxmin: true,
        shade: 0,
        success: function (layero, index) {
            // layer.iframeAuto(index);
            indexx = index;
            complexFinds=1;
        },
        full: function (layero) {

            // 让 iframe 高度自适应
            // layero.find('iframe').css('max-height', '500px');
        },
        restore: function (layero) {

            // 让 iframe 最高高度为 500px
            layero.css({'height':'500px','width':'700px','top':'calc(100% - 500px)'});
        },
        end: function () { 
            //easyQuery("");
            
            complexFinds=0;
            //$('.tab-container .tab').click(switchTab);
        },cancel: function(indexa, layero){ 
		    layer.close(indexa)
            //complexQuery("");
		    complexFinds=0;
        }   
    });
}

/**
 * 移交操作
 * @returns
 */
function submitData(event) {

    // 阻止事件冒泡
    window.event ? window.event.cancelBubble = true : event.stopPropagation();

    var fids = checkBoxData[tabCurrent].toString();//获取选中行的id
    if("" == fids.length){
        layer.msg("至少需要选择一条数据！",{icon: 0,time:2000})
    }else {
        layer.confirm("确定要移交选中的数据吗？", {icon: 3}, function (e) {
            layer.close(e);
            // 添加动画
            var index_loading = layer.msg('处理中，请稍等...', {
                icon: 16,
                shade: [0.1, '#fff'],
                time: false
            });
            
            $.ajax({
                type: "POST",
                url: "/dagl/bmgl/recordSubmit",
                data: {fids: fids, tName: tabCurrent},
                dataType: "json",
                success: function (data) {
                    if ('1' == data.flag) {
                        //刷新当前标签页
                    	initPanel();

                        var msg = "";
                        var successList = data.data[0].successList;
                        if (successList.length > 0) {
                            msg += "<font style='color: green'>";
                            for (var i = 0; i < successList.length; i++) {
                                msg += successList[i].MAINTITLE + "<br>";
                            }
                            msg += "移交成功!</font><br/>";
                        }


                        var alreadySubmmitList = data.data[0].alreadySubmmitList;
                        if (alreadySubmmitList.length > 0) {
                            msg += "<font style='color: red'>";
                            for (var i = 0; i < alreadySubmmitList.length; i++) {
                                msg += alreadySubmmitList[i].MAINTITLE + "<br>";
                            }
                            msg += "已为【已移交待汇总】，移交失败!</font><br/>";
                        }

                        var P_NotSubmmitList = data.data[0].P_NotSubmmitList;
                        if (P_NotSubmmitList.length > 0) {
                            msg += "<font style='color: red'>";
                            for (var i = 0; i < P_NotSubmmitList.length; i++) {
                                msg += P_NotSubmmitList[i].MAINTITLE + "<br>";
                            }
                            msg += "上层档案尚未移交（请先移交上层档案），移交失败！</font><br/>";
                        }

                        var NotAssociatedList = data.data[0].NotAssociatedList;
                        if (NotAssociatedList.length > 0) {
                            msg += "<font style='color: red'>";
                            for (var i = 0; i < NotAssociatedList.length; i++) {
                                msg += NotAssociatedList[i].MAINTITLE + "<br>";
                            }
                            msg += "未查询到上层档案，不在卷内，移交失败!</font><br/>";
                        }
                        //关闭处理中
                        layer.close(index_loading);
                        layer.alert(msg, {title: '移交结果'});

                    } else {
                    	//关闭处理中
                        layer.close(index_loading);
                        layer.msg("移交失败，请刷新页面再次操作。", {
                            icon: 5
                        });
                    }
                },
                error: function (data) {
                	//关闭处理中
                	layer.close(index_loading);
                	layer.msg("移交功能异常，请稍后再试。", {
                        icon: 5
                    });
                }
            });
        });
    }
}
/*
 * 鼠标点击效果
 */
function dianjixiaoguo(){
	$('.tab-container .tab').click(switchTab);
}

/**
 * 汇总操作
 * @returns
 */
function gatherData(event) {

    // 阻止事件冒泡
    window.event ? window.event.cancelBubble = true : event.stopPropagation();
     all = $('.tab', window.document).length;
     ranking = $('.tab-active', window.document).index() + 1;
    var selectIds = checkBoxData[tabCurrent].toString();//获取选中行的id
    var categoryCode= nodes[0].categoryCode;
    if(selectIds==""){
    	layer.msg("请先选择要汇总的数据！",{icon: 0,time:2000})
		return;
    }else{
		$.ajax({
			type: "POST",
			url: "/dagl/bmgl/recodeGather",
			data: {"fids": selectIds,"tName": tabCurrent,"categoryCode":categoryCode,"total":all,"ranking":ranking},
			dataType: "json",
			success: function (data) {
				 if ('1' == data.flag) {
					 layer.msg("汇总成功！", {
                        icon: 1
                    });
					 initPanel();
				 }else{
					 var msg = data.msg;
					 layer.msg(msg, {
	                        icon: 2
	                    });
				 }
			},
			error: function (data) {
			}
		});
    }
}
function orderBy(){
	layer.open({
        type: 2,
        content: '/modules/dagl/bmgl/orderBy.html?tName='+tabCurrent,
        area: ['800px', '550px'],
        offset: '20px',
        title: ['排序', 'font-size:16px;font-weight:bold;'],
        resize: false,
        maxmin: false,
        success: function (layero, index) {
            // layer.iframeAuto(index);
        },
        end: function () { 
            //easyQuery("");
            //complexQuery("");
            //$('.tab-container .tab').click(switchTab);
        }
    });
}

//每页数量的变化
function gradeChange(){
    pageSize =$("#numbers").val();
    //初始化勾选的数据
    checkBoxData[tabCurrent]=[];
    initPanel();
}

//判断是否含有中文
function isChina(str){

    if(/.*[\u4e00-\u9fa5]+.*$/.test(str)) // \u 表示unicode
    {
        return true;
    }
    return false;
}

/**
 * 打开门类第一层表数据（带分页）
 * 王磊 20190423
 * @param event
 */
function adjustFirstLevel(event){

    // 阻止事件冒泡
    window.event ? window.event.cancelBubble = true : event.stopPropagation();
    all = $('.tab', window.document).length;
    ranking = $('.tab-active', window.document).index() + 1;
    var selectIds = checkBoxData[tabCurrent].toString();//获取选中行的id
    var categoryCode= nodes[0].categoryCode;
    if(checkBoxData[tabCurrent].length != 1){
        layer.alert("请选择一条数据确认可被调整的档案范围！",{icon: 0});
        return;
    }else{
        MyLayer.layerOpenUrl({
            url:'/modules/dagl/bmgl/firstLevelDataList.html?tableName='+tabCurrent+'&all='+all+'&ranking='+ranking+'&selectIds='+selectIds+'&categoryCode='+categoryCode,
            title:"门类第一层档案数据",
            width:"90%",
            height:"90%"
        })
    }

}

//加载录入人立卷单位的下拉框
function basefolderUnitInit() {
    $("#basefolderUnit").html("");
    $.ajax({
        url: "/dagl/xtpz/category/LJDWmark?code="+$.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes()[0].categoryCode+"&userId="+getcookie("userid"),
        type: 'get',
        dataType: 'json',
        asyn:true,
        success: function (data) {
            if(data.flag==1){
                for(var i in data.data){
                    $("#basefolderUnit").append("<option value='"+i+"'>"+data.data[i]+"</option> ");
                }
                initPage();
                //默认初始化第一个立卷单位的数据
            }
        }
    })
}

//切换立卷单位
function basefolderUnitChange(a){
    initPanel();
}

/**
 * @Author 王富康
 * @Description //TODO 刷新按钮方法
 * @Date 2019/10/23 15:43
 * @Param
 * @return
 **/
function refresh(){
    complexQueryP = ""//组合查询参数
    easyQueryP = "";
    initPanel();
}

/**
 * @Author 王富康
 * @Description //TODO 计算字符串的长度
 * @Date 2019/10/28 10:30
 * @Param
 * @return
 **/
function getTextWidth(str) {
    var width = 0;
    var html = document.createElement('span');
    html.innerText = str;
    html.className = 'getTextWidth';
    document.querySelector('body').appendChild(html);
    width = document.querySelector('.getTextWidth').offsetWidth;
    if(isIE()||isIE11()){
        document.querySelector('.getTextWidth').removeNode(true);
    }else{
        document.querySelector('.getTextWidth').remove();
    }
    return width;
}

function isIE(){
    if(!!window.ActiveXObject || "ActiveXObject" in window){
        return true;
    }else{
        return false;
    }
}
function isIE11(){
    if((/Trident\/7\./).test(navigator.userAgent)){
        return true;
    }else{
        return false;
    }
}