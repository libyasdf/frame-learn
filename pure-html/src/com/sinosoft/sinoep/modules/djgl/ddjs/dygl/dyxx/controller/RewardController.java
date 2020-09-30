package com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglRewardEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.services.RewardService;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
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

import java.util.List;
import java.util.Map;

/**
 * TODO 党员加你情况控制层
 * @Author: 冯建海
 * @Date: 2018/8/28 18:35
 */
@Controller
@RequestMapping("djgl/ddjs/dygl/dyjl/")
public class RewardController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RewardService rewardService;

    @Autowired
    private DwxtOrgService dwxtOrgService;

    /**
     * 党员信息保存/更新
     * TODO
     * @author 冯建海
     * @Date 2018年8月23日
     * @param entity
     * @return
     */

    @LogAnnotation(value = "save",opName = "保存党员信息")
    @RequestMapping("save")
    @ResponseBody
    public JSONObject save(DdjsDyglRewardEntity entity) {
        JSONObject json = new JSONObject();
        try {
            entity = rewardService.saveForm(entity);
            json.put("flag", 1);
            json.put("data", entity);
        } catch (Exception e) {
         //   log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

    /**
     * 党员信息列表查询
     * TODO
     * @author 冯建海
     * @Date 2018年7月25日 下午5:33:18
     * @param pageImpl
     * @return
     */
    @LogAnnotation(value = "query",opName = "查询党员信息列表")
    @ResponseBody
    @RequestMapping("getList")
    public PageImpl getZbdydhList(PageImpl pageImpl, DdjsDyglRewardEntity entity, String id, String type) {
        if(type.equals("org")){
            DwxtOrg dwxtOrg = new DwxtOrg();
            dwxtOrg.setOrgId(id);
            List<Map<String,Object>> list = dwxtOrgService.getTree(dwxtOrg);
            if(list.size() > 0) {
                String ids = "";
                for (Map<String, Object> map : list) {
                    if (map.get("type").toString().equals("org")) {
                        ids += map.get("id").toString() + ",";
                    }
                }
                id = ids.substring(0, ids.length() - 1);
            }
        }
        Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
        String startTime ="";
        String endTime ="";
        if (StringUtils.isNotBlank(entity.getRewardTime())) {
            startTime =entity.getRewardTime().substring(0,(entity.getRewardTime().length()+1)/2-1).trim();
            endTime =entity.getRewardTime().substring((entity.getRewardTime().length()+1)/2,entity.getRewardTime().length()).trim();
        }
        PageImpl pageList = rewardService.getPageList(pageable, pageImpl, entity,startTime,endTime,id,type);
        return pageList;
    }


    /**
     * 根据id逻辑删除党员信息 对应列表
     * @author 冯建海
     * @Date 2018年8月24日
     * @return
     */
    @LogAnnotation(value = "delete",opName = "删除奖励情况")
    @ResponseBody
    @RequestMapping("deleteById")
    public JSONObject deleteById(String id) {
        JSONObject json = new JSONObject();
        try {
            int n = rewardService.delete(id);
            json.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("flag", "0");
        }
        return json;
    }


    /**
     * TODO 对编辑数据进行渲染
     * @author 冯建海
     * @Date 2018年8月27日
     * @param
     * @param id
     * @return
     */
    @LogAnnotation(value = "edit",opName = "修改页面")
    @ResponseBody
    @RequestMapping("edit")
    public JSONObject edit( String id,String superId,String resId){
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        DdjsDyglRewardEntity entity = null;
        try {
            if(StringUtils.isNotBlank(id)){
                entity = rewardService.getById(id);
            }else{
                entity = rewardService.getBySuperId(superId);
            }
            json.put("flag", "1");
            json.put("data", entity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }


}
