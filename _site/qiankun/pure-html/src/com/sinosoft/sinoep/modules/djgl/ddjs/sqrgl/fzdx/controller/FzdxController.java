package com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.fzdx.controller;/**
 * Created by s on 2018/9/15.
 */

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.fzdx.entity.DdjsSqrglPartyconsiderationEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.fzdx.service.FzdxServiceI;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Description :TODO 预备党员审议 Controller
 * @Author: 李帅
 * @Date: 2018/9/15 14:03
 */
@Controller
@RequestMapping("djgl/ddjs/sqrgl/fzdx/")
public class FzdxController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FzdxServiceI ybdyshService;
    @Autowired
    private DwxtOrgService dwxtOrgService;
    /**
     * @Description :TODO
     * @Author: 李帅
     * @Date: 2018/9/15 14:11
     */
    @LogAnnotation(value = "save",opName = "保存预备党员审议")
    @RequestMapping("saveYbdysy")
    @ResponseBody
    public JSONObject saveYbdysy(DdjsSqrglPartyconsiderationEntity ybdysyEntity,String typeOfPersonnel) {
        JSONObject json = new JSONObject();
        try {
            ybdysyEntity = ybdyshService.saveYbdysy(ybdysyEntity,typeOfPersonnel);
            json.put("flag", 1);
            json.put("data", ybdysyEntity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     * TODO 打开只读、修改页面时，查询数据进行渲染
     * @author 李帅
     * @Date 2018年8月28日
     * @param
     * @param id
     * @return
     */
    @LogAnnotation(value = "edit",opName = "修改页面")
    @ResponseBody
    @RequestMapping("edit")
    public JSONObject edit( String id){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        DdjsSqrglPartyconsiderationEntity ybdysy = null;
        try {
            ybdysy = ybdyshService.getById(id);
            json.put("flag", "1");
            json.put("data", ybdysy);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }


    /**
     * TODO 申请人简要信息
     * @author 李帅
     * @Date 2018年9月16日
     * @param
     * @param id
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询申请人简要信息")
    @ResponseBody
    @RequestMapping("getApplicantStatistics")
    public JSONObject getApplicantStatistics( String id,String type,String orgId,String annual, DwxtOrg dwxtOrg){
        List<Map<String,Object>> list = dwxtOrgService.getTree(dwxtOrg);
        if(list.size() > 0) {
            String ids = "";
            for (Map<String, Object> map : list) {
                if (map.get("type").toString().equals("org")) {
                    ids += map.get("id").toString() + ",";
                }
            }
            ids = ids.substring(0, ids.length() - 1);
            JSONObject json = new JSONObject();
            json.put("flag", "0");
            try {
                Object entity = ybdyshService.applicantStatistics(annual,ids);
                json.put("flag", "1");
                json.put("data", entity);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
            return json;
        }else{
            return  null;
        }
    }

}
