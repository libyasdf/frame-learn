package com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.goods.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.goods.entity.Goods;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.goods.entity.GoodsTreeEntity;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.goods.services.GoodsService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 商品类
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月7日 下午5:08:04
 */
@Controller
@RequestMapping("/mypage/wmgl/basicSet/goods")
public class GoodsController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	GoodsService service;
	
	/**
	 * 商品列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月8日 下午12:00:27
	 * @param pageImpl
	 * @param overTimeType
	 * @param timeRange
	 * @param subflag
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getlistBootHql")
	@LogAnnotation(opType = OpType.QUERY,opName = "查询草稿列表")
	public PageImpl getPageListDraft(PageImpl pageImpl, String GoodsName,String isUse,String categoryId) {
		try {
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
				pageImpl = service.getPageList1(pageable, pageImpl, GoodsName, isUse, categoryId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}
	/**
	 * 获取某门类下的所有的商品
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月7日 下午5:09:02
	 * @param pageImpl
	 * @param columnId
	 * @return
	 */
	  @LogAnnotation(opType = OpType.QUERY,opName = "根据门类id,获取所有的商品")
	    @RequestMapping(value = "getList")
	    @ResponseBody
	    public JSONObject getList(PageImpl pageImpl,String GoodsName,String isUse,String categoryId){
	    	JSONObject json = new JSONObject();
			json.put("flag", "1");
			List<Goods>list=new ArrayList<Goods>();
			try {
				  list = service.getList(GoodsName,isUse,categoryId);
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
	   * 保存商品
	   * TODO 
	   * @author 马秋霞
	   * @Date 2019年5月7日 下午8:58:30
	   * @param entity
	   * @return
	   */
	  @LogAnnotation(opType = OpType.SAVE,opName = "保存内容")
	    @RequestMapping("saveFrom")
	    @ResponseBody
	    public Goods saveFrom(Goods entity,String flg){
	        try{
	            entity = service.saveFrom(entity,flg);
	           
	        }catch (Exception e){
	            e.printStackTrace();
	            log.error("保存异常！");
	        }
	        return entity;
	    }
	  
	  /**
	   * 删除商品
	   * TODO 
	   * @author 马秋霞
	   * @Date 2019年5月8日 上午9:41:33
	   * @param ids
	   * @return
	   */
	  @LogAnnotation(opType = OpType.DELETE,opName = "删除商品")
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
	   * 商品管理打开只读、修改页面时，查询数据进行渲染 TODO
	   * TODO 
	   * @author 马秋霞
	   * @Date 2019年5月8日 下午1:40:13
	   * @param id
	   * @return
	   */
		@LogAnnotation(opType = OpType.UPDATE,opName = "修改页面")
		@ResponseBody
		@RequestMapping("edit")
		public JSONObject edit(String id) {
			JSONObject json = new JSONObject();
			Goods goods = null;
			try {
				goods = service.getById(id);
				json.put("flag", "1");
				json.put("data", goods);
			} catch (Exception e) {
				e.printStackTrace();
				json.put("flag", "0");
			}
			return json;
		}
		
		/**
		 * 获取商品树
		 * TODO 
		 * @author 马秋霞
		 * @Date 2019年5月10日 上午11:46:01
		 * @return
		 */
		@LogAnnotation(opType  = OpType.QUERY,opName = "获取所有的商品树")
		@RequestMapping(value = "getGoodsTree")
		@ResponseBody
		public JSONObject getGoodsTree() {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("flag", "1");
			List<GoodsTreeEntity> treeList = new ArrayList<GoodsTreeEntity>();
			try {
				treeList = service.treeList();
			} catch (Exception e) {
				jsonObj.put("flag", "0");
				e.printStackTrace();
				log.error(e.getMessage(),e);
			}
			jsonObj.put("data", treeList);
			return jsonObj;
		}

}
