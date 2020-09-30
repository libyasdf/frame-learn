
var url_dev = "http://localhost:8080";    // 开发环境
var url_test = "http://192.168.90.135:8080";  // 测试环境
var url_proxy = "/api";   // 本地环境 webpack.proxy跨域
/******************************** 接口地址 ********************************/
var url = url_dev;
var urlSsoLogout =  "http://218.240.159.110:6680/sso/ticket/logout";

var urlDeptsSize = url+'/user/checkDeptsSize';  // 获取用户是否为一人多岗
var urlGetDepts = url+'/user/getDepts'; // 获取用户岗位
var urlSetSession = url+'/user/setSession'; // 设置用户session信息
var urlSetUserCookie = url+'/setUserInfoToCookie'; // 设置用户信息到cookie中
var urlGetUserView = url+'/user/view'; // 获取用户信息
var urlGetCurrentTime = url+'/system/date/getCurrentTime'; // 获取服务器的当前时间
var checkDeptPage = '/modules/dept/checkDept.html';  // 选择部门弹框页面

var urlGetUserSize = url+'/user/getUserSize';   // 获取在线用户
var urlGetUserImg = url+'/user/getUserImg';  // 获取用户头像
var urlGetMenu = url+'/recourse/top';   // 获取菜单接口
var urlMenuTab = url+'/recourse/getRecoursesByPid';  // 获取菜单集合
var urlReadCount = url+'/notity/readcount';  // 获取已读或未读消息数量
var urleMailCount = url+'/mypage/mail/getCount';  // 获取已读或未读消息数量
var urlGetWaitdoList = url+'/system/waitdo/getWaitdoList';  // 获取待办数据
var urlGetFlowReadList = url+'/workflow/getFlowReadList';  // 获取已办数据
var urlGetSendList = url+'/system/flowSend/getSendList';  // 获取待阅或已阅数据
var urlUpByRead = url+'/system/flowSend/updateByReadTime'; // 待阅请求
var urlGetInfoNoticeList = url+'/system/notice/getInfoNoticeList';  // 获取信息和提醒内容
var urlGetResourceInfo = url+'/recourse/getResourceInfo'; // 根据资源id获取资源信息
var urlOftenModel = url+'/mypage/oftenModel/getData'; // 获取常用模块id和名称 
var urlResourceList = url+'/recourse/resourceList';  // 获取常用模块数据
var urlGetlist1 = url+'/mypage/workplan/getlist1'; // 获取某月数据和状态
var urlGetlistBootHql = url+'/mypage/workplan/getlistBootHql';  // 获取某一天的工作计划或日志
var urlUpFinish = url+'/mypage/workplan/upFinish';  // 转为日志接口
var urlGetByDate = url+'/diningMenu/getByDate';  // 获取某天早餐和午餐接口
var urlGetUtilLinkList = url+'/system/config/utilLink/getList'; // 获取常用工具
var urlAffixList = url+'/system/component/affix/list';
var urlGetAppList = url+'/system/config/application/getList'; // 获取应用导航数据

/**
 * 封装jquery Ajax请求方法
 * @param {*} method 请求类型
 * @param {*} url 请求地址
 * @param {} data 请求参数
 * @param {} fnSuccess 成功回调函数
 * @param {} fnError 失败回调函数
 * @param {} fnComplete 请求完成之后回调函数,无论成功或失败都会执行
 * @param {} fnBeforeSend 在发送请求之前调用
 */
function ajax(method,url,data,fnSuccess,fnError,fnComplete,fnBeforeSend) {
    $.ajax({
        type:method,
        url:url,
        data:data,
        // headers: {
        //     Accept: "application/json; charset=utf-8"
        // },
        async: true,
        dataType: "json",
        crossDomain: true == !(document.all),
        beforeSend:function (request) {
            fnBeforeSend ? fnBeforeSend(request) : '';
        },
        success:function(result){
            fnSuccess ? fnSuccess(result) :''; 
        },
        error:function(err){
            console.error(err)
            fnError ? fnError(result) : '';
        },
        complete:function (request) {
            fnComplete ? fnComplete(request) :'';
        }
    });
}

// 接口对象
function Api() {
    this.currentTime = ''; //当前时间
    this.url = url;
    this.url_proxy = url_proxy;
    this.openUrl = url;
    this.urlSsoLogout = urlSsoLogout;

    this.urlGetUserSize = urlGetUserSize;
    this.urlSetSession = urlSetSession;
    this.urlSetUserCookie = urlSetUserCookie;
    this.urlGetUserView = urlGetUserView;
    this.urlGetCurrentTime = urlGetCurrentTime;
    this.checkDeptPage = this.openUrl+checkDeptPage;
    
    this.urlGetUserImg = urlGetUserImg;
    this.urlGetMenu = urlGetMenu;
    this.urlMenuTab = urlMenuTab;
    this.urlGetWaitdoList = urlGetWaitdoList;
    this.urlGetFlowReadList = urlGetFlowReadList;
    this.urlGetSendList = urlGetSendList;
    this.urlUpByRead = urlUpByRead;
    this.urlGetInfoNoticeList = urlGetInfoNoticeList;
    this.urlGetResourceInfo = urlGetResourceInfo;
    this.urlOftenModel = urlOftenModel;
    this.urlResourceList = urlResourceList;
    this.urlGetlist1 = urlGetlist1;
    this.urlGetlistBootHql = urlGetlistBootHql;
    this.urlUpFinish = urlUpFinish;
    this.urlGetByDate = urlGetByDate;
    this.urlGetUtilLinkList = urlGetUtilLinkList;
    this.urlGetAppList = urlGetAppList;
    this.urlReadCount = urlReadCount;
    this.urleMailCount = urleMailCount;
    this.urlDeptsSize = urlDeptsSize;
    this.urlAffixList = urlAffixList;
    this.urlGetDepts = urlGetDepts;
}

