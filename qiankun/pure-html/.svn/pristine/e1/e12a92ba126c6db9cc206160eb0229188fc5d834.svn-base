package com.sinosoft.sinoep.modules.dagl.bygl.services;

import com.alibaba.fastjson.JSON;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dagl.bygl.dao.StudyingDao;
import com.sinosoft.sinoep.modules.dagl.bygl.dao.StudyingSubDao;
import com.sinosoft.sinoep.modules.dagl.bygl.entity.Studying;
import com.sinosoft.sinoep.modules.dagl.bygl.entity.StudyingSub;
import com.sinosoft.sinoep.modules.dagl.bygl.entity.UserDeptVo;
import com.sinosoft.sinoep.modules.dagl.constant.DAGLCommonConstants;
import com.sinosoft.sinoep.modules.system.component.affix.services.AffixService;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import workflow.common.JDateToolkit;

/**
 * @Author 王富康
 * @Description //TODO 编研管理Serivce实现层
 * @Date 2018/12/18 20:22
 * @Param
 * @return
 **/
@Service
public class StudyingServiceImpl implements StudyingService{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudyingDao studyingDao;

    @Autowired
    private StudyingSubDao studyingSubDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AffixService affixService;

	@Autowired
	private UserInfoService userInfoService;
    
    /**
     * 合并两个word文档
     * @Date 2018年12月19日 上午10:44:55
     * @param document
     * @param doucDocument2
     * @return
     * @throws XmlException
     */
    @Override
    public XWPFDocument mergeWord(XWPFDocument document,XWPFDocument doucDocument2) throws Exception {
	    XWPFDocument src1Document =document ;
	    XWPFParagraph p = src1Document.createParagraph();
	    //设置分页符
	    p.setPageBreak(true);
	    p.removeRun(1);
	    CTBody src1Body = src1Document.getDocument().getBody();
	    XWPFDocument src2Document = doucDocument2;
	    CTBody src2Body = src2Document.getDocument().getBody();
//	    XWPFParagraph p2 = src2Document.createParagraph();


        List<XWPFPictureData> allPictures = doucDocument2.getAllPictures();
        // 记录图片合并前及合并后的ID
        Map<String,String> map = new HashMap();
        for (XWPFPictureData picture : allPictures) {
            String before = doucDocument2.getRelationId(picture);

            //获取图片的类型
            int picType = picture.getPictureType();

            //将原文档中的图片加入到目标文档中
            String after = document.addPictureData(picture.getData(), picType);
            map.put(before, after);
        }



        XmlOptions optionsOuter = new XmlOptions();
	    optionsOuter.setSaveOuter();
	    String appendString = src2Body.xmlText(optionsOuter);
	    String srcString = src1Body.xmlText();
	    String prefix = srcString.substring(0,srcString.indexOf(">")+1);
	    String mainPart = srcString.substring(srcString.indexOf(">")+1,srcString.lastIndexOf("<"));
	    String sufix = srcString.substring( srcString.lastIndexOf("<") );
	    String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));

        if (map != null && !map.isEmpty()) {
            //对xml字符串中图片ID进行替换
            for (Map.Entry<String, String> set : map.entrySet()) {
                addPart = addPart.replace(set.getKey(), set.getValue());
            }
        }

	    CTBody makeBody = CTBody.Factory.parse(prefix+mainPart+addPart+sufix);
	    src1Body.set(makeBody);
	    return src1Document;
	}

    /**
	 * 根据文件路径和文件名合并文件
	 * @author 王磊
	 * @Date 2018年12月19日 上午10:46:31
	 * @param path
	 * @param fileNameList
	 */
	@Override
	public void mergeWords(String path, List<String> fileNameList) {
		
		List<OPCPackage> opcpList = new ArrayList<OPCPackage>();
		/**
		 * 循环获取每个docx文件的OPCPackage对象
		 */
		for (int i = 0; i < fileNameList.size(); i++) {
			String doc = fileNameList.get(i);
			OPCPackage srcPackage =  null;
			try {
				srcPackage = OPCPackage.open(path+doc);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(null != srcPackage){
				opcpList.add(srcPackage);
			}
		}
		try {
			XWPFDocument xmd=new XWPFDocument(opcpList.get(0));
			for (int i=0;i<opcpList.size()-1;i++) {
	        	xmd=mergeWord(xmd,new XWPFDocument(opcpList.get(i+1))); //相继合并
	        	opcpList.get(i+1).close();
			}
			OutputStream dest = new FileOutputStream(path+"编研管理合并后的用户文件.docx");
			xmd.write(dest);
			xmd.close();
			log.info("编研管理合并文件成功！");
		} catch (Exception e) {
			log.error("***********************编研管理合并文件出现异常***********************");
			e.printStackTrace();
		}
		
	}


	/**
	 * 根据路径和编研id保存文件到服务路径并返回文件名（有顺序）
	 * @author 王磊
	 * @Date 2018年12月19日 上午10:56:25
	 * @param basePath
	 * @param bianYanId
	 * @return
	 */
	/*@Override
	public List<String> saveAndGetFileNames(final String basePath, final String bianYanId) {
		//根据主表id查找所有子表已经确认的附件id
		final StringBuffer strb = new StringBuffer("SELECT sa.file_name, sa.content\n" +
				"     FROM sys_affix sa, dagl_bygl_sub dbs\n" +
				"    where dbs.pid = ?\n" +
				"      and dbs.status = '1'\n" +
				"      and dbs.visible = '1'\n" +
				"      and dbs.id = sa.table_id\n" +
				"      and sa.table_name ='studying_bak'");


		*//*final StringBuffer strb = new StringBuffer("select sa.file_name,sa.content from sys_affix sa right join ");
    	strb.append(" (select dbs.id||'_new' as id from dagl_bygl_sub dbs where dbs.pid=? and dbs.status='1' and dbs.visible='1' order by dbs.sequence asc) dbs");
    	strb.append(" on dbs.id=sa.table_id");*//*
    	//下载文件到指定目录
    	final List<String> fileNameList = new ArrayList<String>();
    	jdbcTemplate.query(strb.toString(), new Object[] {bianYanId},new ResultSetExtractor<Object>() {
			@Override
			public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				try {
					while(rs != null && rs.next()) {
						Blob blob = rs.getBlob(2);
						String filename = rs.getString(1);
						String inputPath = basePath + bianYanId+ "/"+ filename;
						fileNameList.add(filename);
						File inputFile = new File(inputPath);
						FileUtils.copyInputStreamToFile(blob.getBinaryStream(), inputFile);
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
	}*/

	/**
	 * 根据服务器路径和文件名下载文件
	 * @author 王磊
	 * @Date 2018年12月19日 下午5:30:16
	 * @param path
	 * @param fileName
	 */
	@Override
	public void downloadFormServer(HttpServletResponse response,String path, String fileName) {
		File file = new File(path+fileName);
		FileInputStream fis = null ;
		try {
			response.setContentType("application/x-msdownload");
			String iso_filename = new String(fileName.getBytes("GBK"), "ISO-8859-1");
			 response.setHeader("Content-Disposition","attachment;filename=" + iso_filename);
			fis = new FileInputStream(file);
			CommonUtils.copyStream(fis, response.getOutputStream());
		} catch (IOException e) {
			log.info("从服务器下载编研管理合并文件异常！");
			e.printStackTrace();
		}finally{
			try {
				fis.close();
			} catch (IOException e) {
				log.info("编研管理关闭文件输入流失败！");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查找当前编研记录还没有确认的处室名
	 * @author 王磊
	 * @Date 2018年12月20日 下午3:24:16
	 * @param bianYanId
	 * @return 还没有确认的处室名（逗号分隔）
	 */
	@Override
	public String findChuShiNotConfirm(String bianYanId) {
		String chuShiNames = "";
		String sql = "select to_char(WMSYS.WM_CONCAT(dbs.rec_chushi_name)) as chushi_name "
				+ " from dagl_bygl_sub dbs " +
				"	where dbs.VISIBLE='"+CommonConstants.VISIBLE[1]+"' " +//未删除
				"	and dbs.status='"+DAGLCommonConstants.STUDYING_SUB_STATUS[0]+"' " +//未确认
				"	and dbs.pid=? " +//该编研
				"	order by dbs.sequence asc ";
		Map<String,Object> map = jdbcTemplate.queryForMap(sql,bianYanId);
		if(null != map.get("chushi_name")){
			chuShiNames = map.get("chushi_name").toString();
		}
		return chuShiNames;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 查询编研的列表
	 * @Date 2018/12/20 11:21
	 * @Param [pageable, pageImpl, month, isReport, reportStatus]
	 * @return com.sinosoft.sinoep.common.util.PageImpl
	 **/
	@Override
	public PageImpl getStudyingData(Pageable pageable, PageImpl pageImpl, Studying studying){
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();

		querySql.append(" from Studying t where 1=1 and t.visible='"+CommonConstants.VISIBLE[1]+"'");

		//拼接条件
		if (StringUtils.isNotBlank(studying.getYear())) {
			querySql.append("   and t.year = ?");
			para.add(""+studying.getYear()+"");
		}

		if (StringUtils.isNotBlank(studying.getTitle())) {
			querySql.append("   and t.title like ?");
			para.add("%"+studying.getTitle()+"%");
		}

		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append("  order by t.year desc ) ");
		} else {
			querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + " ");
		}

		Page<Studying> page = studyingDao.query(querySql.toString(), pageable,para.toArray());
		return new PageImpl((int)page.getTotalElements(),page.getContent());
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 删除编研信息
	 * @Date 2018/12/20 14:12
	 * @Param [reportId]
	 * @return int
	 **/
	@Override
	public int deleteStudying(String id) {
		//删除主表数据
		String jpql = "update Studying t set t.visible = ? where t.id = ?";
		try {
			List<StudyingSub> studyingSubs = getStudyingFenFaData(id,"","");
			String ids = "";
			String affixIds = "";
			for(int i=0 ; i<studyingSubs.size(); i++){
				ids += studyingSubs.get(i).getId()+",";
				JSONArray files = affixService.affixList("studying",studyingSubs.get(i).getId());
				for(int j=0;j<files.size();j++){
					JSONObject jsonArray = files.getJSONObject(j);
					affixIds += jsonArray.getString("id")+",";
				}
			}
			//删除子表数据，及附件
			deleteStudyingSub(ids,affixIds);
		}catch (Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return studyingDao.update(jpql,CommonConstants.VISIBLE[0], id);
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 新增/修改
	 * @Date 2018/12/20 17:25
	 * @Param [dut]
	 * @return com.sinosoft.sinoep.modules.dagl.bygl.entity.Studying
	 **/
	@Override
	public Studying saveStudying(Studying studying){

		//获取子表数据
		List<StudyingSub> studyingSubs = studying.getStudyingSubs();

		//主表数据的新增修改
		if(StringUtils.isNotBlank(studying.getId())){
			//id不为空是修改操作
			Studying oldStudying = studyingDao.findOne(studying.getId());

			oldStudying.setYear(studying.getYear());
			oldStudying.setTitle(studying.getTitle());

			if(StringUtils.isNotBlank(studying.getStatus())){
				oldStudying.setStatus(studying.getStatus());
			}

			studying = studyingDao.save(oldStudying);
		}else{
			//id为空是新增操作
			studying.setCreUserId(UserUtil.getCruUserId());
			studying.setCreUserName(UserUtil.getCruUserName());
			studying.setCreDeptId(UserUtil.getCruDeptId());
			studying.setCreDeptName(UserUtil.getCruDeptName());
			studying.setCreChushiId(UserUtil.getCruChushiId());
			studying.setCreChushiName(UserUtil.getCruChushiName());
			studying.setCreJuId(UserUtil.getCruJuId());
			studying.setCreJuName(UserUtil.getCruJuName());
			studying.setVisible(CommonConstants.VISIBLE[1]);
			studying.setCreTime(JDateToolkit.getNowDate4());

			//默认状态为待分发
			studying.setStatus(DAGLCommonConstants.STUDYING_STATUS[0]);

			studying = studyingDao.save(studying);
		}

		//保存子表数据
		if(studyingSubs.size()>0){
			//存放保存返回的数据
			List<StudyingSub> studyingSubList = new ArrayList<StudyingSub>();
			for (int i=0;i<studyingSubs.size();i++){
				if(StringUtils.isBlank(studyingSubs.get(i).getId())){
					//新增
					//父表的一些信息
					studyingSubs.get(i).setPid(studying.getId());
					studyingSubs.get(i).setTitle(studying.getTitle());
					studyingSubs.get(i).setYear(studying.getYear());
					//默认的信息
					studyingSubs.get(i).setCreUserId(UserUtil.getCruUserId());
					studyingSubs.get(i).setCreUserName(UserUtil.getCruUserName());
					studyingSubs.get(i).setCreDeptId(UserUtil.getCruDeptId());
					studyingSubs.get(i).setCreDeptName(UserUtil.getCruDeptName());
					studyingSubs.get(i).setCreChushiId(UserUtil.getCruChushiId());
					studyingSubs.get(i).setCreChushiName(UserUtil.getCruChushiName());
					studyingSubs.get(i).setCreJuId(UserUtil.getCruJuId());
					studyingSubs.get(i).setCreJuName(UserUtil.getCruJuName());
					studyingSubs.get(i).setVisible(CommonConstants.VISIBLE[1]);
					studyingSubs.get(i).setCreTime(JDateToolkit.getNowDate4());
					studyingSubs.get(i).setStatus(DAGLCommonConstants.STUDYING_SUB_STATUS[0]);//默认状态为：未确认
					studyingSubs.get(i).setIsBack(DAGLCommonConstants.IS_BACK[0]);//默认状态为;未反馈

					StudyingSub studyingSub = studyingSubDao.save(studyingSubs.get(i));
					studyingSub.setFjId(studyingSubs.get(i).getFjId());

					studyingSubList.add(studyingSub);
					studying.setStudyingSubs(studyingSubList);

				}else{
					//修改
					StudyingSub oldStudyingSub = studyingSubDao.findOne(studyingSubs.get(i).getId());
					oldStudyingSub.setYear(studying.getYear());
					oldStudyingSub.setTitle(studying.getTitle());
					oldStudyingSub.setRecChushiId(studyingSubs.get(i).getRecChushiId());
					oldStudyingSub.setRecChushiName(studyingSubs.get(i).getRecChushiName());
					oldStudyingSub.setSequence(studyingSubs.get(i).getSequence());

					StudyingSub studyingSub = studyingSubDao.save(oldStudyingSub);
					studyingSub.setFjId(studyingSubs.get(i).getFjId());

					studyingSubList.add(studyingSub);
					studying.setStudyingSubs(studyingSubList);
				}

			}
		}
		return studying;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 根据id获取一条编研信息
	 * @Date 2018/12/20 17:25
	 * @Param [id]
	 * @return com.sinosoft.sinoep.modules.dagl.bygl.entity.Studying
	 **/
	@Override
	public Studying getStudyingById(String id,String chushiId){

		//获取主表数据
		Studying studying = studyingDao.findOne(id);
		//获取子表数据
		List<StudyingSub> studyingSubs = getStudyingFenFaData(id,"",chushiId);
		//获取子表数据对应的附件id
		for(int i=0;i<studyingSubs.size();i++){
			String sql = "select id,filelistid from sys_affix where table_name = 'studying' and table_id = '"+studyingSubs.get(i).getId()+"'";
			List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
			if(result.size()>0){
				Map<String, Object> map = result.get(0);
				//吧附件id放到子表数据中
				studyingSubs.get(i).setFjId(map.get("id").toString());
				studyingSubs.get(i).setFilelistid(map.get("filelistid").toString());
			}
		}
		//把子表数据放到主表中
		studying.setStudyingSubs(studyingSubs);

		return studying ;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 根据编研id查询分发列表
	 * @Date 2018/12/21 9:25
	 * @Param [pageable, pageImpl, studying]
	 * @return com.sinosoft.sinoep.common.util.PageImpl
	 **/
	@Override
	public List<StudyingSub> getStudyingFenFaData( String studyingId, String sequence,String chushiId){

		List<Object> para = new ArrayList<>();
		String sql = "select t.* from dagl_bygl_sub t where 1=1 " +
					"	and t.visible='"+CommonConstants.VISIBLE[1]+"' " +
					"	and t.pid = ? " ;
					para.add(studyingId);
					if(StringUtils.isNotBlank(sequence)){
						sql +="and t.sequence > ?";
						para.add(sequence);
					}
					if(StringUtils.isNotBlank(chushiId)){
						sql +="and t.rec_chushi_id = ?";
						para.add(chushiId);
					}
					sql += "order by t.sequence ";

		List<StudyingSub> studyingSubs = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(StudyingSub.class),para.toArray());
		return studyingSubs;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 删除编研子表信息
	 * @Date 2018/12/20 14:12
	 * @Param [reportId]
	 * @return int
	 **/
	@Override
	public int deleteStudyingSub(String ids, String affixIds) {
		int sum = 0;
		String[] idss =ids.split(",");
		//删除附件

		for(int i = 0;i<idss.length; i++){
			StudyingSub studyingSub = studyingSubDao.findOne(idss[i]);
			//获取修改的子表的数据的id，用于修改附件的div  updateAffixFileListId
			List<StudyingSub> studyingSubs = getStudyingFenFaData(studyingSub.getPid(),studyingSub.getSequence(),"");
			if(studyingSubs.size()>0){
				String sql = "update dagl_bygl_sub t set t.sequence = t.sequence-1 where t.pid = ? and t.sequence > ? and t.visible="+CommonConstants.VISIBLE[1];

				String tableId = "";
				for(int j=0;j<studyingSubs.size();j++){
					tableId += studyingSubs.get(j).getId()+",";
				}
				tableId = CommonUtils.commomSplit(tableId);

				//修改顺序
				int updateStudyingSubSequence = jdbcTemplate.update(sql, studyingSub.getPid(),studyingSub.getSequence());
				//修改附件的回显div的id
				affixService.updateAffixFileListId(tableId);
			}
			//删除
			String jpql = "update StudyingSub t set t.visible = ? where t.id = ?";
			sum += studyingDao.update(jpql,CommonConstants.VISIBLE[0],idss[i]);
		}
		//删除附件
		affixService.delete(affixIds);
		return sum;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 根据分发id获取一个子表的分发信息
	 * @Date 2018/12/28 14:44
	 * @Param [id]
	 * @return com.sinosoft.sinoep.modules.dagl.bygl.entity.StudyingSub
	 **/
	@Override
	public StudyingSub getStudyingSub(String id){
		return studyingSubDao.findOne(id);
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 根据子表ID修改反馈状态
	 * @Date 2018/12/29 14:18
	 * @Param []
	 * @return com.sinosoft.sinoep.modules.dagl.bygl.entity.StudyingSub
	 **/
	@Override
	public int updateStudyingSubIsBack(String id, String type){
		try{
			String sql = "";
			if("send".equals(type)){
				//档案馆管理员退回的时候，修改状态为未反馈，档案馆管理员不去查询附件
				sql ="UPDATE dagl_bygl_sub t SET t.is_back="+DAGLCommonConstants.IS_BACK[0]+" WHERE t.id=?";
			}else if("back".equals(type)){
				//处室反馈回来的时候，修改状态为已反馈，档案馆管理员可查询附件
				sql ="UPDATE dagl_bygl_sub t SET t.is_back="+DAGLCommonConstants.IS_BACK[1]+" WHERE t.id=?";
			}
			return jdbcTemplate.update(sql,id);

		}catch (Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return 0;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 档案编研确认
	 * @Date 2019/1/2 10:51
	 * @Param [id, type]
	 * @return int
	 **/
	@Override
	public int StudyingSubOk(String id){
		try{
			//档案馆管理员退回的时候，修改状态为未反馈，档案馆管理员不去查询附件
			String sql ="UPDATE dagl_bygl_sub t SET t.status="+DAGLCommonConstants.STUDYING_SUB_STATUS[1]+" WHERE t.id=?";

			return jdbcTemplate.update(sql,id);

		}catch (Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return 0;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 查询部门下是否含有某个业务角色编号的人员
	 * @Date 2019/1/7 16:17
	 * @Param [deptIds, deptNames, roleNo]
	 * @return net.sf.json.JSONObject
	 **/
	@Override
	public JSONObject hasReportUserByDeptId(String deptIds, String deptNames,String roleNo) {
		JSONObject json = new JSONObject();
		boolean f = false;
		json.put("flag", "0");
		StringBuilder  deptNoUser = new StringBuilder();
		String roles = CommonUtils.commomSplit(roleNo);
		deptIds = CommonUtils.commomSplit(deptIds);
		try {
			String[] deptName = deptNames.split(",");
			String[] deptId = deptIds.split(",");
			String sql = "select sfd.deptname,sfd.deptid,duser.userid"
					+"	from"
					+"	(select d.deptid, d.deptname, t.userid,substr(d.tree_id,0,12) as treeid"
					+"	from (select t.deptid, t.deptname,t.tree_id"
					+"	from sys_flow_dept t"
					+"	where substr(t.tree_id, 0, 12) in"
					+"	(select t.tree_id from sys_flow_dept t"
					+"	where t.deptid in ("+deptIds+"))) d"
					+"	left join (select d.id, d.deptid, d.userid, f.roleid"
					+"	from sys_user_dprb d"
					+"	join sys_user_frole f"
					+"	on d.id = f.u_dept_id"
					+"	where f.roleid in (select t.roleid from sys_flow_role t where t.role_no in("+roles+")) and d.status = '"+DAGLCommonConstants.STATE[1]+"') t"
					+"	on d.deptid = t.deptid) duser"
					+"	left join sys_flow_dept sfd on duser.treeid=sfd.tree_id where duser.userid is not null";
			log.info(sql);
			JSONObject result = userInfoService.getDateBySql(sql);
			List<UserDeptVo> list = new ArrayList<>();
			log.info(JSON.toJSONString(result));
			JSONArray arr = result.getJSONArray("data");
			list = JSONArray.toList(arr, UserDeptVo.class);
			for(int i=0;i<deptId.length;i++){
				String n = deptId[i];
				String m = deptName[i];
				n = n.replace("'", "").trim();
				for (UserDeptVo deptVo : list) {
					log.info(deptVo.getDeptid());
					if(n.equals(deptVo.getDeptid())){
						f = true;
						break;
					}
				}
				if(!f){
					String pSql ="SELECT t.deptname\n" +
							"  FROM sys_flow_dept t\n" +
							" WHERE t.deptid =\n" +
							"       (SELECT t.super_id FROM sys_flow_dept t WHERE t.deptid = '"+n+"')";
					JSONObject pResult = userInfoService.getDateBySql(pSql);
					List<Map<String,Object>> lists = pResult.getJSONArray("data");
					String pName = "";
					if(lists.size()>0){
						pName = lists.get(0).get("deptname").toString();
					}
					deptNoUser.append("【"+pName+"-"+m+"】");
					log.info(deptNoUser.toString());
				}
				f = false;
			}

			if(deptNoUser.length()!=0){
				//如果有部门没有上报员则返回没有上报员的部门名称，不返回上报员信息
				json.put("flag", "0");
				json.put("data",deptNoUser.toString());
			}else{
				json.put("flag", "1");
				json.put("data", JSONArray.fromObject(list));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			json.put("msg", "解析异常");
		}
		return json;
	}

}
