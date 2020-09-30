package com.sinosoft.sinoep.waitNoflow.services;

import com.sinosoft.sinoep.api.dept.vo.Dept;
import com.sinosoft.sinoep.api.user.model.UserInfo;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.HttpRequestUtil;
import com.sinosoft.sinoep.common.util.SpringBeanUtils;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.message.service.NotityService;
import com.sinosoft.sinoep.modules.system.config.toggle.controller.SysToggleAction;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.waitNoflow.dao.SysWaitNoflowDao;
import com.sinosoft.sinoep.waitNoflow.entity.SysWaitNoflow;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workflow.common.JDateToolkit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO 非流程待办服务
 * @author 李利广
 * @Date 2018年7月24日 下午5:50:28
 */
@Service
public class SysWatiNoflowServiceImpl implements SysWaitNoflowService {

    private static Log log = LogFactory.getLog(SysWatiNoflowServiceImpl.class);
    @Autowired
    private SysWaitNoflowDao dao;
    
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SysToggleAction toggleAction;

    /**
     * 插入不走流程待办,同时发送提醒消息(由开关控制)
     * @param entity 非流程待办实体类
     * @param subject 提醒消息的标题
     * @param content 提醒消息的内容
     * @param messageURL 提醒消息打开的链接
     * @return
     */
	@Override
    public SysWaitNoflow saveWaitNoflow(SysWaitNoflow entity,String subject,String content,String messageURL) {
    	return saveWaitNoflow(entity,true,subject, content, messageURL);
    }

    /**
     * 获取不走流程待办类型
     * @param entity
     * @return
     */
    private String getType(SysWaitNoflow entity){
        String receiveUserId = entity.getReceiveUserId();
        String receiveDeptId = entity.getReceiveDeptId();
        String rolesNo = entity.getRolesNo();
        String type = "";
        if(StringUtils.isNotBlank(receiveDeptId)){//部门不为空
            type = "0";
            if(StringUtils.isNotBlank(receiveUserId)){//接收人不为空
                type = "1";
            }
            if(StringUtils.isNotBlank(rolesNo)){//业务角色编号不为空
                type = "2";
            }
            if(StringUtils.isNotBlank(receiveUserId) && StringUtils.isNotBlank(rolesNo)){
                type="";
                log.info("用户和系统角色编号都存在了！！");
            }
        }
        log.info("待办类型="+type);
        return type;
    }

