package com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.controller;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.ContrastingRelations;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.DataContrast;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.TableStructDescription;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.services.ContrastService;
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
 * @Description //TODO 王富康数据对照Controller
 * @Date 2018/11/13 20:13
 * @Param
 * @return
 **/
@Controller
@RequestMapping("dagl/xtpz/datacontrast")
public class ContrastController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContrastService contrastService;

    /**
     * @Author 王富康
     * @Description //TODO 查询数据对照表信息
     * @Date 2018/11/13 20:58
     * @Param []
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "查询数据对照表信息")
    @RequestMapping(value = "getContrastingRelations", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getContrastingRelations(ContrastingRelations contrastingRelations){
        JSONObject json = new JSONObject();
        try {
            List<ContrastingRelations> list = contrastService.getContrastingRelations(contrastingRelations);
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
     * @Description //TODO 根据id获取一条关系信息
     * @Date 2018/11/20 17:05
     * @Param [id]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "根据id获取一条关系信息")
    @RequestMapping(value = "findOneById", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject findOneById(String id) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            ContrastingRelations st = contrastService.findById(id);
            json.put("flag","1");
            json.put("data",st);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 删除关系信息
     * @Date 2018/11/20 20:49
     * @Param [ruleList]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "delete",opName = "删除关系信息")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(@RequestBody List<ContrastingRelations> RelationsList) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            contrastService.delete(RelationsList);
            json.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 查询指定id的对照详细信息
     * @Date 2018/11/13 21:00
     * @Param []
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "查询数据对照表信息")
    @RequestMapping(value = "getDataContrast", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getDataContrast(DataContrast dataContrast){
        JSONObject json = new JSONObject();
        try {
            List<DataContrast> list = contrastService.getDataContrast(dataContrast);
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
     * @Description //TODO 根据表名获取表的字段相关信息
     * @Date 2018/11/13 21:27
     * @Param [tableName]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "根据表名获取表的字段相关信息")
    @RequestMapping(value = "getColumns", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getColumns(String tableName, String column_name, String contrastingId){
        JSONObject json = new JSONObject();
        try {
            List<TableStructDescription> list = contrastService.getColumns(tableName,column_name,contrastingId);
            json.put("flag", "1");
            json.put("data", list);
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
     * @Description //TODO 新增关系对照关系
     * @Date 2018/11/14 11:57
     * @Param [contratingRelations]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "save",opName = "新增关系对照关系")
    @RequestMapping(value = "saveContratingRelations", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveContratingRelations(ContrastingRelations contratingRelations){
        JSONObject json = new JSONObject();

        try {
            List<ContrastingRelations> list = contrastService.saveContratingRelations(contratingRelations);
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
     * @Description //TODO 根据id修改对应关系
     * @Date 2018/11/14 16:09
     * @Param [dataContrast]
     * @return int
     **/
    @LogAnnotation(value = "update",opName = "根据id修改对应关系")
    @RequestMapping("updateDataContrast")
    @ResponseBody
    public JSONObject updateDataContrast(DataContrast dataContrast) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        if (StringUtils.isNotBlank(dataContrast.getId())) {
            try {
                int result = contrastService.updateDataContrast(dataContrast);
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
     * @Description //TODO 根据关联id获取目标表未做关联的，并且该字段不能为空的字段。
     * @Date 2018/11/19 11:52
     * @Param [tableName, column_name]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "根据关联id获取目标表未做关联的，并且该字段不能为空的字段。")
    @RequestMapping(value = "getNotNullData", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getNotNullData(String id){
        JSONObject json = new JSONObject();
        try {
            List<TableStructDescription> list = contrastService.getNotNullData(id);
            json.put("flag", "1");
            json.put("data", list);
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
     * @Description //TODO 根据表关系id，目标字段名称获取一条字段对应关系
     * @Date 2018/11/28 15:31
     * @Param [id]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "query",opName = "根据表关系id，目标字段名称获取一条字段对应关系")
    @RequestMapping(value = "getcontrastData", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getcontrastData(String contrastingId,String targetColumn){
        JSONObject json = new JSONObject();
        try {
            List<DataContrast> list = contrastService.getcontrastData(contrastingId,targetColumn);
            json.put("flag", "1");
            json.put("data", list);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e.getMessage(), e);
            json.put("flag", 0);
        }

        log.info(json.toString());
        return json;
    }

}
