$(function() {
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
    $("#opertation").val(theRequest.opertation);
    /**
     * 初始化页面，数据加载、渲染
     */
    var id = $("#id").val();
    if(id != ""){
        //表单数据渲染
        var datas = {"id":id,"resId":$("#resId").val()};
        httpRequest("get","/djgl/ddjs/dygl/dyxx/edit?type=8",datas,function (data){
            if($("#opertation").val() == "VIEW"){
                data.data.nation =Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_mz",type:"1"})[data.data.nation];
                data.data.healthStatus  = Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_jkzk",type:"1"})[data.data.healthStatus];
                data.data.nativePlace  = Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_jg",type:"1"})[data.data.nativePlace];
                data.data.maritalStatus = Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_hyzk",type:"1"})[data.data.maritalStatus];
                data.data.ryIdentity = Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_grsf",type:"1"})[data.data.ryIdentity];
                data.data.ryEducation =Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_xl",type:"1"})[data.data.ryEducation];
                data.data.ryGegree =Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_xw",type:"1"})[data.data.ryGegree];
                data.data.frontlineSituation =Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_yxqk",type:"1"})[data.data.frontlineSituation];
                data.data.jobLevel  =Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_zwjb",type:"1"})[data.data.jobLevel];
                data.data.identity =Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_grsf",type:"1"})[data.data.identity];
                data.data.workFrontlineSituation =Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_yxqk",type:"1"})[data.data.workFrontlineSituation];
                data.data.degree =Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_xw",type:"1"})[data.data.degree];

                var technicalPost = data.data.technicalPost.split(",");
                var ar = [];
                $.each(technicalPost, function (i, n) {
                    if (n === "2") {ar.push("教授");}
                    if (n === "1") { ar.push("副教授");}
                })
                data.data.technicalPost = ar.join(", ");

                var isPartyRepresentative = data.data.isPartyRepresentative.split(",");
                var ar = [];
                $.each(isPartyRepresentative, function (i, n) {
                    if (n === "4") {ar.push("乡(镇)代表");}
                    if (n === "1") {ar.push("省(区、市)代表");}
                    if (n === "2") {ar.push("市(州)代表");}
                    if (n === "3") {ar.push("县(市、区)代表")};
                })
                $("#isPartyRepresentative").text(ar.join(", "));

            }
            DisplayData.playData({data:data});
        });

    }
})

function LoadFormpage(pageId){
    var theRequest = GetRequest();
    id = $("#superId").val();
    var datas = {"id":id,"resId":$("#resId").val()};
    if(pageId == "formpage2"){
        $("#partybasicinfoSuperId").val(id);
        httpRequest("get","/djgl/ddjs/dygl/dyxx/edit?type=9",datas,function (data){
            if($("#opertation").val() == "VIEW"){
                var turnOutType = ""
                if (data.data.turnOutType == "01") {
                    turnOutType = "转往军队";
                } else if (data.data.turnOutType == "02") {
                    turnOutType = "转往武警部队";
                }else if (data.data.turnOutType == "03") {
                    turnOutType = "转往其他系统";
                }else if (data.data.turnOutType == "04") {
                    turnOutType = "转往本系统内其他单位";
                }else if (data.data.turnOutType == "05") {
                    turnOutType = "转往本省(市、区)";
                }else if (data.data.turnOutType == "06") {
                    turnOutType = "转往其他单位";
                }
                data.data.turnOutType =turnOutType;
            }
            DisplayData.playData({data:data});
        });
    }
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

