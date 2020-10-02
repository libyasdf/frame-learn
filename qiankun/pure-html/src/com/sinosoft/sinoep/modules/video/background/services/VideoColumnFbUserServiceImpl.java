package com.sinosoft.sinoep.modules.video.background.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.modules.video.background.dao.VideoColumnFbUserDao;
import com.sinosoft.sinoep.modules.video.background.entity.VideoColumnFbUser;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoColumnFbUserServiceImpl implements  VideoColumnFbUserService{

    @Autowired
    private VideoColumnFbUserDao dao ;

    public VideoColumnFbUser getEntity(String columnId,String fbUserId){
    	VideoColumnFbUser entity = new VideoColumnFbUser();
        if(StringUtils.isNotBlank(columnId) && StringUtils.isNotBlank(fbUserId)){
            List<VideoColumnFbUser> list = dao.findByVisibleAndColumnIdAndFbUserId(CommonConstants.VISIBLE[1],columnId,fbUserId);
            if(list.size()>0) {
                entity = list.get(0);
            }
        }
        return entity;
    }
}
