package com.sinosoft.sinoep.modules.info.authority.services;

import com.sinosoft.sinoep.modules.info.authority.entity.ColumnModel;
import com.sinosoft.sinoep.modules.info.authority.entity.InfoGenarals;
import com.sinosoft.sinoep.modules.info.authority.entity.InfoModel;

import java.util.List;

/**
 * TODO 信息发布职务权限业务接口
 * @author 李利广
 * @Date 2018年9月15日 下午5:38:02
 */
public interface InfoFbZwqxService {

	/**
	 * 保存权限
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月16日 上午9:45:46
	 * @param model
	 * @return
	 */
	InfoModel saveInfoZwqx(InfoModel model);

	/**
	 * 查询一条信息的权限，用于数据回显
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月16日 下午4:36:10
	 * @param contentId
	 * @return
	 */
	InfoModel getAuthority(String contentId);

	/**
	 * 保存栏目发布范围
	 * @param model
	 * @return
	 */
	ColumnModel saveColumnFbfw(ColumnModel model);

	/**
	 * 获取栏目发布范围
	 * @param columnId
	 * @return
	 */
	ColumnModel getColumnAuthority(String columnId);


	InfoGenarals saveGenarals(String contentId, String genarals);

    List<InfoGenarals> getGenarals(String contentId);
}
