var index;
var nowTime;
var curTime;    //当前时间的毫秒数
var curDay;     //当前日期yyyy-MM-dd
var curMonth;   //当前月份yyyy-MM
var curYear;    //当前年yyyy
var stspTimer; // 食堂食谱定时器
/**
 * 门户初始化加载
 */
jQuery(document).ready(function ($) {

    $('[data-toggle="popover"]').popover({
        trigger: "hover"
    })

    //获取当前日期
    curTime = getCurrentTime();
    var curDate = new Date(curTime);
    curDay = curDate.Format("yyyy-MM-dd");
    curMonth = curDate.Format("yyyy-MM");
    curYear = curDate.Format("yyyy");
    // 轮播图
    $(".carousel").mouseover(function () {
        $(".carousel__indicators").show();
    });
    $(".carousel").mouseout(function () {
        $(".carousel__indicators").hide();
    });

    $('.carousel__indicators__item').unbind('click').bind('click', function (e) {
        $(this).siblings('li.carousel__active').removeClass('carousel__active')
        $(this).addClass('carousel__active');
        var _index = $('.carousel__indicators__item').index($(this));
        var _width = $('.carousel').width();
        var _offset = 0 - parseInt(_index) * parseInt(_width);
        $('.carousel__container').animate({
            'left': _offset
        }, 1000);
    })

    /**
     * 初始化待办列表、已办列表切换事件
     */
    $(".workflow").click(function () {
        if ($(this).attr('id').indexOf("db") >= 0) {
            $("#yb").removeClass("active");
            $("#yiban").css({
                "display": "none"
            });
            $("#db").addClass("active");
            $("#daiban").css({
                "display": "block"
            });
            getWorkFlowData("daiban");
        } else if ($(this).attr('id').indexOf("yb") >= 0) {
            $("#db").removeClass("active");
            $("#daiban").css({
                "display": "none"
            });
            $("#yb").addClass("active");
            $("#yiban").css({
                "display": "block"
            });
            getWorkFlowData("yiban");
        }
    })

    // 初始化待办显示区域
    getWorkFlowData("daiban");
    //获取未读消息数
    getMsgCount();
    //获取消息列表
    getMsgList();
    //获取邮件消息数
    getMailCount();
    //获取最新发布信息
    getInfoNoticeList();
    getRemind()
    //同时也查询信息发布和音视频的发布内容，如果点击时候再去加载，会先显示默认内容
    getRemind({key:'xxfb'})
    getRemind({key:'ysp'})
    //获取待阅列表(0表示待阅；1表示已阅)
    // getFlowSendList("0");

    //食堂菜谱
    $("#diningMenuDate").empty().append(getNowFormatDatery(getNowFormatDate()) + " " + getWeek(getNowFormatDate()));
    $("#menuDate").val(getNowFormatDate());
    // alert(nextDate($("#menuDate").val()));
    getDiningMenuData(getNowFormatDate());
    $("ul#dinnerMenu").on("click", "li", function () {
        clearInterval(stspTimer);
        if ($(this).attr('id').indexOf("fuli") >= 0) {
            $("#tese").removeClass("active");
            $("#dinnerpage2").css({
                "display": "none"
            });
            $("#fuli").addClass("active");
            $("#dinnerpage1").css({
                "display": "block"
            });

        } else if ($(this).attr('id').indexOf("tese") >= 0) {
            $("#fuli").removeClass("active");
            $("#dinnerpage1").css({
                "display": "none"
            });
            $("#tese").addClass("active");
            $("#dinnerpage2").css({
                "display": "block"
            });
        }
    })
    // 根据当前时间显示内容
    function showStsp(){
        // 更新内容
        function updatedStsp (){
            var d = new Date();
            var H = d.getHours();
            // console.log(H)
            if(H < 9){  // 早晨9点之前 显示当天早晨
                $("#tese").removeClass("active");
                $("#dinnerpage2").css({
                    "display": "none"
                });
                $("#fuli").addClass("active");
                $("#dinnerpage1").css({
                    "display": "block"
                });
                return
            }

            if(H >= 9 && H < 14){   // 早晨9点 到 下午2点之间  显示当天午餐
                $("#fuli").removeClass("active");
                $("#dinnerpage1").css({
                    "display": "none"
                });
                $("#tese").addClass("active");
                $("#dinnerpage2").css({
                    "display": "block"
                });
                return
            }

            if (H >= 14){
                console.log('ccccc')
                getNextDat();
                return
            }
        }
        // 定时更新
        function realTime(){
            var nowDate,nowHours,nowMin;
            stspTimer = setInterval(function(){
                nowDate = new Date();
                nowHours = nowDate.getHours();
                nowMin = nowDate.getMinutes();
                // console.log(nowHours,nowMin)
                if(nowHours < 9){  // 早晨9点之前 显示当天早晨
                    if ($("#fuli").hasClass('active')) return;
                    updatedStsp();
                }else if(nowHours >= 9 && nowHours < 14){   // 早晨9点 到 下午2点之间  显示当天午餐
                    if ($("#tese").hasClass('active')) return;
                    updatedStsp();
                }else if(nowHours >= 14){   // 下午2点以后 显示明天早餐
                    if ($("#fuli").hasClass('active') && ( $("#diningMenuDate").text()== getNowFormatDatery($("#menuDate").val()) + " " + getWeek($("#menuDate").val()) )){
                        return
                    }
                    updatedStsp();
                }
            },1000)
        }
        updatedStsp();
        realTime();
    }
    showStsp()

    //设置工作日志的日期
    var cruDate = getNowFormatDate();
    $("#dateLog").val(cruDate);
    Dictionary.init({ mark: 'log_type', type: '1', position: "logType", domType: "select", name: "logType",hasSelect:false });


    $('.tooltip-show').tooltip('show');
    showDate();// 加载时间日期星期

    // 初始化工作日志列表
    loadWorkLogData1();

    //加载常用模块数据
    loadOftenModel();

    //工作计划
    // var curDate = getCurrentDate("yyyy-MM");
    var curDate = curMonth;
    initYearOptions();
    $('#year').val(curDate.split('-')[0] + '年');
    var year = curDate.split('-')[1];
    if (year.substr(0, 1) == 0) {
        $('#month').val(year.substr(1, 2) + '月');
    } else {
        $('#month').val(year + '月');
    }
    setData(curDate.split('-')[0], curDate.split('-')[1]);

    $('#lt1').click(subYear);
    $('#gt1').click(addYear);
    $('#lt2').click(subMonth);
    $('#gt2').click(addMonth);
    $('#today').click(toToday);
    $('#year').change(updateCalendar);
    $('#month').change(updateCalendar);
});

//定时获取时间并填充首页
function showDate(delter) {
    if (!delter) {
        nowTime = curTime;
    } else {
        nowTime = nowTime + delter
    }
    var now = new Date(nowTime);
    var test = new Date();
    var date_date = now.getFullYear() + "-";
    var temp = now.getMonth() + 1;
    if (temp < 10) date_date += "0";
    date_date += temp + "-";
    temp = now.getDate();
    if (temp < 10) date_date += "0";
    //日期
    date_date += temp;
    var today = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
    //星期
    var date_week = today[now.getDay()];
    var date_time = now.getHours();
    if (date_time < 10)
        date_time = "0" + date_time;
    temp = now.getMinutes();
    if (temp < 10)
        temp = "0" + temp;
    //时间
    date_time = date_time + ":" + temp;

    $("#date_time").html(date_time);
    $("#date_date").html(date_date);
    $("#date_week").html(date_week);
    ctroltime = setTimeout("showDate(10000)", 10000);
}
// 定时获取时间并填充首页结束

// 获取日期的前一天
function preDate(preDateString) {
    var y;
    var m;
    var d;
    var predateArray = preDateString.split("-");
    predate = new Date(predateArray[0], parseInt(predateArray[1] - 1),
        predateArray[2] - 1);
    y = predate.getFullYear();
    m = predate.getMonth() + 1;
    d = predate.getDate();
    m = m >= 10 ? m : "0" + m;
    d = d >= 10 ? d : "0" + d;
    return y + "-" + m + "-" + d;
}
// 获取日期的后一天
function nextDate(nextDateString) {
    var ny;
    var nm;
    var nd;
    var nextdateArray = nextDateString.split("-");
    nextdate = new Date(nextdateArray[0], parseInt(nextdateArray[1] - 1),
        parseInt(nextdateArray[2]) + 1);
    ny = nextdate.getFullYear();
    nm = nextdate.getMonth() + 1;
    nd = nextdate.getDate();
    nm = nm >= 10 ? nm : "0" + nm;
    nd = nd >= 10 ? nd : "0" + nd;
    return ny + "-" + nm + "-" + nd;
}

