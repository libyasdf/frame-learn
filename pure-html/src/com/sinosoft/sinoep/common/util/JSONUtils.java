package com.sinosoft.sinoep.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * TODO JSON工具类
 * @author 李利广
 * @Date 2018年5月22日 下午7:56:45
 */
public class JSONUtils {
	
	private static Log log = LogFactory.getLog(JSONUtils.class);
	
	/**
	 * TODO 将接收到的JSON字符串转换为JSONObject对象
	 * @author 李利广
	 * @Date 2018年5月22日 下午7:55:22
	 * @param json
	 * @return
	 */
	public static JSONObject getJsonObject(String json){
		JSONObject jsonObj = new JSONObject();
		if (!StringUtils.isBlank(json)) {
			jsonObj = JSONObject.fromObject(json);
		}
		return jsonObj;
	}
	
	/**
	 * TODO 将Java对象转换为JSON字符串
	 * @author 李利广
	 * @Date 2018年5月22日 下午7:55:38
	 * @param obj
	 * @return
	 */
	public static String getJsonToString(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		try {
			if (obj != null) {
				result = mapper.writeValueAsString(obj);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * TODO 将同步过来的多条数据，放入到List集合中
	 * @author 李利广
	 * @Date 2018年5月22日 下午7:56:00
	 * @param json
	 * @param clz
	 * @return
	 */
	public static <T> List<T> getList(String json, Class<T> clz){
		List<T> list =  new ArrayList<T>();
		try {
			JSONArray array = JSONArray.fromObject(json);
			for (int i = 0;i < array.size(); i++) {
				list.add(getJsonAsObject(array.getString(i),clz));
			}
		} catch (JSONException e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	
	/**
	 * TODO 将json字符串转换为JAVA对象
	 * @author 李利广
	 * @Date 2018年5月22日 下午7:56:15
	 * @param sjson
	 * @param clz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getJsonAsObject(String sjson, Class<T> clz) {
		T object = null;
		try {
			if (!StringUtils.isBlank(sjson)) {
				JSONObject jsonObject = getJsonObject(sjson);
				object = (T)JSONObject.toBean(jsonObject, clz);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return object;
	}
	
	/**
	 * TODO 根据实体中的Column注解，将JSONArray转换成map key为实体主键
	 * 		注：适用于JSONArray中的属性名与实体属性名不一致，但与数据库字段一致的情况
	 * @author 李利广
	 * @Date 2018年6月15日 上午10:51:10
	 * @param list
	 * @param clz
	 * @return Map<String, T>
	 */
	public static <T> Map<String, T> array2Obj(JSONArray list,Class<T> clz){
		Map<String, T> map = new HashMap<>();
		try {
			for (int i = 0; i < list.size(); i++) {
				JSONObject json = list.getJSONObject(i);
				T obj = clz.newInstance();
				Field[] fields = clz.getDeclaredFields();
				String id = "";
				for (Field field : fields) {
					Column col = field.getAnnotation(Column.class);
					Id idCol = field.getAnnotation(Id.class);
					if(col != null){
						String name = col.name();
						if (json.containsKey(name)){
							field.setAccessible(true);
							field.set(obj, json.getString(name));
							if(idCol != null){
								id = json.getString(name);
							}
						}
					}
				}
				map.put(id, obj);
			}
		} catch (InstantiationException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		}
		return map;
	}
	
}
