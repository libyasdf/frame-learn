package com.sinosoft.sinoep.modules.zhbg.hygl.arrangeSeat.test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TreeSetTest {
	public static void main(String[] args){
			Set<Map<String,Object>> businessDept = new TreeSet<Map<String, Object>>(new Comparator<Map<String,Object>>() {
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					if(o1.get("deptid")!=null && o2.get("deptid")!=null){
						if(o1.get("deptid").toString().compareTo(o2.get("deptid").toString())!=0){
							return o1.get("order_no").toString().compareTo(o2.get("order_no").toString());
						}else{
							return 0;
						}
					}else{
						return 1;
					}
					
					
				}
		   });
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("deptid", "1");
			map.put("order_no", "01");
			
			Map<String,Object> map1 = new HashMap<String,Object>();
			map1.put("deptid", "11");
			map1.put("order_no", "010");
			
			Map<String,Object> map2 = new HashMap<String,Object>();
			map2.put("deptid", "12");
			map2.put("order_no", "011");
			
			businessDept.add(map);
			businessDept.add(map1);
			businessDept.add(map2);
		
		  for (Map<String, Object> tempMap : businessDept) {
			  System.out.println(tempMap.get("deptid") +"......"+tempMap.get("order_no"));
		  }
	
	}
}