// 获取日期格式日月
function getNowFormatDatery(dateString) {
    // var date = new Date();
    var datery;
    var seperatory = "月";
    var seperatorr = "日";
    var dateArrayry = dateString.split("-");
    datery = new Date(dateArrayry[0], parseInt(dateArrayry[1] - 1),
        dateArrayry[2]);
    var yearry = datery.getFullYear();
    var monthry = datery.getMonth() + 1;
    var strDatery = datery.getDate();
    var currentdatery = monthry + seperatory + strDatery + seperatorr;
    return currentdatery;
}
// 食堂菜单
// 获取当前日期
function getNowFormatDate() {
    return curDay;
}
// 判断是否为空
function isNull(object) {
    if (object == null || typeof object == "undefined") {
        return true;
    }
    return false;
};
// 根据日期获取星期
function getWeek(dateString) {
    var date;
    if (isNull(dateString)) {
        date = curDay;
    } else {
        var dateArray = dateString.split("-");
        date = new Date(dateArray[0], parseInt(dateArray[1] - 1), dateArray[2]);
    }
    // var weeks = new Array("日", "一", "二", "三", "四", "五", "六");
    // return "星期" + weeks[date.getDay()];
    return "星期" + "日一二三四五六".charAt(date.getDay());
};

//加载常用模块数据
function loadOftenModel() {
    $.post("/mypage/oftenModel/getData", {}, function (data) {
        console.info(data)
        if (data.length == 0) {
            $("#cymkli_main").empty();
            $("#cymkli_main").append("<div class=\"st_main\"></div><img src=\"static/images/noMes.png\"/><p class=\"noMes\">暂无设置常用模块！</p></div>");
        } else {
            $("#cymkli_main").empty();
            $("#cymkli_main").append(" <ul class=\"modulesBox clearfix\" id=\"cymkli\">");
            $("#cymkli").empty();
            for (var i = 0; i < data.length; i++) {
                var str = " <li class=\"commonModules\" title='"+ data[i].parent +"' onclick='oftenModelInfo(\""+ data[i].url +"\",\""+ data[i].flag +"\")'> " +
                    "<div class=\"imgBox\"><i class='"+ data[i].classes+"'></i></div>" +
                    "<p class=\"moduleName\">"+data[i].name+"</p>"
                // var str = '<li onclick="oftenModelInfo(\'' + data[i].discription + '\',\'' + data[i].memo + '\')"><a href="#" ><span class="' + data[i].style + '" aria-hidden="true"></span><br>' + data[i].name + '</a></li>'
                $("#cymkli").append(str)
            }
        }

    });
}

function oftenModelInfo(url,flag) {
    if(flag == '1'){
        location.href = url;
    }else{
        layer.msg("模块不存在或没有权限，请重新设置！", {icon: 0});
    }
}
//加载常用模块数据
function loadWorkLogData1() {
    $("#workLogList").empty()
    $.post("/mypage/worklog/getPageList", {}, function (data) {
        if (data.length == 0) {
            $("#workLogList").hide();
            $("#workLogInfo").show();
        } else {
            $("#workLogInfo").hide();
            $("#workLogList").show();
            for (var i = 0; i < data.length; i++) {
                var li = "<li class='gr_worklog_listitem' ><div class='worklog_listitem_date fl' onclick='workLogReadOnly(\"" + data[i].ID + "\")'>" + data[i].DATELOG.substr(5, 2) + "." + data[i].DATELOG.substr(8, 2) + "</div>";
                li += "<div class='worklog_listitem_name fl' style='cursor:pointer' title='点击查看详情' onclick='workLogReadOnly(\"" + data[i].ID + "\")'>" + data[i].CONTENT + "</div>"
                li += "<div class='worklog_listitem_items fl' >";
                if (data[i].LOGTYP == '0') {
                    li += "<img src='/static/images/main/listitem_items_daily.png' />"
                } else if (data[i].LOGTYP == '1') {
                    li += "<img src='/static/images/main/listitem_items_work.png' />"
                } else if (data[i].LOGTYP == '2') {
                    li += "<img src='/static/images/main/listitem_items_partyWork.png' />"
                } else if (data[i].LOGTYP == '3') {
                    li += "<img src='/static/images/main/listitem_items_others.png' />"
                }
                li += "</div>"
                if (data[i].STATE == "0") {
                    //未上报
                    li += "<div class='operationBtns fr' style='cursor:pointer'>"
                    li += "<i class='glyphicon glyphicon-send'  title='上报' onclick='appear(\"" + data[i].ID + "\")'></i>"
                    li += "<i class='glyphicon glyphicon-edit'  title='修改' onclick='updateLog(\"" + data[i].ID + "\")'></i>"
                    li += "<i class='glyphicon glyphicon-trash'  title='删除' onclick='deleteLog(\"" + data[i].ID + "\")'></i>"
                    li += "</div>"
                }
                li += "</li>"
                $("#workLogList").append(li)
            }
        }

    });

}
//工作日志的只读
function workLogReadOnly(id) {
    MyLayer.layerOpenUrl({
        url: '/modules/mypage/worklog/workLogReadOnlyForm.html?id=' + id + "&oper=EDIT&from=1",
        title: "只读"
    });
}
//我的工资
function mySalary() {
	layer.open({
        type: 2,
        maxmin:true,
        zIndex:1900,//layer默认19891014 	dialog默认值为1976
        content: '/modules/zhbg/salary/mySalaryList.html',
        area: ["90%", "90%"],
        title: false
        
    });
   /* MyLayer.layerOpenUrl({
        url: "/modules/zhbg/salary/mySalaryList.html",
        title: "我的工资",
        titleStrle: "height:0px;border-bottom:0px"
    })*/
}

//我的考勤
function myKqAttendance() {
	layer.open({
        type: 2,
        maxmin:true,
        zIndex:1900,//layer默认19891014 	dialog默认值为1976
        content: '/modules/zhbg/kqgl/xtpz/myKqAttendanceList.html',
        area: ["90%", "90%"],
        title: false
    });
   /* MyLayer.layerOpenUrl({
        url: "/modules/zhbg/kqgl/xtpz/myKqAttendanceList.html",
        title: "我的考勤",
        titleStrle: "height:0px;border-bottom:0px"
    })*/
}
//我的履历
function myRecord() {
	layer.open({
        type: 2,
        maxmin:true,
        zIndex:1900,//layer默认19891014 	dialog默认值为1976
        content: '/modules/mypage/record/myRecordReadOnlyForm.html',
        area: ["80%", "80%"],
        title: false
    });
}

// 刷新待办、已办区域
function refreshPage() {
	//alert("djdj")
    getWorkFlowData("yiban");
    getWorkFlowData("daiban");
}
/**
 * 查询待办、已办列表
 */
