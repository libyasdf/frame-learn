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
   /* 	$("#page1").attr("class","active")
    	$("#page2").attr("class","")
    	$("#page3").attr("class","")*/
    	$('.nav-tabs > li').removeClass("active");
    	$('.nav-tabs > li:first').attr("class","active")
    	$('.main').children().addClass('hidden');
    	var id = $('.nav-tabs > li:first').attr('for');
        $('#'+id).removeClass('hidden');
        
        $("#columnId").val(treeNode.id);
        $("#isFirst").val(treeNode.isFirst);
        $('#right_table1').bootstrapTable('refresh');
        $('#right_table2').bootstrapTable('refresh');
        $('#right_table3').bootstrapTable('refresh');
        //parent.isShowPage();
    }


    /**
     * 初始化左侧树
     */
    var init = function () {
        var userId = getcookie("userid");
        $.ajaxSettings.async = false;
        $.getJSON("/video/background/column/getContentColumnTree?columnName=&userId="+userId+"&math="+Math.random(), function (json) {
        	console.log("json11: "+JSON.stringify(json));
            if ("1" == json.flag) {
                for (var i = 0, length = json.data.length; i < length; i++) {
                    if (json.data[i].pId=="0") {
                        json.data[i].open = true;
                    }
                }
                var ztree = $.fn.zTree.init($("#treeDemo1"), setting, json.data);
                var nodes = ztree.getNodes();
                if (nodes.length > 0) {
                    ztree.selectNode(nodes[0]);
                    $("#columnId").val(nodes[0].id);
                    $("#isFirst").val(nodes[0].isFirst)
                    
                    //isShowPages()
                }
            }
        });
        $.ajaxSettings.async = true;
    }
    /**
     * 根据节点名称模糊查询节点并触发点击事件
     */
    var searchNodes = function(nodeName){
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo1");
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