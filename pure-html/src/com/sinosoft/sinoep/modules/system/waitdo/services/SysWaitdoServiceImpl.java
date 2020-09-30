package com.sinosoft.sinoep.modules.system.waitdo.services;

import com.sinosoft.sinoep.api.dept.vo.Dept;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.waitdo.dao.SysWaitdoDao;
import com.sinosoft.sinoep.modules.system.waitdo.entity.SysWaitdo;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 系统待办service业务层实现类
 * @author 李利广
 * @Date 2018年5月25日 上午10:16:04
 */
@Service
public class SysWaitdoServiceImpl implements SysWaitdoService {
	
	@Autowired
	private SysWaitdoDao waitdoDao;

	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * TODO 查询系统待办列表
	 * @author 李利广
	 * @Date 2018年5月25日 下午2:56:33
	 * @param pageImpl
	 * @param workFlowId
	 * @param fileTypeId
	 * @param startDate
	 * @param endDate
	 * @param title
	 * @return
	 */
	@Override
	public PageImpl getWaitdoList(PageImpl pageImpl,String workFlowId,String fileTypeId,String startDate,String endDate,String title) throws Exception{
		String receiveDeptId = "";
		List<Dept> listDepts = userInfoService.getAllSuperDeptById(UserUtil.getCruDeptId());
		for(Dept dept :listDepts){
			receiveDeptId += "'"+dept.getDeptid()+"',";
		}
		receiveDeptId = receiveDeptId.substring(0,receiveDeptId.length()-1);
		List listzwf = new ArrayList<>();
		StringBuilder sb = new StringBuilder("from SysWaitdo t ");
		sb.append(" where ((t.receiveDeptId in ("+receiveDeptId+") and t.receiveUserId is null and t.receiveRoleNo is null)");
		sb.append(" or(t.receiveDeptId = '"+UserUtil.getCruDeptId()+"' and t.receiveUserId = '"+UserUtil.getCruUserId()+"' and t.receiveRoleNo is null)");
		sb.append(" or(t.receiveDeptId in ("+receiveDeptId+") and t.receiveUserId is null and");
		sb.append(" t.receiveRoleNo in ("+CommonUtils.commomSplit(UserUtil.getCruUserRoleNo())+")))");
		if(StringUtils.isNotBlank(workFlowId)){
			workFlowId = CommonUtils.commomSplit(workFlowId);
			sb.append(" and t.workflowid in (" + workFlowId + ")");
		}
		if(StringUtils.isNotBlank(fileTypeId)){
			fileTypeId = CommonUtils.commomSplit(fileTypeId);
			sb.append(" and t.fileTypeId in (" + fileTypeId + ")");
		}
		if(StringUtils.isNotBlank(title)){
			sb.append(" and t.title like ? ");
			listzwf.add("%" + title + "%");
		}
		if(StringUtils.isNotBlank(startDate)){
			sb.append(" and substr(t.receiveTime, 1, 10)  >= ? ");
			listzwf.add(startDate);
		}
		if(StringUtils.isNotBlank(endDate)){
			sb.append(" and substr(t.receiveTime, 1, 10) <= ? ");
			listzwf.add(endDate);
		}
		sb.append(" order by t.receiveTime desc");
		Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
		Page<SysWaitdo> page = waitdoDao.query(sb.toString(), pageable, listzwf.toArray());
		pageImpl.getData().setRows(page.getContent());
		pageImpl.getData().setTotal((int)page.getTotalElements());
		return pageImpl;
	}

	/**
	 * 根据ID查询一条待办
	 * @param id
	 * @return
	 */
	@Override
	public SysWaitdo getById(String id){
		return waitdoDao.findOne(id);
	}

}
