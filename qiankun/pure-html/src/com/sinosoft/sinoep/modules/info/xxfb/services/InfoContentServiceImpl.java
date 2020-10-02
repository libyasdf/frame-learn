package com.sinosoft.sinoep.modules.info.xxfb.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sinosoft.sinoep.modules.system.notice.common.SysNoticeConstants;
import com.sinosoft.sinoep.modules.system.notice.common.util.NoticeUtils;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNotice;
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
import com.sinosoft.sinoep.modules.info.xxfb.common.InfoConstants;
import com.sinosoft.sinoep.modules.info.xxfb.common.util.InfoUtils;
import com.sinosoft.sinoep.modules.info.xxfb.dao.InfoColumnDao;
import com.sinosoft.sinoep.modules.info.xxfb.dao.InfoContentDao;
import com.sinosoft.sinoep.modules.info.xxfb.entity.BannerImgVo;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumn;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumnFbUser;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoContent;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoSpEntity;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;

import net.sf.json.JSONObject;

import javax.persistence.TypedQuery;

@Service
public class InfoContentServiceImpl implements InfoContentService {

    @Autowired
    private InfoContentDao dao;

    @Autowired
    private InfoColumnFbUserService fbUserService;

    @Autowired
    private InfoColumnDao columnDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public PageImpl getContentPageList(Pageable pageable, PageImpl pageImpl, String columnId, String title, String creTime) {
        StringBuilder sql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        sql.append(" from InfoContent t where t.visible='1'");
        if(StringUtils.isNotBlank(columnId)){
            sql.append(" and t.columnId  = '").append(columnId).append("'");
        }else{
            sql.append(" and t.columnId ='").append(InfoConstants.XXFBONEID).append("'");
        }
        if(StringUtils.isNotBlank(title)){
            sql.append(" and t.title like '%").append(title).append("%'");
        }
        if(StringUtils.isNotBlank(creTime)){
            sql.append(" and t.creTime like '%").append(creTime).append("%'");
        }
        sql.append(" order by t.isZd+0,t.creTime desc ");
        try{
            Page<InfoContent> page = dao.query(sql.toString(),pageable,para.toArray());
            List<InfoContent> list = page.getContent();
            pageImpl.setFlag("1");
            pageImpl.getData().setRows(list);
            pageImpl.getData().setTotal((int)page.getTotalElements());
        }catch (Exception e){
            e.printStackTrace();
            pageImpl.setFlag("0");
        }
        return pageImpl;
    }


    @Override
    public PageImpl getContentPageList1(Pageable pageable, PageImpl pageImpl, String columnId, String title, String creTime) {
        StringBuilder sql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        sql.append(" from InfoContent t where t.visible='1' and t.subflag ='5'");
        if(StringUtils.isNotBlank(columnId)){
            sql.append(" and t.columnId  = '").append(columnId).append("'");
        }else{
            sql.append(" and t.columnId ='").append(InfoConstants.XXFBONEID).append("'");
        }
        if(StringUtils.isNotBlank(title)){
            sql.append(" and t.title like '%").append(title).append("%'");
        }
        if(StringUtils.isNotBlank(creTime)){
            sql.append(" and t.creTime like '%").append(creTime).append("%'");
        }
        sql.append(" order by t.isZd+0,t.creTime desc ");
        try{
            Page<InfoContent> page = dao.query(sql.toString(),pageable,para.toArray());
            List<InfoContent> list = page.getContent();
            pageImpl.setFlag("1");
            pageImpl.getData().setRows(list);
            pageImpl.getData().setTotal((int)page.getTotalElements());
        }catch (Exception e){
            e.printStackTrace();
            pageImpl.setFlag("0");
        }
        return pageImpl;
    }

