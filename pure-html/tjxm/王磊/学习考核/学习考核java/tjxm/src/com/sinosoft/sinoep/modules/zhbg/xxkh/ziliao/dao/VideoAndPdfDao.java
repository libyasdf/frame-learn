package com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.entity.VideoAndPdf;

/**
 * 资料对应文件的dao层
 * TODO 
 * @author 王磊
 * @Date 2018年8月21日 下午5:13:55
 */
public interface VideoAndPdfDao extends BaseRepository<VideoAndPdf,String> {
	
	/**
	 * 根据资料id查找某一资料对应的视频和文档
	 * TODO 
	 * @author 王磊
	 * @Date 2018年8月21日 下午5:12:10
	 * @param infoId 资料id
	 * @return
	 */
	@Query("select u from VideoAndPdf u where u.visible='1' and u.infoId=?1 order by u.creTime asc")
	public List<VideoAndPdf> getFilesByInfoId(String infoId);
}
