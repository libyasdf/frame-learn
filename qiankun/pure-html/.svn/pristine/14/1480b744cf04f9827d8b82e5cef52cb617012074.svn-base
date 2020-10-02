package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dfgl.service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglUserbasicinfoEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dfgl.dao.DfglDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dfgl.entity.DfglEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dfgl.entity.DzbDfgEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dzzgl.entity.DwxtOrg;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util.DzzUtil;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.service.ZbdydhService;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import workflow.common.JDateToolkit;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: 李帅
 * @Date: 2018/9/11 16:39
 */
@Service
public class DfglServiceImpl implements DfglServiceI {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DfglDao dfglDao;
    @Autowired
    private ZbdydhService zbdydhService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     *
     *党费管理 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/9/11
     * */
    public DfglEntity saveDfgl(DfglEntity dfgl)  throws IOException {
        List<DfglEntity> dfglList  = dfglDao.findByPartyIdAndAnnualAndMonthvalAndVisible(dfgl.getPartyId(),dfgl.getAnnual(),dfgl.getMonthval(),CommonConstants.VISIBLE[1]);
        if(dfglList.size()>0){
            String id =dfglList.get(0).getId();
            dfgl.setId(id);
        }else{
            dfgl.setId("");
        }
        String id = dfgl.getId();
        if (StringUtils.isBlank(id)) {
            dfgl.setCreUserId(UserUtil.getCruUserId());
            dfgl.setCreUserName(UserUtil.getCruUserName());
            dfgl.setCreDeptId(UserUtil.getCruDeptId());
            dfgl.setCreDeptName(UserUtil.getCruDeptName());
            dfgl.setCreChushiId(UserUtil.getCruChushiId());
            dfgl.setCreChushiName(UserUtil.getCruChushiName());
            dfgl.setCreJuId(UserUtil.getCruJuId());
            dfgl.setCreJuName(UserUtil.getCruJuName());
            dfgl.setVisible(CommonConstants.VISIBLE[1]);
            dfgl.setCreTime(JDateToolkit.getNowDate4());
            dfgl.setPartyOrganizationName(dfgl.getPartyOrganizationName());
            dfgl = dfglDao.save(dfgl);
            return dfgl;
        } else {
            DfglEntity oldDfgl = dfglDao.findOne(dfgl.getId());
            oldDfgl.setPartyOrganizationName(dfgl.getPartyOrganizationName());
            oldDfgl.setPartyOrganizationId(dfgl.getPartyOrganizationId());
            oldDfgl.setPartyId(dfgl.getPartyId());
            oldDfgl.setPartyName(dfgl.getPartyName());
            oldDfgl.setAnnual(dfgl.getAnnual());
            oldDfgl.setMonthval(dfgl.getMonthval());
            oldDfgl.setApprovedMonthPartyfee(dfgl.getApprovedMonthPartyfee());
            oldDfgl.setCurrentPaymentAmount(dfgl.getCurrentPaymentAmount());
            oldDfgl = dfglDao.save(oldDfgl);
            return oldDfgl;
        }

    }

