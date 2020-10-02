package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.dxzh.service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util.DzzUtil;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.dao.DrhdDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.entity.DrhdEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.service.DrhdService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.dxzh.dao.DxzhDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.dxzh.entity.DxzhEntity;
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

/**TODO 三会一课 党小组会Service
 * @Author: 李帅
 * @Date: 2018/8/28 18:47
 */
@Service
public class DxzhServiceImpl implements DxzhService {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ZzshhDao zzshhDao;
    @Autowired

    private DrhdDao drhdDao;
    @Autowired
    private ZbdydhService zbdydhService;
    @Autowired
    private JtxxDao jtxxDao;
    @Autowired
    private ZzshhService zzshhService;
    @Autowired
    private JtxxService jtxxService;
    @Autowired
    private DrhdService drhdService;


    @Autowired
    private DxzhDao dxzhDao;
    /**
     *
     *党小组会 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/8/28
     * */
    public DxzhEntity saveDxzh(DxzhEntity dxzh) throws IOException {
        String id = dxzh.getId();
        if (StringUtils.isBlank(id)) {
            dxzh.setCreUserId(UserUtil.getCruUserId());
            dxzh.setCreUserName(UserUtil.getCruUserName());
            dxzh.setCreDeptId(UserUtil.getCruDeptId());
            dxzh.setCreDeptName(UserUtil.getCruDeptName());
            dxzh.setCreChushiId(UserUtil.getCruChushiId());
            dxzh.setCreChushiName(UserUtil.getCruChushiName());
            dxzh.setCreJuId(UserUtil.getCruJuId());
            dxzh.setCreJuName(UserUtil.getCruJuName());
            dxzh.setVisible(CommonConstants.VISIBLE[1]);
            dxzh.setCreTime(JDateToolkit.getNowDate4());
            dxzh.setPartyOrganizationName(java.net.URLDecoder.decode(dxzh.getPartyOrganizationName(),"UTF-8"));
            DxzhEntity dxzhVal = dxzhDao.save(dxzh);
            //获得会议类型
            List<String> list = new ArrayList();
            if(dxzh.getMeetingType()!=null&&(!dxzh.getMeetingType().equals(""))){
                String meetingTypeArray[] = (dxzh.getMeetingType()).split(",");
                list = Arrays.asList(meetingTypeArray);
            }

            //保存组织生活会
            if(list.contains("1")){
                ZzshhEntity zzssh = new ZzshhEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(zzssh,dxzh);
                    zzssh.setTableName("DDJS_SHYK_PARTYGROUP");
                    zzssh.setTableId(dxzhVal.getId());
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
                    BeanUtils.copyProperties(jtxx,dxzh);
                    jtxx.setTableName("DDJS_SHYK_PARTYGROUP");
                    jtxx.setTableId(dxzhVal.getId());
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
                    BeanUtils.copyProperties(drhd,dxzh);
                    drhd.setTableName("DDJS_SHYK_PARTYGROUP");
                    drhd.setTableId(dxzhVal.getId());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                drhdDao.save(drhd);
            }

            return dxzhVal;
        } else {
            DxzhEntity oldDxzh = dxzhDao.findOne(dxzh.getId());
            oldDxzh.setCompere(dxzh.getCompere());
            oldDxzh.setNoteTaker(dxzh.getNoteTaker());
            oldDxzh.setNumberOfPeople(dxzh.getNumberOfPeople());
            oldDxzh.setActualNumber(dxzh.getActualNumber());
            oldDxzh.setPrimaryCoverage(dxzh.getPrimaryCoverage());
            oldDxzh.setAttendants(dxzh.getAttendants());
            oldDxzh.setLeaveAndReasons(dxzh.getLeaveAndReasons());
            oldDxzh.setSeats(dxzh.getSeats());
            oldDxzh.setMeetingSituation(dxzh.getMeetingSituation());
            oldDxzh.setPartyOrganizationName(java.net.URLDecoder.decode(dxzh.getPartyOrganizationName(),"UTF-8"));
            oldDxzh.setPartyOrganizationId(dxzh.getPartyOrganizationId());
            oldDxzh.setMeetingTime(dxzh.getMeetingTime());
            oldDxzh.setMeetingPlace(dxzh.getMeetingPlace());
            oldDxzh.setMeetingType(dxzh.getMeetingType());
            oldDxzh = dxzhDao.save(oldDxzh);
            //获得会议类型
            List<String> list = new ArrayList();
            if(dxzh.getMeetingType()!=null&&(!dxzh.getMeetingType().equals(""))){
                String meetingTypeArray[] = (dxzh.getMeetingType()).split(",");
                list = Arrays.asList(meetingTypeArray);
            }
            //保存组织生活会
            if(list.contains("1")){
                ZzshhEntity zzsshCopy =zzshhDao.findByTableId(dxzh.getId());
                ZzshhEntity zzssh = new ZzshhEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(zzssh,dxzh);
                    zzssh.setTableName("DDJS_SHYK_PARTYGROUP");
                    zzssh.setTableId(dxzh.getId());
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
                String delQuestion = "update ZzshhEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+dxzh.getId()+"'";
                zzshhDao.update(delQuestion);
            }
            //保存集体学习讨论
            if(list.contains("2")){
                JtxxEntity jtxxCopy =jtxxDao.findByTableId(dxzh.getId());
                JtxxEntity jtxx = new JtxxEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(jtxx,dxzh);
                    jtxx.setTableName("DDJS_SHYK_PARTYGROUP");
                    jtxx.setTableId(dxzh.getId());
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
                String delQuestion = "update JtxxEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+dxzh.getId()+"'";
                jtxxDao.update(delQuestion);
            }
            //保存党日活动
            if(list.contains("3")){
                DrhdEntity drhdCopy =drhdDao.findByTableId(dxzh.getId());
                DrhdEntity drhd = new DrhdEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(drhd,dxzh);
                    drhd.setTableName("DDJS_SHYK_PARTYGROUP");
                    drhd.setTableId(dxzh.getId());
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
                String delQuestion = "update DrhdEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+dxzh.getId()+"'";
                drhdDao.update(delQuestion);
            }
            return oldDxzh;
        }

    }

