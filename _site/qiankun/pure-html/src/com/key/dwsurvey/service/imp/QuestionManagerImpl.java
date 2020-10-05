package com.key.dwsurvey.service.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.key.common.CheckType;
import com.key.common.QuType;
import com.key.common.YesnoOption;
import com.key.common.plugs.page.Page;
import com.key.common.plugs.page.PropertyFilter;
import com.key.common.service.BaseServiceImpl;
import com.key.common.utils.ReflectionUtils;
import com.key.dwsurvey.dao.TQuestionDao;
import com.key.dwsurvey.entity.QuCheckbox;
import com.key.dwsurvey.entity.QuChenColumn;
import com.key.dwsurvey.entity.QuChenOption;
import com.key.dwsurvey.entity.QuChenRow;
import com.key.dwsurvey.entity.QuMultiFillblank;
import com.key.dwsurvey.entity.QuOrderby;
import com.key.dwsurvey.entity.QuRadio;
import com.key.dwsurvey.entity.QuScore;
import com.key.dwsurvey.entity.QuestionLogic;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.entity.TQuestion;
import com.key.dwsurvey.service.QuCheckboxManager;
import com.key.dwsurvey.service.QuChenColumnManager;
import com.key.dwsurvey.service.QuChenOptionManager;
import com.key.dwsurvey.service.QuChenRowManager;
import com.key.dwsurvey.service.QuMultiFillblankManager;
import com.key.dwsurvey.service.QuOrderbyManager;
import com.key.dwsurvey.service.QuRadioManager;
import com.key.dwsurvey.service.QuScoreManager;
import com.key.dwsurvey.service.QuestionLogicManager;
import com.key.dwsurvey.service.QuestionManager;
import com.sinosoft.sinoep.common.util.CommonUtils;