    /**
     * 党费管理列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年9月11日
     * @param pageable
     * @param pageImpl
     * @param dfgl
     * @return
     */
    @Override
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DfglEntity dfgl, String startTime, String endTime,String ids,String type) {

        StringBuilder dfglSql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        String xx = UserUtil.getCruUserRoleNo();
        dfglSql.append(" from DfglEntity t ");
        //querySql.append(" where t.creChushiId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        dfglSql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        if(!type.equals("user")){
            ids = DzzUtil.spiltString(ids);
            dfglSql.append(" and partyOrganizationId in ("+ids+")");
        }else{
            dfglSql.append(" and t.partyId = '" + dfgl.getPartyId() + "'");
        }

        // 年份
        if (StringUtils.isNotBlank(dfgl.getAnnual())) {
            dfglSql.append(" and t.annual ='" + dfgl.getAnnual() + "'");
        }

        // 党组织名称
        if (StringUtils.isNotBlank(dfgl.getPartyOrganizationName()) ) {
            dfglSql.append(" and t.partyOrganizationName like ?");
            para.add("%"+dfgl.getPartyOrganizationName()+"%");
        }
        // 党员姓名
        if (StringUtils.isNotBlank(dfgl.getPartyName()) ) {
            dfglSql.append(" and t.partyName like ?");
            para.add("%"+dfgl.getPartyName()+"%");
        }
        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            dfglSql.append(" order by TO_NUMBER(t.annual) ,TO_NUMBER(t.monthval) desc ");
        } else {
            if(pageImpl.getSortName().equals("annual")||pageImpl.getSortName().equals("monthval")){
                dfglSql.append(" order by TO_NUMBER(t." + pageImpl.getSortName() + " )" + pageImpl.getSortOrder() + ") ");
            }else{
                dfglSql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
            }
        }

        Page<DfglEntity> page = dfglDao.query(dfglSql.toString(), pageable, para.toArray());
        // 添加列表操作
        List<DfglEntity> content = page.getContent();
        for (DfglEntity dfglVal : content) {
            String id = dfglVal.getPartyOrganizationId()==null?"":(String)dfglVal.getPartyOrganizationId();
            String  PartyOrganizationName = "";//组织名称
            String superName ="";//上一级组织名称
            List<Map<String,Object>> listOrgName = zbdydhService.getOrgName(id)==null? new ArrayList():zbdydhService.getOrgName(id);
            if(listOrgName.size()>0){
                PartyOrganizationName =listOrgName.get(0).get("ORG_NAME")==null?"":(String)listOrgName.get(0).get("ORG_NAME");
                superName=listOrgName.get(0).get("SUPER_ORG_NAME")==null?"":(String)listOrgName.get(0).get("SUPER_ORG_NAME");
            }
            dfglVal.setPartyOrganizationName(PartyOrganizationName);
            dfglVal.setSuperName(superName);
            dfglVal.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
        }
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }


    /**
     * 根据id逻辑党费管理一条信息
     * TODO
     * @author 李帅
     * @Date 2018年9月11日
     * @param dfgl
     * @return
     */
    @Override
    public int delete(DfglEntity dfgl) {
        int n = 0;
        if(StringUtils.isNotBlank(dfgl.getId())){
            try {
                //逻辑删除试题
                String delQuestion = "update DfglEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.id='"+dfgl.getId()+"'";
                n = dfglDao.update(delQuestion);
            } catch (Exception e) {
                log.info(e.getMessage());
                log.info("删除组织生活会出现异常！");
                n=0;
            }
        }
        return n;
    }
    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年9月11日
     * @param
     * @return
     */
    @Override
    public DfglEntity getById(String partyId,String annual,String month) throws Exception {
        //查询该月已交党费的员工信息
        String sqlDfgl = "select * from DDJS_DZZ_PARTYFEE where visible ="+ CommonConstants.VISIBLE[1] +" and PARTY_ID ='"+partyId+"' and ANNUAL = '"+annual+"' and MONTHVAL ='"+month+"' order by PARTY_ID";
        List<DfglEntity>  dfglEntityList =  jdbcTemplate.query(sqlDfgl,new BeanPropertyRowMapper<>(DfglEntity.class));
        DfglEntity dfglEntity = new DfglEntity();
        if(dfglEntityList.size()>0){
            dfglEntity = dfglEntityList.get(0)==null? new DfglEntity():dfglEntityList.get(0);
        }else{
            String sqlUser = "select * from DDJS_DYGL_USERBASICINFO where visible ="+ CommonConstants.VISIBLE[1] +" and ID ='"+partyId+"' and TYPE_OF_PERSONNEL like '%05%' order by ID";
            List<DdjsDyglUserbasicinfoEntity>  userEntityList =  jdbcTemplate.query(sqlUser,new BeanPropertyRowMapper<>(DdjsDyglUserbasicinfoEntity.class));
            if(userEntityList.size()>0){
                String monthVal ="";
                if(month.length()==1){
                    monthVal =annual+"-0"+month;
                }else{
                    monthVal =annual+"-"+month;
                }
                String idCar = userEntityList.get(0).getPinCodes()==null?"":(String)userEntityList.get(0).getPinCodes();
                String partyName = userEntityList.get(0).getName()==null?"":(String)userEntityList.get(0).getName();
                List<Map<String,Object>>  hddfList = this.getdf(idCar,monthVal)==null? new  ArrayList<Map<String,Object>>():this.getdf(idCar,monthVal);
                String approvedMonthPartyfee = "";
                if(hddfList.size()>0){
                    BigDecimal partyfee = hddfList.get(0).get("JS") ==null?BigDecimal.valueOf(0):(BigDecimal)hddfList.get(0).get("JS");
                    approvedMonthPartyfee =partyfee.toString();
                }
                dfglEntity.setAnnual(annual);
                dfglEntity.setMonthval(month);
                dfglEntity.setPartyId(partyId);
                dfglEntity.setPartyName(partyName);
                dfglEntity.setApprovedMonthPartyfee(approvedMonthPartyfee);
                dfglEntity.setCurrentPaymentAmount(approvedMonthPartyfee);
            }

        }
        return dfglEntity;
    }
    /**
     * 新增党费时判断该人员该月是否已交党费
     * TODO
     * @author 李帅
     * @Date 2018年8月30日
     * @param
     * @param
     * @return
     */
    public DfglEntity queryFee(DfglEntity dfgl){
        List<DfglEntity> feeList = dfglDao.findByPartyIdAndAnnualAndMonthvalAndVisible(dfgl.getPartyId(),dfgl.getAnnual(),dfgl.getMonthval(),CommonConstants.VISIBLE[1]);
        DfglEntity  entity = new DfglEntity();
        if(feeList.size()>0){
            entity = feeList.get(0)==null? new DfglEntity():feeList.get(0);
        }
        return entity;
    }
    /**
     * 新增党员所有月份的党费缴纳情况
     * */
 /*   public DfglNEntity saveDfgln (DfglNEntity dfgln) throws IOException{
        List<DfglEntity> dfglList =  new ArrayList<DfglEntity>();
        dfglList =  dfgln.getDfglList();
        List<DfglEntity> dfglListVal =  new ArrayList<DfglEntity>();
       for(int i=0;i<dfglList.size();i++){
           DfglEntity dfglEntity =dfglList.get(i);
           List<DfglEntity> dfglEntityList = dfglDao.findByPartyIdAndAnnualAndMonthvalAndVisible(dfglEntity.getPartyId(),dfglEntity.getAnnual(),dfglEntity.getMonthval(),CommonConstants.VISIBLE[1]);
           DfglEntity oldDfglEntity =new DfglEntity();
           if(dfglEntityList.size()>0){
               oldDfglEntity = dfglEntityList.get(0)==null? new DfglEntity():dfglEntityList.get(0);
           }

            dfglEntity.setId(oldDfglEntity.getId());
           dfglEntity =  this.saveDfgl(dfglEntity);
           dfglListVal.add(dfglEntity);
       }
        dfgln.setDfglList(dfglListVal);

        return dfgln;
    }*/
    /**
     * TODO 计算某一个人某个月的党费基数
     * @author 李帅
     * @Date 2018年8月30日
     * @param
     * @param
     * @return
     */
    public  List<Map<String,Object>> getdf (String idCar ,String month){
        String sql  ="select JOB_SALARY,LEVEL_SALARY,WORK_SALARY,LIFE_SALARY,ACCUMULATION_SALARY,MEDICAL_INSURANCE ,PENSION_ANNUITY,PERSONAL_INCOME_TAX, \n" +
                "ROUND(case when nvl(decode(x.JOB_SALARY+x.LEVEL_SALARY+x.WORK_SALARY+x.LIFE_SALARY-x.ACCUMULATION_SALARY-x.MEDICAL_INSURANCE-x.PENSION_ANNUITY-x.PERSONAL_INCOME_TAX,null,0,x.JOB_SALARY+x.LEVEL_SALARY+x.WORK_SALARY+x.LIFE_SALARY-x.ACCUMULATION_SALARY-x.MEDICAL_INSURANCE-x.PENSION_ANNUITY-x.PERSONAL_INCOME_TAX),0)<=3000\n" +
                "then nvl(decode(x.JOB_SALARY+x.LEVEL_SALARY+x.WORK_SALARY+x.LIFE_SALARY-x.ACCUMULATION_SALARY-x.MEDICAL_INSURANCE-x.PENSION_ANNUITY-x.PERSONAL_INCOME_TAX,null,0,x.JOB_SALARY+x.LEVEL_SALARY+x.WORK_SALARY+x.LIFE_SALARY-x.ACCUMULATION_SALARY-x.MEDICAL_INSURANCE-x.PENSION_ANNUITY-x.PERSONAL_INCOME_TAX),0)*0.005 \n" +
                " when nvl(decode(x.JOB_SALARY+x.LEVEL_SALARY+x.WORK_SALARY+x.LIFE_SALARY-x.ACCUMULATION_SALARY-x.MEDICAL_INSURANCE-x.PENSION_ANNUITY-x.PERSONAL_INCOME_TAX,null,0,x.JOB_SALARY+x.LEVEL_SALARY+x.WORK_SALARY+x.LIFE_SALARY-x.ACCUMULATION_SALARY-x.MEDICAL_INSURANCE-x.PENSION_ANNUITY-x.PERSONAL_INCOME_TAX),0)<=5000\n" +
                "then nvl(decode(x.JOB_SALARY+x.LEVEL_SALARY+x.WORK_SALARY+x.LIFE_SALARY-x.ACCUMULATION_SALARY-x.MEDICAL_INSURANCE-x.PENSION_ANNUITY-x.PERSONAL_INCOME_TAX,null,0,x.JOB_SALARY+x.LEVEL_SALARY+x.WORK_SALARY+x.LIFE_SALARY-x.ACCUMULATION_SALARY-x.MEDICAL_INSURANCE-x.PENSION_ANNUITY-x.PERSONAL_INCOME_TAX),0)*0.01 \n" +
                " when nvl(decode(x.JOB_SALARY+x.LEVEL_SALARY+x.WORK_SALARY+x.LIFE_SALARY-x.ACCUMULATION_SALARY-x.MEDICAL_INSURANCE-x.PENSION_ANNUITY-x.PERSONAL_INCOME_TAX,null,0,x.JOB_SALARY+x.LEVEL_SALARY+x.WORK_SALARY+x.LIFE_SALARY-x.ACCUMULATION_SALARY-x.MEDICAL_INSURANCE-x.PENSION_ANNUITY-x.PERSONAL_INCOME_TAX),0)<=10000\n" +
                "then nvl(decode(x.JOB_SALARY+x.LEVEL_SALARY+x.WORK_SALARY+x.LIFE_SALARY-x.ACCUMULATION_SALARY-x.MEDICAL_INSURANCE-x.PENSION_ANNUITY-x.PERSONAL_INCOME_TAX,null,0,x.JOB_SALARY+x.LEVEL_SALARY+x.WORK_SALARY+x.LIFE_SALARY-x.ACCUMULATION_SALARY-x.MEDICAL_INSURANCE-x.PENSION_ANNUITY-x.PERSONAL_INCOME_TAX),0)*0.015 \n" +
                "else nvl(decode(x.JOB_SALARY+x.LEVEL_SALARY+x.WORK_SALARY+x.LIFE_SALARY-x.ACCUMULATION_SALARY-x.MEDICAL_INSURANCE-x.PENSION_ANNUITY-x.PERSONAL_INCOME_TAX,null,0,x.JOB_SALARY+x.LEVEL_SALARY+x.WORK_SALARY+x.LIFE_SALARY-x.ACCUMULATION_SALARY-x.MEDICAL_INSURANCE-x.PENSION_ANNUITY-x.PERSONAL_INCOME_TAX),0)*0.02  end ,2)AS js\n" +
                "from GZGL_SALARY x "+
                " where SA_MONTH='"+month+"' and ID_CAR_NO ='"+idCar+"'";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
    /**
     * TODO 计算某一个党支部某个月所有党员的党费信息
     * @author 李帅
     * @Date 2018年11月27日
     * @param
     * @param
     * @return
     */
    public JSONObject getByIdVal(int pageNumber ,String ids , String month , String year,String opertation) {
        net.sf.json.JSONObject jsonRow = new net.sf.json.JSONObject();
        jsonRow.put("total","300");
        //查询该党支部所有的的党员
        ids = DzzUtil.spiltString(ids);
        String sqlUser = "select * from DDJS_DYGL_USERBASICINFO where visible ="+ CommonConstants.VISIBLE[1] +" and PARTY_ORGANIZATION_ID in ("+ids+") and (TYPE_OF_PERSONNEL like '%05%' OR TYPE_OF_PERSONNEL like '%04%') order by ID";
        List<DdjsDyglUserbasicinfoEntity>  userEntityList =  jdbcTemplate.query(sqlUser,new BeanPropertyRowMapper<>(DdjsDyglUserbasicinfoEntity.class));
        //查询该月已交党费的员工信息
        String sqlDfgl = "select * from DDJS_DZZ_PARTYFEE where visible ="+ CommonConstants.VISIBLE[1] +" and PARTY_ORGANIZATION_ID in ("+ids+") and ANNUAL = '"+year+"' and MONTHVAL ='"+month+"' order by PARTY_ID";
        List<DfglEntity>  dfglEntityList =  jdbcTemplate.query(sqlDfgl,new BeanPropertyRowMapper<>(DfglEntity.class));
       //得到某一个党支部本月所交党费之和
        String superNameSql = "select sum(CURRENT_PAYMENT_AMOUNT)AS superName from DDJS_DZZ_PARTYFEE where visible ="+ CommonConstants.VISIBLE[1] +" and PARTY_ORGANIZATION_ID in ("+ids+") and ANNUAL = '"+year+"' and MONTHVAL ='"+month+"'";
        List<Map<String,Object>> superNameList = jdbcTemplate.queryForList(superNameSql);
        String superNameVar ="";
        if(superNameList.size()>0){
            BigDecimal superNameBig = superNameList.get(0).get("superName") ==null?BigDecimal.valueOf(0):(BigDecimal)superNameList.get(0).get("superName");
            superNameVar = superNameBig.toString();
        }
        jsonRow.put("totalSum",superNameVar);
        //得到该党支部所有党员id
        List<String> userIdList = new ArrayList<String>();
        Map<String,Object> userIdCardMap = new HashMap();
        Map<String,Object> userNameMap = new HashMap();
        for(int i=0;i<userEntityList.size();i++){
            String partyId = userEntityList.get(i).getId() ==null?"":(String)userEntityList.get(i).getId();
            String idCard = userEntityList.get(i).getPinCodes() ==null?"":(String)userEntityList.get(i).getPinCodes();
            String name = userEntityList.get(i).getName() ==null?"":(String)userEntityList.get(i).getName();
            userIdList.add(partyId);
            userIdCardMap.put(partyId,idCard);
            userNameMap.put(partyId,name);
        }
        //得到所有已交党费党员id
        List<String> partyIdList = new ArrayList<String>();
        for(int i=0;i<dfglEntityList.size();i++){
            String partyId = dfglEntityList.get(i).getPartyId() ==null?"":(String)dfglEntityList.get(i).getPartyId();
            partyIdList.add(partyId);
        }
        //去除所有已交党费人员
        userIdList.removeAll(partyIdList);
        //计算未交党费人员的核定党费数额
        for(int i=0;i<userIdList.size();i++){
            String monthVal ="";
            if(month.length()==1){
                monthVal =year+"-0"+month;
            }else{
                monthVal =year+"-"+month;
            }
            String partyId = userIdList.get(i) ==null?"":userIdList.get(i);
            String idCar = userIdCardMap.get(partyId)==null?"":(String)userIdCardMap.get(partyId);
            String partyName = userNameMap.get(partyId)==null?"":(String)userNameMap.get(partyId);
            List<Map<String,Object>>  hddfList = this.getdf(idCar,monthVal)==null? new  ArrayList<Map<String,Object>>():this.getdf(idCar,monthVal);
            String approvedMonthPartyfee = "";
            if(hddfList.size()>0){
                BigDecimal partyfee = hddfList.get(0).get("JS") ==null?BigDecimal.valueOf(0):(BigDecimal)hddfList.get(0).get("JS");
                approvedMonthPartyfee =partyfee.toString();
            }
            DfglEntity dafglEntity = new  DfglEntity();
            dafglEntity.setAnnual(year);
            dafglEntity.setMonthval(month);
            dafglEntity.setPartyId(partyId);
            dafglEntity.setPartyName(partyName);
            dafglEntity.setApprovedMonthPartyfee(approvedMonthPartyfee);
            if(opertation.equals("VIEW")){
                dafglEntity.setCurrentPaymentAmount("");
            }else{
                dafglEntity.setCurrentPaymentAmount(approvedMonthPartyfee);
            }
            dfglEntityList.add(dafglEntity);
        }
         if(pageNumber!=3){
             int pageSizeVal  = (int)dfglEntityList.size()%3;
             int pageSize  = dfglEntityList.size()/3;
             if(pageSizeVal!=0){
                 pageSize =pageSize+1;
             }
            if(pageNumber==1){
                dfglEntityList = dfglEntityList.subList(0,pageSize);
            }else {
              /*  if(dfglEntityList.size()>2){
                    dfglEntityList = dfglEntityList.subList(pageSize,(2*pageSize));
                }else if(dfglEntityList.size()==2) {
                    dfglEntityList = dfglEntityList.subList(pageSize,dfglEntityList.size());
                }else{
                    dfglEntityList = new ArrayList<DfglEntity>() ;
                } */
                if(dfglEntityList.size()>1){
                    if(pageSizeVal==0){
                        dfglEntityList = dfglEntityList.subList(pageSize,(dfglEntityList.size()-pageSize));
                    }else {
                        dfglEntityList = dfglEntityList.subList(pageSize, (dfglEntityList.size()-pageSize+1));
                    }
                }else{
                    dfglEntityList = new ArrayList<DfglEntity>() ;
                }

            }
         }else {
             int pageSizeVal  = (int)dfglEntityList.size()%3;
             int pageSize  = dfglEntityList.size()/3;
             if(pageSizeVal!=0){
                 pageSize =pageSize+1;
             }
           /*  if(dfglEntityList.size()>4||dfglEntityList.size()==3){
                 int pageSize  = (int)Math.ceil(dfglEntityList.size()/3);
                 pageSize=dfglEntityList.size()-(pageSize*2);
                 dfglEntityList = dfglEntityList.subList(pageSize+1,dfglEntityList.size());
             }else{
                 dfglEntityList = new ArrayList<DfglEntity>() ;
             }*/
             if(dfglEntityList.size()>2){
                 if(pageSizeVal==0){
                     dfglEntityList = dfglEntityList.subList((dfglEntityList.size()-pageSize),dfglEntityList.size());
                 }else {
                     dfglEntityList = dfglEntityList.subList( (dfglEntityList.size()-pageSize+1),dfglEntityList.size());
                 }
             }else{
                 dfglEntityList = new ArrayList<DfglEntity>() ;
             }
         }
         jsonRow.put("rows",dfglEntityList);

        return jsonRow;
    }

    /**
     * 按党支部保存党费数据
     * TODO
     * @author 李帅
     * @Date 2018年11月27日
     * @param
     * @return
     */
    @Override
    public DzbDfgEntity saveDzbForm(DzbDfgEntity dzbDfgl)throws IOException {
            //保存字表数据
            List<DfglEntity>  dfglEntityList = new ArrayList();
            List<DfglEntity>  listDfglEntity = new ArrayList();
            listDfglEntity.addAll(dzbDfgl.getDfglEntityList());
            listDfglEntity.addAll(dzbDfgl.getDfglEntityListOne());
            listDfglEntity.addAll(dzbDfgl.getDfglEntityListTwo());
            for(DfglEntity dfglEntity : listDfglEntity){
              /*  //设置常用字段开始
                List<DfglEntity> dfglList  = dfglDao.findByPartyIdAndAnnualAndMonthvalAndVisible(dfglEntity.getPartyId(),dzbDfgl.getAnnual(),dzbDfgl.getMonthval(),CommonConstants.VISIBLE[1]);
                if(dfglList.size()>0){
                    String id =dfglList.get(0).getId();
                    dfglEntity.setId(id);
                }*/
                dfglEntity.setAnnual(dzbDfgl.getAnnual());
                dfglEntity.setMonthval(dzbDfgl.getMonthval());
                dfglEntity.setPartyOrganizationName(dzbDfgl.getPartyOrganizationName());
                dfglEntity.setPartyOrganizationId(dzbDfgl.getPartyOrganizationId());
                dfglEntity =this.saveDfgl(dfglEntity);
                dfglEntityList.add(dfglEntity);
            }
            dzbDfgl.setId("1");
            dzbDfgl.setDfglEntityList(dfglEntityList);
            return dzbDfgl;

    }
    /**
     * TODO 在党支部视角查看的list列表
     * @author 李帅
     * @Date 2018年11月28日
     * @param
     * @param
     * @return
     */
    public JSONObject getDzbByIdVal(String PartyOrganizationId ,String annual,String ids) {
        net.sf.json.JSONObject jsonRow = new net.sf.json.JSONObject();
        String  partyOrganizationName = "";//组织名称
        String superName ="";//上一级组织名称
        ids = DzzUtil.spiltString(ids);
        List<Map<String,Object>> listOrgName = zbdydhService.getOrgName(PartyOrganizationId)==null? new ArrayList():zbdydhService.getOrgName(PartyOrganizationId);
        if(listOrgName.size()>0){
           partyOrganizationName =listOrgName.get(0).get("ORG_NAME")==null?"":(String)listOrgName.get(0).get("ORG_NAME");
            superName=listOrgName.get(0).get("SUPER_ORG_NAME")==null?"":(String)listOrgName.get(0).get("SUPER_ORG_NAME");
        }
        String sql ="SELECT\n" +
                "	MONTHVAL,\n" +
                "	ANNUAL,\n" +
                "	COUNT(ID) AS currentNumber,\n" +
                "	sum(APPROVED_MONTH_PARTYFEE) AS yearApprovedMonthPartyfee,\n" +
                "	sum(CURRENT_PAYMENT_AMOUNT) AS yearCurrentPaymentAmount\n" +
                "FROM\n" +
                "	DDJS_DZZ_PARTYFEE\n" +
                "WHERE\n" +
                "	visible = '1'\n" +
                "AND PARTY_ORGANIZATION_ID in ("+ids+")\n" +
                "AND CURRENT_PAYMENT_AMOUNT != '"+0+"'\n" ;
                if (StringUtils.isNotBlank(annual)) {
                    sql +=  "AND ANNUAL = '"+annual+"'\n" ;
                }
                sql += "GROUP BY\n" +
                    "	ANNUAL,"+
                    "	MONTHVAL\n"+
                        "ORDER BY\n" +
                        "TO_NUMBER(ANNUAL),\n"+
                        "TO_NUMBER(MONTHVAL)\n"+
                        "DESC";
      //  List<Map<String,Object>> superNameList = jdbcTemplate.queryForList(sql);
       List<DzbDfgEntity>  dfglEntityList =  jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DzbDfgEntity.class));
       //查询该党支部所有的的党员
        String sqlUser ="select * from DDJS_DYGL_USERBASICINFO where visible ="+CommonConstants.VISIBLE[1]+" and PARTY_ORGANIZATION_ID in ("+ids+") and (TYPE_OF_PERSONNEL like '%05%' OR TYPE_OF_PERSONNEL like '%04%') order by ID";
        List<DdjsDyglUserbasicinfoEntity>  userEntityList =  jdbcTemplate.query(sqlUser,new BeanPropertyRowMapper<>(DdjsDyglUserbasicinfoEntity.class));

        //得到该党支部所有党员id
        List<String> userIdList = new ArrayList<String>();
        List<String> userEntityListAll = new ArrayList();
        Map<String,Object> userIdCardMap = new HashMap();
        for(int i=0;i<userEntityList.size();i++){
            String partyId = userEntityList.get(i).getId()==null?"":(String)userEntityList.get(i).getId();
            userIdList.add(partyId);
            String idCard = userEntityList.get(i).getPinCodes()==null?"":(String)userEntityList.get(i).getPinCodes();
            userIdCardMap.put(partyId,idCard);
        }
        int number =0;
        for(DzbDfgEntity dzbDfgEntity :dfglEntityList){
            number =number+1;
            dzbDfgEntity.setId(number+"");
            userEntityListAll.addAll(userIdList);
            List<DfglEntity> dfglListEntity = new ArrayList();
            //查询该月已交党费的员工信息
            String sqlDfgl = "select * from DDJS_DZZ_PARTYFEE where  visible ="+CommonConstants.VISIBLE[1]+" and PARTY_ORGANIZATION_ID in ("+ids+") and ANNUAL ='"+dzbDfgEntity.getAnnual()+"'and MONTHVAL='"+dzbDfgEntity.getMonthval()+"' order by ID";
             dfglListEntity =  jdbcTemplate.query(sqlDfgl,new BeanPropertyRowMapper<>(DfglEntity.class));
            //得到所有已交党费党员id
            List<String> partyIdList = new ArrayList<String>();
            for(int i=0;i<dfglListEntity.size();i++){
                String partyId = dfglListEntity.get(i).getPartyId()==null?"":(String)dfglListEntity.get(i).getPartyId();
                partyIdList.add(partyId);
           }
            //去除所有已交党费人员
            userEntityListAll.removeAll(partyIdList);
            //计算未交党费人员的核定党费数额
            BigDecimal approvedMonthPartyfeeVal = new BigDecimal(dzbDfgEntity.getYearApprovedMonthPartyfee()==null?"0":dzbDfgEntity.getYearApprovedMonthPartyfee());
            for(int i=0;i<userEntityListAll.size();i++){
                String monthVal="";
                String month = dzbDfgEntity.getMonthval();
                String year = dzbDfgEntity.getAnnual();
                if(month.length()==1){
                    monthVal =year+"-0"+month;
                }else{
                    monthVal =year+"-"+month;
                }
                String partyId = userEntityListAll.get(i)==null?"":userEntityListAll.get(i);
                String idCar = userIdCardMap.get(partyId)==null?"":(String)userIdCardMap.get(partyId);
                List<Map<String,Object>> hddfList = this.getdf(idCar,monthVal)==null? new ArrayList<Map<String,Object>>():this.getdf(idCar,monthVal);
                if(hddfList.size()>0){
                    BigDecimal partyfee = hddfList.get(0).get("JS") ==null?BigDecimal.valueOf(0):(BigDecimal)hddfList.get(0).get("JS");
                    approvedMonthPartyfeeVal = approvedMonthPartyfeeVal.add(partyfee);
                }
            }
            dzbDfgEntity.setYearApprovedMonthPartyfee(approvedMonthPartyfeeVal.toString());
            dzbDfgEntity.setPartyOrganizationId(PartyOrganizationId);
            dzbDfgEntity.setPartyOrganizationName(partyOrganizationName);
            dzbDfgEntity.setSuperName(superName);
            int approvedNumber = userEntityListAll.size()+partyIdList.size();
            dzbDfgEntity.setApprovedNumber(approvedNumber+"");//应交多少人
            dzbDfgEntity.setUnpaidNumber(userEntityListAll.size()+"");//未交多少人
            userEntityListAll.removeAll(userEntityListAll);
            partyIdList.removeAll(partyIdList);
        }
        jsonRow.put("total",dfglEntityList.size());
        jsonRow.put("rows",dfglEntityList);
        return jsonRow;
    }

    /**
     * TODO 得到某一个党支部有多少个人
     * @author 李帅
     * @Date 2018年11月29日
     * @param
     * @param
     * @return
     */
    public BigDecimal getTotal(String ids , String month , String year) {
        net.sf.json.JSONObject jsonRow = new net.sf.json.JSONObject();
        //查询当前月有多少人
        ids = DzzUtil.spiltString(ids);
        String sqlUser = "select count(id) AS TOTAL from DDJS_DYGL_USERBASICINFO where visible ="+ CommonConstants.VISIBLE[1] +" and PARTY_ORGANIZATION_ID in ("+ids+") and TYPE_OF_PERSONNEL like '%05%'";
        List<Map<String,Object>> totalList = jdbcTemplate.queryForList(sqlUser);
        //查询当前月除外的月份有多少人
        String sqlDfgl = "select count(id) AS TOTAL from DDJS_DZZ_PARTYFEE where visible ="+ CommonConstants.VISIBLE[1] +" and PARTY_ORGANIZATION_ID in ("+ids+") and ANNUAL = '"+year+"' and MONTHVAL ='"+month+"'";
        List<Map<String,Object>> listTotal = jdbcTemplate.queryForList(sqlUser);        //得到某一个党支部本月所交党费之和
        Calendar cal = Calendar.getInstance();
        int yearVal = cal.get(Calendar.YEAR);
        int monthVal = cal.get(Calendar.MONTH) + 1;
        BigDecimal total =BigDecimal.valueOf(0);
        if((String.valueOf(monthVal)).equals(month)&&(String.valueOf(yearVal)).equals(year)){
            if(totalList.size()>0){
                total = totalList.get(0).get("TOTAL")==null?BigDecimal.valueOf(0):(BigDecimal)totalList.get(0).get("TOTAL");
            }
        }else{
            if(listTotal.size()>0){
                total = listTotal.get(0).get("TOTAL")==null?BigDecimal.valueOf(0):(BigDecimal)listTotal.get(0).get("TOTAL");
            }
        }
        return total;
    }
}


