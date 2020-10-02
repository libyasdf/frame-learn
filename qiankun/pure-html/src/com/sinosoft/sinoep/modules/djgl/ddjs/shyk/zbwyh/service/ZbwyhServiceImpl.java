package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbwyh.service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util.DzzUtil;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.dao.DrhdDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.entity.DrhdEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.service.DrhdService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.jtxx.dao.JtxxDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.jtxx.entity.JtxxEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.jtxx.service.JtxxService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.service.ZbdydhService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbwyh.dao.ZbwyhDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbwyh.entity.ZbwyhEntity;
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

/**TODO 三会一课 支部委员会Service
 * @Author: 李帅
 * @Date: 2018/8/27 18:47
 */
@Service
public class ZbwyhServiceImpl implements ZbwyhService {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ZzshhDao zzshhDao;
    @Autowired
    private ZbdydhService zbdydhService;
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
    private ZbwyhDao zbwyhDao;
    /**
     *
     *支部委员会 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/8/27
     * */
    public ZbwyhEntity saveZbwyh(ZbwyhEntity zbwyh) throws IOException {
        String id = zbwyh.getId();
        if (StringUtils.isBlank(id)) {
            zbwyh.setCreUserId(UserUtil.getCruUserId());
            zbwyh.setCreUserName(UserUtil.getCruUserName());
            zbwyh.setCreDeptId(UserUtil.getCruDeptId());
            zbwyh.setCreDeptName(UserUtil.getCruDeptName());
            zbwyh.setCreChushiId(UserUtil.getCruChushiId());
            zbwyh.setCreChushiName(UserUtil.getCruChushiName());
            zbwyh.setCreJuId(UserUtil.getCruJuId());
            zbwyh.setCreJuName(UserUtil.getCruJuName());
            zbwyh.setVisible(CommonConstants.VISIBLE[1]);
            zbwyh.setCreTime(JDateToolkit.getNowDate4());
            zbwyh.setPartyOrganizationName(java.net.URLDecoder.decode(zbwyh.getPartyOrganizationName(),"UTF-8"));
            ZbwyhEntity zbwyhVal = zbwyhDao.save(zbwyh);
            //获得会议类型
            List<String> list = new ArrayList();
            if(zbwyh.getMeetingType()!=null&&(!zbwyh.getMeetingType().equals(""))){
                String meetingTypeArray[] = (zbwyh.getMeetingType()).split(",");
                list = Arrays.asList(meetingTypeArray);
            }

            //保存组织生活会
            if(list.contains("1")){
                ZzshhEntity zzssh = new ZzshhEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(zzssh,zbwyh);
                    zzssh.setTableName("DDJS_SHYK_COMMITTEE");
                    zzssh.setTableId(zbwyhVal.getId());
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
                    BeanUtils.copyProperties(jtxx,zbwyh);
                    jtxx.setTableName("DDJS_SHYK_COMMITTEE");
                    jtxx.setTableId(zbwyhVal.getId());
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
                    BeanUtils.copyProperties(drhd,zbwyh);
                    drhd.setTableName("DDJS_SHYK_COMMITTEE");
                    drhd.setTableId(zbwyhVal.getId());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                drhdDao.save(drhd);
            }
            return zbwyhVal;
        } else {
            ZbwyhEntity oldZbwyh = zbwyhDao.findOne(zbwyh.getId());
            oldZbwyh.setCompere(zbwyh.getCompere());
            oldZbwyh.setNoteTaker(zbwyh.getNoteTaker());
            oldZbwyh.setNumberOfPeople(zbwyh.getNumberOfPeople());
            oldZbwyh.setActualNumber(zbwyh.getActualNumber());
            oldZbwyh.setPrimaryCoverage(zbwyh.getPrimaryCoverage());
            oldZbwyh.setAttendants(zbwyh.getAttendants());
            oldZbwyh.setLeaveAndReasons(zbwyh.getLeaveAndReasons());
            oldZbwyh.setSeats(zbwyh.getSeats());
            oldZbwyh.setMeetingSituation(zbwyh.getMeetingSituation());
            oldZbwyh.setPartyOrganizationName(java.net.URLDecoder.decode(zbwyh.getPartyOrganizationName(),"UTF-8"));
            oldZbwyh.setPartyOrganizationId(zbwyh.getPartyOrganizationId());
            oldZbwyh.setMeetingTime(zbwyh.getMeetingTime());
            oldZbwyh.setMeetingPlace(zbwyh.getMeetingPlace());
            oldZbwyh.setMeetingType(zbwyh.getMeetingType());
            oldZbwyh = zbwyhDao.save(oldZbwyh);
            //获得会议类型
            List<String> list = new ArrayList();
            if(zbwyh.getMeetingType()!=null&&(!zbwyh.getMeetingType().equals(""))){
                String meetingTypeArray[] = (zbwyh.getMeetingType()).split(",");
                list = Arrays.asList(meetingTypeArray);
            }
            //保存组织生活会
            if(list.contains("1")){
                ZzshhEntity zzsshCopy =zzshhDao.findByTableId(zbwyh.getId());
                ZzshhEntity zzssh = new ZzshhEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(zzssh,zbwyh);
                    zzssh.setTableName("DDJS_SHYK_COMMITTEE");
                    zzssh.setTableId(zbwyh.getId());
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
                String delQuestion = "update ZzshhEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+zbwyh.getId()+"'";
                zzshhDao.update(delQuestion);
            }
            //保存集体学习讨论
            if(list.contains("2")){
                JtxxEntity jtxxCopy =jtxxDao.findByTableId(zbwyh.getId());
                JtxxEntity jtxx = new JtxxEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(jtxx,zbwyh);
                    jtxx.setTableName("DDJS_SHYK_COMMITTEE");
                    jtxx.setTableId(zbwyh.getId());
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
                String delQuestion = "update JtxxEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+zbwyh.getId()+"'";
                jtxxDao.update(delQuestion);
            }
            //保存党日活动
            if(list.contains("3")){
                DrhdEntity drhdCopy =drhdDao.findByTableId(zbwyh.getId());
                DrhdEntity drhd = new DrhdEntity();
                try {
                    //复制对象
                    BeanUtils.copyProperties(drhd,zbwyh);
                    drhd.setTableName("DDJS_SHYK_COMMITTEE");
                    drhd.setTableId(zbwyh.getId());
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
                String delQuestion = "update DrhdEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+zbwyh.getId()+"'";
                drhdDao.update(delQuestion);
            }
            return oldZbwyh;
        }

    }

    /**
     * 支部委员会列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年8月27日
     * @param pageable
     * @param pageImpl
     * @param zbwyh
     * @return
     */
    @Override
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, ZbwyhEntity zbwyh,String startTime, String endTime,String ids) {
        ids = DzzUtil.spiltString(ids);
        StringBuilder zbwyhSql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        zbwyhSql.append(" from ZbwyhEntity t ");
        //querySql.append(" where t.creChushiId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        zbwyhSql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        zbwyhSql.append(" and partyOrganizationId in ("+ids+")");

        // 时间
        if (StringUtils.isNotBlank(zbwyh.getMeetingTime())) {
            zbwyhSql.append(" and substr(t.meetingTime,1,10) between ? and ?");
            para.add(startTime);
            para.add(endTime);
        }

        // 地点
        if (StringUtils.isNotBlank(zbwyh.getMeetingPlace()) ) {
            zbwyhSql.append(" and t.meetingPlace like ?");
            para.add("%"+zbwyh.getMeetingPlace()+"%");
        }

        // 主持人
        if (StringUtils.isNotBlank(zbwyh.getCompere())) {
            zbwyhSql.append(" and t.compere like ?");
            para.add("%"+zbwyh.getCompere()+"%");
        }

        // 记录人
        if (StringUtils.isNotBlank(zbwyh.getNoteTaker())) {
            zbwyhSql.append(" and t.noteTaker like ?");
            para.add("%"+zbwyh.getNoteTaker()+"%");
        }

        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            zbwyhSql.append(" order by t.creTime desc ");
        } else {
            zbwyhSql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }

