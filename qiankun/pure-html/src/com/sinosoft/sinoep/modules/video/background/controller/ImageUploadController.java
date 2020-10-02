package com.sinosoft.sinoep.modules.video.background.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.sinosoft.sinoep.modules.system.component.affix.services.AffixService;
import com.sinosoft.sinoep.modules.video.background.services.VideoContentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/video/content/imageUp")
public class ImageUploadController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AffixService affixService;
	@Autowired
	private VideoContentService videoContentService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 使用bootstrap fileinput 插件图片上传后台 TODO
	 * 
	 * @author 郝灵涛
	 * @Date 2018年12月1日 下午5:26:16
	 * @param request
	 * @param fish_file
	 * @param tableName
	 * @param tableId
	 * @param recordId
	 * @param fileListId
	 * @param path
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/imageUpload", method = RequestMethod.POST)
	public JSONObject uploadToServerPath(HttpServletRequest request, MultipartFile fish_file, String tableName,
			String tableId, String recordId, String fileListId, String path,String imageId) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		JSONArray files = new JSONArray();
		json.put("flag", "0");
		json.put("msg", "图片上传失败");
		json.put("data", null);
		// 上传
		path = request.getRealPath("/") + "upload" + File.separator + path
				+ File.separator;
		System.out.println("path: " + request.getSession().getServletContext().getRealPath(""));
		System.out.println("path1: " + path);

		// File file = new File(request.getRealPath("")+filePath);
		try {
			//删除之前的
			if(StringUtils.isNotBlank(imageId)){
				return json;
			}
			String sql="delete from sys_affix t where t.table_id=?";
			jdbcTemplate.update(sql,tableId);
			String id = affixService.fileuploadToServer(request, fish_file, tableName, tableId, recordId, fileListId,
					path);
			if (StringUtils.isNotBlank(id)) {
				//更新业务表 的图片id
				videoContentService.updateImageId(tableId,id);
				/*String updateSql = "update video_content set image_id='" + id + "' where id='" + tableId + "'";
				jdbcTemplate.execute(updateSql);;*/
				
				JSONObject jfile = new JSONObject();
				jfile.put("name", fish_file.getOriginalFilename());
				jfile.put("id", id);
				String savePath = affixService.getSavePath(id);
				jfile.put("path", savePath);
				files.add(jfile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (files.size() > 0) {
			json.put("flag", "1");
			json.put("msg", "图片上传成功" + files.size() + "个文件！");
			data.put("files", files);
			json.put("data", data);
		}
		log.info(json.toString());
		return json;// 可以
	}

	/**
	 * 根据tableId 和tableName查找文件的路径 TODO
	 * 
	 * @author 郝灵涛
	 * @Date 2018年11月29日 下午4:43:32
	 * @param tableName
	 * @param tableId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPicPath", method = RequestMethod.GET)
	public JSONObject affixList(String tableName, String tableId) {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		json.put("flag", "0");
		json.put("msg", "");
		json.put("data", null);
		if (StringUtils.isNotBlank(tableName) && StringUtils.isNotBlank(tableId)) {
			try {
				String sql = "select file_name,id,filelistid,save_path,file_size from sys_affix where table_name = ? and table_id = ? order by cre_time desc";
				JSONArray jsonAry = new JSONArray();
				List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, tableName, tableId);
				for (int i = 0, length = result.size(); i < length; i++) {
					JSONObject file = new JSONObject();
					Map<String, Object> map = result.get(i);
					file.put("name", map.get("file_name"));
					file.put("id", map.get("id"));
					file.put("fileListId", map.get("filelistid"));
					file.put("path", map.get("save_path"));
					file.put("fileSize", map.get("file_size"));
					jsonAry.add(file);
				}
				// JSONArray files = affixService.affixList(tableName, tableId);
				data.put("files", jsonAry);
				json.put("data", data);
				json.put("msg", "");
				json.put("flag", "1");
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return json;
	}

	/**
	 * 根据affixId删除服务器上的图片 TODO
	 * 
	 * @author 郝灵涛
	 * @Date 2018年12月1日 下午5:27:05
	 * @param affixId
	 *            图片存放路径id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteInServer", method = RequestMethod.POST)
	public JSONObject deleteInServer(String id) {
		JSONObject json = new JSONObject();
		json.put("flag", 0);
		if (StringUtils.isNotBlank(id)) {
			StringBuilder sb = new StringBuilder("select id,save_path from sys_affix where table_id = ? ");
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString(),id);
			String path = list.get(0).get("save_path").toString();
			String imageId=list.get(0).get("id").toString();
			if (deleteFile(path)) {
				try {
					affixService.delete(imageId);
					json.put("flag", "1");
					json.put("msg", "删除成功");
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					json.put("flag", "0");
				}
			}

		}
		return json;
	}

	/**
	 * 根据文件路径删除文件 TODO
	 * 
	 * @author 郝灵涛
	 * @Date 2018年12月1日 下午5:24:27
	 * @param fileName
	 * @return
	 */
	public boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				log.info("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				log.info("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			log.info("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}
}
