/*ajax请求封装*/
function httpRequest(method,url,data,callback,errorCallback){
	$.ajax({
	     type: method,
	     url: url,
	     data:data,
	     dataType:"json",
	     cache:false,
	     async: false,
	     success: function(datas){
	 		if(datas){
	 			if(callback){
	 				callback(datas);
	 			}
			}
		},
		error:function(){
			if(errorCallback){
				errorCallback();
			}
		}
	 });
}


/*公用编辑时数据写入调后台端（通过Name 与  后台字段对应）
 * 调用 ：editFunction(url,datas,flag)
 * 参数1（必填）： url  后台请求地址
 * 参数2（必填）： datas  后台请求需要的参数
 * 参数3(选填)
 * */
function editFunction(url,datas,oper,callback){
	httpRequest("POST",url,datas,function(data){
		var str="";
		$("#idea").val(data.tempIdea);
		var arr = "";
		if(callback){
			callback(data);
		}
		if(oper=="NEW"){
			arr = data;
		}else if(oper=="EDIT"){
			arr = data.flowData.buttonVec;
			var ideas = JSON.parse(data.formalIdea);
			var subIdeaType = "";
			var coverMode = "";
			var fillmode = "";
			wfleveType = data.wfleveType;
			for (var i = 0; i < data.flowData.purivewVec.length; i++) {
				if($("#"+data.flowData.purivewVec[i].attributeEn+"").attr("attrType")=="select"||$("#"+data.flowData.purivewVec[i].attributeEn+"").attr("attrType")=="button"||$("#"+data.flowData.purivewVec[i].attributeEn+"").attr("attrType")=="textarea"){
					if(data.flowData.purivewVec[i].purivewName=="readonly"||data.flowData.purivewVec[i].purivewName=="readOnly"){
						$("#"+data.flowData.purivewVec[i].attributeEn+"").attr("disabled","readonly");
					}else if($("#"+data.flowData.purivewVec[i].attributeEn+"").attr("attrType")=="textarea"&&data.flowData.purivewVec[i].purivewName=="write"){
						if(data.flowData.ideaFieldVec[i]){
							if(data.flowData.ideaFieldVec[i].fillmode==2){
								$("#"+data.flowData.purivewVec[i].attributeEn).attr("CK_TYPE","NotEmpty,MaxLen_2000");
							}
						}
						$("."+data.flowData.purivewVec[i].attributeEn+"").css("display","block");
					}else if(data.flowData.purivewVec[i].purivewName=="write"||data.flowData.purivewVec[i].purivewName=="Write"){
						//显示
						$("#"+data.flowData.purivewVec[i].attributeEn+"").css("display","block");
					}else{
						$("#"+data.flowData.purivewVec[i].attributeEn+"").attr(data.flowData.purivewVec[i].purivewName,data.flowData.purivewVec[i].purivewName);
					}
				}else if($("#"+data.flowData.purivewVec[i].attributeEn+"").attr("attrType")=="radio"||$("#"+data.flowData.purivewVec[i].attributeEn+"").attr("attrType")=="checkbox"){
					var radiosName = $("#"+data.flowData.purivewVec[i].attributeEn+"").attr("name") +"Radios";
					$("input[name="+radiosName+"]").attr(data.flowData.purivewVec[i].purivewName,data.flowData.purivewVec[i].purivewName);
				}else{
					$("#"+data.flowData.purivewVec[i].attributeEn+"").attr(data.flowData.purivewVec[i].purivewName,data.flowData.purivewVec[i].purivewName);
				}
				if(data.flowData.ideaFieldVec[i]){
					subIdeaType = data.flowData.ideaFieldVec[i].name;
					coverMode = data.flowData.ideaFieldVec[i].covermode;
					fillmode=data.flowData.ideaFieldVec[i].fillmode;
				}
				if(fillmode==2){
					stateFlag = true;
				}
			}
			//必填
			console.log(ideas);
			for (var j = 0; j < ideas.length; j++) {
				if(!ideas[j].ideaList[0]){
					continue;
				}
				if(ideas[j].name==subIdeaType&&ideas[j].ideaList[0].userid.trim()==getcookie("userid")&&coverMode==2){
					$("#idea").val(ideas[j].ideaList[0].idea);
				}
			}
		}else{
			arr = data.buttonVec;
		}
		if(arr && arr.length > 0){
			for (var i = 0; i < arr.length; i++) {
				console.log(arr[i]);
	    		// 意见
	    		if(arr[i].num == Config.ideaButton){
	    			$("#flowIdea").css("display","block");
	    		}
	    		// 文字流程
	    		if(arr[i].num == Config.courseButton){
	    			$("#flowCourse").css("display","block");
	    		}
	    		// 办结
	    		if(arr[i].num == Config.banjButton){
	    			$("#gd").css("display","block");
	    		}
	    		// 办结无提交
	    		if (arr[i].num == Config.banjNoSubmitButton) {
	    			$("#gd").css("display","block");
	    			$("#subflow").css("display","none");
	    		}
	    		// 撤办
	    		if(arr[i].num == Config.removeButton){
	    			$("#remove").css("display","block");
	    		}
	    		// 恢复撤办
	    		if(arr[i].num == Config.recoveryButton){
	    			$("#resumeFlow").css("display","block");
	    		}
	    		// 收回
	    		if(arr[i].num == Config.takeBackButton){
	    			$("#takeBack").css("display","block");
	    		}
	    		// 审批通过
	    		if(arr[i].num==Config.approvalButton){
	    			$("#approval").css("display","block");
	    		}
	    		// 审批不通过
	    		if(arr[i].num==Config.noApprovalButton){
	    			$("#noApproval").css("display","block");
	    		}
	    		// 退回
	    		if(arr[i].num == Config.returnBackButton){
	    			$("#returnBack").css("display","block");
//		    			$("#returnBtn").attr("onclick","getJumpWflevePassed('"+$("#workitemid").val()+"')");
	    		}
	    		// 增加会签
	    		if(arr[i].num == Config.increasesignatureButton){
	    			$("#increaseSign").css("display","block");
//		    			$("#addSignBtn").attr("onclick","getIncreaseSignature('"+$("#workitemid").val()+"')");
	    		}
	    		// 删除会签
	    		if(arr[i].num == Config.deletingsignatureButton){
	    			$("#deleteSign").css("display","block");
//		    			$("#delSignBtn").attr("onclick","DeletingSignature('"+$("#workitemid").val()+"')");
	    		}
	    		// 保存
	    		if(arr[i].num == Config.saveButton){
	    			$("#save").css("display","block");
	    		}
	    		// 提交
	    		if(arr[i].num == Config.comitButton){
	    			$("#subflow").css("display","block");
	    		}
			}
		}
    	
	});
	//请求和渲染后台数据结束
	
	//datas {flag:"1-走流程 0-不走流程",oper:"edit-编辑 view-已办"} 工作流服务，渲染前台
	//按钮渲染的规则，方案1：隐藏-显示 方案 2：动态加载
	
	//权限角色：Edit状态具体的权限：creDate:hidden/readOnly/wriet(all) 
	//取临时意见服务，意见内容取出来；填写意见的文本框做为fieldName传过去，取的内容需要渲染到页面上
	
	//签收服务Edit状态
	
	
	//保存临时意义（在后台写一个公共的方法供保存调用）
}

