package com.sinosoft.sinoep.modules.dagl.bmgl.services;

import java.util.List;
import java.util.Map;

import com.sinosoft.sinoep.common.util.Page;
import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dagl.bmgl.entity.MultiLevelQuery;
import com.sinosoft.sinoep.modules.dagl.bmgl.entity.VirtualClass;
import net.sf.json.JSONObject;

public interface BmglService {
	/**
	 * 
	 * @param tName
	 * @return
	 * @author 颜振兴
	 * List<Map<String,Object>>
	 * TODO 查询新增动态列表
	 */
	List<Map<String, Object>> dynamicFind(String tName);
	
	/**
	 * 
	 * @param tName
	 * @return
	 * @author 颜振兴
	 * List<Map<String,Object>>
	 * TODO 动态查询标签
	 */
	List<Map<String, Object>> findLabel(String tName);
	
	/**
	 * 
	 * @param jsonStr
	 * @param tName
	 * @return
	 * @author 颜振兴
	 * int
	 * TODO 动态新增
	 */
	com.alibaba.fastjson.JSONObject dynamicAdd(String jsonStr,String tName, String fids, String all, String ranking);
	
	/**
	 * 
	 * @param jsonStr
	 * @param tName
	 * @return
	 * @author 颜振兴
	 * int
	 * TODO  动态修改
	 */
	int dynamicUpdate(String jsonStr,String tName,String key,String value, String all, String ranking, String fileNumberRule);
	
	/**
	 * 
	 * @param tName
	 * @return
	 * @author 颜振兴
	 * List<Map<String,Object>>
	 * TODO 动态列表
	 * @param ljdanweiAndRenyuan 
	 */
	net.sf.json.JSONObject dynamicList(String tName, PageImpl pageImpl, String jsonStr, String conditions,
					 String fids, String parameters, String complexParam, String danweihao, String danweiku, Integer all, String ljdanweiAndRenyuan);
	
	/**
	 * 
	 * @return
	 * @author 颜振兴
	 * List<VirtualClass>
	 * TODO 获取部门树（整理编目、档案检索树查询）
	 */
	List<VirtualClass> findTree(boolean isQ2,String isAdmin,String dwylk);
	
	/**
	 * 
	 * @param tName
	 * @param fids
	 * @param column
	 * @param
	 * @param
	 * @return
	 * @author 颜振兴
	 * int
	 * TODO 替换
	 */
    JSONObject replace(String tName, String fids, String column,String categoryCode,String fileNumberRule, String replaceInputType, String oldReplaceValue,String oldReplaceCode, String nowReplaceValue, String nowReplaceCode, String all, String ranking, String type);
	
	/**
	 * 
	 * @param tName
	 * @param id
	 * @return
	 * @author 颜振兴
	 * List<Map<String,Object>>
	 * TODO 根据recid获取一条数据
	 */
	List<Map<String, Object>> findById(String tName, String id);
	
	/**
	 * 
	 * @param virtualClass
	 * @return
	 * @author 颜振兴
	 * VirtualClass
	 * TODO 添加树节点
	 */
	List<VirtualClass> addTree(VirtualClass virtualClass);

	/**
	 * @Author 王富康
	 * @Description //TODO 判断该门类下是否已经含有该分类/根据门类code，pid，字段英文名，是否admin，创建人查询已有的选项回填下拉框
	 * @Date 2019/5/28 11:54
	 * @Param [virtualClass]
	 * @return com.sinosoft.sinoep.modules.dagl.bmgl.entity.VirtualClass
	 **/
	List<VirtualClass> findVirtualClass(VirtualClass virtualClass);

	/**
	 * 
	 * @param id
	 * @return
	 * @author 颜振兴
	 * int
	 * TODO 删除树的节点 包括子节点
	 */
	int deleteTree(String id);
	
	/**
	 * 
	 * @param tName
	 * @param id
	 * @return
	 * @author 颜振兴
	 * int
	 * TODO 动态删除一条数据
	 */
	int dynamicDelete(String tName,String id,int all,int ranking);
	
	/**
	 * 
	 * @param category_code
	 * @return
	 * @author 颜振兴
	 * List<Map<String,Object>>
	 * TODO	根据门类代号查门类id
	 */
	String findMenById(String category_code); 
	
	/**
	 * 
	 * @param category_code_id
	 * @return
	 * @author 颜振兴
	 * List<Map<String,Object>>
	 * TODO 根据门类查档号规则
	 */
	List<Map<String, Object>> findDHGZbyMCode(String category_code_id);
	
