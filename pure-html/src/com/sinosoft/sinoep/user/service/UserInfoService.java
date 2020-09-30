/**
 * Copyright 2017 SinoSoft. All Rights Reserved.
 */
package com.sinosoft.sinoep.user.service;

import java.util.List;
import java.util.Map;

import com.sinosoft.sinoep.api.dept.vo.Dept;
import com.sinosoft.sinoep.api.dept.vo.MessageDept;
import com.sinosoft.sinoep.api.user.model.UserInfo;
import com.sinosoft.sinoep.api.user.vo.MessageUser;
import com.sinosoft.sinoep.common.exception.SysException;
import com.sinosoft.sinoep.user.entity.DeptAllInfo;

import com.sinosoft.sinoep.user.entity.UserParam;
import net.sf.json.JSONObject;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 张战国
 * @since 2017年2月24日
 */
public interface UserInfoService {
    /**
     * 
     * <B>方法名称：getUserBase</B><BR>
     * <B>概要说明：根据用户id获取用户基础信息</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年1月10日 上午10:14:56
     * @param uid:用户id
     * @param type: Dubbo服务：1 rest服务 :2
     * @return UserInfo:用户基础信息
     */
    public UserInfo getUserBase(String uid, String type);

    /**
     * 
     * <B>方法名称：getPrisonCompanyIdByDeptId</B><BR>
     * <B>概要说明：根据部门id获取最近实体信息</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年1月10日 上午10:16:31
     * @param deptid:部门id
     * @return Dept:部门基础信息
     */
    public Dept getPrisonCompanyIdByDeptId(String deptid);

    /**
     * 
     * <B>方法名称：getDeptById</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年1月10日 上午10:17:22
     * @param deptID:获取部门信息
     * @return Dept:部门基础信息
     */
    public Dept getDeptById(String deptID);

    /**
     * <B>方法名称：</B>根据部门id获取该部门下的所有的组织部门<BR>
     * <B>概要说明：</B><BR>
     * 
     * @param type：类型 综合处： 1 ,非综合处：2
     * @param deptid：部门id
     * @return
     */
    public List<Dept> getDeptlistByDeptid(String type, String deptid);

    /**
     * 
     * <B>方法名称：getUserFullName</B><BR>
     * <B>概要说明：得到部门名称</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年1月10日 上午10:22:53
     * @param deptId:当前部门ID
     * @return String 部门名称(根据业务需要,是否得到部门全称,默认是全称)
     */
    public String getUserFullName(String deptId);

    /**
     * 
     * <B>方法名称：getDeptOrUserTree</B><BR>
     * <B>概要说明：获取部门或人员树</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年1月8日 下午2:54:15
     * @param type：类型 2是部门 ,3是人
     * @param orgId：组织机构id
     * @param nodeType:节点类型
     * @param status:状态
     * @return String
     */
    public String getDeptOrUserTree(String type, String nodeType, String orgId, String deptId,
            String superId, String status) throws SysException;

    /**
     * 
     * <B>方法名称：得到部门名称</B><BR>
     * <B>概要说明：getDeptFullName</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年1月10日 上午10:30:17
     * @param deptId:当前部门ID
     * @return String 部门名称(根据业务需要,是否得到部门全称,默认是全称)
     */
    public String getDeptFullName(String deptId);

    /**
     * 
     * <B>方法名称：getOrgId</B><BR>
     * <B>概要说明：获得组织体系id</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年1月10日 上午10:31:17
     * @param deptId:当前部门ID
     * @return String
     */
    public String getOrgId(String deptId);

    /**
     * 
     * <B>方法名称：getDeptId</B><BR>
     * <B>概要说明：根据用户ID得到部门ID</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年1月10日 上午10:31:45
     * @param userId:用户id
     * @return String
     */
    public String getDeptId(String userId);

    /**
     * 
     * <B>方法名称：getAllDeptByUserId</B><BR>
     * <B>概要说明：根据用户ID得用户到所有的部门</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年1月9日 上午9:46:31
     * @param userId:用户id
     * @return MessageDept
     */
    public MessageDept getAllDeptByUserId(String userId);

    /**
     * 
     * <B>方法名称：getDeptName</B><BR>
     * <B>概要说明：得到部门全称</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年1月9日 上午9:55:14
     * @param deptId:部门id
     * @return String
     */
    public String getDeptName(String deptId);

    /**
     * 
     * <B>方法名称：getUserInfoById</B><BR>
     * <B>概要说明：根据用户id获得用户</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年1月9日 下午7:14:51
     * @param userId:用户id
     * @return MessageUser
     */
    public MessageUser getUserInfoById(String userId);

    /**
     * 
     * <B>方法名称：getUserInfoByName</B><BR>
     * <B>概要说明：根据用户名获得用户信息</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年1月9日 下午7:26:27
     * @param userName：用户名称
     * @return MessageUser
     */
    public MessageUser getUserInfoByName(String userName);

    /**
     * 
     * <B>方法名称：getDeptByNmAndOrgId</B><BR>
     * <B>概要说明：通过部门名称和部门组织体系id获取部门列表</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年1月9日 下午7:31:54
     * @param deptname：部门名称
     * @param orgid：部门组织机构体系id
     * @return List<Dept>
     */
    public List<Dept> getDeptByNmAndOrgId(String deptname, String orgid);