/******************************** 顶部接口 ********************************/
// 获取用户是否为一人多岗
Api.prototype.getDeptsSize = function getDeptsSize(data,fnSuccess){
    ajax('post',this.urlDeptsSize,data,fnSuccess);
}
// 获取用户岗位
Api.prototype.getDepts = function getDepts(data,fnSuccess){
    ajax('post',this.urlGetDepts,data,fnSuccess);
}
// 设置用户session信息
Api.prototype.setSession = function setSession(data,fnSuccess){
    ajax('post',this.urlSetSession,data,fnSuccess);
}
// 获取用户信息，存到cookie中
Api.prototype.setUserCookie = function setUserCookie(data,fnSuccess){
    ajax('post',this.urlSetUserCookie,data,fnSuccess);
}
// 获取用户登录信息
Api.prototype.getUserView = function getUserView(data,fnSuccess){
    ajax('post',this.urlGetUserView,data,fnSuccess);
}
// 获取服务器当前时间
Api.prototype.getCurrentTime = function getCurrentTime(data,fnSuccess){
    ajax('get',this.urlGetCurrentTime,data,fnSuccess);
}

// 获取当前在线人数
Api.prototype.getUserSize = function getUserSize(data,fnSuccess){
    ajax('post',this.urlGetUserSize,data,fnSuccess);
}
// 获取用户头像
Api.prototype.getUserImg = function getUserImg(data,fnSuccess){
    ajax('post',this.urlGetUserImg,data,fnSuccess);
}

/******************************** 左侧接口 ********************************/
// 获取菜单内容
Api.prototype.getMenu = function getMenu(data,fnSuccess){
    ajax('post',this.urlGetMenu,data,fnSuccess);
}
// 获取
Api.prototype.getMenuTab = function getMenuTab(data,fnSuccess){
    ajax('post',this.urlMenuTab,data,fnSuccess);
}
/******************************** 内容模块接口 ********************************/
// 获取待办数据
Api.prototype.getWaitdoList = function getWaitdoList(data,fnSuccess){
    ajax('post',this.urlGetWaitdoList,data,fnSuccess);
}
// 获取已办数据 
Api.prototype.getFlowReadList = function getFlowReadList(data,fnSuccess){
    ajax('post',this.urlGetFlowReadList,data,fnSuccess);
}
// 获取已读或未读消息数量
Api.prototype.readcount = function readcount(data,fnSuccess){
    ajax('post',this.urlReadCount,data,fnSuccess);
}
// 获取未读邮件和已读邮件数量
Api.prototype.getEmailCount = function getEmailCount(data,fnSuccess){
    ajax('post',this.urleMailCount,data,fnSuccess);
}
// 获取待阅或已阅数据 
Api.prototype.getSendList = function getSendList(data,fnSuccess){
    ajax('post',this.urlGetSendList,data,fnSuccess);
}
// 待阅请求
Api.prototype.getUpByRead = function getUpByRead(data,fnSuccess) {
    ajax('post',this.urlUpByRead,data,fnSuccess)
}
// 获取信息和提醒内容
Api.prototype.getInfoNoticeList = function getInfoNoticeList(data,fnSuccess){
    ajax('post',this.urlGetInfoNoticeList,data,fnSuccess);
}
// 根据资源id获取资源信息
Api.prototype.getResourceInfo = function getResourceInfo(data,fnSuccess){
    ajax('post',this.urlGetResourceInfo,data,fnSuccess);
}
// 获取常用模块Id列表
Api.prototype.getOftenModel = function getOftenModel(data,fnSuccess){
    ajax('post',this.urlOftenModel,data,fnSuccess);
}
// 获取常用模块数据
Api.prototype.getResourceList = function getResourceList(data,fnSuccess){
    ajax('post',this.urlResourceList,data,fnSuccess);
}
// 获取某月数据和状态
Api.prototype.getlist1 = function getlist1(data,fnSuccess){
    ajax('post',this.urlGetlist1,data,fnSuccess);
}
// 获取某一天的工作计划和日志
Api.prototype.getlistBootHql = function getlistBootHql(data,fnSuccess){
    ajax('post',this.urlGetlistBootHql,data,fnSuccess);
}
// 转为日志接口
Api.prototype.upFinish = function upFinish(data,fnSuccess){
    ajax('post',this.urlUpFinish,data,fnSuccess);
}
// 获取某天早餐和午餐接口
Api.prototype.getRecipeByDate = function getRecipeByDate(data,fnSuccess){
    ajax('post',this.urlGetByDate,data,fnSuccess);
}
// 获取最新的常用工具
Api.prototype.getUtilLinkList = function getUtilLinkList(data,fnSuccess){
    ajax('post',this.urlGetUtilLinkList,data,fnSuccess);
}
// 判断常用工具有无附件
Api.prototype.getAffixList = function getAffixList(data,fnSuccess){
    ajax('get',this.urlAffixList,data,fnSuccess);
}
// 获取应用导航数据
Api.prototype.getAppList = function getAppList(data,fnSuccess){
    ajax('post',this.urlGetAppList,data,fnSuccess);
}

var Api = new Api();