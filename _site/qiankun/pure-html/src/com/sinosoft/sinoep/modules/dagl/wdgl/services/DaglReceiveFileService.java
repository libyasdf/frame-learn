package com.sinosoft.sinoep.modules.dagl.wdgl.services;

import java.util.List;

import com.sinosoft.sinoep.modules.dagl.wdgl.entity.DaglConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dagl.wdgl.entity.DaglReceiveFile;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.ContrastingRelations;

/**
 * TODO 文电管理业务层
 * @author 李颖洁  
 * @date 2018年11月10日  下午6:09:22
 */
public interface DaglReceiveFileService {

	/**
	 *  TODO 保存文件信息
	 * @author 李颖洁  
	 * @date 2018年11月12日上午10:37:50
	 * @param receiveFile
	 * @return DaglReceiveFile
	 */
	DaglReceiveFile editReceiveFile(DaglReceiveFile receiveFile);

	/** 
	 * TODO 修改文件信息
	 * @author 李颖洁  
	 * @date 2018年11月12日上午11:11:44
	 * @param receiveFile
	 * @return DaglReceiveFile
	 */
	DaglReceiveFile getOne(DaglReceiveFile receiveFile);

	/** 
	 * TODO 删除单个文件 
	 * @author 李颖洁  
	 * @date 2018年11月12日上午11:27:02
	 * @param receiveFile
	 * @return int
	 */
	int deleteOne(DaglReceiveFile receiveFile);

	/** 
	 * TODO 批量删除文件
	 * @author 李颖洁  
	 * @date 2018年11月12日上午11:57:49
	 * @param list
	 * @return int
	 */
	int batchDelete(String ids);

	/** 
	 * TODO 获取文件列表 ，带分页
	 * @author 李颖洁  
	 * @date 2018年11月12日下午2:12:51
	 * @param receiveFile
	 * @param pageImpl
	 * @param pageable 
	 * @return Page<DaglReceiveFile>
	 */
	Page<DaglReceiveFile> list(DaglReceiveFile receiveFile, PageImpl pageImpl, Pageable pageable);

	/** 
	 * TODO 导入数据
	 * @author 李颖洁  
	 * @date 2018年11月13日下午4:10:18
	 * @param filePath
	 * @param file
	 * @return String
	 */
	String importInfo(String filePath, MultipartFile fileToUpload);

	/** 
	 * TODO 文件归档操作
	 * @author 李颖洁  
	 * @date 2018年11月16日下午2:13:02
	 * @param id
	 * @param ids
	 * @return void
	 * @throws ClassNotFoundException 
	 * @throws Exception 
	 */
	int tidyRecode(ContrastingRelations relations, String ids, String jsonStr,String fileNumberRule, String form) throws Exception;

	/** 
	 * TODO 获取归档数据列表
	 * @author 李颖洁  
	 * @date 2018年11月19日下午2:48:52
	 * @param ids
	 * @return List<DaglReceiveFile>
	 */
	List<DaglReceiveFile> getTidyRecodedList(String ids);

	/**
	 *  TODO 更改数据状态
	 * @author 李颖洁  
	 * @date 2018年11月19日下午6:13:24
	 * @param ids
	 * @return void
	 */
	int updateState(String ids,String status);

	/**
	 *  TODO 扫码枪配置
	 * @author 张浩磊
	 * @date 2018年11月12日上午10:37:50
	 * @param
	 * @return
	 */
	DaglConfig saveConfig(DaglConfig daglConfig);

	DaglConfig getConfig(DaglConfig daglConfig);

	/**
	 * TODO 获取打印数据
	 * @author 张浩磊
	 * @date 2018年11月20日上午11:27:01
	 * @param ids
	 * @return int
	 */
	List<DaglReceiveFile> getDaglReceiveFileList(DaglReceiveFile daglReceiveFile,String ids);

	List<DaglReceiveFile> getDestoryList(String year);

	/** 
	 * TODO 根据勾选的数据批量修改数据
	 * @author 李颖洁  
	 * @date 2018年12月18日下午4:58:32
	 * @param ids
	 * @return int
	 */
	int batchUpdate(String ids,String type);

	/**
	 * TODO 获取文件列表 ，不带分页
	 * @author 李颖洁  
	 * @date 2018年12月18日下午5:37:19
	 * @param receiveFile
	 * @return List<DaglReceiveFile>
	 */
	List<DaglReceiveFile> getList(DaglReceiveFile receiveFile);
	
	/**
	 * 
	 * TODO 档案进行删除时，更新对应收文的状态为：已退回待处置
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
	List<DaglReceiveFile> getTransferRepositoryList(DaglReceiveFile receiveFile);

	/**
	 * @Author 王富康
	 * @Description //TODO 中转库退回文件管理
	 * @Date 2019/7/27 16:18
	 * @Param [lrrId, data]
	 * @return net.sf.json.JSONObject
	 **/
	int returnBack( String data);
}
