package com.sinosoft.sinoep.workflow.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.sinoep.user.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.Page;
import com.sinosoft.sinoep.workflow.service.CountersignService;
import com.sinosoft.sinoep.workflow.service.WorkFlowService;

import net.sf.json.JSONObject;

/**
 * 
 * <B>系统名称：</B><BR>
 * <B>模块名称：会签操作模块</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：添加，删除会签,强制添加和删除</B><BR>
 * 
 * @author 中科软科技 pangxj
 * @since 2018年1月4日
 */
@Controller
@RequestMapping(value = "operationcs")
public class OperationCountersignController {

    @Autowired
    CountersignService countersignService;
    
    @Autowired
    WorkFlowService workFlowService;

    /**
     * 
     * <B>方法名称：对办结列表进行列表展示</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午6:00:12
     * @param workitemid：工作项id
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "check")
    public String check(String workitemid) {
        JSONObject json = new JSONObject();
        String type = ConfigConsts.SERVICE_TYPE;
        String flag = "false";
        if (countersignService.addAndDelHq_list(workitemid, type)) {
            flag = "true";
        }
        else {
            flag = "false";
        }
        json.put("flag", flag);
        return json.toString();
    }

    /**
     * 
     * <B>方法名称：对办结列表进行列表展示</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午6:00:19
     * @param workitemid：工作项id
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "addDel")
    public String addAndDelHq(String workitemid) {
        String type = ConfigConsts.SERVICE_TYPE;
        return countersignService.addAndDelHq(workitemid, type);
    }

    /**
     * 
     * <B>方法名称：对办结列表进行列表展示</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午6:00:34
     * @param workitemid：工作项id
     * @param delHq：删除的多个会签分支（用","隔开）
     * @param addHq：新增的多个会签分支（用","隔开）
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "add_Del", produces = "application/json;charset=UTF-8")
    public String addAndDelHqDo(String workitemid, String delHq, String addHq) {
        String type = ConfigConsts.SERVICE_TYPE;
        return countersignService.addAndDelHq_do(workitemid, delHq, addHq, type);
    }

    /**
     * 
     * <B>方法名称：展示强制增加会签列表</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午6:00:40
     * @param workitemid：工作项id
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "forceaddDel")
    public String forceAddAndDelHq(String workitemid) {
        String type = ConfigConsts.SERVICE_TYPE;
        return countersignService.forceAddHq(workitemid, type);
    }

    /**
     * 
     * <B>方法名称：对办结列表进行强制增删</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午6:00:49
     * @param workitemid：工作项id
     * @param addHq：新增的多个会签分支（用","隔开）
     * @param delHq：删除的多个会签分支（用","隔开）
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "forceadd_Del", produces = "application/json;charset=UTF-8")
    public String forceaddAndDelHqDo(String workitemid, String addHq, String delHq) {
        String type = ConfigConsts.SERVICE_TYPE;
        return countersignService.forceaddHq_do(workitemid, addHq, delHq, type);
    }

    /**
     * 
     * <B>方法名称：对办结列表进行判断状态</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午6:01:01
     * @param workitemid：工作项id
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "forcecheck")
    public String focecheckOperCs(String workitemid) {
        JSONObject json = new JSONObject();
        String flag = "false";
        String type = ConfigConsts.SERVICE_TYPE;
        if (countersignService.isDisForceAddHq(workitemid, type)) {
            flag = "true";
        }
        else {
            flag = "false";
        }
        json.put("flag", flag);
        return json.toString();
    }

    /**
     * 
     * <B>方法名称：强制会签列表</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午6:01:09
     * @param request
     * @return Page
     * @throws UnsupportedEncodingException
     */
    @ResponseBody
    @RequestMapping("forcehqlist")
    public Page getForceHq(HttpServletRequest request) throws UnsupportedEncodingException {
        String userid = UserUtil.getCruUserId();
        String pageNum = request.getParameter("pageNum");
        String endTime = request.getParameter("endTime");
        String beginTime = request.getParameter("startTime");
        String title = request.getParameter("title");
        String type = ConfigConsts.SERVICE_TYPE;
        Page page = workFlowService.getForceHqList(userid, pageNum, endTime, beginTime, title, type);
        return countersignService.list(page);
    }
}
