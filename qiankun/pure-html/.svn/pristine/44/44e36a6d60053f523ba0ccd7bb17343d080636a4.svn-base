package com.sinosoft.sinoep.modules.taskplan.service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.modules.system.component.importFile.util.ImportFileUtil;
import com.sinosoft.sinoep.modules.taskplan.dao.TaskPlanDao;
import com.sinosoft.sinoep.modules.taskplan.entity.TaskPlan;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import workflow.common.JDateToolkit;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Transactional
public class ImportTaskPlanServiceImpl implements ImportTaskPlanService{


    @Autowired
    private TaskPlanDao dao;
    @Override
    public String importInfo(String filePath, MultipartFile file) throws IOException {
        String msg = "导入失败！";
        InputStream is = file.getInputStream();
        Workbook workbook = null;
        try {
            workbook = ImportFileUtil.getWorkBook(is,filePath);
            int sheetNum = ImportFileUtil.getSheetNumByWorkBook(workbook);
            for(int sheetIdex = 0;sheetIdex< sheetNum;sheetIdex++){
                Sheet sheet = workbook.getSheetAt(sheetIdex);
                if(sheet == null){
                    continue;
                }
                for (int rowIndex =1;rowIndex<=sheet.getLastRowNum();rowIndex++){
                    Row row = sheet.getRow(rowIndex);
                    if(row == null){
                        continue;
                    }
                    TaskPlan entity = new TaskPlan();
                    entity.setCreTime(JDateToolkit.getNowDate4());
                    entity.setTitle(ImportFileUtil.getRowCellValue(row,(short)0));
                    entity.setSubflag("0");
                    entity.setNote(ImportFileUtil.getRowCellValue(row,(short)1));
                    entity.setZhubDeptNm(ImportFileUtil.getRowCellValue(row,(short)2));
                    entity.setZhubPersonNm(ImportFileUtil.getRowCellValue(row,(short)3));
                    entity.setXiebDept(ImportFileUtil.getRowCellValue(row,(short)4));
                    entity.setXiebPerson(ImportFileUtil.getRowCellValue(row,(short)5));
                    entity.setCreUserId(UserUtil.getCruUserId());
                    entity.setCreUserName(UserUtil.getCruUserName());
                    entity.setVisible(CommonConstants.VISIBLE[1]);
                    entity.setArea(ImportFileUtil.getRowCellValue(row,(short)7));
                    dao.save(entity);
                    msg = "导入成功！";
                }
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            msg = "导入失败！";
        }
        return msg;
    }
}
