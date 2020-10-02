package com.sinosoft.sinoep.modules.taskplan.service;

import com.sinosoft.sinoep.common.util.Page;

public interface TaskPlanService {

	Page getPageList(Integer pageNum, Integer showCount, String title, String startTime,String endTime, String subflag);

}