    @Override
    public InfoContent saveFrom(InfoContent entity) {
        InfoContent entityDB = new InfoContent();
        try{
            if(StringUtils.isBlank(entity.getId())){
                entity.setVisible("1");
                entity.setCreUserId(UserUtil.getCruUserId());
                entity.setCreUserName(UserUtil.getCruUserName());
                entity.setCreChushiId(UserUtil.getCruChushiId());
                entity.setCreChushiId(UserUtil.getCruChushiId());
                entity.setCreChushiName(UserUtil.getCruChushiName());
                entity.setCreJuId(UserUtil.getCruJuId());
                entity.setCreJuName(UserUtil.getCruJuName());
                entity.setCreTime(JDateToolkit.getNowDate1());
                entity.setSubflag(InfoConstants.SUBFLAG[0]);
                entityDB = dao.save(entity);
            }else {
                entityDB = dao.findOne(entity.getId());
                entityDB.setUpdateUserId(UserUtil.getCruUserId());
                entityDB.setUpdateUserName(UserUtil.getCruUserName());
                entityDB.setUpdateTime(JDateToolkit.getNowDate1());
                entityDB.setTitle(entity.getTitle());
                entityDB.setSubtitle(entity.getSubtitle());
                entityDB.setAuthor(entity.getAuthor());
                entityDB.setShowStartTime(entity.getShowStartTime());
                entityDB.setShowEndTime(entity.getShowEndTime());
                entityDB.setContent(entity.getContent());
                entityDB.setSource(entity.getSource());
                entityDB.setImageId(entity.getImageId());
                entityDB.setSubflag(entity.getSubflag());
                entityDB.setIsZd(entity.getIsZd());
                entityDB.setColumnId(entity.getColumnId());
                entityDB.setFbfw(entity.getFbfw());
                entityDB.setFbTime(entity.getFbTime());
                entityDB.setIsFBContent(entity.getIsFBContent());
                entityDB.setSubflag(InfoConstants.SUBFLAG[0]);
                entityDB.setDeptId(entity.getDeptId());
                entityDB = dao.save(entityDB);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return entityDB;
    }

    @Override
    public InfoContent getInfoContentById(String id) {
        InfoContent entity = new InfoContent();
        if(StringUtils.isNotBlank(id)){
            try{
                entity = dao.findOne(id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return entity;
    }

    @Override
    public int delete(String ids) {
        int n = 0;
        try{
            if(StringUtils.isNotBlank(ids)){
                String[] idsArray = ids.split(",");
                for (String id :idsArray){
                    n = dao.delVisible(id);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return n;
    }

    @Override
    public void updateSort(String ids) {
        if(StringUtils.isNotBlank(ids)){
           String[] idsArray = ids.split(",");
           for(int n = 0;n<idsArray.length;n++){
               InfoContent entity = new InfoContent();
               entity = dao.findOne(idsArray[n]);
               /*entity.set*/
           }
        }
    }

    @Override
    public JSONObject queryQx(String columnId, String contentId) {
        JSONObject json = new JSONObject();
        json.put("fbButton","0");//发布
        json.put("sendButton","0");//发送
        json.put("passButton","0");//通过
        json.put("noPassButton","0");//不通过
        json.put("isFbfw",'0');
        if(StringUtils.isNotBlank(columnId)){
            InfoColumn columnEntity = columnDao.findOne(columnId);//栏目信息
            InfoContent contentEntity = dao.findOne(contentId);//内容信息
            json.put("isFbfw",columnEntity.getIsFbfw());
            if(contentEntity !=null) {
                json.put("isFbfw", contentEntity.getIsFBContent());
            }
            if(InfoConstants.ISSP[0].equals(columnEntity.getIsSp())){//如果这个栏目不需要审批则显示发布按钮
                json.put("fbButton","1");//发布
            }else{
                InfoColumnFbUser fbUserEntity = fbUserService.getEntity(columnId,UserUtil.getCruUserId());//发布人信息
                if(StringUtils.isNotBlank(contentId)) {//如果内容id不为空并且是发布人
                    if (InfoConstants.SUBFLAG[0].equals(contentEntity.getSubflag()) && StringUtils.isNotBlank(fbUserEntity.getId())) {//草稿
                        json.put("sendButton", "1");//发送
                    } else if (InfoConstants.SUBFLAG[1].equals(contentEntity.getSubflag())) {//流程中
                        if (StringUtils.isNotBlank(fbUserEntity.getId())) {//当前登录用户是拟稿人时不显示发送按钮（避免多次发送）
                            json.put("sendButton", "0");    //发送
                        }
                        json.put("passButton", "1");//通过
                        json.put("noPassButton", "1");//不通过
                    } else if (InfoConstants.SUBFLAG[2].equals(contentEntity.getSubflag())) {//已发布
                        json.put("fbButton", "1");//发布
                    } else if (InfoConstants.SUBFLAG[3].equals(contentEntity.getSubflag())) {//退回
                        json.put("sendButton", "1");    //发送
                    }
                }else if(StringUtils.isNotBlank(fbUserEntity.getId())){
                    json.put("sendButton", "1");//发送
                }
            }
        }
        return json;
    }

    @Override
    public PageImpl getSpList(Pageable pageable, PageImpl pageImpl, String columnName, String title) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select c.id, c.title, w.draft_time,l.id column_id, l.column_name, w.draft_user_name, c.subflag,w.id work_itme_id from info_content c, info_column l, sys_waitdo_noflow w " +
                " where c.column_id = l.id and c.id = w.source_id and c.visible = '1' and w.visible = '1'");
        if(StringUtils.isNotBlank(UserUtil.getCruUserId()) ){
            sql.append(" and w.receive_user_id ='").append(UserUtil.getCruUserId()).append("'");
        }
        if(StringUtils.isNotBlank(columnName)){
            sql.append(" and l.column_name like '%").append(columnName).append("%'");
        }
        if(StringUtils.isNotBlank(title)){
            sql.append(" and c.title like '%").append(title).append("%' ");
        }
        sql.append(" order by w.draft_time desc ");
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql.toString());
        List<InfoSpEntity> data = new ArrayList<InfoSpEntity>();
        for(Map<String,Object> map :list){
            InfoSpEntity entity = new InfoSpEntity();
            entity.setId((String)map.get("id"));
            entity.setColumnName((String)map.get("column_name"));
            entity.setTitle((String)map.get("title"));
            entity.setDraftUserName((String)map.get("draft_user_name"));
            entity.setDraftTime((String)map.get("draft_time"));
            entity.setSubflag((String)map.get("subflag"));
            entity.setColumnId((String)map.get("column_id"));
            entity.setWorkItemId((String)map.get("work_Item_id"));
            data.add(entity);
        }
        List<InfoSpEntity> list2 = new ArrayList<InfoSpEntity>();
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

    /**
     * 获取当前用户收到的信息(分页)
     * TODO 
     * @author 李利广
     * @Date 2018年9月18日 下午9:11:13
     * @param page
     * @param columnCode
     * @return
     */
	@Override
	public PageImpl getContentByColumn(PageImpl page, String columnCode, InfoContent info) throws Exception {
		String nowTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd");
		Integer pageNumber = page.getPageNumber();
		Integer pageSize = page.getPageSize();
		if (pageNumber == null) {
			pageNumber = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		Pageable pageable = new PageRequest(pageNumber-1, pageSize);
		//获取该栏目是否有发布范围
		StringBuilder contentIds = new StringBuilder();
		String contentIdStr = "'',";
		List<String> authList = InfoAuthorityUtils.getAuthList();
		if (!authList.isEmpty()) {
			for (String contentId : authList) {
				contentIds.append("'" + contentId + "',");
			}
		}
		if(contentIds!=null && StringUtils.isNotBlank(contentIds.toString())){
            contentIdStr = contentIds.substring(0, contentIds.length() - 1);
        }else {
            contentIdStr = "''";
        }
		StringBuilder hql = new StringBuilder("select new com.sinosoft.sinoep.modules.info.xxfb.entity.InfoContent(t.id, t.creUserId, t.creUserName, t.creDeptId,t.creDeptName,"
		+ "t.visible, t.creTime, t.title, t.subtitle, t.author, t.showStartTime,t.showEndTime, t.content, t.source, t.imageId, t.subflag, t.isZd,"
		+ "t.columnId, t.fbTime ,c.columnCode,t.infoTitle,t.deptId,t.deptName,t.status) ");
		hql.append("from InfoContent t,InfoColumn c where t.columnId = c.id and t.visible = '1' and t.subflag = '"+InfoConstants.SUBFLAG[2]+"'");
		hql.append(" and (( t.showStartTime <= '" + nowTime + "' and t.showEndTime >= '" + nowTime + "')");
		hql.append(" or t.showStartTime is null)");
		if(StringUtils.isNotBlank(columnCode)){
			hql.append(" and c.columnCode  = '").append(columnCode).append("'");
        }
		//如果有发布范围，查询发布范围内的数据
		hql.append(" and (t.isFBContent = '0' or (t.isFBContent = '1' and t.id in ("+contentIdStr+")))");
		if(StringUtils.isNotBlank(info.getTitle())){
        	hql.append(" and t.title like '%").append(info.getTitle()).append("%'");
        }
        if(StringUtils.isNotBlank(info.getCreTime())){
        	hql.append(" and t.creTime ='").append(info.getCreTime()).append("'");
        }
        hql.append(" order by t.isZd+0 desc nulls last,t.fbTime desc ");
        Page<InfoContent> query = dao.query(hql.toString(), pageable);
        page.getData().setRows(query.getContent());
		page.getData().setTotal((int) query.getTotalElements());
		return page;
	}

	/**
     * 获取轮播图
     * TODO 
     * @author 李利广
     * @Date 2018年9月19日 下午2:49:47
     * @param columnCode
     * @return
     */
	@Override
	public List<BannerImgVo> getImgsByColumn(String columnCode) throws Exception{
		String nowTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd");
		List<BannerImgVo> query = new ArrayList<>();
		//获取该栏目是否有发布范围
		StringBuilder contentIds = new StringBuilder();
		String contentIdStr = "'',";
		List<String> authList = InfoAuthorityUtils.getAuthList();
		if (!authList.isEmpty()) {
			for (String contentId : authList) {
				contentIds.append("'" + contentId + "',");
			}
		}
		contentIdStr = contentIds.substring(0, contentIds.length() - 1);
		StringBuilder sBuilder = new StringBuilder("select i.id infoId,i.title,i.subTitle,t.save_path imgPath");
		sBuilder.append(" from sys_affix t, info_content i, info_column c ");
		sBuilder.append(" where c.column_code = '"+columnCode+"' and i.visible = '1' and i.subflag = '"+InfoConstants.SUBFLAG[2]+"' and i.column_id = c.id and t.table_id = i.id ");
		sBuilder.append(" and (( i.show_start_time <= '" + nowTime + "' and i.show_end_time >= '" + nowTime + "')");
		sBuilder.append(" or i.show_start_time is null)");
		sBuilder.append(" and t.save_path is not null");
		//如果有发布范围，查询发布范围内的数据
		sBuilder.append(" and (i.is_FB_Content = '0' or (i.is_FB_Content = '1' and i.id in ("+contentIdStr+")))");
		sBuilder.append(" order by i.is_zd+0 desc nulls last,i.FB_TIME desc");
		query = jdbcTemplate.query(sBuilder.toString(), new BeanPropertyRowMapper<>(BannerImgVo.class));
		return query;
	}


    @Override
    public int updateZdByConetenIds(String contentIds, String isZd) {
        int n = 0;
        try{
            if(StringUtils.isNotBlank(contentIds)){
                String [] contentIdsArray = contentIds.split(",");
                for(String id:contentIdsArray){
                    dao.updateZdById(isZd,id);
                }
                n = 1;
            }
        }catch (Exception e){
            e.printStackTrace();
            n = 0;
        }
        return n;
    }

    @Override
    public List<InfoContent> getZdList(String columnId) {
        List<InfoContent> list = new ArrayList<InfoContent>();
        StringBuilder sql = new StringBuilder("from InfoContent t where t.visible = '1' and t.columnId = '"+columnId+"' and t.isZd != null order by t.isZd DESC");
        TypedQuery<InfoContent> infoContentTypedQuery = dao.getEntityManager().createQuery(sql.toString(), InfoContent.class);
        list = infoContentTypedQuery.getResultList();
        return list;
    }

    @Override
    public int orderZd(String ids) {
        int n = 0;
        try {
            if (StringUtils.isNotBlank(ids)) {
                String[] idsArray = ids.split(",");
                for(int m = idsArray.length-1;m >= 0; m--){
                    String iszd = String.valueOf(m+1);
                    dao.updateZdById(iszd,idsArray[(idsArray.length-1)-m]);
                }
                n = 1;
            }
        }catch (Exception e){
            e.printStackTrace();
            n = -1;
        }
        return n;
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
    public InfoContent updateFlag(String id, String flag) throws Exception {
        InfoContent infoContent = dao.findOne(id);
        infoContent.setSubflag(flag);
        if (flag.equals(SysNoticeConstants.FLAG[3])) {
            infoContent.setFbTime(DateUtil.getDateText(new Date(), "yyyy-MM-dd"));
        }
        infoContent = dao.save(infoContent);
        return infoContent;
    }

}
