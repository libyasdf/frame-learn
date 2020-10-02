package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.jtxx.service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util.DzzUtil;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.jtxx.dao.JtxxDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.jtxx.entity.JtxxEntity;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.service.ZbdydhService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.util.tool.JDateToolkit;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**TODO 三会一课 集体学习讨论Service
 * @Author: 李帅
 * @Date: 2018/8/28 18:47
 */
@Service
public class JtxxServiceImpl implements JtxxService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ZbdydhService zbdydhService;
    @Autowired
    private JtxxDao jtxxDao;
    /**
     *
     *集体学习讨论 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/8/28
     * */
    public JtxxEntity saveJtxx(JtxxEntity jtxx) throws IOException {
        String id = jtxx.getId();
        if (StringUtils.isBlank(id)) {
            jtxx.setCreUserId(UserUtil.getCruUserId());
            jtxx.setCreUserName(UserUtil.getCruUserName());
            jtxx.setCreDeptId(UserUtil.getCruDeptId());
            jtxx.setCreDeptName(UserUtil.getCruDeptName());
            jtxx.setCreChushiId(UserUtil.getCruChushiId());
            jtxx.setCreChushiName(UserUtil.getCruChushiName());
            jtxx.setCreJuId(UserUtil.getCruJuId());
            jtxx.setCreJuName(UserUtil.getCruJuName());
            jtxx.setVisible(CommonConstants.VISIBLE[1]);
            jtxx.setCreTime(JDateToolkit.getNowDate4());
            jtxx.setPartyOrganizationName(java.net.URLDecoder.decode(jtxx.getPartyOrganizationName(),"UTF-8"));
            jtxx = jtxxDao.save(jtxx);
            return jtxx;
        } else {
            JtxxEntity oldJtxx = jtxxDao.findOne(jtxx.getId());
            oldJtxx.setCompere(jtxx.getCompere());
            oldJtxx.setNoteTaker(jtxx.getNoteTaker());
            oldJtxx.setNumberOfPeople(jtxx.getNumberOfPeople());
            oldJtxx.setActualNumber(jtxx.getActualNumber());
            oldJtxx.setPrimaryCoverage(jtxx.getPrimaryCoverage());
            oldJtxx.setAttendants(jtxx.getAttendants());
            oldJtxx.setLeaveAndReasons(jtxx.getLeaveAndReasons());
            oldJtxx.setSeats(jtxx.getSeats());
            oldJtxx.setMeetingSituation(jtxx.getMeetingSituation());
            oldJtxx.setPartyOrganizationName(java.net.URLDecoder.decode(jtxx.getPartyOrganizationName(),"UTF-8"));
            oldJtxx.setPartyOrganizationId(jtxx.getPartyOrganizationId());
            oldJtxx.setMeetingTime(jtxx.getMeetingTime());
            oldJtxx.setMeetingPlace(jtxx.getMeetingPlace());
            oldJtxx.setTableId(jtxx.getTableId());
            oldJtxx.setTableName(jtxx.getTableName());
            oldJtxx = jtxxDao.save(oldJtxx);
            return oldJtxx;
        }

    }

    /**
     * 集体学习讨论列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param pageable
     * @param pageImpl
     * @param jtxx
     * @return
     */
    @Override
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, JtxxEntity jtxx,String startTime, String endTime,String ids) {
        ids = DzzUtil.spiltString(ids);
        StringBuilder jtxxSql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        jtxxSql.append(" from JtxxEntity t ");
        //querySql.append(" where t.creChushiId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        jtxxSql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        jtxxSql.append(" and partyOrganizationId in ("+ids+")");


        // 时间
        if (StringUtils.isNotBlank(jtxx.getMeetingTime())) {
            jtxxSql.append(" and substr(t.meetingTime,1,10) between ? and ?");
            para.add(startTime);
            para.add(endTime);
        }

        // 地点
        if (StringUtils.isNotBlank(jtxx.getMeetingPlace()) ) {
            jtxxSql.append(" and t.meetingPlace like ?");
            para.add("%"+jtxx.getMeetingPlace()+"%");
        }

        // 主持人
        if (StringUtils.isNotBlank(jtxx.getCompere())) {
            jtxxSql.append(" and t.compere like ?");
            para.add("%"+jtxx.getCompere()+"%");
        }

        // 记录人
        if (StringUtils.isNotBlank(jtxx.getNoteTaker())) {
            jtxxSql.append(" and t.noteTaker like ?");
            para.add("%"+jtxx.getNoteTaker()+"%");
        }

        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            jtxxSql.append(" order by t.creTime desc ");
        } else {
            jtxxSql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }

        Page<JtxxEntity> page = jtxxDao.query(jtxxSql.toString(), pageable, para.toArray());
        // 添加列表操作
        List<JtxxEntity> content = page.getContent();
      for (JtxxEntity jtxxVal : content) {
            if(!(jtxxVal.getTableId()!=null&&(!jtxxVal.getTableId().equals("")))){
                String id = jtxxVal.getPartyOrganizationId()==null?"":(String)jtxxVal.getPartyOrganizationId();
                String  PartyOrganizationName = "";//组织名称
                String superName ="";//上一级组织名称
                List<Map<String,Object>> listOrgName = zbdydhService.getOrgName(id)==null? new ArrayList():zbdydhService.getOrgName(id);
                if(listOrgName.size()>0){
                    PartyOrganizationName =listOrgName.get(0).get("ORG_NAME")==null?"":(String)listOrgName.get(0).get("ORG_NAME");
                    superName=listOrgName.get(0).get("SUPER_ORG_NAME")==null?"":(String)listOrgName.get(0).get("SUPER_ORG_NAME");
                }
                jtxxVal.setSuperName(superName);
                jtxxVal.setPartyOrganizationName(PartyOrganizationName);
                jtxxVal.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
            }else{
                String id = jtxxVal.getPartyOrganizationId()==null?"":(String)jtxxVal.getPartyOrganizationId();
                String  PartyOrganizationName = "";//组织名称
                String superName ="";//上一级组织名称
                List<Map<String,Object>> listOrgName = zbdydhService.getOrgName(id)==null? new ArrayList():zbdydhService.getOrgName(id);
                if(listOrgName.size()>0){
                    PartyOrganizationName =listOrgName.get(0).get("ORG_NAME")==null?"":(String)listOrgName.get(0).get("ORG_NAME");
                    superName=listOrgName.get(0).get("SUPER_ORG_NAME")==null?"":(String)listOrgName.get(0).get("SUPER_ORG_NAME");
                }
                jtxxVal.setSuperName(superName);
                jtxxVal.setPartyOrganizationName(PartyOrganizationName);
            }
        }
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }


    /**
     * 根据id逻辑集体学习讨论一条信息
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param jtxx
     * @return
     */
    @Override
    public int delete(JtxxEntity jtxx) {
        int n = 0;
        if(StringUtils.isNotBlank(jtxx.getId())){
            try {
                //逻辑删除试题
                String delQuestion = "update JtxxEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.id='"+jtxx.getId()+"'";
                n = jtxxDao.update(delQuestion);
            } catch (Exception e) {
                log.info(e.getMessage());
                log.info("删除集体学习讨论出现异常！");
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
    public JtxxEntity getById(String id) throws Exception {
        return jtxxDao.findOne(id);
    }

}
