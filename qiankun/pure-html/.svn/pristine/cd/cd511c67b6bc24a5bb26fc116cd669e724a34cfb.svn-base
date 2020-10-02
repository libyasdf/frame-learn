/*
 * 函数名：chksafe 功能介绍：检查是否含有"'",'\\',"/" 参数说明：要检查的字符串 返回值：0：是 1：不是
 */
function chksafe(a, b) {
	// return 1;
	var ero = b;
	fibdn = new Array("\'", "\\", "、", ",", ";", "/", "\&", "$", "~", "!", "`");
	i = fibdn.length;
	j = a.length;
	for (ii = 0; ii < i; ii++) {
		for (jj = 0; jj < j; jj++) {
			temp1 = a.charAt(jj);
			temp2 = fibdn[ii];
			if (temp1 == temp2) {
				return ero;
			}
		}
	}
	return;
}

/*
 * 函数名：chkspc 功能介绍：检查是否含有空格 参数说明：要检查的字符串 返回值：0：是 1：不是
 */
function chkspc(a) {// var ero=b;
	var i = a.length;
	var j = 0;
	var k = 0;
	while (k < i) {
		if ((a.charAt(k) == " ") || (a.charAt(k) == "　")) {
			j = j + 1;
		}
		k = k + 1;
	}
	if (j > 0) {
		// alert(ero);
		return 0;
	}
	return 1;
}

/*
 * 检查在字符串a中是否包含b
 */
function isContainChar(a, b) {
	var i = a.length;
	var j = 0;
	var k = 0;
	while (k < i) {
		if (a.charAt(k) == b) {
			j = 1;
			break;
		}
		k = k + 1;
	}
	return j;
}

function chkemail(name, b) {
	return _chkemail(EP.getTarget().value, b);
}

function chkemailOnSubmit(name, b) {
	var elements = document.getElementsByName(name);
	var message;
	for (var i = 0; i < elements.length; ++i) {
		if (!isVerifiable(elements[i]))
			continue;
		message = _chkemail(elements[i].value, b);
		if (message) {
			if (!EP.checkErrorElement)
				EP.checkErrorElement = elements[i];
			return message;
		}
	}
	return;
}

/*
 * 函数名：chkemail 功能介绍：检查是否为Email Address 参数说明：要检查的字符串 返回值：0：不是 1：是
 */
function _chkemail(inputString, b) {
	var ero = b;
	var pattern = /^([\.a-zA-Z0-9_-]){3,}@([a-zA-Z0-9_-]){1,}(\.([a-zA-Z0-9]){1,}){1,}$/;
	if (!pattern.test(inputString)) {
		return ero;
	}
	return;
}

/*
 * opt1 小数 opt2 负数 当opt2为1时检查num是否是负数 当opt1为1时检查num是否是小数 返回1是正确的，0是错误的
 */
function chknbr(num, opt1, opt2) {
	var i = num.length;
	var staus;
	// staus用于记录.的个数
	status = 0;
	if ((opt2 != 1) && (num.charAt(0) == '-')) {
		// alert("You have enter a invalid number.");
		return 0;

	}
	// 当最后一位为.时出错
	if (num.charAt(i - 1) == '.') {
		// alert("You have enter a invalid number.");
		return 0;
	}

	for (j = 0; j < i; j++) {
		if (num.charAt(j) == '.') {
			status++;
		}
		if (status > 1) {
			// alert("You have enter a invalid number.");
			return 0;
		}
		if (num.charAt(j) < '0' || num.charAt(j) > '9') {
			if (((opt1 == 0) || (num.charAt(j) != '.')) && (j != 0)) {
				// alert("You have enter a invalid number.");
				return 0;
			}
		}
	}
	return 1;
}

function chkdateOnSubmit(name, b) {
	var elements = document.getElementsByName(name);
	var message;
	for (var i = 0; i < elements.length; ++i) {
		if (!isVerifiable(elements[i]))
			continue;
		message = _chkdate(elements[i].value, b)
		if (message) {
			if (!EP.checkErrorElement)
				EP.checkErrorElement = elements[i];
			return message;
		}
	}
	return;
}

function chkdate(name, b) {
	return _chkdate(EP.getTarget().value, b);
}

function chkyear(value) {
	return _chkyear(value);
}

function _chkyear(value) {
	var a = value;
	var c = chkdateimp1(a);
	if (c == 0) {
		return 0;
	}
	return;
}

