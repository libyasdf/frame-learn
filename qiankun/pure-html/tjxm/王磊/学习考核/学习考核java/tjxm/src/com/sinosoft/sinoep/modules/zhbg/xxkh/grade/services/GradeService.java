package com.sinosoft.sinoep.modules.zhbg.xxkh.grade.services;

import java.util.List;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestInfo;

public interface GradeService {

	List<XxkhTestInfo> getGradeList(PageImpl pageImpl,XxkhTestInfo xxkhTestInfo);
}
