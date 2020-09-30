package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.services.DwxtOrgService;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util.DzzUtil;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.dao.NdjhDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.dao.NdjhWordTableDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.entity.NdjhEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.entity.NdjhTjEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.entity.NdjhUnreportedEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.entity.NdjhWordtablEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.service.ZbdydhService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 年度计划主表Service
 * @Author: 李帅
 * @Date: 2018/8/30 9:35
 */
@Service
public class NdjhServiceImpl implements NdjhService {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private NdjhDao ndjhDao;
    @Autowired
    private ZbdydhService zbdydhService;
    @Autowired
    private NdjhWordTableDao ndjhWordTableDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DwxtOrgService dwxtOrgService;
    /**
     * 年度计划列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param pageable
     * @param pageImpl
     * @param ndjh
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, NdjhEntity ndjh, String startTime, String endTime,String typeVal,String ids) {

        StringBuilder dxzhSql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        dxzhSql.append(" from NdjhEntity t ");
        //querySql.append(" where t.creChushiId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        dxzhSql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        if(typeVal.equals("hz")){
            ids = DzzUtil.spiltString(ids);
            dxzhSql.append(" and t.partyOrganizationId in ("+ids+")");
          //  dxzhSql.append(" and t.creChushiId =  ("+UserUtil.getCruChushiId()+")");
        }else{
            dxzhSql.append(" and t.subflag =  ("+ ConfigConsts.APPROVAL_FLAG+")");
        }


        // 年度
        if (StringUtils.isNotBlank(ndjh.getAnnual())) {
            dxzhSql.append(" and substr(t.annual,1,10) between ? and ?");
            para.add(startTime);
            para.add(endTime);
        }

        // 地点
        if (StringUtils.isNotBlank(ndjh.getSubflag()) ) {
            dxzhSql.append(" and t.subflag = ?");
            para.add(ndjh.getSubflag());
        }

        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            dxzhSql.append(" order by t.creTime desc ");
        } else {
            dxzhSql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }

        Page<NdjhEntity> page = ndjhDao.query(dxzhSql.toString(), pageable, para.toArray());
        // 添加列表操作
        List<NdjhEntity> content = page.getContent();
        for (NdjhEntity dxzhVal : content) {
            String id = dxzhVal.getPartyOrganizationId()==null?"":(String)dxzhVal.getPartyOrganizationId();
            String  PartyOrganizationName = "";//组织名称
            String superName ="";//上一级组织名称
            List<Map<String,Object>> listOrgName = zbdydhService.getOrgName(id)==null? new ArrayList():zbdydhService.getOrgName(id);
            if(listOrgName.size()>0){
                PartyOrganizationName =listOrgName.get(0).get("ORG_NAME")==null?"":(String)listOrgName.get(0).get("ORG_NAME");
                superName=listOrgName.get(0).get("SUPER_ORG_NAME")==null?"":(String)listOrgName.get(0).get("SUPER_ORG_NAME");
            }
            dxzhVal.setSuperName(superName);
            dxzhVal.setPartyOrganizationName(PartyOrganizationName);
            if(dxzhVal.getSubflag().equals(ConfigConsts.START_FLAG)){
                dxzhVal.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);

            }
        }
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }

    /**
     * 年度计划保存
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param ndjhEntity
     * @return
     */
    @Override
    public NdjhEntity saveForm(NdjhEntity ndjhEntity) throws IOException {
        if(StringUtils.isBlank(ndjhEntity.getId())){
            //新增
            //设置常用字段开始
            ndjhEntity.setCreJuId(UserUtil.getCruJuId());
            ndjhEntity.setCreJuName(UserUtil.getCruJuName());
            ndjhEntity.setCreChushiId(UserUtil.getCruChushiId());
            ndjhEntity.setCreChushiName(UserUtil.getCruDeptName());
            ndjhEntity.setCreDeptId(UserUtil.getCruDeptId());
            ndjhEntity.setCreDeptName(UserUtil.getCruDeptName());
            ndjhEntity.setCreUserId(UserUtil.getCruUserId());
            ndjhEntity.setCreUserName(UserUtil.getCruUserName());
            ndjhEntity.setCreTime(JDateToolkit.getNowDate4());
            ndjhEntity.setPartyOrganizationName(java.net.URLDecoder.decode(ndjhEntity.getPartyOrganizationName(),"UTF-8"));
            ndjhEntity.setVisible(CommonConstants.VISIBLE[1]);
            Calendar date = Calendar.getInstance();
            String year = String.valueOf(date.get(Calendar.YEAR));
            ndjhEntity.setAnnual(year);
            //设置常用字段结束

            //年度计划主表
            NdjhEntity  oldNdjh = ndjhDao.save(ndjhEntity);
            //保存字表数据
            for(NdjhWordtablEntity ndjhWordtabl : ndjhEntity.getList()){
                //设置常用字段开始
                ndjhWordtabl.setCreJuId(UserUtil.getCruJuId());
                ndjhWordtabl.setCreJuName(UserUtil.getCruJuName());
                ndjhWordtabl.setCreChushiId(UserUtil.getCruChushiId());
                ndjhWordtabl.setCreChushiName(UserUtil.getCruDeptName());
                ndjhWordtabl.setCreDeptId(UserUtil.getCruDeptId());
                ndjhWordtabl.setCreDeptName(UserUtil.getCruDeptName());
                ndjhWordtabl.setCreUserId(UserUtil.getCruUserId());
                ndjhWordtabl.setCreUserName(UserUtil.getCruUserName());
                ndjhWordtabl.setCreTime(JDateToolkit.getNowDate4());
                ndjhWordtabl.setVisible(CommonConstants.VISIBLE[1]);
                if(ndjhWordtabl.getMonthval()!=null&&(!ndjhWordtabl.getMonthval().equals(""))){
                    ndjhWordtabl.setSort(new BigDecimal(ndjhWordtabl.getMonthval().substring(0,ndjhWordtabl.getMonthval().length()-1)));

                }

                //设置常用字段结束
                //设置关联
                ndjhWordtabl.setNdjhId(oldNdjh.getId());
                ndjhWordTableDao.save(ndjhWordtabl);
            }

            return oldNdjh;
        }else{
            //修改
            //更新试题内容
            NdjhEntity oldNdjh = ndjhDao.findOne(ndjhEntity.getId());
            oldNdjh.setSubflag(ndjhEntity.getSubflag());
            oldNdjh.setTitle(ndjhEntity.getTitle());
            oldNdjh.setPartyOrganizationName(oldNdjh.getPartyOrganizationName());
            oldNdjh.setPartyOrganizationId(oldNdjh.getPartyOrganizationId());
            oldNdjh.setReportingTime(ndjhEntity.getReportingTime());
            oldNdjh = ndjhDao.save(oldNdjh);

            //更新字表内容：1、逻辑删除之前的内容；2、插入新的内容
            String delNdjhWordTable = "update NdjhWordtablEntity o set o.visible="+CommonConstants.VISIBLE[0]+" where o.ndjhId='"+oldNdjh.getId()+"'";
            ndjhWordTableDao.update(delNdjhWordTable);

            for(NdjhWordtablEntity ndjhWordtabl : ndjhEntity.getList()){
                //设置常用字段开始
                ndjhWordtabl.setCreJuId(UserUtil.getCruJuId());
                ndjhWordtabl.setCreJuName(UserUtil.getCruJuName());
                ndjhWordtabl.setCreChushiId(UserUtil.getCruChushiId());
                ndjhWordtabl.setCreChushiName(UserUtil.getCruDeptName());
                ndjhWordtabl.setCreDeptId(UserUtil.getCruDeptId());
                ndjhWordtabl.setCreDeptName(UserUtil.getCruDeptName());
                ndjhWordtabl.setCreUserId(UserUtil.getCruUserId());
                ndjhWordtabl.setCreUserName(UserUtil.getCruUserName());
                ndjhWordtabl.setCreTime(JDateToolkit.getNowDate4());
                ndjhWordtabl.setNdjhId(oldNdjh.getId());
                ndjhWordtabl.setVisible(CommonConstants.VISIBLE[1]);
                if(ndjhWordtabl.getMonthval()!=null&&(!ndjhWordtabl.getMonthval().equals(""))){
                    ndjhWordtabl.setSort(new BigDecimal(ndjhWordtabl.getMonthval().substring(0,ndjhWordtabl.getMonthval().length()-1)));

                }
            }
            ndjhWordTableDao.save(ndjhEntity.getList());
            return oldNdjh;
        }
    }

