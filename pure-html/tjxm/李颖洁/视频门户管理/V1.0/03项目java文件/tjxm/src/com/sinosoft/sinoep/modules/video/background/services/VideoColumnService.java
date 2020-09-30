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

    public void updatetreeSort(String[] ids,String dropId,String superId,String nodeLevel);

    public PageImpl getColumnPageList(Pageable pageable,PageImpl pageImpl,String superId,String columnName,String columnCode);

    public VideoColumn saveFroms(VideoColumn entity,String isFirst);

    public VideoColumn getInfoClumnById(String id);

    public int delete(String ids);

    public int deleteItme(String ids);

	public List<VideoColumn> getAllColumn(String columnCode) throws Exception;

	public VideoColumn getColumnByCode(String columnCode) throws Exception;

	public List<VideoColumn> getHomePageColumns();
	
	public boolean checkDic(String columnCold,String id);
	
}