/**
 * 获取当前系统时间
 * @param format 返回日期样式
 * @param callback
 * @param errorCallback
 * @return curDate 返回当前时间
 */
function getCurrentDate(format,callback,errorCallback){
	var datas = {"format":format};
	var url = "/system/date/getCurrentDate";
	var curDate = "";
	$.ajax({
	     type:"POST",
	     url: url,
	     data:datas,
	     dataType:"json",
	     async: false,
	     success: function(data){
	    	if(data && data.flag === "1"){
	    		if(data.data){
	    			if(callback){
	    				callback(data.data)
	    			}
	    			curDate = data.data;
	    		}
	    		
	    	}
		},
		error:function(msg){
			if(errorCallback){
				errorCallback();
			}
		}
	 });
	return curDate;
}

/**
 *  获取1970年1月1号0时0分0秒所差的毫秒数
 * @param format 返回日期样式
 * @param callback
 * @param errorCallback
 * @return curDate 返回当前时间
 */
function getCurrentTime(callback,errorCallback){
	var url = "/system/date/getCurrentTime";
	var curDate = "";
	$.ajax({
	     type:"get",
	     url: url,
	     dataType:"json",
	     async: false,
	     success: function(data){
	    	if(data && data.flag === "1"){
	    		if(data.data){
	    			if(callback){
	    				callback(data.data)
	    			}
	    			curDate = data.data;
	    		}
	    		
	    	}
		},
		error:function(msg){
			if(errorCallback){
				errorCallback();
			}
		}
	 });
	return curDate;
}


