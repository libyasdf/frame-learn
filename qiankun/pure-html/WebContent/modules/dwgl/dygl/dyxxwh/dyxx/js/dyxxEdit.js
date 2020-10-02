$(function() {
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
    $("#opertation").val(theRequest.opertation);
    $("#partyOrganizationId").val(theRequest.orgId);
    $("#partyOrganizationName").val(theRequest.orgName);
    $("#superId").val(regVlaue(theRequest.superId));
    var idVal = $("#id").val();
    if(regVlaue(theRequest.opertation)=="ADD"){
        $("#partyOrganizationId").val(theRequest.orgId);
        $("#partyOrganizationName").val(theRequest.orgName);
        var datas = {"id":$("#partyOrganizationId").val()};
        httpRequest("get","/djgl/ddjs/shyk/zbdydh/getOrgName",datas,function (data){
            var  unitId =data.data[0].ASSOCIATIVE_UNIT_ID;
            $("#associativeUnitId").val(unitId);
        });
    }

    if($("#opertation").val() != "VIEW"){
        //初始化字典项--民族
        Dictionary.init({position:"nation",mark:"dwgl_dygl_ryjbqk_mz",type:"1",name:"nation",domType:"select"});
        //初始化字典项--健康情况
        Dictionary.init({position:"healthStatus",mark:"dwgl_dygl_ryjbqk_jkzk",type:"1",name:"healthStatus",domType:"select"});
        //初始化字典项--籍贯
        Dictionary.init({position:"nativePlace",mark:"dwgl_dygl_ryjbqk_jg",type:"1",name:"nativePlace",domType:"select"});
        //初始化字典项--婚姻情况
        Dictionary.init({position:"maritalStatus",mark:"dwgl_dygl_ryjbqk_hyzk",type:"1",name:"maritalStatus",domType:"select"});
        //初始化字典项--个人身份
        Dictionary.init({position:"ryIdentity",mark:"dwgl_dygl_ryjbqk_grsf",type:"1",name:"ryIdentity",domType:"select"});
        //初始化字典项--学历
        Dictionary.init({position:"ryEducation",mark:"dwgl_dygl_ryjbqk_xl",type:"1",name:"ryEducation",domType:"select"});
        //初始化字典项--学位
        Dictionary.init({position:"ryGegree",mark:"dwgl_dygl_ryjbqk_xw",type:"1",name:"ryGegree",domType:"select"});
        //初始化字典项--机关第一线
        Dictionary.init({position:"frontlineSituation",mark:"dwgl_dygl_ryjbqk_yxqk",type:"1",name:"frontlineSituation",domType:"select"});
        //初始化字典项--职务级别
        Dictionary.init({position:"jobLevel",mark:"dwgl_dygl_ryjbqk_zwjb",type:"1",name:"jobLevel",domType:"select"});
        //初始化字典项--转正情况
        Dictionary.init({position:"introducerSituation",mark:"dwgl_dygl_dyjbqk_zzqk",type:"1",name:"introducerSituation",domType:"select"});
        //初始化字典项--党员增加
        Dictionary.init({position:"increasePartymember",mark:"dwgl_dygl_dyzjqk_dyzj",type:"1",name:"increasePartymember",domType:"select"});
        //初始化字典项--原个人身份
        Dictionary.init({position:"originalIndividualStatus",mark:"dwgl_dygl_dyzjqk_ygrsf",type:"1",name:"originalIndividualStatus",domType:"select"});
        // //初始化字典项--工作岗位个人身份
        // Dictionary.init({position:"identity",mark:"dwgl_dygl_ryjbqk_grsf",type:"1",name:"identity",domType:"select"});
        // //初始化字典项--工作岗位个人身份
        // Dictionary.init({position:"workFrontlineSituation",mark:"dwgl_dygl_ryjbqk_yxqk",type:"1",name:"workFrontlineSituation",domType:"select"});
        //初始化字典项--行政职务名称
        // Dictionary.init({position:"administrativeDutiesName",mark:"dwgl_dygl_xzzw_xzzw",type:"1",name:"administrativeDutiesName",domType:"select"});
        // //初始化字典项--个人排序
        // Dictionary.init({position:"personalPositionOrder",mark:"dwgl_dygl_xzzw_grzwpx",type:"1",name:"personalPositionOrder",domType:"select"});
        // //初始化字典项--学历
        // Dictionary.init({position:"education",mark:"dwgl_dygl_ryjbqk_xl",type:"1",name:"education",domType:"select"});
        // //初始化字典项--学位
        // Dictionary.init({position:"degree",mark:"dwgl_dygl_ryjbqk_xw",type:"1",name:"degree",domType:"select"});
        //初始化字典项--是否为党代表
        Dictionary.init({position:"isPartyRepresentative",mark:"dwgl_dygl_ryjbqk_sfwddb",type:"1",name:"isPartyRepresentative",domType:"checkbox"});
    }
    /**
     * 初始化页面，数据加载、渲染
     */
    var id = $("#id").val();
    if(id != ""){
        //表单数据渲染
        var datas = {"id":id,"resId":$("#resId").val()};
        if(regVlaue(theRequest.dyType)=="HISTORY"){
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
        }else {
            httpRequest("get", "/djgl/ddjs/dygl/dyxx/edit?type=1", datas, function (data) {
                if ($("#opertation").val() == "VIEW") {
                    data.data.nation = Dictionary.getNameAndCode({
                        mark: "dwgl_dygl_ryjbqk_mz",
                        type: "1"
                    })[data.data.nation];
                    data.data.healthStatus = Dictionary.getNameAndCode({
                        mark: "dwgl_dygl_ryjbqk_jkzk",
                        type: "1"
                    })[data.data.healthStatus];
                    data.data.nativePlace = Dictionary.getNameAndCode({
                        mark: "dwgl_dygl_ryjbqk_jg",
                        type: "1"
                    })[data.data.nativePlace];
                    data.data.maritalStatus = Dictionary.getNameAndCode({
                        mark: "dwgl_dygl_ryjbqk_hyzk",
                        type: "1"
                    })[data.data.maritalStatus];
                    data.data.ryIdentity = Dictionary.getNameAndCode({
                        mark: "dwgl_dygl_ryjbqk_grsf",
                        type: "1"
                    })[data.data.ryIdentity];
                    data.data.ryEducation = Dictionary.getNameAndCode({
                        mark: "dwgl_dygl_ryjbqk_xl",
                        type: "1"
                    })[data.data.ryEducation];
                    data.data.ryGegree = Dictionary.getNameAndCode({
                        mark: "dwgl_dygl_ryjbqk_xw",
                        type: "1"
                    })[data.data.ryGegree];
                    data.data.frontlineSituation = Dictionary.getNameAndCode({
                        mark: "dwgl_dygl_ryjbqk_yxqk",
                        type: "1"
                    })[data.data.frontlineSituation];
                    data.data.jobLevel = Dictionary.getNameAndCode({
                        mark: "dwgl_dygl_ryjbqk_zwjb",
                        type: "1"
                    })[data.data.jobLevel];
                    data.data.identity = Dictionary.getNameAndCode({
                        mark: "dwgl_dygl_ryjbqk_grsf",
                        type: "1"
                    })[data.data.identity];
                    data.data.workFrontlineSituation = Dictionary.getNameAndCode({
                        mark: "dwgl_dygl_ryjbqk_yxqk",
                        type: "1"
                    })[data.data.workFrontlineSituation];
                    data.data.degree = Dictionary.getNameAndCode({
                        mark: "dwgl_dygl_ryjbqk_xw",
                        type: "1"
                    })[data.data.degree];
                    var technicalPost = data.data.technicalPost.split(",");
                    var ar = [];
                    $.each(technicalPost, function (i, n) {
                        if (n === "2") {
                            ar.push("教授");
                        }
                        if (n === "1") {
                            ar.push("副教授");
                        }
                    })
                    data.data.technicalPost = ar.join(", ");

                    var isPartyRepresentative = data.data.isPartyRepresentative.split(",");
                    var ar = [];
                    $.each(isPartyRepresentative, function (i, n) {
                        if (n === "4") {
                            ar.push("乡(镇)代表");
                        }
                        if (n === "1") {
                            ar.push("省(区、市)代表");
                        }
                        if (n === "2") {
                            ar.push("市(州)代表");
                        }
                        if (n === "3") {
                            ar.push("县(市、区)代表")
                        }
                        ;
                    })
                    $("#isPartyRepresentative").text(ar.join(", "));

                }
                DisplayData.playData({data: data});
            });
        }
    }



})