function _chkdate(dateser, b) {
	var ero = b;
	var a = dateser;
	var c = chkdateimp(a);
	if (c == 0) {
		return ero;
	}
	return;
}

/*
 * 函数名：chkdate 功能介绍：检查是否为日期 参数说明：要检查的字符串 返回值：0：不是日期 1：是日期
 */
function chkdateimp1(datestr) {
	// var ero=b;
	var lthdatestr;
	if (datestr != "")
		lthdatestr = datestr.length;
	else
		lthdatestr = 0;
	var status;
	status = 0;
	if (lthdatestr == 0)
		return 0;
	if ((datestr.length != 4)) {
		return 0;
	}
	var param=/[1-2][0-9][0-9][0-9]/;
	if(!param.exec(datestr)){
		return 0;
	}
	return 1;
}
/*
 * 函数名：chkdate 功能介绍：检查是否为日期 参数说明：要检查的字符串 返回值：0：不是日期 1：是日期
 */
function chkdateimp(datestr) {
	// var ero=b;
	var lthdatestr;
	if (datestr != "")
		lthdatestr = datestr.length;
	else
		lthdatestr = 0;
	var tmpy = "";
	var tmpm = "";
	var tmpd = "";
	var status;
	status = 0;
	if (lthdatestr == 0)
		return 0;

	for (i = 0; i < lthdatestr; i++) {
		if (datestr.charAt(i) == '-') {
			status++;
		}
		if (status > 2) {
			return 0;
		}
		if ((status == 0) && (datestr.charAt(i) != '-')) {
			tmpy = tmpy + datestr.charAt(i)
		}
		if ((status == 1) && (datestr.charAt(i) != '-')) {
			tmpm = tmpm + datestr.charAt(i)
		}
		if ((status == 2) && (datestr.charAt(i) != '-')) {
			tmpd = tmpd + datestr.charAt(i)
		}
	}
	year = new String(tmpy);
	month = new String(tmpm);
	day = new String(tmpd);
	if ((tmpy.length != 4) || (tmpm.length > 2) || (tmpd.length > 2)) {
		return 0;
	}
	if (!((1 <= month) && (12 >= month) && (31 >= day) && (1 <= day))) {
		return 0;
	}
	if (!((year % 4) == 0) && (month == 2) && (day == 29)) {
		return 0;
	}
	if ((month <= 7) && ((month % 2) == 0) && (day >= 31)) {
		return 0;
	}
	if ((month >= 8) && ((month % 2) == 1) && (day >= 31)) {
		return 0;
	}
	if ((month == 2) && (day == 30)) {
		return 0;
	}
	return 1;
}

/*
 * 函数名：fucPWDchk 功能介绍：检查是否含有非数字或字母 参数说明：要检查的字符串 返回值：0：含有 1：全部为数字或字母
 */
function fucPWDchk(str, b) {
	var ero = b;
	var strSource = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var ch;
	var i;
	var temp;

	for (i = 0; i <= (str.length - 1); i++) {

		ch = str.charAt(i);
		temp = strSource.indexOf(ch);
		if (temp == -1) {
			return ero;
		}
	}
	if (strSource.indexOf(ch) == -1) {
		return ero;
	} else {
		return;
	}
}

function jtrim(str) {
	while (str.charAt(0) == " ") {
		str = str.substr(1);
	}
	while (str.charAt(str.length - 1) == " ") {
		str = str.substr(0, str.length - 1);
	}
	return (str);
}

function fucCheckDoubleOnSubmit(name, b) {
	var elements = document.getElementsByName(name);
	var message;
	for (var i = 0; i < elements.length; ++i) {
		if (!isVerifiable(elements[i]))
			continue;
		message = _fucCheckDouble(elements[i].value, b);
		if (message) {
			if (!EP.checkErrorElement)
				EP.checkErrorElement = elements[i];
			return message;
		}
	}
	return;
}

function fucCheckDouble(name, b) {
	return _fucCheckDouble(EP.getTarget().value, b);
}

/*
 * 检测双精度小数,是整数也可通过检测
 */
