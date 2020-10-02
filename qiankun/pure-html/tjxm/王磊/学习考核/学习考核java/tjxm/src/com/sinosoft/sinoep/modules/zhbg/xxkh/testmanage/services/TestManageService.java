package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.SysTestUserVo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestInfo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestPaper;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhUserPaper;
import net.sf.json.JSONObject;

/**
 * TODO 考试管理业务层
 * @author 李颖洁  
 * @date 2018年8月16日  下午3:09:47
 */
public interface TestManageService {

	/** 
	 * TODO 获取职务和职级信息
	 * @author 李颖洁  
	 * @date 2018年8月16日下午7:57:27
	 * @return JSONObject
	 */
	JSONObject getUserPositon();

	/** 
	 * TODO 删除人员与试卷关联信息
	 * @author 李颖洁  
	 * @date 2018年8月17日上午9:58:18
	 * @param paper
	 * @return int
	 */
	int deleteUserPaper(XxkhUserPaper paper);

	/** TODO 删除考试与试卷关联信息
	 * @author 李颖洁  
	 * @date 2018年8月17日上午9:59:05
	 * @param paper
	 * @return int
	 */
	int deleteTestPaper(XxkhTestPaper paper);

	/** 
	 * TODO 根据考试id获取试卷信息
	 * @author 李颖洁  
	 * @param id 考试id
	 * @date 2018年8月17日上午10:18:31
	 * @return List<XxkhTestPaper>
	 */
	List<XxkhTestPaper> getPapersByTestId(String id);

	/** TODO 获取考试人员数量
	 * @author 李颖洁  
	 * @date 2018年8月17日上午11:53:02
	 * @param deptId 处室id
	 * @param position 职务
	 * @param positionLevel 职级
	 * @return JSONObject
	 */
	JSONObject getTestPersonCount(String deptId, String position, String positionLevel);

	/** 
	 * TODO 获取考试人员信息
	 * @author 李颖洁  
	 * @date 2018年8月17日上午11:59:56
	 * @param deptId 处室id（多个）
	 * @param position 职务（多个）
	 * @param positionLevel 职级（多个）
	 * @return List<SysTestUserVo>
	 */
	List<SysTestUserVo> getTestPersonInfo(String deptId, String positions, String positionLevels);

	/** 
	 * TODO 保存人员与试卷关联信息
	 * @author 李颖洁  
	 * @date 2018年8月20日下午2:30:57
	 * @param id 考试id
	 * @param name 考试名称
	 * @param list 试卷数据（可能多个数据）
	 * @param userList 考试人员信息
	 * @return List<XxkhUserPaper>
	 * @throws Exception 
	 */
	List<XxkhUserPaper> saveUserPaper(String id, String name, List<XxkhTestPaper> list,List<SysTestUserVo> userList) throws Exception;

	/**
	 * TODO 保存人员信息（人员与考试与试卷的关联表）
	 * @author 李颖洁  
	 * @date 2018年8月31日上午9:40:22
	 * @param userList
	 * @return XxkhUserPaper
	 */
	XxkhUserPaper saveUserInfo(SysTestUserVo userList);
	
	/** 
	 * TODO 根据考试id获取人员与试卷信息
	 * @author 李颖洁  
	 * @param id 
	 * @param 考试人员id
	 * @date 2018年8月17日上午10:18:31
	 * @return JSONObject
	 */
	List<XxkhUserPaper> getUserPaperByTestId(String id,String userId);
	
	/** 
	 * TODO 判断是否有上报员
	 * @author 李颖洁  
	 * @date 2018年8月20日下午8:58:38
	 * @param deptIds
	 * @param deptNames
	 * @return JSONObject
	 */
	JSONObject hasReportUserByDeptId(String deptIds, String deptNames,String roleNo);

	/** 
	 * TODO 根据考试id保存试卷信息
	 * @author 李颖洁  
	 * @date 2018年8月22日下午2:51:01
	 * @param paper
	 * @return XxkhTestPaper
	 */
	List<XxkhTestPaper> saveTestPaper(String testId,String paperId);

	/** 
	 * TODO 获取考试试卷信息
	 * @author 李颖洁  
	 * @date 2018年8月22日下午3:32:06
	 * @param paper
	 * @return List<XxkhTestPaper>
	 */
	List<XxkhTestPaper> getPapersByTestId(XxkhTestPaper paper);

	/**
	 * TODO 获取考试试卷顺序最大值
	 * @author 李颖洁  
	 * @date 2018年8月22日下午4:52:04
	 * @param id 考试id
	 * @return int
	 */
	int getMaxSortByTestId(String id);

	/** TODO 获取单次考试人员信息详情
	 * @author 李颖洁  
	 * @date 2018年8月24日上午11:08:33
	 * @param pageable
	 * @param pageImpl
	 * @param userPar
	 * @return PageImpl
	 */
	PageImpl getTestPersonInfoByDeptId(Pageable pageable, PageImpl pageImpl, XxkhUserPaper userPar);

	/** TODO 获取考试人员数量(从考试与人员关联表)
	 * @author 李颖洁  
	 * @param pageable 
	 * @date 2018年8月24日下午3:09:29
	 * @param paper
	 * @param pageImpl
	 * @return PageImpl
	 */
	PageImpl getTestPersonCountInUserPaper(Pageable pageable, XxkhUserPaper paper, PageImpl pageImpl);