/**
 * 创建面包屑导航
 * @params BrandMenuId  面包屑导航容器id名称
 * @params viewId  内容视图容器id名称
 */
var breadCrumbInit = function InitBreadCrumb (BrandMenuId,viewId){
    /**
     * 创建面包屑导航方法
     * @params  idName 面包屑导航容器的id名称
     * */
    function createBrandMenu(idName){
        var breadDom = '<div class="normal"></div>';
        $('#'+idName).empty().append(breadDom);
        var BreadCrumb = $('.normal');
        $('.BreadCrumbs').css('border-bottom','none');
        // 插入正常的面包屑导航dom
        for(var i=0; i<2; i++){  // 插入面包屑导航内容
            var menuName;
            if(i==0){  // 获取一级菜单名称
                menuName = $('.dropdown-menu .active').parents('.dropdown').children('a').text();
            }else if(i==1){   // 获取二级菜单
                menuName = $('.dropdown-menu .active a').html();
            }else if(i==2){   // 获取三级菜单
                menuName = $('.left_ul li .active').html();
            }
            // 创建面包屑菜单Dom
            var menu =  '> <span class="BreadCrumbItem">\n' +
                '<a>'+  menuName +'</a>\n' +
                '</span>\n';
            // 插入dom元素
            BreadCrumb.append(menu)
        }
        // console.error('开始插入三级列表');
        var activeMenu = $('.left_ul li .active');
        var activeParents = activeMenu.parents('li');
        var menuArr = [];
        activeParents.each(function(index,element){
            var menu_Name = $.trim($(this).children('a').text());
            // 创建面包屑菜单Dom
            var leftMenu =  '> <span class="BreadCrumbItem">\n' +
                '<a>'+  menu_Name +'</a>\n' +
                '</span>\n';
            menuArr.unshift(leftMenu)
        })
        menuArr.forEach(function(item){
            BreadCrumb.append(item)
        })
        $('.BreadCrumbs').css('border-bottom','1px solid #0073aa');
    }
    /**
     * 加载菜单集合
     * @params viewId 视图容器id
     * @params pid 菜单集合的 id
     * */
    function loadMenuSetData(viewId,pid){
        var menuSetData = [];
        var menuSetId =pid;
        $.ajax({   // 获取菜单集合数据
            url:'recourse/getRecoursesByPid',
            method:'post',
            data:{
                pid:menuSetId
            },
            success:function(res){
                if(res.flag==1){
                    menuSetData = res.data;
                    $('#'+ viewId).load(menuSetData[0].url)  // 默认加载第一个子菜单
                    // 创建菜单合集下拉框
                    var mentSetDom = '<span class="BreadCrumbItem menuSetSelect">\n' +
                        '<select  id="menuSetSelect"></select>\n' +
                        '</span>\n';
                    if($('.menuSetSelect')){  // 如果已存在就先删除再添加菜单合集下拉框dom
                        $('.menuSetSelect').remove();
                        $('.normal').append(mentSetDom);
                    }else{
                        $('.normal').append(mentSetDom)
                    }
                    // 添加菜单合集下拉框的选项
                    menuSetData.forEach(function(menuSetTtem){
                        var menuOption = '<option value='+ menuSetTtem.url +'>'+  menuSetTtem.name +'</option>\n';
                        $('#menuSetSelect').append(menuOption);
                    })

                    // 绑定选择后的事件
                    $('#menuSetSelect').change(function(){
                        var opetionVal = $('#menuSetSelect option:selected').val();
                        opetionVal ? $('#'+ viewId).load(opetionVal) :'';
                    })
                }
            }
        })
        return menuSetData;
    }

    // console.error('********初始化面包屑导航********')
    var activeMenu = $(this);   // 绑定当前点击的菜单

    if($(this).attr('url')=='MenuSet'){   // 如果是点击进来的 当前菜单设为this
        activeMenu = $(this);
    }else{    // 如果不是点击进来的 当前菜单设为 左侧菜单中class为active的菜单
        activeMenu = $("#left_ul").find("a.active");
    }

    // 创建面包屑导航
    createBrandMenu(BrandMenuId);

    // 如果是菜单集合就加载菜单集合数据和dom
    if(activeMenu.attr('url')=="MenuSet")  loadMenuSetData(viewId,activeMenu.attr('id'));

}








/**
 * Tab 菜单标签页 对象    YangQi  By 2019年1月25日14:00:23
 * menuTab.createMenuTab() 创建Tab 菜单标签页方法
 * */