    /**
     * 
     * <B>方法名称：getDepartmentStaffByName</B><BR>
     * <B>概要说明：根据名字查询部门和用户</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年1月10日 上午10:11:32
     * @param type: 获取用户：user 获取部门 ：dept 获取全部 ：""
     * @param qName:需要查询的部门名称或用户名
     * @param userid:用户id
     * @return String
     */
    public String getDepartmentStaffByName(String type, String qName, String userid,String deptId);

    /**
     * 
     * <B>方法名称：getDepartmentStaffTree</B><BR>
     * <B>概要说明：获取全部部门人员数据</B><BR>
     *
     * @author：yuhao
     * @cretetime:2018年1月10日 上午9:44:46
     * @param type: 获取用户：user 获取部门 ：dept 获取全部 ：""
     * @return String
     */
    public String getDepartmentStaffTree(String type);

    /**
     * 根据deptid获取其所有父部门的信息
     * @param deptId
     * @return
     */
    public List<Dept> getAllSuperDeptById(String deptId);

    /**
     * 获取部门下人员信息
     * @param deptId
     * @return
     */
    public List<UserInfo> getUserByDeptId(String deptId);

    /**
     * 获取部门及以下部门人员信息
     * @param deptid
     * @return
     */
    public List<UserInfo> getAllUserByDeptId(String deptid);
    /**
     * 根据部门ids和业务角色编号获取对应人员信息
     * @param deptId 如需差多个部门的需要将部门id拼接起来，用逗号分隔即可
     * @param rolesNo
     * @return
     */
    public List<UserInfo> getUserByDeptIdAndRolesNo(String deptId,String rolesNo);
    /**
     * 根据部门id和业务角色编号获取对应人员信息
     * @param deptId 只传一个部门能id,能查询到该部门以下子部门的业务角色人员信息
     * @param rolesNo
     * @return
     */
    public List<UserInfo> getUserByDeptIdAndRolesNo1(String deptId,String rolesNo);

    /**
     * 根据部门id获取部门信息和局、二级局、处室信息
     * @param deptId
     * @return
     */
    public DeptAllInfo getDeptInfoByDeptId(String deptId);
    
    /**
     * TODO 根据自定义sql查询用户信息（uias数据库）
     * @author 李利广
     * @Date 2018年7月3日 下午5:02:08
     * @param sql
     * @return
     */
    public JSONObject getDateBySql(String sql);

    /**
     * 根据部门id获取所有下级部门信息
     * @param superId
     * @return
     */
    public JSONObject getAllDeptBySuperId(String superId);

    /**
     * 根据部门id获取部门下所有职务权限（职级或职务级别）的部门人员树
     * @param deptId
     * @param zw
     * @param zj
     * @param zwjb
     * @return
     */
    public JSONObject getUserInfoByDeptIdAndZwZj(String deptId,String zw,String zj,String zwjb);

    /**
     * 获取排好序的部门通过userIds
     * @param userIds
     * @return
     */
    public JSONObject getDeptByUserIds(String userIds);

    /**
     * 获取用户信息通过真实姓名（精确查询）
     * @param userNameFull 用户真实姓名
     * @return
     */
    public JSONObject getUsersByUserNameFull(String userNameFull);

    /**
     * 查询用户部门树（精确查询）
     * @param userNameFull 用户真实姓名
     * @return
     */
    public JSONObject getUserTreeOnlySelectByUserNameFull(String userNameFull);

    /**
     * 通过用户ids获取所在部门及以上部门信息
     * @param userIds
     * @return
     */
    public JSONObject getAllDeptByUserids(String userIds);

    /**
     * 获取群组树
     * @param subId 系统id 如:97206,85585
     * @return
     */
    public JSONObject getGroup(String subId);
    /**
     * 获取群组下用户
     * @param groupId 群组id
     * @return
     */
    public JSONObject getGroupUser(String groupId);

    /**
     * 通过类型和编号获取对应名称（职务权限：1，职级：2，职务：5）
     * @param remarks
     * @param value
     * @return
     */
    public String getNameByCode(String remarks,String value);

    /**
     * TODO 更新用户信息
     * @author 李利广
     * @Date 2019年03月05日 16:30:54
     * @param userInfo
     * @return net.sf.json.JSONObject
     */
    JSONObject updateUserInfo(Map<UserParam,String> userInfo);

    /**
     * TODO 迁移用户
     * @author 李利广
     * @Date 2019年03月05日 17:18:26
     * @param userId 用户ID
     * @param deptId 用户原deptId
     * @param moveDeptId 要迁移到的deptId
     * @return net.sf.json.JSONObject
     */
    JSONObject removeUser(String userId,String deptId,String moveDeptId);

    /**
     * TODO 设置一人多岗
     * @author 李利广
     * @Date 2019年03月05日 17:30:54
     * @param userId 用户ID
     * @param moveDeptId 要设置岗位的deptId
     * @return net.sf.json.JSONObject
     */
    JSONObject setUserMultipleDept(String userId,String moveDeptId);

}