package com.sinosoft.sinoep.modules.dagl.wdgl.services;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.UUIDGenerator;
import com.sinosoft.sinoep.message.controller.NotifyController;
import com.sinosoft.sinoep.message.model.NotityMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dagl.bmgl.services.BmglService;
import com.sinosoft.sinoep.modules.dagl.constant.DAGLCommonConstants;
import com.sinosoft.sinoep.modules.dagl.wdgl.dao.DaglSendFileDao;
import com.sinosoft.sinoep.modules.dagl.wdgl.entity.DaglSendFile;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.ContrastingRelations;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.DataContrast;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity.TableStructDescription;
import com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.services.ContrastService;
import com.sinosoft.sinoep.modules.system.component.importFile.util.ImportFileUtil;
import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;
import com.sinosoft.sinoep.modules.system.config.dictionary.services.DataDictionaryService;
import com.sinosoft.sinoep.user.util.UserUtil;
import workflow.common.JDateToolkit;
import javax.persistence.Query;

/**
 * TODO 文电管理业务层实现类（发文）
 * @author 李颖洁  
 * @date 2018年11月20日  上午11:37:56
 */
@Service
public class DaglSendFileServiceImpl implements DaglSendFileService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DaglSendFileDao dao;
	
	@Autowired
	private DataDictionaryService service;
	
	@Autowired
	private ContrastService contrastService;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private BmglService bmglService;

	@Autowired
	private NotifyController notifyController;

	@Autowired
	private PlatformTransactionManager transactionManager;

	private static Connection con = null;
	
	/**
	 * TODO 保存文件信息（发文）
	 * @author 李颖洁  
	 * @date 2018年11月20日上午11:38:28
	 * @param sendFile
	 * @return
	 */
	@Override
	public DaglSendFile editSendFile(DaglSendFile sendFile) {
		if(StringUtils.isBlank(sendFile.getId())){
			sendFile.setCreUserId(UserUtil.getCruUserId());
			sendFile.setCreUserName(UserUtil.getCruUserName());
			sendFile.setCreDeptId(UserUtil.getCruDeptId());
			sendFile.setCreDeptName(UserUtil.getCruDeptName());
			sendFile.setCreChushiId(UserUtil.getCruChushiId());
			sendFile.setCreChushiName(UserUtil.getCruChushiName());
			sendFile.setCreJuId(UserUtil.getCruJuId());
			sendFile.setCreJuName(UserUtil.getCruJuName());
			sendFile.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			sendFile.setVisible(CommonConstants.VISIBLE[1]);
			sendFile.setFileSite(DAGLCommonConstants.FILE_SITE[0]);
			try {
				if(DAGLCommonConstants.WEN_DIAN_TYPE[0].equals(sendFile.getType())){
					sendFile.setState(DAGLCommonConstants.WEN_DIAN_STATUS[0]);
				}else{
					sendFile.setState("");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			sendFile = dao.save(sendFile);
		}else{
			DaglSendFile preFile = getOne(sendFile);
			preFile.setTitle(sendFile.getTitle());
			preFile.setFileNum(sendFile.getFileNum());
			preFile.setPageNum(sendFile.getPageNum());
			preFile.setFileDept(sendFile.getFileDept());
			preFile.setSecurityClass(sendFile.getSecurityClass());
			preFile.setCreatedDate(sendFile.getCreatedDate());
			preFile.setNote(sendFile.getNote());
			preFile.setType(sendFile.getType());
			preFile.setSerialNum(sendFile.getSerialNum());
			preFile.setFileTime(sendFile.getFileTime());
			preFile.setQuantity(sendFile.getQuantity());
			preFile.setBarcode(sendFile.getBarcode());
			preFile.setFileType(sendFile.getFileType());
			preFile.setZhusUnit(sendFile.getZhusUnit());
			preFile.setUrgencyDegree(sendFile.getUrgencyDegree());
			preFile.setHandleUnit(sendFile.getHandleUnit());
			preFile.setReceipient(sendFile.getReceipient());
			preFile.setFileSite(sendFile.getFileSite());
			try {
				if(DAGLCommonConstants.WEN_DIAN_TYPE[0].equals(sendFile.getType())){
					preFile.setState(DAGLCommonConstants.WEN_DIAN_STATUS[0]);
				}else{
					preFile.setState("");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			sendFile = dao.save(preFile);
		}
		return sendFile;
	}

	/**
	 * TODO  根据id获取一个文件（发文）
	 * @author 李颖洁  
	 * @date 2018年11月20日上午11:38:54
	 * @param sendFile
	 * @return
	 */
	@Override
	public DaglSendFile getOne(DaglSendFile sendFile) {
		return dao.findOne(sendFile.getId());
	}

	/**
	 * TODO 删除一个文件信息（发文）
	 * @author 李颖洁  
	 * @date 2018年11月20日上午11:39:03
	 * @param sendFile
	 * @return
	 */
	@Override
	public int deleteOne(DaglSendFile sendFile) {
		String jpql = "delete  from DaglSendFile t  where t.id = ?";
		return dao.update(jpql, sendFile.getId());
	}

	/**
	 * TODO 删除多个文件信息（发文）
	 * @author 李颖洁  
	 * @date 2018年11月20日上午11:40:30
	 * @param ids
	 * @return
	 */
	@Override
	public int batchDelete(String ids) {
		//防止sql注入 王磊 2019年4月26日
		List<Object> paraList = new ArrayList<>();
		String jpql = "delete from DaglSendFile t  where t.id in ("+CommonUtils.solveSqlInjectionOfIn(ids, paraList)+") ";
		return dao.update(jpql,paraList.toArray());
	}

	/**
	 * TODO 获取文件列表带分页 （发文）
	 * @author 李颖洁  
	 * @date 2018年11月20日上午11:40:22
	 * @param sendFile
	 * @param pageImpl
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<DaglSendFile> list(DaglSendFile sendFile, PageImpl pageImpl,Pageable pageable) {
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		//全部列表查询
		querySql.append("select new DaglSendFile(t.id,t.title,t.fileNum,t.pageNum,t.fileDept,t.securityClass,t.createdDate,t.state,t.note,t.type,t.serialNum"
				+ "	,t.fileTime,t.quantity,t.barcode,t.fileType,t.zhusUnit,t.urgencyDegree,t.handleUnit,t.receipient)");
		querySql.append("	from DaglSendFile t");
		querySql.append("	where t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		if(!StringUtils.isBlank(sendFile.getCreChushiId())){
			querySql.append("   and t.creChushiId = ?");
			para.add(sendFile.getCreChushiId());
		}
		if(!StringUtils.isBlank(sendFile.getType())){
			querySql.append("   and t.type = ?");
			para.add(sendFile.getType());
		}
		if(!StringUtils.isBlank(sendFile.getState())){
			querySql.append("   and t.state = ?");
			para.add(sendFile.getState());
		}
		if(!StringUtils.isBlank(sendFile.getSecurityClass())){
			querySql.append("   and t.securityClass = ?");
			para.add(sendFile.getSecurityClass());
		}
		if(!StringUtils.isBlank(sendFile.getTitle())){
			querySql.append("   and t.title like ? ");
			para.add("%"+sendFile.getTitle()+"%");
		}
		if(!StringUtils.isBlank(sendFile.getFileDept())){
			querySql.append("   and t.fileDept like ? ");
			para.add("%"+sendFile.getFileDept()+"%");
		}
		String timeRange = sendFile.getTimeRange();
		if (StringUtils.isNotBlank(timeRange)) {
			String startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			String endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			querySql.append("   and t.createdDate >= ? ");
			querySql.append("   and t.createdDate <= ? ");
			para.add(startTime);
			para.add(endTime);
		}
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc) ");
		}else{
			querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}
		Page<DaglSendFile> page = dao.query(querySql.toString(), pageable,para.toArray());
		return page;
	}

	/**
	 * TODO 获取归档列表数据（发文）
	 * @author 李颖洁  
	 * @date 2018年11月20日上午11:40:09
	 * @param ids
	 * @return
	 */
	@Override
	public List<DaglSendFile> getTidyRecodedList(String ids) {
		String querySql = "";
		//全部列表查询
		//防止sql注入 王磊 2019年4月26日
		List<Object> paraList = new ArrayList<>();
		querySql = "select t.* from dagl_send_file t	where t.visible = '"+CommonConstants.VISIBLE[1]+"' and t.id in ("+CommonUtils.solveSqlInjectionOfIn(ids, paraList)+")";
		List<DaglSendFile> page = jdbcTemplate.query(querySql,new BeanPropertyRowMapper<>(DaglSendFile.class),paraList.toArray());
		return page;
	}
	
	/** 
	 * TODO 导入数据
	 * @author 李颖洁  
	 * @date 2018年11月13日下午4:11:17
	 * @param filePath
	 * @param fileToUpload
	 * @return
	 */
	@Override
	public String importInfo(String filePath, MultipartFile fileToUpload) {
		 String msg = "导入失败！";
	        try {
	            //这里是为了格式化手机号，获取excel的行数据
	            InputStream is = fileToUpload.getInputStream();
	            Workbook workbook = null;
	            workbook = ImportFileUtil.getWorkBook(is, filePath);
	            Sheet sheet = workbook.getSheetAt(0);//下角标为0的sheet页数据
	            List<DaglSendFile> list = new ArrayList<>();
	            Row head = sheet.getRow(0);
	            boolean flag = true;
	            if(head==null){
	            	msg = "导入模板格式不正确，请重新导入！";
	            }else{
	            	String[] headcol = new String[11];
	            	String[] oricol = {"*收/发文日期","收/发文编号","*文件标题","文号","*成文日期","页数","份数","*来/发文单位","*密级","*文件类型","备注"};
	            	for (int j = 0; j < headcol.length; j++) {
	            		headcol[j] = ImportFileUtil.getRowCellValue(head,(short)j);//收发文日期
	            		if(!headcol[j].equals(oricol[j])){
	            			flag = false;
	            			break;
	            		}
	            	}
	            	if(flag){
	            		/*这里为循环校验数据*/
	            		int errorCount = 0;
	            		String errContent = "";
	            		int len = sheet.getLastRowNum();
	            		if(len<1){
	            			msg = "导入数据为空，请重新导入！";
	            		}else{
	            			for (int i = 1; i <=len; i++) {
	            				
	            				Row row = sheet.getRow(i);//得到第i行数据
	            				//获取值
	            				String fileTime = ImportFileUtil.getRowCellValue(row,(short)0);//收发文日期
	            				String serialNum = ImportFileUtil.getRowCellValue(row,(short)1);//文件编号
	            				String title = ImportFileUtil.getRowCellValue(row,(short)2);//标题
	            				String fileNum = ImportFileUtil.getRowCellValue(row,(short)3);//文号
	            				String createdDate = ImportFileUtil.getRowCellValue(row,(short)4);//成文日期
	            				String pageNum = ImportFileUtil.getRowCellValue(row,(short)5);//页数
	            				String quantity = ImportFileUtil.getRowCellValue(row,(short)6);//份数
	            				String fileDept = ImportFileUtil.getRowCellValue(row,(short)7);//来文单位
	            				String sercurityClass = ImportFileUtil.getRowCellValue(row,(short)8);//密级
	            				String type = ImportFileUtil.getRowCellValue(row,(short)9);//文件类型
	            				String note = ImportFileUtil.getRowCellValue(row,(short)10);//备注
	            				//创建文件对象
	            				DaglSendFile daglSendFile = new DaglSendFile();
	            				daglSendFile.setCreUserId(UserUtil.getCruUserId());
	            				daglSendFile.setCreUserName(UserUtil.getCruUserName());
	            				daglSendFile.setCreDeptId(UserUtil.getCruDeptId());
	            				daglSendFile.setCreDeptName(UserUtil.getCruDeptName());
	            				daglSendFile.setCreChushiId(UserUtil.getCruChushiId());
	            				daglSendFile.setCreChushiName(UserUtil.getCruChushiName());
	            				daglSendFile.setCreJuId(UserUtil.getCruJuId());
	            				daglSendFile.setCreJuName(UserUtil.getCruJuName());
	            				daglSendFile.setVisible(CommonConstants.VISIBLE[1]);
	            				daglSendFile.setCreTime(JDateToolkit.getNowDate4());
	            				daglSendFile.setFileSite(DAGLCommonConstants.FILE_SITE[0]);
	            				//导入
	            				daglSendFile.setFileNum(fileNum);
	            				daglSendFile.setSerialNum(serialNum);
	            				daglSendFile.setNote(note);
	            				//验证收发文日期不为空
	            				if(StringUtils.isNotBlank(fileTime)){
	            					//不为空
	            					daglSendFile.setFileTime(fileTime);
	            				}else{
	            					errorCount++;
	            					if(errContent.indexOf("收/发文日期")==-1){
	            						errContent += "收/发文日期不能为空！<br/>";
	            					}
	            				}
	            				//验证标题不为空
	            				if(StringUtils.isNotBlank(title)){
	            					//不为空
	            					daglSendFile.setTitle(title);
	            				}else{
	            					errorCount++;
	            					if(errContent.indexOf("文件标题")==-1){
	            						errContent += "文件标题不能为空！<br/>";
	            					}
	            				}
	            				//验证页数为数字
	            				if(pageNum.matches("[0-9]+")){
	            					daglSendFile.setPageNum(Integer.valueOf(pageNum));
	            				}else{
	            					errorCount++;
	            					if(errContent.indexOf("页数")==-1){
	            						errContent += "页数必须是数字！<br/>";
	            					}
	            				}
	            				//验证份数为数字
	            				if(quantity.matches("[0-9]+")){
	            					daglSendFile.setQuantity(Integer.valueOf(quantity));
	            				}else{
	            					errorCount++;
	            					if(errContent.indexOf("份数")==-1){
	            						errContent += "份数必须是数字！<br/>";
	            					}
	            				}
	            				//验证页数是否纯数字
	            				if(StringUtils.isNotBlank(createdDate)){
	            					if(createdDate.matches("[0-9]+")){
	            						daglSendFile.setCreatedDate(createdDate);
	            					}else{
	            						errorCount++;
	            						if(errContent.indexOf("成文日期必须是数字")==-1){
	            							errContent += "成文日期必须是数字！<br/>";
	            						}
	            					}
	            				}else{
	            					errorCount++;
	            					if(errContent.indexOf("成文日期不能为空")==-1){
	            						errContent += "成文日期不能为空！<br/>";
	            					}
	            				}
	            				
	            				//来文单位不为空
	            				if(StringUtils.isNotBlank(fileDept)){
	            					//不为空
	            					daglSendFile.setFileDept(fileDept);
	            				}else{
	            					errorCount++;
	            					if(errContent.indexOf("来/发文单位")==-1){
	            						errContent += "来/发文单位不能为空！<br/>";
	            					}
	            				}
	            				//密级不为空
	            				//获取密级的数据字典数据
	            				/*List<DataDictionary> dicts = service.getListByMark("04","1");
		     					Map<String, String> map = new HashMap<>();
		     					for (DataDictionary dic : dicts) {
		     						map.put(dic.getName(),dic.getCode());
		     					}*/
	            				if(StringUtils.isNotBlank(sercurityClass)){
	            					//不为空
	            					daglSendFile.setSecurityClass(sercurityClass);
	            				}else{
	            					errorCount++;
	            					if(errContent.indexOf("密级")==-1){
	            						errContent += "密级不能为空！<br/>";
	            					}
	            				}
	            				//文件类型不为空
	            				//获取类型的数据字典数据
	            				List<DataDictionary> typeDicts = service.getListByMark("dagl_fileType","1");
	            				Map<String, String> typeMap = new HashMap<>();
	            				for (DataDictionary dic : typeDicts) {
	            					typeMap.put(dic.getName(),dic.getCode());
	            				}
	            				if(StringUtils.isNotBlank(type)){
	            					//不为空
	            					daglSendFile.setType(typeMap.get(type));
	            					if("需归档".equals(type)){
	            						daglSendFile.setState(DAGLCommonConstants.WEN_DIAN_STATUS[0]);
	            					}
	            				}else{
	            					errorCount++;
	            					if(errContent.indexOf("文件类型")==-1){
	            						errContent += "文件类型不能为空！<br/>";
	            					}
	            				}
	            				list.add(daglSendFile);
	            			}
	            			if(errorCount>0){
	            				return errContent;
	            			}else{
	            				//保存新的文件数据
	            				dao.save(list);
	            				msg = "导入成功！";
	            			}
	            			
	            		}
	            		
	            	}else{
	            		msg = "导入模板格式不正确，请重新导入！";
	            	}
	            	
	            }
	        } catch (Exception e) {
	             e.printStackTrace();
	         }
	        return msg;
	}
	
	/**
	 * @Author 王富康
	 * @Description //TODO 文件归档操作
	 * @Date 2019/7/27 11:17
	 * @Param [relations, ids, jsonStr, fileNumberRule, form]
	 * @return int
	 **/
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int tidyRecode(ContrastingRelations relations, String ids, String jsonStr,String fileNumberRule, String form) throws Exception{
		try {
			//获取所有表，防止SQL注入
			List<String> allTables = bmglService.findAllTables();
			//目标表名，即即将插入的表名
			String targetName = "";
			//序号前边的连接符
			String lastConnector = "";
			//档号
			String dh = "";
			String dhCol = "";
			String dhVal = "";
			//目标表的档号字段
			String mydhCol = "";
			//“序号”的字段
			String lastdhCol= "";
			//目标表的档号值
			String mydhVal = "";
			//“序号”的值
			String lastdhVal = "";
			String recid = "";
			String pageNum = "0";
			//档号规则涉及到的档号字段，包括档号字段，（两层的话，包含父表字段）
			List<String> ruleColList=new ArrayList<String>();
			for(int j=0;j<allTables.size();j++){
				if(allTables.get(j).equals(relations.getTargetName())){
					targetName = allTables.get(j);
				}
			}
			//立卷单位+档号项数据
			Map<String, String> parseObject = com.alibaba.fastjson.JSONObject.parseObject(jsonStr,
					new TypeReference<Map<String, String>>() {
					});
			//档号规则
			JSONArray ruleJsonArray = JSONArray.fromObject(fileNumberRule);
			//档号字段信息项
			JSONArray formJsonArray = JSONArray.fromObject(form);

			for (int i=0;i<ruleJsonArray.size();i++){
				String columnName = ruleJsonArray.getJSONObject(i).getString("RULE_FIELD");
				//添加档号字段到list中
				ruleColList.add(columnName);
				if(i<ruleJsonArray.size()-1){
					dhCol +=columnName+",";
					if("fonds_no".equals(columnName)){
						//全宗号取code
						dhVal += "'"+parseObject.get(columnName+"_code")+"',";
					}else{
						//其他的取name
						dhVal += "'"+parseObject.get(columnName)+"',";
					}
				}
				for(int j=0;j<formJsonArray.size();j++){
					if(formJsonArray.getJSONObject(j).get("COLUMN_NAME").equals(columnName) && "S".equals(formJsonArray.getJSONObject(j).get("COLUMN_INPUT_TYPE"))){
						columnName +="_code";
					}
				}
				String columnvalue =parseObject.get(columnName);
				//这里的意思是前台没有存的话，就不取了，指的是不取“序号”那个字段；
				if(!"".equals(columnvalue) && !"null".equals(columnvalue) && null != columnvalue){
					dh += columnvalue+ruleJsonArray.getJSONObject(i).get("CONNECTOR");
				}
				if(i==ruleJsonArray.size()-2){
					//获取序号前边的连接符
					lastConnector = ruleJsonArray.getJSONObject(i).get("CONNECTOR").toString();
				}
			}
			if(!"".equals(dh)){
				dh = dh.substring(0,dh.length()-1);
			}
			String ljdwMark = parseObject.get("basefolder_unit_code").toString();
			String ljdwName = parseObject.get("basefolder_unit").toString();



			//调整为中转库归档的时候，设置为已组卷，因为是直接归到某个案卷中，或者是归档到第一层。
			//组卷标识
			String archiveFlag =DAGLCommonConstants.ARCHIVE_FLAG[0];
			String sqlRelation = "select t.*, t.rowid from tables_relation t start with s_table_name = ? connect by s_table_name = prior m_table_name";
			List<Map<String, Object>> query = jdbcTemplate.queryForList(sqlRelation,relations.getTargetName());
			//向数组中添加档号字段，两层的添加父表字段
			if(query.size()==1){
				//添加档号字段到list中
				ruleColList.add(query.get(0).get("S_COL_NAME").toString());
				mydhCol +="archive_no,";
			}else if(query.size()==2){
				//添加档号字段到list中
				ruleColList.add(query.get(0).get("S_COL_NAME").toString());
				ruleColList.add(query.get(0).get("M_COL_NAME").toString());
				//两层的记录父级档号
				dhCol+=query.get(0).get("S_COL_NAME").toString()+",";
				mydhCol += "archive_no,";
				dhVal+="'"+dh+"',";
			}
			//即将插入的"序号"
			String sColName = query.get(0).get("S_COL_NAME").toString();
			String lastColName = ruleJsonArray.getJSONObject(ruleJsonArray.size()-1).getString("RULE_FIELD");
			lastdhCol += lastColName+",";
			int pieceNo = bmglService.queryCountAdd(dh,sColName, targetName, ljdwMark, UserUtil.getCruUserId(), lastColName , query.size()+"");
			//第一个档案的序号（未补0）;
			int thisTotal=(pieceNo-0+1);
			//"序号"的长度
			String pieceNoLength = ruleJsonArray.getJSONObject(ruleJsonArray.size()-1).getString("NUMB_LENGTH");
			//判断是否是多层表结构
		/*if(query.size()>1){
			archiveFlag = DAGLCommonConstants.ARCHIVE_FLAG[1];
		}else{
			archiveFlag = DAGLCommonConstants.ARCHIVE_FLAG[0];
		}*/
			//获取选中数据的全部信息
			List<DaglSendFile> list = getTidyRecodedList(ids);

			//公共有的字段
			String  commonalityColumns = "cre_user_name,cre_user_id,cre_dept_name,cre_dept_id,cre_chushi_id,cre_chushi_name,cre_ju_id,cre_ju_name,cre_time,visible,";

			//根据id获取数据对照字段
			DataContrast dataContrast = new DataContrast();
			dataContrast.setContrastingId(relations.getId());
			List<DataContrast> dataList = contrastService.getDataContrast(dataContrast);
			//根据id获取没有在对照表中设置并不为空的字段
			List<TableStructDescription> notNullData = contrastService.getNotNullData(relations.getId());
			//用于判断是否有系统唯一字段，具体的index，以便赋值
			int index = -1;
			//用于判断是否有立卷单位
			boolean flag = false;
			//防止sql注入 王磊 2019年4月26日
			List<Object> paraList = new ArrayList<>();
			List<String> arrCol = new ArrayList<>();
			List<String> arrval = new ArrayList<>();
			for(int k=0;k<notNullData.size();k++){
				//去除页面填写的信息(包含立卷单位)
				if(!ruleColList.contains(notNullData.get(k).getColumnName()) && notNullData.get(k).getColumnName().indexOf("basefolder_unit")==-1){
					if(notNullData.get(k).getColumnName().indexOf("recid")==-1){
						//recid特殊处理，
						arrCol.add(notNullData.get(k).getColumnName());
					}
					if(notNullData.get(k).getColumnName().indexOf("archive_flag")!=-1){
						//archiveFlag组卷标识字段
						arrval.add("'"+archiveFlag+"'");
					}else if(notNullData.get(k).getColumnName().indexOf("recid")!=-1){
						//recid 系统唯一字段
						index = k;
					}else{
						switch (notNullData.get(k).getColumnType()) {
							case "1"://String
								arrval.add("' '");
								break;
							case "2"://Integer
								arrval.add("'0'");
								break;
							case "3"://Double
								arrval.add("'0.0'");
								break;
							case "4"://Date(当前时间)
								arrval.add(DateUtil.COMMON_FULL.getDateText(new Date()));
								break;
							case "5"://String
								arrval.add("' '");
								break;
							default:
								break;
						}
					}
				}
			}

			Class<?> c = Class.forName("com.sinosoft.sinoep.modules.dagl.wdgl.entity.DaglSendFile");
			//定义sql数组
			String[] sql = new String[list.size()];
			//保存不为空字段的值
			String notNullVal = "";
			//目标表已对照的字段
			String targetColumns = "";
			//没有在对照表中设置并不为空的字段
			String notNullColumns = "";
			int pigeonholeDateNum = 0;
			//文件信息（多行）
			for (int i=0;i<list.size();i++) {
				//把档号相关的信息补全
				// 0 代表前面补充0
				// 4 代表长度为4
				// d 代表参数为正数型
				String firstPieceNo = String.format("%0"+pieceNoLength+"d", thisTotal+i);
				//第一个档号应该是
				String NewDh = dh+lastConnector+firstPieceNo;
				//"序号"
				mydhVal = "'"+NewDh+"',";
				//自己的档号
				lastdhVal = "'"+firstPieceNo+"',";


				List<Object> para = new ArrayList<>();
				notNullVal = "";
				targetColumns = "";
				notNullColumns = "";
				//设置插入语句表名
				sql[i] = "insert into "+targetName;
				//共有字段赋值
				//创建人姓名和id、立卷单位id使用用户页面选择的内容 王磊 20190211
				//保存一行数据的值
				String vals = "(?,?,'"+list.get(i).getCreDeptName()+"',"
						+ "'"+list.get(i).getCreDeptId()+"',?,'"+list.get(i).getCreChushiName()+"','"+list.get(i).getCreJuId()+"',"
						+ "	'"+list.get(i).getCreJuName()+"','"+list.get(i).getCreTime()+"','"+list.get(i).getVisible()+"',";
				para.add(UserUtil.getCruUserName());
				para.add(UserUtil.getCruUserId());
				para.add(ljdwMark);
				if(index >-1){
					recid ="'"+list.get(i).getId()+"',";
				}
				for (String str : arrval) {
					notNullVal += str+",";
				}
				for (String col : arrCol) {
					notNullColumns += col+",";
				}
				vals += notNullVal;
				//字段对应信息
				for (DataContrast dc : dataList) {
					if(StringUtils.isNotBlank(dc.getTargetColumn())){
						//不包含档号项
						if(!ruleColList.contains(dc.getTargetColumn())){
							targetColumns += dc.getTargetColumn()+",";
							String[] sources= dc.getSourceColumn().split("_");
							for (int j=0;j<sources.length;j++) {
								sources[j] =sources[j].substring(0, 1).toUpperCase()+sources[j].substring(1,sources[j].length());
							}
							String name = "get";
							for (String str : sources) {
								name += str;
							}
							try {
								Method method = c.getMethod(name);
								Object val = method.invoke(list.get(i));
								if(dc.getTargetColumn().indexOf("pigeonhole_date")!=-1){
									pigeonholeDateNum++;
								}
								if(val==null||val==""){
									if(dc.getTargetColumn().indexOf("archive_flag")!=-1){
										val += archiveFlag;
									}else if(dc.getTargetColumn().indexOf("pigeonhole_date")!=-1){
										val += DateUtil.getDateText(new Date(), "yyyyMMdd");
									}else{
										switch (dc.getTargetType()) {
											case "1"://String
												val = " ";
												break;
											case "2"://Integer
												val = 0;
												break;
											case "3"://Double
												val = 0.0;
												break;
											case "4"://Date(???)
												val = (Date)val;
												break;
											case "5"://String
												val = " ";
												break;
											default:
												break;
										}
									}
								}else{
									if(dc.getTargetColumn().indexOf("page_num")!=-1){
										pageNum = val.toString();
									}
									switch (dc.getTargetType()) {
										//调整类型转化方式 王磊 20190419
										case "1"://String
											val = val.toString();
											break;
										case "2"://Integer
											val = Integer.parseInt(val.toString());
											break;
										case "3"://Double
											val = Double.parseDouble(val.toString());
											break;
										case "4"://Date(???)
											val = (Date)val;
											break;
										case "5"://String(8位数字组成)
											val = val.toString();
											break;
										default:
											break;
									}
								}
								val = "'"+val+"'";
								vals += val+",";
							} catch (Exception e) {
								log.error("数据类型转化异常:"+dc.getContrastingId()+"$"+dc.getSourceColumn()+"("+dc.getSourceColumnChnName()+")"+"$"+dc.getSourceType()+"$"+dc.getTargetColumn()+"("+dc.getTargetColumnChnName()+")"+"$"+dc.getTargetType());
								log.error(e.getMessage(),e);
							}
						}
					}else{
						continue;
					}
				}
				String endColumns = "";
				endColumns = commonalityColumns+notNullColumns+targetColumns+dhCol+mydhCol+lastdhCol+"recid,";
				vals+=dhVal+mydhVal+lastdhVal+recid;
				//立卷单位使用用户从页面选择的内容 王磊 20190211
				if(!flag){
					endColumns += "basefolder_unit,";
					vals += "?,";
					para.add(ljdwName);
				}
				//添加档案的档案实体状态为：待提交 王磊20190409 开始
				endColumns += "archive_entity_status,";
				vals += "'"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[0]+"',";
				//如果数据对照目标字段不包含归档日期，那么默认存归档的日期pigeonholeDateNum
				if(pigeonholeDateNum==0){
					endColumns += "pigeonhole_date,";
					vals += "'"+DateUtil.getDateText(new Date(), "yyyyMMdd")+"',";
				}

				//添加档案的档案实体状态为：待提交 王磊20190409 结束
				sql[i] += "("+endColumns.substring(0, endColumns.length()-1)+") values ";
				//拼接一行数据的值
				sql[i] += vals.substring(0,vals.length()-1)+")";
				//最终插入语句
				log.info(sql[i]);
				if(paraList.size()>0){
					para.add(paraList.get(0));
				}
				jdbcTemplate.update(sql[i],para.toArray());

				//如果是两层的话，更新父表数据
				//修改父级的数据
				if(query.size()==2){
					//关联信息
					String pTableSql =
							"SELECT t.*\n" +
									"  FROM tables_relation t, table_description s\n" +
									" WHERE t.m_table_name = s.table_name\n" +
									"   and t.s_table_name = ? ";//排除父节点为门类的数据

					List<Map<String,Object>> pTableList = jdbcTemplate.queryForList(pTableSql,targetName);
					if(pTableList.size()>0) {
						//有父表，查询出父表的状态
						Map<String, Object> relationMap = pTableList.get(0);
						//父表表名
						String pTableName = String.valueOf(relationMap.get("M_TABLE_NAME"));
						//父表字段
						String mColName = String.valueOf(relationMap.get("M_COL_NAME"));
						//子表字段
						String ssColName = String.valueOf(relationMap.get("S_COL_NAME"));
						String sColData = "";
						//立卷单位的code
						String creChushiId="";
						//已组卷
						//与父表做了关联,查询出父表的那条数据//查询父表数据时添加同一个创建人、立卷单位的过滤条件 王磊 2019-03-02
						String pDataSql = "SELECT t.* FROM " + pTableName + " t WHERE t." + mColName + " = '" + dh +"'";
						if(targetName.indexOf("dak")==-1){
							//单位预立库添加录入人和立卷单位标识
							pDataSql += " and t.CRE_USER_ID='" + UserUtil.getCruUserId() + "' and t.CRE_CHUSHI_ID='" + ljdwMark + "'";
						}
						List<Map<String, Object>> pTableDataList = jdbcTemplate.queryForList(pDataSql);
						if(pTableDataList.size()>0){
							//有案卷，归档到该案卷下之后，更新开始时间和结束时间，还有页数，总件数
							//新增两层表的第二层时，动态修改父表的文件开始时间和文件结束时间
							bmglService.setStratAndEndTime("2",pTableName,pTableDataList);
							//调整第一层的总件数和页数
							bmglService.updatePNum(pTableDataList.get(0).get("RECID").toString(),recid,targetName,"2", "2","1",pageNum,"add","");
						}else{
							List<Object> pPara = new ArrayList<>();
							String uuid = new UUIDGenerator().generateUUID32();
							String pVals = "?,?,'"+list.get(i).getCreDeptName()+"',"
									+ "'"+list.get(i).getCreDeptId()+"',?,'"+list.get(i).getCreChushiName()+"','"+list.get(i).getCreJuId()+"',"
									+ "	'"+list.get(i).getCreJuName()+"','"+list.get(i).getCreTime()+"','"+list.get(i).getVisible()+"',";
							pPara.add(UserUtil.getCruUserName());
							pPara.add(UserUtil.getCruUserId());
							pPara.add(ljdwMark);
							String queryArchiveFlagColSql ="SELECT COUNT(*) FROM USER_TAB_COLUMNS WHERE TABLE_NAME = ? AND COLUMN_NAME = 'ARCHIVE_FLAG'";
							int count = jdbcTemplate.queryForObject(queryArchiveFlagColSql,Integer.class,pTableName.toUpperCase());
							//没有该案卷，反向生成案卷信息
							String insertPData = "";
							if(count>1){
								insertPData = "insert into "+pTableName +"(recid,"+commonalityColumns+dhCol+"maintitle,archive_entity_status,basefolder_unit,page_num,piece_num,archive_flag) values ('"+uuid+"',"+pVals+dhVal+"'中转库归档文件，请及时修改','"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[0]+"','"+ljdwName+"','0','0','"+DAGLCommonConstants.ARCHIVE_FLAG[0]+"')";
							}else{
								insertPData = "insert into "+pTableName +"(recid,"+commonalityColumns+dhCol+"maintitle,archive_entity_status,basefolder_unit,page_num,piece_num) values ('"+uuid+"',"+pVals+dhVal+"'中转库归档文件，请及时修改','"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[0]+"','"+ljdwName+"','0','0')";
							}
							jdbcTemplate.update(insertPData,pPara.toArray());
							List<Map<String,Object>> findById = bmglService.findById(pTableName, uuid);
							//新增两层表的第二层时，动态修改父表的文件开始时间和文件结束时间
							bmglService.setStratAndEndTime("2",pTableName,findById);
							//调整第一层的总件数和页数
							bmglService.updatePNum(uuid,recid,targetName,"2", "2","1",pageNum,"add","");
						}

					}
				}
			}
			int num = updateState(ids,DAGLCommonConstants.WEN_DIAN_STATUS[4]);
			int nums = updateFileSite(ids,DAGLCommonConstants.FILE_SITE[2]);
			if(num==0){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return 0;
			}else{
				return num;
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
	}

	/** 
	 * TODO 更改数据状态（发文）
	 * @author 李颖洁  
	 * @date 2018年11月19日下午6:13:45
	 * @param ids
	 */
	@Override
	public int updateState(String ids,String status) {
		//防止sql注入 王磊 2019年4月26日
		List<Object> paraList = new ArrayList<>();
		String updateType = "";
		/*if(ids.indexOf("'")==-1){
			ids = "'"+ids.replaceAll(",", "','")+"'";
		}*/
		//如果status=03，已退回待处置，那么表示档案那边删除了此文件，需要对文件类型做更新
		if(StringUtils.isNotBlank(status) && DAGLCommonConstants.WEN_DIAN_STATUS[2].equals(status)){
			updateType = ",t.type=''";
		}
		paraList.add(status);
		String jpql = "update  dagl_send_file t set t.state = ? "+updateType+" where t.id in ("+CommonUtils.solveSqlInjectionOfIn(ids, paraList)+")";
		log.info(jpql);
		return jdbcTemplate.update(jpql,paraList.toArray());
	}

	/**
	 * TODO 获取打印数据
	 * @author 张浩磊
	 * @date 2018年11月20日上午11:27:01
	 * @param ids
	 * @return int
	 */
	@Override
	public List<DaglSendFile> getDaglSendFileList(DaglSendFile daglSendFile,String ids) {
		//防止sql注入 王磊 2019年4月26日
		List<Object> paraList = new ArrayList<>();
		String[] strIds = ids.split(",");
		StringBuilder querySql = new StringBuilder();
		List<DaglSendFile> list = new ArrayList<>();
		//全部列表查询
		querySql.append("select new DaglSendFile(t.id,t.title,t.fileNum,t.pageNum,t.fileDept,t.securityClass,t.createdDate,t.state,t.note,t.type,t.serialNum"
				+ "	,t.fileTime,t.quantity,t.barcode,t.fileType,t.zhusUnit,t.urgencyDegree,t.handleUnit,t.receipient)");
		querySql.append("	from DaglSendFile t");
		querySql.append("	where t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		if(!ids.equals("")){
			querySql.append("   and t.id in ("+CommonUtils.solveSqlInjectionOfIn(ids, paraList)+")");
		}else{
			if(!StringUtils.isBlank(daglSendFile.getCreChushiId())){
				querySql.append("   and t.creChushiId = ? ");
				paraList.add(daglSendFile.getCreChushiId());
			}
			if(!StringUtils.isBlank(daglSendFile.getType())){
				querySql.append("   and t.type = ? ");
				paraList.add(daglSendFile.getType());
			}
			if(!StringUtils.isBlank(daglSendFile.getState())){
				querySql.append("   and t.state = ? ");
				paraList.add(daglSendFile.getState());
			}
			if(!StringUtils.isBlank(daglSendFile.getSecurityClass())){
				querySql.append("   and t.securityClass = ? ");
				paraList.add(daglSendFile.getSecurityClass());
			}
			if(!StringUtils.isBlank(daglSendFile.getTitle())){
				querySql.append("   and t.title like ? ");
				paraList.add("%"+daglSendFile.getTitle()+"%");
			}
			if(!StringUtils.isBlank(daglSendFile.getFileDept())){
				querySql.append("   and t.fileDept like ? ");
				paraList.add("%"+daglSendFile.getFileDept()+"%");
			}
			String timeRange = daglSendFile.getTimeRange();
			if (StringUtils.isNotBlank(timeRange)) {
				String startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				String endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
				querySql.append("   and t.createdDate >= ? ");
				querySql.append("   and t.createdDate <= ? ");
				paraList.add(startTime);
				paraList.add(endTime);
			}
		}
		Query query = dao.getEntityManager().createQuery(querySql.toString());
		for(int i=0;i<paraList.size();i++){
			query.setParameter(i+1, paraList.get(i));
		}
		list = query.getResultList();
		return list;
	}

	/**
	 * TODO 获取销毁数据
	 * @author 张浩磊
	 * @date 2018年11月20日上午11:27:01
	 * @param
	 * @return int
	 */
	@Override
	public List<DaglSendFile> getDestoryList(String year) {
		StringBuilder querySql = new StringBuilder();
		List<DaglSendFile> list = new ArrayList<>();
		//全部列表查询
		querySql.append("select new DaglSendFile(t.id,t.title,t.fileNum,t.pageNum,t.fileDept,t.securityClass,t.createdDate,t.state,t.note,t.type,t.serialNum"
				+ "	,t.fileTime,t.quantity,t.barcode,t.fileType,t.zhusUnit,t.urgencyDegree,t.handleUnit,t.receipient)");
		querySql.append("	from DaglSendFile t");
		querySql.append("	where t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		querySql.append("	and t.type = '03'");
		querySql.append("   and t.creChushiId = '" + UserUtil.getCruChushiId() + "'");
		//防止sql注入 王磊 2019年4月26日
		if(!StringUtils.isBlank(year)){
			querySql.append("   and substr(t.createdDate,1,4) = ? ");
		}
		Query query = dao.getEntityManager().createQuery(querySql.toString());
		if(!StringUtils.isBlank(year)){
			query.setParameter(1, year);
		}
		list = query.getResultList();
		return list;
	}
	
	/** 
	 * TODO 根据勾选的数据批量修改数据
	 * @author 李颖洁  
	 * @date 2018年12月18日下午4:58:50
	 * @param ids
	 * @return
	 */
	@Override
	public int batchUpdate(String ids,String type) {
		String jpql = "update DaglSendFile t set t.type = ? ";
		if(DAGLCommonConstants.WEN_DIAN_TYPE[0].equals(type)){
			jpql += ",t.state='"+DAGLCommonConstants.WEN_DIAN_STATUS[0]+"'";
		}else{
			jpql += ",t.state=''";
		}
		jpql += " where 1=1 ";
		//防止sql注入 王磊 2019年4月26日
		List<Object> paraList = new ArrayList<>();
		paraList.add(type);
		if(ids.length()>0){
			jpql += " and t.id in ("+CommonUtils.solveSqlInjectionOfIn(ids, paraList)+") ";
		}
		jpql += " and (t.state not in('"+DAGLCommonConstants.WEN_DIAN_STATUS[1]+"','"+DAGLCommonConstants.WEN_DIAN_STATUS[3]+"') or t.state is null)";
		log.info(jpql);
		return dao.update(jpql,paraList.toArray());
	}

	/** 
	 * TODO 查询数据，不带分页
	 * @author 李颖洁  
	 * @date 2018年12月18日下午5:37:46
	 * @param daglSendFile
	 * @return
	 */
	@Override
	public List<DaglSendFile> getList(DaglSendFile daglSendFile) {
		//防止sql注入 王磊 2019年4月26日
		List<Object> paraList = new ArrayList<>();
		StringBuilder querySql = new StringBuilder();
		List<DaglSendFile> list = new ArrayList<>();
		//全部列表查询
		querySql.append("select t.*");
		querySql.append("	from dagl_send_file t");
		querySql.append("	where t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		querySql.append("   and t.cre_chushi_id = '"+UserUtil.getCruChushiId()+"' ");

		if(!StringUtils.isBlank(daglSendFile.getType())){
			querySql.append("   and t.type = ? ");
			paraList.add(daglSendFile.getType());
		}
		if(!StringUtils.isBlank(daglSendFile.getState())){
			querySql.append("   and t.state = ? ");
			paraList.add(daglSendFile.getState());
		}
		if(!StringUtils.isBlank(daglSendFile.getSecurityClass())){
			querySql.append("   and t.security_class = ? ");
			paraList.add(daglSendFile.getSecurityClass());
		}
		if(!StringUtils.isBlank(daglSendFile.getTitle())){
			querySql.append("   and t.title like ? ");
			paraList.add("%"+daglSendFile.getTitle()+"%");
		}
		if(!StringUtils.isBlank(daglSendFile.getFileDept())){
			querySql.append("   and t.file_dept like ? ");
			paraList.add("%"+daglSendFile.getFileDept()+"%");
		}
		String timeRange = daglSendFile.getTimeRange();
		if (StringUtils.isNotBlank(timeRange)) {
			String startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			String endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			querySql.append("   and t.created_date >= ? ");
			querySql.append("   and t.created_date <= ? ");
			paraList.add(startTime);
			paraList.add(endTime);
		}
		 list = jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(DaglSendFile.class),paraList.toArray());
		return list;
	}
	
	/**
	 * 
	 * TODO 档案进行删除时，更新对应发文的状态为：已退回待处置
	 * @author 王磊
	 * @Date 2019年4月11日 下午5:24:52
	 * @param tName
	 * @param creUserId
	 * @param column
	 * @param columnValue
	 * @return
	 */
	@Override
	public boolean updateStateByIdAndState(String tName, String creUserId, String column, String columnValue) {
		boolean isSuccess = false;
		try {
			String sql = "update DAGL_RECEIVE_FILE r set r.STATE='"+DAGLCommonConstants.WEN_DIAN_STATUS[2]+"',r.type='' where r.id in"
					+" (select "+tName+".recid from "+tName+" "+tName+" where cre_user_id ='"+creUserId+"' "+"and "+column+"='"+columnValue+"' )";
			jdbcTemplate.execute(sql);
			isSuccess = true;
		} catch (Exception e) {
			log.info("删除档案时，更新发文状态异常："+e.getMessage());
		}
		return isSuccess;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 文件管理归档操作，加入录入人id
	 * @Date 2019/7/20 14:20
	 * @Param [ids]
	 * @return net.sf.json.JSONObject
	 **/
	@Override
	public int placeOnFile(String lrrId, String data){
		List<Object> para = new ArrayList<>();
		int updateCount = 0;
		List<DaglSendFile> listData = JSON.parseArray(data,DaglSendFile.class);
		String ids = "";
		String mainTitle = "";
		for (DaglSendFile daglsendFile : listData) {
			ids += "'"+daglsendFile.getId()+"',";
			mainTitle +="【"+daglsendFile.getTitle()+"】<br/>";
		}

		ids = ids.substring(0,ids.length()-1);
		if(ids.length()>0){

			//1.更新录入人id，表示为已归档到中转库了
			para.add(lrrId);
			para.add(DAGLCommonConstants.FILE_SITE[1]);
			String inString = CommonUtils.solveSqlInjectionOfIn(ids,para);
			String sql ="update dagl_send_file t set t.lrr_id= ?,t.file_site=? where t.id in (" + inString + ")";
			updateCount = jdbcTemplate.update(sql,para.toArray());

			//2.改变文件状态为：已推送至档案管理员
			int num = updateState(ids,DAGLCommonConstants.WEN_DIAN_STATUS[4]);

			//3.给录入人发送消息
			NotityMessage ms = new NotityMessage();
			//系统id
			ms.setSenderId(ConfigConsts.SYSTEM_ID);
			//消息主题
			ms.setSubject("【"+UserUtil.getCruUserName()+"】 归档了新的发文档案");
			//消息内容
			ms.setContent("归档的档案有：<br/>"+mainTitle+"请及时处理！");
			//接收人id
			ms.setAccepterId(""+lrrId+"");
			//0：未读,1：已读
			ms.setStatus("0");
			//3：站内消息
			ms.setType("3");
			//打开消息的url
			ms.setMsgUrl("");
			notifyController.save(ms);
		}
		return updateCount;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 获取发文中转库数据
	 * @Date 2019/7/22 14:07
	 * @Param [daglSendFile]
	 * @return net.sf.json.JSONObject
	 **/
	@Override
	public List<DaglSendFile> getTransferRepositoryList(DaglSendFile daglSendFile){

		String sql="select t.* from dagl_send_file t where t.visible = '"+CommonConstants.VISIBLE[1]+"' and t.lrr_id = '"+UserUtil.getCruUserId()+"' and t.file_site = '"+DAGLCommonConstants.FILE_SITE[1]+"'";
		//保存参数
		List<String> paraList = new ArrayList<String>();
		if (StringUtils.isNotBlank(daglSendFile.getTitle())) {
			sql +=" and t.title like ? ";
			paraList.add("%"+daglSendFile.getTitle()+"%");
		}
		sql +=" order by t.cre_time desc ";
		log.info(sql);
		List<DaglSendFile> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DaglSendFile.class),paraList.toArray());
		return list;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 选择对照关系之后根据表名获取档号规则，回填页面表单样式
	 * @Date 2019/7/22 17:12
	 * @Param [tableName]
	 * @return net.sf.json.JSONObject
	 **/
	@Override
	public JSONObject getRuleFrom(String tableName){
		// 定义事务隔离级别，传播行为
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		//事务状态类，通过PlatformTransactionManager的getTransaction方法根据事务定义获取；获取事务状态后，Spring根据传播行为来决定如何开启事务
		TransactionStatus transactionStatus = transactionManager.getTransaction(def);

		JSONObject json = new JSONObject();
		json.put("flag", "0");

		try {
			con = jdbcTemplate.getDataSource().getConnection();
			//首先根据表名获取门类code
			String tablesRelationSql ="select s.*\n" +
							"  from tables_relation s\n" +
							" start with s.s_table_name = ?\n" +
							"connect by prior s.m_table_name = s.s_table_name\n" +
							" order by rownum asc";
			//得到子表及关联的信息
			List<Map<String,Object>> tablesRelationList = jdbcTemplate.queryForList(tablesRelationSql,tableName);

			if(3 == tablesRelationList.size()){
				//判断层数，三层的直接返回
				json.put("flag", "0");
				json.put("msg", "系统不支持三层表归档!");
			}else if(0 == tablesRelationList.size()){
				json.put("flag", "0");
				json.put("msg", "未查询到相关的门类信息，请联系管理员!");
			}else{
				json.put("ceng",tablesRelationList.size());
				//根据门类code，获取门类的id
				String categoryCode = tablesRelationList.get(tablesRelationList.size()-1).get("M_TABLE_NAME").toString();
				json.put("categoryCode",categoryCode);
				String queryCategoryIdSql ="select t.* from dagl_category_table t where t.category_code = ?";
				List<Map<String,Object>> categoryList = jdbcTemplate.queryForList(queryCategoryIdSql,categoryCode);
				if(categoryList.size()>0){
					//门类id
					String categoryId = categoryList.get(0).get("ID").toString();
					//该门类所属全宗
					String generalArchiveCode = categoryList.get(0).get("GENERAL_ARCHIVE_CODE").toString();
					json.put("generalArchiveCode",generalArchiveCode);
					//根据门类id获取档号规则
					String queryRulesql = "select * from dagl_party_num_rule where category_id = ? order by order_num asc";
					List<Map<String,Object>> queryForList = jdbcTemplate.queryForList(queryRulesql,categoryId);
					String queryForListJson = com.alibaba.fastjson.JSONObject.toJSONString(queryForList,SerializerFeature.WriteMapNullValue).replaceAll("null", "\"\"");
					json.put("rule",queryForListJson);

					//获取门类的字段信息，这里主要是看表单应该回填什么类型框（下拉/input），下拉的话，确定字典值
					String queryTableStructDescriptionSql ="select t.* from table_struct_description t where t.table_name =?";
					List<Map<String,Object>> tableStructDescriptionList = jdbcTemplate.queryForList(queryTableStructDescriptionSql,tableName);
					String tableStructDescriptionListJson = com.alibaba.fastjson.JSONObject.toJSONString(tableStructDescriptionList,SerializerFeature.WriteMapNullValue).replaceAll("null", "\"\"");
					json.put("tableStructDescriptionList",tableStructDescriptionListJson);
					json.put("flag", "1");
				}


			}
			transactionManager.commit(transactionStatus);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				//回滚事务
				transactionManager.rollback(transactionStatus);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return json;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 修改文件位置为：03：已经归档到了单位预立库
	 * @Date 2019/7/26 10:41
	 * @Param [ids, status]
	 * @return int
	 **/
	//@Override
	public int updateFileSite(String ids,String status) {
		//防止sql注入 王磊 2019年4月26日
		List<Object> paraList = new ArrayList<>();
		paraList.add(status);
		String jpql = "update  dagl_send_file t set t.file_site = ?  where t.id in ("+CommonUtils.solveSqlInjectionOfIn(ids, paraList)+")";
		log.info(jpql);
		return jdbcTemplate.update(jpql,paraList.toArray());
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 中转库退回文件管理
	 * @Date 2019/7/27 16:18
	 * @Param [lrrId, data]
	 * @return net.sf.json.JSONObject
	 **/
	public int returnBack( String id){
		List<Object> para = new ArrayList<>();
		int updateCount = 0;
		DaglSendFile daglSendFile = new DaglSendFile();
		daglSendFile.setId(id);
		DaglSendFile Data = getOne(daglSendFile);

		/*DaglReceiveFile dataItem; // 数据库中查询到的每条记录
		Map<String, List<DaglReceiveFile>> resultMap= new HashMap<String, List<DaglReceiveFile>>(); // 最终要的结果
		for(int i=0;i<listData.size();i++){
			dataItem = listData.get(i);
			if(resultMap.containsKey(dataItem.getCreUserId())){
				resultMap.get(dataItem.getCreUserId()).add(dataItem);
			}else{
				List<DaglReceiveFile> list = new ArrayList<>();
				list.add(dataItem);
				resultMap.put(dataItem.getCreUserId(),list);
			}
		}*/
		String mainTitle = "";
		//1.更新录入人id，表示为已归档到中转库了
		para.add(DAGLCommonConstants.FILE_SITE[0]);
		String inString = CommonUtils.solveSqlInjectionOfIn(id,para);
		String sql ="update dagl_send_file t set t.lrr_id= '' ,t.file_site=? where t.id in (" + inString + ")";
		updateCount = jdbcTemplate.update(sql,para.toArray());

		//2.改变文件状态为：已推送至档案管理员
		int num = updateState(id,DAGLCommonConstants.WEN_DIAN_STATUS[2]);

		//3.给录入人发送消息
		NotityMessage ms = new NotityMessage();
		//系统id
		ms.setSenderId(ConfigConsts.SYSTEM_ID);
		//消息主题
		ms.setSubject("退回文档");
		//消息内容
		ms.setContent("档案：【"+Data.getTitle()+"】已被退回，请及时处理！");
		//接收人id
		ms.setAccepterId(Data.getCreUserId());
		//0：未读,1：已读
		ms.setStatus("0");
		//3：站内消息
		ms.setType("3");
		//打开消息的url
		ms.setMsgUrl("");
		notifyController.save(ms);
		return updateCount;
	}
}
