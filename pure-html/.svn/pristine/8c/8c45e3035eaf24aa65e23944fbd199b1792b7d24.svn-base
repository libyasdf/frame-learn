package com.sinosoft.sinoep.modules.system.component.export.excel.poi.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.sinosoft.sinoep.modules.system.component.export.excel.poi.entity.ExportBean;

/**
 * TODO 导出excel公共类
 * @Date 2016-11-29 下午04:40:18
 */
public class ExportDataToExcel {
	private HSSFWorkbook workbook = new HSSFWorkbook();
	ExportUtil exportUtil = new ExportUtil();

	/**
	 * TODO 最基本的没有合并单元格的excel导出
	 * @author 邴秀慧
	 * @Date 2016-12-14 下午04:56:35
	 * @param response
	 * @param exportLists 存放ExportBean对象的list
	 * @param excelFileName 导出的excel表名
	 * @return
	 * @throws Exception
	 */
	public void excelProject(
			HttpServletResponse response,List<ExportBean> exportLists,String excelFileName){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		for(int i = 0;i < exportLists.size();i++){
			if(exportLists.get(i).getSheetTitle() == null || "".equals(exportLists.get(i).getSheetTitle())){
				exportLists.get(i).setSheetTitle("sheet" + (i+1));
			}
		}
        try {
        	//导出excel
			byte[] by = this.exportExcel(exportLists,out);
			response.setContentType("application/x-msdownload;charset=UTF-8");
			
			response.setContentLength(by.length);
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(excelFileName.getBytes("gb2312"), "ISO8859-1"));
			response.getOutputStream().write(by);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			//if(out != null){
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			//}
			try {
				//if(response.getOutputStream() != null){
					response.getOutputStream().flush();
					response.getOutputStream().close();
				//}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * TODO 导出excel
	 * @author 邴秀慧
	 * @Date 2016-12-16 下午03:57:00
	 * @param exportLists
	 * @param out
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public byte[] exportExcel(List<ExportBean> exportLists, ByteArrayOutputStream out) {
		try {
			for(int i=0; i<exportLists.size(); i++){
				editSheet( exportLists.get(i));
			}
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}
	
	/**
	 * TODO 编辑sheet
	 * @author 邴秀慧
	 * @Date 2016-12-16 下午03:57:14
	 * @param exportBean
	 */
	public void editSheet(ExportBean exportBean) {
		//生成一个表格
		if(exportBean.getSheetTitle() != null && !"".equals(exportBean.getSheetTitle())){
			HSSFSheet sheet = exportUtil.setSheet(workbook, exportBean.getSheetTitle());
			if(exportBean.getSheetColWidth() != 0){
				//定义行宽
				sheet.setDefaultColumnWidth(exportBean.getSheetColWidth());
			}
			//自定义样式
			HSSFCellStyle style = exportUtil.setStyle(workbook,exportBean);
			//添加数据
			setRowInnovation(sheet, style, exportBean);
		}
	}
	/**
	 * TODO 自定义HSSFRow
	 * @author 邴秀慧
	 * @Date 2016-12-16 下午03:57:56
	 * @param sheet
	 * @param style
	 * @param exportBean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private HSSFRow setRowInnovation(HSSFSheet sheet, HSSFCellStyle style, ExportBean exportBean){
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell((short) 0);
		row = setTitleData(sheet, exportBean,row,cell);
        // 遍历集合数据，产生数据行
		List<Object> datasets = new ArrayList<Object>();
		datasets.add(exportBean.getExportList());
		Collection<Object> dataset = (Collection<Object>)datasets.get(0);
		Iterator<Object> it = dataset.iterator();
		int index = 0;
		if(exportBean.getGetMethod()!=null){
			while (it.hasNext()) {
				index++;
				row = sheet.createRow(index);
				//处理序号列（第2行第1列）
				cell = row.createCell((short)0);
				cell.setCellStyle(style);
				HSSFRichTextString richString = new HSSFRichTextString(String.valueOf(index));
				cell.setCellValue(richString);
				Object t = (Object) it.next();
				// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
				String[] fields = new String[]{};
				fields = exportBean.getGetMethod();
				for (short i = 0; i < fields.length; i++) {
					cell = row.createCell((short) (i+1));
					cell.setCellStyle(style);
					String getMethodName = fields[i];         
					try {
						Class<? extends Object> tCls = t.getClass();
						Method getMethod = tCls.getMethod(getMethodName,new Class[] {});
						Object value = getMethod.invoke(t, new Object[] {});
						if (value != null && !"".equals(value)) {
							Pattern p = Pattern.compile("^//d+(//.//d+)?{1}$/");
							Matcher matcher = p.matcher(value.toString());
							if (matcher.matches()) {
								// 是数字当作double处理
								cell.setCellValue(Double.parseDouble(value.toString()));
							}else {
								richString = new HSSFRichTextString(value.toString());
								cell.setCellValue(richString);
							}
						}
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} finally {	
					}
				}
			}
		}else{
			row = sheet.createRow(1);
			HSSFRichTextString text = new HSSFRichTextString();
			sheet.addMergedRegion(new CellRangeAddress(1,(short)0,1,(short)6)); //合并单元格
			short i = 0;
			cell = row.createCell(i);
			text = new HSSFRichTextString("请设置ExportBean实体类里面的getMethod属性值");
			cell.setCellValue(text);
		}
		return row;
	}
	
	/**
	 * TODO 存储表头的内容
	 * @author 邴秀慧
	 * @Date 2016-12-21 下午02:46:36
	 * @param sheet
	 * @param exportBean
	 * @param row
	 * @param cell
	 * @return
	 */
	@SuppressWarnings("unused")
	private HSSFRow setTitleData(HSSFSheet sheet, ExportBean exportBean,HSSFRow row,HSSFCell cell){
		// 产生表格表头行
		if(exportBean.getTitleRowHight() != 0){
			row.setHeight(exportBean.getTitleRowHight());//设置表头行高
		}
		HSSFRichTextString text = new HSSFRichTextString();
		if(exportBean.getHeader() != null){
			for (short i = 0; i < exportBean.getHeader().length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(exportUtil.getTitleStyle(workbook,exportBean));
				text = new HSSFRichTextString(exportBean.getHeader()[i]);
				cell.setCellValue(text);
			}
		}else{
			sheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)6)); //合并单元格
			short i = 0;
			cell = row.createCell(i);
			text = new HSSFRichTextString("请设置ExportBean实体类里面的header属性值");
			cell.setCellValue(text);
		}
		return row;
	}
}