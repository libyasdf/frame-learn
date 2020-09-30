
//拖拽
var drag = {

    class_name : null,  //允许放置的容器
	permitDrag : false,	//是否允许移动标识

	_x : 0,             //节点x坐标
    _y : 0,			    //节点y坐标
    _left : 0,          //光标与节点坐标的距离
    _top : 0,           //光标与节点坐标的距离

    old_elm : null,     //拖拽原节点
    tmp_elm : null,     //跟随光标移动的临时节点
    new_elm : null,     //拖拽完成后添加的新节点

    //初始化
    init : function (className){

        //允许拖拽节点的父容器的classname(可按照需要，修改为id或其他)
        drag.class_name = className;

        //监听鼠标按下事件，动态绑定要拖拽的节点（因为节点可能是动态添加的）
        $('.' + drag.class_name).on('mousedown', '.ms-worker-list', function(event){
            //当在允许拖拽的节点上监听到点击事件，将标识设置为可以拖拽
            drag.permitDrag = true;
            //获取到拖拽的原节点对象
            drag.old_elm = $(this);
            //执行开始拖拽的操作
            drag.mousedown(event);
            return false;
        });

        //监听鼠标移动
        $(document).mousemove(function(event){
            //判断拖拽标识是否为允许，否则不进行操作
            if(!drag.permitDrag) return false;
            //执行移动的操作
            drag.mousemove(event);
            return false;
        });

        //监听鼠标放开
        $(document).mouseup(function(event){
            //判断拖拽标识是否为允许，否则不进行操作
            if(!drag.permitDrag) return false;
            //拖拽结束后恢复标识到初始状态
            drag.permitDrag = false;
            //执行拖拽结束后的操作
            drag.mouseup(event);
            return false;
        });

    },

	//按下鼠标 执行的操作
	mousedown : function (event){

		console.log('我被mousedown了');
        //1.克隆临时节点，跟随鼠标进行移动
        drag.tmp_elm = $(drag.old_elm).clone();

        //2.计算 节点 和 光标 的坐标
        drag._x = $(drag.old_elm).offset().left;
        drag._y = $(drag.old_elm).offset().top;

        var e = event || window.event;
        drag._left = e.pageX - drag._x;
        drag._top = e.pageY - drag._y;
        var old_elm_width = $(drag.old_elm).width();
        //3.修改克隆节点的坐标，实现跟随鼠标进行移动的效果
        $(drag.tmp_elm).find('.mswl-end').hide();
        $(drag.tmp_elm).find('.mswl-center p').hide();

        $(drag.tmp_elm).css({
            'position' : 'fixed',
            'background-color' : '#FF8C69',
            'left' : drag._x,
            'top' : drag._y,
            'width':old_elm_width/2,
        });

        //4.添加临时节点
        tmp = $(drag.old_elm).parent().append(drag.tmp_elm);
        drag.tmp_elm = $(tmp).find(drag.tmp_elm);
        $(drag.tmp_elm).css('cursor', 'move');

	},

	//移动鼠标 执行的操作
	mousemove : function (event){

		console.log('我被mousemove了');

        //2.计算坐标
        var e = event || window.event;
        var x = e.pageX - drag._left;
        var y = e.pageY - drag._top;
        var maxL = $(document).width() - $(drag.old_elm).outerWidth();
        var maxT = $(document).height() - $(drag.old_elm).outerHeight();
        //不允许超出浏览器范围
        x = x < 0 ? 0: x;
        x = x > maxL ? maxL: x;
        y = y < 0 ? 0: y;
        y = y > maxT ? maxT: y;

        //3.修改克隆节点的坐标
        $(drag.tmp_elm).css({
            'left' : x,
            'top' : y,
        });
        console.log($(drag.tmp_elm))
        //判断当前容器是否允许放置节点
        $.each($('.' + drag.class_name + ' .ms-selected'), function(index, value){

            //获取容器的坐标范围 (区域)
            var box_x = $(value).offset().left;     //容器左上角x坐标
            var box_y = $(value).offset().top;      //容器左上角y坐标
            var box_width = $(value).outerWidth();  //容器宽
            var box_height = $(value).outerHeight();//容器高

            //给可以放置的容器加背景色
            if(e.pageX > box_x && e.pageX < box_x-0+box_width && e.pageY > box_y && e.pageY < box_y-0+box_height){

                //判断是否不在原来的容器下（使用坐标进行判断：x、y任意一个坐标不等于原坐标，则表示不是原来的容器）
                if($(value).offset().left !== drag.old_elm.parent().offset().left
                || $(value).offset().top !== drag.old_elm.parent().offset().top){
                    console.log('进来了')
                    $(value).addClass('move');
                    $(value).attr('data-active','1');
                }
            }else{
                //恢复容器原背景色
                $(value).removeClass('move');
                $(value).attr('data-active','0');
            }

        });

	},

    //放开鼠标 执行的操作
    mouseup : function (event){

        console.log('我被mouseup了');
        //移除临时节点
        $(drag.tmp_elm).remove();

        //判断所在区域是否允许放置节点
        var e = event || window.event;

        $.each($('.' + drag.class_name + ' .ms-selected'), function(index, value){
            var newElm = $(value);

            //获取容器的坐标范围 (区域)
            var box_x = newElm.offset().left;     //容器左上角x坐标
            var box_y = newElm.offset().top;      //容器左上角y坐标
            var box_width = newElm.outerWidth();  //容器宽
            var box_height = newElm.outerHeight();//容器高
            
            //判断放开鼠标位置是否想允许放置的容器范围内
            if(e.pageX > box_x && e.pageX < box_x-0+box_width && e.pageY > box_y && e.pageY < box_y-0+box_height){

                //判断是否不在原来的容器下（使用坐标进行判断：x、y任意一个坐标不等于原坐标，则表示不是原来的容器）
                if(newElm.offset().left !== drag.old_elm.parent().offset().left
                || newElm.offset().top !== drag.old_elm.parent().offset().top){
                    var newOwnerid = newElm.attr('data-ownerid');
                    var newSeat = newElm.find('span').eq(0).html();
                    // //向目标容器添加节点并删除原节点
                    tmp = $(drag.old_elm);
                    // // 获取值
                    var seat = tmp.attr('data-seat');   // 座位
                    var seatid = tmp.attr('data-seatid');  // 座位id
                    var owner = tmp.find('.mswl-center h3').html();   // 人物名称
                    var ownerid = tmp.attr('data-ownerid');   // 人物id
                    var deptid = tmp.attr('data-owner-deptid');   // 部门id
                    var deptname = tmp.attr('data-owner-deptname');   // 部门名称
                    
                    // 判断这个人之前是否有其他座位，如果有就在上一个座位中把他的信息清空。
                    var nextSeat = $('.ms-selected[data-ownerid='+ ownerid +']');
                    if (nextSeat.length>0) {
                        console.log(nextSeat.find('span').eq(1))
                        nextSeat.find('span').eq(1).html('');
                        nextSeat.removeClass('left-arranged');
                        nextSeat.attr('data-ownerid','');
                        nextSeat.attr('data-owner','');
                        nextSeat.attr('data-owner-current','');
                        nextSeat.attr('data-ownerid-current','');
                        nextSeat.attr('data-owner-deptid','');
                        nextSeat.attr('data-owner-deptname','');
                    }
                    // 判断这个座位之前是否有人，如果有人就先把那个人的座位信息清除掉
                    if (newOwnerid) {
                        var oldElm = $('.ms-worker-list[data-ownerid='+ newOwnerid +']');
                        oldElm.find('.mswl-end span').html('');
                        oldElm.attr('data-seat','');
                        oldElm.attr('data-seatid','');
                        oldElm.attr('data-seat-current','');
                        oldElm.attr('data-seatid-current','');
                    }

                    // // 设置值
                    // console.error(drag.old_elm)
                    drag.old_elm.find('.mswl-end span').text(newSeat);
                    // drag.old_elm.find('.mswl-end span').html(newSeat);
                    // console.error(owner)
                    newElm.find('span').eq(1).html(owner);
                    newElm.addClass('left-arranged');
                    newElm.attr('data-seatid',seatid);
                    newElm.attr('data-seatid',seatid);
                    newElm.attr('data-owner',owner);
                    newElm.attr('data-ownerid',ownerid);
                    newElm.attr('data-owner-current',owner);
                    newElm.attr('data-ownerid-current',ownerid);
                    newElm.attr('data-owner-deptid',deptid);
                    newElm.attr('data-owner-deptname',deptname);
                    newElm.attr('data-active','1');

                    // $(drag.old_elm).remove();
                    //获取新添加节点的对象
                    drag.new_elm = $(value);
                }
            }
            //恢复容器原背景色
            $(value).removeClass('move')
            $(value).attr('data-active','0');
        });

    },

};
