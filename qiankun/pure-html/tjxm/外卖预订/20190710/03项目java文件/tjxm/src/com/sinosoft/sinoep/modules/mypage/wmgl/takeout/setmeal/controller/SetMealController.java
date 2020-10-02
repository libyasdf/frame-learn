package com.sinosoft.sinoep.modules.mypage.wmgl.takeout.setmeal.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fr.third.org.apache.poi.hssf.record.formula.functions.Int;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.entity.Category;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.config.entity.WmglConfig;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.setmeal.entity.SetMeal;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.setmeal.services.SetMealService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * TODO 套餐controller
 * @author 李颖洁  
 * @date 2019年5月27日  下午2:00:09
 */
@Controller
@RequestMapping("/mypage/wmgl/takeout/setmeal")
public class SetMealController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SetMealService service;
	
	/**
	 * TODO 根据外卖和门类id获取所有的套餐组合
	 * @author 李颖洁  
	 * @date 2019年5月27日下午3:22:02
	 * @param infoId 外卖id
	 * @param classId 门类id
	 * @return JSONObject
	 */
	@LogAnnotation(opType = OpType.QUERY,opName = "根据外卖和门类id获取所有的套餐组合")
	@RequestMapping("getSetMealByInfoId")
	@ResponseBody
	public JSONObject getSetMealByInfoId(String infoId,String classId) {
		 JSONObject json = new JSONObject();
			json.put("flag", "1");
			List<SetMeal>list=new ArrayList<SetMeal>();
			try {
				if(StringUtils.isNotBlank(infoId)) {
					 list = service.getDetilByInfoId(infoId,classId);
				}
				JSONObject data = new JSONObject();
				data.put("total",list.size());
				JSONArray array = new JSONArray();
				array = JSONArray.fromObject(list);
				data.put("rows", array);
				json.put("data",data);
			} catch (Exception e) {
				e.printStackTrace();
				json.put("flag", "0");
			}
			return json;
	}
	 
	 /**
	  * TODO 获取外卖套餐中所有的门类
	  * @author 李颖洁  
	  * @date 2019年5月27日下午3:22:29
	  * @param infoId 外卖id
	  * @return JSONObject
	  */
	 @LogAnnotation(opType = OpType.QUERY,opName = "获取外卖套餐中所有的门类")
	 @RequestMapping("getCategoryByInfoId")
	 @ResponseBody
	 public JSONObject getCategoryByInfoId(String infoId) {
		 JSONObject jsonObj = new JSONObject();
		 jsonObj.put("flg", "1");
		 List<Category> list = new ArrayList<Category>();
		 try {
			list = service.getCategoryByInfoId(infoId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			jsonObj.put("flg", "1");
		}
		jsonObj.put("data", list);
		return jsonObj;
	 }
	 
	/**
	 * TODO 根据id删除指定的套餐组合
	 * @author 李颖洁  
	 * @date 2019年5月27日下午3:28:45
	 * @param id
	 * @return JSONObject
	 */
	@LogAnnotation(opType = OpType.DELETE,opName = "根据id删除指定的套餐组合")
	@RequestMapping("delete")
	@ResponseBody
	public JSONObject delete(String id) {
	  JSONObject json = new JSONObject();
		int result = service.delete(id);
		if (result != 0) {
			json.put("flag", "1");
		} else {
			json.put("flag", "0");
		}
		return json;
	}
	
	/**
	 * TODO 保存套餐组合规则
	 * @author 李颖洁  
	 * @date 2019年5月27日下午3:32:51
	 * @param meal
	 * @return JSONObject
	 */
    @LogAnnotation(opType = OpType.SAVE,opName = "保存套餐组合规则")
    @RequestMapping("save")
    @ResponseBody
    public JSONObject saveForm(SetMeal meal) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            int num = service.saveForm(meal);
            if(num >0){
            	json.put("flag", "1");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return json;
    }
		  /**
		   * 套餐组合
		   * TODO 
		   * @author 马秋霞
		   * @Date 2019年5月27日 下午2:19:04
		   * @param categoryId
		   * @param goodsIds
		   * @return
		   */
		  @LogAnnotation(opType = OpType.SAVE,opName = "套餐组合")
		  @RequestMapping("CombineGoods")
		  @ResponseBody
		  public JSONObject CombineGoods(String takeOutId,String categoryId,String goodsIds,String goodsNames) {
			  JSONObject json = new JSONObject();
			  json.put("flag", "1");
			  try {
				service.CombineGoods(takeOutId,categoryId, goodsIds,goodsNames);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(),e);
				json.put("flg", "0");
			}
			return json;
		  }
		  
		 /**
		  * TODO 根据外卖id和门类id查询套餐列表
		  * @author 李颖洁  
		  * @date 2019年5月27日下午5:12:03
		  * @param pageImpl
		  * @param infoId
		  * @param classId
		  * @return PageImpl
		  */
	    @LogAnnotation(opType = OpType.QUERY,opName = "查询通知信息列表")
	    @ResponseBody
	    @RequestMapping("list")
	    public PageImpl list(PageImpl pageImpl,String infoId,String classId){
	    	pageImpl.setFlag("0");
	        try {
	            Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
	            pageImpl = service.getListByClassId(pageable,pageImpl,infoId,classId);
	        } catch (Exception e) {
	            e.printStackTrace();
	            log.error(e.getMessage(),e);
	            pageImpl.setFlag("0");
	        }
	        return pageImpl;
	    }	  
}
