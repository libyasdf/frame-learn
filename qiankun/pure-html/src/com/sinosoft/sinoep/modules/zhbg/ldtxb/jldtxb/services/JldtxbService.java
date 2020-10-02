package com.sinosoft.sinoep.modules.zhbg.ldtxb.jldtxb.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.ldtxb.jldtxb.entity.Jldtxb;
import org.springframework.data.domain.Pageable;

public interface JldtxbService {

    Jldtxb saveForm(Jldtxb jldtxb);

    PageImpl getPageListDraft(Pageable pageable,PageImpl pageImpl,String name,String unitName);

    Jldtxb getById(String id);

    int deleteOne(String id);
}
