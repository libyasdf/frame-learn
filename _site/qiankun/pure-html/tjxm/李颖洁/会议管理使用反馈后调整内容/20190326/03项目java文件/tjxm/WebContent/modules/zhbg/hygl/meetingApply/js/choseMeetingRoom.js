var curDate = getCurrentDate("yyyy-MM-dd")
var parentMeetingDate = parent.$("#meetingTime").val()
var startTime = parentMeetingDate.substring(0,10);
var endTime = parentMeetingDate.substring(22,32);


// var dataList = [];   //  已选的数据列表
// var RealSelectedList = [];
var subflag; // 流程状态
var SelectedList = [];   // 公共json数组对象
var indexArr = [];  // 索引数组
var parentTrs = []; // 从父页面传入的tr数据
var num=0;  // 每个状态的单独索引
var meetingRoomList = [];
//定义bootatrap-table数据列
var right_table_col = [];

//判断某天日期是周几
function getWeekDay(date){
    var weekDay = ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
    var myDate = new Date(Date.parse(date));
    return weekDay[myDate.getDay()];
}

// data 日期  n 数字   return 日期的前n天
function getBeforeDate(date, n) {
    var n = n;
    var d = new Date(date);
    var year = d.getFullYear();
    var mon = d.getMonth() + 1;
    var day = d.getDate();
    if (day <= n) {
        if (mon > 1) {
            mon = mon - 1;
        } else {
            year = year - 1;
            mon = 12;
        }
    }
    d.setDate(d.getDate() - n);
    year = d.getFullYear();
    mon = d.getMonth() + 1;
    day = d.getDate();
    s = year + "-" + (mon < 10 ? ('0' + mon) : mon) + "-" + (day < 10 ? ('0' + day) : day);
    return s;
}

//前一天
function lastDay(){
    if($('#selectTime').val()>startTime){
        var lastDay = getBeforeDate($('#selectTime').val(),1)
       /* if($("#rightImage").attr("class")=='glyphicon glyphicon-ban-circle'){
     	   $("#rightImage").attr("class","btn-both rightbtn");
        }*/
        $('#selectTime').val(lastDay)
        /*if($('#selectTime').val()==startTime){
        	$("#leftImage").attr("class","glyphicon glyphicon-ban-circle");
        }*/
        selectDate()
    }

}
//后一天
function nextDay(){
    if($('#selectTime').val()<endTime){
        var nextDay = getBeforeDate($('#selectTime').val(),-1)
        $('#selectTime').val(nextDay)
       /*if($("#leftImage").attr("class")=='glyphicon glyphicon-ban-circle'){
    	   $("#leftImage").attr("class","btn-both leftbtn");
       }*/
        
       /* if($('#selectTime').val()==endTime){
        	$("#rightImage").attr("class","glyphicon glyphicon-ban-circle");
        }*/
        selectDate()
    }

}

/*
* 数组中选择相连的索引
* 参数：source  Type:Array
* 返回值：[Array,Array,Array]
* */
function arrange(source) {
    var t;
    var ta;
    var r = [];

    source.forEach(function(v) {
        // console.log(t, v);   // 跟踪调试用
        if (t === v) {
            ta.push(t);
            t++;
            return;
        }

        ta = [v];
        t = v + 1;
        r.push(ta);
    });

    return r;
}

/**
 * 初始化json数组对象
 * @return SelectedList 公共json数组对象，包含日期信息，暂时没有已选列表内容和会议室列表信息
 */