    /**
     * 党小组会列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param pageable
     * @param pageImpl
     * @param dxzh
     * @return
     */
    @Override
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DxzhEntity dxzh,String startTime, String endTime,String ids) {
        ids = DzzUtil.spiltString(ids);
        StringBuilder dxzhSql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        dxzhSql.append(" from DxzhEntity t ");
        //querySql.append(" where t.creChushiId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        dxzhSql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        dxzhSql.append(" and partyOrganizationId in ("+ids+")");


        // 时间
        if (StringUtils.isNotBlank(dxzh.getMeetingTime())) {
            dxzhSql.append(" and substr(t.meetingTime,1,10) between ? and ?");
            para.add(startTime);
            para.add(endTime);
        }

        // 地点
        if (StringUtils.isNotBlank(dxzh.getMeetingPlace()) ) {
            dxzhSql.append(" and t.meetingPlace like ?");
            para.add("%"+dxzh.getMeetingPlace()+"%");
        }

        // 主持人
        if (StringUtils.isNotBlank(dxzh.getCompere())) {
            dxzhSql.append(" and t.compere like ?");
            para.add("%"+dxzh.getCompere()+"%");
        }

        // 记录人
        if (StringUtils.isNotBlank(dxzh.getNoteTaker())) {
            dxzhSql.append(" and t.noteTaker like ?");
            para.add("%"+dxzh.getNoteTaker()+"%");
        }

        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            dxzhSql.append(" order by t.creTime desc ");
        } else {
            dxzhSql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }

        Page<DxzhEntity> page = dxzhDao.query(dxzhSql.toString(), pageable, para.toArray());
        // 添加列表操作
        List<DxzhEntity> content = page.getContent();
        for (DxzhEntity dxzhVal : content) {
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
            dxzhVal.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
        }
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }


    /**
     * 根据id逻辑党小组会一条信息
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param dxzh
     * @return
     */
    @Override
    public int delete(DxzhEntity dxzh) {
        int n = 0;
        DxzhEntity  dxzhVal = dxzhDao.findOne(dxzh.getId());
        if(StringUtils.isNotBlank(dxzh.getId())){
            try {
                //逻辑删除试题
                String delQuestion = "update DxzhEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.id='"+dxzh.getId()+"'";
                n = dxzhDao.update(delQuestion);
                List<String> list = new ArrayList();
                if(dxzhVal.getMeetingType()!=null&&(!dxzhVal.getMeetingType().equals(""))){
                    String meetingTypeArray[] = (dxzhVal.getMeetingType()).split(",");
                    list = Arrays.asList(meetingTypeArray);
                }
                if(list.contains("1")){
                    //逻辑删除组织生活会
                    String delZzshh = "update ZzshhEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+dxzh.getId()+"'";
                    zzshhDao.update(delZzshh);
                }
                if(list.contains("2")){
                    //逻辑删除集体学习讨论
                    String delJtxx = "update JtxxEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+dxzh.getId()+"'";
                    jtxxDao.update(delJtxx);
                }
                if(list.contains("3")){
                    //逻辑删除党日活动
                    String deldrhd = "update DrhdEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+dxzh.getId()+"'";
                    drhdDao.update(deldrhd);
                }

            } catch (Exception e) {
                log.info(e.getMessage());
                log.info("删除党小组会出现异常！");
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
    public DxzhEntity getById(String id) throws Exception {
        return dxzhDao.findOne(id);
    }

}
