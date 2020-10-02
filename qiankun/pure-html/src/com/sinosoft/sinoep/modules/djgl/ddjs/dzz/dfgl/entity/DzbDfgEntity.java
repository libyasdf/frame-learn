package com.sinosoft.sinoep.modules.djgl.ddjs.dzz.dfgl.entity;

import java.util.List;


/**
 *
 * 党的建设——党费主表管理实体类
 * @Author: 李帅
 * @Date: 2018/9/11 14:56
 */

public class DzbDfgEntity {
    private String id;
    /** 上级党组织名称 */
    private String superName = "";
    /**党组织名*/
    private String partyOrganizationName;
    /**党组织ID*/
    private String partyOrganizationId;
    /**年份*/
    private String annual;
    /**月份*/
    private String monthval;
    /**当前交纳金额*/
    private String totalSum;
    /**核定本年应交纳党费金额*/
    private String yearApprovedMonthPartyfee;
    /**本年实际交纳金额*/
    private String yearCurrentPaymentAmount;
    /**应交多少人*/
    private String approvedNumber;
    /**实交多少人*/
    private String currentNumber;
    /**未交多少人*/
    private String unpaidNumber;
    private List<DfglEntity> dfglEntityList;
    private List<DfglEntity> dfglEntityListOne;
    private List<DfglEntity> dfglEntityListTwo;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }

    public String getPartyOrganizationName() {
        return partyOrganizationName;
    }

    public void setPartyOrganizationName(String partyOrganizationName) {
        this.partyOrganizationName = partyOrganizationName;
    }

    public String getPartyOrganizationId() {
        return partyOrganizationId;
    }

    public void setPartyOrganizationId(String partyOrganizationId) {
        this.partyOrganizationId = partyOrganizationId;
    }

    public String getAnnual() {
        return annual;
    }

    public void setAnnual(String annual) {
        this.annual = annual;
    }

    public String getMonthval() {
        return monthval;
    }

    public void setMonthval(String monthval) {
        this.monthval = monthval;
    }

    public String getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(String totalSum) {
        this.totalSum = totalSum;
    }


    public List<DfglEntity> getDfglEntityList() {
        return dfglEntityList;
    }

    public void setDfglEntityList(List<DfglEntity> dfglEntityList) {
        this.dfglEntityList = dfglEntityList;
    }

    public String getYearApprovedMonthPartyfee() {
        return yearApprovedMonthPartyfee;
    }

    public void setYearApprovedMonthPartyfee(String yearApprovedMonthPartyfee) {
        this.yearApprovedMonthPartyfee = yearApprovedMonthPartyfee;
    }

    public String getYearCurrentPaymentAmount() {
        return yearCurrentPaymentAmount;
    }

    public void setYearCurrentPaymentAmount(String yearCurrentPaymentAmount) {
        this.yearCurrentPaymentAmount = yearCurrentPaymentAmount;
    }

    public String getApprovedNumber() {
        return approvedNumber;
    }

    public void setApprovedNumber(String approvedNumber) {
        this.approvedNumber = approvedNumber;
    }

    public String getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(String currentNumber) {
        this.currentNumber = currentNumber;
    }

    public String getUnpaidNumber() {
        return unpaidNumber;
    }

    public void setUnpaidNumber(String unpaidNumber) {
        this.unpaidNumber = unpaidNumber;
    }

    public List<DfglEntity> getDfglEntityListOne() {
        return dfglEntityListOne;
    }

    public void setDfglEntityListOne(List<DfglEntity> dfglEntityListOne) {
        this.dfglEntityListOne = dfglEntityListOne;
    }

    public List<DfglEntity> getDfglEntityListTwo() {
        return dfglEntityListTwo;
    }

    public void setDfglEntityListTwo(List<DfglEntity> dfglEntityListTwo) {
        this.dfglEntityListTwo = dfglEntityListTwo;
    }

    public DzbDfgEntity(){
        super();
    }

    public DzbDfgEntity(String id, String superName, String partyOrganizationName, String partyOrganizationId, String annual, String monthval, String totalSum, String yearApprovedMonthPartyfee, String yearCurrentPaymentAmount, String approvedNumber, String currentNumber, String unpaidNumber, List<DfglEntity> dfglEntityList, List<DfglEntity> dfglEntityListOne, List<DfglEntity> dfglEntityListTwo) {
        this.id = id;
        this.superName = superName;
        this.partyOrganizationName = partyOrganizationName;
        this.partyOrganizationId = partyOrganizationId;
        this.annual = annual;
        this.monthval = monthval;
        this.totalSum = totalSum;
        this.yearApprovedMonthPartyfee = yearApprovedMonthPartyfee;
        this.yearCurrentPaymentAmount = yearCurrentPaymentAmount;
        this.approvedNumber = approvedNumber;
        this.currentNumber = currentNumber;
        this.unpaidNumber = unpaidNumber;
        this.dfglEntityList = dfglEntityList;
        this.dfglEntityListOne = dfglEntityListOne;
        this.dfglEntityListTwo = dfglEntityListTwo;
    }
}
