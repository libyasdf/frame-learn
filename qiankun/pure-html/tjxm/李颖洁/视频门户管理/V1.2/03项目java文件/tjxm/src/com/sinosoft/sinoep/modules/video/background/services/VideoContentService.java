package com.sinosoft.sinoep.modules.video.background.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.video.background.entity.BannerImgVo;
import com.sinosoft.sinoep.modules.video.background.entity.VideoColumn;
import com.sinosoft.sinoep.modules.video.background.entity.VVideoAndPdf;
import com.sinosoft.sinoep.modules.video.background.entity.VideoContent;
import com.sinosoft.sinoep.modules.video.background.entity.VideoInfo;

import net.sf.json.JSONObject;


public interface VideoContentService {

    public PageImpl getContentPageList(Pageable pageable, PageImpl pageImpl, String columnId, String title, String creTime);
    
    public PageImpl getFbPageList(Pageable pageable, PageImpl pageImpl, String columnId, String title, String creTime);
    
    public VideoContent saveFrom(VideoContent entity);

    public VideoContent getVideoContentById(String id);

    public int delete(String ids);

    public void updateSort(String ids);

    public JSONObject queryQx(String columnId,String contentId);

    public PageImpl getSpList(Pageable pageable,PageImpl pageImpl,String columnName,String title);

	public PageImpl getContentByColumn(PageImpl page, String columnCode, VideoContent info) throws Exception;

	public List<BannerImgVo> getImgsByColumn(String columnCode) throws Exception;

    public int updateZdByConetenIds(String contentIds,String isZd);

    public List<VideoContent> getZdList(String columnId);

    public int orderZd(String ids);
    
    public VVideoAndPdf saveForm(VVideoAndPdf videoAndPdf);
    
    /**
     * 根据栏目id获取首页内容
     * TODO 
     * @author 马秋霞
     * @Date 2018年11月16日 上午9:52:08
     * @param columnId
     * @return
     */
	public Map<String, Object> getHomePageContent(PageImpl page,String columnId,String flag,String columnName) throws Exception;

	public List<VideoContent> getContentByColumnId(String columnId, String title) throws Exception;
	
	/**
	 * 获取某个内容下的视频信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年12月3日 下午2:42:54
	 * @param contentId
	 * @return
	 */
	public List<VideoInfo> getVideoByContentId(String contentId,String fileName);
	
	/**
	 * 根据id修改imageid
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年12月12日 上午10:15:35
	 * @param id
	 * @param imageid
	 */
	public void updateImageId(String id, String imageid);

    

}
