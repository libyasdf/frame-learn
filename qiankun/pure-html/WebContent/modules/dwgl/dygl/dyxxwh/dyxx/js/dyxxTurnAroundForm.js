$(function() {
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#orgId").val(theRequest.orgId);
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.opertation);

	if($("#opertation").val() != "VIEW"){
		//初始化字典项--党员增加
		Dictionary.init({position:"TurnAroundType",mark:"dwgl_dygl_dyzjqk_dyzj",type:"1",name:"TurnAroundType",domType:"select"});
		//初始化字典项--原个人身份
		Dictionary.init({position:"originalIndividualStatus",mark:"dwgl_dygl_dyzjqk_ygrsf",type:"1",name:"originalIndividualStatus",domType:"select"});
	}
	$.ajax({
		type: "get",
		url:"/djgl/ddjs/dzz/dzzgl/findOne",
		data:{id:theRequest.orgId,
			resId:theRequest.resId
		},
		dataType:"json",
		success:function(data){
			$("#receiveParty").val(data.data.orgName);
		},
		error:function(data){

		}
	});
	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){

		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/djgl/ddjs/dygl/dyxx/trunAroundEdit",datas,function (data){
			DisplayData.playData({data:data});
		});

	}

})

function commitForm(flag) {
//	$("#subflag").val(flag);
	//console.log($("#subflag").val()+"a"+flag)
	var data = saveForm();

	if (data) {

		layer.msg("保存成功！", {
			icon : 1
		});
//		var index = parent.layer.getFrameIndex(window.name);
//		parent.layer.close(index);
		// 刷新列表
		parent.TableInit.refTable('right_table');

	}
}


/**
 * 保存
 */
function saveForm(){
	var res = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	//手动触发验证
	bootstrapValidator.validate();
	if(bootstrapValidator.isValid()){
        var theRequest = GetRequest();
		$.ajax({
			type: "POST",
			url: "/djgl/ddjs/dygl/dyxx/DdjsDyglAroundSave",
			data:$("#form").serialize(),
			async: false,
			success:function(json){
				var aroundId = json.data.id;
				debugger
                MyLayer.layerOpenUrl({
                    url: '/modules/dwgl/dygl/dyxxwh/dyxx/dyxxEditForm.html?orgId='+theRequest.orgId + "&opertation=ADD&orgName="+encodeURI(encodeURI($("#receiveParty").val()))+"&superId="+aroundId,
                    title: "新增党员信息",
					width: '90%',
					height: '85%'
                })
			},
			error:function(){
			}
		});
	}
	console.log(res);
	return res;
}

/**
 * 空值设置
 * @param val
 * @returns
 */
function regVlaue(val){
	if(!val||val=="undefined"){
		val = "";
	}
	return val;
}