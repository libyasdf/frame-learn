package com.sinosoft.sinoep.common.util;

import java.util.List;

/**
 * 分页的Model类
 * 
 * @author yinrh
 * @date 2015-12-25
 */
public class Page {

	private int showCount = 10; // 每页显示记录数
	private int totalPage; // 总页数
	private int totalResult; // 总记录数
	private int currentPage; // 当前页
	private int currentResult; // 当前记录起始索引
	private boolean entityOrField; // true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性
	private PageData pd = new PageData();
	@SuppressWarnings("rawtypes")
	private List recordList;// 记录数据
	@SuppressWarnings("unused")
	private Integer indexNum;// 序号

	public Page() {
		this.showCount = 2;
	}

	/**
	 * Page 构造器
	 * 
	 * @param showCount
	 *            记录数
	 * @param totalResult
	 *            记录总数
	 * @param currentPage
	 *            当前页
	 * @param recordList
	 *            当前页数据
	 */
	public Page(int currentPage, int showCount, Integer totalResult, List<?> recordList) {
		this.showCount = showCount;
		this.totalResult = totalResult;
		this.currentPage = currentPage;
		this.recordList = recordList;
	}

	public int getTotalPage() {
		if (totalResult % showCount == 0)
			totalPage = totalResult / showCount;
		else
			totalPage = totalResult / showCount + 1;
		return totalPage;
	}

	/**
	 * 
	 * <B>方法名称：计算当前页面序号</B><BR>
	 * <B>概要说明：</B><BR>
	 * 
	 * @return
	 */
	public Integer getIndexNum() {
		return showCount * (currentPage - 1) + 1;
	}

	public void setIndexNum(Integer indexNum) {
		this.indexNum = indexNum;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}

	public int getCurrentPage() {
		if (currentPage <= 0)
			currentPage = 1;
		if (currentPage > getTotalPage())
			currentPage = getTotalPage();
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getShowCount() {
		return showCount;
	}

	public void setShowCount(int showCount) {

		this.showCount = showCount;
	}

	public int getCurrentResult() {
		currentResult = (getCurrentPage() - 1) * getShowCount();
		if (currentResult < 0) {
			currentResult = 0;
		}
		return currentResult;
	}

	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}

	public boolean isEntityOrField() {
		return entityOrField;
	}

	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}

	public PageData getPd() {
		return pd;
	}

	public void setPd(PageData pd) {
		this.pd = pd;
	}

	@SuppressWarnings("rawtypes")
	public List getRecordList() {
		return recordList;
	}

	@SuppressWarnings("rawtypes")
	public void setRecordList(List recordList) {
		this.recordList = recordList;
	}

}