function getWorkFlowData(data) {
    var url = "";
    if (data == "daiban") {
        //url = "/workflow/daiblist?pageSize=6&pageNumber=1";
        url = "/system/waitdo/getWaitdoList?pageSize=6&pageNumber=1";
    } else if (data == "yiban") {
        // url = "/workflow/getHadDone?pageSize=6&pageNumber=1";
        url = "/workflow/getFlowReadList?pageSize=6&pageNumber=1";
    }
    $.ajax({
        type: "get",
        url: url,
        cache: false,
        dataType: "json",
        success: function (res) {
            if (res.flag == "1") {
                var html = "<tr><td width='61%'></td><td width='18%'></td><td width='21%'></td></tr>";
                $("#" + data + " table tbody").empty().append(html);
                var tdWidth = $("#" + data + " table tbody tr td:first").width();
                //console.log(tdWidth);
                if (res.data.rows.length > 0) {
                    $.each(res.data.rows, function (i, n) {
                        var title = n.title;
                        if ((n.title.length + n.workFlowName.length + 2) * 14 >= (tdWidth - 70)) {
                            var size = parseInt((tdWidth - 70 - (14 * n.workFlowName.length)) / 14, 10);
                            title = n.title.substring(0, size) + "...";
                        }
                        var newImg = "", color = "";
                        if(data == "daiban") {
                            if ($.trim(n.receiveTime.substring(0, 10)) == $.trim(curDay)) {
                                newImg = "<img src='/static/images/ico_new.png'/>";
                                color = "red";
                            }
                        }
                        var type = "";
                        if (n.waitdoType && n.waitdoType == "2") {
                            type = n.waitdoType;
                        }

                        html += "<tr>";
                        html += "	<td width='61%'><span class='" + color + "'>【" + n.workFlowName
                            + "】<a href='javascript:void(0);' title='" + n.title + "' onclick='openWorkflow(\""
                            + data + "\",\"" + n.url + "\",\"" + n.recordid + "\",\"" + n.workitemid + "\",\""
                            + n.fileTypeId + "\",\"" + n.workflowid + "\",\"" + n.workFlowName + "\",\"" + n.attr + "\",\""
                            + n.attr1 + "\",\"" + n.attr2 + "\",\"" + type + "\");'>"
                            + title + "</a></span>" + newImg
                            + "</td>";
                        if (data == "daiban") {
                            html += "	<td width='18%' class='text-center'>" + n.preUserName + "</td>";
                            html += "	<td width='21%' class='time'>" + n.receiveTime.substring(0, 16) + "</td>";
                        } else {
                            html += "	<td width='18%' class='text-center'>" + n.username + "</td>";
                            html += "	<td width='21%' class='time'>" + n.readTime.substring(0, 16) + "</td>";
                        }
                        html += "</tr>";
                    })
                } else {
                    html = "<li><span style='color:red;' color='red' class='glyphicon glyphicon-exclamation-sign'> 暂无数据！</span></li>";
                }
                $("#" + data + " table tbody").empty().append(html);
            } else {
                $("#" + data + " table tbody").empty().append("<li><span style='color:red;' color='red' class='glyphicon glyphicon-exclamation-sign'> 获取数据失败！</span></li>");
            }
        },
        error: function () {
            $("#" + data + " table tbody").empty().append("<li><span style='color:red;' color='red' class='glyphicon glyphicon-exclamation-sign'> 获取数据失败！</span></li>");
        }
    })
}

/**
 * 打开待办、已办事项
 * 
 * @param url
 * @param recordId
 * @param workItemId
 * @param fileTypeId
 * @param workflowId
 * @param workFlowName
 */
function openWorkflow(data, url, recordId, workItemId, fileTypeId, workflowId, workFlowName, attr, attr1, attr2, type) {
    var oper = "";
    if (data == "daiban") {
        oper = "EDIT";
    } else if (data == "yiban") {
        oper = "VIEW";
    }
    if (url.indexOf("?") > 0) {
        url = url + '&id=' + recordId + "&fileTypeId=" + fileTypeId + "&workFlowId=" + workflowId + "&oper=" + oper + "&attr=" + attr + "&attr1=" + attr1 + "&attr2=" + attr2;
    } else {
        url = url + '?id=' + recordId + "&fileTypeId=" + fileTypeId + "&workFlowId=" + workflowId + "&oper=" + oper + "&attr=" + attr + "&attr1=" + attr1 + "&attr2=" + attr2;
    }
    if (type == "2") {
        url += "&workItemId1=" + workItemId;
    } else {
        url += "&workItemId=" + workItemId;
    }
    console.log(url);
    if (getcookie("deptSize") == "1"){//如果是一人多岗,判断当前岗位是否可以办理待办
        $.ajax({
            type:"get",
            url:"/sysWaitNoflowController/getOpenDaiBanQx?workItemId="+workItemId,
            dataType:"json",
            success:function (json) {
                var isHaveQx = json.isHaveQx;
                if(isHaveQx =="1"){
                    MyLayer.layerOpenUrl({
                        url: url,
                        title: workFlowName
                    });
                }else{
                    layer.msg("当前岗位没有操作权限，请切换岗位！",{icon:0});
                }
            },
            error:function () {
                layer.msg("网络连接错误，请重试！",{icon:2});
            }
        });
    }else{
        MyLayer.layerOpenUrl({
            url: url,
            title: workFlowName
        });
    }

}

/**
 * 获取未读消息数
 */
function getMsgCount() {
    $.ajax({
        type: "get",
        url: "/notity/msgcount",
        dataType: "json",
        success: function (res) {
            if (res.flag == "1") {
                $("#msgNum").html(res.msgCount);
                // 显示首页右上角消息提示数

                // 判断未读个数是否为三位数以下
                if(res.msgCount > 0 && res.msgCount<=99){
                    $('.icon-tip').show()
                    $('.icon-tip-text').html(res.msgCount)
                }else if(res.msgCount>99){   // 如果未读个数超过两位数就显示省略号
                    $('.icon-tip').show()
                    // var msgCount = String(res.msgCount).substr(0,2)+'..'
                    $('.icon-tip-text').html('···')
                }else {
                    $('.icon-tip').hide()
                    return
                }
            } else {
                $('.icon-tip').hide()
            }
        },
        error: function () {
            $("#msgNum").html("0");
            $('.icon-tip').hide()
        }
    })
}

/**
 * 获取消息列表
 */
function getMsgList() {
    $.ajax({
        type: "get",
        url: "/notity/list?pageSize=5&pageNumber=1&status=0",
        dataType: "json",
        success: function (res) {
            if (res.flag == "1") {
                var html = "";
                if (res.data.rows.length != 0) {
                    $.each(res.data.rows, function (i, msg) {
                        var title = "【" + msg.senderId + "】" + msg.subject;
                        if (title.length >= 30) {
                            title = title.substring(0, 30) + "...";
                        }
                        var sendTime = new Date(msg.sendTime).Format("yyyy-MM-dd hh:mm");
                        html += "<li>";
                        html += "<span class='date'>" + sendTime + "</span>";
                        if (msg.status == "1") {
                            html += "<img src='/static/images/ico_mail02.png' />&nbsp;&nbsp;";
                        } else {
                            html += "<img src='/static/images/ico_mail01.png' />&nbsp;&nbsp;";
                        }
                        html += "<a href='javascript:void(0);' onclick='openMsgContent(\"" + msg.id + "\",\"" + msg.senderId + "\",\"" + msg.msgUrl + "\",\"" + msg.subject + "\",\"" + msg.content + "\",\"" + sendTime + "\");'>" + title + "</a>";
                        html += "</li>";
                    })
                } else {
                    html = "<li><span style='color:red;' color='red' class='glyphicon glyphicon-exclamation-sign'> 暂无未读消息！</span></li>";
                }
                $("#msgList").empty().append(html);
            } else {
                $("#msgList").empty().append("<li><span style='color:red;' color='red' class='glyphicon glyphicon-exclamation-sign'> 获取消息失败！</span></li>");
            }
        },
        error: function () {
            $("#msgList").empty().append("<li><span style='color:red;' class='glyphicon glyphicon-exclamation-sign'></span> 获取消息失败！</li>");
        }
    })
}

/**
 * 打开消息内容页面
 */
function openMsgContent(msgId, senderId, msgUrl, subject, content, sendTime) {
    debugger;
    if(msgUrl && msgUrl !="") {
        var urlWorkItemId = msgUrl.substring(msgUrl.indexOf("workItemId")+11,msgUrl.length);
        var workItemId = urlWorkItemId.substring(0,urlWorkItemId.indexOf("&"));
        if (getcookie("deptSize") == "1") {//如果是一人多岗,判断当前岗位是否可以办理待办
            $.ajax({
                type: "get",
                url: "/sysWaitNoflowController/getOpenDaiBanQx?workItemId=" + workItemId,
                async:false,
                dataType: "json",
                success: function (json) {
                    var isHaveQx = json.isHaveQx;
                    if (isHaveQx != "1") {
                        layer.msg("当前岗位没有操作权限，请切换岗位！", {icon: 0});
                        return false;
                    }
                },
                error: function () {
                    layer.msg("网络连接错误，请重试！", {icon: 2});
                    return false;
                }
            });
        }
    }
    layer.open({
        id: "openMsg",
        type: 2,
        content: "/message/msgContent.html",
        area: ['800px', '300px'],
        title: ["【" + senderId + "】" + '消息', 'font-size:16px;font-weight:bold;'],
        success: function (layero, index) {
            // 通过iframe 操作
            var iframeId = $("#openMsg").find('iframe').attr('id')
            var obj = $(window.frames["" + iframeId + ""].document)

            $(obj).find('#msgId').val(msgId);
            $(obj).find('#msgUrl').val(msgUrl);
            $(obj).find('#sendTimeIn').val(sendTime);
            $(obj).find('#contentIn').val(content);
            $(obj).find('#subjectIn').val(subject);
            // 调用数据回显方法
            document.getElementById(iframeId).contentWindow.init();
            //修改消息状态,并刷新消息
            editMsgStatus(msgId);
        }
    })
}

/**
 * 修改消息状态，并刷新列表
 * @param id
 */
