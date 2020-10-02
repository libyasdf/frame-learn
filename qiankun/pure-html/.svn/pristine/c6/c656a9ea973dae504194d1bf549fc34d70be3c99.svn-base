package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.web.multipart.MultipartFile;

import com.csvreader.CsvReader;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.component.importFile.util.ImportFileUtil;
import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;
import com.sinosoft.sinoep.modules.system.config.dictionary.services.DataDictionaryService;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.dao.KqAttendanceDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.dao.KqLogDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.AttendanceHelp;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqAttendance;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.KqLog;
import com.sinosoft.sinoep.user.util.UserUtil;

import workflow.common.JDateToolkit;

/**
 * 
 * TODO 出勤表serviceImpl
 * @author 冯超
 * @Date 2018年5月30日 上午11:06:03
 */
@Service
@Transactional
public class KqAttendanceServiceImpl implements KqAttendanceService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private KqAttendanceDao kqAttendanceDao;
	@Autowired
	private KqLogDao kqLogDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private DataDictionaryService dataDictionaryService;
	
	/**
	 * 
	 * TODO 导入出勤信息
	 * @author 冯超
	 * @Date 2018年5月30日 上午11:17:11
	 * @param filePath
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@Override
	public String importInfo(String filePath, MultipartFile file) throws IOException {
		String msg = "导入失败！";
		InputStream is = file.getInputStream();
		Workbook workbook = null;
		try {
			workbook = ImportFileUtil.getWorkBook(is, filePath);
			int sheetNum = ImportFileUtil.getSheetNumByWorkBook(workbook);
			int successNum = 0;//成功的条数
			int errorNum = 0;//失败的条数
			int repeatNum = 0;//重复的条数
			StringBuilder remark = new StringBuilder();
			for (int sheetIdex = 0; sheetIdex < sheetNum; sheetIdex++) {
				Sheet sheet = workbook.getSheetAt(sheetIdex);
				if (sheet == null) {
					continue;
				}
				for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
					Row row = sheet.getRow(rowIndex);
					if (row == null) {
						continue;
					}
					KqAttendance kqAttendance = new KqAttendance();
					kqAttendance.setCreUserId(UserUtil.getCruUserId());
					kqAttendance.setCreUserName(UserUtil.getCruUserName());
					kqAttendance.setCreDeptId(UserUtil.getCruDeptId());
					kqAttendance.setCreDeptName(UserUtil.getCruDeptName());
					kqAttendance.setCreChuShiId(UserUtil.getCruChushiId());
					kqAttendance.setCreChuShiName(UserUtil.getCruChushiName());
					kqAttendance.setCreJuId(UserUtil.getCruJuId());
					kqAttendance.setCreJuName(UserUtil.getCruJuName());
					kqAttendance.setVisible(CommonConstants.VISIBLE[1]);
					kqAttendance.setCreTime(JDateToolkit.getNowDate4());
					//判断日期格式
					String RegexNull = "\\s+";//空格
					String Regex="^\\d{4}-\\d{2}-\\d{2}";//2018-01-01
					String RegexTime="^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}";//2018-01-01 08:20:20
					String importDate = ImportFileUtil.getRowCellValue(row, (short) 1);
					String careTime = ImportFileUtil.getRowCellValue(row, (short) 3);
					careTime = careTime.trim();//做非空处理
					importDate = importDate.trim();//做非空处理
					careTime = careTime.replaceAll(RegexNull, " ");//多个空格--一个
					//判断日期
					Pattern p=Pattern.compile(Regex);
					Matcher matcher=p.matcher(importDate);
					if(!matcher.matches()){
						errorNum += 1;
						int hang = rowIndex+1;
						if(remark.length() < 2470){
							remark.append(hang+"行B列数据格式错误   ");
						}
						
						continue;
					}
					//判断打卡日期
					Pattern pRegexTime=Pattern.compile(RegexTime);
					Matcher matcherRegexTime = pRegexTime.matcher(careTime);
					if(!matcherRegexTime.matches()){
						errorNum += 1;
						int hang = rowIndex+1;
						if(remark.length() < 2470){
							remark.append(hang+"行D列数据格式错误   ");
						}
						continue;
					}
					//判断是否重复
					String importId = ImportFileUtil.getRowCellValue(row, (short) 0);
					List<KqAttendance> kqList = getKqAttendance(importId);
					if(kqList.size()>0){
						repeatNum += 1;
						continue;
					}
					kqAttendance.setImportId(ImportFileUtil.getRowCellValue(row, (short) 0));
					kqAttendance.setImportDate(importDate);
					String gateNumber = ImportFileUtil.getRowCellValue(row, (short) 2);
					gateNumber = gateNumber.trim();
					kqAttendance.setGateNumber(gateNumber);
					kqAttendance.setCareTime(careTime);
					kqAttendance.setCardNumber(ImportFileUtil.getRowCellValue(row, (short) 4));
					
					kqAttendanceDao.save(kqAttendance);
					successNum += 1;
					msg = "导入成功！";
				}
			}
			//形成日志
			KqLog kqLog = new KqLog();
			kqLog.setCreUserId(UserUtil.getCruUserId());
			kqLog.setCreUserName(UserUtil.getCruUserName());
			kqLog.setCreDeptId(UserUtil.getCruDeptId());
			kqLog.setCreDeptName(UserUtil.getCruDeptName());
			kqLog.setCreChuShiId(UserUtil.getCruChushiId());
			kqLog.setCreChuShiName(UserUtil.getCruChushiName());
			kqLog.setCreJuId(UserUtil.getCruJuId());
			kqLog.setCreJuName(UserUtil.getCruJuName());
			kqLog.setVisible(CommonConstants.VISIBLE[1]);
			kqLog.setCreTime(JDateToolkit.getNowDate4());
			kqLog.setSuccessNum(successNum+"");
			kqLog.setErrorNum(errorNum+"");
			kqLog.setRemark(remark.toString());
			kqLog.setRepeatNum(repeatNum+"");
			if(errorNum>0){
				kqLog.setErrorReason("数据格式错误");
			}
			kqLogDao.save(kqLog);
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "导入失败！";
		}
		return msg;
	}
	
	/**
	 * 
	 * TODO 导入CSV 
	 * IPI:http://javacsv.sourceforge.net/com/csvreader/CsvReader.html
	 * @author 冯超
	 * @Date 2018年6月3日 下午2:58:55
	 * @param file
	 * @return
	 */
	public String importCSV(MultipartFile file){
		String msg = "导入成功！";
		try {
			int successNum = 0;//成功的条数
			int errorNum = 0;//失败的条数
			int repeatNum = 0;//重复的条数
			StringBuilder remark = new StringBuilder();
			ArrayList<String[]> csvList = new ArrayList<String[]>(); //用来保存数据
			List<AttendanceHelp> attendanceHelpList = new ArrayList<AttendanceHelp>();// 用来判重和批量保存数据
			InputStream in = file.getInputStream(); 
			CsvReader reader = new CsvReader(in,',',Charset.forName("Utf-8"));    //一般用这编码读就可以了
			//reader.readHeaders(); // 跳过表头   如果需要表头的话，不要写这句。
			while(reader.readRecord()){ //逐行读入除表头的数据      
				csvList.add(reader.getValues());  
	        }
			reader.close();
			for(int row=0;row<csvList.size();row++){
				
				String importId = csvList.get(row)[0]; //取得第row行第0列的数据
				String importDate = csvList.get(row)[1];
				String gateNumber = csvList.get(row)[2];
				String careTime = csvList.get(row)[3];
				String cardNumber = csvList.get(row)[4];
				//如果id为空
				if (importId==null || "".equals(importId)) {
					errorNum += 1;
					int hang = row+1;
					if(remark.length() < 2470){
						if(errorNum % 3== 0){
							remark.append(hang+"行1列数据不能为空\t\t\n");
						}else{
							remark.append(hang+"行1列数据不能为空\t\t");
						}
					}
					continue;
				}
				//日期为空
				if (importDate==null || "".equals(importDate)) {
					errorNum += 1;
					int hang = row+1;
					if(remark.length() < 2470){
						if(errorNum % 3== 0){
							remark.append(hang+"行2列数据不能为空\t\t\n");
						}else{
							remark.append(hang+"行2列数据不能为空\t\t");
						}
					}
					continue;
				}
				//闸机号
				if (gateNumber==null || "".equals(gateNumber)) {
					errorNum += 1;
					int hang = row+1;
					if(remark.length() < 2470){
						if(errorNum % 3== 0){
							remark.append(hang+"行3列数据不能为空\t\t\n");
						}else{
							remark.append(hang+"行3列数据不能为空\t\t");
						}
					}
					continue;
				}
				 //打开日期
				int  index=careTime.indexOf(".");
				if(index!=-1){
					careTime=careTime.substring(0, careTime.indexOf("."));
				}
				if (careTime==null || "".equals(careTime)) {
					errorNum += 1;
					int hang = row+1;
					if(remark.length() < 2470){
						if(errorNum % 3== 0){
							remark.append(hang+"行4列数据不能为空\t\t\n");
						}else{
							remark.append(hang+"行4列数据不能为空\t\t");
						}
					}
					continue;
				}
				//卡号
				if (cardNumber==null || "".equals(cardNumber)) {
					errorNum += 1;
					int hang = row+1;
					if(remark.length() < 2470){
						if(errorNum % 3== 0){
							remark.append(hang+"行5列数据不能为空\t\t\n");
						}else{
							remark.append(hang+"行5列数据不能为空\t\t");
						}
					}
					continue;
				}
				
				//判断日期格式
				String Regex="^\\d{4}-\\d{2}-\\d{2}";//2018-01-01
				Pattern p=Pattern.compile(Regex);
				Matcher matcher=p.matcher(importDate);
				if(!matcher.matches()){
					errorNum += 1;
					int hang = row+1;
					if(remark.length() < 2470){
						if(errorNum % 3== 0){
							remark.append(hang+"行2列数据格式错误\t\t\n");
						}else{
							remark.append(hang+"行2列数据格式错误\t\t");
						}
					}
					continue;
				}
				//判断打卡日期格式
				String RegexNull = "\\s+";//空格
				careTime = careTime.trim();//做非空处理
				careTime = careTime.replaceAll(RegexNull, " ");//多个空格--一个
				String RegexTime="^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}";//2018-01-01 08:20:20
				Pattern pRegexTime=Pattern.compile(RegexTime);
				Matcher matcherRegexTime = pRegexTime.matcher(careTime);
				if(!matcherRegexTime.matches()){
					errorNum += 1;
					int hang = row+1;
					if(remark.length() < 2470){
						if(errorNum % 3== 0){
							remark.append(hang+"行4列数据格式错误\t\t\n");
						}else{
							remark.append(hang+"行4列数据格式错误\t\t");
						}
					}
					continue;
				}
				//判断是否重复
				String flag  = "1";//1 代表考勤帮助类中不包含当前日期的数据  0 代表考勤帮助类中包含当前日期的数据
				String repeatFlag = "1";// 1 代表该条数据不重复 0 代表该条数据重复
				for(AttendanceHelp attendanceHelp : attendanceHelpList){
					//如果包含该日期的数据
					if(importDate.equals(attendanceHelp.getImportDateHelp())){
						flag = "0";
						break;
					}
				}
			    //如果不包含当前日期的数据
				if("1".equals(flag)){
					long startTime=System.currentTimeMillis();
					// 查询当前的数据
					String importDateHelp = importDate;
					List<KqAttendance> kqAttendanceRepeat = getKqAttendanceByImportDate(importDate);
					AttendanceHelp attendanceHelpNew = new AttendanceHelp();
					attendanceHelpNew.setImportDateHelp(importDateHelp);
					attendanceHelpNew.setKqAttendanceRepeat(kqAttendanceRepeat);
					attendanceHelpList.add(attendanceHelpNew);
					long endTime=System.currentTimeMillis(); //获取结束时间
					log.info("获取"+importDate+"的数据，耗用时间"+((endTime - startTime)/1000)+"秒，查询了"+kqAttendanceRepeat.size()+"条数据");
					
				}
				
				for(AttendanceHelp attendanceHelp : attendanceHelpList){
					//如果包含该日期的数据
					if(importDate.equals(attendanceHelp.getImportDateHelp())){
						//判断该条数据是否重复
						for(KqAttendance kqAttendanceRep : attendanceHelp.getKqAttendanceRepeat()){
							if(importId.equals(kqAttendanceRep.getImportId())){
								repeatFlag = "0";
								break;
							}
						}
						break;
					}
				}
				
				//如果数据重复
				if("0".equals(repeatFlag)){
					repeatNum += 1;
					continue;
				}
				/*List<KqAttendance> kqList = getKqAttendance(importId);
				if(kqList.size()>0){
					repeatNum += 1;
					continue;
				}*/
				
				//格式没有问题
				KqAttendance kqAttendance = new KqAttendance();
				kqAttendance.setCreUserId(UserUtil.getCruUserId());
				kqAttendance.setCreUserName(UserUtil.getCruUserName());
				kqAttendance.setCreDeptId(UserUtil.getCruDeptId());
				kqAttendance.setCreDeptName(UserUtil.getCruDeptName());
				kqAttendance.setCreChuShiId(UserUtil.getCruChushiId());
				kqAttendance.setCreChuShiName(UserUtil.getCruChushiName());
				kqAttendance.setCreJuId(UserUtil.getCruJuId());
				kqAttendance.setCreJuName(UserUtil.getCruJuName());
				kqAttendance.setVisible(CommonConstants.VISIBLE[1]);
				kqAttendance.setCreTime(JDateToolkit.getNowDate4());
				kqAttendance.setImportId(importId);
				kqAttendance.setImportDate(importDate);
				kqAttendance.setGateNumber(gateNumber);
				kqAttendance.setCareTime(careTime);
				kqAttendance.setCardNumber(cardNumber);
				//导入
				kqAttendanceDao.save(kqAttendance);
				successNum += 1;
				msg = "导入成功！";
			}
			//形成日志
			KqLog kqLog = new KqLog();
			kqLog.setCreUserId(UserUtil.getCruUserId());
			kqLog.setCreUserName(UserUtil.getCruUserName());
			kqLog.setCreDeptId(UserUtil.getCruDeptId());
			kqLog.setCreDeptName(UserUtil.getCruDeptName());
			kqLog.setCreChuShiId(UserUtil.getCruChushiId());
			kqLog.setCreChuShiName(UserUtil.getCruChushiName());
			kqLog.setCreJuId(UserUtil.getCruJuId());
			kqLog.setCreJuName(UserUtil.getCruJuName());
			kqLog.setVisible(CommonConstants.VISIBLE[1]);
			kqLog.setCreTime(JDateToolkit.getNowDate4());
			kqLog.setSuccessNum(successNum+"");
			kqLog.setErrorNum(errorNum+"");
			kqLog.setRemark(remark.toString());
			kqLog.setRepeatNum(repeatNum+"");
			if(errorNum>0){
				kqLog.setErrorReason("数据格式错误");
			}
			if(csvList.size()==0){
				kqLog.setErrorReason("文件没有数据");
				msg = "导入失败！";
			}else{
				if(successNum==0 || successNum!=csvList.size()){
					msg = "导入成功"+successNum+"条，重复导入"+repeatNum+"条，导入失败"+errorNum+"条！";
				}else if(successNum==csvList.size()){
					msg = "导入成功！";
				}
			}
			
			kqLogDao.save(kqLog);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "导入失败！";
		}
		return msg;
	}
	
	/**
	 * 根据日期获取所有该日期的考勤数据
	 * TODO 
	 * @author 冯超
	 * @Date 2018年7月31日 上午10:20:07
	 * @param importDate
	 * @return
	 */
	public List<KqAttendance> getKqAttendanceByImportDate(String importDate) {
		StringBuilder querySql = new StringBuilder();
		querySql.append("select import_id ");
		querySql.append("	from kqgl_attendance t");
		querySql.append("	where ");
		querySql.append("   t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		if (StringUtils.isNotBlank(importDate)) {
			querySql.append("   and t.import_date = '" + importDate + "'");
		}
		return jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(KqAttendance.class));
	}
	
	/**
	 * 根据导入id获取考勤记录
	 * TODO 
	 * @author 冯超
	 * @Date 2018年5月31日 下午2:52:07
	 * @param importId
	 * @return
	 */
	public List<KqAttendance> getKqAttendance(String importId) {
		StringBuilder querySql = new StringBuilder();
		querySql.append("select * ");
		querySql.append("	from kqgl_attendance t");
		querySql.append("	where ");
		querySql.append("   t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 拼接条件
		if (StringUtils.isNotBlank(importId)) {
			querySql.append("   and t.import_id = '" + importId + "'");
		}
		return jdbcTemplate.query(querySql.toString(), new BeanPropertyRowMapper<>(KqAttendance.class));
	}
	
	/**
	 * 
	 * TODO 
	 * @author 冯超
	 * @Date 2018年7月26日 下午5:37:19
	 * @param pageable
	 * @param pageImpl
	 * @param timeRange
	 * @param cardNumber
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String importDate, String cardNumber,String name) throws Exception {
		pageImpl.setFlag("0");
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();

		querySql.append("	from KqAttendance t ");
		querySql.append("	where  1=1 ");
		
		if (StringUtils.isNotBlank(cardNumber)) {
			querySql.append("   and t.cardNumber = ?");
			para.add(cardNumber);
		}
		
		if (StringUtils.isNotBlank(importDate)) {
			querySql.append("   and t.importDate = ?");
			para.add(importDate);
		}

		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.careTime ) ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}

		Page<KqAttendance> page = kqAttendanceDao.query(querySql.toString(), pageable, para.toArray());
		List<KqAttendance> content = page.getContent();
		//获取闸机号的进出
		Map<String, String> map = new HashMap<>();
		List<DataDictionary> DataDictionaryIn =  dataDictionaryService.getListByMark("gateNumber_in", "1");
		for (DataDictionary dic : DataDictionaryIn) {
			map.put(dic.getCode(), dic.getName());
		}
		List<DataDictionary> DataDictionaryout =  dataDictionaryService.getListByMark("gateNumber_out", "1");
		for (DataDictionary dic : DataDictionaryout) {
			map.put(dic.getCode(), dic.getName());
		}
		for(KqAttendance kqAttendance : content){
			String gateName =  map.get(kqAttendance.getGateNumber());
			kqAttendance.setGateName(gateName);
			kqAttendance.setName(name);
		}
		pageImpl.setFlag("1");
		pageImpl.getData().setRows(content);
		pageImpl.getData().setTotal((int) page.getTotalElements());
		return pageImpl;
	}
	

}
