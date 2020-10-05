package com.sinosoft.sinoep.modules.system.component.template.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.modules.system.component.affix.util.UploadUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * TODO 
 * @author 王富康
 * @Date 2018年07月10日 下午18:18
 */
@Repository
public class TemplateDaoImpl implements TemplateDao {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/**
	 * TODO 根据id下载模板
	 * @author 周俊林
	 * @Date 2018年07月10日 下午18:18
	 * @param response
	 * @param templateId
	 */
	@Override
	public void download(final HttpServletResponse response, String templateId) {
		String sql = "select content,title from sys_template where id = ?";
		jdbcTemplate.query(sql, new Object[] {templateId}, new ResultSetExtractor<Object>() {

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
}
