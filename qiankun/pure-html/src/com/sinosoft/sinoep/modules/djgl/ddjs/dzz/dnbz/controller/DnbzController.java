package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dnbz.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dnbz.entity.DnbzEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dnbz.service.DnbzServiceI;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TODO 党组织管理 党内表彰Controller
 * @Author: 李帅
 * @Date: 2018/9/9 11:06
 */
@Controller
@RequestMapping("djgl/ddjs/dzz/dnbz/")
public class DnbzController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DnbzServiceI dnbzService;

    /**
     * 党内表彰 添加和修改的方法
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param dnbzEntity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存党内表彰")
    @RequestMapping("saveDnbz")
    @ResponseBody
    public JSONObject saveDnbz(DnbzEntity dnbzEntity) {
        JSONObject json = new JSONObject();
        try {
            dnbzEntity = dnbzService.saveDnbz(dnbzEntity);
            json.put("flag", 1);
            json.put("data", dnbzEntity);
        } catch (Exception e) {
            //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     * 党内表彰 列表查询的方法
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param pageImpl
     * @param dnbzEntity
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询党内表彰列表")
    @ResponseBody
    @RequestMapping("getDnbzList")
    public PageImpl getDnbzList(PageImpl pageImpl, DnbzEntity dnbzEntity) {
        Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
        String startTime ="";
        String endTime ="";
        if (StringUtils.isNotBlank(dnbzEntity.getGrantTime())) {
            startTime =dnbzEntity.getGrantTime().substring(0,(dnbzEntity.getGrantTime().length()+1)/2-1).trim();
            endTime =dnbzEntity.getGrantTime().substring((dnbzEntity.getGrantTime().length()+1)/2,dnbzEntity.getGrantTime().length()).trim();
        }
        PageImpl pageList = dnbzService.getPageList(pageable, pageImpl, dnbzEntity,startTime,endTime);

        return pageList;
    }


    /**
     * 根据id逻辑删除党内表彰会 对应列表
     * @author 李帅
     * @Date 2018年8月28日
     * @param dnbz
     * @return
     */
    @LogAnnotation(value = "delete",opName = "根据id删除党内表彰对应列表")
    @ResponseBody
    @RequestMapping("deleteById")
    public JSONObject deleteById(DnbzEntity dnbz) {
        JSONObject json = new JSONObject();
        try {
            int n = dnbzService.delete(dnbz);
            json.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("flag", "0");
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
        DnbzEntity dnbz = null;
        try {
            dnbz = dnbzService.getById(id);
            json.put("flag", "1");
            json.put("data", dnbz);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }

}
