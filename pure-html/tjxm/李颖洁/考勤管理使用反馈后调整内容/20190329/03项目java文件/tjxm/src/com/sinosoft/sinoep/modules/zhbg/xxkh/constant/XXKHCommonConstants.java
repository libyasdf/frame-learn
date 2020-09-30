package com.sinosoft.sinoep.modules.zhbg.xxkh.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 学习考核常量类
 * @author 王磊
 * @Date 2018年7月28日 下午12:10:06
 */
public class XXKHCommonConstants {
	
	/**
	 *  数据状态：0为草稿，1为已提交，2，已发布
	 *  目前在用（持续维护）：1、试题状态;2、考试发布状态
	 */
	public static final String RECORD_STATE[] = {"0","1","2"};
	
	/**
	 *  职务、职级、涉密等类别（目前暂用模块：考试管理和学习时长统计）
	 *  目前在用：2：职级，5：职务级别
	 */
	public static final String OCCUPATION_TYPE[] = {"'5'","'2'"};
	
	/**
	 *  职务、职级、涉密等类别下的等级（目前暂用模块：考试管理和学习时长统计）
	 */
	public static final String OCCUPATION_LEVEL[] = {"1","2","3"};
	
	/**
	 *  试卷提交状态 0：未交卷，1：已交卷（目前暂用模块：考试管理）
	 */
	public static final String IS_SUBMIT[] = {"0","1"};
	
	/**
	 *  人员状态 0：取消，1：存在
	 */
	public static final String STATUS[] = {"0","1"};
	
	/**
	 * 树类型和业务表对应关系,删除类型时，还需要确认该类型对应的业务表没有可用数据
	 */
	public final static Map<String,String> treeTypeReflectTable = new HashMap<String,String>();   

	static{
		//树类型和资料业务表关系
		treeTypeReflectTable.put("fz", "xxkh_zi_liao"); 
		treeTypeReflectTable.put("bm", "xxkh_zi_liao");
		treeTypeReflectTable.put("zzll", "xxkh_zi_liao");
		treeTypeReflectTable.put("zxpx", "xxkh_zi_liao");//王磊添加在线培训 2019-02-28
		
		//树类型和试题业务表关系
		treeTypeReflectTable.put("fzst","xxkh_question_info");
		treeTypeReflectTable.put("bmst","xxkh_question_info");
		treeTypeReflectTable.put("zzllst","xxkh_question_info");
		
		//树类型和试卷业务表关系
		treeTypeReflectTable.put("fzsj","xxkh_paper_info");
		treeTypeReflectTable.put("bmsj","xxkh_paper_info");
		treeTypeReflectTable.put("zzllsj","xxkh_paper_info");
		treeTypeReflectTable.put("juj","xxkh_paper_info");
		
		//树类型和考试业务表关系
		treeTypeReflectTable.put("fzks","xxkh_test_info");
		treeTypeReflectTable.put("bmks","xxkh_test_info");
		treeTypeReflectTable.put("zzllks","xxkh_test_info");
		treeTypeReflectTable.put("deptks","xxkh_test_info");
	}
	/**
	 *  部门试卷treeType
	 */
	public static final String JUSJ = "juj";
	
	/**
	 *  treeType 0考试:ks;1试卷:sj;2试题:st
	 */
	public static final String TREE_TYPE_STATUS[] = {"ks","sj","st"};
	
	/** 未组卷状态 */
	public static final String ZUJUAN_NO_START = "0";
	
	/** 已组卷状态 */
	public static final String ZUJUAN_YES_START = "1";
	
	/** 学习考核大类：0fz,1zzll,2bm,3ju,4在线培训*/
	public static final String[] XXKH_TYPE = {"fz","zzll","bm","ju","zxpx"};//王磊添加在线培训 2019-02-28

	/** 学习试题大类：0fzst,1zzllst,2bmst3st */
	public static final String[] XXKH_TYPE_ST = {"fzst","zzllst","bmst"};
	
	/** 难度级别：0简单,1一般,2困难 */
	public static final String[] DIFFICULTY_LEVEL = {"jiandan","yiban","kunnan"};
	
	/** 试题类型：0单选,1多选,2判断，3填空，4问答 */
	public static final String[] QUESTION_TYPE = {"danxuan","duoxuan","panduan","tiankong","wenda"};
	
	
	/** 判断资料类型 */
	public static String ZILIAO_TYPE[] = {"fz","zzll","bm"};
	
	/** 判断考试类型"0fzks","1zzllks","2bmks","3deptks" */
	public static String TEST_TYPE[] = {"fzks","zzllks","bmks","deptks"};
	
	/** 判断资料类名 */
	public static String ZILIAO_TYPE_NAME[] = {"法制","政治理论","保密"};
	
	/** 人工评卷状态0：未评卷；1：已评卷 (xxkh_user_paper表中字段)*/
	public static String ARTIFICIAL_MARKING_STATE[] = {"0","1"};
	
	/** 上报员业务角色编号 */
	public static final String[] REPORT_ROLE = {"D221","C221"};
}
