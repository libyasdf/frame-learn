package com.sinosoft.sinoep.workflow.service;

import com.sinosoft.sinoep.common.util.Page;

/**
 * 
 * <B>系统名称：会签操作</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * 
 * @author 中科软科技 pangxj
 * @since 2018年1月4日
 */
public interface CountersignService {

    /**
     * 
     * <B>方法名称：添加删除会签分支</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:07:13
     * @param workitemid：工作项ID
     * @param type:调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return String
     */
    public String addAndDelHq(String workitemid, String type);

    /**
     * 
     * <B>方法名称：检查增删会签按钮显示方法</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:07:16
     * @param workitemid：工作项ID
     * @param type:调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return boolean
     */
    public boolean addAndDelHq_list(String workitemid, String type);

    /**
     * 
     * <B>方法名称：删除会签分支/和增加会签分支</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:07:19
     * @param workitemid：工作项ID
     * @param delHq：要删除的会签分支
     * @param addHq：要增加的会签分支
     * @param type:调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return String
     */
    public String addAndDelHq_do(String workitemid, String delHq, String addHq, String type);

    /**
     * 
     * <B>方法名称：根据工作项ID workitemid 查询强制增删会签分支</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:07:22
     * @param workitemid：工作项ID
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return String
     */
    public String forceAddHq(String workitemid, String type);

    /**
     * 
     * <B>方法名称：检查增删会签按钮显示方法</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:07:25
     * @param workitemid：工作项ID
     * @param type：调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return boolean
     */
    public boolean isDisForceAddHq(String workitemid, String type);

    /**
     * 
     * <B>方法名称：强制删除会签分支/和增加会签分支</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月4日 下午7:07:27
     * @param workitemid：工作项ID
     * @param addHq：要增加的会签分支
     * @param delHq：要删除的会签分支
     * @param type:调用类型 1-本地服务调用 2-http服务调用 3-本地调用
     * @return String
     */
    public String forceaddHq_do(String workitemid, String addHq, String delHq, String type);

    /**
     * 
     * <B>方法名称：列表筛选</B><BR>
     * <B>概要说明：</B><BR>
     *
     * @author：pangxj
     * @cretetime:2018年1月5日 下午4:28:18
     * @param page：分页列表数据
     * @return Page
     */
    public Page list(Page page);
}
