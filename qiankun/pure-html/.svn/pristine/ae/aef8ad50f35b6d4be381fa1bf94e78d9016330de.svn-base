package com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutinfo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutinfo.entity.TakeOutInfo;
import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutinfo.services.TakeOutInfoService;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 外卖controller类
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月7日 下午5:08:04
 */
@Controller
@RequestMapping("/mypage/wmgl/takeout/takeoutInfo")
public class TakeOutInfoController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	TakeOutInfoService service;
	
	/**
	 * 获取外卖标题及注意事项
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月9日 下午2:10:59
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getTitle")
	@LogAnnotation(opType = OpType.QUERY,opName = "获取外卖标题")
	public JSONObject getTitle() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("flg", "1");
		jsonObj.put("title", "");
		try {
			String title = service.getTitle();
			jsonObj.put("title", title);
		} catch (Exception e) {
			jsonObj.put("flg", "0");
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return jsonObj;
	}
	
	/**
	 * 外卖分页列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月9日 下午4:44:51
	 * @param pageImpl
	 * @param GoodsName
	 * @param isUse
	 * @param categoryId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getlistBootHql")
	@LogAnnotation(opType = OpType.QUERY,opName = "查询草稿列表")
	public PageImpl getPageListDraft(PageImpl pageImpl, String title,String takeFoodTime,String status,String deadlineTime) {
		String startDate = "";
		String endDate = "";
		String startDate1 = "";
		String endDate1 = "";
		try {
			if (StringUtils.isNotBlank(takeFoodTime)) {
				startDate = takeFoodTime.substring(0, (takeFoodTime.length() + 1) / 2 - 1).trim();
				endDate = takeFoodTime.substring((takeFoodTime.length() + 1) / 2, takeFoodTime.length()).trim();
			}
			if (StringUtils.isNotBlank(deadlineTime)) {
				startDate1 = deadlineTime.substring(0, (deadlineTime.length() + 1) / 2 - 1).trim();
				endDate1 = deadlineTime.substring((deadlineTime.length() + 1) / 2, deadlineTime.length()).trim();
			}
			Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
				pageImpl = service.getPageList(pageable, pageImpl, title, startDate,endDate,startDate1,endDate1, status);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return pageImpl;
	}	
	/**
	 * 保存外卖信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年5月9日 下午3:58:59
	 * @param entity
	 * @param flg
	 * @return
	 */
	   @LogAnnotation(opType = OpType.SAVE,opName = "保存内容")
	   @RequestMapping("saveFrom")
	   @ResponseBody
	   public TakeOutInfo saveFrom(TakeOutInfo entity){
	        try{
	            entity = service.saveFrom(entity);
	           
	        }catch (Exception e){
	        	e.printStackTrace();
				log.error(e.getMessage(),e);
	        }
	        return entity;
	    }
	   
	   /**
	    * 删除外卖信息
	    * TODO 
	    * @author 马秋霞
	    * @Date 2019年5月9日 下午4:06:25
	    * @param id
	    * @return
	    */
		  @LogAnnotation(value = "delete",opName = "删除外卖")
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
		   * 外卖管理打开只读、修改页面时，查询数据进行渲染 TODO
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
				TakeOutInfo info = null;
				try {
					info = service.getById(id);
					json.put("flag", "1");
					json.put("data", info);
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage(),e);
					json.put("flag", "0");
				}
				return json;
			}
			
			/**
			 * 获取可订餐的外卖列表
			 * TODO 
			 * @author 马秋霞
			 * @Date 2019年5月13日 下午7:04:47
			 * @return
			 */
			@LogAnnotation(opType = OpType.QUERY,opName = "获取可订餐的外卖列表")
			@ResponseBody
			@RequestMapping("getList")
			public JSONObject getList() {
				JSONObject json = new JSONObject();
				List<TakeOutInfo> list = new ArrayList<TakeOutInfo>();
				json.put("flag", "1");
				try {
					list = service.getList();
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage(),e);
					json.put("flag", "0");
				}
				json.put("data", list);
				return json;
			}
			
			/**
			 * 外卖的复用
			 * TODO 
			 * @author 马秋霞
			 * @Date 2019年5月22日 上午11:29:47
			 * @return
			 */
			@LogAnnotation(opType = OpType.SAVE,opName = "外卖的复用")
			@ResponseBody
			@RequestMapping("reuse")
			public JSONObject reuse(String id) {
				JSONObject json = new JSONObject();
				json.put("flag", "1");
				try {
					service.reuse(id);
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage(),e);
					json.put("flag", "0");
				}
				return json;
			}
			
}
