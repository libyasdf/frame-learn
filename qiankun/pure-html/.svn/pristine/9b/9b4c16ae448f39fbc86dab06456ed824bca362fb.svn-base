package com.sinosoft.sinoep.modules.dagl.wdgl.services;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.sinosoft.sinoep.modules.dagl.wdgl.dao.DaglConfigDao;
import com.sinosoft.sinoep.modules.dagl.wdgl.entity.DaglConfig;
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
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dagl.constant.DAGLCommonConstants;
import com.sinosoft.sinoep.modules.dagl.wdgl.dao.DaglReceiveFileDao;
import com.sinosoft.sinoep.modules.dagl.wdgl.entity.DaglReceiveFile;
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
 * TODO 文电管理业务层实现类
 * @author 李颖洁  
 * @date 2018年11月10日  下午6:10:31
 */
@Service
public class DaglReceiveFileServiceImpl implements DaglReceiveFileService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DaglReceiveFileDao dao;
	
	@Autowired
	private DataDictionaryService service;
	
	@Autowired
	private ContrastService contrastService;

	@Autowired
	private DaglConfigDao daglConfigDao;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/** 
	 * TODO 保存文件信息
	 * @author 李颖洁  
	 * @date 2018年11月12日上午10:38:05
	 * @param receiveFile
	 * @return DaglReceiveFile
	 */
	@Override
	public DaglReceiveFile editReceiveFile(DaglReceiveFile receiveFile) {
		if(receiveFile.getId()==null ||receiveFile.getId()==""){
			receiveFile.setCreUserId(UserUtil.getCruUserId());
			receiveFile.setCreUserName(UserUtil.getCruUserName());
			receiveFile.setCreDeptId(UserUtil.getCruDeptId());
			receiveFile.setCreDeptName(UserUtil.getCruDeptName());
			receiveFile.setCreChushiId(UserUtil.getCruChushiId());
			receiveFile.setCreChushiName(UserUtil.getCruChushiName());
			receiveFile.setCreJuId(UserUtil.getCruJuId());
			receiveFile.setCreJuName(UserUtil.getCruJuName());
			receiveFile.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			receiveFile.setVisible(CommonConstants.VISIBLE[1]);
			try {
				if(DAGLCommonConstants.WEN_DIAN_TYPE[0].equals(receiveFile.getType())){
					receiveFile.setState(DAGLCommonConstants.WEN_DIAN_STATUS[0]);
				}else{
					receiveFile.setState("");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			receiveFile = dao.save(receiveFile);
		}else{
			DaglReceiveFile preFile = getOne(receiveFile);
			preFile.setTitle(receiveFile.getTitle());
			preFile.setFileNum(receiveFile.getFileNum());
			preFile.setPageNum(receiveFile.getPageNum());
			preFile.setFileDept(receiveFile.getFileDept());
			preFile.setSecurityClass(receiveFile.getSecurityClass());
			preFile.setCreatedDate(receiveFile.getCreatedDate());
			preFile.setNote(receiveFile.getNote());
			preFile.setType(receiveFile.getType());
			preFile.setSerialNum(receiveFile.getSerialNum());
			preFile.setFileTime(receiveFile.getFileTime());
			preFile.setQuantity(receiveFile.getQuantity());
			preFile.setBarcode(receiveFile.getBarcode());
			preFile.setFileType(receiveFile.getFileType());
			preFile.setZhusUnit(receiveFile.getZhusUnit());
			preFile.setUrgencyDegree(receiveFile.getUrgencyDegree());
			preFile.setHandleUnit(receiveFile.getHandleUnit());
			preFile.setReceipient(receiveFile.getReceipient());
			try {
				if(DAGLCommonConstants.WEN_DIAN_TYPE[0].equals(receiveFile.getType())){
					preFile.setState(DAGLCommonConstants.WEN_DIAN_STATUS[0]);
				}else{
					preFile.setState("");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			receiveFile = dao.save(preFile);
		}
		return receiveFile;
	}

	/** 
	 * TODO 根据id获取一个文件
	 * @author 李颖洁  
	 * @date 2018年11月12日上午11:20:05
	 * @param receiveFile
	 * @return DaglReceiveFile
	 */
	@Override
	public DaglReceiveFile getOne(DaglReceiveFile receiveFile) {
		return dao.findOne(receiveFile.getId());
	}

	/**
	 * TODO  删除一个文件信息
	 * @author 李颖洁  
	 * @date 2018年9月5日上午9:14:02
	 * @param  receiveFile
	 * @return int
	 */
	@Override
	public int deleteOne(DaglReceiveFile receiveFile) {
		String jpql = "delete  from DaglReceiveFile t  where t.id = ?";
		return dao.update(jpql, receiveFile.getId());
	}

	/** 
	 * TODO 删除多个文件信息
	 * @author 李颖洁  
	 * @date 2018年11月12日上午11:58:19
	 * @param ids 多个文件的id
	 * @return int
	 */
	@Override
	public int batchDelete(String ids) {
		String jpql = "delete from DaglReceiveFile t  where t.id in ("+ids+") ";
		return dao.update(jpql);
	}

	/** 
	 * TODO 获取文件列表带分页 
	 * @author 李颖洁  
	 * @date 2018年11月12日下午2:13:29
	 * @param receiveFile
	 * @param pageImpl
	 * @return
	 */
	@Override
	public Page<DaglReceiveFile> list(DaglReceiveFile receiveFile, PageImpl pageImpl,Pageable pageable) {
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		//全部列表查询
		querySql.append("select new DaglReceiveFile(t.id,t.title,t.fileNum,t.pageNum,t.fileDept,t.securityClass,t.createdDate,t.state,t.note,t.type,t.serialNum"
				+ "	,t.fileTime,t.quantity,t.barcode,t.fileType,t.zhusUnit,t.urgencyDegree,t.handleUnit,t.receipient)");
		querySql.append("	from DaglReceiveFile t");
		querySql.append("	where t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		if(!StringUtils.isBlank(receiveFile.getCreChushiId())){
			querySql.append("   and t.creChushiId = ?");
			para.add(receiveFile.getCreChushiId());
		}
		if(!StringUtils.isBlank(receiveFile.getType())){
			querySql.append("   and t.type = ?");
			para.add(receiveFile.getType());
		}
		if(!StringUtils.isBlank(receiveFile.getState())){
			querySql.append("   and t.state = ?");
			para.add(receiveFile.getState());
		}
		if(!StringUtils.isBlank(receiveFile.getSecurityClass())){
			querySql.append("   and t.securityClass = ?");
			para.add(receiveFile.getSecurityClass());
		}
		if(!StringUtils.isBlank(receiveFile.getTitle())){
			querySql.append("   and t.title like '%"+receiveFile.getTitle()+"%'");
		}
		if(!StringUtils.isBlank(receiveFile.getFileDept())){
			querySql.append("   and t.fileDept like '%"+receiveFile.getFileDept()+"%'");
		}
		String timeRange = receiveFile.getTimeRange();
		if (StringUtils.isNotBlank(timeRange)) {
			String startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			String endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			querySql.append("   and t.createdDate >= "+startTime);
			querySql.append("   and t.createdDate <= "+endTime);
		}
		
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.creTime desc) ");
		}else{
			querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}
		Page<DaglReceiveFile> page = dao.query(querySql.toString(), pageable,para.toArray());
		return page;
	}

	/** 
	 * TODO 获取归档列表数据
	 * @author 李颖洁  
	 * @date 2018年11月12日下午2:13:29
	 * @param receiveFile
	 * @return
	 */
	@Override
	public List<DaglReceiveFile> getTidyRecodedList(String ids) {
		String querySql = "";
		//全部列表查询
		querySql = "select t.* from dagl_receive_file t	where t.visible = '"+CommonConstants.VISIBLE[1]+"' and t.id in ("+ids+")";
		List<DaglReceiveFile> page = jdbcTemplate.query(querySql,new BeanPropertyRowMapper<>(DaglReceiveFile.class) );
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
	            List<DaglReceiveFile> list = new ArrayList<>();
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
	            				DaglReceiveFile daglReceiveFile = new DaglReceiveFile();
	            				daglReceiveFile.setCreUserId(UserUtil.getCruUserId());
	            				daglReceiveFile.setCreUserName(UserUtil.getCruUserName());
	            				daglReceiveFile.setCreDeptId(UserUtil.getCruDeptId());
	            				daglReceiveFile.setCreDeptName(UserUtil.getCruDeptName());
	            				daglReceiveFile.setCreChushiId(UserUtil.getCruChushiId());
	            				daglReceiveFile.setCreChushiName(UserUtil.getCruChushiName());
	            				daglReceiveFile.setCreJuId(UserUtil.getCruJuId());
	            				daglReceiveFile.setCreJuName(UserUtil.getCruJuName());
	            				daglReceiveFile.setVisible(CommonConstants.VISIBLE[1]);
	            				daglReceiveFile.setCreTime(JDateToolkit.getNowDate4());
	            				//导入
	            				daglReceiveFile.setFileNum(fileNum);
	            				daglReceiveFile.setSerialNum(serialNum);
	            				daglReceiveFile.setNote(note);
	            				//验证收发文日期不为空
	            				if(StringUtils.isNotBlank(fileTime)){
	            					//不为空
	            					daglReceiveFile.setFileTime(fileTime);
	            				}else{
	            					errorCount++;
	            					if(errContent.indexOf("收/发文日期")==-1){
	            						errContent += "收/发文日期不能为空！<br/>";
	            					}
	            				}
	            				//验证标题不为空
	            				if(StringUtils.isNotBlank(title)){
	            					//不为空
	            					daglReceiveFile.setTitle(title);
	            				}else{
	            					errorCount++;
	            					if(errContent.indexOf("文件标题")==-1){
	            						errContent += "文件标题不能为空！<br/>";
	            					}
	            				}
	            				//验证页数为数字
	            				if(pageNum.matches("[0-9]+")){
	            					daglReceiveFile.setPageNum(Integer.valueOf(pageNum));
	            				}else{
	            					errorCount++;
	            					if(errContent.indexOf("页数")==-1){
	            						errContent += "页数必须是数字！<br/>";
	            					}
	            				}
	            				//验证份数为数字
	            				if(quantity.matches("[0-9]+")){
	            					daglReceiveFile.setQuantity(Integer.valueOf(quantity));
	            				}else{
	            					errorCount++;
	            					if(errContent.indexOf("份数")==-1){
	            						errContent += "份数必须是数字！<br/>";
	            					}
	            				}
	            				//验证页数是否纯数字
	            				if(StringUtils.isNotBlank(createdDate)){
	            					if(createdDate.matches("[0-9]+")){
	            						daglReceiveFile.setCreatedDate(createdDate);
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
	            					daglReceiveFile.setFileDept(fileDept);
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
	            					daglReceiveFile.setSecurityClass(sercurityClass);
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
	            					daglReceiveFile.setType(typeMap.get(type));
	            					if("需归档".equals(type)){
	            						daglReceiveFile.setState(DAGLCommonConstants.WEN_DIAN_STATUS[0]);
	            					}
	            				}else{
	            					errorCount++;
	            					if(errContent.indexOf("文件类型")==-1){
	            						errContent += "文件类型不能为空！<br/>";
	            					}
	            				}
	            				list.add(daglReceiveFile);
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
		String sqlRelation = "select t.*, t.rowid from tables_relation t start with s_table_name = '"+relations.getTargetName()+"' connect by s_table_name = prior m_table_name";
		List<Map<String, Object>> query = jdbcTemplate.queryForList(sqlRelation);
		//判断是否是多层表结构
		if(query.size()>1){
			archiveFlag = DAGLCommonConstants.ARCHIVE_FLAG[1];
		}else{
			archiveFlag = DAGLCommonConstants.ARCHIVE_FLAG[0];
		}
		//获取选中数据的全部信息
		List<DaglReceiveFile> list = getTidyRecodedList(ids);
		
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
		String[] arrCol = new String[notNullData.size()];
		String[] arrval = new String[notNullData.size()];
		for(int k=0;k<notNullData.size();k++){
			arrCol[k] = notNullData.get(k).getColumnName();
			if(notNullData.get(k).getColumnName().indexOf("archive_flag")!=-1){//archiveFlag组卷标识字段
				arrval[k] = "'"+archiveFlag+"'";
			}else if(notNullData.get(k).getColumnName().indexOf("recid")!=-1){//recid 系统唯一字段
				index = k;
			}else if(notNullData.get(k).getColumnName().indexOf("basefolder_unit")!=-1){//basefolder_unit 立卷单位字段
				arrval[k] = "'"+UserUtil.getCruChushiName()+"'";//赋值为当前登录人的处室id
				flag = true;
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
			Class<?> c = Class.forName("com.sinosoft.sinoep.modules.dagl.wdgl.entity.DaglReceiveFile");
			//定义sql数组
			String[] sql = new String[list.size()];
			
			String notNullVal = "";//保存不为空字段的值
			String targetColumns = "";//目标表已对照的字段
			String notNullColumns = "";//没有在对照表中设置并不为空的字段
			for (int i=0;i<list.size();i++) {//文件信息（多行）
				notNullVal = "";
				targetColumns = "";
				notNullColumns = "";
				//设置插入语句表名
				sql[i] = "insert into "+relations.getTargetName();
				
				//共有字段赋值
				String vals = "('"+list.get(i).getCreUserName()+"','"+list.get(i).getCreUserId()+"','"+list.get(i).getCreDeptName()+"',"
						+ "'"+list.get(i).getCreDeptId()+"','"+list.get(i).getCreChushiId()+"','"+list.get(i).getCreChushiName()+"','"+list.get(i).getCreJuId()+"',"
								+ "	'"+list.get(i).getCreJuName()+"','"+list.get(i).getCreTime()+"','"+list.get(i).getVisible()+"',";//保存一行数据的值
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
								switch (dc.getTargetType()) {
								case "1"://String
									val = (String)val;
									break;
								case "2"://Integer
									val = (Integer)val;
									break;
								case "3"://Double
									val = (Double)val;
									break;
								case "4"://Date(???)
									val = (Date)val;
									break;
								case "5"://String(8位数字组成)
									val = (String)val;
									break;
								default:
									break;
								}
							}
							val = "'"+val+"'";
							vals += val+",";
						} catch (Exception e) {
							log.info("异常");
							log.error(e.getMessage(),e);
						}
					}else{
						continue;
					}
				}
				String endColumns = "";
				endColumns = commonalityColumns+notNullColumns+targetColumns;
				if(!flag){
					endColumns += "basefolder_unit,";
					vals += "'"+UserUtil.getCruChushiName()+"',";
				}
				sql[i] += "("+endColumns.substring(0, endColumns.length()-1)+") values ";
				//拼接一行数据的值
				sql[i] += vals.substring(0,vals.length()-1)+")";
				//最终插入语句
				log.info(sql[i]);
			}
			int[] batchUpdate = jdbcTemplate.batchUpdate(sql);
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
	 * TODO 更改数据状态
	 * @author 李颖洁  
	 * @date 2018年11月19日下午6:13:45
	 * @param ids
	 */
	@Override
	public int updateState(String ids,String status) {
		if(ids.indexOf("'")==-1){
			ids = "'"+ids.replaceAll(",", "','")+"'";
		}
		String jpql = "update  DaglReceiveFile t set t.state = '"+status+"' where t.id in ("+ids+")";
		log.info(jpql);
		return dao.update(jpql);
	}

	@Override
	public DaglConfig saveConfig(DaglConfig daglConfig) {
		daglConfig = daglConfigDao.save(daglConfig);
		return daglConfig;
	}

	@Override
	public DaglConfig getConfig(DaglConfig daglConfig) {
		StringBuffer str = new StringBuffer(" from DaglConfig t where id = '" + daglConfig.getId() + "'");
		Query query =  daglConfigDao.getEntityManager().createQuery(str.toString());
		List<DaglConfig> list = query.getResultList();
		return list.get(0);
	}

	@Override
	public List<DaglReceiveFile> getDaglReceiveFileList(DaglReceiveFile daglReceiveFile, String ids) {
		String[] strIds = ids.split(",");
		StringBuilder querySql = new StringBuilder();
		List<DaglReceiveFile> list = new ArrayList<>();
		//全部列表查询
		querySql.append("select new DaglReceiveFile(t.id,t.title,t.fileNum,t.pageNum,t.fileDept,t.securityClass,t.createdDate,t.state,t.note,t.type,t.serialNum"
				+ "	,t.fileTime,t.quantity,t.barcode,t.fileType,t.zhusUnit,t.urgencyDegree,t.handleUnit,t.receipient)");
		querySql.append("	from DaglReceiveFile t");
		querySql.append("	where t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		if(!ids.equals("")){
			querySql.append("   and t.id in (");
			for(int i = 0;i<strIds.length;i++){
				if(i == strIds.length-1){
					querySql.append("'" + strIds[i] + "'");
				}else{
					querySql.append("'" + strIds[i] + "',");
				}
			}
			querySql.append(")");
		}else{
			if(!StringUtils.isBlank(daglReceiveFile.getCreChushiId())){
				querySql.append("   and t.creChushiId = '" + daglReceiveFile.getCreChushiId() + "'");
			}
			if(!StringUtils.isBlank(daglReceiveFile.getType())){
				querySql.append("   and t.type = '" + daglReceiveFile.getType() + "'");
			}
			if(!StringUtils.isBlank(daglReceiveFile.getState())){
				querySql.append("   and t.state = '" + daglReceiveFile.getState() + "'");
			}
			if(!StringUtils.isBlank(daglReceiveFile.getSecurityClass())){
				querySql.append("   and t.securityClass = '" + daglReceiveFile.getSecurityClass() + "'");
			}
			if(!StringUtils.isBlank(daglReceiveFile.getTitle())){
				querySql.append("   and t.title like '%"+daglReceiveFile.getTitle()+"%'");
			}
			if(!StringUtils.isBlank(daglReceiveFile.getFileDept())){
				querySql.append("   and t.fileDept like '%"+daglReceiveFile.getFileDept()+"%'");
			}
			String timeRange = daglReceiveFile.getTimeRange();
			if (StringUtils.isNotBlank(timeRange)) {
				String startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
				String endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
				querySql.append("   and t.createdDate >= "+startTime);
				querySql.append("   and t.createdDate <= "+endTime);
			}
		}
		Query query = dao.getEntityManager().createQuery(querySql.toString());
		list = query.getResultList();
		return list;
	}

	@Override
	public List<DaglReceiveFile> getDestoryList(String year) {
		StringBuilder querySql = new StringBuilder();
		List<DaglReceiveFile> list = new ArrayList<>();
		//全部列表查询
		querySql.append("select new DaglReceiveFile(t.id,t.title,t.fileNum,t.pageNum,t.fileDept,t.securityClass,t.createdDate,t.state,t.note,t.type,t.serialNum"
				+ "	,t.fileTime,t.quantity,t.barcode,t.fileType,t.zhusUnit,t.urgencyDegree,t.handleUnit,t.receipient)");
		querySql.append("	from DaglReceiveFile t");
		querySql.append("	where t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		querySql.append("	and t.type = '03'");
		querySql.append("   and t.creChushiId = '" + UserUtil.getCruChushiId() + "'");
		if(!StringUtils.isBlank(year)){
			querySql.append("   and substr(t.createdDate,1,4) = '" + year + "'");
		}
		Query query = dao.getEntityManager().createQuery(querySql.toString());
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
		String jpql = "update DaglReceiveFile t set t.type ='"+type+"' ";
		if(DAGLCommonConstants.WEN_DIAN_TYPE[0].equals(type)){
			jpql += ",t.state='"+DAGLCommonConstants.WEN_DIAN_STATUS[0]+"'";
		}else{
			jpql += ",t.state=''";
		}
		jpql += " where 1=1 ";
		if(ids.length()>0){
			jpql += " and (";
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				jpql += " t.id = '"+id+"' or";
			}
			jpql = jpql.substring(0,jpql.length()-2)+")";
			jpql += " and (t.state<> '"+DAGLCommonConstants.WEN_DIAN_STATUS[1]+"' or t.state is null)";
		}
		log.info(jpql);
		return dao.update(jpql);
	}

	/** 
	 * TODO 查询list数据，不带分页
	 * @author 李颖洁  
	 * @date 2018年12月18日下午5:37:46
	 * @param receiveFile
	 * @return
	 */
	@Override
	public List<DaglReceiveFile> getList(DaglReceiveFile receiveFile) {
		StringBuilder querySql = new StringBuilder();
		List<DaglReceiveFile> list = new ArrayList<>();
		//全部列表查询
		querySql.append("select t.*");
		querySql.append("	from dagl_receive_file t");
		querySql.append("	where t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		querySql.append("   and t.cre_chushi_id = "+UserUtil.getCruChushiId());
		if(!StringUtils.isBlank(receiveFile.getType())){
			querySql.append("   and t.type = "+receiveFile.getType());
		}
		if(!StringUtils.isBlank(receiveFile.getState())){
			querySql.append("   and t.state = "+receiveFile.getState());
		}
		if(!StringUtils.isBlank(receiveFile.getSecurityClass())){
			querySql.append("   and t.security_class = '"+receiveFile.getSecurityClass()+"'");
		}
		if(!StringUtils.isBlank(receiveFile.getTitle())){
			querySql.append("   and t.title like '%"+receiveFile.getTitle()+"%'");
		}
		if(!StringUtils.isBlank(receiveFile.getFileDept())){
			querySql.append("   and t.file_dept like '%"+receiveFile.getFileDept()+"%'");
		}
		String timeRange = receiveFile.getTimeRange();
		if (StringUtils.isNotBlank(timeRange)) {
			String startTime = timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim();
			String endTime = timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim();
			querySql.append("   and t.created_date >= "+startTime);
			querySql.append("   and t.created_date <= "+endTime);
		}
		 list = jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(DaglReceiveFile.class));
		return list;
	}
}
