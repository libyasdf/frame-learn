package com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.services;

import java.util.List;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.kqgl.kqcx.entity.PersionInfo;

public interface AttendenceTableService {
	
	/**
	 * 考勤表查询
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年8月27日 下午5:24:39
	 * @param month
	 * @param userids
	 * @return
	 */
	PageImpl getList(PageImpl pageImpl,String month, String userids,String isAll,Integer total)throws Exception ;
	
}
