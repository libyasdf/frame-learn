package com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.services;

import org.springframework.data.domain.Pageable;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.xxkh.questionandgroup.entity.XxkhQuestionQgroup;
/**
 * 
 * @author 颜振兴
 * 时间：2018年8月21日
 *	XxkhQuestionQgroupService
 */
public interface XxkhQuestionQgroupService {
	//添加一个关联
	XxkhQuestionQgroup save(XxkhQuestionQgroup xxkhQuestionQgroup);
	
	//删除全部关联
	int delete(XxkhQuestionQgroup xxkhQuestionQgroup);
	
	//删除一个关联
	int deleteti(XxkhQuestionQgroup xxkhQuestionQgroup);
	
	//是否被选中
	XxkhQuestionQgroup isCheck(String tizhu, String ti);

	//获取被选中的题
	PageImpl list(PageImpl pageImpl, XxkhQuestionQgroup xxkhQuestionQgroup, Pageable pageable);

	//自动组卷加关联
	XxkhQuestionQgroup autoSave(XxkhQuestionQgroup qgroup);

	
}
