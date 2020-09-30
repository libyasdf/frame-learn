package com.sinosoft.sinoep.modules.mypage.oftenModel.services;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.sinosoft.sinoep.modules.system.config.dictionary.entity.DataDictionary;
import com.sinosoft.sinoep.modules.system.config.dictionary.services.DataDictionaryService;
import com.sinosoft.sinoep.uias.util.RecourseUtils;
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

	@Autowired
	DataDictionaryService dataDictionaryService;

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
		jdbcTemplate.execute("delete from often_model t where t.cre_user_id='"  + UserUtil.getCruUserId() + "' and cre_dept_id = '" + UserUtil.getCruDeptId() + "' ");
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
			temp.setPpid(jsonObj.getString("ppid"));
			temp.setCreTime(cruDate);
			temp.setCreUserId(UserUtil.getCruUserId());
			temp.setCreDeptId(UserUtil.getCruDeptId());
			temp.setCreUserName(UserUtil.getCruUserName());
			temp.setCreDeptName(UserUtil.getCruDeptName());
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
	public List<OftenModule> findByUserId() throws Exception {
		//flag 0 不存在 1 正常 2 没权限
		StringBuffer sb = new StringBuffer();
		sb.append(" from OftenModule where creUserId ='" + UserUtil.getCruUserId() + "' and creDeptId = '" + UserUtil.getCruDeptId() + "'");
		List<OftenModule> list = dao.getEntityManager().createQuery(sb.toString()).getResultList();
		if(list.size() > 0){
			String id = "";
			for(OftenModule oftenModule : list){
				id += oftenModule.getResourceId() + ",";
			}
			id = id.substring(0, id.length()-1);
			String param = "{\"id\":\""+ id +"\"}";
			List<Map<String, Object>> resourcesList = RecourseUtils.getResourceListByIds(param);

			String userId = UserUtil.getCruUserId();
			String deptId = UserUtil.getCruDeptId();
			List<SysRecourse> data = recourseDubboService.getFlowRlsyResId(userId,deptId);
			String resourseId = "";
			resourseId = child(data,resourseId);

			List<DataDictionary> dicts = dataDictionaryService.getListByMark("cymk","1");
			Map<String, String> dictsMap = new HashMap<>();
			for (DataDictionary dic : dicts) {
				dictsMap.put(dic.getName(),dic.getCode());
			}

			for(OftenModule oftenModule : list){
				for(Map<String, Object> map : resourcesList){
					if(oftenModule.getResourceId().equals(map.get("id").toString())){
						oftenModule.setFlag(map.get("flag").toString());
						oftenModule.setParent(map.get("parent") == null ? oftenModule.getResourceName() : map.get("parent").toString() +"/"+ oftenModule.getResourceName());
						oftenModule.setUrl("moduleIndex.html?id="+oftenModule.getPpid()+"&sid="+oftenModule.getResourceId());
						String name = oftenModule.getResourceName();
						oftenModule.setName(name);
						if(name.length() > 7){
							oftenModule.setName(name.substring(0,5) + "...");
						}
						if(map.get("type") != null){
							oftenModule.setClasses(dictsMap.get(map.get("type").toString()));
						}
						if(map.get("flag").toString().equals("1") && !resourseId.contains(oftenModule.getResourceId())){
							oftenModule.setFlag("2");
						}
					}
				}
			}

			Collections.sort(list, new Comparator<OftenModule>() {
				@Override
				public int compare(OftenModule o1, OftenModule o2) {
					return o1.getSort().compareTo(o2.getSort());
				}
			});

		}
		return list;

	}

	@Override
	public void delete(String id) {
		dao.delete(id);
	}

	private String child(List<SysRecourse> data,String ids) {
		for(SysRecourse  sysRecourse : data){
			ids += sysRecourse.getId()+",";
			if(sysRecourse.getChildrenList().size() > 0){
				ids = child(sysRecourse.getChildrenList(),ids);
			}
		}
		return ids;
	}


	@Override
	public void sort(String[] ids) {
		List<OftenModule> list = new ArrayList<OftenModule>();
		for (int i = 0; i < ids.length; i++) {
			OftenModule oftenModule = dao.findOne(ids[i]);
			oftenModule.setSort(i);
			list.add(oftenModule);
		}
		dao.save(list);
	}


}
