package com.sinosoft.sinoep.modules.dagl.daly.borrow.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.constant.ConfigConsts;
import com.sinosoft.sinoep.common.util.CommonUtils;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.message.controller.NotifyController;
import com.sinosoft.sinoep.message.model.NotityMessage;
import com.sinosoft.sinoep.modules.dagl.bmgl.services.BmglService;
import com.sinosoft.sinoep.modules.dagl.constant.DAGLCommonConstants;
import com.sinosoft.sinoep.modules.dagl.daly.borrow.dao.DaglBorrowDao;
import com.sinosoft.sinoep.modules.dagl.daly.borrow.dao.DaglFileDao;
import com.sinosoft.sinoep.modules.dagl.daly.borrow.entity.DaglBorrow;
import com.sinosoft.sinoep.modules.dagl.daly.borrow.entity.DaglFile;
import com.sinosoft.sinoep.modules.dagl.daly.shoppingtrolley.services.ShoppingTrolleyService;
import com.sinosoft.sinoep.modules.dagl.daly.urge.entity.BorrowUrge;
import com.sinosoft.sinoep.modules.dagl.daly.urge.services.BorrowUrgeService;
import com.sinosoft.sinoep.user.util.UserUtil;
import com.sinosoft.sinoep.waitNoflow.services.SysWaitNoflowService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.message.dateUtil.DataUtil;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


/**
 * @Auther:邴秀慧
 * @Description:档案借阅服务类
 * @Date:2018/11/10 9:49
 */
@Service
public class DaglBorrowServiceImpl implements DaglBorrowService {

    @Autowired
    private DaglBorrowDao daglBorrowDao;

    @Autowired
    private DaglFileDao daglFileDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SysWaitNoflowService sysWatiNoflowService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private BmglService bmglService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private static Connection con = null;

    @Autowired
    private BorrowUrgeService borrowUrgeService;

    @Autowired
    private NotifyController notifyController;

    @Autowired
    private ShoppingTrolleyService shoppingTrolleyService;

