package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util.DzzUtil;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.dao.DrhdDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.entity.DrhdEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.service.DrhdService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.jtxx.dao.JtxxDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.jtxx.entity.JtxxEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.jtxx.service.JtxxService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.dao.ZbdydhDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.entity.HytjEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.entity.ZbdydhEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zzshh.dao.ZzshhDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zzshh.entity.ZzshhEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zzshh.service.ZzshhService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**TODO 三会一课 支部党员大会Service
 * @Author: 李帅
 * @Date: 2018/8/23 18:47
 */
@Service
public class ZbdydhServiceImpl implements ZbdydhService {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ZzshhDao zzshhDao;
    @Autowired
    private DrhdDao drhdDao;
    @Autowired
    private JtxxDao jtxxDao;
    @Autowired
    private ZbdydhDao zbdydhDao;
    @Autowired
    private ZzshhService zzshhService;
    @Autowired
    private JtxxService jtxxService;
    @Autowired
    private DrhdService drhdService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     *
     *支部党员大会 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/8/23
     * */
    public ZbdydhEntity saveZbdydh(ZbdydhEntity zbdydh) throws IOException {
        String id = zbdydh.getId();
        if (StringUtils.isBlank(id)) {
            zbdydh.setCreUserId(UserUtil.getCruUserId());
            zbdydh.setCreUserName(UserUtil.getCruUserName());
            zbdydh.setCreDeptId(UserUtil.getCruDeptId());
            zbdydh.setCreDeptName(UserUtil.getCruDeptName());
            zbdydh.setCreChushiId(UserUtil.getCruChushiId());
            zbdydh.setCreChushiName(UserUtil.getCruChushiName());
            zbdydh.setCreJuId(UserUtil.getCruJuId());
            zbdydh.setCreJuName(UserUtil.getCruJuName());
            zbdydh.setVisible(CommonConstants.VISIBLE[1]);
            zbdydh.setCreTime(JDateToolkit.getNowDate4());
            zbdydh.setPartyOrganizationName(java.net.URLDecoder.decode(zbdydh.getPartyOrganizationName(),"UTF-8"));
            ZbdydhEntity zbdydhVal = zbdydhDao.save(zbdydh);
            //获得会议类型
            List<String> list = new ArrayList();
            if(zbdydh.getMeetingType()!=null&&(!zbdydh.getMeetingType().equals(""))){
                String meetingTypeArray[] = (zbdydh.getMeetingType()).split(",");
                 list = Arrays.asList(meetingTypeArray);
            }

        //保存组织生活会
            if(list.contains("1")){
                ZzshhEntity zzssh = new ZzshhEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(zzssh,zbdydh);
                    zzssh.setTableName("DDJS_SHYK_PARTYCONGRESS");
                    zzssh.setTableId(zbdydhVal.getId());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                zzshhDao.save(zzssh);
            }
            //保存集体学习讨论
            if(list.contains("2")){
                JtxxEntity jtxx = new JtxxEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(jtxx,zbdydh);
                    jtxx.setTableName("DDJS_SHYK_PARTYCONGRESS");
                    jtxx.setTableId(zbdydhVal.getId());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                jtxxDao.save(jtxx);
            }
            //保存党日活动
            if(list.contains("3")){
                DrhdEntity drhd = new DrhdEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(drhd,zbdydh);
                    drhd.setTableName("DDJS_SHYK_PARTYCONGRESS");
                    drhd.setTableId(zbdydhVal.getId());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                drhdDao.save(drhd);
            }
            return zbdydhVal;
        } else {
            ZbdydhEntity oldZbdydh = zbdydhDao.findOne(zbdydh.getId());
            oldZbdydh.setCompere(zbdydh.getCompere());
            oldZbdydh.setNoteTaker(zbdydh.getNoteTaker());
            oldZbdydh.setNumberOfPeople(zbdydh.getNumberOfPeople());
            oldZbdydh.setActualNumber(zbdydh.getActualNumber());
            oldZbdydh.setPrimaryCoverage(zbdydh.getPrimaryCoverage());
            oldZbdydh.setAttendants(zbdydh.getAttendants());
            oldZbdydh.setLeaveAndReasons(zbdydh.getLeaveAndReasons());
            oldZbdydh.setSeats(zbdydh.getSeats());
            oldZbdydh.setMeetingSituation(zbdydh.getMeetingSituation());
            oldZbdydh.setPartyOrganizationName(java.net.URLDecoder.decode(zbdydh.getPartyOrganizationName(),"UTF-8"));
            oldZbdydh.setPartyOrganizationId(zbdydh.getPartyOrganizationId());
            oldZbdydh.setMeetingTime(zbdydh.getMeetingTime());
            oldZbdydh.setMeetingPlace(zbdydh.getMeetingPlace());
            oldZbdydh.setMeetingType(zbdydh.getMeetingType());
            oldZbdydh = zbdydhDao.save(oldZbdydh);
            //获得会议类型
            List<String> list = new ArrayList();
            if(zbdydh.getMeetingType()!=null&&(!zbdydh.getMeetingType().equals(""))){
                String meetingTypeArray[] = (zbdydh.getMeetingType()).split(",");
                list = Arrays.asList(meetingTypeArray);
            }
            //保存组织生活会
            if(list.contains("1")){
                ZzshhEntity zzsshCopy =zzshhDao.findByTableId(zbdydh.getId());
/*                zzsshCopy.setCompere(zbdydh.getCompere());
                zzsshCopy.setNoteTaker(zbdydh.getNoteTaker());
                zzsshCopy.setNumberOfPeople(zbdydh.getNumberOfPeople());
                zzsshCopy.setActualNumber(zbdydh.getActualNumber());
                zzsshCopy.setPrimaryCoverage(zbdydh.getPrimaryCoverage());
                zzsshCopy.setAttendants(zbdydh.getAttendants());
                zzsshCopy.setLeaveAndReasons(zbdydh.getLeaveAndReasons());
                zzsshCopy.setSeats(zbdydh.getSeats());
                zzsshCopy.setMeetingSituation(zbdydh.getMeetingSituation());
                zzsshCopy.setPartyOrganizationName(zbdydh.getPartyOrganizationName());
                zzsshCopy.setPartyOrganizationId(zbdydh.getPartyOrganizationId());
                zzsshCopy.setMeetingTime(zbdydh.getMeetingTime());
                zzsshCopy.setMeetingPlace(zbdydh.getMeetingPlace());
                zzshhDao.save(zzsshCopy);*/
                ZzshhEntity zzssh = new ZzshhEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(zzssh,zbdydh);
                    zzssh.setTableName("DDJS_SHYK_PARTYCONGRESS");
                    zzssh.setTableId(zbdydh.getId());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                if(zzsshCopy!=null){
                    zzssh.setId(zzsshCopy.getId());
                }else{
                    zzssh.setId(null);
                }
                zzshhService.saveZzshh(zzssh);
            }else{
                //更新时，如果去掉该会议类型则逻辑删除该条数据
                String delQuestion = "update ZzshhEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+zbdydh.getId()+"'";
                zzshhDao.update(delQuestion);
            }
            //保存集体学习讨论
            if(list.contains("2")){
                JtxxEntity jtxxCopy =jtxxDao.findByTableId(zbdydh.getId());
/*                jtxxCopy.setCompere(zbdydh.getCompere());
                jtxxCopy.setNoteTaker(zbdydh.getNoteTaker());
                jtxxCopy.setNumberOfPeople(zbdydh.getNumberOfPeople());
                jtxxCopy.setActualNumber(zbdydh.getActualNumber());
                jtxxCopy.setPrimaryCoverage(zbdydh.getPrimaryCoverage());
                jtxxCopy.setAttendants(zbdydh.getAttendants());
                jtxxCopy.setLeaveAndReasons(zbdydh.getLeaveAndReasons());
                jtxxCopy.setSeats(zbdydh.getSeats());
                jtxxCopy.setMeetingSituation(zbdydh.getMeetingSituation());
                jtxxCopy.setPartyOrganizationName(zbdydh.getPartyOrganizationName());
                jtxxCopy.setPartyOrganizationId(zbdydh.getPartyOrganizationId());
                jtxxCopy.setMeetingTime(zbdydh.getMeetingTime());
                jtxxCopy.setMeetingPlace(zbdydh.getMeetingPlace());
                jtxxDao.save(jtxxCopy);*/
                JtxxEntity jtxx = new JtxxEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(jtxx,zbdydh);
                    jtxx.setTableName("DDJS_SHYK_PARTYCONGRESS");
                    jtxx.setTableId(zbdydh.getId());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                if(jtxxCopy!=null){
                    jtxx.setId(jtxxCopy.getId());
                }else{
                    jtxx.setId(null);
                }
                jtxxService.saveJtxx(jtxx);
            }else{
                //更新时，如果去掉该会议类型则逻辑删除该条数据
                String delQuestion = "update JtxxEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+zbdydh.getId()+"'";
                jtxxDao.update(delQuestion);
            }
            //保存党日活动
            if(list.contains("3")){
                DrhdEntity drhdCopy =drhdDao.findByTableId(zbdydh.getId());
              /*  drhdCopy.setCompere(zbdydh.getCompere());
                drhdCopy.setNoteTaker(zbdydh.getNoteTaker());
                drhdCopy.setNumberOfPeople(zbdydh.getNumberOfPeople());
                drhdCopy.setActualNumber(zbdydh.getActualNumber());
                drhdCopy.setPrimaryCoverage(zbdydh.getPrimaryCoverage());
                drhdCopy.setAttendants(zbdydh.getAttendants());
                drhdCopy.setLeaveAndReasons(zbdydh.getLeaveAndReasons());
                drhdCopy.setSeats(zbdydh.getSeats());
                drhdCopy.setMeetingSituation(zbdydh.getMeetingSituation());
                drhdCopy.setPartyOrganizationName(zbdydh.getPartyOrganizationName());
                drhdCopy.setPartyOrganizationId(zbdydh.getPartyOrganizationId());
                drhdCopy.setMeetingTime(zbdydh.getMeetingTime());
                drhdCopy.setMeetingPlace(zbdydh.getMeetingPlace());
                drhdDao.save(drhdCopy);*/
                DrhdEntity drhd = new DrhdEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(drhd,zbdydh);
                    drhd.setTableName("DDJS_SHYK_PARTYCONGRESS");
                    drhd.setTableId(zbdydh.getId());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                if(drhdCopy!=null){
                    drhd.setId(drhdCopy.getId());
                }else{
                    drhd.setId(null);
                }
                drhdService.saveDrhd(drhd);
            }else{
                //更新时，如果去掉该会议类型则逻辑删除该条数据
                String delQuestion = "update DrhdEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+zbdydh.getId()+"'";
                drhdDao.update(delQuestion);
            }
            return oldZbdydh;
        }

    }