function LoadFormpage(pageId){
    var theRequest = GetRequest();
    var id = $("#id").val();
    if(regVlaue(theRequest.dyType)=="HISTORY"){
        id = regVlaue(theRequest.userbasicinfoId)
    }
    var datas = {"id":id,"resId":$("#resId").val()};
    if(pageId == "formpage2"){
        $("#partybasicinfoSuperId").val(id);
        httpRequest("get","/djgl/ddjs/dygl/dyxx/edit?type=2",datas,function (data){
            if($("#opertation").val() == "VIEW"){
                //data.data.introducerSituation = Dictionary.getNameAndCode({mark:"dwgl_dygl_dyjbqk_zzqk",type:"1"})[data.data.introducerSituation];
                data.data.introducerSituation =Dictionary.getNameAndCode({mark:"dwgl_dygl_dyjbqk_zzqk",type:"1"})[data.data.introducerSituation];
                var developmentType = ""
                if (data.data.developmentType == "01") {
                    developmentType = "新吸收为中共预备党员";
                } else if (data.data.developmentType == "02") {
                    developmentType = "重新吸收为中共预备党员";
                }else if (data.data.developmentType == "03") {
                    developmentType = "其他组织推荐";
                }else if (data.data.developmentType == "04") {
                    developmentType = "直接批准为中共正式党员";
                }
                data.data.developmentType =developmentType;
            }
            DisplayData.playData({data:data});
        });
    }else if(pageId == "formpage3"){
        $("#increaseSuperId").val(id);
        httpRequest("get","/djgl/ddjs/dygl/dyxx/edit?type=3",datas,function (data){
            if($("#opertation").val() == "VIEW"){
                data.data.increasePartymember =Dictionary.getNameAndCode({mark:"dwgl_dygl_dyzjqk_dyzj",type:"1"})[data.data.increasePartymember];
                data.data.originalIndividualStatus =Dictionary.getNameAndCode({mark:"dwgl_dygl_dyzjqk_ygrsf",type:"1"})[data.data.originalIndividualStatus];
            }
            DisplayData.playData({data:data});
        });
        if($("#increaseId").val() == ""){
            debugger
            //表单数据渲染
            var datas = {"id":$("#superId").val(),"resId":$("#resId").val()};
            httpRequest("get","/djgl/ddjs/dygl/dyxx/trunAroundEdit",datas,function (data){
                $("#turnToParty").val(data.data.turnAroundParty);
                $("#increaseTime").val(data.data.turnAroundTime);
                $("#originalIndividualStatus").val(data.data.originalIndividualStatus);
                $("#increasePartymember").val(data.data.turnAroundType);
                DisplayData.playData({data:data});
                $("#id").val(id);
            });

        }
    }else if(pageId == "formpage4"){//工作岗位
        if($("#opertation").val() != "VIEW") {
            commitForm("0", false);
        }
        httpRequest("get","/djgl/ddjs/dygl/dyxx/edit?type=4",datas,function (data){
            if(data.data.identity!="") {
                data.data.identity = Dictionary.getNameAndCode({
                    mark: "dwgl_dygl_ryjbqk_grsf",
                    type: "1"
                })[data.data.identity];
            }else{
                $("#identity").text( data.data.identity);
            }
            if(data.data.workFrontlineSituation!="") {
                data.data.workFrontlineSituation = Dictionary.getNameAndCode({
                    mark: "dwgl_dygl_ryjbqk_yxqk",
                    type: "1"
                })[data.data.workFrontlineSituation];
            }else{
                $("#workFrontlineSituation").text( data.data.workFrontlineSituation);
            }
            $("#beforeTime").text( data.data.beforeTime);
            $("#endTime").text( data.data.endTime);
            $("#identity").text( data.data.identity);
           // $("#workFrontlineSituation").text( data.data.workFrontlineSituation);
            DisplayData.playData({data:data});
        });

        //初始化表格
        $("#right_table4").bootstrapTable('destroy');
        TableInit.init({
            domId: "right_table4",
            url: "/djgl/ddjs/dygl/dyxx/getListByType?type=4&superId="+id,
            columns: right_table_col4,
            readOnlyFn: function () {
                httpRequest("get","/djgl/ddjs/dygl/dyxx/edit?type=4",datas,function (data){
                    if(data.data.identity!="") {
                        data.data.identity = Dictionary.getNameAndCode({
                            mark: "dwgl_dygl_ryjbqk_grsf",
                            type: "1"
                        })[data.data.identity];
                    }else{
                        $("#identity").text( data.data.identity);
                    }
                    if(data.data.workFrontlineSituation!="") {
                        data.data.workFrontlineSituation = Dictionary.getNameAndCode({
                            mark: "dwgl_dygl_ryjbqk_yxqk",
                            type: "1"
                        })[data.data.workFrontlineSituation];
                    }else{
                        $("#workFrontlineSituation").text( data.data.workFrontlineSituation);
                    }
                    $("#beforeTime").text( data.data.beforeTime);
                    $("#endTime").text( data.data.endTime);
                    $("#identity").text( data.data.identity);
                    $("#workFrontlineSituation").text( data.data.workFrontlineSituation);
                    DisplayData.playData({data:data});
                });

            }
        })
    }else if(pageId == "formpage5"){//党内职务
        httpRequest("get","/djgl/ddjs/dygl/dyxx/edit?type=5",datas,function (data){
            data.data.duties = Dictionary.getNameAndCode({mark:"dwgl_duties",type:"1"})[data.data.duties];
            DisplayData.playData({data:data});
        });
        $("#right_table5").bootstrapTable('destroy');
        $('#right_table5').bootstrapTable({
            url: "/djgl/ddjs/dygl/dyxx/getUserDuties?id="+id,
            sidePagination:"client",
            columns: right_table_col5,
            pagination:true,
            striped:true,
            classes: 'table table-hover',
            rowStyle: function (row, index) {	//默认样式：居中，鼠标为手
                return {
                    classes: 'readOnly'
                    ,css: {"text-align":"center","cursor":""}
                };
            },
            readOnlyFn: function () {

            }
        })
    }else if(pageId == "formpage6"){//行政职务
        if($("#opertation").val() != "VIEW") {
            commitForm("0", false);
        }
        httpRequest("get","/djgl/ddjs/dygl/dyxx/edit?type=6&superId="+id,datas,function (data){
            data.data.administrativeDutiesName = Dictionary.getNameAndCode({mark:"dwgl_dygl_xzzw_xzzw",type:"1"})[data.data.administrativeDutiesName];

            if(data.data.personalPositionOrder!="") {
                data.data.personalPositionOrder = Dictionary.getNameAndCode({
                    mark: "dwgl_dygl_xzzw_grzwpx",
                    type: "1"
                })[data.data.personalPositionOrder];
            }else{
                $("#personalPositionOrder").text( data.data.personalPositionOrder);
            }

            $("#administrativeDutiesName").text( data.data.administrativeDutiesName);
            $("#tenureUnit").text( data.data.tenureUnit);
            $("#personalPositionOrder").text( data.data.personalPositionOrder);
            $("#xzTenureTime").text( data.data.xzTenureTime);
            $("#xzOutgoingTime").text( data.data.xzOutgoingTime)
            DisplayData.playData({data:data});
        });
        $("#right_table6").bootstrapTable('destroy');
        TableInit.init({
            domId: "right_table6",
            url: "/djgl/ddjs/dygl/dyxx/getListByType?type=2&superId="+id,
            columns: right_table_col6,
            readOnlyFn: function () {
                httpRequest("get","/djgl/ddjs/dygl/dyxx/edit?type=6&superId="+id,datas,function (data){
                    data.data.administrativeDutiesName = Dictionary.getNameAndCode({mark:"dwgl_dygl_xzzw_xzzw",type:"1"})[data.data.administrativeDutiesName];
                    data.data.personalPositionOrder = Dictionary.getNameAndCode({mark:"dwgl_dygl_xzzw_grzwpx",type:"1"})[data.data.personalPositionOrder];

                    $("#administrativeDutiesName").text( data.data.administrativeDutiesName);
                    $("#tenureUnit").text( data.data.tenureUnit);
                    $("#personalPositionOrder").text( data.data.personalPositionOrder);
                    $("#xzTenureTime").text( data.data.xzTenureTime);
                    $("#xzOutgoingTime").text( data.data.xzOutgoingTime)
                    DisplayData.playData({data:data});
                });
            }
        })
    }else if(pageId == "formpage7"){//学位学历
        if($("#opertation").val() != "VIEW") {
            commitForm("0", false);
        }
        httpRequest("get","/djgl/ddjs/dygl/dyxx/edit?type=7&superId="+id,datas,function (data){
            data.data.degree =Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_xw",type:"1"})[data.data.degree];
            data.data.education = Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_xl",type:"1"})[data.data.education];
            DisplayData.playData({data:data});
            var educationSector = ""
            if (data.data.educationSector == "121") {
                educationSector = "全日制教育";
            } else if (data.data.educationSector == "123") {
                educationSector = "在职教育";
            }
            data.data.educationSector = educationSector;

            $("#educationSector").text( data.data.educationSector);
            $("#education").text( data.data.education);
            $("#enrolmentTime").text( data.data.enrolmentTime);
            $("#graduationTime").text( data.data.graduationTime);
            $("#major").text( data.data.major);
            $("#graduationAcademy").text( data.data.graduationAcademy);
            $("#degree").text( data.data.degree);
            $("#degreeAwardTime").text( data.data.degreeAwardTime);
            DisplayData.playData({data:data});
        });
        $("#right_table7").bootstrapTable('destroy');
        TableInit.init({
            domId: "right_table7",
            url: "/djgl/ddjs/dygl/dyxx/getListByType?type=5&superId="+id,
            columns: right_table_col7,
            readOnlyFn: function () {
                httpRequest("get","/djgl/ddjs/dygl/dyxx/edit?type=7&superId="+id,datas,function (data){
                    data.data.degree =Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_xw",type:"1"})[data.data.degree];
                    data.data.education = Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_xl",type:"1"})[data.data.education];
                    DisplayData.playData({data:data});
                    var educationSector = ""
                    if (data.data.educationSector == "121") {
                        educationSector = "全日制教育";
                    } else if (data.data.educationSector == "123") {
                        educationSector = "在职教育";
                    }
                    data.data.educationSector = educationSector;

                    $("#educationSector").text( data.data.educationSector);
                    $("#education").text( data.data.education);
                    $("#enrolmentTime").text( data.data.enrolmentTime);
                    $("#graduationTime").text( data.data.graduationTime);
                    $("#major").text( data.data.major);
                    $("#graduationAcademy").text( data.data.graduationAcademy);
                    $("#degree").text( data.data.degree);
                    $("#degreeAwardTime").text( data.data.degreeAwardTime);
                    DisplayData.playData({data:data});
                });
            }
        })
    }
}

