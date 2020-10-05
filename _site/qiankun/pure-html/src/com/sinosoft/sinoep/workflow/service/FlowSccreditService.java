package com.sinosoft.sinoep.workflow.service;

import java.util.List;
import java.util.Vector;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.workflow.model.FlowSccredit;
import com.sinosoft.util.exception.DAOException;

/**
 * 
 * <B>系统名称：</B><BR>
 * <B>模块名称：待办授权</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 pangxj
 * @since 2018年1月4日
 */

public interface FlowSccreditService {

    /**
     * 
     * <B>方法名称：</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月9日 上午10:10:08
     * @param userid：用户id
     * @param flowSccredit：保存代办授权
     * @param fileTypeItem：文件类型数组
     * @return
     * @throws DAOException String
     */
    public String save(String userid, @ModelAttribute FlowSccredit flowSccredit,
            @RequestParam(value = "fileTypeItem") String[] fileTypeItem) throws DAOException;

    /**
     * 
     * <B>方法名称：添加多条授权信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:37:02
     * @param fileTypes：文件类型数组
     * @param flowSccredit：代办授权实体
     * @return String
     * @throws DAOException
     */
    public String addAuth(String[] fileTypes, FlowSccredit flowSccredit) throws DAOException;

    /**
     * 
     * <B>方法名称：根据创建人获取授权信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:37:06
     * @param pageNum:当前页
     * @param pageSize：每页条数
     * @param flowSccredit：代办授权实体
     * @param userid：用户id
     * @return Page
     * @throws DAOException
     */
    public PageImpl queryFlowSccredit(Pageable pageable, PageImpl pageImpl, FlowSccredit flowSccredit, String userid)
            throws DAOException;

    /**
     * 
     * <B>方法名称：删除授权信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:37:09
     * @param ids:多个代办授权id
     * @return String
     * @throws DAOException
     */
    public String delAuth(String ids);

    /**
     * 
     * <B>方法名称：获取授权信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:37:12
     * @param id：代办授权id
     * @return FlowSccredit
     * @throws DAOException
     */
    public FlowSccredit viewAuth(String id);

    /**
     * 
     * <B>方法名称：修改待办授权信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:37:15
     * @param deptId：授权部门id
     * @param sccreditdeptId：被授权部门id
     * @param fileTypeItem：文件类型
     * @param flowSccredit：代办授权实体类
     * @return String
     * @throws DAOException
     */
    public String updateAuth(String deptId, String sccreditdeptId, String fileTypeItem, FlowSccredit flowSccredit);

    /**
     * 
     * <B>方法名称：根据条件查询待办授权数据</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:37:20
     * @param flowSccredit：代办授权实体类
     * @return List<FlowSccredit>
     */
    public List<FlowSccredit> findAll(FlowSccredit flowSccredit);

    /**
     * 
     * <B>方法名称：根据条件查询待办授权数据</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:37:24
     * @param flowSccredit：代办授权实体类
     * @return Vector<FlowSccredit>
     */
    public Vector<FlowSccredit> findAllForVec(FlowSccredit flowSccredit);

    /**
     * 
     * <B>方法名称：获取所有流程分类 待办授权使用</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午3:41:32
     * @param userid：用户id
     * @return String
     */
    public String getLicenseFlowType(String userid);

    /**
     * 
     * <B>方法名称：获取所有流程分类</B><BR>
     * <B>概要说明：根据sysId， orgId筛选当前系统的流程分类</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月10日 下午2:17:11
     * @return String
     */
    public String getFlowTypeList(String orgId);
}
