
--A.新增业务角色及授权
--新增的业务角色：资料管理员-保密（处） 注：张鹏帅已在正式新增此业务角色，部署正式时，需要修改业务角色编号为：C222
select t.* from sys_flow_role t where t.rolename like '%资料管理员-保密（处）%';

--1.系统角色与业务角色的绑定关系
select s.* from sys_rlsy_dprb s where sub_id = '97206' and  s.roleid='141894';

--2.系统角色与业务角色的绑定关系（包含系统角色、业务角色名称）方便核对数据的正确性
select s.rlsy_id,x.rlsy_nm, s.roleid,y.rolename from sys_rlsy_dprb s,sys_role_syst x,sys_flow_role y where s.sub_id = '97206' and  s.roleid='141894' and s.rlsy_id = x.rlsy_id and s.roleid = y.roleid order by x.rlsy_nm;

--B.新增资源元素及授权
--1.学习考核资源对应的资源元素
select t.* from sys_element_con t where sub_id = '97206' and t.resource_id in (select t.id from sys_resource t start with id = '1000017' connect by super_id = prior id);

--2.获取某些资源下的系统角色与资源、资源元素的绑定数据
select t.* from sys_ctrl_record t where t.rlsy_id in (select t.rlsy_id from sys_role_syst t where t.rlsy_nm like '%学习考核%' and t.status = '1');