    /**
     * 根据id逻辑一条年度计划
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param ndjhEntity
     * @return
     */
    @Override
    public int delete(NdjhEntity ndjhEntity) {
        int n = 0;
        if(StringUtils.isNotBlank(ndjhEntity.getId())){
            try {
                //逻辑年度计划主表内容
                String delQuestion = "update NdjhEntity n set n.visible="+CommonConstants.VISIBLE[0]+" where n.id='"+ndjhEntity.getId()+"'";
                n = ndjhDao.update(delQuestion);
                //逻辑删除年度计划字表内容
                String delOption = "update NdjhWordtablEntity o set o.visible="+CommonConstants.VISIBLE[0]+" where o.ndjhId='"+ndjhEntity.getId()+"'";
                ndjhDao.update(delOption);
            } catch (Exception e) {
                log.info(e.getMessage());
                log.info("年度计划出现异常！");
                n=0;
            }
        }
        return n;
    }

    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param id
     * @return
     */
    @Override
    public NdjhEntity getByIdVal(String id ) throws Exception {
        NdjhEntity ndjhEntity = new NdjhEntity();
        ndjhEntity = ndjhDao.findOne(id);
        if(id!=null){
            Sort sort = new Sort(Sort.Direction.ASC,"sort");
            List<NdjhWordtablEntity> ndjhWordList=   ndjhWordTableDao.findByNdjhIdAndVisible(id,CommonConstants.VISIBLE[1],sort);
            if(ndjhWordList.size()==0){
                String quarter ="";
                int val =0;
                for(int i=0;i<4;i++){
                    switch (i){
                        case 0: quarter ="第一季度";
                            val =1;
                            break;
                        case 1: quarter ="第二季度";
                            val =3;
                            break;
                        case 2: quarter ="第三季度";
                            val =5;
                            break;
                        case 3: quarter ="第四季度";
                            val =7;
                            break;
                    }
                    for(int k=0;k<3;k++){
                        NdjhWordtablEntity ndjhWord = new NdjhWordtablEntity();
                        ndjhWord.setQuarter(quarter);
                        ndjhWord.setMonthval((i+k+val)+"月");
                        ndjhWordList.add(ndjhWord);
                    }

                }


            }

            ndjhEntity.setList(ndjhWordList);
        }else{
            List<NdjhWordtablEntity> ndjhWordList  = new ArrayList<NdjhWordtablEntity>();
            String quarter ="";
            int val =0;
            for(int i=0;i<4;i++){
                switch (i){
                    case 0: quarter ="第一季度";
                        val =1;
                        break;
                    case 1: quarter ="第二季度";
                        val =3;
                        break;
                    case 2: quarter ="第三季度";
                        val =5;
                        break;
                    case 3: quarter ="第四季度";
                        val =7;
                        break;
                }
                for(int k=0;k<3;k++){
                    NdjhWordtablEntity ndjhWord = new NdjhWordtablEntity();
                    ndjhWord.setQuarter(quarter);
                    ndjhWord.setMonthval((i+k+val)+"月");
                    ndjhWordList.add(ndjhWord);
                }

            }
            ndjhEntity.setList(ndjhWordList);
        }

        return ndjhEntity;
    }
/**
 *
 * */


