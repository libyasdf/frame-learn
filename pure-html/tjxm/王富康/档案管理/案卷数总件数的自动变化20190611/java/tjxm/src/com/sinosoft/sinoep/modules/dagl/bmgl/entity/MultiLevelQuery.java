package com.sinosoft.sinoep.modules.dagl.bmgl.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 多级查询用于封装数据的对象
 * @author 王磊
 * @Date 2019年2月13日 下午4:34:44
 */
public class MultiLevelQuery {

	//存储档案数据
	private List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
	
	//层级名称，用于多级查询页面区分各个层级列表
	private String levelName;
	
	//表英文名
	private String tableName;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
}
