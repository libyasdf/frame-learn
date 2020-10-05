$(function() {
    var theRequest = GetRequest();
    $("#resId").val(theRequest.resId);
    $("#id").val(regVlaue(theRequest.id));
    $("#opertation").val(theRequest.opertation);
    $("#typeOfPersonnel").val("01");// 01—申请人、02-积极分子、03-发展对象、04-预备党员、05-党员、06-历史党员
    var recrodId = $("#id").val();
    if(recrodId != null && recrodId != '' && (typeof(recrodId) != undefined)){
        $('a.btn.disabled').removeClass();
        initTab();
    }
    if(regVlaue(theRequest.addOrUpdate)=="add"){
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
        //初始化字典项--工作岗位个人身份
       // Dictionary.init({position:"identity",mark:"dwgl_dygl_ryjbqk_grsf",type:"1",name:"identity",domType:"select"});
        //初始化字典项--工作岗位个人身份
    //    Dictionary.init({position:"workFrontlineSituation",mark:"dwgl_dygl_ryjbqk_yxqk",type:"1",name:"workFrontlineSituation",domType:"select"});
        //初始化字典项--行政职务名称
        Dictionary.init({position:"administrativeDutiesName",mark:"dwgl_dygl_xzzw_xzzw",type:"1",name:"administrativeDutiesName",domType:"select"});
        //初始化字典项--个人排序
        Dictionary.init({position:"personalPositionOrder",mark:"dwgl_dygl_xzzw_grzwpx",type:"1",name:"personalPositionOrder",domType:"select"});
        //初始化字典项--学历
        Dictionary.init({position:"education",mark:"dwgl_dygl_ryjbqk_xl",type:"1",name:"education",domType:"select"});
        //初始化字典项--学位
        Dictionary.init({position:"degree",mark:"dwgl_dygl_ryjbqk_xw",type:"1",name:"degree",domType:"select"});
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
        httpRequest("get","/djgl/ddjs/sqrgl/sqrxx/edit",datas,function (data){
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
                data.data.isPartyRepresentative =Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_sfwddb",type:"1"})[data.data.isPartyRepresentative];

            }
            DisplayData.playData({data:data});
            if($("#opertation").val() == "VIEW") {
                var technicalPost = ""
                if (data.data.technicalPost == "1") {
                    technicalPost = "副教授";
                } else if (data.data.technicalPost == "2") {
                    technicalPost = "教授";
                }
                $("#technicalPost").text(technicalPost);
            }
        });
        var datas = {"id":$("#partyOrganizationId").val()};
        httpRequest("get","/djgl/ddjs/shyk/zbdydh/getOrgName",datas,function (data){
            var  unitId =data.data[0].ASSOCIATIVE_UNIT_ID;
            $("#associativeUnitId").val(unitId);
        });

        var datas = {"id":id,"resId":$("#resId").val()};
        httpRequest("get","/djgl/ddjs/sqrgl/sqrxx/edit?type=2&superId="+id,datas,function (data){
            DisplayData.playData({data:data});
            if($("#opertation").val() == "VIEW") {
                var developmentType = "";
                if (data.data.developmentType == "01") {
                    developmentType = "入党申请人员";
                } else if (data.data.developmentType == "02") {
                    developmentType = "入党积极分子";
                } else if (data.data.developmentType == "03") {
                    developmentType = "计划发展对象"

                }
                $("#developmentType").text(developmentType);
                var politicalOutlook = ""
                if (data.data.politicalOutlook == "01") {
                    politicalOutlook = "团员";
                } else if (data.data.politicalOutlook == "02") {
                    politicalOutlook = "群众";
                }
                $("#politicalOutlook").text(politicalOutlook);
            }
        });
        if($('#applicationTime').val()!=""){
            $('#applicationTime').attr('readonly',true);
        }
    }


})

