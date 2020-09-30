package com.sinosoft.sinoep.modules.mypage.record.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.record.entity.Record;
import com.sinosoft.sinoep.modules.zhbg.salary.entity.Salary;

public interface RecordService {
	
	/**
	 * 获取工资的分页数据
	 * TODO 
	 * @author 张建明
	 * @Date 2018年5月28日 下午2:29:56
	 * @param pageable
	 * @param pageImpl
	 * @param name
	 * @param idCard
	 * @return
	 */
	PageImpl getlistBootHql(Pageable pageable, PageImpl pageImpl, String name, String yearMonth,String idCarNo,String flag,String personId);
	
	/**
	 * 导入
	 * TODO 
	 * @author 张建明
	 * @Date 2018年5月29日 上午9:31:45
	 * @param filePath
	 * @param file
	 * @return
	 */
	String importInfo(String filePath, MultipartFile file)throws IOException;
	
	/**
	 * 根据id删除一条记录
	 * TODO 
	 * @author 张建明
	 * @Date 2018年5月31日 下午1:31:46
	 * @param id
	 * @return
	 */
	int delete(String id);
	
	/**
	 * 根据id查询一个工资
	 * TODO 
	 * @author 张建明
	 * @Date 2018年5月31日 下午2:28:01
	 * @param id
	 * @return
	 */
	Record getById(String id);
	
	/**
	 * 修改工资数据
	 * TODO 
	 * @author 张建明
	 * @Date 2018年5月31日 下午2:48:18
	 * @param info
	 * @return
	 */
	Record saveForm(Record info);

	List<Record> getMyRecord();

	List findByKeyCode(String keyCode);

	Record findCACodeById(String reUserId);

}
