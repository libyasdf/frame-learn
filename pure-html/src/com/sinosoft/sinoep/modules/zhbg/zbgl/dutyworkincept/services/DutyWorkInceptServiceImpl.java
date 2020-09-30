package com.sinosoft.sinoep.modules.zhbg.zbgl.dutyworkincept.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sinosoft.sinoep.common.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.exception.SysException;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.modules.zhbg.zbgl.constant.ZBGLCommonConstants;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.dao.DutyWorkDao;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.entity.DutyWork;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutyworkincept.dao.DutyWorkInceptDao;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutyworkincept.entity.DutyWorkIncept;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONObject;

/**
 * TODO 值班工作通知接收
 * @author 李颖洁  
 * @date 2018年7月16日  下午3:58:21
 */
@Service
public class DutyWorkInceptServiceImpl implements DutyWorkInceptService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private DutyWorkInceptDao dutyWorkInceptDao;
	
	@Autowired
	private DutyWorkDao dutyWorkDao;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * TODO 按部门保存通知接收表
	 * @author 李颖洁  
	 * @date 2018年8月9日下午1:48:02
	 * @param dutyWork
	 * @param deptIds
	 * @param deptNames
	 * @return List<DutyWorkIncept>
	 */
	@Override
	public List<DutyWorkIncept> sendNotice(DutyWork dutyWork,String deptIds,String deptNames) {
		List<DutyWorkIncept> inceptList = new ArrayList<>();
		//获取部门id
		try {
			String[] deptId = mapper.readValue(deptIds, String[].class);
			String[] deptName = mapper.readValue(deptNames, String[].class);
			List<DutyWorkIncept> oriDutyWorkIncept = getDeptList(dutyWork.getId());
			if(oriDutyWorkIncept.size()==0){
				//保存
				for(int i=0;i<deptId.length;i++){
					if(deptId[i]!=""){
					DutyWorkIncept incept = new DutyWorkIncept();
					//创建人、部门、处室、二级局（id、name）
					incept.setCreUserId(UserUtil.getCruUserId());
					incept.setCreUserName(UserUtil.getCruUserName());
					incept.setCreDeptId(UserUtil.getCruDeptId());
					incept.setCreDeptName(UserUtil.getCruDeptName());
					incept.setCreChuShiId(UserUtil.getCruChushiId());
					incept.setCreChuShiName(UserUtil.getCruChushiName());
					incept.setCreJuId(UserUtil.getCruJuId());
					incept.setCreJuName(UserUtil.getCruJuName());
					//随机生成id
					incept.setZbNoticeId(dutyWork.getId());
					incept.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
					incept.setVisible(CommonConstants.VISIBLE[0]);
					incept.setZbDeptId(deptId[i]);
					incept.setZbDeptName(deptName[i]);
					incept.setIsRead(ZBGLCommonConstants.IS_READ[0]);
					incept.setIsReport(ZBGLCommonConstants.IS_REPORT[0]);
					
					incept = dutyWorkInceptDao.save(incept);
					inceptList.add(incept);
					}
				}
			}else{
					for (DutyWorkIncept dutyWorkIncept : oriDutyWorkIncept) {
						String d = dutyWorkIncept.getZbDeptId();
						boolean f = hasIn(deptId,d);
						if(f){
							inceptList.add(dutyWorkIncept);
						}else{
							//删除
							delete(d,dutyWork.getId());
						}
					}
					for(int i=0;i<deptId.length;i++ ){
						boolean f = false;
						for (DutyWorkIncept dutyWorkIncept : oriDutyWorkIncept) {
							String d = dutyWorkIncept.getZbDeptId();
							if(deptId[i].equals(d)){
								f = true;
							}
						}
						if(!f){
							//保存
							DutyWorkIncept incept = new DutyWorkIncept();
							//创建人、部门、处室、二级局（id、name）
							incept.setCreUserId(UserUtil.getCruUserId());
							incept.setCreUserName(UserUtil.getCruUserName());
							incept.setCreDeptId(UserUtil.getCruDeptId());
							incept.setCreDeptName(UserUtil.getCruDeptName());
							incept.setCreChuShiId(UserUtil.getCruChushiId());
							incept.setCreChuShiName(UserUtil.getCruChushiName());
							incept.setCreJuId(UserUtil.getCruJuId());
							incept.setCreJuName(UserUtil.getCruJuName());
							//随机生成id
							incept.setZbNoticeId(dutyWork.getId());
							incept.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
							incept.setVisible(CommonConstants.VISIBLE[0]);
							incept.setZbDeptId(deptId[i]);
							incept.setZbDeptName(deptName[i]);
							incept.setIsRead(ZBGLCommonConstants.IS_READ[0]);
							incept.setIsReport(ZBGLCommonConstants.IS_REPORT[0]);
							
							incept = dutyWorkInceptDao.save(incept);
							inceptList.add(incept);
						}
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return inceptList;
	}
	
	private static boolean hasIn(Object[] arr, String v) {
	    for(Object s: arr){
	        if(s.equals(v))
	        	return true;
	    }
	    return false;
	}
	
	/**
	 * TODO 根据ID删除接收表
	 * @author 李颖洁  
	 * @date 2018年8月9日下午1:39:55
	 * @param id
	 * @throws SysException
	 * @return int
	 */
	@Override
	public int delete(String id,String noticeId) throws SysException {
		//验证参数有效性
		if(StringUtils.isBlank(id))
			throw new SysException("参数为空，删除数据失败,请重试！");
		String jpql = "delete from DutyWorkIncept t where t.visible = ? and t.zbDeptId = ? and t.zbNoticeId= ?";
		return dutyWorkInceptDao.update(jpql, CommonConstants.VISIBLE[0],id,noticeId);
	}
	
	/**
	 * TODO 发布通知后显示可见
	 * @author 李颖洁  
	 * @date 2018年8月9日下午3:39:15
	 * @param id
	 * @throws SysException
	 */
	@Override
	public int updateVisible(String id,String deptId) throws SysException {

		List<Object> para = new ArrayList<>();
		//验证参数有效性
		if(StringUtils.isBlank(id)){
			throw new SysException("参数为空，删除数据失败,请重试！");
		}
			para.add(CommonConstants.VISIBLE[1]);
		para.add(id);
		String inString = CommonUtils.solveSqlInjectionOfIn(deptId,para);
		String jpql = "update DutyWorkIncept t set t.visible = ? where t.zbNoticeId = ? and t.zbDeptId in ("+inString+")";
		return dutyWorkInceptDao.update(jpql,para.toArray());
	}
	
	/**
	 * TODO 根据通知信息id批量删除
	 * @author 李颖洁  
	 * @date 2018年8月9日下午1:39:55
	 * @param id
	 * @throws SysException
	 * @return int
	 */
	@Override
	public int deleteBatch(String id) throws SysException {
		//验证参数有效性
		if(StringUtils.isBlank(id))
			throw new SysException("参数为空，删除数据失败,请重试！");
		String jpql = "update DutyWorkIncept t set t.visible = ? where t.zbNoticeId = ?";
		return dutyWorkInceptDao.update(jpql, CommonConstants.VISIBLE[0], id);
	}
	
	/**
	 * TODO 获取处室接收的通知列表
	 * @author 李颖洁
	 * @date 2018年7月19日上午9:22:54
	 * @Param [pageable, pageImpl, startTime, endTime, isRead, isReport, isSecurity]
	 * @return com.sinosoft.sinoep.common.util.PageImpl
	 **/
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String startTime, String endTime,String isRead,String isReport,String isSecurity) {
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		//全部列表查询
		querySql.append("select new com.sinosoft.sinoep.modules.zhbg.zbgl.dutywork.entity.DutyWork(t.id, t.title,t.noticeText,t.remark, t.startTime, t.endTime, d.isRead as isRead,d.isReport as isReport,d.id as inceptId)");
		querySql.append("	from DutyWork t,DutyWorkIncept d ");
		querySql.append("	where d.zbDeptId = ?");
		querySql.append("	and t.id = d.zbNoticeId and t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		para.add(UserUtil.getCruChushiId());
		if (!StringUtils.isBlank(isReport)){
			querySql.append("   and d.isReport = ?");
			para.add(isReport);
		}
		if (!StringUtils.isBlank(isRead)){
			querySql.append("   and d.isRead = ?");
			para.add(isRead);
		}
		if(!StringUtils.isBlank(isSecurity)){
			querySql.append("   and t.isSecurity = ?");
			para.add(isSecurity);
		}
		if (!StringUtils.isBlank(endTime)) {
			querySql.append("   and t.startTime <= ?");
			para.add(endTime);
		}
		if (!StringUtils.isBlank(startTime)) {
			querySql.append("   and t.endTime >= ?");
			para.add(startTime);
		}
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc) ");
		}else{
			querySql.append("  order by d."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}
		//执行查询
		log.info(querySql.toString());
        Page<DutyWork> page = dutyWorkInceptDao.query(querySql.toString(), pageable,para.toArray());
		pageImpl.setFlag("1");
		pageImpl.getData().setRows(page.getContent());
		pageImpl.getData().setTotal((int)page.getTotalElements());
		return pageImpl;
		
	}

	/**
	 * TODO 获取本处室的通知信息和修改处室通知的读取状态
	 * @author 李颖洁  
	 * @date 2018年7月27日下午5:49:45
	 * @param id
	 * @param isRead
	 * @throws SysException
	 */
	@Override
	public DutyWork getById(String id,String isRead) throws SysException {
		//验证参数有效性
		if(StringUtils.isBlank(id))
			throw new SysException("参数为空，加载数据失败！");
		List<Object> para = new ArrayList<>();
		para.add(isRead);
		para.add(UserUtil.getCruUserId());
		para.add(UserUtil.getCruUserName());
		para.add(DateUtil.COMMON_FULL.getDateText(new Date()));
		para.add(id);
		para.add(UserUtil.getCruChushiId());
		String sql = "update DutyWorkIncept t set t.isRead = ?,t.readUserId = ?,t.readUserName = ?,t.readTime = ? where t.zbNoticeId = ? and zbDeptId = ?";
		int row =  dutyWorkInceptDao.update(sql,para.toArray());
		log.info(String.valueOf(row));
		DutyWork dutyWork = dutyWorkDao.findOne(id);
		if(dutyWork.getStartTime()==null){
			dutyWork.setYxq(dutyWork.getStartTime()+"-"+dutyWork.getEndTime());
		}else{
			dutyWork.setYxq("");
		}
		return dutyWork;
	}

	/** TODO 根据通知ID查询所有处室接收的通知
	 * @author 李颖洁  
	 * @date 2018年7月20日下午2:47:09
	 * @param zbNoticeId
	 * @return List<DutyWorkIncept>
	 */
	@Override
	public List<DutyWorkIncept> getList(PageImpl pageImpl,String zbNoticeId, String state) {
		List<DutyWorkIncept> list = new ArrayList<>();
		List<Object> paraList = new ArrayList<>();
		String sql = "select * from zbgl_duty_notice_incept t where t.visible = ? ";
		paraList.add(state);
		if (StringUtils.isNotBlank(zbNoticeId)){
            sql += "	and t.zb_notice_id = ? ";
            paraList.add(zbNoticeId);
        }
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			sql += "  order by t.cre_time desc ";
		}else{
			String str = pageImpl.getSortName();
			String ss = "";
			char[] charArray = str.toCharArray(); 
			for (int i = 0; i < charArray.length; i++) { 
				if(charArray[i] >= 'A' && charArray[i] <= 'Z'){ 
					ss += "_"+charArray[i]; 
				} 
				else{ 
					ss += charArray[i]; 
				} 
			} 
			sql += "  order by t."+ss.toLowerCase()+" "+pageImpl.getSortOrder();
		}
		list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DutyWorkIncept.class),paraList.toArray());
		return list;
	}

	/** TODO 修改部门值班表上报状态
	 * @author 李颖洁  
	 * @date 2018年7月30日下午4:46:58
	 * @param id
	 * @throws SysException 
	 */
	@Override
	public int updateState(String id) throws SysException {
		//验证参数有效性
		if(StringUtils.isBlank(id))
			throw new SysException("参数为空，上报数据失败,请重试！");
		String jpql = "update DutyWorkIncept t set t.isReport = ? where t.id = ?";
		return dutyWorkDao.update(jpql, ZBGLCommonConstants.IS_REPORT[1], id);
	}

	/**
	 * TODO 获取工作通知消息id
	 * @author 李颖洁
	 * @date 2018年8月2日下午5:11:36
	 * @Param [notityMessage]
	 * @return net.sf.json.JSONObject
	 **/
	@Override
	public JSONObject queryMessageId(NotityMessage notityMessage) {
		String sql ="select * from message t where t.senderid ='"+notityMessage.getSenderId()+"' and t.accepterid='"+notityMessage.getAccepterId()+"' and t.subject='"+notityMessage.getSubject()+"' and t.content='"+notityMessage.getContent()+"'";
		JSONObject result = userInfoService.getDateBySql(sql);
		return result;
	}

	/**
	 * TODO 根据接收表ID查询接收表
	 * @author 李颖洁  
	 * @date 2018年8月3日上午9:47:51
	 * @param id
	 * @return DutyWorkIncept
	 */
	@Override
	public DutyWorkIncept getInceptById(String id) {
		return dutyWorkInceptDao.findOne(id);
	}

	/** 
	 * TODO 获取未发布保存的部门
	 * @author 李颖洁  
	 * @date 2018年8月9日下午4:49:03
	 * @param zbNoticeId
	 * @return List<DutyWorkIncept>
	 */
	@Override
	public List<DutyWorkIncept> getDeptList(String zbNoticeId) {
		List<DutyWorkIncept> list = new ArrayList<>();
		List<Object> paraList = new ArrayList<>();
		String sql = "select * from zbgl_duty_notice_incept t where t.visible = '"+CommonConstants.VISIBLE[0]+"' ";
		if (StringUtils.isNotBlank(zbNoticeId)){
            sql +="	and t.zb_notice_id = ? ";
            paraList.add(zbNoticeId);
        }
		list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DutyWorkIncept.class),paraList.toArray());
		return list;
	}

	

	
}
