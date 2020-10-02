package com.sinosoft.sinoep.wordTransfer.dao;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;



@Repository
public class FileResponsitoryImpl implements FileResponsitory {

	private static Logger log = LoggerFactory.getLogger(FileResponsitory.class);
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate(){
		if(jdbcTemplate == null){
			synchronized (this) {
				if(jdbcTemplate == null){
					jdbcTemplate = new JdbcTemplate(dataSource);
				}
			}
		}
		return jdbcTemplate;
	}

	@Override
	public File getAffixContent(String affixId) {
		String sql = "select file_type,content from sys_affix where id='"+affixId+"'";
		RowMapper<File> rowMapper = new RowMapper<File>() {
			public File mapRow(ResultSet rs ,int rowNum) throws SQLException{
				try {
					File file = File.createTempFile(UUID.randomUUID().toString(), "." +rs.getString(1));//创建文件
					FileUtils.copyInputStreamToFile(rs.getBinaryStream(2), file);//拷贝内容到文件中
					return file;
				} catch (IOException e) {
					log.error("拷贝正文到文件失败");
					throw new RuntimeException("拷贝正文到文件失败",e);
				}
			}
		};
		return getJdbcTemplate().queryForObject(sql, rowMapper);
	}
}
