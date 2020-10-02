$(function () {
    var publishState = GetRequest().publishState;
    var meetingName = GetRequest().meetingName;
    var fankuiType = GetRequest().fankuiType;
    var backId = GetRequest().backId;
    var userid=GetRequest().userid;
   
    if(!userid){
    	$(".a").hide();
    }
    var userSelecte;
    var AllSeats;
    $('.info-tip').hide();

    if (publishState !== undefined && Number(publishState)) { // 已发布
        // 给画布绑定事件方法
        $(".seat-canvas").off("click");
        if (fankuiType == 1) {
            $('.seating-menu-content .disabled:eq(0)').hide();
        }
    } else { // 未发布
        disenableButtons();
        if (fankuiType == 1) {
            $('[data-target="#manualSortTip"]').hide();
        }

        console.log("未发布");

        // 初始化座位序号画布
        initSeatNumberSideCanvas();
        initSeatNumberBottomCanvas();

        // 给按钮绑定事件方法
        $('.seating-menu-content button:eq(0)').click(manualSortTip);
        $('.seating-menu-content button:eq(2)').click(reserveTip);
        $('.seating-menu-content button:eq(3)').click(cancelReserveTip);
        $('.seating-menu-content button:eq(4)').click(resetTip);
        $('.modal-dialog-tip [data-func="autoSortTip"]').click(autoSort);
        $('.modal-dialog-tip [data-func="manualSortTip"]').click(initManualSortPanel);
        $('.modal-dialog-tip [data-func="publishTip"]').click(publish);
        $('.modal-dialog-tip [data-func="reserveTip"]').click(reserve);
        $('.modal-dialog-tip [data-func="cancelReserveTip"]').click(cancelReserve);
        $('.modal-dialog-tip [data-func="resetTip"]').click(reset);
        $('.manual-sort-bottom button:eq(0)').click(manualSortSubmit);

        // 给画布绑定事件方法
        $(".seat-canvas").click(clickCanvas);
    }

    // 给画布绑定事件方法
    $(".seat-canvas").mousemove(changePointer);
    $(".seat-canvas").mousemove(mousemoveCanvas);
    $(".seat-canvas").mouseout(function () {
        $("body").css("cursor", "default");
        $('.info-tip').hide();
    });

    // 人员提示信息
    $(document).mousemove(function (event) {
        $('.info-tip').css({
            top: event.pageY + 20,
            left: event.pageX + 20
        });
    });

    // 初始化页面
    init();

    // 初始化页面
    function init() {
    	
    	
        $.ajax({
            url: "/zhbg/hygl/arrangeSeat/getList1",
            async: false,
            data: {
                meetingNoticeId: GetRequest().meetingNoticeId,
                attendDeptId: GetRequest().attendDeptId,
                userid:userid,
                time: new Date().getTime()
            },
            success: function (data) {
                for (var i = 0; i < data.length; ++i) {
                    for (var j = 0; j < data[i].length; ++j) {
                    	
                        loadData(data[i][j]);
                    }
                }

                console.log('init data', data);
                console.log('seatData', seatData);

                // 初始化座位画布
                initSeatCanvas("t1");
                initSeatCanvas("t2");
                initSeatCanvas("t3");

                // 清空选择框
                $('.selected-box .selected').remove();
                $('.selected-box').append($('<span class="selected">未选择座位</span>'))
            }
        });
    }

    /*
    ** 加载数据
    ** @seat    { Object }  座位数据
    */
    function loadData(seat) {
        var row = convertColToCanvas(seat.col).row;
        var col = seat.row - 1;
        var target = convertColToCanvas(seat.col).target;

        if (!seatData[target].seats[row]) { seatData[target].seats[row] = []; }

        seatData[target].seats[row][col] = {
            state: seat.state,
            owner: seat.owner,
            ownerid: seat.ownerid,
            id: seat.id,
            fankuiType: fankuiType,
            attendDeptName: seat.attendDeptName,
            seatNum: seat.seatNum
        };
    }

    /*
    ** 渲染画布
    ** @target  { string }  目标画布
    */
    function initSeatCanvas(target) {

        // 获取 canvas
        var canvas = $(".seat-canvas")[target.split("t")[1] - 1];

        if ($(canvas).length === 0) {
            return null;
        }

        canvas.width = seatData[target].width;
        canvas.height = seatData[target].height;
        var ctx = canvas.getContext("2d");

        // 初始化标题
        ctx.fillStyle = '#ccc';
        ctx.font = "bold 14px '字体','字体','微软雅黑','宋体'";
        ctx.textBaseline = 'hanging';
        ctx.fillText(seatData[target].text, seatData[target].textPosition.x, seatData[target].textPosition.y);

        var ss = {};

        for (var i = 0; i <= 5; ++i) {
            ss["ss" + i] = new Image();
            ss["ss" + i].src = "images/ss" + i + ".png";
        }

        ss.ss5.onload = function () {
            for (var i = 0; i < seatData[target].row; ++i) {
                for (var j = 0; j < seatData[target].col; ++j) {
                    var img = ss["ss" + seatData[target].seats[i][j].state];
                    ctx.drawImage(img, 0, 0, img.width, img.height, seatData[target].firstPadding.x + i * (seatData.seatSize.width + seatData[target].seatMargin.x), seatData[target].firstPadding.y + j * (seatData.seatSize.height + seatData[target].seatMargin.y), seatData.seatSize.width, seatData.seatSize.width);
                }
            }
        }
    }

    /* 
    ** 初始化左侧座位序号画布
    */
    function initSeatNumberSideCanvas() {

        var canvas = $('.seating-seatnumber-side')[0];

        // 获取 canvas
        canvas.width = seatData.s1.width;
        canvas.height = seatData.s1.height;
        var ctx = canvas.getContext("2d");

        // 设置字体样式
        ctx.fillStyle = '#fff';
        ctx.textAlign = "center";
        ctx.font = "bold 14px '微软雅黑'";
        ctx.textBaseline = 'middle';


        for (var i = 0; i < seatData.s1.length; ++i) {
            ctx.roundRect(seatData.s1.firstPadding.x, seatData.s1.firstPadding.y + i * seatData.s1.margin, seatData.seatSize.width, seatData.seatSize.height, 7, "#ccc", "fill");
            ctx.fillText(i + 1, seatData.s1.firstPadding.x + (seatData.seatSize.width / 2), seatData.s1.firstPadding.y + (seatData.seatSize.height / 2) + i * seatData.s1.margin);
        }
    }

    /* 
    ** 初始化底部座位序号画布
    */
    function initSeatNumberBottomCanvas() {

        var canvas = $('.seating-seatnumber-bottom')[0];

        // 获取 canvas
        canvas.width = seatData.s2.width;
        canvas.height = seatData.s2.height;
        var ctx = canvas.getContext("2d");

        // 设置字体样式
        ctx.fillStyle = '#fff';
        ctx.textAlign = "center";
        ctx.font = "bold 14px '微软雅黑'";
        ctx.textBaseline = 'middle';

        var count = 27;
        for (var i = 1; i <= 3; ++i) {
            for (var j = 0; j < seatData.s2["p" + i].length; ++j) {
                ctx.roundRect(seatData.s2["p" + i].firstPadding.x + j * seatData.s2.margin, seatData.s2["p" + i].firstPadding.y, seatData.seatSize.width, seatData.seatSize.height, 7, "#ccc", "fill");
                ctx.fillText(count, seatData.s2["p" + i].firstPadding.x + (seatData.seatSize.width / 2) + j * seatData.s2.margin, seatData.s2["p" + i].firstPadding.y + (seatData.seatSize.height / 2));
                if (count % 2 && count !== 1) {
                    count -= 2;
                } else if (count === 1) {
                    count++;
                } else {
                    count += 2;
                }
            }
        }
    }

    /*
    ** 鼠标在座位上划过时改变指针样式
    **  @event event对象
    */
    function changePointer(event) {

        var target = getCanvasIndex(event);

        if (isOnSeat(event.offsetX, event.offsetY, target)) {
            $("body").css("cursor", "pointer");
        } else {
            $("body").css("cursor", "default");
        }
    }

    /*
    ** 点击画布的回调函数
    ** @event event对象
    */
    function clickCanvas(event) {

        var target = getCanvasIndex(event);

        if (isOnSeat(event.offsetX, event.offsetY, target)) {
            var position = getSeatPosition(event.offsetX, event.offsetY, target);
            var state = seatData[target].seats[position.row - 1][position.col - 1].state;
            console.log(state % 2);
            if (state % 2) {
                state--;
            } else {
                state++;
            }

            addCheckedInfo(position, state, target);
            checkSeat(position, state, target);

        } else {
            console.log("没有点中座位");
        }
    }

    /**
    * 滑过画布的回调函数
    * @event event对象
    */
    function mousemoveCanvas(event) {
        var target = getCanvasIndex(event);

        if (isOnSeat(event.offsetX, event.offsetY, target)) {
            $('.info-tip').show();
            var position = getSeatPosition(event.offsetX, event.offsetY, target);
            if (seatData[target].seats[position.row - 1][position.col - 1].fankuiType == 1) {
                if (seatData[target].seats[position.row - 1][position.col - 1].attendDeptName) {
                    $('.info-tip p:eq(0)').html(seatData[target].seats[position.row - 1][position.col - 1].attendDeptName + seatData[target].seats[position.row - 1][position.col - 1].seatNum);
                }
            } else {
                $('.info-tip p:eq(0)').html(seatData[target].seats[position.row - 1][position.col - 1].owner);
            }

            $('.info-tip p:eq(1)').html(position.col + '排' + convertColToAll(position.row - 1, target) + '座');
        } else {
            $('.info-tip').hide();
        }
    }

    /*
    ** 判断鼠标是否在座位上
    ** @x       { Number }      鼠标在画布中的横坐标
    ** @y       { Number }      鼠标在画布中的纵坐标
    ** @target  { String }      目标画布
    ** @return  { Boolean }     true: 在座位上, false: 不在座位上
    */
    function isOnSeat(x, y, target) {
        for (var i = 0; i < seatData[target].row; ++i) {
            if (x >= seatData[target].firstPadding.x + i * (seatData.seatSize.width + seatData[target].seatMargin.x) && x <= (seatData[target].firstPadding.x + seatData.seatSize.width) + i * (seatData.seatSize.width + seatData[target].seatMargin.x)) {
                for (var j = 0; j < seatData[target].col; ++j) {
                    if (y >= seatData[target].firstPadding.y + j * (seatData.seatSize.height + seatData[target].seatMargin.y) && y <= (seatData[target].firstPadding.y + seatData.seatSize.height) + j * (seatData.seatSize.height + seatData[target].seatMargin.y)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /*
    ** 获取座位位置
    ** @x       { Number }      鼠标在画布中的横坐标
    ** @y       { Number }      鼠标在画布中的纵坐标
    ** @target  { String }      目标画布
    ** @return  { Object }     包含位置信息的对象
    */
    function getSeatPosition(x, y, target) {
        var row = 0, col = 0;
        if ((x - seatData[target].firstPadding.x) === 0) {
            row = 1;
        } else {
            row = (x - seatData[target].firstPadding.x) / (seatData.seatSize.width + seatData[target].seatMargin.x);
            if (Math.round(row) === row) {
                row = row++;
            } else {
                row = Math.ceil(row);
            }
        }

        if ((y - seatData[target].firstPadding.y) === 0) {
            col = 1;
        } else {
            col = (y - seatData[target].firstPadding.y) / (seatData.seatSize.height + seatData[target].seatMargin.y);
            if (Math.round(col) === col) {
                col = col++;
            } else {
                col = Math.ceil(col);
            }
        }

        return {
            row: row,
            col: col
        }
    }

    /*
    ** 判断座位状态, 修改数据中的座位的状态码, 重新渲染画布相应位置
    ** @position    { Object }      座位的行列信息
    ** @state       { Number }      座位的状态码
    ** @target      { String }      目标画布
    */
    function checkSeat(position, state, target) {

        // console.log(position, target, state);

        var canvas = $(".seat-canvas")[target.split("t")[1] - 1];
        var ctx = canvas.getContext("2d");

        // 修改数据中的座位的状态码
        seatData[target].seats[position.row - 1][position.col - 1].state = state;

        // 清除需要替换的区域
        ctx.clearRect(seatData[target].firstPadding.x + (position.row - 1) * (seatData.seatSize.width + seatData[target].seatMargin.x), seatData[target].firstPadding.y + (position.col - 1) * (seatData.seatSize.height + seatData[target].seatMargin.y), seatData.seatSize.width, seatData.seatSize.height);

        // 重新渲染
        var img = new Image();
        img.src = "images/ss" + state + ".png";
        img.onload = function () {
            ctx.drawImage(img, 0, 0, img.width, img.height, seatData[target].firstPadding.x + (position.row - 1) * (seatData.seatSize.width + seatData[target].seatMargin.x), seatData[target].firstPadding.y + (position.col - 1) * (seatData.seatSize.height + seatData[target].seatMargin.y), seatData.seatSize.width, seatData.seatSize.height);
        }
    }

    /*
    ** 显示选中的座位信息
    ** @position    { Object }      座位的行列信息
    ** @state       { Number }      座位的状态码
    ** @target      { String }      画布序号
    */
    function addCheckedInfo(position, state, target) {
        var $target = $(".selected-box");
        if (state % 2) { // 改为选中
            if ($target.find(".selected:eq(0)").html() === "未选择座位") {
                $target.find(".selected").remove();
            }
            $target.append('<span class="selected" data-select="' + (position.row - 1) + '-' + (position.col - 1) + '-' + target.split("t")[1] + '">' + position.col + '排' + convertColToAll(position.row - 1, target) + '座</span>');
        } else { // 取消选中
            $target.find('[data-select="' + (position.row - 1) + '-' + (position.col - 1) + '-' + target.split("t")[1] + '"]').remove();
            if (!$target.find(".selected").length) {
                $target.append('<span class="selected">未选择座位</span>');
            }
        }
    }

    /*
    ** 获取画布序号
    ** @event   { Object }      event对象
    ** @return  { String }      目标画布
    */
    function getCanvasIndex(event) {
        if ($(event.target).index() == 1) {
            return "t1";
        } else if ($(event.target).index() == 3) {
            return "t2";
        } else if ($(event.target).index() == 5) {
            return "t3";
        }

        return "";
    }

    /*
    ** 画布中的列数转换成总体列数
    ** @row     { Number }      画布中的列数
    ** @target  { String }      画布序号
    ** @return  { Number }      总体列数
    */
    function convertColToAll(row, target) {
        if (target === "t1") {
            return 27 - row * 2;
        } else if (target === "t2") {
            if (row >= 0 && row < 5) {
                return 11 - row * 2;
            } else if (row === 5) {
                return 1;
            } else {
                return 2 + (row - 6) * 2;
            }
        } else if (target === "t3") {
            return 12 + row * 2;
        }

        return null;
    }

    /*
    ** 总体列数转换成画布中的列数
    ** @col     { Number }      总体列数
    ** @return  { Object }      画布序号, 画布中的列数
    */
    function convertColToCanvas(col) {
        if (col % 2 && col >= 13 && col <= 27) {
            return {
                target: "t1",
                row: (27 - col) / 2
            }
        } else if (!(col % 2) && col >= 12 && col <= 26) {
            return {
                target: "t3",
                row: (col - 12) / 2
            }
        } else if (col >= 1 && col <= 11) {
            if (col % 2) {
                return {
                    target: "t2",
                    row: (11 - col) / 2
                }
            } else {
                return {
                    target: "t2",
                    row: (col - 2) / 2 + 6
                }
            }
        }

        return null;
    }

    /*
    ** 是否有座位被选中
    ** @return  { Object }  如果有元素被选中，返回一个包含位置和状态的对象，否则返回 null
    */
    function isSeatSelected() {

        var selected = $(".selected-box .selected");

        var selectedObj = {};
        selectedObj.isReserved = 0;
        selectedObj.length = 0;

        if (selected.length === 1 && selected.html() === "未选择座位") {
            console.log("未选择座位");
            return selectedObj;
        }

        for (var i = 0; i < selected.length; ++i, ++selectedObj.length) {
            var row = Number($(selected[i]).html().split("排")[0]);
            var col = convertColToCanvas(Number($(selected[i]).html().split("排")[1].split("座")[0])).row;
            var target = convertColToCanvas(Number($(selected[i]).html().split("排")[1].split("座")[0])).target;

            var state = seatData[target].seats[col][row - 1].state;

            var id = seatData[target].seats[col][row - 1].id;

            var position = {
                row: col + 1,
                col: row
            }

            if (state === 5) { selectedObj.isReserved = 1; }

            selectedObj[i] = { position: position, state: state, target: target, id: id };
        }

        return selectedObj;
    }

    /*
    ** 判断座位是否已经被占有
    ** @positionText { String }  位置描述
    ** @return       { Object }  若座位被占有，返回一个带有位置和占有者的对象，否则返回 null
    */
    function isSeatHasOwner(positionText) {
        var row = Number(positionText.split("排")[0]);
        var col = convertColToCanvas(Number(positionText.split("排")[1].split("座")[0])).row;
        var target = convertColToCanvas(Number(positionText.split("排")[1].split("座")[0])).target;

        var state = seatData[target].seats[col][row - 1].state;

        console.log(col, row - 1, target, state);
        if (state === 3) {
            return {
                position: {
                    row: col + 1,
                    col: row
                },
                target: target,
                owner: seatData[target].seats[col][row - 1].owner,
                ownerid: seatData[target].seats[col][row - 1].ownerid,
                id: seatData[target].seats[col][row - 1].id
            }
        }

        return {
            position: {
                row: col + 1,
                col: row
            },
            target: target,
            owner: "",
            ownerid: "",
            id: seatData[target].seats[col][row - 1].id
        };
    }

    /*
    ** 判断员工是否有座位
    ** @name    { String }  员工姓名
    ** @return  { Ojbect }  若有，返回座位的位置信息，否则返回 null
    */
    function isWorkerHasSeat(name) {
        for (var i = 1; i <= 3; ++i) {
            var target = "t" + i;
            for (var j = 0; j < seatData[target].seats.length; ++j) {
                for (var k = 0; k < seatData[target].seats[j].length; ++k) {
                    if (seatData[target].seats[j][k].state === 2 || seatData[target].seats[j][k].state === 3) {
                        if (name === seatData[target].seats[j][k].owner) {
                            return {
                                position: {
                                    row: j,
                                    col: k
                                },
                                target: target,
                                ownerid: seatData[target].seats[j][k].ownerid,
                                id: seatData[target].seats[j][k].id
                            };
                        }
                    }
                }
            }
        }

        return null;
    }

    /*
    ** 更新全局变量 seatData
    */
    function updateSeatData(updateObject) {
        seatData[updateObject.target].seats[updateObject.row][updateObject.col] = {
            id: updateObject.id,
            owner: updateObject.owner,
            ownerid: updateObject.ownerid
        }
    }

    /*
    ** 显示手动排座提示框
    ** 并根据当前选中的座位修改提示框的样式和提示信息
    */
    function manualSortTip() {

        // 获取 DOM 节点
        var tipDiv = $("#manualSortTip .modal-dialog-tip");
        var tipHeader = tipDiv.find(".modal-header");
        var tipBody = tipDiv.find(".modal-body");
        var tipFooter = tipDiv.find(".modal-footer");

        if (isSeatSelected().length) {
            tipHeader.removeClass("bg-danger").removeClass("text-danger");
            tipBody.find("span").html("您是否要对当前座位手动排座？");
            tipFooter.find(".hide").removeClass("hide");
        } else {
            tipHeader.addClass("bg-danger").addClass("text-danger");
            tipBody.find("span").html("您未选择座位！请选择后重试。");
            tipFooter.find('[data-func="manualSortTip"]').addClass("hide");
        }
    }

    /*
    ** 显示预留提示框
    ** 并根据当前选中的座位修改提示框的样式和提示信息
    */
    function reserveTip() {

        // 获取 DOM 节点
        var tipDiv = $("#reserveTip .modal-dialog-tip");
        var tipHeader = tipDiv.find(".modal-header");
        var tipBody = tipDiv.find(".modal-body");
        var tipFooter = tipDiv.find(".modal-footer");

        if (isSeatSelected().length) {
            tipHeader.removeClass("bg-danger").removeClass("text-danger");
            tipBody.find("span").html("您是否要预留当前座位？");
            tipFooter.find(".hide").removeClass("hide");
        } else {
            tipHeader.addClass("bg-danger").addClass("text-danger");
            tipBody.find("span").html("您未选择座位！请选择后重试。");
            tipFooter.find('[data-func="reserve"]').addClass("hide");
        }
    }

    /*
    ** 显示取消预留提示框
    ** 并根据当前选中的座位修改提示框的样式和提示信息
    */
    function cancelReserveTip() {

        // 获取 DOM 节点
        var tipDiv = $("#cancelReserveTip .modal-dialog-tip");
        var tipHeader = tipDiv.find(".modal-header");
        var tipBody = tipDiv.find(".modal-body");
        var tipFooter = tipDiv.find(".modal-footer");

        if (isSeatSelected().isReserved) {
            tipHeader.removeClass("bg-danger").removeClass("text-danger");
            tipBody.find("span").html("您是否要取消当前预留座位？");
            tipFooter.find(".hide").removeClass("hide");
        } else {
            tipHeader.addClass("bg-danger").addClass("text-danger");
            tipBody.find("span").html("您未选择预留的座位！请选择后重试。");
            tipFooter.find('[data-func="cancelReserve"]').addClass("hide");
        }
    }

    /*
    ** 显示重置提示框
    ** 并根据当前选中的座位修改提示框的样式和提示信息
    */
    function resetTip() {

        // 获取 DOM 节点
        var tipDiv = $("#resetTip .modal-dialog-tip");
        var tipHeader = tipDiv.find(".modal-header");
        var tipBody = tipDiv.find(".modal-body");
        var tipFooter = tipDiv.find(".modal-footer");
        tipHeader.addClass("bg-danger").addClass("text-danger");
        tipBody.find("span").html("您确定要重置所有座位吗？");
        if (isSeatSelected().length) {
            tipHeader.removeClass("bg-danger").removeClass("text-danger");
            tipBody.find("span").html("您是否要重置当前座位？");
        } else {
            tipHeader.addClass("bg-danger").addClass("text-danger");
            tipBody.find("span").html("您是否要重置所有座位？");
        }
    }

    /*
    ** 初始化手动排座面板
    */
    function initManualSortPanel() {

        var selected = $(".selected-box .selected");
        $('.manual-sort-left').remove();
        $('.manual-sort-header').after($('<div class="manual-sort-left"></div>'));
        $('.manual-sort-right').empty();

        $('.manual-sort-right').append($(''
            + '<div class="ms-search">'
            + '<input type="checkbox" value="部门领导" checked><span>部门领导</span>'
            + '<input type="checkbox" value="正处长" checked><span>正处长</span>'
            + '</div>'
            + '<ul class="attendees-list"></ul>'
        ));

        for (var i = 0; i < selected.length; ++i) {
            $('.manual-sort-left').append($('<div class="ms-selected" data-seatid="" data-active="0" data-owner="" data-ownerid="" data-owner-current="" data-ownerid-current=""><span>' + $(selected[i]).html() + '</span><br><span></span></div>'));

            var seatInfo = isSeatHasOwner($(selected[i]).html());
            $('.manual-sort-left .ms-selected:eq(' + i + ') span:eq(1)').html(seatInfo.owner);
            $('.manual-sort-left .ms-selected:eq(' + i + ')')
                .attr("data-owner", seatInfo.owner)
                .attr("data-ownerid", seatInfo.ownerid)
                .attr("data-owner-current", seatInfo.owner)
                .attr("data-ownerid-current", seatInfo.ownerid);
            if (seatInfo.id && seatInfo.id.length) $('.manual-sort-left .ms-selected:eq(' + i + ')').attr("data-seatid", seatInfo.id);
            if (seatInfo.owner && seatInfo.owner.length) $('.manual-sort-left .ms-selected:eq(' + i + ')').addClass("left-arranged");
        }

        initMCustomScrollbar();

        loadgetAllAttendees();
        $('.ms-search input[type=checkbox]').unbind();
        $('.ms-search input[type=checkbox]').change(loadgetAllAttendees);

        hiddenPersonnel();
    }

    /*
    ** 获取人员列表
    */
    function loadgetAllAttendees() {
        layer.load(2);

        $.ajax({
            url: "/zhbg/hygl/arrangeSeat/getAllAttendees",
            data: {
                time: new Date().getTime(),
                meetingNoticeId: GetRequest().meetingNoticeId,
                isJu: $('.manual-sort-right .ms-search input[type=checkbox]:eq(0)').is(':checked') ? 1 : 0,
                isChuZhang: $('.manual-sort-right .ms-search input[type=checkbox]:eq(1)').is(':checked') ? 1 : 0
            },
            success: function (data, message, res) {
                console.log('用户选择', $('.manual-sort-left .ms-selected:not(.inlist)'));
                console.log($('.manual-sort-left .ms-selected:not(.inlist)').length);
                console.error(data)
    
                // 获取选中的座位
                var selected = $(".selected-box .selected");
                var userSelected = [];
                for (var i = 0; i < $('.manual-sort-left .ms-selected:not(.inlist)').length; ++i) {
                    var seatInfo = isSeatHasOwner($(selected[i]).html());
                    for (var j = 0; j < data.length; ++j) {

                        // 如果左侧座位（用户选中非添加）有人占并且该占有者在人员列表中
                        if ($('.manual-sort-left .ms-selected:not(.inlist):eq(' + i + ') span:eq(1)').html() == data[j].userNameFull) {
                            userSelected.push($('.manual-sort-left .ms-selected:not(.inlist):eq(' + i + ')').clone());
                            break;
                        }



              
              
                       
                        // 如果占有者不在人员列表中或者其他情况，从全局变量中将数据拿回
                        if (j === data.length - 1) {
                           
                            

                            var $clone = $('.manual-sort-left .ms-selected:not(.inlist):eq(' + i + ')').clone();
                                    var clone = $('.manual-sort-left .ms-selected:not(.inlist):eq(' + i + ')')
                        //     console.error('打印输出内容...')
                            var row = parseInt(clone[0].innerText.split("排")[0]);
                            var col = parseInt(clone[0].innerText.split("排")[1]);
                            console.log('空座的位置')
                            console.log(row,col);  // 空座的位置
                            
                            $clone.find('span:eq(1)').html(seatInfo.owner);
                            $clone.attr("data-owner", seatInfo.owner)
                                .attr("data-ownerid", seatInfo.ownerid)
                                .attr("data-owner-current", seatInfo.owner)
                                .attr("data-ownerid-current", seatInfo.ownerid);
                            if (seatInfo.id && seatInfo.id.length) {
                                $clone.attr("data-seatid", seatInfo.id);
                            } else {
                                $clone.attr("data-seatid", "");
                            }
                            if (seatInfo.owner && seatInfo.owner.length) {
                                $clone.addClass("left-arranged");
                            } else {
                                $clone.removeClass("left-arranged");
                            }

                            // 修改切换checkbox时候的bug
                            // $clone.removeClass("left-arranged");
                            // $clone.find('span:eq(1)').html('');
                            // $clone.attr("data-seatid", '')
                                //   .attr('data-owner', '')
                                //   .attr('data-ownerid','')
                                //   .attr("data-owner-current", '')
                                //   .attr("data-ownerid-current", '');


                            userSelected.push($clone);
                        }

                
                    }
                }
 
                // 为了使用滚动条优化，不能用 empty() 方法，只能删除元素重新添加
                $('.manual-sort-left').remove();
                $('.manual-sort-header').after($('<div class="manual-sort-left"></div>'));
                $('.manual-sort-right .attendees-list').remove();
                $('.manual-sort-right').append($('<ul class="attendees-list"></ul>'))
                $('.manual-sort-right .sort-em').remove();
                $('.attendees-list').show();
                console.log('userSelected', userSelected);
                console.log('left', $('.manual-sort-left'));

                // 将保存下来的座位添加到容器中
                for (var i = 0; i < userSelected.length; ++i) {
                    $('.manual-sort-left').append(userSelected[i]);
                }
                layer.closeAll('loading');

                $('.ms-selected').unbind('click');
                $('.ms-worker-list').unbind('click');
                $('.manual-sort-bottom button:eq(0)').show();

                // 如果人员列表为空，把座位数据拿回
                if (!data.length) {
                    for (var i = 0; i < selected.length; ++i) {
                        $('.manual-sort-left').append($('<div class="ms-selected" data-seatid="" data-active="0" data-owner="" data-ownerid="" data-owner-current="" data-ownerid-current=""><span>' + $(selected[i]).html() + '</span><br><span></span></div>'));

                        var seatInfo = isSeatHasOwner($(selected[i]).html());
                        $('.manual-sort-left .ms-selected:eq(' + i + ') span:eq(1)').html(seatInfo.owner);
                        $('.manual-sort-left .ms-selected:eq(' + i + ')')
                            .attr("data-owner", seatInfo.owner)
                            .attr("data-ownerid", seatInfo.ownerid)
                            .attr("data-owner-current", seatInfo.owner)
                            .attr("data-ownerid-current", seatInfo.ownerid);
                        if (seatInfo.id && seatInfo.id.length) $('.manual-sort-left .ms-selected:eq(' + i + ')').attr("data-seatid", seatInfo.id);
                        if (seatInfo.owner && seatInfo.owner.length) $('.manual-sort-left .ms-selected:eq(' + i + ')').addClass("left-arranged");
                    }
                    $('.attendees-list').hide();
                    $('.manual-sort-right').append($('<div class="sort-em" style="height: 540px; line-height: 520px; text-align: center; font-size: 24px;">暂无可排座人员</div>'));
                    $('.manual-sort-bottom button:eq(0)').hide();
                    initMCustomScrollbar();
                    return;
                }

                // 循环人员列表
                for (var i = 0; i < data.length; ++i) {

                    // 如果这个人有座位（id有值）
                    if (data[i].id !== undefined && data[i].id) {
                        var canAppend = 1; // 标识符
                        var $arrangedSeat = null; // 已拍座的对象
                        for (var j = 0; j < $('.ms-selected').length; ++j) {

                            // 这个人有的座位已经渲染出来了，而且上面应该有他的名字，所以不能添加
                            if ((data[i].row + '排' + data[i].col + '座') === $('.ms-selected:eq(' + j + ') span:eq(0)').html()) {
                                canAppend = 0;
                            }

                            // 这个人的名字在左侧座位上
                            if (data[i].userNameFull === $('.ms-selected:eq(' + j + ') span:eq(1)').html()) {
                                canAppend = 0;
                                $arrangedSeat = $('.ms-selected:eq(' + j + ')');
                            }
                        }

                        if (canAppend) {
                            $('.manual-sort-left').append($(''
                                + '<div class="ms-selected left-arranged inlist" data-seatid="' + data[i].id + '" data-owner="' + data[i].userNameFull + '" data-ownerid="' + data[i].userId + '" '
                                + 'data-owner-current="' + data[i].userNameFull + '" data-ownerid-current="' + data[i].userId + '">'
                                + '<span>' + data[i].row + '排' + data[i].col + '座' + '</span>'
                                + '<br>'
                                + '<span>' + data[i].userNameFull + '</span>'
                                + '</div>')
                            );
                        }
                    }
                    if ($arrangedSeat) {
                        $('.manual-sort-right .attendees-list').append($(''
                            + '<div class="ms-worker-list" data-active="0" data-seat="" data-seatid="" data-ownerid="' + data[i].userId + '" data-seat-current=""  data-seatid-current="">'
                            + '<div class="mswl-center">'
                            + '<h3>' + data[i].userNameFull + '</h3>'
                            + '<p>' + data[i].userDeptName + '</p>'
                            + '</div>'
                            + '<div class="mswl-end">'
                            + '<span>' + $arrangedSeat.find('span:eq(0)').html() + '</span>'
                            + '</div>'
                            + '</div>'));
                        if (data[i].row && data[i].col) {
                            $('.manual-sort-right .ms-worker-list:eq(' + i + ')')
                                .attr("data-seat", $arrangedSeat.find('span:eq(0)').html())
                                .attr("data-seatid", $arrangedSeat.attr('data-seatid'))
                                .attr("data-seat-current", $arrangedSeat.find('span:eq(0)').html())
                                .attr("data-seatid-current", $arrangedSeat.attr('data-seatid'));
                        }
                        $arrangedSeat = null;
                    } else {
                        $('.manual-sort-right .attendees-list').append($(''
                            + '<div class="ms-worker-list" data-active="0" data-seat="" data-seatid="" data-ownerid="' + data[i].userId + '" data-seat-current=""  data-seatid-current="">'
                            + '<div class="mswl-center">'
                            + '<h3>' + data[i].userNameFull + '</h3>'
                            + '<p>' + data[i].userDeptName + '</p>'
                            + '</div>'
                            + '<div class="mswl-end">'
                            + '<span>' + ((Number(data[i].row) && Number(data[i].col)) ? (data[i].row + "排" + data[i].col + "座") : "") + '</span>'
                            + '</div>'
                            + '</div>'));
                        if (data[i].row && data[i].col) {
                            $('.manual-sort-right .ms-worker-list:eq(' + i + ')')
                                .attr("data-seat", data[i].row + '排' + data[i].col + '座')
                                .attr("data-seatid", data[i].id)
                                .attr("data-seat-current", data[i].row + '排' + data[i].col + '座')
                                .attr("data-seatid-current", data[i].id);
                        }
                    }
                }

           
            
   
                $('.ms-selected').click(manualSort);
                $('.ms-worker-list').click(manualSort);

                initMCustomScrollbar();
                hiddenPersonnel(1);

            },
            error: function () {
                layer.msg('获取人员列表失败了，请稍后再试！');
                layer.closeAll('loading');
            }
        });
    }

    /*
    ** 手动排座
    */
    function manualSort() {

        // 取消激活
        if ($(this).attr("data-active") == 1) {
            $(this).attr('data-active', 0)
                .removeClass("left-selected")
                .removeClass("right-selected");
            return;
        }

        // 左侧
        if ($(this).hasClass("ms-selected")) {

            // 将选中项激活，其他项不激活
            $('.ms-selected').attr("data-active", 0)
                .removeClass("left-selected");
            $(this).attr("data-active", 1)
                .addClass("left-selected");

            // 判断右侧是否有激活项
            if ($('.manual-sort-right [data-active="1"]').length) {

                for (var i = 0; i < $('.ms-worker-list').length; ++i) {

                    // 遍历人名，查找是否有人已经占有此座位
                    if ($('.ms-worker-list:eq(' + i + ') .mswl-end span:eq(0)').html() === $(this).find('span:eq(0)').html()) {

                        // 判断右侧占有此座位的人员原本是否有座位，若有，清空座位
                        $('.ms-worker-list:eq(' + i + ') .mswl-end span:eq(0)').html("");
                    }
                }

                for (var i = 0; i < $('.ms-selected').length; ++i) {

                    // 遍历座位，查找右侧激活项人员是否已经拥有座位
                    if ($('.ms-selected:eq(' + i + ') span:eq(1)').html() === $('.manual-sort-right [data-active="1"] .mswl-center h3').html()) {

                        // 判断被右侧激活项占用的座位原本是否有人占，若有，清空座位
                        $('.ms-selected:eq(' + i + ') span:eq(1)').html("");
                        $('.ms-selected:eq(' + i + ')')
                            .attr('data-owner-current', "")
                            .attr('data-ownerid-current', "")
                            .removeClass("left-arranged");
                    }
                }

                // 占座，并将所有项变为不激活状态
                $(this)
                    .addClass('left-arranged')
                    .removeClass("left-selected")
                    .attr("data-active", 0)
                    .attr('data-owner-current', $('.manual-sort-right [data-active="1"] h3').html())
                    .attr('data-ownerid-current', $('.manual-sort-right [data-active="1"]').attr('data-ownerid'));
                $(this).find('span:eq(1)').html($('.manual-sort-right [data-active="1"] .mswl-center h3').html());
                $('.manual-sort-right [data-active="1"] .mswl-end span:eq(0)').html($(this).find('span:eq(0)').html());
                $('.manual-sort-right [data-active="1"]')
                    .removeClass("right-selected")
                    .attr("data-active", 0);

                if ($(this).attr('data-owner').length) $(this).addClass('left-arranged');
            }
        } else {

            // 将选中项激活，其他项不激活
            $('.ms-worker-list').attr("data-active", 0)
                .removeClass("right-selected");
            $(this).attr("data-active", 1)
                .addClass("right-selected");

            // 判断左侧是否有激活项
            if ($('.manual-sort-left [data-active="1"]').length) {

                for (var i = 0; i < $('.ms-worker-list').length; ++i) {

                    // 遍历人名，查找是否有人已经占有该座位
                    if ($('.ms-worker-list:eq(' + i + ') .mswl-end span:eq(0)').html() == $('.manual-sort-left [data-active="1"] span:eq(0)').html()) {

                        // 判断该人员原本是否有座位，若有，清空座位
                        $('.ms-worker-list:eq(' + i + ') .mswl-end span:eq(0)').html("");
                    }
                }

                for (var i = 0; i < $('.ms-selected').length; ++i) {

                    // 遍历座位，查找右侧激活项人员是否已经占有其他座位
                    if ($('.ms-selected:eq(' + i + ') span:eq(1)').html() === $(this).find('.mswl-center h3').html()) {

                        // 判断该座位原本是否有人占，若有，清空座位
                        $('.ms-selected:eq(' + i + ') span:eq(1)').html("");
                        $('.ms-selected:eq(' + i + ')')
                            .attr('data-owner-current', "")
                            .attr('data-ownerid-current', "")
                            .removeClass("left-arranged");


                    }
                }

                // 占座，并将所有项变为不激活状态
                $(this).find('.mswl-end span:eq(0)').html($('.manual-sort-left [data-active="1"] span:eq(0)').html());
                $('.manual-sort-left [data-active="1"] span:eq(1)').html($(this).find('.mswl-center h3').html());
                $(this)
                    .attr("data-active", 0)
                    .removeClass("right-selected");
                $('.manual-sort-left [data-active="1"]')
                    .addClass("left-arranged")
                    .removeClass("left-selected")
                    .attr("data-active", 0)
                    .attr('data-owner-current', $(this).find('h3').html())
                    .attr('data-ownerid-current', $(this).attr('data-ownerid'));
            }
        }
    }

    /*
    ** 提交手动排序
    ** 重新渲染画布中对应位置
    */
    function manualSortSubmit() {
        layer.load(2);
        var data = {
            time: new Date().getTime(),
            meetingNoticeId: GetRequest().meetingNoticeId,
            fankuiType: GetRequest().fankuiType
        };
        var seatDataJson = [];
        var selected = $('.manual-sort-left .ms-selected');
        for (var i = 0; i < selected.length; ++i) {
            var row = $(selected[i]).find('span:eq(0)').html().split("排")[0];
            var col = convertColToCanvas(Number($(selected[i]).find('span:eq(0)').html().split("排")[1].split("座")[0])).row;
            var target = convertColToCanvas(Number($(selected[i]).find('span:eq(0)').html().split("排")[1].split("座")[0])).target;

            seatDataJson.push({
                row: $(selected[i]).find('span:eq(0)').html().split("排")[0],
                col: $(selected[i]).find('span:eq(0)').html().split("排")[1].split("座")[0],
                id: $(selected[i]).attr("data-seatid"),
                owner: $(selected[i]).attr("data-owner-current"),
                ownerid: $(selected[i]).attr("data-ownerid-current")
            });


            updateSeatData({
                row: col,
                col: row - 1,
                target: target,
                id: $(selected[i]).attr("data-seatid"),
                owner: $(selected[i]).attr("data-owner-current"),
                ownerid: $(selected[i]).attr("data-ownerid-current")
            });
        }
        data.seatDataJson = JSON.stringify(seatDataJson);
        console.log("手动排座提交：", seatDataJson);

        $.ajax({
            url: "/zhbg/hygl/arrangeSeat/manualSeatSave",
            data: data,
            success: function (data, message, res) {
                layer.closeAll('loading');
                if (Number(data.flag)) {
                    init();
                    opener.fresh();
                    layer.msg('排座成功！');
                } else {
                    layer.msg('排座失败了，请稍后再试！');
                    init();
                }
            },
            error: function (err) {
                layer.msg('提交失败了，请稍后再试！');
                layer.closeAll('loading');
                init();
                throw err;
            }
        })
    }

    /*
    ** 自动排座
    */
    function autoSort() {
        layer.load(2);
        console.log({
            meetingNoticeId: GetRequest().meetingNoticeId,
            fankuiType: GetRequest().fankuiType
        });
        $.ajax({
            url: "/zhbg/hygl/arrangeSeat/autoArrangeSeat",
            data: {
                time: new Date().getTime(),
                meetingNoticeId: GetRequest().meetingNoticeId,
                fankuiType: GetRequest().fankuiType
            },
            success: function (flag, message, res) {
                layer.closeAll('loading');
                if (Number(flag.flag)) {
                    init();
                    opener.fresh();
                    layer.msg('排座成功！');
                } else {
                    layer.msg('排座失败了，请稍后再试！');
                }

            },
            error: function (err) {
                layer.msg('排座失败了，请稍后再试！');
                layer.closeAll('loading');
                throw err;
            }
        })
    }

    /*
    ** 预留座位
    ** 将所有选择的座位变为预留的座位
    */
    function reserve() {
        layer.load(2);
        var selectedObj = isSeatSelected();
        var seatListJSON = [];

        for (var i = 0; i < selectedObj.length; ++i) {
            // var id = seatData[selectedObj[i].target].seats[selectedObj[i].position.col-1][selectedObj[i].position.row-1].id;
            // if(!id) id = "";
            seatListJSON.push({
                row: selectedObj[i].position.col,
                col: convertColToAll(selectedObj[i].position.row - 1, selectedObj[i].target),
                id: selectedObj[i].id
            });
        }

        console.log("预留座位", seatListJSON);

        $.ajax({
            url: "/zhbg/hygl/arrangeSeat/reserve",
            data: {
                time: new Date().getTime(),
                meetingNoticeId: GetRequest().meetingNoticeId,
                seatListJson: JSON.stringify(seatListJSON),
                state: 4,
                fankuiType: GetRequest().fankuiType
            },
            success: function (flag, message, res) {
                layer.closeAll('loading');
                if (flag.flag == 1) {
                    init();
                    layer.msg('预留座位成功！');
                }
            },
            error: function (err) {
                layer.msg('预留失败了，请稍后再试！');
                layer.closeAll('loading');
                throw err;
            }
        });

    }

    /*
    ** 取消预留
    ** 将预留的座位变为可选的座位，将其他的座位变为对应的未选中状态
    */
    function cancelReserve() {
        layer.load(2);
        var selectedObj = isSeatSelected();
        var seatListJSON = [];

        for (var i = 0; i < selectedObj.length; ++i) {
            var target = selectedObj[i].target;
            var row = selectedObj[i].position.row - 1;
            var col = selectedObj[i].position.col - 1;

            if (seatData[target].seats[row][col].state == 5) {
                seatListJSON.push({
                    row: selectedObj[i].position.col,
                    col: convertColToAll(selectedObj[i].position.row - 1, selectedObj[i].target)
                });
            }
        }

        var meetingNoticeId = GetRequest().meetingNoticeId;
        var data = {
            time: new Date().getTime(),
            meetingNoticeId: meetingNoticeId,
            seatListJson: JSON.stringify(seatListJSON),
            state: 0
        };

        console.log("data", data);

        $.ajax({
            url: "/zhbg/hygl/arrangeSeat/cancelReserve",
            data: data,
            success: function (flag, message, res) {
                layer.closeAll('loading');
                console.log("取消预留", "\n", "flag:", flag, "\n", "message:", message, "\n", "res:", res);
                if (flag.flag == "1") {

                    init();
                    layer.msg('取消预留座位成功！');
                }
            },
            error: function (err) {
                layer.msg('取消失败了，请稍后再试！');
                layer.closeAll('loading');
                throw err;
            }
        });
    }

    /*
    ** 重置
    ** 将所有选择的座位变为可选的座位，若未选择则将所有座位变为可选
    */
    function reset() {
        layer.load(2);
        var selectedObj = isSeatSelected();
        var data = {};

        if (selectedObj.length) {
            var seatListJSON = [];
            for (var i = 0; i < selectedObj.length; ++i) {
                seatListJSON.push({
                    row: selectedObj[i].position.col,
                    col: convertColToAll(selectedObj[i].position.row - 1, selectedObj[i].target)
                });
            }
            data = {
                flag: 1,
                meetingNoticeId: GetRequest().meetingNoticeId,
                seatListJson: JSON.stringify(seatListJSON),
                time: new Date().getTime()
            };
            $.ajax({
                url: "/zhbg/hygl/arrangeSeat/reset",
                data: data,
                success: function (flag, message, res) {
                    layer.closeAll('loading');
                    if (flag.flag == 1) {

                        init();
                        layer.msg('重置成功！');
                    }
                },
                error: function (err) {
                    layer.msg('重置失败了，请稍后再试！');
                    layer.closeAll('loading');
                    throw err;
                }
            });
        } else {
            data = {
                flag: 0,
                meetingNoticeId: GetRequest().meetingNoticeId,
                time: new Date().getTime()
            };
            $.ajax({
                url: "/zhbg/hygl/arrangeSeat/reset",
                data: data,
                success: function (flag, message, res) {
                    layer.closeAll('loading');
                    if (flag.flag == 1) {

                        init();
                        layer.msg('重置成功！');
                    }
                },
                error: function (err) {
                    layer.msg('重置失败了，请稍后再试！');
                    layer.closeAll('loading');
                    throw err;
                }
            });
        }
        console.log("重置", data);
    }

    /*
    ** 发布
    */
    function publish() {
        layer.load(2);
        $.ajax({
            url: "/zhbg/hygl/arrangeSeat/publish",
            data: {
                meetingNoticeId: GetRequest().meetingNoticeId,
                time: new Date().getTime()
            },
            success: function (data, message, res) {
                layer.closeAll('loading');
                if (data.flag == 1) {
                    opener.fresh();
                    disenableButtons(1);
                    $(".seat-canvas").off("click");
                    layer.msg('发布成功', {
                        time: 1000,
                    }, function () {
                        window.location.href = window.location.href.split('publishState=0')[0] + "publishState=1" + ((window.location.href.split('publishState=0')[1]) ? (window.location.href.split('publishState=0')[1]) : "");
                    });
                } else {
                    layer.msg('发布失败了，请稍后再试');
                }
            },
            error: function (err) {
                layer.closeAll('loading');
                layer.msg('发布失败了，请稍后再试');
                throw err;
            }
        });
    }

    // 禁用按钮
    /**
     * 是否显示禁用按钮
     * @params n  1 为显示不可点击按钮  其他值或不传值为显示可点击按钮
    **/
    function disenableButtons(n) {
        if(n==1){
            $('.seating-menu-content button').hide();
            $('.seating-menu-publish button').hide();
            $('.seating-menu-content .disabled').show();
            $('.seating-menu-publish .disabled').show();
        }else{
            $('.seating-menu-content button').show();
            $('.seating-menu-publish button').show();
            $('.seating-menu-content .disabled').hide()
            $('.seating-menu-publish .disabled').hide();
        }
       
    }

    // 初始化滚动条
    function initMCustomScrollbar() {
        $('.manual-sort-left').mCustomScrollbar({
            theme: 'dark-3',
            mouseWheelPixels: 300,
            scrollButtons: {
                enable: true
            }
        });
        $('.attendees-list').mCustomScrollbar({
            theme: 'minimal-dark',
            mouseWheelPixels: 300,
            scrollButtons: {
                enable: true
            }
        });
    }
    // 隐藏多余的人员座位
    function hiddenPersonnel(sign){
        console.log('进来了')
        console.log(sign)
        var workerList= $('.attendees-list .ms-worker-list'); // 筛选后的所有人员
        console.error(workerList)
        // 所有的座位
        if (sign==1) { // 存储所有用户选择的座位
            // console.log('存储所有用户选择的座位')
            AllSeats = $('.ms-selected');
            console.log(AllSeats)
            console.log(userSelecte)
            for(var i=0; i<AllSeats.length; i++){  // 遍历所有的座位
                AllSeats[i].style.display="none";   // 将所有座位全部隐藏
                 for(var j=0; j<userSelecte.length; j++){  // 遍历用户选择的座位
                    if(AllSeats[i].getAttribute("data-ownerid")===userSelecte[j].getAttribute('data-ownerid')){
                        AllSeats[i].style.display="block";  // 将用户选择的座位显示
                    }
                 }
            }
            for(var index=0; index<workerList.length; index++){
                for(var it=0; it<AllSeats.length; it++){
                    // console.log(workerList[index].dataset.ownerid,AllSeats[it].getAttribute('data-ownerid-current'))
                    if(workerList[index].dataset.ownerid == AllSeats[it].getAttribute('data-ownerid-current')){
                        console.log(workerList[index].getAttribute('data-seat') ,AllSeats[it].firstChild.innerText)
                        if(workerList[index].getAttribute('data-seat') != AllSeats[it].firstChild.innerText){
                            // AllSeats[it].classList.remove("left-arranged");
                            console.log(AllSeats[it].lastChild)
                            AllSeats[it].classList.remove('left-arranged');
                            AllSeats[it].lastChild.innerText=""
                        }
                    }
               
                }
            }
        } else {  // 存储用户选择的座位   
            userSelecte = $('.ms-selected');
            console.log(userSelecte)
        }
    }
});



/**
 * @param  { Number } x         起始横坐标
 * @param  { Number } y         起始纵坐标
 * @param  { Number } width     矩形宽度
 * @param  { Number } height    举行高度
 * @param  { Number } height    圆角半径
 * @param  { Number } radius    起始横坐标
 * @param  { Number } color     颜色
 * @param  { Number } type      填充/描边
 */
CanvasRenderingContext2D.prototype.roundRect = function (x, y, width, height, radius, color, type) {

    this.save();

    this.beginPath();
    this.moveTo(x, y + radius);
    this.lineTo(x, y + height - radius);
    this.quadraticCurveTo(x, y + height, x + radius, y + height);
    this.lineTo(x + width - radius, y + height);
    this.quadraticCurveTo(x + width, y + height, x + width, y + height - radius);
    this.lineTo(x + width, y + radius);
    this.quadraticCurveTo(x + width, y, x + width - radius, y);
    this.lineTo(x + radius, y);
    this.quadraticCurveTo(x, y, x, y + radius);
    this[type + 'Style'] = color || params.color;
    this.closePath();
    this[type]();

    this.restore();
}

