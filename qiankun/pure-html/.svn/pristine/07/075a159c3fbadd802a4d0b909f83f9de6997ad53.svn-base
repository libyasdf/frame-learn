package com.sinosoft.sinoep.modules.video.background.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.video.background.common.VideoConstants;
import com.sinosoft.sinoep.modules.video.background.dao.VideoColumnDao;
import com.sinosoft.sinoep.modules.video.background.dao.VideoColumnFbUserDao;
import com.sinosoft.sinoep.modules.video.background.dao.VideoContentDao;
import com.sinosoft.sinoep.modules.video.background.entity.VideoColumn;
import com.sinosoft.sinoep.modules.video.background.entity.VideoColumnFbUser;
import com.sinosoft.sinoep.modules.video.background.entity.VideoColumnTreeEntity;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;
import net.sf.json.JSONObject;

@Service
public class VideoColumnServiceImpl implements VideoColumnService{

    @Autowired
    private VideoColumnDao dao;
    @Autowired
    private VideoColumnFbUserDao fbUserDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private VideoContentDao videoContentDao;
    
    /**
     * 根据当前登录人获取信息发布栏目树数据
     * @param userId 当前登录人
     * @return
     */
    @Override
    public JSONObject getColumnTree(String columnName,String userId) {
        JSONObject json = new JSONObject();
        json.put("flag","0");
        //String[] param = new String[] {};
        List<String> param =new ArrayList<String>();
        try{
            StringBuilder sql = new StringBuilder("select nvl(t.column_name,' ') columnName,t.IS_FIRST isFirst,t.id id,trim(t.super_id) superId,nvl(t.order_no,' ') orderNo,nvl(t.code,' ') code,nvl(t.node_level,' ') nodeLevel " +
                    " from video_column t where t.visible = '1'" );
            String roleNo = UserUtil.getCruUserRoleNo();//获取当前人业务角色编号
            if(StringUtils.isNotBlank(userId) && (roleNo.indexOf(VideoConstants.VIDEOFBGLYKEROLENO) < 0 
            		&& roleNo.indexOf(VideoConstants.VIDEOFBGLYCHUROLENO)<0)){
                sql.append("  and t.id in( select distinct (c.id) from video_column c where c.visible = '1' " +
                        "start with c.id in(select id from video_column where  IS_SP = '1'  and id in(select distinct(u.column_id) from video_column_fb_user u where u.visible='1'" +
                        " and u.fb_user_id = ? ").append(")");
                sql.append("  or   id in(select m.id from video_column m where m.visible = '1' and m.column_gl_user_ids like ? ").append(")");
                sql.append(" )");
                sql.append(" connect by prior trim(c.super_id) = c.id)");
                param.add(userId);
                param.add('%'+userId+'%');
               
            }
            if(StringUtils.isNotBlank(columnName)){
                sql.append(" and t.column_name like ? ");
                param.add('%'+columnName+'%');
               
            }
            sql.append(" or is_first='1' and visible = '1' order by CRE_TIME ");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString(),param.toArray());
            List<VideoColumnTreeEntity> treeList = new ArrayList<VideoColumnTreeEntity>();
            for(Map<String ,Object> map : list){
            	VideoColumnTreeEntity entity = new VideoColumnTreeEntity();
                entity.setId((String) map.get("ID"));
                entity.setpId((String) map.get("SUPERID"));
                entity.setName((String) map.get("COLUMNNAME"));
                entity.setNodeLevel((String) map.get("NODELEVEL"));
                entity.setOrderNo((String) map.get("ORDERNO"));
                entity.setCode((String) map.get("CODE"));
                entity.setIsFirst((String) map.get("ISFIRST"));
                treeList.add(entity);
            }
            json.put("flag","1");
            json.put("data",treeList);
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
            json.put("msg","获取失败!");
        }
        return json;
    }
    
    @Override
    public JSONObject getContentColumnTree(String columnName,String userId) {
        JSONObject json = new JSONObject();
        json.put("flag","0");
        //String[] param = new String[] {};
        List<String> param =new ArrayList<String>();
        try{
            StringBuilder sql = new StringBuilder("select nvl(t.column_name,' ') columnName,t.IS_FIRST isFirst,t.id id,trim(t.super_id) superId,nvl(t.order_no,' ') orderNo,nvl(t.code,' ') code,nvl(t.node_level,' ') nodeLevel " +
                    " from video_column t where t.visible = '1'" );
            String roleNo = UserUtil.getCruUserRoleNo();//获取当前人业务角色编号
            if(StringUtils.isNotBlank(userId) && (roleNo.indexOf(VideoConstants.VIDEOFBGLYKEROLENO) < 0 
            		&& roleNo.indexOf(VideoConstants.VIDEOFBGLYCHUROLENO)<0)){
            	sql.append("  and t.id in( select distinct (c.id) from video_column c where c.visible = '1' " +
                        "start with c.id in(select id from video_column where   ");
            	//是视频发布人 
            	if((roleNo.indexOf(VideoConstants.VIDEOFBFBRKROLENO)>0 || roleNo.indexOf(VideoConstants.VIDEOFBFBRCROLENO)>0)){
            		sql.append("  IS_SP = '1' and id in(select distinct(u.column_id) from video_column_fb_user u where u.visible='1'" +
                        " and u.fb_user_id = ?)");
            		param.add(userId);
            		//同时是二级栏目管理员
            		if(roleNo.indexOf(VideoConstants.VIDEOFBGLYEJCHUROLENO)>0 || roleNo.indexOf(VideoConstants.VIDEOFBGLYEJKEROLENO)>0){
            			sql.append("  or   id in(select m.id from video_column m where m.visible = '1' and m.column_gl_user_ids like ? ").append(")");
            			param.add('%'+userId+"%");
            		}
            		sql.append(" )");
            		sql.append(" connect by prior trim(c.super_id) = c.id)");
            	}
            	//只是二级栏目管理员
            	if((roleNo.indexOf(VideoConstants.VIDEOFBGLYEJCHUROLENO)>0 || roleNo.indexOf(VideoConstants.VIDEOFBGLYEJKEROLENO)>0)&&(roleNo.indexOf(VideoConstants.VIDEOFBFBRKROLENO)<0 && roleNo.indexOf(VideoConstants.VIDEOFBFBRCROLENO)<0)){
            		sql.append("	id in(select m.id from video_column m where m.visible = '1' and m.column_gl_user_ids like ? ").append(")");
            		param.add('%'+userId+"%");
            		sql.append(" )");
            		sql.append(" connect by prior trim(c.super_id) = c.id)");
            	}
            }
            if(StringUtils.isNotBlank(columnName)){
                sql.append(" and t.column_name like ? ");
                param.add('%'+columnName+"%");
            }
            sql.append(" or is_first='1' and visible = '1' order by CRE_TIME ");
            
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString(),param.toArray());
            List<VideoColumnTreeEntity> treeList = new ArrayList<VideoColumnTreeEntity>();
            for(Map<String ,Object> map : list){
            	VideoColumnTreeEntity entity = new VideoColumnTreeEntity();
                entity.setId((String) map.get("ID"));
                entity.setpId((String) map.get("SUPERID"));
                entity.setName((String) map.get("COLUMNNAME"));
                entity.setNodeLevel((String) map.get("NODELEVEL"));
                entity.setOrderNo((String) map.get("ORDERNO"));
                entity.setCode((String) map.get("CODE"));
                entity.setIsFirst((String) map.get("ISFIRST"));
                treeList.add(entity);
            }
            json.put("flag","1");
            json.put("data",treeList);
        }catch (Exception e){
            e.printStackTrace();
            json.put("flag","-1");
            json.put("msg","获取失败!");
        }
        return json;
    }

    @Override
    public void updatetreeSort(String[] ids,String dropId,String superId,String nodeLevel) {
        VideoColumn entity = dao.findOne(dropId);
        entity.setSuperId(superId);
        entity.setNodeLevel(nodeLevel);
        dao.save(entity);
        for (int i = 0;i<ids.length;i++){
            VideoColumn newEntity = dao.findOne(ids[i]);
            newEntity.setOrderNo(String.valueOf(i));
            newEntity.setNodeLevel(nodeLevel);
            dao.save(newEntity);
        }
    }

    @Override
    public PageImpl getColumnPageList(Pageable pageable, PageImpl pageImpl, String superId, String columnName, String columnCode,String isShow) {
    	//总个数
		StringBuilder sb = new StringBuilder("select count(id) No from (");
		List<String> param = querySql(sb,superId,columnName,columnCode,isShow);
		sb.append(")");
		
		Integer total=jdbcTemplate.queryForObject(sb.toString(), Integer.class,param.toArray());
		//Map map=jdbcTemplate.queryForMap(sb.toString(), param.toArray());
		//查询分页数据
		 String pageSql = pageSql(pageable,superId,columnName,columnCode,isShow);
		 
		List<VideoColumn> listData =jdbcTemplate.query(pageSql.toString(), new RowMapper<VideoColumn>(){
			@Override
			public VideoColumn mapRow(ResultSet result, int arg1) throws SQLException {
				VideoColumn column = new VideoColumn();
				column.setId(result.getString("id"));
				column.setColumnName(result.getString("columnName"));
				column.setColumnCode(result.getString("columnCode"));
				column.setIsSp(result.getString("isSp"));
				column.setColumnGlUserNames(result.getString("columnGlUserNames"));
				column.setColumnRemark(result.getString("columnRemark"));
				column.setIsFbfw(result.getString("isFbfw"));
				column.setIsShow(result.getString("isShow"));
				return column;
			}
			
		},param.toArray());
		return new PageImpl(total,listData);

    }

    private String pageSql(Pageable pageable, String superId, String columnName, String columnCode,String isShow) {
    	StringBuilder sb=new StringBuilder("select * from ( select a1.*,ROWNUM RN from (");
		querySql(sb,superId,columnName,columnCode,isShow);
		sb.append("  ) a1 where ROWNUM <= " + (pageable.getOffset()+pageable.getPageSize()) + " ) where RN >=  " + (pageable.getOffset()+1));
		return sb.toString();
	}

	private List<String> querySql(StringBuilder sql, String superId, String columnName, String columnCode,String isShow) {
        List<String> param = new ArrayList<String>();
        sql.append(" select t.id,t.COLUMN_NAME columnName,t.COLUMN_CODE columnCode,t.is_sp isSp,t.COLUMN_GL_USER_NAMES columnGlUserNames,t.COLUMN_REMARK columnRemark,t.is_fbfw isFbfw,t.IS_SHOW isShow  ");
        sql.append(" from video_column t where visible= '1' ");
        if(StringUtils.isNotBlank(superId)){
            sql.append(" and trim(t.SUPER_ID) = ? ");
            param.add(superId);
        }else {
        	sql.append(" and t.SUPER_ID in (").append(" select t.id from video_column t where t.IS_FIRST='1' and t.visible='1' ").append(")");
        	
        }
        if(StringUtils.isNotBlank(isShow)){
            sql.append(" and t.IS_SHOW = ? ");
            param.add(isShow);
        }
        if(StringUtils.isNotBlank(columnName)){
            sql.append(" and t.COLUMN_NAME like ? ");
            param.add("%" + columnName + "%");
        }
        if(StringUtils.isNotBlank(columnCode)){
            sql.append(" and t.COLUMN_CODE = ? ");
            param.add(columnCode);
        }
        if(StringUtils.isNotBlank(UserUtil.getCruUserId())){
            String fbUserSql = " select distinct (u.column_id) column_id from video_column_fb_user u where u.visible = '1' and u.fb_user_id = '"+UserUtil.getCruUserId()+"'";
            String columnIds = "";
            List<Map<String,Object>> list = jdbcTemplate.queryForList(fbUserSql);
            for(Map<String,Object> map:list){
                columnIds+="'"+map.get("column_id")+"',";
            }
            if(StringUtils.isNotBlank(columnIds)) {
                columnIds = columnIds.substring(0, columnIds.length() - 1);
            }
            String roleNo = UserUtil.getCruUserRoleNo();
            if(roleNo.indexOf(VideoConstants.VIDEOFBGLYKEROLENO) <0  && roleNo.indexOf(VideoConstants.VIDEOFBGLYCHUROLENO)<0){
                sql.append(" and ( t.COLUMN_GL_USER_IDS like '%").append(UserUtil.getCruUserId()).append("%'");
                if(StringUtils.isNotBlank(columnIds)) {
                    sql.append(" or t.id in(").append(columnIds).append(") and t.is_sp='1' ");
                }
                sql.append(")");
            }
        }
        sql.append(" order by CRE_TIME ");
		return param;
	}

	@Override
    public VideoColumn saveFroms(VideoColumn entity,String isFirst) {
        VideoColumn entityDB = new VideoColumn();
        String cruDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try {
            if(StringUtils.isBlank(entity.getId())){
            	/*if("1".equals(entity.getIsFirst())){
            		entity.setNodeLevel("0");
            		entity.setOrderNo("0");
            	}*/
            	//新增栏目设置排序号
                entity.setVisible("1");
                entity.setCreUserId(UserUtil.getCruUserId());
                entity.setCreUserName(UserUtil.getCruUserName());
                entity.setCreChushiId(UserUtil.getCruChushiId());
                entity.setCreChushiName(UserUtil.getCruChushiName());
                entity.setCreJuId(UserUtil.getCruJuId());
                entity.setCreJuName(UserUtil.getCruJuName());
                entity.setCreTime(cruDate);
                entityDB = dao.save(entity);
            }else{
            	VideoColumn entity1 = dao.findOne(entity.getId());
            	entity1.setColumnName(entity.getColumnName());
            	entity1.setColumnGlUserIds(entity.getColumnGlUserIds());
            	entity1.setColumnGlUserNames(entity.getColumnGlUserNames());
            	entity1.setIsShow(entity.getIsShow());
            	entity1.setIsSp(entity.getIsSp());
            	entity1.setIsFbfw(entity.getIsFbfw());
            	entity1.setColumnRemark(entity.getColumnRemark());
                
            	entity1.setUpdateUserId(UserUtil.getCruUserId());
            	entity1.setUpdateUserName(UserUtil.getCruUserName());
            	entity1.setUpdateTime(cruDate);
                entityDB = dao.save(entity1);
            }
            List<VideoColumnFbUser> fbUserList = new ArrayList<VideoColumnFbUser>();
            if(entity.getFbUserList().size()>0){
                for (VideoColumnFbUser userEntity :entity.getFbUserList()){
                    userEntity.setColumnId(entityDB.getId());
                    userEntity.setCreTiem(JDateToolkit.getNowDate1());
                    userEntity.setCreUserId(UserUtil.getCruUserId());
                    userEntity.setCreUserName(UserUtil.getCruUserName());
                    userEntity.setVisible(CommonConstants.VISIBLE[1]);
                    userEntity = fbUserDao.save(userEntity);
                    fbUserList.add(userEntity);
                }
            }
            entityDB.setFbUserList(fbUserList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return entityDB;
    }

    @Override
    public VideoColumn getInfoClumnById(String id) {
    	VideoColumn entity = new VideoColumn();
        if(StringUtils.isNotBlank(id)){
            try{
                entity = dao.findOne(id);
                List<VideoColumnFbUser> fbUserList = fbUserDao.findByColumnIdAndVisible(id,CommonConstants.VISIBLE[1]);
                entity.setFbUserList(fbUserList);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return entity;
    }

	@Override
	public int delete(String ids) {
		int n = 0;
		try {
			if (StringUtils.isNotBlank(ids)) {
				String[] idsArry = ids.split(",");
				for (String id : idsArry) {
					// 删除栏目下的内容
					videoContentDao.delContentsByColumnId(id);
					n = dao.delVisible(id);
					fbUserDao.deleteAllVisible(id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}

    @Override
    public int deleteItme(String ids) {
        int n = 0;
        try{
            if(StringUtils.isNotBlank(ids)){
                String[] idsArry = ids.split(",");
                for(String id : idsArry){
                    n = fbUserDao.delVisible(id);
                }
            }
        }catch (Exception e){
         e.printStackTrace();
        }
        return n;
    }

    /**
     * 获取所有栏目
     * TODO 
     * @author 李利广
     * @Date 2018年9月18日 下午8:30:59
     * @return
     */
	@Override
	public List<VideoColumn> getAllColumn(String columnCode) throws Exception{
		List<String> params = new ArrayList<String>();
		StringBuilder sql = new StringBuilder("select i.id,i.super_id superId,i.node_level nodeLevel,i.column_name columnName,i.column_code columnCode from video_column i,info_column t");
		sql.append(" where t.column_code = ? and t.id = trim(i.super_id)");
		sql.append(" and i.visible = '1' and i.is_show = '1' order by i.order_no ");
		params.add(columnCode);
	    List<VideoColumn> query = jdbcTemplate.query(sql.toString(), params.toArray(),new BeanPropertyRowMapper<VideoColumn>(VideoColumn.class));
		return query;
	}

	 /**
     * 根据code获取一条栏目信息
     * TODO 
     * @author 李利广
     * @Date 2018年9月20日 下午3:58:36
     * @param columnCode
     * @return
     */
	@Override
	public VideoColumn getColumnByCode(String columnCode) throws Exception {
		return dao.findByColumnCode(columnCode);
	}

	@Override
	public List<VideoColumn> getHomePageColumns() {
		StringBuilder sb = new StringBuilder("select t.id,t.column_name from video_COLUMN t where t.visible='1' and trim(t.super_id) in (select a.id from video_COLUMN a where a.IS_FIRST = '1') and t.is_show='1'  order by  to_Number(t.order_no),t.cre_time ");
		List<VideoColumn> list = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<VideoColumn>(VideoColumn.class));
		return list;
	}
	 @Override
	    public boolean checkDic(String columnCold,String id) {
		    boolean valid = true;
		    if(StringUtils.isNotBlank(columnCold)){
		    	VideoColumn entity = dao.findByColumnCodeAndVisible(columnCold,CommonConstants.VISIBLE[1]);
	            if(entity != null){
	                if(!id.equals(entity.getId()) && columnCold.equals(entity.getColumnCode())){
	                    valid = false;
	                }
	            }
	        }
	        return valid;
	    }

	@Override
	public VideoColumn getById(String columnId) {
		
		return dao.getOne(columnId);
	}
	
	/**
	 * 栏目的排序列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年1月28日 下午2:47:19
	 * @param superId
	 * @return
	 */
	@Override
	public List<VideoColumn> getZdList(String superId) {
		List<Object> param = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder("select t.id,t.COLUMN_NAME columnName,t.COLUMN_GL_USER_NAMES glUserNames,t.IS_SHOW isShow from video_column t where t.visible='1' and t.is_show='1' ");
		if(StringUtils.isNotBlank(superId)){
			sb.append(" and trim(t.SUPER_ID) = ? ");
			param.add(superId);
        }else {
        	sb.append(" and t.SUPER_ID in (").append(" select t.id from video_column t where t.IS_FIRST='1' and t.visible='1' ").append(")");
        }
		sb.append(" order by to_Number(t.ORDER_NO),t.cre_time ");
		List<VideoColumn> list = jdbcTemplate.query(sb.toString(), new RowMapper<VideoColumn>() {
			@Override
			public VideoColumn mapRow(ResultSet result, int arg1) throws SQLException {
				VideoColumn column = new VideoColumn();
				column.setId(result.getString("id"));
				column.setColumnName(result.getString("columnName"));
				column.setColumnGlUserNames(result.getString("glUserNames"));
				column.setIsShow(result.getString("isShow"));
				return column;
			}
        	
        },param.toArray());
        return list;
		
	}
	
	/**
	 * 栏目的拖拽排序
	 * TODO 
	 * @author 马秋霞
	 * @Date 2019年1月28日 下午3:44:20
	 * @param ids
	 * @return
	 */
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
}
