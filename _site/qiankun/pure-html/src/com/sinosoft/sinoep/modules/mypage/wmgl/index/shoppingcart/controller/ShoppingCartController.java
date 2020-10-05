package com.sinosoft.sinoep.modules.mypage.wmgl.index.shoppingcart.controller;

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
import com.sinosoft.sinoep.modules.mypage.wmgl.index.shoppingcart.entity.ShoppingCart;
import com.sinosoft.sinoep.modules.mypage.wmgl.index.shoppingcart.services.ShoppingCartService;
import net.sf.json.JSONObject;

/**
 * 购物车controller
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月13日 下午5:55:25
 */
@Controller
@RequestMapping("/mypage/wmgl/index/shoppingCart")
public class ShoppingCartController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ShoppingCartService service;
	
	/**
	 * 清空购物车
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月13日 下午5:58:32
	 * @param takeOutId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("clearCart")
	@LogAnnotation(opType = OpType.QUERY,opName = "清空购物车")
	public JSONObject clearCart(String takeOutId) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("flag", "1");
		try {
			service.clearCart(takeOutId);
		} catch (Exception e) {
			jsonObj.put("flag", "0");
		}
		return jsonObj;
	}
	
	/**
	 * 添加购物车
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月13日 下午6:11:42
	 * @param takeOutId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addCart")
	@LogAnnotation(opType = OpType.SAVE,opName = "添加购物车数据")
	public JSONObject addCart(ShoppingCart cart) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("flag", "1");
		try {
			service.addCart(cart);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			jsonObj.put("flag", "0");
		}
		return jsonObj;
	}
	
	/**
	 * 获取购物车数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月13日 下午6:39:07
	 * @param takeOutId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getCartData")
	@LogAnnotation(opType = OpType.QUERY,opName = "获取购物车数据")
	public JSONObject getCartData(String  takeOutId) {
		JSONObject jsonObj = new JSONObject();
		List<ShoppingCart> list = new ArrayList<ShoppingCart>();
		jsonObj.put("flag", "1");
		try {
			list = service.getCartData(takeOutId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			jsonObj.put("flag", "0");
		}
		jsonObj.put("goodsList", list);
		return jsonObj;
	}
}
