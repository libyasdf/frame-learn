package com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.services;

import java.util.List;
import com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.entity.VideoAndPdf;

/**
 * 资料对应文件的service
 * TODO 
 * @author 王磊
 * @Date 2018年8月21日 下午5:20:34
 */
public interface VideoAndPdfService {
	
	/**
	 * 保存资料对应的文件
	 * TODO 
	 * @author 王磊
	 * @Date 2018年8月21日 下午5:16:39
	 * @param videoAndPdf
	 * @return
	 */
	VideoAndPdf saveForm(VideoAndPdf videoAndPdf);
	
	/**
	 * 根据资料id查询视频或文档
	 * TODO 
	 * @author 王磊
	 * @Date 2018年8月21日 下午5:18:01
	 * @param id
	 * @return
	 */
	List<VideoAndPdf> getByInfoId(String infoId);

	/**
	 * 根据文件id逻辑删除文件
	 * TODO 
	 * @author 王磊
	 * @Date 2018年8月21日 下午5:20:01
	 * @param fileId
	 * @return
	 */
	int delete(String fileId);
}
