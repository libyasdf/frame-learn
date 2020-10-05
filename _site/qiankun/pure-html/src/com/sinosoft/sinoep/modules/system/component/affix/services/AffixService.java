package com.sinosoft.sinoep.modules.system.component.affix.services;

import com.sinosoft.sinoep.modules.system.component.affix.dao.AffixDao;
import net.sf.json.JSONArray;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.List;

public interface AffixService {

	/**
	 * TODO 
	 * @author 周俊林
	 * @Date 2018年3月15日 下午3:31:54
	 * @return
	 */
	public AffixDao getRepository();

	/**
	 * TODO 上传文件
	 * @author 周俊林
	 * @Date 2018年3月15日 下午3:31:50
	 * @param file
	 * @param tableName
	 * @param tableId
	 * @param recordId
	 * @param defaultName //默认名称
	 */
	public String fileUpload(MultipartFile file, String tableName, String tableId,String recordId,String fileListId,String defaultName);

	/**
	 * TODO 根据id删除附件
	 * @author 周俊林
	 * @Date 2018年3月16日 上午10:12:49
	 * @param affixId 以,分隔的id串
	 */
	public void delete(String affixId);

	/**
	 * TODO 根据id下载附件
	 * @author 周俊林
	 * @Date 2018年3月16日 上午10:47:03
	 * @param response
	 * @param affixId
	 */
	public void download(HttpServletResponse response, String affixId);

	/**
	 * TODO 根据tableName，tableId查询附件
	 * @author 周俊林
	 * @Date 2018年3月19日 下午8:47:45
	 * @param tableName
	 * @param tableId
	 */
	public JSONArray affixList(String tableName, String tableId);

	/**
	 * TODO 保存业务ID到附件表
	 * @author 李利广
	 * @Date 2018年3月28日 上午10:39:59
	 * @param affixIds
	 * @param docId
	 */
	public Integer saveIdToAffix(String affixIds, String docId);
	
	/**
	 * 
	 * TODO 删除附件到服务器
	 * @author 冯超
	 * @Date 2018年5月14日 下午2:30:11
	 * @param file
	 * @param tableName
	 * @param tableId
	 * @param recordId
	 * @param fileListId
	 * @return
	 */
	public String fileuploadToServer(HttpServletRequest request, MultipartFile file, String tableName, String tableId,String recordId,String fileListId,String path);
	
	/**
	 * 
	 * TODO 从服务器上下载文件
	 * @author 冯超
	 * @Date 2018年5月14日 下午5:02:44
	 * @param response
	 * @param affixId
	 */
	public void downloadInServer(HttpServletResponse response, String affixId);
	
	/**
	 * 
	 * TODO 删除文件在服务器上
	 * @author 冯超
	 * @Date 2018年5月14日 下午6:58:08
	 * @param affixId
	 * @return
	 */
	public String getSavePath(String affixId);

	/**
	 * 下载pdf附件
	 * @param response
	 * @param affixId
	 */
	public void downloadPDF(HttpServletResponse response, String affixId);

	/**
	 * 判断业务数据中是否有附件
	 * @param tableId
	 * @param tableName
	 * @return
	 */
	Boolean hasAffix(String tableName,String tableId);

	/**
	 * @Author 王富康
	 * @Description //TODO 修改附件的回显div（档案编研列表附件删除某个子表信息时需要更改回显的div）
	 * @Date 2018/12/27 17:00
	 * @Param [tableId]
	 * @return void
	 **/
	void updateAffixFileListId(String tableId);
	
	/**
	 * 根据编研id，判断合并后的文件在数据库中是不是存在
	 * @author 王磊
	 * @Date 2018年12月19日 下午4:45:16
	 * @param bianYanId
	 * @return
	 */
	public String fileIsExist(String bianYanId);
	
	/**
	 * 读取服务器目录文件，将合并后的文件上传到数据库
	 * @author 王磊
	 * @Date 2018年12月19日 下午2:30:20
	 * @param bianYanId
	 * @param path
	 * @return 附件对应的id
	 * @throws FileNotFoundException 
	 */
	public String uploadFile(String bianYanId,String path) throws FileNotFoundException;
	
	/**
	 * 根据路径和编研id保存文件到服务路径并返回文件名（有顺序）
	 * @author 王磊
	 * @Date 2018年12月19日 上午10:56:25
	 * @param basePath
	 * @param bianYanId
	 * @return
	 */
	public List<String> saveAndGetFileNames(final String basePath, final String bianYanId);

	/**
	 * TODO 根据附件ID查询附件内容
	 * @author 李利广
	 * @Date 2019年03月05日 20:20:53
	 * @param affixId
	 * @return void
	 */
	String getAffixContent(String affixId);
}
