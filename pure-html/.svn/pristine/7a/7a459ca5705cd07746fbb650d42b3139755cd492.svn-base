package com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.entity.DaglShoppingTrolley;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;

/**
 * @Author 王富康
 * @Description //TODO 王富康购物车Sercice层
 * @Date 2019/2/18 14:57
 * @Param
 * @return
 **/
public interface ShoppingTrolleyService {

    /**
     * @Author 王富康
     * @Description //TODO 查询购物车内容
     * @Date 2019/2/18 15:16
     * @Param [pageable, pageImpl, daglShoppingTrolley]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    PageImpl getShoppingTrolleyData(Pageable pageable, PageImpl pageImpl, DaglShoppingTrolley daglShoppingTrolley);

    /**
     * @Author 王富康
     * @Description //TODO 查询购物车数据（不分页）（管理员）
     * @Date 2019/2/19 9:53
     * @Param [daglCateDeptPersonRelation]
     * @return java.util.List<com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.entity.DaglShoppingTrolley>
     **/
    List<DaglShoppingTrolley> getShoppingTrolleyListOfAdmin(DaglShoppingTrolley daglShoppingTrolley);

    /**
     * @Author 王富康
     * @Description //TODO 新增购物车数据
     * @Date 2019/2/18 15:27
     * @Param [daglShoppingTrolley]
     * @return com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.entity.DaglShoppingTrolley
     **/
    List<Map<String,Object>> saveShoppingTrolley(String ids, String tName, String all, String ranking, String categoryCode, String categoryName);

    /**
     * @Author 王富康
     * @Description //TODO 删除收集列表数据（管理员）
     * @Date 2019/2/18 15:28
     * @Param []
     * @return int
     **/
    int deleteShoppingTrolley(String ShoppingTrolleyIds);

    /**
     * @Author 王富康
     * @Description //TODO 推送收集列表
     * @Date 2019/2/25 17:19
     * @Param [borrowUserId, borrowUserName]
     * @return int
     **/
    List<Map<String,Object>> sendShoppingTrolley( String borrowUserId, String borrowUserName);

    /**
     * @Author 王富康
     * @Description //TODO 查询收集列表数据（不分页）（借阅人）
     * @Date 2019/2/26 9:29
     * @Param [recid, categoryno]
     * @return java.util.List<com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.entity.DaglShoppingTrolley>
     **/
    List<DaglShoppingTrolley> getShoppingTrolleyListOfUser(DaglShoppingTrolley daglShoppingTrolley);

    /**
     * @Author 王富康
     * @Description //TODO 删除收集列表数据（借阅人）
     * @Date 2019/2/26 17:17
     * @Param [ShoppingTrolleyIds]
     * @return int
     **/
    int deleteShoppingTrolleyOfUser(String ShoppingTrolleyIds);

    /**
     * @Auther:邴秀慧
     * @Description:借阅人删除收集数据，根据档案的唯一标识和表名（用于借阅发送时，删除收集列表的数据）
     * @Date:2019/3/5 21:01
     */
    public int deleteShoppingTrolleyOfUserByRecidAndTableName(String recid, String tableName);

    /**
     * @Author 王富康
     * @Description //TODO 删除档案时，同时删除收集列表的数据
     * @Date 2019/4/11 9:31
     * @Param [recid, tableName]
     * @return int
     **/
    int deleteShoppingTrolleyByRecidAndTableName(String recid, String tableName);
}
