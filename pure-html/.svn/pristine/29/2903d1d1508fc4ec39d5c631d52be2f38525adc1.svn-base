package com.sinosoft.sinoep.modules.dagl.daly.change.services;

import java.text.DecimalFormat;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.sinosoft.sinoep.common.constant.CommonConstants;
import com.sinosoft.sinoep.common.util.DateUtil;
import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dagl.daly.change.dao.QTwoChangeDao;
import com.sinosoft.sinoep.modules.dagl.daly.change.entity.QTwoChange;
import com.sinosoft.sinoep.user.util.UserUtil;

/**
 * Q2变更service实现类
 * @author 王磊
 * @Date 2018年11月24日 上午10:42:28
 */
@Service
public class QTwoChangeServiceImpl implements QTwoChangeService {
	
	@Autowired
	private QTwoChangeDao qTwoChangeDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 保存变更记录
	 * @author 王磊
	 * @Date 2018年11月24日 上午10:57:38
	 * @param qTwoChangeList
	 */
	@Override
	public void saveForm(QTwoChange qTwoChange) {
		//生成uuid，用于确定唯一一组变更记录
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		for(QTwoChange q : qTwoChange.getList()){
			//给记录设置uuid
			q.setPid(uuid);
			//设置常用字段开始
			q.setCreJuId(UserUtil.getCruJuId());
			q.setCreJuName(UserUtil.getCruJuName());
			q.setCreChushiId(UserUtil.getCruChushiId());
			q.setCreChushiName(UserUtil.getCruChushiName());
			q.setCreDeptId(UserUtil.getCruDeptId());
			q.setCreDeptName(UserUtil.getCruDeptName());
			q.setCreUserId(UserUtil.getCruUserId());
			q.setCreUserName(UserUtil.getCruUserName());
			q.setCreTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			q.setVisible("1");
			//设置常用字段结束
			//设置备注
			q.setRemark(qTwoChange.getRemark());
			//设置新主管单位名、id
			q.setNewBasefolderUnitName(qTwoChange.getNewBasefolderUnitName());
			q.setNewBasefolderUnitId(qTwoChange.getNewBasefolderUnitId());
			//设置新主管人姓名
			q.setNewMaintitle(qTwoChange.getNewMaintitle());
			//设置变更日期
			q.setChangeDate(qTwoChange.getChangeDate());
			//设置登记日期
			q.setRegisterDate(qTwoChange.getRegisterDate());
			//设置变更单号
			q.setChangeNo(qTwoChange.getChangeNo());
		}
		//保存记录
		qTwoChangeDao.save(qTwoChange.getList());
	}

	/**
	 * 查询变更列表（带分页）
	 * @author 王磊
	 * @Date 2018年11月24日 上午10:58:01
	 * @param pageable
	 * @param pageImpl
	 * @param qTwoChange
	 * @return
	 */
	@Override
	public PageImpl getPageList(Pageable pageable, PageImpl pageImpl,String timeRange,String changeNo,String folderNo) {
		StringBuilder querySql = new StringBuilder();
		List<Object> para = new ArrayList<>();
		querySql.append(" from QTwoChange t ");
		querySql.append(" where t.visible = '" + CommonConstants.VISIBLE[1] + "'");
		// 变更单号
		if (StringUtils.isNotBlank(changeNo)) {
			querySql.append(" and t.changeNo like ?");
			para.add("%"+changeNo+"%");
		}
		
		// 案卷级档号
		if (StringUtils.isNotBlank(folderNo)) {
			querySql.append(" and t.folderNo like ?");
			para.add("%"+folderNo+"%");
		}
		
		// 登记日期
		if (StringUtils.isNotBlank(timeRange)) {
			querySql.append(" and t.registerDate >= ?");
			querySql.append(" and t.registerDate <= ?");
			para.add(timeRange.substring(0, (timeRange.length() + 1) / 2 - 1).trim());
			para.add(timeRange.substring((timeRange.length() + 1) / 2, timeRange.length()).trim());
		}
		// 拼接排序语句
		if (StringUtils.isBlank(pageImpl.getSortName())) {
			querySql.append(" order by t.creTime desc ");
		} else {
			querySql.append(" order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + ") ");
		}
		Page<QTwoChange> page = qTwoChangeDao.query(querySql.toString(), pageable,para.toArray());
		return new PageImpl((int) page.getTotalElements(), page.getContent());
	}

