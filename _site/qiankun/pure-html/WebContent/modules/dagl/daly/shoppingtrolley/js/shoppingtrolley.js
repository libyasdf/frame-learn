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
var loadedDataTimer=null;
var RollTop=0;
var pageSize = 20;
var fondsNo = Dictionary.getNameAndCode({mark: "fonds_no", type: "1"});

// 页面加载完成
$(function () {
    var url = $("#left_ul").find("a.active").attr("url");
    layer.closeAll();
    parameter = new GetParameter(url);
    // 初始化左侧树
    typeTree.init({isQ2:false,isAdmin:1});
    // 初始化页面
    initPage();
    //nodes = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();
    //当前用户的角色编号
    rolesNo = getcookie("rolesNo");
    if(rolesNo.indexOf("D702")>-1){
        $("#rolesNo").show();
    }

});


/**
 * 初始化页面
 */
function initPage() {

    // 删除 layer.js 额外引用的 link 标签
    $('#layuicss-layer').remove();

    try{
        nodes = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();
    }catch (e) {
        layer.close(index);
        layer.alert("尚未授权立卷单位管理员，请联系档案馆管理员！",{icon:5});
    }




    // 解除绑定方法
    $('.dropdown-menu .dropdown-item').unbind();

    // 初始化标签
    initTabs();
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
    var nodeCategoryCode = "";
    try{
        nodeCategoryCode = nodes[0].categoryCode;
    }catch (e) {
        layer.close(index);
        layer.alert("暂无门类信息，请联系档案馆管理员！",{icon:5});
        return;
    }
    $.ajax({
        url: LableUrl,
        data: {
            tName: nodeCategoryCode
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
    if(indexEasyFind==1&&isRefresh){
        layer.msg("请关闭查询页面后，再切换/点击便签",{icon: 0,time:2000})
        return;
    }
    isRefresh=true;
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
        if(ranking==all-0-1){
            $("#dangantiaozheng").show();
        }else{
            $("#dangantiaozheng").hide();
        }
    }
}
var easyIndex;
function easyQuery(pm) {
    easyQueryP = pm;
    initPanel();
}

/**
 * 初始化面板
 */
function initPanel() {
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
    if(rolesNo.indexOf("D702")>-1){
        $("#huizong").show();
    }else{
        $("#huizong").hide();
    }
    if(all>=2){
        if(ranking==all-0-1){
            $("#dangantiaozheng").show();
        }else{
            $("#dangantiaozheng").hide();
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
                field: 'BORROW_STATUS'
                ,title: '封号状态'
                , width: '100px'
                , align:"center"
                ,formatter:function(value,row,index){

                    if(null == value || "0" == value || "" == value){
                        return "<span style='color: green;'>未借出</span>";
                    }else if("1" == value){
                        return "<span style='color: blue;'>部分借出</span>";
                    }else if("2" == value){
                        return "<span style='color: red;'>已借出</span>";
                    }
                }
            });//借阅状态

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
                    params.danweiku='dak',
                    params.all=all,
                    params.parameters=easyQueryP,
                    params.complexParam=complexQueryP
                    return params;
                },
                readOnlyFn: function () {
                    // 给标签绑定事件
                    $('.tab-container .tab').unbind('click');
                    $('.tab-container .tab').click(switchTab);

                    $('tr.readOnly').find('td:not(:first)').attr("title", "点击查看详情");
                    $('tr.readOnly').find('td:not(:first)').unbind('click').bind('click', function (e) {
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

function clearPrinterHtml() {
    printerHtml = "";
}

/**
 * 打开只读页
 */
function readOnly() {

    var $this = $(this);

    // 添加修改标识
    $this.addClass('readonly');

    // 打开修改弹框
    layer.open({
        type: 2,
        title:[ '预览','font-size:16px;font-weight:bold;'],
        content: '/modules/dagl/bmgl/readonly.html',
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
        end: function () {

            // 去掉 tr 的 readonly 类
            $this.removeClass('readonly');

            // 点击当前激活的标签页来刷新当前列表内容
            // $('.tab-active').trigger('click');
        }
    });
}

var indexx;
function max() {
    layer.full(indexx);
}

/**
 * 加载标签页表格
 */

function complexQuery(pm) {
    complexQueryP = pm;
    initPanel();
}

/*
 * 鼠标点击效果
 */
function dianjixiaoguo(){
    $('.tab-container .tab').click(switchTab);
}


//每页数量的变化
function gradeChange(){
    pageSize =$("#numbers").val();
    checkBoxData[tabCurrent]=[];
    initPanel();
}

/**
 * 收集
 * @returns
 */
function addShoppingTrolley(event) {
    if(checkBoxData[tabCurrent].toString()==""){
        layer.msg("至少需要选择一条数据！",{icon: 0,time:2000})
        return;
    }
    layer.confirm("确定收集所选数据？", { icon: 3 }, function () {

        var ids = checkBoxData[tabCurrent].toString();
        var tName = tabCurrent;
        /*var all = all;
        var ranking = ranking;*/
        var aa ="";
        var categoryCode = nodes[0].categoryCode;
        $.ajax({
            type: "POST",
            url: "dagl/daly/shoppingtrolley/saveShoppingTrolley",
            data: {
                ids: checkBoxData[tabCurrent].toString(),
                tName: tabCurrent,
                all:all,
                ranking:ranking,
                categoryCode : nodes[0].categoryCode,
                categoryName : nodes[0].name

            },
            dataType: "json",
            success: function (data) {
                if ('1' == data.flag) {
                    var msg = "";
                    if(data.data[0].successList.length>0){
                        //新增
                        msg+=data.data[0].successList.length+"条记录收集成功！<br/>"
                    }
                    if(data.data[0].existErrorList.length>0){
                        //已存在的
                        for(var i=0;i<data.data[0].existErrorList.length;i++){
                            msg += "【"+data.data[0].existErrorList[i].mainTitle+"】";
                        }
                        msg += "已存在，收集失败！<br/>"
                    }
                    if(data.data[0].statusErrorList.length>0){
                        //状态为空、已借出、部分借出的
                        for(var i=0;i<data.data[0].statusErrorList.length;i++){
                            msg += "【"+data.data[0].statusErrorList[i].mainTitle+"】";
                        }
                        msg += "已借出或已有部分借出，收集失败！<br/>"
                    }
                    layer.alert(msg);

                } else {
                    layer.msg("收集失败，请刷新页面再次操作", {
                        icon: 5
                    });
                }
            },
            error: function (data) {
            }
        });
    });
}

/**
 * @Author 王富康
 * @Description //TODO 我的收集列表（管理员）
 * @Date 2019/2/26 10:45
 **/
function myShoppingTrolley(){

    /*$('#btnShowAdd1').on('click',function(){
        addOrEdit = "add";
        $('#myModal1').modal('show');
    })*/
    layer.open({
        type: 2,
        content: '/modules/dagl/daly/shoppingtrolley/shoppingTrolleyList.html',
        //content: '/modules/dagl/daly/borrow/apply/shoppingTrolleyList.html?type=borrow',//import/borrow
        area: ['1200px', '650px'],
        offset: '20px',
        title: ['收集列表', 'font-size:16px;font-weight:bold;'],
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

/**
 * 简单查询
 */
var indexEasyFind=0;
function easyFind() {
    checkBoxData[tabCurrent]=[];
    if(indexEasyFind==1){
        layer.msg("该查询页面已经打开，请勿再次操作！",{icon: 0,time:2000})
        return;
    }
    //$('.tab-container .tab').unbind('click');
    layer.open({
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
            //easyQuery("");
            indexEasyFind=0;

        }
    });
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

//判断是否含有中文
function isChina(str){

    if(/.*[\u4e00-\u9fa5]+.*$/.test(str)) // \u 表示unicode
    {
        return true;
    }
    return false;
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