

var dyglTree = function(){

    /**
     * ztree的初始化参数
     */
    var setting = {
        edit: {
            enable: true,
            showRemoveBtn: false,
            showRenameBtn: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick,

        },
        view:{
            selectedMulti:false
        }
    };


    /**
     * 点击事件，展示字典项
     */
    function onClick(event, treeId, treeNode) {
        $("#right_table").bootstrapTable('destroy');
        right_table(treeNode.id,treeNode.zid);
    }



    /**
     * 初始化左侧字典类型树
     */
    var init = function (json) {
        $.ajaxSettings.async = false;
        $.getJSON("/djgl/ddjs/dygl/dyxx/getTree?resId="+resource.resId+"&orgId=001001&ryType="+json, function (json) {
            if(json.length > 0){
                var ztree = $.fn.zTree.init($("#treeDemo"), setting, json);
                var node = ztree.getNodes()[0];
                ztree.selectNode(node);

                setting.callback.onClick(null, ztree.setting.treeId, node);
            }
        });
        $.ajaxSettings.async = true;
    }

    /**
     * 增加节点
     */
    var addNode = function (json) {
        var ztree = $.fn.zTree.getZTreeObj("treeDemo");
        var parentNode = ztree.getNodeByParam("id",json.pId);
        ztree.addNodes(parentNode, json);
    }

    /**
     * 更新节点
     */
    var updateNode = function(data,nodeId){
        var ztree = $.fn.zTree.getZTreeObj("treeDemo");
        var node = ztree.getNodeByParam("id", nodeId, null);
        if(node){
            node.id = data.data.orgId;
            node.pId = data.data.superId;
            node.name = data.data.orgName;
        }
        ztree.updateNode(node);
        onClick(null, ztree.setting.treeId,ztree.getSelectedNodes()[0]);//调用事件

    }

    /**
     * 删除节点
     */
    var deletNode = function(nodeId){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var node = treeObj.getNodeByParam("id", nodeId, null);
        treeObj.removeNode(node);
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
        init:function (json) {
            init(json);
        },
        addNode:function (json) {
            addNode(json);
        },
        updateNode:function(json,nodeId){
            updateNode(json,nodeId);
        },
        delNode:function(nodeId){
            deletNode(nodeId);
        },
        searchNodes:function(nodeName){
            searchNodes(nodeName);
        }
    }
}();
