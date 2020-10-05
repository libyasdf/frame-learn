package com.sinosoft.sinoep.workflow.subflag.controller;

import com.sinosoft.sinoep.workflow.subflag.entity.SysFlowSubflag;
import com.sinosoft.sinoep.workflow.subflag.services.SysFlowSubflagService;
import com.sinosoft.sinoep.workflow.util.WorkFlowUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import workflow.vo.FlowReadVO;
import workflow.vo.FlowWriteVO;

/**
 * TODO 流程状态控制类
 * @author 李利广
 * @Date 2019年01月14日 16:38:50
 */
@Controller
@RequestMapping("/workflow/subflag")
public class SysFlowSubflagController {

    private Log log = LogFactory.getLog(SysFlowSubflagController.class);

    @Autowired
    private SysFlowSubflagService service ;

    /**
     * TODO 保存、修改流程状态
     * @author 李利广
     * @Date 2019年01月14日 16:40:37
     * @param subflag
     * @param workItemId
     * @return net.sf.json.JSONObject
     */
    @RequestMapping("saveSubflag")
    @ResponseBody
    public JSONObject saveSubflag(SysFlowSubflag subflag,String workItemId){
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try {
            //三个参数，只要有一个为空，则根据workitemid获取这三个参数
            if (StringUtils.isBlank(subflag.getFileTypeId()) || StringUtils.isBlank(subflag.getWorkFlowId()) || StringUtils.isBlank(subflag.getRecordId())){
                if(StringUtils.isNotBlank(workItemId)){
                    FlowWriteVO writeVo = WorkFlowUtil.getWriteVoById(workItemId);
                    if (writeVo == null){
                        FlowReadVO readVO = WorkFlowUtil.getFlowReadVo(workItemId);
                        if (readVO != null){
                            subflag.setFileTypeId(readVO.getFiletypeid());
                            subflag.setWorkFlowId(readVO.getWorkflowid());
                            subflag.setRecordId(readVO.getRecordid());
                        }
                    }else{
                        subflag.setFileTypeId(writeVo.getFiletypeid());
                        subflag.setWorkFlowId(writeVo.getWorkflowid());
                        subflag.setRecordId(writeVo.getRecordid());
                    }
                }else{
                    json.put("msg","参数不正确");
                }
            }
            //只有三个参数都不为空，
            if (StringUtils.isNotBlank(subflag.getFileTypeId()) && StringUtils.isNotBlank(subflag.getWorkFlowId()) && StringUtils.isNotBlank(subflag.getRecordId())){
                subflag = service.saveSubflag(subflag);
                json.put("flag","1");
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            json.put("msg","更新流程状态失败");
        }
        json.put("data",subflag);
        return json;
    }
}
