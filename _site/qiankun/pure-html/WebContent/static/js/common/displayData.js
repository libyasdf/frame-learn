$(document).ready(function (e) {

    try{
        var theRequest = GetRequest();
        var workItemId = theRequest.workItemId ? theRequest.workItemId : theRequest.workItemId1 ;
        var oper = theRequest.oper;
        if (workItemId && oper == "EDIT"){
            $.ajax({
                url: "/system/waitdo/getWaitdoById",
                data:{"id":workItemId},
                dataType:"json",
                async:false,
                success: function(json){
                    console.log(json);
                    if(json.flag != "1" || !json.data){
                        var index = setInterval(function(){
                            var aip = isAip();
                            if(aip){
                                hideAip();
                                clearInterval(index);
                            }
                        },500);
                        setTimeout(clearInterval(index),5000);
                        //从消息打开，并且待办不可用
                        layer.alert("本条待办已失效！<br>原因：1.您已办理本事项；2.本待办已被申请人撤办；3.本待办已被上一节点收回。",
                            {icon:0,title:'警告',closeBtn:0},
                            function(index){
                                //关闭父窗口
                                // var indexSelf = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                // parent.layer.close(indexSelf); //再执行关闭
                                if(window.name == "" || window.name == null){
                                    window.close();
                                }else{
                                    //当你在iframe页面关闭自身时
                                    var indexSelf = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                    parent.layer.close(indexSelf); //再执行关闭
                                }
                            }
                        )
                    }
                },
                error:function(){}
            })

        }
    }catch (e){

    }
});

/**
 * 根据name查询dom元素
 * @param sname   要查询的name名称
 * @returns {any[]} Array
 */
function getDivsByName(sname){
    var t = document.all;
    var divs = new Array();
    for(var i=0;i<t.length;i++) {
        if(t[i].getAttribute('name') == sname)  {
            divs.push(t[i]);
        }
    }
    return divs;
}

