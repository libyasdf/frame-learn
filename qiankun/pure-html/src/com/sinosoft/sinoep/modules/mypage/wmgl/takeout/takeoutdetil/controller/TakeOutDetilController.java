package com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutdetil.controller;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.entity.Category;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutdetil.entity.TakeOutDetail;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutdetil.services.TakeOutDetilService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 外卖controller
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月7日 下午5:08:04
 */
@Controller
@RequestMapping("/mypage/wmgl/takeout/takeoutDetil")
public class TakeOutDetilController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	TakeOutDetilService service;
	
	/**
	 * 根据外卖和门类id获取所有的商品
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月10日 上午9:12:48
	 * @param infoId
	 * @param classId
	 * @return
	 */
	 @LogAnnotation(opType = OpType.SAVE,opName = "保存内容")
	 @RequestMapping("getDetilByInfoId")
	 @ResponseBody
	public JSONObject getDetilByInfoId(String infoId,String classId) {
		 JSONObject json = new JSONObject();
			json.put("flag", "1");
			List<TakeOutDetail>list=new ArrayList<TakeOutDetail>();
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
	  * 获取外卖所有的门类
	  * TODO 
	  * @author 马秋霞
	  * @Date 2019年5月10日 上午11:00:54
	  * @param infoId
	  * @return
	  */
	 @LogAnnotation(opType = OpType.SAVE,opName = "获取外卖所有的门类")
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
			jsonObj.put("flg", "0");
		}
		jsonObj.put("data", list);
		return jsonObj;
	 }
	 
	 /**
	  * 设置外卖的商品数据
	  * TODO 
	  * @author 马秋霞
	  * @Date 2019年5月10日 下午3:00:46
	  * @param infoId
	  * @return
	  */
	 @LogAnnotation(opType = OpType.SAVE,opName = "设置外卖的商品信息")
	 @RequestMapping("setDetilInfo")
	 @ResponseBody
	 public JSONObject setDetilInfo(String takeOutId,String detil) {
		 JSONObject jsonObj = new JSONObject();
		 jsonObj.put("flg", "1");
		 try {
			service.setDetilInfo(takeOutId, detil);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			jsonObj.put("flg", "0");
		}
		return jsonObj;
	 }
	 
	  /**
	    * 删除外卖的商品信息
	    * TODO 
	    * @author 马秋霞
	    * @Date 2019年5月9日 下午4:06:25
	    * @param id
	    * @return
	    */
		  @LogAnnotation(value = "delete",opName = "删除外卖商品信息")
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
		   * 根据外卖id获取外卖商品列表
		   * TODO 
		   * @author 马秋霞
		   * @Date 2019年5月13日 上午10:03:37
		   * @param infoId
		   * @return
		   */
		  @LogAnnotation(value = "getDetil",opName = "根据外卖id获取外卖详情")
		    @RequestMapping("getDetil")
		    @ResponseBody
		  public JSONObject getDetil(String infoId) {
			  JSONObject json = new JSONObject();
			  json.put("flg", "1");
			  List<TakeOutDetail> list = new ArrayList<TakeOutDetail>();
			  try {
				list = service.getDetil(infoId);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				 json.put("flg", "0");
			}
			  json.put("data", list);
			  return json;
			  
		  }
		  
			/**
			 * 排序
			 * TODO 
			 * @author 马秋霞
			 * @Date 2019年5月14日 下午4:51:30
			 * @param id
			 * @return
			 */
			@RequestMapping(value = "sort", method = RequestMethod.POST)
			@ResponseBody
			public JSONObject sort(String[] id){
				JSONObject json = new JSONObject();
				json.put("flag","0");
				try {
					service.sort(id);
					json.put("flag","1");
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage(),e);
				}
				return json;
			}
			
			/**
			 * 修改外卖的详情
			 * TODO 
			 * @author 马秋霞
			 * @Date 2019年5月23日 上午10:51:02
			 * @param detil
			 * @return
			 */
			@RequestMapping(value = "updateDetil", method = RequestMethod.POST)
			@ResponseBody
			public JSONObject updateDetil(TakeOutDetail detil,String flg) {
				JSONObject json = new JSONObject();
				json.put("flag","0");
				try {
					service.updateDetil(detil,flg);
					json.put("flag","1");
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage(),e);
				}
				return json;
			}
			
			/**
			 * 是否可以修改库存数量
			 * TODO 
			 * @author 马秋霞
			 * @Date 2019年6月12日 上午10:58:05
			 * @param goodsNum
			 * @return
			 */
			@RequestMapping(value = "judgeUpdateGoodsNum", method = RequestMethod.POST)
			@ResponseBody
			public JSONObject judgeUpdateGoodsNum(String takeOutId,String goodsId,Integer goodsNum) {
				JSONObject json = new JSONObject();
				JSONObject data = new JSONObject();
				json.put("flag","0");
				try {
					 data = service.judgeUpdateGoodsNum(takeOutId,goodsId,goodsNum);
					json.put("flag","1");
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage(),e);
				}
				json.put("data",data);
				return json;
			}
}
