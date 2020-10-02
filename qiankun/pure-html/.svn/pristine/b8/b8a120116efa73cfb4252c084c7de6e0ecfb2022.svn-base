package com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.entity.DaglCateDeptPersonRelation;
import com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.entity.RelationVo;
import com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.services.CateDeptPersonRelationService;
import net.sf.json.JSONObject;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 王富康
 * @Description //TODO 立卷单位和录入人及立卷单位管理员关系Controller层
 * @Date 2019/2/1 11:44
 * @Param
 * @return
 **/
@Controller
@RequestMapping("dagl/xtpz/deptpersonrelation")
public class CateDeptPersonRelationController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CateDeptPersonRelationService cateDeptPersonRelationService;

    /**
     * @Author 王富康
     * @Description //TODO 查询立卷单位和录入人及立卷单位管理员关系列表
     * @Date 2019/2/1 12:00
     * @Param [pageImpl, daglCateDeptPersonRelation]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    @LogAnnotation(value = "query",opName = "查询立卷单位和录入人及立卷单位管理员关系列表")
    @RequestMapping(value = "getRelationData", method = RequestMethod.POST ,produces = "application/json;charset=utf-8")
    @ResponseBody
    public PageImpl getRelationData(PageImpl pageImpl, DaglCateDeptPersonRelation daglCateDeptPersonRelation){
        try {
            Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
            pageImpl = cateDeptPersonRelationService.getRelationData(pageable,pageImpl,daglCateDeptPersonRelation);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return pageImpl;
    }

    /**
     * @Author 王富康
     * @Description //TODO 查询关系list
     * @Date 2019/2/13 14:44
     * @Param [daglCateDeptPersonRelation]
     * @return java.util.List<com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.entity.DaglCateDeptPersonRelation>
     **/
    @LogAnnotation(value = "query",opName = "查询关系list")
    @RequestMapping(value = "getRelationList", method = RequestMethod.POST)
    @ResponseBody
    public List<DaglCateDeptPersonRelation> getRelationList(DaglCateDeptPersonRelation daglCateDeptPersonRelation){

        List<DaglCateDeptPersonRelation> daglCateDeptPersonRelations = new ArrayList<>();
        try {
            daglCateDeptPersonRelations = cateDeptPersonRelationService.getRelationList(daglCateDeptPersonRelation);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return daglCateDeptPersonRelations;
    }

    /**
     * @Author 王富康
     * @Description //TODO 新增立卷单位和录入人及立卷单位管理员关系
     * @Date 2019/2/1 14:41
     * @Param [ruleList]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "save",opName = "新增立卷单位和录入人及立卷单位管理员关系")
    @RequestMapping("saveRelation")
    @ResponseBody
    public JSONObject saveRelation(RelationVo relationVo) {

        JSONObject json = new JSONObject();

        try {
            RelationVo relationVo1 = cateDeptPersonRelationService.saveRelation(relationVo);
            json.put("flag", 1);
            json.put("data", relationVo1);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", 0);
        }

        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据门类id和立卷单位删除所有对应关系
     * @Date 2019/2/1 15:23
     * @Param [cateId, ljdwMark]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "delete",opName = "根据门类id和立卷单位删除所有对应关系")
    @RequestMapping("deleteRelation")
    @ResponseBody
    public JSONObject deleteRelation(String cateId, String ljdwMark) {

        JSONObject json = new JSONObject();

        if(StringUtil.isBlank(cateId) || StringUtil.isBlank(ljdwMark)){
            json.put("flag", 0);
            return json;
        }

        try {
            cateDeptPersonRelationService.deleteRelation(cateId,ljdwMark);
            json.put("flag", 1);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", 0);
        }

        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 修改立卷单位关系对应数据
     * @Date 2019/2/14 10:17
     * @Param [cateId, ljdwMark]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "update",opName = "修改立卷单位关系对应数据")
    @RequestMapping("updateRelation")
    @ResponseBody
    public JSONObject updateRelation(RelationVo relationVo) {

        JSONObject json = new JSONObject();

        try {
            cateDeptPersonRelationService.updateRelation(relationVo);
            json.put("flag", 1);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", 0);
        }

        return json;
    }

    /**
     * 去重查询所有立卷单位或者录入人
     * @author 王磊
     * @Date 2019年2月12日 上午10:03:02
     * @param ljdwOrLrr
     * @return
     */
    @LogAnnotation(value = "query",opName = "去重查询所有立卷单位或者录入人")
    @RequestMapping("findAllLjdwOrLrr")
    @ResponseBody
    public JSONObject findAllLjdwOrLrr(String ljdwOrLrr) {
        JSONObject json = new JSONObject();
        try {
            List<DaglCateDeptPersonRelation> list = cateDeptPersonRelationService.getAllLjdwOrLrr(ljdwOrLrr);
            json.put("flag", 1);
            json.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", 0);
        }
        return json;
    }

}
