package com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity;

/**
 * @Author 王富康
 * @Description //TODO 表字段信息实体类
 * @Date 2018/11/14 15:26
 * @Param
 * @return
 **/
public class TableStructDescription {

    /** 表名 */
    private String tableName;

    /** 中文名 */
    private String tableChnName;

    /** 字段名称 */
    private String columnName;

    /** 字段顺序 */
    private String columnOrder;

    /** 字段中文名 */
    private String columnChnName;

    /** 字段类别*/
    private String columnClass;

    /** 字段数据类型 */
    private String columnType;

    /** 字段长度 */
    private String columnWidth;

    /** 小数位数 */
    private String columnPoint;

    /** 是否为主键 */
    private String columnIsKey;

    /** 索引列名称 */
    private String columnIndexName;

    /** 字段是否可为空 */
    private String columnCanNull;

    /** 值是否可重复 */
    private String columnCanRepeat;

    /** 此字段在配置界面是否可见 */
    private String columnVisible;

    /** 字段格式 */
    private String columnFormat;

    /** 取值范围 */
    private String columnValueScale;

    /** 默认值 */
    private String columnValueDefault;

    /** 字段是否为涉密字段 */
    private String columnAsSecret;

    /** 值集继承 */
    private String columnInherit;

    /** 值自动加1 */
    private String columnAutoAdd;

    /** 文本输入 */
    private String columnInputType;

    /** 依赖字典 */
    private String columnSelectNo;

    /** 备注 */
    private String note;

    /** 分类查询字段 */
    private String columnAsCtgQuery;

    /** 简单查询字段 */
    private String columnAsSimquery;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableChnName() {
        return tableChnName;
    }

    public void setTableChnName(String tableChnName) {
        this.tableChnName = tableChnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnOrder() {
        return columnOrder;
    }

    public void setColumnOrder(String columnOrder) {
        this.columnOrder = columnOrder;
    }

    public String getColumnChnName() {
        return columnChnName;
    }

    public void setColumnChnName(String columnChnName) {
        this.columnChnName = columnChnName;
    }

    public String getColumnClass() {
        return columnClass;
    }

    public void setColumnClass(String columnClass) {
        this.columnClass = columnClass;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(String columnWidth) {
        this.columnWidth = columnWidth;
    }

    public String getColumnPoint() {
        return columnPoint;
    }

    public void setColumnPoint(String columnPoint) {
        this.columnPoint = columnPoint;
    }

    public String getColumnIsKey() {
        return columnIsKey;
    }

    public void setColumnIsKey(String columnIsKey) {
        this.columnIsKey = columnIsKey;
    }

    public String getColumnIndexName() {
        return columnIndexName;
    }

    public void setColumnIndexName(String columnIndexName) {
        this.columnIndexName = columnIndexName;
    }

    public String getColumnCanNull() {
        return columnCanNull;
    }

    public void setColumnCanNull(String columnCanNull) {
        this.columnCanNull = columnCanNull;
    }

    public String getColumnCanRepeat() {
        return columnCanRepeat;
    }

    public void setColumnCanRepeat(String columnCanRepeat) {
        this.columnCanRepeat = columnCanRepeat;
    }

    public String getColumnVisible() {
        return columnVisible;
    }

    public void setColumnVisible(String columnVisible) {
        this.columnVisible = columnVisible;
    }

    public String getColumnFormat() {
        return columnFormat;
    }

    public void setColumnFormat(String columnFormat) {
        this.columnFormat = columnFormat;
    }

    public String getColumnValueScale() {
        return columnValueScale;
    }

    public void setColumnValueScale(String columnValueScale) {
        this.columnValueScale = columnValueScale;
    }

    public String getColumnValueDefault() {
        return columnValueDefault;
    }

    public void setColumnValueDefault(String columnValueDefault) {
        this.columnValueDefault = columnValueDefault;
    }

    public String getColumnAsSecret() {
        return columnAsSecret;
    }

    public void setColumnAsSecret(String columnAsSecret) {
        this.columnAsSecret = columnAsSecret;
    }

    public String getColumnInherit() {
        return columnInherit;
    }

    public void setColumnInherit(String columnInherit) {
        this.columnInherit = columnInherit;
    }

    public String getColumnAutoAdd() {
        return columnAutoAdd;
    }

    public void setColumnAutoAdd(String columnAutoAdd) {
        this.columnAutoAdd = columnAutoAdd;
    }

    public String getColumnInputType() {
        return columnInputType;
    }

    public void setColumnInputType(String columnInputType) {
        this.columnInputType = columnInputType;
    }

    public String getColumnSelectNo() {
        return columnSelectNo;
    }

    public void setColumnSelectNo(String columnSelectNo) {
        this.columnSelectNo = columnSelectNo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getColumnAsCtgQuery() {
        return columnAsCtgQuery;
    }

    public void setColumnAsCtgQuery(String columnAsCtgQuery) {
        this.columnAsCtgQuery = columnAsCtgQuery;
    }

    public String getColumnAsSimquery() {
        return columnAsSimquery;
    }

    public void setColumnAsSimquery(String columnAsSimquery) {
        this.columnAsSimquery = columnAsSimquery;
    }
}
