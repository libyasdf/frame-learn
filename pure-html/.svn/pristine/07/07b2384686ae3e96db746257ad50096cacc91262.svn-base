package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.dkgl.service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util.DzzUtil;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.dkgl.dao.DkglDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.dkgl.entity.DkglEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.dao.DrhdDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.entity.DrhdEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.service.DrhdService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.jtxx.dao.JtxxDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.jtxx.entity.JtxxEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.jtxx.service.JtxxService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.service.ZbdydhService;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**TODO 三会一课 党课Service
 * @Author: 李帅
 * @Date: 2018/8/28 18:47
 */
@Service
public class DkglServiceImpl implements DkglService {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ZzshhDao zzshhDao;
    @Autowired
    private DrhdDao drhdDao;
    @Autowired
    private JtxxDao jtxxDao;
    @Autowired
    private ZzshhService zzshhService;
    @Autowired
    private JtxxService jtxxService;
    @Autowired
    private DrhdService drhdService;
   @Autowired
    private ZbdydhService zbdydhService;

    @Autowired
    private DkglDao dkglDao;
    /**
     *
     *党课 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/8/28
     * */
    public DkglEntity saveDkgl(DkglEntity dkgl) throws IOException{
        String id = dkgl.getId();
        if (StringUtils.isBlank(id)) {
            dkgl.setCreUserId(UserUtil.getCruUserId());
            dkgl.setCreUserName(UserUtil.getCruUserName());
            dkgl.setCreDeptId(UserUtil.getCruDeptId());
            dkgl.setCreDeptName(UserUtil.getCruDeptName());
            dkgl.setCreChushiId(UserUtil.getCruChushiId());
            dkgl.setCreChushiName(UserUtil.getCruChushiName());
            dkgl.setCreJuId(UserUtil.getCruJuId());
            dkgl.setCreJuName(UserUtil.getCruJuName());
            dkgl.setVisible(CommonConstants.VISIBLE[1]);
            dkgl.setCreTime(JDateToolkit.getNowDate4());
            dkgl.setPartyOrganizationName(java.net.URLDecoder.decode(dkgl.getPartyOrganizationName(),"UTF-8"));
            DkglEntity dkglVal = dkglDao.save(dkgl);
            //获得会议类型
            List<String> list = new ArrayList();
            if(dkgl.getMeetingType()!=null&&(!dkgl.getMeetingType().equals(""))){
                String meetingTypeArray[] = (dkgl.getMeetingType()).split(",");
                list = Arrays.asList(meetingTypeArray);
            }

            //保存组织生活会
            if(list.contains("1")){
                ZzshhEntity zzssh = new ZzshhEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(zzssh,dkgl);
                    zzssh.setTableName("DDJS_SHYK_PARTYCLASS");
                    zzssh.setTableId(dkglVal.getId());
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
                    BeanUtils.copyProperties(jtxx,dkgl);
                    jtxx.setTableName("DDJS_SHYK_PARTYCLASS");
                    jtxx.setTableId(dkglVal.getId());
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
                    BeanUtils.copyProperties(drhd,dkgl);
                    drhd.setTableName("DDJS_SHYK_PARTYCLASS");
                    drhd.setTableId(dkglVal.getId());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                drhdDao.save(drhd);
            }
            return dkglVal;
        } else {
            DkglEntity oldDkgl = dkglDao.findOne(dkgl.getId());
            oldDkgl.setCompere(dkgl.getCompere());
            oldDkgl.setNoteTaker(dkgl.getNoteTaker());
            oldDkgl.setNumberOfPeople(dkgl.getNumberOfPeople());
            oldDkgl.setActualNumber(dkgl.getActualNumber());
            oldDkgl.setPrimaryCoverage(dkgl.getPrimaryCoverage());
            oldDkgl.setAttendants(dkgl.getAttendants());
            oldDkgl.setLeaveAndReasons(dkgl.getLeaveAndReasons());
            oldDkgl.setSeats(dkgl.getSeats());
            oldDkgl.setMeetingSituation(dkgl.getMeetingSituation());
            oldDkgl.setPartyOrganizationName(java.net.URLDecoder.decode(dkgl.getPartyOrganizationName(),"UTF-8"));
            oldDkgl.setPartyOrganizationId(dkgl.getPartyOrganizationId());
            oldDkgl.setMeetingTime(dkgl.getMeetingTime());
            oldDkgl.setMeetingPlace(dkgl.getMeetingPlace());
            oldDkgl.setMeetingType(dkgl.getMeetingType());
            oldDkgl = dkglDao.save(oldDkgl);
            //获得会议类型
            List<String> list = new ArrayList();
            if(dkgl.getMeetingType()!=null&&(!dkgl.getMeetingType().equals(""))){
                String meetingTypeArray[] = (dkgl.getMeetingType()).split(",");
                list = Arrays.asList(meetingTypeArray);
            }
            //保存组织生活会
            if(list.contains("1")){
                ZzshhEntity zzsshCopy =zzshhDao.findByTableId(dkgl.getId());
                ZzshhEntity zzssh = new ZzshhEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(zzssh,dkgl);
                    zzssh.setTableName("DDJS_SHYK_PARTYCLASS");
                    zzssh.setTableId(dkgl.getId());
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
                String delQuestion = "update ZzshhEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+dkgl.getId()+"'";
                zzshhDao.update(delQuestion);
            }
            //保存集体学习讨论
            if(list.contains("2")){
                JtxxEntity jtxxCopy =jtxxDao.findByTableId(dkgl.getId());
                JtxxEntity jtxx = new JtxxEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(jtxx,dkgl);
                    jtxx.setTableName("DDJS_SHYK_PARTYCLASS");
                    jtxx.setTableId(dkgl.getId());
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
                String delQuestion = "update JtxxEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+dkgl.getId()+"'";
                jtxxDao.update(delQuestion);
            }
            //保存党日活动
            if(list.contains("3")){
                DrhdEntity drhdCopy =drhdDao.findByTableId(dkgl.getId());
                DrhdEntity drhd = new DrhdEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(drhd,dkgl);
                    drhd.setTableName("DDJS_SHYK_PARTYCLASS");
                    drhd.setTableId(dkgl.getId());
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
                String delQuestion = "update DrhdEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+dkgl.getId()+"'";
                drhdDao.update(delQuestion);
            }
            return oldDkgl;
        }

    }

