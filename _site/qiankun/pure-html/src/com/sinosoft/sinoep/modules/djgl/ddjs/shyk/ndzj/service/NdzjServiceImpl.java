package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndzj.service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.djgl.ddjs.dzz.util.DzzUtil;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndzj.dao.NdzjDao;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndzj.entity.NdzjEntity;
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

/**
 * @Author: 李帅
 * @Date: 2018/9/8 8:10
 */
@Service
public class NdzjServiceImpl implements NdzjServiceI{
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NdzjDao ndzjDao;
    @Autowired
    private ZbdydhService zbdydhService;
    /**
     * 年度总结列表查询（带分页）
     * TODO
     * @author 李帅
     * @Date 2018年9月8日
     * @param pageable
     * @param pageImpl
     * @param ndzj
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, NdzjEntity ndzj, String startTime, String endTime,String typeVal,String ids) {

        StringBuilder ndzjSql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        ndzjSql.append(" from NdzjEntity t ");
        ndzjSql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        if(typeVal.equals("hz")){
            ids = DzzUtil.spiltString(ids);
            ndzjSql.append(" and t.partyOrganizationId  in ("+ids+")");
        }

        // 年度
        if (StringUtils.isNotBlank(ndzj.getReportingTime())) {
            ndzjSql.append(" and substr(t.reportingTime,1,10) between ? and ?");
            para.add(startTime);
            para.add(endTime);
        }
        if (StringUtils.isNotBlank(ndzj.getPartyOrganizationName())) {
            ndzjSql.append(" and t.partyOrganizationName = ?");
            para.add(ndzj.getPartyOrganizationName());
        }
        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            ndzjSql.append(" order by t.creTime desc ");
        } else {
            ndzjSql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
        }

        Page<NdzjEntity> page = ndzjDao.query(ndzjSql.toString(), pageable, para.toArray());
        // 添加列表操作
        List<NdzjEntity> content = page.getContent();
        for (NdzjEntity ndzjVal : content) {
            String id = ndzjVal.getPartyOrganizationId()==null?"":(String)ndzjVal.getPartyOrganizationId();
            String  PartyOrganizationName = "";//组织名称
            String superName ="";//上一级组织名称
            List<Map<String,Object>> listOrgName = zbdydhService.getOrgName(id)==null? new ArrayList():zbdydhService.getOrgName(id);
            if(listOrgName.size()>0){
                PartyOrganizationName =listOrgName.get(0).get("ORG_NAME")==null?"":(String)listOrgName.get(0).get("ORG_NAME");
                superName=listOrgName.get(0).get("SUPER_ORG_NAME")==null?"":(String)listOrgName.get(0).get("SUPER_ORG_NAME");
            }
            ndzjVal.setSuperName(superName);
            ndzjVal.setPartyOrganizationName(PartyOrganizationName);
            ndzjVal.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
        }
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }

    /**
     *
     *年底总结 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/9/8
     * */
    public NdzjEntity saveNdzj(NdzjEntity ndzj) throws IOException {
        String id = ndzj.getId();
        if (StringUtils.isBlank(id)) {
            ndzj.setCreUserId(UserUtil.getCruUserId());
            ndzj.setCreUserName(UserUtil.getCruUserName());
            ndzj.setCreDeptId(UserUtil.getCruDeptId());
            ndzj.setCreDeptName(UserUtil.getCruDeptName());
            ndzj.setCreChushiId(UserUtil.getCruChushiId());
            ndzj.setCreChushiName(UserUtil.getCruChushiName());
            ndzj.setCreJuId(UserUtil.getCruJuId());
            ndzj.setCreJuName(UserUtil.getCruJuName());
            ndzj.setVisible(CommonConstants.VISIBLE[1]);
            ndzj.setCreTime(JDateToolkit.getNowDate4());
        //    ndzj.setPartyOrganizationName(java.net.URLDecoder.decode(ndzj.getPartyOrganizationName(),"UTF-8"));
            ndzj = ndzjDao.save(ndzj);
            return ndzj;
        } else {
            NdzjEntity oldNdzj = ndzjDao.findOne(ndzj.getId());
            oldNdzj.setReportingTime(ndzj.getReportingTime());
            oldNdzj.setSummaryContent(ndzj.getSummaryContent());
            oldNdzj.setAttr1(ndzj.getAttr1());
            oldNdzj.setAttr2(ndzj.getAttr2());
            oldNdzj.setPartyOrganizationName(java.net.URLDecoder.decode(ndzj.getPartyOrganizationName(),"UTF-8"));
            oldNdzj.setPartyOrganizationId(ndzj.getPartyOrganizationId());
            oldNdzj = ndzjDao.save(oldNdzj);
            return oldNdzj;
        }

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
    public NdzjEntity getById(String id) throws Exception {
        return ndzjDao.findOne(id);
    }
    /**
     * 根据id逻辑删除年底总结一条信息
     * TODO
     * @author 李帅
     * @Date 2018年8月28日
     * @param ndzj
     * @return
     */
    @Override
    public int delete(NdzjEntity ndzj) {
        int n = 0;
        if(StringUtils.isNotBlank(ndzj.getId())){
            try {
                //逻辑删除试题
                String delQuestion = "update NdzjEntity q set q.visible='"+CommonConstants.VISIBLE[0]+"' where q.id='"+ndzj.getId()+"'";
                n = ndzjDao.update(delQuestion);
            } catch (Exception e) {
                log.info(e.getMessage());
                log.info("删除年底总结出现异常！");
                n=0;
            }
        }
        return n;
    }

    /**
     * 新增时判断今年是否有年底总结
     * TODO
     * @author 李帅
     * @Date 2018年10月10日
     * @param annual
     * @param partyOrganizationId
     * @return
     */
    public NdzjEntity addFrom(String annual,String partyOrganizationId){
        NdzjEntity ndjh = new  NdzjEntity();
        ndjh = ndzjDao.findByReportingTime(annual,partyOrganizationId,"1")==null?ndjh:ndzjDao.findByReportingTime(annual,partyOrganizationId,"1");
        return ndjh;
    }

}
