package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util.DzzUtil;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.dao.DrhdDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.drhd.entity.DrhdEntity;
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

/**TODO 三会一课 党日活动Service
 * @Author: 李帅
 * @Date: 2018/8/31 10:47
 */
@Service
public class DrhdServiceImpl implements DrhdService {
    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private DrhdDao drhdDao;
    @Autowired
    private ZbdydhService zbdydhService;
    /**
     *
     *党日活动 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/8/31
     * */
    public DrhdEntity saveDrhd(DrhdEntity drhd) throws IOException {
        String id = drhd.getId();
        if (StringUtils.isBlank(id)) {
            drhd.setCreUserId(UserUtil.getCruUserId());
            drhd.setCreUserName(UserUtil.getCruUserName());
            drhd.setCreDeptId(UserUtil.getCruDeptId());
            drhd.setCreDeptName(UserUtil.getCruDeptName());
            drhd.setCreChushiId(UserUtil.getCruChushiId());
            drhd.setCreChushiName(UserUtil.getCruChushiName());
            drhd.setCreJuId(UserUtil.getCruJuId());
            drhd.setCreJuName(UserUtil.getCruJuName());
            drhd.setVisible(CommonConstants.VISIBLE[1]);
            drhd.setCreTime(JDateToolkit.getNowDate4());
            drhd.setPartyOrganizationName(java.net.URLDecoder.decode(drhd.getPartyOrganizationName(),"UTF-8"));
            drhd = drhdDao.save(drhd);
            return drhd;
        } else {
            DrhdEntity oldDrhd = drhdDao.findOne(drhd.getId());
            oldDrhd.setCompere(drhd.getCompere());
            oldDrhd.setNoteTaker(drhd.getNoteTaker());
            oldDrhd.setNumberOfPeople(drhd.getNumberOfPeople());
            oldDrhd.setActualNumber(drhd.getActualNumber());
            oldDrhd.setPrimaryCoverage(drhd.getPrimaryCoverage());
            oldDrhd.setAttendants(drhd.getAttendants());
            oldDrhd.setLeaveAndReasons(drhd.getLeaveAndReasons());
            oldDrhd.setSeats(drhd.getSeats());
            oldDrhd.setMeetingSituation(drhd.getMeetingSituation());
            oldDrhd.setPartyOrganizationName(java.net.URLDecoder.decode(drhd.getPartyOrganizationName(),"UTF-8"));
            oldDrhd.setPartyOrganizationId(drhd.getPartyOrganizationId());
            oldDrhd.setMeetingTime(drhd.getMeetingTime());
            oldDrhd.setMeetingPlace(drhd.getMeetingPlace());
            oldDrhd.setTableId(drhd.getTableId());
            oldDrhd.setTableName(drhd.getTableName());
            oldDrhd = drhdDao.save(oldDrhd);
            return oldDrhd;
        }

    }

    /**
     * 党日活动列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年8月31日
     * @param pageable
     * @param pageImpl
     * @param drhd
     * @return
     */
    @Override
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DrhdEntity drhd,String startTime, String endTime,String ids) {
        ids = DzzUtil.spiltString(ids);
        StringBuilder drhdSql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        drhdSql.append(" from DrhdEntity t ");
        //querySql.append(" where t.creChushiId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        drhdSql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        drhdSql.append(" and partyOrganizationId in ("+ids+")");

        // 时间
        if (StringUtils.isNotBlank(drhd.getMeetingTime())) {
            drhdSql.append(" and substr(t.meetingTime,1,10) between ? and ?");
            para.add(startTime);
            para.add(endTime);
        }

        // 地点
        if (StringUtils.isNotBlank(drhd.getMeetingPlace()) ) {
            drhdSql.append(" and t.meetingPlace like ?");
            para.add("%"+drhd.getMeetingPlace()+"%");
        }

        // 主持人
        if (StringUtils.isNotBlank(drhd.getCompere())) {
            drhdSql.append(" and t.compere like ?");
            para.add("%"+drhd.getCompere()+"%");
        }

        // 记录人
        if (StringUtils.isNotBlank(drhd.getNoteTaker())) {
            drhdSql.append(" and t.noteTaker like ?");
            para.add("%"+drhd.getNoteTaker()+"%");
        }

        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            drhdSql.append(" order by t.creTime desc ");
        } else {
            drhdSql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }

        Page<DrhdEntity> page = drhdDao.query(drhdSql.toString(), pageable, para.toArray());
        // 添加列表操作
        List<DrhdEntity> content = page.getContent();
        for (DrhdEntity drhdVal : content) {
            String id = drhdVal.getPartyOrganizationId()==null?"":(String)drhdVal.getPartyOrganizationId();
            String  PartyOrganizationName = "";//组织名称
            String superName ="";//上一级组织名称
            List<Map<String,Object>> listOrgName = zbdydhService.getOrgName(id)==null? new ArrayList():zbdydhService.getOrgName(id);
            if(listOrgName.size()>0){
                PartyOrganizationName =listOrgName.get(0).get("ORG_NAME")==null?"":(String)listOrgName.get(0).get("ORG_NAME");
                superName=listOrgName.get(0).get("SUPER_ORG_NAME")==null?"":(String)listOrgName.get(0).get("SUPER_ORG_NAME");
            }
            drhdVal.setSuperName(superName);
            drhdVal.setPartyOrganizationName(PartyOrganizationName);

            drhdVal.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
        }
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }


    /**
     * 根据id逻辑党日活动一条信息
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param drhd
     * @return
     */
    @Override
    public int delete(DrhdEntity drhd) {
        int n = 0;
        if(StringUtils.isNotBlank(drhd.getId())){
            try {
                //逻辑删除试题
                String delQuestion = "update DrhdEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.id='"+drhd.getId()+"'";
                n = drhdDao.update(delQuestion);
            } catch (Exception e) {
                log.info(e.getMessage());
                log.info("删除党日活动出现异常！");
                n=0;
            }
        }
        return n;
    }
    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年8月31日
     * @param id
     * @return
     */
    @Override
    public DrhdEntity getById(String id) throws Exception {
        return drhdDao.findOne(id);
    }

}
