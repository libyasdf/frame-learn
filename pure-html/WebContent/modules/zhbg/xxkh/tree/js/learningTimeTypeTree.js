var typeTree = function() {

	var dragId;// 用于存储被拖拽节点的父id
    var from;
	/**
	 * ztree的初始化参数
	 */
	var setting = {
			check: {
	            enable: true
	        },//添加复选框
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
			onCheck : OnChecks,//用户点击复选框 回调事件
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
	function onClick(event, treeId, treeNode) {
		$('#right_table').bootstrapTable('getOptions').pageNumber=1;
		ztree.checkNode(treeNode, !treeNode.checked, true); //点字选择复选框
		var nodeId = treeNode.id;
		var treeType = treeNode.treeType;
		// 默认查询个人学习时长、其他功能需要根据树的类型动态调整url  区分个人统计和部门统计
		var url="";
		if(from=='personal'){
			url="/zhbg/xxkh/learntime/learningTimeList?type="+treeType;
		}
		if(from=='dept'){
			url="/zhbg/xxkh/learntime/deptLearningTimeList?type="+treeType;
		}
		url = url+nodeId;
		var opt = {
			url : url
			
		};
		// 动态新增按钮
		$('#right_table').bootstrapTable('refresh', opt);
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
	function OnChecks(event, treeId, treeNode){
		var nodeId = treeNode.id;
		var treeType = treeNode.treeType;
		// 默认查询个人学习时长、其他功能需要根据树的类型动态调整url  区分个人统计和部门统计
		var url="";
		if(from=='personal'){
			url="/zhbg/xxkh/learntime/learningTimeList?type="+treeType;
		}
		if(from=='dept'){
			url="/zhbg/xxkh/learntime/deptLearningTimeList?type="+treeType;
		}
		url = url+nodeId;
		var opt = {
			url : url
			
		};
		// 动态新增按钮
		$('#right_table').bootstrapTable('refresh', opt);
		//在线培训独有逻辑，控制总人数div及内容显示 王磊 2019-03-06
		var url = $("#left_ul").find("a.active").attr("url");
		var parameter = new GetParameter(url);
		console.log("OnChecks：parameter.treeType:"+parameter.treeType);
		if("zxpx"==parameter.treeType){
			var chuShiId = $("#unitId").val();
			var erJiJu = $("#creDeptId").val();
			if(chuShiId !==null && chuShiId !== '' && chuShiId.indexOf(",")==-1){
				//有处室，优先显示处室总人数
				$.ajax({
					type : "POST",
					url : "/zhbg/xxkh/learntime/getDeptUserCount",
					data : {
						deptId : chuShiId
					},
					dataType : "json",
					success : function(data) {
						if(data.data != "0"){
							$("#detpUserCountTip").show();
							$("#detpUserCountTip").html("<font style='color:red;font-size:14px;'>该处室总人数："+data.data+"</font>");
						}
					},
					error : function(data) {
						layer.msg("获取总人数异常，请稍后再试！", {icon : 0});
					}
				});
			}else if(erJiJu !==null && erJiJu !== '' && erJiJu.indexOf(",")==-1 && (chuShiId.indexOf(",")==-1 || chuShiId=='')){
				//如果没处室，就显示部门总人数
				$.ajax({
					type : "POST",
					url : "/zhbg/xxkh/learntime/getDeptUserCount",
					data : {
						deptId : erJiJu
					},
					dataType : "json",
					success : function(data) {
						if(data.data != "0"){
							$("#detpUserCountTip").show();
							$("#detpUserCountTip").html("<font style='color:red;font-size:14px;'>该部门总人数："+data.data+"</font>");
						}
					},
					error : function(data) {
						layer.msg("获取总人数异常，请稍后再试！", {icon : 0});
					}
				});
			}else{
				//部门和处室什么都没选，应该清空总人数提示
				$("#detpUserCountTip").html("");
				$("#detpUserCountTip").hide();
			}
		}
	}

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
		from=_arg.from;
		var url = "/zhbg/xxkh/tree/learningTimeTreeList?type=1&treeType=" + _arg.treeType;
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
					ztree.selectNode(nodes[0]);
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
		var nodes = ztree.getNodes();
		if (nodes.length > 0) {
			ztree.selectNode(nodes[0]);
		}
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
		}
	}
}();
// @ sourceURL=typeTree.js
