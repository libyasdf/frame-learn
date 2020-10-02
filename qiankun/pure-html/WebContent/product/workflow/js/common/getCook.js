/**
 * 
 */
/**
 * 
 */
// 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
var curWwwPath = window.document.location.href;
// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
// 获取主机地址，如： http://localhost:8083
var localhostPaht = curWwwPath.substring(0, pos);
// 获取带"/"的项目名，如：/uimcardprj
var projectName = pathName.substring(0,
		pathName.substr(1).indexOf('/') + 1);
// 访问前缀路径
var BASEPATH = "";
var basePath = "";
var WIDGET_COUNTER = "widgetcounter";
var widgetcounter = 0;
var USERID = "userid";
var USERNM = "usernm";
var ORGID = "orgid";
var SYSID = "sysid";
//写cookies
function setCookie(name,value){
	var Days = 30;
	var exp = new Date();
	exp.setTime(exp.getTime() + Days*24*60*60*1000);
	document.cookie = name + "="+ encodeURI (value) + ";expires=" + exp.toGMTString()+";path=/";
}

/**
 * session中存放人员信息的key值集合
 * @type {string[]}
 */
var cookieName = ['userid','usernm','sysid','orgid','deptid','deptname','deptnm','orgName','chuShiId','chuShiName','juId','juName','rolesNo','sysRoleIds','sysRoleNos'];

//获取指定名称的cookie的值
function getcookie(objname){
	if(cookieName.indexOf(objname) >= 0){
        return getRoleInfo(objname);
	}
	var arrstr = document.cookie.split("; ");
	for(var i = 0;i < arrstr.length;i ++){
		var temp = arrstr[i].split("=");
		if(temp[0] == objname){
			if (objname == "deptnm") {
				var deptnm = decodeURI(temp[1]);
				return deptnm.replace(/%2F/g, "/");
			}else{
				return decodeURI(temp[1]);	
			}
		}
	}
}

/**
 * 与后台交互获取当前登录的用户角色信息
 */
function getRoleInfo(objname) { 
	var _now_userinfo = "";
	$.ajax({
		   type: "GET",
		   async: false,
           url: "/user/userRolesInfo",
		   data: {
			   infoName: objname
		   },
		   dataType: "json",
           cache:false,  // 禁用缓存
		   ifModified:true,
		   success: function(data){
			   _now_userinfo = data.info;
		   },
		   error : function(error) {
			   _now_userinfo = "get role info error！";
		   }
	});
	return _now_userinfo; 
}

//删除cookies
function delCookie(name){
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval=getcookie(name);
	if(cval!=null)
	document.cookie= name + "="+cval+";expires="+exp.toGMTString()+";path=/";
}


//==========================
//kjx 20160623
//跨越问题cookie获取信息
//==========================

var syncCookieFlag = false;//是否同步完成

/* 通过后台同步cookie
*key cookie的name
*val cookie的value
成功返回true  失败返回false
*/
function syncCookie(status,val){
	$.ajax({
			type: 'post',
			url: basePath + '/cookie/sync',
			async: false,
			data:{
				status:status,
				cookie:val
			},
			success: function() {
				syncCookieFlag = true;
			},
			error:function(){
				console.log("cookie sync error!");    
			}
	});
}

/**
 * 赋值给公共值
 * @param json {key:value,key:value}
 */
function assignCookieToHidden(json){
	$.ajax({
	     type: method,
	     url: url,
	     data:{authorType:"1"},
	     dataType:"json",
	     success: function(datas){
	 		if(datas){
	 			if(datas!=null){
	 			$.each(json,function(name,value) {
	 				$("#"+name).val(datas[value]);
	 				});
	 			}
			}
		}
	 });
}

/**
 * 赋值给公共值
 * @param json {key:value,key:value}
 */
function getCookiesToHidden(json){
	$.each(json,function(name,value) {
		$("#"+name).val(getcookie(value));
	});
}

/**
 * 跳转更多页面
 */
function goMoreModel(flag){
	var resourceId = Util.queryString('resourceId');
	var resourceName =  Util.queryString('resourceName');
	var url = "./moreModel.html?resourceId="+resourceId+"&resourceName="+encodeURI(resourceName)+"&flag="+flag;
	window.location.href=url;
}


//加载页面时 在form中 增加隐藏域 
//参数为数组 例如 [{"sysid":"sysId"},{"orgid":"orgId"},{"usernm":"usernm"},{"userid":"userid"}]
//数组中对象的key 为cookie的key值  ； 对象的value值为name属性值
function appendCookieValueToFormHidden(hiddens) {
	if (hiddens && hiddens.length < 1) {
		return;
	}
	for (var i = 0; i < hiddens.length; i++) {
		var hidden = hiddens[i];
		for ( var a in hidden) {
			var $inputHidden = $('<input type="hidden">').attr({
				"name" : hidden[a],
				"value" : getcookie(a)
			});
			$('form').eq(0).prepend($inputHidden);
		}
	}
} 
