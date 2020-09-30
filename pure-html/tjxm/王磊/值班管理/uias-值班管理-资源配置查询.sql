
--综合办公模块
select t.* from sys_resource t where t.resource_name='综合办公';

--综合办公对应的资源配置
select t.* from sys_robject_ext t where t.resource_id in (select t.id from sys_resource t where t.resource_name='综合办公');

--值班管理模块
select t.* from sys_resource t start with id = '1000163' connect by super_id = prior id;

--获取资源对应的资源配置(补充王富康整理内容)
select t.* from sys_robject_ext t where t.resource_id in (select t.id from sys_resource t start with id = '1000163' connect by super_id = prior id);

--资源元素
select t.* from sys_element_con t where t.resource_id in (
select t.id from sys_resource t start with id = '1000163' connect by super_id = prior id
);

--系统角色
select t.* from sys_role_syst t where t.rlsy_nm like '%值班管理%' and t.status='1';
--select t.rlsy_nm,t.rlsy_no,t.memo from sys_role_syst t where t.rlsy_nm like '%值班管理%' and t.status='1';

--（系统角色和资源、元素的关联表）
select t.* from sys_ctrl_record t where t.rlsy_id in (
select t.rlsy_id from sys_role_syst t where t.rlsy_nm like '%值班管理%' and t.status='1'
);

--业务角色表
select t.* from sys_flow_role t where t.rolename like '%值班%' and t.status='1';

--系统角色和业务角色关联
select t.* from sys_rlsy_dprb t where t.rlsy_id in (
select t.rlsy_id from sys_role_syst t where t.rlsy_nm like '%值班管理%' and t.status='1'
);