function initDataStru(){
    //  写入日期数据
    // 当前显示时间
    var showDate = $('#selectTime').val();
    var week = '';
    SelectedList = [];

    // 定义当前显示的时间对象，包含日期信息和一个空的已选列表数组，不包含会议室信息
    week = getWeekDay(showDate)
    var selectedObj = {
        date:showDate,  // 日期
        week:week,  // 星期
        data:[]  // 已选列表数组
    }
    // 将当前显示的日期对象push进入json数组中
    SelectedList.push(selectedObj);

    // 循环其他日期push进入json数组中
    while (showDate<endTime){
        showDate = getBeforeDate(showDate,-1);
        week = getWeekDay(showDate)
        selectedObj = {
            date:showDate,
            week:week,
            data:[]
        }
        SelectedList.push(selectedObj);
    }
    console.log('初始化数据结构')
    console.log(SelectedList)
    // 返回初始化好的json数组对象
    return SelectedList;
}

/*
* 循环数据插入Dom到列表
* @params oDom tbody容器  jquery Dom对象
* @params dataList  要循环的数据对象  数据类型 Array
* */
function appendDom(oDom,dataList){
    var o_date = '';   // 日期
    var o_week = '';   // 星期
    var o_rmName = '';  // 会议室名称
    var o_rmId = 0;   // 会议室id
    var o_min = 0;   // 最小时间
    var o_max = 0;  // 最大时间
   // var o_index = 0;  // 对象index索引
    var n_index = 0;  // 列表上的索引
    oDom.empty();  // 清空列表
    // 开始循环数据对象  第一层循环所有日期
    for (var j = 0; j < dataList.length; j++) {
        var selectedObj = dataList[j];   // 循环的日期对象数据
        o_date = selectedObj.date;     // 日期赋值
        o_week = selectedObj.week;     // 星期赋值
        // 第二层循环所有会议室
        for (var k=0; k<selectedObj.data.length; k++){
            o_rmName = selectedObj.data[k].mrName;  // 会议室名称赋值
            o_rmId  =  selectedObj.data[k].mrId;    // 会议室id赋值
            // 第三层循环所有已选的目标索引值
            for (var m=0; m<selectedObj.data[k].fData.length; m++){
               // var fDataArr = selectedObj.data[k].fData[m];   // 当前循环的索引数组 （相连的数据合并后的数组，暂时没有用到）
                var tData = selectedObj.data[k].tData[m];    // 当前循环的索引数组时间（过滤后的时间）
                o_min = tData.min;    // 最小时间赋值
                o_max = tData.max;    // 最大时间赋值
                n_index++   // 索引增加
                // 拼接dom元素
                var dom = '<tr data-index="'+ n_index +'" data-time-min="'+ o_min +'"  data-time-max="'+ o_max +'" data-mrid="' + o_rmId + '"> ' +
                            '<td width="10%">'+ n_index +'</td> ' +
                            '<td width="20%">'+ o_date +'</td> ' +
                            '<td width="15%">'+ o_week +'</td> ' +
                            '<td  rmName="' + o_rmName + '">'+ o_rmName +'</td> ' +
                            '<td width="20%">'+ o_min +":00-"+ o_max +":00" +'</td> ' +
                            '<td width="15%"><span class="delect glyphicon glyphicon-trash p-l-10" title="删除" style="cursor: pointer" data-index="'+ n_index +'"></span></td> ' +
                        '</tr>'
                // 插入dom元素到列表
                oDom.append(dom);
            }
        }
    }
}
/***
 * 更新数据对象
 */
