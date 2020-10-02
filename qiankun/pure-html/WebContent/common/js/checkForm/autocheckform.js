/**
 * Title: 用于自动检查表单的输入是否符合要求的JS文件 Description: 1.使用方法(注意字母大小写) 在你要检查的控件上加上属性
 * CK_TYPE 要检查的类型，多种类型可以用逗号组合起来 CK_NAME 出错时，显示的出错字段名 CK_MSG_XXXX
 * 定制的出错信息,XXXX表示可以是任意要检查的类型
 * 
 * 
 *  autoCheckForm(objForm,checkType)；/ / objForm 表示 要检查的窗体（传dom的是对象） checkType是 // 默认提示的名字  （默认写"1"）
 * 举例(必须一次包含下列js文件): 
 * <Script language="JavaScript" src="include/js/checkform.js"></Script>
 * <Script language="JavaScript" src="include/js/autocheckform.js"></Script>
 * input name="postcode" ck_TYPE="NotEmpty,Number,PostCode" ck_NAME="邮政编码"
 * CK_MSG_NotEmpty = "邮政编码是无论如何都不可以为空的，请重新输入!"
 * 
 * 目前可以使用的类型有: NotEmpty 非空 Number 数字 0-9 Int 数字 1为是数字，0为不是数字 Date 日期
 * 验证格式为：yyyy_mm_dd Double 双精度数（整数也可） Pselect 请选择 EMail EMail地址 Money 货币
 * Postcode 邮政编码 6位数字 Telphone 电话号码 号码中可含有:0123456789-()# MobileNO 手机号码 NoSpace
 * 不含空格 Len_X 字符串长度要求为X,例如:Len_2 表示要求长度为2 Float 可带小数点的数字。 FloatNew
 * 可带小数点的数字，可以是负数。 MaxLen_X 字符串长度要求最大为X,例如:MaxLen_2 表示要求长度最大为2 MinLen_X
 * 字符串长度要求最小为X,例如:MinLen_2 表示要求长度最小为2 如何修订和改进本文件:
 * 如果要添加新的类型，请在函数autoCheckForm(objForm)中添加相关的处理代码,并在上面的可用类型 列表中给出说明即可
 */

/**
 * 自动检查表单的输入是否符合要求
 * 
 * @param objForm
 *            要检查的窗体
 */
