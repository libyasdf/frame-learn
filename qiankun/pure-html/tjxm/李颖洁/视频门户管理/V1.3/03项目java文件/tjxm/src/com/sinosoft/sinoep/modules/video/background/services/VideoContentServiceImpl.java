package com.sinosoft.sinoep.modules.video.background.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.bulenkov.iconloader.util.StringUtil;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.video.authority.common.util.VideoAuthorityUtils;
import com.sinosoft.sinoep.modules.video.background.common.VideoConstants;
import com.sinosoft.sinoep.modules.video.background.dao.VVideoAndPdfDao;
import com.sinosoft.sinoep.modules.video.background.dao.VideoColumnDao;
import com.sinosoft.sinoep.modules.video.background.dao.VideoColumnFbUserDao;
import com.sinosoft.sinoep.modules.video.background.dao.VideoContentDao;
import com.sinosoft.sinoep.modules.video.background.entity.BannerImgVo;
import com.sinosoft.sinoep.modules.video.background.entity.VVideoAndPdf;
import com.sinosoft.sinoep.modules.video.background.entity.VideoColumn;
import com.sinosoft.sinoep.modules.video.background.entity.VideoColumnFbUser;
import com.sinosoft.sinoep.modules.video.background.entity.VideoContent;
import com.sinosoft.sinoep.modules.video.background.entity.VideoInfo;
import com.sinosoft.sinoep.modules.video.background.entity.VideoSpEntity;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;
import net.sf.json.JSONObject;

@Service
public class VideoContentServiceImpl implements VideoContentService {

    @Autowired
    private VideoContentDao dao;
    
    @Autowired
    private VVideoAndPdfDao vdao;

    @Autowired
    private VideoColumnFbUserService fbUserService;

    @Autowired
    private VideoColumnDao columnDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private VideoColumnFbUserDao fbUserDao ;

    @Override
    public PageImpl getContentPageList(Pageable pageable, PageImpl pageImpl, String columnId, String title, String creTime) {
    	
    	//总个数
    	StringBuilder sb = new StringBuilder("select count(1) from (");
    	querySql(sb,columnId,title,creTime);
    	sb.append(")");
    	Integer total=jdbcTemplate.queryForObject(sb.toString(), Integer.class);
    	//查询分页数据
		 String pageSql = pageSql(pageable,columnId,title,creTime);
		List<VideoContent> listData =jdbcTemplate.query(pageSql.toString(), new RowMapper<VideoContent>(){
			@Override
			public VideoContent mapRow(ResultSet result, int arg1) throws SQLException {
				VideoContent content = new VideoContent();
				content.setId(result.getString("id"));
				content.setTitle(result.getString("title"));
				content.setCreTime(result.getString("creTime"));
				content.setFbTime(result.getString("fbTime"));
				content.setSubflag(result.getString("subflag"));
				content.setIsZd(result.getString("isZd"));
				return content;
			}
			
		});
		return new PageImpl(total,listData);
        
    }
    private String pageSql(Pageable pageable, String columnId, String title, String creTime) {
    	StringBuilder sb=new StringBuilder("select * from ( select a1.*,ROWNUM RN from (");
    	querySql(sb,columnId,title,creTime);
		sb.append("  ) a1 where ROWNUM <= " + (pageable.getOffset()+pageable.getPageSize()) + " ) where RN >=  " + (pageable.getOffset()+1));
		return sb.toString();
	}
	private void querySql(StringBuilder sb, String columnId, String title, String creTime) {
		sb.append(" select t.id,t.title,t.cre_time creTime,t.fb_time fbTime,t.subflag subflag,t.IS_ZD isZd from video_content t  ");
		sb.append(" where t.visible='1'  ");
		 if(StringUtils.isNotBlank(columnId)){
	            sb.append(" and t.COLUMN_ID  = '").append(columnId).append("'");
	     }else{
	            sb.append(" and t.COLUMN_ID in (").append(" select t.id from video_column t where t.visible='1' and t.IS_FIRST='1' ").append(")");
	     }
	     if(StringUtils.isNotBlank(title)){
	            sb.append(" and t.title like '%").append(title).append("%'");
	      }
	      if(StringUtils.isNotBlank(creTime)){
	            sb.append(" and t.CRE_TIME like '%").append(creTime).append("%'");
	      }
	      sb.append(" order by t.CRE_TIME desc ");
		
	}
    @Override
    public PageImpl getFbPageList(Pageable pageable, PageImpl pageImpl, String columnId, String title, String creTime) {
    	//总个数
    	StringBuilder sb = new StringBuilder("select count(1) from (");
    	fbQuerySql(sb,columnId,title,creTime);
    	sb.append(")");
    	Integer total=jdbcTemplate.queryForObject(sb.toString(), Integer.class);
    	//查询分页数据
		 String pageSql = fbPageSql(pageable,columnId,title,creTime);
		List<VideoContent> listData =jdbcTemplate.query(pageSql.toString(), new RowMapper<VideoContent>(){
			@Override
			public VideoContent mapRow(ResultSet result, int arg1) throws SQLException {
				VideoContent content = new VideoContent();
				content.setId(result.getString("id"));
				content.setTitle(result.getString("title"));
				content.setCreTime(result.getString("creTime"));
				content.setFbTime(result.getString("fbTime"));
				content.setSubflag(result.getString("subflag"));
				content.setIsZd(result.getString("isZd"));
				return content;
			}
			
		});
		return new PageImpl(total,listData);
      
    } 
    
