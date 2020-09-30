$(function(){
	var theRequest = GetRequest();
	$("#resId").val(theRequest.resId);
    $("#superOrgId").val(regVlaue(theRequest.superOrgId));
    $("#superId").val(regVlaue(theRequest.superId));
	$("#id").val(regVlaue(theRequest.id));
    $("#superOrgType").val(regVlaue(theRequest.superOrgType));
	$("#opertation").val(theRequest.oper);
    if($("#id").val()=="4028d08266cd1d0a0166cd462e260001"){
        var div = document.getElementById("jdw");
        div.style.display = "none";
    }
    Dictionary.init({position:"orgType",mark:"dwgl_orgType",type:"1",name:"orgType",domType:"select"});
	if($("#opertation").val() == "ADD") {
        //初始化字典项
        var dxz = "666";//党小组选项
        var mySelect = $("#orgType option");
        if ($("#superOrgType").val() == "621") { //党总支下边仅有党支部
            mySelect.each(function (i, el) {
                if ($(el).val() == dxz || $(el).val() == '611' || $(el).val() == '621') {
                    $(this).remove();
                }
            })
        } else {
            mySelect.each(function (i, el) {
                if ($(el).val() == dxz) {
                    $(this).remove();
                }
            })
        }

            var datas = {"id":$("#superId").val()};
        debugger
            httpRequest("get","/djgl/ddjs/shyk/zbdydh/getOrgName",datas,function (data){
                var  unitId =data.data[0].ASSOCIATIVE_UNIT_ID;
                $("#unitId").val(unitId);
            });
/*        if(getcookie("rolesNo").indexOf("D478") >= 0){
            $("#associativeUnitName").click(function(){
                openSelectZtree({'id':'associativeUnitId','name':'associativeUnitName','check':'','cancle':'','type':'2','isMulti':'1', 'asyn':true,'url':'/system/component/tree/deptTree','select':true});
            });
            $("#associativeUnitSpan").click(function(){
                openSelectZtree({'id':'associativeUnitId','name':'associativeUnitName','check':'','cancle':'','type':'2','isMulti':'1', 'asyn':true,'url':'/system/component/tree/deptTree','select':true});
            });
            $("#associativeUserName").unbind('click').bind('click', function (e) {
                openSelectZtree({'deptId':$("#associativeUnitId").val(), 'check':'ps','cancle':'ps','rolesNo':'D488,C433','id':'associativeUserId','name':'associativeUserName','type':'1','asyn':false,'level':'4','url':'/system/component/tree/getDeptUserByDeptIdAndRolesNo'});
            })
            $("#associativeUserSpan").unbind('click').bind('click', function (e) {
                openSelectZtree({'deptId':$("#associativeUnitId").val(), 'check':'ps','cancle':'ps','rolesNo':'D488,C433','id':'associativeUserId','name':'associativeUserName','type':'1','asyn':false,'level':'4','url':'/system/component/tree/getDeptUserByDeptIdAndRolesNo'});
            })
        }else{
            $.ajax({
                type: "get",
                url: "/djgl/ddjs/dzz/dzzgl/findOne",
                dataType: "json",
                data:{id: theRequest.superId},
                success: function (data) {
                    $("#associativeUnitName").click(function(){
                        openSelectZtree({'deptId': data.data.associativeUnitId,'id':'associativeUnitId','name':'associativeUnitName','check':'','cancle':'','type':'2','isMulti':'1', 'asyn':true,'url':'/system/component/tree/deptTree','select':true});
                    });
                    $("#associativeUnitSpan").click(function(){
                        openSelectZtree({'deptId': data.data.associativeUnitId,'id':'associativeUnitId','name':'associativeUnitName','check':'','cancle':'','type':'2','isMulti':'1', 'asyn':true,'url':'/system/component/tree/deptTree','select':true});
                    });
                  /!*  $("#associativeUserName").unbind('click').bind('click', function (e) {
                        openSelectZtree({'deptId':$("#associativeUnitId").val(), 'check':'ps','cancle':'ps','rolesNo':'D488,C433','id':'associativeUserId','name':'associativeUserName','type':'1','asyn':false,'level':'3','url':'/system/component/tree/getDeptUserByDeptIdAndRolesNo'});
                    })
                    $("#associativeUserSpan").unbind('click').bind('click', function (e) {
                        openSelectZtree({'deptId':$("#associativeUnitId").val(), 'check':'ps','cancle':'ps','rolesNo':'D488,C433','id':'associativeUserId','name':'associativeUserName','type':'1','asyn':false,'level':'3','url':'/system/component/tree/getDeptUserByDeptIdAndRolesNo'});
                    })*!/
                },
                error: function (data) {
                }
            })

        }*/

    }

        var belongAddress = {
            mark:"dwgl_belongAddress",        //字典类型唯一码值
            type:"1"		//要初始化的数据类型：1字典项；0字典类型
        };
        var  belongAddressMap = Dictionary.getNameAndCode(belongAddress);
        for(var key in belongAddressMap){
            $("#belongAddress").val(key);
            $("#belongAddressText").val(belongAddressMap[key]);
			break;
        }

        // if(getcookie("rolesNo").indexOf("D478") >= 0){
        //     $("#associativeUnitName").click(function(){
        //         openSelectZtree({'id':'associativeUnitId','name':'associativeUnitName','check':'','cancle':'','type':'2','isMulti':'1', 'asyn':true,'url':'/system/component/tree/deptTree','select':true});
        //     }); 0
        //     $("#associativeUnitSpan").click(function(){
        //         openSelectZtree({'id':'associativeUnitId','name':'associativeUnitName','check':'','cancle':'','type':'2','isMulti':'1', 'asyn':true,'url':'/system/component/tree/deptTree','select':true});
        //     });
        // }else{
        //     $.ajax({
        //         type: "get",
        //         url: "/djgl/ddjs/dzz/dzzgl/findOne",
        //         dataType: "json",
        //         data:{id: theRequest.id},
        //         success: function (data) {
        //             $("#associativeUnitName").click(function(){
        //                 openSelectZtree({'deptId': data.data.associativeUnitId,'id':'associativeUnitId','name':'associativeUnitName','check':'','cancle':'','type':'2','isMulti':'1', 'asyn':true,'url':'/system/component/tree/deptTree','select':true});
        //             });
        //             $("#associativeUnitSpan").click(function(){
        //                 openSelectZtree({'deptId': data.data.associativeUnitId,'id':'associativeUnitId','name':'associativeUnitName','check':'','cancle':'','type':'2','isMulti':'1', 'asyn':true,'url':'/system/component/tree/deptTree','select':true});
        //             });
        //         },
        //         error: function (data) {
        //         }
        //     })
        // }


	/**
	 * 初始化页面，数据加载、渲染
	 */
	if($("#id").val() != ""){
		// 表单数据渲染
		var datas = {"id":$("#id").val(),"resId":$("#resId").val()};
		httpRequest("get","/djgl/ddjs/dzz/dzzgl/findOne",datas,function (data){
            data.data.jcOrgId = data.data.orgId.substring(data.data.orgId.length-3);
            if($("#opertation").val() == "VIEW"){
                data.data.orgType = Dictionary.getNameAndCode({mark:"dwgl_orgType",type:"1"})[data.data.orgType];
                data.data.belongAddress = Dictionary.getNameAndCode({mark:"dwgl_belongAddress",type:"1"})[data.data.belongAddress];
			}else{
                var orgType=data.data.orgType;
                var mySelect=$("#orgType option");
                mySelect.each(function (i,el) {
                    if($(el).val() != orgType){
                        $(this).remove();
                    }
                })
                if(getcookie("rolesNo").indexOf("D478") >= 0){
                    $("#associativeUnitName").click(function(){
                        openSelectZtree({'id':'associativeUnitId','name':'associativeUnitName','check':'','cancle':'','type':'2','isMulti':'1', 'asyn':true,'url':'/system/component/tree/deptTree','select':true});
                    });
                    $("#associativeUnitSpan").click(function(){
                        openSelectZtree({'id':'associativeUnitId','name':'associativeUnitName','check':'','cancle':'','type':'2','isMulti':'1', 'asyn':true,'url':'/system/component/tree/deptTree','select':true});
                    });
                    $("#associativeUserName").unbind('click').bind('click', function (e) {
                        openSelectZtree({'deptId':$("#associativeUnitId").val(), 'check':'ps','cancle':'ps','rolesNo':'D488,C433','id':'associativeUserId','name':'associativeUserName','type':'1','asyn':false,'level':'4','url':'/system/component/tree/getDeptUserByDeptIdAndRolesNo'});
                    })
                    $("#associativeUserSpan").unbind('click').bind('click', function (e) {
                        openSelectZtree({'deptId':$("#associativeUnitId").val(), 'check':'ps','cancle':'ps','rolesNo':'D488,C433','id':'associativeUserId','name':'associativeUserName','type':'1','asyn':false,'level':'4','url':'/system/component/tree/getDeptUserByDeptIdAndRolesNo'});
                    })
                }else{
                    $("#associativeUnitName").click(function(){
                        openSelectZtree({'deptId': data.data.associativeUnitId,'id':'associativeUnitId','name':'associativeUnitName','check':'','cancle':'','type':'2','isMulti':'1', 'asyn':true,'url':'/system/component/tree/deptTree','select':true});
                    });
                    $("#associativeUnitSpan").click(function(){
                        openSelectZtree({'deptId': data.data.associativeUnitId,'id':'associativeUnitId','name':'associativeUnitName','check':'','cancle':'','type':'2','isMulti':'1', 'asyn':true,'url':'/system/component/tree/deptTree','select':true});
                    });
                    $("#associativeUserName").unbind('click').bind('click', function (e) {
                        openSelectZtree({'deptId':$("#associativeUnitId").val(), 'check':'ps','cancle':'ps','rolesNo':'D488,C433','id':'associativeUserId','name':'associativeUserName','type':'1','asyn':false,'level':'3','url':'/system/component/tree/getDeptUserByDeptIdAndRolesNo'});
                    })
                    $("#associativeUserSpan").unbind('click').bind('click', function (e) {
                        openSelectZtree({'deptId':$("#associativeUnitId").val(), 'check':'ps','cancle':'ps','rolesNo':'D488,C433','id':'associativeUserId','name':'associativeUserName','type':'1','asyn':false,'level':'3','url':'/system/component/tree/getDeptUserByDeptIdAndRolesNo'});
                    })
                }
            }
			DisplayData.playData({data:data});
		});
	}
})

