package com.sinosoft.sinoep.modules.info.xxfb.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.modules.info.xxfb.common.InfoConstants;
import com.sinosoft.sinoep.modules.info.xxfb.dao.InfoColumnFbUserDao;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumnFbUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoColumnFbUserServiceImpl implements  InfoColumnFbUserService{

    @Autowired
    private InfoColumnFbUserDao dao ;

    public InfoColumnFbUser getEntity(String columnId,String fbUserId){
        InfoColumnFbUser entity = new InfoColumnFbUser();
        if(StringUtils.isNotBlank(columnId) && StringUtils.isNotBlank(fbUserId)){
            List<InfoColumnFbUser> list = dao.findByVisibleAndColumnIdAndFbUserId(CommonConstants.VISIBLE[1],columnId,fbUserId);
            if(list.size()>0) {
                entity = list.get(0);
            }
        }
        return entity;
    }
}
