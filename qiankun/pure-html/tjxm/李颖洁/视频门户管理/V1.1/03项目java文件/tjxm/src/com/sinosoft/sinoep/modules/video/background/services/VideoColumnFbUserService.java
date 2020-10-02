package com.sinosoft.sinoep.modules.video.background.services;

import com.sinosoft.sinoep.modules.video.background.entity.VideoColumnFbUser;

public interface VideoColumnFbUserService {

    public VideoColumnFbUser getEntity(String columnId, String fbUserId);

}