function _fucCheckDouble(str, b) {
	var i = str.indexOf(".");
	var iStr, fStr;
	// 整数部分和小数部分。最后一位数是小数点也可以通过检测
	if (i == str.length - 1) {
		return;
	}
	// 分析整数核小数部分格式
	if (i != -1) {
		iStr = str.substring(0, i);
		fStr = str.substring(i + 1);
		if (fucCheckNUMimp(iStr) == 1 && fucCheckNUMimp(fStr) == 1)
			return;
		else {
			return b;
		}
	} else {
		if (fucCheckNUMimp(str) == 1)
			return;
		else {
			return b;
		}
	}
}

/*
 * 函数名：fucCheckInt 功能介绍：检查是否为数字 参数说明：要检查的数字 返回值：1为是数字，0为不是数字
 */
function fucCheckIntimp(num) {
	var i, j, strTemp;
	strTemp = "0123456789";
	if (num.length == 0)
		return 0;
	for (i = 0; i < num.length; i++) {
		j = strTemp.indexOf(num.charAt(i));
		if (j == -1) {
			// 说明有字符不是数字
			return 0;
		}
	}
	if (num.charAt(0) == "0" & num.size > 1)
		return 0;
	return 1;
}

/*
 * 函数名：fucCheckInt 功能介绍：检查是否为正整数 参数说明：要检查的数字 返回值：1为是数字，0为不是数字
 */
function fucCheckPlusimp(num) {
	var i, j, strTemp;
	strTemp = "0123456789";
	if (num.length == 0)
		return 0;
	for (i = 0; i < num.length; i++) {
		j = strTemp.indexOf(num.charAt(i));
		if (j == -1) {
			// 说明有字符不是数字
			return 0;
		}
	}
	if (num.charAt(0) == "0" & num.size > 1)
		return 0;
	if (num.substring(0,1)== "0"){ //首位不能为0
		return 0;
	}
	return 1;
}

function fucCheckNUMOnSubmit(name, b) {
	var elements = document.getElementsByName(name);
	var message;
	for (var i = 0; i < elements.length; ++i) {
		if (!isVerifiable(elements[i]))
			continue;
		message = _fucCheckNUM(elements[i].value, b);
		if (message) {
			if (!EP.checkErrorElement)
				EP.checkErrorElement = elements[i];
			return message;
		}
	}
	return;
}

function fucCheckNUM(str, b) {
	return fucCheckNUMimp(str);
}

/*
 * 数字验证提示函数
 */
function _fucCheckNUM(num, b) {
	var ero = b;
	var str = num;
	var c = fucCheckIntimp(str);
	if (c == 0) {
		return ero;
	}
	return;
}

function fucCheckIntOnSubmit(name, b) {
	var elements = document.getElementsByName(name);
	var message;
	for (var i = 0; i < elements.length; ++i) {
		if (!isVerifiable(elements[i]))
			continue;
		message = _fucCheckInt(elements[i].value, b);
		if (message) {
			if (!EP.checkErrorElement)
				EP.checkErrorElement = elements[i];
			return message;
		}
	}
	return;
}

function fucCheckInt(name, b) {
	return _fucCheckInt(EP.getTarget().value, b);
}

function _fucCheckInt(num, b) {
	var ero = b;
	var str = num;
	var c = fucCheckNUMimp(str);
	if (c == 0) {
		return ero;
	}
	return;
}

/*
 * 函数名：fucCheckNUM 功能介绍：检查是否为数字 参数说明：要检查的数字 返回值：1为是数字，0为不是数字
 */
function fucCheckNUMimp(num) {
	var i, j, strTemp;
	strTemp = "0123456789";
	if (num.length == 0)
		return 0;
	for (i = 0; i < num.length; i++) {
		j = strTemp.indexOf(num.charAt(i));
		if (j == -1) {
			return 0;
		}
	}
	return 1;
}

/*
 * 函数名：fucCheckPostcode 功能介绍：检查是否为邮编 参数说明：要检查的数字 返回值：1为是邮编，0为不是邮编
 */
function fucCheckPostcodeimp(postCode) {
	var i, j;
	if (fucCheckNUMimp(postCode) == 0)
		return 0;
	if (postCode.length != 6)
		return 0;
	return 1;
}

function fucCheckPostcodeOnSubmit(name, b) {
	var elements = document.getElementsByName(name);
	var message;
	for (var i = 0; i < elements.length; ++i) {
		if (!isVerifiable(elements[i]))
			continue;
		message = _fucCheckPostcode(elements[i].value, b);
		if (message) {
			if (!EP.checkErrorElement)
				EP.checkErrorElement = elements[i];
			return message;
		}
	}
	return;
}

