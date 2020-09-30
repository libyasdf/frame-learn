$(function(){
    
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#contentId").val(theRequest.contentId);
    $("#subflag").val(theRequest.subflag);
    $("#level").val(theRequest.level);

	//获取职务权限,拼接成html放到隐藏域中
	getUserLevel();
	//将获取到的职务权限放入（类型二）职务权限中
	$("#positionName").append($("#positionNameDiv").html());
	
	var datas = {
		contentId: $("#contentId").val(),
        columnId: theRequest.columnId,
		math:Math.random(),
	}
	httpRequest("get","/video/authority/contentFbZwqx/getAuthority",datas,function (json){
		if(json.flag == "1"){
			var data = json.data;
			//回显部门权限
			var deptList = data.deptList;
			if(!$.isEmptyObject(deptList)){
				var deptIds = "";
				var deptNames = "";
				for (var i = 0;i < deptList.length; i++){
					deptIds += deptList[i].deptId + ",";
					deptNames += deptList[i].deptName + ",";
				}
				deptIds = deptIds.substring(0,deptIds.length-1);
				deptNames = deptNames.substring(0,deptNames.length-1);
				$("#deptId").val(deptIds);
				//$("#deptName").val(deptNames);
				document.getElementById("deptName").innerHTML=deptNames;
			}
			//回显群组权限
			var groupList = data.groupList;
            if(!$.isEmptyObject(groupList)){
                var groupIds = "";
                var groupNames = "";
                for (var i = 0;i < groupList.length; i++){
                    groupIds += groupList[i].groupId + ",";
                    groupNames += groupList[i].groupName + ",";
                }
                groupIds = groupIds.substring(0,groupIds.length-1);
                groupNames = groupNames.substring(0,groupNames.length-1);
                $("#groupId").val(groupIds);
                //$("#groupName").val(groupNames);
                document.getElementById("groupName").innerHTML=groupNames;
            }
			//回显职务权限
			var zwqxList = data.zwqxList;
			if(!$.isEmptyObject(zwqxList)){
				for (var i = 0;i < zwqxList.length; i++){
					$("#positionName").find("[name=positionName][value="+zwqxList[i].positionId+"]").prop("checked",true).prop("disabled",true);
					$("#positionName").find("[name=positionName]").prop("disabled",true);

				}
			}
			//初始化table表	 回显部门+职务权限
			var type=json.type;//判断从栏目还是内容获取的权限
			if(type=="content"){
                iniTable(data.deptZwqxList,data.deptZwqxListList);
			}else{
                iniTable(data.deptZwqxListList,data.deptZwqxList);
			}

		}
	})    
})

/**
 * 初始化table表格
 */
function iniTable(deptZwqxList,deptZwqxListList){
	if(!$.isEmptyObject(deptZwqxList)){
		$(deptZwqxList).each(function(i,n){
			$(deptZwqxListList).each(function(i,list){
				if(list.deptZwqxListId == n.id){
					n["deptId"] = list.deptId;
					n["deptName"] = list.deptName;
				}
			})
		});
	}else{
		deptZwqxList = [{
			id:'',
			deptId:'',
			deptName:''
		}]
	}
	
    var html = $('[name=main]').html();
    nodetpl.render(html,{data:deptZwqxList}, function(html) {
        $('#otherPeople').find('tbody').empty().append(html);
        $(".positionName").append($("#positionNameDiv").html());
        // 全选
        var check = new CheckAll('checkboxSuccess','checkboxName');
        // 动态增加
        new AddTr({
            'tableId': 'otherPeople',
            'addId':'addId',
            'deleteId':'deleteId',
            'numName':'numName',
            'bottom': 'true',
            'isNumber': 'true',
            'numSort': 'asc',
            'addTrFn':addTabFn,
            "deleteFn": null
        })
    });
	//回显职务权限
	if(!$.isEmptyObject(deptZwqxListList)){
		for (var j = 0;j < deptZwqxListList.length; j++){
			var $subtableRows = $('#otherPeople').find("tr");
			if($subtableRows.size() > 0){
				$subtableRows.each(function (i,tr) {
					$(tr).find("input[name=positionName]:checkbox").each(function(i,n){
						var id = $(tr).find("input[name=id]").val();
						$(n).prop("disabled",true)
						if(n.value == deptZwqxListList[j].positionId && deptZwqxListList[j].deptZwqxListId == id){
							$(n).prop("checked",true).prop("disabled",true);
						}
					})
				})
			}
		}
	}
}

/**
 * 添加一行
 */
