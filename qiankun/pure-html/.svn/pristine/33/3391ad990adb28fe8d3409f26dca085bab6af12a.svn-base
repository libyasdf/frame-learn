package com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.modules.zhbg.xxkh.constant.XXKHCommonConstants;
import com.sinosoft.sinoep.modules.zhbg.xxkh.paper.entity.XxkhPaperInfo;
import com.sinosoft.sinoep.modules.zhbg.xxkh.paper.services.XxkhPaperInfoService;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questiongroup.entity.XxkhQuestionGroup;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.dao.XxkhTestInfoDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.dao.XxkhTestPaperDao;
import com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.dao.XxkhUserPaperDao;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * TODO 考试管理业务层
 * @author 李颖洁  
 * @date 2018年8月16日  下午3:10:31
 */
@Service
public class TestManageServiceImpl implements TestManageService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private XxkhTestPaperDao xxkhTestPaperDao;
	
	@Autowired
	private XxkhUserPaperDao xxkhUserPaperDao;
	
	@Autowired
	private XxkhTestInfoDao dao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private XxkhPaperInfoService xxkhPaperInfoService;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	private static  final String SQL = "select distinct t.userid,t.usernamefull as username,t.user_sex,t.phone,t.status,t.position_real_level ,p1.name as position_name_full,t.position_level ,p2.name as position_level_name,"
					+ "	d.deptid as userDeptId,fd.deptname as userDeptName,fd.chuid as userChushiId,fd.chuname as userChushiName,fd.juid as userJuId,fd.juname as userJuName "
					+ "	from sys_flow_user t "
					+ "	left join sys_dictionary p1 on (t.position_real_level = p1.value and p1.remarks = "+XXKHCommonConstants.OCCUPATION_TYPE[0]+")	"
					+ "	left join sys_dictionary p2 on (t.position_level = p2.value and p2.remarks = "+XXKHCommonConstants.OCCUPATION_TYPE[1]+")	"
					+ "	left join sys_user_dprb d on t.userid = d.userid "
					+ "	left join "
					+ "	(select t1.deptid,t1.tree_id,t1.deptname,t2.deptid as chuid,substr(t2.tree_id,0,12)as chutreeid,t2.deptname chuname,t3.deptid as juid,t3.tree_id as jutreeid,t3.deptname as juname "
					+ "	from sys_flow_dept t1 , "
					+ "		(select t.deptid, t.deptname,t.tree_id	"
					+ "		from sys_flow_dept t"
					+ "		where length(t.tree_id) = 12) t2,	"
					+ "		(select t.deptid, t.deptname,t.tree_id	"
					+ "		from sys_flow_dept t"
					+ "		where length(t.tree_id) = 8) t3 	"
					+ "	where substr(t1.tree_id,0,12) = t2.tree_id and substr(t1.tree_id,0,8) = t3.tree_id	"
					+ "	) fd on d.deptid = fd.deptid where d.status = '"+XXKHCommonConstants.STATUS[1]+"' and t.status='"+XXKHCommonConstants.STATUS[1]+"'";
	
	/**
	 *  TODO 获取职务和职级信息
	 * @author 李颖洁  
	 * @date 2018年8月16日下午7:57:39
	 * @return JSONObject
	 */
	@Override
	public JSONObject getUserPositon() {
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		try {
			String sql = "select t.name,t.value,t.remarks,t.orderno,t.id from sys_dictionary t where t.remarks ="+XXKHCommonConstants.OCCUPATION_TYPE[0]+" and t.orderno >= "+XXKHCommonConstants.OCCUPATION_LEVEL[2]+" order by t.orderno asc";
			String sql2 = "select t.name,t.value,t.remarks,t.orderno,t.id from sys_dictionary t where t.remarks ="+XXKHCommonConstants.OCCUPATION_TYPE[1]+" and t.orderno >= "+XXKHCommonConstants.OCCUPATION_LEVEL[2]+" order by t.orderno asc";
			JSONObject result = userInfoService.getDateBySql(sql);
			JSONObject result2 = userInfoService.getDateBySql(sql2);
			if("1".equals(result.get("flag")) && "1".equals(result2.get("flag"))){
				json.put("flag", "1");
				JSONArray arr = result.getJSONArray("data");
				JSONArray arr2 = result2.getJSONArray("data");
				json.put("data", arr);
				json.put("data2", arr2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", "解析异常");
		}
		return json;
	}

	/** 
	 * TODO 删除人员与试卷关联信息
	 * @author 李颖洁  
	 * @date 2018年8月17日上午10:00:59
	 * @param id 考试id
	 * @return
	 */
	@Override
	public int deleteUserPaper(XxkhUserPaper paper) {
		String jpql = "delete from  XxkhUserPaper t where  t.visible = "+CommonConstants.VISIBLE[1];
		if (StringUtils.isNotBlank(paper.getTestId())){
			jpql += "	and t.testId = '"+paper.getTestId()+"'";
        }
		if (StringUtils.isNotBlank(paper.getPaperId())){
			jpql += "	and t.paperId = '"+paper.getPaperId()+"'";
        }
		if (StringUtils.isNotBlank(paper.getUserId())){
			jpql += "	and t.userId = '"+paper.getUserId()+"'";
        }
		log.info(jpql);
		return xxkhUserPaperDao.update(jpql);
	}

	/** 
	 * TODO 删除考试与试卷关联信息
	 * @author 李颖洁  
	 * @date 2018年8月17日上午10:00:59
	 * @param id
	 * @return
	 */
	@Override
	public int deleteTestPaper(XxkhTestPaper paper) {
		String jpql = "delete from  XxkhTestPaper t where t.visible = "+CommonConstants.VISIBLE[1];
		if (StringUtils.isNotBlank(paper.getId())){
			jpql += "	and t.id = '"+paper.getId()+"'";
        }
		if (StringUtils.isNotBlank(paper.getTestId())){
			jpql += "	and t.testId = '"+paper.getTestId()+"'";
        }
		if (StringUtils.isNotBlank(paper.getPaperId())){
			jpql += "	and t.paperId = '"+paper.getPaperId()+"'";
        }
		return xxkhUserPaperDao.update(jpql);
	}

	/**
	 *  TODO 根据考试id获取试卷信息(选完以后回显在页面)
	 * @author 李颖洁  
	 * @date 2018年8月17日上午10:18:53
	 * @return
	 */
	@Override
	public List<XxkhTestPaper> getPapersByTestId(String id) {
		List<XxkhTestPaper> list = new ArrayList<>();
		String sql = "select t.* from xxkh_test_paper t left join xxkh_paper_info d  on t.test_id = d.id  where t.visible = '"+CommonConstants.VISIBLE[1]+"' ";
		if (StringUtils.isNotBlank(id)){
            sql += "	and t.test_id = '"+id+"'";
        }
		//拼接排序语句
		sql += "  order by t.sort asc ";
		list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(XxkhTestPaper.class));
		return list;
	}

	/** 
	 * TODO 获取考试人员数量(查询符合要求的人员)
	 * @author 李颖洁  
	 * @date 2018年8月17日上午11:53:24
	 * @param deptId 考试处室id
	 * @param position 职务(01,02格式的字符串)
	 * @param positionLevel 职级
	 * @return JSONObject
	 */
	@Override
	public JSONObject getTestPersonCount(String deptId, String position, String positionLevel) {
		deptId = CommonUtils.commomSplit(deptId);
		position = CommonUtils.commomSplit(position);
		positionLevel = CommonUtils.commomSplit(positionLevel);
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		String sql = "";
		try {
			String treeSql = "select t.tree_id from sys_flow_dept t";
			if(StringUtils.isNotBlank(deptId)){
				treeSql += "	where t.deptid in ("+deptId+")";
			}
			//根据处室id查出所有的处和科的id
			String jsql = "select t.deptid from sys_flow_dept t where substr(t.tree_id,0,12) in ( "+treeSql+" )";
			sql += "select distinct t.userid,t.usernamefull as username,t.user_sex,t.phone,t.status,t.position_real_level ,p1.name as position_name_full,t.position_level ,p2.name as position_level_name,	"
					+ "	d.deptid as userDeptId,fd.deptname as userDeptName,fd.chuid as userChushiId,fd.chuname as userChushiName,fd.juid as userJuId,fd.juname as userJuName "
					+ "	from sys_flow_user t "
					+ "	left join sys_dictionary p1 on (t.position_real_level = p1.value and p1.remarks = "+XXKHCommonConstants.OCCUPATION_TYPE[0]+")	"
					+ "	left join sys_dictionary p2 on (t.position_level = p2.value and p2.remarks = "+XXKHCommonConstants.OCCUPATION_TYPE[1]+")"
					+ "	left join sys_user_dprb d on t.userid = d.userid "
					+ "	left join "
					+ "	(select t1.deptid,t1.tree_id,t1.deptname,t2.deptid as chuid,substr(t2.tree_id,0,12)as chutreeid,t2.deptname chuname,t3.deptid as juid,t3.tree_id as jutreeid,t3.deptname as juname "
					+ "	from sys_flow_dept t1 , "
					+ "		(select t.deptid, t.deptname,t.tree_id	"
					+ "		from sys_flow_dept t"
					+ "		where length(t.tree_id) = 12) t2,	"
					+ "		(select t.deptid, t.deptname,t.tree_id	"
					+ "		from sys_flow_dept t"
					+ "		where length(t.tree_id) = 8) t3 	"
					+ "	where substr(t1.tree_id,0,12) = t2.tree_id and substr(t1.tree_id,0,8) = t3.tree_id	"
					+ "	) fd on d.deptid = fd.deptid";
			sql += "	where t.status = '"+CommonConstants.VISIBLE[1]+"' and d.status='"+CommonConstants.VISIBLE[1]+"' and d.deptid in ( "+jsql+" )";
			
			if(!StringUtils.isBlank(position) && !StringUtils.isBlank(positionLevel)){
				sql += "   and ( t.position_real_level in ("+position+") or t.position_level in ("+positionLevel+"))";
			}else{
				if(!StringUtils.isBlank(position)){
					sql += "   and t.position_real_level in ("+position+")";
				}
				if(!StringUtils.isBlank(positionLevel)){
					sql += "   and t.position_level in ("+positionLevel+")";
				}
			}
			//拼接排序语句
			sql += "  order by fd.juid asc";
			String endSql = "select n.userChushiId as candidateChushiId,n.userChushiName as candidateChushiName,n.userJuId as candidateJuId,n.userJuName as candidateJuName,count( distinct n.userid) as num "
					+ "	from ("+sql+") n   group by n.userJuId,n.userJuName,n.userChushiId,n.userChushiName order by n.userJuId";
			log.info(endSql);
			JSONObject result = userInfoService.getDateBySql(endSql);
			log.info(JSON.toJSONString(result));
			if("1".equals(result.get("flag")) ){
				json.put("flag", "1");
				JSONArray arr = result.getJSONArray("data");
				json.put("data", arr);
				json.put("total", arr.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", "解析异常");
		}
		return json;
	}

	/** 
	 * TODO 获取考试人员基本信息
	 * @author 李颖洁  
	 * @date 2018年8月17日下午12:00:18
	 * @param deptId 考试处室id
	 * @param position 职务
	 * @param positionLevel 职级
	 * @return List<SysFlowUserVo>
	 */
	@Override
	public List<SysTestUserVo> getTestPersonInfo(String deptId, String position, String positionLevel) {
		deptId = CommonUtils.commomSplit(deptId);
		position = CommonUtils.commomSplit(position);
		positionLevel = CommonUtils.commomSplit(positionLevel);
		List<SysTestUserVo> list = new ArrayList<>();
		String sql = "";
		try {
			String treeSql = "select t.tree_id from sys_flow_dept t	";
			if(StringUtils.isNotBlank(deptId)){
				treeSql += "	where t.deptid in ("+deptId+")";
			}
			String jsql = "select t.deptid from sys_flow_dept t where substr(t.tree_id,0,12) in ("+treeSql+")";
			sql += "select distinct t.userid,t.usernamefull as username,t.user_sex as sex,t.phone,t.status,t.position_real_level as positionRealLevel ,p1.name as positionNameFull,t.position_level as positionLevel ,p2.name as positionLevelName,	"
					+ "	d.deptid as userDeptId,fd.deptname as userDeptName,fd.chuid as userChushiId,fd.chuname as userChushiName,fd.juid as userJuId,fd.juname as userJuName	"
					+ "	from sys_flow_user t "
					+ "	left join sys_dictionary p1 on (t.position_real_level = p1.value and p1.remarks = "+XXKHCommonConstants.OCCUPATION_TYPE[0]+")	"
					+ "	left join sys_dictionary p2 on (t.position_level = p2.value and p2.remarks = "+XXKHCommonConstants.OCCUPATION_TYPE[1]+")	"
					+ "	left join sys_user_dprb d on t.userid = d.userid "
					+ "	left join "
					+ "	(select t1.deptid,t1.tree_id,t1.deptname,t2.deptid as chuid,substr(t2.tree_id,0,12)as chutreeid,t2.deptname chuname,t3.deptid as juid,t3.tree_id as jutreeid,t3.deptname as juname "
					+ "	from sys_flow_dept t1 , "
					+ "		(select t.deptid, t.deptname,t.tree_id	"
					+ "		from sys_flow_dept t	"
					+ "		where length(t.tree_id) = 12) t2,	"
					+ "		(select t.deptid, t.deptname,t.tree_id	"
					+ "		from sys_flow_dept t	"
					+ "		where length(t.tree_id) = 8) t3	"
					+ "	where substr(t1.tree_id,0,12) = t2.tree_id and substr(t1.tree_id,0,8) = t3.tree_id	"
					+ "	) fd on d.deptid = fd.deptid";
			sql += "	where t.status = '"+CommonConstants.VISIBLE[1]+"' and d.status = '"+CommonConstants.VISIBLE[1]+"'"
					+ "		and d.deptid in ("+jsql+")";
			
			if(!StringUtils.isBlank(position) && !StringUtils.isBlank(positionLevel)){
				sql += "   and ( t.position_real_level in ("+position+") or t.position_level in ("+positionLevel+"))";
			}else{
				if(!StringUtils.isBlank(position)){
					sql += "   and t.position_real_level in ("+position+")";
				}
				if(!StringUtils.isBlank(positionLevel)){
					sql += "   and t.position_level in ("+positionLevel+")";
				}
			}
			//拼接排序语句
			sql += "  order by fd.chuid asc";
			log.info(sql);
			//获取考试人员信息
			JSONObject result = userInfoService.getDateBySql(sql);
			if("1".equals(result.get("flag")) ){
				JSONArray arr = result.getJSONArray("data");
				list = JSONArray.toList(arr, SysTestUserVo.class);
				int count = 0;
				for (int i=0;i<list.size();i++) {
					for (SysTestUserVo obj : list) {
						if(list.get(i).getUserid().equals(obj.getUserid())){
							count += 1;
							if(count>1){
								list.remove(i);
								break;
							}
						}
					}
					count = 0;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return list;
	}
	
	/** 
	 * TODO 保存人员与试卷关联信息
	 * @author 李颖洁  
	 * @date 2018年8月20日下午2:31:33
	 * @param id 考试id
	 * @param name  考试名称
	 * @param list 试卷列表（多套试卷信息）
	 * @param userId (人员id)
	 * @return List<XxkhUserPaper>
	 * @throws Exception 
	 */
	@Override
	public List<XxkhUserPaper> saveUserPaper(String id, String name, List<XxkhTestPaper> list,List<SysTestUserVo> userVoList) throws Exception {
		List<XxkhUserPaper> userlist = getUserPaperByTestId(id,"");
			//长度为0,以前没有保存过，创建id保存数据
			if(userlist.size()==0){
				//验证参数有效性
				if(!StringUtils.isBlank(id)){
					//验证试卷是否存在
					if(list.size()==0){
						//如果试卷为0，先保存人
						for (SysTestUserVo uVo : userVoList) {
							XxkhUserPaper userPaper = saveUserInfo(uVo);;
							//随机生成id
							userPaper.setTestId(id);
							userPaper.setTestName(name);
							userlist.add(userPaper);
						}
					}else{
						//已存在试卷信息，判断考试是否为多次考试
						XxkhTestInfo testInfo = getOne(new XxkhTestInfo(id));
						if("1".equals(testInfo.getIsMoreChance())){
							//按照试卷顺序保存人
							for(XxkhTestPaper par : list){
								//判断试卷是否有简答题
								XxkhPaperInfo info = xxkhPaperInfoService.getOne(new XxkhPaperInfo(par.getPaperId()));
								boolean f = false;
								for (XxkhQuestionGroup gr : info.getGroup()) {
									if("5".equals(gr.getQuestionType())){
										f = true;
										break;
									}
								}
								for (SysTestUserVo uVo : userVoList) {
									XxkhUserPaper userPaper = saveUserInfo(uVo);
									//随机生成id
									userPaper.setTestId(id);
									userPaper.setTestName(name);
									userPaper.setPaperId(par.getPaperId());
									userPaper.setPaperOrder(par.getSort());
									if(f){
										userPaper.setArtificialMarkingState(XXKHCommonConstants.ARTIFICIAL_MARKING_STATE[0]);
									}else{
										userPaper.setArtificialMarkingState(XXKHCommonConstants.ARTIFICIAL_MARKING_STATE[1]);
									}
									userlist.add(userPaper);
								}
							}
						}else{
							for (SysTestUserVo uVo : userVoList) {
								XxkhUserPaper userPaper = saveUserInfo(uVo);
								//随机获取一套试卷
								int i = list.size();
								Random rand = new Random();
								int ran = rand.nextInt(i);
								log.info("随机数为："+ran);
								userPaper.setTestId(id);
								userPaper.setTestName(name);
								userPaper.setPaperId(list.get(ran).getPaperId());
								userPaper.setPaperOrder(list.get(ran).getSort());
								//判断试卷是否有简答题
								XxkhPaperInfo info = xxkhPaperInfoService.getOne(new XxkhPaperInfo(list.get(ran).getPaperId()));
								boolean f = false;
								for (XxkhQuestionGroup gr : info.getGroup()) {
									if("5".equals(gr.getQuestionType())){
										f = true;
										break;
									}
								}
								if(f){
									userPaper.setArtificialMarkingState(XXKHCommonConstants.ARTIFICIAL_MARKING_STATE[0]);
								}else{
									userPaper.setArtificialMarkingState(XXKHCommonConstants.ARTIFICIAL_MARKING_STATE[1]);
								}
								userlist.add(userPaper);
							}
						}
					}
					userlist = xxkhUserPaperDao.save(userlist);
				}else{
					//考试id为空，则返回0
					return userlist;
				}
			}else{
				//长度不为0，重新选择的考试人员
				//删除原来的，插入新的（可能更换试卷，全部删除）
				List<XxkhUserPaper> users = new ArrayList<>();
				XxkhUserPaper uPar = new XxkhUserPaper();
				uPar.setTestId(id);
				int row = deleteUserPaper(uPar);
				if(row>0){
					//插入新数据
					if(!StringUtils.isBlank(id)){
						if(list.size()==0){
							//如果试卷为0，先保存人
							for (SysTestUserVo uVo : userVoList) {
								XxkhUserPaper userPaper = saveUserInfo(uVo);;
								//随机生成id
								userPaper.setTestId(id);
								userPaper.setTestName(name);
								users.add(userPaper);
							}
						}else{
							//已存在试卷信息，判断考试是否为多次考试
							XxkhTestInfo testInfo = getOne(new XxkhTestInfo(id));
							if("1".equals(testInfo.getIsMoreChance())){
								for(XxkhTestPaper par : list){
									//判断试卷是否有简答题
									XxkhPaperInfo info = xxkhPaperInfoService.getOne(new XxkhPaperInfo(par.getPaperId()));
									boolean f = false;
									for (XxkhQuestionGroup gr : info.getGroup()) {
										if("5".equals(gr.getQuestionType())){
											f = true;
											break;
										}
									}
									for (SysTestUserVo uVo : userVoList) {
										XxkhUserPaper userPaper = saveUserInfo(uVo);
										//随机生成id
										userPaper.setTestId(id);
										userPaper.setTestName(name);
										userPaper.setPaperId(par.getPaperId());
										userPaper.setPaperOrder(par.getSort());
										if(f){
											userPaper.setArtificialMarkingState(XXKHCommonConstants.ARTIFICIAL_MARKING_STATE[0]);
										}else{
											userPaper.setArtificialMarkingState(XXKHCommonConstants.ARTIFICIAL_MARKING_STATE[1]);
										}
										users.add(userPaper);
									}
								}
							}else{
								for (SysTestUserVo uVo : userVoList) {
									XxkhUserPaper userPaper = saveUserInfo(uVo);
									//随机获取一套试卷
									int i = list.size();
									Random rand = new Random();
									int ran = rand.nextInt(i);
									log.info("随机数为："+ran);
									userPaper.setTestId(id);
									userPaper.setTestName(name);
									userPaper.setPaperId(list.get(ran).getPaperId());
									userPaper.setPaperOrder(list.get(ran).getSort());
									//判断试卷是否有简答题
									XxkhPaperInfo info = xxkhPaperInfoService.getOne(new XxkhPaperInfo(list.get(ran).getPaperId()));
									boolean f = false;
									for (XxkhQuestionGroup gr : info.getGroup()) {
										if("5".equals(gr.getQuestionType())){
											f = true;
											break;
										}
									}
									if(f){
										userPaper.setArtificialMarkingState(XXKHCommonConstants.ARTIFICIAL_MARKING_STATE[0]);
									}else{
										userPaper.setArtificialMarkingState(XXKHCommonConstants.ARTIFICIAL_MARKING_STATE[1]);
									}
									users.add(userPaper);
								}
							}
						}
						userlist = xxkhUserPaperDao.save(users);
					}
				}
			}
		return userlist;
	}

	public static boolean hasIn(Object arr, String v) {
		if(arr instanceof List<?>){
			for(Object s: (List<?>)arr){
				if(s.equals(v))
					return true;
			}
			
		}else if (arr .getClass().isArray() ){
			for(Object s: (Object[])arr){
				if(s.equals(v))
					return true;
			}
		}
	    return false;
	}
	
	/** 
	 * TODO 根据考试id获取人员与试卷关联信息
	 * @author 李颖洁  
	 * @date 2018年8月20日下午2:50:18
	 * @param id
	 * @return List<XxkhUserPaper>
	 */
	@Override
	public List<XxkhUserPaper> getUserPaperByTestId(String id,String userId) {
		List<XxkhUserPaper> list = new ArrayList<>();
		String sql = "select * from xxkh_user_paper t where t.visible = '"+CommonConstants.VISIBLE[1]+"' ";
		if (StringUtils.isNotBlank(id)){
            sql += "	and t.test_id = '"+id+"'";
        }
		if (StringUtils.isNotBlank(userId)){
			sql += "	and t.user_id = '"+userId+"'";
		}
		
		//拼接排序语句
		sql += "  order by t.paper_id asc ";
		list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(XxkhUserPaper.class));
		return list;
	}

	/** 
	 * TODO 判断是否有上报员
	 * @author 李颖洁  
	 * @date 2018年8月20日下午8:59:01
	 * @param deptIds
	 * @param deptNames
	 * @return
	 */
	@Override
	public JSONObject hasReportUserByDeptId(String deptIds, String deptNames,String roleNo) {
		JSONObject json = new JSONObject();
		boolean f = false;
		json.put("flag", "0");
		StringBuilder  deptNoUser = new StringBuilder();
		String roles = CommonUtils.commomSplit(roleNo);
		deptIds = CommonUtils.commomSplit(deptIds);
		try {
			String[] deptName = deptNames.split(",");
			String[] deptId = deptIds.split(",");
			String sql = "select sfd.deptname,sfd.deptid,duser.userid" 
					+"	from" 
					+"	(select d.deptid, d.deptname, t.userid,substr(d.tree_id,0,12) as treeid"
					+"	from (select t.deptid, t.deptname,t.tree_id"
					+"	from sys_flow_dept t"
					+"	where substr(t.tree_id, 0, 12) in"
					+"	(select t.tree_id from sys_flow_dept t"
					+"	where t.deptid in ("+deptIds+"))) d"
					+"	left join (select d.id, d.deptid, d.userid, f.roleid"
					+"	from sys_user_dprb d"
					+"	join sys_user_frole f"
					+"	on d.id = f.u_dept_id"
					+"	where f.roleid in (select t.roleid from sys_flow_role t where t.role_no in("+roles+"))) t"
					+"	on d.deptid = t.deptid) duser" 
					+"	left join sys_flow_dept sfd on duser.treeid=sfd.tree_id where duser.userid is not null";
			log.info(sql);
			JSONObject result = userInfoService.getDateBySql(sql);
			List<UserDeptVo> list = new ArrayList<>();
			log.info(JSON.toJSONString(result));
			JSONArray arr = result.getJSONArray("data");
			list = JSONArray.toList(arr, UserDeptVo.class);
			for(int i=0;i<deptId.length;i++){
				String n = deptId[i];
				String m = deptName[i];
				n = n.replace("'", "").trim();
				for (UserDeptVo deptVo : list) {
					log.info(deptVo.getDeptid());
					if(n.equals(deptVo.getDeptid())){
						f = true;
						break;
					}
				}
				if(!f){
					String pSql ="SELECT t.deptname\n" +
							"  FROM sys_flow_dept t\n" +
							" WHERE t.deptid =\n" +
							"       (SELECT t.super_id FROM sys_flow_dept t WHERE t.deptid = '"+n+"')";
					JSONObject pResult = userInfoService.getDateBySql(pSql);
					List<Map<String,Object>> lists = pResult.getJSONArray("data");
					String pName = "";
					if(lists.size()>0){
						pName = lists.get(0).get("deptname").toString();
					}
					deptNoUser.append(pName+"-"+m+"；");
					log.info(deptNoUser.toString());
				}
				f = false;
			}
			if(deptNoUser.length()!=0){
				//如果有部门没有上报员则返回没有上报员的部门名称，不返回上报员信息
				json.put("flag", "0");
				json.put("data",deptNoUser.toString());
			}else{
				json.put("flag", "1");
				json.put("data", JSONArray.fromObject(list));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", "解析异常");
		}
		return json;
	}

	/** 
	 * TODO 根据考试id保存试卷信息(批量保存或单个保存)
	 * @author 李颖洁  
	 * @date 2018年8月22日下午3:04:03
	 * @param id 考试 id
	 * @return XxkhTestPaper
	 */
	@Override
	public List<XxkhTestPaper> saveTestPaper(String testId,String paperId) {
		List<XxkhTestPaper> list = new ArrayList<>();
		if(paperId.indexOf(",")!= -1){
			String[] papers = paperId.split(",");
			int sort = 0;
			for (int i=0;i<papers.length;i++) {
				XxkhTestPaper testPaper = new XxkhTestPaper();
				//创建人、部门、处室、二级局（id、name）
				testPaper.setCreUserId(UserUtil.getCruUserId());
				testPaper.setCreUserName(UserUtil.getCruUserName());
				testPaper.setCreDeptId(UserUtil.getCruDeptId());
				testPaper.setCreDeptName(UserUtil.getCruDeptName());
				testPaper.setCreChushiId(UserUtil.getCruChushiId());
				testPaper.setCreChushiName(UserUtil.getCruChushiName());
				testPaper.setCreJuId(UserUtil.getCruJuId());
				testPaper.setCreJuName(UserUtil.getCruJuName());
				testPaper.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
				testPaper.setVisible(CommonConstants.VISIBLE[1]);
				//随机生成id
				testPaper.setTestId(testId);
				testPaper.setPaperId(papers[i]);
				//暂时按照一套试卷先保存
				if(i<1){
					list = getPapersByTestId(testId);
					if(list.size()==0){
						sort = 1;
					}else{
						sort = getMaxSortByTestId(testId)+1;
					}
					testPaper.setSort(String.valueOf(sort));
				}else{
					sort += 1;
					testPaper.setSort(String.valueOf(sort));
				}
				list.add(testPaper);
				list = xxkhTestPaperDao.save(list);
			}
		}else{
			XxkhTestPaper testPaper = new XxkhTestPaper();
			//创建人、部门、处室、二级局（id、name）
			testPaper.setCreUserId(UserUtil.getCruUserId());
			testPaper.setCreUserName(UserUtil.getCruUserName());
			testPaper.setCreDeptId(UserUtil.getCruDeptId());
			testPaper.setCreDeptName(UserUtil.getCruDeptName());
			testPaper.setCreChushiId(UserUtil.getCruChushiId());
			testPaper.setCreChushiName(UserUtil.getCruChushiName());
			testPaper.setCreJuId(UserUtil.getCruJuId());
			testPaper.setCreJuName(UserUtil.getCruJuName());
			testPaper.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			testPaper.setVisible(CommonConstants.VISIBLE[1]);
			//随机生成id
			testPaper.setTestId(testId);
			testPaper.setPaperId(paperId);
			//暂时按照一套试卷先保存
			int sort;
			list = getPapersByTestId(testId);
			if(list.size()==0){
				sort = 1;
			}else{
				sort = getMaxSortByTestId(testId)+1;
			}
			testPaper.setSort(String.valueOf(sort));
			list.add(testPaper);
			list = xxkhTestPaperDao.save(list);
		}
		return list;
	}

	/** 
	 * TODO 获取考试试卷顺序最大值
	 * @author 李颖洁  
	 * @date 2018年8月22日下午4:14:04
	 * @param id
	 * @return
	 */
	@Override
	public int getMaxSortByTestId(String id) {
		String sql = "select max(to_number(t.sort)) from xxkh_test_paper t where t.visible = '"+CommonConstants.VISIBLE[1]+"' ";
		if (StringUtils.isNotBlank(id)){
            sql += "	and t.test_id = '"+id+"'";
        }
		int sort = jdbcTemplate.queryForObject(sql,Integer.class);
		return sort;
	}
	
	/** 
	 * TODO 获取考试试卷信息 
	 * @author 李颖洁  
	 * @date 2018年8月22日下午3:32:59
	 * @param paper
	 * @return List<XxkhTestPaper>
	 */
	@Override
	public List<XxkhTestPaper> getPapersByTestId(XxkhTestPaper paper) {
		List<XxkhTestPaper> list = new ArrayList<>();
		String people ="";
		if (StringUtils.isNotBlank(paper.getTestPaperUpPeople())) {
			people = ",(select count(1) from xxkh_user_paper up where t.paper_id = up.paper_id and up.test_id= f.id) as testPaperUpPeople";
		}
		String sql = "select t.id,t.test_id,t.paper_id,t.sort,d.name,d.full_score,d.state as paperState,d.is_share as paperShared,f.type	"+people+ " "
				+ "	from xxkh_test_paper t left join xxkh_paper_info d on t.paper_id = d.id "
				+ "	join xxkh_test_info f on t.test_id = f.id "
				+ "	where t.visible = '"+CommonConstants.VISIBLE[1]+"' ";
		if (StringUtils.isNotBlank(paper.getTestId())){
            sql += "	and t.test_id = '"+paper.getTestId()+"'";
        }
		if (StringUtils.isNotBlank(paper.getIsJanDa())) {
			sql = "select * from ("+sql+") t left join xxkh_question_group q on t.paper_id = q.paper_id where q.question_type = '5' ";
		}
		//拼接排序语句
		sql += "  order by t.sort asc ";
		log.info(sql);
		list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(XxkhTestPaper.class));
		//判断试卷是否可用
		if("0".equals(list.get(0).getPaperState())){
			//如果是部门的考试，判断试卷是否共享
			if("deptks".equals(list.get(0).getType())){
				if("0".equals(list.get(0).getPaperShared())){
					//删除保存的考试与试卷关联表信息
					XxkhTestPaper testPaper = new XxkhTestPaper();
					testPaper.setId(list.get(0).getId());
					int row = deleteTestPaper(testPaper);
				}
			}else{
				//删除保存的考试与试卷关联表信息
				XxkhTestPaper testPaper = new XxkhTestPaper();
				testPaper.setId(list.get(0).getId());
				int row = deleteTestPaper(testPaper);
			}
		}
		return list;
	}

	/** 
	 * TODO 获取考试人员信息详情(考试-人员关联表里查,带分页)
	 * @author 李颖洁  
	 * @date 2018年8月24日上午11:09:29
	 * @param pageable
	 * @param pageImpl
	 * @param userPar
	 * @return PageImpl
	 */
	@Override
	public PageImpl getTestPersonInfoByDeptId(Pageable pageable, PageImpl pageImpl, XxkhUserPaper userPar) {
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		//全部列表查询
		querySql.append("	from XxkhUserPaper t ");
		querySql.append("	where t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		if(!StringUtils.isBlank(userPar.getTestId())){
			querySql.append("   and t.testId = '"+userPar.getTestId()+"' ");
		}
		if(!StringUtils.isBlank(userPar.getPaperId())){
			querySql.append("   and t.paperId = '"+userPar.getPaperId()+"' ");
		}
		if (!StringUtils.isBlank(userPar.getCandidateChushiId())){
			querySql.append("   and t.candidateChushiId = ?");
			para.add(userPar.getCandidateChushiId());
		}
		if (!StringUtils.isBlank(userPar.getCandidateJuId())){
			querySql.append("   and t.candidateJuId = ?");
			para.add(userPar.getCandidateJuId());
		}
		if (!StringUtils.isBlank(userPar.getCandidateDutyName())){
			querySql.append("   and t.candidateDutyName like '%"+userPar.getCandidateDutyName()+"%'");
		}
		if (!StringUtils.isBlank(userPar.getUserName())){
			querySql.append("   and t.userName like '%"+userPar.getUserName()+"%' ");
		}
		if (!StringUtils.isBlank(userPar.getIsSubmit())){
			querySql.append("   and t.isSubmit = ? ");
			para.add(userPar.getIsSubmit());
		}
		if(StringUtils.isBlank(userPar.getIsUser())) {
			//拼接排序语句
			if (StringUtils.isBlank(pageImpl.getSortName())) {
				querySql.append("  order by t.creTime desc");
			}else{
				querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder());
			}
		}else {
			//拼接排序语句
			if (StringUtils.isBlank(pageImpl.getSortName())) {
				querySql.append("  order by t.isSubmit desc");
			}else{
				querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder());
			}
		}
		
		log.info(querySql.toString());
		Page<XxkhUserPaper> page = xxkhUserPaperDao.query(querySql.toString(), pageable,para.toArray());
		pageImpl.setFlag("1");
		pageImpl.getData().setRows(page.getContent());
		pageImpl.getData().setTotal((int)page.getTotalElements());
		return pageImpl;

	}

	/** 
	 * TODO 获取考试人员数量(查询本次考试的人员)(后台不分页)
	 * @author 李颖洁  
	 * @date 2018年8月24日下午3:11:18
	 * @param pageable
	 * @param paper
	 * @param pageImpl
	 * @return
	 */
	@Override
	public List<XxkhUserPaper> getTestPersonCountInUserPaper(XxkhUserPaper paper) {
		StringBuilder querySql = new StringBuilder();
		//全部列表查询
		querySql.append(" select t.candidate_chushi_id,t.candidate_chushi_name,t.candidate_ju_id,t.candidate_ju_name,count(distinct t.user_id) as num  from xxkh_user_paper t ");
		querySql.append("	where t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		if(!StringUtils.isBlank(paper.getTestId())){
			querySql.append("   and t.test_id = '"+paper.getTestId()+"'");
		}
		querySql.append("  group by t.candidate_chushi_id,t.candidate_chushi_name,t.candidate_ju_id,t.candidate_ju_name ");
		//拼接排序语句
		querySql.append("  order by t.candidate_ju_id desc");
		log.info(querySql.toString());
		List<XxkhUserPaper>  list = jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(XxkhUserPaper.class));
		return list;
	}

	/** 
	 * TODO 查询已选中考试试卷列表
	 * @author 李颖洁  
	 * @date 2018年8月25日上午10:23:56
	 * @param pageImpl
	 * @param testPaper
	 * @param pageable
	 * @return
	 */
	@Override
	public PageImpl getPaperInfoList(PageImpl pageImpl, XxkhTestPaper testPaper, Pageable pageable) {
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		//全部列表查询
		querySql.append("select new XxkhPaperInfo(t.id,t.name,t.type,t.nodeId,t.difficultyLevel,t.fullScore)");
		querySql.append("	from XxkhTestPaper p ,XxkhPaperInfo t");
		querySql.append("	where t.id = p.paperId and p.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		if(!StringUtils.isBlank(testPaper.getTestId())){
			querySql.append("   and p.testId = ?");
			para.add(testPaper.getTestId());
		}
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by p.sort asc) ");
		}else{
			querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}
		Page<XxkhPaperInfo> page = xxkhTestPaperDao.query(querySql.toString(), pageable,para.toArray());
		pageImpl.setFlag("1");
		pageImpl.getData().setRows(page.getContent());
		pageImpl.getData().setTotal((int)page.getTotalElements());
		return pageImpl;
	}

	/** TODO 删除考试试卷数据（批量删除）
	 * @author 李颖洁  
	 * @date 2018年8月25日下午5:55:26
	 * @param testId
	 * @param paperIds
	 * @return
	 */
	@Override
	public int deleteBatchTestPaper(String testId, String paperIds) {
		String jpql = "delete from  XxkhTestPaper t where t.visible = "+CommonConstants.VISIBLE[1];
		if (StringUtils.isNotBlank(testId)){
			jpql += "	and t.testId = '"+testId+"'";
        }
		StringBuilder pIds = new StringBuilder();
		String[] pId = paperIds.split(",");
		for (String i : pId) {
			pIds.append("'"+i+"',");
		}
		String ids  = pIds.substring(0, pIds.length()-1);
		if (StringUtils.isNotBlank(paperIds)){
			jpql += "	and t.paperId in ("+ids+")";
        }
		return xxkhUserPaperDao.update(jpql);
	}

	/**
	 * TODO  保存上报的考试人员信息
	 * @author 李颖洁  
	 * @date 2018年8月28日下午8:02:21
	 * @param id 考试id
	 * @param personIds 上报的人员id
	 * @param deptId 上报的处室id
	 * @param name 考试名称
	 * @throws Exception
	 * @return JSONObject
	 */
	@Override
	public JSONObject saveReportPerson(String id, String personIds, String deptId,String name)throws Exception {
		deptId = CommonUtils.commomSplit(deptId);
		personIds = CommonUtils.commomSplit(personIds);
		JSONObject json = new JSONObject();
		json.put("falg", "0");
		String sql = "select m.userid,m.username,m.user_sex as sex,m.phone,m.status,"
				+ "	m.position_real_level as positionRealLevel,m.position_name_full as positionNameFull,m.position_level as positionLevel,m.position_level_name as positionLevelName,"
				+ "	m.userDeptId,m.userDeptName,m.userChushiId,m.userChushiName,m.userJuId,m.userJuName "
				+ "	from ("+SQL+") m  where m.status='"+XXKHCommonConstants.STATUS[1]+"'";
		if (StringUtils.isNotBlank(deptId)){
			sql += "	and m.userChushiId = "+deptId;
        }
		if (StringUtils.isNotBlank(personIds)){
			sql += "	and m.userid in ("+personIds+")";
		}
		log.info(sql);
		List<SysTestUserVo> userVoList = new ArrayList<>();
		
		try {
			//获取考试人员信息
			JSONObject result = userInfoService.getDateBySql(sql);
			if("1".equals(result.get("flag")) ){
				JSONArray arr = result.getJSONArray("data");
				if(arr.size()==0){
					json.put("msg", "没有找到上报的人员信息");
				}else{
					userVoList = JSONArray.toList(arr, SysTestUserVo.class);
					int count = 0;
					for (int i=0;i<userVoList.size();i++) {
						for (SysTestUserVo obj : userVoList) {
							if(userVoList.get(i).getUserid().equals(obj.getUserid())){
								count += 1;
								if(count>1){
									userVoList.remove(i);
									break;
								}
							}
						}
						count = 0;
					}
					try {
						//保存人员信息
						List<XxkhUserPaper> userList = new ArrayList<>();
						//遍历人员信息
						for (SysTestUserVo uVo : userVoList) {
							XxkhUserPaper userPaper = saveUserInfo(uVo);
							//随机生成id
							userPaper.setTestId(id);
							userPaper.setTestName(name);
							userList.add(userPaper);
						}
						userList = xxkhUserPaperDao.save(userList);
						if(userList.size()!=0){
							json.put("flag", "1");
							json.put("data", userList);
						}else{
							json.put("msg", "保存人员信息失败！");
						}
					} catch (Exception e) {
						e.printStackTrace();
						log.error(e.getMessage(),e);
						json.put("msg", "保存人员信息异常");
					}
				}
			}else{
				json.put("msg", "获取人员信息异常！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", e.getMessage());
		}
		return json;
	}

	/** TODO 保存人员信息（人员与考试与试卷的关联表）
	 * @author 李颖洁  
	 * @date 2018年8月31日上午9:41:50
	 * @param userList
	 * @return
	 */
	@Override
	public XxkhUserPaper saveUserInfo(SysTestUserVo uVo) {
		XxkhUserPaper userPaper = new XxkhUserPaper();
		//创建人、部门、处室、二级局（id、name）
		userPaper.setCreUserId(UserUtil.getCruUserId());
		userPaper.setCreUserName(UserUtil.getCruUserName());
		userPaper.setCreDeptId(UserUtil.getCruDeptId());
		userPaper.setCreDeptName(UserUtil.getCruDeptName());
		userPaper.setCreChushiId(UserUtil.getCruChushiId());
		userPaper.setCreChushiName(UserUtil.getCruChushiName());
		userPaper.setCreJuId(UserUtil.getCruJuId());
		userPaper.setCreJuName(UserUtil.getCruJuName());
		//随机生成id
		userPaper.setIsSubmit(XXKHCommonConstants.IS_SUBMIT[0]);
		userPaper.setVisible(CommonConstants.VISIBLE[1]);
		userPaper.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		userPaper.setUserId(uVo.getUserid());
		userPaper.setUserName(uVo.getUsername());
		userPaper.setCandidateDeptId(uVo.getUserdeptid());
		userPaper.setCandidateDeptName(uVo.getUserdeptname());
		userPaper.setCandidateChushiId(uVo.getUserchushiid());
		userPaper.setCandidateChushiName(uVo.getUserchushiname());
		userPaper.setCandidateJuId(uVo.getUserjuid());
		userPaper.setCandidateJuName(uVo.getUserjuname());
		userPaper.setCandidateDuty(uVo.getPositionreallevel());
		userPaper.setCandidateDutyName(uVo.getPositionnamefull());
		userPaper.setCandidateLevel(uVo.getPositionlevel());
		userPaper.setCandidateLevelName(uVo.getPositionlevelname());
		userPaper.setPhone(uVo.getPhone());
		userPaper.setUserSex(uVo.getSex());
		userPaper.setPaperSubjectiveScore("0");//保存主观题初始值为0
		userPaper.setPaperObjectiveScore("");//保存客观题初始值为空
		return userPaper;
	}

	/**
	 * TODO 保存（修改）考试基本信息
	 * @author 李颖洁  
	 * @date 2018年9月5日上午9:12:30
	 * @param info
	 * @return XxkhTestInfo
	 */
	@Override
	public XxkhTestInfo edit(XxkhTestInfo info) {
		if (info.getId()!=null && !info.getId().equals("")) {
			XxkhTestInfo oldInfo = dao.findOne(info.getId());
			oldInfo.setAnswerRandom(info.getAnswerRandom());
			oldInfo.setAnswerTime(info.getAnswerTime());
			oldInfo.setDifficultyLevel(info.getDifficultyLevel());
			oldInfo.setDutyIds(info.getDutyIds());
			oldInfo.setEndTime(info.getEndTime());
			oldInfo.setStartTime(info.getStartTime());
			oldInfo.setFullScore(info.getFullScore());
			oldInfo.setIsShowAnswer(info.getIsShowAnswer());
			oldInfo.setName(info.getName());
			oldInfo.setLevelIds(info.getLevelIds());
			oldInfo.setTestToDepartment(info.getTestToDepartment());
			oldInfo.setPassScore(info.getPassScore());
			oldInfo.setTestChuShiIds(info.getTestChuShiIds());
			oldInfo.setTestChuShiNames(info.getTestChuShiNames());
			oldInfo.setTestNotice(info.getTestNotice());
			oldInfo.setRequirement(info.getRequirement());
			oldInfo.setIsMoreChance(info.getIsMoreChance());
			oldInfo.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			oldInfo.setUpdateUserId(UserUtil.getCruUserId());
			oldInfo.setUpdateUserName(UserUtil.getCruUserName());
			info = dao.save(oldInfo);
		}else {
			info.setCreUserId(UserUtil.getCruUserId());
			info.setCreUserName(UserUtil.getCruUserName());
			info.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			info.setCreDeptId(UserUtil.getCruDeptId());
			info.setCreDeptName(UserUtil.getCruDeptName());
			info.setCreJuId(UserUtil.getCruJuId());
			info.setCreJuName(UserUtil.getCruJuName());
			info.setVisible(CommonConstants.VISIBLE[1]);
			info = dao.saveAndFlush(info);
		}
		return info;
	}

	/**
	 * TODO 更改考试信息状态
	 * @author 李颖洁  
	 * @date 2018年9月5日上午9:13:10
	 * @param info
	 * @return XxkhTestInfo
	 */
	@Override
	public XxkhTestInfo updateStatus(XxkhTestInfo info) {
		XxkhTestInfo testInfo = dao.findOne(info.getId());
		testInfo.setUpdateTime(DateUtil.COMMON_FULL.getDateText(new Date()));
		testInfo.setUpdateUserId(UserUtil.getCruUserId());
		testInfo.setUpdateUserName(UserUtil.getCruUserName());
		if (info.getState()!=null&&!info.getState().equals("")) {
			testInfo.setState(info.getState());
		}
		return dao.saveAndFlush(testInfo);
	}

	/**
	 * TODO 获取一个考试信息
	 * @author 李颖洁  
	 * @date 2018年9月5日上午9:13:42
	 * @param info
	 * @return	XxkhTestInfo
	 */
	@Override
	public XxkhTestInfo getOne(XxkhTestInfo info) {
		return dao.findOne(info.getId());
	}

	/**
	 * TODO  删除一个考试信息
	 * @author 李颖洁  
	 * @date 2018年9月5日上午9:14:02
	 * @param info
	 * @return int
	 */
	@Override
	public int delete(XxkhTestInfo info) {
		String jpql = "update   XxkhTestInfo t set t.visible = "+CommonConstants.VISIBLE[0]+" where t.id = ?";
		return dao.update(jpql, info.getId());
	}

	/**
	 * TODO 获取考试信息列表
	 * @author 李颖洁  
	 * @date 2018年9月5日上午9:02:07
	 * @param info
	 * @param pageImpl
	 * @return Page<XxkhTestInfo>
	 */
	@Override
	public Page<XxkhTestInfo> list(final XxkhTestInfo info, final PageImpl pageImpl) {
		Pageable pageable = new PageRequest(pageImpl.getPageNumber()-1, pageImpl.getPageSize());
		return dao.findAll(new Specification<XxkhTestInfo>() {
			@Override
			public Predicate toPredicate(Root<XxkhTestInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
				Predicate pType =  cb.equal(root.get("type").as(String.class), info.getType());
				Predicate pcreJuId =  cb.equal(root.get("creJuId").as(String.class), info.getCreJuId());
				predicates.add(pVisible);
				predicates.add(pType);
				predicates.add(pcreJuId);
				Predicate dataName = null,nodeId=null,type=null,state=null;
				Order desc=null;
				if (!StringUtils.isBlank(info.getName())){
					dataName = cb.like(root.get("name").as(String.class), "%"+info.getName()+"%");
					predicates.add(dataName);
				}
				if (!StringUtils.isBlank(info.getNodeId())){
					nodeId = cb.equal(root.get("nodeId").as(String.class), info.getNodeId());
					predicates.add(nodeId);
				}
				if (!StringUtils.isBlank(info.getType())){
					type = cb.equal(root.get("type").as(String.class), info.getType());
					predicates.add(type);
				}
				if (!StringUtils.isBlank(info.getState())){
					state = cb.equal(root.get("state").as(String.class), info.getState());
					predicates.add(state);
				}
				if (!StringUtils.isBlank(info.getEndTime())) {
					Predicate startTime =cb.lessThanOrEqualTo(root.get("startTime").as(String.class),info.getEndTime() );
					predicates.add(startTime);
				}
				if (!StringUtils.isBlank(info.getStartTime())) {
					Predicate endTime =cb.greaterThanOrEqualTo(root.get("endTime").as(String.class), info.getStartTime());
					predicates.add(endTime);
				}
				if(StringUtils.isBlank(pageImpl.getSortName())){
					desc = cb.desc(root.get("creTime").as(String.class));
				}else{
					if (pageImpl.getSortOrder().equals("desc")) {
						desc = cb.desc(root.get(pageImpl.getSortName()).as(Double.class));	
					}
					if (pageImpl.getSortOrder().equals("asc")) {
						desc = cb.asc(root.get(pageImpl.getSortName()).as(Double.class));	
					}
				}
				query.where(cb.and(predicates.toArray(new Predicate[0])));
				query.orderBy(desc);
				return query.getRestriction();
			}
		},pageable);
	}

	/**
	 * TODO 获取工作通知消息id
	 * @author 李颖洁  
	 * @date 2018年9月12日下午4:08:40
	 * @param notityMessage
	 * @return JSONObject
	 */
	@Override
	public JSONObject queryMessageId(NotityMessage notityMessage) {
		String sql ="select * from message t where t.senderid ="+notityMessage.getSenderId()+" and t.accepterid="+notityMessage.getAccepterId()+" and t.subject='"+notityMessage.getSubject()+"' and t.content='"+notityMessage.getContent()+"'";
		JSONObject result = userInfoService.getDateBySql(sql);
		return result;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 根据日期获取开始时间为日期的考试
	 * @Date 2018/9/4 20:33
	 * @Param [TestDate]
	 * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.xxkh.tsetinfo.entity.XxkhTestInfo>
	 **/
	public List<XxkhTestInfo> queryTestByTestDate(String TestDate){
		String sql =
				"SELECT s.*\n" +
						"  FROM xxkh_test_info s\n" +
						" where s.start_time = '"+TestDate+"' " +//当天的
						" and s.state ='"+XXKHCommonConstants.RECORD_STATE[2]+"'" +//已发布的
						" and s.visible ='"+CommonConstants.VISIBLE[1]+"'";//未删除的
		List<XxkhTestInfo> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(XxkhTestInfo.class));
		return list;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 根据日期获取结束时间为昨天以前所有的考试
	 * @Date 2018/9/18 20:55
	 * @Param [TestDate]
	 * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhTestInfo>
	 **/
	public List<XxkhTestInfo> queryTestOfdeleteWaitNoflw(){

		Date today = new Date();
		/* System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(today));
		System.out.println(today.getTime());
		System.out.println(1477058241633L-86400000L);*/
		Date yesterday = new Date(today.getTime() - 86400000L);//86400000L，它的意思是说1天的时间=24小时 x 60分钟 x 60秒 x 1000毫秒 单位是L。
		//即86400000L可用写成 24*60*60*1000L
		//System.out.println(24*60*60*1000L);
		String TestEndDate = new SimpleDateFormat("yyyy-MM-dd").format(yesterday);

		String sql =
				"SELECT s.*\n" +
						"  FROM xxkh_test_info s\n" +
						" where s.end_time = '"+TestEndDate+"' " +//当天的
						"and s.state ='"+XXKHCommonConstants.RECORD_STATE[2]+"'" +//已发布的
						" and s.visible ='"+CommonConstants.VISIBLE[1]+"'";//未删除的
		List<XxkhTestInfo> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(XxkhTestInfo.class));
		return list;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 根据考试id获取考试人员(不分页)
	 * @Date 2018/9/4 17:34
	 * @Param [TestDate]
	 * @return java.util.List<java.lang.Object>
	 **/
	public List<XxkhUserPaper> queryAllTestUser(String testId){
		String sql ="SELECT distinct\n" +
				"       t.user_id,\n" +
				"       t.user_name,\n" +
				"       t.candidate_dept_id,\n" +
				"       t.candidate_dept_name,\n" +
				"       t.candidate_chushi_id,\n" +
				"       t.candidate_chushi_name,\n" +
				"       t.candidate_ju_id,\n" +
				"       t.candidate_ju_name,\n" +
				"       t.candidate_duty,\n" +
				"       t.candidate_level,\n" +
				"       t.user_sex,\n" +
				"       t.phone,\n" +
				"       t.candidate_duty_name,\n" +
				"       t.candidate_level_name\n" +
				"  FROM xxkh_user_paper t\n" +
				" WHERE t.test_id = '"+testId+"'\n" +
				"";
		List<XxkhUserPaper> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(XxkhUserPaper.class));
		return list;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 交卷后根据考试id，试卷id，考试人员id更新交卷状态,更新客观题得分
	 * @Date 2018/9/11 15:23
	 * @Param [info]
	 * @return int
	 **/
	public void updateObjectiveScore(XxkhUserPaper info){
		String sql ="update xxkh_user_paper t set t.paper_objective_score ='"+info.getPaperObjectiveScore()+"',t.is_submit='"+XXKHCommonConstants.IS_SUBMIT[1]+"' where t.test_id ='"+info.getTestId()+"' and t.paper_id='"+info.getPaperId()+"' and t.user_id='"+info.getUserId()+"'";
		jdbcTemplate.execute(sql);
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 进入考试更新首次进入试卷考试时间
	 * @Date 2018/9/20 19:44
	 * @Param [info]
	 * @return void
	 **/
	public void updateBeginTestTime(XxkhUserPaper info){
		String sql ="update xxkh_user_paper t set t.begin_test_time ='"+DateUtil.COMMON_FULL.getDateText(new Date())+"' where t.test_id ='"+info.getTestId()+"' and t.paper_id='"+info.getPaperId()+"' and t.user_id='"+info.getUserId()+"'";
		jdbcTemplate.execute(sql);
	}

    /**
     * @Author 王富康
     * @Description //TODO 查询本厂考试的已考试次数和未考次数
     * @Date 2018/10/8 10:08
     * @Param [info]
     * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.TestCountVo>
     **/
    public List<TestCountVo> queryTestCount(XxkhTestInfo info){
        String sql ="SELECT aa.wkCount, bb.ykCount\n" +
                        "  FROM (SELECT count(m.paper_id) as wkCount\n" +
                        "          FROM (SELECT t.paper_id\n" +
                        "                  FROM xxkh_user_paper t\n" +
                        "                 WHERE t.test_id = '"+info.getId()+"'\n" +
                        "                   and t.is_submit = '"+XXKHCommonConstants.IS_SUBMIT[0]+"'\n" +
                        "                   and t.user_id = '"+UserUtil.getCruUserId()+"'\n" +//当前登录人的考试
                        "                 group by t.paper_id) m) aa,\n" +
                        "       (SELECT count(n.paper_id) as ykCount\n" +
                        "          FROM (SELECT s.paper_id\n" +
                        "                  FROM xxkh_user_paper s\n" +
                        "                 WHERE s.test_id = '"+info.getId()+"'\n" +
                        "                   and s.is_submit = '"+XXKHCommonConstants.IS_SUBMIT[1]+"'\n" +
                        "                   and s.user_id = '"+UserUtil.getCruUserId()+"'\n" +//当前登录人的考试
                        "                 group by s.paper_id) n) bb";

        List<TestCountVo> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(TestCountVo.class));
        return list;
    }

    /**
     * @Author 王富康
     * @Description //TODO 按照试卷顺序获取本场考试未考试的第一个试卷id
     * @Date 2018/10/8 15:09
     * @Param [info]
     * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.xxkh.testmanage.entity.XxkhUserPaper>
     **/
    public List<XxkhUserPaper> getTestOfFristPaperId(XxkhTestInfo info){
        String sql ="SELECT t.paper_id,t.paper_order\n" +
                        "                  FROM xxkh_user_paper t\n" +
                        "                 WHERE t.test_id = '"+info.getId()+"'\n" +
                        "                   and t.is_submit = '"+XXKHCommonConstants.IS_SUBMIT[0]+"'\n" +
                        "                   and t.user_id = '"+UserUtil.getCruUserId()+"'\n" +//当前登录人的考试
                        "                 group by t.paper_id,t.paper_order\n" +
                        "                 order by t.paper_order asc";
        List<XxkhUserPaper> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(XxkhUserPaper.class));
        return list;
    }

	@Override
	public XxkhUserPaper updateIsPingjuan(XxkhUserPaper info) {
		List<XxkhUserPaper> userByPaperIdtestIdUserId = xxkhUserPaperDao.getUserByPaperIdtestIdUserId(info.getPaperId(), info.getTestId(), info.getUserId());
		if (userByPaperIdtestIdUserId.size()>0) {
			userByPaperIdtestIdUserId.get(0).setPaperSubjectiveScore(info.getPaperSubjectiveScore());
			if(info.getArtificialMarkingState()!=null && !info.getArtificialMarkingState().equals(""))
			userByPaperIdtestIdUserId.get(0).setArtificialMarkingState("1");
			
		}
		return xxkhUserPaperDao.saveAndFlush(userByPaperIdtestIdUserId.get(0));
	}

	@Override
	public int updateTestPingJuan(XxkhTestInfo info) {
		String sql  = " update xxkh_test_info t set t.mark_status = '"+info.getMarkStatus()+"' where t.id = '"+info.getId()+"'";
		return jdbcTemplate.update(sql);
	}

	@Override
	public int updateTestAllUser(String id) {
		String sql  = " update xxkh_user_paper t set t.artificial_marking_state = '1' where t.test_id = '"+id+"'";
		return jdbcTemplate.update(sql);
	}
	
	/**
	 * TODO 获取考试人员信息详情(考试-人员关联表里查,带分页,没有关联考试试卷信息前提下)
	 * @author 李颖洁  
	 * @date 2018年10月8日下午2:57:25
	 * @param pageable
	 * @param pageImpl
	 * @param userPar
	 * @return
	 */
	@Override
	public PageImpl getTestPersonInfoList(Pageable pageable, PageImpl pageImpl, XxkhUserPaper userPar) {
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		//全部列表查询
		querySql.append(" select distinct t.user_id,t.user_name,t.candidate_dept_id,t.candidate_dept_name,t.candidate_chushi_id,"
				+ "t.candidate_chushi_name,t.candidate_ju_id,t.candidate_ju_name,t.candidate_duty,t.candidate_duty_name,t.candidate_level,"
				+ "t.candidate_level_name,t.user_sex,t.phone  from xxkh_user_paper t ");
		querySql.append("	where t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		if(!StringUtils.isBlank(userPar.getTestId())){
			querySql.append("   and t.test_id = '"+userPar.getTestId()+"'");
		}
		//拼接排序语句
		if (!StringUtils.isBlank(userPar.getCandidateChushiId())){
			querySql.append("   and t.candidate_chushi_id = "+userPar.getCandidateChushiId());
		}
		if (!StringUtils.isBlank(userPar.getCandidateJuId())){
			querySql.append("   and t.candidate_ju_id = "+userPar.getCandidateJuId());
		}
		if (!StringUtils.isBlank(userPar.getCandidateDutyName())){
			querySql.append("   and t.candidate_duty_name like '%"+userPar.getCandidateDutyName()+"%'");
		}
		if (!StringUtils.isBlank(userPar.getUserName())){
			querySql.append("   and t.user_name like '%"+userPar.getUserName()+"%' ");
		}
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.user_id desc");
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
			querySql.append("  order by t."+ss+" "+pageImpl.getSortOrder());
		}
		log.info(querySql.toString());
		int after = pageImpl.getPageNumber()*pageImpl.getPageSize();
		int pre = (pageImpl.getPageNumber()-1)*pageImpl.getPageSize()+1;
		String sql = "select m.*,(select count(distinct t.user_id) from ("+querySql+") t ) as total from (select n.*,rownum rn from ("+querySql+") n where rownum<="+after+") m  where rn>="+pre;
		List<XxkhUserPaper>  list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(XxkhUserPaper.class));
		pageImpl.setFlag("1");
		pageImpl.getData().setRows(list);
		if(list.size()>0){
			pageImpl.getData().setTotal(list.get(0).getTotal());
		}else{
			pageImpl.getData().setTotal(0);
		}
		return pageImpl;

	}
	
}