function commitForm(flag,flagLayer) {
    var data = saveForm();
    if (data) {

        if(flagLayer){
            layer.msg("保存成功！", {
                icon : 1
            });
        }
        $('#id').val(data);
        $('a.btn.disabled').removeClass();
        initTab();
        //		var index = parent.layer.getFrameIndex(window.name);
//		parent.layer.close(index);
        // 刷新列表
        parent.parent.TableInit.refTable('right_table');

    }
}


/**
 * 保存
 */
function saveForm(){
    var theRequest = GetRequest();
    Loading.open();
    var record = "";
    var res = "";
    debugger;
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
        var all = $("#form").serialize().concat("&");
        $.ajax({
            type: "POST",
            url: "/djgl/ddjs/dygl/dyxx/save",
            data:all,
            async: false,
            success:function(json){
                record = json.data.id;
               checkAllParents();
            },
            error:function(){
            }
        });
    }
    Loading.clear();
    return record;
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


function checkAllParents(){
    parent.parent.document.getElementById("ckb").checked=false;
    if(getcookie("rolesNo").indexOf("D478") >= 0){
        parent.parent.dzzTree.init({id:'4028d08266cd1d0a0166cd462e260001',type:"org"});
    }else{
        $.ajax({
            type: "get",
            async: false,
            url: "/djgl/ddjs/dzz/dzzgl/getOrgId",
            dataType: "json",
            success: function (data) {
                parent.parent.dzzTree.init({id:data.data,type:"org"});
            },
            error: function (data) {
            }
        })
    }
}
