package com.sinosoft.sinoep.modules.system.notice.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoSpEntity;
import com.sinosoft.sinoep.modules.system.notice.dao.SysNoticeVerifyUserDao;
import com.sinosoft.sinoep.modules.system.notice.entity.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.info.authority.common.util.InfoAuthorityUtils;
import com.sinosoft.sinoep.modules.system.notice.common.SysNoticeConstants;
import com.sinosoft.sinoep.modules.system.notice.common.util.NoticeUtils;
import com.sinosoft.sinoep.modules.system.notice.dao.SysNoticeDao;
import com.sinosoft.sinoep.user.util.UserUtil;

import javax.persistence.TypedQuery;

/**
 * TODO 通知通告业务实现类
 * @author 李利广
 * @Date 2018年8月29日 下午2:54:01
 */
@Service
public class SysNoticeServiceImpl implements SysNoticeService {
	
	@Autowired
	private SysNoticeDao noticeDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private SysNoticeVerifyService sysNoticeVerifyService;
	
	/**
	 * TODO 保存通知通告
	 * @author 李利广
	 * @Date 2018年8月17日 下午2:29:06
	 * @param notice
	 * @return
	 */
	@Override
	public SysNotice saveNotice(SysNotice notice) throws Exception {
		if(StringUtils.isNotBlank(notice.getId())){
			SysNotice oldNotice = noticeDao.findOne(notice.getId());
			oldNotice.setContent(notice.getContent());
			oldNotice.setEndTime(notice.getEndTime());
			oldNotice.setNoticeType(notice.getNoticeType());
			oldNotice.setStartTime(notice.getStartTime());
			oldNotice.setTitle(notice.getTitle());
			oldNotice.setSource(notice.getSource());
			oldNotice.setStatus(notice.getStatus());
			oldNotice.setIsBack(notice.getIsBack());
			oldNotice.setUpdateUserId(UserUtil.getCruUserId());
			oldNotice.setUpdateUserName(UserUtil.getCruUserName());
			oldNotice.setUpdateTime(DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss"));
			notice = noticeDao.save(oldNotice);
		}else{
			notice.setCreUserId(UserUtil.getCruUserId());
			notice.setCreUserName(UserUtil.getCruUserName());
			notice.setCreDeptId(UserUtil.getCruDeptId());
			notice.setCreDeptName(UserUtil.getCruDeptName());
			notice.setCreChushiId(UserUtil.getCruChushiId());
			notice.setCreChushiName(UserUtil.getCruChushiName());
			notice.setCreJuId(UserUtil.getCruJuId());
			notice.setCreJuName(UserUtil.getCruJuName());
			notice.setCreTime(DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss"));
			notice.setOrderNo(NoticeUtils.getSort(notice).toString());
			notice.setSubflag(SysNoticeConstants.FLAG[0]);
			notice.setIsTop(SysNoticeConstants.IS_TOP[0]);
			notice.setVisible(SysNoticeConstants.VISIBLE[1]);
			notice = noticeDao.save(notice);
		}
		return notice;
	}
	
	/**
	 * TODO 更新通知通告状态位
	 * @author 李利广
	 * @Date 2018年8月17日 下午2:30:44
	 * @param id
	 * @param flag
	 * @return
	 */
	@Override
	public SysNotice updateFlag(String id, String flag) throws Exception {
		SysNotice notice = noticeDao.findOne(id);
		notice.setSubflag(flag);
		if (flag.equals(SysNoticeConstants.FLAG[1])) {
			notice.setPublishTime(DateUtil.getDateText(new Date(), "yyyy-MM-dd"));
		}
		notice = noticeDao.save(notice);
		return notice;
	}

	/**
	 * TODO 查询当前用户收到的通知通告（分页）
	 * @author 李利广
	 * @Date 2018年8月17日 下午2:46:49
	 * @param pageImpl
	 * @param notice
	 * @return
	 */
	@Override
	public PageImpl getNoticeList(PageImpl pageImpl, SysNotice notice,String startTime,String endTime) throws Exception {
		String nowTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd");
		Integer pageNumber = pageImpl.getPageNumber();
		Integer pageSize = pageImpl.getPageSize();
		if (pageNumber == null) {
			pageNumber = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		Pageable pageable = new PageRequest(pageNumber-1, pageSize);
		List<String> authList = InfoAuthorityUtils.getAuthList();
		if (!authList.isEmpty()) {
			StringBuilder contentIds = new StringBuilder();
			String contentIdStr = "";
			for (String contentId : authList) {
				contentIds.append("'" + contentId + "',");
			}
			if (contentIds.length() > 0) {
				contentIdStr = contentIds.substring(0, contentIds.length() - 1);
			}
			StringBuilder hql = new StringBuilder("from SysNotice n where n.visible = '" + SysNoticeConstants.VISIBLE[1] + "'");
			hql.append(" and (( n.startTime <= '" + nowTime + "' and n.endTime >= '" + nowTime + "')");
			hql.append(" or n.startTime is null)");
			hql.append(" and n.id in (" + contentIdStr + ")");
			hql.append(" and n.subflag = '" + SysNoticeConstants.FLAG[3] + "'");
			if (StringUtils.isNotBlank(notice.getContent())) {
				hql.append(" and n.content like '%" + notice.getContent() + "%'");
			}
			if (StringUtils.isNotBlank(notice.getTitle())) {
				hql.append(" and n.title like '%" + notice.getTitle() + "%'");
			}
			if (StringUtils.isNotBlank(startTime)) {
				hql.append(" and n.publishTime >= '" + startTime + "'");
			}
			if (StringUtils.isNotBlank(endTime)) {
				hql.append(" and n.publishTime <= '" + endTime + "'");
			}
			if (StringUtils.isNotBlank(notice.getNoticeType())) {
				hql.append(" and n.noticeType = '" + notice.getNoticeType() + "'");
				hql.append(" order by n.isTop desc, n.orderNo+0 desc");
			} else {
				hql.append(" order by n.isTop desc, n.noticeType, n.orderNo+0 desc");
			}
			Page<SysNotice> query = noticeDao.query(hql.toString(), pageable);
			pageImpl.getData().setRows(query.getContent());
			pageImpl.getData().setTotal((int) query.getTotalElements());
		}
		return pageImpl;
	}
	
	/**
	 * TODO 查询通知通告维护列表(部门)（当前用户查看自己维护的数据）
	 * @author 李利广
	 * @Date 2018年8月17日 下午2:46:49
	 * @param pageImpl
	 * @param notice
	 * @return
	 */
	public PageImpl getAllNoticeList(PageImpl pageImpl,SysNotice notice,String startTime,String endTime) throws Exception{
		Integer pageNumber = pageImpl.getPageNumber();
		Integer pageSize = pageImpl.getPageSize();
		if (pageNumber == null) {
			pageNumber = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		Pageable pageable = new PageRequest(pageNumber-1, pageSize);
		StringBuilder sBuilder = new StringBuilder("from SysNotice t where t.visible = '"+SysNoticeConstants.VISIBLE[1]+"' ");
		sBuilder.append(" and t.creUserId = '"+UserUtil.getCruUserId()+"'");
		sBuilder.append(" and t.noticeDeptId = 1");
		if (StringUtils.isNotBlank(notice.getContent())) {
			sBuilder.append(" and t.content like '%"+notice.getContent()+"%'");
		}
		if (StringUtils.isNotBlank(notice.getTitle())) {
			sBuilder.append(" and t.title like '%"+notice.getTitle()+"%'");
		}
		if (StringUtils.isNotBlank(notice.getSubflag())) {
			sBuilder.append(" and t.subflag = '"+notice.getSubflag()+"'");
		}
		if (StringUtils.isNotBlank(notice.getIsBack())) {
			sBuilder.append(" and t.isBack = '"+notice.getIsBack()+"'");
		}
		if (StringUtils.isNotBlank(startTime)) {
			sBuilder.append(" and t.publishTime >= '"+startTime+"'");
		}
		if (StringUtils.isNotBlank(endTime)) {
			sBuilder.append(" and t.publishTime <= '"+endTime+"'");
		}
		if (StringUtils.isNotBlank(pageImpl.getSortName())) {
			sBuilder.append("  order by t.isTop desc, t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder());
		}else{
			if (StringUtils.isNotBlank(notice.getNoticeType())) {
				sBuilder.append(" and t.noticeType = '"+notice.getNoticeType()+"'");
				sBuilder.append(" order by t.isTop desc, t.orderNo+0 desc");
			}else{
				sBuilder.append(" order by t.isTop desc, t.noticeType, t.orderNo+0 desc");
			}
		}
		Page<SysNotice> query = noticeDao.query(sBuilder.toString(), pageable);
		List<SysNotice> content = query.getContent();
		for (SysNotice sysNotice : content) {
			/*if (SysNoticeConstants.FLAG[0].equals(sysNotice.getFlag())) {
				//草稿	发布、修改、删除
			//	sysNotice.setCz(CommonConstants.OPTION_PUBLISH);
			}else if (SysNoticeConstants.FLAG[1].equals(sysNotice.getFlag())) {
				//已发布	撤销、修改、删除
			//	sysNotice.setCz(CommonConstants.OPTION_CANCEL);
			}else if (SysNoticeConstants.FLAG[2].equals(sysNotice.getFlag())) {
				//已撤销	发布、修改、删除
			//	sysNotice.setCz(CommonConstants.OPTION_PUBLISH);
			}*/
			if (SysNoticeConstants.IS_BACK[1].equals(sysNotice.getIsBack())) {
				sysNotice.setCz(CommonConstants.OPTION_SEE_BACK);
				if(!SysNoticeConstants.FLAG[1].equals(sysNotice.getSubflag())){
					sysNotice.setCz(CommonConstants.OPTION_SEE_BACK+","+CommonConstants.OPTION_DELETE);
				}
			}else{
				if (!SysNoticeConstants.FLAG[1].equals(sysNotice.getSubflag())) {
					sysNotice.setCz(CommonConstants.OPTION_DELETE);
					if(SysNoticeConstants.IS_BACK[1].equals(sysNotice.getIsBack())){
						sysNotice.setCz(CommonConstants.OPTION_SEE_BACK+","+CommonConstants.OPTION_DELETE);
					}
				}
			}
		}
		pageImpl.getData().setRows(content);
		pageImpl.getData().setTotal((int)query.getTotalElements());
		return pageImpl;
	}

	@Override
	public PageImpl getAllNoticeList1(PageImpl pageImpl, SysNotice notice, String startTime, String endTime) throws Exception {
		Integer pageNumber = pageImpl.getPageNumber();
		Integer pageSize = pageImpl.getPageSize();
		if (pageNumber == null) {
			pageNumber = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		Pageable pageable = new PageRequest(pageNumber-1, pageSize);
		StringBuilder sBuilder = new StringBuilder("from SysNotice t where t.visible = '"+SysNoticeConstants.VISIBLE[1]+"' ");
		sBuilder.append(" and t.creUserId = '"+UserUtil.getCruUserId()+"'");
		sBuilder.append(" and t.noticeDeptId = 0");
		if (StringUtils.isNotBlank(notice.getContent())) {
			sBuilder.append(" and t.content like '%"+notice.getContent()+"%'");
		}
		if (StringUtils.isNotBlank(notice.getTitle())) {
			sBuilder.append(" and t.title like '%"+notice.getTitle()+"%'");
		}
		if (StringUtils.isNotBlank(notice.getSubflag())) {
			sBuilder.append(" and t.subflag = '"+notice.getSubflag()+"'");
		}
		if (StringUtils.isNotBlank(notice.getIsBack())) {
			sBuilder.append(" and t.isBack = '"+notice.getIsBack()+"'");
		}
		if (StringUtils.isNotBlank(startTime)) {
			sBuilder.append(" and t.publishTime >= '"+startTime+"'");
		}
		if (StringUtils.isNotBlank(endTime)) {
			sBuilder.append(" and t.publishTime <= '"+endTime+"'");
		}
		if (StringUtils.isNotBlank(pageImpl.getSortName())) {
			sBuilder.append("  order by t.isTop desc, t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder());
		}else{
			if (StringUtils.isNotBlank(notice.getNoticeType())) {
				sBuilder.append(" and t.noticeType = '"+notice.getNoticeType()+"'");
				sBuilder.append(" order by t.isTop desc, t.orderNo+0 desc");
			}else{
				sBuilder.append(" order by t.isTop desc, t.noticeType, t.orderNo+0 desc");
			}
		}
		Page<SysNotice> query = noticeDao.query(sBuilder.toString(), pageable);
		List<SysNotice> content = query.getContent();
		for (SysNotice sysNotice : content) {
			/*if (SysNoticeConstants.FLAG[0].equals(sysNotice.getFlag())) {
				//草稿	发布、修改、删除
			//	sysNotice.setCz(CommonConstants.OPTION_PUBLISH);
			}else if (SysNoticeConstants.FLAG[1].equals(sysNotice.getFlag())) {
				//已发布	撤销、修改、删除
			//	sysNotice.setCz(CommonConstants.OPTION_CANCEL);
			}else if (SysNoticeConstants.FLAG[2].equals(sysNotice.getFlag())) {
				//已撤销	发布、修改、删除
			//	sysNotice.setCz(CommonConstants.OPTION_PUBLISH);
			}*/
			if (SysNoticeConstants.IS_BACK[1].equals(sysNotice.getIsBack())) {
				sysNotice.setCz(CommonConstants.OPTION_SEE_BACK);
				if(!SysNoticeConstants.FLAG[1].equals(sysNotice.getSubflag())){
					sysNotice.setCz(CommonConstants.OPTION_SEE_BACK+","+CommonConstants.OPTION_DELETE);
				}
			}else{
				if (!SysNoticeConstants.FLAG[1].equals(sysNotice.getSubflag())) {
					sysNotice.setCz(CommonConstants.OPTION_DELETE);
					if(SysNoticeConstants.IS_BACK[1].equals(sysNotice.getIsBack())){
						sysNotice.setCz(CommonConstants.OPTION_SEE_BACK+","+CommonConstants.OPTION_DELETE);
					}
				}
			}
		}
		pageImpl.getData().setRows(content);
		pageImpl.getData().setTotal((int)query.getTotalElements());
		return pageImpl;
	}

	@Override
	public PageImpl getAllNoticeList2(PageImpl pageImpl, SysNotice notice, String startTime, String endTime) throws Exception {
		Integer pageNumber = pageImpl.getPageNumber();
		Integer pageSize = pageImpl.getPageSize();
		if (pageNumber == null) {
			pageNumber = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		Pageable pageable = new PageRequest(pageNumber-1, pageSize);
		StringBuilder sBuilder = new StringBuilder("from SysNotice t where t.visible = '"+SysNoticeConstants.VISIBLE[1]+"' ");
		sBuilder.append(" and t.noticeDeptId = 0");
		sBuilder.append(" and t.subflag = 5");
		if (StringUtils.isNotBlank(notice.getContent())) {
			sBuilder.append(" and t.content like '%"+notice.getContent()+"%'");
		}
		if (StringUtils.isNotBlank(notice.getTitle())) {
			sBuilder.append(" and t.title like '%"+notice.getTitle()+"%'");
		}
		if (StringUtils.isNotBlank(notice.getIsBack())) {
			sBuilder.append(" and t.isBack = '"+notice.getIsBack()+"'");
		}
		if (StringUtils.isNotBlank(startTime)) {
			sBuilder.append(" and t.publishTime >= '"+startTime+"'");
		}
		if (StringUtils.isNotBlank(endTime)) {
			sBuilder.append(" and t.publishTime <= '"+endTime+"'");
		}
		if (StringUtils.isNotBlank(pageImpl.getSortName())) {
			sBuilder.append("  order by t.isTop desc, t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder());
		}else{
			if (StringUtils.isNotBlank(notice.getNoticeType())) {
				sBuilder.append(" and t.noticeType = '"+notice.getNoticeType()+"'");
				sBuilder.append(" order by t.isTop desc, t.orderNo+0 desc");
			}else{
				sBuilder.append(" order by t.isTop desc, t.noticeType, t.orderNo+0 desc");
			}
		}
		Page<SysNotice> query = noticeDao.query(sBuilder.toString(), pageable);
		List<SysNotice> content = query.getContent();
		for (SysNotice sysNotice : content) {
			/*if (SysNoticeConstants.FLAG[0].equals(sysNotice.getFlag())) {
				//草稿	发布、修改、删除
			//	sysNotice.setCz(CommonConstants.OPTION_PUBLISH);
			}else if (SysNoticeConstants.FLAG[1].equals(sysNotice.getFlag())) {
				//已发布	撤销、修改、删除
			//	sysNotice.setCz(CommonConstants.OPTION_CANCEL);
			}else if (SysNoticeConstants.FLAG[2].equals(sysNotice.getFlag())) {
				//已撤销	发布、修改、删除
			//	sysNotice.setCz(CommonConstants.OPTION_PUBLISH);
			}*/
			if (SysNoticeConstants.IS_BACK[1].equals(sysNotice.getIsBack())) {
				sysNotice.setCz(CommonConstants.OPTION_SEE_BACK);
			}
		}
		pageImpl.getData().setRows(content);
		pageImpl.getData().setTotal((int)query.getTotalElements());
		return pageImpl;
	}

	/**
	 * TODO 删除通知公告
	 * @author 李利广
	 * @Date 2018年9月5日 下午4:47:41
	 * @param noticeId
	 * @return
	 */
	@Override
	public Boolean delNotice(String noticeId) throws Exception{
		StringBuilder sql = new StringBuilder();
		int count = 0;
		sql.append("update SysNotice t set t.visible = '"+SysNoticeConstants.VISIBLE[0]+"' where t.id = '"+noticeId+"'");
		count = noticeDao.update(sql.toString());
		return count == 0?false:true;
	}
	
	/**
	 * TODO 获取一条数据
	 * @author 李利广
	 * @Date 2018年9月5日 下午5:05:48
	 * @param noticeId
	 * @return
	 */
	public SysNotice getView(String noticeId) throws Exception{
		return noticeDao.findOne(noticeId);
	}
	
	/**
	 * TODO 通知置顶
	 * @author 李利广
	 * @Date 2018年9月10日 上午11:18:01
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public Boolean setTop(String ids, Boolean upDown) throws Exception{
		String hql = "";
		String idStr = CommonUtils.commomSplit(ids);
		if (upDown) {	//置顶
			hql = "update SysNotice t set t.isTop = '"+SysNoticeConstants.IS_TOP[1]+"' where t.id in ("+idStr+")";
		}else{
			hql = "update SysNotice t set t.isTop = '"+SysNoticeConstants.IS_TOP[0]+"' where t.id in ("+idStr+")";
		}
		int count = noticeDao.update(hql);
		return count == 0?false:true;
	}

	/**
	 * 置顶排序
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean sort(String[] ids) throws Exception{
		if (ids.length != 0){
			for (int i = ids.length-1; i >=0 ;i--){
				SysNotice sysNotice = noticeDao.findOne(ids[i]);
				String noticeNo = NoticeUtils.getSort(sysNotice).toString();
				String hql = "update SysNotice t set t.orderNo = '"+noticeNo+"' where t.id = '"+ids[i]+"'";
				noticeDao.update(hql);
			}
			return true;
		}
		return false;
	}

	/**
	 * 查询门户信息提醒
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageImpl getInfoNoticeList(PageImpl page) throws Exception{
		String nowTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd");
		Integer pageNumber = page.getPageNumber();
		Integer pageSize = page.getPageSize();
		if (pageNumber == null) {
			pageNumber = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		StringBuilder sql = new StringBuilder();
		//获取权限范围
		StringBuilder contentIds = new StringBuilder();
		String contentIdStr = "'',";
		List<String> authList = InfoAuthorityUtils.getAuthList();
		if (!authList.isEmpty()) {
			for (String contentId : authList) {
				contentIds.append("'" + contentId + "',");
			}
		}
		contentIdStr += contentIds.toString();
		contentIdStr = contentIdStr.substring(0, contentIdStr.length() - 1);
		sql.append("select * from (");
		sql.append("		select a.*,rownum rn");
		sql.append("		  from (select r.* from (");
		//获取通知公告
		if (StringUtils.isNotBlank(contentIdStr)) {
			sql.append("select t.id,t.title,t.cre_time time,t.is_Top isTop,t.order_No orderNo,'2' type,'' columnCode,'通知公告' columnName from sys_notice t where t.visible = '" + SysNoticeConstants.VISIBLE[1] + "'");
			sql.append(" and (( t.start_time <= '" + nowTime + "' and t.end_time >= '" + nowTime + "')");
			sql.append(" or t.start_time is null)");
			sql.append(" and t.id in (" + contentIdStr + ")");
			sql.append(" and t.subflag = '" + SysNoticeConstants.FLAG[3] + "'");
		}
		sql.append(" ) r");
		sql.append(" order by r.isTop+0 desc nulls last,r.orderNo+0 desc nulls last,r.time desc) a");
		sql.append(" where rownum <= " + (pageSize * pageNumber) + ")");
		sql.append(" where rn >= " + ((pageNumber-1) * pageSize+1));
		List<InfoNoticeVo> query = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<InfoNoticeVo>(InfoNoticeVo.class));
		page.getData().setRows(query);
		page.getData().setTotal(query.size());
		return page;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 查询公文发布最新发布消息
	 * @Date 2019/10/10 9:25
	 * @Param [page]
	 * @return com.sinosoft.sinoep.common.util.PageImpl
	 **/
	@Override
	public PageImpl getInfoNoticeDocumentsList(PageImpl page) throws Exception{
		String nowTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd");
		Integer pageNumber = page.getPageNumber();
		Integer pageSize = page.getPageSize();
		if (pageNumber == null) {
			pageNumber = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		StringBuilder sql = new StringBuilder();
		//获取权限范围
		StringBuilder contentIds = new StringBuilder();
		String contentIdStr = "'',";
		List<String> authList = InfoAuthorityUtils.getAuthList();
		if (!authList.isEmpty()) {
			for (String contentId : authList) {
				contentIds.append("'" + contentId + "',");
			}
		}
		contentIdStr += contentIds.toString();
		contentIdStr = contentIdStr.substring(0, contentIdStr.length() - 1);
		sql.append("select * from (");
		sql.append("		select a.*,rownum rn");
		sql.append("		  from (select r.* from (");
		sql.append("select t.id, t.title, t.cre_time time,t.is_zd isTop,'' orderNo, '1' type, i.column_code columnCode,i.column_name columnName");
		sql.append("  from info_content t, info_column i");
		sql.append(" where t.column_id = i.id");
		sql.append("  and t.visible = '1'");
		sql.append("   and t.subflag = '5'");
		sql.append("  and ( t.is_fb_content = '0'");
		sql.append("   or t.is_fb_content = '1'");
		sql.append("  and t.id in (" + contentIdStr + "))) r");
		sql.append(" order by r.isTop+0 desc nulls last,r.orderNo+0 desc nulls last,r.time desc) a");
		sql.append(" where rownum <= " + (pageSize * pageNumber) + ")");
		sql.append(" where rn >= " + ((pageNumber-1) * pageSize+1));
		List<InfoNoticeVo> query = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<InfoNoticeVo>(InfoNoticeVo.class));
		page.getData().setRows(query);
		page.getData().setTotal(query.size());
		return page;
	}

	@Override
	public List<SysNotice> getZhiDingList() {
		List<SysNotice> list = new ArrayList<SysNotice>();
		StringBuilder sql = new StringBuilder("from SysNotice t where t.visible = '1' and t.creUserId = '"+UserUtil.getCruUserId()+"' and t.isTop ='1' order by orderNo ASC");
		TypedQuery<SysNotice> notices = noticeDao.getEntityManager().createQuery(sql.toString(), SysNotice.class);
		list = notices.getResultList();
		return list;
	}

	@Override
	public JSONObject queryNotice() {
		JSONObject json = new JSONObject();
		json.put("fbButton","0");//发布
		json.put("sendButton","0");//发送
		json.put("passButton","0");//通过
		json.put("noPassButton","0");//不通过
		json.put("isFbfw",'0');
		String userNoticeId = UserUtil.getCruUserId();
		SysNoticeVerifyUser sysNoticeVerifyUser = sysNoticeVerifyService.findByFbUserIdAndVisible(userNoticeId,"1");
		SysNoticeVerifyUser noticeShUser = sysNoticeVerifyService.findByShUserIdAndVisible(userNoticeId,"1");
		//判断是否需要有发送按钮
		if(sysNoticeVerifyUser.getFbUserId()!=null){
			if(sysNoticeVerifyUser.getFbUserId().equals(userNoticeId)){
				json.put("sendButton","1");//发送
				json.put("isFbfw",'1');
			}
		}
		//判断是否审核人
		if(noticeShUser.getShUserId()!=null){
			if(noticeShUser.getShUserId().equals(userNoticeId)){
				json.put("passButton","1");//通过
				json.put("noPassButton","1");//不通过
				json.put("isFbfw",'0');
			}
		}
		//判断是否需要审批
		if(sysNoticeVerifyUser.getFbUserId() ==null && noticeShUser.getShUserId()==null){
			json.put("fbButton","1");//发布
			json.put("isFbfw",'1');
		}
		return json;
	}

	@Override
	public PageImpl getSpList(Pageable pageable, PageImpl pageImpl, String subflag, String title) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select c.id, c.title, w.draft_time,w.draft_user_name, c.flag,w.id work_itme_id from sys_notice c, sys_waitdo_noflow w " +
				" where  c.id = w.source_id and c.visible = '1' and w.visible ='1'");
		if(StringUtils.isNotBlank(UserUtil.getCruUserId()) ){
			sql.append(" and w.receive_user_id ='").append(UserUtil.getCruUserId()).append("'");
		}
		if(StringUtils.isNotBlank(title)){
			sql.append(" and c.title like '%").append(title).append("%' ");
		}
		if (StringUtils.isNotBlank(subflag)) {
			sql.append(" and c.subflag = '"+subflag+"'");
		}
		sql.append(" order by w.draft_time desc ");
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql.toString());
		List<NoticeSpEntity> data = new ArrayList<NoticeSpEntity>();
		for(Map<String,Object> map :list){
			NoticeSpEntity entity = new NoticeSpEntity();
			entity.setId((String)map.get("id"));
			entity.setTitle((String)map.get("title"));
			entity.setDraftUserName((String)map.get("draft_user_name"));
			entity.setDraftTime((String)map.get("draft_time"));
			entity.setSubflag((String)map.get("flag"));
			entity.setWorkItemId((String)map.get("work_itme_id"));
			if (entity.getSubflag().equals("3")) {
				entity.setCz("1");
			}else{
				entity.setCz("0");
			}
			data.add(entity);
		}
		List<NoticeSpEntity> list2 = new ArrayList<NoticeSpEntity>();
		if(pageable.getPageSize()*(pageable.getPageNumber() + 1)<list.size()){
			int startNumber = pageable.getPageSize() * pageable.getPageNumber();
			int endNumber = pageable.getPageSize() * (pageable.getPageNumber() + 1);
			list2 = data.subList(startNumber,endNumber);
		}else{
			list2 = data;
		}
		pageImpl = new PageImpl(list.size(),list2);
		return pageImpl;
	}

}
