package com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.services;

import com.sinosoft.sinoep.common.util.PageImpl;
import com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.dao.CateDeptPersonRelationDao;
import com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.entity.DaglCateDeptPersonRelation;
import com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.entity.RelationVo;
import com.sinosoft.sinoep.user.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import workflow.common.JDateToolkit;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 王富康
 * @Description //TODO 立卷单位和录入人及立卷单位管理员关系Serivce实现层
 * @Date 2019/2/1 11:47
 * @Param
 * @return
 **/
@Service
public class CateDeptPersonRelationServiceImpl implements CateDeptPersonRelationService{

    @Autowired
    private CateDeptPersonRelationDao cateDeptPersonRelationDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @Author 王富康
     * @Description //TODO 查询立卷单位和录入人及立卷单位管理员关系列表
     * @Date 2019/2/1 12:00
     * @Param [pageImpl, daglCateDeptPersonRelation]
     * @return com.sinosoft.sinoep.common.util.PageImpl
     **/
    @Override
    public PageImpl getRelationData(Pageable pageable, PageImpl pageImpl, DaglCateDeptPersonRelation daglCateDeptPersonRelation){
        StringBuilder querySql = new StringBuilder();
        List<Object> para = new ArrayList<>();

        querySql.append(" from DaglCateDeptPersonRelation t where 1=1 and t.cateId = ?");
        para.add(""+daglCateDeptPersonRelation.getCateId()+"");//该门类下

        //拼接条件
        if (StringUtils.isNotBlank(daglCateDeptPersonRelation.getLjdwMark())) {//立卷单位id
            querySql.append("   and t.ljdwMark = ?");
            para.add(""+daglCateDeptPersonRelation.getLjdwMark()+"");
        }

        if (StringUtils.isNotBlank(daglCateDeptPersonRelation.getLrrName())) {//录入人
            String llrName = "";
            try{
                llrName = java.net.URLDecoder.decode(daglCateDeptPersonRelation.getLrrName(),"UTF-8");
            }catch (Exception e){
                e.printStackTrace();
            }
            querySql.append("   and t.lrrName like ? ");
            para.add("%"+llrName+"%");
        }

        if (StringUtils.isNotBlank(daglCateDeptPersonRelation.getLjdwAdminName())) {//立卷单位管理员
            String ljdwAdminName = "";
            try{
                ljdwAdminName = java.net.URLDecoder.decode(daglCateDeptPersonRelation.getLjdwAdminName(),"UTF-8");
            }catch (Exception e){
                e.printStackTrace();
            }
            querySql.append("   and t.ljdwAdminName like ? ");
            para.add("%"+ljdwAdminName+"%");
        }

        // 拼接排序语句
        if (StringUtils.isBlank(pageImpl.getSortName())) {
            querySql.append("  order by t.ljdwMark desc ) ");
        } else {
            querySql.append("  order by t." + pageImpl.getSortName() + " " + pageImpl.getSortOrder() + " ");
        }

        Page<DaglCateDeptPersonRelation> page = cateDeptPersonRelationDao.query(querySql.toString(), pageable,para.toArray());
        return new PageImpl((int)page.getTotalElements(),page.getContent());
    }

