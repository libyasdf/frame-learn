package com.sinosoft.sinoep.message.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sinosoft.sinoep.common.constant.GlobalNames;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.message.service.NotityService;
import com.sinosoft.sinoep.user.service.UserInfoService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * TODO 消息客户端接口
 * @author 李利广
 * @Date 2018年8月23日 上午11:51:28
 */
@Service
public class MessageServiceImpl implements MessageService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private NotityService notityService;

	/**
	 * TODO 查询某用户某一时间段内的所有消息，日期可空
	 * @author 李利广
	 * @Date 2018年8月23日 上午11:54:31
	 * @param startDate	yyyy-MM-dd
	 * @param endDate yyyy-MM-dd
	 * @param userId
	 * @return
	 */
	@Override
	public List<NotityMessage> getMsgByDate(String startDate, String endDate, String userId) {
		List<NotityMessage> msgList = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select t.id,t.senderid,t.subject,t.content,t.sendtime,t.accepterid,"
				+ "t.accepttime,t.status,t.type,t.msgurl from message t where t.accepterid = '"+userId+"'");
		if (!StringUtils.isBlank(startDate)) {
			sql.append(" and t.sendtime >= to_date('"+startDate+"','yyyy-MM-dd')");
		}
		if (!StringUtils.isBlank(endDate)) {
			sql.append(" and t.sendtime < to_date('"+endDate+"','yyyy-MM-dd')");
		}
		sql.append(" order by t.sendtime desc");
		try {
			JSONObject json = userInfoService.getDateBySql(sql.toString());
			if(json.getString("flag").equals("1")){
				JSONArray msgs = json.getJSONArray("data");
				for (int i=0;i<msgs.size();i++) {
					JSONObject msg = msgs.getJSONObject(i);
					msgList.add(json2Obj(msg));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return msgList;
	}

	/**
	 * TODO 分页查询某用户一段时间内的所有消息
	 * @author 李利广
	 * @Date 2018年8月23日 上午11:57:37
	 * @param page
	 * @param startDate
	 * @param endDate
	 * @param content
	 * @param userId
	 * @return
	 */
	@Override
	public PageImpl getMsgByDateAndContent(PageImpl page, String startDate, String endDate, String content,
			String userId) {
		List<NotityMessage> msgList = new ArrayList<>();
		page.setFlag("0");
		StringBuilder sql = new StringBuilder();
		StringBuilder countSql = new StringBuilder("select count(1) total from message t ");
		StringBuilder paramSql = new StringBuilder();
		sql.append("select b.rn, b.id,b.senderid,b.subject,b.content,b.sendtime,b.accepterid,b.accepttime,b.status,b.type,b.msgurl ");
		sql.append("  from (select rownum rn, a.id,a.senderid,a.subject,a.content,a.sendtime,a.accepterid,"
				+ "a.accepttime,a.status,a.type,a.msgurl ");
		sql.append("          from (select t.id,t.senderid,t.subject,t.content,t.sendtime,t.accepterid,"
				+ "t.accepttime,t.status,t.type,t.msgurl ");
		sql.append("                  from message t ");
		
		paramSql.append("         			where t.accepterid = '"+userId+"' ");
		if (!StringUtils.isBlank(startDate)) {
			paramSql.append(" and t.sendtime >= to_date('"+startDate+"','yyyy-MM-dd')");
		}
		if (!StringUtils.isBlank(endDate)) {
			paramSql.append(" and t.sendtime < to_date('"+endDate+"','yyyy-MM-dd')");
		}
		if (!StringUtils.isBlank(content)) {
			paramSql.append(" and (t.subject like '%"+content+"%' or t.content like '%"+content+"%')");
		}
		sql.append(paramSql);
		sql.append("                 order by t.sendtime desc) a ");
		sql.append("         where rownum <= "+page.getPageNumber()*page.getPageSize()+") b ");
		sql.append(" where rn >= " + ((page.getPageNumber()-1)*page.getPageSize()+1));
		try {
			JSONObject json = userInfoService.getDateBySql(sql.toString());
			if(json.getString("flag").equals("1")){
				JSONArray msgs = json.getJSONArray("data");
				for (int i=0;i<msgs.size();i++) {
					JSONObject msg = msgs.getJSONObject(i);
					msgList.add(json2Obj(msg));
				}
			}
			countSql.append(paramSql);
			JSONObject countRes = userInfoService.getDateBySql(countSql.toString());
			Integer total = null;
			if (countRes.getString("flag").equals("1")) {
				JSONArray count = countRes.getJSONArray("data");
				total = count.getJSONObject(0).getInt("total");
			}
			page.setFlag("1");
			page.getData().setRows(msgList);
			page.getData().setTotal(total);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return page;
	}

	/**
	 * TODO 将某用户一段时间内的未读消息置为已读状态
	 * @author 李利广
	 * @Date 2018年8月23日 下午12:00:56
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @return
	 */
	@Override
	public Boolean updateStatusByDate(String startDate, String endDate, String userId) {
		Boolean result = false;
		StringBuilder sql = new StringBuilder("select t.id from message t where t.status = '"+GlobalNames.SYS_NOTIFY_STATUS_W+"' and t.accepterid = '"+userId+"'");
		if (!StringUtils.isBlank(startDate)) {
			sql.append(" and t.sendtime >= to_date('"+startDate+"','yyyy-MM-dd')");
		}
		if (!StringUtils.isBlank(endDate)) {
			sql.append(" and t.sendtime < to_date('"+endDate+"','yyyy-MM-dd')");
		}
		try {
			JSONObject res = userInfoService.getDateBySql(sql.toString());
			if(res.getString("flag").equals("1")){
				JSONArray ids = res.getJSONArray("data");
				for (int i=0;i<ids.size();i++) {
					JSONObject idJSON = ids.getJSONObject(i);
					String id = idJSON.getString("id");
					NotityMessage notity = notityService.getById(id);
					notity.setStatus(GlobalNames.SYS_NOTIFY_STATUS_Y);
					notityService.updateStatus(notity);
				}
			}
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return result;
	}

	/**
	 * TODO 查询当天该用户所有的消息
	 * @author 李利广
	 * @Date 2018年8月23日 下午12:03:16
	 * @param userId
	 * @return
	 */
	@Override
	public List<NotityMessage> getTodayMsg(String userId) {
		String today = DateUtil.getDateText(new Date(), "yyyy-MM-dd");
		List<NotityMessage> msgList = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select t.id,t.senderid,t.subject,t.content,t.sendtime,"
				+ "t.accepterid,t.accepttime,t.status,t.type,t.msgurl from message t where t.accepterid = '"+userId+"'");
		sql.append(" and to_char(t.sendtime,'yyyy-mm-dd hh24:mi:ss') like '" + today + "%'");
		sql.append(" order by t.sendtime desc");
		try {
			JSONObject json = userInfoService.getDateBySql(sql.toString());
			if(json.getString("flag").equals("1")){
				JSONArray msgs = json.getJSONArray("data");
				for (int i=0;i<msgs.size();i++) {
					JSONObject msg = msgs.getJSONObject(i);
					msgList.add(json2Obj(msg));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return msgList;
	}
	
	private NotityMessage json2Obj(JSONObject msg){
		NotityMessage message = new NotityMessage();
		try {
			message.setId(msg.getString("id"));
			message.setAccepterId(msg.getString("accepterid"));
			JSONObject acceptTime = msg.getJSONObject("accepttime");
			if (!acceptTime.isEmpty() && !acceptTime.equals("null")) {
				message.setAcceptTime(new Date(acceptTime.getLong("time")));
			}
			JSONObject sendTime = msg.getJSONObject("sendtime");
			if (!sendTime.isEmpty() && !sendTime.equals("null")) {
				message.setSendTime(new Date(sendTime.getLong("time")));
			}
			message.setContent(msg.getString("content"));
			message.setMsgUrl(msg.getString("msgurl"));
			message.setSenderId(msg.getString("senderid"));
			message.setStatus(msg.getString("status"));
			message.setSubject(msg.getString("subject"));
			message.setType(msg.getString("type"));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return message;
	}

	@Override
	public JSONObject historyCount(String startDate, String endDate, String userId) {
		StringBuilder sql = new StringBuilder("select count(1) as num from message t where t.status = '"+GlobalNames.SYS_NOTIFY_STATUS_W+"' and t.accepterid = '"+userId+"'");
		if (!StringUtils.isBlank(startDate)) {
			sql.append(" and t.sendtime >= to_date('"+startDate+"','yyyy-MM-dd')");
		}
		if (!StringUtils.isBlank(endDate)) {
			sql.append(" and t.sendtime < to_date('"+endDate+"','yyyy-MM-dd')");
		}
			JSONObject res = userInfoService.getDateBySql(sql.toString());
		return res;
	}

	@Override
	public PageImpl getIsRead(PageImpl page, String status, String userId) {
		List<NotityMessage> msgList = new ArrayList<>();
		
		String Sql = "select   t.id,t.senderid,t.subject,t.content,t.sendtime,t.accepterid,t.accepttime,t.status,t.timestamp,t.msgurl,t.type from message t where  t.status  ='"+status+"' and t.accepterid = '"+userId+"' order by t.sendtime desc ";
		if (page.getPageNumber()!=null&&page.getPageSize()!=null) {
			Sql = "select * from (select rownum rn,b.* from  ("+Sql+") b where rownum <="+page.getPageNumber()*page.getPageSize()+") where rn>="+((page.getPageNumber()-1)*page.getPageSize()+1)+" ";
		}
		JSONObject countRes = userInfoService.getDateBySql(Sql);
		
		Integer total = null;
		if (countRes.getString("flag").equals("1")) {
			//JSONArray count = countRes.getJSONArray("data");
			//total = count.getJSONObject(0).getInt("total");
			JSONArray msgs = countRes.getJSONArray("data");
			for (int i=0;i<msgs.size();i++) {
				JSONObject msg = msgs.getJSONObject(i);
				msgList.add(json2Obj(msg));
			}
		}
		page.setFlag("1");
		page.getData().setRows(msgList);
		page.getData().setTotal(0);
		return page;
	}

	@Override
	public JSONObject sumTotal(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