/**
 * 基础题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service("questionManager")
public class QuestionManagerImpl extends BaseServiceImpl<TQuestion, String> implements QuestionManager{

	@Autowired
	private TQuestionDao questionDao;
	@Autowired
	private QuCheckboxManager quCheckboxManager;
	@Autowired
	private QuRadioManager quRadioManager;
	@Autowired
	private QuMultiFillblankManager quMultiFillblankManager;
	@Autowired
	private QuChenRowManager quChenRowManager;
	@Autowired
	private QuChenColumnManager quChenColumnManager;
	@Autowired
	private QuChenOptionManager quChenOptionManager;
	@Autowired
	private QuScoreManager quScoreManager;
	@Autowired
	private QuOrderbyManager quOrderbyManager;
	@Autowired
	private QuestionLogicManager questionLogicManager;
	@Autowired
	JdbcTemplate jdbcTemple;
	
	@Override
	public void setBaseDao() {
		this.baseDao=questionDao;
	}

	
	/**
	 * 所有修改，新增题的入口 方法
	 * @param question
	 */
	@Transactional
	@Override
	public void save(TQuestion question){
//		User user=accountManager.getCurUser();
//		if(user!=null){
			String uuid=question.getId();
			if(uuid==null || "".equals(uuid)){
				question.setId(null);
			}
//			question.setUserUuid(user.getId());
//			System.out.println("getRows:"+question.getRows().size());
			questionDao.save(question);
//		}
	}
	
	
	/**************************************************************************/
	/**
	 * 依据条件得到符合条件的题列表，不包含选项信息   用于列表显示
	 * @param qubankId
	 * @param tag  1题库  2问卷
	 * @return
	 */
	public List<TQuestion> find(String belongId,String tag){
		Page<TQuestion> page=new Page<TQuestion>();
		page.setOrderBy("orderById");
		page.setOrderDir("asc");
		
		List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_belongId", belongId));
		filters.add(new PropertyFilter("EQI_tag", tag));
		filters.add(new PropertyFilter("NEI_quTag", "3"));
		return findAll(page, filters);
	}
	
	/**
	 * 查出指定条件下的所有题，及每一题内容的选项   用于展示试卷,如预览,答卷,查看
	 * @param qubankId
	 * @param tag
	 * @return
	 */
	public List<TQuestion> findDetails(String belongId,String tag){
		List<TQuestion> questions=find(belongId, tag);
		for (TQuestion question : questions) {
			getQuestionOption(question);
		}
		return questions;
	}
	/**
	 * 得到某一题下面的选项，包含大题下面的小题
	 * @param question
	 */
	public void getQuestionOption(TQuestion question) {
		String quId=question.getId();
		QuType quType=question.getQuType();
		if(quType==QuType.RADIO || quType==QuType.COMPRADIO){
			question.setQuRadios(quRadioManager.findByQuId(quId));
		}else if(quType==QuType.CHECKBOX || quType==QuType.COMPCHECKBOX){
			question.setQuCheckboxs(quCheckboxManager.findByQuId(quId));
		}else if(quType==QuType.MULTIFILLBLANK){
			question.setQuMultiFillblanks(quMultiFillblankManager.findByQuId(quId));
		}else if(quType==QuType.BIGQU){
			//根据大题ID，找出所有小题
			String parentQuId=question.getId();
			List<TQuestion> childQuList=findByParentQuId(parentQuId);
			for (TQuestion childQu : childQuList) {
				getQuestionOption(childQu);
			}
			question.setQuestions(childQuList);
			//根据小题的类型，取选项
		}else if(quType==QuType.CHENRADIO || quType==QuType.CHENCHECKBOX   || quType==QuType.CHENSCORE || quType==QuType.CHENFBK || quType==QuType.COMPCHENRADIO){//矩阵单选，矩阵多选，矩阵填空题，复合矩阵单选
			List<QuChenRow> rows = quChenRowManager.findByQuId(quId);
			List<QuChenColumn> columns = quChenColumnManager.findByQuId(quId);
			question.setRows(rows);
			question.setColumns(columns);
			
			if(quType==QuType.COMPCHENRADIO){//如果是复合矩阵单选题， 则还有题选项
				List<QuChenOption> options = quChenOptionManager.findByQuId(quId);
				question.setOptions(options);
			}
		}else if(quType==QuType.SCORE){
		 List<QuScore>	quScores=quScoreManager.findByQuId(quId);
		 question.setQuScores(quScores);
		}else if(quType==QuType.ORDERQU){
			 List<QuOrderby>	quOrderbys=quOrderbyManager.findByQuId(quId);
			 question.setQuOrderbys(quOrderbys);
		}
		List<QuestionLogic> questionLogics=questionLogicManager.findByCkQuId(quId);
		question.setQuestionLogics(questionLogics);
	}
	
	public List<TQuestion> findByParentQuId(String parentQuId){
		List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_parentQuUuId", parentQuId));
		return findList(filters);
	}
	/**
	 * 根据ID，得到一批题
	 * @param quIds
	 * @param b  表示是否提出每一题的详细选项信息
	 * @return
	 */
	public List<TQuestion> findByQuIds(String[] quIds, boolean b) {
		List<TQuestion> questions=new ArrayList<TQuestion>();
		List<Object> param = new ArrayList<Object>();
		if(quIds==null || quIds.length<=0){
			return questions;
		}
		StringBuffer hqlBuf=new StringBuffer("from Question qu where qu.id in(");
		StringBuilder quIdsb= new StringBuilder();
		for (String quId : quIds) {
			quIdsb.append(quId + ",");
			//hqlBuf.append("'"+quId+"'").append(",");	
		}
		//String hql=hqlBuf.substring(0, hqlBuf.lastIndexOf(","))+")";
		quIdsb.deleteCharAt(quIdsb.length()-1);
		hqlBuf.append(CommonUtils.solveSqlInjectionOfIn(quIdsb.toString(),param)+")");
	
		questions=questionDao.find(hqlBuf.toString(),param.toArray());
		if(b){
			for (TQuestion question : questions) {
				getQuestionOption(question);
			}
		}
		return questions;
	}
	
	/**
	 * 批量删除题，及题包含的选项一同删除-真删除。
	 * @param delQuIds
	 */
	@Transactional
	public void deletes(String[] delQuIds) {
		if(delQuIds!=null){
			for (String quId : delQuIds) {
				delete(quId);
			}	
		}
	}
	
	@Transactional
	public void delete(String quId){
		if(quId!=null && !"".equals(quId)){
			TQuestion question = (TQuestion) questionDao.getSession().get(TQuestion.class, quId);
//			TQuestion question=get(quId);
			//同时删除掉相应的选项
			if(question!=null){
//				QuType quType=question.getQuType();
				String belongId = question.getBelongId();
				int orderById= question.getOrderById();
				questionDao.delete(question);
				//更新ID
				questionDao.quOrderByIdDel1(belongId,orderById);
			}
		}
	}
	
	/**
	 * 题排序
	 * @param prevId
	 * @param nextId
	 */
	@Transactional
	public boolean upsort(String prevId, String nextId) {
		if(prevId!=null && !"".equals(prevId) && nextId!=null && !"".equals(nextId)){
			TQuestion prevQuestion=get(prevId);
			TQuestion nextQuestion=get(nextId);
			int prevNum=prevQuestion.getOrderById();
			int nextNum=nextQuestion.getOrderById();
			
			prevQuestion.setOrderById(nextNum);
			nextQuestion.setOrderById(prevNum);
			
			save(prevQuestion);
			save(nextQuestion);
			return true;
		}
		return false;
	}
	
	public TQuestion findUnById(String id){
		return questionDao.findUniqueBy("id", id);
	}
	public List<TQuestion> findByparentQuId(String parentQuUuId){
		List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_parentQuUuId", parentQuUuId));
		return findList(filters);
	}
	
	public void saveBySurvey(String belongId  ,int tag, List<TQuestion> questions) {
		for (TQuestion question : questions) {
			copyQu(belongId, tag, question);
		}
	}
	/**
	 * 保存选中的题目 即从题库或从其它试卷中的题
	 */
	@Transactional
	public void saveChangeQu(String belongId,int tag, String[] quIds) {
		for (String quId : quIds) {
			TQuestion changeQuestion=findUnById(quId);
			copyQu(belongId, tag, changeQuestion);
		}
	}

	private void copyQu(String belongId, int tag, TQuestion changeQuestion) {
		String quId=changeQuestion.getId();
		if(changeQuestion.getQuType()==QuType.BIGQU){
			TQuestion question=new TQuestion();
			ReflectionUtils.copyAttr(changeQuestion,question);
			//设置相关要改变的值
			question.setId(null);
			question.setBelongId(belongId);
			question.setCreateDate(new Date());
			question.setTag(tag);
			question.setQuTag(2);
			question.setCopyFromId(quId);
			
			List<TQuestion> changeChildQuestions=findByparentQuId(quId);
			List<TQuestion> qulits=new ArrayList<TQuestion>();
			for (TQuestion changeQu : changeChildQuestions) {
				TQuestion question2=new TQuestion();
				ReflectionUtils.copyAttr(changeQu,question2);
				//设置相关要改变的值
				question2.setId(null);
				question2.setBelongId(belongId);
				question2.setCreateDate(new Date());
				question2.setTag(tag);
				question2.setQuTag(3);
				question2.setCopyFromId(changeQu.getId());
				
				getQuestionOption(changeQu);
				copyItems(belongId,changeQu, question2);
				
				qulits.add(question2);
			}
			question.setQuestions(qulits);
			save(question);
		}else{
			copyroot(belongId, tag, changeQuestion);	
		}
	}
	private void copyroot(String belongId,Integer tag, TQuestion changeQuestion) {
		//拷贝先中的问题属性值到新对象中
		TQuestion question=new TQuestion();
		ReflectionUtils.copyAttr(changeQuestion,question);
		//设置相关要改变的值
		question.setId(null);
		question.setBelongId(belongId);
		question.setCreateDate(new Date());
		question.setTag(tag);
		question.setCopyFromId(changeQuestion.getId());
		
		getQuestionOption(changeQuestion);
		copyItems(belongId,changeQuestion, question);
		save(question);
	}
	private void copyItems(String quBankUuid,TQuestion changeQuestion, TQuestion question) {
		QuType quType=changeQuestion.getQuType();
		if(quType==QuType.RADIO || quType==QuType.COMPRADIO){
			List<QuRadio> changeQuRadios=changeQuestion.getQuRadios();
			List<QuRadio> quRadios=new ArrayList<QuRadio>();
			for (QuRadio changeQuRadio : changeQuRadios) {
				QuRadio quRadio=new QuRadio();
				ReflectionUtils.copyAttr(changeQuRadio,quRadio);
				quRadio.setId(null);
				quRadios.add(quRadio);
			}
			question.setQuRadios(quRadios);
		}else if(quType==QuType.CHECKBOX || quType==QuType.COMPCHECKBOX){
			List<QuCheckbox> changeQuCheckboxs=changeQuestion.getQuCheckboxs();
			List<QuCheckbox> quCheckboxs=new ArrayList<QuCheckbox>();
			for (QuCheckbox changeQuCheckbox : changeQuCheckboxs) {
				QuCheckbox quCheckbox=new QuCheckbox();
				ReflectionUtils.copyAttr(changeQuCheckbox,quCheckbox);
				quCheckbox.setId(null);
				quCheckboxs.add(quCheckbox);
			}
			question.setQuCheckboxs(quCheckboxs);
		}else if(quType==QuType.MULTIFILLBLANK){
			List<QuMultiFillblank> changeQuDFillbanks=changeQuestion.getQuMultiFillblanks();
			List<QuMultiFillblank> quDFillbanks=new ArrayList<QuMultiFillblank>();
			for (QuMultiFillblank changeQuDFillbank : changeQuDFillbanks) {
				QuMultiFillblank quDFillbank=new QuMultiFillblank();
				ReflectionUtils.copyAttr(changeQuDFillbank,quDFillbank);
				quDFillbank.setId(null);
				quDFillbanks.add(quDFillbank);
			}
			question.setQuMultiFillblanks(quDFillbanks);
		}else if(quType==QuType.SCORE){
			//评分
			List<QuScore> changeQuScores=changeQuestion.getQuScores();
			List<QuScore> quScores=new ArrayList<QuScore>();
			for (QuScore changeQuScore : changeQuScores) {
				QuScore quScore=new QuScore();
				ReflectionUtils.copyAttr(changeQuScore, quScore);
				quScore.setId(null);
				quScores.add(quScore);
			}
			question.setQuScores(quScores);
		}else if(quType==QuType.CHENRADIO || quType==QuType.CHENCHECKBOX || quType==QuType.CHENFBK  || quType==QuType.COMPCHENRADIO){
			List<QuChenRow> changeRows=changeQuestion.getRows();
			List<QuChenColumn> changeColumns=changeQuestion.getColumns();
			List<QuChenRow> rows=new ArrayList<QuChenRow>();
			List<QuChenColumn> columns=new ArrayList<QuChenColumn>();
			for (QuChenRow changeRow : changeRows) {
				QuChenRow quChenRow=new QuChenRow();
				ReflectionUtils.copyAttr(changeRow, quChenRow);
				quChenRow.setId(null);
				rows.add(quChenRow);
			}
			question.setRows(rows);
			
			for (QuChenColumn changeColumn : changeColumns) {
				QuChenColumn quChenColumn=new QuChenColumn();
				ReflectionUtils.copyAttr(changeColumn, quChenColumn);
				quChenColumn.setId(null);
				columns.add(quChenColumn);
			}
			question.setColumns(columns);
			
			if(quType==QuType.COMPCHENRADIO){
				List<QuChenOption> changeOptions=changeQuestion.getOptions();
				List<QuChenOption> options=new ArrayList<QuChenOption>();
				for (QuChenOption changeOption : changeOptions) {
					QuChenOption quChenColumn=new QuChenOption();
					ReflectionUtils.copyAttr(changeOption, quChenColumn);
					quChenColumn.setId(null);
					options.add(quChenColumn);
				}
				question.setOptions(options);
			}
			
		}
		
	}


	@Override
	public List<TQuestion> findStatsRowVarQus(SurveyDirectory survey) {
		Criterion criterion1=Restrictions.eq("belongId", survey.getId());
		Criterion criterion2=Restrictions.eq("tag", 2);
		
//		Criterion criterion31=Restrictions.ne("quType", QuType.FILLBLANK);
//		Criterion criterion32=Restrictions.ne("quType", QuType.MULTIFILLBLANK);
//		Criterion criterion33=Restrictions.ne("quType", QuType.ANSWER);
//		
////		Criterion criterion3=Restrictions.or(criterion31, criterion32);
//		//where s=2 and (fds !=1 or fds!=2 )
//		return questionDao.find(criterion1,criterion2,criterion31,criterion32,criterion33);
		
		Criterion criterion31=Restrictions.ne("quType", QuType.FILLBLANK);
		Criterion criterion32=Restrictions.ne("quType", QuType.MULTIFILLBLANK);
		Criterion criterion33=Restrictions.ne("quType", QuType.ANSWER);
		Criterion criterion34=Restrictions.ne("quType", QuType.CHENCHECKBOX);
		Criterion criterion35=Restrictions.ne("quType", QuType.CHENFBK);
		Criterion criterion36=Restrictions.ne("quType", QuType.CHENRADIO);
		Criterion criterion37=Restrictions.ne("quType", QuType.ENUMQU);
		Criterion criterion38=Restrictions.ne("quType", QuType.ORDERQU);
		Criterion criterion39=Restrictions.ne("quType", QuType.SCORE);
		
		return questionDao.find(criterion1,criterion2,criterion31,criterion32,criterion33,criterion34,criterion35,criterion36,criterion37,criterion38,criterion39);
//		return null;
	}


	@Override
	public List<TQuestion> findStatsColVarQus(SurveyDirectory survey) {	
		Criterion criterion1=Restrictions.eq("belongId", survey.getId());
		Criterion criterion2=Restrictions.eq("tag", 2);
		
//		Criterion criterion31=Restrictions.ne("quType", QuType.FILLBLANK);
//		Criterion criterion32=Restrictions.ne("quType", QuType.MULTIFILLBLANK);
//		Criterion criterion33=Restrictions.ne("quType", QuType.ANSWER);
//		
////		Criterion criterion3=Restrictions.or(criterion31, criterion32);
//		//where s=2 and (fds !=1 or fds!=2 )
//		return questionDao.find(criterion1,criterion2,criterion31,criterion32,criterion33);
		
		Criterion criterion31=Restrictions.ne("quType", QuType.FILLBLANK);
		Criterion criterion32=Restrictions.ne("quType", QuType.MULTIFILLBLANK);
		Criterion criterion33=Restrictions.ne("quType", QuType.ANSWER);
		Criterion criterion34=Restrictions.ne("quType", QuType.CHENCHECKBOX);
		Criterion criterion35=Restrictions.ne("quType", QuType.CHENFBK);
		Criterion criterion36=Restrictions.ne("quType", QuType.CHENRADIO);
		Criterion criterion37=Restrictions.ne("quType", QuType.ENUMQU);
		Criterion criterion38=Restrictions.ne("quType", QuType.ORDERQU);
		Criterion criterion39=Restrictions.ne("quType", QuType.SCORE);
		
		return questionDao.find(criterion1,criterion2,criterion31,criterion32,criterion33,criterion34,criterion35,criterion36,criterion37,criterion38,criterion39);
	}


	@Override
	public TQuestion getDetail(String quId) {
		TQuestion question=get(quId);
		getQuestionOption(question);
		return question;
	}
	
	@Override
	public TQuestion getDetail1(String quId) {
		TQuestion question=findById(quId);
		getQuestionOption(question);
		return question;
	}

	@Transactional
	public void update(TQuestion entity){
		questionDao.update(entity);
	}


	@Override
	public TQuestion findById(String quId) {
		/*if(quId!=null && !"".equals(quId)){
			StringBuilder sb = new StringBuilder("select t.* from t_question t where t.id='" + quId + "'");
			return jdbcTemple.queryForObject(sb.toString(), new BeanPropertyRowMapper<>(TQuestion.class));
		}else{
			return new TQuestion();
		}*/
		final QuType[] quTypes = QuType.values();
		
		final CheckType[] checkTypes = CheckType.values();
		final YesnoOption[] yesnoOption = YesnoOption.values();
		
		if(quId!=null && !"".equals(quId)){
			StringBuilder sb = new StringBuilder("select t.* from t_question t where t.id= ? ");
			return jdbcTemple.queryForObject(sb.toString(), new RowMapper<TQuestion>(){
				@Override
				public TQuestion mapRow(ResultSet result, int arg1) throws SQLException {
					TQuestion question = new TQuestion();
					question.setId(result.getString("id"));
					question.setAnswerInputRow(result.getInt("ANSWER_INPUT_ROW"));
					question.setAnswerInputWidth(result.getInt("ANSWER_INPUT_WIDTH"));
					question.setBelongId(result.getString("BELONG_ID"));
					question.setCellCount(result.getInt("CELL_COUNT"));
					int checktype = result.getInt("CHECK_TYPE");
					for (CheckType checkType : checkTypes) {
						if(checkType.getIndex()==checktype){
							question.setCheckType(checkType);
							break;
						}
					}
				
					question.setContactsAttr(result.getInt("CONTACTS_ATTR"));
					question.setContactsField(result.getString("CONTACTS_FIELD"));
					question.setCopyFromId(result.getString("COPY_FROM_ID"));
					question.setCreateDate(result.getDate("CREATE_DATE"));
					question.setHv(result.getInt("HV"));
					question.setIsRequired(result.getInt("IS_REQUIRED"));
					question.setKeywords(result.getString("KEYWORDS"));
					question.setOrderById(result.getInt("ORDER_BY_ID"));
					question.setParamInt01(result.getInt("PARAM_INT01"));
					question.setParamInt02(result.getInt("PARAM_INT02"));
					question.setParentQuId(result.getString("PARENT_QU_ID"));
					question.setQuName(result.getString("QU_NAME"));
					question.setQuNote(result.getString("QU_NOTE"));
					question.setQuTag(result.getInt("QU_TAG"));
					question.setQuTitle(result.getString("QU_TITLE"));
					
					int quType = result.getInt("QU_TYPE");
					for (QuType quType1 : quTypes) {
						if(quType1.getIndex()==quType){
							question.setQuType(quType1);
							break;
						}
					}
					
					question.setRandOrder(result.getInt("RAND_ORDER"));
					question.setTag(result.getInt("TAG"));
					question.setVisibility(result.getInt("VISIBILITY"));
					int yesNo = result.getInt("YESNO_OPTION");
					for (YesnoOption yesnoOption2 : yesnoOption) {
						if(yesnoOption2.getIndex()==yesNo){
							question.setYesnoOption(yesnoOption2);
							break;
						}
					}
					
					return question;
				}
				
			},quId);
		}else{
			return new TQuestion();
		}
		
	}
	
}
