package com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.mypage.wmgl.basicSet.category.entity.Category;

/**
 * 门类dao层
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月7日 下午4:12:27
 */
public interface CategoryDao extends BaseRepository<Category, String>{

	@Transactional
    @Modifying
    @Query("update Category t set t.visible = '0' where t.id = ?1")
    public int delVisible(String id);

}
