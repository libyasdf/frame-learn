package com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.services;

import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.system.component.importFile.util.ExcelReaderUtil;
import com.sinosoft.sinoep.modules.system.component.importFile.util.ImportFileUtil;
import com.sinosoft.sinoep.modules.zhbg.zbgl.config.entity.Config;
import com.sinosoft.sinoep.modules.zhbg.zbgl.config.services.ConfigService;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.dao.DutyTableDao;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.entity.DutyTable;
import com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.vo.userDprbVo;
import com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.entity.ShangBao;
import com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.services.ShangBaoService;
import com.sinosoft.sinoep.user.service.UserInfoService;
import com.sinosoft.sinoep.user.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import workflow.common.JDateToolkit;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author 王富康
 * @Description //TODO 值班表实现层
 * @Date 2018/7/11 15:29
 **/
@Service
public class DutyTableServiceImpl implements DutyTableService{

    @Autowired
    private DutyTableDao dutyTableDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private ShangBaoService shangBaoService;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * @Author 王富康
     * @Description //TODO 值班表列表查询
     * @Date 2018/7/17 16:53
     * @Param [dut]
     * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.entity.DutyTable>
     **/
    public List<DutyTable> getList(DutyTable dut){


        String sql="select t.* from zbgl_duty_detail t where t.visible = "+CommonConstants.VISIBLE[1];

        //根据上报表查询是传上报表id
        if(StringUtils.isNotBlank(dut.getReportId())){
            sql +=" and t.report_id ='"+dut.getReportId()+"'";
        }

        //根据值班工作通知查询时传值班通知的id
        if (StringUtils.isNotBlank(dut.getZbNoticeId())) {
            sql +=" and t.zb_notice_id ='"+dut.getZbNoticeId()+"'";
        }

        if (StringUtils.isNotBlank(dut.getWatchDate())) {
            sql +=" and t.watch_date = '"+dut.getWatchDate()+"'";
        }

        if (StringUtils.isNotBlank(dut.getWatchName())) {
            sql +=" and t.watch_name like '%"+dut.getWatchName()+"%'";
        }
        sql +=" order by t.watch_Date Asc,decode(t.classes,'早',1,'中',2,'晚',3,'昼',4,'夜',5) ";
        log.info(sql);
        List<DutyTable> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DutyTable.class));
        return list;
    }

    /**
     * @Author 王富康
     * @Description //TODO 总值班室值班查询，值班查询接口
     * @Date 2018/7/31 14:53
     * @Param [dut]
     * @return java.util.List<com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.entity.DutyTable>
     **/
    @Override
    public PageImpl getListOfDutySearch(Pageable pageable, PageImpl pageImpl, DutyTable dut) {

        StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();
        querySql.append(" from DutyTable t where t.visible = "+CommonConstants.VISIBLE[1]+" and  t.reportId != null ");

        // querySql.append("unitId="+UserUtil.getCruChushiId());//本部门的
        //拼接条件
        if (!(StringUtils.isNotBlank(dut.getWatchDate()))){
            //获取今天的日期
            Date day=new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String today = df.format(day);
            querySql.append(" and t.watchDate = ?");
            para.add(""+today+"");

            //根据月份查询出基础配置的单位
            SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM");
            String month = dff.format(day);
            List<Config> cf = configService.queryOne(month);
            String reportIds ="";
            if(cf.size()>0){
                //得到基础配置的单位的上报表的数据
                List<ShangBao> sb = shangBaoService.queryOneByMonthAndUnitId(month, cf.get(0).getUnitId());

                for(int i = 0;i<sb.size();i++){
                    reportIds += "'"+sb.get(i).getId()+"',";
                }
            }
            //去掉最后一个逗号
            if(reportIds.length()>0){
                int n = reportIds.length();
                reportIds = reportIds.substring(0,reportIds.length()-1);
                querySql.append(" and t.reportId in ( "+reportIds+" )");
            }else{
                querySql.append(" and t.reportId in ('')");
            }
        }

        //选择日期后根据日期查询当日的值班情况
        if (StringUtils.isNotBlank(dut.getWatchDate())) {
            querySql.append(" and t.watchDate = ?");
            para.add(""+dut.getWatchDate()+"");

            //根据月份查询出基础配置的单位
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM");
            Date day =null;
            try {
                day = df.parse(dut.getWatchDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String month = dff.format(day);
            List<Config> cf = configService.queryOne(month);
            String reportIds ="";
            if(cf.size()>0){
                //得到基础配置的单位的上报表的数据
                List<ShangBao> sb = shangBaoService.queryOneByMonthAndUnitId(month, cf.get(0).getUnitId());

                for(int i = 0;i<sb.size();i++){
                    reportIds += "'"+sb.get(i).getId()+"',";
                }
            }
            //去掉最后一个逗号
            if(reportIds.length()>0){
                int n = reportIds.length();
                reportIds = reportIds.substring(0,reportIds.length()-1);
                querySql.append(" and t.reportId in ( "+reportIds+" )");
            }else{
                querySql.append(" and t.reportId in ('')");
            }
        }

        //部门
        if(StringUtils.isNotBlank(dut.getCreJuId())){
            querySql.append(" and t.creJuId = ?");
            para.add(""+dut.getCreJuId()+"");
        }

        //处室
        if (StringUtils.isNotBlank(dut.getCreChushiId())) {
            querySql.append(" and t.creChushiId = ?");
            para.add(""+dut.getCreChushiId()+"");
        }
        //拼接排序语句
        querySql.append("  order by creJuName desc, creChushiName desc) ");

        Page<DutyTable> page = dutyTableDao.query(querySql.toString(), pageable,para.toArray());
        return new PageImpl((int)page.getTotalElements(),page.getContent());
    }

    /**
     * @Author 王富康
     * @Description //TODO 新增值班信息
     * @Date 2018/7/30 11:59
     * @Param [dut, ideaName]
     * @return com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.entity.DutyTable
     **/
    public DutyTable save(DutyTable dut){

        DutyTable dutyTable = new DutyTable();
        dutyTable.setCreUserId(UserUtil.getCruUserId());
        dutyTable.setCreUserName(UserUtil.getCruUserName());
        dutyTable.setCreDeptId(UserUtil.getCruDeptId());
        dutyTable.setCreDeptName(UserUtil.getCruDeptName());
        dutyTable.setCreChushiId(UserUtil.getCruChushiId());
        dutyTable.setCreChushiName(UserUtil.getCruChushiName());
        dutyTable.setCreJuId(UserUtil.getCruJuId());
        dutyTable.setCreJuName(UserUtil.getCruJuName());
        dutyTable.setVisible(CommonConstants.VISIBLE[1]);
        dutyTable.setCreTime(JDateToolkit.getNowDate4());

        dutyTable.setWatchDate(dut.getWatchDate());
        dutyTable.setWeekday(dut.getWeekday());
        dutyTable.setClasses(dut.getClasses());
        dutyTable.setWatchName(dut.getWatchName());
        dutyTable.setPhone(dut.getPhone());
        dutyTable.setShiftLeaderName(dut.getShiftLeaderName());
        dutyTable.setShiftLeaderPhone(dut.getShiftLeaderPhone());
        dutyTable.setCommonPhone(dut.getCommonPhone());//普网电话
        dutyTable.setPrivatePhone(dut.getPrivatePhone());//公网电话
        dutyTable.setReportId(dut.getReportId());
        dutyTable.setIsSecurity(dut.getIsSecurity());//是否安保值班
        dutyTable.setZbNoticeId(dut.getZbNoticeId());//值班工作通知的id
        dut = dutyTableDao.save(dutyTable);
        return dut;
    }

    /**
     * @Author 王富康
     * @Description //TODO 保存list数据
     * @Date 2018/8/7 10:43
     * @Param [dut, privatePone, commonPhone, isSecurity, zbNoticeId]
     * @return net.sf.json.JSONObject
     **/
    public List<DutyTable> saveList(List<DutyTable> dut, String privatePhone, String commonPhone, String isSecurity, String zbNoticeId){

        List<DutyTable> dutyTableList = new ArrayList<>();

        //加入普网专网电话，上报id，是否安保值班，等字段
        for(int i = 0;i < dut.size();i++){
            if(StringUtils.isNotBlank(dut.get(i).getId())){
                //id不为空，证明为修改
                DutyTable oldDt = dutyTableDao.findOne(dut.get(i).getId());

                oldDt.setCommonPhone(commonPhone);
                oldDt.setPrivatePhone(privatePhone);

                oldDt.setWatchDate(dut.get(i).getWatchName());
                oldDt.setPhone(dut.get(i).getPhone());
                oldDt.setShiftLeaderName(dut.get(i).getShiftLeaderName());
                oldDt.setShiftLeaderPhone(dut.get(i).getShiftLeaderPhone());

                dutyTableDao.save(oldDt);
            }else{
                //id为空，则证明是新增
                dut.get(i).setCreUserId(UserUtil.getCruUserId());
                dut.get(i).setCreUserName(UserUtil.getCruUserName());
                dut.get(i).setCreDeptId(UserUtil.getCruDeptId());
                dut.get(i).setCreDeptName(UserUtil.getCruDeptName());
                dut.get(i).setCreChushiId(UserUtil.getCruChushiId());
                dut.get(i).setCreChushiName(UserUtil.getCruChushiName());
                dut.get(i).setCreJuId(UserUtil.getCruJuId());
                dut.get(i).setCreJuName(UserUtil.getCruJuName());
                dut.get(i).setVisible(CommonConstants.VISIBLE[1]);
                dut.get(i).setCreTime(JDateToolkit.getNowDate4());

                dut.get(i).setIsSecurity(isSecurity);//是否安保值班
                dut.get(i).setZbNoticeId(zbNoticeId);

                dut.get(i).setPrivatePhone(privatePhone);
                dut.get(i).setCommonPhone(commonPhone);

                DutyTable newDut = dutyTableDao.save(dut.get(i));

                dutyTableList.add(newDut);
            }

        }

        return dutyTableList;
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据id获取一条值班信息
     * @Date 2018/7/30 12:10
     * @Param [id]
     * @return com.sinosoft.sinoep.modules.zhbg.zbgl.dutytable.entity.DutyTable
     **/
    @Override
    public DutyTable getDutyTableById(String id) {
        return dutyTableDao.findOne(id);
    }

    /**
     * @Author 王富康
     * @Description //TODO 修改值班表信息
     * @Date 2018/7/17 18:04
     * @Param [dut]
     * @return int
     **/
    public int updateDutyTable(DutyTable dut) {

        String jpql ="update DutyTable t set t.watchDate = ?,watchId=?,watchName=?,classes=?,phone=?,shiftLeaderId=?,shiftLeaderName=?,shiftLeaderPhone=? where id = ?";
        int updateDuty = dutyTableDao.update(jpql, dut.getWatchDate(),dut.getWatchId(),dut.getWatchName(),dut.getClasses(),dut.getPhone(),dut.getShiftLeaderId(),dut.getShiftLeaderName(),dut.getShiftLeaderPhone(),dut.getId());
        return updateDuty;
    }

    /**
     * @Author 王富康
     * @Description //TODO 修改值班表信息(普网电话，专网电话)
     * @Date 2018/7/17 18:04
     * @Param [dut]
     * @return int
     **/
    public int updateDutyTablePhone(String id, String commonPhone, String privatePhone) {

        String jpql = "update DutyTable t set t.commonPhone=?,t.privatePhone=? where t.reportId=? and t.visible="+CommonConstants.VISIBLE[1];
        int updateDuty = dutyTableDao.update(jpql, commonPhone, privatePhone, id);
        return updateDuty;
    }

    /**
     * @Author 王富康
     * @Description //TODO 导入excel考勤表数据
     * @Date 2018/7/19 20:10
     * @Param [file]
     * @return java.lang.String
     **/
    public String importInfo(String filePath, MultipartFile fileToUpload,String id) throws IOException {

        String msg = "导入失败！";
        try {
             ExcelReaderUtil readExcel = new ExcelReaderUtil(fileToUpload.getInputStream(),fileToUpload.getOriginalFilename());
             readExcel.setSheetNum(0); // 设置读取索引为0的工作表

            //这里是为了格式化手机号，获取excel的行数据
            InputStream is = fileToUpload.getInputStream();
            Workbook workbook = null;
            workbook = ImportFileUtil.getWorkBook(is, filePath);
            Sheet sheet = workbook.getSheetAt(0);//下角标为0的sheet页数据
            List<DutyTable> list = new ArrayList<>();
            /*这里为循环校验数据*/
            int errorCount = 0;
            String errContent = "";
            String errContentCommon = "";
            String errContentPrivata = "";
             for (int i = 1; i < readExcel.getRowCount()+1; i++) {

                 Row row = sheet.getRow(i);//得到第i行数据，用来获取手机号
                 //库名,先判断是否在合并单元格内，若有则取合并单元格的值
                 //String dataName=readExcel.getCellValue(i, 0).trim();//序号
                 String watchDate=readExcel.getCellValue(i, 1).trim();//值班日期
                 String weekday=readExcel.getCellValue(i, 2).trim();//星期
                 String classes=readExcel.getCellValue(i, 3).trim();//班次
                 String watchName=readExcel.getCellValue(i, 4).trim();//值班员
                 String shiftLeaderName = readExcel.getCellValue(i, 6).trim();//带班领导
                 String commonPhone = readExcel.getCellValue(i, 8).trim();//带班领导电话
                 String privatePhone = readExcel.getCellValue(i, 9).trim();//带班领导电话

                 //创建值班表对象
                 DutyTable dutyTable = new DutyTable();
                 dutyTable.setCreUserId(UserUtil.getCruUserId());
                 dutyTable.setCreUserName(UserUtil.getCruUserName());
                 dutyTable.setCreDeptId(UserUtil.getCruDeptId());
                 dutyTable.setCreDeptName(UserUtil.getCruDeptName());
                 dutyTable.setCreChushiId(UserUtil.getCruChushiId());
                 dutyTable.setCreChushiName(UserUtil.getCruChushiName());
                 dutyTable.setCreJuId(UserUtil.getCruJuId());
                 dutyTable.setCreJuName(UserUtil.getCruJuName());
                 dutyTable.setVisible(CommonConstants.VISIBLE[1]);
                 dutyTable.setCreTime(JDateToolkit.getNowDate4());
                 //导入
                 dutyTable.setWatchDate(watchDate);
                 dutyTable.setWeekday(weekday);
                 dutyTable.setClasses(classes);

                 if(StringUtils.isNotBlank(watchName)){
                     //不为空
                     dutyTable.setWatchName(watchName);
                 }else{
                     errorCount++;
                     errContent += watchDate+"值班员不能为空！<br/>";
                 }

                 //手机号
                 String phone = "";
                 DecimalFormat df = new DecimalFormat("#");
                 String mobileStr="";
                 if(StringUtils.isNotBlank(readExcel.getCellValue(i, 5).trim())){
                     //不为空
                     //String mobileStr = readExcel.getCellValue(i, 5).trim();

                     try{
                          mobileStr = df.format(row.getCell(5).getNumericCellValue());
                         if(isMobileNO(mobileStr)){
                             phone = df.format(row.getCell(5).getNumericCellValue());
                             dutyTable.setPhone(phone);
                         }else{
                             errorCount++;
                             errContent += watchDate+"值班员手机号格式不正确！<br/>";
                         }
                     }catch (Exception ex){
                         errorCount++;
                         errContent += watchDate+"值班员手机号格式不正确！<br/>";
                     }
                 }else{
                     errorCount++;
                     errContent += watchDate+"值班员手机号不能为空！<br/>";
                     //为空，就直接存""
                 }

                 if(StringUtils.isNotBlank(shiftLeaderName)){
                     //不为空
                     dutyTable.setShiftLeaderName(shiftLeaderName);
                 }else{
                     errorCount++;
                     errContent += watchDate+"值班领导不能为空！<br/>";
                 }

                 //手机号
                 String shiftLeaderPhone = "";
                 if(StringUtils.isNotBlank(readExcel.getCellValue(i, 7).trim())){
                     //不为空
                     //String mobileStr = readExcel.getCellValue(i, 7).trim();
                     try{
                         mobileStr = df.format(row.getCell(7).getNumericCellValue());
                         if(isMobileNO(mobileStr)){
                             shiftLeaderPhone = df.format(row.getCell(7).getNumericCellValue());
                             dutyTable.setShiftLeaderPhone(shiftLeaderPhone);
                         }else{
                             errorCount++;
                             errContent += watchDate+"带班领导手机号格式不正确！<br/>";
                         }
                     }catch (Exception e){
                         errorCount++;
                         errContent += watchDate+"带班领导手机号格式不正确！<br/>";
                     }

                 }else{
                     errorCount++;
                     errContent += watchDate+"带班领导手机号不能为空！<br/>";
                     //为空，就直接存""
                 }

                 //普网电话的校验
                 if(StringUtils.isNotBlank(commonPhone)){
                     try{
                         BigDecimal maxlng1 = new BigDecimal(commonPhone);

                         double minlat = maxlng1.doubleValue();
                         commonPhone = (new Double(minlat)).intValue()+"";
                         if(isPhoneNO(commonPhone)){
                             dutyTable.setCommonPhone(commonPhone);//普网电话
                         }else{
                             errorCount++;
                             errContentCommon = "普网电话格式不正确！<br/>";
                         }
                     }catch (Exception e){
                         errorCount++;
                         errContentCommon = "普网电话格式不正确！<br/>";
                     }

                 }else{
                     errorCount++;
                     errContentCommon = "普网电话不能为空！<br/>";
                     //为空，就直接存""
                 }

                 //专网电话的校验
                 if(StringUtils.isNotBlank(privatePhone)){

                     try{
                         BigDecimal maxlng1 = new BigDecimal(privatePhone);

                         double minlat = maxlng1.doubleValue();
                         privatePhone = (new Double(minlat)).intValue()+"";
                         if(isPhoneNO(privatePhone)){
                             dutyTable.setPrivatePhone(privatePhone);//专网电话
                         }else{
                             errorCount++;
                             errContentPrivata = "专网电话格式不正确！<br/>";
                         }
                     }catch (Exception e){
                         errorCount++;
                         errContentPrivata = "专网电话格式不正确！<br/>";
                     }

                 }else{
                     errorCount++;
                     errContentPrivata = "专网电话不能为空！<br/>";
                     //为空，就直接存""
                 }

                 dutyTable.setReportId(id);

                 list.add(dutyTable);
                 }
                    errContent += errContentCommon;//普网电话的错误信息
                    errContent += errContentPrivata;//专网电话的错误信息
                 if(errorCount>0){
                    return errContent;
                 }else{
                     //校验成功之后删除之前的数据
                     delete(id);
                     //删除成功之后，保存新的值班表数据
                     dutyTableDao.save(list);
                     msg = "导入成功！";
                 }

        } catch (Exception e) {
             e.printStackTrace();
         }
        return msg;
    }

    /**
     * @Author 王富康
     * @Description //TODO 删除值班表信息
     * @Date 2018/7/20 11:48
     * @Param [id]
     * @return int
     **/
    @Override
    public int delete(String reportId) {
        String jpql = "update DutyTable t set t.visible = ? where t.reportId = ?";
        return dutyTableDao.update(jpql,CommonConstants.VISIBLE[0], reportId);
    }

    /**
     * @Author 王富康
     * @Description //TODO 查询值班管理员
     * @Date 2018/8/9 15:27
     * @Param []
     * @return net.sf.json.JSONObject
     **/
    @Override
    public JSONObject queryReportAdmin() throws Exception {
        JSONObject json = new JSONObject();
        json.put("flag", "0");
        try {
            String sql =    " select t.id,\n" +
                            "        t.deptid,\n" +
                            "        t.userid,\n" +
                            "        t.user_code,\n" +
                            "        t.usdr_name,\n" +
                            "        t.order_no,\n" +
                            "        t.note,\n" +
                            "        t.auther,\n" +
                            "        t.status,\n" +
                            "        t.time\n" +
                            "   from sys_User_Dprb t\n" +
                            "  where t.id in (select m.u_dept_id\n" +
                            "                   from sys_user_frole m\n" +
                            "                  where m.status = '1'\n" +
                            "                    and m.roleid = (select n.roleid\n" +
                            "                                      from sys_flow_role n\n" +
                            "                                     where n.role_no = 'D121'\n" +
                            "                                       and n.isvisible = '1'\n" +
                            "                                       and n.status = '1'))";
            JSONObject result = userInfoService.getDateBySql(sql);
            List<userDprbVo> list = new ArrayList<>();
            JSONArray arr = result.getJSONArray("data");
            list = JSONArray.toList(arr, userDprbVo.class);
            json.put("flag", "1");
            //JSONArray lists = JSONArray.fromObject(list);
            json.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
            json.put("msg", "解析异常");
        }
        return json;
    }

    /**
     * @Author 王富康
     * @Description //TODO 导入，手机号码的校验
     * @Date 2018/8/22 17:07
     * @Param [mobiles]
     * @return boolean
     **/
    public static boolean isMobileNO(String mobiles) {

        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (mobiles.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(mobiles);
            boolean isMatch = m.matches();
            if (!isMatch) {
                return false;
            }
            return isMatch;
        }
    }

    /**
     * @Author 王富康
     * @Description //TODO 导入，电话号码的校验
     * @Date 2018/8/23 20:25
     * @Param [mobiles]
     * @return boolean
     **/
    public static boolean isPhoneNO(String mobiles) {

        String regex = "^\\d{4}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(mobiles);
            boolean isMatch = m.matches();
            if (!isMatch) {
                return false;
            }
            return isMatch;
    }
}
