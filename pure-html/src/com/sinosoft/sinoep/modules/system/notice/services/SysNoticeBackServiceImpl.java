package com.sinosoft.sinoep.modules.system.notice.services;

import java.lang.reflect.Array;
import java.util.*;

import javax.persistence.TypedQuery;

import com.sinosoft.sinoep.api.dept.vo.Dept;
import com.sinosoft.sinoep.common.util.*;
import com.sinosoft.sinoep.modules.info.authority.common.util.InfoAuthorityUtils;
import com.sinosoft.sinoep.modules.system.component.affix.services.AffixService;
import com.sinosoft.sinoep.modules.system.notice.common.util.NoticeUtils;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeNoBack;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.service.UserInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.modules.system.notice.dao.SysNoticeBackDao;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeBack;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * TODO 通知公告反馈接口实现
 * @author 李利广
 * @Date 2018年9月5日 下午2:18:47
 */
@Service
public class SysNoticeBackServiceImpl implements SysNoticeBackService {
	
	@Autowired
	private SysNoticeBackDao backDao;

	@Autowired
	private AffixService affixService;

	/**
	 * TODO 保存通知公告反馈结果
	 * @author 李利广
	 * @Date 2018年9月5日 下午2:22:26
	 * @param back 通知公告
	 * @return
	 */
	@Override
	public SysNoticeBack saveBack(SysNoticeBack back) throws Exception{
		if (StringUtils.isBlank(back.getId())) {//新增
			back.setBackTime(DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss"));
			back.setBackUserId(UserUtil.getCruUserId());
			back.setBackUserName(UserUtil.getCruUserName());
			back.setBackDeptId(UserUtil.getCruDeptId());
			back.setBackDeptName(UserUtil.getCruDeptName());
			back.setBackChushiId(UserUtil.getCruChushiId());
			back.setBackChushiName(UserUtil.getCruChushiName());
			back.setBackJuId(UserUtil.getCruJuId());
			back.setBackJuName(UserUtil.getCruJuName());
			back = backDao.save(back);
		}else{//修改
			SysNoticeBack oldBack = backDao.findOne(back.getId());
			oldBack.setBackContent(back.getBackContent());
			oldBack.setBackTime(DateUtil.getDateText(new Date(), "yyyy-MM-dd HH:mm:ss"));
			back = backDao.save(oldBack);
		}
		return back;
	}

	/**
	 * TODO 获取当前用户指定通知公告的反馈信息
	 * @author 李利广
	 * @Date 2018年9月5日 下午2:24:07
	 * @param noticeId 通知公告ID
	 * @return
	 */
	@Override
	public SysNoticeBack getBack(String noticeId) throws Exception{
		List<SysNoticeBack> backList = new ArrayList<>();
		StringBuilder sql = new StringBuilder("from SysNoticeBack t where t.noticeId = '"+noticeId+"' ");
		sql.append(" and t.backUserId = '"+UserUtil.getCruUserId()+"'");
		TypedQuery<SysNoticeBack> backs = backDao.getEntityManager().createQuery(sql.toString(), SysNoticeBack.class);
		backList = backs.getResultList();
		return backList.isEmpty()?null:backList.get(0);
	}
	
	/**
	 * TODO 获取通知公告的所有反馈信息
	 * @author 李利广
	 * @Date 2018年9月5日 下午2:24:07
	 * @param noticeId 通知公告ID
	 * @return
	 */
	@Override
	public PageImpl getAllBack(PageImpl page,String noticeId) throws Exception{
		Integer pageNumber = page.getPageNumber();
		Integer pageSize = page.getPageSize();
		if (pageNumber == null) {
			pageNumber = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		Pageable pageable = new PageRequest(pageNumber-1, pageSize);
		StringBuilder sql = new StringBuilder("from SysNoticeBack t where t.noticeId = '"+noticeId+"'");
		if (StringUtils.isNotBlank(page.getSortName())) {
			sql.append(" order by t."+page.getSortName()+" " + page.getSortOrder());
		}else{
			sql.append(" order by t.backTime desc");
		}
		Page<SysNoticeBack> query = backDao.query(sql.toString(), pageable);
		List<SysNoticeBack> backList = query.getContent();
		for (SysNoticeBack back: backList) {
			back.setHasAffix(affixService.hasAffix("sys_notice_back",back.getId()));
		}
		page.getData().setRows(query.getContent());
		page.getData().setTotal((int) query.getTotalElements());
		return page;
	}

	/**
	 * TODO 统计未反馈处室(以处室去重)
	 * @author 李利广
	 * @Date 2018年9月5日 下午2:24:07
	 * @param noticeId 通知公告ID
	 * @return
	 */
	@Override
	public PageImpl getAllNoBackChu(PageImpl page,String noticeId) throws Exception{
		List<String> chuShiId = new ArrayList<>();
		//page = getAllNoBackUser(page,noticeId);
		//获取所有未反馈人员,未去重
		List<SysNoticeNoBack> noBackList = getAllNoBackUser(noticeId);
		//以处室ID去重
		Iterator<SysNoticeNoBack> it = noBackList.iterator();
		while(it.hasNext()){
			SysNoticeNoBack noBack = it.next();
			String chuId = noBack.getNoBackChuId();
			if (chuShiId.contains(chuId)){
				it.remove();
			}else{
				chuShiId.add(chuId);
			}
		}
		page.getData().setRows(noBackList);
		return page;
	}

	/**
	 * TODO 统计未反馈人员(以人员去重)
	 * @author 李利广
	 * @Date 2018年9月5日 下午2:24:07
	 * @param noticeId 通知公告ID
	 * @return
	 */
	@Override
	public PageImpl getAllNoBackUser(PageImpl page,String noticeId) throws Exception{
		List<String> userIdList = new ArrayList<>();
		//获取所有未反馈人员(未去重)
		List<SysNoticeNoBack> noBackUserList = getAllNoBackUser(noticeId);
		//以人员userid去重
		Iterator<SysNoticeNoBack> it = noBackUserList.iterator();
		while(it.hasNext()){
			SysNoticeNoBack noBack = it.next();
			String userId = noBack.getUserId();
			if (userIdList.contains(userId)){
				it.remove();
			}else{
				userIdList.add(userId);
			}
		}
		page.getData().setRows(noBackUserList);
		page.getData().setTotal(noBackUserList.size());
		return page;
	}

	/**
	 * TODO 获取所有为反馈人员（没有去重）
	 * @param noticeId
	 * @return
	 */
	private List<SysNoticeNoBack> getAllNoBackUser(String noticeId){
		List<SysNoticeNoBack> noBackUserList = new ArrayList<>();
		//获取所有通知公告的发布范围
		Map<String,List<Map<String, Object>>> authMap = InfoAuthorityUtils.getAuthListByContentId(noticeId);
		//获取拥有权限的userid
		JSONArray jsonArray = NoticeUtils.getUser(authMap);
		//获取所有已反馈人员
		List<SysNoticeBack> backList = getBackUser(noticeId);
		//处理、获取所有未反馈人员
		for(int i = 0;i < jsonArray.size(); i++){
			JSONObject object = jsonArray.getJSONObject(i);
			String userId = object.getString("userid");
			String deptId = object.getString("deptid");
			String userName = object.getString("username");
			if (backList.isEmpty()){
				SysNoticeNoBack noticeNoBack = new SysNoticeNoBack();
				noticeNoBack.setNoticeId(noticeId);
				noticeNoBack.setUserId(userId);
				noticeNoBack.setUserName(userName);
				noticeNoBack.setDeptId(deptId);
				noBackUserList.add(noticeNoBack);
			}else {
				boolean flag = false;
				back: for (SysNoticeBack back : backList) {
					if (userId.equals(back.getBackUserId())) {
						flag = true;
						break back;
					}
				}
				if (!flag){
					SysNoticeNoBack noticeNoBack = new SysNoticeNoBack();
					noticeNoBack.setNoticeId(noticeId);
					noticeNoBack.setUserId(userId);
					noticeNoBack.setUserName(userName);
					noticeNoBack.setDeptId(deptId);
					noBackUserList.add(noticeNoBack);
				}
			}
		}
		//去重
		//分页
//		return getChuShi(noBackUserList);
		return  getDeptInfo(noBackUserList);
	}

	/**
	 * 获取未反馈人员的处室信息
	 * @param noBackUserList
	 * @return
	 */
	private List<SysNoticeNoBack> getChuShi(List<SysNoticeNoBack> noBackUserList){
		StringBuilder userIds = new StringBuilder();
		for (SysNoticeNoBack noBack:noBackUserList) {
			userIds.append(noBack.getUserId() + ";");
		}
		if (userIds.length() > 0){
			String userIdStr = userIds.substring(0,userIds.length() - 1);
			Map<String, SysFlowUserVo> userInfo = UserUtil.getUserVo(userIdStr);
			for (SysNoticeNoBack noBack:noBackUserList) {
//				Map<String, SysFlowUserVo> userInfo = UserUtil.getUserVo(noBack.getUserId());
				noBack.setNoBackChuId(userInfo.get(noBack.getUserId()).getUserChushiId());
				noBack.setNoBackChuName(userInfo.get(noBack.getUserId()).getUserChushiName());
				noBack.setNoBackJuId(userInfo.get(noBack.getUserId()).getUserJuId());
				noBack.setNoBackJuName(userInfo.get(noBack.getUserId()).getUserJuName());
			}
		}
		return noBackUserList;
	}

	private List<SysNoticeNoBack> getDeptInfo(List<SysNoticeNoBack> noBackUserList){
		UserInfoService userInfoService = (UserInfoService) SpringBeanUtils.getBean("com_sinosoft_sinoep_user_service_UserInfoService");
		for (SysNoticeNoBack noBack:noBackUserList) {
			StringBuffer chuId = new StringBuffer();
			StringBuffer chuName = new StringBuffer();
			StringBuffer juId = new StringBuffer();
			StringBuffer juName = new StringBuffer();
			String deptId = noBack.getDeptId();
			List<Dept> deptLists = userInfoService.getAllSuperDeptById(deptId);
			for (int i = 0; i < deptLists.size(); i++) {
				Dept dept = deptLists.get(i);
				if (dept.getTreeId().length() == 8) {//二级局
					if (juId.indexOf(String.valueOf(dept.getDeptid())) < 0) {
						juId.append(dept.getDeptid().toString()).append(",");
					}
					if (juName.indexOf(dept.getDeptname().toString()) < 0) {
						juName.append(dept.getDeptname().toString()).append(",");
					}
				} else if (dept.getTreeId().length() == 12) {//处室
					if (chuId.indexOf(String.valueOf(dept.getDeptid())) < 0) {
						chuId.append(dept.getDeptid().toString()).append(",");
					}
					if (chuName.indexOf(dept.getDeptname().toString()) < 0) {
						chuName.append(dept.getDeptname().toString()).append(",");
					}
				}
			}
			if (chuId.length() > 0) {
				noBack.setNoBackChuId(chuId.substring(0, chuId.length() - 1).toString());
			} else {
				noBack.setNoBackChuId("");
			}
			if (chuName.length() > 0) {
				noBack.setNoBackChuName(chuName.substring(0, chuName.length() - 1).toString());
			} else {
				noBack.setNoBackChuName("");
			}
			if (juId.length() > 0) {
				noBack.setNoBackJuId(juId.substring(0, juId.length() - 1).toString());
			} else {
				noBack.setNoBackJuId("");
			}
			if (juName.length() > 0) {
				noBack.setNoBackJuName(juName.substring(0, juName.length() - 1).toString());
			} else {
				noBack.setNoBackJuName("");
			}
		}
		return noBackUserList;
	}

	/**
	 * 获取所有已反馈数据
	 * @param noticeId
	 * @return
	 */
	private List<SysNoticeBack> getBackUser(String noticeId){
		String hql = "from SysNoticeBack t where t.noticeId = '"+noticeId+"'";
		TypedQuery<SysNoticeBack> backs = backDao.getEntityManager().createQuery(hql, SysNoticeBack.class);
		List<SysNoticeBack> backList = backs.getResultList();
		return backList;
	}

}
