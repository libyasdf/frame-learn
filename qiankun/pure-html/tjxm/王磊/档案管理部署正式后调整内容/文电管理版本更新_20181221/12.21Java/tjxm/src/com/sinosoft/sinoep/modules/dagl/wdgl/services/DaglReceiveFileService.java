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
	int tidyRecode(ContrastingRelations relations, String ids) throws Exception;

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

}
