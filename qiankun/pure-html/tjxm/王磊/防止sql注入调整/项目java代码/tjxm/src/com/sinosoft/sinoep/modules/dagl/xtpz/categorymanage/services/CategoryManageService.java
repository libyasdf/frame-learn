package com.sinosoft.sinoep.modules.dagl.xtpz.categorymanage.services;

import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;
import com.sinosoft.sinoep.modules.dagl.xtpz.categorymanage.entity.MenTree;

/**
 * 门类管理service
 * @author 王磊
 * @Date 2018年12月28日 上午10:36:40
 */
public interface CategoryManageService {
	/**
	 * 
	 *  开发人：颜振兴
	 *  时间 2018年12月28日
	 *  TODO 查门类树
	 *  @return
	 *  findMenLeiTree
	 */
	List<MenTree> findMenLeiTree() ;

	/**
	 * 门类新增时，判断门类名称和门类代号是否可用
	 * @author 王磊
	 * @Date 2018年12月28日 下午4:09:55
	 * @param columnName
	 * @param columnValue
	 * @return 个数，0就是不存在
	 */
	int isCategoryExist(String columnName,String columnValue);
	
	/**
	 * 查询所有门类名称及门类代码
	 * @author 王磊
	 * @Date 2018年12月28日 下午5:00:32
	 * @return
	 */
	List<Map<String,Object>> getCategoryOption();
	
	/**
	 * 根据门类模板创建新的门类
	 * @author 王磊
	 * @Date 2018年12月28日 下午5:03:54
	 * @param newCategoryName
	 * @param newCategoryCode
	 * @param templateCategoryCode
	 * @return
	 */
	boolean addCategory(String newCategoryName,String newCategoryCode,String templateCategoryCode, String generalArchiveCode);
	
	/**
	 * 
	 *  开发人：颜振兴
	 *  时间 2018年12月28日
	 *  TODO 修改树的节点名称
	 *  @param menTree
	 *  @return
	 *  editTreeName
	 */
	int editTreeName(MenTree menTree);

	/**
	 * 
	 *  开发人：颜振兴
	 *  时间 2018年12月28日
	 *  TODO 删除树的父节点
	 *  @param name
	 *  @return
	 *  deleteTreeName
	 */
	int deleteTreeName(MenTree menTree);
	
	/**
	 * 
	 *  开发人：颜振兴
	 *  时间 2019年1月3日
	 *  TODO 查找门类下的所有字段
	 *  @param tName
	 *  @param pid
	 *  @return
	 *  findZiDuan
	 */
	List<Map<String, Object>> findZiDuan(String tName,String pid);
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
	int deleteZiDuan(String tName,String columnName);
	/**
	 * 
	 *  开发人：颜振兴
	 *  时间 2019年1月7日
	 *  TODO  修改门类下的字段
	 *  @param tName
	 *  @param columnName
	 *  @param column
	 *  @return
	 *  updateZiDuan
	 */
	int updateZiDuan(String tName, String updateColumnName,String columnName,String column,String code);
	/**
	 * 
	 *  开发人：颜振兴
	 *  时间 2019年1月7日
	 *  TODO  添加门类下的字段
	 *  @param tName
	 *  @param StrJson
	 *  @return
	 *  addZiDuan
	 */
	int addZiDuan(String chnName,String tName,String StrJson);
	
	/**
	 * 创建人员树
	 *
	 * @param codeId
	 * @return
	 */
	JSONObject personnelTree(String codeId);
	
	/**
	 * 通过人员树查询门类树
	 * @param codeId
	 * @return
	 */
	JSONObject personnelTreeToMenTree(MenTree menTree);
	
	/**
	 * 判断新增字段是否可以删除
	 * @param tName
	 * @param columnName
	 * @param code
	 * @return
	 */
	Map<String, String> isKeYiDelete(String tName,String columnName,String code);
	
	/**
	 * 是否为档号规则
	 * 颜振兴
	 * @param tName
	 * @param columnNames
	 * @return
	 */
	JSONObject isDanghaoGuizhe(String code,String[] columnNames);
	
	/**
	 * 根据门类查询立卷单位
	 * @param code
	 * @return
	 */
	Map<String,String> LJDWmark(String code,String userId);
	
	/**
	 * 根据门类和立卷单位查找立卷单位下的所有人员
	 * @param code
	 * @param chushi
	 * @return
	 */
	List<Map<String,Object>> queryLjUser(String code,String chushi);
	
	/**
	 * 批量删除
	 * @param jsonStr
	 * @return
	 */
	JSONObject deleteAllZiDuan(String jsonStr);

	/**
	 * @Author 王富康
	 * @Description //TODO 删除门类时，判断业务表中是否有数据
	 * @Date 2019/4/8 14:03
	 * @Param [tableNames]
	 * @return com.alibaba.fastjson.JSONObject
	 **/
	JSONObject canDelete(String[] tableCodes);
	
	/**
	 * 
	 * TODO 获得Q2的授权立卷单位，Q2变更用，正式环境中Q2的门类code为：q2
	 * @author 王磊
	 * @Date 2019年4月23日 下午8:21:30
	 * @return
	 */
	List<Map<String,Object>> getLiJuanDanWeiForQtow();
}
