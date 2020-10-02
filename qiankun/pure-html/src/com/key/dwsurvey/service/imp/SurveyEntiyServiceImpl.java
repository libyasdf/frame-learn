package com.key.dwsurvey.service.imp;

import com.key.dwsurvey.dao.SurveyDirectoryDao;
import com.key.dwsurvey.dao.SurveyEntiyDao;
import com.key.dwsurvey.entity.SurveyDetail;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.entity.SurveyEntiy;
import com.key.dwsurvey.service.SurveyDetailManager;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import com.key.dwsurvey.service.SurveyEntiyService;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.modules.system.notice.common.SysNoticeConstants;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SurveyEntiyServiceImpl implements SurveyEntiyService{

    @Autowired
    private SurveyEntiyDao surveyEntiyDao;

    @Autowired
    private SurveyDirectoryDao surveyDirectoryDao;

    @Autowired
    private SurveyDirectoryManager directoryManager;



    @Override
    public SurveyEntiy saveSurvey(SurveyEntiy surveyEntiy) throws Exception {
        if(StringUtils.isNotBlank(surveyEntiy.getId())){
            SurveyEntiy oldSurvey = surveyEntiyDao.findOne(surveyEntiy.getId());
            oldSurvey.setContent(surveyEntiy.getContent());
            oldSurvey.setTitle(surveyEntiy.getTitle());
            oldSurvey.setWenjuanDeptId(surveyEntiy.getWenjuanDeptId());
            oldSurvey.setStatus(surveyEntiy.getStatus());
            oldSurvey.setUpdateUserId(UserUtil.getCruUserId());
            oldSurvey.setUpdateUserName(UserUtil.getCruUserName());
            oldSurvey.setUpdateTime(DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss"));
            surveyEntiy = surveyEntiyDao.save(oldSurvey);

            SurveyDirectory survey = directoryManager.findById(surveyEntiy.getSurveyId());
            survey.setSubflag("0");
            directoryManager.save(survey);
        }else{
            surveyEntiy.setCreUserId(UserUtil.getCruUserId());
            surveyEntiy.setCreUserName(UserUtil.getCruUserName());
            surveyEntiy.setCreDeptId(UserUtil.getCruDeptId());
            surveyEntiy.setCreDeptName(UserUtil.getCruDeptName());
            surveyEntiy.setCreChushiId(UserUtil.getCruChushiId());
            surveyEntiy.setCreChushiName(UserUtil.getCruChushiName());
            surveyEntiy.setCreJuId(UserUtil.getCruJuId());
            surveyEntiy.setCreJuName(UserUtil.getCruJuName());
            surveyEntiy.setCreTime(DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss"));
            surveyEntiy.setSubflag(SysNoticeConstants.FLAG[0]);
            surveyEntiy.setVisible(SysNoticeConstants.VISIBLE[1]);
            surveyEntiy = surveyEntiyDao.save(surveyEntiy);

            SurveyDirectory survey = directoryManager.findById(surveyEntiy.getSurveyId());
            survey.setSubflag("0");
            directoryManager.save(survey);
        }
        return surveyEntiy;
    }

    @Override
    public SurveyEntiy updateFlag(String id, String flag) throws Exception {

        SurveyEntiy surveyEntiy = surveyEntiyDao.findOne(id);
        SurveyDirectory survey = directoryManager.findById(surveyEntiy.getSurveyId());
        surveyEntiy.setSubflag(flag);
        if (flag.equals(SysNoticeConstants.FLAG[1])) {
            surveyEntiy.setPublishTime(DateUtil.getDateText(new Date(), "yyyy-MM-dd"));
            survey.setSurveyState(1);
        }
        surveyEntiy = surveyEntiyDao.save(surveyEntiy);

        survey.setSubflag(flag);
        directoryManager.save(survey);
        return surveyEntiy;
    }

    @Override
    public Boolean delSurvey(String surveyId) throws Exception {
        StringBuilder sql = new StringBuilder();
        int count = 0;
        sql.append("update SurveyEntiy t set t.visible = '"+SysNoticeConstants.VISIBLE[0]+"' where t.id = '"+surveyId+"'");
        count = surveyEntiyDao.update(sql.toString());
        return count == 0?false:true;
    }

    @Override
    public SurveyEntiy getView(String surveyId) throws Exception {
        SurveyEntiy surveyEntiy = new SurveyEntiy();
        List<SurveyEntiy> list = new ArrayList<SurveyEntiy>();
        StringBuilder sql = new StringBuilder("from SurveyEntiy t where t.visible = '"+SysNoticeConstants.VISIBLE[1]+"' and t.surveyId = ?");
        TypedQuery<SurveyEntiy> surveyEntiyTypedQuery = surveyEntiyDao.getEntityManager().createQuery(sql.toString(), SurveyEntiy.class);
        surveyEntiyTypedQuery.setParameter(1,surveyId.toString());
        list = surveyEntiyTypedQuery.getResultList();
        if(list.size()>0){
            surveyEntiy = list.get(0);
        }
        return surveyEntiy;
    }

    @Override
    public SurveyEntiy getIdView(String id) throws Exception {
        return surveyEntiyDao.findOne(id);
    }
}
