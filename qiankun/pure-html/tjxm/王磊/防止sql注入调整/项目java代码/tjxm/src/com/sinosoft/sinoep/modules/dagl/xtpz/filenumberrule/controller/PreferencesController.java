package com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.controller;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.TableStructDescription;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.entity.DATreeVo;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.entity.DaglCategoryTable;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.entity.PartyNumRule;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.services.PreferencesService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * @Author 王富康
 * @Description //TODO 系统配置-Controller层
 * @Date 2018/11/8 19:36
 * @Param
 * @return
 **/
@Controller
@RequestMapping("dagl/xtpz/filenumberrule")
public class PreferencesController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PreferencesService preferencesService;

    /**
     * @Author 王富康
     * @Description //TODO 门类列表查询
     * @Date 2018/11/9 15:22
     * @Param
     * @return
     **/
    @LogAnnotation(value = "query",opName = "门类列表查询")
    @RequestMapping(value = "getCategoryListData", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getCategoryListData(String tName){

        JSONObject json = new JSONObject();
        try {
            List<DaglCategoryTable> list = preferencesService.getCategoryListData(tName);
            json.put("flag", "1");
            JSONObject data = new JSONObject();
            data.put("total",list.size());
            data.put("rows", list);
            json.put("data", data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e.getMessage(), e);
            json.put("flag", 0);
        }

        log.info(json.toString());
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据门类代号查询最底层表的字段(不包括已配置的字段)
     * @Date 2018/11/12 15:07
     * @Param []
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "根据门类代号查询最底层表的字段(不包括已配置的字段)")
    @RequestMapping(value = "getRuleColumn", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getRuleColumn(String category_code){

        JSONObject json = new JSONObject();
        try {
            List<TableStructDescription> list = preferencesService.getRuleColumn(category_code);
            json.put("flag", "1");
            JSONObject data = new JSONObject();
            data.put("total",list.size());
            data.put("rows", list);
            json.put("data", data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e.getMessage(), e);
            json.put("flag", 0);
        }

        log.info(json.toString());
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据门类id查询档号规则
     * @Date 2018/11/12 19:45
     * @Param [category_code]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "根据门类id查询档号规则")
    @RequestMapping(value = "getRule", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getRule(String category_id,String order_num){

        JSONObject json = new JSONObject();
        try {
            List<PartyNumRule> list = preferencesService.getRule(category_id,order_num);
            json.put("flag", "1");
            JSONObject data = new JSONObject();
            data.put("total",list.size());
            data.put("rows", list);
            json.put("data", data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e.getMessage(), e);
            json.put("flag", 0);
        }

        log.info(json.toString());
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 新增档号规则
     * @Date 2018/11/13 10:20
     * @Param [list]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "save",opName = "新增档号规则")
    @RequestMapping("save")
    @ResponseBody
    public JSONObject save(@RequestBody List<PartyNumRule> ruleList) {

        JSONObject json = new JSONObject();

        try {
            List<PartyNumRule> list = preferencesService.save(ruleList);
            json.put("flag", 1);
            json.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", 0);
        }

        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 删除档号规则某些字段
     * @Date 2018/11/13 10:57
     * @Param [reportId]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "delete",opName = "删除档号规则某些字段")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(@RequestBody List<PartyNumRule> ruleList) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
            try {
                preferencesService.delete(ruleList);
                json.put("flag", "1");
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 获取档案树
     * @Date 2018/11/17 11:46
     * @Param [ruleIds]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "获取档案树")
    @RequestMapping(value = "findTree", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject findTree() {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            List<DATreeVo> treeList =  preferencesService.findTree();
            json.put("flag", "1");
            json.put("data", treeList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据id修改修改档号规则的连接符和长度
     * @Date 2018/11/19 20:28
     * @Param [partyNumRule]
     * @return int
     **/
    @LogAnnotation(value = "update",opName = "根据id修改修改档号规则的连接符和长度")
    @RequestMapping("updatePartyNumRule")
    @ResponseBody
    public JSONObject updatePartyNumRule(PartyNumRule partyNumRule) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        if (StringUtils.isNotBlank(partyNumRule.getId())) {
            try {
                int result = preferencesService.updatePartyNumRule(partyNumRule);
                json.put("flag", "1");
                json.put("result", result);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
        }
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 上移档号规则
     * @Date 2018/11/20 9:43
     * @Param [partyNumRule]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "update",opName = "上移档号规则")
    @RequestMapping("moveUp")
    @ResponseBody
    public JSONObject moveUp(PartyNumRule partyNumRule) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        if (StringUtils.isNotBlank(partyNumRule.getId())) {
            try {
                int result = preferencesService.moveUp(partyNumRule);
                json.put("flag", "1");
                json.put("result", result);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
        }
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 下移档号规则
     * @Date 2018/11/20 9:43
     * @Param [partyNumRule]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "update",opName = "下移档号规则")
    @RequestMapping("moveDown")
    @ResponseBody
    public JSONObject moveDown(PartyNumRule partyNumRule) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        if (StringUtils.isNotBlank(partyNumRule.getId())) {
            try {
                int result = preferencesService.moveDown(partyNumRule);
                json.put("flag", "1");
                json.put("result", result);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(),e);
            }
        }
        return json;
    }

}
