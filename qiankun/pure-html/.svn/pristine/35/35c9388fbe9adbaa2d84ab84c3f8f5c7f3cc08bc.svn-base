package com.sinosoft.sinoep.modules.system.config.holidayset.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;;

/**
 * 用于包装日历上每个日期的类
 * TODO 
 * @author 马秋霞
 * @Date 2018年5月10日 下午5:52:06
 */
public class DayStatus {

    private boolean isWorkDay = false;//是否工作日
    private String day = "";//当前天序列
    private String month="";
    private String year="";
    private boolean isValidate=false;
    private boolean toDay = false;
    private boolean isCurMonth = false;
    private boolean isUsed = false;
    private String time_id = "";
    private String hasData = ""; //在节假日设置数据库里是否有数据（1表示有数据，没数据为空串）
    private String isHoliday;//该天是否为工作日
	public boolean getToDay() {
		return toDay;
	}

	public void setToDay(boolean toDay) {
		this.toDay = toDay;
	}
	
	public boolean getIsCurMonth() {
		return isCurMonth;
	}

	public void setCurMonth(boolean isCurMonth) {
		this.isCurMonth = isCurMonth;
	}
	
    public String getHasData() {
		return hasData;
	}
    
	public String getIsHoliday() {
		return isHoliday;
	}

	public void setIsHoliday(String isHoliday) {
		this.isHoliday = isHoliday;
	}

	public void setHasData(String hasData) {
		this.hasData = hasData;
	}

	//添加代码开始 冯旭星 2015-4-19 17:12:16
    private String workType = "";

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}
	//添加代码结束 冯旭星 2015-4-19 17:12:37


     /**
      * 构造函数,初始化一个实例
     * @param year 年
     * @param month 月
     * @param day 日
     */
    public DayStatus(HttpServletRequest req, int year,int month,int day) {
        this.year=String.valueOf(year);
        this.month=String.valueOf(month);
        if (day == 0) {
            this.day = "0";
            this.isWorkDay = false;
            this.isValidate=false;
         } else {
            this.day = String.valueOf(day);
            //this.calWorkDay();//查询数据库设置是否为工作日
            this.isValidate=true;
       
            
        }
    }
    

    public String getDay() {
        return this.day;
    }

    /**
     * Desc:返回完整日期
     *
     * @return 完整日期
     * @author boril
     * @LastModifyTime:9:48:11
     */
    public String getFullDay() {
        Calendar cal=this.getCalendar();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    /**
     * Desc:判断是否当前天
     *
     * @return
     * @author boril
     * @LastModifyTime:9:51:57
     */
    public boolean isToday() {
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        String nowtime = df.format(now).toString();

        if (this.day.equals(nowtime))
            return true;
        else
            return false;
    }
    
    /**
     * @return 返回 isWorkDay。
     */
    public boolean isWorkDay() {
        return isWorkDay;
    }
    
    /**
     * @param isWorkDay 要设置的 isWorkDay。
     */
    public void setWorkDay(boolean isWorkDay) {
        this.isWorkDay = isWorkDay;
    }
    
    private Calendar getCalendar(){
        Calendar cal=Calendar.getInstance();
        cal.set(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
        return cal;
    }
    
    /**
     * @return 返回 isValidate。
     */
    public boolean isValidate() {
        return isValidate;
    }
    /**
     * @param isValidate 要设置的 isValidate。
     */
    public void setValidate(boolean isValidate) {
        this.isValidate = isValidate;
    }
    
    public static void main (String arg[]){
        Calendar cal=Calendar.getInstance();
        cal.set(2005,10,5);
        
    }
    
    /** 考勤修改 开始 2015-5-8 14:37:43*/
    /**
     * Desc:计算否为工作日
     * @author boril
     * @LastModifyTime:11:07:16
     */

    /** 考勤修改 结束 2015-5-8 14:37:43*/


	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public String getTime_id() {
		return time_id;
	}

	public void setTime_id(String time_id) {
		this.time_id = time_id;
	}
}
