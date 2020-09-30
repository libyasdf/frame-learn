package com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.fzdx.service;/**
 * Created by s on 2018/9/15.
 */

import com.sinosoft.sinoep.modules.djgl.ddjs.sqrgl.fzdx.entity.DdjsSqrglPartyconsiderationEntity;

/**
 * @Description :TODO
 * @Author: 李帅
 * @Date: 2018/9/15 14:11
 */
public interface FzdxServiceI {
    /**
     *
     *预备党员审议 添加和修改的方法
     * @Author: 李帅
     * @Date: 2018/9/15
     * */
    public DdjsSqrglPartyconsiderationEntity saveYbdysy(DdjsSqrglPartyconsiderationEntity ybdysy,String typeOfPersonnel);

    /**
     * 根据主键ID查询一条数据
     * TODO
     * @author 李帅
     * @Date 2018年9月15日
     * @param id
     * @return
     */
    public DdjsSqrglPartyconsiderationEntity getById(String id) throws Exception;

    /**
     * 申请人简要信息查询
     * TODO
     * @author 李帅
     * @Date 2018年9月16日
     * @param
     * @return
     */
    public  Object applicantStatistics(String annual,String ids);

}