function  upData(domMrId){
    var selectTime = $("#selectTime").val()
    indexArr = []   // 已选的索引数组
    // 循环所有显示的已选
    $('#right_table .selected:visible').each(function(i,d){
        var dMrId = $(d).attr('data-mrid');
        var dIndex = $(d).attr('data-index');
        if (domMrId == dMrId) {   // 在同一行
            indexArr.push(Number(dIndex));    // 插入到已选的数组
        }
    })

    // 将选择进行分组，相连的为一组，不相连的单独为一组，返回值多个Array
    var arr =  arrange(indexArr);

    var tArr = [];   // 合并后的时间数组
    for (var q=0; q<arr.length; q++) {
        var arrq =  arr[q];
        var min = $('#right_table .selected[data-index="'+ arrq[0] +'"]').attr('data-time-min');
        var max = $('#right_table .selected[data-index="'+ arrq[arrq.length-1] +'"]').attr('data-time-max');
        var tObj = {
            min:min,
            max:max
        }
        tArr.push(tObj);  // 将合并后的时间插入到数组
    }
    // 循环数据 将过滤后的索引数组和合并后的时间数组插入到数据中
    for (var i=0; i<SelectedList.length; i++)  {
        var selectedObj = SelectedList[i];
        if (selectedObj.date == selectTime) {
            for (var j=0; j<selectedObj.data.length; j++){
                var dataObj = selectedObj.data[j];
                if (dataObj.mrId == domMrId) {
                    dataObj.fData = arr;
                    dataObj.tData = tArr;
                }
            }
        }
    }
     console.log(SelectedList);
}
/*
* 选择数据 添加到SelectedList数据对象中
* 参数:item  当前选择的jquery对象
* */
function addItem(item){
    var domMrId = item.attr('data-mrid');
    upData(domMrId)
}

/*
* 取消选择 (移除数据)
* 参数:item  已选的jquery对象
* */
function delectItem(item){
    var domMrId = item.attr('data-mrid')
    upData(domMrId)
}


function upIndex(){
    $('#list_content').find('tr').each(function(index,dom){
        $(this).find('td').eq(0).text(index+1)
    })
}

/**
 * 删除内容
 * @params item 点击的jquery对象
 */
function delect(item,selectedList){
    var dIndex = item.attr('data-index');
    var dTr = item.parents('tr[data-index="'+ dIndex +'"]');
    var dMrId = dTr.attr('data-mrid');
    var trMin = dTr.attr('data-time-min');
    var trMax = dTr.attr('data-time-max');
    var trDate = dTr.find('td').eq(1).html();
    var selecteds = $('#right_table tbody .selected:visible');
    // 循环数据 将点击的那条数据删除
    for (var i=0; i<selectedList.length; i++)  {
        var selectedObj = selectedList[i];
        for (var j=0; j<selectedObj.data.length; j++){
            var dataObj = selectedObj.data[j];
            if (selectedObj.date == trDate && dataObj.tData && dataObj.tData.length > 0){
                for (var t=0; t<dataObj.tData.length; t++) {
                    var tdata =  dataObj.tData[t];
                    if (tdata.min == trMin && tdata.max == trMax && dataObj.mrId==dMrId ) {
                        // 查找在数组中的索引
                        var inArrIndex = $.inArray(tdata,dataObj.tData);
                        // 在数组中删除
                        dataObj.tData.splice(inArrIndex,1)
                        dataObj.fData.splice(inArrIndex,1)
                        // 在表格中删除选中状态
                        selecteds.each(function (i,selected) {   // 循环所有表格中显示的选中状态
                            var sMin = $(selected).attr('data-time-min');
                            var sMax = $(selected).attr('data-time-max');
                            var sMrId = $(selected).attr('data-mrid');
                            // 判断选中状态是否在删除的时间范围内并且是相同的会议室,如果是就隐藏选中状态
                            if (Number(sMin)>=Number(trMin) && Number(sMax)<=Number(trMax) && dMrId==sMrId){
                                $(selected).hide();
                                dTr.remove();
                            }
                        })

                    }
                }
            }
        }
    }

    upData();
    upIndex();
}

