package com.sinosoft.sinoep.modules.video.background.services;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.modules.video.background.dao.HistoryRecordDao;
import com.sinosoft.sinoep.modules.video.background.entity.HistoryRecord;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * 查看视频的历史记录
 * TODO 
 * @author 马秋霞
 * @Date 2018年12月3日 下午8:39:18
 */
@Service
public class HistoryRecordServiceImpl implements HistoryRecordService{

    @Autowired
    private HistoryRecordDao dao;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 历史记录添加
     * TODO 
     * @author 马秋霞
     * @Date 2018年12月3日 下午8:39:35
     * @param contentId
     * @param videoId
     * @return
     */
	@Override
	public HistoryRecord save(String contentId,String videoId) {
		HistoryRecord record = new HistoryRecord();
		String nowTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
		record.setUserId(UserUtil.getCruUserId());
		record.setUserName(UserUtil.getCruUserName());
		record.setDeptId(UserUtil.getCruDeptId());
		record.setDeptName(UserUtil.getCruDeptName());
		record.setChushiId(UserUtil.getCruChushiId());
		record.setChushiName(UserUtil.getCruChushiName());
		record.setJuId(UserUtil.getCruJuId());
		record.setJuName(UserUtil.getCruJuId());
		record.setContentId(contentId);
		record.setVideoId(videoId);
		record.setCreTime(nowTime);
		record.setState("0");
		//Long second = Math.round(surplusTime);
		//String minuteSecond = second/60 + "分" + second%60 + "秒";
		//record.setSurplusTime(second);
		//record.setSurplusMinute(minuteSecond);
		record=dao.save(record);
		return record;
	}
	
	/**
	 * 查看最近的5条历史记录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年12月3日 下午8:39:50
	 * @return
	 */
	@Override
	public List<HistoryRecord> getLastestHistoryRecord() {
		List<HistoryRecord> query = new ArrayList<>();
		StringBuilder sql = new StringBuilder(" select * from ( ");
		sql.append("  select t1.id contentId,t1.title contentTitle,t3.file_name fileName,t3.id videoId,t3.file_id uuid,case when t3.visible='1' and t1.visible='1' then '1' else '0' end visible,t.cre_time creatime from history_record t  ");
		sql.append(" left outer join (  ");
		sql.append("  select t.id, t.title, case when t.visible='1' and t1.visible='1' then '1' else '0' end visible from VIDEO_CONTENT t left outer join video_column t1 on t.column_id=t1.id  ");
		sql.append(" ) t1 on t.content_id=t1.id ");
		sql.append("  left outer join (select t.id,t.file_id,t.file_name,t.visible from video_video_and_pdf t) t3 on t.video_id=t3.id ");
		sql.append(" where t.user_id ='" + UserUtil.getCruUserId() + "'  order by t.cre_time desc");
		sql.append(" )where rownum <=5 ");
		query=jdbcTemplate.query(sql.toString(),new RowMapper<HistoryRecord>(){
			@Override
			public HistoryRecord mapRow(ResultSet result, int arg1) throws SQLException {
				HistoryRecord record = new HistoryRecord();
				record.setVideoId(result.getString("videoid"));
				record.setContentId(result.getString("contentid"));
				record.setContentTitle(result.getString("contenttitle"));
				record.setUuid(result.getString("uuid"));
				record.setFileName(result.getString("filename"));
				record.setCreTime(result.getString("creatime"));
				record.setVisible(result.getString("visible"));
				return record;
			}
			
		});
		return query;
	}
	
	/**
	 * 视频播放完成后更新状态
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年12月3日 上午10:47:42
	 * @param id
	 */
	@Override
	public void updateState(String historyId) {
		String sql="update history_record set state = '1' where id='" + historyId + "'";
		jdbcTemplate.execute(sql);
	}

    
}
