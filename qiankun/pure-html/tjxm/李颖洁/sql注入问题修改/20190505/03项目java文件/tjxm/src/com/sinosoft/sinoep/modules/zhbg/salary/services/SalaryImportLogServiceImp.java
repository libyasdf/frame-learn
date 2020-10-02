package com.sinosoft.sinoep.modules.zhbg.salary.services;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.worklog.entity.WorkLog;
import com.sinosoft.sinoep.modules.zhbg.salary.dao.SalaryImportLogDao;
import com.sinosoft.sinoep.modules.zhbg.salary.entity.SalaryImportLog;

@Service
public class SalaryImportLogServiceImp implements SalaryImportLogService{
	@Autowired
	SalaryImportLogDao dao;
	/**
	 * 
	 * 工资导入日志的分页数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月30日 上午10:34:38
	 * @param pageable
	 * @param pageImpl
	 * @param name
	 * @param yearMonth
	 * @return
	 */
	@Override
	public PageImpl getlistBootHql(Pageable pageable, PageImpl pageImpl, String name, String yearMonth,String personId) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from SalaryImportLog t ");
		querySql.append("	where  t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		if (StringUtils.isNotBlank(name)) {
			querySql.append("   and t.creUserName like ?");
			para.add("%"+name+"%");
		}
		if (StringUtils.isNotBlank(yearMonth)) {
			querySql.append("   and t.creTime like ?");
			para.add("%"+yearMonth+"%");
		}
		if (StringUtils.isNotBlank(personId)) {
			querySql.append("   and t.creUserId in (" +CommonUtils.solveSqlInjectionOfIn(personId,para) +")");
		}
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
				querySql.append("  order by t.creTime desc) ");
		}else{
				querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}
		
		
        Page<WorkLog> page = dao.query(querySql.toString(), pageable,para.toArray());
        //草稿列表，添加操作列
     
        return new PageImpl((int)page.getTotalElements(),page.getContent());
	}
	
	/**
	 * 根据id查询工资导入日志
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月31日 上午11:35:59
	 * @param id
	 * @return
	 */
	@Override
	public SalaryImportLog getById(String id) {
		return dao.findOne(id);
	}

}