function editMsgStatus(id) {
    MessageUtil.editStatus({ id: id });
    //刷新未读消息个数
    getMsgCount();
    //刷新消息列表
    getMsgList();
}

/**
 * 打开消息详情
 * @param url
 * @param id 消息ID
 */
function showMsg(url, id, status) {
    if (url && url.indexOf("?") >= 0) {
        url += "&msgId=" + id;
    } else {
        url += "?msgId=" + id;
    }
    MyLayer.layerOpenUrl({
        url: url, title: "消息", success: function (layero, index) {
            //修改消息状态
            if (status == "0") {
                MessageUtil.editStatus({ id: id });
                //刷新未读消息个数
                getMsgCount();
                //刷新消息列表
                getMsgList();
            }
        }
    });
}

/**
 * 打开消息更多列表
 */
function getMoreMsg() {
    MyLayer.layerOpenUrl({
        url: "/message/msgMoreList.html", title: "消息列表",
        cancel: function (index, layero) {
            //刷新未读消息个数
            getMsgCount();
            //刷新消息列表
            getMsgList();
            layer.close(index);
        }
    });
}

/**
 * 获取邮件数
 */
function getMailCount() {
    $.ajax({
        type: "get",
        url: "/mypage/mail/getCount",
        dataType: "json",
        success: function (res) {
            console.log(res);
            if (res.flag == "1") {
                $("#unreadNum").html(res.unread);
                $("#readNum").html(res.read);
            } else {
                $("#unreadNum").html("0");
                $("#readNum").html("0");
            }
        },
        error: function () {
            $("#unreadNum").html("0");
            $("#readNum").html("0");
        }
    })
}

// 绑定工作日志的日期输入input
var dateLog1 = laydate.render({
    elem: '#dateLog1' // 指定元素
    , btns: []
    , done: function (value, date, endDate) {
        $("#dateLog").val(value)
        $("#dateLog1").empty();
        this.value = value;
        saveWorkLog("1");
    }
});
//查看工作日志更多的工作日志信息
function moreWorkLog() {
    MyLayer.layerOpenUrl({
        url: '/modules/mypage/worklog/myWorkLogList.html',
        title: "我的工作日志",
    })

}
// 给工作日志的工作内容绑定一个回车事件，一按回车就把日志保存到数据库中
$('#content').bind('keypress', function (event) {
    if (event.keyCode == "13") {
        saveWorkLog("0");
    }
});//
//工作日志的日志类别一改变，变检查日志内容和日期是否为空，都不为空则保存
/*function logTypeChange(){
	saveWorkLog("3")
}*/

// 保存工作日志内容
function saveWorkLog(arg) {
    var content = $('#content').val().trim();
    var dateLog = $('#dateLog').val()
    var logType = $('#logType').val()
    if (content == '') {
        if (arg == '1') {
            // 通过日期控件调用此方法；
            return;
        }
        layer.msg("请填入日志内容！", {
            icon: 0
        });
        //alert("请填入日志内容")
        return;
    }
    if (dateLog == '') {
        layer.msg("选择一个日期！", {
            icon: 0
        });
        return;
    }
    $.post("/mypage/worklog/saveForm", {
        content: content,
        dateLog: dateLog,
        logType: logType
    }, function (data) {
        if (data.id != '' && data.id != null) {
            // 保存成功
            $("#content").val("")
            layer.msg("保存成功！", {
                icon: 1
            });
            loadWorkLogData1();
        }
    });

}

//工作日志的删除
function deleteLog(id) {
    layer.confirm("确定要删除吗", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        del(id)
    }, function () {

    });


}
//删除该条工作日志
function del(id) {
    $.post("/mypage/worklog/delete", {
        id: id
    }, function (data) {
        if (data.flag == '1') {
            layer.msg("删除成功！", {
                icon: 1
            });
            loadWorkLogData1()
        } else {
            layer.msg("删除失败！", {
                icon: 0
            });
        }
    });
}
//修改工作日志
function updateLog(id) {
    MyLayer.layerOpenUrl({
        url: '/modules/mypage/worklog/workLogEditForm.html?id=' + id + "&oper=EDIT&from=1",
        title: "修改"
    });
}
// 工作日志的上报
function appear(id) {
    layer.confirm("确定要上报吗", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        shangBao(id)
    }, function () {
    });
}
function shangBao(id) {
    $.post("/mypage/worklog/shangBao", {
        id: id
    }, function (data) {
        if (data.flag == '1') {
            layer.msg("上报成功！", {
                icon: 1
            });
            loadWorkLogData1()
        } else {
            layer.msg("上报失败！", {
                icon: 0
            });
        }
    });

}
// 前一天菜谱
function getPreDat() {
    clearInterval(stspTimer)
    $("#menuDate").val(preDate($("#menuDate").val()));
    $("#diningMenuDate").empty().append(
        getNowFormatDatery($("#menuDate").val()) + " "
        + getWeek($("#menuDate").val()));
    $("#fuliqu").empty();
    //$("#erlouqu").empty();
    $("#tesequ").empty();
    //$("#waimaiqu").empty();
    getDiningMenuData($("#menuDate").val());
    $('[data-toggle="popover"]').popover({
        trigger: "hover"
    })
}
// 后一天菜谱
function getNextDat() {
    clearInterval(stspTimer)
    $("#menuDate").val(nextDate($("#menuDate").val()));
    $("#diningMenuDate").empty().append(
        getNowFormatDatery($("#menuDate").val()) + " "
        + getWeek($("#menuDate").val()));
    $("#fuliqu").empty();
    //$("#erlouqu").empty();
    $("#tesequ").empty();
    //$("#waimaiqu").empty();
    getDiningMenuData($("#menuDate").val());
    $('[data-toggle="popover"]').popover({
        trigger: "hover"
    })
}
// 获取食堂菜谱
function getDiningMenuData(date) {
    var datas = {
        "date": date
    };
    httpRequest(
        "get",
        "/diningMenu/getByDate",
        datas,
        function (data) {
            if (data.flag == "1") {
                //早餐
                var html = "";
                $("#fuliqu").empty().append(html);
                //早餐
                html += "<p style='text-align:left;'>" + data.data.welfare4 + "</p>";
                // 内容超出显示省略号
                // if (data.data.welfare4.replace(/\s+/g, "").length > 160) {
                //     html += "<p style='text-align:left;'>"
                //         + (data.data.welfare4.replace(/\s+/g, "")).substring(0, 160) + "...</p>";
                // } else {
                //     html += "<p style='text-align:left;'>" + data.data.welfare4 + "</p>";
                // }


                $("#fuliqu").empty().append(html);
                //特殊餐
                var html2 = "";
                $("#tesequ").empty().append(html2);
                html2 += "<p style='text-align:left;'>" + data.data.welfare5 + "</p>";
                // 内容超出显示省略号
                // if (data.data.welfare5.replace(/\s+/g, "").length > 160) {
                //     html2 += "<p style='text-align:left;'>"
                //         + (data.data.welfare5.replace(/\s+/g, "")).substring(0, 160) + "...</p>";
                // } else {
                //     html2 += "<p style='text-align:left;'>" + data.data.welfare5 + "</p>";
                // }
                /*//1楼
                if(data.data.specialDishes.replace(/\s+/g,"").length>40){
                    html2 += "<p style='height:40%;' data-toggle='popover' data-placement='bottom' data-content='"
                        + data.data.specialDishes
                        + "'><b class='blue'>一楼</b><br>"
                        + (data.data.specialDishes.replace(/\s+/g,"")).substring(0, 40) + "...</p>";
                }else{
                    html2 += "<p style='height:40%;'><b class='blue'>一楼</b><br>"+ data.data.specialDishes + "</p>";
                }
                //2楼
                if(data.data.specialDishes2.replace(/\s+/g,"").length>40){
                    html2 += "<p style='height:60%;' data-toggle='popover' data-placement='bottom' data-content='"
                        + data.data.specialDishes2
                        + "'><b class='blue'>二楼</b><br>"
                        + (data.data.specialDishes2.replace(/\s+/g,"")).substring(0, 40) + "...</p>";
                }else{
                    html2 += "<p style='height:60%;'><b class='blue'>二楼</b><br>"+ data.data.specialDishes2 + "</p>";
                }*/

                $("#tesequ").empty().append(html2);
                //二楼面食
                /*var html3 = "";
                $("#erlouqu").empty().append(html3);
                //二楼面食
                if(data.data.flour.replace(/\s+/g,"").length>160){
                    html3 += "<p data-toggle='popover' data-placement='bottom' data-content='"
                        + data.data.flour + "'>"
                        + (data.data.flour.replace(/\s+/g,"")).substring(0, 160) + "...</p>";
                }else{
                    html3 += "<p>"+ data.data.flour + "</p>";
                }
                $("#erlouqu").empty().append(html3);
                //晚外卖
                var html4 = "";
                $("#waimaiqu").empty().append(html4);
            	
                if(data.data.takeOut.replace(/\s+/g,"").length>160){
                    html4 += "<p data-toggle='popover' data-placement='bottom' data-content='"
                        + data.data.takeOut + "'>"
                        + (data.data.takeOut.replace(/\s+/g,"")).substring(0, 160) + "...</p>";
                }else{
                    html4 += "<p>"+ data.data.takeOut + "</p>";
                }
            	
                $("#waimaiqu").empty().append(html4);*/

                $("#tese").removeClass("active");
                $("#dinnerpage2").css({
                    "display": "none"
                });
                /*$("#erlou").removeClass("active");
                $("#dinnerpage3").css({
                    "display" : "none"
                });
                $("#waimai").removeClass("active");
                $("#dinnerpage4").css({
                    "display" : "none"
                });*/
                $("#fuli").addClass("active");
                $("#dinnerpage1").css({
                    "display": "block"
                });
            } else {
                $("#tese").removeClass("active");
                $("#dinnerpage2").css({
                    "display": "none"
                });
                /*$("#erlou").removeClass("active");
                $("#dinnerpage3").css({
                    "display" : "none"
                });
                $("#waimai").removeClass("active");
                $("#dinnerpage4").css({
                    "display" : "none"
                });*/
                $("#fuli").addClass("active");
                $("#dinnerpage1").css({
                    "display": "block"
                });
                $("#fuliqu").empty().append(
                    "<p><b class='blue'></b><br>" + data.data + "</p>");
                $('#tesequ').empty().append(
                    "<p><b class='blue'></b><br>" + data.data + "</p>");
            }
        });
}

