  --1.新增【资源、资源元素】
  --1.获取某模块的资源id
  select t.* from sys_resource t where resource_name = '文电管理';
  --2.获取某模块下的所有资源（递归查询）
  select t.id,t.tree_id,t.resourcetype_id,
  t.resource_name,t.sub_id,t.time,
  t.auther,t.modifiy_time,t.modifier,
   t.content,t.memo,t.resource_order,
   t.discription,t.super_id,t.s_navigation,
   t.is_show,t.is_control_sub,t.sub_ids,
   t.status,t.flag,t.is_portal
   ,t.rtype_id,t.form_url
   from sys_resource t start with id = '4028da0669147b170169224f331f0054' connect by super_id = prior id;

  --3.获取资源对应的资源配置
  select t.* from sys_robject_ext t where t.resource_id in (select t.id from sys_resource t start with id = '4028da0669147b170169224f331f0054' connect by super_id = prior id);

  --4.获取某些资源对应的资源元素
  select t.* from sys_element_con t where sub_id = '97206' and t.resource_id in (select t.id from sys_resource t start with id = '4028da0669147b170169224f331f0054' connect by super_id = prior id);

  --5.新增【系统角色】【sys_role_syst】
  --1.获取某些资源对应的系统角色
  select t.* from sys_role_syst t where sub_id = '97206' and t.status='1' and t.resource_id in (select t.id from sys_resource t start with id = '4028da0669147b170169224f331f0054' connect by super_id = prior id);

  --6.新增【业务角色】【sys_flow_role】
  --1.获取新增的业务角色(1)
  --select t.* from sys_flow_role t where sub_id = '97206' and t.rolename like '立卷单位管理员%';

  --7.新增【系统角色与资源、资源元素的绑定关系】【sys_ctrl_record】
  --1.获取某些资源下的系统角色与资源、资源元素的绑定数据
  select t.* from sys_ctrl_record t where t.rlsy_id in (select t.rlsy_id from sys_role_syst t where t.rlsy_nm like '综合办公-文电管理%' and t.status = '1');

  --8.新增【系统角色与业务角色的绑定关系】【sys_rlsy_dprb】
  --1.获取某些资源下的系统角色与业务角色的绑定关系
  select s.* from sys_rlsy_dprb s where sub_id = '97206' and s.rlsy_id in (select t.rlsy_id from sys_role_syst t where sub_id = '97206' and t.resource_id in (select t.id from sys_resource t start with id = '4028da0669147b170169224f331f0054' connect by super_id = prior id));

  --2.获取某些资源下的系统角色与业务角色的绑定关系（包含系统角色、业务角色名称）方便核对数据的正确性(仅用于检查授权，不用打包)
  select s.rlsy_id,x.rlsy_nm, s.roleid,y.rolename from sys_rlsy_dprb s,sys_role_syst x,sys_flow_role y where s.sub_id = '97206' and s.rlsy_id in (select t.rlsy_id from sys_role_syst t where sub_id = '97206' and t.resource_id in (select t.id from sys_resource t start with id = '4028da0669147b170169224f331f0054' connect by super_id = prior id)) and s.rlsy_id = x.rlsy_id and s.roleid = y.roleid order by x.rlsy_nm;


select t.*,t.rowid from sys_resource t where t.resource_name='文电管理'
