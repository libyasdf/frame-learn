package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.jbqk.controller;

/*import com.fr.web.core.A.T;*/
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.jbqk.services.OrgSituationService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TODO 党组织信息
 * @Author: 胡石阳
 * @Date: 2018/8/28 18:35
 */
@Controller
@RequestMapping("djgl/ddjs/dzz/jbqk/")
public class OrgSituationController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrgSituationService orgSituationService;

    /**
     * TODO 党组织基本情况
     * @author 胡石阳
     * @Date 2018年8月27日
     * @param
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询党组织基本情况")
    @ResponseBody
    @RequestMapping("getOrgSituation")
    public JSONObject getOrgSituation(String id){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            Object entity = orgSituationService.getOrgSituation(id);
            json.put("flag", "1");
            json.put("data", entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }

}