/**
 * 待办已办更多列表
 */
function getMoreyYibanDaiban() {
    if ($("#daiban").css("display") == "block") {
        openWaitdoList();
    } else {
        openYibanList();
    }
}

/**
 * 打开待办列表
 */
function openWaitdoList(){
    MyLayer.layerOpenUrl({ url: "/modules/workflow/daibanMoreList.html", title: "待办列表" });
}

/**
 * 打开已办列表
 */
function openYibanList(){
    MyLayer.layerOpenUrl({ url: "/modules/workflow/yibanMoreList.html", title: "已办列表" });
}

/**
 * 获取最新发布信息
 */
function getInfoNoticeList() {
    $.ajax({
        type: "get",
        url: "/system/notice/getInfoNoticeList",
        dataType: "json",
        data: {
            pageSize: 6,
            pageNumber: 1
        },
        success: function (res) {
            console.log(res);
            var html = "";
            if (res.flag == "1") {
                var rows = res.data.rows;
                if (!$.isEmptyObject(rows)) {
                    $.each(rows, function (i, row) {
                        var title = row.title;
                        var newImg = "", color = "";
                        if ($.trim(row.time.substring(0, 10)) == $.trim(curDay)) {
                            newImg = "<img src='/static/images/ico_new.png'/>";
                            color = "style='color:red'";
                        }
                        html += "<li class='clearfix'>";
                        if (row.type == "1") {
                            html += "<a class='fl' " + color + " href='/gateway/infoDetail.html?columnCode=" + row.columnCode + "&id=" + row.id + "' target='_blank' title='" + title + "'>【" + row.columnName + "】" + title;
                        } else if (row.type == "2") {
                            html += "<a class='fl' " + color + " href='javascript:void(0);' onclick='openNotice(\"" + row.id + "\");' title='" + title + "'>【" + row.columnName + "】" + title;
                        }
                        html += "</a>";
                        html += newImg;
                        html += "<span class='date fr'>" + row.time.substring(0, 10) + "</span>";
                        html += "</li>";
                    })
                }
            } else {
                html += "<li>";
                html += "<span>暂无信息</span>";
                html += "</li>";
            }
            $("#infoNotice").append(html);
        },
        error: function () {
            html += "<li>";
            html += "<span>数据获取异常</span>";
            html += "</li>";
            $("#infoNotice").append(html);
        }
    })
}

/**
 * 获取消息提醒内容
 * @param params Object
 * @param params.key String  key为'xxfb'时，获取信息发布提醒内容，不传内容默认是通知公告
 * @example getRemind({ key: 'xxfb' })  // 获取信息发布提醒内容
 */
function getRemind(params) {
    var dataJson = {
        url:'/system/notice/getInfoNoticeList',
        pageSize:8,
        pageNumber:1,
        parent: $('#tabOne')
    };
    $.extend(dataJson,params);
    if (params && params.key){
    	//信息发布
        if (params.key == 'xxfb') {
            dataJson.url = '/system/notice/getInfoNoticeDocumentsList';
            dataJson.parent = $('#tabTwo');
        }
        //音视频
        if (params.key == 'ysp') {
            dataJson.url = '/video/content/getVideoContentNoticeList';
            dataJson.parent = $('#tabThree');
        }
    }
    $.ajax({
        url:dataJson.url,
        type:"get",
        data:{
            pageSize: dataJson.pageSize,
            pageNumber: dataJson.pageNumber
        },
        success:function (res) {
            var html = "";
            if (res.flag == "1") {
                var rows = res.data.rows;
                var parentDom = dataJson.parent;
                parentDom.find('.gr_index_block_ul').empty().append(html);;
                var tdWidth = 668;
                if (!$.isEmptyObject(rows)) {
                    parentDom.find('.num').text(rows.length);
                    $.each(rows,function (i,row) {
                        //王富康截取title
                        var title = "";//
                        if(row.type){
                            if ((row.title.length + row.columnName.length + 2) * 14 >= (tdWidth - 300)) {
                                var size = parseInt((tdWidth - 300 - (14 * row.columnName.length)) / 14, 10);
                                title = row.title.substring(0, size) + "...";
                            }else{
                                title = row.title;
                            }
                        }else{
                            if ((row.contentTitle.length + row.columeName.length + 2) * 14 >= (tdWidth - 300)) {
                                var size = parseInt((tdWidth - 300 - (14 * row.columeName.length)) / 14, 10);
                                title = row.contentTitle.substring(0, size) + "...";
                            }
                            else{
                                title = row.contentTitle;
                            }
                        }

                        var rowImg = "", color = "";
                        if ($.trim(row.time.substring(0, 10)) == $.trim(curDay)) {
                            rowImg = "<img src='/static/images/ico_new.png'/>";
                            color = "style='color:red'";
                        }else{
                            rowImg = '<img src="/static/images/ico_xinxi.png">'
                        }
                        if(row.type){
                        	if (row.type == "1") {
                                html+= '<li>'+ rowImg +'&nbsp;&nbsp;<a  '+ color +'   href="/gateway/infoDetail.html?columnCode='+ row.columnCode +'&id= '+ row.id +' "   target="_blank" title="'+ row.title +'">【'+ row.columnName +'】'+ title +'</a><span class="date">'+ row.time.substring(0, 10) +'</span></li>'
                            } else if (row.type == "2") {
                                html+= '<li>'+ rowImg +'&nbsp;&nbsp;<a  '+ color +'   href="javascript:void(0);"  onclick="openNotice(\''+ row.id +'\')"  title="'+ row.title +'">【'+ row.columnName +'】'+ title +'</a><span class="date">'+ row.time.substring(0, 10) +'</span></li>'
                            }
                        }else{
                        	//处理视频发布提醒内容逻辑
                        	//displayFile(id,fileUuid,fileFullName,title,videoId)
                        	html+= '<li>'+ rowImg +'&nbsp;&nbsp;<a  '+ color +'   href="javascript:void(0);"  onclick="displayFile(\''+ row.contentId +'\',\''+ row.firstVideoUuid +'\',\''+ row.firstVideoName +'\',\''+ row.contentTitle +'\',\''+ row.firstVideoId +'\')" title="'+ row.contentTitle +'">【'+ row.columeName +'】'+ title +'</a><span class="date">'+ row.time.substring(0, 10) +'</span></li>'
                        }
                    })
                }else{
                    html += "<li>";
                    html += "<span>暂无信息</span>";
                    html += "</li>";
                }
                parentDom.find('.gr_index_block_ul').empty().append(html);
            }

        },
        error:function () {
            
        }
    });
}

/**
 * 打开通知公告详情页
 */
function openNotice(id) {
    MyLayer.layerOpenUrl({
        url: '/modules/system/notice/noticeBackForm.html?id=' + id + "&oper=VIEW",
        title: "通知公告详情"
    })
}

