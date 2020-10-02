--1.调整档案实体状态数据字典：
--调整已归档-》待移交
update sys_data_dictionary t set t.name='待移交',t.remark='单位预立库默认状态' where t.name='已归档' and t.mark='06' and t.code='01';
--调整已移交待审核-》已移交待汇总
update sys_data_dictionary t set t.name='已移交待汇总',t.remark='录入人在单位预立库进行移交操作后，档案状态变为【已移交待汇总】' where t.name='已移交待审核' and t.mark='06' and t.code='02';
--调整已移交-》已归档
update sys_data_dictionary t set t.name='已归档',t.remark='档案馆管理员进行汇总操作后，档案状态变为【已归档】' where t.name='已移交' and t.mark='06' and t.code='03';
--添加已退回待处置状态
insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028d0896c2729b5016c27335cd40002', null, '已退回待处置', '04', '06', '1', '1', 3, '档案管理员将录入人移交的【已移交待汇总】状态数据，退回（退回不归档、退回待修改）给录入人，此时数据的状态改为【已退回待处置】', '1', '2019-07-25 11:36:13', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-07-25 11:36:13', '111245', '杜蒙蒙');

--数据字典：文件类型加：已推送至档案录入人
insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028d08d6c837ff4016c83f812ca001b', '', '已推送至档案录入人', '05', 'dagl_fileStatus', '1', '1', 4, '', '1', '2019-08-12 11:56:08', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-08-12 11:56:08', '111245', '杜蒙蒙');

--收文添加录入人id字段；文件所在位置字段
alter table dagl_receive_file add (lrr_id varchar2(50 char));
comment on column dagl_receive_file.lrr_id
  is '录入人的id，录入人id为空，在文件管理展示，录入人不为空，在中转库展示。';
alter table dagl_receive_file add (file_site varchar2(20 char));
comment on column dagl_receive_file.file_site
  is '文件位置：01：文件管理；02：中转库；03：已经归档到了单位预立库';

  --发文添加录入人id字段；文件所在位置字段
alter table dagl_send_file add (lrr_id varchar2(50 char));
comment on column dagl_send_file.lrr_id
  is '录入人的id，录入人id为空，在文件管理展示，录入人不为空，在中转库展示。';
alter table dagl_send_file add (file_site varchar2(20 char));
comment on column dagl_send_file.file_site
  is '文件位置：01：文件管理；02：中转库；03：已经归档到了单位预立库';

--更新表字段配置中maintitle长度为500
update table_struct_description t set t.column_width='500' where lower(t.column_name)='maintitle';

--修改所有档案业务表题名（maintitle）长度为500
DECLARE
  V_SQL        VARCHAR2(2000);
  V_TABLE_NAME VARCHAR2(50);
  CURSOR C1 IS select distinct table_name from table_struct_description t;--查询所有档案业务表
BEGIN
  OPEN C1;
  LOOP
    --提取一行数据到c1
    FETCH C1
      INTO V_TABLE_NAME;
    --判读是否提取到值，没取到值就退出
    --取到值c_job%notfound 是false
    --取不到值c_job%notfound 是true
    EXIT WHEN C1%NOTFOUND;
    begin
    --修改maintitle字段长度
    V_SQL := 'alter table ' || V_TABLE_NAME || ' modify maintitle VARCHAR2(500 CHAR)';
    EXECUTE IMMEDIATE V_SQL;
    dbms_output.put_line(V_TABLE_NAME||'处理完成！');

    exception when others then
      dbms_output.put_line(V_TABLE_NAME||'出现异常，该表需要手动修改题名(maintitle)长度为 VARCHAR2(500 CHAR)！');
    end;
  END LOOP; --关闭游标
  CLOSE C1;
END;