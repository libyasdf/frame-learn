package com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.sqrxx.service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao.DegreeDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao.DyxxDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.dao.WorkingDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglDegreeEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglUserbasicinfoEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.dygl.dyxx.entity.DdjsDyglWorkingEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.sqrxx.dao.SqrxxPartyBasicInfoDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.sqrxx.entity.DdjsSqrglPartybasicinfoEntity;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *TODO 申请人管理 申请人信息Service
 *
 * @Author: 李帅
 * @Date: 2018/9/13 16:46
 */

@Service
public class SqrxxServiceImpl implements  SqrxxServiceI{
    @Autowired
    private DyxxDao dyxxDao;
    @Autowired
    private DegreeDao degreeDao;
    @Autowired
    private SqrxxPartyBasicInfoDao partyBasicInfoDao;
    @Autowired
    private WorkingDao workingDao;
    /**
     * 申请人信息信息保存/更新
     * TODO
     * @author 李帅
     * @Date 2018年9月13日
     * @return
     */

    @Override
    public DdjsDyglUserbasicinfoEntity saveForm(DdjsDyglUserbasicinfoEntity entity, DdjsSqrglPartybasicinfoEntity partybasicinfoEntity,  DdjsDyglWorkingEntity workingEntity,
                                                  DdjsDyglDegreeEntity degreeEntity) throws IOException {
        String id = entity.getId();
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
            entity.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
            entity.setIsHistoryparty("0");
            entity.setPartyOrganizationName(java.net.URLDecoder.decode(entity.getPartyOrganizationName(),"UTF-8"));
            BeanUtils.copyProperties(entity,partybasicinfoEntity);
            BeanUtils.copyProperties(entity,workingEntity);
            BeanUtils.copyProperties(entity,degreeEntity);
            entity = dyxxDao.save(entity);
        } else {
            DdjsDyglUserbasicinfoEntity oldEntity = dyxxDao.findOne(entity.getId());
            oldEntity.setBirthplace(entity.getBirthplace());
            oldEntity.setContactNumber(entity.getContactNumber());
            oldEntity.setDateOfBirth(entity.getDateOfBirth());
            oldEntity.setRyGegree(entity.getRyGegree());
            oldEntity.setDemocraticParties(entity.getDemocraticParties());
            oldEntity.setWorkingUnit(entity.getWorkingUnit());
            oldEntity.setWorkingTime(entity.getWorkingTime());
            oldEntity.setTechnicalPost(entity.getTechnicalPost());
            oldEntity.setSex(entity.getSex());
            oldEntity.setRegisteredResidence(entity.getRegisteredResidence());
            oldEntity.setPinCodes(entity.getPinCodes());
            entity.setPartyOrganizationName(java.net.URLDecoder.decode(entity.getPartyOrganizationName(),"UTF-8"));
            oldEntity.setPartyOrganizationId(entity.getPartyOrganizationId());
            oldEntity.setNewStratum(entity.getNewStratum());
            oldEntity.setNativePlace(entity.getNativePlace());
            oldEntity.setNation(entity.getNation());
            oldEntity.setName(entity.getName());
            oldEntity.setMaritalStatus(entity.getMaritalStatus());
            oldEntity.setRyMajor(entity.getRyMajor());
            oldEntity.setLocalPoliceStation(entity.getLocalPoliceStation());
            oldEntity.setJobLevel(entity.getJobLevel());
            oldEntity.setIsProbationary(entity.getIsProbationary());
            oldEntity.setIsPartyRepresentative(entity.getIsPartyRepresentative());
            oldEntity.setIsHistoryparty(entity.getIsHistoryparty());
            oldEntity.setRyIdentity(entity.getRyIdentity());
            oldEntity.setHomeAddress(entity.getHomeAddress());
            oldEntity.setHistoryTime(entity.getHistoryTime());
            oldEntity.setHistoryPartyname(entity.getHistoryPartyname());
            oldEntity.setHistoryPartyid(entity.getHistoryPartyid());
            oldEntity.setHealthStatus(entity.getHealthStatus());
            oldEntity.setRyGraduationAcademy(entity.getRyGraduationAcademy());
            oldEntity.setGoPartyname(entity.getGoPartyname());
            oldEntity.setGoPartyid(entity.getGoPartyid());
            oldEntity.setFrontlineSituation(entity.getFrontlineSituation());
            oldEntity.setFamilyOrigin(entity.getFamilyOrigin());
            oldEntity.setRyEducation(entity.getRyEducation());
            oldEntity.setUserId(entity.getUserId());
            if(!StringUtil.isBlank(partybasicinfoEntity.getDetermineActivistTime())){
                String typeArry [] = entity.getTypeOfPersonnel().split(",");
                List typeVal = Arrays.asList(typeArry);
                if(!typeVal.contains("02")){
                    oldEntity.setTypeOfPersonnel(entity.getTypeOfPersonnel()+",02");
                }else{
                    oldEntity.setTypeOfPersonnel(entity.getTypeOfPersonnel());
                }
                if(!StringUtil.isBlank(partybasicinfoEntity.getDevelopmentObjectTime())){
                    if(!typeVal.contains("03")){
                        oldEntity.setTypeOfPersonnel(entity.getTypeOfPersonnel()+",03");
                    }else{
                        oldEntity.setTypeOfPersonnel(entity.getTypeOfPersonnel());
                    }
                }else{
                    oldEntity.setTypeOfPersonnel("01,02");
                }
            }else{
                oldEntity.setTypeOfPersonnel("01");
            }
            oldEntity.setIsHistoryparty("0");
            entity =  dyxxDao.save(oldEntity);

            BeanUtils.copyProperties(entity,partybasicinfoEntity);
       /*     BeanUtils.copyProperties(entity,workingEntity);
            BeanUtils.copyProperties(entity,degreeEntity);*/

            //学位学历
           /* List<DdjsDyglDegreeEntity> degreeEntityList = new ArrayList<DdjsDyglDegreeEntity>();
            DdjsDyglDegreeEntity olddegreeEntity  = degreeDao.findFirstByDegreeSuperIdOrderByDegreeAwardTimeDesc(entity.getId());
            if(olddegreeEntity!=null){
                degreeEntity.setDegreeId(olddegreeEntity.getDegreeId());
            }
                degreeEntity.setDegreeSuperId(entity.getId());
                degreeEntity = degreeDao.save(degreeEntity);
                degreeEntityList.add(degreeEntity);*/


            //工作岗位
           /* List<DdjsDyglWorkingEntity> workingEntityList = new ArrayList<DdjsDyglWorkingEntity>();
            DdjsDyglWorkingEntity oldworkingEntity = workingDao.findFirstByWorkingSuperIdOrderByBeforeTimeDesc(entity.getId());
            if(oldworkingEntity!=null){
                workingEntity.setWorkingId(oldworkingEntity.getWorkingId());
            }
                workingEntity.setWorkingSuperId(entity.getId());
                workingEntity = workingDao.save(workingEntity);
                workingEntityList.add(workingEntity);
*/

            //申请人基本信息

                List<DdjsSqrglPartybasicinfoEntity> partybasicinfoEntityList = new ArrayList<DdjsSqrglPartybasicinfoEntity>();
                DdjsSqrglPartybasicinfoEntity oldPartybasicinfoEntity = partyBasicInfoDao.findBySuperId(entity.getId());
                if(oldPartybasicinfoEntity!=null){
                    partybasicinfoEntity.setPartybasicinfoId(oldPartybasicinfoEntity.getPartybasicinfoId());
                }
                partybasicinfoEntity.setSuperId(entity.getId());
                partybasicinfoEntity = partyBasicInfoDao.save(partybasicinfoEntity);
                partybasicinfoEntityList.add(partybasicinfoEntity);

            /*entity.setWorkingEntityList(workingEntityList);
            entity.setDegreeEntityList(degreeEntityList);*/
                entity.setSqrxxPartybasicinfoEntityList(partybasicinfoEntityList);
            }
        return entity;
    }

    /**
     * TODO 对编辑数据进行渲染
     * @author 李帅
     * @Date 2018年9月13日
     * @param
     * @param id
     * @return
     */

    @Override
    public Object getById(String id, String type) {
        Object obj = null;
        if("2".equals(type)){
           obj = partyBasicInfoDao.findBySuperId(id);
           if(null == obj){
               obj = new DdjsSqrglPartybasicinfoEntity();
           }
        }else if("3".equals(type)){
            obj = workingDao.findFirstByWorkingSuperIdAndVisibleOrderByBeforeTimeDesc(id,CommonConstants.VISIBLE[1]);
            if(null == obj){
                obj = new DdjsDyglWorkingEntity();
            }
        }else if("4".equals(type)){
            obj = degreeDao.findFirstByDegreeSuperIdAndVisibleOrderByEnrolmentTimeDesc(id,CommonConstants.VISIBLE[1]);
            if(null == obj){
                obj = new DdjsDyglDegreeEntity();
            }
        }else{
            obj = dyxxDao.findOne(id);
        }
        return obj;
    }




    /**
     * 申请人信息列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年9月14日
     * @param pageable
     * @param pageImpl
     * @param partyBasicinfo
     * @return
     */
    @Override
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DdjsSqrglPartybasicinfoEntity partyBasicinfo, String startTime, String endTime) {

        StringBuilder sqrglSql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        sqrglSql.append(" from DdjsSqrglPartybasicinfoEntity t ");
        //querySql.append(" where t.creChushiId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        sqrglSql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");


        // 入党申请时间
        if (StringUtils.isNotBlank(partyBasicinfo.getApplicationTime())) {
            sqrglSql.append(" and substr(t.applicationTime,1,10) between ? and ?");
            para.add(startTime);
            para.add(endTime);
        }

      /*  // 姓名
        System.out.print(partyBasicinfo.getUserbasicinfo().getName());
        if (StringUtils.isNotBlank(partyBasicinfo.getUserbasicinfo().getName()) ) {
            sqrglSql.append(" and t.userbasicinfo.name = ?");
            para.add(partyBasicinfo.getUserbasicinfo().getName());
        }

        // 性别
        if (StringUtils.isNotBlank(partyBasicinfo.getUserbasicinfo().getSex())) {
            sqrglSql.append(" and t.userbasicinfo.sex = ?");
            para.add(partyBasicinfo.getUserbasicinfo().getSex());
        }

        // 民族
        if (StringUtils.isNotBlank(partyBasicinfo.getUserbasicinfo().getNation())) {
            sqrglSql.append(" and t.userbasicinfo.nation = ?");
            para.add(partyBasicinfo.getUserbasicinfo().getNation());
        }
        // 学历
        if (StringUtils.isNotBlank(partyBasicinfo.getUserbasicinfo().getRyEducation())) {
            sqrglSql.append(" and t.userbasicinfo.ryEducation = ?");
            para.add(partyBasicinfo.getUserbasicinfo().getRyEducation());
        }*/

        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            sqrglSql.append(" order by t.creTime desc ");
        } else {
            sqrglSql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }

        Page<DdjsSqrglPartybasicinfoEntity> page = partyBasicInfoDao.query(sqrglSql.toString(), pageable, para.toArray());
        // 添加列表操作
        List<DdjsSqrglPartybasicinfoEntity> content = page.getContent();
        for (DdjsSqrglPartybasicinfoEntity userVal : content) {
            userVal.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
        }
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }
    /**
     * 新增申请人时判断该人员是否已经为申请人
     * TODO
     * @author 李帅
     * @Date 2018年8月29日
     * @param
     * @param
     * @return
     */
    public DdjsDyglUserbasicinfoEntity  queryUser(String userId){
     List<DdjsDyglUserbasicinfoEntity> userList = dyxxDao.findByUserIdAndVisible(userId,CommonConstants.VISIBLE[1]);
        DdjsDyglUserbasicinfoEntity  entity = new DdjsDyglUserbasicinfoEntity();
        if(userList.size()>0){
            entity = userList.get(0)==null? new DdjsDyglUserbasicinfoEntity():userList.get(0);
      }
        return entity;
    }

}
