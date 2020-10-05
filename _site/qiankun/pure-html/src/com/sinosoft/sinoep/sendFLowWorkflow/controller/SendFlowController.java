package com.sinosoft.sinoep.sendFLowWorkflow.controller;

import com.sinosoft.sinoep.sendFLowWorkflow.entity.*;
import com.sinosoft.sinoep.sendFLowWorkflow.services.SendFlowInfoService;
import com.sinosoft.sinoep.sendFLowWorkflow.services.SendFlowObjectService;
import com.sinosoft.sinoep.sendFLowWorkflow.services.SendFlowService;
import com.sinosoft.sinoep.sendFLowWorkflow.services.SendFlowTemplateService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "sendFlow")
public class SendFlowController {

    public static Log log = LogFactory.getLog(SendFlowController.class);

    @Autowired
    private SendFlowInfoService sendFlowInfoService;
    @Autowired
    private SendFlowObjectService sendFlowObjectService;
    @Autowired
    private SendFlowService sendFlowService;
    @Autowired
    private SendFlowTemplateService sendFlowTemplateService;

    /**
     * 添加流程实例和生成报送情况
     * @param templateId
     * @return
     */
    @RequestMapping(value = "/addSendFlowObject")
    @ResponseBody
    public JSONObject addSendFlowObject(String templateId){
        JSONObject json = new JSONObject();
        boolean flag =false;
        if(StringUtils.isNotEmpty(templateId)) {
            /*if (templateId.indexOf(",") != -1) {
            } else {*/
                this.sendFlowInfoService.generateFlowInfo(templateId);
                flag = true;
           /* }*/
        }
        json.put("flag",flag);
        return json;
    }

    /**
     * 获取报送情况
     * @param param
     * @return
     */
    @RequestMapping(value = "/getSendFlowInfo")
    @ResponseBody
    public Map<String,List<SendFlowInfo>> getWaitDoReadList(WaitDoParamBean param){
        String objectId = param.getObjectId();
        if(StringUtils.isBlank(param.getObjectId())){
            objectId = this.sendFlowObjectService.getObjectIdCruYearOrMonthByTemplateId(param.getTemplateId(),param.getRuleType());
            param.setObjectId(objectId);
        }
        Map<String,List<SendFlowInfo>> waitDoMap = this.sendFlowService.getWaitDoList(param);
        return waitDoMap;
    }

    /**
     * 修改报送情况
     * @param status
     * @param templateId
     * @param objectId
     * @param deptId
     * @return
     */
    @RequestMapping("/updateSendFlowInfo")
    @ResponseBody
    public boolean updateSendFlowInfo(String status,String templateId,String objectId,String deptId){
        return this.sendFlowInfoService.updateSendFlowInfo(status,templateId,objectId,deptId);
    }

    /**
     * 获取报送情况数量
     * @param paramBean
     * @return
     */
    @RequestMapping("/getCountInfo")
    @ResponseBody
    public List<CountInfo> getCountInfo(WaitDoParamBean paramBean){
        return sendFlowService.getCountInfo(paramBean);
    }

    /**
     * 获取当前月实例id
     * @param templateId
     * @return
     */
    @RequestMapping("/getObjectId")
    @ResponseBody
    public String getObjectId(String templateId){
        return this.sendFlowObjectService.getObjectId(templateId);
    }

    /**
     * TODO 根据templateId查找实例
     * @author 李利广
     * @Date 2019年05月15日 17:52:17
     * @param templateId
     * @return java.util.List<com.sinosoft.sinoep.sendFLowWorkflow.entity.SendFlowObject>
     */
    @RequestMapping("/objectNameList")
    @ResponseBody
    public List<SendFlowObject> objectNameList(String templateId){
        return this.sendFlowObjectService.objectNameList(templateId);
    }
}
