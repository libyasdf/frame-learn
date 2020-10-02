package com.sinosoft.sinoep.modules.video.authority.services;

import com.sinosoft.sinoep.modules.video.authority.entity.VideoModel;
import com.sinosoft.sinoep.modules.video.authority.entity.ProgramaModel;

/**
 * TODO 视频发布职务权限业务接口
 * @author 郝灵涛
 * @Date 2018年9月15日 下午5:38:02
 */
public interface ContentFbZwqxService {

	/**
	 * 保存权限
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月16日 上午9:45:46
	 * @param model
	 * @return
	 */
	VideoModel saveInfoZwqx(VideoModel model);

	/**
	 * 查询一条信息的权限，用于数据回显
	 * TODO 
	 * @author 郝灵涛
	 * @Date 2018年9月16日 下午4:36:10
	 * @param contentId
	 * @return
	 */
	VideoModel getAuthority(String contentId);

	/**
	 * 保存栏目发布范围
	 * @param model
	 * @return
	 */
	ProgramaModel saveColumnFbfw(ProgramaModel model);

	/**
	 * 获取栏目发布范围
	 * @param columnId
	 * @return
	 */
	ProgramaModel getColumnAuthority(String columnId);



}