    /**
     * 支部党员大会列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年8月24日
     * @param pageable
     * @param pageImpl
     * @param zbdydh
     * @return
     */
    @Override
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, ZbdydhEntity zbdydh,String startTime, String endTime,String ids) {
        ids = DzzUtil.spiltString(ids);
        StringBuilder zbdydhSql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        zbdydhSql.append(" from ZbdydhEntity t ");
        //querySql.append(" where t.creChushiId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        zbdydhSql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        zbdydhSql.append(" and partyOrganizationId in ("+ids+")");

        // 时间
        if (StringUtils.isNotBlank(zbdydh.getMeetingTime())) {
            zbdydhSql.append(" and substr(t.meetingTime,1,10) between ? and ?");
            para.add(startTime);
            para.add(endTime);
        }

        // 地点
        if (StringUtils.isNotBlank(zbdydh.getMeetingPlace()) ) {
            zbdydhSql.append(" and t.meetingPlace like ?");
            para.add("%"+zbdydh.getMeetingPlace()+"%");
        }

        // 主持人
        if (StringUtils.isNotBlank(zbdydh.getCompere())) {
            zbdydhSql.append(" and t.compere like ?");
            para.add("%"+zbdydh.getCompere()+"%");
        }

        // 记录人
        if (StringUtils.isNotBlank(zbdydh.getNoteTaker())) {
            zbdydhSql.append(" and t.noteTaker like ?");
            para.add("%"+zbdydh.getNoteTaker()+"%");
        }

        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            zbdydhSql.append(" order by t.creTime desc ");
        } else {
            zbdydhSql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }

        Page<ZbdydhEntity> page = zbdydhDao.query(zbdydhSql.toString(), pageable, para.toArray());
        // 添加列表操作
        List<ZbdydhEntity> content = page.getContent();
        for (ZbdydhEntity zbdydhVal : content) {
            String id = zbdydhVal.getPartyOrganizationId()==null?"":(String)zbdydhVal.getPartyOrganizationId();
            String  PartyOrganizationName = "";//组织名称
            String superName ="";//上一级组织名称
            List<Map<String,Object>> listOrgName = this.getOrgName(id)==null? new ArrayList():this.getOrgName(id);
            if(listOrgName.size()>0){
                PartyOrganizationName =listOrgName.get(0).get("ORG_NAME")==null?"":(String)listOrgName.get(0).get("ORG_NAME");
                superName=listOrgName.get(0).get("SUPER_ORG_NAME")==null?"":(String)listOrgName.get(0).get("SUPER_ORG_NAME");
            }
            zbdydhVal.setSuperName(superName);
            zbdydhVal.setPartyOrganizationName(PartyOrganizationName);
            zbdydhVal.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
        }
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }


    /**
     * 根据id逻辑支部党员大会一条信息
     * TODO
     * @author 李帅
     * @Date 2018年8月24日
     * @param zbdydh
     * @return
     */
    @Override
    public int delete(ZbdydhEntity zbdydh) {
        int n = 0;
        ZbdydhEntity zbdydhVal =  zbdydhDao.findOne(zbdydh.getId());
        if(StringUtils.isNotBlank(zbdydh.getId())){
            try {
                //逻辑删除支部党员大会
                String delQuestion = "update ZbdydhEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.id='"+zbdydh.getId()+"'";
                n = zbdydhDao.update(delQuestion);
                List<String> list = new ArrayList();
                if(zbdydhVal.getMeetingType()!=null&&(!zbdydhVal.getMeetingType().equals(""))){
                    String meetingTypeArray[] = (zbdydhVal.getMeetingType()).split(",");
                    list = Arrays.asList(meetingTypeArray);
                }
                if(list.contains("1")){
                    //逻辑删除组织生活会
                    String delZzshh = "update ZzshhEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+zbdydh.getId()+"'";
                    zzshhDao.update(delZzshh);
                }
                if(list.contains("2")){
                    //逻辑删除集体学习讨论
                    String delJtxx = "update JtxxEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+zbdydh.getId()+"'";
                    jtxxDao.update(delJtxx);
                }
                if(list.contains("3")){
                    //逻辑删除党日活动
                    String deldrhd = "update DrhdEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+zbdydh.getId()+"'";
                    drhdDao.update(deldrhd);
                }

            } catch (Exception e) {
                log.info(e.getMessage());
                log.info("删除支部党员大会出现异常！");
                n=0;
            }
        }
        return n;
    }
    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年8月27日
     * @param id
     * @return
     */
    @Override
    public ZbdydhEntity getById(String id) throws Exception {
        return zbdydhDao.findOne(id);
    }


    /**
     *三会一课会议次数统计
     * TODO
     * @author 李帅
     * @Date 2018年9月7日
     * @return
     * */
    public PageImpl hytjStatistics(Pageable pageable,PageImpl pageImpl,String tableName, String startTime, String endTime,String ids){
        ids = DzzUtil.spiltString(ids);
        String  sql = "SELECT\n" +
                "	SUBSTR (MEETING_TIME, 0, 4) AS ANNUAL,\n" +
                "	PARTY_ORGANIZATION_ID AS PARTYID,\n" +
                "	COUNT (*) COUNTNUMBE\n" +
                "FROM\n" +
                tableName+"\n" +
                "WHERE \n" +
                "VISIBLE ='1'\n" ;
        if((!startTime.equals(""))&&(!endTime.equals(""))){
            sql+= "AND  substr(SUBSTR (MEETING_TIME, 0, 4), 1, 10) between "+startTime+" and "+endTime;
        }
            sql+=    " \nAND  PARTY_ORGANIZATION_ID IN("+ids+")\n"+
                    " \nGROUP BY\n" +
                "	PARTY_ORGANIZATION_ID,\n" +
                "	SUBSTR (MEETING_TIME, 0, 4)\n" ;

        if (StringUtils.isBlank(pageImpl.getSortName())) {
            sql+="ORDER BY\n" +
                    "	to_number(substr(MEETING_TIME,0,4)) DESC";
        } else {
            sql+="ORDER BY\n" +
                    "	to_number(substr(MEETING_TIME,0,4))" + pageImpl.getSortOrder()  ;
        }
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        List<HytjEntity> hytjEntityList = new ArrayList<HytjEntity>();
        for(int i=0;i<list.size();i++){
            HytjEntity hytjEntity = new HytjEntity();
            String  annual = list.get(i).get("ANNUAL")==null?"":(String)list.get(i).get("ANNUAL");
            String  partyId = list.get(i).get("PARTYID")==null?"":(String)list.get(i).get("PARTYID");
            String  partyName = "";//组织名称
            String superName ="";//上一级组织名称
            List<Map<String,Object>> listOrgName = this.getOrgName(partyId)==null? new ArrayList():this.getOrgName(partyId);
            if(listOrgName.size()>0){
               partyName =listOrgName.get(0).get("ORG_NAME")==null?"":(String)listOrgName.get(0).get("ORG_NAME");
               superName=listOrgName.get(0).get("SUPER_ORG_NAME")==null?"":(String)listOrgName.get(0).get("SUPER_ORG_NAME");
            }
            BigDecimal countNumber = list.get(i).get("COUNTNUMBE")==null?BigDecimal.valueOf(0):(BigDecimal)list.get(i).get("COUNTNUMBE");
            hytjEntity.setAnnual(annual);//年度
            hytjEntity.setActualReport(countNumber);//实际上报
            hytjEntity.setId(i+1);
            hytjEntity.setPartyOrganizationName(partyName);
            hytjEntity.setSuperName(superName);
            hytjEntityList.add(hytjEntity);
        }
        List<HytjEntity> hytjEntityListPage = new ArrayList<HytjEntity>();
        int k = 0;
        int n = pageable.getPageSize()*(pageable.getPageNumber());
        if(pageable.getPageSize()*(pageable.getPageNumber()+1)<hytjEntityList.size()){
            k=pageable.getPageSize()*(pageable.getPageNumber()+1);
        }else{
            k=hytjEntityList.size();
        }

        for(int i=n;i<k;i++ ){
            hytjEntityListPage.add(hytjEntityList.get(i)) ;
        }
        Page<HytjEntity> page = new org.springframework.data.domain.PageImpl(hytjEntityListPage, pageable, hytjEntityList.size());
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }
    /**
     *根据组织id得到组织名称
     * TODO
     * @author 李帅
     * @Date 2018年9月28日
     * @return
     * */
public List<Map<String,Object>> getOrgName(String id){
    String sql ="select\n" +
            "distinct(select ORG_NAME FROM DDJS_DZZ_ORG WHERE ID= '"+id+"') AS XX,\n" +
            "(select ORG_NAME FROM DDJS_DZZ_ORG WHERE ID= '"+id+"') AS ORG_NAME,\n" +
            "(CASE WHEN(select ASSOCIATIVE_UNIT_ID FROM DDJS_DZZ_ORG WHERE ID= '"+id+"') IS NULL THEN 'NO VALUE' ELSE (select ASSOCIATIVE_UNIT_ID FROM DDJS_DZZ_ORG WHERE ID= '"+id+"') END) AS ASSOCIATIVE_UNIT_ID,\n" +
           // "(select ASSOCIATIVE_UNIT_ID FROM DDJS_DZZ_ORG WHERE ID= '"+id+"') AS ASSOCIATIVE_UNIT_ID,\n" +
            "(select ORG_NAME FROM DDJS_DZZ_ORG where ID=( select SUPER_ID  FROM DDJS_DZZ_ORG WHERE ID= '"+id+"')) AS SUPER_ORG_NAME\n" +
            "from\n" +
            "DDJS_DZZ_ORG";
             List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
    return list;
}

}