function autoCheckForm(objForm,checkType) {
	try{
	if(objForm && $("input[name='start']")){ 
		$("input[name='start']").val(0);
		//verifyAlert($("input[name='start']").val())
	}}catch(e){
		verifyAlert(e)
	}
	var i;
	//特殊字符
	var refn = /['"]+|--+|#+|[$]+|&+|[<>]+|[[]+|[]]+|([Ss][Cc][Rr][Ii][Pp][Tt])/
	//前后空格
	var refn2 = /^\s+|\s+$/g
	var refn3 = /\|\$\|+/g
	var refn1 = /\s+/
	
		for (i = 0; i < objForm.elements.length; i++) { // 遍历表单所有元素
		var objCheckItem; // 要检查的项
		var strItemCKType; // 要检查的格式列表，用逗号分隔
		var defTipMsg; // 自定义提示消息
		objCheckItem = objForm.elements[i];
		strItemCKType = objCheckItem.getAttribute("CK_TYPE");
		if (strItemCKType != null && strItemCKType != "undefined") {
			//objCheckItem.parentElement.style.backgroundColor="#ffffff";//变成白色
			var array = strItemCKType.split(",");
			for (var j = 0; j < array.length; j++) { // 遍历所有该字段要检查的格式要求
				var sCKType;
				sCKType = array[j];
				var sCKName = objCheckItem.getAttribute("CK_NAME");
				var objValue = objCheckItem.value;
				if(objValue != ""){
				if (refn.test(objValue) || refn2.test(objValue) ||  refn3.test(objValue)) {
					if(checkType=="1"){
						if(refn.test(objValue)){
							verifyAlert("信息项["+sCKName+"]输入的内容含有非法字符! 不能输入以下字符 ' \" -- # $ & <> [] script大小写 ");
						}else if(refn1.test(objValue)){
							verifyAlert("信息项["+sCKName+"]输入的内容含有空格");
						}else{
							verifyAlert("信息项["+sCKName+"]输入的内容含有非法字符!不能输入以下字符 |$| 和前后不能输入空格");
						}	
					}else{
						if(refn.test(objValue)){
							verifyAlert("信息项["+sCKName+"]输入的内容含有非法字! 不能输入以下字符 ' \" -- # $ & <> [] script大小写 ");
						}else if(refn1.test(objValue)){
							verifyAlert("信息项["+sCKName+"]输入的内容含有空格");
						}else  if(objValue=="--请选择--"){
							verifyAlert(sCKName+"为必选项");
						}else{
							verifyAlert("信息项["+sCKName+"]输入的内容含有非法字符! 不能输入以下字符 |$| 和前后不能输入空格");
						}
					}
					objCheckItem.parentElement.style.backgroundColor="#FFFFE1";//变成白色
					objCheckItem.focus();
					return false;
				}}
				// 验证特殊字符开始 add by kongfanyu
				// var objValue = objCheckItem.value;
				// if(objValue != null && objValue != ""){
				// var iCharpos = objValue.search("['/\\\\|\"<>`~\^]");
				// if(iCharpos >= 0){
				// verifyAlert("输入的内容含有非法字符 '/\\|\"<>`~\^");
				// objCheckItem.focus();
				// return false;
				// }
				// }
				// 验证特殊字符结束

				// 默认提示的名字
				if(checkType=="1"){
					if(sCKType=="NotEmpty" || sCKType=="Pselect"){
						sCKType = "";
					}
				}
				switch (sCKType) {
					case "NotEmpty" :
						if (JsTrim(objCheckItem.value).length <= 0) {
							defTipMsg = objCheckItem.getAttribute("CK_MSG_NotEmpty");
							if (defTipMsg != null) {
								verifyAlert(objCheckItem.CK_MSG_NotEmpty);
							} else {
								verifyAlert(sCKName + "不能为空!",objCheckItem);
								objCheckItem.parentElement.style.backgroundColor="#FFFFE1";
							}
							try{
								//objCheckItem.focus();
							}catch(e){}
							return false;
						}
						break;
					case "Number" :
						if (objCheckItem.value != ""
								&& fucCheckNUM(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Number");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "必须是半角数字!");
								objCheckItem.style.backgroundColor="#FFFFE1";
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Int" :
						if (objCheckItem.value != ""
								&& fucCheckIntimp(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem.getAttribute("CK_MSG_Int");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "必须是整数!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Plus" :
						if (objCheckItem.value != ""
								&& fucCheckPlusimp(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem.getAttribute("CK_MSG_Plus");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "必须是正整数!");
								objCheckItem.style.backgroundColor="#FFFFE1";
							}
							objCheckItem.select();
							return false;
						}
						break;	
					case "Money" :
						if (objCheckItem.value != ""&& checkMoney(objCheckItem.value) == -1) {
							defTipMsg = objCheckItem.getAttribute("CK_MSG_Money");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "填写有误!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Half" :
						if (objCheckItem.value != ""
							&& checkHalf(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
							.getAttribute("CK_MSG_Half");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "必须是0.5的整数倍!");
								objCheckItem.style.backgroundColor="#FFFFE1";
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Tel" :
						if (objCheckItem.value != ""
							&& checkTel(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
							.getAttribute("CK_MSG_Tel");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "的格式无效！");
								objCheckItem.style.backgroundColor="#FFFFE1";
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Double" :
						if (objCheckItem.value != ""
								&& checkDouble(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Double");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "必须是整数或两位小数以内!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Float" :
						if (objCheckItem.value != ""
								&& objCheckItem.value != null) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Float");
							if (chknbr(objCheckItem.value, 1, 0) == 0) {
								if (defTipMsg != null) {
									verifyAlert(defTipMsg);
								} else {
									verifyAlert(sCKName + "必须是数字!");
								}
								objCheckItem.select();
								return false;
							}
						}
						break;
					case "Date" :
						if (objCheckItem.value != ""
								&& chkdateimp(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Date");
							if (objCheckItem.value != "") {
								if (defTipMsg != null) {
									verifyAlert(defTipMsg);
								} else {
									verifyAlert(sCKName + "的格式无效!正确格式：YYYY-MM-DD!");
								}
								objCheckItem.select();
								return false;
							}
						}
						break;
					case "Year" :
						if (objCheckItem.value != ""
								&& chkyear(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Year");
							if (objCheckItem.value != "") {
								if (defTipMsg != null) {
									verifyAlert(defTipMsg);
								} else {
									verifyAlert(sCKName + "的格式无效!正确格式(YYYY)：[1-2][0-9][0-9][0-9]!");
								}
								objCheckItem.select();
								return false;
							}
						}
						break;
					case "English" :
						if (isEnglish(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem.getAttribute("CK_MSG_English");
							//verifyAlert(checkEnglish(objCheckItem.value))
							if (objCheckItem.value != "") {
								if (defTipMsg != null) {
									verifyAlert(defTipMsg);
								} else {
									verifyAlert(sCKName + "只能含有英文字母,数字,中划线,下划线,逗号!");
								}
								objCheckItem.select();
								return false;
							}
						}
						break;
					case "Pselect" :
						if (isEnglish(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Pselect");
							if (objCheckItem.value == "") {
								if (defTipMsg != null) {
									verifyAlert(defTipMsg);
								} else {
									verifyAlert("请选择" + sCKName);
								}
								objCheckItem.focus();
								return false;
							}
						}
						break;
					case "EMail" :
						var strEMail = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/
						//var strEMail = /^(.+)@(.+)$/
						if (objCheckItem.value != ""
								&& !strEMail.test(objCheckItem.value)) {
						//if (objCheckItem.value != ""
						//		&& chkemail(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_EMail");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName
										+ "的格式无效!正确格式：\n1. 必须有@和.符号\n2. 不能包含空格且@前至少要有三位字符");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Postcode" :
						var strPostcode = /^\d{6}$/
						if (objCheckItem.value != ""
								&& !strPostcode.test(objCheckItem.value)) {
						//if (objCheckItem.value != ""
						//		&& fucCheckPostcode(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Postcode");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "格式无效!格式为6位的邮政编码");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Telphone" :
						if (objCheckItem.value != "" && fucCheckTEL(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem.getAttribute("CK_MSG_Telphone");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "的格式无效!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "MobileNO" :
						if (objCheckItem.value != "" ) {
							var MobileStr = /^1\d{10}$////^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
							if(objCheckItem.value.match(MobileStr)==null){
								verifyAlert(sCKName + "格式不正确");
								return false;
							}
						}
						break;
//						if (!MobileStr.test(objCheckItem.value) ) {
//							defTipMsg = objCheckItem
//									.getAttribute("CK_MSG_MobileNO");
//							if (defTipMsg != null) {
//								verifyAlert(defTipMsg);
//							} else {
//								verifyAlert(sCKName + "的格式无效!");
//							}
//							objCheckItem.select();
//							return false;
//						}
//						break;
					case "NoSpace" :
						if (chkspc(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_NoSpace");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "不能含有空格符!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "FloatNew" :
						if (fucCheckFloat(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Float");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "格式无效!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Cards" :
						var strCards = /^\d{15}$|^\d{18}$|^\d{17}[x|X]$/
						if (!strCards.test(objCheckItem.value)) {
						//if (fucCheckFloat(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem.getAttribute("CK_MSG_Cards");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "格式无效!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Url" :
						var strUrl = /(http\:\/\/\w+\.com\S*)|(http\:\/\/\w+\.net\S*)|(http\:\/\/\w+\.cn\S*)|(http\:\/\/www\.\w+\.com\S*)|(http\:\/\/www\.\w+\.net\S*)|(http\:\/\/www\.\w+\.cn\S*)|(\w+\.com\S*)|(\w+\.net\S*)|(\w+\.cn\S*)|(\w+\.net\S*)|(\w+\.cc\S*)/
							if (objCheckItem.value&&!strUrl.test(objCheckItem.value)) {
							defTipMsg = objCheckItem.getAttribute("CK_MSG_Url");
					
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "格式无效!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Name" :
						var regStr = /[\u4e00-\u9fa5]+/;
						if(objCheckItem.value.match(regStr)==null){
							verifyAlert(sCKName + "必须包含中文!");
							return false;
						}
						break;
					case "Chinese" :
						var regStr = /^[\u4e00-\u9fa5]$/;
						if(objCheckItem.value.match(regStr)==null){
							verifyAlert(sCKName + "只能是中文");
							return false;
						}
						break;
					case "CarMark" ://车牌号校验
						var regStr = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;
						if(objCheckItem.value.match(regStr)==null){
							verifyAlert(sCKName + "格式不正确");
							return false;
						}
						break;
					default :
						if (sCKType.indexOf("MaxLen_") >= 0) {
							var iLen;
							iLen = sCKType.substr(sCKType.indexOf("_") + 1);
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_MaxLen_X");
							if (objCheckItem.value.length > iLen) {
								if (defTipMsg != null) {
									verifyAlert(defTipMsg);
								} else {
									verifyAlert(sCKName + "长度最大为" + iLen + "!");
								}
								objCheckItem.select();
								return false;
							}
						} else if (sCKType.indexOf("MinLen_") >= 0) {
							var iLen;
							iLen = sCKType.substr(sCKType.indexOf("_") + 1);
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_MixLen_X");
							if (objCheckItem.value.length < iLen) {
								if (defTipMsg != null) {
									verifyAlert(defTipMsg);
								} else {
									verifyAlert(sCKName + "长度最小为： " + iLen + "  ！  ");
								}
								objCheckItem.select();
								return false;
							}
						} else if (sCKType.indexOf("Len_") >= 0) {
							var iLen;
							iLen = sCKType.substr(sCKType.indexOf("_") + 1);
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Len_X");
							if (objCheckItem.value.length != iLen) {
								if (defTipMsg != null) {
									verifyAlert(defTipMsg);
								} else {
									verifyAlert(sCKName + "长度不满足要求!");
								}
								objCheckItem.select();
								return false;
							}
						} else if (sCKType == null) {
							verifyAlert(sCKType + "没有定义此类型的检查函数！");
							objCheckItem.select();
							return false;
						}
						break;
				}

			}
		}
	}
	return true;
}

function test() {
	var refn = /^\s*$|[^"']+$/
	var formElementLength = document.getElementsByTagName("input").length;
	for (var i = 0; i < formElementLength; i++) {
		var ob = document.getElementsByTagName("input")[i].value;
		var obk = document.getElementsByTagName("input")[i].parentNode.previousSibling;
		if (document.getElementsByTagName("input")[i].type == "text"
				|| document.getElementsByTagName("input")[i].type == "textarea") {
			if (refn.test(ob)) {
				
			} else {
				if (obk) {
					if (obk.tagName == "TD") {
						if (obk.innerHTML != "") {
							var x = obk.innerHTML;
							var regS = new RegExp("&nbsp;", "gi");
							verifyAlert(x.replace(regS, "") + "不能有特殊字符&nbsp;");
						}
					}
					// verifyAlert(obk.innerHTML + "不能有特殊字符");
				}
				return false;
			}

		}
	}
	return true;
}

function checkEnglish(val){
	var refn = /^[a-zA-Z0-9_-]|.{0-20}$/;
	if (refn.test(val)) {
		return false;
	}else{
		return true;
	}
}

//0.5的整数倍
function checkHalf(val){
	var flag = false;
	var refn = /^([1-9][0-9]*(\.5)?)$/;
	var refn2 = /^(0\.5)$/;
	if (refn.test(val) || refn2.test(val)) {
		return 1
	}else{
		return 0;
	}
}

function checkDouble(val){
	var flag = false;
	var refn = /^-?\d+\.{0,1}\d{0,2}$/;
	if (!refn.test(val)) {
		return 0
	}
	return 1;
}

function checkTel(val) {
	var flag = false;
	var refn = /^[0-9-+()]*$/;
	if (!refn.test(val)) {
		return 0
	}
	return 1;
}

/*包装alert---->verifyAlert*/
function verifyAlert(msg,obj){
	if(obj){
		$.dialog({title:'提示',icon: 'smiley_sad.png',lock:true,zIndex:9999,content: msg,ok:function(){
			try{
				obj.focus();
			}catch(e){}
		}});
	}else{
		$.dialog({
	        id: '_uploadOne',
	        title: '警告',
	        lock: true,
	        icon: 'alert.gif',
	        zIndex:9999,
	        content: '<span>'+msg+'</span>',
	        button: [{
	            name: '关闭 ',
	            callback: function() {
	               
	            }
	        }]
	    });
		
	}
}


function autoCheckFormByIds(objForm,ids,checkType) {
	try{
	if(objForm && $("input[name='start']")){ 
		$("input[name='start']").val(0);
		//verifyAlert($("input[name='start']").val())
	}}catch(e){
		verifyAlert(e)
	}
	var i;
	//特殊字符
	var refn = /['"]+|--+|#+|[$]+|&+|[<>]+|[[]+|[]]+|([Ss][Cc][Rr][Ii][Pp][Tt])/
	//前后空格
	var refn2 = /^\s+|\s+$/g
	var refn3 = /\|\$\|+/g
	var refn1 = /\s+/
	var f = ids.split(",");
	for (i = 0; i < f.length; i++) { // 遍历表单所有元素
		var objCheckItem; // 要检查的项
		var strItemCKType; // 要检查的格式列表，用逗号分隔
		var defTipMsg; // 自定义提示消息
		objCheckItem_id = f[i];
		objCheckItem = document.getElementById(objCheckItem_id);
		strItemCKType = objCheckItem.getAttribute("CK_TYPE");
		if (strItemCKType != null && strItemCKType != "undefined") {
			//objCheckItem.parentElement.style.backgroundColor="#ffffff";//变成白色
			var array = strItemCKType.split(",");
			for (var j = 0; j < array.length; j++) { // 遍历所有该字段要检查的格式要求
				var sCKType;
				sCKType = array[j];
				var sCKName = objCheckItem.getAttribute("CK_NAME");
				var objValue = objCheckItem.value;
				if(objValue != ""){
				if (refn.test(objValue) || refn2.test(objValue) ||  refn3.test(objValue)) {
					if(checkType=="1"){
						if(refn.test(objValue)){
							verifyAlert("信息项["+sCKName+"]输入的内容含有非法字符! 不能包含以下字符 ' \" -- # $ & <> [] script大小写 ");
						}else if(refn1.test(objValue)){
							verifyAlert("信息项["+sCKName+"]输入的内容含有空格");
						}else{
							verifyAlert("信息项["+sCKName+"]输入的内容含有非法字符! 不能包含|$|字符，字符开头和结尾不能包含空格");
						}	
					}else{
						if(refn.test(objValue)){
							verifyAlert("信息项["+sCKName+"]输入的内容含有非法字符! 不能包含以下字符 ' \" -- # $ & <> [] script大小写 ");
						}else if(refn1.test(objValue)){
							verifyAlert("信息项["+sCKName+"]输入的内容含有空格");
						}else  if(objValue=="--请选择--"){
							verifyAlert(sCKName+"为必选项");
						}else{
							verifyAlert("信息项["+sCKName+"]输入的内容含有非法字符! 不能包含|$|字符，字符开头和结尾不能包含空格 ");
						}
					}
					objCheckItem.parentElement.style.backgroundColor="#FFFFE1";//变成白色
					objCheckItem.focus();
					return false;
				}}
				// 验证特殊字符开始 add by kongfanyu
				// var objValue = objCheckItem.value;
				// if(objValue != null && objValue != ""){
				// var iCharpos = objValue.search("['/\\\\|\"<>`~\^]");
				// if(iCharpos >= 0){
				// verifyAlert("输入的内容含有非法字符 '/\\|\"<>`~\^");
				// objCheckItem.focus();
				// return false;
				// }
				// }
				// 验证特殊字符结束

				// 默认提示的名字
				if(checkType=="1"){
					if(sCKType=="NotEmpty" || sCKType=="Pselect"){
						sCKType = "";
					}
				}
				switch (sCKType) {
					case "NotEmpty" :
						if (JsTrim(objCheckItem.value).length <= 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_NotEmpty");
							if (defTipMsg != null) {
								verifyAlert(objCheckItem.CK_MSG_NotEmpty);
							} else {
								verifyAlert(sCKName + "不能为空!");
								objCheckItem.parentElement.style.backgroundColor="#FFFFE1";
							}
							try{
								objCheckItem.focus();
							}catch(e){}
							return false;
						}
						break;
					case "Number" :
						if (objCheckItem.value != ""
								&& fucCheckNUM(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Number");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "必须是半角数字!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Int" :
						if (objCheckItem.value != ""
								&& fucCheckIntimp(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem.getAttribute("CK_MSG_Int");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "必须是整数!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Plus" :
						if (objCheckItem.value != ""
								&& fucCheckPlusimp(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem.getAttribute("CK_MSG_Plus");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "必须是正整数!");
							}
							objCheckItem.select();
							return false;
						}
						break;	
					case "Money" :
						if (objCheckItem.value != ""
								&& checkMoney(objCheckItem.value) == -1) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Money");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "填写有误!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Double" :
						if (objCheckItem.value != ""
								&& checkDouble(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Double");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "必须是整数或两位小数!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Float" :
						if (objCheckItem.value != ""
								&& objCheckItem.value != null) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Float");
							if (chknbr(objCheckItem.value, 1, 0) == 0) {
								if (defTipMsg != null) {
									verifyAlert(defTipMsg);
								} else {
									verifyAlert(sCKName + "必须是数字!");
								}
								objCheckItem.select();
								return false;
							}
						}
						break;
					case "Date" :
						if (objCheckItem.value != ""
								&& chkdateimp(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Date");
							if (objCheckItem.value != "") {
								if (defTipMsg != null) {
									verifyAlert(defTipMsg);
								} else {
									verifyAlert(sCKName + "的格式无效!正确格式：YYYY-MM-DD!");
								}
								objCheckItem.select();
								return false;
							}
						}
						break;
					case "Year" :
						if (objCheckItem.value != ""
								&& chkyear(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Year");
							if (objCheckItem.value != "") {
								if (defTipMsg != null) {
									verifyAlert(defTipMsg);
								} else {
									verifyAlert(sCKName + "的格式无效!正确格式(YYYY)：[1-2][0-9][0-9][0-9]!");
								}
								objCheckItem.select();
								return false;
							}
						}
						break;
					case "English" :
						if (isEnglish(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem.getAttribute("CK_MSG_English");
							//verifyAlert(checkEnglish(objCheckItem.value))
							if (objCheckItem.value != "") {
								if (defTipMsg != null) {
									verifyAlert(defTipMsg);
								} else {
									verifyAlert(sCKName + "只能含有英文字母,数字,中划线,下划线,逗号!");
								}
								objCheckItem.select();
								return false;
							}
						}
						break;
					case "Pselect" :
						if (isEnglish(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Pselect");
							if (objCheckItem.value == "") {
								if (defTipMsg != null) {
									verifyAlert(defTipMsg);
								} else {
									verifyAlert("请选择" + sCKName);
								}
								objCheckItem.focus();
								return false;
							}
						}
						break;
					case "EMail" :
						var strEMail = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/
						//var strEMail = /^(.+)@(.+)$/
						if (objCheckItem.value != ""
								&& !strEMail.test(objCheckItem.value)) {
						//if (objCheckItem.value != ""
						//		&& chkemail(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_EMail");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName
										+ "的格式无效!正确格式：\n1. 必须有@和.符号\n2. 不能包含空格且@前至少要有三位字符");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Postcode" :
						var strPostcode = /^\d{6}$/
						if (objCheckItem.value != ""
								&& !strPostcode.test(objCheckItem.value)) {
						//if (objCheckItem.value != ""
						//		&& fucCheckPostcode(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Postcode");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "格式无效!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Telphone" :
						if (objCheckItem.value != ""
								&& fucCheckTEL(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Telphone");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "的格式无效!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "MobileNO" :
						var MobileStr = /^1\d{10}$////^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
						if (!MobileStr.test(objCheckItem.value) ) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_MobileNO");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "的格式无效!");
							}
							objCheckItem.select();
							return false;
					
						}
						break;
					case "NoSpace" :
						if (chkspc(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_NoSpace");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "不能含有空格符!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "FloatNew" :
						if (fucCheckFloat(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Float");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "格式无效!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Cards" :
						var strCards = /^\d{15}$|^\d{18}$|^\d{17}[x|X]$/
						if (!strCards.test(objCheckItem.value)) {
						//if (fucCheckFloat(objCheckItem.value) == 0) {
							defTipMsg = objCheckItem.getAttribute("CK_MSG_Cards");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "格式无效!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Url" :
						var strUrl = /(http\:\/\/\w+\.com\S*)|(http\:\/\/\w+\.net\S*)|(http\:\/\/\w+\.cn\S*)|(http\:\/\/www\.\w+\.com\S*)|(http\:\/\/www\.\w+\.net\S*)|(http\:\/\/www\.\w+\.cn\S*)|(\w+\.com\S*)|(\w+\.net\S*)|(\w+\.cn\S*)|(\w+\.net\S*)|(\w+\.cc\S*)/
						if (objCheckItem.value&&!strUrl.test(objCheckItem.value)) {
							defTipMsg = objCheckItem.getAttribute("CK_MSG_Url");
							if (defTipMsg != null) {
								verifyAlert(defTipMsg);
							} else {
								verifyAlert(sCKName + "格式无效!");
							}
							objCheckItem.select();
							return false;
						}
						break;
					case "Name" :
						var regStr = /[\u4e00-\u9fa5]+/;
						if(objCheckItem.value.match(regStr)==null){
							verifyAlert(sCKName + "必须包含中文!");
							return false;
						}
						break;
					case "Chinese" :
						var regStr = /^[\u4e00-\u9fa5]$/;
						if(objCheckItem.value.match(regStr)==null){
							verifyAlert(sCKName + "只能是中文");
							return false;
						}
						break;
					case "CarMark" :
						var regStr = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;
						if(objCheckItem.value.match(regStr)==null){
							verifyAlert(sCKName + "格式不正确");
							return false;
						}
						break;
					default :
						if (sCKType.indexOf("MaxLen_") >= 0) {
							var iLen;
							iLen = sCKType.substr(sCKType.indexOf("_") + 1);
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_MaxLen_X");
							if (objCheckItem.value.length > iLen) {
								if (defTipMsg != null) {
									verifyAlert(defTipMsg);
								} else {
									verifyAlert(sCKName + "长度最大为" + iLen + "个字!");
								}
								objCheckItem.select();
								return false;
							}
						} else if (sCKType.indexOf("MinLen_") >= 0) {
							var iLen;
							iLen = sCKType.substr(sCKType.indexOf("_") + 1);
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_MixLen_X");
							if (objCheckItem.value.length < iLen) {
								if (defTipMsg != null) {
									verifyAlert(defTipMsg);
								} else {
									verifyAlert(sCKName + "长度最小为： " + iLen + "  ！  ");
								}
								objCheckItem.select();
								return false;
							}
						} else if (sCKType.indexOf("Len_") >= 0) {
							var iLen;
							iLen = sCKType.substr(sCKType.indexOf("_") + 1);
							defTipMsg = objCheckItem
									.getAttribute("CK_MSG_Len_X");
							if (objCheckItem.value.length != iLen) {
								if (defTipMsg != null) {
									verifyAlert(defTipMsg);
								} else {
									verifyAlert(sCKName + "长度不满足要求!");
								}
								objCheckItem.select();
								return false;
							}
						} else if (sCKType == null) {
							verifyAlert(sCKType + "没有定义此类型的检查函数！");
							objCheckItem.select();
							return false;
						}
						break;
				}

			}
		}
	}
	return true;
}



//检查结束值 是否大于 起始值
function compareEndAndStartVal(sName,eName,sLabel,eLabel,calTextName){
	//name 属性值不为空 下做处理
	if(sName && eName){
		var sVal = parseFloat($('[name="'+sName+'"]').val());
		var eVal = parseFloat($('[name="'+eName+'"]').val());
		//输入的值是数值类型 才做处理 且结束值大于起始值后 
		if((sVal || sVal==0) && (eVal || eVal==0) && typeof sVal == "number" && typeof eVal == "number"){
			if(eVal <= sVal){
				showDialogAlert(eLabel+"必须大于"+sLabel+"!", false);
				return true;
			}else{
				var tempVal = eVal-sVal;
				$('[name="'+calTextName+'"]').val(tempVal.toFixed(2));
			}
			
		}
	}
}

function setFuelPreHundredVal(fuelOil,total,calTextName){
	var fuelOilNumber = $('[name="'+fuelOil+'"]').val();
	var totalNumber = $('[name="'+total+'"]').val();
	if(fuelOilNumber && totalNumber && fuelOilNumber!=0 && totalNumber!=0){
		//百公里耗油量 = 耗油量/(里程*100)
		fuelOilNumber = parseFloat(fuelOilNumber);
		totalNumber = parseFloat(totalNumber);
		$('[name="'+calTextName+'"]').val((fuelOilNumber/(totalNumber*100)).toFixed(2));
		
	}else{
		$('[name="'+calTextName+'"]').val("");
	}
}