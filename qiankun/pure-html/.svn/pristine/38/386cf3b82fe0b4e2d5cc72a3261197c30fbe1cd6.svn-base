package com.sinosoft.sinoep.modules.video.background.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.key.dwsurvey.entity.SurveyDirectory;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.video.background.common.VideoConstants;
import com.sinosoft.sinoep.modules.video.background.dao.VideoColumnDao;
import com.sinosoft.sinoep.modules.video.background.dao.VideoColumnFbUserDao;
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
    
    /**
     * 根据当前登录人获取信息发布栏目树数据
     * @param userId 当前登录人
     * @return
     */
    @Override
    public JSONObject getColumnTree(String columnName,String userId) {
        JSONObject json = new JSONObject();
        json.put("flag","0");
        try{
            StringBuilder sql = new StringBuilder("select nvl(t.column_name,' ') columnName,t.IS_FIRST isFirst,t.id id,trim(t.super_id) superId,nvl(t.order_no,' ') orderNo,nvl(t.code,' ') code,nvl(t.node_level,' ') nodeLevel " +
                    " from video_column t where t.visible = '1'" );
            String roleNo = UserUtil.getCruUserRoleNo();//获取当前人业务角色编号
            if(StringUtils.isNotBlank(userId) && (roleNo.indexOf(VideoConstants.VIDEOFBGLYKEROLENO) < 0 && roleNo.indexOf(VideoConstants.VIDEOFBGLYCHUROLENO)<0)){
                sql.append("  and t.id in( select distinct (c.id) from video_column c where c.visible = '1' " +
                        "start with c.id in(select id from video_column where  IS_SP = '1'  and id in(select distinct(u.column_id) from video_column_fb_user u where u.visible='1'" +
                        " and u.fb_user_id ='").append(userId).append("'  ").append(")");
                sql.append("  or   id in(select m.id from video_column m where m.visible = '1' and m.column_gl_user_ids like '%").append(userId).append("%')");
                sql.append(" )");
                sql.append(" connect by prior trim(c.super_id) = c.id)");
            }
            if(StringUtils.isNotBlank(columnName)){
                sql.append(" and t.column_name like '%").append(columnName).append("%'");
            }
            sql.append(" or is_first='1' order by t.order_no+0");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
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
    public PageImpl getColumnPageList(Pageable pageable, PageImpl pageImpl, String superId, String columnName, String columnCode) {
    	//总个数
		StringBuilder sb = new StringBuilder("select count(1) from (");
		querySql(sb,superId,columnName,columnCode);
		sb.append(")");
		Integer total=jdbcTemplate.queryForObject(sb.toString(), Integer.class);
		//查询分页数据
		 String pageSql = pageSql(pageable,superId,columnName,columnCode);
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
			
		});
		return new PageImpl(total,listData);

    }

    private String pageSql(Pageable pageable, String superId, String columnName, String columnCode) {
    	StringBuilder sb=new StringBuilder("select * from ( select a1.*,ROWNUM RN from (");
		querySql(sb,superId,columnName,columnCode);
		sb.append("  ) a1 where ROWNUM <= " + (pageable.getOffset()+pageable.getPageSize()) + " ) where RN >=  " + (pageable.getOffset()+1));
		return sb.toString();
	}

	private void querySql(StringBuilder sql, String superId, String columnName, String columnCode) {
        List<Object> para = new ArrayList<>();
        sql.append(" select t.id,t.COLUMN_NAME columnName,t.COLUMN_CODE columnCode,t.is_sp isSp,t.COLUMN_GL_USER_NAMES columnGlUserNames,t.COLUMN_REMARK columnRemark,t.is_fbfw isFbfw,t.IS_SHOW isShow  ");
        sql.append(" from video_column t where visible= '1' ");
        if(StringUtils.isNotBlank(superId)){
            sql.append(" and t.SUPER_ID ='").append(superId).append("' ");
        }else {
            sql.append(" and t.SUPER_ID = '").append(VideoConstants.VIDEOFBONESUPERID).append("'");
        }
        if(StringUtils.isNotBlank(columnName)){
            sql.append(" and t.COLUMN_NAME like '%").append(columnName).append("%' ");
        }
        if(StringUtils.isNotBlank(columnCode)){
            sql.append(" and t.COLUMN_CODE ='").append(columnCode).append("'");
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
        sql.append(" order by t.ORDER_NO ");
		
	}

	@Override
    public VideoColumn saveFroms(VideoColumn entity,String isFirst) {
        VideoColumn entityDB = new VideoColumn();
        try {
            if(StringUtils.isBlank(entity.getId())){
            	/*if("1".equals(entity.getIsFirst())){
            		entity.setNodeLevel("0");
            		entity.setOrderNo("0");
            	}*/
                entity.setVisible("1");
                entity.setCreUserId(UserUtil.getCruUserId());
                entity.setCreUserName(UserUtil.getCruUserName());
                entity.setCreChushiId(UserUtil.getCruChushiId());
                entity.setCreChushiName(UserUtil.getCruChushiName());
                entity.setCreJuId(UserUtil.getCruJuId());
                entity.setCreJuName(UserUtil.getCruJuName());
                entity.setCreTime(JDateToolkit.getNowDate1());
                entityDB = dao.save(entity);
            }else{
                entity.setVisible("1");
                entity.setCreUserId(UserUtil.getCruUserId());
                entity.setCreUserName(UserUtil.getCruUserName());
                entity.setCreChushiId(UserUtil.getCruChushiId());
                entity.setCreChushiName(UserUtil.getCruChushiName());
                entity.setCreJuId(UserUtil.getCruJuId());
                entity.setCreJuName(UserUtil.getCruJuName());
                entity.setCreTime(JDateToolkit.getNowDate1());
                entity.setUpdateUserId(UserUtil.getCruUserId());
                entity.setUpdateUserName(UserUtil.getCruUserName());
                entity.setUpdateTime(JDateToolkit.getNowDate1());
                entityDB = dao.save(entity);
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
        try{
            if(StringUtils.isNotBlank(ids)){
                String[] idsArry = ids.split(",");
                for(String id : idsArry){
                    n = dao.delVisible(id);
                    fbUserDao.deleteAllVisible(id);
                }
            }
        }catch (Exception e){
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
		StringBuilder sql = new StringBuilder("select i.id,i.super_id superId,i.node_level nodeLevel,i.column_name columnName,i.column_code columnCode from video_column i,info_column t");
		sql.append(" where t.column_code = '"+columnCode+"' and t.id = trim(i.super_id)");
		sql.append(" and i.visible = '1' and i.is_show = '1' order by i.order_no ");
		List<VideoColumn> query = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<VideoColumn>(VideoColumn.class));
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
		StringBuilder sb = new StringBuilder("select t.id,t.column_name from video_COLUMN t where t.visible='1' and trim(t.super_id) in (select a.id from video_COLUMN a where a.IS_FIRST = '1') and t.is_show='1' order by  t.order_no ");
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
}
