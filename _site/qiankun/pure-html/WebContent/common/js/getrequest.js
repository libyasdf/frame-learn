
function toView(url){
    window.open(encodeURI("url"));  
}

/**
 * 获取模块资源ID
 * @returns {*}
 * @constructor
 */
function GetMenuResId(){
    var val=$.trim($("#tab_menu .tab_menu_acitve").attr("id"));
    if(!val){
        val=$.trim($('#left_ul a.active').attr("id"));//
    }
    return val;
}

/**
 * 获取资源URL
 * @returns {*}
 * @constructor
 */
function GetMenuUrl(){
    var val=$.trim($("#tab_menu .tab_menu_acitve").attr("url"));
    if(!val){
        val=$.trim($("#left_ul").find("a.active").attr("url"));
    }
    return val;
}

/**
 *  获取HTML或JSP传递的参数：请求路径 test/list.html?id=1001&name=test
 *  页面获取参数:
 *   var Request = GetRequest();
 *   var id = Request['id'];
 *   var name = Request['test'];
 */
function GetRequest() { 
	var url = decodeURI(location.search); //获取url中"?"符后的字串
	var theRequest = new Object(); 
	if (url.indexOf("?") != -1) {
		var str = url.substr(1); 
		strs = str.split("&"); 
		for(var i = 0; i < strs.length; i ++) { 
			theRequest[strs[i].split("=")[0]] = strs[i].split("=")[1]; 
		} 
	} 
	return theRequest; 
}

//获取url中的参数
function GetParameter(url) {
	// 档案管理-兼容新版本处理-获取url
    if (!url){  // 兼容新版本处理—新版本获取当前菜单url
        var activeMenuId = $('#breadcrumb li:last-child').attr('data-crumb-id');
        url = $('#menu_wrapper li[data-menu-id="'+ activeMenuId +'"]').attr('data-url');
    }
    url ? url=url : url = url || window.location.href;
	url = url.substr(url.indexOf("?"),url.length); 
	url = decodeURI(url); //获取url中"?"符后的字串
	var theParameter = new Object(); 
	if (url.indexOf("?") != -1) { 
		var str = url.substr(1); 
		strs = str.split("&"); 
		for(var i = 0; i < strs.length; i ++) { 
			theParameter[strs[i].split("=")[0]] = strs[i].split("=")[1]; 
		}
	} 
	return theParameter; 
} 

var basePath = getBasePath();
var path = getPath();


/**
 * 获取项目名
 * 形如：/trade
 */
function getBasePath(){ 
	 var pathName = document.location.pathname; 
	 var index = pathName.substr(1).indexOf("/"); 
	 var result = pathName.substr(0,index+1); 
	 return ""; 
} 

/**
 * 获取路径加项目名
 * 形如：http://localhost:8080/trade
 */
function getPath(){  
	 return window.location.protocol +"//"+ window.location.host + getBasePath(); 
} 

/**
 * 截取指定长度的字符
 */
function subNote(str,length){
	//if(str&&typeof(str)!="undefined"){
	if(!length){
		length = 9;
	}
	if(str){
		//需要处理
		//第一步判断长度
		if(str.length>length){
			//截取前十个字符然后再加两个。。
			var str1=str.substring(0,length);
			str1+="...";
			return str1;
		}else{
			return str;
		}
	}else{
		//直接返回空的字符串
		return "";
	}
}
/**
 * textarea过滤js中的<>
 * @param value
 */
function formatContent(value){
	value = value.replace(/</g,"&lt;");
	value = value.replace(/>/g,"&gt;");
	return value;
}