    /**
     * 新增时判断今年是否有年度计划
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param annual
     * @param partyOrganizationId
     * @return
     */
public NdjhEntity addFrom(String annual,String partyOrganizationId){
    NdjhEntity ndjh = new  NdjhEntity();
    List<NdjhEntity> ndjhList = new ArrayList<NdjhEntity>();
    ndjhList = ndjhDao.findByAnnual(annual,partyOrganizationId,"1")==null?new ArrayList<NdjhEntity>():ndjhDao.findByAnnual(annual,partyOrganizationId,"1");
   if(ndjhList.size()>0){
     ndjh = ndjhList.get(0)  ==null? new  NdjhEntity():ndjhList.get(0);
   }
    return ndjh;
}

    /**
     *年度计划统计
     * TODO
     * @author 李帅
     * @Date 2018年9月6日
     * @return
     * */
    public PageImpl Statistics(Pageable pageable,String sqlName, String startTime, String endTime){
        String sql ="";
        if(sqlName.equals("ndjh")){
            sql ="SELECT\n" +
                    "	*\n" +
                    "FROM\n" +
                    "	(\n" +
                    "		SELECT\n" +
                    "	         ANNUAL,\n" +
                    "			COUNT ( DISTINCT (ANNUAL||PARTY_ORGANIZATION_ID)) ANNUALNUMBER\n" +
                    "		FROM\n" +
                    "			DDJS_SHYK_ANNUALPLAN\n" +
                    "		WHERE\n" +
                    "			VISIBLE = '1'  \n" +
                    "		AND 	subflag = '5'  \n" ;
                    if((!startTime.equals(""))&&(!endTime.equals(""))){
                        sql+= " AND substr(ANNUAL, 1, 10) between "+startTime+" and "+endTime;
                    }
                 sql+=   "		GROUP BY\n" +
                    "			ANNUAL\n" +
                    "	)\n" +
                    "ORDER BY\n" +
                    "	TO_NUMBER (ANNUAL) DESC";
        }else  if(sqlName.equals("ndzj")){
            sql="SELECT\n" +
                    "	SUBSTR (REPORTING_TIME, 0, 4) ANNUAL,\n" +
                    "	COUNT (SUBSTR(REPORTING_TIME, 0, 4)) ANNUALNUMBER\n" +
                    "FROM\n" +
                    "	DDJS_SHYK_SUMMARY\n" +
                    "WHERE\n" +
                    "	VISIBLE = '1'\n" ;
            if((!startTime.equals(""))&&(!endTime.equals(""))){
                sql+= "AND  substr(SUBSTR (REPORTING_TIME, 0, 4), 1, 10) between "+startTime+" and "+endTime;
            }
                sql+=    "GROUP BY\n" +
                    "	SUBSTR (REPORTING_TIME, 0, 4)\n" +
                    "ORDER BY\n" +
                    "	TO_NUMBER (SUBSTR(REPORTING_TIME, 0, 4)) DESC";
        }
        String  shouldSql = "SELECT\n" +
                "	COUNT (ID) AS COUNTNUMBER\n" +
                "FROM\n" +
                "	ddjs_dzz_org\n" +
                "WHERE\n" +
                "	VISIBLE = '1'\n" +
                "and 	org_type = '631'";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        List<Map<String,Object>> shouldList = jdbcTemplate.queryForList(shouldSql);
        List<NdjhTjEntity> ndjhTjEntityList = new ArrayList<NdjhTjEntity>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        //当前年份
        String newYear = sdf.format(date);
        boolean flag =true;
        for(int i=0;i<list.size();i++){
            NdjhTjEntity ndjhTjEntity = new NdjhTjEntity();
            String  annual = list.get(i).get("ANNUAL")==null?"":(String)list.get(i).get("ANNUAL");
           BigDecimal annualNumber = (BigDecimal) list.get(i).get("ANNUALNUMBER");
            BigDecimal shouldNumber = BigDecimal.valueOf(0);
            if(shouldList.size()>0){
                shouldNumber = shouldList.get(0).get("COUNTNUMBER")==null?BigDecimal.valueOf(0):(BigDecimal)shouldList.get(0).get("COUNTNUMBER");
            }
            if(newYear.equals(annual)){
                flag=false;
            }
            ndjhTjEntity.setAnnual(annual);//年度
            ndjhTjEntity.setShould(shouldNumber);//应上报次数
            ndjhTjEntity.setNotReported(shouldNumber.subtract(annualNumber));//未上报次数
            ndjhTjEntity.setActualReport(annualNumber);//实际上报
            ndjhTjEntity.setId(i+1);
            ndjhTjEntity.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
            ndjhTjEntityList.add(ndjhTjEntity);
        }
        //如果本年没有计划，显示本年统计数
        if(flag){
            if((!startTime.equals(""))&&(!endTime.equals(""))){
                int startVal =Integer.parseInt(startTime);
                int endVal =Integer.parseInt(endTime);
                int annualVal = Integer.parseInt(newYear);
                if(!(annualVal>=startVal&&annualVal<=endVal)){
                    flag=false;
                }
            }
            if(flag){
                NdjhTjEntity ndjhTjEntity = new NdjhTjEntity();
                String  annual = newYear;
                BigDecimal annualNumber = BigDecimal.valueOf(0);
                BigDecimal shouldNumber = BigDecimal.valueOf(0);
                if(shouldList.size()>0){
                    shouldNumber = shouldList.get(0).get("COUNTNUMBER")==null?BigDecimal.valueOf(0):(BigDecimal)shouldList.get(0).get("COUNTNUMBER");
                }
                ndjhTjEntity.setAnnual(annual);//年度
                ndjhTjEntity.setShould(shouldNumber);//应上报次数
                ndjhTjEntity.setNotReported(shouldNumber.subtract(annualNumber));//未上报次数
                ndjhTjEntity.setActualReport(annualNumber);//实际上报
                ndjhTjEntity.setId(list.size()+1);
                ndjhTjEntity.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
                ndjhTjEntityList.add(ndjhTjEntity);
            }

        }
        List<NdjhTjEntity> ndjhTjEntityListPage = new ArrayList<NdjhTjEntity>();
        int k = 0;
        int n = pageable.getPageSize()*(pageable.getPageNumber());
        if(pageable.getPageSize()*(pageable.getPageNumber()+1)<ndjhTjEntityList.size()){
            k=pageable.getPageSize()*(pageable.getPageNumber()+1);
        }else{
            k=ndjhTjEntityList.size();
        }

        for(int i=n;i<k;i++ ){
            ndjhTjEntityListPage.add(ndjhTjEntityList.get(i)) ;
        }
        Page<NdjhUnreportedEntity> page = new org.springframework.data.domain.PageImpl(ndjhTjEntityListPage, pageable, ndjhTjEntityList.size());
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }
    /**
     *查询未上报的组织列表
     * TODO
     * @author 李帅
     * @Date 2018年9月6日
     * @param annual
     * @return
     * */
    public PageImpl getUnreported(Pageable pageable, String annual,String sqlName){
        String sql ="";
        if(sqlName.equals("ndjh")) {
            sql = "		SELECT\n" +
      //              "	         ANNUAL,\n" +
                    "	         PARTY_ORGANIZATION_ID,\n " +
                    "	         PARTY_ORGANIZATION_NAME\n" +
                    "	        FROM\n" +
                    "			DDJS_SHYK_ANNUALPLAN\n" +
                    "		WHERE\n" +
                    "			VISIBLE = '1'\n" +
                    "		AND 	subflag = '5'  \n" +
                    "  AND  ANNUAL ='" + annual + "'";
        }else if(sqlName.equals("ndzj")){
            sql ="SELECT\n" +
           //         "	SUBSTR (REPORTING_TIME, 0, 4) ANNUAL,\n" +
                    "	         PARTY_ORGANIZATION_ID,\n" +
                    "	         PARTY_ORGANIZATION_NAME\n" +
                    "FROM\n" +
                    "	DDJS_SHYK_SUMMARY\n" +
                    "WHERE\n" +
                    "	VISIBLE = '1'"+
                    "  AND  SUBSTR (REPORTING_TIME, 0, 4) ='" + annual + "'";
        }
        String sqlAll = "SELECT\n" +
                "	ID,\n" +
                "	ORG_ID,\n" +
                "	ORG_NAME\n" +
                "FROM\n" +
                "	DDJS_DZZ_ORG\n" +
                "WHERE\n" +
                "	VISIBLE = '1'\n" +
                "AND ORG_TYPE = '631'";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        List<Map<String,Object>> listAll = jdbcTemplate.queryForList(sqlAll);
        List<NdjhUnreportedEntity> ndjhUnreportedEntityList = new ArrayList<NdjhUnreportedEntity>();
        //已上报组织名称的集合
        List<String> haveList = new ArrayList<String>();
        //所有组织名称的集合
        List<String> andList = new ArrayList<String>();
        for(int i=0;i<list.size();i++){
          /*  String  nameVal = list.get(i).get("PARTY_ORGANIZATION_NAME")==null?"":(String)list.get(i).get("PARTY_ORGANIZATION_NAME");
            haveList.add(nameVal);*/
            String  idVal = list.get(i).get("PARTY_ORGANIZATION_ID")==null?"":(String)list.get(i).get("PARTY_ORGANIZATION_ID");
            haveList.add(idVal);
        }
        Map<String,Object> addMap = new HashMap();
        Map<String,Object> orgIdMap = new HashMap();
        for(int i=0;i<listAll.size();i++){
           /* String  nameVal = listAll.get(i).get("ORG_NAME")==null?"":(String)listAll.get(i).get("ORG_NAME");
            andList.add(nameVal);*/
            String  idVal = listAll.get(i).get("ID")==null?"":(String)listAll.get(i).get("ID");
            String  partyName = listAll.get(i).get("ORG_NAME")==null?"":(String)listAll.get(i).get("ORG_NAME");
            String  orgId = listAll.get(i).get("ID")==null?"":(String)listAll.get(i).get("ID");
            addMap.put(idVal,partyName);
            orgIdMap.put(idVal,orgId);
            andList.add(idVal);
        }
        andList.removeAll(haveList);
        for(int i=0;i<andList.size();i++){
            NdjhUnreportedEntity ndjhUnreportedEntity = new NdjhUnreportedEntity();
            ndjhUnreportedEntity.setAnnual(annual);//年度
            ndjhUnreportedEntity.setPartyOrganizationName((String)addMap.get(andList.get(i)));
            ndjhUnreportedEntity.setId(i+1);
            ndjhUnreportedEntity.setPartyOrganizationId((String)orgIdMap.get(andList.get(i)));
            ndjhUnreportedEntity.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
            ndjhUnreportedEntityList.add(ndjhUnreportedEntity);
        }
        List<NdjhUnreportedEntity> ndjhUnreportedEntityListPage = new ArrayList<NdjhUnreportedEntity>();
        int k = 0;
        int n = pageable.getPageSize()*(pageable.getPageNumber());
        if(pageable.getPageSize()*(pageable.getPageNumber()+1)<ndjhUnreportedEntityList.size()){
            k=pageable.getPageSize()*(pageable.getPageNumber()+1);
        }else{
            k=ndjhUnreportedEntityList.size();
        }

        for(int i=n;i<k;i++ ){
            ndjhUnreportedEntityListPage.add(ndjhUnreportedEntityList.get(i)) ;
        }
        Page<NdjhUnreportedEntity> page = new org.springframework.data.domain.PageImpl(ndjhUnreportedEntityListPage, pageable, ndjhUnreportedEntityList.size());
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }
    /**
     * @Description 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param id
     * @return
     */
    @Override
    public NdjhEntity getById1(String id ){
        return ndjhDao.findOne(id);
    }