    /**
     * 删除不走流程待办
     * @param id 待办id
     * @param sourceId 业务id
     * @param receiveUserId 接收人
     * @param receiveDeptId 接收部门
     * @param rolesNo 业务角色编号
     * @return
     */
    @Override
    public int deleteWaitNoflow(String id,String sourceId,String receiveDeptId,String receiveUserId,String rolesNo) {
        int num = 0;
        if(StringUtils.isNotBlank(id)){//如果待办id不为空则单删除
            String sql = "update SysWaitNoflow set visible=?,updateTime=? where id =?";
            num = dao.update(sql,CommonConstants.VISIBLE[0],JDateToolkit.getNowDate4(),id);
            if(num !=0){
                log.info("删除单条成功！");
            }
        }else{
            if(StringUtils.isNotBlank(sourceId)){//如果业务数据id不为空则多删除
                String updateAllSql="update SysWaitNoflow s set visible=?,updateTime=? where s.sourceId=? ";
                if(StringUtils.isNotBlank(receiveDeptId)){//部门不为空
                    updateAllSql +=" and s.receiveDeptId =? ";
                    if(StringUtils.isNotBlank(receiveUserId) || StringUtils.isNotBlank(rolesNo)) {
                        if (StringUtils.isNotBlank(receiveUserId)) {//部门下人员
                            updateAllSql += " and s.receiveUserId =? ";
                            num = dao.update(updateAllSql, CommonConstants.VISIBLE[0],JDateToolkit.getNowDate4(), sourceId, receiveDeptId, receiveUserId);
                        }else if (StringUtils.isNotBlank(rolesNo)) {//部门下业务角色编号
                            updateAllSql += " and s.rolesNo = ? ";
                            num = dao.update(updateAllSql, CommonConstants.VISIBLE[0],JDateToolkit.getNowDate4(), sourceId, receiveDeptId, rolesNo);
                        }
                    }else{//只有部门
                        num = dao.update(updateAllSql, CommonConstants.VISIBLE[0],JDateToolkit.getNowDate4(), sourceId, receiveDeptId);
                    }
                }else{//根据业务id全部删除
                    num = dao.update(updateAllSql,CommonConstants.VISIBLE[0],JDateToolkit.getNowDate4(),sourceId);
                }
                if(num !=0) {
                    log.info("删除全部成功！");
                }
            }
        }
        return num;
    }

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
    private static boolean sendMsgsByUid(String userId,String subject,String content,String messageURL){
        boolean flag = false;
        if(StringUtils.isNotBlank(userId)){
           if(ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)){
                NotityMessage message = new NotityMessage();
                message.setSenderId(ConfigConsts.SYSTEM_ID);//系统id
                message.setSubject(subject);//标题
                message.setContent(content);//内容
                message.setSendTime(new Date());//发送时间
                message.setAcceptTime(new Date());//接收时间
                message.setAccepterId(userId);//接收人id
                message.setStatus("0");//状态
                message.setType("3");//类型  1手机    2邮箱   3栈内
                message.setMsgUrl(messageURL);//消息链接
                NotityService notityService =  (NotityService) SpringBeanUtils.getBean("notityService");
                notityService.add(message);
                flag = true;
           }else{
                String param = "sendType=3&sendContent="+content+"&uids="+userId+"&subId=" +ConfigConsts.SYSTEM_ID+
                        "&msgUrl="+messageURL+"&title="+subject+"&appSecret=af2fff3bda2043a991018689848793b4";
                HttpRequestUtil.sendGet(ConfigConsts.MESSAGE_SERVICE_ROOT_URL+"/sendMsgsByUid",param);
                flag = true;
           }
        }
        return flag;
    }
    
    /**
     * 系统发送 不走流程待办,同时发送提醒消息(由开关控制)
     * @param entity 非流程待办实体类
     * @param subject 提醒消息的标题
     * @param content 提醒消息的内容
     * @param messageURL 提醒消息打开的链接
     * @return
     */
    @Override
    public SysWaitNoflow saveWaitNoflowSystem(SysWaitNoflow entity,String subject,String content,String messageURL){
    	return saveWaitNoflowSystem( entity, true, subject, content, messageURL);
    }
    
    /**
     * 系统发送 不走流程待办,同时发送提醒消息(由开关控制)
     * @param entity 非流程待办实体类
     * @param sendMsg (开关必须是打开状态，若开关关闭，该参数无效)true:发送消息；false:不发送消息
     * @param subject 提醒消息的标题
     * @param content 提醒消息的内容
     * @param messageURL 提醒消息打开的链接
     * @return
     */
    @SuppressWarnings({ "static-access", "deprecation", "unchecked" })
    @Override
    public SysWaitNoflow saveWaitNoflowSystem(SysWaitNoflow entity,Boolean sendMsg,String subject,String content,String messageURL) {
        String type = getType(entity);
        String userids = "";
        List<UserInfo> userList = new ArrayList<UserInfo>();
        if(StringUtils.isBlank(type)){
            return entity;
        }
        if("1".equals(type)){
            userids = entity.getReceiveUserId();
        }else{
            if("0".equals(type)){//只有部门
                userList = userInfoService.getUserByDeptId(entity.getReceiveDeptId());
            }
            if("2".equals(type)){//部门下业务角色
                String ReceiveDeptIds = "";
                JSONObject deptJSON = userInfoService.getAllDeptBySuperId(entity.getReceiveDeptId());
                JSONArray jsonArray = deptJSON.getJSONArray("data");
                List<Dept> list = jsonArray.toList(jsonArray,Dept.class);
                for(Dept item :list){
                    ReceiveDeptIds +=item.getDeptid()+",";
                }
                userList = userInfoService.getUserByDeptIdAndRolesNo(ReceiveDeptIds,entity.getRolesNo());
            }
            for (UserInfo user:userList){
                userids += String.valueOf(user.getUid())+",";
            }
        }
        if(StringUtils.isNotBlank(entity.getId())){
            SysWaitNoflow entityDB = dao.findOne(entity.getId());
            entityDB.setReceiveUserId(entity.getReceiveUserId());
            entityDB.setReceiveUserName(entity.getReceiveUserName());
            entityDB.setReceiveDeptId(entity.getReceiveDeptId());
            entityDB.setReceiveDeptName(entity.getReceiveDeptName());
            entityDB.setRolesNo(entity.getRolesNo());
            entityDB.setTableName(entity.getTableName());
            entityDB.setTableId(entity.getTableId());
            entityDB.setSourceId(entity.getSourceId());
            entityDB.setOpName(entity.getOpName());
            entityDB.setDaibanUrl(entity.getDaibanUrl());
            entityDB.setDaibanNum(entity.getDaibanNum());
            entityDB.setType(type);
            entityDB.setTitle(entity.getTitle());
            entityDB.setAttr1(entity.getAttr1());
            entityDB.setAttr2(entity.getAttr2());
            entityDB.setAttr3(entity.getAttr3());
            entity = dao.save(entityDB);
            log.info("修改成功！");
        }else{
            entity.setDraftUserId("");
            //entity.setDraftUserName("");
            entity.setVisible(CommonConstants.VISIBLE[1]);
            entity.setDraftTime(JDateToolkit.getNowDate4());
            entity.setType(type);
            entity = dao.save(entity);
            log.info("插入成功！");
        }
        JSONObject isOpen = toggleAction.getToggleIsOpenByKey("sendWaitdoNoFlowMessage");
        if("1".equals(isOpen.get("flag").toString())) {
            if ("1".equals(isOpen.get("isOpen").toString())) {
                if (sendMsg) {
					String[] userIdArray = userids.split(",");
					for (String userid : userIdArray) {
						sendMsgsByUid(userid, subject, content, messageURL);
					}
				}
            }
        }
        return entity;
    }
    
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
    @SuppressWarnings({ "static-access", "deprecation", "unchecked" })
	@Override
    public SysWaitNoflow saveWaitNoflow(SysWaitNoflow entity,Boolean sendMsg,String subject,String content,String messageURL) {
		String type = getType(entity);
        String userids = "";
        List<UserInfo> userList = new ArrayList<UserInfo>();
        if(StringUtils.isBlank(type)){
            return entity;
        }
        if("1".equals(type)){
            userids = entity.getReceiveUserId();
        }else{
            if("0".equals(type)){//只有部门
                userList = userInfoService.getUserByDeptId(entity.getReceiveDeptId());
            }
            if("2".equals(type)){//部门下业务角色
                /*userList = userInfoService.getUserByDeptIdAndRolesNo(entity.getReceiveDeptId(),entity.getRolesNo());*/
                String ReceiveDeptIds = "";
                JSONObject deptJSON = userInfoService.getAllDeptBySuperId(entity.getReceiveDeptId());
                JSONArray jsonArray = deptJSON.getJSONArray("data");
                List<Dept> list = jsonArray.toList(jsonArray,Dept.class);
                for(Dept item :list){
                    ReceiveDeptIds +=item.getDeptid()+",";
                }
                userList = userInfoService.getUserByDeptIdAndRolesNo(ReceiveDeptIds,entity.getRolesNo());
            }
            for (UserInfo user:userList){
                userids += String.valueOf(user.getUid())+",";
            }
        }
        if(StringUtils.isNotBlank(entity.getId())){
            SysWaitNoflow entityDB = dao.findOne(entity.getId());
            entityDB.setReceiveUserId(entity.getReceiveUserId());
            entityDB.setReceiveUserName(entity.getReceiveUserName());
            entityDB.setReceiveDeptId(entity.getReceiveDeptId());
            entityDB.setReceiveDeptName(entity.getReceiveDeptName());
            entityDB.setRolesNo(entity.getRolesNo());
            entityDB.setTableName(entity.getTableName());
            entityDB.setTableId(entity.getTableId());
            entityDB.setSourceId(entity.getSourceId());
            entityDB.setOpName(entity.getOpName());
            entityDB.setDaibanUrl(entity.getDaibanUrl());
            entityDB.setDaibanNum(entity.getDaibanNum());
            entityDB.setType(type);
            entityDB.setTitle(entity.getTitle());
            entityDB.setAttr1(entity.getAttr1());
            entityDB.setAttr2(entity.getAttr2());
            entityDB.setAttr3(entity.getAttr3());
            entity = dao.save(entityDB);
            log.info("修改成功！");
        }else{
            entity.setDraftUserId(UserUtil.getCruUserId());
            entity.setDraftUserName(UserUtil.getCruUserName());
            entity.setDraftDeptId(UserUtil.getCruDeptId());
            entity.setDraftDeptName(UserUtil.getCruDeptName());
            entity.setDraftChushiId(UserUtil.getCruChushiId());
            entity.setDraftChushiName(UserUtil.getCruChushiName());
            entity.setDraftJuId(UserUtil.getCruJuId());
            entity.setDraftJuName(UserUtil.getCruJuName());
            entity.setVisible(CommonConstants.VISIBLE[1]);
            entity.setDraftTime(JDateToolkit.getNowDate4());
            entity.setType(type);
            entity = dao.save(entity);
            log.info("插入成功！");
        }
        JSONObject isOpen = toggleAction.getToggleIsOpenByKey("sendWaitdoNoFlowMessage");
        if("1".equals(isOpen.get("flag").toString())) {
            if ("1".equals(isOpen.get("isOpen").toString())) {
                if (sendMsg) {
					String[] userIdArray = userids.split(",");
					for (String userid : userIdArray) {
						sendMsgsByUid(userid, subject, content, messageURL);
					}
				}
            }
        }
        return entity;
    }

    @Override
    public SysWaitNoflow getSysWaitNoflow(String id){
        return dao.findOne(id);
    }

}
