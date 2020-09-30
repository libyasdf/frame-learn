package com.sinosoft.sinoep.modules.zhbg.xxkh.learntime.dao;

import java.util.List;
import com.sinosoft.sinoep.modules.zhbg.xxkh.learntime.entity.XxkhLearnTime;

public interface XxkhLearnTimeRepository {
	List<XxkhLearnTime> findList(XxkhLearnTime learnTime);
	
	List<XxkhLearnTime> findBuMenList(XxkhLearnTime learnTime);
	
	List<XxkhLearnTime> findDeptUserList(XxkhLearnTime learnTime);
}
