package com.sinosoft.sinoep.modules.system.component.affix.dao;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONArray;

/**
 * TODO 附件
 * @author 周俊林
 * @Date 2018年3月15日 下午3:26:58
 */
public interface AffixDao {
	
	/**
	 * TODO 上传附件
	 * @author 周俊林
	 * @Date 2018年3月16日 上午9:30:52
	 * @param map key 列名，value 值
	 * @param file 需要上传的文件
	 * @throws Exception
	 */
	public void fileupload(Map<String, String> map, MultipartFile file);

	/**
	 * TODO 删除
	 * @author 周俊林
	 * @Date 2018年3月16日 上午10:18:44
	 * @param affixId
	 */
	public void delete(String affixId);

	/**
	 * TODO 根据id下载附件
	 * @author 周俊林
	 * @Date 2018年3月16日 上午10:49:37
	 * @param response
	 * @param affixId
	 */
	public void download(HttpServletResponse response, String affixId);

	/**
	 * TODO 查询附件
	 * @author 周俊林
	 * @Date 2018年3月19日 下午8:50:37
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
	 * TODO 上传附件到服务器
	 * @author 冯超
	 * @Date 2018年5月14日 下午2:32:17
	 * @param map
	 * @param file
	 */
	public void fileuploadToServer(HttpServletRequest request, Map<String, String> map, MultipartFile file, String path);
	
	/**
	 * 
	 * TODO 根据id下载附件在服务器上
	 * @author 冯超
	 * @Date 2018年5月14日 下午5:05:33
	 * @param response
	 * @param affixId
	 */
	public void downloadInServer(HttpServletResponse response, String affixId);
	
	/**
	 * 
	 * TODO 获取文件路径
	 * @author 冯超
	 * @Date 2018年5月14日 下午6:19:31
	 * @param affixId
	 * @return
	 */
	public String getSavePath(String affixId);

	/**
	 * 根据affixId判断是否已经存在转换后的pdf文件
	 * @param affixId
	 * @return true ：存在 false：不存在
	 */
	public boolean isHavePdfFile(String affixId);

	/**
	 * 根据附件id获取附件名
	 * @param affixId
	 * @return
	 */
	public String getFileNameByAffixId(String affixId);
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
