package com.sinosoft.sinoep.modules.zhbg.salary.services;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.component.importFile.util.ImportFileUtil;
import com.sinosoft.sinoep.modules.zhbg.salary.dao.SalaryDao;
import com.sinosoft.sinoep.modules.zhbg.salary.dao.SalaryImportLogDao;
import com.sinosoft.sinoep.modules.zhbg.salary.entity.Salary;
import com.sinosoft.sinoep.modules.zhbg.salary.entity.SalaryImportLog;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;
import workflow.common.JDateToolkit;

@Service
public class SalaryServiceImp implements SalaryService{
	@Autowired
	SalaryDao dao;
	@Autowired
	SalaryImportLogDao sidao;

	@Override
	public PageImpl getlistBootHql(Pageable pageable, PageImpl pageImpl, String name, String yearMonth,String idCarNo,String flag,String personId) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from Salary t ");
		querySql.append("	where   t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		if (StringUtils.isNotBlank(yearMonth)) {
			querySql.append("   and t.saMonth = ?");
			para.add(yearMonth);
		}
		if(StringUtils.isNotBlank(personId)){
			querySql.append("  and UPPER(t.idCarNo) in ( " + CommonUtils.commomSplit(getUserVo(personId))+")");
		}
		if("1".equals(flag)){
			//查询的是我的工资
			String cruUserId = UserUtil.getCruUserId();
			Map<String, SysFlowUserVo> map = UserUtil.getUserVo(cruUserId);
			String identity = map.get(cruUserId).getIdentity().toUpperCase();
			querySql.append("   and UPPER(t.idCarNo) = ?");
			para.add(identity);
		}
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
				querySql.append("  order by t.saMonth desc,t.sort asc) ");
		}else{
				querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}		
        Page<Salary> page = dao.query(querySql.toString(), pageable,para.toArray());
        //草稿列表，添加操作列
        //草稿列表，添加操作列
        List<Salary> content = page.getContent();
        for (Salary info : content) {
        		info.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
		}
        return new PageImpl((int)page.getTotalElements(),page.getContent());
	}
	
	/**
	 * 导出
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月29日 上午9:33:07
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
	    	int successNum = 0;//成功的条数
			int errorNum = 0;//失败的条数
			String errorReason = "";
			StringBuilder mark = new StringBuilder();
			DecimalFormat    df   = new DecimalFormat("######0.00");   
	        try {
	            workbook = ImportFileUtil.getWorkBook(is,filePath);
	            int sheetNum = ImportFileUtil.getSheetNumByWorkBook(workbook);
	            int cnt=0;
	            int bankCnt=0;
	            int lastCnt=0;
	            for(int sheetIdex = 0;sheetIdex< sheetNum;sheetIdex++){
	                Sheet sheet = workbook.getSheetAt(sheetIdex);
	                if(sheet == null){
	                    continue;
	                }
	               int lastNum= sheet.getLastRowNum();
	               lastCnt=lastCnt+lastNum;
	               if(lastNum==0){
	            	   errorReason="表格中无数据";
	               }
	                for (int rowIndex =1;rowIndex<=sheet.getLastRowNum();rowIndex++){
	                    Row row = sheet.getRow(rowIndex);
	                    if(row == null){
	                        continue;
	                    }
	                    String flag="0"; //表示这一行所有单元格数据都不为空格
	                    Salary entity = new Salary();
	                    entity.setCreTime(JDateToolkit.getNowDate4());
	                    entity.setCreUserId(UserUtil.getCruUserId());
	                    entity.setCreUserName(UserUtil.getCruUserName());
	                    entity.setCreDeptId(UserUtil.getCruDeptId());
	                    entity.setCreDeptName(UserUtil.getCruDeptName());
	                    entity.setCreChuShiId(UserUtil.getCruChushiId());
	                    entity.setCreChuShiName(UserUtil.getCruChushiName());
	                    entity.setCreJuId(UserUtil.getCruJuId());
	                    entity.setCreJuName(UserUtil.getCruJuName());
	                    entity.setVisible("1");
	                    String saMonth=ImportFileUtil.getRowCellValue(row,(short)0);
	                    String year="";
	                    String month;
	                   String yearMonth;
						
	                    if(StringUtils.isBlank(saMonth)){
	                    	entity.setSaMonth("");
	                    }else{
	                    	flag="1";
	                    	year=saMonth.substring(0, 4);
	                    	int index=saMonth.indexOf(".");
	                    	if(index==-1){
	                    		month=saMonth.substring(4, saMonth.length());
	                    	}else{
	                    		month=saMonth.substring(4, saMonth.indexOf("."));
	                    	}
	                    	yearMonth=year+"-"+month;
	                    	if(!yearMonth.matches("^.*\\d{4}.*$")){
	    						errorNum += 1;
	    						errorReason="数据格式错误";
	    						if(mark.length() < 2470){
	    							if(errorNum % 3== 0){
	    								mark.append((rowIndex+1)+"行发放月份列数据格式不对\t\n");
	    							}else{
	    								mark.append((rowIndex+1)+"行发放月份列数据格式不对\t");
	    							}
	    						}
	    						continue;
	    					}
	                    	entity.setSaMonth(year+"-"+month);
	                    }
	                    String saDeptName=ImportFileUtil.getRowCellValue(row,(short)1);
	                    if(!StringUtils.isBlank(saDeptName)){
	                    	flag="1";
	                    }
	                    entity.setSaDeptName(saDeptName);
	                    
	                    String saUsername=ImportFileUtil.getRowCellValue(row,(short)2);
	                    if(!StringUtils.isBlank(saUsername)){
	                    	flag="1";
	                    }
	                    entity.setSaUserName(saUsername);
	                   
	                    
	                    //entity.setActualSalary(ImportFileUtil.getRowCellValue(row,(short)24));
	                    String idCard=ImportFileUtil.getRowCellValue(row,(short)3);
	                    if("".equals(idCard)){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行身份证号不能为空  \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行身份证号不能为空  \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行实发工资必须为数值      ");
	   						continue;
	                    } else{
	                    	entity.setIdCarNo(idCard.toUpperCase());
	                    }
	                    String actualSalary=ImportFileUtil.getRowCellValue(row,(short)4);
	                    if("".equals(actualSalary)){
	                    	
	                    	entity.setActualSalary("0.00");
	                    } else if(!actualSalary.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行实发工资必须为数值  \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行实发工资必须为数值 \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行实发工资必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	entity.setActualSalary(df.format(Double.valueOf(actualSalary)));
	                    }
	                    String jobSalary=ImportFileUtil.getRowCellValue(row,(short)5);
	                    if("".equals(jobSalary)){
	                    	entity.setJobSalary("0.00");
	                    } else if(!jobSalary.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行职务工资必须为数值 \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行职务工资必须为数值 \t");
    							}
    						}
    						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setJobSalary(df.format(Double.valueOf(jobSalary)));
	                    }
	                   
	                     
	                    String levelSalary=ImportFileUtil.getRowCellValue(row,(short)6);
	                    if("".equals(levelSalary)){
	                    	
	                    	entity.setLevelSalary("0.00");
	                    } else if(!levelSalary.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行级别工资列必须为数值 \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行级别工资列必须为数值 \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行级别工资列必须为数值      ");
    						continue;
	                    }else{
	                    	flag="1";
	                    	entity.setLevelSalary(df.format(Double.valueOf(levelSalary)));
	                    }
	                    
	                    
	                    
	                    String policeRankSalary=ImportFileUtil.getRowCellValue(row,(short)7);
	                    if("".equals(policeRankSalary)){
	                    	
	                    	entity.setPoliceRankSalary("0.00");
	                    } else if(!policeRankSalary.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行警衔工资必须为数值 \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行警衔工资必须为数值 \t");
    							}
    						}
    						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setPoliceRankSalary(df.format(Double.valueOf(policeRankSalary)));
	                    }
	                   
	                    
	                    String workSalary=ImportFileUtil.getRowCellValue(row,(short)8);
	                    if("".equals(workSalary)){
	                    	
	                    	entity.setWorkSalary("0.00");
	                    } else if(!workSalary.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行工作津贴必须为数值  \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行工作津贴必须为数值  \t");
    							}
    						}
    						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setWorkSalary(df.format(Double.valueOf(workSalary)));
	                    }
	                   
	                    
	                    String lifeSalary=ImportFileUtil.getRowCellValue(row,(short)9);
	                    if("".equals(lifeSalary)){
	                    	
	                    	entity.setLifeSalary("0.00");
	                    } else if(!lifeSalary.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行生活补贴必须为数值  \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行生活补贴必须为数值  \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行生活补贴必须为数值      ");
    						continue;
	                    }else{
	                    	flag="1";
	                    	entity.setLifeSalary(df.format(Double.valueOf(lifeSalary)));
	                    }
	                   
	                   
	                   String DutySalary=ImportFileUtil.getRowCellValue(row,(short)10);
	                   if("".equals(DutySalary)){
	                	  
	                    	entity.setDutySalary("0.00");
	                    } else if(!DutySalary.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行执勤津贴必须为数值  \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行执勤津贴必须为数值  \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行执勤津贴必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	entity.setDutySalary(df.format(Double.valueOf(DutySalary)));
	                    }
	                    
	                    
	                    String fooSalary=ImportFileUtil.getRowCellValue(row,(short)11);
	                    if("".equals(fooSalary)){
	                    	
	                    	entity.setFooSalary("0.00");
	                    } else if(!fooSalary.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行副食补贴必须为数值  \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行副食补贴必须为数值  \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行副食补贴必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setFooSalary(df.format(Double.valueOf(fooSalary)));
	                    }
	                   
	                    
	                    String huiSalary=ImportFileUtil.getRowCellValue(row,(short)12);
	                    if("".equals(huiSalary)){
	                    	
	                    	entity.setHuiSalary("0.00");
	                    } else if(!huiSalary.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行回民补贴必须为数值   \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行回民补贴必须为数值   \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行回民补贴必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	entity.setHuiSalary(df.format(Double.valueOf(huiSalary)));
	                    }
	                    
	                    
	                    String houseSalary=ImportFileUtil.getRowCellValue(row,(short)13);
	                    if("".equals(houseSalary)){
	                    	
	                    	entity.setHouseSalary("0.00");
	                    } else if(!houseSalary.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行房屋补贴必须为数值   \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行房屋补贴必须为数值   \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行房屋补贴必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	entity.setHouseSalary(df.format(Double.valueOf(houseSalary)));
	                    }
	                    
	                    
	                    String onlyChildSalary=ImportFileUtil.getRowCellValue(row,(short)14);
	                    if("".equals(onlyChildSalary)){
	                    	
	                    	entity.setOnlyChildSalary("0.00");
	                    } else if(!onlyChildSalary.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行独子费必须为数值   \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行独子费必须为数值   \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行独子费必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	entity.setOnlyChildSalary(df.format(Double.valueOf(onlyChildSalary)));
	                    }
	                    
	                    String dutyFee=ImportFileUtil.getRowCellValue(row,(short)15);
	                    if("".equals(dutyFee)){
	                    	
	                    	entity.setDutyFee("0.00");
	                    } else if(!dutyFee.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行值班费必须为数值   \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行值班费必须为数值   \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行独子费必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	entity.setDutyFee(df.format(Double.valueOf(dutyFee)));
	                    }
	                    
	                    String policeOvertimePay=ImportFileUtil.getRowCellValue(row,(short)16);
	                    if("".equals(policeOvertimePay)){
	                    	
	                    	entity.setPoliceOvertimePay("0.00");
	                    } else if(!policeOvertimePay.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行独子费必须为数值   \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行独子费必须为数值   \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行独子费必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	entity.setPoliceOvertimePay(df.format(Double.valueOf(policeOvertimePay)));
	                    }
	                    
	                    
	                    String shouldSalary=ImportFileUtil.getRowCellValue(row,(short)17);
	                    if("".equals(shouldSalary)){
	                    	
	                    	entity.setShouldSalary("0.00");
	                    } else if(!shouldSalary.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行应发工资必须为数值   \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行应发工资必须为数值   \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行应发工资必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	entity.setShouldSalary(df.format(Double.valueOf(shouldSalary)));
	                    }
	                    
	                    
	                    String accumulationSalary=ImportFileUtil.getRowCellValue(row,(short)18);
	                    if("".equals(accumulationSalary)){
	                    	
	                    	entity.setAccumulationSalary("0.00");
	                    } else if(!accumulationSalary.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行公积金必须为数值  \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行公积金必须为数值   \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行公积金必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setAccumulationSalary(df.format(Double.valueOf(accumulationSalary)));
	                    }
	                   
	                    
	                    String medicalInsurance=ImportFileUtil.getRowCellValue(row,(short)19);
	                    if("".equals(medicalInsurance)){
	                    	
	                    	entity.setMedicalInsurance("0.00");
	                    } else if(!medicalInsurance.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行医保必须为数值  \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行医保必须为数值   \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行医保必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	entity.setMedicalInsurance(df.format(Double.valueOf(medicalInsurance)));
	                    }
	                    
	                    
	                    String pensionAnnuity=ImportFileUtil.getRowCellValue(row,(short)20);
	                    if("".equals(pensionAnnuity)){
	                    	
	                    	entity.setPensionAnnuity("0.00");
	                    } else if(!pensionAnnuity.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                     	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行养老年金必须为数值  \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行养老年金必须为数值  \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行养老年金必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	entity.setPensionAnnuity(df.format(Double.valueOf(pensionAnnuity)));
	                    }
	                    
	                    
	                    String rentSalary=ImportFileUtil.getRowCellValue(row,(short)21);
	                    if("".equals(rentSalary)){
	                    	
	                    	entity.setRentSalary("0.00");
	                    } else if(!rentSalary.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行房租必须为数值  \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行房租必须为数值  \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行房租必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setRentSalary(df.format(Double.valueOf(rentSalary)));
	                    }
	                   
	                    
	                    String buckleSalary=ImportFileUtil.getRowCellValue(row,(short)22);
	                    if("".equals(buckleSalary)){
	                    	
	                    	entity.setBuckleSalary("0.00");
	                    } else if(!buckleSalary.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行扣款必须为数值  \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行扣款必须为数值  \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行扣款必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setBuckleSalary(df.format(Double.valueOf(buckleSalary)));
	                    }
	                   
	                    
	                    String dueSalary=ImportFileUtil.getRowCellValue(row,(short)23);
	                    if("".equals(dueSalary)){
	                    	
	                    	entity.setDuesSalary("0.00");
	                    } else if(!dueSalary.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行会费必须为数值  \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行会费必须为数值  \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行会费必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setDuesSalary(df.format(Double.valueOf(dueSalary)));
	                    }
	                   
	                    
	                    String buckleLoan=ImportFileUtil.getRowCellValue(row,(short)24);
	                    if("".equals(buckleLoan)){
	                    	
	                    	entity.setBuckleLoan("0.00");
	                    } else if(!buckleLoan.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行扣贷款必须为数值  \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行扣贷款必须为数值  \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行扣贷款必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setBuckleLoan(df.format(Double.valueOf(buckleLoan)));
	                    }
	                   
	                    
	                    String loanInterest=ImportFileUtil.getRowCellValue(row,(short)25);
	                    if("".equals(loanInterest)){
	                    	
	                    	entity.setLoanInterest("0.00");
	                    } else if(!loanInterest.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行贷款利息必须为数值  \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行贷款利息必须为数值 \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行贷款利息必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setLoanInterest(df.format(Double.valueOf(loanInterest)));
	                    }
	                   
	                    
	                    String personalIncomeTax=ImportFileUtil.getRowCellValue(row,(short)26);
	                    if("".equals(personalIncomeTax)){
	                    	
	                    	entity.setPersonalIncomeTax("0.00");
	                    } else if(!personalIncomeTax.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行个人所得税必须为数值  \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行个人所得税必须为数值 \t");
    							}
    						}
	                    	//mark.append((rowIndex+1)+"行个人所得税必须为数值      ");
	   						continue;
	                    }else{
	                    	flag="1";
	                    	entity.setPersonalIncomeTax(df.format(Double.valueOf(personalIncomeTax)));
	                    }
	                    
	                    String propertyFee=ImportFileUtil.getRowCellValue(row,(short)27);
	                    if("".equals(propertyFee)){
	                    	entity.setPropertyFee("0.00");
	                    
	                    } else if(!propertyFee.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行物业费必须为数值 \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行物业费必须为数值 \t");
    							}
    						}
    						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setPropertyFee(df.format(Double.valueOf(propertyFee)));
	                    }
	                    
	                    String heatingSubsidies=ImportFileUtil.getRowCellValue(row,(short)28);
	                    if("".equals(heatingSubsidies)){
	                    	entity.setHeatingSubsidies("0.00");
	                    
	                    } else if(!heatingSubsidies.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行采暖补贴必须为数值 \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行采暖补贴必须为数值 \t");
    							}
    						}
    						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setHeatingSubsidies(df.format(Double.valueOf(heatingSubsidies)));
	                    }
	                    
	                    String travelAllowance=ImportFileUtil.getRowCellValue(row,(short)29);
	                    if("".equals(travelAllowance)){
	                    	entity.setTravelAllowance("0.00");
	                    
	                    } else if(!travelAllowance.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行交通补贴必须为数值 \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行交通补贴必须为数值 \t");
    							}
    						}
    						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setTravelAllowance(df.format(Double.valueOf(travelAllowance)));
	                    }
	                    
	                    String phoneAllowance=ImportFileUtil.getRowCellValue(row,(short)30);
	                    if("".equals(phoneAllowance)){
	                    	entity.setPhoneAllowance("0.00");
	                    
	                    } else if(!phoneAllowance.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行通讯补贴必须为数值 \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行通讯补贴必须为数值 \t");
    							}
    						}
    						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setPhoneAllowance(df.format(Double.valueOf(phoneAllowance)));
	                    }
	                    
	                    String publicTransportation=ImportFileUtil.getRowCellValue(row,(short)31);
	                    if("".equals(publicTransportation)){
	                    	entity.setPublicTransportation("0.00");
	                    
	                    } else if(!publicTransportation.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行公务交通必须为数值 \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行公务交通必须为数值 \t");
    							}
    						}
    						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setPublicTransportation(df.format(Double.valueOf(publicTransportation)));
	                    }
	                    
	                    String unpaidLeaveAllowance=ImportFileUtil.getRowCellValue(row,(short)32);
	                    if("".equals(unpaidLeaveAllowance)){
	                    	entity.setUnpaidLeaveAllowance("0.00");
	                    
	                    } else if(!unpaidLeaveAllowance.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行公务交通必须为数值 \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行公务交通必须为数值 \t");
    							}
    						}
    						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setUnpaidLeaveAllowance(df.format(Double.valueOf(unpaidLeaveAllowance)));
	                    }
	                    
	                    String doublePay=ImportFileUtil.getRowCellValue(row,(short)33);
	                    if("".equals(doublePay)){
	                    	entity.setDoublePay("0.00");
	                    
	                    } else if(!doublePay.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行双薪必须为数值 \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行双薪必须为数值 \t");
    							}
    						}
    						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setDoublePay(df.format(Double.valueOf(doublePay)));
	                    }
	                    
	                    String meritPay=ImportFileUtil.getRowCellValue(row,(short)34);
	                    if("".equals(meritPay)){
	                    	entity.setMeritPay("0.00");
	                    
	                    } else if(!meritPay.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行绩效奖必须为数值 \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行绩效奖必须为数值 \t");
    							}
    						}
    						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setMeritPay(df.format(Double.valueOf(meritPay)));
	                    }
	                    
	                    String heatstrokePreventionSubsidy=ImportFileUtil.getRowCellValue(row,(short)35);
	                    if("".equals(heatstrokePreventionSubsidy)){
	                    	entity.setHeatstrokePreventionSubsidy("0.00");
	                    
	                    } else if(!heatstrokePreventionSubsidy.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行防暑降温费必须为数值 \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行防暑降温费必须为数值 \t");
    							}
    						}
    						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setHeatstrokePreventionSubsidy(df.format(Double.valueOf(heatstrokePreventionSubsidy)));
	                    }
	                    
	                    String winterHeatingSubsidy=ImportFileUtil.getRowCellValue(row,(short)36);
	                    if("".equals(winterHeatingSubsidy)){
	                    	entity.setWinterHeatingSubsidy("0.00");
	                    
	                    } else if(!winterHeatingSubsidy.matches("-?[0-9]+.?[0-9]+")){
	                    	flag="1";
	                    	errorNum += 1;
	                    	errorReason="数据格式错误";
	                    	if(mark.length() < 2000){
    							if(errorNum % 3== 0){
    								mark.append((rowIndex+1)+"行冬季采暖补贴必须为数值 \t\n");
    							}else{
    								mark.append((rowIndex+1)+"行冬季采暖补贴必须为数值 \t");
    							}
    						}
    						continue;
	                    }else{
	                    	flag="1";
	                    	 entity.setWinterHeatingSubsidy(df.format(Double.valueOf(winterHeatingSubsidy)));
	                    }
	                    if("1".equals(flag)){
	                    	//该行至少有一个单元格有数据
	                    	entity.setSort(cnt);
	                    	dao.save(entity);
	                    	 successNum += 1;
	                    	 cnt++;
	                    }else{
	                    	bankCnt++;
	                    }
	                    
	                   
	                    msg = "导入成功！";
	                }
	            }
	            if(bankCnt==lastCnt){
	            	   errorReason="表格中无数据";
	            	   msg = "导入失败！";
	             }
	          
	        } catch (Exception e) {
	            e.printStackTrace();
	            msg = "导入失败！";
	        }finally{
	        	//形成日志
				SalaryImportLog log = new SalaryImportLog();
				log.setCreUserId(UserUtil.getCruUserId());
				log.setCreUserName(UserUtil.getCruUserName());
				log.setCreDeptId(UserUtil.getCruDeptId());
				log.setCreDeptName(UserUtil.getCruDeptName());
				log.setCreChuShiId(UserUtil.getCruChushiId());
				log.setCreChuShiName(UserUtil.getCruChushiName());
				log.setCreJuId(UserUtil.getCruJuId());
				log.setCreJuName(UserUtil.getCruJuName());
				log.setVisible(CommonConstants.VISIBLE[1]);
				log.setCreTime(JDateToolkit.getNowDate4());
				
				log.setSuccessNum(successNum+"");
				log.setErrorNum(errorNum+"");
				log.setRemark(mark.toString());
				log.setErrorReason(errorReason);
				sidao.save(log);
	        }
	        return msg;
	}

	/**
	 * 根据id删除一条记录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月31日 下午1:32:23
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		String jpql = "update Salary t set t.visible = ? where t.id = ?";
		return dao.update(jpql, CommonConstants.VISIBLE[0], id);
	}
	
	/**
	 * 根据id查询一条工资记录
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月31日 下午2:29:21
	 * @param id
	 * @return
	 */
	@Override
	public Salary getById(String id) {
		return dao.findOne(id);
	}
	
	/**
	 * 修改工资数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月31日 下午2:50:39
	 * @param info
	 * @return
	 */
	@Override
	public Salary saveForm(Salary info) {
			String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			DecimalFormat    df   = new DecimalFormat("######0.00");   
			
			Salary salary=dao.findOne(info.getId());
			salary.setUpdateTime(creTime);
			salary.setUpdateUserId(UserUtil.getCruUserId());
			salary.setUpdateUserName(UserUtil.getCruUserName());
			String saMonth=info.getSaMonth();
			salary.setSaMonth(saMonth);
			
			String jobSalary=info.getJobSalary();
			if(StringUtils.isBlank(jobSalary)){
				salary.setJobSalary("0.00");
			}else{
				salary.setJobSalary(df.format(Double.valueOf(jobSalary)));
			}
			
			
			String leveSalary=info.getLevelSalary();
			if(StringUtils.isBlank(leveSalary)){
				salary.setLevelSalary("0.00");
			}else{
				salary.setLevelSalary(df.format(Double.valueOf(leveSalary)));
			}
			
			
			String policeSalary=info.getPoliceRankSalary();
			if(StringUtils.isBlank(policeSalary)){
				salary.setPoliceRankSalary("0.00");
			}else{
				salary.setPoliceRankSalary(df.format(Double.valueOf(policeSalary)));
			}
			
			
			String workSalary=info.getWorkSalary();
			if(StringUtils.isBlank(workSalary)){
				salary.setWorkSalary("0.00");
			}else{
				salary.setWorkSalary(df.format(Double.valueOf(workSalary)));
			}
			
			
			String lifeSalary=info.getLifeSalary();
			if(StringUtils.isBlank(lifeSalary)){
				salary.setLifeSalary("0.00");
			}else{
				salary.setLifeSalary(df.format(Double.valueOf(lifeSalary)));
			}
			
			
			String dutySalary=info.getDutySalary();
			if(StringUtils.isBlank(dutySalary)){
				salary.setDutySalary("0.00");
			}else{
				salary.setDutySalary(df.format(Double.valueOf(dutySalary)));
			}
			
			
			String fooSalary=info.getFooSalary();
			if(StringUtils.isBlank(fooSalary)){
				salary.setFooSalary("0.00");
			}else{
				salary.setFooSalary(df.format(Double.valueOf(fooSalary)));
			}
			
			
			String huiSalary=info.getHuiSalary();
			if(StringUtils.isBlank(huiSalary)){
				salary.setHuiSalary("0.00");
			}else{
				salary.setHuiSalary(df.format(Double.valueOf(huiSalary)));
			}
			
			
			String houseSalary=info.getHouseSalary();
			if(StringUtils.isBlank(houseSalary)){
				salary.setHouseSalary("0.00");
			}else{
				salary.setHouseSalary(df.format(Double.valueOf(houseSalary)));
			}	
			
			
			String onlyChildSalary=info.getOnlyChildSalary();
			if(StringUtils.isBlank(onlyChildSalary)){
				salary.setOnlyChildSalary("0.00");
			}else{
				salary.setOnlyChildSalary(df.format(Double.valueOf(onlyChildSalary)));
			}
			
			
			String shouldSalary=info.getShouldSalary();
			if(StringUtils.isBlank(shouldSalary)){
				salary.setShouldSalary("0.00");
			}else{
				salary.setShouldSalary(df.format(Double.valueOf(shouldSalary)));
			}
			
			
			String accumulationSalary=info.getAccumulationSalary();
			if(StringUtils.isBlank(accumulationSalary)){
				salary.setAccumulationSalary("0.00");
			}else{
				salary.setAccumulationSalary(df.format(Double.valueOf(accumulationSalary)));
			}
			
			
			String medicalInsurance=info.getMedicalInsurance();
			if(StringUtils.isBlank(medicalInsurance)){
				salary.setMedicalInsurance("0.00");
			}else{
				salary.setMedicalInsurance(df.format(Double.valueOf(medicalInsurance)));
			}
			
			
			String pensionAnnuity=info.getPensionAnnuity();
			if(StringUtils.isBlank(pensionAnnuity)){
				salary.setPensionAnnuity("0.00");
			}else{
				salary.setPensionAnnuity(df.format(Double.valueOf(pensionAnnuity)));
			}
			
			
			String rentSalary=info.getRentSalary();
			if(StringUtils.isBlank(rentSalary)){
				salary.setRentSalary("0.00");
			}else{
				salary.setRentSalary(df.format(Double.valueOf(rentSalary)));
			}
			
			
			String buckleSalary=info.getBuckleSalary();
			if(StringUtils.isBlank(buckleSalary)){
				salary.setBuckleSalary("0.00");
			}else{
				salary.setBuckleSalary(df.format(Double.valueOf(buckleSalary)));
			}
			
			
			String duesSalary=info.getDuesSalary();
			if(StringUtils.isBlank(duesSalary)){
				salary.setDuesSalary("0.00");
			}else{
				salary.setDuesSalary(df.format(Double.valueOf(duesSalary)));
			}
			
			
			String buckleLoan=info.getBuckleLoan();
			if(StringUtils.isBlank(buckleLoan)){
				salary.setBuckleLoan("0.00");
			}else{
				salary.setBuckleLoan(df.format(Double.valueOf(buckleLoan)));
			}
			
			
			String loanInterest=info.getLoanInterest();
			if(StringUtils.isBlank(loanInterest)){
				salary.setLoanInterest("0.00");
			}else{
				salary.setLoanInterest(df.format(Double.valueOf(loanInterest)));
			}
			
			
			String personIncomeTax=info.getPersonalIncomeTax();
			if(StringUtils.isBlank(personIncomeTax)){
				salary.setPersonalIncomeTax("0.00");
			}else{
				salary.setPersonalIncomeTax(df.format(Double.valueOf(personIncomeTax)));
			}
			
			
			String actualSalary=info.getActualSalary();
			if(StringUtils.isBlank(actualSalary)){
				salary.setActualSalary("0.00");
			}else{
				salary.setActualSalary(df.format(Double.valueOf(actualSalary)));
			}
			String dutyFee=info.getDutyFee();
			if(StringUtils.isBlank(dutyFee)){
				salary.setDutyFee("0.00");
			}else{
				salary.setDutyFee(df.format(Double.valueOf(dutyFee)));
			}
			String policeOvertimePay=info.getPoliceOvertimePay();
			if(StringUtils.isBlank(policeOvertimePay)){
				salary.setPoliceOvertimePay("0.00");
			}else{
				salary.setPoliceOvertimePay(df.format(Double.valueOf(policeOvertimePay)));
			}
			
			salary.setIdCarNo(info.getIdCarNo());
			info=dao.save(salary);
			
			return info;
		
	}
	

	public String getUserVo(String userIds){
		StringBuilder sb=new StringBuilder();
		String[] ids=userIds.split(",");
		Map<String, SysFlowUserVo> map=UserUtil.getUserVo(userIds);
		SysFlowUserVo vo=map.get(userIds);
		for(int i=0;i<ids.length;i++){
			SysFlowUserVo vo1=map.get(ids[i]);
			if(vo1!=null){
				String identity=vo1.getIdentity();
				if(!StringUtils.isBlank(identity)){
					sb.append(identity.toUpperCase()+",");
				}
			}
			
		}
		return sb.toString();
	}



}
