package com.sinosoft.sinoep.uias.service;

import java.util.List;
import java.util.Map;

import com.sinosoft.sinoep.api.auth.vo.MessageSysResourceAndExt;
import com.sinosoft.sinoep.uias.model.SysRecourse;
import net.sf.json.JSONObject;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软 康俊祥
 * @since 2016年6月20日
 */
public interface RecourseService {

    /**
     * TODO 根据资源ID，获取用户在指定岗位下的拥有的子级资源
     * @author 李利广
     * @Date 2019年03月19日 13:31:35
     * @param uid
     * @param pid
     * @param deptId
     * @return java.util.List<com.sinosoft.sinoep.uias.model.SysRecourse>
     */
    List<SysRecourse> getRecoursesByPid(String uid, String pid,String deptId);

    /**
     * <B>方法名称：getResourceInfo</B><BR>
     * <B>概要说明：根据资源id获取资源详细信息</B><BR>
     * 
     * @param rid 资源id
     * @return
     */
    MessageSysResourceAndExt getResourceInfo(String rid);

    /**
     * TODO 根据资源ID，获取用户在指定岗位下的拥有的子孙资源
     * @author 李利广
     * @Date 2019年03月19日 13:36:28
     * @param userId
     * @param pid
     * @param deptId
     * @return java.util.List<com.sinosoft.sinoep.uias.model.SysRecourse>
     */
    List<SysRecourse> getResourceByRid(String userId, String pid,String deptId);

    /**
     * 根据业务角色编号给用户授权
     * @param userId 被授权人ID
     * @param deptId 被授权人所在单位ID
     * @param roleNo 授权业务角色编号
     * @param type  类型：是否需要审核（1需要审核；0不需要审核）
     * @return
     */
    JSONObject accreditByRoleNo(String userId, String deptId, String roleNo, String type);

    /**
     * 根据业务角色编号取消给用户授权
     * @param userId 被授权人ID
     * @param deptId 被授权人所在单位ID
     * @param roleNo 授权业务角色编号
     * @param type  类型：是否需要审核（1需要审核；0不需要审核）
     * @return
     */
    JSONObject cancelAccreditByRoleNo(String userId, String deptId, String roleNo, String type);

    /**
     * TODO 授权审核（通过/不通过）
     * @author 李利广
     * @Date 2019年02月19日 15:19:16
     * @param authIds   待审核数据ID（由授权/取消授权接口返回），多个由逗号分隔
     * @param status    审核通过/不通过（1通过；-1不通过）
     * @return net.sf.json.JSONObject（status：状态码；msg：状态描述）
     *          status 0是接口无数据;1是审核成功，-1审核操作异常，2审核无效,3参数为空
     */
    JSONObject addAudits(String authIds,String status);

    /**
     * 根据资源id查询资源数据
     * 多个资源id用逗号分隔
     * @author 张浩磊
     * @param ids
     * @return
     */
    List<Map<String,Object>> getResourceListByIds(String ids);

    /**
     * TODO 获取用户在指定岗位下拥有的所有资源
     * @author 李利广
     * @Date 2019年03月15日 14:40:32
     * @param userId
     * @param deptId
     * @return java.util.List<com.sinosoft.sinoep.uias.model.SysRecourse>
     */
    List<SysRecourse> getFlowRlsyResId(String userId,String deptId);

}
