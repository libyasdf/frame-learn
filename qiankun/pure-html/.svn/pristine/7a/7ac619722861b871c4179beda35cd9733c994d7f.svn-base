var typeTree = function(){
	var dragId;//用于存储被拖拽节点的父id
	/**
	 * ztree的初始化参数
	 */
    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick,
        }
    };

    /**
     * 点击事件，展示栏目内容项
     */
    function onClick(event, treeId, treeNode) {
        $("#columnId").val(treeNode.id);
        $('#right_table1').bootstrapTable('refresh');
        $('#right_table2').bootstrapTable('refresh');
        $('#right_table3').bootstrapTable('refresh');
       /* parent.isShowPage();*/
    }


    /**
     * 初始化左侧树
     */
    var init = function () {
        var userId = getcookie("userid");
        $.ajaxSettings.async = false;
        $.getJSON("/info/column/getColumnTree?columnName=&userId="+userId+"&math="+Math.random(), function (json) {
            if ("1" == json.flag) {
                for (var i = 0, length = json.data.length; i < length; i++) {
                    if (json.data[i].pId=="0") {
                        json.data[i].open = true;
                    }
                }
                var ztree = $.fn.zTree.init($("#treeDemo"), setting, json.data);
                var nodes = ztree.getNodes();
                if (nodes.length > 0) {
                    ztree.selectNode(nodes[0]);
                }
            }
        });
        $.ajaxSettings.async = true;
    }
    /**
     * 根据节点名称模糊查询节点并触发点击事件
     */
    var searchNodes = function(nodeName){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getNodesByParamFuzzy("name", nodeName, null);
        if (nodes.length > 0) {
            treeObj.selectNode(nodes[0]);
            onClick(null, treeObj.setting.treeId, nodes[0]);//调用事件
        }
    }

    return {
        init:function () {
            init();
        },
        searchNodes:function(nodeName){
            searchNodes(nodeName);
        }
    }
}();