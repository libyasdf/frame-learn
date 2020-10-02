package com.sinosoft.sinoep.modules.invocation.codegeneration.service.impl;


import com.sinosoft.sinoep.modules.invocation.codegeneration.service.CodeGenerationServiceI;
import com.sinosoft.sinoep.modules.djgl.internalsecurityoffice.responsibilitybook.toolitem.entity.Help;
import com.sinosoft.sinoep.modules.djgl.internalsecurityoffice.responsibilitybook.toolitem.entity.ReturnClass;
import com.sinosoft.sinoep.modules.djgl.internalsecurityoffice.responsibilitybook.toolitem.tool.Helper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 子火
 * @Date 2019年4月10日09:38:20
 */

@Service("codeGenerationService")
@Transactional(rollbackFor = Exception.class)
public class CodeGenerationServiceImpl implements CodeGenerationServiceI {
	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 代码生成的默认地址
	 */
	private String codePath="";
	/**
	 * 模板路径
	 */
	private String ftlUrl="";

	@Override
	public ReturnClass autoCode(String tabN,String packageN,String packagen,String packAgeN,HttpServletRequest request)throws Exception{
		String sql="SELECT a.TABLE_NAME,a.COLUMN_NAME,replace(INITCAP(a.COLUMN_NAME),'_') AS INITCAP,a.COMMENTS,b.comments AS TABLE_COMMENTS\n" +
				",col.DATA_TYPE,col.char_length,col.data_precision,col.data_scale,col.NULLABLE " +
				"from user_col_comments a \n" +
				"INNER JOIN user_tab_comments b\n" +
				"ON a.Table_Name=b.Table_Name \n" +
				"INNER JOIN user_tab_columns col\n" +
				"on col.COLUMN_NAME=a.COLUMN_NAME \n" +
				"where a.Table_Name='DAGL_FILE' and col.Table_Name=?";
		List<Map<String, Object>> list=jdbc.queryForList(sql,tabN);
		if(list.size()>0){
			for(Map<String, Object> listmap: list){
				listmap.put("CAPITALIZE",StringUtils.uncapitalize((String)listmap.get("INITCAP")));
			}
			ftlUrl=request.getSession().getServletContext().getRealPath("/")+"WEB-INF/classes/com/sinosoft/sinoep/modules/invocation/codegeneration/ftl";
			codePath="D:/codeGeneration/"+packagen+"/";
			boolean bolent=createEntity(list,tabN, packageN, packagen, packAgeN);
			if(bolent){
				return Help.returnClassT(200,"生成成功","");
			}else{
				return Help.returnClassT(300,"生成失败","");
			}

		}else{
			return Help.returnClassT(300,"无查询结果,注意表名大小写","无查询结果");
		}




	}
	/**
	 * 生成实体类页面
	 * @author 子火
	 * @Date 2019年5月9日14:45:34
	 * @param tablelist 表的说明
	 * @return false 说明异常
	 */
	public boolean createEntity(List<Map<String, Object>> tablelist,String tabN,String packageN,String packagen,String packAgeN){
		Map<String,Object> map=new HashMap();
		map.put("cur_time",new Date());
		map.put("table_name",tabN);
		map.put("entity_name",packAgeN+"Entity");
		map.put("table_comments",tablelist.get(0).get("TABLE_COMMENTS"));
		map.put("col_list",tablelist);
		String tempName="entity.ftl";
		String fileName=packAgeN+"Entity.java";
		String pageName="entity";
		boolean reabol=template(map, tempName,fileName,pageName);
		return reabol;
	}


	/**
	 * 模板生成通用方法
	 * @author 子火
	 * @Date 2019年5月9日14:45:34
	 * @param map 动态参
	 * @param tempName 模板名称
     * @param fileName 生成文件名称
	 * @param pageName 文件所在文件夹名称
	 * @return false 说明异常
	 */
	public boolean template(Map map,String tempName,String fileName,String pageName){
		boolean reabol=true;
		try{
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(ftlUrl));  //在这下找模版
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			Template temp = cfg.getTemplate(tempName);  // 模版名称
			File dir = new File(codePath+pageName);//保存路径
			if(!dir.exists()){
				dir.mkdirs();
			}
			OutputStream fos = new FileOutputStream( new File(dir, fileName)); //文件的生成名称
			Writer out = new OutputStreamWriter(fos);
			temp.process(map, out);
			fos.flush();
			fos.close();

		}catch(Exception e){
			System.out.println(Helper.exceptionToString(e));
			reabol=false;
			return reabol;
		}
		return reabol;
	}
	
}