function fucCheckPostcode(name, b) {
	return _fucCheckPostcode(EP.getTarget().value, b);
}

/*
 * 邮编验证提示函数
 */
function _fucCheckPostcode(postCode, b) {
	var ero = b;
	var str = postCode;
	var c = fucCheckPostcodeimp(str);
	if (c == 0) {
		return ero;
	}
	return;

}

function fucCheckTELOnSubmit(name, b) {
	var elements = document.getElementsByName(name);
	var message;
	for (var i = 0; i < elements.length; ++i) {
		if (!isVerifiable(elements[i]))
			continue;
		message = _fucCheckTEL(elements[i].value, b);
		if (message) {
			if (!EP.checkErrorElement)
				EP.checkErrorElement = elements[i];
			return message;
		}
	}
	return;
}

function fucCheckTEL(name, b) {
	return _fucCheckTEL(EP.getTarget().value, b);
}

/*
 * 函数名：fucCheckTEL 功能介绍：检查是否为电话号码 参数说明：要检查的字符串 返回值：1为是合法，0为不合法
 */
function _fucCheckTEL(TEL, b) {
	var ero = b;
	var i, j, strTemp;
	strTemp = "0123456789-()# ";
	for (i = 0; i < TEL.length; i++) {
		j = strTemp.indexOf(TEL.charAt(i));
		if (j == -1) {
			return ero;
		}
	}
	// 明合法
	return;
}

/*
 * 函数名：fucCheckLength 功能介绍：检查字符串的长度 参数说明：要检查的字符串 返回值：长度值
 */
function fucCheckLength(strTemp) {
	var i, sum;
	sum = 0;
	for (i = 0; i < strTemp.length; i++) {
		if ((strTemp.charCodeAt(i) >= 0) && (strTemp.charCodeAt(i) <= 255))
			sum = sum + 1;
		else
			sum = sum + 2;
	}
	return sum;
}

/*******************************************************************************
 * * 日 期:2002-7-18 * 修改人: * 日 期: * 描 述:公用函数 * 版 本:V1.0
 ******************************************************************************/
/*******************************************************************************
 * * 函数 名: isBlank * 输 入: value 需要操作的字符串 * * 输 出：true是空，false是不为空 * 功能描述:检查值是否为空 *
 * 修改 人: * 日 期:
 ******************************************************************************/
function Is_Null(value) {
	var j = 0;
	var Text = "";
	Text = value;
	if (Text.length) {
		for (var i = 0; i < Text.length; i++) {
			if (Text.charAt(i) != " ")
				j = j + 1;
		}

		if (j == 0) {
			Ret = true;
		} else {
			Ret = false;
		}
	} else
		Ret = true;
	return (Ret);
}

/*
 * 本函数用于对sString字符串进行空格截除
 */
function JsTrim(sString) {
	var sTemp = "";
	sTemp = JsRTrim(JsLTrim(sString));
	return sTemp;
}

/*
 * 本函数用于对sString字符串进行后空格截除
 */
function JsRTrim(sString) {
	var sStr, i, sResult = "", sTemp = "";
	if (sString.length == 0) {
		return "";
	}
	sStr = sString.split("");
	for (i = sStr.length - 1; i >= 0; i--) // 将字符串进行倒序
	{
		sResult = sResult + sStr[i];
	}
	sTemp = JsLTrim(sResult); // 进行字符串前空格截除
	if (sTemp == "") {
		return "";
	}

	sStr = sTemp.split("");
	sResult = "";
	// 将经处理后的字符串再进行倒序
	for (i = sStr.length - 1; i >= 0; i--) {
		sResult = sResult + sStr[i];
	}
	return sResult;
}

/*
 * 本函数用于对sString字符串进行前空格截除
 */
function JsLTrim(sString) {
	var sStr, i, iStart, sResult = "";

	sStr = sString.split("");
	iStart = -1;
	for (i = 0; i < sStr.length; i++) {
		if (sStr[i] != " ") {
			iStart = i;
			break;
		}
	}
	if (iStart == -1) {
		return "";
	} // 表示sString中的所有字符均是空格,则返回空串
	else {
		return sString.substring(iStart);
	}
}

/*
 * 函数名：checkStringCompose 功能介绍：检查InputString是否由ComposeString组成 参数说明：要检查的字符串
 * 返回值：1为是由ComposeString组成，0为不是由ComposeString组成
 */
