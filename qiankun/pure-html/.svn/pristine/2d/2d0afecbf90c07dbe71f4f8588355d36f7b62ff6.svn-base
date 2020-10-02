/**
 * 归档库和预立库（按门类）用到的门类树js
 */
var typeTree = function() {
	var creTree = "";
	var dragId;// 用于存储被拖拽节点的父id

	/**
	 * ztree的初始化参数
	 */
	var setting = {
//			check: {
//	            enable: true
//	        },//添加复选框
		edit : {
			drag : {
				borderMax : 20,
				autoExpandTrigger : true,
				prev : true,
				inner : true,
				next : true
			},
			
			enable : true,
			showRemoveBtn : false,
			showRenameBtn : false
		},
		
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			//onCheck : OnChecks,//用户点击复选框 回调事件
			onClick : onClick,
			beforeDrag : beforeDrag,// 用于捕获节点被拖拽之前的事件回调函数，并且根据返回值确定是否允许开启拖拽操作
			beforeDrop : beforeDrop,// 用于捕获节点拖拽操作结束之前的事件回调函数，并且根据返回值确定是否允许此拖拽操作
			onDrag : null,// 用于捕获节点被拖拽的事件回调函数
			onDrop : onDrop
			
		// 用于捕获节点拖拽操作结束的事件回调函数
		}
	};
	//复选框 点击子节点是否选中父节点问题
	//setting.check.chkboxType = { "Y" : "s", "N" : "s" };

	/**
	 * 实现字典类型拖拽排序
	 */
	function onDrop(event, treeId, treeNodes, targetNode, moveType) {
		if (targetNode == null) {
			return;
		}
		for (var i = 0, l = treeNodes.length; i < l; i++) {
			var dropId = treeNodes[i].id;
			var pId = treeNodes[i].pId;
			var ids = [];
			var node = treeNodes[i].getParentNode();
			var nodeChildren = node.children;
			for (var j = 0; j < nodeChildren.length; j++) {
				ids.push(nodeChildren[j].id);
			}
			var idStr = ids.join("&id=");
			$.ajax({
				type : "post",
				url : "/zhbg/xxkh/tree/updatetreeSort?id=" + idStr + "&dropId=" + dropId + "&pId=" + pId,
				dataType : "json",
				success : function(res) {
					if (res.flag == "1") {
						layer.msg("排序成功！", {
							icon : 1
						});
					} else {
						layer.msg("排序失败！", {
							icon : 2
						});
					}
				},
				error : function() {
					layer.msg("排序异常，请刷新重试！", {
						icon : 2
					});
				}
			})
		}
	}
	;

	/**
	 * 点击事件，展示字典项
	 */
	var dongxi="";
	function onClick(event, treeId, treeNode) {
		layer.closeAll();
		console.log("前---"+checkBoxData[tabCurrent]);
        //清空选中的数据
        checkBoxData[tabCurrent]=[];
        console.log("后---"+checkBoxData[tabCurrent]);
        if(treeNode.pId != "" && treeNode.pId != null) {//排除点击全宗的点击事件
            if (creTree != treeNode.categoryCode) {
                tabData = [];
                creTree = treeNode.categoryCode;
            }
            if (treeNode.categoryCode == dongxi) {
                initPanel()
                if (dongxi != "") {
                    try {
                        typeTree1.init({codeId: treeNode.categoryCode});
                    } catch (e) {
                        console.error('档案库没有typeTree1');
                    }
                }
            } else {
                //判断是否是第一次点击 如果是则不刷新人员树
                //if(dongxi!=""){
                try {
                    typeTree1.init({codeId: treeNode.categoryCode});
                } catch (e) {
                    console.error('档案库没有typeTree1');
                }
                //}
                dongxi = treeNode.categoryCode;
                initPage();
            }
        }
			//checkBoxData = {};
	}

	/**
	 * 将被拖拽的节点父id存到dragId
	 */
	function beforeDrag(treeId, treeNodes) {
		for (var i = 0, l = treeNodes.length; i < l; i++) {
			dragId = treeNodes[i].pId;
			if (treeNodes[i].drag === false) {
				return false;
			}
		}
		return true;
	}
	//复选框 选中 取消时间
