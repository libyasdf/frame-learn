package com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.dagl.xtpz.filenumberrule.entity.DaglCategoryTable;

public interface DaglCategoryTableDao extends BaseRepository<DaglCategoryTable, String> {
	@Query(" from DaglCategoryTable t order by t.creTime asc")
	List<DaglCategoryTable> DoorList();

}