function save() {
    var bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
        $.ajax({
            type: "POST",
            url:"/djgl/ddjs/dzz/dzzgl/save",
            data:$("#form").serialize(),
            dataType:"json",
            success:function(data){
                if ('1' == data.flag) {
                    if($("#id").val() == ""){
                        var count = 0;
                        var name = data.data.orgName;
                        $("#id").val(data.data.id);
                        if($('#ckb',window.parent.document).is(':checked')){
                            parent.dzzTree.addNode({id:data.data.id,pId:data.data.superId,name:name+"("+count+")",sname:name,orgType:data.data.orgType,count:count});// 在ztree中增加新保存的节点
                        }else{
                            parent.dzzTree.addNode({id:data.data.id,pId:data.data.superId,name:name,orgType:data.data.orgType,count:count});// 在ztree中增加新保存的节点
                        }
                        parent.refreshPage();
                    }else{
                        parent.dzzTree.updateNode(data);
                        // $("#yOrgId").val(yOrgId.substring(0,yOrgId.length-6)+$('#orgId').val());
                    }
                    layer.msg("保存成功！", {icon: 1,time:3000}, function (index) {
                    });
                }
            },
            error:function(data){

            }
        });
    }

}

/**
 * 空值设置
 * @param val
 * @returns
 */
function regVlaue(val){
	if(!val){
		val = "";
	}
	return val;
}