package com.sinosoft.sinoep.modules.dagl.xtpz.categorymanage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.dagl.xtpz.categorymanage.entity.MenTree;
import com.sinosoft.sinoep.modules.dagl.xtpz.categorymanage.services.CategoryManageService;
import net.sf.json.JSONObject;

/**
 * 门类管理控制层
 * @author 王磊
 * @Date 2018年12月28日 上午10:33:51
 */
@Controller
@RequestMapping("dagl/xtpz/category")
public class CategoryManageController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CategoryManageService categoryManageService;
	
	/**
	 * 
	 *  开发人：颜振兴
	 *  时间 2018年12月28日
	 *  TODO 查询门类树
	 *  @return
	 *  findMenLeiTree
	 */
	@LogAnnotation(value = "query",opName = "查询门类树")
	@RequestMapping(value="findMenLeiTree",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject findMenLeiTree(){
		List<MenTree> findMenLeiTree = categoryManageService.findMenLeiTree();
		JSONObject jsonObject = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("total", findMenLeiTree.size());
		data.put("rows", findMenLeiTree);
		jsonObject.put("flag", "1");
		jsonObject.put("data", data);
		return jsonObject;
	}
	
	/**
	 * 
	 *  开发人：颜振兴
	 *  时间 2018年12月28日
	 *  TODO 修改树的节点名称
	 *  @param menTree
	 *  @return
	 *  editTreeName
	 */
	@LogAnnotation(value = "update",opName = "修改树的节点名称")
	@RequestMapping(value="editTreeName",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject editTreeName(MenTree menTree){
		JSONObject jsonObject = new JSONObject();
		int result = categoryManageService.editTreeName(menTree);
		if(result>=1) {
			jsonObject.put("flag", "1");
			jsonObject.put("data", menTree);
		}else {
			jsonObject.put("flag", "0");
			
		}
		return jsonObject;
	}
	
	/**
	 * 
	 *  开发人：颜振兴
	 *  时间 2018年12月28日
	 *  TODO 删除树的节点
	 *  @param menTree
	 *  @return
	 *  editTreeName
	 */
	@LogAnnotation(value = "delete",opName = "删除树的节点名称")
	@RequestMapping(value="deleteTreeName",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject deleteTreeName(MenTree menTree ){
		JSONObject jsonObject = new JSONObject();
		int result = categoryManageService.deleteTreeName(menTree);
		if(result==1) {
			jsonObject.put("flag", "1");
		}else if(result==2){
			jsonObject.put("flag", "2");
		}else {
			jsonObject.put("flag", "0");
		}
		return jsonObject;
	}
	
	/**
	 * 添加门类
	 * @author 王磊
	 * @Date 2019年1月2日 上午9:32:36
	 * @param newCategoryName
	 * @param newCategoryCode
	 * @param templateCategoryCode
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "添加档案管理门类")
	@RequestMapping(value="addCategory")
	@ResponseBody
	public JSONObject addCategory(String newCategoryName, String newCategoryCode, String templateCategoryCode, String generalArchiveCode){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag", categoryManageService.addCategory(newCategoryName, newCategoryCode, templateCategoryCode, generalArchiveCode));
		return jsonObject;
		
	}
	
	
	/**
	 * 获取所有门类模板（门类代号及名称）
	 * @author 王磊
	 * @Date 2019年1月3日 上午11:04:01
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询门类模板")
	@RequestMapping(value="getCategoryOption")
	@ResponseBody
	public JSONObject getCategoryOption(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", categoryManageService.getCategoryOption());
		return jsonObject;
	}
	
	/**
	 * 根据字段及字段值查询门类是否已经存在
	 * 门类名称和代号不能重复
	 * @author 王磊
	 * @Date 2019年1月3日 上午11:30:08
	 * @param name
	 * @param value
	 * @return 0可用，其他数值不可用
	 */
	@LogAnnotation(value = "query",opName = "查询新的模板和代号是否存在")
	@RequestMapping(value="isCategoryExist")
	@ResponseBody
	public JSONObject isCategoryExist(String name,String value){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", categoryManageService.isCategoryExist(name, value));
		return jsonObject;
	}	
	
	/**
	 * 
	 *  开发人：颜振兴
	 *  时间 2019年1月3日
	 *  TODO 查找门类下的所有字段
	 *  @return
	 *  findZiDuan
	 */
	@LogAnnotation(value = "query",opName = "查找门类下的所有字段")
	@RequestMapping(value="findZiDuan",produces = "application/json;charset=utf-8")
	@ResponseBody
	public List<Map<String,Object>> findZiDuan(String tName, String pid){
		List<Map<String,Object>> findZiDuan = categoryManageService.findZiDuan(tName, pid);
		return findZiDuan;
		
	}
	
	/**
	 * 
	 *  开发人：颜振兴
	 *  时间 2019年1月7日
	 *  TODO 删除门类下的字段
	 *  @param tName
	 *  @param columnName
	 *  @return
	 *  deleteZiDuan
	 */
	@LogAnnotation(value = "delete",opName = "删除门类下的字段")
	@RequestMapping(value="deleteZiDuan",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject deleteZiDuan(String tName,String columnName){
		JSONObject jsonObject = new JSONObject();
		int deleteZiDuan = categoryManageService.deleteZiDuan(tName, columnName);
		if(deleteZiDuan==1) {
			jsonObject.put("flag", "1");
			return jsonObject;
		}else if(deleteZiDuan==2) {
			jsonObject.put("flag", "2");
			return jsonObject;
		}else{
			jsonObject.put("flag", "0");
			return jsonObject;
		}
		
	}
	/**
	 * 
	 *  开发人：颜振兴
	 *  时间 2019年1月7日
	 *  TODO 修改门类下的字段
	 *  @param tName
	 *  @param updateColumnName
	 *  @param columnName
	 *  @param column
	 *  @return
	 *  updateZiDuan
	 */
	@LogAnnotation(value = "update",opName = "修改门类下的字段")
	@RequestMapping(value="updateZiDuan",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject updateZiDuan(String tName, String updateColumnName,String columnName,String column,String code){
		JSONObject jsonObject = new JSONObject();
		int updateZiDuan = categoryManageService.updateZiDuan(tName, updateColumnName, columnName, column,code);
		 if(updateZiDuan>0) {
				jsonObject.put("flag", "1");
				return jsonObject;
			}else {
				jsonObject.put("flag", "0");
				return jsonObject;
			}
		
	}
	
	/**
	 * 
	 *  开发人：颜振兴
	 *  时间 2019年1月7日
	 *  TODO 添加门类下的字段
	 *  @param tName
	 *  @param StrJson
	 *  @return
	 *  addZiDuan
	 */
	@LogAnnotation(value = "save",opName = "添加门类下的字段")
	@RequestMapping(value="addZiDuan",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject addZiDuan(String chnName,String tName,String StrJson){
		JSONObject jsonObject = new JSONObject();
		int updateZiDuan = categoryManageService.addZiDuan(chnName,tName, StrJson);
		 if(updateZiDuan>0) {
				jsonObject.put("flag", "1");
				return jsonObject;
			}else {
				jsonObject.put("flag", "0");
				return jsonObject;
			}
		
	}
	/**
	 * 获取人员树
	 * @param codeId
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "获取人员树")
	@RequestMapping(value="personnelTree",produces = "application/json;charset=utf-8")
	@ResponseBody
	public com.alibaba.fastjson.JSONObject personnelTree(String codeId){
	com.alibaba.fastjson.JSONObject personnelTree = categoryManageService.personnelTree(codeId);
	return personnelTree;
	}
	
	/**
	 * 通过人员树查询门类树
	 * @param menTree
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "通过人员树查询门类树")
	@RequestMapping(value="personnelTreeToMenTree",produces = "application/json;charset=utf-8")
	@ResponseBody
	public com.alibaba.fastjson.JSONObject findTreeByMenCode(MenTree menTree){
		com.alibaba.fastjson.JSONObject personnelTreeToMenTree = categoryManageService.personnelTreeToMenTree(menTree);
		return personnelTreeToMenTree;
	}
	
	@LogAnnotation(value = "delete",opName = "判断是否档号项、对照表")
	@RequestMapping(value="isKeYiDelete",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject isKeYiDelete(String tName, String columnName, String code){
		Map<String, String> keYiDelete = categoryManageService.isKeYiDelete(tName, columnName, code);
		String result = keYiDelete.get("result");
		JSONObject jsonObject = new JSONObject();
		if(result.equals("0")) {
			jsonObject.put("result", 0);
			return jsonObject;
		}else if(result.equals("1")) {
			jsonObject.put("result", 1);
			jsonObject.put("msg",  "【"+keYiDelete.get("SS")+"】字段配置为档号项，无法删除！");
			return jsonObject;
		}else if(result.equals("2")) {
			jsonObject.put("result", 1);
			jsonObject.put("msg",  "该字段在数据对照配置-对照关系列表-"+keYiDelete.get("HH")+"中配置，无法删除！");
			return jsonObject;
		}else if(result.equals("3")) {
			jsonObject.put("result", 1);
			jsonObject.put("msg", "【"+keYiDelete.get("SS")+"】字段配置为档号项，无法删除！\r\n 并且在数据对照配置-对照关系列表-"+keYiDelete.get("HH")+"中配置，无法删除！");
			return jsonObject;
		}else {
			jsonObject.put("result", 2);
			jsonObject.put("msg", "删除出现错误，请刷新页面再次尝试");
			return jsonObject;
		}
	}
	
	/**
	 * 判断是否档号项、对照表
	 * 批量删除的情况
	 */
	@LogAnnotation(value = "delete",opName = "判断是否档号项、对照表")
	@RequestMapping(value="isDanghaoGuizhe",produces = "application/json;charset=utf-8")
	@ResponseBody
	public com.alibaba.fastjson.JSONObject isDanghaoGuizhe(String code,String[] columnNames) {
		com.alibaba.fastjson.JSONObject danghaoGuizhe = categoryManageService.isDanghaoGuizhe(code, columnNames);
		return danghaoGuizhe;
	}
	
	/**
	 * 根据门类查询立卷单位
	 * 批量删除的情况
	 */
	@LogAnnotation(value = "query",opName = "根据门类查询立卷单位")
	@RequestMapping(value="LJDWmark",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject LJDWmark(String code,String userId) {
		Map<String,String> ljdWmark = categoryManageService.LJDWmark(code,userId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", ljdWmark);
		jsonObject.put("flag", "1");
		return jsonObject;
	}
	
	/**
	 * 根据门类和立卷单位查找立卷单位下的所有人员
	 * @param code
	 * @param chushi
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "根据门类和立卷单位查找立卷单位下的所有人员")
	@RequestMapping(value="queryLjUser",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject queryLjUser(String code,String chushi) {
	List<Map<String, Object>> queryLjUser = categoryManageService.queryLjUser(code, chushi);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", queryLjUser);
		jsonObject.put("flag", "1");
		return jsonObject;
	}
	
	@LogAnnotation(value = "delete",opName = "批量删除")
	@RequestMapping(value="deleteAllZiDuan",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject deleteAllZiDuan(String jsonStr) {
		com.alibaba.fastjson.JSONObject deleteAllZiDuan = categoryManageService.deleteAllZiDuan(jsonStr);
		JSONObject jsonObject = new JSONObject();
		if((int)deleteAllZiDuan.get("flag")>0) {
			jsonObject.put("flag", "1");
		}else {
			jsonObject.put("flag", "0");
		}
		return jsonObject;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 删除门类时，判断业务表中是否有数据
	 * @Date 2019/4/8 12:01
	 * @Param [jsonStr]
	 * @return net.sf.json.JSONObject
	 **/
	@LogAnnotation(value = "query",opName = "删除门类时，判断业务表中是否有数据")
	@RequestMapping("canDelete")
	@ResponseBody
	public JSONObject canDelete(String[] tableCodes) {
		JSONObject json = new JSONObject();
		try {
			com.alibaba.fastjson.JSONObject canDelete = categoryManageService.canDelete(tableCodes);
			json.put("flag", "1");
			json.put("num", canDelete.get("num"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(), e);
			json.put("flag", "0");
		}

		log.info(json.toString());
		return json;
	}
}
