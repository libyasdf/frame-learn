package com.sinosoft.sinoep.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.sinoep.common.base.BaseDao;

/**
 * kjx 2016-4-23 用于辅助拼接生成HQL的工具类
 */
public class HqlHelper {

	private String fromClause; // From子句，必须
	private String whereClause = ""; // Where子句，可选
	private String orderByClause = ""; // OrderBy子句，可选

	private List<Object> parameters = new ArrayList<Object>(); // 参数列表

	/**
	 * 生成From子句，默认的别名为'o'
	 * 
	 * @param clazz
	 */
	@SuppressWarnings("rawtypes")
	public HqlHelper(Class clazz) {
		this.fromClause = "FROM " + clazz.getSimpleName() + " o";
	}

	/**
	 * 生成From子句，使用指定的别。'
	 * 
	 * @param clazz
	 * @param alias
	 *            别名
	 */
	@SuppressWarnings("rawtypes")
	public HqlHelper(Class clazz, String alias) {
		this.fromClause = "FROM " + clazz.getSimpleName() + " " + alias;
	}

	/**
	 * 拼接Where子句
	 * 
	 * @param condition
	 * @param params
	 */
	public HqlHelper addCondition(String condition, Object... params) {
		// 拼接
		if (whereClause.length() == 0) {
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " AND " + condition;
		}

		// 保存参数
		if (params != null && params.length > 0) {
			for (Object obj : params) {
				parameters.add(obj);
			}
		}

		return this;
	}

	/**
	 * 将不为空的值进行Where拼接
	 * 
	 * @return
	 * @throws Exception
	 */
	public void setWhere(Object conditionModel) throws Exception {
		Field[] f = conditionModel.getClass().getDeclaredFields();
		for (Field field : f) {
			field.setAccessible(true);
			Object value = field.get(conditionModel);
			if ("serialVersionUID".equals(field.getName()))
				continue;
			if (value == null)
				continue;
			if (value instanceof String && StringUtils.isBlank((String) value))
				continue;
			if ("Long".equals(value.getClass().getSimpleName()) || "Integer".equals(value.getClass().getSimpleName())
					|| "Double".equals(value.getClass().getSimpleName())
					|| "Date".equals(value.getClass().getSimpleName())) {
				addCondition(true, " o." + field.getName() + "= ? ", value);
			} else if ("String".equals(value.getClass().getSimpleName())) {
				addCondition(true, " o." + field.getName() + "  like ? ", "%" + value + "%");
			}
		}
	}

	/**
	 * 如果第1个参数为true，则拼接Where子句
	 * 
	 * @param append
	 * @param condition
	 * @param params
	 */
	public HqlHelper addCondition(boolean append, String condition, Object... params) {
		if (append) {
			addCondition(condition, params);
		}
		return this;
	}

	/**
	 * 拼接OrderBy子句
	 * 
	 * @param propertyName
	 *            属性名
	 * @param isAsc
	 *            true表示升序，false表示降序
	 */
	public HqlHelper addOrder(String propertyName, boolean isAsc) {
		if (orderByClause.length() == 0) {
			orderByClause = " ORDER BY " + propertyName + (isAsc ? " ASC" : " DESC");
		} else {
			orderByClause += ", " + propertyName + (isAsc ? " ASC" : " DESC");
		}
		return this;
	}

	/**
	 * 如果第1个参数为true，则拼接OrderBy子句
	 * 
	 * @param append
	 * @param propertyName
	 *            属性名
	 * @param isAsc
	 *            true表示升序，false表示降序
	 */
	public HqlHelper addOrder(boolean append, String propertyName, boolean isAsc) {
		if (append) {
			addOrder(propertyName, isAsc);
		}
		return this;
	}

	/**
	 * 获取生成的查询数据列表的HQL语句
	 * 
	 * @return
	 */
	public String getQueryListHql() {
		return fromClause + whereClause + orderByClause;
	}

	/**
	 * 获取生成的查询总记录数的HQL语句（没有OrderBy子句）
	 * 
	 * @return
	 */
	public String getQueryCountHql() {
		return "SELECT COUNT(*) " + fromClause + whereClause;
	}

	/**
	 * 获取参数列表，与HQL过滤条件中的'?'一一对应
	 * 
	 * @return
	 */
	public List<Object> getParameters() {
		return parameters;
	}

	/**
	 * 查询分页信息
	 * 
	 * @param pageNum
	 *            当前页数
	 * @param service
	 *            业务Service
	 * @return Page
	 */
	public Page buildPageBean(Integer pageNum, BaseDao<?> service) {
		if (pageNum == null) {
			pageNum = 0;
		}
		return service.getPageBean(pageNum, 10, this);
	}

	/**
	 * 查询分页信息
	 * 
	 * @param pageNum
	 *            当前页数
	 * @param pageSize
	 *            每页显示条数
	 * @param service
	 *            业务Service
	 * @return
	 */
	public Page buildPageBean(Integer pageNum, int pageSize, BaseDao<?> service) {
		if (pageNum == null) {
			pageNum = 0;
		}
		return service.getPageBean(pageNum, pageSize, this);
	}

}
