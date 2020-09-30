package com.sinosoft.sinoep.modules.video.background.services;

import java.util.List;
import com.sinosoft.sinoep.modules.video.background.entity.VVideoAndPdf;

/**
 * 资料对应文件的service
 * TODO 
 * @author 郝玲涛
 * @Date 2018年8月21日 下午5:20:34
 */
public interface VVideoAndPdfService {
	
	/**
	 * 保存资料对应的文件
	 * TODO 
	 * @author 郝玲涛
	 * @Date 2018年8月21日 下午5:16:39
	 * @param videoAndPdf
	 * @return
	 */
	VVideoAndPdf saveForm(VVideoAndPdf videoAndPdf);
	
	/**
	 * 根据资料id查询视频或文档
	 * TODO 
	 * @author 郝玲涛
	 * @Date 2018年8月21日 下午5:18:01
	 * @param id
	 * @return
	 */
	List<VVideoAndPdf> getByInfoId(String infoId);

	/**
	 * 根据文件id逻辑删除文件
	 * TODO 
	 * @author 郝玲涛
	 * @Date 2018年8月21日 下午5:20:01
	 * @param fileId
	 * @return
	 */
	int delete(String fileId);
}
