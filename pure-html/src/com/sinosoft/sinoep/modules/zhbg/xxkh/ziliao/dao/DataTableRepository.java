package com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.dao;



import java.util.List;

import com.sinosoft.sinoep.modules.zhbg.xxkh.ziliao.entity.DataTable;

public interface DataTableRepository {


	List<DataTable> getlist(DataTable table);

	List<DataTable> gettypelist(DataTable table);
	
	DataTable getById(DataTable table);

	List<DataTable> getlistDiGui(DataTable table);
}
