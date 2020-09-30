package com.sinosoft.sinoep.modules.system.component.affix.services;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.sinoep.wordTransfer.dao.FileResponsitory;
import com.sinosoft.sinoep.wordTransfer.services.WordTransferClient;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.UUIDGenerator;
import com.sinosoft.sinoep.modules.system.component.affix.dao.AffixDao;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONArray;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * TODO 
 * @author 周俊林
 * @Date 2018年3月15日 下午3:25:20
 */
@Service
@Transactional
public class AffixServiceImpl implements AffixService {

	@Autowired
	private AffixDao affixDao;

	@Autowired
	private FileResponsitory fileResponsitory;

	@Autowired
	private WordTransferClient wordTransferClient;

	@Override
	public AffixDao getRepository() {
		return this.affixDao;
	}

	/**
	 * TODO 上传文件
	 * @author 周俊林
	 * @Date 2018年3月15日 下午3:32:12
	 * @param file
	 * @param tableName
	 * @param tableId
	 * @param recordId
	 * @param defaultName //默认名称
	 */
	@Override
	public String fileUpload(MultipartFile file, String tableName, String tableId,String recordId,String fileListId,String defaultName) {
		String uuid = new UUIDGenerator().generateUUID32();
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", uuid);
		map.put("table_name", tableName);
		map.put("table_id", tableId);
		map.put("title", file.getOriginalFilename());
		map.put("cre_user_id", UserUtil.getCruUserId());
		map.put("cre_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		String fileName = file.getOriginalFilename();
		String suffix =  file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).toLowerCase();
		if(StringUtils.isNotBlank(defaultName) && !"undefined".equals(defaultName)){
			fileName = defaultName+suffix;
		}
		map.put("file_name", fileName);
		map.put("file_type", file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase());
		map.put("file_size", String.valueOf(file.getSize()));
		map.put("filelistid", fileListId);
//			map.put("content", "EMPTY_BLOB()");
		affixDao.fileupload(map, file);
		this.wordToPdf(uuid);//保存pdf文件
		return uuid;
	}

	/**
	 * TODO 根据id删除附件
	 * @author 周俊林
	 * @Date 2018年3月16日 上午10:47:35
	 * @param affixId
	 */
	@Override
	public void delete(String affixId) {
		affixDao.delete(CommonUtils.commomSplit(affixId));
	}

	/**
	 * TODO 根据id下载附件
	 * @author 周俊林
	 * @Date 2018年3月16日 上午10:47:44
	 * @param response
	 * @param affixId
	 */
	@Override
	public void download(HttpServletResponse response, String affixId) {
		affixDao.download(response,affixId);
	}

	/**
	 * TODO 查询附件
	 * @author 周俊林
	 * @Date 2018年3月19日 下午8:48:35
	 * @param tableName
	 * @param tableId
	 */
	@Override
	public JSONArray affixList(String tableName, String tableId) {
		return affixDao.affixList(tableName,tableId);
	}

	/**
	 * TODO 保存业务ID到附件表
	 * @author 李利广
	 * @Date 2018年3月28日 上午10:39:59
	 * @param affixIds
	 * @param docId
	 */
	@Override
	public Integer saveIdToAffix(String affixIds, String docId) {
		//this.wordToPdf(affixIds);//保存pdf文件
		return affixDao.saveIdToAffix(affixIds,docId);
	}

