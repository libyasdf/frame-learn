package com.sinosoft.sinoep.workflow.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.ep.webform.tool.Utiles;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.workflow.model.FlowSccredit;
import com.sinosoft.sinoep.workflow.service.FlowSccreditService;
import com.sinosoft.util.exception.DAOException;

import net.sf.json.JSONObject;

/**
 * 
 * <B>系统名称：</B><BR>
 * <B>模块名称：代办授权控制类</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 pangxj
 * @since 2018年1月4日
 */

@Controller
@RequestMapping("flowSccredit")
public class FlowSccreditController {

    @Resource
    FlowSccreditService flowSccreditService;
    @Resource
    UserInfoService userInfoService;

    /**
     * 
     * <B>方法名称：保存代办授权</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午5:53:55
     * @param flowSccredit：待办授权实体类
     * @param fileTypeItem：文件类型
     * @return String
     * @throws DAOException
     */
    @ResponseBody
    @RequestMapping(value = "save", produces = "application/json; charset=utf-8")
    public String save( @ModelAttribute FlowSccredit flowSccredit,
            @RequestParam(value = "fileTypeItem") String[] fileTypeItem) throws DAOException {
        String userId = UserUtil.getCruUserId();
        return flowSccreditService.save(userId, flowSccredit, fileTypeItem);
    }

    /**
     * 
     * <B>方法名称：删除代办授权</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午5:57:09
     * @param ids：多个授权实体id
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "del", produces = "application/json; charset=utf-8")
    public JSONObject delete(String ids) {
        JSONObject res = new JSONObject();
        res.put("flag", "0");
        String result = "删除授权信息失败！";
        if (Utiles.isNullStr(ids)) {
            return res;
        }
        result = flowSccreditService.delAuth(ids);
        res.put("flag", "1");
        res.put("msg", result);
        return res;
    }

    /**
     * 
     * <B>方法名称：查看代办授权</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午5:57:16
     * @param id：授权实体id
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "view", produces = "application/json; charset=utf-8")
    public String view(String id) {
        JSONObject res = new JSONObject();
        res.put("result", "0");
        String result = "获取授权信息失败！";
        if (Utiles.isNullStr(id)) {
            return res.toString();
        }
        FlowSccredit flowSccredit = flowSccreditService.viewAuth(id);
        if (!Utiles.isNull(flowSccredit)) {
            res = JSONObject.fromObject(flowSccredit);
            res.put("result", "1");
            res.put("msg", "获取授权信息成功！");
        }
        else {
            res.put("msg", result);
        }
        return res.toString();
    }

    /**
     * 
     * <B>方法名称：修改代办授权</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午5:57:26
     * @param flowSccredit：授权实体类
     * @param fileTypeItem：文件类型
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "update", produces = "application/json; charset=utf-8")
    public String update(@ModelAttribute FlowSccredit flowSccredit,
            @RequestParam(value = "fileTypeItem") String[] fileTypeItem) {
        JSONObject res = new JSONObject();
        res.put("result", "0");
        String result = "修改授权信息失败！";
        if (Utiles.isNull(flowSccredit) || fileTypeItem.length > 1 || fileTypeItem.length == 0) {
            return res.toString();
        }
        //根据授权用户id获取部门id
        String deptId = userInfoService.getDeptId(flowSccredit.getUserid());
        //根据被授权人id获取被授权部门id
        String sccreditdeptId = userInfoService.getDeptId(flowSccredit.getSccredituserid());
        result = flowSccreditService.updateAuth(deptId, sccreditdeptId, fileTypeItem[0], flowSccredit);
        res.put("result", "1");
        res.put("msg", result);
        return res.toString();
    }

    /**
     * 
     * <B>方法名称：根据条件查询数据，出列表用，要做分页查询</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午5:57:34
     * @param flowSccredit：授权实体类
     * @return String
     * @throws DAOException
     */
    @RequestMapping(value = "flowSccreditList", produces = "application/json; charset=utf-8")
    @ResponseBody
    public PageImpl queryFlowSccredit(@ModelAttribute FlowSccredit flowSccredit, PageImpl pageImpl) throws DAOException {
        String userid = UserUtil.getCruUserId();
        if (pageImpl.getPageSize() == null) {
        	pageImpl.setPageSize(ConfigConsts.PAGE_SIZE);
        }
        Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
        pageImpl = flowSccreditService.queryFlowSccredit(pageable, pageImpl, flowSccredit, userid);
        return pageImpl;
    }

    /**
     * 
     * <B>方法名称：获取所有流程分类 待办授权使用</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午5:59:07
     * @param request
     * @return String
     */
    @ResponseBody
    @RequestMapping("/getLicenseFlowType")
    public String getLicenseFlowType(HttpServletRequest request) {
        String userId = UserUtil.getCruUserId();
        return flowSccreditService.getLicenseFlowType(userId);
    }

    /**
     * 
     * <B>方法名称：获取所有流程分类 待办授权使用</B><BR>
     * <B>概要说明：根据SYSId， orgId筛选当前系统的流程分类</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午5:59:07
     * @param request
     * @return String
     */
    @ResponseBody
    @RequestMapping("/getFlowTypeList")
    public String getFlowTypeList(HttpServletRequest request) {
        String orgId = UserUtil.getCruOrgId();
        return flowSccreditService.getFlowTypeList(orgId);
    }

}
