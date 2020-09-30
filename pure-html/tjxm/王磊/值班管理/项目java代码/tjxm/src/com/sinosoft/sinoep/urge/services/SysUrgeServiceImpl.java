package com.sinosoft.sinoep.urge.services;

import com.sinosoft.sinoep.urge.dao.SysUrgeDao;
import com.sinosoft.sinoep.urge.entity.SysUrge;
import com.sinosoft.sinoep.waitNoflow.services.SysWatiNoflowServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/*
* 催办记录表
* */
@Service
public class SysUrgeServiceImpl implements SysUrgeService {

    private static Log log = LogFactory.getLog(SysWatiNoflowServiceImpl.class);
    @Autowired
    private SysUrgeDao dao;

    /*
    * 保存催办信息
    * */
    @Override
    public boolean saveUrge(SysUrge sysUrge) {
        try {
            if(StringUtils.isNotBlank(sysUrge.getAccepterId()) && StringUtils.isNotBlank(sysUrge.getAcceptTime())
                    && StringUtils.isNotBlank(sysUrge.getContent()) && StringUtils.isNotBlank(sysUrge.getSenderId())
            && StringUtils.isNotBlank(sysUrge.getSendTime())){
                sysUrge.setTimeStamp(String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sysUrge.getSendTime()).getTime()));
                sysUrge = dao.save(sysUrge);
                log.info("保存成功！");
                return true;
            }else{
                log.info("保存失败！");
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            log.info("保存失败！");
            return false;
        }
    }
}
