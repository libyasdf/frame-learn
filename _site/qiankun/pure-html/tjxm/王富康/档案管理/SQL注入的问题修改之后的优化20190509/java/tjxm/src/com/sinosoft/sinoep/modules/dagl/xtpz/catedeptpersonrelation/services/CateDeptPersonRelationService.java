package com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.entity.DaglCateDeptPersonRelation;
import com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.entity.RelationVo;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 * @Author 王富康
 * @Description //TODO 立卷单位和录入人及立卷单位管理员关系Service层
 * @Date 2019/2/1 11:47
 * @Param
 * @return
 **/
public interface CateDeptPersonRelationService {

    /**
     * @Author 王富康
     * @Description //TODO 查询立卷单位和录入人及立卷单位管理员关系列表
     * @Date 2019/2/1 12:00
     * @Param [pageImpl, daglCateDeptPersonRelation]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    PageImpl getRelationData(Pageable pageable, PageImpl pageImpl, DaglCateDeptPersonRelation daglCateDeptPersonRelation);

    /**
     * @Author 王富康
     * @Description //TODO 新增立卷单位和录入人及立卷单位管理员关系
     * @Date 2019/2/1 14:47
     * @Param [relationVo]
     * @return com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.entity.RelationVo
     **/
    RelationVo saveRelation(RelationVo relationVo);

    /**
     * @Author 王富康
     * @Description //TODO 查询关系list
     * @Date 2019/2/13 14:45
     * @Param [daglCateDeptPersonRelation]
     * @return java.util.List<com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.entity.DaglCateDeptPersonRelation>
     **/
    List<DaglCateDeptPersonRelation> getRelationList(DaglCateDeptPersonRelation daglCateDeptPersonRelation);

    /**
     * @Author 王富康
     * @Description //TODO 根据门类id和立卷单位删除所有对应关系
     * @Date 2019/2/1 15:23
     * @Param [cateId, ljdwMark]
     * @return int
     **/
    void deleteRelation(String cateId, String ljdwMark);

    /**
     * @Author 王富康
     * @Description //TODO 根据门类删除所有对应关系，删除树节点时用的
     * @Date 2019/3/16 15:43
     * @Param [cateId, ljdwMark]
     * @return void
     **/
    void deleteAllRelation(String cateId);

    /**
     * @Author 王富康
     * @Description //TODO 修改立卷单位关系对应数据
     * @Date 2019/2/14 10:19
     * @Param [relationVo]
     * @return com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.entity.RelationVo
     **/
    RelationVo updateRelation(RelationVo relationVo);

    /**
     * 去重查询所有立卷单位或者录入人
     * @author 王磊
     * @Date 2019年2月12日 上午9:53:23
     * @param ljdwOrLrr
     * @return
     */
    List<DaglCateDeptPersonRelation> getAllLjdwOrLrr(String ljdwOrLrr);
}
