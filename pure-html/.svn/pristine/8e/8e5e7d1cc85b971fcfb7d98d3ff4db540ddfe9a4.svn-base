package com.sinosoft.sinoep.modules.dagl.wdgl.services;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
	 * @param file
	 * @param id
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
	 * TODO 文件归档操作
	 * @author 李颖洁  
	 * @date 2018年11月16日下午2:13:31
	 * @param id
	 * @param list
	 */
	@Transactional  
	@Override
	public int tidyRecode(ContrastingRelations relations, String ids) throws Exception{
		//获取组卷标识的数据字典
		/*List<DataDictionary> dicts = service.getListByMark("09","1");
		Map<String, String> map = new HashMap<>();
		for (DataDictionary dic : dicts) {
			map.put(dic.getName(),dic.getCode());
		}*/
		String archiveFlag =""; //组卷标识
		String sqlRelation = "select t.*, t.rowid from tables_relation t start with s_table_name = ? connect by s_table_name = prior m_table_name";
		List<Map<String, Object>> query = jdbcTemplate.queryForList(sqlRelation,relations.getTargetName());
		//判断是否是多层表结构
		if(query.size()>1){
			archiveFlag = DAGLCommonConstants.ARCHIVE_FLAG[1];//未组卷
		}else{
			archiveFlag = DAGLCommonConstants.ARCHIVE_FLAG[0];//已组卷
		}
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
		
		int index = -1;//用于判断是否有系统唯一字段，具体的index，以便赋值
		boolean flag = false;//用于判断是否有立卷单位
		//防止sql注入 王磊 2019年4月26日
		List<Object> paraList = new ArrayList<>();
		String[] arrCol = new String[notNullData.size()];
		String[] arrval = new String[notNullData.size()];
		for(int k=0;k<notNullData.size();k++){
			arrCol[k] = notNullData.get(k).getColumnName();
			if(notNullData.get(k).getColumnName().indexOf("archive_flag")!=-1){//archiveFlag组卷标识字段
				arrval[k] = "'"+archiveFlag+"'";
			}else if(notNullData.get(k).getColumnName().indexOf("recid")!=-1){//recid 系统唯一字段
				index = k;
			}else if(notNullData.get(k).getColumnName().indexOf("basefolder_unit")!=-1){//basefolder_unit 立卷单位字段
				arrval[k] = "?";//赋值为用户页面选择的立卷单位，王磊 20190211
				flag = true;
				paraList.add(relations.getLjdwName());
			}else{
				switch (notNullData.get(k).getColumnType()) {
				case "1"://String
					arrval[k] = "' '";
					break;
				case "2"://Integer
					arrval[k] = "'0'";
					break;
				case "3"://Double
					arrval[k] = "'0.0'";
					break;
				case "4"://Date(当前时间)
					arrval[k] = DateUtil.COMMON_FULL.getDateText(new Date());
					break;
				case "5"://String
					arrval[k] = "' '";
					break;
				default:
					break;
				}
			}
		}
		try {
			Class<?> c = Class.forName("com.sinosoft.sinoep.modules.dagl.wdgl.entity.DaglSendFile");
			//定义sql数组
			String[] sql = new String[list.size()];
			
			String notNullColumns = "";//没有在对照表中设置并不为空的字段
			String notNullVal = "";//保存不为空字段的值
			String targetColumns = "";//目标表已对照的字段
			for (int i=0;i<list.size();i++) {//文件信息（多行）
                List<Object> para = new ArrayList<>();
				notNullColumns = "";
				notNullVal = "";
				targetColumns = "";
				//设置插入语句表名
				List<String> allTables = bmglService.findAllTables();
				for(int j= 0;j<allTables.size();j++){
					if(allTables.get(j).equals(relations.getTargetName())){
						sql[i] = "insert into "+allTables.get(j);
					}
				}
				//共有字段赋值
				//创建人姓名和id、立卷单位id使用用户页面选择的内容 王磊 20190211
				String vals = "(?,?,'"+list.get(i).getCreDeptName()+"',"
						+ "'"+list.get(i).getCreDeptId()+"',?,'"+list.get(i).getCreChushiName()+"','"+list.get(i).getCreJuId()+"',"
								+ "	'"+list.get(i).getCreJuName()+"','"+list.get(i).getCreTime()+"','"+list.get(i).getVisible()+"',";//保存一行数据的值
				para.add(relations.getLrrName());
				para.add(relations.getLrrId());
				para.add(relations.getLjdwMark());
				if(index >-1){
					arrval[index] = "'"+list.get(i).getId()+"'";
				}
				for (String str : arrval) {
					notNullVal += str+",";
				}
				for (String col : arrCol) {
					notNullColumns += col+",";
				}
				vals += notNullVal;
				for (DataContrast dc : dataList) {//字段对应信息
					if(StringUtils.isNotBlank(dc.getTargetColumn())){
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
							if(val==null||val==""){
								if(dc.getTargetColumn().indexOf("archive_flag")!=-1){
									val += archiveFlag;
								}else{
									switch (dc.getTargetType()) {
									case "1"://String
										val = " ";
										break;
									case "2"://Integer
										val = 1;
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
								//调整类型转化方式 王磊 20190419
								switch (dc.getTargetType()) {
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
					}else{
						continue;
					}
				}
				String endColumns = "";
				endColumns = commonalityColumns+notNullColumns+targetColumns;
				//立卷单位使用用户从页面选择的内容 王磊 20190211
				if(!flag){
					endColumns += "basefolder_unit,";
					vals += "?,";
					para.add(relations.getLjdwName());
				}
				//添加档案的档案实体状态为：待提交 王磊20190409 开始
				endColumns += "archive_entity_status,";
				vals += "'"+DAGLCommonConstants.ARCHIVE_ENTITY_STATUS[0]+"',";
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
			}
			//int[] batchUpdate = jdbcTemplate.batchUpdate(sql);
			int num = updateState(ids,DAGLCommonConstants.WEN_DIAN_STATUS[1]);
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
}
