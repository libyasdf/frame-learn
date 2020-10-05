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

    // 添加载动画
    index = layer.msg('加载中，请稍等...', {
        icon: 16,
        shade: [0.1, '#fff'],
        time: false
    });

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

    // 事件绑定可以放在下面
    $('.dropdown-menu .dropdown-item:contains(删除)').click(dels);
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
            console.log("加载标签页的数据：\n", data);

            // 加载标签
            for (var i = 0; i < data.length; ++i) {
                $('.select-container').before($(''
                    + '<div class="tab" data-TABLE_NAME="' + data[i].TABLE_NAME + '">'
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
            console.log("加载标签页失败，错误信息：\n", err);
        }
    });

}

/**
 * 切换标签页
 */
var index2;
function switchTab() {
//	if($('.tab-container .tab').data("events")['click']){
//		layer.msg("请关闭查询页面后，再切换/点击便签",{icon: 0,time:2000})
//    	return;
//	}
    if(indexEasyFind==1&&isRefresh){
        layer.msg("请关闭查询页面后，再切换/点击便签",{icon: 0,time:2000})
        return;
    }
   /* if((complexFinds==1&&isRefresh)){
        layer.msg("请关闭查询页面后，再切换/点击便签",{icon: 0,time:2000})
        return;
    }*/
    isRefresh=true;
    // 解绑 click 事件
    $('.tab-container .tab').unbind('click');

    // 添加载动画
    index2 = layer.msg('加载中，请稍等...', {
        icon: 16,
        shade: [0.1, '#fff'],
        time: false
    });

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

    console.log("点击标签页后的tabCurrent", tabCurrent);
    console.log("点击标签页后的checkboxData", checkBoxData);

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
    // 添加载动画
    easyIndex = layer.msg('加载中，请稍等...', {
        icon: 16,
        shade: [0.1, '#fff'],
        time: false
    });
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
    $('.tab-panel-container').append($('<div class="tab-panel">' +
        '                               </div>' +
        '                               <div class="total" style="margin-top:-27px;margin-right:20px;font-size:17px;text-align: right;margin-bottom:10px">' +
        '                                   <button onclick="addShoppingTrolley()"  type="button" class="list_table_btn2" style="margin-right:1%;font-size:15px">' +
        '                                       <span class="glyphicon glyphicon-plus-sign" aria-hidden="true" ></span> 收集 ' +
        '                                   </button>' +
        '                                   <button onclick="myShoppingTrolley()"  type="button" class="list_table_btn2" style="margin-right:1%;font-size:15px">' +
        '                                       <span class="glyphicon glyphicon-tasks" aria-hidden="true"></span> 查看收集 ' +
        '                                   </button>' +
        '                                   <button onclick="easyFind()"  type="button" class="list_table_btn2" style="margin-right:35%;font-size:15px">' +
        '                                       <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查询 ' +
        '                                   </button>' +
        '                                   <font >当前总条数为</font><span style="font-size:17px" id="total"></span> <font>条，</font>' +
        '                                   <font >加载条数<span class="glyphicon glyphicon-exclamation-sign"  data-toggle="popover" data-placement="top"data-content="当鼠标滚动到列表底部时，每次滚动动态加载的条数"></span></font>' +
        '                                   <select class="form-control" id="numbers" style="width: 75px;float: right;" onchange="gradeChange()">' +
        '                                       <option value="20">20</option>' +
        '                                       <option value="50" >50</option>' +
        '                                       <option value="100" >100</option>' +
        '                                   </select>' +
        '                               </div>'));

    document.getElementById("numbers").value = pageSize;
    // 添加表格
    $('.tab-panel').append($(''
        + '<table id="table" class="table table-bordered table-striped table-hover table-condensed table-responsive">'
        + '<thead>'
        + '<tr></tr>'
        + '</thead>'
        + '<tbody id="tbody"></tbody>'
        + '</table>')
    );
    $('[data-toggle="tooltip"]').tooltip()
    $('[data-toggle="popover"]').popover({
        trigger:"hover"
    })
    // 加载表头
    initTableHead();

    /**
     * 滚轮到底加载
     * @returns
     */
    $('#tbody').bind('scroll', function (ev) {
        if ($('#tbody').scrollTop() != ud) {
            ud = $('#tbody').scrollTop();
            if ($(this).scrollTop() + $(this).innerHeight() >= $(this)[0].scrollHeight) {
                // 增加定时器 防止滚动时多次加载数据
                clearTimeout(loadedDataTimer)
                loadedDataTimer = setTimeout(function(){
                    num++;
                    initTableBody(num)
                }, 500);
            }
        }
        if ($('#tbody').scrollLeft() != lr) {

        }

    });

    // 滚动结束监听事件 修改滚动时表格thead和tbody不同步滚动的问题
    $.fn.scrollEnd = function(callback, timeout) {
        $(this).scroll(function(){
            var $this = $(this);
            if ($this.data('scrollTimeout')) {
                clearTimeout($this.data('scrollTimeout'));
            }
            $this.data('scrollTimeout', setTimeout(callback,timeout));
        });
    };
    // 绑定滚动结束监听事件
    $('#tbody').scrollEnd(function(){
        /************* 横向滚动时  **************/
        // 设置table滚动长度
        $('#table').scrollLeft($('#tbody').scrollLeft())

        if ($('#tbody').scrollLeft() != lr) {  // 当横向滚动时
            if(($('#table').width()+$('#table').scrollLeft()) == $('#table thead').width()){  // 判断是否横向滚动到最后
                // 设置thead的宽度
                // console.log($('#table thead').width(),$('#tbody').scrollLeft(),$('#table').scrollLeft())
                var scrollbarWidth = $('#table #tbody')[0].offsetWidth - $('#table #tbody')[0].clientWidth;
                var setTheadWidth = $('#table thead').width()+scrollbarWidth;
                $('#table thead').width(setTheadWidth);
            }
        }

    },1)
}