function addTabFn(obj){
	var subTableLineNum = $("#otherPeople").find("tr").length;
    subTableLineNum ++;
    var trCustom = "";
    trCustom += '<tr>';
    trCustom += '<td class="text-center" style="width: 5%"><input type="checkbox" name="checkboxName" value="option1"></td>';
    trCustom += '<td class="text-center" name="numName" style="width: 8%">10</td>';
    trCustom += '<td class="text-center" style="width: 27%">';
    trCustom += '<div class="input-group">';
    trCustom += '	<input type="hidden" id="deptId'+subTableLineNum+'" name="deptId" />';
    trCustom += '	<input type="text" id="deptName'+subTableLineNum+'" onclick="openDeptTree(\''+subTableLineNum+'\');" unselectable="on" name="deptName" class="form-control">';
    trCustom += '    <span class="input-group-addon" style="cursor: pointer;"';
    trCustom += '          onclick="openDeptTree(\''+subTableLineNum+'\');">';
    trCustom += '    <span class="glyphicon glyphicon-user" title="选择部门"></span>';
    trCustom += '    </span>';
    trCustom += '</div>';
    trCustom += '</td>';
    trCustom += '<td class="text-center" style="width: 60%">';
    trCustom += '	<div class="col-md-10" name="positionName">';
    trCustom += $("#positionNameDiv").html();
    trCustom += '	</div>';
    trCustom += '</td>';
    trCustom += '</tr>';
    return trCustom;
}

/**
 * 获取职务权限,拼接成html放到隐藏域中
 */
function getUserLevel(){
	$.ajax({
		url:"/user/getUserLevel",
		data:{"type":"1"},
		dataType:"json",
		async:false,
		success:function(json){
			console.log(json);
			var html = "";
			if(json.flag == "1"){
				$.each(json.data,function(i,n){
					html += "<label class='checkbox-inline'>";
					html += "<input type='checkbox' title='"+n.occupation_name+"' name='positionName' value='"+n.occupation_level+"'> " + n.occupation_name;
					html += "</label>";
				})
			}
			$("#positionNameDiv").append(html);
		}
	})
}

/**
 * 保存按钮事件
 */
function saveData(){
    var checkResult = checkForm();
    if(checkResult){
        var data = getFormData();
        $.ajax({
        	type: "POST",
        	url:"/video/authority/contentFbZwqx/save",
        	contentType:"application/json",
        	data:JSON.stringify(data),
        	dataType:"json",
        	success:function(json){
        		console.log(json);
        		if(json.flag == "1"){
        			//回调父页面方法，设置标识位，表示已经保存成功权限，可直接发布
        			parent.putStatus(true);
        			var msg = "保存成功!";
        			if($("#subflag").val() == "2"){
                        msg="发布成功!";
					}else{
                        msg="保存成功";
                    }
                    layer.msg(msg,{icon:1});
                    setTimeout(function(){
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
					},500);

        		}
        	},
        	error:function(){
        		layer.msg("保存失败，请刷新重试",{icon:0});
        	}
        })
    }
}

/**
 * 数据校验
 */
function checkForm(){
	var res = false;
	var msg = "";
	//校验部门权限
	var deptFlag = false;
	var deptId = $("#deptId").val();
	var deptName = $("#deptName").val();
	if(deptId && deptName){
		deptFlag = true;
	}
	console.log(deptFlag);
	//校验群组权限
	var groupFlag = false;
	var groupId = $("#groupId").val();
    var groupName = $("#groupName").val();
    if(groupId && groupName){
        groupFlag = true;
    }
    console.log(groupFlag);
	//校验职务权限
	var positionFlag = false;
	var checkedPosit = $("#positionName input:checkbox:checked").size();
	if(checkedPosit && checkedPosit > 0){
		positionFlag = true;
	}
	console.log(positionFlag);
	//校验部门+职务 权限
	var tableFlag = false;
	var $subtableRows = $('#otherPeople').find("tr");
	if($subtableRows.size() > 0){
		$subtableRows.each(function (i,tr) {
			var line = $(tr).find("td[name=numName]").text();
			var deptId = $(tr).find("input[name=deptId]").val();
			var detpName = $(tr).find("input[name=deptName]").val();
			var checkedSize = $(tr).find("input[name=positionName]:checkbox:checked").size();
			if(deptId && detpName && checkedSize == '0'){
				msg = "请选择列表第"+line+"行部门对应的职务权限";
				tableFlag = false;
				return false;
			}else if(checkedSize != '0' && !deptId && !detpName){
				msg = "请选择列表第"+line+"行职务权限对应的部门";
				tableFlag = false;
			    return false;
			}else if(deptId && detpName && checkedSize != '0'){
				tableFlag = true;
			}else if(!deptId && !detpName && checkedSize == '0'){//有空行
				tableFlag = null;
			}
		})
	}else{//空表格
		tableFlag = null;
	}
	console.log(tableFlag);
	if(!deptFlag && !groupFlag && !positionFlag && (!tableFlag || tableFlag==null)){
		msg = msg ? msg : "请至少选择一种权限范围";
	}
	//部门、职务、群组只要有一个不为false  并且  表格为true或者为空
	if((deptFlag || positionFlag || groupFlag) && (tableFlag==null ||tableFlag)){
		res = true;
	}
	//部门、职务、群组均为空，并且  表格有填写完整的一行
	if(!deptFlag && !groupFlag && !positionFlag && tableFlag != null && tableFlag){
		res = true;
	}
	if(!res){
		layer.msg(msg,{icon:0});
	}
	return res;
}

