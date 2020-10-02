package com.sinosoft.sinoep.modules.mypage.record.services;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.mypage.record.dao.RecordDao;
import com.sinosoft.sinoep.modules.mypage.record.entity.Record;
import com.sinosoft.sinoep.modules.mypage.record.util.ImportFileUtil;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;

import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONObject;
import workflow.common.JDateToolkit;

@Service
public class RecordServiceImp implements RecordService{
	@Autowired
	RecordDao dao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	UserInfoService userInfoService;

	@Override
	public PageImpl getlistBootHql(Pageable pageable, PageImpl pageImpl,  String reDeptName,String reUserName,String identity,String flag,String personId) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append("	from Record t ");
		querySql.append("	where   t.visible = '"+CommonConstants.VISIBLE[1]+"'");
		//拼接条件
		if (StringUtils.isNotBlank(reDeptName)) {
			querySql.append("   and (t.reDeptName in("+ CommonUtils.solveSqlInjectionOfIn(reDeptName,para)+ ") ");
			querySql.append("   or t.creChushiName in("+ CommonUtils.solveSqlInjectionOfIn(reDeptName,para)+ ") ");
			querySql.append("   or t.creJuName in("+ CommonUtils.solveSqlInjectionOfIn(reDeptName,para)+ ")) ");
		}
		
