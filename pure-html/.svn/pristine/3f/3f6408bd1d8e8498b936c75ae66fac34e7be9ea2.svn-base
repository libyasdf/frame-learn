package com.sinosoft.sinoep.modules.consultManage.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.HqlHelper;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.common.util.Page;
import com.sinosoft.sinoep.modules.consultManage.dao.ConsultManageDao;
import com.sinosoft.sinoep.modules.consultManage.model.ConsultManage;
import com.sinosoft.sinoep.workflow.constant.WorkFlowConfigConsts;
import com.sinosoft.sinoep.workflow.service.WorkFlowClientService;
import com.sinosoft.sinoep.workflow.util.WorkFlowUtil;

import workflow.spring.ProcessRelaDataService;

@Service
public class ConsultManageServiceImp implements ConsultManageService {

    @Autowired
    ConsultManageDao consultManageDao;
    @Autowired
    WorkFlowClientService workFlowClientService;
    @Autowired
    ProcessRelaDataService  processRelaDataService;

    @Override
    public String saveOrUpdate(ConsultManage entity, String workitemid, String idea, String id,
            String userid, String orgid, String sysid) {
        if (StringUtils.isBlank(id)) {
            entity.setOrgid(orgid);
            entity.setSysid(sysid);
            entity.setRegistrantId(userid);
            String date = DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss");
            entity.setCreateDate(date);
            entity.setSubflag(ConfigConsts.START_FLAG);// 设置为起草状态
            consultManageDao.add(entity);
            id = entity.getId();
        }else {
            ConsultManage consult = view(id);
            consult.setOutCode(entity.getOutCode());
            consult.setTitle(entity.getTitle());
            consult.setReadingMark(entity.getReadingMark());
            consult.setReceivedUnit(entity.getReceivedUnit());
            consult.setReceivedUnitId(entity.getReceivedUnitId());
            consult.setFileClass(entity.getFileClass());
            consult.setNeedGrade(entity.getNeedGrade());
            consult.setReturnType(entity.getReturnType());
            consult.setPigeonholeType(entity.getPigeonholeType());
            consult.setArchiveType(entity.getArchiveType());
            consult.setQueryLevel(entity.getQueryLevel());
            consult.setSecGrade(entity.getSecGrade());
            consult.setWrittenDate(entity.getWrittenDate());
            consult.setReceiptDate(entity.getReceiptDate());
            consult.setTransferUnit(entity.getTransferUnit());
            consult.setTransferUnitId(entity.getTransferUnitId());
            consult.setTransferDate(entity.getTransferDate());
            consult.setHandlelimit(entity.getHandlelimit());
            consult.setReturnTime(entity.getReturnTime());
            consult.setCopies(entity.getCopies());
            consult.setPages(entity.getPages());
            consult.setOffice(entity.getOffice());
            consult.setOfficeNumber(entity.getOfficeNumber());
            consult.setNote(entity.getNote());
            consultManageDao.update(consult);
        }
        // 本地服务调用	保存临时意见
		if (!StringUtils.isBlank(idea)) {
			saveTempIdea(workitemid, idea);
		}
		return id;
    }
    
    /**
     * 保存临时意见
     * @param workItemId
     * @param idea
     * @return
     */
    public Boolean saveTempIdea(String workItemId, String idea) {
    	String type = ConfigConsts.SERVICE_TYPE;
        boolean res = false;
        if (type.equals(ConfigConsts.DUBBO_TYPE)) {
            // 本地服务调用R
            res = processRelaDataService.saveIdeaTemp(workItemId, "idea", idea);
        }else if (type.equals(ConfigConsts.REST_TYPE)) {
            // http服务调用
            String url = WorkFlowConfigConsts.WORKFLOW_SERVICE_ROOT_URL + "/processdata/saveIdeaTemp";
            res = WorkFlowUtil.getBoolean(HttpRequestUtil.sendPost(url, "workitemid=" + workItemId
                    + "&fieldName=idea&idea=" + idea));
        }
        return res;
    }

    @Override
    public ConsultManage view(String id) {
        String hql = "from ConsultManage where id = ? ";
        return (ConsultManage) consultManageDao.getUnique(hql, id);
    }

    @SuppressWarnings("unchecked")
	@Override
    public Long getCount(String subflag, String userid, String orgid, String sysid, String flowType) {
        String hql = " from ConsultManage where subflag = ? and doctype = ? and registrantId = ? and sysid = ? and orgid = ? ";
        List<ConsultManage> list = (List<ConsultManage>) consultManageDao.pageQuery(hql, subflag, flowType, userid,
                sysid, orgid);
        return (long) list.size();
    }

    /**
     * <B>方法名称：</B><BR>
     * <B>概要说明：</B><BR>
     * 
     * @see com.sinosoft.oa.archives.out.service.ArchivesOutService#deleteDoc(java.lang.String)
     */
    @Override
    public String deleteDoc(String id, String userid, String orgid, String sysid) {
        String result = "failed";
        ConsultManage entity = view(id);
        if (entity != null) {
            consultManageDao.delete(entity);
            result = "success";
        }
        return result;
    }

    @Override
    public String updateSubFlag(String id, String subFlag) {
        ConsultManage entity = view(id);
        entity.setSubflag(subFlag);
        consultManageDao.update(entity);
        return "success";
    }

    @Override
    public Page pageList(String userid, int pageNum, int showCount, String title, String endTime, String beginTime) {
        HqlHelper helper = new HqlHelper(ConsultManage.class);
        helper.addCondition(" registrantId = ? ", userid);
        if (StringUtils.isNotBlank(title)) {
            helper.addCondition(" title like ? ", "%" + title + "%");
        }
        if (StringUtils.isNotBlank(beginTime)) {
            helper.addCondition("receiptDate >= ? ", beginTime);
        }
        if (StringUtils.isNotBlank(endTime)) {
            helper.addCondition("receiptDate <= ? ", endTime);
        }
        helper.addOrder("createDate", false);// 按cname 降序排列
        return helper.buildPageBean(pageNum, showCount, consultManageDao);
    }

}
