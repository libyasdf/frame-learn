package com.sinosoft.sinoep.modules.mypage.wmgl.index.index.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.entity.Category;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.index.services.IndexService;

import net.sf.json.JSONObject;

/**
 * 获取首页商品的controller
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月14日 上午9:44:11
 */
@Controller
@RequestMapping("/mypage/wmgl/index/index/indexController")
public class IndexController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	IndexService service;
	
	/**
	 * 获取首页外卖的所有商品
	 * TODO 
	 * @author 马秋霞
	 * @param <Category>
	 * @Date 2019年5月14日 上午9:46:05
	 * @return
	 */
	 @LogAnnotation(opType = OpType.QUERY,opName = " 获取首页外卖的所有商品")
	 @RequestMapping("getWmGoods")
	 @ResponseBody
	public JSONObject  getWmGoods(String takeOutId) {
		JSONObject jonObj = new JSONObject();
		jonObj.put("flag", "1");
		List<Category> list = new ArrayList<Category>();
		try {
			list = service.getWmGoods(takeOutId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			jonObj.put("flag", "0");
		}
		jonObj.put("categoryList", list);
		return jonObj;
	}
}
