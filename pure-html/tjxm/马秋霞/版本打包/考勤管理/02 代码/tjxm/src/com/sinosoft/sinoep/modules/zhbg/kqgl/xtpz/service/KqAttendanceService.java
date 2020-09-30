package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.service;

import java.io.IOException;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.sinosoft.sinoep.common.util.PageImpl;

/**
 * 
 * TODO 出勤表service 
 * @author 冯超
 * @Date 2018年5月30日 上午11:05:01
 */
public interface KqAttendanceService {
	/**
	 * 
	 * TODO 导入excle
	 * @author 冯超
	 * @Date 2018年5月30日 上午11:15:38
	 * @param filePath
	 * @param file
	 * @return
	 * @throws IOException
	 */
	String importInfo(String filePath, MultipartFile file) throws IOException;
	
	/**
	 * 
	 * TODO 导入CSV
	 * @author 冯超
	 * @Date 2018年6月1日 下午8:13:05
	 * @param filePath
	 * @return
	 */
	String importCSV(MultipartFile file);
	
	/**
	 * 
	 * TODO 
	 * @author 冯超
	 * @Date 2018年7月26日 下午5:36:15
	 * @param pageable
	 * @param pageImpl
	 * @param timeRange
	 * @param cardNumber
	 * @return
	 * @throws Exception
	 */
	PageImpl getPageList(Pageable pageable, PageImpl pageImpl, String importDate, String cardNumber,String name) throws Exception;

}
