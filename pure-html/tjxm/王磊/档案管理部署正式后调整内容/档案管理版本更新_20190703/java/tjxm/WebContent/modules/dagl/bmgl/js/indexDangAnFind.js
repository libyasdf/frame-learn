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
//var fondsNo = Dictionary.getNameAndCode({mark: "fonds_no", type: "1"});
// 页面加载完成
$(function () {
   // var url = $("#left_ul",window.parent.document).find("a.active").attr("url");
    layer.closeAll();
    parameter = {};
    parameter.dak="dak"
    // 初始化左侧树
    typeTree.init({isQ2:true,isAdmin:1});
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
    $('.dropdown-menu .dropdown-item:contains(排序)').click(orderBy);
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
	if((complexFinds==1&&isRefresh)){
		layer.msg("请关闭查询页面后，再切换/点击便签",{icon: 0,time:2000})
		return;
	}
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
    $('.tab-panel-container').append($('<div class="tab-panel"></div><div class="total" style="margin-top:-27px;margin-right:20px;font-size:17px;text-align: right;margin-bottom:10px">当前总条数为<span style="font-size:17px" id="total"></span>条</div>'));

    // 添加表格
    $('.tab-panel').append($(''
        + '<table id="table" class="table table-bordered table-striped table-hover table-condensed table-responsive">'
        + '<thead>'
        + '<tr></tr>'
        + '</thead>'
        + '<tbody id="tbody"></tbody>'
        + '</table>')
    );

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
            // 添加复选框和操作
            $('#table thead tr:first-child').prepend($('<th style="text-align: center; min-width: 100px;">借阅状态</th>'));
            $('#table thead tr:first-child').prepend($('<th style="text-align: center; min-width: 100px;">操作</th>'));
            $('#table thead tr:first-child').prepend($('<th style="text-align: center; min-width: 100px;">选择</th>'));
            //$('#table thead tr:first-child').append($('<th>操作</th>'));
            $("#table  #all").click(alll)
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
            obj[filename] = treeObj.columnValue;
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
function getTreeWhere() {
    var object = {};
    getKeyValue(object, nodes[0]);
    return JSON.stringify(object);
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
            pageSize: "20", // 每页显示数据条数
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
                // $('.tab-panel-container .tab-panel table').css('overflow-x','auto');
                // $('.tab-panel-container .tab-panel table tbody').css('overflow-x','hidden');
            }
            // 计算是否滚动到底部
            var tbodyScrollTop = $('#table #tbody').scrollTop();
            if(pageNumber == 1){
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
                var $tr = $('<tr  title="点击查看详情" ></tr>');
                $('.tab-panel #table tbody').append($tr);
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
                        /*if(keyStrMin=='maintitle'){
                        	if(maintitle!=''){
                        		var patt1=new RegExp(maintitle,'g');//正则表达式
                        		valueStr=valueStr.replace(patt1,"<font class='font'>"+maintitle+"</font>");
                        		
                        	}
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
                            $tr.append($('<td style="min-width: '+tHeadData[j].LIST_COLUMN_WIDTH+'px"> <span  title='+valueStr2+'>'+ (valueStr.length >= dataWidth?valueStr.substring(0,dataWidth-3)+'...':valueStr) + '</span></td>'));
                        }else if(null == tHeadData[j].LIST_COLUMN_WIDTH && (valueStr.length*14+12) > 200){
                            //如果没有设置宽度，默认宽度为200，超过的则省略号展示
                            var dataWidth = (200-12)/14;

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
                          	$tr.append($('<td style="min-width: '+tHeadData[j].LIST_COLUMN_WIDTH+'px"><span  title='+valueStr2+'> '+ valueStr + '</span></td>'));
                          }
                    }
                }

                // 添加复选框和操作按钮
//                var ed = JSON.stringify(checkBoxData).indexOf(data[i].RECID) > -1 ? "checked" : "";
//                console.log(ed);
                $tr.prepend($('<td class="text-center" style="text-align: center;min-width: 100px; '+(data[i].BORROW_STATUS==null||data[i].BORROW_STATUS==""||data[i].BORROW_STATUS==0?"color: green":(data[i].BORROW_STATUS==1?"color: blue":"color: red"))+'" >'+(data[i].BORROW_STATUS==null||data[i].BORROW_STATUS==""||data[i].BORROW_STATUS==0?"未借出":(data[i].BORROW_STATUS==1?"部分借出":"借出"))+'</td>'));
                $tr.prepend($('<td title="" class="text-center" style="text-align: center;min-width: 100px;"><button title="点击加入收集列表" class="aaa list_table_btn2" onClick="adddd(this)" title="" style="z-index:99"  >收集<input type="hidden" borrowStatus="'+data[i].BORROW_STATUS+'" value="' + data[i].RECID + '" ></button></td>'));
                $tr.prepend($('<td title="" class="text-center" style="text-align: center;min-width: 100px;"><input type="checkbox"  value="' + data[i].RECID + '" ></td>'));
//                $tr.append($(''
//                    + '<td>'
//                    + '<i class="glyphicon glyphicon-edit opreation" title="修改"></i>'
//                    + '<i class="glyphicon glyphicon-trash opreation"  title="删除" ></i>'
//                    + '</td>')
//                );

                // 给复选框绑定方法
                $tr.find('td input[type="checkbox"]').change(changeCheckBoxState).click(stopBubble);
                $tr.find('.aaa').click(stopBubble);
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
            $('.tab-panel-container .tab-panel #table').scroll(function () {
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
            maintitle='';
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
    var dataLength = $('.tab-panel #table #tbody').children().length
    if(dataLength<=0){
        // 渲染表格内容
        var $tr = $('<p class="noData" id="noData" >没有找到匹配的记录</p>')
        $('.tab-panel #table #tbody').append($tr);
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
    for(var n = 0;n<checkBoxData[tabCurrent].length;n++){
        for(var m = 0;m<tableListData.length;m++){
            if(tableListData[m].RECID == checkBoxData[tabCurrent][n] ){
                var recId = checkBoxData[tabCurrent][n];//档号数据的业务id
                var daCode = "";//档案号
                var code =tableListData[m].BARCODE;//条码号
                if(tableListData[m].ITEM_FOLDER_NO && tableListData[m].ITEM_FOLDER_NO != ""  ){
                    daCode = tableListData[m].ITEM_FOLDER_NO;
                }else if(tableListData[m].FOLDER_NO && tableListData[m].FOLDER_NO!= ""  ){
                    daCode = tableListData[m].FOLDER_NO;
                }else if(tableListData[m].ARCHIVE_NO && tableListData[m].ARCHIVE_NO != ""  ){
                    daCode = tableListData[m].ARCHIVE_NO;
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
        console.log(printerHtml);
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
    layer.open({
        type: 2,
        title:[ '新增','font-size:16px;font-weight:bold;'],
        content: '/modules/dagl/bmgl/form.html?categoryCode=' + $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes()[0].categoryCode+"&fids="+getFids(),
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
                	if(data.total==data.ids){
	                    layer.msg(data.total+"条记录删除成功！", {
	                        icon: 1,
	                        time: 2000
	                    }, function (index) {
	                    	checkBoxData[tabCurrent] = [];
	                        initPanel();
	                    });
                	}else{
                		  layer.msg(data.total+"条数据删除成功！其中有"+(data.ids-0-data.total)+"条数据无法删除：该数据的档案实体状态为已移交或已移交待审核。", {
  	                        icon: 1,
  	                        time: 2000
  	                    }, function (index) {
  	                    	checkBoxData[tabCurrent] = [];
  	                        initPanel();
  	                    });
                	}
                } else {
                    layer.msg("删除失败，请刷新页面再次操作", {
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
                    layer.msg("无法删除：该项档案实体状态为已移交或已移交待审核", {
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

/**
 * 替换操作
 */
function replacelyAdded() {
    if (checkBoxData[tabCurrent].length < 1) {
        layer.msg("请选择需要替换的项再点击替换按钮",{icon: 0,time:2000})
        return;
    }
    layer.open({
        type: 2,
        title:[ '替换','font-size:16px;font-weight:bold;'],
        content: '/modules/dagl/bmgl/replace.html?fids=' + checkBoxData[tabCurrent].toString() + "&tName=" + tabCurrent + "&categoryCode=" + nodes[0].categoryCode,
        area: ['700px', '350px'],
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
}
var indexx;
function max() {
    layer.full(indexx);
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
	}else{
		$("#tbody").find("tr").each(function(){
			$(this).css('background-color', '');
			$(this).find("td").eq(0).find("input").prop("checked",false)
			checkBoxData[tabCurrent]=[];
		})
	}
	console.log(checkBoxData[tabCurrent])
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
//     console.log('index', index);
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
           // complexQuery("");
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
                                msg += successList[i].MAINTITLE + ", ";
                            }
                            msg += "移交成功!</font><br/>";
                        }


                        var alreadySubmmitList = data.data[0].alreadySubmmitList;
                        if (alreadySubmmitList.length > 0) {
                            msg += "<font style='color: red'>";
                            for (var i = 0; i < alreadySubmmitList.length; i++) {
                                msg += alreadySubmmitList[i].MAINTITLE + ", ";
                            }
                            msg += "已为移交待审核或者已移交，移交失败!</font><br/>";
                        }

                        var P_NotSubmmitList = data.data[0].P_NotSubmmitList;
                        if (P_NotSubmmitList.length > 0) {
                            msg += "<font style='color: red'>";
                            for (var i = 0; i < P_NotSubmmitList.length; i++) {
                                msg += P_NotSubmmitList[i].MAINTITLE + ", ";
                            }
                            msg += "上层档案尚未移交（请先移交上层档案），移交失败！</font><br/>";
                        }

                        var NotAssociatedList = data.data[0].NotAssociatedList;
                        if (NotAssociatedList.length > 0) {
                            msg += "<font style='color: red'>";
                            for (var i = 0; i < NotAssociatedList.length; i++) {
                                msg += NotAssociatedList[i].MAINTITLE + ", ";
                            }
                            msg += "未查询到上层档案，不在卷内，移交失败!</font><br/>";
                        }

                        layer.alert(msg, {title: '移交结果'});

                    } else {
                        layer.msg("移交失败，请刷新页面再次操作", {
                            icon: 5
                        });
                    }
                },
                error: function (data) {
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
        content: '/modules/dagl/bmgl/orderBy.html',
        area: ['800px', '550px'],
        offset: '20px',
        title: ['排序', 'font-size:16px;font-weight:bold;'],
        resize: true,
        maxmin: true,
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
function danganFind(){
	layer.open({
		type: 2,
		content: '/modules/dagl/bmgl/indexDangAnFind.html',
		area: ['1100px', '600px'],
		offset: '20px',
		title: ['排序', 'font-size:16px;font-weight:bold;'],
		resize: true,
		maxmin: true,
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

function findTreeParent(obj){
	if(obj.pId!=null){
		var node = obj.getParentNode();
		return findTreeParent(node)
	}else{
		return obj;
	}
}
var ad=null;
function adddd(e){
	ad= layer.msg('加载中，请稍等...', {
        icon: 16,
        shade: [0.1, '#fff'],
        time: false
    });
	var areYouOk = false;
	if(ranking==all){
		areYouOk=true;
	}
	if($(e).find("input").attr('borrowStatus')=="1"|| $(e).find("input").attr('borrowStatus')=="2"){
        layer.msg("该档案已借出，禁止添加！",{icon:0});
        return false;
    }
	var m = $("#tbody1 tr").length-1;
	$("#tbody1 tr").each(function(index,value){
		var $this =$(this);
		console.log($this.find("input").eq(0).val());
		console.log($(e).find("input").val());
		console.error(m+"      "+index)
		if($this.find("input").eq(0).val()==$(e).find("input").val()){
			layer.msg("已存在该条数据，禁止重复添加！",{icon:0})
			return false;
		}
		
		if(m==index){
			if($this.find("input").eq(0).val()==$(e).find("input").val()){
				layer.msg("已存在该条数据，禁止重复添加！",{icon:0})
			}else{
				nodes = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();
                $.get("/dagl/bmgl/adddd",{id:$(e).find("input").val(),tName:tabCurrent,areYouOk:areYouOk,all:all},function(data){
                    $("#tbody1").append("<tr><td class='cent'>"+data.main_title+"</td><td class='cent'>"+data.basefolder_unit+"</td><td class='cent'>"+data.archive_no+"</td><td class='cent'>"+findTreeParent(nodes[0]).name+"</td><td class='cent'><i class='glyphicon glyphicon-trash opreation' onClick='dell(this)' style='cursor:pointer' title='删除' ></i></td><input type='hidden' value="+data.recid+"><input type='hidden' value="+nodes[0].categoryCode+"><input type='hidden' value="+data.ljdw_mark+"><input type='hidden' value="+tabCurrent+"></tr>")
                    layer.close(ad)
                },"json");

            }
		}
	});
	
	if($("#tbody1 tr").length==0){
		nodes = $.fn.zTree.getZTreeObj("treeDemo").getSelectedNodes();
        $.get("/dagl/bmgl/adddd",{id:$(e).find("input").val(),tName:tabCurrent,areYouOk:areYouOk,all:all},function(data){
            $("#tbody1").append("<tr><td class='cent'>"+data.main_title+"</td><td class='cent'>"+data.basefolder_unit+"</td><td class='cent'>"+data.archive_no+"</td><td class='cent'>"+findTreeParent(nodes[0]).name+"</td><td class='cent'><i class='glyphicon glyphicon-trash opreation' onClick='dell(this)' style='cursor:pointer' title='删除' ></i></td><input type='hidden' value="+data.recid+"><input type='hidden' value="+nodes[0].categoryCode+"><input type='hidden' value="+data.ljdw_mark+"><input type='hidden' value="+tabCurrent+"></tr>")
            layer.close(ad)
        },"json");
    }
}

function dell(e){
	$(e).parent().parent().remove();
}
function importSearch(){
	var arr = [];
	var p =$("#tbody1 tr").length-1;
	$("#tbody1 tr").each(function(index,value){
		var obj = {};
		obj.mainTitle=$(this).find("td").eq(0).html()?$(this).find("td").eq(0).html():'';
		obj.basefolderUnit=$(this).find("td").eq(1).html()?$(this).find("td").eq(1):'';
		obj.ljdwMark=$(this).find("input").eq(2).val()?$(this).find("input").eq(2).val():'';
		obj.archiveNo=$(this).find("td").eq(2).html()?$(this).find("td").eq(2).html():'';
		obj.categoryName=$(this).find("td").eq(3).html()?$(this).find("td").eq(3).html():'';
		obj.categoryNo=$(this).find("input").eq(1).val()?$(this).find("input").eq(1).val():'';
		obj.recid=$(this).find("input").eq(0).val()?$(this).find("input").eq(0).val():'';
        obj.id="";
        obj.tableName=$(this).find("input").eq(3).val()?$(this).find("input").eq(3).val():'';
		arr.push(JSON.stringify(obj));
	});
	if(arr.length > 0){
        sessionStorage.setItem('daglSearchImportData',arr.join('$$$'));
        window.parent.layer.closeAll();
        window.parent.getImportData();
    }else{
        layer.msg("列表为空，请先添加数据再借阅！", {icon: 0});
    }
}

function borrowSearch(){
    var arr = [];
    var p =$("#tbody1 tr").length-1;
    $("#tbody1 tr").each(function(index,value){
        var obj = {};
        obj.mainTitle=$(this).find("td").eq(0).html()?$(this).find("td").eq(0).html():'';
        obj.basefolderUnit=$(this).find("td").eq(1).html()?$(this).find("td").eq(1):'';
        obj.ljdwMark=$(this).find("input").eq(2).val()?$(this).find("input").eq(2).val():'';
        obj.archiveNo=$(this).find("td").eq(2).html()?$(this).find("td").eq(2).html():'';
        obj.categoryName=$(this).find("td").eq(3).html()?$(this).find("td").eq(3).html():'';
        obj.categoryNo=$(this).find("input").eq(1).val()?$(this).find("input").eq(1).val():'';
        obj.recid=$(this).find("input").eq(0).val()?$(this).find("input").eq(0).val():'';
        obj.id="";
        obj.tableName=$(this).find("input").eq(3).val()?$(this).find("input").eq(3).val():'';
        arr.push(JSON.stringify(obj));
    });
    if(arr.length > 0){
        var resId = $('#left_ul').find('a.active').attr("id")
        sessionStorage.setItem('daglSearchBorrowData',arr.join('$$$'));
        window.location.replace("/modules/dagl/daly/borrow/borrowmanage/traditionalRegisterEditForm.html?oper=NEW&type=search&resId=" + resId);
    }else{
        layer.msg("列表为空，请先添加数据再借阅！", {icon: 0});
    }
}

//判断是否含有中文
function isChina(str){

    if(/.*[\u4e00-\u9fa5]+.*$/.test(str)) // \u 表示unicode
    {
        return true;
    }
    return false;
}