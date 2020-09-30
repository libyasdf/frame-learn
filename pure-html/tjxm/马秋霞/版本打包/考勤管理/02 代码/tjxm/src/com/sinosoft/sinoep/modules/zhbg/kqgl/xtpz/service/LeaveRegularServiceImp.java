package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.dao.LeaveRegularDao;
import com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity.LeaveRegular;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 业务层service实现类
 * TODO 
 * @author 马秋霞
 * @Date 2018年4月20日 下午4:02:08
 */
@Service
public class LeaveRegularServiceImp implements LeaveRegularService{
	
	@Autowired
	LeaveRegularDao dao;

	/**
	 * 查询年休假规则的所有数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月23日 上午10:55:23
	 * @return
	 */
	@Override
	public Map<String,Object> findAll() {
		Map<String,Object> map=new HashMap<String,Object>();
		List<LeaveRegular> list = dao.findAll();
		map.put("flag", "1");
		map.put("data", list);
		return map;
		
	}
	
	/**
	 * 根据id查询年休假数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月24日 下午4:32:58
	 * @param string
	 * @return
	 */
	@Override
	public LeaveRegular findById(String id) {
		return dao.findOne(id);
	}

	/**
	 * 保存年休假规则列表数据
	 * TODO 
	 * @author 马秋霞
	 * @Date 2018年4月24日 下午4:41:13
	 * @param data
	 */
	@Override
	public void saveAll(String data,String dayNumbers) {
		String[] days=dayNumbers.split(",");
		JSONArray jsonAry = JSONArray.fromObject(data);
		for(int i=0;i<jsonAry.size();i++) {
			JSONObject jsonObj = JSONObject.fromObject(jsonAry.get(i));
			LeaveRegular info = dao.findOne(jsonObj.getString("id"));
			info.setDayNumber(days[i]);
			dao.save(info);
		}
		
	}

}
