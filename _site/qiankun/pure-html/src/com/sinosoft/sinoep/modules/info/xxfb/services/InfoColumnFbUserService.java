package com.sinosoft.sinoep.modules.info.xxfb.services;

import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumnFbUser;

public interface InfoColumnFbUserService {

    public InfoColumnFbUser getEntity(String columnId, String fbUserId);

}
