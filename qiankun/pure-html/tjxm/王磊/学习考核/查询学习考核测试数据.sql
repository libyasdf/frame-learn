
--查询学习时长  xxkh_learn_time
select t.*,t.rowid from xxkh_learn_time t;

--资料文件表 xxkh_video_and_pdf
select t.*,t.rowid from xxkh_video_and_pdf t;

--资料表 xxkh_zi_liao
select t.*,t.rowid from xxkh_zi_liao t;

--学习考核相关树 xxkh_tree
select t.*,t.rowid from xxkh_tree t;

--查询试题选项 xxkh_option
select t.*,t.rowid from xxkh_option t;

--查询答题管理 xxkh_answer
select t.*,t.rowid from xxkh_answer t;

--查询试题与题组关联 xxkh_question_qgroup
select t.*,t.rowid from xxkh_question_qgroup t;

--查询试题组 xxkh_question_group
select t.*,t.rowid from xxkh_question_group t;

--查询试题 xxkh_question_info
select t.*,t.rowid from xxkh_question_info t;

--查询人员试卷关联 xxkh_user_paper
select t.*,t.rowid from xxkh_user_paper t;

--查询考试与试卷关联表 xxkh_test_paper
select t.*,t.rowid from xxkh_test_paper t;

--查询试卷表 xxkh_paper_info
select t.*,t.rowid from xxkh_paper_info t;

--查询考试表 xxkh_test_info
select t.*,t.rowid from xxkh_test_info t;

--查询学习考核待办 sys_waitdo_noflow
select t.*,t.rowid from sys_waitdo_noflow t  where t.op_name in('参加考试','上报考试人员通知');



































