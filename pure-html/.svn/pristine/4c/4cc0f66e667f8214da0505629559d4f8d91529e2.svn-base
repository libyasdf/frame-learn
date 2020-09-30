package com.sinosoft.sinoep.modules.system.config.msgVersion.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.config.msgVersion.entity.MsgVersion;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * TODO 消息客户端版本管理业务接口
 * @author 李利广
 * @Date 2019年03月08日 20:08:58
 */
public interface MsgVersionService {

    /**
     * TODO 根据版本号获取客户端更新信息，如果版本号为空，则获取最新客户端
     * @author 李利广
     * @Date 2019年03月08日 20:39:29
     * @param version 版本号（1.4.0）
     * @return com.sinosoft.sinoep.modules.system.config.msgVersion.entity.MsgVersion
     */
    MsgVersion getMsgByVersion(String version) throws Exception;

    /**
     * TODO 保存版本信息
     * @author 李利广
     * @Date 2019年03月08日 22:40:56
     * @param msgVersion
     * @return net.sf.json.JSONObject
     */
    MsgVersion save(MsgVersion msgVersion);

    /**
     * TODO 删除版本信息
     * @author 李利广
     * @Date 2019年03月08日 22:40:56
     * @param id
     * @return net.sf.json.JSONObject
     */
    Boolean deleteVersion(String id);

    /**
     * TODO 查询版本信息列表
     * @author 李利广
     * @Date 2019年03月09日 10:00:14
     * @param pageable
     * @param pageImpl
     * @param msgVersion
     * @param startDate
     * @param endDate
     * @return net.sf.json.JSONObject
     */
    PageImpl getVersionList(Pageable pageable,PageImpl pageImpl, MsgVersion msgVersion, String startDate, String endDate) throws Exception;

    /**
     * TODO 根据ID查询一条数据
     * @author 李利广
     * @Date 2019年03月09日 10:00:14
     * @param id
     * @return net.sf.json.JSONObject
     */
    MsgVersion getById(String id) throws Exception ;

}