	/**
	 * 
	 * @param tName
	 * @return
	 * @author 颜振兴
	 * List<Map<String,Object>>
	 * TODO 根据z表名查询档号关联字段
	 */
	Map<String, Object> findDAGLByTableName(String tName);
	
	/**
	 * 
	 * @param tName
	 * @return
	 * @author 颜振兴
	 * List<Map<String,Object>>
	 * TODO 根据f表名查询档号关联字段
	 */
	Map<String, Object> findDAGLByTableNameF(String tName);
	
	/**
	 * 
	 * @param tName
	 * @param colnum
	 * @return
	 * @author 颜振兴
	 * String 
	 * TODO 使用英文名查中文名
	 */
	String findZHbycolnum(String tName,String colnum);

	/**
	 * 
	 * @param tName
	 * @return
	 * @author 颜振兴
	 * List<Map<String,Object>>
	 * TODO 获取档案库标签
	 */
	List<Map<String, Object>> findLabelDAK(String tName);

	/** 
	 * TODO 获取勾选的多行数据列表
	 * @author 李颖洁  
	 * @date 2018年11月22日上午11:27:24
	 * @param tName
	 * @param fids
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> getSelectedData(String tName, String fids);

	/**
	 * TODO 获取未组卷的文件
	 * @author 李颖洁
	 * @date 2018年11月22日下午1:55:45
	 * @param tName 案卷表名
	 * @param basefolderUnit 立卷单位在数据字典中的code
	 * @param creUserId 创建者id
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> getNotSetVolumeList(String tName,String basefolderUnit,String creUserId);

	/**
	 * TODO  根据父表id 查询字表的关联信息
	 * @author 李颖洁
	 * @date 2018年11月23日上午11:53:31
	 * @param tName 表名
	 * @param fid 案卷号
	 * @param basefolderUnit 立卷单位在数据字典中的code
	 * @param creUserId 案卷记录创建者id
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> getChildDataByFartherId(String tName, String fid, String basefolderUnit,String creUserId);

	/**
	 * TODO 卷内文件从案卷中调出
	 * @author 李颖洁
	 * @date 2018年11月23日下午4:13:10
	 * @param tName 子表表名
	 * @param ids 选中的id
	 * @param archiveFlag  组卷标识
	 * @param conCol 关联字段
	 * @param conVal 关联字段的值
	 * @return int
	 */
	int openVolume(String tName, String ids, String archiveFlag, String conCol, String conVal, String fid, String all, String ranking);

	/**
	 * TODO 更改卷内文件关联（档案确认调整）
	 * @author 李颖洁
	 * @date 2018年11月24日下午6:08:39
	 * @param tName 卷内表名
	 * @param conCol 关联字段
	 * @param conVal 关联字段值
	 * @param data 更改数据项
	 * @return int
	 */
	int updateFileRelation(String tName, String conCol, String conVal, String data,String categoryCode,String pName,String pId, String ranking, String all);

	/**
	 * @Author 王富康
	 * @Description //TODO 档案移交
	 * @Date 2018/11/22 19:35
	 * @Param [tName, fids]
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 **/
	List<Map<String, Object>> recordSubmit(String tName, String fids);
	
	/**
	 * 
	 * @param relevancyId
	 * @param tName
	 * @param chushiId
	 * @return
	 * @author 颜振兴
	 * int
	 * TODO 判断是否在本处下纯在相同管理号 返回>1证明已存在0不存在
	 * @param recId 
	 */
	int isRepetition(String column,String relevancyId,String tName,String chushiId, String userid, String recId, String ranking);

	/**
	 * 生成条码号
	 * @param ids
	 * @param tableName
	 * @return
	 */
	boolean saveCode(String ids,String tableName);
	
	/**
	 * 
	 * @param tName
	 * @param fids
	 * @return
	 * @author 颜振兴
	 * Map<String,Object>
	 * TODO 根据id查数据
	 */
	Map<String, Object> findParentData(String tName,String fids);

	/**
	 * 
	 * @param tName
	 * @param ids
	 * @return
	 * @author 颜振兴
	 * int
	 * TODO 批量删除
	 */
	int dynamicDeletes(String tName, String ids,int all,int ranking);

	/** TODO 档案汇总
	 * @author 李颖洁  
	 * @date 2018年11月29日上午11:01:37
	 * @param tName
	 * @param fids
	 * @return json
	 */
	JSONObject recodeGather(String tName, String fids,String categoryCode,String total,String ranking);
	
	
	