	/**
	 * 根据pid查询本次变更记录
	 * @author 王磊
	 * @Date 2018年11月24日 上午10:58:08
	 * @param qTwoChange
	 * @return
	 */
	@Override
	public QTwoChange findByPid(String pid) {
		List<QTwoChange> list = new ArrayList<QTwoChange>();
		QTwoChange qtTwoChange = new QTwoChange();
		//根据pid查询本次修改记录
		list = qTwoChangeDao.getByPid(pid);
		if(null != list && list.size()>0){
			//设置修改记录
			qtTwoChange.setList(list);
			//设置备注
			qtTwoChange.setRemark(list.get(0).getRemark());
			//设置新主管单位姓名
			qtTwoChange.setNewBasefolderUnitName(list.get(0).getNewBasefolderUnitName());
			//设置新主管人姓名
			qtTwoChange.setNewMaintitle(list.get(0).getNewMaintitle());
			//设置变更日期
			qtTwoChange.setChangeDate(list.get(0).getChangeDate());
			//设置登记日期
			qtTwoChange.setRegisterDate(list.get(0).getRegisterDate());
			//设置变更单号
			qtTwoChange.setChangeNo(list.get(0).getChangeNo());
		}
		return qtTwoChange;
	}

	/**
	 * @param ids
	 * @return
	 * @author 颜振兴
	 * List<Map<String,Object>>
	 * TODO 通过表明和id查数据
	 */
	@Override
	public List<Map<String, Object>> findListByIds(String ids, String tName) {
		String idss="";
		String[] rid = ids.split(",");
		List<Object> para = new ArrayList<>();
		if (rid!=null&&rid.length>0) {
			idss=" and(";
			int i =0; 
			for(String id:rid) {
				i++;
				if(i==rid.length) {
					idss+="recid=?)";
					para.add(id);
					continue;
				}
				idss+="recid= ? or ";
				para.add(id);
			}
		}
		String sql = "select * from "+tName+" where 1=1 "+idss;
		return jdbcTemplate.queryForList(sql,para.toArray());
	}

	@Override
	public int updateForm(QTwoChange twoChange) {
		int n= 0;
		QTwoChange change= new QTwoChange();
		for(int i=0;i<twoChange.getList().size();i++) {
			change = twoChange.getList().get(i);
			String sql = "update "+twoChange.gettName()+ " set basefolder_unit = ? ,cre_chushi_id= ?,maintitle=? where recid=?";
			jdbcTemplate.update(sql,twoChange.getNewBasefolderUnitName(),twoChange.getNewBasefolderUnitId(),twoChange.getNewMaintitle(),change.getRecid());
		}

		return n;
	}

	/**
	 * @Author 王富康
	 * @Description //TODO 查询变更数量生成流水号
	 * @Date 2019/1/31 17:07
	 * @Param []
	 * @return java.lang.String
	 **/
	@Override
	public String findChangeConut(){

		String changeNo = "";
		//获取当前年份
		Calendar cale = null;
		cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);

		String sql =	"	SELECT t.*\n" +
						"  		FROM dagl_q2_change t\n" +
						" 			WHERE t.visible = "+CommonConstants.VISIBLE[1]+"\n" +//未删除的
						"   		and t.change_no like '"+year+"-%'";//当前年的

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		DecimalFormat df=new DecimalFormat("0000");
		String no =df.format(list.size()+1);
		changeNo = year+"-"+no;
		return changeNo;
	}

}
