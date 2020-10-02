--1.新增资源：个人管理下 在线培训时长统计
insert into sys_resource (ID, TREE_ID, RESOURCETYPE_ID, RESOURCE_NAME, SUB_ID, TIME, AUTHER, MODIFIY_TIME, MODIFIER, CONTENT, RESOURCE_EXT, MEMO, RESOURCE_ORDER, DISCRIPTION, SUPER_ID, S_NAVIGATION, IS_SHOW, IS_CONTROL_SUB, SUB_IDS, STATUS, FLAG, IS_PORTAL, RTYPE_ID, FORM_URL)
values ('4028da0669471a74016974fa6e5e0dbe', '0052000300050010', '97210', '在线培训时长统计', '97206', '2019-03-13 10:56:03', '1', '2019-03-13 10:56:03', '1', '/modules/zhbg/xxkh/learningTimeCount/personLearningTimeList.html?treeType=zxpx', '', null, 318, null, '1000409', '1', '0', '0', null, '1', null, '0', '1', null);

--2.新增资源配置
insert into sys_robject_ext (ID, RESOURCE_ID, EXTNODE, EXTNODE_VALUE)
values ('4028da0669471a74016974fa6e5f0dbf', '4028da0669471a74016974fa6e5e0dbe', 'isPublish', '0');

insert into sys_robject_ext (ID, RESOURCE_ID, EXTNODE, EXTNODE_VALUE)
values ('4028da0669471a74016974fa6e5f0dc0', '4028da0669471a74016974fa6e5e0dbe', 'extType', '1');

insert into sys_robject_ext (ID, RESOURCE_ID, EXTNODE, EXTNODE_VALUE)
values ('4028da0669471a74016974fa6e5f0dc1', '4028da0669471a74016974fa6e5e0dbe', 'isPer', '0');

--3.系统角色对模块授权
insert into sys_ctrl_record (ID, RLSY_ID, RESOURCE_ID, RELEMENT_STR, IT_PURVIEW, IT_CONTROL, SUB_ID, AUTHOR, TIME)
values ('4028da0669471a74016974faf1310dd9', '1000802', '4028da0669471a74016974fa6e5e0dbe', null, null, '0', '97206', '1', '2019-03-13 10:56:36');
