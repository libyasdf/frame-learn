package com.sinosoft.sinoep.modules.dagl.daly.urge.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dagl.daly.urge.dao.BorrowUrgeDao;
import com.sinosoft.sinoep.modules.dagl.daly.urge.entity.BorrowUrge;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * 档案借阅催还service实现
 * @author 王磊
 * @Date 2019年2月11日 上午10:28:20
 */
@Service
public class BorrowUrgeServiceImpl implements BorrowUrgeService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BorrowUrgeDao borrowUrgeDao;

	/**
	 * 根据借阅id查询催还记录列表
	 * @author 王磊
	 * @Date 2019年2月11日 上午10:38:07
	 * @param pageable
	 * @param pageImpl
	 * @param borrowUrge
	 * @return
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, BorrowUrge borrowUrge) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append(" from BorrowUrge t ");
		querySql.append(" where t.borrowId =? ");
		para.add(borrowUrge.getBorrowId());

		if(StringUtils.isNotBlank(borrowUrge.getCreUserName())){
			querySql.append("   and t.creUserName like ? ");//借阅人
			para.add("%"+borrowUrge.getCreUserName()+"%");
		}

		if(StringUtils.isNotBlank(borrowUrge.getBorrowUserName())){
			querySql.append("   and t.borrowUserName like ? ");//被借阅人
			para.add("%"+borrowUrge.getBorrowUserName()+"%");
		}

		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append(" order by t.creTime desc ");
		} else {
			querySql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}
		Page<BorrowUrge> page = borrowUrgeDao.query(querySql.toString(), pageable,para.toArray());
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	/**
	 * 添加一条催还记录
	 * @author 王磊
	 * @Date 2019年2月16日 下午3:07:53
	 * @param borrowUrge 借阅记录实体
	 * @return
	 */
	@Override
	public boolean addUrgeInfo(BorrowUrge borrowUrge) {
		boolean flag = false;
		try {
			//设置常规属性
			String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			borrowUrge.setCreUserId(UserUtil.getCruUserId());
			borrowUrge.setCreTime(creTime);
	        borrowUrge.setCreUserId(UserUtil.getCruUserId());
	        borrowUrge.setCreUserName(UserUtil.getCruUserName());
	        borrowUrge.setCreChushiName(UserUtil.getCruChushiName());
	        borrowUrge.setCreChushiId(UserUtil.getCruChushiId());
	        borrowUrge.setCreJuName(UserUtil.getCruJuName());
	        borrowUrge.setCreJuId(UserUtil.getCruJuId());
	        borrowUrge.setCreDeptId(UserUtil.getCruDeptId());
	        borrowUrge.setCreDeptName(UserUtil.getCruDeptName());
	        borrowUrgeDao.save(borrowUrge);
	        flag = true;
		} catch (Exception e) {
			log.info("添加档案管理催还记录异常，错误信息："+e.getMessage());
		}
		return flag;
	}
	

}
