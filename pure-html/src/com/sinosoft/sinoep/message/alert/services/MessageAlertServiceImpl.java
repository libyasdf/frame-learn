package com.sinosoft.sinoep.message.alert.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.sinoep.message.alert.dao.MessageAlertDao;
import com.sinosoft.sinoep.message.alert.entity.MessageAlert;

@Service
@Transactional
public class MessageAlertServiceImpl implements MessageAlertService {

	@Autowired
	private MessageAlertDao messageAlertDao;

	@Override
	public MessageAlert save(MessageAlert messageAlert) {
		
		return messageAlertDao.saveAndFlush(messageAlert);
	}

	@Override
	public MessageAlert update(MessageAlert messageAlert) {
		 int updatePath = messageAlertDao.updatePath(messageAlert.getSettingPath(),messageAlert.getUserId());
		 if (updatePath>0) {
			 return messageAlert;
		 }else {
			 return null; 
		 }
		 
	}

	@Override
	public MessageAlert getByUserId(MessageAlert messageAlert) {
		 List<MessageAlert> byUserId = messageAlertDao.getByUserId(messageAlert.getUserId());
		 if (byUserId!=null&&byUserId.size()>0) {
			return byUserId.get(0);
		}else {
			return null;
		}
	}

	@Override
	public int updateTheme(MessageAlert alert) {
		 int updateTheme = messageAlertDao.updateTheme(alert.getThemeClass(),alert.getUserId());
		 if (updateTheme>0) {
			 return updateTheme;
		 }else {
			 return 0; 
		 }
	}

	@Override
	public int updateType(MessageAlert alert) {
		int updateType = messageAlertDao.updateType(alert.getClassify(), alert.getUserId());
		if (updateType>0) {
			 return updateType;
		 }else {
			 return 0; 
		 }
	}
}
