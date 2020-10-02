package com.sinosoft.sinoep.flowsccredit.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.flowsccredit.entity.FlowSccredit;
import com.sinosoft.util.exception.DAOException;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
     * @param flowSccredit：保存代办授权
     * @param fileTypeItem：文件类型数组
     * @return
     * @throws DAOException String
     */
    String save(FlowSccredit flowSccredit, String[] fileTypeItem) throws DAOException;

    /**
     *
     * <B>方法名称：根据创建人获取授权信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:37:06
     * @param pageImpl：每页条数
     * @param flowSccredit：代办授权实体
     * @return Page
     * @throws DAOException
     */
    PageImpl queryFlowSccredit(PageImpl pageImpl, FlowSccredit flowSccredit)
            throws DAOException;

    /**
     *
     * <B>方法名称：根据当前人角色获取授权信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:37:06
     * @param pageImpl：每页条数
     * @param flowSccredit：代办授权实体
     * @param role 1:总收发（查看本单位）;2:部门综合（查看本部门）
     * @return Page
     * @throws DAOException
     */
    PageImpl queryFlowSccredit(PageImpl pageImpl, FlowSccredit flowSccredit,String role)
            throws DAOException;

    /**
     *
     * <B>方法名称：删除授权信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:37:09
     * @param id:多个代办授权id
     * @return Boolean
     * @throws DAOException
     */
    JSONObject delAuth(String id) throws DAOException;

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
    FlowSccredit viewAuth(String id);

    /**
     *
     * <B>方法名称：修改待办授权信息</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:37:15
     * @param fileTypeItem：文件类型
     * @param flowSccredit：代办授权实体类
     * @return String
     * @throws DAOException
     */
    String updateAuth(String fileTypeItem, FlowSccredit flowSccredit);

    /**
     *
     * <B>方法名称：获取所有流程分类</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月9日 上午10:23:26
     * @return Collection
     */
    Collection<JSONObject> getAllFlowType();

}
