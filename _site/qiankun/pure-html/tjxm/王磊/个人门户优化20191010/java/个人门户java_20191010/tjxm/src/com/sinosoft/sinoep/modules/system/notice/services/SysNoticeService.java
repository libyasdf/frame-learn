package com.sinosoft.sinoep.modules.system.notice.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.info.xxfb.entity.InfoContent;
import com.sinosoft.sinoep.modules.system.notice.entity.SysNotice;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * TODO 
 * @author 李利广
 * @Date 2018年8月17日 下午2:27:07
 */
public interface SysNoticeService {
	
	/**
	 * TODO 保存通知通告
	 * @author 李利广
	 * @Date 2018年8月17日 下午2:29:06
	 * @param notice
	 * @return
	 */
	SysNotice saveNotice(SysNotice notice) throws Exception;
	
	/**
	 * TODO 更新通知通告状态位
	 * @author 李利广
	 * @Date 2018年8月17日 下午2:30:44
	 * @param id
	 * @param flag
	 * @return
	 */
	SysNotice updateFlag(String id,String flag) throws Exception;
	
	/**
	 * TODO 查询当前用户收到的通知通告（分页）
	 * @author 李利广
	 * @Date 2018年8月17日 下午2:46:49
	 * @param pageImpl
	 * @param notice
	 * @return
	 */
	PageImpl getNoticeList(PageImpl pageImpl,SysNotice notice,String startTime,String endTime) throws Exception;
	
	/**
	 * TODO 查询通知通告维护列表（部门级）（当前用户查看自己维护的数据）
	 * @author 李利广
	 * @Date 2018年8月17日 下午2:46:49
	 * @param pageImpl
	 * @param notice
	 * @return
	 */
	PageImpl getAllNoticeList(PageImpl pageImpl,SysNotice notice,String startTime,String endTime) throws Exception;

	/**
	 * TODO 查询通知通告维护列表（局级）（当前用户查看自己维护的数据）
	 * @author 李利广
	 * @Date 2018年8月17日 下午2:46:49
	 * @param pageImpl
	 * @param notice
	 * @return
	 */
	PageImpl getAllNoticeList1(PageImpl pageImpl,SysNotice notice,String startTime,String endTime) throws Exception;

	/**
	 * TODO 查询通知通告维护列表（局级）（当前用户查看自己维护的数据）
	 * @author 李利广
	 * @Date 2018年8月17日 下午2:46:49
	 * @param pageImpl
	 * @param notice
	 * @return
	 */
	PageImpl getAllNoticeList2(PageImpl pageImpl,SysNotice notice,String startTime,String endTime) throws Exception;

	/**
	 * TODO 删除通知公告
	 * @author 李利广
	 * @Date 2018年9月5日 下午4:47:41
	 * @param noticeId
	 * @return
	 */
	Boolean delNotice(String noticeId) throws Exception;
	
	/**
	 * TODO 获取一条数据
	 * @author 李利广
	 * @Date 2018年9月5日 下午5:05:48
	 * @param noticeId
	 * @return
	 */
	SysNotice getView(String noticeId) throws Exception;
	
	/**
	 * TODO 通知置顶
	 * @author 李利广
	 * @Date 2018年9月10日 上午11:18:01
	 * @param ids
	 * @param upDown true:置顶；false:取消置顶
	 * @return
	 * @throws Exception
	 */
	Boolean setTop(String ids, Boolean upDown) throws Exception;

	/**
	 * 置顶排序
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	Boolean sort(String[] ids) throws Exception;

	/**
	 * 查询通知公告最新发布消息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	PageImpl getInfoNoticeList(PageImpl page) throws Exception;

	/**
	 * @Author 王富康
	 * @Description //TODO 查询公文发布最新发布消息
	 * @Date 2019/10/10 9:24
	 * @Param [page]
	 * @return com.sinosoft.sinoep.common.util.PageImpl
	 **/
	PageImpl getInfoNoticeDocumentsList(PageImpl page) throws Exception;

	public List<SysNotice> getZhiDingList();

	public JSONObject queryNotice();

	//获取审核列表
	public PageImpl getSpList(Pageable pageable, PageImpl pageImpl, String subflag, String title);
	
}
