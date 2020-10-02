package com.sinosoft.sinoep.modules.info.xxfb.services;

import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.modules.info.xxfb.dao.InfoOfficeScopeDao;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoOfficeScope;
import com.sinosoft.sinoep.modules.system.notice.common.SysNoticeConstants;
import com.sinosoft.sinoep.modules.system.notice.common.util.NoticeUtils;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Service
public class InfoOfficeScopeServiceImpl implements InfoOfficeScopeService {

    @Autowired
    private InfoOfficeScopeDao dao;

    @Override
    public InfoOfficeScope getInfoOfficeScope() {
        InfoOfficeScope infoOfficeScope = new InfoOfficeScope();
        StringBuilder sql = new StringBuilder("from InfoOfficeScope t where t.visible = '1' ");
        TypedQuery<InfoOfficeScope> infoOfficeScopeTypedQuery = dao.getEntityManager().createQuery(sql.toString(), InfoOfficeScope.class);
        List<InfoOfficeScope> infoOfficeScopeList = infoOfficeScopeTypedQuery.getResultList();
        if(infoOfficeScopeList.size()>0){
            infoOfficeScope = infoOfficeScopeList.get(0);
        }
        return infoOfficeScope;
    }

    @Override
    public InfoOfficeScope saveFroms(InfoOfficeScope entity) throws Exception{
        if(StringUtils.isNotBlank(entity.getId())){
            InfoOfficeScope oldInfoOfficeScope = dao.findOne(entity.getId());
            oldInfoOfficeScope.setOfficeScopeId(entity.getOfficeScopeId());
            oldInfoOfficeScope.setOfficeScopeName(entity.getOfficeScopeName());
            oldInfoOfficeScope.setUpdateUserId(UserUtil.getCruUserId());
            oldInfoOfficeScope.setUpdateUserName(UserUtil.getCruUserName());
            oldInfoOfficeScope.setUpdateTime(DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss"));
            entity = dao.save(oldInfoOfficeScope);
        }else{
            entity.setCreUserId(UserUtil.getCruUserId());
            entity.setCreUserName(UserUtil.getCruUserName());
            entity.setCreDeptId(UserUtil.getCruDeptId());
            entity.setCreDeptName(UserUtil.getCruDeptName());
            entity.setCreChushiId(UserUtil.getCruChushiId());
            entity.setCreChushiName(UserUtil.getCruChushiName());
            entity.setCreJuId(UserUtil.getCruJuId());
            entity.setCreJuName(UserUtil.getCruJuName());
            entity.setCreTime(DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss"));
            entity.setVisible(SysNoticeConstants.VISIBLE[1]);
            entity = dao.save(entity);
        }


        return entity;
    }
}