// 设置data项
function setData(value,row,index,oTimeNum){
    num++;
    var apply = 'apply glyphicon glyphicon-adjust'; // 申请中
    var takeUp = 'takeUp glyphicon glyphicon-exclamation-sign'; // 已占用
    var selected = 'selected glyphicon glyphicon-ok-sign'; // 已选择

    var html = "";
    if (value == "0") {
        html += "<span class='"+ selected +"'  data-time-min='"+ oTimeNum.min +"'  data-time-max='"+ oTimeNum.max +"' data-index='"+ num +"' data-mrName='"+ row.meetingRoomName +"' data-mrId='"+ row.meetingRoomId +"'></span>";
    } else if (value == "1") {
        html += "<span class='"+ selected +"'  data-time-min='"+ oTimeNum.min +"'  data-time-max='"+ oTimeNum.max +"' data-index='"+ num +"' data-mrName='"+ row.meetingRoomName +"' data-mrId='"+ row.meetingRoomId +"'></span>";
        html += "<span class='"+ apply +"' data-index='"+ index +"' data-mrId='"+ row.meetingRoomId +"'></span>";
    } else if (value == "2") {
        html += "<span class='"+ selected +"'  data-time-min='"+ oTimeNum.min +"'  data-time-max='"+ oTimeNum.max +"' data-index='"+ num +"' data-mrName='"+ row.meetingRoomName +"' data-mrId='"+ row.meetingRoomId +"'></span>";
        html += "<span class='"+ takeUp +"' data-index='"+ index +"' data-mrId='"+ row.meetingRoomId +"'></span>";
    }else{
        html += "<span class='' data-index='"+ index +"' data-mrId='"+ row.meetingRoomId +"'></span>";
    }
    return html;
}


/**
 * 数据回显 根据父页面传进来的值插入到数据结构中
 * **/
function loadDate(parentTrs,selectedList){
        var dataArr = [];
        for (var g=0;g<selectedList.length; g++) {
            var obj = selectedList[g];
            if (obj.data.length <= 0) {
                dataArr = [];
                for (var r = 0; r < meetingRoomList.length; r++) {
                    var rmObj = {
                        mrId: meetingRoomList[r].meetingRoomId,
                        mrName: meetingRoomList[r].meetingRoomName,
                        fData: [],
                        tData: []
                    };
                    // 将会议室列表push进一个数组，然后将这个会议室列表数组赋值给当前日期的data数组
                    dataArr.push(rmObj);
                }
                obj.data = dataArr;
            }
            parentTrs.each(function (i,parentTr) {
                var parentData = $(parentTr);
                var min = parentData.attr('data-time-min');
                var max =  parentData.attr('data-time-max');
                var domMrId = parentData.attr('data-mrid');
                var domDate = parentData.find('td').eq(1).html();
                if (obj.date == domDate){
                        $.each(obj.data, function (j,dataObj) {
                            if (dataObj.mrId == domMrId){
                                var oTime = {
                                    min:min,
                                    max:max
                                };
                                dataObj.fData.push(oTime);
                                dataObj.tData.push(oTime);
                            }
                        })
                }
            })
        }


    SelectedList = selectedList;
}

/**
 * 初始化bootstrap table
 */