    private String fbPageSql(Pageable pageable, String columnId, String title, String creTime) {
    	StringBuilder sb=new StringBuilder("select * from ( select a1.*,ROWNUM RN from (");
    	fbQuerySql(sb,columnId,title,creTime);
		sb.append("  ) a1 where ROWNUM <= " + (pageable.getOffset()+pageable.getPageSize()) + " ) where RN >=  " + (pageable.getOffset()+1));
		return sb.toString();
	}

	private void fbQuerySql(StringBuilder sb, String columnId, String title, String creTime) {
		sb.append(" select t.id,t.title,t.cre_time creTime,t.fb_time fbTime,t.subflag subflag,t.IS_ZD isZd from video_content t  ");
		sb.append(" where t.visible='1' and t.subflag='2' ");
		 if(StringUtils.isNotBlank(columnId)){
	          sb.append(" and t.column_id  = '").append(columnId).append("'");
	     }else{
	          sb.append(" and t.column_id in (").append("select t.id from video_column t where t.visible='1' and t.IS_FIRST='1'").append(")");
	     }
	     if(StringUtils.isNotBlank(title)){
	          sb.append(" and t.title like '%").append(title).append("%'");
	     }
	     if(StringUtils.isNotBlank(creTime)){
	         sb.append(" and t.cre_time like '%").append(creTime).append("%'");
	     }
	      sb.append(" order by t.IS_ZD asc,zd_time desc,zd_order,t.fb_time desc ");
		
	}

	@Override
    public VideoContent saveFrom(VideoContent entity) {
        VideoContent entityDB = new VideoContent();
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
                entityDB.setStatus(entity.getStatus());
                entityDB = dao.save(entityDB);
            }
      