    /**
     * 党课列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param pageable
     * @param pageImpl
     * @param dkgl
     * @return
     */
    @Override
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DkglEntity dkgl,String startTime, String endTime,String ids) {
        ids = DzzUtil.spiltString(ids);
        StringBuilder dkglSql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        dkglSql.append(" from DkglEntity t ");
        //querySql.append(" where t.creChushiId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        dkglSql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        dkglSql.append(" and partyOrganizationId in ("+ids+")");
        // 时间
        if (StringUtils.isNotBlank(dkgl.getMeetingTime())) {
            dkglSql.append(" and substr(t.meetingTime,1,10) between ? and ?");
            para.add(startTime);
            para.add(endTime);
        }

        // 地点
        if (StringUtils.isNotBlank(dkgl.getMeetingPlace()) ) {
            dkglSql.append(" and t.meetingPlace like ?");
            para.add("%"+dkgl.getMeetingPlace()+"%");
        }

        // 主持人
        if (StringUtils.isNotBlank(dkgl.getCompere())) {
            dkglSql.append(" and t.compere like ?");
            para.add("%"+dkgl.getCompere()+"%");
        }

        // 记录人
        if (StringUtils.isNotBlank(dkgl.getNoteTaker())) {
            dkglSql.append(" and t.noteTaker like ?");
            para.add("%"+dkgl.getNoteTaker()+"%");
        }

        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            dkglSql.append(" order by t.creTime desc ");
        } else {
            dkglSql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }

        Page<DkglEntity> page = dkglDao.query(dkglSql.toString(), pageable, para.toArray());
        // 添加列表操作
        List<DkglEntity> content = page.getContent();
        for (DkglEntity dkglVal : content) {
            String id = dkglVal.getPartyOrganizationId()==null?"":(String)dkglVal.getPartyOrganizationId();
            String  PartyOrganizationName = "";//组织名称
            String superName ="";//上一级组织名称
            List<Map<String,Object>> listOrgName = zbdydhService.getOrgName(id)==null? new ArrayList():zbdydhService.getOrgName(id);
            if(listOrgName.size()>0){
                PartyOrganizationName =listOrgName.get(0).get("ORG_NAME")==null?"":(String)listOrgName.get(0).get("ORG_NAME");
                superName=listOrgName.get(0).get("SUPER_ORG_NAME")==null?"":(String)listOrgName.get(0).get("SUPER_ORG_NAME");
            }
            dkglVal.setPartyOrganizationName(PartyOrganizationName);
            dkglVal.setSuperName(superName);
            dkglVal.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
        }
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }


    /**
     * 根据id逻辑党课一条信息
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param dkgl
     * @return
     */
    @Override
    public int delete(DkglEntity dkgl) {
        int n = 0;
        DkglEntity dkglVal = dkglDao.findOne(dkgl.getId());
        if(StringUtils.isNotBlank(dkgl.getId())){
            try {
                //逻辑删除党课
                String delQuestion = "update DkglEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.id='"+dkgl.getId()+"'";
                n = dkglDao.update(delQuestion);
                List<String> list = new ArrayList();
                if(dkglVal.getMeetingType()!=null&&(!dkglVal.getMeetingType().equals(""))){
                    String meetingTypeArray[] = (dkglVal.getMeetingType()).split(",");
                    list = Arrays.asList(meetingTypeArray);
                }
                if(list.contains("1")){
                    //逻辑删除组织生活会
                    String delZzshh = "update ZzshhEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+dkgl.getId()+"'";
                    zzshhDao.update(delZzshh);
                }
                if(list.contains("2")){
                    //逻辑删除集体学习讨论
                    String delJtxx = "update JtxxEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+dkgl.getId()+"'";
                    jtxxDao.update(delJtxx);
                }
                if(list.contains("3")){
                    //逻辑删除党日活动
                    String deldrhd = "update DrhdEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+dkgl.getId()+"'";
                    drhdDao.update(deldrhd);
                }

            } catch (Exception e) {
                log.info(e.getMessage());
                log.info("删除党课出现异常！");
                n=0;
            }
        }
        return n;
    }
    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param id
     * @return
     */
    @Override
    public DkglEntity getById(String id) throws Exception {
        return dkglDao.findOne(id);
    }




}