/**
 * 保存封装数据json
 */
function getFormData(){
	var deptList = [];	//部门权限集合
	var groupList = [];	//群组权限集合
    var zwqxList = [];	//职务权限集合
    var deptZwqxList = [];
    var deptZwqxListList = [];	//部门+职务list集合
    //组装部门权限集合
    var deptIds = $("#deptId").val();
    if(deptIds){
    	var depts = deptIds.split(",");
    	var deptNames = $("#deptName").val().split(",");
    	$(depts).each(function(i,n){
    		var dept = {
            	"contentId":$("#contentId").val(),
            	"deptId":n,
            	"deptName":deptNames[i]
            };
    		deptList.push(dept);
    	})
    }
    //组装群组权限集合
    var groupIds = $("#groupId").val();
    if(groupIds){
        var groups = groupIds.split(",");
        var groupNames = $("#groupName").val().split(",");
        $(groups).each(function(i,n){
            var group = {
                "contentId":$("#contentId").val(),
                "groupId":n,
                "groupName":groupNames[i]
            };
            groupList.push(group);
        })
    }
    //组装职务权限集合
    $("#positionName input:checkbox:checked").each(function(i,n){
    	var zwqx = {
        	"contentId":$("#contentId").val(),
        	"positionId":n.value,
        	"positionName":$(n).attr("title")
        };
    	zwqxList.push(zwqx);
    })
    //组装部门+职务list集合	以及	组装部门+职务权限
    var $subtableRows = $('#otherPeople').find("tr");
    $subtableRows.each(function (i,tr) {
    	var line = $(tr).find("td[name=numName]").text();
    	//组装部门+职务list集合
    	var deptZwqxLi = {
    		"line":line,
    		"contentId":$("#contentId").val()
    	};
    	deptZwqxList.push(deptZwqxLi);
        $(tr).find("input[name=positionName]:checkbox:checked").each(function(i,n){
        	var deptZwqx = {
        		"line":line,
            	"contentId":$("#contentId").val(),
            	"deptId":$(tr).find('[name="deptId"]').val(),
                "deptName":$(tr).find('[name="deptName"]').val(),
            	"positionId":n.value,
            	"positionName":$(n).attr("title")
            };
        	deptZwqxListList.push(deptZwqx);
        })
    });
    var data = {
        contentId:$("#contentId").val(),
    	deptList: deptList,
		groupList: groupList,
    	zwqxList: zwqxList,
    	deptZwqxList: deptZwqxList,
    	deptZwqxListList: deptZwqxListList
    };
    console.log(data);
    return data;
}

/**
 * 打开type1部门选择框
 */
function openTree(){
	var level = $("#level").val();
	openSelectZtree({'id':'deptId','name':'deptName','type':'2','asyn':true,'height': '450px','level':level,'url':'/system/component/tree/deptTree','select':true});
}

/**
 * 打开type3部门选择框
 */
function openDeptTree(subTableLineNum){
	var level = $("#level").val();
	openSelectZtree({'id':'deptId'+subTableLineNum+'','name':'deptName'+subTableLineNum+'','select':true,'asyn':true,'isMulti':'1','height': '525px','check':'','cancle':'','type':'2','level':level,'url':'/system/component/tree/deptTree'});
}

/**
 * 打开群组树
 */
function openGroupZtree(){
    openGroupSelectZtree({'id':'groupId','name':'groupName','check':'s','cancle':'s','url':'/user/getGroupTree','subId':Config.sysId})
}