	/**
	 * 
	 * TODO 
	 * @author 冯超
	 * @Date 2018年5月14日 下午2:29:07
	 * @param file
	 * @param tableName
	 * @param tableId
	 * @param recordId
	 * @param fileListId
	 * @return
	 */
	@Override
	public String fileuploadToServer(HttpServletRequest request, MultipartFile file, String tableName, String tableId,String recordId,String fileListId,String path) {
		String uuid = new UUIDGenerator().generateUUID32();
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", uuid);
		map.put("table_name", tableName);
		map.put("table_id", tableId);
		map.put("title", file.getOriginalFilename());
		map.put("cre_user_id", UserUtil.getCruUserId());
		map.put("cre_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		map.put("file_name", file.getOriginalFilename());
		map.put("file_type", file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase());
		map.put("file_size", String.valueOf(file.getSize()));
		map.put("filelistid", fileListId);
//			map.put("content", "EMPTY_BLOB()");
		affixDao.fileuploadToServer(request, map, file, path);
		return uuid;
	}
	
	/**
	 * 
	 * TODO 根据id在服务器端下载附件
	 * @author 冯超
	 * @Date 2018年5月14日 下午5:03:42
	 * @param response
	 * @param affixId
	 */
	@Override
	public void downloadInServer(HttpServletResponse response, String affixId) {
		affixDao.downloadInServer(response,affixId);
	}
	
	/**
	 * 
	 * TODO 获取路径
	 * @author 冯超
	 * @Date 2018年5月14日 下午7:01:38
	 * @param affixId
	 * @return
	 */
	@Override
	public String getSavePath(String affixId){
		return affixDao.getSavePath(affixId);
	}

	/**
	 * 当保存附件表中业务id时，将文件转换成pdf存入数据库
	 * @param affixIds
	 */
	public void wordToPdf(String affixIds){
		if(StringUtils.isNotEmpty(affixIds)){
			String[] affixidsArray = affixIds.split(",");
			for(String affixId :affixidsArray){
				try {
					if(!affixDao.isHavePdfFile(affixId)){//不存在pdf文件才进行转换保存
						File file = fileResponsitory.getAffixContent(affixId);//获取数据库中文件
						String title = affixDao.getFileNameByAffixId(affixId);//获取附件名称
						String fileName = file.getName();//文件名
						String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
						title = title.substring(0,title.lastIndexOf(".")+1)+"pdf";
						System.out.println(title);
						if("doc,docx".contains(suffix.toLowerCase())){//如果该文件是doc,docx格式的则转换
							File pdfFile = wordTransferClient.getFilePdf(file);
							FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(pdfFile.toPath()),false,pdfFile.getName(),(int)pdfFile.length(),pdfFile.getParentFile());
							try(InputStream input = new FileInputStream(pdfFile); OutputStream os = fileItem.getOutputStream();){
								IOUtils.copy(input,os);
								MultipartFile mulFile = new CommonsMultipartFile(fileItem);//将File转成MultipartFile类型

								String uuid = this.pdfFileUpload(mulFile,title,affixId);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将转换好的pdf文件保存到数据库中并用file_name 保存员文件的id值
	 * @param file
	 * @param file_name
	 * @return
	 */
	public String pdfFileUpload(MultipartFile file,String title,String file_name){
		String uuid = new UUIDGenerator().generateUUID32();
		Map<String,String> map = new HashMap<String,String >();
		map.put("id",uuid);
		map.put("file_name",file_name);
		map.put("title",title);
		map.put("cre_user_id", UserUtil.getCruUserId());
		map.put("cre_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		map.put("file_type", file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase());
		affixDao.fileupload(map,file);
		return uuid;
	}

	/**
	 * 下载pdf附件
	 * @param response
	 * @param affixId
	 */
	@Override
	public void downloadPDF(HttpServletResponse response, String affixId) {
		affixDao.downloadPDF(response,affixId);
	}

	/**
	 * 判断业务数据中是否有附件
	 * @param tableId
	 * @param tableName
	 * @return
	 */
	@Override
	public Boolean hasAffix(String tableName,String tableId){
		return affixDao.hasAffix(tableName,tableId);
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 修改附件的回显div（档案编研列表附件删除某个子表信息时需要更改回显的div）
	 * @Date 2018/12/27 17:01
	 * @Param [tableId]
	 * @return void
	 **/
	@Override
	public void updateAffixFileListId(String tableId){
		affixDao.updateAffixFileListId(tableId);
	}
	
	/**
	 * 根据路径和编研id保存文件到服务路径并返回文件名（有顺序）
	 * @author 王磊
	 * @Date 2018年12月19日 上午10:56:25
	 * @param basePath
	 * @param bianYanId
	 * @return
	 */
	@Override
	public List<String> saveAndGetFileNames(final String basePath, final String bianYanId) {
		return affixDao.saveAndGetFileNames(basePath, bianYanId);
	}

	/**
	 * 读取服务器目录文件，将合并后的文件上传到数据库
	 * @author 王磊
	 * @Date 2018年12月19日 下午2:30:20
	 * @param bianYanId
	 * @param path
	 * @return 附件对应的id
	 * @throws FileNotFoundException 
	 */
	@Override
	@Transactional
	public String uploadFile(String bianYanId,String path) throws FileNotFoundException {
		return affixDao.uploadFile(bianYanId, path);
	}

	/**
	 * 根据编研id，判断合并后的文件在数据库中是不是存在
	 * @author 王磊
	 * @Date 2018年12月19日 下午4:45:16
	 * @param bianYanId
	 * @return
	 */
	@Override
	public String fileIsExist(String bianYanId) {
		return affixDao.fileIsExist(bianYanId);
	}

	/**
	 * TODO 根据附件ID查询附件内容
	 * @author 李利广
	 * @Date 2019年03月05日 20:20:53
	 * @param affixId
	 * @return void
	 */
	@Override
	public String getAffixContent(String affixId){
		return affixDao.getAffixContent(affixId);
	}
}
