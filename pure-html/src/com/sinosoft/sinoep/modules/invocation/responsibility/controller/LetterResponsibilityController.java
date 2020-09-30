package com.sinosoft.sinoep.modules.invocation.responsibility.controller;

import com.sinosoft.sinoep.modules.invocation.responsibility.service.LetterResponsibilityServiceI;
import com.sinosoft.sinoep.modules.djgl.internalsecurityoffice.responsibilitybook.toolitem.entity.Help;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/letterResponsibilityController")
public class LetterResponsibilityController {
	private static final Logger logger = Logger.getLogger(LetterResponsibilityController.class);

	@Autowired
	private LetterResponsibilityServiceI letResSer;

	@RequestMapping(value="subDepartments")
	@ResponseBody
	public String subDepartments(String depid,String time) throws Exception{
		//获取此部门下所有人和子部门
		//http://localhost:8181/system/component/tree/deptAndUserTree?deptId=441
		//letResSer.departmentsCrew("");
		Map resultMap=new HashMap();
		resultMap.put("headcount",46);//总人数
		resultMap.put("signed",30);//已签人数
		resultMap.put("notsign",16);//未签人数
		List<Map> subList=new ArrayList<>();
		for(int i=0;i<12;i++){
			Map subMap=new HashMap();
			String name=i+"部";
			String headcount="10";
			String signed="6";
			String notsign="4";
			subMap.put("name",name);
			subMap.put("headcount",headcount);
			subMap.put("signed",signed);
			subMap.put("notsign",notsign);
			subList.add(subMap);
		}

		resultMap.put("subDepartments",subList);//签订详情


		return Help.returnClass(200,"执行成功",resultMap);
	}

	/**
	 * 根据部门id生成人员的详细信息
	 * @auto 子火
	 * @Date 2018年12月7日11:02:07	 *
	 */
	@RequestMapping(value="departmentsCrew")
	@ResponseBody
	public String departmentsCrew(String depid,String time) throws Exception{
		letResSer.departmentsCrew("");
		Map resultMap=new HashMap();
		resultMap.put("headcount",37);//总人数
		resultMap.put("signed",21);//已签人数
		resultMap.put("notsign",15);//未签人数
		List<Map> crewList=new ArrayList<>();
		for(int i=0;i<90;i++){
			Map crewMap=new HashMap();//0:未签;1:已签
			String name="录歌";
			crewMap.put("name",name);
			String type="0";
			if (i%3!=0){
				type="1";
			}
			crewMap.put("type",type);
			crewList.add(crewMap);
		}

		resultMap.put("departmentsCrew",crewList);//签订详情


		return Help.returnClass(200,"执行成功",resultMap);
	}

	
	 
}
