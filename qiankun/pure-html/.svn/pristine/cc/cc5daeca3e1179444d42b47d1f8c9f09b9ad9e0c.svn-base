package com.sinosoft.sinoep.modules.system.config.userdefinesetting.services;

import com.sinosoft.sinoep.modules.system.config.userdefinesetting.dao.UserSettingDao;
import com.sinosoft.sinoep.modules.system.config.userdefinesetting.entity.UserSetting;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户自定义设置SERVICE实现
 */
@Service
public class UserSettingServiceImpl implements UserSettingService {
	
	@Autowired
	private UserSettingDao userSettingDao;

	@Override
	public UserSetting findByUserId(String userId) {
		return userSettingDao.findByUserId(userId);
	}

	@Override
	public UserSetting save(UserSetting userSetting) {
		String userId = UserUtil.getCruUserId();
		UserSetting old = userSettingDao.findByUserId(userId);
		if(old != null){
			userSetting.setId(old.getId());
		}
		userSetting.setUserId(userId);
		return userSettingDao.saveAndFlush(userSetting);
	}


}