		if(StringUtils.isNotBlank(reUserName)){
			String[] reUserName1 = reUserName.split(",");
			ArrayList<String> list = new ArrayList<String>(Arrays.asList(reUserName1));
			StringBuilder useridSb = new StringBuilder();
			int index = 0;
			if(list.size()>0){
				querySql.append(" and  (");
				while(list.size()>0){
					useridSb.setLength(0);
					if(index == 0){
						querySql.append(" reUserName in (");
					}else{
						querySql.append(" or reUserName in (");
					}
					for(int i=0;i<900&&list.size()>0;i++){
						index=1;
						String tempUserid = list.remove(0);
						useridSb.append( tempUserid + ",");
					}
					querySql.append(CommonUtils.solveSqlInjectionOfIn(useridSb.substring(0, useridSb.length()-1),para) + " )");
				}
				querySql.append(" )");
			}
			//querySql.append("  and t.reUserName in("+ CommonUtils.commomSplit(reUserName) + ") ");
		}
		if(StringUtils.isNotBlank(identity)){
			querySql.append("  and t.identity like ?");
			para.add("%"+identity+"%");
		}
		//拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
				querySql.append("  order by t.creTime desc) ");
		}else{
				querySql.append("  order by t."+pageImpl.getSortName()+" "+pageImpl.getSortOrder()+") ");
		}		
        Page<Record> page = dao.query(querySql.toString(), pageable,para.toArray());
        //草稿列表，添加操作列
        //草稿列表，添加操作列
        List<Record> content = page.getContent();
        for (Record info : content) {
        		info.setCz(CommonConstants.OPTION_UPDATE+","+CommonConstants.OPTION_DELETE);
		}
        return new PageImpl((int)page.getTotalElements(),page.getContent());
	}
	
	/**
	 * 导出
	 * TODO 
	 * @author 张建明
	 * @Date 2018年5月29日 上午9:33:07
	 * @param filePath
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@Override
	public String importInfo(String filePath, MultipartFile file) throws IOException {
		 String msg = "导入失败！";
	        InputStream is = file.getInputStream();
	        Workbook workbook = null;
	    	int successNum = 0;//成功的条数
			int errorNum = 0;//失败的条数
			String errorReason = "";
			StringBuilder mark = new StringBuilder();
			StringBuilder mark2 = new StringBuilder();
			DecimalFormat    df   = new DecimalFormat("######0.00");   
	        try {
	        	Order order1 = new Order(Direction.ASC, "creTime");
	    		List<Order> list = new ArrayList<Order>();
	    		list.add(order1);
	    		Sort sort = new Sort(list);
	    		List<Record> recordList = dao.findAll(new Specification<Record>() {
	    			@Override
	    			public Predicate toPredicate(Root<Record> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	    				List<Predicate> predicates = new ArrayList<>();
	    				Predicate pVisible = cb.equal(root.get("visible").as(String.class), CommonConstants.VISIBLE[1]);
	    				predicates.add(pVisible);
	    				return cb.and(predicates.toArray(new Predicate[0]));
	    			}
	    		}, sort);
	    		
	            workbook = ImportFileUtil.getWorkBook(is,filePath);
	            int sheetNum = ImportFileUtil.getSheetNumByWorkBook(workbook);
	            int cnt=0;
	            int bankCnt=0;
	            int lastCnt=0;
	            int breakFor = 0;
	            for(int sheetIdex = 0;sheetIdex< sheetNum;sheetIdex++){
	                Sheet sheet = workbook.getSheetAt(sheetIdex);
	                if(sheet == null){
	                    continue;
	                }
	               int lastNum= sheet.getLastRowNum();
	               lastCnt=lastCnt+lastNum;
	               if(lastNum==0){
	            	   msg="表格中无数据,请添加数据！";
	            	   break;
	               }
	                for (int rowIndex =1;rowIndex<=sheet.getLastRowNum();rowIndex++){
	                    Row row = sheet.getRow(rowIndex);
	                    if(row == null){
	                        continue;
	                    }
	                    String flag="1"; //表示这一行所有单元格数据都不为空格
	                    Record entity = new Record();
	                    entity.setCreTime(JDateToolkit.getNowDate4());
	                    SysFlowUserVo uvo;
	                    String identity=ImportFileUtil.getRowCellValue(row,(short)0).toUpperCase();
	                    entity.setCreUserId(UserUtil.getCruUserId());
 	                    entity.setCreUserName(UserUtil.getCruUserName());
 	                    entity.setCreDeptId(UserUtil.getCruDeptId());
 	                    entity.setCreDeptName(UserUtil.getCruDeptName());
 	                    if(rowIndex==1){
 	                    	if(!isIDCard(identity)){
	                    		msg = "导入失败,请核对数据格式是否与模板数据格式一致";
	                    		breakFor = 1;
	                    		break;
	                    	}
 	                    }
	                    if(!StringUtils.isBlank(identity)){
	                    	//flag="1";
	                    	
	                    	
	                    	uvo= getUserInfoByCA(identity);
	                    	if(StringUtils.isBlank(uvo.getIdentity())){
	                    		mark.append("身份证号："+identity+"未找到用户,请手动编辑<br/>");
	                    		
	                    	}
	 	                    entity.setCreChushiId(uvo.getUserChushiId());
	 	                    entity.setCreChushiName(uvo.getUserChushiName());
	 	                    entity.setCreJuId(uvo.getUserJuId());
	 	                    entity.setCreJuName(uvo.getUserJuName());
	 	                    entity.setReUserId(uvo.getUserId());
	 	                    entity.setReUserName(uvo.getUserName());
	 	                   //2019.5.30日李颖洁修修改，去掉人所属的部门
	 	                    //entity.setReDeptId(uvo.getUserDeptId());
	 	                    //entity.setReDeptName(uvo.getUserDeptName());
	 	                    entity.setIdentity(identity);
		                    entity.setVisible("1");
		                    String startDate=ImportFileUtil.getRowCellValue(row,(short)1);
		                    if(!StringUtils.isBlank(startDate)){
		                    	//flag="1";
		                    }
		                    entity.setStartDate(startDate);
		                    String endDate=ImportFileUtil.getRowCellValue(row,(short)2);
		                    if(!StringUtils.isBlank(endDate)){
		                    	//flag="1";
		                    }
		                    entity.setEndDate(endDate);
		                   
		                    String recordInfo=ImportFileUtil.getRowCellValue(row,(short)3);
		                    if(!StringUtils.isBlank(recordInfo)){
		                    	//flag="1";
		                    }
		                    entity.setRecordInfo(recordInfo);
		                   
		                    if("1".equals(flag)){
		                    	//该行都有数据
		                    	//entity.setSort(cnt);
		                    	
		                    	dao.save(entity);
		                    	 successNum += 1;
		                    	 cnt++;
		                    }else{
		                    	bankCnt++;
		                    }
		                    msg = "导入成功！";
	                    }else{
		                	mark2.append((rowIndex+1)+"行身份证号为空，该条数据未导入，请手动添加！<br/>");
		                }
	                }
	                if(breakFor==1){
	                	break;
	                }
	            }
	            if(bankCnt==lastCnt){
	            	msg="表格中无数据,请添加数据！";
	            	   //msg = "导入失败！";
	             }
	          
	        } catch (Exception e) {
	            e.printStackTrace();
	            msg = "导入失败！";
	        }finally{
	        	
	        }
	        if(mark.length()>0||mark2.length()>0){
	        	return "<div style='padding:20px; line-height:30px; text-align:center'>"+msg+"<br/>其中：<br/>"+mark2.toString()+"<br/>"+mark.toString()+"</div>";
	        }else{
	        	return "<div>"+msg+"</div>";
	        }
	
	}
	 public boolean isIDCard(String idCard) {
		 boolean flag = true;
	       System.out.println("==="+idCard.length());
	       if(idCard.length()==15||idCard.length()==18){
	    	   flag = true;
	       }else{
	    	   flag = false; 
	       }
		return flag;
	    }
	/**
	 * 根据id删除一条记录
	 * TODO 
	 * @author 张建明
	 * @Date 2018年5月31日 下午1:32:23
	 * @param id
	 * @return
	 */
	@Override
	public int delete(String id) {
		String jpql = "update Record t set t.visible = ? where t.id = ?";
		return dao.update(jpql, CommonConstants.VISIBLE[0], id);
	}
	
	/**
	 * 根据id查询一条工资记录
	 * TODO 
	 * @author 张建明
	 * @Date 2018年5月31日 下午2:29:21
	 * @param id
	 * @return
	 */
	@Override
	public Record getById(String id) {
		return dao.findOne(id);
	}
	
	/**
	 * 修改履历数据
	 * TODO 
	 * @author 张建明
	 * @Date 2018年5月31日 下午2:50:39
	 * @param info
	 * @return
	 */
	@Override
	public Record saveForm(Record info) {
		 	SysFlowUserVo uvo = new SysFlowUserVo();
			String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			if(!StringUtils.isBlank(info.getIdentity())){
				info.setIdentity(info.getIdentity().toUpperCase());
			}
				Map<String,SysFlowUserVo> vo =	UserUtil.getUserVo(info.getReUserId());
				
				for (Entry<String, SysFlowUserVo> entry : vo.entrySet()) {
					uvo = entry.getValue();
			        if (uvo != null) {
			            break;
			        }
			    }
				// 履历人处室
				String creChushiId = uvo.getUserChushiId();
				String creChushiName = uvo.getUserChushiName();
				// 履历人二级局
				String creJuId = uvo.getUserJuId();
				String creJuName = uvo.getUserJuName();
				if(StringUtils.isBlank(info.getId())){
					
					info.setCreTime(creTime);
					info.setCreUserId(UserUtil.getCruUserId());
					info.setCreUserName(UserUtil.getCruUserName());
					info.setCreDeptId(UserUtil.getCruDeptId());
					info.setCreDeptName(UserUtil.getCruDeptName());
					info.setCreChushiId(creChushiId);
					info.setCreChushiName(creChushiName);
					info.setCreJuId(creJuId);
					info.setCreJuName(creJuName);
					info.setUpdateTime(creTime);
					info.setUpdateUserId(UserUtil.getCruUserId());
					info.setUpdateUserName(UserUtil.getCruUserName());
					info.setVisible("1");
					info=dao.save(info);
				}else{
					//Record record = dao.findOne(info.getId());
					info.setCreUserId(UserUtil.getCruUserId());
					info.setCreUserName(UserUtil.getCruUserName());
					info.setCreDeptId(UserUtil.getCruDeptId());
					info.setCreDeptName(UserUtil.getCruDeptName());
					info.setCreChushiId(creChushiId);
					info.setCreChushiName(creChushiName);
					info.setCreJuId(creJuId);
					info.setCreJuName(creJuName);
					info.setUpdateTime(creTime);
					info.setUpdateUserId(UserUtil.getCruUserId());
					info.setUpdateUserName(UserUtil.getCruUserName());
					String reDeptName=info.getReDeptName();
					if(StringUtils.isBlank(reDeptName)){
						info.setReDeptName("");
					}else{
						info.setReDeptName(reDeptName);
					}
					String reUserName=info.getReUserName();
					if(StringUtils.isBlank(reUserName)){
						info.setReUserName("");
					}else{
						info.setReUserName(reUserName);
					}
					
					String recordInfo=info.getRecordInfo();
					if(StringUtils.isBlank(recordInfo)){
						info.setRecordInfo("");
					}else{
						info.setRecordInfo(recordInfo);
					}
					String remark=info.getRemark();
					if(StringUtils.isBlank(remark)){
						info.setRemark("");
					}else{
						info.setRemark(remark);
					}
					info.setVisible("1");
					info=dao.save(info);	
				}
			
			
			//DecimalFormat    df   = new DecimalFormat("######0.00");   
			//Record record = new Record();
			//String startDate = "";
			//String endDate = "";
			//startDate = info.getTimeRange().substring(0, (info.getTimeRange().length() + 1) / 2 - 1).trim();
			//endDate = info.getTimeRange().substring((info.getTimeRange().length() + 1) / 2, info.getTimeRange().length()).trim();
			
			
			return info;
	}
	

	public String getUserVo(String userIds){
		StringBuilder sb=new StringBuilder();
		String[] ids=userIds.split(",");
		Map<String, SysFlowUserVo> map=UserUtil.getUserVo(userIds);
		SysFlowUserVo vo=map.get(userIds);
		for(int i=0;i<ids.length;i++){
			SysFlowUserVo vo1=map.get(ids[i]);
			if(vo1!=null){
				String identity=vo1.getIdentity();
				if(!StringUtils.isBlank(identity)){
					sb.append(identity.toUpperCase()+",");
				}
			}
			
		}
		return sb.toString();
	}
	
	//根据身份证号获取人员信息
	public SysFlowUserVo getUserInfoByCA(String identity){
		StringBuilder sb=new StringBuilder();
		SysFlowUserVo uvo = new SysFlowUserVo();
		sb.append(" select t.* from SYS_FLOW_USER_EXT t where t.identity = "+"'"+identity+"'");
		JSONObject jsonObj = userInfoService.getDateBySql(sb.toString());
		List<Map<String,Object>> data = (List<Map<String,Object>>)jsonObj.get("data");
		if(data !=null&&!"".equals(data)&&data.size()>0){
			String userid = (String) data.get(0).get("userid");
			Map<String,SysFlowUserVo> vo = UserUtil.getUserVo(userid);
			for (Entry<String, SysFlowUserVo> entry : vo.entrySet()) {
				uvo = entry.getValue();
		        if (uvo != null) {
		            break;
		        }
		    }
		}
		return uvo;
	}
	@Override
	public List<Record> getMyRecord() {
		Record record = new Record();
	Map<String,SysFlowUserVo> vo =	UserUtil.getUserVo(UserUtil.getCruUserId());
	SysFlowUserVo uvo = new SysFlowUserVo();
	for (Entry<String, SysFlowUserVo> entry : vo.entrySet()) {
		uvo = entry.getValue();
        if (uvo != null) {
            break;
        }
    }
		StringBuilder sb=new StringBuilder("SELECT t.* FROM HQGL_RECORD  t ");
		sb.append(" where t.visible='1' and t.identity =? order by update_time desc");
		List<Record> list = jdbcTemplate.query(sb.toString(),
				new BeanPropertyRowMapper<>(Record.class),uvo.getIdentity());
		
		
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findByKeyCode(String identity) {
		StringBuilder sb=new StringBuilder("SELECT t.id,t.identity identity FROM HQGL_RECORD t ");
		sb.append(" where t.visible='1' and t.identity =? ");
		/*if(!StringUtils.isBlank(id)){
			sb.append(" and id='" +id+ "'");
		}*/
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sb.toString(),identity);
		return list;
	}

	@Override
	public Record findCACodeById(String reUserId) {
		Record record = new Record();
		Map<String,SysFlowUserVo> vo =	UserUtil.getUserVo(reUserId);
		SysFlowUserVo uvo = new SysFlowUserVo();
		for (Entry<String, SysFlowUserVo> entry : vo.entrySet()) {
			uvo = entry.getValue();
	        if (uvo != null) {
	            break;
	        }
	    }
		// TODO Auto-generated method stub
		record.setIdentity(uvo.getIdentity());
		return record;
	}



}
