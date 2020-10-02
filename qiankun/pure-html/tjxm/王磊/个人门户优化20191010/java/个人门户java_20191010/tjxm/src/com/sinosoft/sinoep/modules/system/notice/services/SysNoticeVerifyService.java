package com.sinosoft.sinoep.modules.system.notice.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoColumn;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNotice;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeVerify;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNoticeVerifyUser;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 
 * @author 杜建松
 * @Date 2019年1月8日 下午2:27:07
 */
public interface SysNoticeVerifyService {

	//反显通知通告审批页面
	public SysNoticeVerify getSysNoticeVerifyByDeptId(String deptId);

	//保存通知通告审批表
	public SysNoticeVerify saveFroms(SysNoticeVerify entity,String deptLevels);

	//删除发布、审批人的信息和授权
	public JSONObject deleteItme(String ids);


	public SysNoticeVerifyUser findByFbUserIdAndVisible(String fbUserId, String visible);

	public SysNoticeVerifyUser findByShUserIdAndVisible(String shUserId,String visible);

	//查询当前管理员是否有发布人和审核人
	public List<SysNoticeVerifyUser> findByUseridAndVisible(String userId);


	
}