//	function OnChecks(event, treeId, treeNode){
//
//		var zTree = $.fn.zTree.getZTreeObj("treeDemo");//换成实际的图层的id
//		var changedNodes = zTree.getChangeCheckedNodes(); //获取改变的全部结点
//		for ( var i=0 ; i < changedNodes.length ; i++ ){
//			var treeNode = changedNodes[i];
//			console.log((treeNode?treeNode.name:"root") + "checked " +(treeNode.checked?"true":"false"));
//		}
//		
//	}

	/**
	 * 判断拖拽到节点的父id是否和被拖到节点的父id一样，实现只允许同级目录拖拽
	 */
	function beforeDrop(treeId, treeNodes, targetNode, moveType) {
		/*
		 * if(targetNode.pId == dragId){ return true; }else{
		 * layer.msg('只允许同级之间目录拖动！',{icon:2}) return false; }
		 */
		return true;
	}

	/**
	 * 初始化左侧字典类型树
	 */
	var ztree;
	var init = function(json) {
		
		var arg = {
			pid : "" // 需要初始化的模块数据字典唯一标识
		};
		var _arg = $.extend(arg, json);
		//资料维护树、考试维护树、试卷维护树(试卷管理-部门除外)走以下URL↓
		var url ="/dagl/bmgl/findTree?isQ2="+_arg.isQ2+"&isAdmin="+_arg.isAdmin;
		$.ajaxSettings.async = false;
		$.getJSON(url, function(json) {

			if ("1" == json.flag) {
				for (var i = 0, length = json.data.total; i < length; i++) {
					if (!json.data.rows[i].pId) {
						json.data.rows[i].open = true;
					}
				}
				ztree = $.fn.zTree.init($("#treeDemo"), setting, json.data.rows);
				var nodes = ztree.getNodes();
				if (nodes.length > 0) {
                    for(var j = 0;j<nodes.length;j++){
                        if(nodes[j].isParent){
                            ztree.selectNode(nodes[j].children[0]);
                            treeNode=nodes[j].children[0].categoryCode;
                            break;
                        }

                    }
                    //全宗下无节点的时候隐藏
                    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                    for(var j = 0;j<nodes.length;j++){
                        if(!nodes[j].isParent){
                            treeObj.hideNode(nodes[j]);
                        }

                    }
				}
			}
		});
		$.ajaxSettings.async = true;
	}

	/**
	 * 增加节点
	 */
	var addNode = function(json) {
		var ztree = $.fn.zTree.getZTreeObj("treeDemo");
		var parentNode = ztree.getNodeByParam("id", json.pId);
		ztree.addNodes(parentNode, json);
	//	var nodes = ztree.getNodes();
//		if (nodes.length > 0) {
//			ztree.selectNode(nodes[0]);
//		}
	}

	/**
	 * 更新节点
	 */
	var updateNode = function(data) {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var node = treeObj.getNodeByParam("id", data.data.id, null);
		if (node) {
			node.pId = data.data.pId;
			node.name = data.data.name;
			node.mark = data.data.mark;
		}
		treeObj.updateNode(node);
	}

	/**
	 * 删除节点
	 */
	var deletNode = function(nodeId) {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var node = treeObj.getNodeByParam("id", nodeId, null);
		treeObj.removeNode(node);
	}

	/**
	 * 根据节点名称模糊查询节点并触发点击事件
	 */
	var searchNodes = function(nodeName) {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getNodesByParamFuzzy("name", nodeName, null);
		if (nodes.length > 0) {
			treeObj.selectNode(nodes[0]);
			onClick(null, treeObj.setting.treeId, nodes[0]);// 调用事件
		}else{
			layer.msg("未查询到结果", {
				icon : 0
			});
		}
	}

	return {
		init : function(json) {
			init(json);
		},
		addNode : function(json) {
			addNode(json);
		},
		updateNode : function(json) {
			updateNode(json);
		},
		delNode : function(nodeId) {
			deletNode(nodeId);
		},
		searchNodes : function(nodeName) {
			searchNodes(nodeName);
		},
        onClick : onClick
	}
}();
// @ sourceURL=typeTree.js