function LoadFormpage(pageId){
    var id = $("#id").val();
    var datas = {"id":id,"resId":$("#resId").val()};
    if(pageId == "formpage2"){
        httpRequest("get","/djgl/ddjs/sqrgl/sqrxx/edit?type=2&superId="+id,datas,function (data){
            DisplayData.playData({data:data});
            if($("#opertation").val() == "VIEW") {
                var developmentType = "";
                if (data.data.developmentType == "01") {
                    developmentType = "入党申请人员";
                } else if (data.data.developmentType == "02") {
                    developmentType = "入党积极分子";
                } else if (data.data.developmentType == "03") {
                    developmentType = "计划发展对象"

                }
                $("#developmentType").text(developmentType);
                var politicalOutlook = ""
                if (data.data.politicalOutlook == "01") {
                    politicalOutlook = "团员";
                } else if (data.data.politicalOutlook == "02") {
                    politicalOutlook = "群众";
                }
                $("#politicalOutlook").text(politicalOutlook);

            }
        });
        if($('#applicationTime').val()!=""){
            $('#applicationTime').attr('readonly',true);
        }
       
    }else if(pageId == "formpage4"){
        if($("#applicationTime").val()!=""&&$("#developmentType").val()!="") {
            commitForm("0", false);
        }
        httpRequest("get","/djgl/ddjs/sqrgl/sqrxx/edit?type=3&superId="+id,datas,function (data){
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
        $("#right_table3").bootstrapTable('destroy');
        TableInit.init({
            domId: "right_table3",
            url: "/djgl/ddjs/dygl/dyxx/getListByType?type=4&superId="+id,
            columns: right_table_col3,
            rowStyle: function (row, index) {	//默认样式：居中，鼠标为手

                return {
                    classes: 'readOnly'
                    ,css: {"text-align":"center","cursor":""}
                };
            },
        readOnlyFn: function () {
            httpRequest("get","/djgl/ddjs/sqrgl/sqrxx/edit?type=3&superId="+id,datas,function (data){
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
    }else if(pageId == "formpage7"){
        if($("#applicationTime").val()!=""&&$("#developmentType").val()!=""){
            commitForm("0",false);
        }

        httpRequest("get","/djgl/ddjs/sqrgl/sqrxx/edit?type=4&superId="+id,datas,function (data){
           if(data.data.degree!=""){
                data.data.degree =Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_xw",type:"1"})[data.data.degree];
            }else{
                $("#degree").text( data.data.degree);
            }
            if(data.data.education!=""){
                data.data.education = Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_xl",type:"1"})[data.data.education];
            }else{
                $("#education").text( data.data.education);
            }
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
        $("#right_table4").bootstrapTable('destroy');
        TableInit.init({
            domId: "right_table4",
            url: "/djgl/ddjs/dygl/dyxx/getListByType?type=5&superId="+id,
            columns: right_table_col4,
            rowStyle: function (row, index) {	//默认样式：居中，鼠标为手

                return {
                    classes: 'readOnly'
                    ,css: {"text-align":"center","cursor":""}
                };
            },
            readOnlyFn: function () {
                httpRequest("get","/djgl/ddjs/sqrgl/sqrxx/edit?type=4&superId="+id,datas,function (data){
                    if(data.data.degree!=""){
                        data.data.degree =Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_xw",type:"1"})[data.data.degree];
                    }else{
                        $("#degree").text( data.data.degree);
                    }
                    if(data.data.education!=""){
                        data.data.education = Dictionary.getNameAndCode({mark:"dwgl_dygl_ryjbqk_xl",type:"1"})[data.data.education];
                    }else{
                        $("#education").text( data.data.education);
                    }
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

//	$("#subflag").val(flag);
    //console.log($("#subflag").val()+"a"+flag)
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
    var record = "";
    var res = "";
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
    	var all = $("#form").serialize().concat("&");
        $.ajax({
            type: "POST",
            url: "/djgl/ddjs/sqrgl/sqrxx/save",
            data:all,
            async: false,
            success:function(json){
                record = json.data.id;
            },
            error:function(){
            }
        });
    }
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