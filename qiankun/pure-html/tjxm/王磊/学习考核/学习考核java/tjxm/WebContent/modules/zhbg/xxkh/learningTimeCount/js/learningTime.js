$(function() {
	var theRequest = GetRequest();
	getposition();
	//考试树类型(type:大类；nodeId小类)
	$("#type").val(theRequest.treeType);
});

/**
 * 职务和职级数据渲染
 */
function getposition(){
	var res =false;
	$.ajax({
		type: "get",
		url:"/zhbg/xxkh/testmanage/getPosition",
		async:false,
		success:function(json){
			if (json.flag == '1') {
				//职务数据
				var data1 = json.data;
				//职级数据
				var data2 = json.data2;
				//创建
				$('#position').append('<label  class="checkbox-inline"><input id="inlineCheckboxAll" type="checkbox" ">全选</label>');
				for(var i in data1){
					$('#position').append('<label  class="checkbox-inline"><input name="dutyIds" type="checkbox" value="'+data1[i].occupation_level+'">'+data1[i].occupation_name+'</label>');
				}
				$('#positionLevel').append('<label  class="checkbox-inline"><input id="inlineCheckboxAll2" type="checkbox" ">全选</label>');
				for(var i in data2){
					$('#positionLevel').append('<label class="checkbox-inline"><input type="checkbox"  name="levelIds" value="'+data2[i].occupation_level+'">'+data2[i].occupation_name+'</label>');
				}
				 //点击全选改变所有复选框状态
				$("#inlineCheckboxAll").click(function(){
					var state = $("#inlineCheckboxAll").prop("checked");
					$("input[name='dutyIds']").prop("checked",state);
				});
				//给每一个部门复选框添加点击事件，改变全选状态
				$("input[name='dutyIds']").click(
							function(){
				   var flag= true;
				   $("input[name='dutyIds']").each(function(){
					   flag=flag&$(this).prop("checked");
				   });
				   $("#inlineCheckboxAll").prop("checked",flag);
				});
				//点击全选改变所有复选框状态
				$("#inlineCheckboxAll2").click(function(){
					var state = $("#inlineCheckboxAll2").prop("checked");
					$("input[name='levelIds']").prop("checked",state);
				});
				//给每一个部门复选框添加点击事件，改变全选状态
				$("input[name='levelIds']").click(
							function(){
				   var flag= true;
				   $("input[name='levelIds']").each(function(){
					   flag=flag&$(this).prop("checked");
				   });
				   $("#inlineCheckboxAll2").prop("checked",flag);
				});
				res = true;
			}else {
				
			}
		},
		error:function(){
		}
	});
	return res;
}
/**
 * 不上报考试人员
 * 选择处室（部门考试与其他三个打开的部门树不一样）
 */
function selectChushi(){
	openSelectZtree({'id':'unitId','name':'unitName','type':'2','asyn':false,'level':2,'url':'/system/component/tree/deptTree'})
}