    /**
     * @Author 王富康
     * @Description //TODO 查询关系list
     * @Date 2019/2/13 14:46
     * @Param [daglCateDeptPersonRelation]
     * @return java.util.List<com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.entity.DaglCateDeptPersonRelation>
     **/
    @Override
    public List<DaglCateDeptPersonRelation> getRelationList(DaglCateDeptPersonRelation daglCateDeptPersonRelation){

    	//防止sql注入 王磊 20190426
    	List<Object> paraList = new ArrayList<>();
        String sql = "select * from dagl_cate_dept_person_relation t where 1=1 and t.cate_id = ? ";
        paraList.add(daglCateDeptPersonRelation.getCateId());
        //拼接条件
        if (StringUtils.isNotBlank(daglCateDeptPersonRelation.getLjdwMark())) {//立卷单位id
            sql +="     and t.ljdw_mark = ? ";
            paraList.add(daglCateDeptPersonRelation.getLjdwMark());
        }

        List<DaglCateDeptPersonRelation> daglCateDeptPersonRelations = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DaglCateDeptPersonRelation.class),paraList.toArray());

        return daglCateDeptPersonRelations;
    }

    /**
     * @Author 王富康
     * @Description //TODO 新增立卷单位和录入人及立卷单位管理员关系
     * @Date 2019/2/1 14:48
     * @Param [relationVo]
     * @return com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.entity.RelationVo
     **/
    @Override
    public RelationVo saveRelation(RelationVo relationVo){
        //分析数据
        String[] lrrIds = relationVo.getLrrIds().split(",");
        String[] lrrNames = relationVo.getLrrNames().split(",");
        String[] lrrDeptId = relationVo.getLrrDeptId().split(",");
        //根据数据字典的mark值查询value


        for(int i=0;i<lrrIds.length;i++){
            //公用的页面的数据
            DaglCateDeptPersonRelation daglCateDeptPersonRelation = new DaglCateDeptPersonRelation();
            daglCateDeptPersonRelation.setCateId(relationVo.getCateId());
            daglCateDeptPersonRelation.setLjdwMark(relationVo.getLjdwMark());
            daglCateDeptPersonRelation.setLjdwName(relationVo.getLjdwName());
            daglCateDeptPersonRelation.setLjdwAdminId(relationVo.getLjdwAdminIds());
            daglCateDeptPersonRelation.setLjdwAdminName(relationVo.getLjdwAdminNames());
            daglCateDeptPersonRelation.setLjdwAdminDeptId(relationVo.getLjdwAdminDeptId());
            //分析页面数据的录入人，一个录入人一条数据
            daglCateDeptPersonRelation.setLrrId(lrrIds[i]);
            daglCateDeptPersonRelation.setLrrName(lrrNames[i]);
            daglCateDeptPersonRelation.setLrrDeptId(lrrDeptId[i]);
            //基本数据
            daglCateDeptPersonRelation.setCreUserId(UserUtil.getCruUserId());
            daglCateDeptPersonRelation.setCreUserName(UserUtil.getCruUserName());
            daglCateDeptPersonRelation.setCreDeptId(UserUtil.getCruDeptId());
            daglCateDeptPersonRelation.setCreDeptName(UserUtil.getCruDeptName());
            daglCateDeptPersonRelation.setCreChushiId(UserUtil.getCruChushiId());
            daglCateDeptPersonRelation.setCreChushiName(UserUtil.getCruChushiName());
            daglCateDeptPersonRelation.setCreJuId(UserUtil.getCruJuId());
            daglCateDeptPersonRelation.setCreJuName(UserUtil.getCruJuName());
            daglCateDeptPersonRelation.setCreTime(JDateToolkit.getNowDate4());

            cateDeptPersonRelationDao.save(daglCateDeptPersonRelation);
        }
        return relationVo;//这里的操作不会涉及到某个录入人那条数据的id，返回页面的数据就好了
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据门类id和立卷单位删除所有对应关系
     * @Date 2019/2/1 15:27
     * @Param [cateId, ljdwMark]
     * @return void
     **/
    @Override
    public void deleteRelation(String cateId, String ljdwMark){
        String sql = "delete from dagl_cate_dept_person_relation t where t.cate_id= ? and t.ljdw_mark= ? ";
        jdbcTemplate.update(sql,cateId,ljdwMark);
    }

    /**
     * @Author 王富康
     * @Description //TODO 根据门类删除所有对应关系，删除树节点时用的
     * @Date 2019/3/16 15:43
     * @Param [cateId, ljdwMark]
     * @return void
     **/
    @Override
    public void deleteAllRelation(String cateId){
        String sql = "delete from dagl_cate_dept_person_relation t where t.cate_id=?";
        jdbcTemplate.update(sql,cateId);
    }

    /**
     * @Author 王富康
     * @Description //TODO 修改立卷单位关系对应数据
     * @Date 2019/2/14 10:20
     * @Param [relationVo]
     * @return com.sinosoft.sinoep.modules.dagl.xtpz.catedeptpersonrelation.entity.RelationVo
     **/
    @Override
    public RelationVo updateRelation(RelationVo relationVo){
        //首先根据门类及立卷单位删除数据
        deleteRelation(relationVo.getCateId(),relationVo.getLjdwMark());
        //再执行新增操作
        return saveRelation(relationVo);
    }

    /**
     * 去重查询所有立卷单位或者录入人
     * @author 王磊
     * @Date 2019年2月12日 上午9:53:23
     * @param ljdwOrLrr
     * @return
     */
    @Override
    public List<DaglCateDeptPersonRelation> getAllLjdwOrLrr(String ljdwOrLrr){
    	List<DaglCateDeptPersonRelation> list = new ArrayList<DaglCateDeptPersonRelation>();
    	//查所有立卷单位
    	if(StringUtils.isNotBlank(ljdwOrLrr) && "ljdw".equals(ljdwOrLrr)){
    		String sql = "select distinct ljdw_name,ljdw_mark from dagl_cate_dept_person_relation t";
    		list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DaglCateDeptPersonRelation.class));
    	}else if(StringUtils.isNotBlank(ljdwOrLrr) && "lrr".equals(ljdwOrLrr)){//查所有录入人
    		String sql = "select distinct lrr_id,lrr_name from dagl_cate_dept_person_relation t";
    		list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DaglCateDeptPersonRelation.class));
    	}
    	return list;
    }
}
