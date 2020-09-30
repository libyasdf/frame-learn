package com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.dao;

import com.sinosoft.sinoep.common.jpa.repository.BaseRepository;
import com.sinosoft.sinoep.modules.djgl.ddjs.shyk.ndjh.entity.NdjhWordtablEntity;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @Author: 李帅
 * @Date: 2018/8/30 18:52
 */
public interface NdjhWordTableDao  extends BaseRepository<NdjhWordtablEntity,String> {
    public List<NdjhWordtablEntity> findByNdjhIdAndVisible(String ndjhId,String visible, Sort sort);

}
