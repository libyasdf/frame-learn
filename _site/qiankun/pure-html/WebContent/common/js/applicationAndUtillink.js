/**
 * 首页展示应用导航
 * @param id
 * @param num 展示个数
 * @param errorCallback
 * @return curDate 返回当前时间
 */
function showDaohang(id,num) {
    var url = "/system/config/application/getList";
    $.ajax({
        type : "POST",
        url : url,
        dataType : "json",
        async : false,
        success : function(data) {
            if (data && data.flag === "1") {
                if (data.data) {
                    var applicationList = data.data.rows;
                    if (applicationList && applicationList.length > 0) {
                        if(num){
                            for (var i = 0; i < applicationList.length; i++) {
                                // 首页最多展示12个应用导航
                                if (i < num) {
                                    $("#"+id).append("<li><a href='"+applicationList[i].url+"' target=\"view_window\"><div class=\"imgBox\"><img src='"+applicationList[i].path+"'/>" +
                                        "</div><span class=\"fontColor_blue\">"+applicationList[i].name+"</span></a></li>");
                                }

                            }
                        }else{
                            for (var i = 0; i < applicationList.length; i++) {
                                $("#"+id).append("<li><a href='"+applicationList[i].url+"' target=\"view_window\"><div class=\"imgBox\"><img src='"+applicationList[i].path+"'/>" +
                                    "</div><span class=\"fontColor_blue\">"+applicationList[i].name+"</span></a></li>");
                            }

                        }


                    }
                }

            }
        },
        error : function(msg) {
        }
    });
}

/**
 * 点击应用导航更多
 */
function showMoreYydh(){
    MyLayer.layerOpenUrl({url:'/modules/system/config/applicationlick/moreApplicationForm.html',title:'应用导航'})
}

/**
 * 点击常用工具更多
 */
function showMoreCygj(){
    MyLayer.layerOpenUrl({url:'/modules/system/config/utillink/moreUtillinkForm.html',title:'常用工具'})
}



/**
 * 首页展示常用工具
 * @param id
 * @param num 展示个数
 * @param errorCallback
 * @return curDate 返回当前时间
 */
function showUtillink(id,num) {
    var url = "/system/config/utilLink/getList";
    $.ajax({
        type : "POST",
        url : url,
        dataType : "json",
        async : false,
        success : function(data) {
            if (data && data.flag === "1") {
                if (data.data) {
                    var utillinkList = data.data.rows;
                    if (utillinkList && utillinkList.length > 0) {
                        if(num){
                            for (var i = 0; i < utillinkList.length; i++) {
                                // 首页最多展示9个常用工具
                                if (i < num) {
                                    $("#"+id).append("<li  onclick='openDownload(\""+utillinkList[i].id+"\")'><i class='"+utillinkList[i].path+ " iconStyle'></i><br>"+utillinkList[i].name+"</li>");
                                }

                            }
                        }else{
                            for (var i = 0; i < utillinkList.length; i++) {
                                $("#"+id).append("<li  onclick='openDownload(\""+utillinkList[i].id+"\")'><i class='"+utillinkList[i].path+ " iconStyle'></i><br>"+utillinkList[i].name+"</li>");
                            }

                        }


                    }
                }

            }
        },
        error : function(msg) {
        }
    });
}

/**
 * 打开常用工具下载附件列表
 */
function openDownload(id){
    $.ajax({
        type:"get",
        url:"/system/component/affix/list",
        data:{
            "tableName":"sys_util_link",
            "tableId":id
        },
        dataType:"json",
        async:false,
        success:function(data){
            if(data.flag == "1"){
                if(data.data.files){
                    var affixList = data.data.files;
                    if (affixList && affixList.length > 0) {
                        layer.open({
                            type: 2,
                            content: "/modules/system/config/utillink/utillinkAffixList.html?id="+id,
                            area: ['500px', '300px'],
                            title: ['下载列表', 'font-size:16px;font-weight:bold;']
                        })
                    }else{
                        layer.msg('暂无可下载的附件！',{icon: 0})
                    }
                }
            }
        },
        error:function(){
            layer.msg("加载数据异常！请刷新重试！",{icon:2});
        }
    })

}











