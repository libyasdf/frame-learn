package com.sinosoft.sinoep.message.alert.services;

import com.sinosoft.sinoep.message.alert.entity.MessageAlert;

public interface MessageAlertService {

	MessageAlert save(MessageAlert messageAlert);
	
	MessageAlert update(MessageAlert messageAlert);
	
	MessageAlert getByUserId(MessageAlert messageAlert);

	int updateTheme(MessageAlert alert);
	
	int updateType(MessageAlert alert);
}