    /**
     * @Description 更新业务表流程状态
     * @author 李帅
     * @Date 2018年9月28日
     * @param
     * @return
     */
    @Override
    public NdjhEntity updateFlag(String id, String subflag) {
        NdjhEntity outgoing = getById1(id);
        outgoing.setSubflag(subflag);
        return ndjhDao.save(outgoing);
    }
    /**
     * @Description 根据组织编码得到党务工作者ID
     * @author 李帅
     * @Date 2018年9月28日
     * @param
     * @return
     */
    public String selectUserId(String id){
        DwxtOrg dwxtOrg = dwxtOrgService.findOne(id);
        String userId ="";
        if(dwxtOrg!=null){
            userId = dwxtOrg.getAssociativeUserId()==null?"":dwxtOrg.getAssociativeUserId();
        }
        return userId;
    }
    /**
     * TODO 打开新增页面、只读页面时，查询数据进行渲染
     * @author 李帅
     * @Date 2018年8月30日
     * @param
     * @param id
     * @return
     */
    @Override
    public JSONObject getById(String id ) throws Exception {
        JSONObject jsonRow = new JSONObject();
        jsonRow.put("total","300");
        if(id!=null){
            Sort sort = new Sort(Sort.Direction.ASC,"sort");
            List<NdjhWordtablEntity> ndjhWordList=   ndjhWordTableDao.findByNdjhIdAndVisible(id,CommonConstants.VISIBLE[1],sort);
            if(ndjhWordList.size()==0){
                String quarter ="";
                int val =0;
                for(int i=0;i<4;i++){
                    switch (i){
                        case 0: quarter ="第一季度";
                            val =1;
                            break;
                        case 1: quarter ="第二季度";
                            val =3;
                            break;
                        case 2: quarter ="第三季度";
                            val =5;
                            break;
                        case 3: quarter ="第四季度";
                            val =7;
                            break;
                    }
                    for(int k=0;k<3;k++){
                        NdjhWordtablEntity ndjhWord = new NdjhWordtablEntity();
                        ndjhWord.setQuarter(quarter);
                        ndjhWord.setMonthval((i+k+val)+"月");
                        ndjhWordList.add(ndjhWord);
                    }

                }


            }

            jsonRow.put("rows",ndjhWordList);
        }else{
            List<NdjhWordtablEntity> ndjhWordList  = new ArrayList<NdjhWordtablEntity>();
            String quarter ="";
            int val =0;
            for(int i=0;i<4;i++){
                switch (i){
                    case 0: quarter ="第一季度";
                        val =1;
                        break;
                    case 1: quarter ="第二季度";
                        val =3;
                        break;
                    case 2: quarter ="第三季度";
                        val =5;
                        break;
                    case 3: quarter ="第四季度";
                        val =7;
                        break;
                }
                for(int k=0;k<3;k++){
                    NdjhWordtablEntity ndjhWord = new NdjhWordtablEntity();
                    ndjhWord.setQuarter(quarter);
                    ndjhWord.setMonthval((i+k+val)+"月");
                    ndjhWordList.add(ndjhWord);
                }

            }
            jsonRow.put("rows",ndjhWordList);
        }

        return jsonRow;
    }

}
