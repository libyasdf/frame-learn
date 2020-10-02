package com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutinfo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.sinosoft.sinoep.modules.mypage.wmgl.takeout.takeoutdetil.entity.TakeOutDetail;


/**
 * 外卖实体类
 * TODO 
 * @author 马秋霞
 * @Date 2019年5月6日 下午5:51:13
 */
@Entity
@Table(name = "WMGL_TAKE_OUT_INFO")
public class TakeOutInfo {
	
	/** 主键*/
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	/** 创建人id*/
	@Column(name = "cre_user_id")
	private String creUserId;
	
	/** 创建人名*/
	@Column(name = "cre_user_name")
	private String creUserName;
	
	/** 创建人部门id*/
	@Column(name = "cre_dept_id")
	private String creDeptId;
	
	/** 创建人部门名*/
	@Column(name = "cre_dept_name")
	private String creDeptName;
	
	/** 创建人处室ID*/
	@Column(name = "cre_chushi_id")
	private String creChushiId;
	
	/** 创建人处室名*/
	@Column(name = "cre_chushi_name")
	private String creChushiName;
	
	/**创建人二级局ID*/
	@Column(name = "cre_ju_id")
	private String creJuId;
	
	/** 创建人二级局名*/
	@Column(name = "cre_ju_name")
	private String creJuName;
	
	/** 逻辑删除*/
	@Column(name = "visible")
	private String visible;
	
	/** 创建时间*/
	@Column(name = "cre_time")
	private String creTime;
	
	/** 最后更新人id*/
	@Column(name = "update_user_id")
	private String updateUserId;
	
	/** 最后更新人名*/
	@Column(name = "update_user_name")
	private String updateUserName;
	
	/** 最后更新时间*/
	@Column(name = "update_time")
	private String updateTime;
	
	/** 主题 */
	@Column(name = "TITLE")
	private String title;
	
	/** 取餐时间  */
	@Column(name = "TAKEFOOD_TIME")
	private String takeFoodTime;
	
	/** 最晚预定时间  */
	@Column(name = "DEADLINE_TIME")
	private String deadlineTime;
	
	
	/** 注意事项 */
	@Column(name = "ATTENTION_ITEM")
	private String attentionItem;
	
	/** 状态 */
	@Column(name = "STATUS")
	private String status;
	
	/** 备注 */
	@Column(name = "MARK")
	private  String mark;
	
	/** 是否为复用记录，1表示是，0表示否 */
	@Column(name = "COPY")
	private  String copy;
	
	/** 定时器是否改过订单状态，1表示是，0表示 */
	@Column(name = "tongji")
	private  String tongji;
	
	@Transient
	private String cz = "";
	
	/** 是否停止预定；0表示未停止，1表示已停止*/
	@Transient
	private String stop = "1";
	
	/** 订单数量 */
	@Transient
	private String orderNo = "0";
	
	/** 订单数量 */
	@Transient
	private List<TakeOutDetail> detil;
	
	
	public String getTongji() {
		return tongji;
	}

	public void setTongji(String tongji) {
		this.tongji = tongji;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public List<TakeOutDetail> getDetil() {
		return detil;
	}

	public void setDetil(List<TakeOutDetail> detil) {
		this.detil = detil;
	}

	public String getCopy() {
		return copy;
	}

	public void setCopy(String copy) {
		this.copy = copy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTakeFoodTime() {
		return takeFoodTime;
	}

	public void setTakeFoodTime(String takeFoodTime) {
		this.takeFoodTime = takeFoodTime;
	}

	public String getDeadlineTime() {
		return deadlineTime;
	}

	public void setDeadlineTime(String deadlineTime) {
		this.deadlineTime = deadlineTime;
	}


	public String getAttentionItem() {
		return attentionItem;
	}

	public void setAttentionItem(String attentionItem) {
		this.attentionItem = attentionItem;
	}



	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreUserId() {
		return creUserId;
	}

	public void setCreUserId(String creUserId) {
		this.creUserId = creUserId;
	}

	public String getCreUserName() {
		return creUserName;
	}

	public void setCreUserName(String creUserName) {
		this.creUserName = creUserName;
	}

	public String getCreDeptId() {
		return creDeptId;
	}

	public void setCreDeptId(String creDeptId) {
		this.creDeptId = creDeptId;
	}

	public String getCreDeptName() {
		return creDeptName;
	}

	public void setCreDeptName(String creDeptName) {
		this.creDeptName = creDeptName;
	}

	public String getCreChushiId() {
		return creChushiId;
	}

	public void setCreChushiId(String creChushiId) {
		this.creChushiId = creChushiId;
	}

	public String getCreChushiName() {
		return creChushiName;
	}

	public void setCreChushiName(String creChushiName) {
		this.creChushiName = creChushiName;
	}

	public String getCreJuId() {
		return creJuId;
	}

	public void setCreJuId(String creJuId) {
		this.creJuId = creJuId;
	}

	public String getCreJuName() {
		return creJuName;
	}

	public void setCreJuName(String creJuName) {
		this.creJuName = creJuName;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getCreTime() {
		return creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	
	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	
	
	

}
