$(function() {
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
	$("#id").val(regVlaue(theRequest.id));
	$("#opertation").val(theRequest.opertation);
	if(regVlaue(theRequest.addOrUpdate)=="add"){
		$("#partyOrganizationId").val(theRequest.orgId);
		$("#partyOrganizationName").val(decodeURI(regVlaue(theRequest.orgName)));
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		$("#annual").val(year)
		$("#monthval").val(month)

	}
	if(regVlaue(theRequest.opertation)=="VIEW"){
		$("#partyOrganizationId").text(theRequest.partyOrganizationId);
		$("#partyOrganizationName").text(decodeURI(regVlaue(theRequest.partyOrganizationName)));
		$("#annual").text(theRequest.annual);
		$("#monthval").text(theRequest.monthval+"月");
		$("#yearCurrentPaymentAmount").text(theRequest.yearCurrentPaymentAmount);
	}
})

/**
 * 提交表单
 * */

function commitSave() {
	var valid =getInputVal();
	if(valid){
		var data = saveForm();
		if (data) {
			layer.msg("保存成功！", {
				icon : 1
			});
			// 刷新列表
			myFunction();
			parent.TableInit.refTable('right_table');

		}
	}

}
/**
 * 保存并新增试题
 */
function saveForm(isNew){
	var form = $('#form').serializeJSON();
	var rows1 = $('#right_table1').bootstrapTable("getData");
	$("#right_table1 input[name=currentPaymentAmount]").each(function (i) {
		$('#right_table1').bootstrapTable('updateRow', {
			index: i,
			row: {
				currentPaymentAmount : $(this).val(),
			}
		});
	})
	var rows2 = $('#right_table2').bootstrapTable("getData");
	$("#right_table2 input[name=currentPaymentAmount]").each(function (i) {
		$('#right_table2').bootstrapTable('updateRow', {
			index: i,
			row: {
				currentPaymentAmount : $(this).val(),
			}
		});
	})
	var rows3 = $('#right_table3').bootstrapTable("getData");
	$("#right_table3 input[name=currentPaymentAmount]").each(function (i) {
		$('#right_table3').bootstrapTable('updateRow', {
			index: i,
			row: {
				currentPaymentAmount : $(this).val(),
			}
		});
	})
	form['dfglEntityList'] = rows1;
	form['dfglEntityListOne'] = rows2;
	form['dfglEntityListTwo'] = rows3;
	$.ajax({
		type: "POST",
		url:"/djgl/ddjs/dzz/dfgl/saveDzbDfgl",
		data:JSON.stringify(form),
		contentType:"application/json",
		async: false,
		dataType:"json",
		success:function(data){
			if(data.flag=='1'){
				$("#id").val(data.data.id);
				layer.msg("保存成功！", {icon: 1});
				//刷新列表
				$('#totalSumVal').load();
				parent.TableInit.refTable('right_table');
				TableInit.refTable('right_table1');
				TableInit.refTable('right_table2');
				TableInit.refTable('right_table3');
			}else{
				layer.msg("保存失败！", {icon: 1});
			}
		},
		error:function(data){
			layer.msg("请稍后再试！", {icon: 1});
		}
	});
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

$("#right_table").on("keydown","tr input",function(){

//响应回车键按下的处理
	var e = event || window.event || arguments.callee.caller.arguments[0];

//捕捉是否按键为回车键，可百度JS键盘事件了解更多
	if(e && e.keyCode==13) {

//捕捉para_table下的tr里面文本输入框的个数
		var inputs = $("#right_table table tr ").find(":text");
		console.log(inputs.length);
		var idx = inputs.index(this);                         // 获取当前焦点输入框所处的位置
		if (idx == inputs.length - 1) {                       // 判断是否是最后一个输入框
			//if (confirm("最后一个输入框已经输入,是否提交?"))  // 用户确认
			//     $("form[id='save']").save();                 // 提交表单
		} else {
			inputs[idx + 1].focus(); // 设置焦点
			inputs[idx + 1].select(); // 选中文字
		}
	}
});

$("#right_table").on("keyup","tr input",function(){
	/*var value =$(this).val();
	var msgVal=$(this).parent().prev().prev().find("span[value]").attr("value");*/
	var inputs = $("#right_table table tr ").find(":text");
	var spans = $("#right_table table tr ").find("span");
	var  spansIdx= inputs.index(this);
	var  inputsIdx= inputs.index(this);
	var msgVal =spans[spansIdx].title;
	var value =inputs[inputsIdx].value;
	changeFin(msgVal,value)
})


function changeFin(msgVal,value){
	var valid = true;
	if(value!=null&&value.length!=0){
		var regPos = /^\d+(\.\d+)?$/;
		var regNeg = / ^\d+$/;
		if(regPos.test(value) || regNeg.test(value)){
			if(value.length<10){
				var array = value.split(".");
				if(array.length==2){
					value =array[1];
					if(value.length>3){
						valid = false;
						layer.msg(msgVal+"实际交纳金额小数位长度不能超过3位！", {icon:7});
					}
				}else{

				}
			}else{
				valid = false;
				layer.msg(msgVal+"实际交纳金额长度不得大于10!", {icon: 7});
			}
		}else{
			valid = false;
			layer.msg(msgVal+"实际交纳金额不能为非数字或负数!", {icon: 7});
		}
	}else{
		valid = false;
		layer.msg(msgVal+"实际交纳金额不能为空！", {icon: 7});
	}
	return valid;
}
function getInputVal(){
	var valid = true;
	// 设置 table id
	var table_id = 'right_table';
	// 获取 table 元素
	var table = document.getElementById(table_id);
	// 获取 table 内的全部 input
	var textinputs = table.getElementsByTagName('input');
	// 循环
	var inputs = $("#right_table table tr ").find(":text");
	var spans = $("#right_table table tr ").find("span");
	for(var i = 0; i < inputs.length; i++) {
		var msgVal =spans[i].title;
		var value =inputs[i].value;

		if(valid){
			valid = changeFin(msgVal,value);
		}

	}
	return valid;
}