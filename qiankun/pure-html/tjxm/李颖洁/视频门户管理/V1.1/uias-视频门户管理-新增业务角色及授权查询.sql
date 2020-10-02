
--查询视频门户管理新增【视频发布发布人（处）】、【视频发布发布人（科）】角色的sql
select t.* from sys_flow_role t where sub_id = '97206' and  t.rolename in ('视频发布发布人（处）','视频发布发布人（科）');

--查询视频门户管理新增角色与系统角色关联关系的sql
select t.* from sys_rlsy_dprb t where sub_id = '97206' and t.rlsy_id = '1027300';

--查询更改的【视频发布二级管理员】为非默认角色的sql(sys_def字段)
select t.* from sys_role_syst t where sub_id = '97206' and t.rlsy_id = '1027300';







