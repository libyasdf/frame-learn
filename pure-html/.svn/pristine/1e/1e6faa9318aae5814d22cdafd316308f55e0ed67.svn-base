//package com.sinosoft.sinoep.workflow.service;
//
//import java.util.List;
//import java.util.Vector;
//
//import javax.annotation.Resource;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.MediaType;
//
//import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
//import com.sinosoft.sinoep.api.user.model.UserInfo;
//import com.sinosoft.sinoep.api.user.vo.MessageUser;
//import com.sinosoft.sinoep.user.service.UserInfoService;
//import com.sinosoft.sinoep.workflow.model.FlowSccredit;
//
//import net.sf.json.JSONObject;
//import workflow.common.JDateToolkit;
//import workflow.common.JStringToolkit;
//import workflow.common.WBaseSymbol;
//import workflow.locale.MessageFormat;
//import workflow.locale.Messages;
//import workflow.service.AccreditService;
//import workflow.vo.FlowAccreditVO;
//import workflow.vo.FlowDeptUserVO;
//
///**
// *
// * <B>系统名称：</B><BR>
// * <B>模块名称：待办授权</B><BR>
// * <B>中文类名：</B><BR>
// * <B>概要说明：实现工作流的待办授权接口</B><BR>
// *
// * @author 中科软科技 pangxj
// * @since 2018年1月5日
// */
//@Path("/accredit")
//public class AccreditServiceImpl implements AccreditService {
//
//	private Logger log = LoggerFactory.getLogger(this.getClass());
//
//    @Resource
//    FlowSccreditService flowSccreditService;
//    @Resource
//    private UserInfoService userInfoService;
//
//    /**
//     *
//     * <B>方法名称：获取代办授权事项</B><BR>
//     * <B>概要说明：</B><BR>
//     *
//     * @see workflow.service.AccreditService#getAccreditInfo(java.lang.String)
//     */
//	@Override
//    @POST
//    @Path("/getAccreditInfo")
//    @Produces({ MediaType.APPLICATION_JSON_VALUE, ContentType.APPLICATION_JSON_UTF_8 })
//    public String getAccreditInfo(String param) {
//		log.info("待办授权回调方法getAccreditInfo，参数："+param);
//        String pae = "";
//        FlowAccreditVO vo = new FlowAccreditVO();
//		if (param != null && !"".equals(param)) {
//			if (param.indexOf("=") >= 0) {
//				String[] pa = param.split("=");
//				pae = pa[1];
//			} else {
//				pae = param;
//			}
//            JSONObject json = JSONObject.fromObject(pae);
//            //String sysId = json.containsKey("sysId") ? json.getString("sysId") : "";
//            String deptId = json.containsKey("deptId") ? json.getString("deptId") : "";
//            String userId = json.containsKey("userId") ? json.getString("userId") : "";
//            String fileTypeId = json.containsKey("fileTypeId") ? json.getString("fileTypeId") : "";
//            vo = getAccreditInfo(deptId, userId, fileTypeId, "", deptId);
//        }
//		log.info("待办授权回调方法getAccreditInfo返回值："+vo.toString());
//        return vo.toString();
//    }
//
//    /**
//     *
//     * <B>方法名称：得到授权用户信息授权记录</B><BR>
//     * <B>概要说明：用递归方法实现,得到层层授权用户 返回:授权人=被授权人被授权人</B><BR>
//     *
//     * @author：pangxj
//     * @cretetime:2018年1月5日 上午9:47:32
//     * @param deptId：部门id
//     * @param userId：用户id
//     * @param fileTypeId：授权事项id
//     * @param strAccreidtUser:被授权人串
//     * @param firstDeptId:第一个授权人所在部门ID
//     * @return FlowAccreditVO:[0]-待办 [1]-已办 [2]-授权信息[3]-被授权人的部门ID和用户ID[4]顶级授权人记录ID [5]写入日志
//     */
//    public FlowAccreditVO getAccreditInfo(String deptId, String userId, String fileTypeId, String strAccreidtUser,
//            String firstDeptId) {
//        String userName = "";
//        String deptName = userInfoService.getDeptFullName(deptId);
//        MessageUser messagecreUser = userInfoService.getUserInfoById(userId);
//        if (messagecreUser.getUserInfo().size() > 0) {
//            UserInfo userInfo = messagecreUser.getUserInfo().get(0);
//            userName = userInfo.getUserFullName();
//        }
//        FlowAccreditVO accreditVo = new FlowAccreditVO();
//        String curDate = JDateToolkit.getNowDate2();
//        FlowSccredit flowSccredit = new FlowSccredit();
//        flowSccredit.setUserid(userId);
//        flowSccredit.setDeptid(deptId);
//        flowSccredit.setFiletype(fileTypeId);
//        flowSccredit.setSccbegindate(curDate + " 23:59:59");//开始时间 2018-07-30 23:59：59
//        flowSccredit.setSccenddate(curDate + " 00:00:00");	//结束时间 2018-07-30 00:00:00
//        flowSccredit.setZ_status("1");
//        Vector<FlowSccredit> vec = flowSccreditService.findAllForVec(flowSccredit);
//        int size = vec.size();
//        if (vec != null && size > 0) {
//            for (int i = 0; i < size; i++) {
//                Vector<FlowDeptUserVO> accVec = new Vector<FlowDeptUserVO>();
//                String write = "";
//                String read = "";
//                String sccInfo = "";
//                String sccreditUser = "";
//                String topFsccredit = "";
//                String logReceiveId = "";
//                FlowSccredit vo = vec.elementAt(i);
//                String sccreditType = vo.getSccredittype(); // 1-单独办理 2-共同办理
//                String sccreditUserId = vo.getSccredituserid();
//                String sccreditDeptId = vo.getSccreditdeptid();
//                String issccredit = vo.getIssccredit(); // 是否允许继续授权
//                String sccreditDate = vo.getSccreditdate();
//                String sccreditId = vo.getSccreditid();
//                String sccreditDeptName = userInfoService.getDeptFullName(sccreditDeptId);// FlowDeptTool.getAllDeptName(sccreditDeptId);
//                MessageUser sccreditUserInfo = userInfoService.getUserInfoById(sccreditUserId);
//                String sccreditUserName = "";
//                if (sccreditUserInfo.getUserInfo().size() > 0) {
//                    UserInfo userInfo = sccreditUserInfo.getUserInfo().get(0);
//                    sccreditUserName = userInfo.getUserFullName();
//                }
//                // 把第一次查询的ID放到topFsccredit
//                if (strAccreidtUser.equals("")) {
//                    topFsccredit = MessageFormat.format("{0},{1}", topFsccredit, sccreditId);
//                }
//                if (issccredit.trim().equals("1")) { // 允许再授权
//                    if (strAccreidtUser.indexOf(sccreditUserId) == -1) {
//                        // 非循环授权
//                        String strAccreidtUserTemp = MessageFormat.format("{0},{1}", strAccreidtUser, sccreditUserId);
//                        FlowAccreditVO accVo = getAccreditInfo(sccreditDeptId, sccreditUserId, fileTypeId,
//                                strAccreidtUserTemp, firstDeptId);
//                        // 处理前一返回值
//                        accVec = doAccVec(accVec, accVo.getDeptUserVec());
//                        if (sccreditType.trim().equals("1")) { // 单独办理
//                            // 处理待办
//                            write = doReadWriteStr(accVo.getWrite(), MessageFormat.format("{0}*{1}", firstDeptId,
//                                    sccreditUserId));
//                            // 被授权人没有进行过单独授权且不在当前待办中
//                            if (accVo.getRead().indexOf(sccreditUserId) == -1 && !isIn(accVec, sccreditUserId)) {
//                                FlowDeptUserVO deptUserVo = new FlowDeptUserVO();
//                                deptUserVo.setDeptId(firstDeptId);
//                                deptUserVo.setUserid(sccreditUserId);
//                                deptUserVo.setAllDeptName(deptName);
//                                deptUserVo.setUserNm(sccreditUserName);
//                                deptUserVo.setAccreditDeptId(sccreditDeptId);
//                                deptUserVo.setAccreditDeptName(sccreditDeptName);
//                                deptUserVo.setAccFlag("1");
//                                accVec.add(deptUserVo);
//                            }
//                            // 处理已办
//                            read = doReadWriteStr(accVo.getRead(), deptId + WBaseSymbol.splitDeptUser + userId);
//                            // 处理待办授权信息
//                            sccInfo = MessageFormat.format(Messages.getString(Messages.ACCREDITTOOL_9), userName,
//                                    WBaseSymbol.splitSpace, deptName, sccreditDate, sccreditUserName,
//                                    WBaseSymbol.splitSpace, sccreditDeptName, accVo.getAccInfo());
//                            // 处理被授权人
//                            sccreditUser = doReadWriteStr(accVo.getAccredit(),
//                                    MessageFormat.format("{0}*{1}", sccreditDeptId, sccreditUserId));
//                            // 处理制日志的接受人名称信息
//                            logReceiveId = doReadWriteStr(accVo.getConstituent(),
//                                    MessageFormat.format("{0}*{1}", sccreditDeptId, sccreditUserId));
//                        } else if (sccreditType.trim().equals("2")) {// 共同办理
//                            write = doReadWriteStr(accVo.getWrite(), firstDeptId + WBaseSymbol.splitDeptUser + userId);
//                            write = doReadWriteStr(write, MessageFormat.format("{0}*{1}", firstDeptId, sccreditUserId));
//                            if (accVo.getRead().indexOf(userId) == -1 && !isIn(accVec, userId)) {
//                                FlowDeptUserVO deptUserVo = new FlowDeptUserVO();
//                                deptUserVo.setDeptId(firstDeptId);
//                                deptUserVo.setUserid(userId);
//                                deptUserVo.setAllDeptName(deptName);
//                                deptUserVo.setUserNm(userName);
//                                deptUserVo.setAccreditDeptId("");
//                                deptUserVo.setAccreditDeptName("");
//                                deptUserVo.setAccFlag("0");
//                                accVec.add(deptUserVo);
//                            }
//                            if (accVo.getRead().indexOf(sccreditUserId) == -1 && !isIn(accVec, sccreditUserId)) {
//                                FlowDeptUserVO deptUserVo = new FlowDeptUserVO();
//                                deptUserVo.setDeptId(firstDeptId);
//                                deptUserVo.setUserid(sccreditUserId);
//                                deptUserVo.setAllDeptName(deptName);
//                                deptUserVo.setUserNm(sccreditUserName);
//                                deptUserVo.setAccreditDeptId(sccreditDeptId);
//                                deptUserVo.setAccreditDeptName(sccreditDeptName);
//                                deptUserVo.setAccFlag("1");
//                                accVec.add(deptUserVo);
//                            }
//                            // 处理授权信息
//                            sccInfo = MessageFormat.format(Messages.getString(Messages.ACCREDITTOOL_13), userName,
//                                    WBaseSymbol.splitSpace, deptName, sccreditDate, sccreditUserName,
//                                    WBaseSymbol.splitSpace, sccreditDeptName, accVo.getAccInfo());
//                            // 处理被授权人
//                            sccreditUser = doReadWriteStr(accVo.getAccredit(),
//                                    MessageFormat.format("{0}*{1}", sccreditDeptId, sccreditUserId));
//                            // 处理日志的接收人名称信息
//                            logReceiveId = doReadWriteStr(accVo.getConstituent(),
//                                    MessageFormat.format("{0}*{1}", deptId, userId));
//                            logReceiveId = doReadWriteStr(accVo.getConstituent(),
//                                    MessageFormat.format("{0}*{1}", sccreditDeptId, sccreditUserId));
//                        }
//                    }
//                } else if (issccredit.trim().equals("0")) { // 不允许再授权
//                    if (strAccreidtUser.indexOf(sccreditUserId) == -1) {
//                        if (sccreditType.trim().equals("1")) { // 单独代办
//                            // 处理待办
//                            write = doReadWriteStr(write, MessageFormat.format("{0}*{1}", firstDeptId, sccreditUserId));
//                            if (!isIn(accVec, sccreditUserId)) {
//                                FlowDeptUserVO deptUserVo = new FlowDeptUserVO();
//                                deptUserVo.setDeptId(firstDeptId);
//                                deptUserVo.setUserid(sccreditUserId);
//                                deptUserVo.setAllDeptName(deptName);
//                                deptUserVo.setUserNm(sccreditUserName);
//                                deptUserVo.setAccreditDeptId(sccreditDeptId);
//                                deptUserVo.setAccreditDeptName(sccreditDeptName);
//                                deptUserVo.setAccFlag("1");
//                                accVec.add(deptUserVo);
//                            }
//                            // 处理已办
//                            read = doReadWriteStr(read, deptId + WBaseSymbol.splitDeptUser + userId);
//                            sccInfo = MessageFormat.format(Messages.getString(Messages.ACCREDITTOOL_17), userName,
//                                    WBaseSymbol.splitSpace, deptName, sccreditDate, sccreditUserName,
//                                    WBaseSymbol.splitSpace, sccreditDeptName, accreditVo.getAccInfo());
//                            sccreditUser = doReadWriteStr(sccreditUser,
//                                    MessageFormat.format("{0}*{1}", sccreditDeptId, sccreditUserId));
//                            // 处理日志的接收人名称
//                            logReceiveId = doReadWriteStr(logReceiveId,
//                                    MessageFormat.format("{0}*{1}", sccreditDeptId, sccreditUserId));
//                        } else if (sccreditType.trim().equals("2")) { // 共同办理
//                            // 处理待办
//                            write = doReadWriteStr(write, firstDeptId + WBaseSymbol.splitDeptUser + userId);
//                            write = doReadWriteStr(write, MessageFormat.format("{0}*{1}", firstDeptId, sccreditUserId));
//                            if (!isIn(accVec, userId)) {
//                                FlowDeptUserVO deptUserVo = new FlowDeptUserVO();
//                                deptUserVo.setDeptId(firstDeptId);
//                                deptUserVo.setUserid(userId);
//                                deptUserVo.setAllDeptName(deptName);
//                                deptUserVo.setUserNm(userName);
//                                deptUserVo.setAccreditDeptId(firstDeptId);
//                                deptUserVo.setAccreditDeptName(deptName);
//                                deptUserVo.setAccFlag("0");
//                                accVec.add(deptUserVo);
//                            }
//                            if (!isIn(accVec, sccreditUserId)) {
//                                FlowDeptUserVO deptUserVo = new FlowDeptUserVO();
//                                deptUserVo.setDeptId(firstDeptId);
//                                deptUserVo.setUserid(sccreditUserId);
//                                deptUserVo.setAllDeptName(deptName);
//                                deptUserVo.setUserNm(sccreditUserName);
//                                deptUserVo.setAccreditDeptId(sccreditDeptId);
//                                deptUserVo.setAccreditDeptName(sccreditDeptName);
//                                deptUserVo.setAccFlag("1");
//                                accVec.add(deptUserVo);
//                            }
//                            sccInfo = MessageFormat.format(Messages.getString(Messages.ACCREDITTOOL_20), userName,
//                                    WBaseSymbol.splitSpace, deptName, sccreditDate, sccreditUserName,
//                                    WBaseSymbol.splitSpace, sccreditDeptName, accreditVo.getAccInfo());
//                            sccreditUser = doReadWriteStr(sccreditUser,
//                                    MessageFormat.format("{0}*{1}", sccreditDeptId, sccreditUserId));
//                            logReceiveId = doReadWriteStr(logReceiveId,
//                                    MessageFormat.format("{0}*{1}", sccreditDeptId, sccreditUserId));
//                            // 处理日志的接收人名称
//                        }
//                    }
//                }
//                if (!accreditVo.getWrite().equals("")) {// 去掉重复的
//                    if (!write.equals("")) {
//                        String[] temp = JStringToolkit.splitString(write, WBaseSymbol.splitStr);
//                        if (temp != null) {
//                            for (int k = 0; k < temp.length; k++) {
//                                if (accreditVo.getWrite().indexOf(temp[k]) == -1) {
//                                    accreditVo.setWrite(accreditVo.getWrite() + temp[k] + WBaseSymbol.splitStr);
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    accreditVo.setWrite(accreditVo.getWrite() + write);
//                }
//                accreditVo.setDeptUserVec(doAccVec(accreditVo.getDeptUserVec(), accVec));
//                if (!read.equals("")) {// 处理单独代办
//                    if (accreditVo.getRead().equals("")) {
//                        accreditVo.setRead(read);
//                    } else {
//                        accreditVo.setRead(accreditVo.getRead() + read.substring(3));
//                    }
//                }
//                accreditVo.setAccInfo(accreditVo.getAccInfo() + sccInfo);
//                if (!sccreditUser.equals("")) {// 处理被授权人
//                    if (accreditVo.getAccredit().equals("")) {
//                        accreditVo.setAccredit(sccreditUser);
//                    } else {
//                        accreditVo.setAccredit(accreditVo.getAccredit() + sccreditUser.substring(3));
//                    }
//                }
//                accreditVo.setTopAccId(accreditVo.getTopAccId() + topFsccredit);
//                if (!accreditVo.getConstituent().equals("")) {// 去掉重复的
//                    if (!logReceiveId.equals("")) {
//                        String[] temp = JStringToolkit.splitString(logReceiveId, WBaseSymbol.splitStr);
//                        if (temp != null) {
//                            for (int k = 0; k < temp.length; k++) {
//                                if (accreditVo.getConstituent().indexOf(temp[k]) == -1) {
//                                    accreditVo.setConstituent(
//                                            accreditVo.getConstituent() + temp[k] + WBaseSymbol.splitStr);
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    accreditVo.setConstituent(accreditVo.getConstituent() + logReceiveId);
//                }
//            }
//        }
//        return accreditVo;
//    }
//
//    private Vector<FlowDeptUserVO> doAccVec(Vector<FlowDeptUserVO> accVec, Vector<FlowDeptUserVO> retVec) {
//        if (accVec != null && accVec.size() > 0) {
//            if (retVec != null && retVec.size() > 0) {
//                for (int i = 0; i < retVec.size(); i++) {
//                    FlowDeptUserVO vo = retVec.elementAt(i);
//                    boolean flag = false;
//                    for (int j = 0; j < accVec.size(); j++) {
//                        FlowDeptUserVO rvo = accVec.elementAt(j);
//                        if (vo.getUserid().equals(rvo.getUserid())) {
//                            flag = true;
//                            break;
//                        }
//                    }
//                    if (!flag) {
//                        accVec.add(vo);
//                    }
//                }
//            }
//        } else if (retVec != null && retVec.size() > 0) {
//            accVec.addAll(retVec);
//        }
//        return accVec;
//    }
//
//    /**
//     *
//     * <B>方法名称：处理待办</B><BR>
//     * <B>概要说明：</B><BR>
//     *
//     * @author：pangxj
//     * @cretetime:2018年1月5日 上午9:48:54
//     * @param oldStr
//     * @param newStr
//     * @return String
//     */
//    private String doReadWriteStr(String oldStr, String newStr) {
//        if (oldStr != null && !oldStr.equals("")) {
//            if (oldStr.indexOf(newStr) == -1) {
//                oldStr = oldStr + newStr + WBaseSymbol.splitStr;
//            }
//        } else {
//            oldStr = WBaseSymbol.splitStr + newStr + WBaseSymbol.splitStr;
//        }
//        return oldStr;
//    }
//
//    /**
//     *
//     * <B>方法名称：判断增加的人是否在待办中</B><BR>
//     * <B>概要说明：</B><BR>
//     *
//     * @author：pangxj
//     * @cretetime:2018年1月5日 上午9:49:05
//     * @param accreditVec：部门用户VO类动态数组
//     * @param userId：用户id
//     * @return boolean
//     */
//    private boolean isIn(Vector<FlowDeptUserVO> accreditVec, String userId) {
//        if (accreditVec != null && accreditVec.size() > 0) {
//            for (int j = 0; j < accreditVec.size(); j++) {
//                FlowDeptUserVO deptUserVo = accreditVec.elementAt(j);
//                if (deptUserVo.getUserid().equals(userId)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     *
//     * <B>方法名称：获取代办授权用户</B><BR>
//     * <B>概要说明：</B><BR>
//     *
//     * @see workflow.service.AccreditService#getAccUser(java.lang.String, java.lang.String, java.lang.String)
//     */
//    @POST
//    @Path("/getAccUser")
//    @Produces({ MediaType.APPLICATION_JSON_VALUE, ContentType.APPLICATION_JSON_UTF_8 })
//    public String getAccUser(String fileTypeId, String sccreditdeptid, String sccredituserid) {
//        log.info("待办授权回调方法getAccUser");
//        log.info("fileTypeId:" + fileTypeId);
//        log.info("sccreditdeptid:" + sccreditdeptid);
//        log.info("sccredituserid:" + sccredituserid);
//    	FlowSccredit flowSccredit = new FlowSccredit();
//        flowSccredit.setSccredituserid(sccredituserid);
//        flowSccredit.setSccreditdeptid(sccreditdeptid);
//        flowSccredit.setFiletype(fileTypeId);
//        flowSccredit.setZ_status("1");
//        List<FlowSccredit> flowSccreditList = flowSccreditService.findAll(flowSccredit);
//        if (flowSccreditList != null && flowSccreditList.size() > 0) {
//        	log.info("待办授权回调方法getAccUser返回值：" + flowSccreditList.get(0).getUserid());
//            return flowSccreditList.get(0).getUserid();
//        } else {
//            return "";
//        }
//    }
//
//    /**
//     * <B>方法名称：</B><BR>
//     * <B>概要说明：</B><BR>
//     *
//     * @see workflow.service.AccreditService#addAuth(java.lang.String)
//     */
//    @Override
//    public String addAuth(String arg0) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    /**
//     * <B>方法名称：</B><BR>
//     * <B>概要说明：</B><BR>
//     *
//     * @see workflow.service.AccreditService#delAuth(java.lang.String)
//     */
//    @Override
//    public String delAuth(String arg0) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    /**
//     * <B>方法名称：</B><BR>
//     * <B>概要说明：</B><BR>
//     *
//     * @see workflow.service.AccreditService#getAuth(java.lang.String)
//     */
//    @Override
//    public String getAuth(String arg0) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//}
