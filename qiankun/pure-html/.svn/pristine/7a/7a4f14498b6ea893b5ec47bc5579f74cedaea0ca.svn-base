package com.sinosoft.sinoep.modules.dagl.wdgl.services;

import java.util.List;

import net.sf.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dagl.wdgl.entity.DaglReceiveFile;
import com.sinosoft.sinoep.modules.dagl.wdgl.entity.DaglSendFile;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.ContrastingRelations;

/**
 * TODO 文电管理发文业务层（发文）
 * @author 李颖洁  
 * @date 2018年11月20日  上午11:37:27
 */
public interface DaglSendFileService {

	/**
	 * TODO 保存文件信息(发文)
	 * @author 李颖洁  
	 * @date 2018年11月20日上午11:24:43
	 * @param sendFile
	 * @return DaglSendFile
	 */
	DaglSendFile editSendFile(DaglSendFile sendFile);

	/**
	 * TODO 修改文件信息(发文)
	 * @author 李颖洁  
	 * @date 2018年11月20日上午11:25:09
	 * @param sendFile
	 * @return DaglSendFile
	 */
	DaglSendFile getOne(DaglSendFile sendFile);

	/**
	 * TODO 删除单个文件 (发文)
	 * @author 李颖洁  
	 * @date 2018年11月20日上午11:25:31
	 * @param sendFile
	 * @return int
	 */
	int deleteOne(DaglSendFile sendFile);

	/**
	 * TODO 批量删除文件(发文)
	 * @author 李颖洁  
	 * @date 2018年11月20日上午11:25:49
	 * @param ids
	 * @return int
	 */
	int batchDelete(String ids);

	/**
	 * TODO 获取文件列表 ，带分页(发文)
	 * @author 李颖洁  
	 * @date 2018年11月20日上午11:25:58
	 * @param sendFile
	 * @param pageImpl
	 * @param pageable
	 * @return Page<DaglSendFile>
	 */
	Page<DaglSendFile> list(DaglSendFile sendFile, PageImpl pageImpl, Pageable pageable);

	/**
	 * TODO  导入数据(发文)
	 * @author 李颖洁  
	 * @date 2018年11月20日上午11:26:29
	 * @param filePath
	 * @param fileToUpload
	 * @return String
	 */
	String importInfo(String filePath, MultipartFile fileToUpload);

	/**
	 * TODO 文件归档操作(发文)
	 * @author 李颖洁  
	 * @date 2018年11月20日上午11:26:41
	 * @param relations
	 * @param ids
	 * @throws Exception
	 * @return int
	 */
	int tidyRecode(ContrastingRelations relations, String ids, String jsonStr,String fileNumberRule, String form) throws Exception;

	/**
	 * TODO 获取归档数据列表
	 * @author 李颖洁  
	 * @date 2018年11月20日上午11:26:51
	 * @param ids
	 * @return List<DaglSendFile>
	 */
	List<DaglSendFile> getTidyRecodedList(String ids);

	/**
	 * TODO 更改数据状态(发文)
	 * @author 李颖洁  
	 * @date 2018年11月20日上午11:27:01
	 * @param ids
	 * @return int
	 */
	int updateState(String ids,String status);

	/**
	 * TODO 获取打印数据
	 * @author 张浩磊
	 * @date 2018年11月20日上午11:27:01
	 * @param ids
	 * @return int
	 */
	List<DaglSendFile> getDaglSendFileList(DaglSendFile daglSendFile,String ids);

	/**
	 * TODO 获取销毁数据
	 * @author 张浩磊
	 * @date 2018年11月20日上午11:27:01
	 * @param ids
	 * @return int
	 */
	List<DaglSendFile> getDestoryList(String year);

	/** 
	 * TODO 根据勾选的数据批量修改数据
	 * @author 李颖洁  
	 * @date 2018年12月18日下午4:58:32
	 * @param ids
	 * @param type 要修改的类型
	 * @return int
	 */
	int batchUpdate(String ids,String type);

	/**
	 * TODO 获取文件列表 ，不带分页
	 * @author 李颖洁  
	 * @date 2018年12月18日下午5:37:19
	 * @param receiveFile
	 * @return List<DaglSendFile>
	 */
	List<DaglSendFile> getList(DaglSendFile sendFile);
	
	/**
	 * 
	 * TODO 档案进行删除时，更新对应发文的状态为：已退回待处置
	 * @author 王磊
	 * @Date 2019年4月11日 下午5:24:52
	 * @param tName
	 * @param creUserId
	 * @param column
	 * @param columnValue
	 * @return
	 */
	boolean updateStateByIdAndState(String tName,String creUserId,String column, String columnValue);

	/**
	 * @Author 王富康
	 * @Description //TODO 文件管理归档操作，加入录入人id
	 * @Date 2019/7/20 14:20
	 * @Param [ids]
	 * @return net.sf.json.JSONObject
	 **/
	int placeOnFile(String lrrId, String data);

	/**
	 * @Author 王富康
	 * @Description //TODO 获取发文中转库数据
	 * @Date 2019/7/22 14:07
	 * @Param [daglSendFile]
	 * @return net.sf.json.JSONObject
	 **/
	List<DaglSendFile> getTransferRepositoryList(DaglSendFile daglSendFile);

	/**
	 * @Author 王富康
	 * @Description //TODO 选择对照关系之后根据表名获取档号规则，回填页面表单样式
	 * @Date 2019/7/22 17:09
	 * @Param [tableName]
	 * @return net.sf.json.JSONObject
	 **/
	JSONObject getRuleFrom(String tableName);

	/**
	 * @Author 王富康
	 * @Description //TODO 中转库退回文件管理
	 * @Date 2019/7/27 16:18
	 * @Param [lrrId, data]
	 * @return net.sf.json.JSONObject
	 **/
	int returnBack( String data);
}
