package com.sinosoft.sinoep.modules.zhbg.kqgl.statistics.service;

import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;

public interface DirectorStatsService {

	PageImpl getList(Pageable pageable,String timeRange, String deptid,String flg)throws Exception ;

}
