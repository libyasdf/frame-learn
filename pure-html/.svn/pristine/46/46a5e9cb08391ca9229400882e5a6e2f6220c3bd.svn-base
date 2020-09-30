package com.sinosoft.sinoep.modules.mypage.oftenModel.services;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.common.util.JSONUtils;
import com.sinosoft.sinoep.modules.mypage.oftenModel.dao.OftenModelDao;
import com.sinosoft.sinoep.modules.mypage.oftenModel.entity.OftenModule;
import com.sinosoft.sinoep.uias.model.SysRecourse;
import com.sinosoft.sinoep.uias.service.RecourseService;
import com.sinosoft.sinoep.user.entity.SysFlowUserVo;
import com.sinosoft.sinoep.user.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class OftenModelServiceImp implements OftenModelService{
	@Autowired
	OftenModelDao dao;
	@Autowired
	JdbcTemplate jdbcTemplate;
	 @Resource(name = "recourseDubboService")
	 RecourseService recourseDubboService;
	
	/**
	 * 保存
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月18日 上午9:45:35
	 * @param model
	 * @return
	 */
	@Override
	public void save(String model) {
		ArrayList<OftenModule> list = new ArrayList<OftenModule>();
		ArrayList<String> resourceIds = new ArrayList<String>();
		jdbcTemplate.execute("delete from often_model t where t.cre_user_id='"  + UserUtil.getCruUserId() + "'");
		JSONArray jsonArray=JSONArray.fromObject(model);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String cruDate=sdf.format(new Date());
		int cnt=0;
		for (Object object : jsonArray) {
			OftenModule temp =new OftenModule();
			JSONObject jsonObj=JSONObject.fromObject(object);
			resourceIds.add(jsonObj.getString("resourceId"));
			temp.setResourceId(jsonObj.getString("resourceId"));
			temp.setResourceName(jsonObj.getString("resourceName"));
			temp.setResourceUrl(jsonObj.getString("resourceUrl"));
			temp.setResourceIco(jsonObj.getString("resourceIco"));
			temp.setCreTime(cruDate);
			temp.setCreUserId(UserUtil.getCruUserId());
			temp.setCreDeptId(UserUtil.getCruDeptId());
			temp.setCreUserName(UserUtil.getCruUserName());
			temp.setCreDeptName(UserUtil.getCruDeptName());
			temp.setFlag("1");
			temp.setSort(cnt);
			list.add(temp);
			cnt++;
		}
		
		dao.save(list);
	}

	/**
	 * 根据用户id查询该用户创建的常用模块
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月23日 上午8:52:52
	 * @param userId
	 * @return
	 */
	@Override
	public List<SysRecourse> findByUserId(String userId) {
		StringBuilder sb=new StringBuilder(" select t.sort sort,t.resource_id resourceId,t.resource_name resourceName,t.resource_url resourceUrl,t.resource_ico resourceIco,t.flag flag from often_model t ");
		sb.append(" where t.cre_user_id='" + UserUtil.getCruUserId() + "'");
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sb.toString());
		//根据用户id获取常用模块下的子模块
		 List<SysRecourse> data = recourseDubboService.getResourceByRid(UserUtil.getCruUserId(),  CommonConstants.OFTENMODEL_ID,UserUtil.getCruDeptId());
		 //List<SysRecourse> data=getResourceByRid(UserUtil.getCruUserId(), CommonConstants.OFTENMODEL_ID);
		 //存放公有模块的list
		 ArrayList<SysRecourse> commonList=new ArrayList<SysRecourse>();
		 for (SysRecourse resource : data) {
			 for (Map<String, Object> map : list) {
				 if(resource.getChildrenList()==null || resource.getChildrenList().size()==0){
					 if(map.get("RESOURCEID").equals(resource.getId())){
						 resource.setSort(Integer.parseInt(String.valueOf(map.get("SORT"))));						 
						 commonList.add(resource);
					 }
				 }else{
					 child(commonList,resource,map.get("RESOURCEID"),map.get("SORT"));
				 }
			 }
		 }
		//排序list里面的数据
		 Collections.sort(commonList, new Comparator< SysRecourse>(){

			@Override
			public int compare(SysRecourse o1, SysRecourse o2) {
				// TODO Auto-generated method stub
				return o1.getSort().compareTo(o2.getSort());
			}
			 
		 });
		return commonList;
	}
	
	/**
	 * 资源下有子数据，需要遍历子数据的资源id,和数据中的资源id是否有一样的，有则放到list集合中
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年5月25日 下午4:08:02
	 * @param commonList
	 * @param resource
	 * @param id
	 */
	private void child(ArrayList<SysRecourse> commonList, SysRecourse resource, Object object,Object sort) {
		List<SysRecourse> list=resource.getChildrenList();
		for (SysRecourse sysRecourse : list) {
			if(sysRecourse.getChildrenList()==null || sysRecourse.getChildrenList().size()==0){
				if(object.equals(sysRecourse.getId())){
					sysRecourse.setSort(Integer.parseInt(String.valueOf(sort)));
					 commonList.add(sysRecourse);
				 }
			}else{
				child(commonList,sysRecourse,object,sort);
			}
		}
		
	}
	
	/**
	 * 根据用户id和资源id获取他的子资源（获取常用模块下的子模块）
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年6月1日 上午10:12:44
	 * @return
	 */
	public List<SysRecourse> getResourceByRid(String userId,String pid){//97604
		String RecourceStr = HttpRequestUtil.sendGet(ConfigConsts.UIAS_SERVICE_ROOT_URL + "/resource/getReByPid","uid=" + userId + "&subId=" + ConfigConsts.SYSTEM_ID + "&pid="+pid);
		String resourceInfo=JSONObject.fromObject(RecourceStr).getString("resourceInfo");
		List<SysRecourse> recourses=new ArrayList<SysRecourse>();
		List<Map>list=new ArrayList();
		if (StringUtils.isNotBlank(resourceInfo)) {
			list = JSONUtils.getList(resourceInfo, Map.class);
		}
		
		for (Map map : list) {
			SysRecourse recourse=new SysRecourse();
			recourse.setId((String)map.get("resourceId"));
			recourse.setName((String)map.get("resourceName"));
			try {
				recourse.setUrl((String)map.get("resourceContent"));
			} catch (Exception e) {
				recourse.setUrl("");
			}
			
			try {
				recourse.setStyle((String)map.get("style"));
			} catch (Exception e) {
				recourse.setStyle("");
			}
			try {
				recourse.setDiscription((String)map.get("discription"));
			} catch (Exception e) {
				recourse.setDiscription("");
			}
			try {
				recourse.setMemo((String)map.get("memo"));
			} catch (Exception e) {
				recourse.setMemo("");
			}
			recourses.add(recourse);
		}
		return recourses;
	}
	
	
	

	

}