//设置日历数据
function setData(year, month) {
    var year = year;
    var month = month;

    // 传参-1操作
    if (month == "01") {
        month = "0";
        //year = String(Number(year) - 1);
    } else if (Number(month) >= 2 && Number(month) <= 10) {
        month = "0" + (Number(month) - 1);
    } else {
        month = String(Number(month) - 1);
    }
    $("#table tr:gt(0)").remove();
    $.post("/mypage/workplan/getlist1", { year: year, month: month }, function (data) {
        spellData(data);
    });
}

// 初始化年下拉框
function initYearOptions() {
    $("#year").empty();
    var year = new Date().getFullYear();
    for (var i = 0; i < 10; ++i) {
        $('#year').append($('<option>' + (year+5 - i) + '年</option>'));
    }
}

// 拼凑要展示的数据
function spellData(data) {
	var color = {"0":"班","1":"休"}
    var curDate = getNowFormatDate();
    for (var i = 0; i < data.length; i++) {
        var trHTML = "<tr>"
        for (var j = 0; j < data[i].length; j++) {
        
        	
            var type;
            //alert(data[i][j].fullDay+"---"+data[i][j].validate)
            if (!data[i][j].validate) {
                type = "<input type='text' value=" + data[i][j].isFinish + " style='display:none'></input> "

                if (data[i][j].hasData && data[i][j].isFinish == '0') {
                    //工作计划

                    trHTML += "<td onclick='check(this)' style='cursor: pointer;position: relative;'> <span class='none hasPlan'>"+ data[i][j].day + type +"<span class='redOfPlan'></span></span> <input type='hidden' value=" + data[i][j].fullDay + " />"
                    if(data[i][j].isHoliday && data[i][j].isHoliday=='1'){
                    	//休息日
                    	 trHTML += "<span class='xiu-icon'><b>休</b></span>"
                    }else if(data[i][j].isHoliday && data[i][j].isHoliday=='0'){
                    	//工作日
                   	 	trHTML += "<span class='ban-icon'><b>班</b></span>"

                    }
                    trHTML += "</td>"
                } else if (data[i][j].hasData && data[i][j].isFinish == '1') {
                    //工作日志
                    trHTML += "<td onclick='check(this)' style='cursor: pointer;position: relative;'><span class='none hasLog'>"+ data[i][j].day + type +"</span> <input type='hidden' value=" + data[i][j].fullDay + " />"
                    if(data[i][j].isHoliday && data[i][j].isHoliday=='1'){
                    	//休息日
                    	 trHTML += "<span class='xiu-icon'><b>休</b></span>"
                    }else if(data[i][j].isHoliday && data[i][j].isHoliday=='0'){
                    	//工作日
                   	 	trHTML += "<span class='ban-icon'><b>班</b></span>"

                    }
                    trHTML += "</td>"
                }else if (data[i][j].hasData && data[i][j].isFinish == '2') {
                    //工作计划和日志
                    trHTML += "<td onclick='check(this)' style='cursor: pointer;position: relative;'><span class='none hasPlanLog'>"+ data[i][j].day + type +"<span class='redOfPlan'></span></span> <input type='hidden' value=" + data[i][j].fullDay + " />"
                    if(data[i][j].isHoliday && data[i][j].isHoliday=='1'){
                    	//休息日
                    	 trHTML += "<span class='xiu-icon'><b>休</b></span>"
                    }else if(data[i][j].isHoliday && data[i][j].isHoliday=='0'){
                    	//工作日
                   	 	trHTML += "<span class='ban-icon'><b>班</b></span>"

                    }
                    trHTML += "</td>"
                } else {//什么都没有
                    trHTML += "<td onclick='check(this)' class='' style='cursor: pointer;position: relative;'><span class='none'>"+ data[i][j].day + type +"</span><input type='hidden' value=" + data[i][j].fullDay + " />"
                    if(data[i][j].isHoliday && data[i][j].isHoliday=='1'){
                    	//休息日
                    	 trHTML += "<span class='xiu-icon'><b>休</b></span>"
                    }else if(data[i][j].isHoliday && data[i][j].isHoliday=='0'){
                    	//工作日
                   	 	trHTML += "<span class='ban-icon'><b>班</b></span>"

                    }
                    trHTML += "</td>"
                
                }
            } else {
                type = data[i][j].day + "<input type='text' value=" + data[i][j].isFinish + " style='display:none'></input> ";
                //alert(data[i][j].fullDay+".........."+data[i][j].isHoliday)
                if (data[i][j].hasData && data[i][j].isFinish == '0') {
                    //工作计划
                    trHTML += "<td onclick='check(this)' style='cursor: pointer;position: relative;'><span class='hasPlan'>"+ type +"<span class='redOfPlan'></span></span> <input type='hidden' value=" + data[i][j].fullDay + " />"
                    if(data[i][j].isHoliday && data[i][j].isHoliday=='1'){
                    	//休息日
                    	 trHTML += "<span class='xiu-icon'><b>休</b></span>"
                    }else if(data[i][j].isHoliday && data[i][j].isHoliday=='0'){
                    	//工作日
                   	 	trHTML += "<span class='ban-icon'><b>班</b></span>"

                    }
                    trHTML += "</td>"
                } else if (data[i][j].hasData && data[i][j].isFinish == '1') {
                    //工作日志
                    trHTML += "<td onclick='check(this)' style='cursor: pointer;position: relative;'><span class='hasLog'>"+ type +"</span> <input type='hidden' value=" + data[i][j].fullDay + " />"
                    if(data[i][j].isHoliday && data[i][j].isHoliday=='1'){
                    	//休息日
                    	 trHTML += "<span class='xiu-icon'><b>休</b></span>"
                    }else if(data[i][j].isHoliday && data[i][j].isHoliday=='0'){
                    	//工作日
                   	 	trHTML += "<span class='ban-icon'><b>班</b></span>"

                    }
                    trHTML += "</td>"
                } else if (data[i][j].hasData && data[i][j].isFinish == '2') {
                    //工作计划和日志
                    trHTML += "<td onclick='check(this)' style='cursor: pointer;position: relative;'><span class='hasPlanLog'>"+ type +"<span class='redOfPlan'></span></span> <input type='hidden' value=" + data[i][j].fullDay + " />"
                    if(data[i][j].isHoliday && data[i][j].isHoliday=='1'){
                    	//休息日
                    	 trHTML += "<span class='xiu-icon'><b>休</b></span>"
                    }else if(data[i][j].isHoliday && data[i][j].isHoliday=='0'){
                    	//工作日
                   	 	trHTML += "<span class='ban-icon'><b>班</b></span>"

                    }
                    trHTML += "</td>"
                
                }else {
                	//什么都没有
                    trHTML += "<td onclick='check(this)' class='' style='cursor: pointer;position: relative;' >" + type + "<input type='hidden' value=" + data[i][j].fullDay + " />"
                    if(data[i][j].isHoliday && data[i][j].isHoliday=='1'){
                    	//休息日
                    	 trHTML += "<span class='xiu-icon'><b>休</b></span>"
                    }else if(data[i][j].isHoliday && data[i][j].isHoliday=='0'){
                    	//工作日
                   	 	trHTML += "<span class='ban-icon'><b>班</b></span>"

                    }
                    trHTML += "</td>"
                }

            }

        }
        trHTML += "</tr>"
        //alert(trHTML)
        $("#table tbody").append(trHTML)
        index = i;

    }
    $("#workplantb").find('tr').find('td').each(function () {
        if ($(this).find("input[type='hidden']").val() == curDate) {
            console.log('找到今天了')
            console.log($(this));
            $(this).addClass('today');
        }
    });
}

//设置某个单元格被选中
function check(obj) {
    var datePlan = $(obj).find("input[type='hidden']").val();
    MyLayer.layerOpenUrl({ url: '/modules/mypage/workPlan/workPlanEditForm.html?datePlan=' + datePlan + "&oper=EDIT", title: "新增工作计划和日志" });
}

// 更新日历
function updateCalendar() {
    setData(formatYear(), formatMonth());
}

//增加一个月
function addMonth() {
    // var curDate = getCurrentDate("yyyy-MM");
    var curDate = curMonth;
    if ($('#month').val() === "12月") {
        if ($('#year').val() !== (curDate.split('-') + '年')) {
            $('#year').val(Number($('#year').val().split('年')[0]) + 1 + "年");
            $('#month').val("1月");
            setData(formatYear(), formatMonth());
        }
    } else {
        $('#month').val(Number($('#month').val().split('月')[0]) + 1 + "月");
        setData(formatYear(), formatMonth());
    }
}

