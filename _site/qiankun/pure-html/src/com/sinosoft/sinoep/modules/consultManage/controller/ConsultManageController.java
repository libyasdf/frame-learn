package com.sinosoft.sinoep.modules.consultManage.controller;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.CookieUtil;
import com.sinosoft.sinoep.common.util.Page;
import com.sinosoft.sinoep.modules.consultManage.model.ConsultManage;
import com.sinosoft.sinoep.modules.consultManage.service.ConsultManageService;
import com.sinosoft.sinoep.workflow.service.WorkFlowService;

import net.sf.json.JSONObject;

/**
 *
 * <B>系统名称：请示</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 *
 * @author 中科软科技luyuewen
 * @since 2017年8月25日
 */
@Controller
@RequestMapping("/ConsultManage")
public class ConsultManageController {

    @Autowired
    ConsultManageService consultManageService;

    @Autowired
    WorkFlowService workFlowService;

    /**
     * 保存或者修改
     *
     * @param entity 咨询管理实体
     * @param id 主键id
     * @param workitemid 待办工作项ID
     * @param idea 意见
     * @param request
     *
     * @return String
     */
    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(ConsultManage entity, String workitemid, String idea, String id,
                               HttpServletRequest request) {
        String userid = UserUtil.getCruUserId();
        String orgid = "";
        String sysid = ConfigConsts.SYSTEM_ID;
        if (StringUtils.isNotBlank(userid)) {
            return consultManageService.saveOrUpdate(entity, workitemid, idea, id, userid, orgid, sysid);
        }
        return "";
    }

    /**
     * 获取草稿数量
     *
     * @param request
     * @param subflag 状态
     * @param flowType 公文类型
     * @return Long
     */
    @ResponseBody
    @RequestMapping("/getCount")
    public Long getCount(HttpServletRequest request, String subflag, String flowType) {
        String userid = UserUtil.getCruUserId();
        String orgid = "";
        return consultManageService.getCount(subflag, userid, orgid, ConfigConsts.SYSTEM_ID, flowType);

    }

    /**
     *
     * <B>方法名称：根据ID删除发文</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param id
     *            发文ID
     * @return result
     *         成功或失败
     */
    @ResponseBody
    @RequestMapping("/deletedoc")
    public String deleteDoc(String id, HttpServletRequest request) {
        String userid = UserUtil.getCruUserId();
        String orgid = "";
        return consultManageService.deleteDoc(id, userid, orgid, ConfigConsts.SYSTEM_ID);
    }

    /**
     *
     * <B>方法名称：修改状态</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param id
     *            业务ID subflag 状态
     * @return json
     *         flag 状态
     */
    @ResponseBody
    @RequestMapping("/updateFlag")
    public JSONObject updateSubFlag(String id, String subFlag, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String result = consultManageService.updateSubFlag(id, subFlag);
        if (!"success".equals(result)) {
            json.put("flag", 0);
        }else {
            json.put("flag", 1);
        }
        return json;
    }

    /**
     * 获取具体的表单信息
     * @param id
     *            表单ID
     * @return ConsultManage
     *         表单实体
     */
    @ResponseBody
    @RequestMapping("/view")
    public ConsultManage view(String id) {
        ConsultManage consult = consultManageService.view(id);
        return consult;

    }

    /**
     * 分页查询草稿
     *
     * @return page
     *         分页数据
     */
    @ResponseBody
    @RequestMapping("/getPageList")
    public Page getPageList(HttpServletRequest request, int pageNum, int showCount, String endTime, String beginTime,
                            String title) {
        String userid = UserUtil.getCruUserId();
        if (userid != null && !"".equals(userid)) {
            Page page = consultManageService.pageList(userid, pageNum, showCount, title, endTime, beginTime);
            return page;
        }
        return null;
    }

}