        return entityDB;
    }
    
    @Override
	public VVideoAndPdf saveForm(VVideoAndPdf videoAndPdf) {
		videoAndPdf.setCreJuId(UserUtil.getCruJuId());
		videoAndPdf.setCreJuName(UserUtil.getCruJuName());
		videoAndPdf.setCreChushiId(UserUtil.getCruChushiId());
		videoAndPdf.setCreChushiName(UserUtil.getCruDeptName());
		videoAndPdf.setCreDeptId(UserUtil.getCruDeptId());
		videoAndPdf.setCreDeptName(UserUtil.getCruDeptName());
		videoAndPdf.setCreUserId(UserUtil.getCruUserId());
		videoAndPdf.setCreUserName(UserUtil.getCruUserName());
		videoAndPdf.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		videoAndPdf.setVisible(CommonConstants.VISIBLE[1]);
		return vdao.save(videoAndPdf);
	}
    
    @Override
    public VideoContent getVideoContentById(String id) {
        VideoContent entity = new VideoContent();
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
               VideoContent entity = new VideoContent();
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
        String rolesNo = UserUtil.getCruUserRoleNo();
        if(StringUtils.isNotBlank(columnId)){
            VideoColumn columnEntity = columnDao.findOne(columnId);//栏目信息
            VideoContent contentEntity = dao.findOne(contentId);//内容信息
            json.put("isFbfw",columnEntity.getIsFbfw());
            if(contentEntity !=null) {
                json.put("isFbfw", contentEntity.getIsFBContent());
            }
            if(contentEntity==null) {
            	contentEntity = new VideoContent();
            }
            
            if((StringUtils.isNotBlank(columnEntity.getColumnGlUserIds())  && columnEntity.getColumnGlUserIds().contains(UserUtil.getCruUserId())  || rolesNo.contains(VideoConstants.VIDEOFBGLYKEROLENO) || rolesNo.contains(VideoConstants.VIDEOFBGLYCHUROLENO)) && !"2".equals(contentEntity.getSubflag())){
               //只要当前用户是此栏目设置好的栏目管理员就有发布按钮
            	   json.put("fbButton","1");//发布
            }else{
                VideoColumnFbUser fbUserEntity = fbUserService.getEntity(columnId,UserUtil.getCruUserId());//发布人信息
                if(StringUtils.isNotBlank(contentId)) {//如果内容id不为空并且是发布人
                    if (VideoConstants.SUBFLAG[0].equals(contentEntity.getSubflag()) && StringUtils.isNotBlank(fbUserEntity.getId())) {//草稿
                        json.put("sendButton", "1");//发送
                    } else if (VideoConstants.SUBFLAG[1].equals(contentEntity.getSubflag())) {//流程中
                        if (StringUtils.isNotBlank(fbUserEntity.getId())) {//当前登录用户是拟稿人时不显示发送按钮（避免多次发送）
                            json.put("sendButton", "0");    //发送
                        }
                        json.put("passButton", "1");//通过
                        json.put("noPassButton", "1");//不通过
                    } /*else if (VideoConstants.SUBFLAG[2].equals(contentEntity.getSubflag())) {//已发布
                        json.put("fbButton", "1");//发布
                    } */else if (VideoConstants.SUBFLAG[3].equals(contentEntity.getSubflag())) {//退回
                        json.put("sendButton", "1");    //发送
                    }
                }else if(StringUtils.isNotBlank(fbUserEntity.getFbUserId())){
                    json.put("sendButton", "1");//发送
                }
            }
        }
        return json;
    }

    @Override
    public PageImpl getSpList(Pageable pageable, PageImpl pageImpl, String columnName, String title) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select c.id, c.title, w.draft_time,l.id column_id, l.column_name, w.draft_user_name, c.subflag,w.id work_item_id from video_content c, video_column l, sys_waitdo_noflow w " +
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
        //判断当前登录用户角色是发布员还是审核员
        String roleNos=UserUtil.getCruUserRoleNo();
        String isFbOrSpUser="";
        if(StringUtils.isNotBlank(roleNos) &&(roleNos.contains(VideoConstants.VIDEOFBSHRKROLENO) || 
        		roleNos.contains(VideoConstants.VIDEOFBSHRCROLENO))){
        	//是视频审核人
            //是审核人  操作按钮只显示审批  数据展示流程中的数据
            sql.append(" and c.subflag='1'");
            isFbOrSpUser="1";
        }else if(StringUtils.isNotBlank(roleNos) &&(roleNos.contains(VideoConstants.VIDEOFBFBRKROLENO) || 
        		roleNos.contains(VideoConstants.VIDEOFBFBRCROLENO))){
        	//是视频发布人
        	//是发布人 操作按钮只显示修改 数据展示只展示退回的数据
        	sql.append(" and c.subflag='3'");
        	isFbOrSpUser="0";
        }
        //判断是发布人 还是审核人
        //VideoColumnFbUser fbUserEntity = fbUserService.getEntity(columnId,UserUtil.getCruUserId());//发布人信息
      /* List<VideoColumnFbUser> fbUserList=fbUserDao.findByVisibleAndFbUserId(CommonConstants.VISIBLE[1],UserUtil.getCruUserId());
        StringBuilder fbUserIds = new StringBuilder();
        
        		
        if(fbUserList!=null && fbUserList.size()>0){
        	for(VideoColumnFbUser fbs:fbUserList){
        		if(StringUtils.isNotBlank(fbs.getFbUserId())){
        			fbUserIds.append(fbs.getFbUserId()).append(",");
        		}
        	}
        	fbUserIds.substring(0, fbUserIds.length()-1);
        }else{
           List<VideoColumnFbUser> shUserList=fbUserDao.findByVisibleAndShUserId(CommonConstants.VISIBLE[1],UserUtil.getCruUserId());
        	if(shUserList!=null && shUserList.size()>0){
        		for(VideoColumnFbUser vis:shUserList){
        			if(StringUtils.isNotBlank(vis.getShUserId())){
            			spUserIds.append(vis.getShUserId()).append(",");
            		}
        		}
        		spUserIds.substring(0, spUserIds.length()-1);
        	}
        }*/
        //是发布人
        //String isFbOrSpUser="";
        /*if(fbUserIds.indexOf(UserUtil.getCruUserId())!=-1){
           //是发布人 操作按钮只显示修改 数据展示只展示退回的数据
        	sql.append(" and c.subflag='3'");
        	isFbOrSpUser="0";
        }else if(spUserIds.indexOf(UserUtil.getCruUserId())!=-1){
        	//是审核人  操作按钮只显示审批  数据展示流程中的数据
        	sql.append(" and c.subflag='1'");
        	isFbOrSpUser="1";
        }*/
        sql.append(" order by w.draft_time desc ");
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql.toString());
        List<VideoSpEntity> data = new ArrayList<VideoSpEntity>();
        for(Map<String,Object> map :list){
        	VideoSpEntity entity = new VideoSpEntity();
            entity.setId((String)map.get("id"));
            entity.setColumnName((String)map.get("column_name"));
            entity.setTitle((String)map.get("title"));
            entity.setDraftUserName((String)map.get("draft_user_name"));
            entity.setDraftTime((String)map.get("draft_time"));
            entity.setSubflag((String)map.get("subflag"));
            entity.setColumnId((String)map.get("column_id"));
            entity.setWorkItemId((String)map.get("work_item_id"));
            entity.setIsFbOrSpUser(isFbOrSpUser);
            data.add(entity);
        }
        List<VideoSpEntity> list2 = new ArrayList<VideoSpEntity>();
        if(pageable.getPageSize()*(pageable.getPageNumber() + 1)<list.size()){
            int startNumber = pageable.getPageSize() * pageable.getPageNumber();
            int endNumber = pageable.getPageSize() * (pageable.getPageNumber() + 1);
            list2 = data.subList(startNumber,endNumber);
        }else{
            list2 = data.subList(pageable.getPageSize() * pageable.getPageNumber(),list.size());
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
	public PageImpl getContentByColumn(PageImpl page, String columnCode, VideoContent info) throws Exception {
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
		List<String> authList = VideoAuthorityUtils.getAuthList();
		if (!authList.isEmpty()) {
			for (String contentId : authList) {
				contentIds.append("'" + contentId + "',");
			}
		}
		contentIdStr = contentIds.substring(0, contentIds.length() - 1);
		
		StringBuilder hql = new StringBuilder("select new com.sinosoft.sinoep.modules.info.xxfb.entity.VideoContent(t.id, t.creUserId, t.creUserName, t.creDeptId,t.creDeptName,"
		+ "t.visible, t.creTime, t.title, t.subtitle, t.author, t.showStartTime,t.showEndTime, t.content, t.source, t.imageId, t.subflag, t.isZd,"
		+ "t.columnId, t.fbTime ,c.columnCode) ");
		hql.append("from VideoContent t,VideoColumn c where t.columnId = c.id and t.visible = '1' and t.subflag = '"+VideoConstants.SUBFLAG[2]+"'");
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
        hql.append(" order by t.isZd+0,t.fbTime desc ");
        Page<VideoContent> query = dao.query(hql.toString(), pageable);
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
		List<String> authList = VideoAuthorityUtils.getAuthList();
		if (!authList.isEmpty()) {
			for (String contentId : authList) {
				contentIds.append("'" + contentId + "',");
			}
		}
		contentIdStr = contentIds.substring(0, contentIds.length() - 1);
		StringBuilder sBuilder = new StringBuilder("select i.id infoId,i.title,i.subTitle,t.save_path imgPath");
		sBuilder.append(" from sys_affix t, info_content i, info_column c ");
		sBuilder.append(" where c.column_code = '"+columnCode+"' and i.visible = '1' and i.subflag = '"+VideoConstants.SUBFLAG[2]+"' and i.column_id = c.id and t.table_id = i.id ");
		sBuilder.append(" and (( i.show_start_time <= '" + nowTime + "' and i.show_end_time >= '" + nowTime + "')");
		sBuilder.append(" or i.show_start_time is null)");
		sBuilder.append(" and t.save_path is not null");
		//如果有发布范围，查询发布范围内的数据
		sBuilder.append(" and (i.is_FB_Content = '0' or (i.is_FB_Content = '1' and i.id in ("+contentIdStr+")))");
		sBuilder.append(" order by i.is_zd+0 ,i.FB_TIME desc");
		query = jdbcTemplate.query(sBuilder.toString(), new BeanPropertyRowMapper<>(BannerImgVo.class));
		return query;
	}


    @Override
    public int updateZdByConetenIds(String contentIds, String isZd) {
    	String cruTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        int n = 0;
        int index=0;
        StringBuilder sb = new StringBuilder();
        try{
            if(StringUtils.isNotBlank(contentIds)){
                String [] contentIdsArray = contentIds.split(",");
               
                for(String id:contentIdsArray){
                	 sb.setLength(0);
                	index++;
                	if(StringUtils.isNotBlank(isZd)) {
                    	sb.append(" update video_content set IS_ZD='" + isZd + "',ZD_TIME='" + cruTime + "',ZD_ORDER='" + index + "' where id='" + id + "' ");
                	}else {
                    	sb.append(" update video_content set IS_ZD='',ZD_TIME='',ZD_ORDER='' where id='" + id + "' ");
                	}
                	jdbcTemplate.execute(sb.toString());
                	//dao.updateZdById(isZd,id);
                  
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
    public List<VideoContent> getZdList(String columnId) {
       
        StringBuilder sb = new StringBuilder(" select t.id,t.title,t.IS_ZD isZd from video_content t where t.COLUMN_ID='" + columnId + "' ");
    	sb.append("   and t.visible='1' and t.IS_ZD is not null order by t.IS_ZD asc,ZD_TIME desc,ZD_ORDER,t.fb_time desc ");
        List<VideoContent> list = jdbcTemplate.query(sb.toString(), new RowMapper<VideoContent>() {
			@Override
			public VideoContent mapRow(ResultSet result, int arg1) throws SQLException {
				VideoContent content = new VideoContent();
				content.setId(result.getString("id"));
				content.setTitle(result.getString("title"));
				content.setIsZd(result.getString("isZd"));
				return content;
			}
        	
        });
        return list;
    }

    @Override
    public int orderZd(String ids) {
        int n = 0;
        try {
            if (StringUtils.isNotBlank(ids)) {
                String[] idsArray = ids.split(",");
                for(int m = 0;idsArray.length > m; m++){
                    String iszd = String.valueOf(m+1);
                    dao.updateZdById(iszd,idsArray[m]);
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
     * 根据栏目id获取首页内容
     * TODO 
     * @author 马秋霞
     * @Date 2018年11月16日 上午9:52:08
     * @param columnId
     * @return
     * @throws Exception 
     */
	@Override
	public Map<String, Object> getHomePageContent(PageImpl page,String columnId,String flag,String columnName) throws Exception {
		String nowTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd");
		Map<String, Object>  map = new HashMap<String, Object>();
		//获取该栏目是否有发布范围
		StringBuilder contentIds = new StringBuilder();
		String contentIdStr = "'',";
		List<String> authList = VideoAuthorityUtils.getAuthList();//获取当前用户权限内的数据
		if (!authList.isEmpty()) {
			for (String contentId : authList) {
				contentIds.append("'" + contentId + "',");
			}
		}
		if(contentIds.length()>0){
			contentIdStr = contentIds.substring(0, contentIds.length() - 1);
		}
	
		if(StringUtils.isBlank(columnId) && "3".equals(flag)){
			//展示的首页视频
			//查询最新的前5条发布的视频
			StringBuilder sb = new StringBuilder();
		/*	sb.append(" select t.id,t.TITLE,t1.save_path imgPath from ( ");
			sb.append(" select t.* from( ");
			sb.append(" select t.id,t.TITLE,t.IMAGE_ID from VIDEO_CONTENT t where t.visible='1' and  t.subflag='2' and (IS_FB_CONTENT='0' or IS_FB_CONTENT='1' and t.id in (" + contentIdStr + ")) order by t.IS_ZD+0,t.FB_TIME desc ");
			sb.append(" ) t where ROWNUM<=5  ");
			sb.append(" ) t left outer join (select * from sys_affix ) t1 on t.IMAGE_ID = t1.id   ");*/
			
			/*StringBuilder idSql = new StringBuilder();
			String recommendId = "";
			idSql.append("select v.id from VIDEO_COLUMN v where v.is_recommend ='1' and v.visible='1'");
			List<VideoContent> ids = jdbcTemplate.query(idSql.toString(),new BeanPropertyRowMapper<>(VideoContent.class));
			if(ids.size()>0){
				recommendId = ids.get(0).getId();
				map.put("recommendId", recommendId);
			}
			sb.append(" select t.id,t.TITLE,t.IS_ZD,t.FB_TIME,t1.save_path imgPath,t2.id videoId,t2.file_id uuid,t2.file_name fileName,t3.playTimes playTimes from ( select * from ( ");
			sb.append(" select t.id,t.TITLE,t.IMAGE_ID,t.IS_ZD , t.FB_TIME,t.ZD_ORDER,t.ZD_TIME  from VIDEO_CONTENT t where t.visible='1' and t.SUBFLAG='2' and t.column_id='" + recommendId + "' and (IS_FB_CONTENT='0'    ");
			if(contentIds.length()>0){
			     sb.append("  or IS_FB_CONTENT='1' and t.id in (" + contentIdStr + ") ");
			}
			sb.append(" )");
			sb.append(" and (( t.SHOW_START_TIME <= '" + nowTime + "' and t.SHOW_END_TIME >= '" + nowTime + "') or t.SHOW_START_TIME is null)   order by t.IS_ZD, t.FB_TIME  ");
			sb.append(" ) t where ROWNUM<=5  ) t ");
			sb.append(" left outer join (select * from sys_affix ) t1 on t.id = t1.table_id ");
			sb.append(" left outer join ( ");
			sb.append(" select t1.id,t1.INFO_ID,t1.file_id,t1.file_name  from ( select INFO_ID,min(cre_time) newCreTime from video_video_and_pdf where visible='1'  group by INFO_ID) t left outer join (select * from video_video_and_pdf) t1 on t.INFO_ID=t1.INFO_ID and t.newCreTime=t1.cre_time");
			sb.append("  ) t2 on t.id=t2.info_id ");
			sb.append(" left outer join (select content_id,count(id) playTimes from history_record group by content_id) t3 on t.id=t3.content_id  order by t.IS_ZD asc,t.ZD_TIME desc,t.ZD_ORDER,t.fb_time desc ");
			List<VideoContent> latestList =jdbcTemplate.query(sb.toString(), new RowMapper<VideoContent>(){
				@Override
				public VideoContent mapRow(ResultSet rs, int arg1) throws SQLException {
					VideoContent content = new VideoContent();
					content.setId(rs.getString("id"));
					content.setTitle(rs.getString("title"));
					content.setUuid(rs.getString("uuid"));
					content.setFileName(rs.getString("fileName"));
					content.setPlayTimes(rs.getString("playTimes"));
					content.setVideoId(rs.getString("videoId"));
					String imgPath = rs.getString("imgPath");
					if(StringUtils.isNotBlank(imgPath)){
						int start = imgPath.indexOf("upload")-1;
						imgPath=imgPath.substring(start);
						System.out.println("ddnddjhh");
					}
					content.setImgPath(imgPath);
					return content;
				}
			});
			//List<VideoContent> latestList = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<VideoContent>(VideoContent.class));
			
			map.put("latestList", latestList);*/
			//查询每种类别的视频
			StringBuilder classSql = new StringBuilder(" select t.id,t.column_name columnName,case when t2.contentcnt is null then 0 else t2.contentcnt  end contentcnt from video_COLUMN t  ");
			classSql.append("  left outer join ( select t.column_id,count(1) contentcnt from VIDEO_CONTENT t where t.visible = '1' and t.subflag = '2' and (IS_FB_CONTENT = '0' ");
			if(contentIds.length()>0){
				classSql.append(" or IS_FB_CONTENT='1' and t.id in (" + contentIdStr + ") ");
			}
			classSql.append(" )");
			classSql.append(" and (( t.SHOW_START_TIME <= '" + nowTime + "' and t.SHOW_END_TIME >= '" + nowTime + "') or t.SHOW_START_TIME is null) group by t.column_id ) t2 on t.id=t2.column_id    ");
			classSql.append(" where  t.visible='1'  and t.IS_SHOW='1' and trim(t.super_id) in( select t.id from video_COLUMN t where t.visible = '1' and t.is_first='1'  )   order by to_Number(t.order_no),t.cre_time ");
			StringBuilder contentSql = new StringBuilder("");
			List<VideoColumn> columnList = jdbcTemplate.query(classSql.toString(), new BeanPropertyRowMapper<VideoColumn>(VideoColumn.class));
			for(int i=0;i<columnList.size();i++){
				contentSql.setLength(0);
				contentSql.append(" select t.*,t1.save_path imgPath,t2.id videoId,t2.file_id uuid,t2.file_name fileName,t3.playTimes from ( select * from ( ");
				contentSql.append(" select t.id,t.TITLE,t.IMAGE_ID,t.IS_ZD,t.FB_TIME,t.ZD_ORDER,t.ZD_TIME from VIDEO_CONTENT t where t.visible = '1' and t.column_id='" + columnList.get(i).getId() + "' and t.subflag = '2'  and (IS_FB_CONTENT = '0'    ");
				if(contentIds.length()>0){
				   contentSql.append(" or IS_FB_CONTENT='1' and t.id in (" + contentIdStr + ") ");
				}
				contentSql.append(" )");
				contentSql.append(" and (( t.SHOW_START_TIME <= '" + nowTime + "' and t.SHOW_END_TIME >= '" + nowTime + "') or t.SHOW_START_TIME is null)  order by t.IS_ZD asc, t.ZD_TIME desc, t.ZD_ORDER, t.fb_time desc  ");
				contentSql.append(" )t where ROWNUM<=5 ) t ");
				contentSql.append("  left outer join (select * from sys_affix) t1 on t.id = t1.table_id  ");
				contentSql.append(" left outer join (");
				contentSql.append(" select t1.id,t1.INFO_ID,t1.file_id,t1.file_name  from ( select INFO_ID,min(cre_time) newCreTime from video_video_and_pdf where visible='1'  group by INFO_ID) t left outer join (select * from video_video_and_pdf) t1 on t.INFO_ID=t1.INFO_ID and t.newCreTime=t1.cre_time");
				contentSql.append(" ) t2 on t.id=t2.info_id  ");
				contentSql.append(" left outer join (select content_id,count(id) playTimes from history_record group by content_id) t3 on t.id=t3.content_id order by t.IS_ZD asc,t.ZD_TIME desc,t.ZD_ORDER,t.fb_time desc ");
				List<VideoContent> contentList =jdbcTemplate.query(contentSql.toString(), new RowMapper<VideoContent>(){
					@Override
					public VideoContent mapRow(ResultSet rs, int arg1) throws SQLException {
						VideoContent content = new VideoContent();
						content.setId(rs.getString("id"));
						content.setTitle(rs.getString("title"));
						String imgPath = rs.getString("imgPath");
						content.setUuid(rs.getString("uuid"));
						content.setFileName(rs.getString("fileName"));
						content.setPlayTimes(rs.getString("playTimes"));
						content.setVideoId(rs.getString("videoId"));
						if(StringUtils.isNotBlank(imgPath)){
							int start = imgPath.indexOf("upload")-1;
							imgPath=imgPath.substring(start);
						}
						content.setImgPath(imgPath);
						return content;
					}
				});
				//List<VideoContent> contentList = jdbcTemplate.query(contentSql.toString(), new BeanPropertyRowMapper<VideoContent>(VideoContent.class));
				if(contentList.size()==0){
					columnList.remove(i);
					i--;
				}else{
					columnList.get(i).setContentList(contentList);
				}
				
			}
			map.put("columnList", columnList);
		}else{
			Integer pageNumber = page.getPageNumber();
			Integer pageSize = page.getPageSize();
			if (pageNumber == null) {
				pageNumber = 1;
			}
			if (pageSize == null) {
				pageSize = 10;
			}
			Pageable pageable = new PageRequest(pageNumber-1, pageSize);
			
			//总个数
			StringBuilder sb = new StringBuilder("select count(1) from (");
			querySql(sb,columnId,nowTime,contentIds,contentIdStr,columnName,flag);
			sb.append(")");
			Integer total=jdbcTemplate.queryForObject(sb.toString(), Integer.class);
			//查询分页数据
			String pageSql = pageSql(pageable,columnId,nowTime,contentIds,contentIdStr,columnName,flag);
			List<VideoContent> list =jdbcTemplate.query(pageSql.toString(), new RowMapper<VideoContent>(){
				@Override
				public VideoContent mapRow(ResultSet rs, int arg1) throws SQLException {
					VideoContent content = new VideoContent();
					content.setId(rs.getString("id"));
					content.setTitle(rs.getString("title"));
					content.setUuid(rs.getString("uuid"));
					content.setFileName(rs.getString("fileName"));
					content.setPlayTimes(rs.getString("playTimes"));
					String imgPath = rs.getString("imgPath");
					content.setVideoId(rs.getString("videoId"));
					if(StringUtils.isNotBlank(imgPath)){
						int start = imgPath.indexOf("upload")-1;
						imgPath=imgPath.substring(start);
					}
					content.setImgPath(imgPath);
					return content;
				}
			});
			
			/*StringBuilder hql = new StringBuilder(" from VideoContent t where t.visible = '1' and t.columnId = '" + columnId + "' and t.subflag = '"+InfoConstants.SUBFLAG[2]+"' ");
			hql.append(" and (( t.showStartTime <= '" + nowTime + "' and t.showEndTime >= '" + nowTime + "')");
			hql.append(" or t.showStartTime is null)");
			//如果有发布范围，查询发布范围内的数据
			hql.append(" and (t.isFBContent = '0' or (t.isFBContent = '1' and t.id in ("+contentIdStr+")))");
			hql.append(" order by t.isZd+0,t.fbTime desc ");
	        Page<VideoContent> query = dao.query(hql.toString(), pageable);
	        
	        int total = (int) query.getTotalElements();
	        List<VideoContent> list =query.getContent();*/
	        page.getData().setRows(list);
			page.getData().setTotal(total);
			map.put("pageData", page);
			if(list.size()==0){
				map.put("totalPage", 0);
			}else if(total%(list.size())!=0){
				map.put("totalPage", total/(list.size())+1);
			}else{
				map.put("totalPage", total/(list.size()));
			}
			
		}
		return map;
	}
	
	private void querySql(StringBuilder sb, String columnId,String nowTime,StringBuilder contentIds,String contentIdStr,String columnName,String flag) {
		sb.append(" select t.*,t1.SAVE_PATH imgPath,t2.id videoId,t2.file_id uuid,t2.file_name fileName,t3.playTimes from ( ");
		sb.append("select t.id,t.title,t.IMAGE_ID,t.IS_ZD,t.FB_TIME,t.ZD_ORDER,t.ZD_TIME from VIDEO_CONTENT t where t.visible = '1'   and t.subflag = '" + VideoConstants.SUBFLAG[2] + "' ");
		if("1".equals(flag)){
			//点击搜索获取的数据
			sb.append(" and t.title like '%" + columnName + "%' ");
		}else{
			//点击导航栏上的标签获取的数据
			sb.append(" and t.COLUMN_ID = '" + columnId + "' ");
		}
		sb.append(" and (( t.SHOW_START_TIME <= '" + nowTime + "' and t.SHOW_END_TIME >= '" + nowTime + "')");
		sb.append(" or t.SHOW_START_TIME is null)");
		//如果有发布范围，查询发布范围内的数据
		sb.append(" and (t.IS_FB_CONTENT = '0' ");
		if(contentIds.length()>0){
			sb.append(" or (t.IS_FB_CONTENT = '1' and t.id in ("+contentIdStr+")) ");
		}
		sb.append(" ) ");
		sb.append(" order by t.IS_ZD,t.FB_TIME desc ");
		sb.append(" ) t left outer join (select * from sys_affix) t1 on t.IMAGE_ID=t1.id ");
		sb.append(" left outer join (");
		sb.append(" select t1.id,t1.INFO_ID,t1.file_id,t1.file_name  from ( select INFO_ID,min(cre_time) newCreTime from video_video_and_pdf where visible='1'  group by INFO_ID) t left outer join (select * from video_video_and_pdf) t1 on t.INFO_ID=t1.INFO_ID and t.newCreTime=t1.cre_time");

		sb.append(" ) t2 on t.id=t2.info_id  ");
		sb.append(" left outer join (select content_id,count(id) playTimes from history_record group by content_id) t3 on t.id=t3.content_id order by t.IS_ZD asc,t.ZD_TIME desc,t.ZD_ORDER,t.fb_time desc ");
	}
	private String pageSql(Pageable pageable, String columnId,String nowTime,StringBuilder contentIds,String contentIdStr,String columnName,String flag) {
		StringBuilder sb=new StringBuilder("select * from ( select a1.*,ROWNUM RN from (");
		querySql(sb,columnId,nowTime,contentIds,contentIdStr,columnName,flag);
		sb.append("  ) a1 where ROWNUM <= " + (pageable.getOffset()+pageable.getPageSize()) + " ) where RN >=  " + (pageable.getOffset()+1));
		return sb.toString();
	}
	
	/**
	 * 展示视频播放右边的栏目部分
	 */
	@Override
	public List<VideoContent> getContentByColumnId(String columnId, String title) throws Exception {
		String nowTime = DateUtil.getDateText(new Date(), "yyyy-MM-dd");
		String contentIdStr = "'',";
		
		List<String> authList = VideoAuthorityUtils.getAuthList();
		StringBuilder contentIds = new StringBuilder();
		if (!authList.isEmpty()) {
			for (String contentId : authList) {
				contentIds.append("'" + contentId + "',");
			}
		}
		contentIdStr = contentIds.substring(0, contentIds.length() - 1);
		StringBuilder sb = new StringBuilder(" select t.*,t1.info_id uuid from (select t.id,t.title from video_content t where t.column_id='" + columnId + "' ");
		if(StringUtils.isNotBlank(title)){
			sb.append(" and t.title lik '%" + title + "%'");
		}
		sb.append(" and (( t.SHOW_START_TIME <= '" + nowTime + "' and t.SHOW_END_TIME >= '" + nowTime + "')");
		sb.append(" or t.SHOW_START_TIME is null) ");
		sb.append(" and (IS_FB_CONTENT='0' or IS_FB_CONTENT='1' and t.id in (" + contentIdStr + ")) ");
		sb.append(" )t left outer join video_video_and_pdf t1 on t.id=t1.info_id ");
		List<VideoContent> list = jdbcTemplate.query(sb.toString(),new RowMapper<VideoContent>(){
			@Override
			public VideoContent mapRow(ResultSet rs, int arg1) throws SQLException {
				VideoContent content = new VideoContent();
				content.setId(rs.getString("id"));
				content.setTitle(rs.getString("title"));
				content.setUuid(rs.getString("uuid"));
				return content;
			}
			
		});
		return list;
	}
	
	/**
	 * 获取某个内容下的视频信息
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年12月3日 下午2:43:43
	 * @param contentId
	 * @return
	 */
	@Override
	public List<VideoInfo> getVideoByContentId(String contentId,String fileName) {
		StringBuilder sql = new StringBuilder(" select t.id videoId,t.file_name fileName,t.file_id uuid,t.info_id contentid,t1.title,t1.title,t2.readcnt,t3.notfinishcnt  from video_video_and_pdf t  ");
		sql.append(" left outer join VIDEO_CONTENT t1 on t1.id=t.info_id ");
		sql.append("  left outer join (select t.video_id,count(1) readcnt from history_record t where state='1' and user_id='" + UserUtil.getCruUserId() + "' group by video_id )t2  on t.id=t2.video_id ");
		sql.append("  left outer join (select t.video_id,count(1) notfinishcnt from history_record t where state='0' and user_id='" + UserUtil.getCruUserId() + "' group by video_id )t3  on t.id=t3.video_id ");
		sql.append(" where t.visible='1'  and t.info_id='" + contentId + "' ");
		if(StringUtils.isNotBlank(fileName)){
			sql.append(" and t.file_name like '%" + fileName + "%'");
		}
		sql.append(" order by t.cre_time  ");
		List<VideoInfo> list = jdbcTemplate.query(sql.toString(), new RowMapper<VideoInfo>(){
			int number = 1;
			@Override
			public VideoInfo mapRow(ResultSet rs, int arg1) throws SQLException {
				VideoInfo videoinfo = new VideoInfo();
				videoinfo.setVideoId(rs.getString("videoid"));
				videoinfo.setSeries(rs.getString("title"));
				videoinfo.setName(rs.getString("filename"));
				videoinfo.setUuid(rs.getString("uuid"));
				String readcntstr = rs.getString("readcnt");
				String notfinishcntstr = rs.getString("notfinishcnt");
				if(StringUtils.isNotBlank(readcntstr)){
					int readcnt = Integer.valueOf(readcntstr);
					if(readcnt>0){
						videoinfo.setState("2");
					}
				}else if(StringUtils.isNotBlank(notfinishcntstr)){
					int notfinishcnt = Integer.valueOf(notfinishcntstr);
					if(notfinishcnt>0){
						videoinfo.setState("1");
					}
				}else{
					videoinfo.setState("0");
				}
				videoinfo.setNumber(number);
				number++;
				return videoinfo;
			}
			
		});
		return list;
	}
	
	/**
	 * 根据id修改imageid
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年12月12日 上午10:16:22
	 * @param id
	 * @param imageid
	 */
	@Override
	public void updateImageId(String id, String imageid) {
		String updateSql = "update video_content set image_id='" + imageid + "' where id='" + id + "'";
		jdbcTemplate.execute(updateSql);
		
	}
	
	/**
	 * 通过sql保存表单中的部分数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年2月20日 下午6:33:11
	 * @param entity
	 * @return
	 */
	@Override
	public void saveFromBySql(VideoContent entity) {
		StringBuilder sb = new StringBuilder("update video_content  ");
		sb.append("set SUBFLAG='"+ VideoConstants.SUBFLAG[1]+"'");
		sb.append(", TITLE='").append(entity.getTitle()).append("' ");
		sb.append(", SUBTITLE='" + entity.getSubtitle() + "'");
		sb.append(", SOURCE='" + entity.getSource() + "'");
		sb.append(", AUTHOR='" + entity.getAuthor() + "'");
		sb.append(", SHOW_START_TIME='" + entity.getShowStartTime() + "'");
		sb.append(", SHOW_END_TIME='" + entity.getShowStartTime() + "'");
		sb.append(", IS_FB_CONTENT='" + entity.getIsFBContent() + "'");
		sb.append("  where id='" + entity.getId() + "'");
		jdbcTemplate.execute(sb.toString());
		
	}
}
