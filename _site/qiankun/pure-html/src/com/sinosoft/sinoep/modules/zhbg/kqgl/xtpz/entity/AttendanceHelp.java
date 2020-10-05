package com.sinosoft.sinoep.modules.zhbg.kqgl.xtpz.entity;

import java.util.List;

/**
 * 
 * TODO 考勤导入是否重复帮助类
 * @author 冯超
 * @Date 2018年5月30日 上午10:12:42
 */
public class AttendanceHelp {
	
	/* 导入日期 */
	private String importDateHelp;
	
	/* 导入日期数据库中所有的数据 （用来判别重复）*/
	private List<KqAttendance> KqAttendanceRepeat;
	
	public String getImportDateHelp() {
		return importDateHelp;
	}

	public void setImportDateHelp(String importDateHelp) {
		this.importDateHelp = importDateHelp;
	}

	public List<KqAttendance> getKqAttendanceRepeat() {
		return KqAttendanceRepeat;
	}

	public void setKqAttendanceRepeat(List<KqAttendance> kqAttendanceRepeat) {
		KqAttendanceRepeat = kqAttendanceRepeat;
	}

}