var DisplayData = function () {
   
    /**
     * 页面数据渲染
     */
    var arg = {
        flag:"",	//状态标识，成功1；失败0
        data:null,	//返回的json数据
        msg:"",		//失败的提示信息
        isLoadSeal:true //是否加载通过/不通过的图片
    };
    
    var playData = function (json){
    	var _arg = $.extend(arg, json);
    	var result = _arg.data;
    	var isLoadSeal = _arg.isLoadSeal;
    	if (result && result.flag === "1") {
            var result = result.data;
            //数据回显，这里需要根据需要重写
            for (variable in result) {
            	//添加“通过”或“不通过”的图片
            	/*if(isLoadSeal && variable == "subflag" && $("#opertation").val() == "VIEW"){
            		loadSeal(result[variable]);
            	}*/
                // var ems = document.getElementsByName(variable);
                var ems = getDivsByName(variable);
                var em;
                var tagName;
                if (ems.length > 0) {
                    em = ems[0];
                    tagName = em.tagName;
                } else {
                    continue;
                }

                if (result[variable]) { //是否有该属性
                    var resVar = result[variable];
                    //input输入框
                    if (tagName === "INPUT") {
                        if (em.type === "checkbox") {//checkbox 复选框
                            $.each(ems, function (x, n) {
                                var resArr = resVar.split(",");
                            	$.each(resArr, function (j, jn) {
                                    if (jn === $(n).val()) {
                                        $(n).attr("checked", "checked");
                                    }
                                })
                            })
                        } else if (em.type === "text") { //text文本输入框
                            $(em).val(resVar);
                        } else if (em.type === "radio") { //单选框
                            $.each(ems, function (x, n) {
                                if (resVar === $(n).val()) {
                                    $(n).attr("checked", "checked");
                                }
                            })
                        } else if(em.type === "hidden"){
                        	$(em).val(resVar);
                        }
                    } else if (tagName === "SELECT") {
                        $(em).val(resVar);
                    } else if (tagName === "TEXTAREA") {
                        $(em).html(resVar);
                    } else if (tagName === "SPAN") {
                        $(em).html(resVar);
                    } else if (tagName === "DIV") {
                        $(em).html(resVar);
                    }
                }
            }
            //数据回显 end
        } else {
            if ($.trim(_arg.msg) && _arg.msg !== "") {
                layer.alert(_arg.msg, {icon: 2})
            } else {
                layer.alert("加载数据错误，请刷新重试！", {icon: 2})
            }
        }
    }
    
    /**用以渲染流程相关资源（流程按钮、意见域等）
     * @param url  后台请求地址
     * @param datas  后台请求需要的参数
     * @param oper	"NEW"表示起草页面；"EDIT"表示流转中的审批页面；"VIEW"表示只读页面
     * @param callback	请求成功后的回调函数
     * */
    function playWorkFlow(url,datas,oper,callback,returnData){
    	$.ajax({
    		type:"POST",
    		url:url,
    		data:datas,
    		dataType:"json",
    		async: false,
    		success:function(data){
    			console.log(data);
				var str="";
				$("#idea").val(data.tempIdea);
				var arr = "";
                if(returnData){
                    returnData(data);
                }
				if(oper=="NEW"){
					arr = data;
				}else if(oper=="EDIT"){
					arr = data.flowData.buttonVec;
					var ideas = JSON.parse(data.formalIdea);
					var subIdeaType = "";
					var coverMode = "";
					var fillmode = "";
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
                    if(callback){
                        callback(data);
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
			    			/*$("#gd").css("display","block");*/
                            $("#gd").show();
			    		}
			    		// 办结无提交
			    		if (arr[i].num == Config.banjNoSubmitButton) {
			    			/*$("#gd").css("display","block");
			    			$("#subflow").css("display","none");*/
                            $("#gd").show();
                            $("#subflow").show();
			    		}
			    		// 撤办
			    		if(arr[i].num == Config.removeButton){
			    			/*$("#remove").css("display","block");*/
                            $("#remove").show();
			    		}
			    		// 恢复撤办
			    		if(arr[i].num == Config.recoveryButton){
			    			/*$("#resumeFlow").css("display","block");*/
                            $("#resumeFlow").show();
			    		}
			    		// 收回
			    		if(arr[i].num == Config.takeBackButton){
			    			/*$("#takeBack").css("display","block");*/
                            $("#takeBack").show();
			    		}
			    		// 审批通过
			    		if(arr[i].num==Config.approvalButton){
			    			$("#approval").show();
			    			/*$("#approval").css("display","block");
                            $("#approval").css("width","140px");*/
			    		}
			    		// 审批不通过
			    		if(arr[i].num==Config.noApprovalButton){
                            $("#noApproval").show();
			    			/*$("#noApproval").css("display","block");
                            $("#approval").css("width","140px");*/
			    		}
			    		// 退回
			    		if(arr[i].num == Config.returnBackButton){
			    			/*$("#returnBack").css("display","block");*/
                            $("#returnBack").show();
			    		}
			    		// 增加会签
			    		if(arr[i].num == Config.increasesignatureButton){
			    			/*$("#increaseSign").css("display","block");*/
                            $("#increaseSign").show();
			    		}
			    		// 删除会签
			    		if(arr[i].num == Config.deletingsignatureButton){
			    			/*$("#deleteSign").css("display","block");*/
                            $("#deleteSign").show();
			    		}
			    		// 保存
			    		if(arr[i].num == Config.saveButton){
			    			/*$("#save").css("display","block");*/
                            $("#save").show();
			    		}
			    		// 提交
			    		if(arr[i].num == Config.comitButton){
			    			/*$("#subflow").css("display","block");*/
                            $("#subflow").show();
			    		}
			    		//盖章
                        if(arr[i].num == Config.sealSign){
                            /*$("#subflow").css("display","block");*/
                            $("#sealSign").show();
                        }
					}
				}
		    	
			},
			error: function(e){

			}
	    });
    	//请求和渲染后台数据结束
    	
    	//按钮渲染的规则，方案1：隐藏-显示 方案 2：动态加载
    }

    return {
        playData: function (json) {
        	playData(json);
        },
        playWorkFlow:function (url,datas,oper,callback,returnData){
        	playWorkFlow(url,datas,oper,callback,returnData);
        }
    }
}();

