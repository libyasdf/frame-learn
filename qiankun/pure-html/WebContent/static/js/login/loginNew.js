$(document).ready(function() {
	
	//证书登录
	$("#CA_login").click(function(){
		ca_login();
	});
	
	//清除浏览器中所有的cookie
	clearAllCookie();

});

/**
 * 清除所有cookie
 */
function clearAllCookie(){
	$.ajax({
		url:"/user/clearAllCookie",
		dataType:"json",
		success:function(json){
		}
	})
}

/**
 * 证书登录地址
 */
function ca_login() {
	window.location.href=Config.sso_login_url + "?gotoURL=" + path + "/main.html"
}
			
			