function InitTable(){
    right_table_col = [
        {
            field: 'meetingRoomName'
            , title: '会议室'
            , align: "center"
            , formatter: function (value, row, index) {
                // console.log(value, row, index)
                return "<span class='' data-index='"+ index +"' data-mrId='"+ row.meetingRoomId +"'>"+ row.meetingRoomName +"</span>";
            }
        }, {
            field: 'content'
            , title: '容纳人数/人'
            , width: '10%'
            , align: "center"
            , formatter: function (value, row, index) {  //直接定义function,return就是html代码
                return "<span class='' data-index='"+ index +"' data-mrId='"+ row.meetingRoomId +"'>"+ row.content +"</span>";
            }
        },{
            field: 'equipment'
            , title: '设备情况'
            , align: "center"
            , formatter: function (value, row, index) {  //直接定义function,return就是html代码
                return "<span class='' data-index='"+ index +"' data-mrId='"+ row.meetingRoomId +"'>"+ row.equipment +"</span>";
            }
        },
        {
            field: 'noneToTenState'
            , title: '9:00-10:00'
            , width: '5%'
            , align: "center"
            , formatter: function (value, row, index) {  //直接定义function,return就是html代码
                // return "<span class='' data-index='"+ index +"' data-mrId='"+ row.meetingRoomId +"'></span>";
                //console.log(setData(value,row,index,'noneToTenState'))
                var oTimeNum = { min:9, max:10 };
                return setData(value,row,index,oTimeNum)
            }
        },
        {
            field: 'tenToElevenState'
            , title: '10:00-11:00'
            , width: '5%'
            , align: "center"
            , formatter: function (value, row, index) {  //直接定义function,return就是html代码
                var oTimeNum = { min:10, max:11 };
                return setData(value,row,index,oTimeNum)
            }
        },
        {
            field: 'elevenToTwelveState'
            , title: '11:00-12:00'
            , width: '5%'
            , align: "center"
            , formatter: function (value, row, index) {  //直接定义function,return就是html代码
                var oTimeNum = { min:11, max:12 };
                return setData(value,row,index,oTimeNum)
            }
        },
        {
            field: 'twelveToThirteenState'
            , title: '12:00-13:00'
            , width: '5%'
            , align: "center"
            , formatter: function (value, row, index) {  //直接定义function,return就是html代码
                var oTimeNum = { min:12, max:13 };
                return setData(value,row,index,oTimeNum)
            }
        },
        {
            field: 'thirteenToFourteenState'
            , title: '13:00-14:00'
            , width: '5%'
            , align: "center"
            , formatter: function (value, row, index) {  //直接定义function,return就是html代码
                var oTimeNum = { min:13, max:14 };
                return setData(value,row,index,oTimeNum )
            }
        },
        {
            field: 'fourteenToFifteenState'
            , title: '14:00-15:00'
            , width: '5%'
            , align: "center"
            , formatter: function (value, row, index) {  //直接定义function,return就是html代码
                var oTimeNum = { min:14, max:15 };
                return setData(value,row,index,oTimeNum)
            }
        },
        {
            field: 'fifteenToSixteenState'
            , title: '15:00-16:00'
            , width: '5%'
            , align: "center"
            , formatter: function (value, row, index) {  //直接定义function,return就是html代码
                var oTimeNum = { min:15, max:16 };
                return setData(value,row,index,oTimeNum)
            }
        },
        {
            field: 'sixteenToSeventeenState'
            , title: '16:00-17:00'
            , width: '5%'
            , align: "center"
            , formatter: function (value, row, index) {  //直接定义function,return就是html代码
                var oTimeNum = { min:16, max:17 };
                return setData(value,row,index,oTimeNum)
            }
        },
        {
            field: 'seventeenToEighteenState'
            , title: '17:00-18:00'
            , width: '5%'
            , align: "center"
            , formatter: function (value, row, index) {  //直接定义function,return就是html代码
                var oTimeNum = { min:17, max:18 };
                return setData(value,row,index,oTimeNum)
            }
        },
    ];
    //初始化表格
    TableInit.init({
        // height: 500,
        domId: "right_table",
        striped: false,//去除隔行变色
        classes: 'table',//去除悬浮变色
        pagination: false,	//不分页
        url: "/zhbg/hygl/meetingRoomUseInfo/getMeetingRoomUseList",
        columns: right_table_col,
        queryParams: function (params) {
            //这里将各个查询项添加到要传递的参数中
            //                可以做一些校验
            params.date = $('#selectTime').val();
            return params;
        },
    });
    var nq=0;  // 切换日期次数，只有第一次进来的时候加载父页面数据
    // tbody改变时触发
    $('#right_table').on('post-body.bs.table', function (event,row) {
        num=0;  // index 索引初始化

        var nowDate = $('#selectTime').val();
        if (row.length <= 0) return   // 如果表格中没有数据就停止执行

        nq++;   // 累加切换日期次数，只有第一次进来的时候加载父页面数据
        if (nq==1) {  // 判断第一次进来的时候加载父页面数据
            // if (parentTrs && parentTrs.length>0){
                meetingRoomList = row;
                loadDate(parentTrs,SelectedList)
            // }
        }

        /**
         * 内容回显 根据数据结构渲染内容
         * 判断父页面是否传入数据，如果有内容就将父页面的parentTrs列表信息插入josn数组对象中对应的每个会议室信息里面，然后进行渲染页面，在页面显示已选的图标。
         */
        // 第一层循环 循环日期对象
        for (var j = 0; j < SelectedList.length; j++) {
            var selectedObj = SelectedList[j];
            // 判断如果json数组中的日期和表格数据中的日期相等，就将会议室数据插入到当前日期中的data数组中。
            if (selectedObj.date == nowDate) {
                // 根据数据回显内容
                var selecteds = $('#right_table .selected'); // 页面所有的已选状态按钮
                var oneClickDom = [];  // 已选并且被占用的dom数组,需要循环进行重新绑定只可点击一次的click事件
                selecteds.hide();   // 先把所有的已选状态按钮隐藏
                /**  循环日期对象中会议室信息，将时间对应的已选状态按钮显示出来 **/
                for (var k=0; k<selectedObj.data.length; k++){
                    var dMrId = selectedObj.data[k].mrId;
                    // 循环会议室信息中的已选的所有时间数组,相连时间的为一个数组
                    for (var m=0; m<selectedObj.data[k].fData.length; m++){
                        var fDataArr = selectedObj.data[k].fData[m];
                        var tData = selectedObj.data[k].tData;
                        // 循环每个已选的时间数组
                        for (var n=0; n<tData.length; n++){
                            // 找到时间数组中的最大时间和最小时间
                            var trMin = tData[n].min;
                            var trMax = tData[n].max;
                            /** 循环所有的已选状态按钮,显示符合时间的已选按钮 **/
                            selecteds.each(function (i,selected) {   // 循环所有表格中显示的选中状态
                                var sMin = $(selected).attr('data-time-min');  // 表格中每个已选状态的最小时间
                                var sMax = $(selected).attr('data-time-max');  // 表格中每个已选状态的最大时间
                                var sMrId = $(selected).attr('data-mrid'); // 表格中的每个已选状态的会议室id
                                /** 判断选中状态是否在显示的时间范围内并且是相同的会议室,如果是就显示选中状态 **/
                                if (Number(sMin)>=Number(trMin) && Number(sMax)<=Number(trMax) && dMrId==sMrId){
                                    $(selected).show();
                                    // 判断这个选中的是否已经被别人占用了，如果是已占用或者申请中的状态就将这个dom插入oneClickDom数组中重新绑定click事件
                                    if ($(selected).parent().find('.takeUp').length>0 || $(selected).parent().find('.apply').length>0){
                                        if (subflag==0){
                                            oneClickDom.push($(selected).parent())
                                        }else{
                                            $(selected).parent().find('.takeUp').remove();
                                            $(selected).parent().find('.apply').remove();
                                        }
                                    }
                                }
                            })
                        }
                    }
                }
            }

        }
        console.error(SelectedList)

        // 事件绑定
        var mrIds = "";
        $('#right_table').find('tr').find('td:not(:eq(1),:eq(0))').each(function () {
            if ($(this).find("span[data-totalId]").attr("data-totalId") == $('#meetingroomId').val() + $('#meetingStartDate').val() + $('#meetingStartTime').val()) {
                $(this).addClass("checkTd");
                $(this).find('.selected').show();
                $(this).find("span[data-totalId]").empty();
                mrIds += $(this).find("span[data-mrId]").attr("data-mrId") + ",";
            } else if ($(this).find("span[data-totalId]").attr("data-totalId") == $('#meetingroomId').val() + $('#meetingEndDate').val() + $('#meetingEndTime').val()) {
                $(this).addClass("checkTd");
                $(this).find('.selected').show();
                $(this).find("span[data-totalId]").empty();
                mrIds += $(this).find("span[data-mrId]").attr("data-mrId") + ",";
            }

        });
        $('tr.readOnly').find('td:not(:eq(1),:eq(0))').unbind('click').bind('click', function (e) {
            //取消事件冒泡
            var evt = e ? e : window.event;
            if (evt.stopPropagation) {
                evt.stopPropagation();
            } else {
                evt.cancelBubble = true;
            }
            if ($(this).find('.apply').length > 0 || $(this).find('.takeUp').length > 0) {
                layer.msg("此时间段已被占用，请选择其他时间！", {icon: 0});
                return;
            }
            if ($(this).hasClass("checkTd")) {
                $(this).removeClass("checkTd");
                $(this).find('.selected').hide();
                delectItem($(this).find('.selected'))
            } else {
                $(this).addClass("checkTd");
                $(this).find('.selected').show();
                addItem($(this).find('.selected'));
            }
        })

        // 如果内容是已选中并且被占用的状态就绑定一次点击事件
        $.each(oneClickDom,function (i,dom) {
            dom.unbind('click');
            dom.one('click',function () {
                $(this).removeClass("checkTd");
                $(this).find('.selected').hide();
                delectItem($(this).find('.selected'));
                dom.on('click',function (ev) {
                    if ($(this).find('.apply').length > 0 || $(this).find('.takeUp').length > 0) {
                        layer.msg("此时间段已被占用，请选择其他时间！", {icon: 0});
                        return;
                    }
                })
            })
        })
    })
}

