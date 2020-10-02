--数据同步使用，同步立卷单位的code值

update e_e_year_qgda_dak s
   set s.cre_chushi_id =
       (select t.code
          from sys_data_dictionary t
         where s.basefolder_unit = t.name)
