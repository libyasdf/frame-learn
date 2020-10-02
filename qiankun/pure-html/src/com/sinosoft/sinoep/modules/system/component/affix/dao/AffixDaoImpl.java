package com.sinosoft.sinoep.modules.system.component.affix.dao;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.modules.dagl.constant.DAGLCommonConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.UUIDGenerator;
import com.sinosoft.sinoep.modules.system.component.affix.util.UploadUtil;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * TODO 
 * @author 周俊林
 * @Date 2018年3月16日 上午9:17:34
 */
@Repository
public class AffixDaoImpl implements AffixDao {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	/**
	 * TODO 上传附件
	 * @author 周俊林
	 * @Date 2018年3月16日 上午9:32:59
	 * @param map key 列名 value 值
	 * @param file 需要上传的文件
	 * @throws Exception
	 */
	@Override
	public void fileupload(Map<String, String> map, final MultipartFile file) {
		Vector<String> valueVec = new Vector<String>();
		String str1 = "";
		String str2 = "";
		
		if (map != null && map.size() > 0) {
			Iterator<String> iter = map.keySet().iterator();
			while (iter.hasNext()) {
				String next = (String) iter.next();
				str1 += "," + next;
				str2 += ",?";
				valueVec.add(map.get(next));
			}
		}
		str1 = str1.substring(1);
		str2 = str2.substring(1);
		String insertSql = "insert into sys_affix (" + str1 + ",content) values (" + str2 + ",EMPTY_BLOB())";
		log.debug(insertSql);
		jdbcTemplate.update(insertSql, valueVec.toArray());
		String selectSql = "select content from sys_affix where id = ? FOR UPDATE";
		log.debug(selectSql);
		jdbcTemplate.query(selectSql, new Object[] {map.get("id")}, new ResultSetExtractor<Object>() {
			
			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs != null && rs.next()) {
					Blob mapBlob = rs.getBlob(1);
					@SuppressWarnings("deprecation")
					OutputStream blobOutputStream = ((oracle.sql.BLOB) mapBlob).getBinaryOutputStream();
					try {
						CommonUtils.copyStream(file.getInputStream(), blobOutputStream);
					} catch (IOException e) {
						log.error(e.getMessage(),e);
					}
				}
				return null;
			}
			
		});
	}

	/**
	 * TODO 删除
	 * @author 周俊林
	 * @Date 2018年3月16日 上午10:32:34
	 * @param affixId
	 */
	@Override
	public void delete(String affixId) {
		List<Object> para = new ArrayList<>();
		String inString = CommonUtils.solveSqlInjectionOfIn(affixId, para);
		String sql = "delete from sys_affix where id in (" + inString + ")";
		jdbcTemplate.update(sql,para.toArray());
	}

	/**
	 * TODO 根据id下载附件
	 * @author 周俊林
	 * @Date 2018年3月16日 上午10:50:01
	 * @param response
	 * @param affixId
	 */
	@Override
	public void download(final HttpServletResponse response, String affixId) {
		String sql = "select content,file_name from sys_affix where id = ?";
		jdbcTemplate.query(sql, new Object[] {affixId}, new ResultSetExtractor<Object>() {

			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				try {
					if (rs != null && rs.next()) {
						Blob blob = rs.getBlob(1);
						response.setContentType("application/x-msdownload");
						String filename = rs.getString(2);
						String iso_filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
						 response.setHeader("Content-Disposition","attachment;filename=" + iso_filename);
						CommonUtils.copyStream(blob.getBinaryStream(), response.getOutputStream());
					}
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				} 
				return null;
			}
			
		});
	}

	/**
	 * TODO 查询附件
	 * @author 周俊林
	 * @Date 2018年3月19日 下午8:50:59
	 * @param tableName
	 * @param tableId
	 * @return 
	 */
	@Override
	public JSONArray affixList(String tableName, String tableId) {
		String sql = "select file_name,id,filelistid,save_path,file_size from sys_affix where table_name = ? and table_id = ?";
		JSONArray json = new JSONArray();
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, tableName,tableId);
		for (int i = 0, length = result.size(); i < length; i++) {
			JSONObject file = new JSONObject();
			Map<String, Object> map = result.get(i);
			file.put("name", map.get("file_name"));
			file.put("id", map.get("id"));
			file.put("fileListId", map.get("filelistid"));
			file.put("fileSize",map.get("file_size"));
			file.put("path",map.get("save_path"));
			json.add(file);
		}
		return json;
	}

	/**
	 * TODO 保存业务ID到附件表
	 * @author 李利广
	 * @Date 2018年3月28日 上午10:39:59
	 * @param affixIds
	 * @param docId
	 */
	@Override
	public Integer saveIdToAffix(String affixIds, String docId ) {
		List<Object> para = new ArrayList<>();
		para.add(docId);
		String inString = CommonUtils.solveSqlInjectionOfIn(affixIds, para);
		String sql = "update sys_affix set table_id = ? where id in (" +inString + ")";
		int count = jdbcTemplate.update(sql,para.toArray());
		return count > 0 ? count : 0;
	}
	
	/**
	 * 
	 * TODO 上传附件到服务器
	 * @author 冯超
	 * @Date 2018年5月14日 下午2:35:21
	 * @param map
	 * @param file
	 */
	@Override
	public void fileuploadToServer(HttpServletRequest request, Map<String, String> map, final MultipartFile file,String path) {
		Vector<String> valueVec = new Vector<String>();
		String str1 = "";
		String str2 = "";
		
		if (map != null && map.size() > 0) {
			Iterator<String> iter = map.keySet().iterator();
			while (iter.hasNext()) {
				String next = (String) iter.next();
				str1 += "," + next;
				str2 += ",?";
				valueVec.add(map.get(next));
			}
		}
		str1 = str1.substring(1);
		str2 = str2.substring(1);
		String insertSql = "insert into sys_affix (" + str1 + ",content) values (" + str2 + ",EMPTY_BLOB())";
		log.debug(insertSql);
		jdbcTemplate.update(insertSql, valueVec.toArray());
		UploadUtil uploadUtil = new UploadUtil();
		String savPath = uploadUtil.uploadImage(request, path+DateUtil.getDateText(new Date(), "yyyyMMdd")+File.separator, file, true);
		String sql = "update sys_affix set save_path = ? where id = ?" ;
		jdbcTemplate.update(sql,savPath,map.get("id"));
	}
	
	/**
	 * 
	 * TODO 根据id下载附件在服务器端
	 * @author 冯超
	 * @Date 2018年5月14日 下午5:26:09
	 * @param response
	 * @param affixId
	 */
	@Override
	public void downloadInServer(final HttpServletResponse response, String affixId) {
		String sql = "select save_path,file_name from sys_affix where id = ?";
		jdbcTemplate.query(sql, new Object[] {affixId}, new ResultSetExtractor<Object>() {

			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				try {
					if (rs != null && rs.next()) {
						String path = rs.getString(1);
						response.setContentType("application/x-msdownload");
						String filename = rs.getString(2);
						String iso_filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
						response.setHeader("Content-Disposition","attachment;filename=" + iso_filename);
						CommonUtils.copyStream(new FileInputStream(path), response.getOutputStream());
					}
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				} 
				return null;
			}
			
		});
	}
	
	/**
	 * 
	 * TODO 获取文件路径更加id
	 * @author 冯超
	 * @Date 2018年5月14日 下午6:20:24
	 * @param affixId
	 * @return
	 */
	@Override
	public String getSavePath(String affixId){
		StringBuilder sb = new StringBuilder("select save_path from sys_affix where id = ?");
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString(),affixId);
		String path = list.get(0).get("save_path").toString();
		return path;
	}

	@Override
	public boolean isHavePdfFile(String affixId) {
		boolean flag = false;
		if(StringUtils.isNotBlank(affixId)){
			StringBuilder sql = new StringBuilder("");
			sql.append("select id from sys_affix where file_name = ?");
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql.toString(),affixId);
			if(list.size()>0){
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 根据附件id获取附件名
	 * @param affixId
	 * @return
	 */
	@Override
	public String getFileNameByAffixId(String affixId){
		String fileName= "";
		try{
			if(StringUtils.isNotBlank(affixId)){
				String sql = "select file_name from sys_affix where id = ?";
				List<Map<String,Object>> list = jdbcTemplate.queryForList(sql.toString(),affixId);
				Map<String,Object> map = new HashMap<String,Object>();
				if(list.size()>0){
					map = list.get(0);
				}
				fileName =(String) map.get("file_name");
			}
		}catch (Exception e){
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return fileName;
	}

	/**
	 *下载pdf附件
	 * @param response
	 * @param affixId
	 */
	@Override
	public void downloadPDF(final HttpServletResponse response, String affixId) {
		String fileName= this.getFileNameByAffixId(affixId);
		String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
		String sql = "";
		if("pdf".equals(suffix)){
			sql = "select content,title from sys_affix where ID=?";
		}else {
			sql = "select content,title from sys_affix where file_name=?";
		}
		jdbcTemplate.query(sql, new Object[]{affixId}, new ResultSetExtractor<Object>() {
			@Override
			public Object extractData(ResultSet rs) throws SQLException,DataAccessException{
				try{
					if(rs != null && rs.next()){
						Blob blob = rs.getBlob(1);
						String fileName = rs.getString(2);
						String iso_filename = new String(fileName.getBytes("GBK"),"ISO-8859-1");
						response.setStatus(HttpServletResponse.SC_OK);
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition","attachment;filename=" + iso_filename);
						CommonUtils.copyStream(blob.getBinaryStream(),response.getOutputStream());
					}
				}catch (Exception e){
					log.error(e.getMessage(),e);
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	/**
	 * 判断业务数据中是否有附件
	 * @param tableId
	 * @param tableName
	 * @return
	 */
	@Override
	public Boolean hasAffix(String tableName,String tableId){
		String sql = "select count(1) from sys_affix t where t.table_name = ? and t.table_id = ?";
		Integer count = jdbcTemplate.queryForObject(sql,Integer.class,tableName,tableId);
		return count != 0 ? true : false;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 修改附件的回显div（档案编研列表附件删除某个子表信息时需要更改回显的div）
	 * @Date 2018/12/27 17:00
	 * @Param [tableId]
	 * @return void
	 **/
	@Override
	public void updateAffixFileListId(String tableId){
		List<Object> para = new ArrayList<>();
		String inString = CommonUtils.solveSqlInjectionOfIn(tableId, para);
		//div的id存的为10000+列表的序号，删除档案的时候有改动的子表对应的附件id-1；
		String sql = "update sys_affix t set t.filelistid = t.filelistid-1 where t.table_id in ("+inString+")";
		int updatePartyNumRule = jdbcTemplate.update(sql,para.toArray());

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
		//根据主表id查找所有子表已经确认的附件id
		final StringBuffer strb = new StringBuffer("SELECT sa.file_name, sa.content\n" +
				"     FROM sys_affix sa, dagl_bygl_sub dbs\n" +
				"    where dbs.pid = ?\n" +
				"      and dbs.status = '"+DAGLCommonConstants.STUDYING_SUB_STATUS[1] +"'\n" +
				"      and dbs.visible = '"+CommonConstants.VISIBLE[1] +"'\n" +
				"      and dbs.id = sa.table_id\n" +
				"      and sa.table_name ='studying_bak'");
    	//下载文件到指定目录
    	final List<String> fileNameList = new ArrayList<String>();
    	jdbcTemplate.query(strb.toString(), new Object[] {bianYanId},new ResultSetExtractor<Object>() {
			@Override
			public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				try {
				    int i =1;
					while(rs != null && rs.next()) {
						Blob blob = rs.getBlob(2);
						String filename = "("+i+")"+rs.getString(1);
						String inputPath = basePath + bianYanId+ "/"+ filename;
						fileNameList.add(filename);
						File inputFile = new File(inputPath);
						FileUtils.copyInputStreamToFile(blob.getBinaryStream(), inputFile);
						i++;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return fileNameList;
			}

		});
		return fileNameList;
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
		//先删除已经存在的附件
		String delSql = "delete from sys_affix sa where sa.table_id= ?";
		jdbcTemplate.update(delSql,bianYanId);
		String uuid = new UUIDGenerator().generateUUID32();
		//将汇总后的word文件插入数据库
		String insertSql = "insert into sys_affix(id,table_name,table_id,file_name,cre_user_id,cre_time,content,filelistid) "
		+"values('"+uuid+"','dagl_bygl_main',?,'编研材料.docx','"+UserUtil.getCruUserId()+"','"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"',EMPTY_BLOB(),'files')";
		jdbcTemplate.update(insertSql,bianYanId);
		//合并后的文件名：编研管理合并后的用户文件.docx
		File file = new File(path+"编研管理合并后的用户文件.docx");
		final FileInputStream fis = new FileInputStream(file);
		String selectSql = "select content from sys_affix where id = ? FOR UPDATE";
		jdbcTemplate.query(selectSql, new Object[] {uuid}, new ResultSetExtractor<Object>() {
			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs != null && rs.next()) {
					Blob mapBlob = rs.getBlob(1);
					@SuppressWarnings("deprecation")
					OutputStream blobOutputStream = ((oracle.sql.BLOB) mapBlob).getBinaryOutputStream();
					try {
						CommonUtils.copyStream(fis, blobOutputStream);
					} catch (IOException e) {
						log.error(e.getMessage(),e);
					}
				}
				return null;
			}
			
		});
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uuid;
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
		String fileIsExist = "";
		String selectSql = "select length(content) as filesize,id from sys_affix where table_id = ?";
		fileIsExist = (String) jdbcTemplate.query(selectSql, new Object[] {bianYanId}, new ResultSetExtractor<Object>() {
			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs != null && rs.next()) {
					int fileSize = rs.getInt(1);
					String affixId = rs.getString(2);
					//只有存在记录且blob字段长度大于0才表明已经将转化后的文件成功上传到数据库了
					if(fileSize > 0){
						return affixId;
					}
				}
				return "";
			}
			
		});
		return fileIsExist;
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
		String res = "";
		try {
			String sql = "select t.content from sys_affix t where t.id = ?";
			res = jdbcTemplate.query(sql, new Object[] {affixId}, new ResultSetExtractor<String>() {
				@Override
				public String extractData(ResultSet rs) throws SQLException, DataAccessException {
					String res = null;
					try {
						if (rs != null && rs.next()) {
							Blob blob = rs.getBlob(1);
							InputStream msgContent = blob.getBinaryStream();
							ByteArrayOutputStream output = new ByteArrayOutputStream();
							byte[] buffer = new byte[500];
							int n;
							while(-1 != (n = msgContent.read(buffer))) {
								output.write(buffer, 0, n);
							}
							byte[] data = Base64.encodeBase64(output.toByteArray());
							res = new String(data);
							output.close();
						}
					} catch (Exception e) {
						log.error(e.getMessage(),e);
					}
					return res;
				}

			});
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return res;
	}

}
