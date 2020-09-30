package com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.service;

import java.util.List;
import com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.entity.DeptCount;

public interface DirectorStatsService {

	List<DeptCount> getList(String timeRange, String deptid)throws Exception ;

}