//减少一个月
function subMonth() {
    // var curDate = getCurrentDate("yyyy-MM");
    var curDate = curMonth;
    if ($('#month').val() === "1月") {
        if ($('#year').val() !== (Number(curDate.split('-')) - 10 + '年')) {
            $('#year').val(Number($('#year').val().split('年')[0]) - 1 + "年");
            $('#month').val("12月");
            setData(formatYear(), formatMonth());
        }
    } else {
        $('#month').val(Number($('#month').val().split('月')[0]) - 1 + "月");
        setData(formatYear(), formatMonth());
    }
}

// 增加一年
function addYear() {
    // var curDate = getCurrentDate("yyyy");
    var curDate = curYear;
    var nextYear = parseInt(curYear)+1;
      if(Number($('#year').val().split('年')[0])!=nextYear){
    	  $('#year').val(Number($('#year').val().split('年')[0]) + 1 + "年");
          setData(formatYear(), formatMonth());
      }
       
    
}

// 减少一年
function subYear() {
    //var curDate = getCurrentDate("yyyy");
    var curDate = curYear;
    var tempYear = parseInt(curYear)-8;
    //alert(tempYear)
     if(Number($('#year').val().split('年')[0])!=tempYear){
        $('#year').val(Number($('#year').val().split('年')[0]) - 1 + "年");
        setData(formatYear(), formatMonth());
    }
}

// 格式化年
function formatYear() {
    console.log("格式化年", $('#year').val().split('年')[0]);
    return $('#year').val().split('年')[0];
}

// 格式化月
function formatMonth() {
    if ($('#month').val().substr(0, 1) == 1) {
        console.log('格式化月', $('#month').val().split('月')[0]);
        return $('#month').val().split('月')[0];
    } else {
        console.log('格式化月', ("0" + $('#month').val().split('月')[0]));
        return ("0" + $('#month').val().split('月')[0]);
    }

}

//选择今天
function toToday() {
    //var curDate = getCurrentDate("yyyy-MM");
    var curDate = curMonth;
    $('#year').val(curDate.split('-')[0] + '年');
    if (curDate.split('-')[1].substr(0, 1) == 0) {
        $('#month').val(curDate.split('-')[1].substr(1, 2) + '月');
    } else {
        $('#month').val(curDate.split('-')[1] + '月');
    }
    setData(curDate.split('-')[0], curDate.split('-')[1]);
}

//查看工作日志更多的工作日志信息
function moreWorkPlan() {
    MyLayer.layerOpenUrl({
        url: '/modules/mypage/workPlan/myWorkPlanList.html',
        title: "我的工作计划和日志",
    })

}



/**
 * 初始化首页我的信息面板tab鼠标事件
 * */
function InitMyInfoPanel(){
    // 首页点击我的面板箭头按钮显示更多板块
    $('.carousel__btn').click(function() {
        if ($('.More_Ttem').is(':hidden')) {
            $('.More_Ttem').show();
            $('.carousel__btn').removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
        }else{
            $('.More_Ttem').hide();
            $('.carousel__btn').removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
        }
    });
    // 首页下拉框 第二个字体居右
    var carouselItems = $('.carousel__item');
    for(var i=0; i<carouselItems.length; i++){
        if(i%2!=0){
            console.log( carouselItems.eq(i))
            carouselItems.eq(i).css('text-align','right')
        }
    }
    // 首页下拉框 鼠标放上去变色
    $('.More_Ttem > .carousel__item').each(function(){
        $(this).on('mouseover',function(){
            $(this).find('a').css('color','#0375af');
            $(this).find('span').css('color','#0375af');
        })
        $(this).on('mouseout',function(){
            $(this).find('a').css('color','#404040');
            $(this).find('span').css('color','#999');
        })
    });
}

/**
 * 初始化首页待办和已办事项Tab鼠标事件
 * */
function InitItemTab(){
    //待办事项和已办事项鼠标移入移出事件
    var tabLi = $('.gr_block_title_tab_ul').children('li');
    var isCickTab;
    var clickIndex=1;
    tabLi.each(function(index,item){  // 绑定Tab事件
        if(index+1!=tabLi.length){
            $(this).mouseenter(function(){  // 鼠标移入时将线的left为li的宽度*索引index的值
                isCickTab = false;
                $(this).css('color','#0073aa');
                $('.gr_block_title_tab_ul .bottom_line').css('left',(index-1)* $(this).outerWidth())
            });
            $(this).mouseleave(function(){  // 鼠标离开时将线移回到当前显示的tab下
                if (isCickTab) return;  // 判断是否点击了tab  如果点击了就不执行以下内容
                var activeLiLeft =  (clickIndex-1)* $(this).outerWidth();  // 计算当前显示的Tab的left值
                clickIndex !=index ? $(this).css('color','#000000') : '';
                $('.gr_block_title_tab_ul .bottom_line').css('left',activeLiLeft)  // 将线的left设置为activeLiLeft的值
            })
            $(this).click(function(){  // 用户点击时设置线的left为当前点击tab的位置
                isCickTab = true;  // 设置为已点击
                clickIndex = index;  // 存储当前显示tab的index
                tabLi.each(function(){
                    $(this).css('color','#000000');
                })
                $(this).css('color','#0073aa');
                $('.gr_block_title_tab_ul .bottom_line').css('left',(index-1)* $(this).outerWidth())   // 将线的left设置为当前点击tab的值
            })
        }
    })
}

//----------------------------------------------------------------------

function updateTime(id,url,workflowName,status){
    debugger;
    MyLayer.layerOpenUrl({url:url,title:workflowName});
    if(status==0){
        $.ajax({
            'url' : '/system/flowSend/updateByReadTime',//最好带/,表是绝对路径
            'type':'POST',
            'dataType':'json',
            'data' : {
                id:id
            }
        }).done(function( data, textStatus, jqXHR ) {
            if (data.flag == '1') {
                getFlowSendList("daiyue");//刷新
            }else{
                layer.msg(data.msg, { icon: 2 });
            }
        })


    }
}

/**
 * 获取待阅/已阅列表--参考 getWorkFlowData 方法
 * @param status 0待阅；1已阅
 */
function getFlowSendList(data) {
    var status=undefined;
    if (data == "daiyue") {
        status=0;
    } else if (data == "yiyue") {
        status=1;
    }
    if (status!=undefined){
        $.ajax({
            type: "post",
            url: "/system/flowSend/getSendList",
            dataType: "json",
            data: {
                pageSize: 6,
                pageNumber: 1,
                status:status
            },
            success: function (res) {
                //debugger;
                if (res.flag == "1") {
                    var html = "<tr><td width='61%'></td><td width='18%'></td><td width='21%'></td></tr>";
                    $("#" + data + " table tbody").empty().append(html);
                    var tdWidth = $("#" + data + " table tbody tr td:first").width();
                    //console.log(tdWidth);
                    if (res.data.rows.length > 0) {
                        $.each(res.data.rows, function (i, n) {
                            var title = n.title;
                            if ((n.title.length + n.workflowName.length + 2) * 14 >= (tdWidth - 70)) {
                                var size = parseInt((tdWidth - 70 - (14 * n.workflowName.length)) / 14, 10);
                                title = n.title.substring(0, size) + "...";
                            }
                            var newImg = "", color = "";
                            if ($.trim(n.sendTime.substring(0, 10)) == $.trim(curDay)) {
                                newImg = "<img src='/static/images/ico_new.png'/>";
                                color = "red";
                            }
                            html += "<tr>";
                            html += "	<td width='61%'><span class='" + color + "'>【" + n.workflowName
                                + "】<a href='javascript:void(0);' title='" + n.title + "' onclick='updateTime(\""+n.id+"\",\""+n.sendUrl+"\",\""+n.workflowName+"\","+status+")'>"
                                + title + "</a></span>" + newImg
                                + "</td>";
                            if (data == "daiyue") {
                                html += "	<td width='18%' class='text-center'>" + n.sendUserName + "</td>";
                                html += "	<td width='21%' class='time'>" + n.sendTime.substring(0, 16) + "</td>";
                            } else {
                                html += "	<td width='18%' class='text-center'>" + n.sendUserName + "</td>";
                                html += "	<td width='21%' class='time'>" + n.readTime.substring(0, 16) + "</td>";
                            }
                            html += "</tr>";
                        })
                    } else {
                        html = "<li><span style='color:red;' color='red' class='glyphicon glyphicon-exclamation-sign'> 暂无数据！</span></li>";
                    }
                    $("#" + data + " table tbody").empty().append(html);
                } else {
                    $("#" + data + " table tbody").empty().append("<li><span style='color:red;' color='red' class='glyphicon glyphicon-exclamation-sign'> 获取数据失败！</span></li>");
                }
            },
            error: function () {
                $("#" + data + " table tbody").empty().append("<li><span style='color:red;' color='red' class='glyphicon glyphicon-exclamation-sign'> 获取数据失败！</span></li>");
            }
        })
    }
}