//重新加载表
function selectDate() {
    //获取表头数据并添加到right_table_col
    //getByWeek();
    //TableInit.refTable('right_table');
    //销毁表
    $('#right_table').bootstrapTable('destroy');
    //重新加载表
    TableInit.init({
        // height: 500,
        domId: "right_table",
        striped: false,//去除隔行变色
        classes: 'table',//去除悬浮变色
        pagination: false,	//不分页
        url: "/zhbg/hygl/meetingRoomUseInfo/getMeetingRoomUseList",
        columns: right_table_col,
        queryParams: function (params) {
            //这里将各个查询项添加到要传递的参数中
            //可以做一些校验
            params.date = $('#selectTime').val();
            return params;
        },
        success:function () {

        }
    });
}

/**
 * 预览已选列表
 */
function preview(){
    var oDom = $('#list_content')
    appendDom(oDom,SelectedList);
    $('#modal_list').modal('show');

    // 绑定删除事件（删除预览列表内容）
    $('#list_content .delect').off('click')
    $('#list_content .delect').on('click',function () {
        delect($(this),SelectedList)
    })
}

/**
 * 确认
 */
function confirm() {
    window.parent.$("#table_list").empty()
    var trs = $('#list_content tr');
    parent.getChildData(SelectedList);
    parent.layer.closeAll();
}
/**
 * 子页面数据回显 在父页面调用
 */
function getParentData(trs,sflag){
    console.log('子页面数据回显 在父页面调用')
    console.log(trs)
    subflag = sflag;
    parentTrs = trs;
    if (!subflag==0){
        var dom = '<i class="selected glyphicon glyphicon-ok-sign"></i> 当前选中'
        $('.btns_right span').eq(1).html(dom);
    }
    // SelectedList = data;
    // for (var z=0; z<data.length; z++){
    //     var parentData = data[z];
    //     var uDate = parentData.useDate;
    //     var uTime = parentData.useTime;
    //     var urmId  =  parentData.meetingroomId;
    //     var urmName  =  parentData.meetingroomName;
    //     var startTime = uTime.split('-')
    //     var uMin  = startTime[0].split(":")[0];
    //     var uMax = startTime[1].split(":")[0];
    // }
}

$(document).ready(function (e) {
    $("#selectTime").val(startTime);
    /*if($('#selectTime').val()==startTime){
    	$("#leftImage").attr("class","glyphicon glyphicon-ban-circle");
    }
    if($('#selectTime').val()==endTime){
    	$("#rightImage").attr("class","glyphicon glyphicon-ban-circle");
    }*/
    InitTable();
    initDataStru();
    // 预览
    $('#preview').click(preview)
});
