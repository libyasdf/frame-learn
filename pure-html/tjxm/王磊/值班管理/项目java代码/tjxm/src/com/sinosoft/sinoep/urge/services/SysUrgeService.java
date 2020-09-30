package com.sinosoft.sinoep.urge.services;

import com.sinosoft.sinoep.urge.entity.SysUrge;

/*
* 催办记录表
* */
public interface SysUrgeService {

    /*
    * 保存催办信息
    * */
    public boolean saveUrge(SysUrge sysUrge);
}
