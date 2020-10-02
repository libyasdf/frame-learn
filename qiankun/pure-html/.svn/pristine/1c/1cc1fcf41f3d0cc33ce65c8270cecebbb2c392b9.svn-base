package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * TODO 考勤导入处理视图
 * @author 冯超
 * @Date 2018年6月1日 上午9:28:13
 */
@Entity
@Table(name = "kqgl_attendence_handle")
public class KqglAttendenceHandle {
	
	/*主键*/
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	/*卡号*/
	@Column(name = "card_number")
	private String cardNumber;
	
	/*年月*/
	@Column(name = "import_month")
	private String importMonth;
	
	/*日期*/
	@Column(name = "import_date")
	private String importDate;
	
	/*签入时间*/
	@Column(name = "qr_time")
	private String qrTime;
	
	/*签出时间*/
	@Column(name = "qc_time")
	private String qcTime;
	
	/*考勤状态*/
	@Transient
	private String attendanceState;
	
	/*姓名*/
	@Transient
	private String name;
	/*用户所在部门*/
	@Transient
	private String deptName;

	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardNumber() {
		return cardNumber;
	}
	
	public String getAttendanceState() {
		return attendanceState;
	}



	public void setAttendanceState(String attendanceState) {
		this.attendanceState = attendanceState;
	}



	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getImportMonth() {
		return importMonth;
	}

	public void setImportMonth(String importMonth) {
		this.importMonth = importMonth;
	}

	public String getImportDate() {
		return importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

	public String getQrTime() {
		return qrTime;
	}

	public void setQrTime(String qrTime) {
		this.qrTime = qrTime;
	}

	public String getQcTime() {
		return qcTime;
	}

	public void setQcTime(String qcTime) {
		this.qcTime = qcTime;
	}
	

}
