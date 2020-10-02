package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zzshh.service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util.DzzUtil;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zbdydh.service.ZbdydhService;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zzshh.dao.ZzshhDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.zzshh.entity.ZzshhEntity;
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

/**TODO 三会一课 组织生活会Service
 * @Author: 李帅
 * @Date: 2018/8/28 18:47
 */
@Service
public class ZzshhServiceImpl implements ZzshhService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ZbdydhService zbdydhService;
    @Autowired
    private ZzshhDao zzshhDao;
    /**
     *
     *组织生活会 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/8/28
     * */
    public ZzshhEntity saveZzshh(ZzshhEntity zzshh)throws IOException {
        String id = zzshh.getId();
        if (StringUtils.isBlank(id)) {
            zzshh.setCreUserId(UserUtil.getCruUserId());
            zzshh.setCreUserName(UserUtil.getCruUserName());
            zzshh.setCreDeptId(UserUtil.getCruDeptId());
            zzshh.setCreDeptName(UserUtil.getCruDeptName());
            zzshh.setCreChushiId(UserUtil.getCruChushiId());
            zzshh.setCreChushiName(UserUtil.getCruChushiName());
            zzshh.setCreJuId(UserUtil.getCruJuId());
            zzshh.setCreJuName(UserUtil.getCruJuName());
            zzshh.setVisible(CommonConstants.VISIBLE[1]);
            zzshh.setCreTime(JDateToolkit.getNowDate4());
            zzshh.setPartyOrganizationName(java.net.URLDecoder.decode(zzshh.getPartyOrganizationName(),"UTF-8"));
            zzshh = zzshhDao.save(zzshh);
            return zzshh;
        } else {
            ZzshhEntity oldZzshh = zzshhDao.findOne(zzshh.getId());
            oldZzshh.setCompere(zzshh.getCompere());
            oldZzshh.setNoteTaker(zzshh.getNoteTaker());
            oldZzshh.setNumberOfPeople(zzshh.getNumberOfPeople());
            oldZzshh.setActualNumber(zzshh.getActualNumber());
            oldZzshh.setPrimaryCoverage(zzshh.getPrimaryCoverage());
            oldZzshh.setAttendants(zzshh.getAttendants());
            oldZzshh.setLeaveAndReasons(zzshh.getLeaveAndReasons());
            oldZzshh.setSeats(zzshh.getSeats());
            oldZzshh.setPartyOrganizationName(java.net.URLDecoder.decode(zzshh.getPartyOrganizationName(),"UTF-8"));
            // oldZzshh.setMeetingSituation(zzshh.getMeetingSituation());
            oldZzshh.setPartyOrganizationName(zzshh.getPartyOrganizationName());
            oldZzshh.setPartyOrganizationId(zzshh.getPartyOrganizationId());
            oldZzshh.setMeetingTime(zzshh.getMeetingTime());
            oldZzshh.setMeetingPlace(zzshh.getMeetingPlace());
            oldZzshh.setTableId(zzshh.getTableId());
            oldZzshh.setTableName(zzshh.getTableName());
            oldZzshh = zzshhDao.save(oldZzshh);
            return oldZzshh;
        }

    }

    /**
     * 组织生活会列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param pageable
     * @param pageImpl
     * @param zzshh
     * @return
     */
    @Override
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, ZzshhEntity zzshh,String startTime, String endTime,String ids) {
        ids = DzzUtil.spiltString(ids);
        StringBuilder zzshhSql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        zzshhSql.append(" from ZzshhEntity t ");
        //querySql.append(" where t.creChushiId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        zzshhSql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        zzshhSql.append(" and partyOrganizationId in ("+ids+")");


        // 时间
        if (StringUtils.isNotBlank(zzshh.getMeetingTime())) {
            zzshhSql.append(" and substr(t.meetingTime,1,10) between ? and ?");
            para.add(startTime);
            para.add(endTime);
        }

        // 地点
        if (StringUtils.isNotBlank(zzshh.getMeetingPlace()) ) {
            zzshhSql.append(" and t.meetingPlace like ?");
            para.add("%"+zzshh.getMeetingPlace()+"%");
        }

        // 主持人
        if (StringUtils.isNotBlank(zzshh.getCompere())) {
            zzshhSql.append(" and t.compere like ?");
            para.add("%"+zzshh.getCompere()+"%");
        }

        // 记录人
        if (StringUtils.isNotBlank(zzshh.getNoteTaker())) {
            zzshhSql.append(" and t.noteTaker like ?");
            para.add("%"+zzshh.getNoteTaker()+"%");
        }

        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            zzshhSql.append(" order by t.creTime desc ");
        } else {
            zzshhSql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }

        Page<ZzshhEntity> page = zzshhDao.query(zzshhSql.toString(), pageable, para.toArray());
        // 添加列表操作
        List<ZzshhEntity> content = page.getContent();
        for (ZzshhEntity zzshhVal : content) {
            String id = zzshhVal.getPartyOrganizationId()==null?"":(String)zzshhVal.getPartyOrganizationId();
            String  PartyOrganizationName = "";//组织名称
            String superName ="";//上一级组织名称
            List<Map<String,Object>> listOrgName = zbdydhService.getOrgName(id)==null? new ArrayList():zbdydhService.getOrgName(id);
            if(listOrgName.size()>0){
                PartyOrganizationName =listOrgName.get(0).get("ORG_NAME")==null?"":(String)listOrgName.get(0).get("ORG_NAME");
                superName=listOrgName.get(0).get("SUPER_ORG_NAME")==null?"":(String)listOrgName.get(0).get("SUPER_ORG_NAME");
            }
            zzshhVal.setSuperName(superName);
            zzshhVal.setPartyOrganizationName(PartyOrganizationName);
            zzshhVal.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
        }
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }


    /**
     * 根据id逻辑组织生活会一条信息
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param zzshh
     * @return
     */
    @Override
    public int delete(ZzshhEntity zzshh) {
        int n = 0;
        if(StringUtils.isNotBlank(zzshh.getId())){
            try {
                //逻辑删除试题
                String delQuestion = "update ZzshhEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.id='"+zzshh.getId()+"'";
                n = zzshhDao.update(delQuestion);
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
     * @Date 2018年8月28日
     * @param id
     * @return
     */
    @Override
    public ZzshhEntity getById(String id) throws Exception {
        return zzshhDao.findOne(id);
    }

}
