package com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.controller;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.entity.DaglShoppingTrolley;
import com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.services.ShoppingTrolleyService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;

/**
 * @Author 王富康
 * @Description //TODO 收集列表Controller层
 * @Date 2019/2/18 14:58
 * @Param
 * @return
 **/
@Controller
@RequestMapping("dagl/daly/shoppingtrolley")
public class ShoppingTrolleyController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShoppingTrolleyService shoppingTrolleyService;


    /**
     * @Author 王富康
     * @Description //TODO 查询收集列表内容
     * @Date 2019/2/18 15:14
     * @Param [pageImpl, daglShoppingTrolley]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    @LogAnnotation(value = "query",opName = "查询收集列表内容")
    @RequestMapping(value = "getShoppingTrolleyData")
    @ResponseBody
    public PageImpl getShoppingTrolleyData(PageImpl pageImpl, DaglShoppingTrolley daglShoppingTrolley){
        try {
            Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
            pageImpl = shoppingTrolleyService.getShoppingTrolleyData(pageable,pageImpl,daglShoppingTrolley);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return pageImpl;
    }

    /**
     * @Author 王富康
     * @Description //TODO 查询收集列表数据（不分页）（管理员）
     * @Date 2019/2/19 9:52
     * @Param [daglCateDeptPersonRelation]
     * @return java.util.List<DaglCateDeptPersonRelation>
     **/
    @LogAnnotation(value = "query",opName = "查询收集列表数据（不分页）（管理员）")
    @RequestMapping(value = "getShoppingTrolleyListOfAdmin", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getShoppingTrolleyListOfAdmin(DaglShoppingTrolley daglShoppingTrolley){

        JSONObject json = new JSONObject();
        JSONObject data = new JSONObject();
        json.put("flag", "0");
        try {
            List<DaglShoppingTrolley> list = shoppingTrolleyService.getShoppingTrolleyListOfAdmin(daglShoppingTrolley);
            data.put("total", list.size());
            data.put("rows", list);
            json.put("flag", "1");
            json.put("data", data);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return json;

    }

    /**
     * @Author 王富康
     * @Description //TODO 新增收集列表数据
     * @Date 2019/2/18 15:24
     * @Param [daglShoppingTrolley]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "save",opName = "新增收集列表数据")
    @RequestMapping("saveShoppingTrolley")
    @ResponseBody
    public JSONObject saveShoppingTrolley(String ids, String tName, String all, String ranking, String categoryCode, String categoryName) {

        JSONObject json = new JSONObject();

        try {
            List<Map<String,Object>> daglShoppingTrolley = shoppingTrolleyService.saveShoppingTrolley(ids, tName, all, ranking, categoryCode, categoryName);
            json.put("flag", 1);
            json.put("data", daglShoppingTrolley);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", 0);
        }

        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 删除收集列表数据(管理员)
     * @Date 2019/2/18 15:25
     * @Param [cateId, ljdwMark]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "delete",opName = "删除收集列表数据（管理员）")
    @RequestMapping("deleteShoppingTrolley")
    @ResponseBody
    public JSONObject deleteShoppingTrolley(String ShoppingTrolleyIds) {

        JSONObject json = new JSONObject();

        try {
            shoppingTrolleyService.deleteShoppingTrolley(ShoppingTrolleyIds);
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
     * @Description //TODO 推送收集列表
     * @Date 2019/2/25 17:17
     * @Param [ShoppingTrolleyIds]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "update",opName = "推送收集列表")
    @RequestMapping("sendShoppingTrolley")
    @ResponseBody
    public JSONObject sendShoppingTrolley(String borrowUserId, String borrowUserName) {

        JSONObject json = new JSONObject();

        try {
            List<Map<String,Object>> sendShoppingTrolley = shoppingTrolleyService.sendShoppingTrolley(borrowUserId,borrowUserName);
            json.put("flag", 1);
            json.put("data", sendShoppingTrolley);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", 0);
        }

        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 查询收集列表数据（不分页）（借阅人）
     * @Date 2019/2/26 9:27
     * @Param [recid, categoryno]
     * @return java.util.List<com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.entity.DaglShoppingTrolley>
     **/
    @LogAnnotation(value = "query",opName = "查询收集列表数据（不分页）（借阅人）")
    @RequestMapping(value = "getShoppingTrolleyListOfUser", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getShoppingTrolleyListOfUser(DaglShoppingTrolley daglShoppingTrolley){

        JSONObject json = new JSONObject();
        JSONObject data = new JSONObject();
        json.put("flag", "0");
        try {
            List<DaglShoppingTrolley> list = shoppingTrolleyService.getShoppingTrolleyListOfUser(daglShoppingTrolley);
            data.put("total", list.size());
            data.put("rows", list);
            json.put("flag", "1");
            json.put("data", data);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return json;

    }

    /**
     * @Author 王富康
     * @Description //TODO 删除收集列表数据（借阅人）
     * @Date 2019/2/26 17:16
     * @Param [ShoppingTrolleyIds]
     * @return net.sf.json.JSONObject
     **/
    @LogAnnotation(value = "delete",opName = "删除收集列表数据（借阅人）")
    @RequestMapping("deleteShoppingTrolleyOfUser")
    @ResponseBody
    public JSONObject deleteShoppingTrolleyOfUser(String ShoppingTrolleyIds) {

        JSONObject json = new JSONObject();

        try {
            int result = shoppingTrolleyService.deleteShoppingTrolleyOfUser(ShoppingTrolleyIds);
            if (result != 0) {
                json.put("flag", "1");
            } else {
                json.put("flag", "0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("flag", "0");
        }

        return json;
    }

}