/**
 * 初始化消息待阅,已阅
 */
$(".infoWorkFlow").click(function () {
    if ($(this).attr('id').indexOf("db_info") >= 0) {
        $("#yb_info,#info_data").removeClass("active");
        $("#yiyue,#infoData").css({
            "display": "none"
        });
        $("#db_info").addClass("active");
        $("#daiyue").css({
            "display": "block"
        });
        getFlowSendList("daiyue");
    } else if ($(this).attr('id').indexOf("yb_info") >= 0) {
        $("#db_info,#info_data").removeClass("active");
        $("#daiyue,#infoData").css({
            "display": "none"
        });
        $("#yb_info").addClass("active");
        $("#yiyue").css({
            "display": "block"
        });
        getFlowSendList("yiyue");
    }else if ($(this).attr('id').indexOf("info_data") >= 0) {
        $("#db_info,#yb_info").removeClass("active");
        $("#daiyue,#yiyue").css({
            "display": "none"
        });
        $("#info_data").addClass("active");
        $("#infoData").css({
            "display": "block"
        });
    }
})

/**
 * 初始化信息提醒点击事件
 */
$(".remindWorkFlow").click(function () {
    if ($(this).attr('id').indexOf("remind_data") >= 0) {
        $("#db_remind,#yb_remind").removeClass("active");
        $("#tabTwo,#tabThree").css({
            "display": "none"
        });
        $("#remind_data").addClass("active");
        $("#tabOne").css({
            "display": "block"
        });
        getRemind()
    } else if ($(this).attr('id').indexOf("db_remind") >= 0) {
        $("#remind_data,#yb_remind").removeClass("active");
        $("#tabOne,#tabThree").css({
            "display": "none"
        });
        $("#db_remind").addClass("active");
        $("#tabTwo").css({
            "display": "block"
        });
        getRemind({key:'xxfb'})
    }else if ($(this).attr('id').indexOf("yb_remind") >= 0) {
        $("#remind_data,#db_remind").removeClass("active");
        $("#tabOne,#tabTwo").css({
            "display": "none"
        });
        $("#yb_remind").addClass("active");
        $("#tabThree").css({
            "display": "block"
        });
        getRemind({key:'ysp'});
    }
})

/**
 *  初始化信息提醒面板--复制的InitItemTab方法
 * */
function InitRemindTab(){
    var tabLi = $('#xxtx-container .gr_block_info_tab_ul').children('.remindWorkFlow');
    var clickIndex=0;//记录被点击下标
    tabLi.each(function(index,item){  // 绑定Tab事件
        $(this).mouseenter(function(){  // 鼠标移入时将线的left为li的宽度*索引index的值
            $(this).css('color','#0073aa');
            $('#xxtx-container .gr_block_info_tab_ul .bottom_line').css('left',(index)* $(this).outerWidth())
        });
        $(this).mouseleave(function(){  // 鼠标离开时将线移回到当前显示的tab下
            if(clickIndex !=index){
                $(this).css('color','#000000');
            };
            $('#xxtx-container .gr_block_info_tab_ul .bottom_line').css('left',(clickIndex)* $(this).outerWidth()) ;
        })
        $(this).click(function(){  // 用户点击时设置线的left为当前点击tab的位置
            clickIndex = index;
            tabLi.each(function(){
                $(this).css('color','#000000');
            })
            $(this).css('color','#0073aa');
            $('#xxtx-container .gr_block_info_tab_ul .bottom_line').css('left',(index)* $(this).outerWidth())   // 将线的left设置为当前点击tab的值
        })

    })
}


/**
 * 初始化消息面板--复制的InitItemTab方法
 * */
function InitInfoTab(){
    var tabLi = $('#info-container .gr_block_info_tab_ul').children('.infoWorkFlow');
    var clickIndex=0;//记录被点击下标
    tabLi.each(function(index,item){  // 绑定Tab事件
        $(this).mouseenter(function(){  // 鼠标移入时将线的left为li的宽度*索引index的值
            $(this).css('color','#0073aa');
            $('#info-container .gr_block_info_tab_ul .bottom_line').css('left',(index)* $(this).outerWidth())
        });
        $(this).mouseleave(function(){  // 鼠标离开时将线移回到当前显示的tab下
            if(clickIndex !=index){
                $(this).css('color','#000000');
            };
            $('#info-container .gr_block_info_tab_ul .bottom_line').css('left',(clickIndex)* $(this).outerWidth()) ;
        })
        $(this).click(function(){  // 用户点击时设置线的left为当前点击tab的位置
            clickIndex = index;
            tabLi.each(function(){
                $(this).css('color','#000000');
            })
            $(this).css('color','#0073aa');
            $('#info-container .gr_block_info_tab_ul .bottom_line').css('left',(index)* $(this).outerWidth())   // 将线的left设置为当前点击tab的值
        })

    })
}

/**
 * 待办已办更多列表
 */
function getMoreyDYyue() {
    if ($("#daiyue").css("display") == "block") {
        openDaiyueList();
    } else if ($("#yiyue").css("display") == "block"){
        openYiyueList();
    }else{
        getMoreMsg();//即打开 msgMoreList.html 页面
    }
}
/**
 * 打开待阅
 */
function openDaiyueList(){
    MyLayer.layerOpenUrl({ url: "/modules/workflow/daiyueMoreList.html", title: "业务消息列表" });
}
/**
 * 打开已阅
 */
function openYiyueList(){
    MyLayer.layerOpenUrl({ url: "/modules/workflow/yiyueMoreList.html", title: "已阅列表" });
}

function orderingMeals() {
    var url = 'modules/mypage/wmgl/index/index.html'
    window.open(url,'_blank')
}

/**
 * 消息提醒更多列表
 */
function getMoreyRemind() {
    if ($("#tabOne").css("display") == "block") {   // 通知公告提醒更多
        window.open('http://'+location.host+'/gateway/index.html')
    } else if ($("#tabTwo").css("display") == "block"){  // 信息发布提醒更多
        window.open('http://'+location.host+'/gateway/index.html')
    }else{  // 音视频发布提醒
        window.open('http://'+location.host+'/modules/video/reception/videoHomePage.html?id=1027204')
    }
}

/**
 * 打开音视频发布内容，方法是从音视频门户粘过来的
 * @param id 内容id
 * @param fileUuid 视频在音视频系统中的uuid
 * @param fileFullName 视频名（含后缀）
 * @param title 内容名称
 * @param videoId 视频在业务系统中的id
 */
function displayFile(id,fileUuid,fileFullName,title,videoId){
	//alert(videoId)
	//判断当前用户是否可以再打开一个页面结束
		var getVideoUrl = Config.hBServerIp+"/api/v1/load_balancer/vod_play_url?AccessToken="+Config.AccessToken
			+"&OriginalFileUuid="+fileUuid+"&ServiceType=vod_http&FileExtName=.mp4";
	
		$.ajax({
			type:"GET",
			url:getVideoUrl,
			async: true,
			//dataType:"json",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success:function(data){
				/* //当前电脑不存在学习页面
				if(isRecord == "1"){
					setCookie("isLearning","isLearning");
				} */
				
				
				
				//获取播放地址并转码
				var videoUrl = encodeURIComponent(data[0].Url);
				//window.open("/modules/video/reception/displayVideoAndPdf/playVideo.html?videoUrl="+videoUrl+"&infoId="+id+"&infoName="+encodeURIComponent(title)+"&fileFullName="+encodeURIComponent(fileFullName));
				window.open("/modules/video/playVideo/index.html?videoUrl="+videoUrl+"&infoId="+id+"&infoName="+encodeURIComponent(title)+"&videoId="+videoId+"&fileFullName="+encodeURIComponent(fileFullName));
				//window.location="/modules/video/playVideo/index.html?videoUrl="+videoUrl+"&infoId="+id+"&infoName="+encodeURIComponent(title)+"&videoId="+videoId+"&fileFullName="+encodeURIComponent(fileFullName)
			},
			error:function(data){
				layer.msg("视频还未转码成功，稍后再试！", {icon: 0});
			}
		});

}