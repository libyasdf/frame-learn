--修改数据字典名称的长度为100
alter table sys_data_dictionary modify name varchar2(100 char)

--添加字典类型
insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028d08e6955d05c016955d1f2c50000', '402894826531499e016531ed97460001', '请假事由', '05', 'leaveReason', '1', '1', 5, '', '0', '2019-03-07 09:43:36', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 09:43:36', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028d08e6955d05c016955e24c75000e', '402894826531499e016531ed97460001', '迟到原因', '06', 'lateCause', '1', '1', 6, '', '0', '2019-03-07 10:01:27', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:01:27', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955e4f4e10071', '402894826531499e016531ed97460001', '早退原因', '07', 'leaveEarlyCause', '1', '1', 7, '', '0', '2019-03-07 10:04:22', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:04:22', '111245', '杜蒙蒙');

--添加字典项
insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955e824f0007f', '', '带孩子看病、照顾病人、陪孕检、带孩子打疫苗', '1', 'lateCause', '1', '1', 0, '', '1', '2019-03-07 10:07:51', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:07:51', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955e857f30080', '', '本人身体不适、看病、治疗、买药、住院、病假', '2', 'lateCause', '1', '1', 1, '', '1', '2019-03-07 10:08:04', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:08:04', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955e88b050081', '', '送孩子、接孩子、照顾孩子、家长会、参加亲子幼儿园、学校活动', '3', 'lateCause', '1', '1', 2, '', '1', '2019-03-07 10:08:17', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:08:17', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955e8bd220082', '', '购房、办贷款、还贷款、办理房产过户、装修、维修、试气、试水、搬家', '4', 'lateCause', '1', '1', 3, '', '1', '2019-03-07 10:08:29', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:08:29', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955e8f04f0083', '', '登记结婚、参加婚礼、参加葬礼', '5', 'lateCause', '1', '1', 4, '', '1', '2019-03-07 10:08:43', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:08:43', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955e9203f0084', '', '操办本人及子女婚事', '6', 'lateCause', '1', '1', 5, '', '1', '2019-03-07 10:08:55', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:08:55', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955e97e230085', '', '驾校考试、办理车辆手续、车辆故障修理、交通事故', '7', 'lateCause', '1', '1', 6, '', '1', '2019-03-07 10:09:19', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:09:19', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955e9ac2b0086', '', '亲属朋友来津接站及送站', '8', 'lateCause', '1', '1', 7, '', '1', '2019-03-07 10:09:31', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:09:31', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955e9e93f0087', '', '银行办理业务', '9', 'lateCause', '1', '1', 8, '', '1', '2019-03-07 10:09:46', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:09:46', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955ea1aa20095', '', '被困电梯', '10', 'lateCause', '1', '1', 9, '', '1', '2019-03-07 10:09:59', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:09:59', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955ea498b0096', '', '找车位', '11', 'lateCause', '1', '1', 10, '', '1', '2019-03-07 10:10:11', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:10:11', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955eab4050097', '', '天气恶劣、公交晚点、路途堵车', '12', 'lateCause', '1', '1', 11, '', '1', '2019-03-07 10:10:38', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:10:38', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955eae45c0098', '', '出门晚', '13', 'lateCause', '1', '1', 12, '', '1', '2019-03-07 10:10:51', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:10:51', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955eb100b0099', '', '起床晚', '14', 'lateCause', '1', '1', 13, '', '1', '2019-03-07 10:11:02', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:11:02', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955eb6785009a', '', '取快递', '15', 'lateCause', '1', '1', 14, '', '1', '2019-03-07 10:11:24', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:11:24', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955ebaea1009b', '', '前日工作至很晚', '16', 'lateCause', '1', '1', 15, '', '1', '2019-03-07 10:11:42', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:11:42', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955ebf6a5009c', '', '未找到共享单车', '17', 'lateCause', '1', '1', 16, '', '1', '2019-03-07 10:12:01', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:12:01', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955ec356b009d', '', '忘带东西回家取', '18', 'lateCause', '1', '1', 17, '', '1', '2019-03-07 10:12:17', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:12:17', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955ec592c009e', '', '走慢了', '19', 'lateCause', '1', '1', 18, '', '1', '2019-03-07 10:12:26', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:12:26', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955ec9337009f', '', '送家属', '20', 'lateCause', '1', '1', 19, '', '1', '2019-03-07 10:12:41', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:12:41', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955ecc75300a0', '', '迟到5分钟内', '21', 'lateCause', '1', '1', 20, '', '1', '2019-03-07 10:12:54', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:12:54', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955ecea2100a1', '', '火车晚点', '22', 'lateCause', '1', '1', 21, '', '1', '2019-03-07 10:13:03', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:13:03', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955ed186900a2', '', '准备研究生考试', '23', 'lateCause', '1', '1', 22, '', '1', '2019-03-07 10:13:15', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:13:15', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955ed45a700a3', '', '时间误差', '24', 'lateCause', '1', '1', 23, '', '1', '2019-03-07 10:13:27', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:13:27', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955ed749100a4', '', '忘带东西', '25', 'lateCause', '1', '1', 24, '', '1', '2019-03-07 10:13:39', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:13:39', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955edb90c00a5', '', '3分钟内或表有误差', '26', 'lateCause', '1', '1', 25, '', '1', '2019-03-07 10:13:56', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:13:56', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955edf4ff00a6', '', '请假半天', '27', 'lateCause', '1', '1', 26, '', '1', '2019-03-07 10:14:11', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:14:11', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955ee2fbe00a8', '', '路上没算好时间', '28', 'lateCause', '1', '1', 27, '', '1', '2019-03-07 10:14:27', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:14:27', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955ee60c600a9', '', '看错时间', '29', 'lateCause', '1', '1', 28, '', '1', '2019-03-07 10:14:39', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:14:39', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955ee9c7100aa', '', '过于掐点', '30', 'lateCause', '1', '1', 29, '', '1', '2019-03-07 10:14:54', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:14:54', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955eebd5a00ab', '', '送钥匙', '31', 'lateCause', '1', '1', 30, '', '1', '2019-03-07 10:15:03', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:15:03', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955eee31500ac', '', '其他', '32', 'lateCause', '1', '1', 31, '', '1', '2019-03-07 10:15:12', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:15:12', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955f039da00ae', '', '带孩子看病、照顾病人、陪孕检、陪产假', '1', 'leaveEarlyCause', '1', '1', 0, '', '1', '2019-03-07 10:16:40', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:16:40', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955f23e1100b0', '', '本人身体不适、看病、治疗、买药、住院、病假', '2', 'leaveEarlyCause', '1', '1', 1, '', '1', '2019-03-07 10:18:52', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:18:52', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955f26cad00b1', '', '送孩子、接孩子、照顾孩子、家长会、参加亲子幼儿园、学校活动', '3', 'leaveEarlyCause', '1', '1', 2, '', '1', '2019-03-07 10:19:04', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:19:04', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955f291a800b2', '', '孩子升学考试、填报志愿、送孩子入大学', '4', 'leaveEarlyCause', '1', '1', 3, '', '1', '2019-03-07 10:19:14', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:19:14', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955f2b52900b3', '', '购房、办贷款、还贷款、办理房产过户、装修、维修、试气、试水、搬家', '5', 'leaveEarlyCause', '1', '1', 4, '', '1', '2019-03-07 10:19:23', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:19:23', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016955f2d7e700b4', '', '登记结婚、参加婚礼、参加葬礼', '6', 'leaveEarlyCause', '1', '1', 5, '', '1', '2019-03-07 10:19:32', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 10:19:32', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d3201695620c02800b5', '', '操办本人及子女婚事', '7', 'leaveEarlyCause', '1', '1', 6, '', '1', '2019-03-07 11:09:40', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:09:40', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d3201695620f1cc00b6', '', '驾校考试、办理车辆手续、车辆故障修理、交通事故', '8', 'leaveEarlyCause', '1', '1', 7, '', '1', '2019-03-07 11:09:53', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:09:53', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016956212f1e00b7', '', '亲属朋友来津接站及送站', '9', 'leaveEarlyCause', '1', '1', 8, '', '1', '2019-03-07 11:10:09', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:10:09', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d3201695621874c00b8', '', '前往外地赶时间出行', '10', 'leaveEarlyCause', '1', '1', 9, '', '1', '2019-03-07 11:10:31', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:10:31', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016956220e7700b9', '', '晚上值班到24点下午提前回家休息', '11', 'leaveEarlyCause', '1', '1', 10, '', '1', '2019-03-07 11:11:06', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:11:06', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d320169562236bd00ba', '', '挪车', '12', 'leaveEarlyCause', '1', '1', 11, '', '1', '2019-03-07 11:11:16', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:11:16', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016956225b6000bb', '', '找车位', '13', 'leaveEarlyCause', '1', '1', 12, '', '1', '2019-03-07 11:11:26', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:11:26', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d3201695622829500bc', '', '取快递', '14', 'leaveEarlyCause', '1', '1', 13, '', '1', '2019-03-07 11:11:36', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:11:36', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d3201695622cbff00bd', '', '把握考勤记录时间不准确', '15', 'leaveEarlyCause', '1', '1', 14, '', '1', '2019-03-07 11:11:54', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:11:54', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d3201695622fb0800be', '', '交通原因', '16', 'leaveEarlyCause', '1', '1', 15, '', '1', '2019-03-07 11:12:06', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:12:06', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016956232f2400bf', '', '恶劣天气', '17', 'leaveEarlyCause', '1', '1', 16, '', '1', '2019-03-07 11:12:20', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:12:20', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016956235c4a00c0', '', '派出所办事', '18', 'leaveEarlyCause', '1', '1', 17, '', '1', '2019-03-07 11:12:31', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:12:31', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d320169562385f500c1', '', '接待朋友', '19', 'leaveEarlyCause', '1', '1', 18, '', '1', '2019-03-07 11:12:42', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:12:42', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d3201695623ad7000c2', '', '家人生日', '20', 'leaveEarlyCause', '1', '1', 19, '', '1', '2019-03-07 11:12:52', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:12:52', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d3201695624210400c3', '', '去银行', '21', 'leaveEarlyCause', '1', '1', 20, '', '1', '2019-03-07 11:13:22', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:13:22', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d320169562444da00c4', '', '接人', '22', 'leaveEarlyCause', '1', '1', 21, '', '1', '2019-03-07 11:13:31', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:13:31', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016956246be600c5', '', '时间误差', '23', 'leaveEarlyCause', '1', '1', 22, '', '1', '2019-03-07 11:13:41', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:13:41', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d3201695624ab3e00c6', '', '赶公交', '24', 'leaveEarlyCause', '1', '1', 23, '', '1', '2019-03-07 11:13:57', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:13:57', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d3201695624e9da00c7', '', '送孩子出国', '25', 'leaveEarlyCause', '1', '1', 24, '', '1', '2019-03-07 11:14:13', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:14:13', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d3201695625184b00c8', '', '探望病人', '26', 'leaveEarlyCause', '1', '1', 25, '', '1', '2019-03-07 11:14:25', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:14:25', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d320169562549e600c9', '', '看错时间', '27', 'leaveEarlyCause', '1', '1', 26, '', '1', '2019-03-07 11:14:38', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:14:38', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016956259b9f00ca', '', '因私去蓟州、滨海', '28', 'leaveEarlyCause', '1', '1', 27, '', '1', '2019-03-07 11:14:59', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:14:59', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d3201695625c9c300cb', '', '修家中电路', '29', 'leaveEarlyCause', '1', '1', 28, '', '1', '2019-03-07 11:15:10', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:15:10', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d3201695625f43e00cc', '', '业主开会', '30', 'leaveEarlyCause', '1', '1', 29, '', '1', '2019-03-07 11:15:21', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:15:21', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d32016956261a8900cd', '', '办理业务', '31', 'leaveEarlyCause', '1', '1', 30, '', '1', '2019-03-07 11:15:31', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:15:31', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d3201695626741000ce', '', '回部队办事', '32', 'leaveEarlyCause', '1', '1', 31, '', '1', '2019-03-07 11:15:54', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:15:54', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d3201695626974600cf', '', '同学聚会', '33', 'leaveEarlyCause', '1', '1', 32, '', '1', '2019-03-07 11:16:03', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:16:03', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028da0769519d3201695626d96800d0', '', '其他', '34', 'leaveEarlyCause', '1', '1', 33, '', '1', '2019-03-07 11:16:20', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 11:16:20', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028d08e6955d05c016955d6c14a0001', '', '带家人看病、照顾病人、陪孕检、陪产假、带孩子打疫苗', '1', 'leaveReason', '1', '1', 0, '', '1', '2019-03-07 09:48:51', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 09:48:51', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028d08e6955d05c016955d7a0670002', '', '本人身体不适、看病、治疗、买药、住院、病假', '2', 'leaveReason', '1', '1', 1, '', '1', '2019-03-07 09:49:48', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 09:49:48', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028d08e6955d05c016955d858360003', '', '送孩子、接孩子、照顾孩子、家长会、参加亲子幼儿园、学校活动', '3', 'leaveReason', '1', '1', 2, '', '1', '2019-03-07 09:50:35', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 09:50:35', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028d08e6955d05c016955d8f9440004', '', '孩子升学考试、填报志愿、送孩子入大学', '4', 'leaveReason', '1', '1', 3, '', '1', '2019-03-07 09:51:16', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 09:51:16', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028d08e6955d05c016955da54150005', '', '购房、办贷款、还贷款、办理房产过户、装修、维修、试气、试水、搬家', '5', 'leaveReason', '1', '1', 4, '', '1', '2019-03-07 09:52:45', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 09:52:45', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028d08e6955d05c016955dab5720006', '', '登记结婚、参加婚礼、参加葬礼', '6', 'leaveReason', '1', '1', 5, '', '1', '2019-03-07 09:53:10', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 09:53:10', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028d08e6955d05c016955db02890007', '', '操办本人及子女婚事', '7', 'leaveReason', '1', '1', 6, '', '1', '2019-03-07 09:53:30', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 09:53:30', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028d08e6955d05c016955dbb71e0008', '', '驾校考试、办理车辆手续、车辆故障修理、交通事故', '8', 'leaveReason', '1', '1', 7, '', '1', '2019-03-07 09:54:16', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 09:54:16', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028d08e6955d05c016955dc3bf70009', '', '亲属朋友来津接站及送站', '9', 'leaveReason', '1', '1', 8, '', '1', '2019-03-07 09:54:50', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 09:54:50', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028d08e6955d05c016955dc8e68000a', '', '参加研究生考试', '10', 'leaveReason', '1', '1', 9, '', '1', '2019-03-07 09:55:11', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 09:55:11', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028d08e6955d05c016955ddb289000b', '', '旅游', '11', 'leaveReason', '1', '1', 10, '', '1', '2019-03-07 09:56:26', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 09:56:26', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028d08e6955d05c016955ddd94f000c', '', '事假', '12', 'leaveReason', '1', '1', 11, '', '1', '2019-03-07 09:56:36', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 09:56:36', '111245', '杜蒙蒙');

insert into sys_data_dictionary (ID, PID, NAME, CODE, MARK, FLAG, VISIBLE, SORT, REMARK, TYPE, CRE_TIME, CRE_USER_ID, CRE_USER_NAME, CRE_DEPT_ID, CRE_DEPT_NAME, UPDATE_TIME, UPDATE_USER_ID, UPDATE_USER_NAME)
values ('4028d08e6955d05c016955de0048000d', '', '其他', '13', 'leaveReason', '1', '1', 12, '', '1', '2019-03-07 09:56:46', '111245', '杜蒙蒙', '97583', '资金交易科室1', '2019-03-07 09:56:46', '111245', '杜蒙蒙');