function checkStringCompose(InputString, ComposeString) {
	var i, j;
	if (InputString.length == 0)
		return 0;
	for (i = 0; i < InputString.length; i++) {
		j = ComposeString.indexOf(InputString.charAt(i));
		if (j == -1) {
			return 0;
			// 说明有字符不是由ComposeString中的字符组成
		}
	}
	// 说明是由ComposeString中的字符组成
	return 1;
}

/*
 * 函数名：checkDigitBit 功能介绍：检查InputString由几位小数 参数说明：要检查的字符串 返回值：小数位数
 */
function checkDigitBit(InputString) {
	var i, j;
	j = 0;
	// 如果字符串不是由数字和小数点组成的则返回-1
	if (checkStringCompose(InputString, '0123456789.') == 0) {
		return -1;
	} else {
		for (i = 0; i < InputString.length; i++) {
			if (InputString.charAt(i) == '.')
				j++;
			// 如果小数点的个数>1个则返回-1
			if (j > 1) {
				return -1;
			}
		}
		// 如果字符串是整数则返回0
		if (checkStringCompose(InputString, '0123456789') == 1) {
			return 0;
		} else {
			i = InputString.lastIndexOf(".");
			i = InputString.length - i - 1;
			return i;
		}
	}
}

/*
 * 函数名：checkMoney 功能介绍：检查InputString是否是钱数 参数说明：要检查的字符串 返回值：是返回1,不是返回-1
 */
function checkMoneyimp(InputString) {
	var i, j, k;
	j = 0;
	k = -2;
	// 如果字符串不是由数字和小数点组成的则返回-1
	if (checkStringCompose(InputString, '0123456789.') == 0) {
		return -1;
	} else {
		for (i = 0; i < InputString.length; i++) {
			if (InputString.charAt(i) == '.') {
				j++;
				k++;
			}
			if (k != -2)
				k++;
			// 如果小数点的个数>1个则返回-1
			if (j > 1) {
				return -1;
			}
			// 如是小数位数>2
			if (k > 2) {
				return -1;
			}
		}
		return 1;
	}
}

function checkMoneyOnSubmit(name, b) {
	var elements = document.getElementsByName(name);
	var message;
	for (var i = 0; i < elements.length; ++i) {
		if (!isVerifiable(elements[i]))
			continue;
		message = _checkMoney(elements[i].value, b);
		if (message) {
			if (!EP.checkErrorElement)
				EP.checkErrorElement = elements[i];
			return message;
		}
	}
	return;
}

function checkMoney(name, b) {
	return _checkMoney(EP.getTarget().value, b);
}

/*
 * 钱数验证提示函数
 */
function _checkMoney(InputString, b) {
	var ero = b;
	var str = InputString;
	var c = checkMoneyimp(str);
	if (c == -1) {
		return ero;
	}
	return;
}

/*
 * 一个判断日期大小，sDate代表起始时间，eDate代表结束时间，如果eDate大于sDate，返回true
 */
function opinionDate(sDate, eDate) {
	startDate = sDate;
	endDate = eDate;
	startMark1 = startDate.indexOf("-");
	startYear = startDate.substring(0, startMark1);
	startDate = startDate.substring(startMark1 + 1, startDate.length);
	startMark2 = startDate.indexOf("-");
	startMonth = startDate.substring(0, startMark2);
	startDate = startDate.substring(startMark2 + 1, startDate.length);
	startDay = startDate;

	endMark1 = endDate.indexOf("-");
	endYear = endDate.substring(0, endMark1);
	endDate = endDate.substring(endMark1 + 1, endDate.length);
	endMark2 = endDate.indexOf("-");
	endMonth = endDate.substring(0, endMark2);
	endDate = endDate.substring(endMark2 + 1, endDate.length);
	endDay = endDate;

	if (startMonth.substring(0, 1) == 0) {
		startMonth = startMonth.substring(1, 2);
	}
	if (endMonth.substring(0, 1) == 0) {
		endMonth = endMonth.substring(1, 2);
	}
	if (startDay.substring(0, 1) == 0) {
		startDay = startDay.substring(1, 2);
	}
	if (endDay.substring(0, 1) == 0) {
		endDay = endDay.substring(1, 2);
	}
	if (parseInt(endYear) < parseInt(startYear)) {
		return false;
	} else if ((parseInt(endYear) == parseInt(startYear))) {

		if (parseInt(startMonth) < parseInt(endMonth)) {
			return true;
		} else if (parseInt(startMonth) > parseInt(endMonth)) {
			return false;
		} else if (parseInt(startMonth) == parseInt(endMonth)) {
			if (parseInt(startDay) > parseInt(endDay)) {
				return false;

			}
		}
	}

	return true;

}

