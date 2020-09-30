  --1.档案管理数据字典
	select * from sys_data_dictionary sdd where sdd.mark in(
	select t.mark from sys_data_dictionary t where  t.visible='1' start with t.id='4028d0876706f436016706ffd0ed0002' connect by  prior t.id=t.pid
	) and sdd.visible='1';