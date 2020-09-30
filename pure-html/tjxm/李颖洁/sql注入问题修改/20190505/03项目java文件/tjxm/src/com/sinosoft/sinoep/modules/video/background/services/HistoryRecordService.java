package com.sinosoft.sinoep.modules.video.background.services;

import java.util.List;

import com.sinosoft.sinoep.modules.video.background.entity.HistoryRecord;

public interface HistoryRecordService {
	
	//保存历史查看记录
	HistoryRecord save(String contentId,String videoId);
	
	/**
     *显示最近观看的前5条历史记录
     */
	List<HistoryRecord> getLastestHistoryRecord();
	
	/**
	 * 视频播放完成后更新状态
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年12月3日 上午10:47:42
	 * @param id
	 */
	void updateState(String historyId);

   
}