var menuTab = {
    menuData:[],
    tabMenu:{},
    /**
     * 动态插入菜单
     * */
    bingEvent:function (){
        var menuArr = $('#tab_menu li');  // 所有菜单
        var btnLeft = $('#btn_left');
        var btnRight = $('#btn_right');
        var view = $('#right');  // 视图区域 内容显示的地方
        var url = menuArr.eq(0).attr('url');

        /* 绑定菜单缩放事件 */
		$(window).resize(function(ev){
			var tabMenuWidth = $('#tab_menu').width();
			var menuTabWidth = $('#menu_tab_wrapper').width();
			if(menuTabWidth <= tabMenuWidth){
                isShowNavBtn();
			}else{
                isShowNavBtn();
			}
		})
        /*  绑定菜单点击事件  */
        menuArr.click(function (event) {
            var evTarget = event.target;
            menuArr.removeClass('tab_menu_acitve')
            evTarget.classList.add('tab_menu_acitve')
            url  = evTarget.getAttribute('url')
            view.load(url)
            displayCurrentMenu()
        })
        menuArr.eq(0).addClass('tab_menu_acitve')
        // view.load(url)
    },
    fnSuccess:function (menuList) {
        var tab_menu_item;

        menuTab.tabMenu.empty();
        /* 填充tab菜单 */
        menuList.forEach(function(menuItem){
            tab_menu_item = '<li class="tab_menu_item " id='+menuItem.id+' url='+menuItem.url+'  pid='+ menuItem.pid +' >'+ menuItem.name +'</li>'
            menuTab.tabMenu.append(tab_menu_item)
        })
        $('#right').load(menuList[0].url)
        /* 模拟假数据  */
		/*
		var mentArr = [];
		for(var i=0; i<15; i++){
			mentArr.push({ id:1 , url:'abc', pid:'23456',name:'菜单'+i })
		}
        mentArr.forEach(function(menuItem,index){
            tab_menu_item = '<li class="tab_menu_item " id='+menuItem.id+' url='+menuItem.url+'  pid='+ menuItem.pid +' >'+ menuItem.name +'</li>'
            menuTab.tabMenu.append(tab_menu_item)
        })
        */

        menuTab.bingEvent(); // 绑定事件
        isShowNavBtn()  // 初始化菜单左右按钮滚动按钮

    },
    /**
     * 加载菜单集合
     * @params fnSuccess 成功后的回调函数
     * @params pid 菜单集合的 id
     * */
    loadMenuSetData: function(pid,fnSuccess){
        var _this = this;
        var menuSetData = [];
        var menuSetId =pid;

        /* 初始化Tab菜单 */
        var  tabNavWperDom = "<button class=\"tab_btn btn_left\" id=\"btn_left\">\n" +
            "            <i class=\"glyphicon glyphicon-backward\"></i>\n" +
            "        </button>\n" +
            "        <ul class=\"tab_menu\" id=\"tab_menu\">\n" +
            "        </ul>\n" +
            "        <button class=\"tab_btn btn_right\" id=\"btn_right\">\n" +
            "            <i class=\"glyphicon glyphicon-forward\"></i>\n" +
            "  </button> "
        $('#menu_tab_wrapper').empty();


        $.ajax({   // 获取菜单集合数据
            url:'recourse/getRecoursesByPid',
            method:'post',
            data:{
                pid:menuSetId
            },
            success:function(res){
                this.menuData  = res.data;
                if(res.data.length>0){
                    /* console.log('获取tab成功回调函数')
                     console.log(res.data)*/
                    $('#menu_tab_wrapper').append(tabNavWperDom);
                    $('#menu_tab_wrapper').addClass('menu_tab_main');
                    menuTab.tabMenu = $('#tab_menu');
                    fnSuccess( res.data);
                }else{
                    $('#menu_tab_wrapper').removeClass('menu_tab_main');
                    $('.menu_tab_wrapper').hide();
                    $('#menu_tab_wrapper').empty();
                }
            },
            error:function () {
                $('.menu_tab_wrapper').hide();
                $('#menu_tab_wrapper').empty();
            }
        })
        return menuSetData;
    },
    createMenuTab :function (){
        /* 获取当前点击菜单  */
        $('.menu_tab_wrapper').show();
        var activeMenu = $("#left_ul").find("a.active");
        var menuPid = activeMenu.attr('id')  // 获取 pid
        var menuUrl = activeMenu.attr('url');

        if (menuUrl == 'MenuSet')  {
            this.loadMenuSetData(menuPid,this.fnSuccess);
        }else{
            $('.menu_tab_wrapper').hide();
            $('#menu_tab_wrapper').empty();
        }

    }
}