/**
 * 加载表头
 */
function initTableHead() {
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
            console.log("加载表头的数据\n", data);

            // 给全局变量赋值
            tHeadData = data;
            // var marks=[];
            // 加载表头
            for (var i = 0; i < data.length; ++i) {
                if(data[i].COLUMN_INPUT_TYPE=="S"){
                    if(data[i].COLUMN_NAME!="basefolder_unit"){
                        select_no[data[i].COLUMN_NAME]=data[i].COLUMN_SELECT_NO;
                        //marks.push(data[i].COLUMN_SELECT_NO);
                    }
                }
                if (data[i].COLUMN_LIST_ISSHOW === "T") {
                    $('#table thead tr:first-child').append($('<th style="min-width: '+data[i].LIST_COLUMN_WIDTH+'px;">' + data[i].COLUMN_CHN_NAME + '</th>'));
                }
            }
            //封号状态
            $('#table thead tr:first-child').prepend($('<th style="text-align: center; min-width: 100px;">借阅状态</th>'));
            // 添加复选框和操作
            $('#table thead tr:first-child').prepend($('<th style="text-align: center; min-width: 100px;"><input type="checkbox" id="all" >全选 <input type="checkbox" id="alll" >反选</th>'));
            //  $('#table thead tr:first-child').append($('<th>操作</th>'));
            $("#all").click(alll);
            $("#table #alll").click(allll);
            //findAllZiDian(marks.toString())//查数据字典的内容
            // 加载表格内容
            initTableBody(1);

        },
        error: function (err) {
            layer.msg('加载失败，请刷新页面',{icon: 2,time:1000});
            console.log("加载表头失败，错误信息：\n", err);
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
            console.log("数据字典："+dataZiDian)
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
/**
 * 加载表格内容
 */
function initTableBody(pageNumber) {
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
    //var getfids = getFids();
    $.ajax({
        url: '/dagl/bmgl/dynamicList',
        data: {
            tName: tabCurrent, // 表名
            fRecIds: getfids, // 系统唯一关键字
            conditions: getTree(), // 树的节点条件
            pageNumber: pageNumber, // 页码
            pageSize: pageSize, // 每页显示数据条数
            parameters: easyQueryP,
            complexParam:complexQueryP,
            danweihao:$("#danweihao").val(),
            danweiku:'zhiyouwonengkan',
            all:all
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            // 解析数据
            var data = data;
            tableListData = data;
            console.log("加载列表内容的数据", data);
            if(data.length<=1) {
                // 显示滚动条
                $('.tab-panel-container .tab-panel table').css('overflow-x','auto');
                $('.tab-panel-container .tab-panel table tbody').css('overflow-x','hidden');
            }
            // 计算是否滚动到底部
            var tbodyScrollTop = $('#table #tbody').scrollTop();
            if(pageNumber==1){    // table初始化的时候
                RollTop = 0
            }
            // 判断是否纵向滚动到最底部
            if(data.length==1 && data.length-1 <= 0){
                if (pageNumber == num && RollTop<=tbodyScrollTop && num!=1) {
                    RollTop = tbodyScrollTop;
                    layer.msg("已经滚动到最后了哦",{icon: 0,time:2000});
                    return;
                }
            }

            $("#all").prop("checked",false);

            // 加载表格内容
            for (var i = 0; i < data.length; ++i) {
                if( data.length==1){
                    $("#sqlStr").val(data[i].sqlStr);
                    break;
                }
                if(Object.keys(data[i])[0]=="total"||Object.keys(data[i])[0]=="sqlStr"){
                    $("#total").html(data[i].total);
                    $("#sqlStr").val(data[i].sqlStr);
                    continue;
                }
                // 渲染表格内容
                var $tr = $('<tr></tr>')
                $('.tab-panel table tbody').append($tr);
                if(null == data[i].BORROW_STATUS || "0" == data[i].BORROW_STATUS){
                    $tr.append($('<td style="text-align: center;min-width: 100px;"><span style="color: green;"> 未借出 </span></td>'));
                }else if("1" == data[i].BORROW_STATUS){
                    $tr.append($('<td style="text-align: center;min-width: 100px;"><span style="color: blue;"> 部分借出 </span></td>'));
                }else if("2" == data[i].BORROW_STATUS){
                    $tr.append($('<td style="text-align: center;min-width: 100px;"><span style="color: red;"> 已借出 </span></td>'));
                }

                for (var j = 0; j < tHeadData.length; ++j) {
                    if (tHeadData[j].COLUMN_LIST_ISSHOW === 'T') {
                        var keyStr = tHeadData[j].COLUMN_NAME.toUpperCase('utf8');
                        var valueStr= data[i][keyStr] != null ? data[i][keyStr]+"": "";
                        var keyStrMin=keyStr.toLowerCase();
                        var valueStr2 = valueStr;
                        /*if("fonds_no" == keyStrMin && "" != valueStr){
                            //根据code取数据字典的值
                            valueStr = fondsNo[valueStr]==undefined?"":fondsNo[valueStr];
                            valueStr2 = valueStr;
                        }*/
// 以下部分用于下拉菜单中 用code查name的内容
//                        if(JSON.stringify(select_no).indexOf(keyStrMin)>-1){
//                        	if(valueStr!=null&&valueStr!=''){
//	                        	var mark=select_no[keyStrMin];
//	                        	//var map=Dictionary.getNameAndCode({mark:mark,type:"1"});
//	                        	/*console.log("key"+keyStrMin);
//	                        	console.log("marl"+mark);
//	                        	console.log("map"+JSON.stringify(map));
//	                        	console.log("value"+valueStr);*/
//	                        	//console.log(dataZiDian)
//	                        	//console.log(dataZiDian[mark])
//	                        	valueStr=dataZiDian[mark][valueStr]
//
//	                        	if(typeof(valueStr)=="undefinde"||valueStr==null||valueStr==""){
//	                        		valueStr="";
//	                        	}
//                        	}else{
//                        		valueStr=" ";
//                        	}
//                        }
//                        if(keyStrMin=="basefolder_unit"){
//                        	//valueStr=data[i].CRE_CHUSHI_NAME;
//                        	if(valueStr==null||valueStr==''){
//                        		valueStr='';
//                        	}
//                        }
                        if(null != tHeadData[j].LIST_COLUMN_WIDTH &&(valueStr.length*14+12) > tHeadData[j].LIST_COLUMN_WIDTH){
                            //如果设置了列表宽度，超出的数据省略号展示
                            var dataWidth = 0;
                            var subWidth = 0;
                            if(isChina(valueStr2)){
                                //含有中文
                                var dataWidth = (tHeadData[j].LIST_COLUMN_WIDTH-12)/14;
                                subWidth = 1;
                            }else{
                                dataWidth= (tHeadData[j].LIST_COLUMN_WIDTH-12)/7;
                                subWidth = 3;
                            }
                            $tr.append($('<td style="min-width: '+tHeadData[j].LIST_COLUMN_WIDTH+'px"> <span  title='+valueStr2+'>'+ (valueStr.length >= dataWidth?valueStr.substring(0,dataWidth-subWidth)+'...':valueStr) + '</span></td>'));
                        }else if(null == tHeadData[j].LIST_COLUMN_WIDTH && (valueStr.length*14+12) > 200){
                            //如果没有设置宽度，默认宽度为200，超过的则省略号展示
                            var dataWidth = 0;
                            var subWidth = 0;
                            if(isChina(valueStr2)){
                                //含有中文
                                var dataWidth = (200-12)/14;
                                subWidth = 1;
                            }else{
                                dataWidth= (200-12)/7;
                                subWidth = 3;
                            }
                            $tr.append($('<td style="min-width: '+tHeadData[j].LIST_COLUMN_WIDTH+'px"> <span  title='+valueStr2+'>'+ (valueStr.length >= dataWidth?valueStr.substring(0,dataWidth-subWidth)+'...':valueStr) + '</span></td>'));
                        }else{
                            $tr.append($('<td style="min-width: '+tHeadData[j].LIST_COLUMN_WIDTH+'px"><span  title='+valueStr2+'> '+ (valueStr.length >= 50?valueStr.substring(0,50)+'...':valueStr) + '</span></td>'));
                        }
                    }
                }

                // 添加复选框和操作按钮
                var ed = "";JSON.stringify(checkBoxData).indexOf(data[i].RECID) > -1 ? "checked" : "";
                console.log(ed);
                $tr.prepend($('<td class="text-center"><input type="checkbox" value="' + data[i].RECID + '" ' + ed + '></td>'));
//                $tr.append($(''
//                    + '<td>'
//                    + '<i class="glyphicon glyphicon-edit opreation"></i>'
//                    + '<i class="glyphicon glyphicon-trash opreation"></i>'
//                    + '</td>')
//                );

                // 给复选框绑定方法
                $tr.find('td input[type="checkbox"]').change(changeCheckBoxState).click(stopBubble);

                // 给操作按钮绑定方法
                $tr.find('.glyphicon-edit').click(modify);

                // 给操作按钮绑定方法
                $tr.find('.glyphicon-trash').click(del);

                // 打开只读页
                $tr.click(readOnly).find("td:first,td:last").click(stopBubble);
                $("#tbody").find("tr").find("td:not(:first,:last)").css("cursor","pointer");
            }

            // 表格无数据时插入Dom显示提示信息
            showNoData();

            // 给表格绑定滚动条方法让表头列表一起滚动
            $('.tab-panel-container .tab-panel table').scroll(function () {
                var scrollDisplacement = $(this).scrollLeft();
                $(this).find('thead').scrollLeft(scrollDisplacement);
                $(this).find('tbody').scrollLeft(scrollDisplacement).css('left', scrollDisplacement + 'px');
            });
            // 给标签绑定事件
            $('.tab-container .tab').unbind('click');
            $('.tab-container .tab').click(switchTab);
            //如果简答查询页面被打开 禁止切换标签 页
//            if(indexEasyFind==1){
//            	$('.tab-container .tab').unbind('click');
//           }

            // 关闭加载动画
            layer.close(index);
            layer.close(index2);
            layer.close(treeindex);
            layer.close(danweiindex);
            layer.close(easyIndex);

            ud = $("#tbody").scrollTop();
            lr = $("#tbody").scrollLeft();
        },
        error: function (err) {
            // 表格无数据时插入Dom显示提示信息
            showNoData();

            layer.close(index);
            layer.close(index2);
            layer.close(treeindex);
            layer.close(danweiindex);
            layer.close(easyIndex);
            layer.msg("页面出现异常，请尝试刷新页面，再次操作",{icon: 2,time:1000})
        }
    });
}
// 表格无数据时显示提示无数据信息
function showNoData(){
    var dataLength = $('.tab-panel table #tbody').children().length
    if(dataLength<=0)
    {
        // 渲染表格内容
        var $tr = $('<p class="noData" id="noData" >没有找到匹配的记录</p>')
        $('.tab-panel table #tbody').append($tr);
        $("#total").html("0");
    }else{
        //清空"没有找到匹配的记录"的tr
        $("#noData").remove();
    }
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
    // 添加载动画
    danweiindex = layer.msg('加载中，请稍等...', {
        icon: 16,
        shade: [0.1, '#fff'],
        time: false
    });
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
            console.log("添加复选框数据", checkBoxData[tabName]);
        }
    } else {
        $(this).parent().parent().css('background-color', '');
        checkBoxData[tabName].removeItem($(this).val());
        console.log("删除复选框数据", checkBoxData[tabName]);
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


function clearPrinterHtml() {
    printerHtml = "";
}


/**
 * 批量删除操作
 * @returns
 */
function dels(event) {
    if(checkBoxData[tabCurrent].toString()==""){
        layer.msg("至少需要选择一条数据！",{icon: 0,time:2000})
        return;
    }
    layer.confirm(deleteTip, { icon: 3 }, function () {
        $.ajax({
            type: "POST",
            url: "/dagl/bmgl/dynamicDeletes",
            data: {
                ids: checkBoxData[tabCurrent].toString(),
                tName: tabCurrent,
                all:all,
                ranking:ranking
            },
            dataType: "json",
            success: function (data) {
                if ('1' == data.flag) {
                    layer.msg(data.total+"条记录删除成功！", {
                        icon: 1,
                        time: 1000
                    }, function (index) {
                        checkBoxData[tabCurrent] = [];
                        initPanel();
                    });
                } else {
                    layer.msg("删除失败，请刷新页面再次操作", {icon: 5,time:1000});
                }
            },
            error: function (data) {
            }
        });
    });
}

/**
 * 删除操作
 * @returns
 */
function del(event) {

    // 阻止事件冒泡
    window.event ? window.event.cancelBubble = true : event.stopPropagation();

    var rid = $(this).parent().parent().find("td").eq(0).find("input").val();
    layer.confirm(deleteTip, { icon: 3 }, function () {
        $.ajax({
            type: "POST",
            url: "/dagl/bmgl/dynamicDelete",
            data: {
                id: rid,
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
                        checkBoxData[tabCurrent]=[];
                        initPanel();
                    });
                } else {
                    layer.msg("删除失败，请刷新页面再次操作", {
                        icon: 5,time:1000
                    });
                }
            },
            error: function (data) {
            }
        });
    });
}

/**
 * 打开只读页
 */
function readOnly() {

    var $this = $(this);

    // 添加修改标识
    $this.addClass('readonly');

    console.log("操作按钮", $this);

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

/**
 * 修改操作
 */
function modify(event) {

    // 阻止事件冒泡
    window.event ? window.event.cancelBubble = true : event.stopPropagation();

    // 添加修改标识
    $(this).addClass('modify');

    console.log("操作按钮", $(this));

    // 打开修改弹框
    layer.open({
        type: 2,
        title: ['修改','font-size:16px;font-weight:bold;'],
        content: '/modules/dagl/bmgl/modify.html?categoryCode=' + $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes()[0].categoryCode,
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

var indexx;
function max() {
    layer.full(indexx);
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

    console.log(checkBoxData[tabCurrent])
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
    console.log(checkBoxData[tabCurrent])
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
        var all = all;
        var ranking = ranking;
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