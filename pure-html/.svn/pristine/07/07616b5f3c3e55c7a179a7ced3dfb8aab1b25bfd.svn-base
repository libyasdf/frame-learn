package com.sinosoft.sinoep.waitNoflow.controller;

import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.uias.util.RecourseUtils;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.waitNoflow.entity.SysWaitNoflow;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "sysWaitNoflowController")
public class SysWaitNoflowController {

    @Autowired
    private SysWaitNoflowService service;

    /**
     * 插入不走流程待办
     * @return
     */
    @LogAnnotation(opType = OpType.SAVE,opName = "插入不走流程待办")
    @RequestMapping("/sendWaitNoflow")
    @ResponseBody
    public JSONObject sendWaitNoflow(SysWaitNoflow waitNoflw,String subject,String content,String messageURl){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        if(StringUtils.isNotBlank(waitNoflw.getReceiveDeptId())){
            service.saveWaitNoflow(waitNoflw,subject,content,messageURl);
            json.put("flag","1");
        }
        return json;
    }
    
    /**
     * 删除非流程待办
     * TODO 
     * @author 武帅
     * @Date 2018年11月1日 下午7:20:47
     * @param id
     * @param sourceId
     * @param receiveDeptId
     * @param receiveUserId
     * @param rolesNo
     * @return
     */
    @LogAnnotation(opType = OpType.DELETE,opName = "删除不走流程待办")
    @RequestMapping("/deleteWaitNoflow")
    @ResponseBody
    public JSONObject deleteWaitNoflow(String id,String sourceId,String receiveDeptId,String receiveUserId,String rolesNo){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        int num =service.deleteWaitNoflow(id,sourceId,receiveDeptId,receiveUserId,rolesNo);
        if (num !=0){
            json.put("flag","1");
        }
        return json;
    }
    
    /**
     * 根据ID查询一条非流程待办
     * TODO 
     * @author 李利广
     * @Date 2018年11月1日 下午7:22:04
     * @param id
     * @return
     */
    @LogAnnotation(opType = OpType.QUERY,opName = "根据ID查询非流程待办")
    @RequestMapping("/getSysWaitNoflow")
    @ResponseBody
    public JSONObject getSysWaitNoflow(String id){
    	JSONObject json = new JSONObject();
        json.put("flag","0");
        try {
			SysWaitNoflow noflow = service.getSysWaitNoflow(id);
			json.put("flag","1");
			json.put("data",noflow);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return json;
    }

    /**
     * 根据不走流程待办id获取改用户是否有权限打开改待办（处理一人多岗）
     * @param workItemId
     * @return
     */
    @RequestMapping("getOpenDaiBanQx")
    @ResponseBody
    public JSONObject getOpenDaiBanQx(String workItemId){
        JSONObject json = new JSONObject();
        json.put("flag","1");
        //是否拥有权限标识
        json.put("isHaveQx","0");
        try{
            SysWaitNoflow noflow = service.getSysWaitNoflow(workItemId);
            if(noflow != null){
                String userId = UserUtil.getCruUserId();
                String deptId = UserUtil.getCruDeptId();
                //获取当前登录人所在部门下业务角色编号
                String roleNos = RecourseUtils.getFlowRoleNoByDept(userId,deptId);
                String dbDeptId = noflow.getReceiveDeptId();
                String dbRoles = noflow.getRolesNo();
                //只对部门下业务角色类型待办处理（其他的待办没有问题）
                if(StringUtils.isNotEmpty(dbDeptId) && StringUtils.isNotEmpty(dbRoles)){
                    if (StringUtils.isNotBlank(roleNos)){
                        String[] roleNo = roleNos.split(",");
                        for(int i = 0;i < roleNo.length; i++){
                            if(dbRoles.contains(roleNo[i])){
                                json.put("isHaveQx","1");
                            }
                        }
                    }
                }else{
                    json.put("isHaveQx","1");
                }
            }else {
                json.put("isHaveQx","1");
            }
        }catch (Exception e){
            json.put("flag","0");
            e.printStackTrace();
        }
        return json;
    }
}
