package com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.entity.DutyTable;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

/**
 * @Author 王富康
 * @Description //TODO 值班表Service
 * @Date 2018/7/11 15:28
 **/
public interface DutyTableService {

    /**
     * @Author 王富康
     * @Description //TODO 值班表列表查询
     * @Date 2018/7/11 15:04
     * @param dut 值班年月
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    List<DutyTable> getList(String timeRange,DutyTable dut);

    /**
     * @Author 王富康
     * @Description //TODO 总值班室值班查询
     * @Date 2018/7/31 14:52
     * @Param [dut]
     * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.entity.DutyTable>
     **/
    PageImpl getListOfZongDutySearch(Pageable pageable, PageImpl pageImpl, DutyTable dut);

    /**
     * @Author 王富康
     * @Description //TODO 值班查询
     * @Date 2018/12/19 9:37
     * @Param [pageable, pageImpl, dut]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    PageImpl getListOfDutySearch(Pageable pageable, PageImpl pageImpl, String year_month, DutyTable dut);

    /**
     * @Author 王富康
     * @Description //TODO 新增值班信息
     * @Date 2018/7/30 11:59
     * @Param [dut, ideaName]
     * @return com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.entity.DutyTable
     **/
    DutyTable save(DutyTable dut);

    /**
     * @Author 王富康
     * @Description //TODO 保存list数据
     * @Date 2018/8/7 10:43
     * @Param [dut, privatePone, commonPhone, isSecurity, zbNoticeId]
     * @return net.sf.json.JSONObject
     **/
    List<DutyTable> saveList(List<DutyTable> dut, String privatePhone, String commonPhone, String isSecurity, String zbNoticeId);

    /**
     * @Author 王富康
     * @Description //TODO 根据id获取一条值班信息
     * @Date 2018/7/30 12:00
     * @Param [key]
     * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.entity.DutyTable>
     **/
    DutyTable getDutyTableById(String id);


    /**
     * @Author 王富康
     * @Description //TODO 修改值班表信息
     * @Date 2018/7/17 17:54
     * @Param [dut]
     * @return int
     **/
    int updateDutyTable(DutyTable dut);

    /**
     * @Author 王富康
     * @Description //TODO 修改值班表信息(普网电话，专网电话)
     * @Date 2018/7/17 17:54
     * @Param [dut]
     * @return int
     **/
    int updateDutyTablePhone(String id, String commonPhone, String privatePhone);

    /**
     * @Author 王富康
     * @Description //TODO 导入值班表信息
     * @Date 2018/7/19 20:09
     * @Param [file]
     * @return java.lang.String
     **/
    String importInfo(String filePath, MultipartFile file,String id) throws IOException;

    /**
     * @Author 王富康
     * @Description //TODO 删除值班表信息
     * @Date 2018/7/20 11:47
     * @Param [id]
     * @return int
     **/
    int delete(String reportId);

    /**
     * @Author 王富康
     * @Description //TODO 查询值班管理员
     * @Date 2018/8/9 15:28
     * @Param []
     * @return net.sf.json.JSONObject
     **/
    JSONObject queryReportAdmin() throws Exception;

    /**
     * @Author 王富康
     * @Description //TODO 查询值班查询的数据（list）
     * @Date 2018/11/26 21:10
     * @Param [dut]
     * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.entity.DutyTable>
     **/
    List<DutyTable> getListOfSearch(DutyTable dut);

    /**
     * @Author 王富康
     * @Description //TODO 查询部门是否有日常值班上报员
     * @Date 2019/6/25 18:49
     * @Param [deptIds, deptNames]
     * @return net.sf.json.JSONObject
     **/
    JSONObject hasReportUserByDeptId(String deptIds, String deptNames) throws Exception;
}
