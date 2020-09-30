package com.sinosoft.sinoep.modules.info.xxfb.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.info.xxfb.common.InfoConstants;
import com.sinosoft.sinoep.modules.info.xxfb.dao.InfoColumnDao;
import com.sinosoft.sinoep.modules.info.xxfb.dao.InfoColumnFbUserDao;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumn;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumnFbUser;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumnTreeEntity;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;

import net.sf.json.JSONObject;

@Service
public class InfoColumnServiceImpl implements InfoColumnService{

    @Autowired
    private InfoColumnDao dao;

    @Autowired
    private InfoColumnFbUserDao fbUserDao;

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
            StringBuilder sql = new StringBuilder("select nvl(t.column_name,' ') columnName,t.id id,trim(t.super_id) superId,nvl(t.order_no,' ') orderNo,nvl(t.node_level,' ') nodeLevel " +
                    " from info_column t where t.visible = '1'" );
            String roleNo = UserUtil.getCruUserRoleNo();//获取当前人业务角色编号
            if(StringUtils.isNotBlank(userId) && roleNo.indexOf(InfoConstants.INFOFBGLYROLENO) < 0){
                sql.append(" and t.id in( select distinct (c.id) from info_column c where c.visible = '1' " +
                        "start with c.id in(select distinct(u.column_id) from info_column_fb_user u where u.visible='1'" +
                        " and u.fb_user_id ='").append(userId).append("' union "+
                        "select m.id from info_column m where m.visible='1' "+
                        "and m.column_gl_user_ids like '%").append(userId).append("%')");
                sql.append(" connect by prior trim(c.super_id) = c.id)");
            }
            if(StringUtils.isNotBlank(columnName)){
                sql.append(" and t.column_name like '%").append(columnName).append("%'");
            }
            sql.append(" or id ='001' order by t.order_no+0");
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
            List<InfoColumnTreeEntity> treeList = new ArrayList<InfoColumnTreeEntity>();
            for(Map<String ,Object> map : list){
                InfoColumnTreeEntity entity = new InfoColumnTreeEntity();
                entity.setId((String) map.get("ID"));
                entity.setpId((String) map.get("SUPERID"));
                entity.setName((String) map.get("COLUMNNAME"));
                entity.setNodeLevel((String) map.get("NODELEVEL"));
                entity.setOrderNo((String) map.get("ORDERNO"));
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
        InfoColumn entity = dao.findOne(dropId);
        entity.setSuperId(superId);
        entity.setNodeLevel(nodeLevel);
        dao.save(entity);
        for (int i = 0;i<ids.length;i++){
            InfoColumn newEntity = dao.findOne(ids[i]);
            newEntity.setOrderNo(String.valueOf(i));
            newEntity.setNodeLevel(nodeLevel);
            dao.save(newEntity);
        }
    }

    @Override
    public PageImpl getColumnPageList(Pageable pageable, PageImpl pageImpl, String superId, String columnName, String columnCode) {
        StringBuilder sql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        sql.append(" from InfoColumn t where visible= '1' ");
        if(!StringUtils.isNotBlank(superId)){
            superId = InfoConstants.XXFBONESUPERID;
        }
        sql.append(" and t.superId ='").append(superId).append("' ");
        if(StringUtils.isNotBlank(columnName)){
            sql.append(" and t.columnName like '%").append(columnName).append("%' ");
        }
        if(StringUtils.isNotBlank(columnCode)){
            sql.append(" and t.columnCode ='").append(columnCode).append("'");
        }
        if(StringUtils.isNotBlank(UserUtil.getCruUserId())){
            String fbUserSql = "select * from INFO_COLUMN where id in(" +
                    "select distinct (u.column_id) column_id from info_column_fb_user u where u.visible = '1' and u.fb_user_id = '"+UserUtil.getCruUserId()+"'" +
                    ") and VISIBLE ='1' and SUPER_ID ='"+superId+"'";
            String columnIds = "";
            List<Map<String,Object>> list = jdbcTemplate.queryForList(fbUserSql);
            for(Map<String,Object> map:list){
                columnIds+="'"+map.get("column_id")+"',";
            }
            if(StringUtils.isNotBlank(columnIds)) {
                columnIds = columnIds.substring(0, columnIds.length() - 1);
            }
            String roleNo = UserUtil.getCruUserRoleNo();
            if(roleNo.indexOf(InfoConstants.INFOFBGLYROLENO) <0){
                sql.append(" and ( t.columnGlUserIds like '%").append(UserUtil.getCruUserId()).append("%'");
                if(StringUtils.isNotBlank(columnIds)) {
                    sql.append(" or t.id in(").append(columnIds).append(")");
                }
                if(roleNo.indexOf(InfoConstants.INFOFBGLYROLENO) <0){
                    sql.append(")");
                }
            }
        }
        sql.append(" order by t.orderNo +0");
        try {
            Page<InfoColumn> page = dao.query(sql.toString(),pageable,para.toArray());
            List<InfoColumn> list = page.getContent();
            for(InfoColumn entity:list){
                String fbUserNames = "";
                List<InfoColumnFbUser> fbUserList = fbUserDao.findByColumnIdAndVisible(entity.getId(), CommonConstants.VISIBLE[1]);
                for(InfoColumnFbUser fbUserEntity:fbUserList){
                    fbUserNames += fbUserEntity.getFbUserName()+",";
                }
                if(StringUtils.isNotBlank(fbUserNames)) {
                    fbUserNames = fbUserNames.substring(0, fbUserNames.length() - 1);
                }
                entity.setFbUserList(fbUserList);
                entity.setColumnFbUserNames(fbUserNames);
            }
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
    public InfoColumn saveFroms(InfoColumn entity) {
        InfoColumn entityDB = new InfoColumn();
        try {
            if(StringUtils.isBlank(entity.getId())){
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
            List<InfoColumnFbUser> fbUserList = new ArrayList<InfoColumnFbUser>();
            if(entity.getFbUserList().size()>0){
                for (InfoColumnFbUser userEntity :entity.getFbUserList()){
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
    public InfoColumn getInfoClumnById(String id) {
        InfoColumn entity = new InfoColumn();
        if(StringUtils.isNotBlank(id)){
            try{
                entity = dao.findOne(id);
                List<InfoColumnFbUser> fbUserList = fbUserDao.findByColumnIdAndVisible(id,CommonConstants.VISIBLE[1]);
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
	public List<InfoColumn> getAllColumn(String columnCode) throws Exception{
		StringBuilder sql = new StringBuilder("select i.id,i.super_id superId,i.node_level nodeLevel,i.column_name columnName,i.column_code columnCode from info_column i,info_column t");
		sql.append(" where t.column_code = '"+columnCode+"' and t.id = trim(i.super_id)");
		sql.append(" and i.visible = '1' and i.is_show = '1' order by i.order_no ");
		List<InfoColumn> query = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<InfoColumn>(InfoColumn.class));
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
	public InfoColumn getColumnByCode(String columnCode) throws Exception {
		return dao.findByColumnCode(columnCode);
	}

    @Override
    public boolean checkDic(String columnCold,String id) {
	    boolean valid = true;
	    if(StringUtils.isNotBlank(columnCold)){
            InfoColumn entity = dao.findByColumnCodeAndVisible(columnCold,CommonConstants.VISIBLE[1]);
            if(entity != null){
                if(!id.equals(entity.getId()) && columnCold.equals(entity.getColumnCode())){
                    valid = false;
                }
            }
        }
        return valid;
    }
}
