/**
 *  依赖于jquery, 必须引入jquery
 *  @CheckElement 全选/反全选
 */
;(function($, win){
    /**
     * CheckAll
     * @selectId ：全选按钮的id
     * @childrnNames: 全选按钮操作的按钮name
     */
    function CheckAll(selectId, childrnNames) {
        this.selectId = selectId;
        this.childrnNames = childrnNames;
        // 调用初始化方法
        this.init();
    }

    CheckAll.prototype.init = function(){
        var that = this;
        // 父元素点击事件
        $('#'+ that.selectId).unbind('click').bind('click', function(){
            $('[name='+that.childrnNames+']').prop("checked", $(this).prop('checked'));
        })

        // 子元素点击事件
        $('[name='+that.childrnNames+']').unbind('click').bind('click', function(){
            if ($('[name='+that.childrnNames+']').length == $('[name='+that.childrnNames+']:checked').length) {
                $('#'+ that.selectId).prop('checked', true);
            } else {
                $('#'+ that.selectId).prop('checked', false);
            }
        })
    }

    /**
     * AddTr
     * @tableId : 表格的id
     * @bottom : 是向底部还是顶部插入
     * @isNumber： 是否拥有序号
     * @deleteFn： 操作的回调函数，比如删除
     * @isNumber: 是否有序号
     * @numName ：序号列的name
     * @numSort: 序号排列是 asc 或 desc
     */
    function AddTr(arg){
        this.tableId = arg.tableId;
        this.addId = arg.addId;
        this.deleteId = arg.deleteId;
        this.bottom = arg.bottom || 'false';
        this.isNumber = arg.isNumber || 'true';
        this.numName = arg.numName || '';
        this.numSort  = arg.numSort || 'asc';
        //this.subTableLineNum = ++arg.subTableLineNum;
        this.deleteFn = arg.deleteFn || function() {return true};
        this.addTrFn = arg.addTrFn || function() {return true};
        // 调用初始化
        this.init();
    }

    AddTr.prototype.init = function() {
        var that = this;
        // 增加一行
        $('#'+ that.addId).unbind('click').bind('click', function(){

            /*var _trObj = $('#'+ that.tableId).find('tbody').find('tr');
            // 获取第一行
            var tr = $(_trObj).eq(0).clone(true);*/
            //通过回调函数获取需要拼接的行元素
            var trCustom;
            // alert(that.subTableLineNum);
            if(that.addTrFn){
                trCustom = that.addTrFn();
                /* layer.alert(trCustom);
                 layer.msg(trCustom);*/
                console.log(trCustom);
            }



            // 获取当前有几行
            var len = 0;
            var _tds = null;
            if (that.bottom === 'true') {
                $('#'+ that.tableId).find('tbody').append(trCustom);
            } else {
                $('#'+ that.tableId).find('tbody').prepend(trCustom);
            }
            // 判断是否拥有序号
            if (that.isNumber === 'true') {
                _tds = $('#'+ that.tableId).find('td[name='+that.numName+']');
                // 是否是升序
                if (that.numSort === 'asc') {
                    $(_tds).each(function(i){
                        $(this).text(i+1)
                    })
                } else {
                    len = $(_tds).length;
                    $(_tds).each(function(i){
                        var _index = len - i;
                        $(this).text(_index);
                    })
                }
            }
            iniFileUpload();
        })
        // 删除选中的行
        $('#'+ that.deleteId).unbind('click').bind('click', function(){
            var flag = true;
            var _ids = [];
            var _affixIds = [];//附件的id
            var _objs = $('#'+ that.tableId).find('input:checked');
            var rows =  $('#'+ that.tableId).find('tr');
            if (_objs.length == 0) {
                layer.alert('请选择要删除的行！',{icon:0,title:'提示'})
                return false;
            }
            layer.confirm("确定删除吗？",{
                icon:3,
                title:'提示',
                btn:['确定','取消']
            },function (e) {
                layer.close(e);

                //加载动画
                index = layer.load(1,{shade: [0.5, '#393D49'],content: '请稍候',success: function(layero){
                        layero.find('.layui-layer-content').css({'padding-top':'50px','font-size':'16px','color':'#000000','width':'200px'});
                        setTimeout(function(){
                            // 获取要删除的id
                            _objs.each(function() {
                                var id = $(this).parent().parent('tr').attr('id');
                                var affixId = $(this).parent().parent('tr').find('a').attr("id");
                                if(id != undefined && id != "" && id != null)
                                    _ids.push(id);
                                if(affixId != undefined && affixId != "" && affixId != null)
                                    _affixIds.push(affixId);
                            })
                            if (flag) {
                                // 删除Dom 元素
                                _objs.each(function() {
                                    $(this).parent().parent('tr').remove();
                                })
                                if (that.isNumber) {
                                    var _tds = $('#'+ that.tableId).find('td[name='+that.numName+']');
                                    // 是否是升序
                                    if (that.numSort === 'asc') {
                                        $(_tds).each(function(i){
                                            $(this).text(i+1)
                                        })
                                    } else {
                                        len = $(_tds).length;
                                        $(_tds).each(function(i){
                                            var _index = len - i;
                                            $(this).text(_index);
                                        })
                                    }
                                }
                            }
                            if (that.deleteFn && _ids.length > 0) {
                                flag = that.deleteFn(_ids,_affixIds);
                            }
                            layer.msg("删除成功！", {icon: 1});
                        },1000)
                } });
            },function () {
            })
        })
    }

    function TopTitle(arg){
        this.tableId = arg.tableId;
        // 是否可以初始化
        this.initFlag = true;
        // 初始化
        this.init();
        // 绑定事件
        this.event();
    }

    TopTitle.prototype.init = function() {
        var that = this;
        var _offset_left = $('#'+ that.tableId).offset().left;
        var _width = $('#'+ that.tableId).width();
        // 为顶部悬浮设置样式
        $('.table__header-top-wrapper').css({
            'left': _offset_left,
            'width': _width
        })
    }

    TopTitle.prototype.event = function() {
        var that = this;
        var _soroll = $('#'+ that.tableId).offset().top;
        // 监听滚动条事件
        $(window).scroll(function () {
            if ($(window).scrollTop() > _soroll) {
                $('.table__header-top-wrapper').show()
            } else {
                $('.table__header-top-wrapper').hide()
            }
        })

        // 窗口大小变化时触发
        window.onresize = function(){
            if (that.initFlag) {
                that.initFlag = false;
                that.init();
                that.initFlag = true;
            }
        }
    }

    // 绑定到全局变量
    win.CheckAll = CheckAll;
    win.AddTr = AddTr;
    win.TopTitle = TopTitle;

})(jQuery, window);