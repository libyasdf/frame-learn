package com.key.dwsurvey.service.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.key.authority.common.util.AuthorityUtils;
import com.key.common.QuType;
import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.plugs.page.Page;
import com.key.common.service.BaseServiceImpl;
import com.key.common.utils.RandomUtils;
import com.key.dwsurvey.dao.SurveyDirectoryDao;
import com.key.dwsurvey.entity.SurveyDetail;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.entity.SurveyStats;
import com.key.dwsurvey.entity.TQuestion;
import com.key.dwsurvey.service.QuestionBankManager;
import com.key.dwsurvey.service.QuestionManager;
import com.key.dwsurvey.service.SurveyAnswerManager;
import com.key.dwsurvey.service.SurveyDetailManager;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import com.key.dwsurvey.service.SurveyStatsManager;
import com.key.dwsurvey.service.UserManager;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.user.entity.SessionUser;
import com.sinosoft.sinoep.user.util.UserUtil;


/**
 * 问卷
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service("surveyDirectoryManager")
public class SurveyDirectoryManagerImpl extends BaseServiceImpl<SurveyDirectory, String> implements SurveyDirectoryManager {

	@Autowired
	private SurveyDirectoryDao surveyDirectoryDao;
	@Autowired
	private SurveyDetailManager surveyDetailManager;
	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private SurveyStatsManager surveyStatsManager;
	@Autowired
	private SurveyAnswerManager surveyAnswerManager;
	@Autowired
	private AccountManager accountManager;
	@Autowired
	private QuestionBankManager questionBankManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	JdbcTemplate jdbcTemple;
	
	
	@Override
	public void setBaseDao() {
		this.baseDao=surveyDirectoryDao;
	}
	
	@Transactional
	@Override
	public void save(SurveyDirectory t) {
		SessionUser user = UserUtil.getSessionUser();
		String userId=t.getUserId();
		String id=t.getId();
		if(id==null || "".equals(id)){
			String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			t.setCreateDate1(creTime);
			t.setUserId(user.getUserId());
			userId=t.getUserId();
		} if("".equals(id)){
			t.setId(null);
			t.setUserName(user.getUserName());
		}
		
			String sId=t.getSid();
			if(sId==null || "".equals(sId)){
				sId=RandomUtils.randomStr(6, 12);
				t.setSid(sId);
			}
			surveyDirectoryDao.save(t);
			//保存SurveyDirectory
			if(t.getDirType()==2){
				SurveyDetail surveyDetailTemp=t.getSurveyDetail();
				
				SurveyDetail surveyDetail=surveyDetailManager.getBySurveyId(id);
				if(surveyDetail!=null){
					if(surveyDetailTemp!=null){
						surveyDetail.setSurveyNote(surveyDetailTemp.getSurveyNote());
					}
				}else{
					surveyDetail=new SurveyDetail();
					surveyDetail.setSurveyNote("非常感谢您的参与！");
				}
				surveyDetail.setDirId(t.getId());
				surveyDetailManager.save(surveyDetail);
			}
		
	}
	
	@Transactional
	public void saveByAdmin(SurveyDirectory t){
		String sId=t.getSid();
		if(sId==null || "".equals(sId)){
			sId=RandomUtils.randomStr(6, 12);
			t.setSid(sId);
		}
		surveyDirectoryDao.save(t);
	}
	
	/**
	 * 得到当前目录所在的目录位置
	 */
	public List<SurveyDirectory> findPath(SurveyDirectory surveyDirectory) {
		List<SurveyDirectory> resultList=new ArrayList<SurveyDirectory>();
		if(surveyDirectory!=null){
			List<SurveyDirectory> dirPathList=new ArrayList<SurveyDirectory>();
			dirPathList.add(surveyDirectory);
			String parentUuid=surveyDirectory.getParentId();
			while(parentUuid!=null && !"".equals(parentUuid)){
				SurveyDirectory surveyDirectory2=get(parentUuid);
				parentUuid=surveyDirectory2.getParentId();
				dirPathList.add(surveyDirectory2);
			}
			for (int i = dirPathList.size()-1; i >=0; i--) {
				resultList.add(dirPathList.get(i));
			}
		}
		return resultList;
	}

	@Override
	public SurveyDirectory getSurveyBySid(String sid) {
		Criterion criterion=Restrictions.eq("sid", sid);
		SurveyDirectory surveyDirectory = surveyDirectoryDao.findUnique(criterion);
		getSurveyDetail(surveyDirectory.getId(),surveyDirectory);
		return surveyDirectory;
	}
	
	/**
	 * 根据问卷ID获取一条问卷信息
	 * TODO 
	 * @author 李利广
	 * @Date 2018年11月7日 上午9:32:00
	 * @param id
	 * @return
	 */
	@Override
	public SurveyDirectory getSurvey(String id) {
		if(id==null || "".equals(id)){
			return new SurveyDirectory();
		}
		SurveyDirectory directory = (SurveyDirectory)surveyDirectoryDao.getSession().get(SurveyDirectory.class, id);
		getSurveyDetail(id,directory);
		return directory;
	}
	
	/**
	 * 问卷创建人导出统计结果
	 * TODO 
	 * @author 李利广
	 * @Date 2018年11月7日 上午9:28:35
	 * @param id
	 * @param userId
	 * @return
	 */
	public SurveyDirectory getSurveyByUser(String id,String userId) {
		SurveyDirectory directory = (SurveyDirectory)surveyDirectoryDao.getSession().get(SurveyDirectory.class, id);
		if(userId.equals(directory.getUserId())){
			getSurveyDetail(id,directory);
		    return directory;
		}
		return null;
	}

	/**
	 * 查询具体的问卷内容，并放到问卷directory实体中
	 * TODO 
	 * @author 李利广
	 * @Date 2018年11月7日 上午9:30:44
	 * @param id
	 * @param directory
	 */
	public void getSurveyDetail(String id,SurveyDirectory directory) {
		String surveyDetailId=directory.getSurveyDetailId();
		SurveyDetail surveyDetail=null;
		if(surveyDetailId!=null){
			surveyDetail = surveyDetailManager.getByDetailId(surveyDetailId);
		}
		if(surveyDetail==null){
			surveyDetail = surveyDetailManager.getBySurveyId(id);
		}
		if(surveyDetail==null){
			surveyDetail = new SurveyDetail();
		}
		directory.setSurveyDetail(surveyDetail);
	}
	
	@Override
	public void upSurveyData(SurveyDirectory entity) {
		List<TQuestion> questions=questionManager.findDetails(entity.getId(), "2");
		if(questions!=null){
			int anItemLeastNum=0;
			for (TQuestion question : questions) {
				QuType quType=question.getQuType();
				if(quType==QuType.ENUMQU){//枚举
					anItemLeastNum+=question.getParamInt01();
				}else if(quType==QuType.MULTIFILLBLANK){//组合填空
					anItemLeastNum+=question.getQuMultiFillblanks().size();
				}else if(quType==QuType.SCORE){//评分
					anItemLeastNum+=question.getQuScores().size();
				}else if(quType==QuType.CHENRADIO || quType==QuType.CHENCHECKBOX){//矩阵单选 矩阵多选
					anItemLeastNum+=question.getRows().size();
				}else if(quType==QuType.CHENFBK || quType==QuType.COMPCHENRADIO){//矩阵填空 复合矩阵单选
					anItemLeastNum+=question.getRows().size()*question.getColumns().size();
				}else{
					anItemLeastNum++;
				}
			}
			entity.setSurveyQuNum(questions.size());
			entity.setAnItemLeastNum(anItemLeastNum);
		}
	}
	
	@Override
	@Transactional
	public void executeSurvey(SurveyDirectory entity) {
		entity.setSurveyState(1);
		//计算可以回答的题量
		upSurveyData(entity);
		super.save(entity);
		//生成全局统计结果记录表
		
		SurveyStats surveyStats=new SurveyStats();
		surveyStats.setSurveyId(entity.getId());
		surveyStatsManager.save(surveyStats);
	}

	@Override
	@Transactional
	public void closeSurvey(SurveyDirectory entity) {
		entity.setSurveyState(2);
		//计算可以回答的题量
		super.save(entity);
		//生成全局统计结果记录表
	}
	
	@Override
	@Transactional
	public void delete(String id) {
		//设为不可见
		SurveyDirectory parentDirectory=get(id);
		parentDirectory.setVisibility(0);
		surveyDirectoryDao.save(parentDirectory);
		Criterion criterion=Restrictions.eq("parentId", parentDirectory.getId());
		List<SurveyDirectory> directories=findList(criterion);
		if(directories!=null){
			for (SurveyDirectory surveyDirectory : directories) {
				delete(surveyDirectory);
			}
		}
	}
	
	@Transactional
	public void delete(SurveyDirectory parentDirectory) {
		String id=parentDirectory.getId();
		//目录ID，为1的为系统默认注册用户目录不能删除
		if(!"1".equals(id)){
			//设为不可见
			parentDirectory.setVisibility(0);
			Criterion criterion=Restrictions.eq("parentId", parentDirectory.getId());
			List<SurveyDirectory> directories=findList(criterion);
			if(directories!=null){
				for (SurveyDirectory surveyDirectory : directories) {
					delete(surveyDirectory);
				}
			}
		}
	}

	@Override
	public SurveyDirectory findByNameUn(String id,String parentId, String surveyName) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		Criterion eqName=Restrictions.eq("surveyName", surveyName);
		Criterion eqParentId=Restrictions.eq("parentId", parentId);
		criterions.add(eqName);
		criterions.add(eqParentId);
		
		if(id!=null && !"".equals(id)){
			Criterion eqId=Restrictions.ne("id", id);	
			criterions.add(eqId);
		}
		return surveyDirectoryDao.findFirst(criterions);
	}
	@Override
	public SurveyDirectory findByNameUserUn(String id, String surveyName) {
		User user=accountManager.getCurUser();
		if(user!=null){
			List<Criterion> criterions=new ArrayList<Criterion>();
			Criterion eqName=Restrictions.eq("surveyName", surveyName);
			Criterion eqUserId=Restrictions.eq("userId", user.getId());
			criterions.add(eqName);
			criterions.add(eqUserId);
			
			if(id!=null && !"".equals(id)){
				Criterion eqId=Restrictions.ne("id", id);	
				criterions.add(eqId);
			}
			return surveyDirectoryDao.findFirst(criterions);
		}
		return null;
	}

	@Override
	@Transactional
	public void backDesign(SurveyDirectory entity) {
		entity.setSurveyState(0);
		//计算可以回答的题量
		super.save(entity);
	}
	
	@Transactional
	public void checkUp(SurveyDirectory entity) {
		//计算可以回答的题量
		super.save(entity);
	}
	
	
	@Transactional
	public void upSuveyText(SurveyDirectory t){
		String id=t.getId();
		if(id!=null&&id.length()>0){
			super.save(t);
			//保存SurveyDirectory
			if(t.getDirType()==2){
				SurveyDetail surveyDetail=t.getSurveyDetail();
				surveyDetail.setDirId(t.getId());
				surveyDetailManager.save(surveyDetail);
			}
		}
	}
	
	@Transactional
	public void saveUser(SurveyDirectory t) {
		super.save(t);
		//保存SurveyDirectory
		if(t.getDirType()==2){
			SurveyDetail surveyDetail=t.getSurveyDetail();
			surveyDetail.setDirId(t.getId());
			surveyDetailManager.save(surveyDetail);
		}
	}
	
	public void saveUserSurvey(SurveyDirectory entity) {
		User user = accountManager.getCurUser();
		if(user!=null){
			String enId = entity.getId();
			String userId=user.getId();
			if(enId==null || "".equals(enId)){
				//根据用户名得到目录
				String loginName=user.getLoginName();
				SurveyDirectory surveyDirUser=findByNameUn(null, "1", loginName);
				if(surveyDirUser==null){
					//没有此目录则创建此目录，你ID=1
					surveyDirUser=new SurveyDirectory();
					surveyDirUser.setSurveyName(loginName);
					surveyDirUser.setDirType(1);
					surveyDirUser.setUserId(user.getId());
					surveyDirUser.setParentId("1");
					saveUser(surveyDirUser);
				}
				entity.setParentId(surveyDirUser.getId());
				
				entity.setUserId(userId);
				saveUser(entity);
			}else{
				//判断当前人有无权限修改
				String enUserId=entity.getUserId();
				if(userId.equals(enUserId)){
					entity.setUserId(userId);
					saveUser(entity);
				}
			}
		}
	}

	@Override
	public Page<SurveyDirectory> findPage(Page<SurveyDirectory> page,
			SurveyDirectory entity) {
		page.setOrderBy("createDate");
		page.setOrderDir("desc");
		
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("visibility", 1));
		criterions.add(Restrictions.eq("surveyState", 1));
		
		criterions.add(Restrictions.eq("dirType", 2));
		criterions.add(Restrictions.eq("surveyModel", 1));
		
		Integer share = entity.getIsShare();
		if(share!=null && share==1){
			criterions.add(Restrictions.eq("isShare", share));
		}

		return surveyDirectoryDao.findPageList(page, criterions);
	}
	

	public List<SurveyDirectory> newSurveyList() {
		List<SurveyDirectory> result=new ArrayList<SurveyDirectory>();
		try{
			SurveyDirectory entity=new SurveyDirectory();
			Page<SurveyDirectory> page=new Page<SurveyDirectory>();
			page.setPageSize(25);
			page=findPage(page,entity);
			result=page.getResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	@Transactional
	public void saveAll(SurveyDirectory directory) {
		List<TQuestion> questions=null;//directory.getQuestions();
		directory.setDirType(2);
		directory.setParentId("402880e5428a2dca01428a2f1f290000");
		directory.setSurveyTag(0);
		directory.setSurveyQuNum(questions.size());
		surveyDirectoryDao.save(directory);
		String surveyId=directory.getId();
		//详细信息
		SurveyDetail detail=directory.getSurveyDetail();
		detail.setDirId(surveyId);
		surveyDetailManager.save(detail);
//		directory.setSurveyDetailId(detail.getId());
		//题目列表
		for (TQuestion question : questions) {
			if(question!=null){
				question.setBelongId(surveyId);
				question.setCreateDate(directory.getCreateDate());
				question.setTag(2);
				questionManager.save(question);
			}
		}
	}

	@Override
	public SurveyDirectory findNext(SurveyDirectory directory) {
		Date date=directory.getCreateDate();
		Criterion criterion=Restrictions.gt("createDate", date);
		return surveyDirectoryDao.findFirst(criterion);
	}
	
	@Override
	public Page<SurveyDirectory> findByUser(Page<SurveyDirectory> page,
											SurveyDirectory entity) {
	    User user=accountManager.getCurUser();
	    if(user!=null){
			List<Criterion> criterions=new ArrayList<Criterion>();

			criterions.add(Restrictions.eq("userId", user.getId()));
			criterions.add(Restrictions.eq("visibility", 1));
			criterions.add(Restrictions.eq("dirType", 2));
			criterions.add(Restrictions.eq("surveyModel", 1));

			if(entity!=null){
				Integer surveyState = entity.getSurveyState();
				if(surveyState!=null && !"".equals(surveyState)){
					criterions.add(Restrictions.eq("surveyState", surveyState));
				}
				String surveyName = entity.getSurveyName();
				if(surveyName!=null && !"".equals(surveyName)){
					criterions.add(Restrictions.like("surveyName", "%"+surveyName+"%"));
				}
			}

			page.setOrderBy("createDate");
			page.setOrderDir("desc");
			page=surveyDirectoryDao.findPageList(page,criterions);
	    }
	    return page;
	}
	
	public Page<SurveyDirectory> findByGroup(String groupId1,String groupId2,Page<SurveyDirectory> page) {
		
		
		List<Criterion> criterions = new ArrayList<Criterion>();
		if(groupId1!=null && !"".equals(groupId1)){
			Criterion cri1=Restrictions.eq("groupId1", groupId1);
			criterions.add(cri1);
		}
		if(groupId2!=null && !"".equals(groupId2)){
			Criterion cri1_2=Restrictions.eq("groupId2", groupId2);
			criterions.add(cri1_2);
		}
	    
	    Criterion cri2=Restrictions.eq("visibility", 1);
	    Criterion cri4=Restrictions.eq("surveyModel", 4);

	    criterions.add(cri2);
	    criterions.add(cri4);
	    page.setOrderBy("createDate");
		page.setOrderDir("desc");
		
	    return surveyDirectoryDao.findPage(page,criterions.toArray(new Criterion[criterions.size()]) );
	}

	
	@Override
	public Page<SurveyDirectory> findModel(Page<SurveyDirectory> page,
			SurveyDirectory entity) {
		Integer surveyState=entity.getSurveyState();
		String surveyName=entity.getSurveyName();
		List<Criterion> criterions=new ArrayList<Criterion>();
		
		if(surveyState!=null && surveyState.intValue()!=100){
			Criterion cri1=Restrictions.eq("surveyState", surveyState);
			criterions.add(cri1);
		}
		if(surveyName!=null && !"".equals(surveyName)){
			Criterion cri1=Restrictions.like("surveyName", "%"+surveyName+"%");
			criterions.add(cri1);
		}
	    Criterion cri2=Restrictions.eq("visibility", 1);
	    criterions.add(cri2);
	    Criterion cri4=Restrictions.eq("surveyModel", 4);
	    criterions.add(cri4);
	    page.setOrderBy("createDate");
		page.setOrderDir("desc");
	    return surveyDirectoryDao.findPageList(page,criterions);
	}
	
	@Override
	public List<SurveyDirectory> findByIndex() {
	    Criterion cri1=Restrictions.eq("visibility", 1);
	    Criterion cri2=Restrictions.eq("parentId", "402880e5428a2dca01428a2f1f290000");
	    Criterion cri3=Restrictions.eq("surveyTag", 1);
	    Criterion cri4=Restrictions.isNull("sid");
	    Page<SurveyDirectory> page=new Page<SurveyDirectory>();
	    page.setOrderBy("createDate");
		page.setOrderDir("desc");
		page.setPageSize(10);
	    List<SurveyDirectory> surveys = surveyDirectoryDao.findPage(page, cri1,cri2,cri3,cri4).getResult();
	    return surveys;
	}
	
	@Override
	public List<SurveyDirectory> findByT1() {
	    Criterion cri1=Restrictions.eq("visibility", 1);
	    Criterion cri2=Restrictions.eq("parentId", "402880e5428a2dca01428a2f1f290000");
	    Criterion cri3=Restrictions.eq("surveyTag", 1);
	    Criterion cri4=Restrictions.isNull("sid");
	    Page<SurveyDirectory> page=new Page<SurveyDirectory>();
	    page.setOrderBy("createDate");
		page.setOrderDir("desc");
		page.setPageSize(10);
	    List<SurveyDirectory> surveys = surveyDirectoryDao.findPage(page, cri1,cri2,cri3,cri4).getResult();
	    return surveys;
	}

	@Override
	public SurveyDirectory createBySurvey(String fromBankId, String surveyName,
										  String tag) {//new
		SurveyDirectory surveyDirectory = buildCopyObj(fromBankId, surveyName,
				tag);

		saveUserSurvey(surveyDirectory);
		String belongId=surveyDirectory.getId();
		List<TQuestion> questions=questionManager.find(fromBankId, tag);
		questionManager.saveBySurvey(belongId, 2 , questions);
		return surveyDirectory;
	}

	private SurveyDirectory buildCopyObj(String fromBankId, String surveyName,String tag) {
		SurveyDirectory surveyDirectory=new SurveyDirectory();
		surveyDirectory.setSurveyName(surveyName);
		surveyDirectory.setDirType(2);
		surveyDirectory.setSurveyDetail(new SurveyDetail());
		SurveyDirectory directory=getSurvey(fromBankId);
		directory.setExcerptNum(directory.getExcerptNum()+1);
		super.save(directory);
		surveyDirectory.setSurveyQuNum(directory.getSurveyQuNum());
		surveyDirectory.getSurveyDetail().setSurveyNote(surveyDirectory.getSurveyDetail().getSurveyNote());
		return surveyDirectory;
	}


	/**
	 * 分页列表
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年9月28日 下午3:55:35
	 * @param pageable
	 * @param pageImpl
	 * @param wenjuanType
	 * @param surveyState
	 * @param startDate
	 * @param endDate
	 * @param surveyName
	 * @return
	 */
	@Override
	public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, String wenjuanType,Integer surveyState,String startDate,String endDate,String surveyName) {
		//总个数
		StringBuilder sb = new StringBuilder("select count(1) from (");
		List<Object> params = querySql(sb,wenjuanType,surveyState,startDate,endDate,surveyName);
		sb.append(")");
		Integer total=jdbcTemple.queryForObject(sb.toString(), Integer.class,params.toArray());
		//查询分页数据
		String pageSql = pageSql(pageable,wenjuanType,surveyState,startDate,endDate,surveyName,params);
		List<SurveyDirectory> listData =jdbcTemple.query(pageSql.toString(), new RowMapper<SurveyDirectory>(){
			@Override
			public SurveyDirectory mapRow(ResultSet rs, int arg1) throws SQLException {
				SurveyDirectory surveyDirectory = new SurveyDirectory();
				surveyDirectory.setId(rs.getString("id"));
				surveyDirectory.setAnItemLeastNum(rs.getInt("AN_ITEM_LEAST_NUM"));
				surveyDirectory.setAnswerNum(rs.getInt("ANSWER_NUM"));
				surveyDirectory.setCreateDate(rs.getDate("CREATE_DATE"));
				surveyDirectory.setDirType(rs.getInt("DIR_TYPE"));
				surveyDirectory.setExcerptNum(rs.getInt("EXCERPT_NUM"));
				surveyDirectory.setHtmlPath(rs.getString("HTML_PATH"));
				surveyDirectory.setIsShare(rs.getInt("IS_SHARE"));
				surveyDirectory.setParentId(rs.getString("PARENT_ID"));
				surveyDirectory.setSid(rs.getString("SID"));
				surveyDirectory.setSurveyDetailId(rs.getString("SURVEY_DETAIL_ID"));
				surveyDirectory.setSurveyModel(rs.getInt("SURVEY_MODEL"));
				//surveyDirectory.setSurveyModel(rs.getInt("SURVEY_NAME"));
				surveyDirectory.setSurveyName(rs.getString("SURVEY_NAME"));
				surveyDirectory.setSurveyQuNum(rs.getInt("SURVEY_QU_NUM"));
				surveyDirectory.setSurveyState(rs.getInt("SURVEY_STATE"));
				surveyDirectory.setSurveyTag(rs.getInt("SURVEY_TAG"));
				surveyDirectory.setUserId(rs.getString("USER_ID"));
				surveyDirectory.setViewAnswer(rs.getInt("VIEW_ANSWER"));
				surveyDirectory.setVisibility(rs.getInt("VISIBILITY"));
				surveyDirectory.setWenjuanType(rs.getString("WENJUAN_TYPE"));
				surveyDirectory.setUserName(rs.getString("USER_NAME"));
				return surveyDirectory;
			}
				
		},params.toArray());
		for (SurveyDirectory surveyDirectory : listData) {
			surveyDirectory.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE+","+"tongji");
		}
		
		return new PageImpl(total,listData);
		
		
	}
	
	/**
	 * 分页的查询语句
	 * TODO 
	 * @author 马秋霞
	 * @Date 2017-11-8 下午06:21:44
	 * @param pageable
	 * @param startDate
	 * @param endDate
	 * @param name
	 * @return
	 */
	private String pageSql(Pageable pageable, String wenjuanType, Integer surveyState, String startDate, String endDate,
			String surveyNam,List<Object> params) {
		
		StringBuilder sb=new StringBuilder("select * from ( select a1.*,ROWNUM RN from (");
		querySql(sb,wenjuanType,surveyState,startDate,endDate,surveyNam);
		sb.append("  ) a1 where ROWNUM <= ? ) where RN >=  ?");
		params.add(pageable.getOffset()+pageable.getPageSize());
		params.add(pageable.getOffset()+1);
		return sb.toString();
	}
	private List<Object>   querySql(StringBuilder sb, String wenjuanType, Integer surveyState, String startDate, String endDate,
			String surveyName) {
		List<Object> params = new ArrayList<Object>();
		sb.append("select t.* from t_survey_directory t where t.visibility='1'  ");
		if(StringUtils.isNotBlank(wenjuanType)){
			sb.append("  and WENJUAN_TYPE= ? ");
			params.add(wenjuanType);
		}
		if(surveyState!=null && !"".equals(surveyState)){
			sb.append(" and SURVEY_STATE= ? ");
			params.add(surveyState);
		}
		if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
			sb.append(" and TO_CHAR(t.CREATE_DATE, 'YYYY-MM-DD')>= ?  and TO_CHAR(t.CREATE_DATE, 'YYYY-MM-DD')<= ? ");
			params.add(startDate);
			params.add(endDate);
		}
		if(StringUtils.isNotBlank(surveyName) && StringUtils.isNotBlank(surveyName)){
			sb.append(" and SURVEY_NAME like ? ");
			params.add("%" + surveyName + "%");
		}
		sb.append(" order by CREATE_DATE desc ");
		return params;
	}

	@Override
	public SurveyDirectory findById(String surveyId) {
		
		StringBuilder sb = new StringBuilder("select t.* from t_survey_directory t where t.id= ? ");
		
		return jdbcTemple.queryForObject(sb.toString(), new RowMapper<SurveyDirectory>(){

			@Override
			public SurveyDirectory mapRow(ResultSet rs, int i) throws SQLException {
				SurveyDirectory surveyDirectory = new SurveyDirectory();
				surveyDirectory.setId(rs.getString("id"));
				surveyDirectory.setAnItemLeastNum(rs.getInt("AN_ITEM_LEAST_NUM"));
				surveyDirectory.setAnswerNum(rs.getInt("ANSWER_NUM"));
				surveyDirectory.setCreateDate(rs.getDate("CREATE_DATE"));
				surveyDirectory.setDirType(rs.getInt("DIR_TYPE"));
				surveyDirectory.setExcerptNum(rs.getInt("EXCERPT_NUM"));
				surveyDirectory.setHtmlPath(rs.getString("HTML_PATH"));
				surveyDirectory.setIsShare(rs.getInt("IS_SHARE"));
				surveyDirectory.setParentId(rs.getString("PARENT_ID"));
				surveyDirectory.setSid(rs.getString("SID"));
				surveyDirectory.setSurveyDetailId(rs.getString("SURVEY_DETAIL_ID"));
				surveyDirectory.setSurveyModel(rs.getInt("SURVEY_MODEL"));
				//surveyDirectory.setSurveyModel(rs.getInt("SURVEY_NAME"));
				surveyDirectory.setSurveyName(rs.getString("SURVEY_NAME"));
				surveyDirectory.setSurveyQuNum(rs.getInt("SURVEY_QU_NUM"));
				surveyDirectory.setSurveyState(rs.getInt("SURVEY_STATE"));
				surveyDirectory.setSurveyTag(rs.getInt("SURVEY_TAG"));
				surveyDirectory.setUserId(rs.getString("USER_ID"));
				surveyDirectory.setViewAnswer(rs.getInt("VIEW_ANSWER"));
				surveyDirectory.setVisibility(rs.getInt("VISIBILITY"));
				surveyDirectory.setWenjuanType(rs.getString("WENJUAN_TYPE"));
				surveyDirectory.setUserName(rs.getString("USER_NAME"));
				return surveyDirectory;
			}
			
		},surveyId);
	}

	@Override
	public void deleteById(String id) {
		StringBuilder sb = new StringBuilder("update t_survey_directory set visibility=0 where id=?");
		jdbcTemple.update(sb.toString(),id);
		
	}

	/**
	 * 查询当前用户收到的调查问卷
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月27日 下午9:25:38
	 * @param pageImpl
	 * @param type
	 * @param timeRange
	 * @return
	 */
	@Override
	public PageImpl getSurveyList(PageImpl pageImpl,String surveyName, String type, String startDate, String endDate) throws Exception {
		Page<SurveyDirectory> page = new Page<SurveyDirectory>();
		page.setPageNo(pageImpl.getPageNumber());
		page.setPageSize(pageImpl.getPageSize());
		//获取当前用户权限内的问卷ID
		List<String> authList = AuthorityUtils.getAuthList();
		if (authList != null && !authList.isEmpty()) {
			List<Criterion> criterions=new ArrayList<Criterion>();
			criterions.add(Restrictions.in("id", authList));	//id在权限范围内
			criterions.add(Restrictions.eq("visibility", 1));	//逻辑删除
			criterions.add(Restrictions.eq("dirType", 2));		//1目录；2问卷
			criterions.add(Restrictions.eq("surveyModel", 1));	//1问卷模块
			if(type!=null && !"".equals(type)){
				criterions.add(Restrictions.eq("wenjuanType",type));	//调查问卷类型
			}
			if (StringUtils.isNotBlank(surveyName)) {
				criterions.add(Restrictions.like("surveyName","%"+surveyName+"%"));	//调查问卷名称
			}
			criterions.add(Restrictions.eq("surveyState",1));	//发布状态
			if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
				criterions.add(Restrictions.between("createDate", DateUtil.getTextDate(startDate, "yyyy-MM-dd"), DateUtil.getTextDate(endDate, "yyyy-MM-dd")));
			}
			if (StringUtils.isNotBlank(pageImpl.getSortName())) {
				page.setOrderBy(pageImpl.getSortName());
				page.setOrderDir(pageImpl.getSortOrder());
			}else{
				page.setOrderBy("createDate");
				page.setOrderDir("desc");
			}
			page=surveyDirectoryDao.findPageList(page,criterions);
			List<SurveyDirectory> content = page.getResult();
			pageImpl.getData().setTotal(content.size());
			pageImpl.getData().setRows(content);
		}
		return pageImpl;
	}

	@Override
	public void stopCollect(String id) {
		StringBuilder sb = new StringBuilder("update t_survey_directory set SURVEY_STATE=2 where id=?");
		jdbcTemple.update(sb.toString(),id);
		
	}

	@Override
	public void startCollect(String id) {
		StringBuilder sb = new StringBuilder("update t_survey_directory set SURVEY_STATE=1 where id=?");
		jdbcTemple.update(sb.toString(),id);
	}

	/**
	 * 判断当前用户是否已经提交过
	 * TODO 
	 * @author 李利广
	 * @Date 2018年9月28日 上午11:14:59
	 * @param surveyId
	 * @return
	 */
	@Override
	public boolean getIsComplete(String surveyId) throws Exception {
		StringBuilder sbBuilder = new StringBuilder();
		sbBuilder.append("select count(1) from t_survey_answer t where t.user_id = '"+UserUtil.getCruUserId()+"' and t.survey_id = ? and t.is_complete = '1'");
		Integer count = jdbcTemple.queryForObject(sbBuilder.toString(), Integer.class,surveyId);
		return count > 0?true:false;
	}

}
