package com.sinosoft.sinoep.modules.video.background.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.video.background.entity.VVideoAndPdf;

/**
 * 资料对应文件的dao层
 * TODO 
 * @author 郝灵涛
 * @Date 2018年8月21日 下午5:13:55
 */
public interface VVideoAndPdfDao extends BaseRepository<VVideoAndPdf,String> {
	
	/**
	 * 根据资料id查找某一资料对应的视频和文档
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年8月21日 下午5:12:10
	 * @param infoId 资料id
	 * @return
	 */
	@Query("select u from VVideoAndPdf u where u.visible='1' and u.infoId=?1 order by u.creTime asc")
	public List<VVideoAndPdf> getFilesByInfoId(String infoId);
}