    /**
     * @Auther:邴秀慧
     * @Description:查询草稿列表
     * @Date:2018/11/10 9:42
     */
    @Override
    public PageImpl getPageListDraft(Pageable pageable, PageImpl pageImpl, DaglBorrow daglBorrow) throws Exception {
        pageImpl.setFlag("0");
        StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        querySql.append("	from DaglBorrow t ");
        querySql.append("	where t.visible = '" + CommonConstants.VISIBLE[1] + "' and t.subflag = '0' ");
        //拼接条件
        querySql.append("	and t.creUserId = ?");
        para.add(UserUtil.getCruUserId());
        if (StringUtils.isNotBlank(daglBorrow.getCreTime())) {
            String startTime = daglBorrow.getCreTime().substring(0, (daglBorrow.getCreTime().length() + 1) / 2 - 1).trim();
            String endTime = daglBorrow.getCreTime().substring((daglBorrow.getCreTime().length() + 1) / 2, daglBorrow.getCreTime().length()).trim();
            querySql.append("   and substr(t.creTime,0,10) >= ?");
            querySql.append("   and substr(t.creTime,0,10) <= ?");
            para.add(startTime);
            para.add(endTime);
        }
        if (StringUtils.isNotBlank(daglBorrow.getUsePurpose())) {
            querySql.append("   and t.usePurpose = ?");
            para.add(daglBorrow.getUsePurpose());
        }
        //拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            querySql.append("  order by t.creTime desc");
        } else {
            querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + "");
        }

        Page<DaglBorrow> page = daglBorrowDao.query(querySql.toString(), pageable, para.toArray());
        //草稿列表，添加操作列
        List<DaglBorrow> content = page.getContent();
        for (DaglBorrow daglBorrowTemp : content) {
            if (ConfigConsts.START_FLAG.equals(daglBorrowTemp.getSubflag())) {
                daglBorrowTemp.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
            }
        }
        pageImpl.setFlag("1");
        pageImpl.getData().setRows(content);
        pageImpl.getData().setTotal((int) page.getTotalElements());
        return pageImpl;
    }

    /**
     * @Auther:邴秀慧
     * @Description:保存主子表
     * @Date:2018/11/10 9:42
     */
    @Override
    public DaglBorrow saveForm(DaglBorrow daglBorrow) throws Exception {
        List<DaglFile> newFileList = daglBorrow.getFileList();
        daglBorrow.setVisible(CommonConstants.VISIBLE[1]);
        String year = new SimpleDateFormat("yyyy").format(new Date());
        if (StringUtils.isBlank(daglBorrow.getId())) {
            String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            daglBorrow.setCreTime(creTime);
            daglBorrow.setCreUserId(UserUtil.getCruUserId());
            daglBorrow.setCreUserName(UserUtil.getCruUserName());
            daglBorrow.setCreChushiName(UserUtil.getCruChushiName());
            daglBorrow.setCreChushiId(UserUtil.getCruChushiId());
            daglBorrow.setCreJuName(UserUtil.getCruJuName());
            daglBorrow.setCreJuId(UserUtil.getCruJuId());
            daglBorrow.setCreDeptId(UserUtil.getCruDeptId());
            daglBorrow.setCreDeptName(UserUtil.getCruDeptName());
            daglBorrow.setUpdateTime(creTime);
            daglBorrow.setUpdateUserId(UserUtil.getCruUserId());
            daglBorrow.setUpdateUserName(UserUtil.getCruUserName());
            if (StringUtils.isNotBlank(daglBorrow.getFlowType()) && daglBorrow.getFlowType().equals("1")) {
                daglBorrow.setTitle(creTime + daglBorrow.getBorrowUnitName() + daglBorrow.getBorrowUserName() + "借阅本单位档案申请");
            } else {
                daglBorrow.setTitle(creTime + daglBorrow.getBorrowUnitName() + daglBorrow.getBorrowUserName() + "借阅非本单位档案申请");
            }
            daglBorrow.setYear(year);
            daglBorrow = daglBorrowDao.save(daglBorrow);
        } else {
            String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            DaglBorrow oldDaglBorrow = daglBorrowDao.findOne(daglBorrow.getId());
            oldDaglBorrow.setTitle(daglBorrow.getTitle());
            oldDaglBorrow.setUpdateTime(creTime);
            oldDaglBorrow.setUpdateUserId(UserUtil.getCruUserId());
            oldDaglBorrow.setUpdateUserName(UserUtil.getCruUserName());
            oldDaglBorrow.setBorrowUserId(daglBorrow.getBorrowUserId());
            oldDaglBorrow.setBorrowUserName(daglBorrow.getBorrowUserName());
            oldDaglBorrow.setBorrowUnitName(daglBorrow.getBorrowUnitName());
            oldDaglBorrow.setBorrowUnitId(daglBorrow.getBorrowUnitId());
            oldDaglBorrow.setUsePurpose(daglBorrow.getUsePurpose());
            oldDaglBorrow.setPhone(daglBorrow.getPhone());
            oldDaglBorrow.setRemark(daglBorrow.getRemark());
            oldDaglBorrow.setBorrowDeptId(daglBorrow.getBorrowDeptId());
            oldDaglBorrow.setBorrowDeptName(daglBorrow.getBorrowDeptName());
            oldDaglBorrow.setFlowType(daglBorrow.getFlowType());
            oldDaglBorrow.setApproveUserId(daglBorrow.getApproveUserId());
            oldDaglBorrow.setApproveUserName(daglBorrow.getApproveUserName());
            oldDaglBorrow.setApproveUnitId(daglBorrow.getApproveUnitId());
            oldDaglBorrow.setApproveUnitName(daglBorrow.getApproveUnitName());
            if (StringUtils.isNotBlank(daglBorrow.getFlowType()) && daglBorrow.getFlowType().equals("1")) {
                oldDaglBorrow.setTitle(creTime + daglBorrow.getBorrowUnitName() + daglBorrow.getBorrowUserName() + "借阅本单位档案申请");
            } else {
                oldDaglBorrow.setTitle(creTime + daglBorrow.getBorrowUnitName() + daglBorrow.getBorrowUserName() + "借阅非本单位档案申请");
            }
            daglBorrow = daglBorrowDao.save(oldDaglBorrow);
        }
        if (newFileList.size() > 0) {
            List<DaglFile> fileList = new ArrayList<DaglFile>();
            for (DaglFile daglFile : newFileList) {
                if (StringUtils.isBlank(daglFile.getId())) {
                    String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
                    daglFile.setVisible(CommonConstants.VISIBLE[1]);
                    daglFile.setCreTime(creTime);
                    daglFile.setCreUserId(UserUtil.getCruUserId());
                    daglFile.setCreUserName(UserUtil.getCruUserName());
                    daglFile.setCreChushiName(UserUtil.getCruChushiName());
                    daglFile.setCreChushiId(UserUtil.getCruChushiId());
                    daglFile.setCreJuName(UserUtil.getCruJuName());
                    daglFile.setCreJuId(UserUtil.getCruJuId());
                    daglFile.setCreDeptId(UserUtil.getCruDeptId());
                    daglFile.setCreDeptName(UserUtil.getCruDeptName());
                    daglFile.setUpdateTime(creTime);
                    daglFile.setUpdateUserId(UserUtil.getCruUserId());
                    daglFile.setUpdateUserName(UserUtil.getCruUserName());
                    daglFile.setApproveUnitName(daglBorrow.getApproveUnitName());
                    daglFile.setApproveUnitId(daglBorrow.getApproveUnitId());
                    daglFile.setBorrowUserId(daglBorrow.getBorrowUserId());
                    daglFile.setBorrowUserName(daglBorrow.getBorrowUserName());
                    daglFile.setBorrowUnitId(daglBorrow.getBorrowUnitId());
                    daglFile.setBorrowUnitName(daglBorrow.getBorrowUnitName());
                    daglFile.setBorrowDeptId(daglBorrow.getBorrowDeptId());
                    daglFile.setBorrowDeptName(daglBorrow.getBorrowDeptName());
                    daglFile.setUsePurpose(daglBorrow.getUsePurpose());
                    daglFile.setPhone(daglBorrow.getPhone());
                    daglFile.setApproveUserId(daglBorrow.getApproveUserId());
                    daglFile.setApproveUserName(daglBorrow.getApproveUserName());
                    daglFile.setBorrowId(daglBorrow.getId());
                    daglFile = daglFileDao.save(daglFile);
                } else {
                    String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
                    DaglFile oldDaglFile = daglFileDao.findOne(daglFile.getId());
                    oldDaglFile.setUpdateTime(creTime);
                    oldDaglFile.setUpdateUserId(UserUtil.getCruUserId());
                    oldDaglFile.setUpdateUserName(UserUtil.getCruUserName());
                    oldDaglFile.setCategoryName(daglFile.getCategoryName());
                    oldDaglFile.setArchiveNo(daglFile.getArchiveNo());
                    oldDaglFile.setCategoryNo(daglFile.getCategoryNo());
                    oldDaglFile.setMainTitle(daglFile.getMainTitle());
                    oldDaglFile.setLjdwMark(daglFile.getLjdwMark());
                    oldDaglFile.setBasefolderUnit(daglFile.getBasefolderUnit());
                    oldDaglFile.setUseWay(daglFile.getUseWay());
                    oldDaglFile.setApproveUnitName(daglBorrow.getApproveUnitName());
                    oldDaglFile.setApproveUnitId(daglBorrow.getApproveUnitId());
                    oldDaglFile.setBorrowUserId(daglBorrow.getBorrowUserId());
                    oldDaglFile.setBorrowUserName(daglBorrow.getBorrowUserName());
                    oldDaglFile.setBorrowUnitId(daglBorrow.getBorrowUnitId());
                    oldDaglFile.setBorrowUnitName(daglBorrow.getBorrowUnitName());
                    oldDaglFile.setBorrowDeptId(daglBorrow.getBorrowDeptId());
                    oldDaglFile.setBorrowDeptName(daglBorrow.getBorrowDeptName());
                    oldDaglFile.setUsePurpose(daglBorrow.getUsePurpose());
                    oldDaglFile.setPhone(daglBorrow.getPhone());
                    oldDaglFile.setRecid(daglFile.getRecid());
                    oldDaglFile.setTableName(daglFile.getTableName());
                    oldDaglFile.setApproveUserId(daglBorrow.getApproveUserId());
                    oldDaglFile.setApproveUserName(daglBorrow.getApproveUserName());
                    oldDaglFile.setBorrowId(daglBorrow.getId());
                    daglFile = daglFileDao.save(oldDaglFile);
                }
                fileList.add(daglFile);
            }
            for (DaglFile daglFile : fileList) {
                List<Map<String, Object>> tableRelationPList = new ArrayList<Map<String, Object>>();
                if (StringUtils.isNotBlank(daglFile.getTableName()) && StringUtils.isNotBlank(daglFile.getRecid())) {
                    String selTableRelationP = "select t.* from " + daglFile.getTableName() + " t where t.recid='" + daglFile.getRecid() + "'";
                    tableRelationPList = jdbcTemplate.queryForList(selTableRelationP);
                }
                if (tableRelationPList.size() > 0) {
                    if (tableRelationPList.get(0).get("borrow_status") != null && StringUtils.isNotBlank(tableRelationPList.get(0).get("borrow_status").toString()) &&tableRelationPList.get(0).get("borrow_status").toString().equals("1")) {
                        daglFile.setIsBorrow("部分借出");
                    }else if (tableRelationPList.get(0).get("borrow_status") != null && StringUtils.isNotBlank(tableRelationPList.get(0).get("borrow_status").toString()) &&tableRelationPList.get(0).get("borrow_status").toString().equals("2")) {
                        daglFile.setIsBorrow("借出");
                    }else{
                        daglFile.setIsBorrow("未借出");
                    }
                }
            }
            daglBorrow.setFileList(fileList);
        }
        return daglBorrow;
    }

    /**
     * @Auther:邴秀慧
     * @Description:删除主表数据
     * @Date:2018/11/10 10:06
     */
    @Override
    public int deleteDaglBorrow(String id) throws Exception {
        String jpql = "update DaglBorrow t set t.visible = ? where t.id = ?";
        int num = daglBorrowDao.update(jpql, CommonConstants.VISIBLE[0], id);
        DaglBorrow daglBorrow = daglBorrowDao.findOne(id);
        List<DaglFile> fileList = daglFileDao.findByborrowIdContainingAndVisibleOrderByOrderNumAsc(daglBorrow.getId(), "1");
        if (fileList.size() > 0) {
            for (DaglFile daglFile : fileList) {
                String fileSql = "update DaglFile t set t.visible = ? where t.id = ?";
                daglFileDao.update(fileSql, CommonConstants.VISIBLE[0], daglFile.getId());
            }
        }
        return num;
    }

    /**
     * @Auther:邴秀慧
     * @Description:删除子表数据
     * @Date:2018/11/10 10:56
     */
    @Override
    public int deleteDaglFile(String ids) throws Exception {
        int num = 0;
        String[] idArr = ids.split(",");
        for (int i = 0; i < idArr.length; i++) {
            DaglFile daglFile = daglFileDao.findOne(idArr[i]);
            String fileSql = "update DaglFile t set t.visible = ? where t.id = ?";
            daglFileDao.update(fileSql, CommonConstants.VISIBLE[0], daglFile.getId());
            num++;
        }
        return num;
    }

    /**
     * @Auther:邴秀慧
     * @Description:获取主子表的数据
     * @Date:2018/11/10 10:58
     */
    @Override
    public DaglBorrow getById(String id) throws Exception {
        List<DaglFile> daglFileList = new ArrayList<DaglFile>();
        DaglBorrow daglBorrow = daglBorrowDao.findOne(id);
        daglFileList = daglFileDao.findByborrowIdContainingAndVisibleOrderByOrderNumAsc("%" + daglBorrow.getId() + "%", "1");
        for (DaglFile daglFile : daglFileList) {
            List<Map<String, Object>> tableRelationPList = new ArrayList<Map<String, Object>>();
            if (StringUtils.isNotBlank(daglFile.getTableName()) && StringUtils.isNotBlank(daglFile.getRecid())) {
                String selTableRelationP = "select t.* from " + daglFile.getTableName() + " t where t.recid='" + daglFile.getRecid() + "'";
                tableRelationPList = jdbcTemplate.queryForList(selTableRelationP);
            }
            if (tableRelationPList.size() > 0) {
                if (tableRelationPList.get(0).get("borrow_status") != null && StringUtils.isNotBlank(tableRelationPList.get(0).get("borrow_status").toString()) &&tableRelationPList.get(0).get("borrow_status").toString().equals("1")) {
                    daglFile.setIsBorrow("部分借出");
                }else if (tableRelationPList.get(0).get("borrow_status") != null && StringUtils.isNotBlank(tableRelationPList.get(0).get("borrow_status").toString()) &&tableRelationPList.get(0).get("borrow_status").toString().equals("2")) {
                    daglFile.setIsBorrow("借出");
                }else{
                    daglFile.setIsBorrow("未借出");
                }
            }
        }
        daglBorrow.setFileList(daglFileList);
        return daglBorrow;
    }

    /**
     * @Auther:邴秀慧
     * @Description:更新subflag的状态值
     * @Date:2018/11/13 20:09
     */
    @Override
    public DaglBorrow updateSubFlag(String id, String subflag) throws Exception {
        DaglBorrow daglBorrow = getById(id);
        String oldSubflag = daglBorrow.getSubflag();
        daglBorrow.setSubflag(subflag);
        List<DaglFile> daglFileList = new ArrayList<DaglFile>();
        daglFileList = daglFileDao.findByborrowIdContainingAndVisibleOrderByOrderNumAsc("%" + daglBorrow.getId() + "%", "1");
        if (StringUtils.isNotBlank(daglBorrow.getSubflag()) && (daglBorrow.getSubflag().equals("6") || daglBorrow.getSubflag().equals("5"))) {
            daglBorrow.setApproveUserId(UserUtil.getCruUserId());
            daglBorrow.setApproveUserName(UserUtil.getCruUserName());
            for (DaglFile daglFile : daglFileList) {
                daglFile.setApproveUserId(UserUtil.getCruUserId());
                daglFile.setApproveUserName(UserUtil.getCruUserName());
                if (StringUtils.isNotBlank(daglFile.getOldId())) {
                    //设置为已归还（调用归还的方法）
                    DaglFile oldDaglFile = daglFileDao.findOne(daglFile.getOldId());
                    if (StringUtils.isBlank(oldDaglFile.getReturnDate())) {
                        returnOneBorrow(oldDaglFile.getId(), "1");
                    }
                }
                daglFileDao.save(daglFile);
            }
        }
        //先判断是不是起草的时候点击的发送按钮
        if (StringUtils.isNotBlank(daglBorrow.getSubflag()) && (daglBorrow.getSubflag().equals("1"))
                && StringUtils.isNotBlank(oldSubflag) && (oldSubflag.equals("0"))
                && (StringUtils.isBlank(daglBorrow.getIsInnewOrReborrow()) || daglBorrow.getIsInnewOrReborrow().equals("0"))) {
            //如果有子表并未有档案唯一标识，根据档案唯一标识删除收集列表
            for (DaglFile daglFile : daglFileList) {
                if (StringUtils.isNotBlank(daglFile.getRecid())) {
                    //删除为该id的收集数据
                    shoppingTrolleyService.deleteShoppingTrolleyOfUserByRecidAndTableName(daglFile.getRecid(), daglFile.getTableName());
                }
                daglFileDao.save(daglFile);
            }
        }
        return daglBorrowDao.save(daglBorrow);
    }

    /**
     * @Auther:邴秀慧
     * @Description:根据oldid查询该记录是否被重复续借或者重借
     * @Date:2019/3/1 11:49
     */
    @Override
    public int getBorrowById(String ids) throws Exception {
        int num = 0;
        String[] idArr = ids.split(",");
        for (int i = 0; i < idArr.length; i++) {
            List<DaglFile> fileList = daglFileDao.findByVisibleAndOldId("1", idArr[i]);
            for (DaglFile daglFile : fileList) {
                DaglBorrow daglBorrow = daglBorrowDao.findOne(daglFile.getBorrowId());
                if (StringUtils.isNotBlank(daglBorrow.getSubflag()) && (daglBorrow.getSubflag().equals("1") || daglBorrow.getSubflag().equals("0"))) {
                    num++;
                }
            }
        }
        return num;
    }
    /*
    @Scheduled(cron = "0 10 0 * * ?")
    //@Scheduled(cron = "0 05 11 * * ?")
    public void urgeRevert() {
        List<DaglFile> daglFileList = new ArrayList<DaglFile>();
        String creDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        daglFileList = daglFileDao.findByShouldReturnDateAndVisibleAndReturnDateIsNull(creDate, "1");
        for (DaglFile daglFile : daglFileList) {
            //发消息
            String content = "您今天需要归还档号为：" + daglFile.getFileNo() + "的档案";
            sendMsgsByUid(daglFile.getBorrowUserId(), "档案归还提醒", content, "", creTime, null);
        }
        List<DaglFile> daglFileList1 = new ArrayList<DaglFile>();
        daglFileList1 = daglFileDao.findByVisibleAndInRenewAndReturnDateIsNotNullAndUseResultIsNull("1", "0");

        for (DaglFile daglFile : daglFileList1) {
            String messageURL = "/modules/dagl/daly/borrow/fileManage/useResultEditForm.html?id=" + daglFile.getId();
            String daibanURL = "/modules/dagl/daly/borrow/fileManage/useResultEditForm.html";
            //if (daglFile.getId().equals("4028da0767445a80016744662bea0001")) {
                String sql = "select * from sys_waitdo_noflow t where t.source_id = '" + daglFile.getId() + "'";
                //如果没有发送过代办就发送
                List<Map<String, Object>> noFlowList = jdbcTemplate.queryForList(sql);
                if (noFlowList.size() == 0) {
                    //发不走流程代办
                    SysWaitNoflow sysWaitNoflow = sendWaitNoflow(daglFile.getBorrowDeptName(), daglFile.getBorrowDeptId(), daglFile.getBorrowUserId(), daglFile.getBorrowUserName(), daglFile.getId(), "档案利用效果待办", "请填写档案利用效果", messageURL, daibanURL, "档案利用");
                }
           // }

        }
    }

    public boolean sendMsgsByUid(String userId, String subject, String content, String messageURL, String creTime, SysWaitNoflow entity) {
        boolean flag = false;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data = new Date();
        try {
            data = format.parse(creTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotBlank(userId)) {
            if (StringUtils.isNotBlank(messageURL)) {
                if (messageURL.contains("?")) {
                    messageURL = messageURL += "&workItemId=" + entity.getId();
                } else {
                    messageURL = messageURL += "?workItemId=" + entity.getId();
                }
            }
            if (ConfigConsts.SERVICE_TYPE.equals(ConfigConsts.DUBBO_TYPE)) {
                NotityMessage message = new NotityMessage();
                message.setSenderId(ConfigConsts.SYSTEM_ID);//系统id
                message.setSubject(subject);//标题
                message.setContent(content);//内容
                message.setSendTime(data);//发送时间
                message.setAcceptTime(data);//接收时间
                message.setAccepterId(userId);//接收人id
                message.setStatus("0");//状态
                message.setType("3");//类型  1手机    2邮箱   3栈内
                message.setMsgUrl(messageURL);//消息链接
                NotityService notityService = (NotityService) SpringBeanUtils.getBean("notityService");
                notityService.add(message);
                flag = true;
            } else {
                String param = "sendType=3&sendContent=" + content + "&uids=" + userId + "&subId=" + ConfigConsts.SYSTEM_ID +
                        "&msgUrl=" + messageURL + "&title=" + subject + "&appSecret=af2fff3bda2043a991018689848793b4";
                HttpRequestUtil.sendGet(ConfigConsts.MESSAGE_SERVICE_ROOT_URL + "/sendMsgsByUid", param);
                flag = true;
            }
        }
        return flag;
    }

    *//**
     * @Auther:邴秀慧
     * @Description:发送不走流程待办
     * @Date:2018/11/17 12:00
     *//*
    public SysWaitNoflow sendWaitNoflow(String deptName, String deptId, String userId, String userName, String id, String subject, String content, String messageURL, String daibanURL, String opName) {
        SysWaitNoflow sysWaitNoflow = new SysWaitNoflow();
        //if (StringUtils.isNotBlank(UserUtil.getCruDeptId())) {
        SysWaitNoflow waitNoflw = new SysWaitNoflow();
        waitNoflw.setDraftUserName("档案系统");
        waitNoflw.setReceiveDeptName(deptName);//接收人部门
        waitNoflw.setReceiveDeptId(deptId);//接收人部门id 必填
        waitNoflw.setReceiveUserId(userId);//接受人id
        waitNoflw.setReceiveUserName(userName);//接受人name
        //waitNoflw.setRolesNo("");//接受业务角色
        waitNoflw.setOpName(opName);//操作类型
        waitNoflw.setDaibanUrl(daibanURL);//待办url  必填
        waitNoflw.setTitle(subject);//待办显示标题
        waitNoflw.setTableId("");//业务表id
        waitNoflw.setTableName("dagl_file");//业务表名
        waitNoflw.setSourceId(id);//业务id
        waitNoflw.setAttr1("");//预留字段1
        waitNoflw.setAttr2("");//预留字段2
        waitNoflw.setAttr3("");//预留字段3
        sysWaitNoflow = sysWatiNoflowService.saveWaitNoflowSystem(waitNoflw, subject, content, messageURL);
        // }
        return sysWaitNoflow;
    }

    *//**
     * @Auther:邴秀慧
     * @Description:保存利用效果
     * @Date:2018/11/17 14:55
     *//*
    @Override
    public DaglFile saveUseResult(DaglFile daglFile) throws Exception {
        String creTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        DaglFile oldDaglFile = daglFileDao.findOne(daglFile.getId());
        oldDaglFile.setUpdateTime(creTime);
        oldDaglFile.setUpdateUserId(UserUtil.getCruUserId());
        oldDaglFile.setUpdateUserName(UserUtil.getCruUserName());
        oldDaglFile.setUseResult(daglFile.getUseResult());
        oldDaglFile.setUseReason(daglFile.getUseReason());
        daglFile = daglFileDao.save(oldDaglFile);
        return daglFile;
    }

    */

    /**
     * 根据用户id获取用户的门禁卡卡号
     *
     * @param userId
     * @return
     * @author 王磊
     * @Date 2019年2月16日 下午2:09:47
     */
    @Override
    public JSONObject getUserCardNumByUserId(String userId) {
        String sql = "select nvl(card_number,'未设置门禁卡卡号') as card_number from sys_flow_user_ext t where t.userid='" + userId + "'";
        JSONObject result = userInfoService.getDateBySql(sql);
        return result;
    }

    /**
     * @return com.sinosoft.sinoep.common.util.PageImpl
     * @Author 王富康
     * @Description //TODO 借阅记录主表数据（不分页）
     * @Date 2019/2/27 10:20
     * @Param [pageImpl, daglBorrow]
     **/
    @Override
    public List<DaglBorrow> getBorrowList(DaglBorrow daglBorrow) {

        List<Object> para = new ArrayList<>();
        String sql = "select *\n" +
                "  from dagl_borrow t\n" +
                " where t.visible = '" + CommonConstants.VISIBLE[1] + "'\n" +
                "   and t.should_return_date is not null\n";//已经借出的

        //查询条件
        if (StringUtils.isNotBlank(daglBorrow.getBorrowDate())) {//借阅日期
            String startTime = daglBorrow.getBorrowDate().substring(0, (daglBorrow.getBorrowDate().length() + 1) / 2 - 1).trim();
            String endTime = daglBorrow.getBorrowDate().substring((daglBorrow.getBorrowDate().length() + 1) / 2, daglBorrow.getBorrowDate().length()).trim();
            sql += "   and substr(t.borrow_date,0,10) >= ?";
            para.add(startTime);
            sql += "   and substr(t.borrow_date,0,10) <= ?";
            para.add(endTime);
        }

        if (StringUtils.isNotBlank(daglBorrow.getUsePurpose())) {//利用目的
            sql += "   and t.use_purpose = ?";
            para.add(daglBorrow.getUsePurpose());
        }

        if (StringUtils.isNotBlank(daglBorrow.getBorrowStatus())) {
            sql += "   and t.borrow_status = ? ";//借阅状态
            para.add(daglBorrow.getBorrowStatus());
        }

        if (StringUtils.isNotBlank(daglBorrow.getBorrowUserName())) {
            sql += "   and t.borrow_user_name like ? ";//借阅人姓名
            para.add("%" + daglBorrow.getBorrowUserName() + "%");
        }

        sql += "   and ((t.subflag = '5' and t.cre_user_id = '" + UserUtil.getCruUserId() + "')\n" +//走流程
                "    or (t.subflag is null and t.borrow_user_id = '" + UserUtil.getCruUserId() + "'))";//不走流程
        //拼接排序语句
        sql += "  order by t.should_return_date desc";

        List<DaglBorrow> daglBorrows = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DaglBorrow.class),para.toArray());
        return daglBorrows;
    }

    /**
     * @return com.sinosoft.sinoep.common.util.PageImpl
     * @Author 王富康
     * @Description //TODO 借阅记录子表数据（不分页）
     * @Date 2019/2/27 10:20
     * @Param [pageImpl, daglBorrow]
     **/
    @Override
    public List<DaglFile> getFileList(DaglBorrow daglBorrow) {
        String sql = "select * from dagl_file t where 1=1 and t.visible='"+DAGLCommonConstants.VISIBLE[1]+"' " +
                "   and t.borrow_id = ?";//借阅id

        List<DaglFile> daglFiles = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DaglFile.class),daglBorrow.getId());

        return daglFiles;
    }

    /**
     * @return com.sinosoft.sinoep.common.util.PageImpl
     * @Author 王富康
     * @Description //TODO 传统借阅/线上借阅列表查询
     * @Date 2019/3/1 15:24
     * @Param [pageImpl, daglBorrow, BlStutas-办理状态, type-traditional/onLine]
     **/
    @Override
    public PageImpl traditionalOrOnLineList(Pageable pageable, PageImpl pageImpl, DaglBorrow daglBorrow, String BlStutas, String type) {
        StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();

        querySql.append(" from DaglBorrow t where 1=1 ");

        querySql.append("   and t.visible = ?");//未删除的
        para.add("" + CommonConstants.VISIBLE[1] + "");

        if ("traditional".equals(type)) {
            querySql.append("   and t.subflag is null ");//流程状态为空的-传统借阅
        } else if ("onLine".equals(type)) {
            querySql.append("   and t.subflag ='5' ");//流程通过的-线上借阅
        }

        querySql.append("   and t.shouldReturnDate is null ");//


        if (StringUtils.isNotBlank(daglBorrow.getCreTime())) {//传统借阅的查询项：申请日期
            String startTime = daglBorrow.getCreTime().substring(0, (daglBorrow.getCreTime().length() + 1) / 2 - 1).trim();
            String endTime = daglBorrow.getCreTime().substring((daglBorrow.getCreTime().length() + 1) / 2, daglBorrow.getCreTime().length()).trim();
            querySql.append("   and substr(t.creTime,0,10) >= ?");
            querySql.append("   and substr(t.creTime,0,10) <= ?");
            para.add(startTime);
            para.add(endTime);
        }

        if (StringUtils.isNotBlank(daglBorrow.getBorrowDate())) {//线上借阅的查询项：借阅日期
            String startTime = daglBorrow.getBorrowDate().substring(0, (daglBorrow.getBorrowDate().length() + 1) / 2 - 1).trim();
            String endTime = daglBorrow.getBorrowDate().substring((daglBorrow.getBorrowDate().length() + 1) / 2, daglBorrow.getBorrowDate().length()).trim();
            querySql.append("   and substr(t.borrowDate,0,10) >= ?");
            querySql.append("   and substr(t.borrowDate,0,10) <= ?");
            para.add(startTime);
            para.add(endTime);
        }

        if (StringUtils.isNotBlank(daglBorrow.getUsePurpose())) {
            querySql.append("   and t.usePurpose = ?");//利用目的
            para.add("" + daglBorrow.getUsePurpose() + "");
        }

        if (StringUtils.isNotBlank(daglBorrow.getBorrowStatus())) {
            querySql.append("   and t.borrowStatus = ?");//借阅状态
            para.add("" + daglBorrow.getBorrowStatus() + "");
        }


       /* if(StringUtils.isNotBlank(BlStutas)){//完结/未完结
            if("01".equals(BlStutas)){//已保存的，借阅日期不为空的为完结的
                querySql.append("   and t.borrowDate is not null ");//档号
            }else if("02".equals(BlStutas)){//暂存的，借阅日期为空的为未完结的
                querySql.append("   and t.borrowDate is null ");//档号
            }

        }*/
        if (StringUtils.isNotBlank(daglBorrow.getBorrowUnitName())) {
            querySql.append("   and t.borrowUnitName like ?");//借阅人单位
            para.add("%" + daglBorrow.getBorrowUnitName() + "%");
        }
        if (StringUtils.isNotBlank(daglBorrow.getBorrowUserName())) {
            querySql.append("   and t.borrowUserName like ?");//借阅人姓名
            para.add("%" + daglBorrow.getBorrowUserName() + "%");
        }

        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            querySql.append("  order by t.creTime desc ) ");
        } else {
            querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + " ");
        }

        Page<DaglBorrow> page = daglBorrowDao.query(querySql.toString(), pageable, para.toArray());
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }

    /**
     * @return java.util.List<com.sinosoft.sinoep.modules.dagl.daly.borrow.entity.DaglBorrow>
     * @Author 王富康
     * @Description //TODO 档案归还（主表查询）（不分页）
     * @Date 2019/3/2 15:39
     * @Param [daglBorrow]
     **/
    @Override
    public List<DaglBorrow> getShouldReturnBorrowList(DaglBorrow daglBorrow) {

        List<Object> para = new ArrayList<>();
        String sql = "select *\n" +
                "  from dagl_borrow t\n" +
                " where t.visible = '" + CommonConstants.VISIBLE[1] + "'\n" +
                "   and t.should_return_date is not null " +
                "   and t.borrow_status in('1','2')\n";//未归还、部分归还
        //查询条件
        if (StringUtils.isNotBlank(daglBorrow.getBorrowDate())) {//借阅日期
            String startTime = daglBorrow.getBorrowDate().substring(0, (daglBorrow.getBorrowDate().length() + 1) / 2 - 1).trim();
            String endTime = daglBorrow.getBorrowDate().substring((daglBorrow.getBorrowDate().length() + 1) / 2, daglBorrow.getBorrowDate().length()).trim();
            sql += "   and substr(t.borrow_date,0,10) >= ?";
            sql += "   and substr(t.borrow_date,0,10) <= ?";
            para.add(startTime);
            para.add(endTime);
        }

        if (StringUtils.isNotBlank(daglBorrow.getUsePurpose())) {//利用目的
            sql += "   and t.use_purpose = ?";
            para.add(daglBorrow.getUsePurpose());
        }

        if (StringUtils.isNotBlank(daglBorrow.getBorrowStatus())) {
            if(daglBorrow.getBorrowStatus().equals("3")){
                String creDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                sql += "   and substr(t.should_return_date,0,10) < ?";
                para.add(creDate);
            }else{
                sql += "   and t.borrow_status = ? ";//借阅状态
                para.add(daglBorrow.getBorrowStatus());
            }

        }

        if (StringUtils.isNotBlank(daglBorrow.getBorrowUserName())) {
            sql += "   and t.borrow_user_name like ?";//借阅人姓名
            para.add("%" + daglBorrow.getBorrowUserName() + "%");
        }

        //拼接排序语句
        sql += "  order by t.should_return_date asc";

        List<DaglBorrow> daglBorrows = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DaglBorrow.class),para.toArray());
        return daglBorrows;
    }


    /**
     * @return com.sinosoft.sinoep.common.util.PageImpl
     * @Author 王富康
     * @Description //TODO 档案管理-借阅记录
     * @Date 2019/3/1 15:31
     * @Param [pageable, pageImpl, daglBorrow, BlStutas, type]
     **/
    @Override
    public PageImpl recordList(Pageable pageable, PageImpl pageImpl, DaglFile daglFile) {
        StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();

        querySql.append(" from DaglFile t where 1=1 ");

        querySql.append("   and t.visible = ?");//未删除的
        para.add("" + CommonConstants.VISIBLE[1] + "");

        querySql.append("   and t.shouldReturnDate is not null");//应还日期不为空

        String today = DataUtil.getToday();

        if (StringUtils.isNotBlank(daglFile.getBorrowStatus())) {
            if ("01".equals(daglFile.getBorrowStatus())) {//未归还
                querySql.append("   and t.returnDate is null and '" + today + "' <= shouldReturnDate");//归还日期为空，并且当前日期小于应还日期的为未归还的
            } else if ("02".equals(daglFile.getBorrowStatus())) {//逾期未还
                querySql.append("   and t.borrowDate is null and '" + today + "' > shouldReturnDate");//归还日期为空，并且当前日期大于应还日期的为未归还的
            } else if ("03".equals(daglFile.getBorrowStatus())) {//按时还
                querySql.append("   and t.returnDate <= shouldReturnDate ");
            } else if ("04".equals(daglFile.getBorrowStatus())) {//逾期还
                querySql.append("   and t.returnDate > shouldReturnDate ");
            }

        }

        if (StringUtils.isNotBlank(daglFile.getBorrowDate())) {
            String startTime = daglFile.getBorrowDate().substring(0, (daglFile.getBorrowDate().length() + 1) / 2 - 1).trim();
            String endTime = daglFile.getBorrowDate().substring((daglFile.getBorrowDate().length() + 1) / 2, daglFile.getBorrowDate().length()).trim();
            querySql.append("   and substr(t.borrowDate,0,10) >= ?");
            querySql.append("   and substr(t.borrowDate,0,10) <= ?");
            para.add(startTime);
            para.add(endTime);
        }

        if (StringUtils.isNotBlank(daglFile.getBorrowUserName())) {
            querySql.append("   and t.borrowUserName like ?");//借阅人姓名
            para.add("%" + daglFile.getBorrowUserName() + "%");
        }

        if (StringUtils.isNotBlank(daglFile.getBorrowUnitName())) {
            querySql.append("   and t.borrowUnitName like ?");//借阅人姓名
            para.add("%" + daglFile.getBorrowUnitName() + "%");
        }
        if (StringUtils.isNotBlank(daglFile.getMainTitle())) {
            querySql.append("   and t.mainTitle like ?");//借阅人姓名
            para.add("%" + daglFile.getMainTitle() + "%");
        }
        if (StringUtils.isNotBlank(daglFile.getArchiveNo())) {
            querySql.append("   and t.archiveNo like ?");//借阅人姓名
            para.add("%" + daglFile.getArchiveNo() + "%");
        }
        if (StringUtils.isNotBlank(daglFile.getCategoryName())) {
            querySql.append("   and t.categoryName like ?");//借阅人姓名
            para.add("%" + daglFile.getCategoryName() + "%");
        }
        if (StringUtils.isNotBlank(daglFile.getLjdwMark())) {
            querySql.append("   and t.ljdwMark = ?");//借阅人姓名
            para.add(daglFile.getLjdwMark());
        }
        if (StringUtils.isNotBlank(daglFile.getHandleUserName())) {
            querySql.append("   and t.handleUserName like ?");//借阅人姓名
            para.add("%" + daglFile.getHandleUserName() + "%");
        }
        if (StringUtils.isNotBlank(daglFile.getReturnHandleUserName())) {
            querySql.append("   and t.returnHandleUserName like ?");//借阅人姓名
            para.add("%" + daglFile.getReturnHandleUserName() + "%");
        }
        if (StringUtils.isNotBlank(daglFile.getShouldReturnDate())) {
            String startTime = daglFile.getShouldReturnDate().substring(0, (daglFile.getShouldReturnDate().length() + 1) / 2 - 1).trim();
            String endTime = daglFile.getShouldReturnDate().substring((daglFile.getShouldReturnDate().length() + 1) / 2, daglFile.getShouldReturnDate().length()).trim();
            querySql.append("   and substr(t.shouldReturnDate,0,10) >= ?");
            querySql.append("   and substr(t.shouldReturnDate,0,10) <= ?");
            para.add(startTime);
            para.add(endTime);
        }
        if (StringUtils.isNotBlank(daglFile.getReturnDate())) {
            String startTime = daglFile.getReturnDate().substring(0, (daglFile.getReturnDate().length() + 1) / 2 - 1).trim();
            String endTime = daglFile.getReturnDate().substring((daglFile.getReturnDate().length() + 1) / 2, daglFile.getReturnDate().length()).trim();
            querySql.append("   and substr(t.returnDate,0,10) >= ?");
            querySql.append("   and substr(t.returnDate,0,10) <= ?");
            para.add(startTime);
            para.add(endTime);
        }
        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            querySql.append("  order by t.archiveNo desc, t.returnDate asc ) ");
        } else {
            querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + " ");
        }

        Page<DaglFile> page = daglFileDao.query(querySql.toString(), pageable, para.toArray());
        return new PageImpl((int) page.getTotalElements(), page.getContent());
    }

    /**
     * @return int
     * @Author 王富康
     * @Description //TODO 档案一键归还
     * @Date 2019/3/4 9:57
     * @Param [daglBorrow]
     **/
    @Override
    public JSONObject returnThisBorrow(String id) {

        // 定义事务隔离级别，传播行为
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //事务状态类，通过PlatformTransactionManager的getTransaction方法根据事务定义获取；获取事务状态后，Spring根据传播行为来决定如何开启事务
        TransactionStatus transactionStatus = transactionManager.getTransaction(def);

        JSONObject json = new JSONObject();
        json.put("flag", "0");

        try {
            con = jdbcTemplate.getDataSource().getConnection();

            String sql = "select * \n" +
                    "  from dagl_borrow t WHERE t.id=?";
            List<DaglBorrow> daglBorrow = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DaglBorrow.class),id);
            //DaglBorrow daglBorrow = daglBorrowDao.findOne(id);
            if (daglBorrow.size() > 0) {
                String updateSql = "UPDATE dagl_borrow t SET t.borrow_status = '3' WHERE t.id = ?";
                //1.修改主表【已归还】状态；
                //daglBorrow.setBorrowStatus("3");
                //daglBorrowDao.save(daglBorrow);
                jdbcTemplate.update(updateSql,id);//修改父表的状态
                //这个和下面的修改子表状态顺序的不能变，否则因为事物原因不起作用
                List<DaglFile> daglFiles = getFileList(daglBorrow.get(0));

                for (int i = 0; i < daglFiles.size(); i++) {
                    //排除已归还,线下借阅不在数据库的的数据
                    if (null == daglFiles.get(i).getReturnDate() && null != daglFiles.get(i).getTableName()) {
                        bmglService.returnDangAnToChangeStatus(daglFiles.get(i).getRecid(), daglFiles.get(i).getTableName());
                    }

                }
                //2.给子表设置归还日期；
                String sql1 = "UPDATE dagl_file t " +
                        "   SET t.return_date = '" + DataUtil.getToday() + "'," +
                        "   t.return_time='" + workflow.common.JDateToolkit.getNowDate4() + "'," +
                        "   t.return_handle_user_id = '" + UserUtil.getCruUserId() + "'," +//归还经办人id
                        "   t.return_handle_user_name = '" + UserUtil.getCruUserName() + "'" +//归还经办人的name
                        "    WHERE t.borrow_id = ? and t.return_date is null";
                int num = jdbcTemplate.update(sql1,id);
                //3.调用接口，调整档案（子表数据）在档案库的借阅状态：
            }

            transactionManager.commit(transactionStatus);
            json.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                //回滚事务
                transactionManager.rollback(transactionStatus);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return json;
    }

    /**
     * @return int
     * @Author 王富康
     * @Description //TODO 档案归还（单个）
     * @Date 2019/3/4 10:21
     * @Param [daglBorrow]
     **/
    @Override
    public JSONObject returnOneBorrow(String id, String isAuto) {

        // 定义事务隔离级别，传播行为
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //事务状态类，通过PlatformTransactionManager的getTransaction方法根据事务定义获取；获取事务状态后，Spring根据传播行为来决定如何开启事务
        TransactionStatus transactionStatus = transactionManager.getTransaction(def);

        JSONObject json = new JSONObject();
        json.put("flag", "0");
        json.put("num", "0");
        try {
            con = jdbcTemplate.getDataSource().getConnection();
            //1.设置子表归还日期；
            String sql = "UPDATE dagl_file t " +
                    "   SET t.return_date = '" + DataUtil.getToday() + "'," +
                    "   t.return_time='" + workflow.common.JDateToolkit.getNowDate4() + "'";
            if (StringUtils.isBlank(isAuto)) {//自动归还的，不设置归还经办人id和姓名
                sql += "   ,t.return_handle_user_id = '" + UserUtil.getCruUserId() + "'," +//归还经办人id
                        "   t.return_handle_user_name = '" + UserUtil.getCruUserName() + "'";//归还经办人的name
            }
            sql += "   WHERE t.id = ?";

            int num = jdbcTemplate.update(sql,id);

            //2.判断子表借阅状态，设置主表为已归还或者未归还；

            String sql1 = "select *\n" +
                    "  from dagl_file t WHERE t.id=?";
            List<DaglFile> daglFile = jdbcTemplate.query(sql1, new BeanPropertyRowMapper<>(DaglFile.class),id);
            //DaglFile daglFile = daglFileDao.findOne(id);//子表数据
            if (daglFile.size() > 0) {
                String sql2 = "select t.* from dagl_borrow t WHERE t.id = '" + daglFile.get(0).getBorrowId() + "'";
                List<DaglBorrow> daglBorrow = jdbcTemplate.query(sql2, new BeanPropertyRowMapper<>(DaglBorrow.class));
                //DaglBorrow daglBorrow = daglBorrowDao.findOne(daglFile.get(0).getBorrowId());//查询主表的数据
                if (daglBorrow.size() > 0) {
                    List<DaglFile> daglFiles = getFileList(daglBorrow.get(0));//查询所有的子表数据

                    int notReturnNum = 0;
                    for (int i = 0; i < daglFiles.size(); i++) {//遍历子表数据是否含有未归还的档案
                        if (null == daglFiles.get(i).getReturnDate() && !(id.equals(daglFiles.get(i).getId()))) {
                            notReturnNum++;
                        }
                    }
                    String updateSql = "";
                    if (0 == notReturnNum) {
                        updateSql = "UPDATE dagl_borrow t SET t.borrow_status = '3' WHERE t.id = '" + daglBorrow.get(0).getId() + "'";
                        json.put("num", "1");
                        //daglBorrow.get(0).setBorrowStatus("3");//已归还
                    } else if (0 < notReturnNum && notReturnNum < daglFiles.size()) {
                        updateSql = "UPDATE dagl_borrow t SET t.borrow_status = '2' WHERE t.id = '" + daglBorrow.get(0).getId() + "'";
                        //daglBorrow.get(0).setBorrowStatus("2");//部分归还
                    } else {
                        updateSql = "UPDATE dagl_borrow t SET t.borrow_status = '1' WHERE t.id = '" + daglBorrow.get(0).getId() + "'";
                        //daglBorrow.get(0).setBorrowStatus("1");//未归还；当然这里这种情况不应该存在的，既然归还了一个，至少为部分归还
                    }
                    jdbcTemplate.execute(updateSql);//修改父表的状态
                    //daglBorrowDao.save(daglBorrow.get(0));

                    //3.调用接口，调整档案（子表数据）在档案库的借阅状态：
                    if (null != daglFile.get(0).getTableName()) {//排除线下借阅不在数据库的数据
                        bmglService.returnDangAnToChangeStatus(daglFile.get(0).getRecid(), daglFile.get(0).getTableName());
                    }
                    transactionManager.commit(transactionStatus);
                    json.put("flag", "1");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                //回滚事务
                transactionManager.rollback(transactionStatus);
                json.put("num", "0");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return json;
    }

    /**
     * @return net.sf.json.JSONObject
     * @Author 王富康
     * @Description //TODO 催还
     * @Date 2019/3/4 17:02
     * @Param [id-借阅主表的id, pressContent-催还内容]
     **/
    @Override
    public JSONObject returnPress(String id, String pressContent) {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            DaglFile daglFile = daglFileDao.findOne(id);

            //修改催还的状态
            daglFile.setIsReminder("1");
            daglFileDao.save(daglFile);

            //发送消息
            NotityMessage notityMessage = new NotityMessage();
            notityMessage.setSenderId(ConfigConsts.SYSTEM_ID);  //系统ID
            notityMessage.setSubject("请及时归还【" + daglFile.getMainTitle() + "】档案！"); //消息主题
            notityMessage.setContent(pressContent); //消息内容
            notityMessage.setAccepterId(daglFile.getBorrowUserId());  //接收人ID
            notityMessage.setStatus("0");  //0未读；1已读
            notityMessage.setType("3");  //3站内消息
            //发送消息
            notifyController.save(notityMessage);

            //催办记录
            BorrowUrge borrowUrge = new BorrowUrge();
            borrowUrge.setBorrowId(daglFile.getId());
            borrowUrge.setBorrowUserId(daglFile.getBorrowUserId());
            borrowUrge.setUrgeContent(pressContent);
            borrowUrge.setBorrowUserName(daglFile.getBorrowUserName());
            //保存催还信息
            borrowUrgeService.addUrgeInfo(borrowUrge);
            json.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
        }


        return json;
    }

    /**
     * @return net.sf.json.JSONObject
     * @Author 王富康
     * @Description //TODO 办理借出
     * @Date 2019/3/5 17:00
     * @Param [id-借阅主表的id, borrowDate-借阅日期, shouldReturnDate-归还日期]
     **/
    @Override
    public JSONObject toConductBorrow(String id, String borrowDate, String shouldReturnDate) {

        // 定义事务隔离级别，传播行为
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //事务状态类，通过PlatformTransactionManager的getTransaction方法根据事务定义获取；获取事务状态后，Spring根据传播行为来决定如何开启事务
        TransactionStatus transactionStatus = transactionManager.getTransaction(def);

        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            con = jdbcTemplate.getDataSource().getConnection();

            //添加流水号
            //获取当年年份
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            String querySerialNumberSql = "select t.* from dagl_borrow t where t.serial_number like '" + year + "%'";
            List<DaglBorrow> daglBorrows = jdbcTemplate.query(querySerialNumberSql, new BeanPropertyRowMapper<>(DaglBorrow.class));

            String str = String.format("%04d", daglBorrows.size() + 1);
            String serialNumber = year + "-" + str;

            //1.设置主表表借阅日期和应还日期,默认状态为1（未归还）,流水号,经办人id，经办人姓名；
            String sql = "UPDATE dagl_borrow t " +
                    "   SET t.borrow_date = ?," +
                    "   t.should_return_date = ?," +
                    "   t.borrow_status='1'," +
                    "   t.serial_number=?, " +
                    "   t.handle_user_id = '" + UserUtil.getCruUserId() + "'," +
                    "   t.handle_user_name = '" + UserUtil.getCruUserName() + "'" +
                    "   WHERE t.id = ?";
            int num = jdbcTemplate.update(sql,borrowDate,shouldReturnDate,serialNumber,id);

            //2.设置子表表借阅日期和应还日期,经办人id，经办人姓名；
            String sql1 = "UPDATE dagl_file t " +
                    "       SET t.borrow_date = ?," +
                    "       t.should_return_date = ?, " +
                    "       t.handle_user_id = '" + UserUtil.getCruUserId() + "'," +
                    "       t.handle_user_name = '" + UserUtil.getCruUserName() + "'" +
                    "       WHERE t.borrow_id = ?";
            int num1 = jdbcTemplate.update(sql1,borrowDate,shouldReturnDate,id);

            //查询子表数据，遍历调整档案（子表数据）在档案库的借阅状态：
            DaglBorrow daglBorrow = new DaglBorrow();
            daglBorrow.setId(id);
            List<DaglFile> daglFiles = getFileList(daglBorrow);

            for (int i = 0; i < daglFiles.size(); i++) {
                if (null != daglFiles.get(i).getTableName()) {//排除线下不在数据库的数据
                    JSONObject returnJson = bmglService.borrowDangAnToChangeStatus(daglFiles.get(i).getRecid(), daglFiles.get(i).getTableName());
                    boolean result = Boolean.parseBoolean(returnJson.get("flag").toString());
                    if (result) {
                        //修改子表的当前档案对应的【案卷】数，当前档案对应的【分卷】数，当前档案对应的【卷内】数
                        String anjuan = returnJson.get("anjuan") == null ? "0" : returnJson.get("anjuan").toString();
                        String fenjuan = returnJson.get("fenjuan") == null ? "0" : returnJson.get("fenjuan").toString();
                        String juannei = returnJson.get("juannei") == null ? "0" : returnJson.get("juannei").toString();

                        String borrowNum = "0";//本次借阅的数量，统计最底层的数量。
                        if(!"0".equals(fenjuan)){//分卷不为空，则统计分卷的数量
                            borrowNum = fenjuan;
                        }else if(!"0".equals(juannei)){//卷内不为空，则统计卷内的数量
                            borrowNum = juannei;
                        }else if(!"0".equals(anjuan)){//案卷不为空，则统计案卷的数量
                            borrowNum = anjuan;
                        }
                        String sql2 = "UPDATE dagl_file t " +
                                "       SET t.anjuan = '" + anjuan + "'," +
                                "       t.fenjuan = '" + fenjuan + "', " +
                                "       t.juannei = '" + juannei + "'," +
                                "       t.borrow_num = '"+borrowNum+"'" +
                                "       WHERE t.id = '" + daglFiles.get(i).getId() + "'";
                        int num2 = jdbcTemplate.update(sql2);
                    } else {
                        throw new Exception("");
                    }

                }

            }
            //事务提交
            transactionManager.commit(transactionStatus);
            json.put("flag", "1");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                //回滚事务
                transactionManager.rollback(transactionStatus);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return json;

    }

    /**
     * @Auther:邴秀慧
     * @Description:按次查询已借的记录
     * @Date:2019/3/8 15:05
     */
    @Override
    public PageImpl getRecordPerList(Pageable pageable, PageImpl pageImpl, DaglBorrow daglBorrow) throws Exception {
        pageImpl.setFlag("0");
        StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        querySql.append("	from DaglBorrow t where t.shouldReturnDate is not null");
        querySql.append("	and t.visible = '" + CommonConstants.VISIBLE[1] + "'");
        //借阅日期
        if (StringUtils.isNotBlank(daglBorrow.getBorrowDate())) {
            String startBorrowDate = daglBorrow.getBorrowDate().substring(0, (daglBorrow.getBorrowDate().length() + 1) / 2 - 1).trim();
            String endBorrowDate = daglBorrow.getBorrowDate().substring((daglBorrow.getBorrowDate().length() + 1) / 2, daglBorrow.getBorrowDate().length()).trim();
            querySql.append("   and borrowDate >= ?");
            querySql.append("   and borrowDate <= ?");
            para.add(startBorrowDate);
            para.add(endBorrowDate);
        }
        //应还日期
        if (StringUtils.isNotBlank(daglBorrow.getShouldReturnDate())) {
            String startShouldReturnDate = daglBorrow.getShouldReturnDate().substring(0, (daglBorrow.getShouldReturnDate().length() + 1) / 2 - 1).trim();
            String endShouldReturnDate = daglBorrow.getShouldReturnDate().substring((daglBorrow.getShouldReturnDate().length() + 1) / 2, daglBorrow.getShouldReturnDate().length()).trim();
            querySql.append("   and shouldReturnDate >= ?");
            querySql.append("   and shouldReturnDate <= ?");
            para.add(startShouldReturnDate);
            para.add(endShouldReturnDate);
        }
        //借阅方式：subflag等于5线上借阅，subflag等于00空传统借阅(00是为了区分没有查询条件和查询条件传递了但是选择的是传统借阅)
        if (StringUtils.isNotBlank(daglBorrow.getSubflag())) {
            if (daglBorrow.getSubflag().equals("00")) {
                querySql.append("   and t.subflag is null");
            } else {
                querySql.append("   and t.subflag = ?");
                para.add(daglBorrow.getSubflag());
            }
        }
        //借阅类型
        if (StringUtils.isNotBlank(daglBorrow.getFlowType())) {
            querySql.append("   and t.flowType = ?");
            para.add(daglBorrow.getFlowType());
        }
        //借阅状态
        if (StringUtils.isNotBlank(daglBorrow.getBorrowStatus())) {
            querySql.append("   and t.borrowStatus = ?");
            para.add(daglBorrow.getBorrowStatus());
        }
        //审批单位
        if (StringUtils.isNotBlank(daglBorrow.getApproveUnitName())) {
            querySql.append("   and t.approveUnitName like ?");
            para.add("%" + daglBorrow.getApproveUnitName() + "%");
        }
        //审批人
        if (StringUtils.isNotBlank(daglBorrow.getApproveUserName())) {
            querySql.append("   and t.approveUserName like ?");
            para.add("%" + daglBorrow.getApproveUserName() + "%");
        }
        //经办人
        if (StringUtils.isNotBlank(daglBorrow.getHandleUserName())) {
            querySql.append("   and t.handleUserName like ?");
            para.add("%" + daglBorrow.getHandleUserName() + "%");
        }
        //流水号
        if (StringUtils.isNotBlank(daglBorrow.getSerialNumber())) {
            querySql.append("   and t.serialNumber like ?");
            para.add("%" + daglBorrow.getSerialNumber() + "%");
        }
        //利用目的
        if (StringUtils.isNotBlank(daglBorrow.getUsePurpose())) {
            querySql.append("   and t.usePurpose = ?");
            para.add(daglBorrow.getUsePurpose());
        }
        //拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            querySql.append("  order by t.serialNumber desc");
        } else {
            querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + "");
        }

        Page<DaglBorrow> page = daglBorrowDao.query(querySql.toString(), pageable, para.toArray());
        //草稿列表，添加操作列
        List<DaglBorrow> content = page.getContent();
        for (DaglBorrow daglBorrowTemp : content) {
            if (ConfigConsts.START_FLAG.equals(daglBorrowTemp.getSubflag())) {
                daglBorrowTemp.setCz(CommonConstants.OPTION_UPDATE + "," + CommonConstants.OPTION_DELETE);
            }
        }
        pageImpl.setFlag("1");
        pageImpl.getData().setRows(content);
        pageImpl.getData().setTotal((int) page.getTotalElements());
        return pageImpl;
    }

    /**
     * @Author 王富康
     * @Description //TODO 归档库删除档案信息时，根据recid和表名删除所有档案信息
     * @Date 2019/4/11 9:41
     * @Param [recid, tableName]
     * @return int
     **/
    @Override
    public int deleteDaglFileByTNameAndRecid(String recid, String tableName) {
        List<Object> para = new ArrayList<>();
        para.add(CommonConstants.VISIBLE[0]);
        String inString = CommonUtils.solveSqlInjectionOfIn(recid,para);
        para.add(tableName);
        String fileSql = "update DaglFile t set t.visible = ? where t.recid in ("+inString+") and t.tableName = ?";
        int num = daglFileDao.update(fileSql,para.toArray());
        return num;
    }

}
