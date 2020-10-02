package com.sinosoft.sinoep.modules.system.notice.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNotice;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeGrup;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeVerify;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeVerifyUser;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * TODO 
 * @author 杜建松
 * @Date 2019年1月8日 下午2:27:07
 */
public interface SysNoticeGrupService {


	//保存通知通告发送人群
	public SysNoticeGrup saveFroms(SysNoticeGrup entity);

	//获取通知公告部门群组列表
	public PageImpl getGrupList(PageImpl pageImpl, String sysGrupName);

	//获取通知公告局级群组列表
	public PageImpl getGenaralsList(PageImpl pageImpl, String sysGrupName);

	/**
	 * TODO 获取一条数据
	 * @author 李利广
	 * @Date 2018年9月5日 下午5:05:48
	 * @param id
	 * @return
	 */
	SysNoticeGrup getView(String id) throws Exception;

	//删除通知人员群组中的人员
	public JSONObject deleteItme(String ids);

	/**
	 * TODO 删除通知群组
	 * @author 杜建松
	 * @Date 2018年9月5日 下午4:47:41
	 * @param noticeId
	 * @return
	 */
	Boolean delNotice(String noticeId) throws Exception;

	/**
	 * 获取部门人员树
	 * @param
	 * @return
	 */
	public JSONObject getGroup();


	/**
	 * 获取部门调查问卷人员树
	 * @param
	 * @return
	 */
	public JSONObject getSurveyGroup();

	/**
	 * 获取局级人员树
	 * @param
	 * @return
	 */
	public JSONObject getGenaralsGroup(String typeCode);


	/**
	 * 获取群组下用户
	 * @param groupId 群组id
	 * @return
	 */
	public JSONObject getGroupUser(String groupId,String userid);

	//获取调查问卷部门群组列表
	public PageImpl getSurveyGrupList(PageImpl pageImpl, String sysGrupName);

	
}
