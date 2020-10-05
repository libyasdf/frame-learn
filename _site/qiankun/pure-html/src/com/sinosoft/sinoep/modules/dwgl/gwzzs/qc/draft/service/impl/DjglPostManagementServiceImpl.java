package com.sinosoft.sinoep.modules.dwgl.gwzzs.qc.draft.service.impl;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dwgl.gwzzs.qc.draft.dao.DjglPostManagementDao;
import com.sinosoft.sinoep.modules.dwgl.gwzzs.qc.draft.entity.DjglPostManagementEntity;
import com.sinosoft.sinoep.modules.dwgl.gwzzs.qc.draft.service.DjglPostManagementService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class DjglPostManagementServiceImpl  implements DjglPostManagementService {
  private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DjglPostManagementDao djglPostManagementDao;
    private static Pattern pattern = Pattern.compile("(A001)|(A002)|(B001)|(B002)|(C001)|(C002)|(D001)|(D002)");

    @Autowired
    private JdbcTemplate jdbc;
 	/**
	 * 保存djgl_post_management 页面
	 * @author 
	 * @Date 2018-12-25 16:40:55
	 * @param entity
	 * @return
	 */
 	@Override
    public DjglPostManagementEntity save(DjglPostManagementEntity entity) throws IOException {
        String id = entity.getId();
        //新增时传啥保存成啥,只有如下列是在后台需要获取的(流程状态已经通过前台传过来了)
        if (StringUtils.isBlank(id)) {
            entity.setCreUserId(UserUtil.getCruUserId());
            entity.setCreUserName(UserUtil.getCruUserName());
            entity.setCreDeptId(UserUtil.getCruDeptId());
            entity.setCreDeptName(UserUtil.getCruDeptName());
            entity.setCreChushiId(UserUtil.getCruChushiId());
            entity.setCreChushiName(UserUtil.getCruChushiName());
            entity.setCreJuId(UserUtil.getCruJuId());
            entity.setCreJuName(UserUtil.getCruJuName());
            entity.setVisible(CommonConstants.VISIBLE[1]);
            entity.setCreTime(JDateToolkit.getNowDate4());

            String businessRoleNum="";
            String business=UserUtil.getCruUserRoleNo();
            //Pattern pattern = Pattern.compile("(A001)|(A002)|(B001)|(B002)|(C001)|(C002)|(D001)|(D002)");//此处用if去判断,这种方式无法先获取某,再获取某
            Matcher matcher = pattern.matcher(business);
            if(matcher.find()){
                businessRoleNum=matcher.group(0);//匹配到的第一个值
            }
            if(StringUtils.isNotBlank(businessRoleNum)){
                entity.setBusinessRoleNum(businessRoleNum);
            }

            entity = djglPostManagementDao.save(entity);
            return entity;
        } else {//更新
            DjglPostManagementEntity oldEntity = djglPostManagementDao.findOne(entity.getId());
            oldEntity.setCreTime(JDateToolkit.getNowDate4());//把创建时间更新,建议以后弄个更新时间列
 			oldEntity.setZzsName(entity.getZzsName());
 			oldEntity.setZzsSex(entity.getZzsSex());
 			oldEntity.setZzsAge(entity.getZzsAge());
 			oldEntity.setPoliticsStatus(entity.getPoliticsStatus());
 			oldEntity.setWorkUnit(entity.getWorkUnit());
 			oldEntity.setPositionRank(entity.getPositionRank());
 			oldEntity.setPartyPosts(entity.getPartyPosts());
 			oldEntity.setJobTitle(entity.getJobTitle());
 			oldEntity.setPostType(entity.getPostType());
 			oldEntity.setResponsibilities(entity.getResponsibilities());
 			oldEntity.setAimTask(entity.getAimTask());
 			oldEntity.setDegreePerformance(entity.getDegreePerformance());
 			oldEntity.setRewardsPunishment(entity.getRewardsPunishment());
 			oldEntity.setEvaluationResults(entity.getEvaluationResults());
 			oldEntity.setMyOpinion(entity.getMyOpinion());
 			oldEntity.setCompetentAdvice(entity.getCompetentAdvice());
 			oldEntity.setRemark(entity.getRemark());
 			//oldEntity.setSubflag(entity.getSubflag());//非第一次的保存执行此,流程状态不允许操作,一般也是传空
            if(StringUtils.isNotBlank(entity.getYearSubflag())){//第一次保存年底流程,因为数据已存在,赋年底流程状态走此
                oldEntity.setYearSubflag(entity.getYearSubflag());
            }
 			oldEntity.setZzsYear(entity.getZzsYear());
 			oldEntity.setVersionNumber(entity.getVersionNumber());
            oldEntity = djglPostManagementDao.save(oldEntity);
 			
            return oldEntity;
        }

	}

	
	 /**
     * 根据主键ID查询一条数据 
     * @author 
     * @Date 2018-12-25 16:40:55
     * @param id
     * @return
     */
 	@Override
    public DjglPostManagementEntity getById(String id) throws Exception {
        return djglPostManagementDao.findOne(id);
    }
    
     /**
     * 根据id逻辑删除djgl_post_management 对应列表
     * @author 
     * @Date 2018-12-25 16:40:55
     * @param entity
     * @return
     */
 	@Override
    public int delete(DjglPostManagementEntity entity) {
        int n = 0;
        if(StringUtils.isNotBlank(entity.getId())){
            try {
                //逻辑删除djgl_post_management
                String deleteSql = "update DjglPostManagementEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.id='"+entity.getId()+"'";
                n = djglPostManagementDao.update(deleteSql);
            } catch (Exception e) {
                log.info(e.getMessage());
                log.info("DjglPostManagementServiceImpl类的delete方法出现异常");
                n=0;
            }
        }
        return n;
    }

    /**
     * 修改标识
     * @param subflag
     * @param value
     * @param id
     * @return
     */
    @Override
    public int updateBySubflag(String subflag,String value,String id){
        int n = 0;
        if(StringUtils.isNotBlank(id)){
            try {
                //逻辑删除djgl_post_management
                String updateBySubflagSql = "update DjglPostManagementEntity q set q."+subflag+"=? where q.id='"+id+"'";
                n = djglPostManagementDao.update(updateBySubflagSql,value);
            } catch (Exception e) {
                log.info(e.getMessage());
                log.info("DjglPostManagementServiceImpl类的updateBySubflag方法出现异常！");
                n=0;
            }
        }
        return n;
    }
    /**
     *获取职责书创建人当前起草流程完成的最新同年数据
     * 用于年底流程加载初始数据
     */
    @Override
    public  DjglPostManagementEntity getNewEntity(DjglPostManagementEntity djglPostManagement){
        DjglPostManagementEntity en=null;
        try {
            //创建用户id
            String userID=UserUtil.getCruUserId();
            String getNewEntitySql = "select * from(select * from DJGL_POST_MANAGEMENT q  where q.visible='"+CommonConstants.VISIBLE[1]+"' and q.subflag='"+ ConfigConsts.APPROVAL_FLAG+"'"+
                    " and q.CRE_USER_ID='"+userID+"' and q.ZZS_YEAR='"+(new Date().getYear()+1900)+"' and (q.YEAR_SUBFLAG='"+ConfigConsts.START_FLAG+"' or q.YEAR_SUBFLAG is NULL ) ORDER BY q.CRE_TIME desc)where rownum=1";

            List<DjglPostManagementEntity> list =jdbc.query(getNewEntitySql,new BeanPropertyRowMapper<>(DjglPostManagementEntity.class));
            if(list.size()==1){
                en=list.get(0);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info("DjglPostManagementServiceImpl类的getNewEntity方法出现异常！");
        }
        return en;
    }
    /**
     *仅是根据时间返回最新数据
     *用于变更流程加载初始数据
     */
    @Override
    public  DjglPostManagementEntity getNewBGEntity(DjglPostManagementEntity djglPostManagement){
        DjglPostManagementEntity en=null;
        try {
            //创建用户id
            String userID=UserUtil.getCruUserId();
            String getNewBGEntitySql = " select * from(select * from DJGL_POST_MANAGEMENT q  where q.visible='"+CommonConstants.VISIBLE[1]+"' "+
                    " and q.CRE_USER_ID='"+userID+"' ORDER BY q.CRE_TIME desc)where rownum=1 ";
            List<DjglPostManagementEntity> list =jdbc.query(getNewBGEntitySql,new BeanPropertyRowMapper<>(DjglPostManagementEntity.class));
            if(list.size()==1){
                en=list.get(0);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info("DjglPostManagementServiceImpl类的getNewBGEntity方法出现异常！");
        }
        return en;
    }
     /**
     * djgl_post_management列表查询（带分页)
     * 用于年底流程和起草流程的草稿页面
     */
    @Override
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DjglPostManagementEntity entity,boolean identifying) {
        StringBuilder sql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        sql.append(" from DjglPostManagementEntity t ");
        sql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        if(identifying){//当是年底的流程,只查询起草流程通过的
            sql.append(" and t.subflag = '" + ConfigConsts.APPROVAL_FLAG+ "'");////subflag	yearSubflag
            sql.append(" and t.yearSubflag = '" + ConfigConsts.START_FLAG+ "'");//只查询草稿状态
        }else{
            sql.append(" and t.subflag = '" + ConfigConsts.START_FLAG+ "'");//只查询草稿状态
        }
        if (StringUtils.isNotBlank(entity.getZzsYear())) {//前端查询项即需自定义的条件项
            sql.append(" AND t.zzsYear=? ");
            para.add(entity.getZzsYear());
        }
        //String userID=UserUtil.getCruUserId(); //也可从cookie中获取
        if (StringUtils.isNotBlank(entity.getCreUserId())) {//前端查询项即需自定义的条件项
            sql.append(" AND t.creUserId=? ");
            para.add(entity.getCreUserId());
        }
        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {//若无排序字段
            sql.append(" order by t.creTime desc ");//默认排序方式
        } else {//前端点击某列进行排序
            sql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }

        Page<DjglPostManagementEntity> page = djglPostManagementDao.query(sql.toString(), pageable, para.toArray());
        // 添加列表操作
        List<DjglPostManagementEntity> content = page.getContent();
        for (DjglPostManagementEntity djglPostManagementEntity : content) {
            djglPostManagementEntity.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
        }
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }

    //个人查询
    @Override
    public PageImpl individualQuery(Pageable pageable, PageImpl pageImpl, DjglPostManagementEntity entity){
        StringBuilder sql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        sql.append(" from DjglPostManagementEntity t ");
        sql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        //只查年度节点通过的
        sql.append(" AND t.yearSubflag=? ");
        para.add(ConfigConsts.APPROVAL_FLAG);
        if (StringUtils.isNotBlank(entity.getZzsYear())) {//前端查询项即需自定义的条件项
            sql.append(" AND t.zzsYear=? ");
            para.add(entity.getZzsYear());
        }
        //String userID=UserUtil.getCruUserId(); //也可从cookie中获取
        if (StringUtils.isNotBlank(entity.getCreUserId())) {//前端查询项即需自定义的条件项
            sql.append(" AND t.creUserId=? ");
            para.add(entity.getCreUserId());
        }
        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {//若无排序字段
            sql.append(" order by t.creTime desc ");//默认排序方式
        } else {//前端点击某列进行排序
            sql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }

        Page<DjglPostManagementEntity> page = djglPostManagementDao.query(sql.toString(), pageable, para.toArray());
        // 添加列表操作
        List<DjglPostManagementEntity> content = page.getContent();
        for (DjglPostManagementEntity djglPostManagementEntity : content) {
            djglPostManagementEntity.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
        }
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }
    //根据部门id查询所有其下部门id
    @Override
    public  String getDepId(){
        String cruOrgId=UserUtil.getCruDeptId();
        String sql="SELECT d.DEPTID FROM UIAS.sys_flow_dept d where d.TREE_ID LIKE (SELECT d.TREE_ID||'%' FROM UIAS.sys_flow_dept d where d.DEPTID='"+cruOrgId+"')";
        List<Map<String, Object>> list=jdbc.queryForList(sql);
        StringBuilder depBuilder = new StringBuilder("'"+String.valueOf(list.get(0).get("DEPTID"))+"'");
        for(int i=1;i<list.size();i++){
            depBuilder.append(",'"+String.valueOf(list.get(i).get("DEPTID"))+"'");
        }
        return depBuilder.toString();
    }
    //范围|全局查询,自己写的分页
    @Override
    public Map rangeQuery(PageImpl pageImpl, DjglPostManagementEntity entity,String cxDeptid,boolean bol,boolean bolrange){

        Integer pagenum= pageImpl.getPageNumber();//页数(第一页返回1)
        Integer pageSize= pageImpl.getPageSize();//每页几个
        Integer startNum=(pagenum-1)*pageSize+1;
        Integer endNum=pagenum*pageSize;
        String selHead=" SELECT ROWNUM as rn,dj.* ",selHeadC=" SELECT count(1) as countt ";
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" FROM TJXM.DJGL_POST_MANAGEMENT dj INNER JOIN (SELECT ud.USERID FROM UIAS.sys_user_dprb  ud ");
        if(!bol){   //bol=true表查询全部部门(因为通过条件选的部门id,对应业务表的部门id)
            if (StringUtils.isNotBlank(cxDeptid)) {
                sqlBuilder.append(" where ud.DEPTID in("+cxDeptid+") ");
            }else{
                sqlBuilder.append(" where ud.DEPTID in("+getDepId()+") ");
            }
        }
        sqlBuilder.append(" ) sud  on sud.USERID=dj.CRE_USER_ID where 1=1 ");
        //只查起草节点通过的
        sqlBuilder.append(" AND dj.subflag ='"+ConfigConsts.APPROVAL_FLAG+"'");
        if (StringUtils.isNotBlank(entity.getCreUserName())) {
            sqlBuilder.append(" and dj.CRE_USER_NAME='"+entity.getCreUserName()+"' ");//前台姓名条件
        }
        if (StringUtils.isNotBlank(entity.getZzsYear())) {//前台时间条件
            sqlBuilder.append(" AND dj.ZZS_YEAR='"+entity.getZzsYear()+"' ");
        }
        //bolrange 是否是范围查询
        if(bolrange){
            String businessNum="";
            String business=UserUtil.getCruUserRoleNo();
            if(business.contains("A002")){
                businessNum="A001";
            }else if(business.contains("B002")){
                businessNum="B001";
            }else if(business.contains("C002")){
                businessNum="C001";
            }else if(business.contains("D002")){
                businessNum="D001";
            }
            if (StringUtils.isNotBlank(businessNum)) {
                sqlBuilder.append(" AND dj.BUSINESS_ROLE_NUM !='"+businessNum+"' or dj.BUSINESS_ROLE_NUM IS NULL ");
            }
        }
        if (StringUtils.isBlank(pageImpl.getSortName())) {//若无排序字段
            sqlBuilder.append(" ORDER BY dj.CRE_TIME desc ");//默认排序方式
        } else {//前端点击某列进行排序
            //实体类属性转换为列名
            sqlBuilder.append(" order by dj." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }
        String selBody=sqlBuilder.toString();
        String selPageS=" SELECT * FROM( ";
        String selPageE=" )t WHERE rn BETWEEN "+startNum+" and "+endNum+" ";

        List<Map<String, Object>> resuList=jdbc.queryForList(selPageS+selHead+selBody+selPageE);
        List<Map<String, Object>> resuCount=jdbc.queryForList(selHeadC+selBody);
        Map resultMap=new HashMap(8);
        resultMap.put("flag",'1');
        Map resultmap=new HashMap();
        resultmap.put("rows",resuList); //数据
        resultmap.put("total",resuCount.get(0).get("countt")); //总数
        resultMap.put("data",resultmap);

        return resultMap;

    }

}