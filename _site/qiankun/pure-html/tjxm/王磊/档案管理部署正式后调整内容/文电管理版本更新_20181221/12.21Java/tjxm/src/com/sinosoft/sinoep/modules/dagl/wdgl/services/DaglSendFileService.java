package com.sinosoft.sinoep.modules.dagl.wdgl.services;

import java.util.List;
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
	int tidyRecode(ContrastingRelations relations, String ids) throws Exception;

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
}
