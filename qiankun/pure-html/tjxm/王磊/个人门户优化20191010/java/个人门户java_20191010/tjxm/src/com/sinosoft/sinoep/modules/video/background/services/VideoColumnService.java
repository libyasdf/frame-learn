package com.sinosoft.sinoep.modules.video.background.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.video.background.entity.VideoColumn;
import net.sf.json.JSONObject;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface VideoColumnService {

    /**
     * 根据当前登录人获取信息发布栏目树数据
     * @param userId 当前登录人
     * @return
     */
    public JSONObject getColumnTree(String columnName,String userId);
    
    public JSONObject getContentColumnTree(String columnName,String userId);

    public void updatetreeSort(String[] ids,String dropId,String superId,String nodeLevel);

    public PageImpl getColumnPageList(Pageable pageable,PageImpl pageImpl,String superId,String columnName,String columnCode,String isShow);

    public VideoColumn saveFroms(VideoColumn entity,String isFirst);

    public VideoColumn getInfoClumnById(String id);

    public int delete(String ids);

    public int deleteItme(String ids);

	public List<VideoColumn> getAllColumn(String columnCode) throws Exception;

	public VideoColumn getColumnByCode(String columnCode) throws Exception;

	public List<VideoColumn> getHomePageColumns();
	
	public boolean checkDic(String columnCold,String id);

	/**
	 * 根据栏目id查询栏目信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年1月25日 上午11:54:30
	 * @param columnId
	 * @return
	 */
	public VideoColumn getById(String columnId);
	
	/**
	 * 栏目的排序列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年1月28日 下午2:46:31
	 * @param superId
	 * @return
	 */
	public List<VideoColumn> getZdList(String superId);
	
	
	
	/**
	 * 栏目的拖拽排序
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年1月28日 下午3:42:32
	 * @param ids
	 * @return
	 */
	public int orderZd(String ids);
	

	
}
