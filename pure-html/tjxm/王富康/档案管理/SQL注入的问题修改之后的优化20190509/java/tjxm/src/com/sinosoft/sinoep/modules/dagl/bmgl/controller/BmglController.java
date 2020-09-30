package com.sinosoft.sinoep.modules.dagl.bmgl.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.sinosoft.sinoep.common.util.ZeBraClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.handlerInterceptor.LogAnnotation;
import com.sinosoft.sinoep.modules.dagl.bmgl.entity.VirtualClass;
import com.sinosoft.sinoep.modules.dagl.bmgl.services.BmglService;
import com.sinosoft.sinoep.modules.dagl.constant.DAGLCommonConstants;
import com.sinosoft.sinoep.modules.dagl.wdgl.services.DaglReceiveFileService;
import com.sinosoft.sinoep.modules.dagl.wdgl.services.DaglSendFileService;
import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;
import com.sinosoft.sinoep.modules.system.config.dictionary.services.DataDictionaryService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("dagl/bmgl")
public class BmglController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BmglService bmglService;
	
	@Autowired
	private DataDictionaryService service;
	
	@Autowired
	private DaglReceiveFileService receiveService;
	
	@Autowired
	private DaglSendFileService sendService;
	
	

	/**
	 * 
	 * @param tName
	 * @return
	 * @author 颜振兴
	 * String
	 * TODO 动态获取表单列
	 */
	@LogAnnotation(value = "query",opName = "动态获取表单列")
	@RequestMapping(value="findAdd",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findAdd(String tName){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag", 1);
		List<Map<String, Object>> dynamicFind = bmglService.dynamicFind(tName);
		String jsonStr = JSON.toJSONString(dynamicFind,SerializerFeature.WriteMapNullValue);
		jsonObject.put("data", dynamicFind);
		return jsonStr;
		
	} 
	
	/**
	 * 
	 * @param tName
	 * @return
	 * @author 颜振兴
	 * String
	 * TODO 动态获取标签
	 */
	@LogAnnotation(value = "query",opName = "动态获取标签")
	@RequestMapping(value="findLabel",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findLabel(String tName){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag", 1);
		List<Map<String,Object>> findLabel = bmglService.findLabel(tName);
		String jsonStr = JSON.toJSONString(findLabel);
		jsonObject.put("data", jsonStr);
		return jsonStr;
		
	} 
	
	/**
	 * 
	 * @param tName
	 * @return
	 * @author 颜振兴
	 * String
	 * TODO 动态获取标签DAK
	 */
	@LogAnnotation(value = "query",opName = "动态获取标签DAK")
	@RequestMapping(value="findLabelDAK",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findLabelDAK(String tName){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag", 1);
		List<Map<String,Object>> findLabel = bmglService.findLabelDAK(tName);
		String jsonStr = JSON.toJSONString(findLabel);
		jsonObject.put("data", jsonStr);
		return jsonStr;
		
	} 
	
	/**
	 * 
	 * @param tName
	 * @return
	 * @author 颜振兴
	 * String
	 * TODO 动态加入表数据
	 */
	@LogAnnotation(value = "query",opName = "动态加入表数据")
	@RequestMapping(value="dynamicAdd",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject dynamicAdd(String jsonStr,String tName, String fids, String all, String ranking){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag", 0);
		com.alibaba.fastjson.JSONObject dynamicAdd = bmglService.dynamicAdd(jsonStr, tName,fids, all, ranking);
		if ((int) dynamicAdd.get("flag")>0) {
			jsonObject.put("flag", 1);
			jsonObject.put("uuid", dynamicAdd.get("uuid"));
		}
		return jsonObject;
		
	} 
	
	/**
	 *
	 * @param jsonStr
	 * @param tName
	 * @return
	 * @author 颜振兴
	 * JSONObject
	 * TODO 动态修改
	 */
	@LogAnnotation(value = "query",opName = "动态修改")
	@RequestMapping(value="dynamicUpdate",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject dynamicUpdate(String jsonStr,String tName,String key,String value, String all, String ranking){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag", 0);
		int dynamicAdd = bmglService.dynamicUpdate(jsonStr, tName, key, value, all, ranking);
		if (dynamicAdd>0) {
			jsonObject.put("flag", 1);
		}
		return jsonObject;
		
	} 
	
	/**
	 * 
	 * @param jsonStr
	 * @param tName
	 * @return
	 * @author 颜振兴
	 * JSONObject
	 * TODO 动态列表
	 */
	@LogAnnotation(value = "query",opName = "动态列表")
	@RequestMapping(value="dynamicList",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String dynamicList(String jsonStr,String tName,PageImpl pageImpl,String conditions,String fRecIds,String parameters,String complexParam,String danweihao,String danweiku,Integer all,String ljdanweiAndRenyuan){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag", 0);
		List<Map<String,Object>> dynamicList = bmglService.dynamicList(tName, pageImpl, jsonStr, conditions, fRecIds,parameters,complexParam,danweihao,danweiku,all,ljdanweiAndRenyuan);
		String jsonString = JSON.toJSONString(dynamicList,SerializerFeature.WriteMapNullValue);
		return jsonString;
		
	} 
	
	/**
	 * 
	 * @return
	 * @return
	 * @author 颜振兴
	 * String
	 * TODO 门类树
	 */
	@LogAnnotation(value = "query",opName = "门类树")
	@RequestMapping(value="findTree",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject findTree(boolean isQ2,String isAdmin,String dwylk){
		JSONObject jsonObject = new JSONObject();
		JSONObject data = new JSONObject();
		jsonObject.put("flag", 0);
		List<VirtualClass> findTree = bmglService.findTree(isQ2,isAdmin,dwylk);
		if (findTree.size()>0) {
			jsonObject.put("flag", 1);
			data.put("total", findTree.size());
			data.put("rows", findTree);
			jsonObject.put("data", data);
		}
		return jsonObject;
		
	} 
	
	/**
	 * 
	 * @param tName
	 * @param fids
	 * @param column
	 * @param value1
	 * @param value2
	 * @return
	 * @author 颜振兴
	 * JSONObject
	 * TODO 替换
	 */
	@LogAnnotation(value = "query",opName = "替换")
	@RequestMapping(value="replace",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject replace(String tName, String fids, String column, String value1, String value2) {
		JSONObject json = new JSONObject();
		json.put("flag", 0);
		int replace = bmglService.replace(tName, fids, column, value1, value2);
		if (replace>0) {
			json.put("flag", 1);
			json.put("num", replace);
		}
		return json;
		
	}
	
	/**
	 * 
	 * @param tName
	 * @param id
	 * @return
	 * @author 颜振兴
	 * String
	 * TODO 根据id获取一条数据
	 */
    @LogAnnotation(value = "query",opName = "根据id获取一条数据")
	@RequestMapping(value="findById",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findById(String tName, String id) {
		List<Map<String, Object>> findById = bmglService.findById(tName, id);
		String jsonString = JSON.toJSONString(findById,SerializerFeature.WriteMapNullValue);
		return jsonString;
		
	}
	
	/**
	 *
	 * @param column
	 * @return
	 * @author 颜振兴
	 * JSONObject
	 * TODO 添加树的节点
	 */
	@LogAnnotation(value = "query",opName = "添加树的节点")
	@RequestMapping(value="addTree",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject addTree(VirtualClass column) {
		JSONObject json = new JSONObject();
		json.put("flag", 1);
		List<VirtualClass> addTree = bmglService.addTree(column);
		json.put("data", addTree);
		return json;
		
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 判断该门类下是否已经含有该分类/根据门类code，pid，字段英文名，是否admin，创建人查询已有的选项回填下拉框
	 * @Date 2019/5/28 11:54
	 * @Param [virtualClass]
	 * @return com.sinosoft.sinoep.modules.dagl.bmgl.entity.VirtualClass
	 **/
	@LogAnnotation(value = "query",opName = "判断该门类下是否已经含有该分类/根据门类code，pid，字段英文名，是否admin，创建人查询已有的选项回填下拉框")
	@RequestMapping(value="findVirtualClass",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject findVirtualClass(VirtualClass virtualClass){
		JSONObject json = new JSONObject();
		json.put("flag", 1);
		List<VirtualClass> findSelect = bmglService.findVirtualClass(virtualClass);
		json.put("data", findSelect);
		return json;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @author 颜振兴
	 * JSONObject
	 * TODO 删除树的节点以及子节点
	 */
	@LogAnnotation(value = "query",opName = "删除树的节点以及子节点")
	@RequestMapping(value="deleteTree",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject deleteTree(String id) {
		JSONObject json = new JSONObject();
		json.put("flag", 1);
		bmglService.deleteTree(id);
		return json;
		
	}
	
	/**
	 * 
	 * @param tName
	 * @param id
	 * @return
	 * @author 颜振兴
	 * JSONObject
	 * TODO 动态删除一条数据
	 */
	@LogAnnotation(value = "query",opName = "动态删除一条数据")
	@RequestMapping(value="dynamicDelete",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject dynamicDelete(String tName,String id,int all,int ranking) {
		JSONObject json = new JSONObject();
		json.put("flag", 0);
		int dynamicDelete = bmglService.dynamicDelete(tName, id,all,ranking);
		if (dynamicDelete>0) {
			if(all-ranking==0){
				receiveService.updateState(id, DAGLCommonConstants.WEN_DIAN_STATUS[2]);
				sendService.updateState(id, DAGLCommonConstants.WEN_DIAN_STATUS[2]);
			}
			json.put("flag", 1);
		}
		return json;
		
	}
	
	
	/**
	 * 
	 * @param tName
	 * @param categoryCode
	 * @param all
	 * @param ranking
	 * @return
	 * @author 颜振兴
	 * JSONObject 查询档案规则
	 * TODO
	 */
	@LogAnnotation(value = "query",opName = "查询档案规则")
	@RequestMapping(value="findDHGZ",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject findDHGZ(String tName,String categoryCode,int all,int 
			ranking) {
		JSONObject json = new JSONObject();
		String id = bmglService.findMenById(categoryCode);
		List<Map<String, Object>> list = bmglService.findDHGZbyMCode(id);
		int size = list.size();
		int num = size-(all-ranking);
		List<Map<String, Object>> newList = new ArrayList<>();
		for(int i =0;i<num;i++) {
			newList.add(i, list.get(i));
		}
		String replaceAll = com.alibaba.fastjson.JSONObject.toJSONString(newList,SerializerFeature.WriteMapNullValue).replaceAll("null", "\"\"");
		Map<String, Object> findDAGLByTableName = bmglService.findDAGLByTableName(tName);
		String findZHbycolnum = bmglService.findZHbycolnum(tName, findDAGLByTableName.get("S_COL_NAME").toString().toLowerCase());
		findDAGLByTableName.put("ZH", findZHbycolnum);
		String replaceAll2 = com.alibaba.fastjson.JSONObject.toJSONString(findDAGLByTableName,SerializerFeature.WriteMapNullValue).replaceAll("null", "\"\"");
		
		json.put("flag", 1);
		JSONObject data = new JSONObject();
		if (all==ranking) {
			data.put("rows", replaceAll);
			data.put("colsf", replaceAll2);
			Map<String, String> map = new HashMap<>();
			map.put("archive_no", bmglService.findZHbycolnum(tName, "archive_no"));
			data.put("colsz", map);
			json.put("data", data);
		}else if(ranking>1&&ranking<all) {
			Map<String, Object> TableName = bmglService.findDAGLByTableNameF(tName);
			String findZHbycolnum1 = bmglService.findZHbycolnum(tName, (String)TableName.get("S_COL_NAME"));
			TableName.put("ZH", findZHbycolnum1);
			String replaceAll3 = com.alibaba.fastjson.JSONObject.toJSONString(TableName,SerializerFeature.WriteMapNullValue).replaceAll("null", "\"\"");
			data.put("rows", replaceAll);
			data.put("colsf", replaceAll2);
			data.put("colsz", replaceAll3);
			json.put("data", data);
		}else if(ranking==1){
			data.put("rows", replaceAll);
			data.put("colsf", replaceAll2);
			json.put("data", data);
		}
		
		return json;
		
	}
	
	/**
	 * 
	 * @param categoryCode
	 * @return
	 * @author 颜振兴
	 * List<Map<String, Object>>
	 * TODO 根据门类号获取档号规则 替换的时候用
	 */
	@LogAnnotation(value = "query",opName = "根据门类号获取档号规则 替换的时候用")
	@RequestMapping(value="findDHGZ2",produces = "application/json;charset=utf-8")
	@ResponseBody
	public List<Map<String, Object>> findDHGZ2(String categoryCode) {
		String id = bmglService.findMenById(categoryCode);
		List<Map<String, Object>> list = bmglService.findDHGZbyMCode(id);
		if (list!=null&&!list.isEmpty()) {
			return list;
		}else {
			return null;
		}
	}
	
	/**
	 *
	 * @param marks
	 * @return
	 * @author 颜振兴
	 * JSONObject
	 * TODO 查询指定的数据字典
	 */
	@LogAnnotation(value = "query",opName = "查询指定的数据字典")
	@RequestMapping(value="byDataDictionary",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject byDataDictionary(String marks) {
		JSONObject json = new JSONObject();
		String[] markss = marks.split(",");
		for(String mark : markss) {
			List<DataDictionary> dicts;
			try {
			dicts = service.getListByMark(mark,"1");
				Map<String, String> map = new HashMap<>();
				for (DataDictionary dic : dicts) {
					map.put(dic.getCode(), dic.getName());
				}
				json.put(mark, map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json;
	}

	/**
	 * TODO 获取当前标签页勾选的数据（多行）
	 * @author 李颖洁  
	 * @date 2018年11月22日上午11:17:45
	 * @param tName
	 * @param fids
	 * @return JSONObject
	 */
	@LogAnnotation(value = "query",opName = "查询勾选的多行数据")
	@RequestMapping(value="getSelectedData",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject getSelectedData(String tName, String fids) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("flag", "0");
		try {
			List<Map<String,Object>> list = bmglService.getSelectedData(tName, fids);
			json.put("flag", "1");
			data.put("rows", JSON.toJSONString(list));
			json.put("data", data);
		} catch (Exception e) {
			//log.error(e.getMessage(),e);
			json.put("msg", "获取失败！");
		}
		return json;
	}

	/**
	 * TODO 获取未组卷的文件
	 * @author 李颖洁
	 * @date 2018年11月22日上午11:17:45
	 * @param tName （案卷表名）
	 * @param basefolderUnit 处室id
	 * @param creUserId 创建人id
	 * @return JSONObject
	 */
	@LogAnnotation(value = "query",opName = "查询未组卷的文件")
	@RequestMapping(value="getNotSetVolumeList",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject getNotSetVolumeList(String tName,String basefolderUnit,String creUserId) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("flag", "0");
		try {
			List<Map<String,Object>> list = bmglService.getNotSetVolumeList(tName,basefolderUnit,creUserId);
			json.put("flag", "1");
			data.put("rows", JSON.toJSONString(list));
			json.put("data", data);
		} catch (Exception e) {
			//log.error(e.getMessage(),e);
			json.put("msg", "获取失败！");
		}
		return json;
	}

	/**
	 * TODO 获取案卷下的卷内信息
	 * @author 李颖洁
	 * @date 2018年11月22日上午11:17:45
	 * @param tName  父表表名
	 * @param fid 父表选中的id
	 * @param creUserId 案卷记录创建者id
	 * @return JSONObject
	 */
	@LogAnnotation(value = "query",opName = "查询父表对应的子表数据")
	@RequestMapping(value="getChildData",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject getChildDataByFartherId(String tName, String fid,String basefolderUnit,String creUserId) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("flag", "0");
		try {
			List<Map<String,Object>> list = bmglService.getChildDataByFartherId(tName, fid,basefolderUnit,creUserId);
			json.put("flag", "1");
			data.put("rows", JSON.toJSONString(list));
			json.put("data", data);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "获取失败！");
		}
		return json;
	}

	/**
	 * TODO 获取卷内的关联字段
	 * @author 李颖洁
	 * @date 2018年11月22日上午11:17:45
	 * @param tName  父表表名
	 * @return JSONObject
	 */
	@LogAnnotation(value = "query",opName = "获取卷内的关联字段")
	@RequestMapping(value="getFileConnationCol",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject getFileConnationCol(String tName) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			Map<String,Object> map = bmglService.findDAGLByTableNameF(tName);
			json.put("flag", "1");
			json.put("data", JSON.toJSONString(map));
		} catch (Exception e) {
			//log.error(e.getMessage(),e);
			json.put("msg", "获取失败！");
		}
		return json;
	}

	/**
	 * TODO 拆卷/组卷（卷内文件从案卷中调出/调入）
	 * @author 李颖洁
	 * @date 2018年11月23日下午4:07:40
	 * @param tName 卷内表名
	 * @param ids 选中的卷内id
	 * @param archiveFlag  组卷标识
	 * @param conCol 关联字段
	 * @param conCol 关联字段的值
	 * @return JSONObject
	 */
	@LogAnnotation(value = "query",opName = "卷内文件从案卷中调出/调入")
	@RequestMapping(value="openOrSetVolume",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject openOrSetVolume(String tName,String ids,String archiveFlag,String conCol,String conVal) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int num = bmglService.openVolume(tName,ids,archiveFlag,conCol,conVal);
			json.put("flag", "1");
			json.put("data", num);
		} catch (Exception e) {
			//log.error(e.getMessage(),e);
			json.put("msg", "获取失败！");
		}
		return json;
	}

	/**
	 * TODO 档案卷内文件确认调整
	 * @author 李颖洁
	 * @date 2018年11月24日下午6:05:24
	 * @param tName 卷内表名
	 * @param conCol 关联字段
	 * @param conVal 关联字段值
	 * @param data 更改的数据项
	 * @return JSONObject
	 */
	@LogAnnotation(value = "update",opName = "档案卷内文件确认调整")
	@RequestMapping(value="confirmAdjust",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject confirmAdjust(String tName,String conCol,String conVal,String data,String categoryCode,String pName,String pId, String ranking, String all) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			int num = bmglService.updateFileRelation(tName,conCol,conVal,data,categoryCode,pName,pId, ranking, all);
			if(num==1){
				json.put("flag", "1");
				json.put("data", num);
			}else{
				json.put("msg", "调整失败！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "调整失败！");
			
			
		}
		return json;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 档案移交
	 * @Date 2018/11/22 19:34
	 * @Param [tName, fids]
	 * @return net.sf.json.JSONObject
	 **/
	@LogAnnotation(value = "query",opName = "档案移交")
	@RequestMapping(value="recordSubmit",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject recordSubmit(String tName, String fids) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			List<Map<String,Object>> list = bmglService.recordSubmit(tName, fids);
			String replaceAll = com.alibaba.fastjson.JSONObject.toJSONString(list,SerializerFeature.WriteMapNullValue).replaceAll("null", "\"\"");
			json.put("flag", "1");
			json.put("data", replaceAll);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "获取失败！");
		}
		return json;

	}
	
	/**
	 * 
	 * @param column
	 * @param relevancyId
	 * @param tName
	 * @param chushiId
	 * @return
	 * @author 颜振兴
	 * JSONObject
	 * TODO 判断关联字段是否存在 返回1可用不存在 返回0 数据库存在
	 */
	@LogAnnotation(value = "query",opName = "判断关联字段的是否纯在")
	@RequestMapping(value="isRepetition",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject isRepetition(String column, String relevancyId,String tName,String chushiId,String recId,String ranking) {
		JSONObject json = new JSONObject();
		
		int repetition = bmglService.isRepetition(column, relevancyId, tName, chushiId,recId, ranking);
		if(repetition>0) {
			json.put("flag", "0");
		}else {
			json.put("flag", "1");
		}
		return json;

	}

	/**
	 * 生成条码号
	 * @param ids 需要生成的条码的档案号id
	 * @param tableName  表名
	 * @return
	 */
	@LogAnnotation(value = "save",opName = "生成条码号")
	@RequestMapping("saveCode")
	@ResponseBody
	public JSONObject saveCode(String ids,String tableName){
		JSONObject json = new JSONObject();
		boolean flag = bmglService.saveCode(ids,tableName);
		json.put("flag",flag);
		return json;
	}

	/**
	 * 条码打印
	 * @param printerName   打印机名称
	 * @param printerIp   打印机ip
	 * @param daCode  档案号
	 * @param code   条码
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "打印机打印条码")
	@RequestMapping("zeBraprinter")
	@ResponseBody
	public boolean zeBraprinter(String printerName ,String printerIp,String daCode,String code){
		try {
			printerName = URLDecoder.decode(printerName,"UTF-8");
			printerIp = URLDecoder.decode(printerIp,"UTF-8");
			daCode = URLDecoder.decode(daCode,"UTF-8");
			code = URLDecoder.decode(code,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ZeBraClient.zeBraPrint(printerName,printerIp,daCode,code);
		return true;
	}
	/**
	 * 
	 * @param tName
	 * @param fids
	 * @return
	 * @author 颜振兴
	 * String
	 * TODO 根据id查数据
	 */
	@LogAnnotation(value = "query",opName = "根据id查数据")
	@RequestMapping(value="findParentData",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findParentData(String tName,String fids) {
		Map<String, Object> findParentData = bmglService.findParentData(tName, fids);
		String jsonString = JSON.toJSONString(findParentData,SerializerFeature.WriteMapNullValue);
		return jsonString;
	}
	
	/**
	 * 
	 * @param tName
	 * @return
	 * @author 颜振兴
	 * String
	 * TODO 批量删除
	 */
	@LogAnnotation(value = "delete",opName = "批量删除")
	@RequestMapping(value="dynamicDeletes",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject dynamicDeletes(String tName,String ids,int all,int ranking) {
		JSONObject json = new JSONObject();
		int i = bmglService.dynamicDeletes(tName,ids,all,ranking);
		if(i>0) {
			if(all-ranking==0){
				receiveService.updateState(ids, DAGLCommonConstants.WEN_DIAN_STATUS[2]);
				sendService.updateState(ids, DAGLCommonConstants.WEN_DIAN_STATUS[2]);
			}
			json.put("flag", 1);
			json.put("total", i);
			json.put("ids", ids.split(",").length);
		}else {
			json.put("flag", 0);
		}
		return json;
	}
	
	/**
	 * TODO 档案汇总
	 * @author 李颖洁
	 * @date 2018年11月24日下午6:05:24
	 * @param tName 当前标签页表名
	 * @param fids 选择数据ids
	 * @return JSONObject
	 */
	@LogAnnotation(value = "update",opName = "档案汇总")
	@RequestMapping(value="recodeGather",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject recodeGather(String tName,String fids,String categoryCode,String total,String ranking) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			 json = bmglService.recodeGather(tName,fids,categoryCode,total,ranking);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "汇总失败！");
		}
		return json;
	}
	
	
	
	
	
	/**
	 * 
	 * @param tName
	 * @param chushiid
	 * @return
	 * @author 颜振兴
	 * JSONObject
	 * TODO 查询数量
	 */
	@LogAnnotation(value = "query",opName = "查询数量")
	@RequestMapping(value="findThisTotal",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject findThisTotal(String column,String columnValue, String tName, String chushiid,String hao) {
		JSONObject json = new JSONObject();
		int i = bmglService.findThisTotal(tName, chushiid,column,columnValue,hao);
		json.put("data", i);
		return json;
	}
	
 /**
  * 
  *  开发人：颜振兴
  *  时间 2018年12月10日
  *  TODO 根据当前表明查父级表的管理字段
  *  @param tName
  *  @return
  *  findFguanlian
  */
	@LogAnnotation(value = "query",opName = "根据当前表明查父级表的管理字段")
	@RequestMapping(value="findFguanlian",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String  findFguanlian(String tName) {
		Map<String, Object> findDAGLByTableName = bmglService.findDAGLByTableName(tName);
		Map<String, Object> findDAGLByTableName2 = bmglService.findDAGLByTableName(findDAGLByTableName.get("M_TABLE_NAME").toString());
		return findDAGLByTableName2.get("M_COL_NAME").toString();
	}
	
	
	/**
	 * 根据表名，查询表字段及其在页面所占宽度
	 * @author 王磊
	 * @Date 2018年12月24日 下午4:12:12
	 * @param tName
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "查询当前表对应的各个字段及页面显示宽度")
	@RequestMapping(value="findColumnWidth",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String findColumnWidth(String tName) {
		//JSON.toJSONString(object)
		return JSON.toJSONString(bmglService.findColumnWidth(tName));
	}
	
	/**
	 * 
	 *  开发人：颜振兴
	 *  时间 2018年12月24日
	 *  TODO 修改新增页面表单样式
	 *  @param tName
	 *  @param list
	 *  @return
	 *  editStyle
	 */
	@LogAnnotation(value = "update",opName = "修改新增页面表单样式")
	@RequestMapping(value="editStyle",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String  editStyle(String tName,String list) {
		int editStyle = bmglService.editStyle(tName, list);
		return editStyle+"";
	}
	
	
	/**
	 * 多级查询
	 * @author 王磊
	 * @Date 2019年2月14日 上午10:52:18
	 * @param cateNo
	 * @param mainTitle
	 * @param ljdw
	 * @param isDak
	 * @param personType
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "多级查询")
	@RequestMapping(value="multiLevelQuery",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject multiLevelQuery(String cateNo, String mainTitle, String ljdw,String isDak,String personType) {
		JSONObject json = new JSONObject();
		try {
			json.put("flag", "1");
			json.put("data", bmglService.multiQuery(cateNo, mainTitle, ljdw, isDak, personType));
		} catch (Exception e) {
			log.info("档案管理-多级查询出现异常："+e.getMessage());
			json.put("flag", "0");
		}
		return json;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 批量添加表数据
	 * @Date 2019/2/15 15:48
	 * @Param [jsonStr, tName]
	 * @return net.sf.json.JSONObject
	 **/
	@LogAnnotation(value = "save",opName = "批量添加表数据")
	@RequestMapping(value="dynamicPlAdd",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject dynamicPlAdd(String jsonStr,String tName,String addCount, String categoryCode, String fids, String all, String ranking){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag", 0);
		int dynamicAdd = bmglService.dynamicPlAdd(jsonStr, tName, addCount, categoryCode,fids, all, ranking);
		if (dynamicAdd>0) {
			jsonObject.put("flag", 1);
		}
		return jsonObject;

	}
	
	/**
	 * 修改录入档案人员
	 * 颜振兴
	 * @param tName
	 * @param id
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "修改录入档案人员")
	@RequestMapping(value="tabUser",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject  tabUser(String tName,String id,String userId) {
		JSONObject object = new JSONObject();
		int tabUser = bmglService.tabUser(tName,id, userId);
		if (tabUser>0) {
			object.put("flag", 1);
		}else {
			object.put("flag", 0);
		}
		return object;
	}
	
	/**
	 * 
	 * TODO 借阅档案或者归还时修改相关档案借阅状态
	 * @author 王磊
	 * @Date 2019年2月27日 下午3:12:42
	 * @param recid 档案借阅子表存的档案recid
	 * @param tableName 档案借阅子表存的档案所在业务表
	 * @param isBorrow 0：归还，1：借出
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "借阅档案或者归还时修改相关档案借阅状态")
	@RequestMapping(value="updateDangAnBorrowStatus",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject updateDangAnBorrowStatus(String recid, String tableName,String isBorrow) {
		JSONObject object = new JSONObject();
		try {
			if(StringUtils.isNotEmpty(isBorrow) && "1".equals(isBorrow)){//借出
				object = bmglService.borrowDangAnToChangeStatus(recid, tableName);
			}else{//归还
				object = bmglService.returnDangAnToChangeStatus(recid, tableName);
			}
		} catch (Exception e) {
			object.put("msg", "操作失败！");
		}
		return object;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 封号/解封
	 * @Date 2019/2/21 14:28
	 * @Param [tName, id, userId]
	 * @return net.sf.json.JSONObject
	 **/
	@LogAnnotation(value = "update",opName = "封号")
	@RequestMapping(value="sealUpOrRemoveSeal",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject sealUpOrRemoveSeal(String tName, String fids, String type) {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			List<Map<String,Object>> list = bmglService.sealUpOrRemoveSeal(tName, fids, type);
			String replaceAll = com.alibaba.fastjson.JSONObject.toJSONString(list,SerializerFeature.WriteMapNullValue).replaceAll("null", "\"\"");
			json.put("flag", "1");
			json.put("data", replaceAll);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.put("msg", "获取失败！");
		}
		return json;
	}
	/**
	 * 档案查询新增数据
	 * 颜振兴
	 * @param tName
	 * @param id
	 * @param areYouOk
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "档案查询新增数据")
	@RequestMapping(value="adddd",produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String,Object> adddd(String id,String tName,boolean areYouOk,String all) {
		Map<String,Object> list = bmglService.adddd(id,tName,areYouOk,all);
		return list;
	}

	/**
	 * 
	 * TODO 档案第一层调整顺序
	 * @author 王磊
	 * @Date 2019年4月22日 上午10:30:19
	 * @param tableName 第一层表名
	 * @param recid 用户选择的数据recid
	 * @param newXuHao 调整后序号
	 * @return
	 */
	@LogAnnotation(value = "update",opName = "档案第一层调整顺序")
	@RequestMapping(value="adjustFirstLevel",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject adjustFirstLevel(String tableName,String recid,String newXuHao) {
		JSONObject object = new JSONObject();
		object = bmglService.adjustDangHao(tableName, recid, newXuHao);
		return object;
	}

	/**
	 *
	 * TODO 根据表名、立卷单位、录入人等参数查询数据
	 * @author 王磊
	 * @Date 2019年4月16日 下午12:03:43
	 * @param pageImpl
	 * @param tableName 业务表名
	 * @param selectIds 勾选的一条数据的id，根绝这个id查询跟这条数据的档号，截取掉最后一位，根据档号查询相同类型的数据
	 * @param liJuanDanWei 立卷单位名称
	 * @param luRuRen 录入人名称
	 * @param mainTitle 题名
	 * @param dangHao 档号
	 * @return
	 */
	@LogAnnotation(value = "query",opName = "分页查询第一层表数据")
	@ResponseBody
	@RequestMapping("getFirstLevelData")
	public PageImpl getFirstLevelData(PageImpl pageImpl,String tableName,String selectIds, String all, String liJuanDanWei,String luRuRen,String mainTitle,String dangHao) {
		Pageable pageable = new PageRequest(pageImpl.getPageNumber() - 1, pageImpl.getPageSize());
		pageImpl= bmglService.getFirstLevel(pageImpl, pageable, tableName, selectIds, all ,liJuanDanWei, luRuRen, mainTitle, dangHao);
		return pageImpl;
	}


	/**
	 * @Author 王富康
	 * @Description //TODO 查询数量
	 * @Date 2019/4/23 11:45
	 * @Param [danghao, columnName, tName, chushiid]
	 * @return net.sf.json.JSONObject
	 **/
	@LogAnnotation(value = "query",opName = "查询数量")
	@RequestMapping(value="getCountAdd",produces = "application/json;charset=utf-8")
	@ResponseBody
	public JSONObject queryCountAdd(String danghao,String columnName, String tName, String chushiid,String lastColumnName,String ranking) {
		JSONObject json = new JSONObject();
		int i = bmglService.queryCountAdd(danghao, columnName,tName,chushiid,lastColumnName, ranking);
		json.put("data", i);
		return json;
	}
}
