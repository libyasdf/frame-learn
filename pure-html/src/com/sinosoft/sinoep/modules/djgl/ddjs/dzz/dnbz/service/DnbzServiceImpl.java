package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dnbz.service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dnbz.dao.DnbzDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dnbz.entity.DnbzEntity;
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

/**
 * @Author: 李帅
 * @Date: 2018/9/9 16:39
 */
@Service
public class DnbzServiceImpl implements DnbzServiceI {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DnbzDao dnbzDao;
    /**
     *
     *党内表彰 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/9/9
     * */
    public DnbzEntity saveDnbz(DnbzEntity dnbz) throws IOException {
        String id = dnbz.getId();
        if (StringUtils.isBlank(id)) {
            dnbz.setCreUserId(UserUtil.getCruUserId());
            dnbz.setCreUserName(UserUtil.getCruUserName());
            dnbz.setCreDeptId(UserUtil.getCruDeptId());
            dnbz.setCreDeptName(UserUtil.getCruDeptName());
            dnbz.setCreChushiId(UserUtil.getCruChushiId());
            dnbz.setCreChushiName(UserUtil.getCruChushiName());
            dnbz.setCreJuId(UserUtil.getCruJuId());
            dnbz.setCreJuName(UserUtil.getCruJuName());
            dnbz.setVisible(CommonConstants.VISIBLE[1]);
            dnbz.setCreTime(JDateToolkit.getNowDate4());
            dnbz.setPartyOrganizationName(java.net.URLDecoder.decode(dnbz.getPartyOrganizationName(),"UTF-8"));
            dnbz = dnbzDao.save(dnbz);
            return dnbz;
        } else {
            DnbzEntity oldDnbz = dnbzDao.findOne(dnbz.getId());
            oldDnbz.setGrantTime(dnbz.getGrantTime());
            oldDnbz.setGrantUnit(dnbz.getGrantUnit());
            oldDnbz.setTypeval(dnbz.getTypeval());
            oldDnbz.setMainDeeds(dnbz.getMainDeeds());
            oldDnbz.setBasicSituation(dnbz.getBasicSituation());
            oldDnbz.setUnitOpinion(dnbz.getUnitOpinion());
            oldDnbz.setApprovalOpinion(dnbz.getApprovalOpinion());
            oldDnbz.setPartyOrganizationId(dnbz.getPartyOrganizationId());
            oldDnbz.setPartyOrganizationName(java.net.URLDecoder.decode(dnbz.getPartyOrganizationName(),"UTF-8"));
            oldDnbz.setMemo(dnbz.getMemo());
            oldDnbz = dnbzDao.save(oldDnbz);
            return oldDnbz;
        }

    }

    /**
     * 党内表彰列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年9月9日
     * @param pageable
     * @param pageImpl
     * @param dnbz
     * @return
     */
    @Override
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, DnbzEntity dnbz, String startTime, String endTime) {

        StringBuilder dnbzSql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        dnbzSql.append(" from DnbzEntity t ");
        //querySql.append(" where t.creChushiId = ? and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        dnbzSql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
       dnbzSql.append(" and t.partyOrganizationId = '" + dnbz.getPartyOrganizationId() + "'");


        // 授予时间
        if (StringUtils.isNotBlank(dnbz.getGrantTime())) {
            dnbzSql.append(" and substr(t.grantTime,1,10) between ? and ?");
            para.add(startTime);
            para.add(endTime);
        }

        // 授予单位
        if (StringUtils.isNotBlank(dnbz.getGrantUnit()) ) {
            dnbzSql.append(" and t.grantUnit like ?");
            para.add("%"+dnbz.getGrantUnit()+"%");
        }


        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            dnbzSql.append(" order by t.creTime desc ");
        } else {
            dnbzSql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }

        Page<DnbzEntity> page = dnbzDao.query(dnbzSql.toString(), pageable, para.toArray());
        // 添加列表操作
        List<DnbzEntity> content = page.getContent();
        for (DnbzEntity dnbzVal : content) {
            dnbzVal.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
        }
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }


    /**
     * 根据id逻辑组织生活会一条信息
     * TODO
     * @author 李帅
     * @Date 2018年9月9日
     * @param dnbz
     * @return
     */
    @Override
    public int delete(DnbzEntity dnbz) {
        int n = 0;
        if(StringUtils.isNotBlank(dnbz.getId())){
            try {
                //逻辑删除试题
                String delQuestion = "update DnbzEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.id='"+dnbz.getId()+"'";
                n = dnbzDao.update(delQuestion);
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
     * @Date 2018年9月9日
     * @param id
     * @return
     */
    @Override
    public DnbzEntity getById(String id) throws Exception {
        return dnbzDao.findOne(id);
    }
}