	/** TODO 查询已选中考试试卷列表
	 * @author 李颖洁  
	 * @date 2018年8月25日上午10:18:09
	 * @param pageImpl
	 * @param testPaper
	 * @param pageable
	 * @return PageImpl
	 */
	PageImpl getPaperInfoList(PageImpl pageImpl, XxkhTestPaper testPaper, Pageable pageable);

	/** TODO 删除考试试卷数据（批量删除）
	 * @author 李颖洁  
	 * @date 2018年8月25日下午5:54:58
	 * @param testId
	 * @param paperIds
	 * @return int
	 */
	int deleteBatchTestPaper(String testId, String paperIds);

	/** TODO 保存上报人员信息
	 * @author 李颖洁  
	 * @date 2018年8月28日下午3:19:09
	 * @param id 考试id
	 * @param personIds 人员id
	 * @param deptId 处室id
	 * @param testList  试卷信息
	 * @param name  考试名称
	 * @return JSONObject
	 * @throws Exception 
	 */
	JSONObject saveReportPerson(String id, String personIds, String deptId, List<XxkhTestPaper> testList,String name) throws Exception;

	/**
	 * TODO 保存（修改）考试基本信息
	 * @author 李颖洁  
	 * @date 2018年9月5日上午9:05:15
	 * @param info
	 * @return XxkhTestInfo
	 */
	XxkhTestInfo edit(XxkhTestInfo info);
	
	/**
	 * TODO 更改考试的状态
	 * @author 李颖洁  
	 * @date 2018年9月5日上午9:06:14
	 * @param info
	 * @return XxkhTestInfo
	 */
	XxkhTestInfo updateStatus(XxkhTestInfo info); 
	
	/**
	 * TODO 根据id获取考试信息
	 * @author 李颖洁  
	 * @date 2018年9月5日上午9:06:42
	 * @param info
	 * @return XxkhTestInfo
	 */
	XxkhTestInfo getOne(XxkhTestInfo info);
	
	/**
	 * TODO 删除一个考试信息
	 * @author 李颖洁  
	 * @date 2018年9月5日上午9:15:16
	 * @param info
	 * @return int
	 */
	int delete(XxkhTestInfo info);
	
	/**
	 * TODO 获取考试信息列表
	 * @author 李颖洁  
	 * @date 2018年9月5日上午9:07:16
	 * @param info
	 * @param pageImpl
	 * @return Page<XxkhTestInfo>
	 */
	Page<XxkhTestInfo> list(XxkhTestInfo info,PageImpl pageImpl);

	/** TODO 获取消息id
	 * @author 李颖洁  
	 * @date 2018年9月12日下午4:07:23
	 * @param notityMessage
	 * @return JSONObject
	 */
	JSONObject queryMessageId(NotityMessage notityMessage);

	/**
	 * @Author 王富康
	 * @Description //TODO 根据日期获取开始时间为日期的考试
	 * @Date 2018/9/4 20:36
	 * @Param [TestDate]
	 * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.xxkh.tsetinfo.entity.XxkhTestInfo>
	 **/
	List<XxkhTestInfo> queryTestByTestDate(String TestDate);

	/**
	 * @Author 王富康
	 * @Description //TODO 根据日期获取结束时间为昨天的考试
	 * @Date 2018/9/18 20:55
	 * @Param [TestDate]
	 * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestInfo>
	 **/
	List<XxkhTestInfo> queryTestOfdeleteWaitNoflw();

	/**
	 * @Author 王富康
	 * @Description //TODO 根据考试id获取考试人员
	 * @Date 2018/9/4 17:31
	 * @Param [TestDate]
	 * @return java.util.List<java.lang.Object>
	 **/
	List<XxkhUserPaper> queryAllTestUser(String TestId);

	/**
	 * @Author 王富康
	 * @Description //TODO 交卷后根据考试id，试卷id，考试人员id更新交卷状态,更新客观题得分
	 * @Date 2018/9/11 15:23
	 * @Param [info]
	 * @return int
	 **/
	void updateObjectiveScore(XxkhUserPaper info);

	/**
	 * @Author 王富康
	 * @Description //TODO 进入考试更新首次进入试卷考试时间
	 * @Date 2018/9/11 15:23
	 * @Param [info]
	 * @return int
	 **/
	void updateBeginTestTime(XxkhUserPaper info);

	/**
	 * 
	 * @Title: TestManageService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.services
	 * @Description: TODO修改主观题得分并修改评卷状态
	 * @author 颜振兴
	 * @date 2018年9月13日
	 * @param info
	 * @return
	 */
	XxkhUserPaper updateIsPingjuan(XxkhUserPaper info);
	
	/**
	 * 
	 * @Title: TestManageService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.services
	 * @Description: TODO修改考试评卷状态
	 * @author 颜振兴
	 * @date 2018年9月19日
	 * @param info
	 * @return
	 */
	int updateTestPingJuan(XxkhTestInfo info);

	/**
	 * 
	 * @Title: TestManageService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.services
	 * @Description: TODO将这场考试的所以人员都设为已评卷
	 * @author 颜振兴
	 * @date 2018年9月19日
	 * @param id
	 * @return
	 */
	int updateTestAllUser(String id);
}
