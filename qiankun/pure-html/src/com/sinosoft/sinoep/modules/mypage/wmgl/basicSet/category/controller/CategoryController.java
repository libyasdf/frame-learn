package com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.handlerInterceptor.OpType;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.entity.Category;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.services.CategoryService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 门类controller
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月10日 上午10:39:09
 */
@Controller
@RequestMapping("/mypage/wmgl/basicSet/category")
public class CategoryController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	   @Autowired
	   CategoryService service;
	   
	   /**
	    * 根据id查询门类信息
	    * TODO 
	    * @author 马秋霞
	    * @Date 2019年5月7日 上午10:50:28
	    * @param id
	    * @return
	    */
	   
	    @LogAnnotation(opType = OpType.UPDATE,opName = "修改页面")
	    @RequestMapping("edit")
	    @ResponseBody
	    public JSONObject edit(String id){
	        JSONObject json = new JSONObject();
	        json.put("flag","0");
	        Category entity = new Category();
	        try{
	            entity = service.getCategoryCategById(id);
	            json.put("flag","1");
	            json.put("data",entity);
	            json.put("msg","获取成功！");
	        }catch (Exception e){
	            e.printStackTrace();
	            json.put("flag","-1");
	            json.put("msg","获取失败!");
	            log.error("获取失败！");
	        }
	        return json;
	  }
	    
	    /**
	     * 保存门类信息
	     * TODO 
	     * @author 马秋霞
	     * @Date 2019年5月7日 上午10:51:50
	     * @param entity
	     * @param isFirst
	     * @return
	     */
		@LogAnnotation(opType = OpType.SAVE,opName = "保存表单")
	    @RequestMapping("save")
	    @ResponseBody
	    public JSONObject saveFlow(Category entity){
	        JSONObject json = new JSONObject();
	        json.put("flag",0);
	        try{
	    		entity = service.saveFroms(entity);
	    		json.put("flag","1");
	    		json.put("data",entity);
	    		json.put("msg","保存成功！");
	        }catch (Exception e){
	            e.printStackTrace();
	            json.put("flag","-1");
	            json.put("msg","保存异常！");
	            log.error("保存异常！");
	        }
	        return json;
	    }
	    
		/**
		 * 获取门类树
		 * TODO 
		 * @author 马秋霞
		 * @Date 2019年5月7日 下午4:06:49
		 * @return
		 */
	    @LogAnnotation(opType = OpType.QUERY,opName = "获取门类树")
	    @RequestMapping("/getCategoryTree")
	    @ResponseBody
	    public JSONObject getCategoryTree(){
	        return service.getCategoryTree();
	    }
	    
	    /**
	     * 根据门类id删除门类
	     * TODO 
	     * @author 马秋霞
	     * @Date 2019年5月7日 下午4:06:22
	     * @param ids
	     * @return
	     */
	    @LogAnnotation(opType = OpType.DELETE,opName = "删除门类")
	    @RequestMapping("delete")
	    @ResponseBody
	    public JSONObject delete(String ids){
	        JSONObject json = new JSONObject();
	        json.put("flag","0");
	        int result = service.delete(ids);
	        if(result !=0){
	            json.put("flag","1");
	        }
	        return json;
	    }
}
