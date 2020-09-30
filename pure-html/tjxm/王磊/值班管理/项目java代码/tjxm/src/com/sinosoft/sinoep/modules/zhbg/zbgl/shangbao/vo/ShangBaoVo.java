package com.sinosoft.sinoep.modules.zhbg.zbgl.shangbao.vo;

/**
 * @Author 王富康
 * @Description //TODO 用于接收查询上报比例情况的vo
 * @Date 2018/7/27 11:54
 * @Param
 * @return
 **/
public class ShangBaoVo {

    private String month;//月份
    private String bl;//上报比例

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public ShangBaoVo() {
        super();
    }

    public ShangBaoVo(String month, String bl) {
        super();
        this.month = month;
        this.bl = bl;
    }
}
