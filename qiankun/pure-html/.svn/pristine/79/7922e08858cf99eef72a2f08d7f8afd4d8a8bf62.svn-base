//layer参考：http://layer.layui.com/   http://www.layui.com/doc/modules/layer.html
//打开弹窗操作
var MyLayer = function () {

    /**
     * 打开弹窗
     * @param json
     */
    var layerOpenUrl = function (json) {
        /**
         *起草、修改等的弹窗的默认参数
         * @type {{url: string, title: string}}
         */
        var arg = {
            id: "",
    		url: "",
    		title: "",
    		width: "90%",
    		height: "90%",
    		zIndex:1900,
    		maxmin:true,	//可最大最小化
    		success:null,
    		cancel:null
        };

        var _arg = $.extend(arg, json);

        _arg.url = 'http://'+ window.location.host + '/'+ _arg.url;  // 解决新版本门户中请求url错误的问题,不影响旧版本。（杨奇新增）

        if (_arg.url.indexOf("?")>=0){
            _arg.url += "&" + new Date().getTime();
        }else {
            _arg.url += "?" + new Date().getTime();
        }
        layer.open({
            id:_arg.id,
            type: 2,
            maxmin:_arg.maxmin,
            zIndex:_arg.zIndex,//layer默认19891014 	dialog默认值为1976
            content: _arg.url,
            area: [_arg.width, _arg.height],
            title: [_arg.title, 'font-size:16px;font-weight:bold;'],
            success:_arg.success,
            cancel:_arg.cancel
        });
    };

    /**
     * 弹窗中的按钮关闭当前弹窗
     */
    var closeOpen = function () {
        if(window.name == "" || window.name == null){
            window.close();
        }else{
            //当你在iframe页面关闭自身时
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭
        }
    };

    /**
     * 删除按钮的事件
     */
    var deleteOpt = function (json) {
        /**
         * 删除确定框的默认参数
         * @type {{url: string, msg: string, confirmBtn: confirmBtn, cancelBtn: cancelBtn}}
         */
        var delArg = {
            url: ""
            , msg: "确定要删除吗？"
            , tableId: "table"
            , confirmBtn: function (json) { //确定按钮的事件
                //发送ajax请求来删除，传参url就是删除的地址
                //_delArg.url
                $.ajax({
                    url: json.url
                    , type: "GET"
                    , dataType: "json"
                    , success: function (result) {
                        var msg = "";
                        if (result.flag === "1") {
                            msg = '删除成功！';
                            if ($.trim(result.msg)) {
                                msg = result.msg;
                            }
                            layer.msg(msg, {icon: 1});
                            TableInit.refTable(json.tableId);
                            if(typeof(json.tableIds)!="undefined"){
                            	TableInit.refTable(json.tableIds);
                            }
                        } else {
                            msg = '删除失败，请重试！';
                            if ($.trim(result.msg)) {
                                msg = result.msg;
                            }
                            layer.msg(msg, {icon: 2});
                        }
                    }
                    , error: function () {
                        layer.msg('删除失败，请重试！', {icon: 2});
                    }
                })
                // layer.msg('删除成功', {icon: 1, time: 1000});
                // layer.msg('删除失败', {icon: 0,time:1000});
            }
            , cancelBtn: function () { //取消按钮的事件
                // layer.msg('也可以这样', {
                //     time: 20000, //20s后自动关闭
                //     btn: ['明白了', '知道了']
                // });
            }
        };

        var _delArg = $.extend(delArg, json);
        //询问框
        layer.confirm(_delArg.msg, {
            btn: ['确定', '取消'] //按钮
        }, function () {
            _delArg.confirmBtn(_delArg)
        }, _delArg.cancelBtn);
    };

    /**
     * 弹窗中的按钮关闭当前弹窗的父窗口
     */
    var closeOpenParent = function () {
        var index = parent.parent.layer.getFrameIndex(window.name);
        parent.parent.layer.close(index);
    };

    return {
        layerOpenUrl: function (argJson) {
            layerOpenUrl(argJson);
        },
        closeOpen: function () {
            closeOpen();
        },
        closeOpenParent: function () {
            closeOpenParent();
        },
        deleteOpt: function (delJson) {
            deleteOpt(delJson);
        }
    }
}();

/****************************************************  三级页面换肤功能 **************************************************/
/**
 * 动态加载CSS
 * @param {string} url 样式地址
 * @param {string} id id名称 可选  通过id查找节点，如果存在就修改href，不存在就创建。
 */
function loadCss(url,id) {
    var styleEle = document.getElementById(id);
    if (styleEle) {
        styleEle.href = url;
    } else {
        var head = document.getElementsByTagName('head')[0];
        var link = document.createElement('link');
        link.type='text/css';
        link.rel = 'stylesheet';
        link.href = url;
        id ? link.id = id : '';
        head.appendChild(link);
    }
}

/***
 *  三级页面换肤功能
 * （本功能和layer插件无关,因为几乎所有三级页面都引入了mylayer.js,所以将换肤功能代码加入到mylayer.js中）
 */
if (parent.Api) {  // 判断是不是新版本
    loadCss('/dist/noBuild/skins/skin_blue/pageThree.css','pageThreeStyle');
}