/*
 * 函数名：checkFileExt 功能介绍：检查文件扩展名 参数说明：要检查的字符串 返回值：文件扩展名,扩展名有误时返回error
 */
/*
 * Edited By Ty, 2003-03-26 修改原因：把得到的扩展名强制转换成小写。
 */
function checkFileExt(InputString) {
	var i;
	var tmpString;
	// 如果为空字符串则返回error
	if (InputString == null)
		return 0;
	else {
		// 如果为字符串长度<4则返回error
		if (InputString.length <= 4)
			return 0;
		else {
			i = InputString.lastIndexOf(".");
			tmpString = InputString.substring(i + 1, InputString.length);
			i = tmpString.length;
			if (i == 3 || i == 4) {
				return tmpString.toLowerCase();
			} else {
				return 0;
			}
		}
	}
}

/*
 * 判断一个字符串是否是英文+数字组成
 */
function isEnglish(inputString) {
	var regexp = /^[a-zA-Z0-9_-].*$/;
	if (!regexp.test(inputString)) {
		return 0;
	}
	return 1;
}

function isPicture(picpath) {
	if (checkFileExt(picpath) == "gif")
		return 1;
	else if (checkFileExt(picpath) == "bmp")
		return 1;
	else if (checkFileExt(picpath) == "jpg")
		return 1;
	else
		return 0;
}

function chkMobileTelOnSubmit(name, b) {
	var elements = document.getElementsByName(name);
	var message;
	for (var i = 0; i < elements.length; ++i) {
		if (!isVerifiable(elements[i]))
			continue;
		message = _chkMobileTel(elements[i].value, b);
		if (message) {
			if (!EP.checkErrorElement)
				EP.checkErrorElement = elements[i];
			return message;
		}
	}
	return;
}

function chkMobileTel(name, b) {
	return _chkMobileTel(EP.getTarget().value, b);
}

/*
 * 手机验证功能 0为不是手机号码
 */
function _chkMobileTel(inputStr, b) {
	var a = b;
	var regexp = /^1[3,5]\d{9}$/;
	if (!regexp.test(inputStr)) {
		return a;
	}
	return;
}

function chkStringOnSubmit(name, ispc, isnull, Max, Min, b) {
	var elements = document.getElementsByName(name);
	var message;
	for (var i = 0; i < elements.length; ++i) {
		if (!isVerifiable(elements[i]))
			continue;
		message = _chkString(elements[i].value, ispc, isnull, Max, Min, b);
		if (message) {
			if (!EP.checkErrorElement)
				EP.checkErrorElement = elements[i];
			return message;
		}
	}
	return;
}

function chkString(name, ispc, isnull, Max, Min, b) {
	return _chkString(EP.getTarget().value, ispc, isnull, Max, Min, b);
}

/*
 * 验证字符串
 */
function _chkString(inputString, ispc, isnull, Max, Min, b) {
	var a = inputString;
	if (isnull) {
		if (Is_Null(a)) {
			return b;
		}
	}
	if (ispc) {
		var c = chkspc(a);
		if (c == 0) {
			return b;
		}
	}
	var length = fucCheckLength(a);
	if ((Min <= length) && (length <= Max)) {
		return;
	} else {
		return b;
	}
	return;
}

function checkDbInput(isnull, name, Max, isnum, isdate) {
	var elements = document.getElementsByName(name);
	var message;
	for (var i = 0; i < elements.length; ++i) {
		if (!isVerifiable(elements[i]))
			continue;
		message = _checkDbInput(isnull, elements[i].value, Max, isnum, isdate);
		if (!message) {
			if (!EP.checkErrorElement)
				EP.checkErrorElement = elements[i];
			return message;
		}
	}
	return;
}

function checkDbInput(isnull, name, Max, isnum, isdate) {
	return _checkDbInput(isnull, EP.getTarget().value, Max, isnum, isdate);
}

