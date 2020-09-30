package com.sinosoft.sinoep.waitNoflow.services;

import com.sinosoft.sinoep.waitNoflow.entity.SysWaitNoflow;

public interface SysWaitNoflowService {
    /**
     * 插入不走流程待办并发消息
     * @param entity 不走流程待办实体
     * @param subject  消息标题
     * @param content 消息内容
     * @param messageURL 消息url
     * @return
     */
    SysWaitNoflow saveWaitNoflow(SysWaitNoflow entity,String subject,String content,String messageURL);

    /**
     * 删除不走流程点
     * 如果存在id则为单独删除指定id的数据
     * 如果sourceId不为空 如果不存在receiveDeptId则根据sourceId删除
     * 如果receiveDeptId存在并且receiveUserId 则删除部门下业务id为sourceId的用户待办
     * 如果receiveDeptId存在并且roleId则删除部门下业务id为sourceId的业务角色待办
     * 如果receiveUserId和roleId都不存在则删除部门下业务id为sourceId的所有待办
     * @param id 待 办id
     * @param sourceId 业务id
     * @param receiveUserId 接收人id
     * @param receiveDeptId 接收人部门
     * @param rolesNo 业务角色编号
     * @return
     */
    int deleteWaitNoflow(String id,String sourceId,String receiveDeptId,String receiveUserId,String rolesNo);
    
    /**
     * 系统发送 不走流程待办,同时发送提醒消息(由开关控制)
     * @param entity 非流程待办实体类
     * @param sendMsg (开关必须是打开状态，若开关关闭，该参数无效)true:发送消息；false:不发送消息
     * @param subject 提醒消息的标题
     * @param content 提醒消息的内容
     * @param messageURL 提醒消息打开的链接
     * @return
     */
    SysWaitNoflow saveWaitNoflowSystem(SysWaitNoflow entity,Boolean sendMsg,String subject,String content,String messageURL);
    
    /**
     * 系统发送非流程待办并发消息
     * @param entity 不走流程待办实体
     * @param subject  消息标题
     * @param content 消息内容
     * @param messageURL 消息url
     * @return
     */
    SysWaitNoflow saveWaitNoflowSystem(SysWaitNoflow entity,String subject,String content,String messageURL);
    
    /**
     * TODO 发送非流程待办
     * @author 李利广
     * @Date 2018年9月13日 上午9:14:06
     * @param entity
     * @param sendMsg (开关必须是打开状态，若开关关闭，该参数无效)true:发送消息；false:不发送消息
     * @param subject
     * @param content
     * @param messageURL
     * @return
     */
    SysWaitNoflow saveWaitNoflow(SysWaitNoflow entity,Boolean sendMsg,String subject,String content,String messageURL);

    /**
     * 根据不走流程待办id获取实体
     * @param id
     * @return
     */
    SysWaitNoflow getSysWaitNoflow(String id);

    /**
     * TODO 发送消息
     * @author 李利广
     * @Date 2018年9月13日 上午9:38:46
     * @param userId
     * @param subject
     * @param content
     * @param messageURL
     * @return
     */
    boolean sendMsgsByUid(String userId,String subject,String content,String messageURL,String creTime,SysWaitNoflow entity);
}
