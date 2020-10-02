package com.sinosoft.sinoep.workflow.subflag.services;

import com.sinosoft.sinoep.workflow.subflag.dao.SysFlowSubflagDao;
import com.sinosoft.sinoep.workflow.subflag.entity.SysFlowSubflag;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 流程状态业务实现类
 * @author 李利广
 * @Date 2019年01月14日 16:28:55
 */
@Service
public class SysFlowSubflagServiceImpl implements SysFlowSubflagService {

    @Autowired
    private SysFlowSubflagDao dao;

    /**
     * TODO 保存、修改一条流程状态
     * @author 李利广
     * @Date 2019年01月14日 16:30:20
     * @param subflag
     * @return com.sinosoft.sinoep.workflow.subflag.entity.SysFlowSubflag
     */
    @Override
    public SysFlowSubflag saveSubflag(SysFlowSubflag subflag) throws Exception{
        SysFlowSubflag subflagOld = getByFlowId(subflag);
        if (subflagOld != null){ //修改
            subflagOld.setSubflag(subflag.getSubflag());
            subflag = dao.save(subflagOld);
        }else{  //新增
            subflag = dao.save(subflag);
        }
        return subflag;
    }

    /**
     * TODO 根据流程ID及业务ID获取一条数据
     * @author 李利广
     * @Date 2019年01月14日 16:42:18
     * @param subflag
     * @return com.sinosoft.sinoep.workflow.subflag.entity.SysFlowSubflag
     */
    @Override
    public SysFlowSubflag getByFlowId(SysFlowSubflag subflag) throws Exception{
        if (StringUtils.isNotBlank(subflag.getRecordId())){
            if (StringUtils.isNotBlank(subflag.getFileTypeId()) || StringUtils.isNotBlank(subflag.getWorkFlowId())){
                List listzwf=new ArrayList();
                StringBuilder sBuilder = new StringBuilder("from SysFlowSubflag t where t.recordId = '"+subflag.getRecordId()+"'");
                if (StringUtils.isNotBlank(subflag.getFileTypeId())){
                    sBuilder.append(" and t.fileTypeId =? ");
                    listzwf.add(subflag.getFileTypeId());
                }
                if (StringUtils.isNotBlank(subflag.getWorkFlowId())){
                    sBuilder.append(" and t.workFlowId = ? ");
                    listzwf.add(subflag.getWorkFlowId());
                }
                TypedQuery<SysFlowSubflag> subflagTypedQuery = dao.getEntityManager().createQuery(sBuilder.toString(),SysFlowSubflag.class);
                for(int i=0;i<listzwf.size();i++){
                    subflagTypedQuery.setParameter((i+1),listzwf.get(i));
                }
                List<SysFlowSubflag> subflags = subflagTypedQuery.getResultList();
                if (!subflags.isEmpty()){
                    return subflags.get(0);
                }
            }
        }
        return null;
    }
}