function _checkDbInput(isnull, inputString, Max, isnum, isdate) {

	var a = inputString;

	if (!isnull) {
		if (Is_Null(a)) {
			return "\u5b57\u7b26\u4e0d\u80fd\u4e3a\u7a7a";
		}
	}
	if (isnum) {
		var test = fucCheckIntimp(a);
		if (test == 0) {
			return "\u8bf7\u8f93\u5165\u6574\u6570";
		}
	}

	if (isdate) {
		var t = chkdateimp(a);
		if (t == 0) {
			return "\u8f93\u5165\u65e5\u671f\u683c\u5f0f\u9519\u8bef";
		}
	}
	var length = fucCheckLength(a);
	if ((length <= Max)) {
		return;
	} else {
		return "\u5b57\u7b26\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc7" + Max;
	}
}

function customOnSubmit(name, reg, b) {
	var elements = document.getElementsByName(name);
	var message;
	for (var i = 0; i < elements.length; ++i) {
		if (!isVerifiable(elements[i]))
			continue;
		message = checkByReg(elements[i].value, reg, b);
		if (message) {
			if (!EP.checkErrorElement)
				EP.checkErrorElement = elements[i];
			return message;
		}
	}
	return;
}

function custom(name, reg, b) {
	return checkByReg(EP.getTarget().value, reg, b);
}

/*
 * 根据自定义正则判断字符串是否正确
 */
function checkByReg(inputString, reg, b) {
	var regexp = reg;
	if (!regexp.test(inputStr)) {
		return a;
	}
	return;
}

function validateForm(str) {
	var funs = str.split(";");
	for (var i = 0; i < funs.length; ++i) {
		if (funs[i] != "" && eval(funs[i]) == 0) {
			return false;
		}
	}
	return true;
}

function StringBuilder() {
	this.__string__ = new Array();
}

StringBuilder.prototype.append = function(str) {
	this.__string__.push(str);
}

StringBuilder.prototype.toString = function() {
	return this.__string__.join("");
}

StringBuilder.prototype.size = function() {
	return this.__string__.length;
}

function validateSinoForm(str) {
	var funs = str.split(";");
	var sb = new StringBuilder();
	var message;
	for (var i = 0; i < funs.length; ++i) {
		if (funs[i] != "") {
			var new_fun = funs[i].replace(/([^(]*)/, "$1OnSubmit");
			if (typeof(this[new_fun.substring(0, new_fun.indexOf('('))]) == "function") {
				message = eval(new_fun);
			} else {
				message = eval(funs[i]);
			}
			if (message) {
				sb.append(message);
				sb.append('\n');
			}
		}
	}
	if (sb.size() > 0) {
		showValidateMessage(sb.toString());
		if (EP.checkErrorElement) {
			setSinoFormFocus(EP.checkErrorElement);
			EP.checkErrorElement = null;
		}
		return false;
	} else {
		return true;
	}
}

function completeFormValidateMessage(message) {
	return "\u8868\u5355\u9a8c\u8bc1\u9519\u8bef\uff1a" + message;
}

function showFormValidateMessage(message) {
	if (message) {
		setSinoFormFocus(EP.getTarget());
		showValidateMessage(message);
	}
}

function showValidateMessage(message) {
	$.dialog.alert(message,function(){
	   return;
	});
	// var tar = EP.getTarget();
	// var p = $(tar).parent();
	// if (p.attr('tagName') == 'DIV' && p.attr('class') == 'jqimessage') {
	// if(message){
	// $('div.jqimessage > br:last').after(message + '<br/>');
	// $(tar).addClass('formMessageError');
	// }else{
	// $(tar).removeClass('formMessageError');
	// }
	// } else {
	// if (message) {
	// EP.ele = EP.getTarget();
	// var txt = message + '<br/>Try again:' + $(tar).outerHtml();
	// $.prompt(txt, {
	// submit: mysubmitfunc,
	// buttons: {
	// Ok: true
	// }
	// });
	// $("div.jqimessage").children("#" +
	// EP.ele.id).addClass('formMessageError');
	// }
	// }
}

function setSinoFormFocus(target) {
	target.focus();
	target.select();
}

function isVerifiable(el) {
	if (el.readOnly || el.disabled) {
		return false;
	} else {
		return true;
	}
}

function mysubmitfunc(v, m, f) {
	an = m.children('#' + EP.ele.id);
	if (an.hasClass('formMessageError')) {
		return false;
	} else {
		$(EP.ele).val(an.val());
		return true;
	}
}
