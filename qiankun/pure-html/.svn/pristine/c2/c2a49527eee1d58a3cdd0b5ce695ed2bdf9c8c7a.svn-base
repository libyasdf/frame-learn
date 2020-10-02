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
     * @fn： 操作的回调函数，比如删除
     * @isNumber: 是否有序号
     * @numName ：序号列的name
     * @numAsc: 序号排列是 asc 或 desc
     */
    function AddTr(arg){
        this.tableId = arg.tableId;
        this.addId = arg.addId;
        this.deleteId = arg.deleteId;
        this.bottom = arg.bottom || 'false';
        this.fn = arg.fn || function() {return true};
        this.isNumber = arg.isNumber || 'true';
        this.numName = arg.numName || '';
        this.numAsc  = arg.numAsc || 'asc';
        // 调用初始化
        this.init();
    }

    AddTr.prototype.init = function() {
        var that = this;
        // 增加一行
        $('#'+ that.addId).unbind('click').bind('click', function(){
            var _trObj = $('#'+ that.tableId).find('tbody').find('tr');
            // 获取第一行
            var tr = $(_trObj).eq(0).clone(true);
            // 获取当前有几行
            var len = 0;
            var _tds = null;
            if (that.bottom === 'true') {
                $('#'+ that.tableId).find('tbody').append(tr);
            } else {
                $('#'+ that.tableId).find('tbody').prepend(tr);
            }
            // 判断是否拥有序号
            if (that.isNumber === 'true') {
                _tds = $('#'+ that.tableId).find('td[name='+that.numName+']');
                // 是否是升序
                if (that.numAsc === 'asc') {
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
        })
        // 删除选中的行
        $('#'+ that.deleteId).unbind('click').bind('click', function(){
            var flag = true;
            var _ids = [];
            var _objs = $('#'+ that.tableId).find('input:checked');
            if (_objs.length == 0) {
                alert('请选择要删除的行');
                return false;
            }
            // 获取要删除的id
            _objs.each(function() {
            	var id = $(this).parent().parent('tr').attr('id');
            	if(id!=undefined && id != "" && id!= null){
            		_ids.push($(this).parent().parent('tr').attr('id'));
            	}
            })
            if (that.fn && _ids!=undefined && _ids!="" && _ids!=null) {
                flag = that.fn(_ids);
            }
            if (flag) {
                // 删除Dom 元素
                 _objs.each(function() {
                    $(this).parent().parent('tr').remove();
                 })
                var _tds = $('#'+ that.tableId).find('td[name='+that.numName+']');
                // 是否是升序
                if (that.numAsc === 'asc') {
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