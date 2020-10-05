package com.sinosoft.sinoep.modules.info.xxfb.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumn;
import net.sf.json.JSONObject;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface InfoColumnService {

    /**
     * 根据当前登录人获取信息发布栏目树数据
     * @param userId 当前登录人
     * @return
     */
    public JSONObject getColumnTree(String columnName,String userId);

    public void updatetreeSort(String[] ids,String dropId,String superId,String nodeLevel);

    public PageImpl getColumnPageList(Pageable pageable,PageImpl pageImpl,String superId,String columnName,String columnCode);

    public InfoColumn saveFroms(InfoColumn entity);

    public InfoColumn getInfoClumnById(String id);

    public int delete(String ids);

    public int deleteItme(String ids);

	public List<InfoColumn> getAllColumn(String columnCode) throws Exception;

	public InfoColumn getColumnByCode(String columnCode) throws Exception;

    public boolean checkDic(String columnCold,String id);
	
}