        Page<ZbwyhEntity> page = zbwyhDao.query(zbwyhSql.toString(), pageable, para.toArray());
        // 添加列表操作
        List<ZbwyhEntity> content = page.getContent();
        for (ZbwyhEntity zbwyhVal : content) {
            String id = zbwyhVal.getPartyOrganizationId()==null?"":(String)zbwyhVal.getPartyOrganizationId();
            String  PartyOrganizationName = "";//组织名称
            String superName ="";//上一级组织名称
            List<Map<String,Object>> listOrgName = zbdydhService.getOrgName(id)==null? new ArrayList():zbdydhService.getOrgName(id);
            if(listOrgName.size()>0){
                PartyOrganizationName =listOrgName.get(0).get("ORG_NAME")==null?"":(String)listOrgName.get(0).get("ORG_NAME");
                superName=listOrgName.get(0).get("SUPER_ORG_NAME")==null?"":(String)listOrgName.get(0).get("SUPER_ORG_NAME");
            }
            zbwyhVal.setSuperName(superName);
            zbwyhVal.setPartyOrganizationName(PartyOrganizationName);
            zbwyhVal.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
        }
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }


    /**
     * 根据id逻辑支部委员会一条信息
     * TODO
     * @author 李帅
     * @Date 2018年8月27日
     * @param zbwyh
     * @return
     */
    @Override
    public int delete(ZbwyhEntity zbwyh) {
        int n = 0;
        ZbwyhEntity zbwyhVal = zbwyhDao.findOne(zbwyh.getId());
        if(StringUtils.isNotBlank(zbwyh.getId())){
            try {
                //逻辑删除试题
                String delQuestion = "update ZbwyhEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.id='"+zbwyh.getId()+"'";
                n = zbwyhDao.update(delQuestion);
                List<String> list = new ArrayList();
                if(zbwyhVal.getMeetingType()!=null&&(!zbwyhVal.getMeetingType().equals(""))){
                    String meetingTypeArray[] = (zbwyhVal.getMeetingType()).split(",");
                    list = Arrays.asList(meetingTypeArray);
                }
                if(list.contains("1")){
                    //逻辑删除组织生活会
                    String delZzshh = "update ZzshhEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+zbwyh.getId()+"'";
                    zzshhDao.update(delZzshh);
                }
                if(list.contains("2")){
                    //逻辑删除集体学习讨论
                    String delJtxx = "update JtxxEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+zbwyh.getId()+"'";
                    jtxxDao.update(delJtxx);
                }
                if(list.contains("3")){
                    //逻辑删除党日活动
                    String deldrhd = "update DrhdEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.tableId='"+zbwyh.getId()+"'";
                    drhdDao.update(deldrhd);
                }
            } catch (Exception e) {
                log.info(e.getMessage());
                log.info("删除支部委员会出现异常！");
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
    public ZbwyhEntity getById(String id) throws Exception {
        return zbwyhDao.findOne(id);
    }

}