/**************************** 滚动效果 ************************ */
/* 在菜单中显示当前菜单 */

function displayCurrentMenu(){
    var Ul_Wrapper =  $('#menu_tab_wrapper').width();
    var Ul_X = $('#tab_menu')[0].offsetLeft;
    var activeMenu = $('#tab_menu').find('.tab_menu_acitve');
    var activeMenu_X = activeMenu[0].offsetLeft;
    // console.log(activeMenu_X,Ul_X,Ul_Wrapper,Math.abs(Ul_X)+Ul_Wrapper);
    var ul_Left = '';
    // console.log(activeMenu_X,Ul_X,activeMenu.outerWidth(),Math.abs(Ul_X),Math.abs(Ul_X)+Ul_Wrapper)

    if(activeMenu_X >= Math.abs(Ul_X) && activeMenu_X+activeMenu.outerWidth() <= Math.abs(Ul_X)+Ul_Wrapper ){
        console.log('在当前页')
    }else{
        console.log('未在当前页')
        if($('#btn_left').is(':hidden') || $('#btn_right').is(':hidden') ) return;
        $('#tab_menu').css('left', -activeMenu_X+30)
    }
}



/* 初始化菜单左右滚动按钮 */
function isShowNavBtn(){
    var Ul_Wrapper =  $('#menu_tab_wrapper').width();
    var Ul_Width = $('#tab_menu').width();
    // console.error(Ul_Width,Ul_Wrapper)
    if(Ul_Width <= Ul_Wrapper) {
    	 $('#tab_menu').css('padding-left',0)
        $('#btn_left,#btn_right').hide();
    }else{
        $('#btn_right').click(function(){
            clearTimeout(NavScroll.scrollTimer)  // 清除上个定时器
            // 开启一个定时器 防止连续多次点击
            NavScroll.scrollTimer = setTimeout(function(){
                NavScroll.navScroll('left')
            },500)
        })
        $('#btn_left').click(function(){
            clearTimeout(NavScroll.scrollTimer)  // 清除上个定时器
            // 开启一个定时器 防止连续多次点击
            NavScroll.scrollTimer = setTimeout(function(){
                NavScroll.navScroll('right')
            },500)
        })
        $('#btn_left,#btn_right').show()
        $('#tab_menu').css('padding-left','30px');

/*        // 鼠标移入显示切换菜单按钮
        $('#menu_tab_wrapper').mouseenter(function(){
            $('#btn_left,#btn_right').show()
            $('#tab_menu').css('padding-left','30px')
        })
        // 鼠标移出隐藏切换菜单按钮
        $('#menu_tab_wrapper').mouseleave(function(){
        	$('#tab_menu').css('padding-left','0')
            $('#btn_left,#btn_right').hide()
        })
        */
    }
}

// 导航滚动对象
var NavScroll = {
    scrollTimer:'',  // 定时器
    navScroll:function(direction){   // 点击左右按钮滚动方法   direction的值应为left或者right
        var Ul_X =  $('#tab_menu').position().left;
        var Ul_Wrapper =  $('#menu_tab_wrapper').width();
        var Li_width = $('#tab_menu').children('li').outerWidth();
        var Ul_Width = $('#tab_menu').width();
        // console.log(Ul_X,Ul_Wrapper,Ul_Width)
        // console.log(Ul_Width - Math.abs(Ul_X),Ul_Wrapper)
        // console.log(Ul_X,Ul_Wrapper,Ul_Width,Ul_Width - Math.abs(Ul_X))
        if(direction=='left'){
            if(Ul_Width < Ul_Wrapper || Ul_Width - Math.abs(Ul_X) < Ul_Wrapper){
                layer.msg('滚动到最底部！');
                return
            };
            /*if((Ul_X > 0 && Math.abs(Ul_X) < Ul_Wrapper))  {
               console.log('dddd')
                return
            }*/
            $('#tab_menu').css('left',Ul_X-(Ul_Wrapper-Li_width*2))
        }else if(direction=='right'){
            if(Math.abs(Ul_X) < Ul_Wrapper) {
                console.log(Math.floor(Ul_X))
                if(!Math.floor(Ul_X)) {
                    layer.msg('滚动到最顶部！')
                }

                $('#tab_menu').css('left',0)
                return
            };
            if(Ul_X < 0) $('#tab_menu').css('left',Ul_X+(Ul_Wrapper-Li_width*2))
        }
    }

}