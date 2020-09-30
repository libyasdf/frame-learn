package com.sinosoft.sinoep.modules.djgl.ddjs.bbgl.controller;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.bbgl.service.BbglService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 报表管理Controller
 * @Author: 李帅
 * @Date: 2018/8/28 19:35
 */
@Controller
@RequestMapping("/djgl/ddjs/bbgl/histroyTotal")
public class BbglController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BbglService bbglService;

    /**
     * 报表管理保存历史汇总数据
     */

    @LogAnnotation(value = "save",opName = "保存报表管理历史汇总")
    @RequestMapping("saveHistroyTotal")
    @ResponseBody
    public JSONObject saveDkgl(String tableName) {
        JSONObject json = new JSONObject();
        String status=null;
        try {
            status = bbglService.save(tableName);
            json.put("flag", 1);
            json.put("status", status);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }
}
