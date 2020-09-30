/**
 * 重写ajax complete方法，解决ajax重定向问题
 * 注：如果业务代码中重写了complete方法，这段代码将失效
 */
$.ajaxSetup({
    complete:function (XMLHttpRequest,statusCode) {
        if("REDIRECT" == XMLHttpRequest.getResponseHeader("REDIRECT")){
            var win = window;
            while (win != win.top){
                win = win.top;
            }
            win.location.href = XMLHttpRequest.getResponseHeader("gotoURL");
        }
    }
});


//加载网站导航 start
$(document).ready(function(){

	refreshMSG();//刷新个人信息
	
	//检查是否是一人多岗
    checkDeptSize(function(data){
    	if(data.flag){
            setCookie("deptSize","1");//设置Cookeie,表示是一人多岗
            checkDept(true);
		}else{
            if(getcookie("deptSize") != "1"){
                setCookie("deptSize","0");//设置Cookeie,表示不是一人多岗
            }
		}
	});

	if(getcookie("deptSize") != "1"){
        $(".userbox").removeAttr("href");
		$(".userbox").attr("title","");
	}

	//填充首页用户信息
	setUserDept();

    // 切换新版本
    $("#btnNewVersion").click(function(){
        var activeMenu = $('#left_ul .active');
        var activeMenuId = '';
        // 判断是否在二级页面内，如果在二级页面就赋值菜单id
        if (activeMenu && activeMenu.length>0) {
            activeMenuId = '?id=' + activeMenu.attr('id');
		}
        window.location.href=window.localhostPaht + '/dist/index.html'+ activeMenuId;
    });

	//注销
	$("#logout").click(function(){
	    doLogOut();
	});
	
	//切换用户
	$(".userbox").on("click",function(){
		if(getcookie("deptSize") == "1"){
			checkDept(false);
		}
	})

});

//cookie信息同步完成后，刷新个人信息
function refreshMSG(){ 
	//用户信息
	//getResourceUrl();
	getUserMessage();
	//获取当前在线用户数
	getUserSize();
	
	//获取用户头像
	getUserImg();
}

/**
 * 获取当前在线用户数
 */
function getUserSize(){
	$.ajax({
        type: "get",
        url: "/user/getUserSize",
        success: function(data) {
        	console.log(data.userSize);
        	$("#userSize").html(data.userSize);
        },
        error:function(){
        	$("#userSize").html('0');
        }
	});
}

/**
 * 检查用户所在部门个数，
 * 如果不存在一人多岗，则直接将人员部门信息存入cookie
 * @returns {Boolean}
 */
function checkDeptSize(callback){
	$.ajax({
        type: "POST",
        url: "/user/checkDeptsSize",
        success: function(data) {
        	if(callback){
                callback(data);
			}
        },error: function() {
        	window.location.reload();
		}
	});
}

/**
 * 查询用户信息，存到cookie中
 */
function setUserInfoToCookie(){
	$.ajax({
        type: "POST",
        url: "/user/setUserInfoToCookie",
        async: false,
        success: function(data) {
        	var flag = data.flag;
        	if(!flag){
                doLogOut();
			}
        },
        error:function(){
            doLogOut();
		}
	});
}

/**
 * 一人多岗时，选择登录部门
 * @param isReset 是否在点击“取消”或关闭时，重置session和cookie
 */
function checkDept(isReset){
	layer.open({
        id:"chooseSerachall",
        type: 2,
        content: "/modules/dept/checkDept.html",
        area: ['520px', '300px'],
        title: ["选择岗位", 'font-size:16px;font-weight:bold;'],
        offset:['150px'],
        cancel: function(index, layero){
            if(isReset){
                layer.msg('请选择岗位');
                return false;
            }
        },
        success:function (layero,index) {
            layer.iframeAuto(index);
            var body = layer.getChildFrame('body', index);
            $(body).find('#depts-list li').click(function () {
                var deptid = $(this).attr('data-deptid');
                var deptnm = $(this).attr('data-value');
                var json = {
                    deptid:deptid,
                    deptnm:deptnm
                };
                setSession(json.deptid,function(json){
                    if(json.result == "success"){
                        window.location.reload();//刷新当前页面
                        layer.close(index);
                    }else{
                        doLogOut();
                    }
                });
            })
        }
    })
}

/*
 * 一人多岗时存储session信息
 */
function setSession(deptId,callBack){
	$.ajax({
		type: "POST",
		url:"/user/setSession",
		data:{
			deptId:deptId
		},
		success:function(json){
		    if(callBack){
                callBack(json);
            }
		},
		error:function(){
            doLogOut();
		}
	});
}

/**
 *获取用户登录信息
 */
function getUserMessage() {
	$.ajax({
		type: 'post',
		url: '/user/view',
		dataType: "json",
		success: function(data) {
			console.log(data);
			if (data!=null) {
				$('#userInfo').html('您好！' + data.userFullName);
				//填充首页用户信息
				if($("#userName").length > 0) {
				    //元素存在时执行的代码
					$("#userName").html(data.userFullName);
				}
			} else {
                doLogOut();
			}
		},
		error: function() {
            doLogOut();
		}
	});
}

/**
 * 填充门户首页用户信息
 */
function setUserDept(){
	var deptNm = getcookie("deptnm");
	if(deptNm && deptNm != "\"\"") {
	    //元素存在时执行的代码
		$("#userDept").html(getcookie("deptnm"));
	}
}

/**
 * 获取用户头像
 */
function getUserImg(){
	$.ajax({
		type: 'get',
		url: '/user/getUserImg',
		dataType: "json",
		success: function(data) {
			if (data.flag) {
				$("#userImg").prop("src",data.path);
			} else {
				$("#userImg").prop("src","/static/images/login_user.png");
			}
		},
		error: function() {
			$("#userImg").prop("src","/static/images/login_user.png");
		}
	});
}


/**
 * 退出操作
 */
function doLogOut(){
    // window.location.href=Config.sso_logout_url + "?gotoURL=" + path ;
    $.ajax({
        type: 'post',
        url: '/system/login/logout',
        complete:function (XMLHttpRequest,statusCode) {
            if("REDIRECT" == XMLHttpRequest.getResponseHeader("REDIRECT")){
                var win = window;
                while (win != win.top){
                    win = win.top;
                }
                win.location.href = XMLHttpRequest.getResponseHeader("gotoURL");
            }
        }
    });
}

