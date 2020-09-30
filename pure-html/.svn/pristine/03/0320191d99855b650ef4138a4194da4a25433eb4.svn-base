$(function() {
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.opertation);
	$("#startTime").val(theRequest.startTime);
	$("#endTime").val(theRequest.endTime);
	/**
	 * 初始化页面，数据加载、渲染
	 */

	if($("#opertation").val() != "VIEW"){
		//初始化字典项--评议结果
		Dictionary.init({position:"reviewResults",mark:"dwgl_dzzgl_dymzpy_pyjg",type:"1",name:"reviewResults",domType:"select"});
		//初始化字典项--处理情况
		Dictionary.init({position:"handleSituation",mark:"dwgl_dzzgl_dymzpy_clqk",type:"1",name:"handleSituation",domType:"select"});
		//初始化字典项--处理原因类型
		Dictionary.init({position:"handleReasonType",mark:"dwgl_dzzgl_dymzpy_cfyy",type:"0",name:"handleReasonType",domType:"select"});

	}
	if($("#id").val() != ""){
		//表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/djgl/ddjs/dzz/mzpy/editDymzpy",datas,function (data){
			if($("#opertation").val() == "VIEW") {
				$(function(e){
					if(data.data.reviewResults == '基本不合格' || data.data.reviewResults== '不合格'){
						$('.togleCol').show();
					}else{
						$('.togleCol').hide();
					}
				})
				if(data.data.handleReasonType=="09"){
					data.data.handleReasonType = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy", type: "0"})[data.data.handleReasonType];
					data.data.handleReason = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy_bhgbx", type: "1"})[data.data.handleReason];
				}else if(data.data.handleReasonType=="08"){
					data.data.handleReasonType = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy", type: "0"})[data.data.handleReasonType];
					data.data.handleReason = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy_qtcwl", type: "1"})[data.data.handleReason];
				}else if(data.data.handleReasonType=="07"){
					data.data.handleReasonType = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy", type: "0"})[data.data.handleReasonType];
					data.data.handleReason = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy_wfshglzxl", type: "1"})[data.data.handleReason];
				}else if(data.data.handleReasonType=="06"){
					data.data.handleReasonType = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy", type: "0"})[data.data.handleReasonType];
					data.data.handleReason = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy_yzwfshzyddl", type: "1"})[data.data.handleReason];
				}else if(data.data.handleReasonType=="05"){
					data.data.handleReasonType = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy", type: "0"})[data.data.handleReasonType];
					data.data.handleReason = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy_qfdy", type: "1"})[data.data.handleReason];
				}else if(data.data.handleReasonType=="04"){
					data.data.handleReasonType = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy", type: "0"})[data.data.handleReasonType];
					data.data.handleReason =  Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy_szl", type: "1"})[data.data.handleReason];
				}else if(data.data.handleReasonType=="03"){
					data.data.handleReasonType = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy", type: "0"})[data.data.handleReasonType];
					data.data.handleReason = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy_jjl", type: "1"})[data.data.handleReason];
				}else if(data.data.handleReasonType=="02"){
					data.data.handleReasonType = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy", type: "0"})[data.data.handleReasonType];
					data.data.handleReason = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy_zzrsl", type: "1"})[data.data.handleReason];
				}else if(data.data.handleReasonType=="01"){
					data.data.handleReasonType = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy", type: "0"})[data.data.handleReasonType];
					data.data.handleReason = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_cfyy_zzl", type: "1"})[data.data.handleReason];
				}
				data.data.reviewResults = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_pyjg", type: "1"})[data.data.reviewResults];
				data.data.handleSituation = Dictionary.getNameAndCode({mark: "dwgl_dzzgl_dymzpy_clqk", type: "1"})[data.data.handleSituation];
			}
			DisplayData.playData({data:data});
			if($("#opertation").val() == "EDIT"){
				var shengMark = $("#handleReasonType option:selected").attr("data-mark");
				Dictionary.init({position:"handleReason",mark:shengMark,type:"1",name:"handleReason",domType:"select"});
			$("#handleReason").val(data.data.handleReason );
			}
		});

	}
})





function commitForm() {
	var data = saveForm();
	if (data) {

		layer.msg("保存成功！", {
			icon : 1
		});
//		var index = parent.layer.getFrameIndex(window.name);
//		parent.layer.close(index);
		// 刷新列表
        TableInit.refTable('right_table');
		parent.TableInit.refTable('right_table');

	}
}
/**
 * 数据字典二级联动
 * 根据省份select初始化城市框
 */
function initCity(){
	var shengMark = $("#handleReasonType option:selected").attr("data-mark");
	Dictionary.init({position:"handleReason",mark:shengMark,type:"1",name:"handleReason",domType:"select"});
}

/**
 * 保存
 */
function saveForm(){
	var res = "";
	var bootstrapValidator = $("#form").data('bootstrapValidator');
	//手动触发验证
	bootstrapValidator.validate();
	var myDate = new Date();
	/*var time = myDate.toLocaleDateString().split('/').join('-');
	$("#reportingTime").val(time);*/
	if(bootstrapValidator.isValid()){
		$.ajax({
			type: "POST",
			url: "/djgl/ddjs/dzz/mzpy/saveDymzpy",
			data:$("#form").serialize(),
			async: false,
			success:function(json){
				if (json.flag == '1') {
					res = json.flag;
					$("#id").val(json.data.id);
					MyFileUpload.saveDocIdToAffix({docId:json.data.id,fileListId: "files"});
				}
			},
			error:function(){
			}
		});
		//保存临时意见
		/*var tempIdea = $("#idea").val();
		saveIdeaTemp($("#workitemid").val(),tempIdea)*/;
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