//意见信息加载
function callback(data) {
    if(data.flowData.ideaFieldVec.length>0){
        for(var n=0;n<data.flowData.ideaFieldVec.length;n++){
            if(data.flowData.ideaFieldVec[n].isvisible == "1" ){
                document.getElementById("ideaArea").style.display="block";
                document.getElementById("fillmode").value=data.flowData.ideaFieldVec[n].fillmode;
            }
        }
    }
    $("#flowMemo").text(data.flowData.memo);
    if(data.formalIdea){
        data = $.parseJSON(data.formalIdea);//json字符串转为json对象
        var html = "";
        for(var i = 0;i<data.length;i++ ){
            var tr='<tr>';
            if(data[i].ideaList.length>0){
                tr +='<td class="text-center" width="15%" rowspan="'+data[i].ideaList.length+'"><h4 style="font-weight: bold;">'+data[i].note+':</h4></td>'
                for(var j=0;j<data[i].ideaList.length;j++){
                    if(j==0){
                        tr +='<td class="text-left" width="55%">'+data[i].ideaList[j].idea+'</td>';
                        tr +='<td class="text-center">'+data[i].ideaList[j].username+'</td>';
                        tr +='<td>'+data[i].ideaList[j].ideatime+'</td>';
                    }else{
                        tr+='<tr>';
                        tr +='<td class="text-left" width="55%">'+data[i].ideaList[j].idea+'</td>';
                        tr +='<td class="text-center">'+data[i].ideaList[j].username+'</td>';
                        tr +='<td>'+data[i].ideaList[j].ideatime+'</td>';
                        tr+="</tr>";
                    }
                }
            }/*else{
                tr +='<td class="text-center" width="15%"><h4 style="font-weight: bold;">'+data[i].note+':</h4></td>'
                tr +='<td class="text-center"></td>';
                tr +='<td class="text-center"></td>';
                tr +='<td></td>';
            }*/
            tr+="</tr>";
            html+=tr;
        }
        $('#yjxxTable').find('tbody').empty().append(html);
        if($('#idea').val() != null && $('#idea').val() != ""){
            var len=$('#idea').val().length
            $('#idea-count').text(len);
        }
        $('#idea').keyup(function() {
            var len=this.value.length
            $('#idea-count').text(len);
        });
    }
}

/**
 * 判断当前页面是否是AIP页面
 */
function isAip(){
    var isAip = false;
    //判断是否是AIP页面
    var obj = $("object[aip=true]").size();
    if(obj > 0){
        isAip = true;
    }
    console.log("是否是AIP：" + isAip);
    return isAip;
}

/**
 * 隐藏AIP控件
 */
function hideAip(){
    $("object").parent("div").parent("div").css("display","none");
}

/**
 * 加载印章
 * @param subflag 流程状态
 */
function loadSeal(subflag) {
    var imgSrc = "";
    if (subflag == 5) {
        imgSrc = "/static/images/seal5.png";
    } else if (subflag == 6) {
        imgSrc = "/static/images/seal6.png";
    } else {
        return;
    }
    if(isAip()){
        imgSrc = encodeURI(imgSrc);
        var $seal1 = $(''
            + '<div class="seal">'
            + '<iframe id = "imgIframe" src = "/modules/imgIframe.html?imgSrc='+imgSrc+'" frameborder = "0" marginheight ="0" marginwidth = "0" ' +
            'style="background-color: transparent; position:absolute; visibility: inherit; top: 0px; left: 0px; ' +
            'width: 200px; height: 80px; z-index: 19890000;" align="middle" allowtransparency>'
            +'</iframe>'
            + '</div>'
        );
        $seal1.css({
            width: '200px',
            position: 'absolute',
            right: 'calc(50% - 100px)',
            bottom: 'calc(50% - 40px)',
        });
        $seal1.find('img').css('max-width','100%');
        $('.formpage_area').append($seal1);
    }else {
        var $seal = $(''
            + '<div class="seal">'
            + '<img src="' + imgSrc + '" />'
            + '</div>'
        );
        $seal.css({
            width: '200px',
            position: 'absolute',
            right: 'calc(50% - 100px)',
            bottom: 'calc(50% - 40px)',
            'z-index': '99'
        });
        $seal.find('img').css('max-width', '100%');
        $('.formpage_area').append($seal);
    }
}

