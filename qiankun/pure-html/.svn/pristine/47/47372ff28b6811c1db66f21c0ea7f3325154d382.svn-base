package com.sinosoft.sinoep.urge.controller;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.urge.entity.SysUrge;
import com.sinosoft.sinoep.urge.services.SysUrgeService;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import workflow.common.JDateToolkit;

/**
 * @Author 王富康
 * @Description //TODO 催办记录Controller层
 * @Date 2018/8/30 19:34
 * @Param
 * @return
 **/
@Controller
@RequestMapping("SysUrge")
public class SysUrgeController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUrgeService sysUrgeService;

    /**
     * @Author 王富康
     * @Description //TODO 保存催办记录，一条消息，一条记录。催办多人，保存多条记录。
     * @Date 2018/7/30 11:53
     * @Param [con]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "save",opName = "保存催办记录")
    @RequestMapping("saveUrge")
    @ResponseBody
    public JSONObject saveUrge(SysUrge sysUrge,String userIDs) {

        JSONObject json = new JSONObject();
        try {
            String[] userIds = userIDs.split(",");
            for(int i = 0;i<userIds.length;i++){
                SysUrge sysUrge1 = new SysUrge();
                    sysUrge1.setAccepterId(userIds[i]);
                    sysUrge1.setSubject(sysUrge.getSubject());
                    sysUrge1.setContent(sysUrge.getContent());
                    sysUrge1.setModuleType(sysUrge.getModuleType());
                    sysUrge1.setSenderId(UserUtil.getCruUserId());
                    sysUrge1.setSendTime(JDateToolkit.getNowDate4());
                    sysUrge1.setAcceptTime(JDateToolkit.getNowDate4());
                    sysUrgeService.saveUrge(sysUrge1);
                }
            json.put("flag", 1);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }
}