	/**
	 * 
	 * @param tName
	 * @param chushiid
	 * @return
	 * @author 颜振兴
	 * int
	 * TODO 获取当前处室档案数量
	 * @param hao 
	 * @param columnValue 
	 * @param column 
	 */
	int findThisTotal(String tName, String chushiid, String column, String columnValue, String hao);
	
	/**
	 * 根据表名，查询表字段及其在页面所占宽度
	 * @author 王磊
	 * @Date 2018年12月24日 下午4:13:49
	 * @param tName
	 * @return
	 */
	List<Map<String, String>> findColumnWidth(String tName);
	
	/**
	 * 
	 *  开发人：颜振兴
	 *  时间 2018年12月24日
	 *  TODO 修改新增页面的样式
	 *  @param tName
	 *  @param list
	 *  @return
	 *  editStyle
	 */
	int editStyle(String tName,String list );
	
	
	/**
	 * 多及查询：根据门类代号、题名和立卷单位名称查询各级档案数据
	 * @author 王磊
	 * @Date 2019年2月13日 下午4:56:22
	 * @param cateNo
	 * @param mainTitle
	 * @param ljdw
	 * @param isDak 是否归档库：0不是，1是
	 * @param personType 人员类型：0录入人，1立卷单位管理员，2档案管理员
	 * @return
	 */
	List<MultiLevelQuery> multiQuery(String cateNo,String mainTitle,String ljdw,String isDak,String personType);

	/**
	 * @Author 王富康
	 * @Description //TODO 批量添加表数据
	 * @Date 2019/2/15 15:50
	 * @Param [jsonStr, tName, addCount]
	 * @return int
	 **/
	int dynamicPlAdd(String jsonStr,String tName,String addCount, String categoryCode, String fids, String all, String ranking);
	

	/**
	 * 修改录入档案人员
	 * 颜振兴
	 * @param id
	 * @param userId
	 * @param userId2 
	 * @return
	 */
	int tabUser(String tName,String id, String userId);
	
	/**
	 * 档案借阅流程审批通过后调整对应档案的借阅状态
	 * @author 王磊
	 * @Date 2019年2月16日 下午3:25:31
	 * @param recid
	 * @param tableName
	 * @return {"anjuan":案卷数,"fenjuan":分卷数,"juannei":卷内数,"flag":true或false，true表示成功}，门类层级不同，这三个可能有多种组合
	 */
	JSONObject borrowDangAnToChangeStatus(String recid,String tableName);
	
	/**
	 * 
	 * TODO 归还档案时，调整当前档案及子父表数据的借阅状态
	 * @author 王磊
	 * @Date 2019年2月18日 下午6:01:58
	 * @param recid
	 * @param tableName
	 * @return {"flag":true或者false，true表示成功}
	 */
	JSONObject returnDangAnToChangeStatus(String recid,String tableName);

	/**
	 * @Author 王富康
	 * @Description //TODO 封号
	 * @Date 2019/2/21 14:33
	 * @Param [tName, fids]
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 **/
	List<Map<String, Object>> sealUpOrRemoveSeal(String tName, String fids, String type);

	/**
	 * 
	 * 档案查询新增数据
	 * 颜振兴
	 * @param id recid
	 * @param tName 表名
	 * @param areYouOk 是否最后一层，true是，false不是
	 * @param all 当前门类共有几层
	 * @return
	 */
	Map<String, Object> adddd(String id, String tName,String ranking,String all);
	
	/**
	 * 
	 * TODO 分页查询第一层表数据
	 * @author 王磊
	 * @Date 2019年4月16日 上午10:06:18
	 * @param pageImpl
	 * @param pageable
	 * @param tableName 表名
	 * @param selectIds 勾选的一条数据的id，根绝这个id查询跟这条数据的档号，截取掉最后一位，根据档号查询相同类型的数据
	 * @param liJuanDanWei 立卷单位名称
	 * @param luRuRen 录入人
	 * @param mainTitle 题名
	 * @param dangHao 档号
	 * @return
	 */
	PageImpl getFirstLevel(PageImpl pageImpl,Pageable pageable,String tableName, String selectIds, String all, String liJuanDanWei,String luRuRen,String mainTitle,String dangHao);

