package com.sinosoft.sinoep.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO page封装类，用来接收查询列表的分页参数，及返回数据
 * @author 李利广
 * @Date 2018年3月15日 下午9:00:10
 */
public class PageImpl {
	
	/** 成功标识 0：失败；1：成功 */
	private String flag;
	
	/** 提示信息  */
	private String msg;
	
	/** 页码 */
	private Integer pageNumber;
	
	/** 每页数据条数 */
	private Integer pageSize;
	
	/** 排序：升序asc，降序desc */
	private String sortOrder;
	
	/** 排序字段名 */
	private String sortName;
	
	/** 查询到的数据 */
	private Data data = new Data();
	
	public PageImpl(){}
	
	public PageImpl(Integer total,List<?> rows){
		this("1",total, rows);
	}
	
	public PageImpl(String flag,Integer total,List<?> rows){
		this.flag = flag;
		this.data.total = total;
		this.data.rows = rows;
	}
	
	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public class Data{

		private Integer total;
		
		private List<?> rows = new ArrayList<>();

		public Integer getTotal() {
			return total;
		}

		public void setTotal(Integer total) {
			this.total = total;
		}

		public List<?> getRows() {
			return rows;
		}

		public void setRows(List<?> rows) {
			this.rows = rows;
		}
		
	}

}
