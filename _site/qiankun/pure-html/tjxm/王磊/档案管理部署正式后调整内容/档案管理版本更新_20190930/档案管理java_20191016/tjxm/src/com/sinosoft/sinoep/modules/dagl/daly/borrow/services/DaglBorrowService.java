package com.sinosoft.sinoep.modules.dagl.daly.borrow.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dagl.daly.borrow.entity.DaglBorrow;
import com.sinosoft.sinoep.modules.dagl.daly.borrow.entity.DaglFile;
import com.sinosoft.sinoep.modules.taskplan.entity.TaskPlan;
import com.sinosoft.sinoep.waitNoflow.entity.SysWaitNoflow;

import net.sf.json.JSONObject;

import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Auther:邴秀慧
 * @Description: 档案借阅服务接口
 * @Date:2018/11/10 9:48
 */
public interface DaglBorrowService {

    /**
     * @Auther:邴秀慧
     * @Description:查询草稿类表
     * @Date:2018/11/10 9:43
     */
    public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, DaglBorrow daglBorrow)  throws Exception;
    /**
     * @Auther:邴秀慧
     * @Description:保存主子表
     * @Date:2018/11/10 9:43
     */
    public DaglBorrow saveForm(DaglBorrow daglBorrow) throws Exception;

    /**//**
     * @Auther:邴秀慧
     * @Description:删除主表数据
     * @Date:2018/11/10 10:05
     */
    public int deleteDaglBorrow(String id)  throws Exception;

    /**
     * @Auther:邴秀慧
     * @Description:删除字表数据
     * @Date:2018/11/10 10:56
     */
    public int deleteDaglFile(String id) throws Exception;

    /**
     * @Auther:邴秀慧
     * @Description:获取主子表的数据
     * @Date:2018/11/10 10:57
     */
    public DaglBorrow getById(String id) throws Exception;

    /**
     * @Auther:邴秀慧
     * @Description:更新subflag的状态值
     * @Date:2018/11/13 20:09
     */
    public DaglBorrow updateSubFlag(String id, String subflag) throws Exception;

    /**
     * @Auther:邴秀慧
     * @Description:根据oldid查询该记录是否被重复续借或者重借
     * @Date:2019/3/1 11:49
     */
    public int getBorrowById(String ids) throws Exception;

    /**
     * @Auther:邴秀慧
     * @Description:发送消息
     * @Date:2018/11/17 14:57
     *//*
    public boolean sendMsgsByUid(String userId, String subject, String content, String messageURL, String creTime, SysWaitNoflow entity);

    *//**
     * @Auther:邴秀慧
     * @Description:保存利用效果
     * @Date:2018/11/17 14:57
     *//*
    public DaglFile saveUseResult(DaglFile daglFile) throws Exception;
    */
    /**
     * 根据用户id获取用户的门禁卡卡号
     * @author 王磊
     * @Date 2019年2月16日 下午2:09:47
     * @param userId
     * @return
     */
    public JSONObject getUserCardNumByUserId(String userId);

    /**
     * @Author 王富康
     * @Description //TODO 借阅记录主表数据（不分页）
     * @Date 2019/2/27 10:20
     * @Param [pageImpl, daglBorrow]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    List<DaglBorrow> getBorrowList(DaglBorrow daglBorrow);

    /**
     * @Author 王富康
     * @Description //TODO 借阅记录子表数据（不分页）
     * @Date 2019/2/27 10:20
     * @Param [pageImpl, daglBorrow]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    List<DaglFile> getFileList(DaglBorrow daglBorrow) ;

    /**
     * @Author 王富康
     * @Description //TODO 传统借阅/线上借阅列表查询
     * @Date 2019/3/1 15:24
     * @Param [pageImpl, daglBorrow, BlStutas-办理状态, type-traditional/onLine]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    PageImpl traditionalOrOnLineList(Pageable pageable, PageImpl pageImpl, DaglBorrow daglBorrow, String BlStutas, String type);

    /**
     * @Author 王富康
     * @Description //TODO 档案归还（主表查询）（不分页）
     * @Date 2019/3/2 15:39
     * @Param [daglBorrow]
     * @return java.util.List<com.sinosoft.sinoep.modules.dagl.daly.borrow.entity.DaglBorrow>
     **/
    List<DaglBorrow> getShouldReturnBorrowList(DaglBorrow daglBorrow);

    /**
     * @Author 王富康
     * @Description //TODO 档案管理-借阅记录
     * @Date 2019/3/1 15:30
     * @Param [pageable, pageImpl, daglBorrow, BlStutas, type]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    PageImpl recordList(Pageable pageable, PageImpl pageImpl, DaglFile daglFile);

    /**
     * @Author 王富康
     * @Description //TODO 档案一键归还
     * @Date 2019/3/4 9:57
     * @Param [daglBorrow]
     * @return int
     **/
    JSONObject returnThisBorrow(String id);

    /**
     * @Author 王富康
     * @Description //TODO 档案归还（单个）
     * @Date 2019/3/4 10:20
     * @Param [daglBorrow]
     * @return int
     **/
    JSONObject returnOneBorrow(String id, String isAuto);

    /**
     * @Author 王富康
     * @Description //TODO 催还
     * @Date 2019/3/4 17:02
     * @Param [id-借阅主表的id, pressContent-催还内容]
     * @return net.sf.json.JSONObject
     **/
    JSONObject returnPress(String id, String pressContent);

    /**
     * @Author 王富康
     * @Description //TODO 办理借出
     * @Date 2019/3/5 17:00
     * @Param [id-借阅主表的id, borrowDate-借阅日期, shouldReturnDate-归还日期]
     * @return net.sf.json.JSONObject
     **/
    JSONObject toConductBorrow(String id,String borrowDate,String shouldReturnDate);

    /**
     * @Auther:邴秀慧
     * @Description: 按次查询已借出的记录
     * @Date:2019/3/8 15:04
     */
    PageImpl getRecordPerList(Pageable pageable, PageImpl pageImpl, DaglBorrow daglBorrow) throws Exception;

    /**
     * @Author 王富康
     * @Description //TODO 归档库删除档案信息时，根据recid和表名删除所有档案信息
     * @Date 2019/4/11 9:41
     * @Param [recid, tableName]
     * @return int
     **/
    int deleteDaglFileByTNameAndRecid(String recid, String tableName);
}
