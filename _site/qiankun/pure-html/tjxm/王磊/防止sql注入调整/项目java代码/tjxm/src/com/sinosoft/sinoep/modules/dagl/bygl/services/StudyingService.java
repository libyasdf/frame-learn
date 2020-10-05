package com.sinosoft.sinoep.modules.dagl.bygl.services;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dagl.bygl.entity.Studying;
import com.sinosoft.sinoep.modules.dagl.bygl.entity.StudyingSub;
import net.sf.json.JSONObject;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;
import org.springframework.data.domain.Pageable;

/**
 * @Author 王富康
 * @Description //TODO 编研管理Service层
 * @Date 2018/12/18 20:22
 * @Param
 * @return
 **/
public interface StudyingService {

	/**
     * 合并两个word文档
     * @author 王磊
     * @Date 2018年12月19日 上午10:44:55
     * @param document
     * @param doucDocument2
     * @return
     * @throws XmlException
     */
	public XWPFDocument mergeWord(XWPFDocument document,XWPFDocument doucDocument2) throws Exception ;
	
	/**
	 * 根据文件路径和文件名合并文件
	 * @author 王磊
	 * @Date 2018年12月19日 上午10:46:31
	 * @param path
	 * @param fileNameList
	 */
	public void mergeWords(String path,List<String> fileNameList);
	
	/**
	 * 根据服务器路径和文件名下载文件
	 * @author 王磊
	 * @Date 2018年12月19日 下午5:31:43
	 * @param response
	 * @param path
	 * @param fileName
	 */
	void downloadFormServer(HttpServletResponse response, String path, String fileName);

	/**
	 * 查找当前编研记录还没有确认的处室名
	 * @author 王磊
	 * @Date 2018年12月20日 下午3:24:16
	 * @param bianYanId
	 * @return 还没有确认的处室名（逗号分隔）
	 */
	public String findChuShiNotConfirm(String bianYanId);

	/**
	 * @Author 王富康
	 * @Description //TODO 查询编研的列表
	 * @Date 2018/12/20 11:19
	 * @Param [pageImpl, studying]
	 * @return com.sinosoft.sinoep.common.util.PageImpl
	 **/
	PageImpl getStudyingData(Pageable pageable, PageImpl pageImpl, Studying studying);

	/**
	 * @Author 王富康
	 * @Description //TODO 删除编研信息
	 * @Date 2018/12/20 14:11
	 * @Param [reportId]
	 * @return int
	 **/
	int deleteStudying(String id);

	/**
	 * @Author 王富康
	 * @Description //TODO 新增/修改
	 * @Date 2018/12/20 17:25
	 * @Param [dut]
	 * @return com.sinosoft.sinoep.modules.dagl.bygl.entity.Studying
	 **/
	Studying saveStudying(Studying dut);

	/**
	 * @Author 王富康
	 * @Description //TODO 根据id获取一条编研信息
	 * @Date 2018/12/20 17:25
	 * @Param [id]
	 * @return com.sinosoft.sinoep.modules.dagl.bygl.entity.Studying
	 **/
	Studying getStudyingById(String id, String chushiId);

	/**
	 * @Author 王富康
	 * @Description //TODO 根据编研id查询分发列表
	 * @Date 2018/12/20 11:19
	 * @Param [pageImpl, studying]
	 * @return com.sinosoft.sinoep.common.util.PageImpl
	 **/
	List<StudyingSub> getStudyingFenFaData( String studyingId, String sequence, String chushiId);

	/**
	 * @Author 王富康
	 * @Description //TODO 删除编研子表信息
	 * @Date 2018/12/20 14:11
	 * @Param [reportId]
	 * @return int
	 **/
	int deleteStudyingSub(String ids, String affixIds);

	/**
	 * @Author 王富康
	 * @Description //TODO 根据分发id获取一个子表的分发信息
	 * @Date 2018/12/28 14:43
	 * @Param [id]
	 * @return com.sinosoft.sinoep.modules.dagl.bygl.entity.StudyingSub
	 **/
	StudyingSub getStudyingSub(String id);

	/**
	 * @Author 王富康
	 * @Description //TODO 根据子表ID修改反馈状态
	 * @Date 2018/12/29 14:18
	 * @Param []
	 * @return com.sinosoft.sinoep.modules.dagl.bygl.entity.StudyingSub
	 **/
	int updateStudyingSubIsBack(String id, String type);

	/**
	 * @Author 王富康
	 * @Description //TODO 档案编研确认
	 * @Date 2019/1/2 10:50
	 * @Param [id, type]
	 * @return int
	 **/
	int StudyingSubOk(String id);

	/**
	 * @Author 王富康
	 * @Description //TODO 查询部门下是否含有某个业务角色编号的人员
	 * @Date 2019/1/2 10:27
	 * @Param [id, type]
	 * @return net.sf.json.JSONObject
	 **/
	JSONObject hasReportUserByDeptId(String deptIds, String deptNames, String roleNo);
}
