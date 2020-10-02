package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.services;

import java.util.List;
import org.springframework.data.domain.Page;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.xxkh.paper.entity.XxkhPaperInfo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestInfo;

public interface XxkhPaperInfoService {
	/**
	 * 
	 * @Title: XxkhPaperInfoService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.services
	 * @Description: 查询列表
	 * @author 颜振兴
	 * @date 2018年8月15日
	 * @param info
	 * @param pageImpl
	 * @return
	 */
	Page<XxkhPaperInfo> list(XxkhPaperInfo info,PageImpl pageImpl);
	
	/**
	 * 
	 * @Title: XxkhPaperInfoService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.services
	 * @Description: 保存一条数据
	 * @author 颜振兴
	 * @date 2018年8月15日
	 * @param info
	 * @return
	 */
	XxkhPaperInfo save(XxkhPaperInfo info);
	
	/**
	 * 
	 * @Title: XxkhPaperInfoService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.services
	 * @Description: 修改一条数据
	 * @author 颜振兴
	 * @date 2018年8月15日
	 * @param info
	 * @return
	 */
	XxkhPaperInfo update(XxkhPaperInfo info);
	
	/**
	 * 
	 * @Title: XxkhPaperInfoService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.services
	 * @Description:逻辑删除
	 * @author 颜振兴
	 * @date 2018年8月15日
	 * @param info
	 * @return
	 */
	int delete(XxkhPaperInfo info);
	
	/**
	 * 
	 * @Title: XxkhPaperInfoService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.services
	 * @Description: 获取一条数据
	 * @author 颜振兴
	 * @date 2018年8月15日
	 * @param info
	 * @return
	 */
	XxkhPaperInfo getOne(XxkhPaperInfo info);
	
	/**
	 * 
	 * @Title: XxkhPaperInfoService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.services
	 * @Description: TODO修改总分
	 * @author 颜振兴
	 * @date 2018年8月21日
	 * @param info
	 * @return
	 */
	XxkhPaperInfo updateFenShu(XxkhPaperInfo info);
	
	/**
	 * 
	 * @Title: XxkhPaperInfoService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.services
	 * @Description: TODO获取自动组卷的一张试卷数据
	 * @author 颜振兴
	 * @date 2018年8月21日
	 * @param info
	 * @return
	 */
	XxkhPaperInfo getAutoOne(XxkhPaperInfo info);

	/**
	 *
	 * @Title: XxkhPaperInfoService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.services
	 * @Description: TODO判断是否可以编辑试卷
	 * @author 颜振兴
	 * @date 2018年9月17日
	 * @param id
	 * @return
	 */
	int isCanUpdate(String id);

	/**
	 * @Author 王富康
	 * @Description //TODO 考试后获取试卷信息，拼接了这个人这场考试这张试卷的主观题得分，客观题得分，人工评卷状态的字段
	 * @Date 2018/9/17 20:13
	 * @Param [paperInfo, testId, userId]
	 * @return net.sf.json.JSONObject
	 **/
	XxkhPaperInfo getTestResult(XxkhPaperInfo paperInfo, String testId, String userId);
	
	/**
	 * 
	 * @Title: XxkhPaperInfoService.java
	 * @Package com.sinosoft.sinoep.modules.zhbg.xxkh.paper.services
	 * @Description: TODO是否被考试用到
	 * @author 颜振兴
	 * @date 2018年9月19日
	 * @param paperInfo
	 * @return
	 */
	List<XxkhTestInfo> isCanDelete(XxkhPaperInfo paperInfo);
}