	/**
	 * 
	 * TODO 
	 * @author 王磊
	 * @Date 2019年4月16日 下午6:18:35
	 * @param
	 * @param tableName 业务表表名
	 * @param recid 档案在业务表中的主键
	 * @param newDangHao 新的序号，例如项目号：00010,案卷号：0001
	 * @return
	 */
	net.sf.json.JSONObject adjustDangHao(String tableName,String recid,String newDangHao);

	/**
	 * @Author 王富康
	 * @Description //TODO 查询数量
	 * @Date 2019/4/23 11:45
	 * @Param [danghao, columnName, tName, chushiid]
	 * @return net.sf.json.JSONObject
	 **/
	int queryCountAdd(String danghao,String columnName, String tName, String chushiid, String userid, String lastColumnName, String ranking);
	
	/**
	 * 
	 * TODO 获取所有档案业务表表名
	 * @author 王磊
	 * @Date 2019年4月26日 下午5:27:59
	 * @return
	 */
	List<String> findAllTables();
	
	/**
	 * 
	 * TODO 根据字段名和表名判断某个字段是否是属于这张表
	 * @author 王磊
	 * @Date 2019年4月26日 下午5:37:29
	 * @param tableName
	 * @return 如果能找到则返回，找不到就返回null
	 */
	List<String> findColByTableName(String tableName);

	/**
	 * @Author 王富康
	 * @Description //TODO 更新父表的数量相关信息,目前只考虑三层的
	 * @Date 2019/6/3 10:56
	 * @Param fids  父级数据的id
	 * @Param tableName  子表的表名
	 * @Param all  当前门类层数
	 * @Param ranking  当前修改的是第几层的数据
	 * @Param pieceNumData  子表数据新增删除时，父表加一减一的数据（该参数为1或者-1）
	 * @Param pageNumData  子表数据新增删除，修改时，父表同时进行修改的数据（该参数为正数或者负数）
	 * @return int
	 **/
	int updatePNum(String fids, String id,String tableName,String all, String ranking,String pieceNumData,String pageNumData, String type, String pageNumAddOrDel);
	/**
	 * @Author 王富康
	 * @Description //TODO 是否能调入，防止确认调整之后导致档号跟游离的档案重复
	 * @Date 2019/6/12 9:43
	 * @Param []
	 * @return int
	 **/
	int canDiaoru(String count, String tName, String selectFRecid, String categoryCode, String recid, String all,String ranking);

	/**
	 * @Author 王富康
	 * @Description //TODO 修改文件开始时间和文件结束时间
	 * @Date 2019/7/17 17:47
	 * @Param []
	 * @return void
	 **/
	void setStratAndEndTime(String ranking,String ptableName,List<Map<String,Object>> pData);
	/**
	 * @Author 王富康
	 * @Description //TODO 根据门类code和用户查询上一条等级的数据
	 * @Date 2019/7/18 20:00
	 * @Param [categoryCode]
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 **/
	List<Map<String, Object>> findUserLastOne(String tName,String userId, String chushiId, String danghao, String columnName);

	/**
	 * 退回不归档、退回待修改
	 * @author 王磊
	 * @Date 2019年7月22日 上午10:56:23
	 * @param tName 当前勾选数据所在表
	 * @param ids 当前勾选的数据id串，逗号隔开
	 * @param entityStatus 更新后的状态
	 * @param backReason 退回原因，目前用不到，这里留作扩展
	 */
	net.sf.json.JSONObject backRecords(String tName,String ids,String entityStatus,String backReason);

	/**
	 * @Author 王富康
	 * @Description //TODO 中转库根据录入人和立卷单位和档号查询案卷号
	 * @Date 2019/7/25 15:37
	 * @Param [tName-子表表名, baseFolderUnit-立卷单位, lrrId-录入人id, folderNo-残缺的案卷级档号(没有案卷号)]
	 * @return net.sf.json.JSONObject
	 **/
	List<Map<String, Object>> getYearFolderNo(String tName, String baseFolderUnit, String lrrId, String folderNo);

	/**
	 * @Author 王富康
	 * @Description //TODO 查询父表数据
	 * @Date 2019/8/8 11:28
	 * @Param [tableName, jsonStr]
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 **/
	List<Map<String, Object>> queryPData(String tName,String jsonStr);

	/**
	 * @Author 王富康
	 * @Description //TODO 做了数据导入之后执行此方法，可填充立卷单位的code值，即各表的cre_chushi_id
	 * @Date 2019/11/11 16:35
	 * @Param []
	 * @return void
	 **/
	void synchronousUpdate();
}
