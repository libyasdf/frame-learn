/**
 * Copyright 2018 SinoSoft. All Rights Reserved.
 */
package com.sinosoft.sinoep.modules.leaveManage.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.CookieUtil;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.Page;
import com.sinosoft.sinoep.modules.leaveManage.model.LeaveManage;
import com.sinosoft.sinoep.modules.leaveManage.service.LeaveManageService;
import com.sinosoft.sinoep.workflow.service.WorkFlowService;

import net.sf.json.JSONObject;



/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author 中科软科技  liangxiuhua
 * @since 2018年1月5日
 */
@Controller
@RequestMapping("/LeaveManage")
public class LeaveManageController {
    
    @Autowired
    LeaveManageService leaveManageService;
    @Autowired
    WorkFlowService workFlowService;
    
    /**
     * 
     * <B>方法名称：列表分页</B><BR>
     * <B>概要说明：</B><BR>
     * @param request
     * @return
     * @throws Exception Page
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public Page list(HttpServletRequest request, int pageNum, int showCount, String beginTime, String endTime,
            String title, String leaveType,
            String subflag) throws Exception {
        String userid = UserUtil.getCruUserId();
        String orgid = UserUtil.getCruOrgId();
        String sysid = ConfigConsts.SYSTEM_ID;
        if (userid != null && !"".equals(userid)) {
            Page page = leaveManageService.list(userid, pageNum, showCount, beginTime, endTime, title,leaveType,
                    subflag, orgid, sysid);
            return page;
        }
        return null;
    }
    
    /**
     * 
     * <B>方法名称：保存请假申请</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param leave:请假实体类
     * @param workitemid:流程id
     * @param type:类型
     * @param idea:意见
     * @param request
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "saveOrUpdate")
    public String saveOrUpdate(LeaveManage leave, String workitemid, String type, String idea, HttpServletRequest request) {
        String orgid = UserUtil.getCruOrgId();
        String sysid = ConfigConsts.SYSTEM_ID;
        String userid = UserUtil.getCruUserId();
        String id = request.getParameter("id");
        if (StringUtils.isNotBlank(userid)) {
            try {
                leave.setCreTime(DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss"));
                return leaveManageService.saveOrUpdate(leave, workitemid, type, idea, id, userid, orgid, sysid);
            }
            catch (Exception e) {
                
            }
        }
        return "";
    }
    /**
     * 
     * <B>方法名称：删除请假信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param id:请假id
     * @param request
     * @return
     * @throws Exception String
     */
    @ResponseBody
    @RequestMapping("/deletedoc")
    public String deletedoc(String id, HttpServletRequest request) throws Exception {
        String orgid = UserUtil.getCruOrgId();
        String sysid = ConfigConsts.SYSTEM_ID;
        String userid = UserUtil.getCruUserId();
        return leaveManageService.deleteDoc(id, userid, orgid, sysid);
    
    }
    /**
     * 
     * <B>方法名称：修改业务表状态</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param id:请假id
     * @param request
     * @return
     * @throws Exception JSONObject
     */
    @ResponseBody
    @RequestMapping("/updateSubFlag")
    public JSONObject updateSubFlag(String id, String subflag, HttpServletRequest request) throws Exception {
        JSONObject json = new JSONObject();
        String result = leaveManageService.updateSubFlag(id, subflag);
        if (!"success".equals(result)) {
            json.put("flag", 0);
        }
        json.put("flag", 1);
        return json;
    }
    /**
     * 
     * <B>方法名称：根据id获取请假信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @param id:请假id
     * @param request
     * @return
     * @throws Exception LeaveManagement
     */
    @ResponseBody
    @RequestMapping(value = "view")
    public LeaveManage view(String id, HttpServletRequest request) throws Exception {
        String orgid = UserUtil.getCruOrgId();
        String sysid = ConfigConsts.SYSTEM_ID;
        LeaveManage leave = leaveManageService.view(id, orgid, sysid);
        return leave;
    }

}
