package com.sinosoft.sinoep.modules.dagl.xtpz.datacontrast.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

/**
 * @Author 王富康
 * @Description //TODO 对照字段数据的实体类
 * @Date 2018/11/13 20:24
 * @Param
 * @return
 **/
@Entity
@Table(name = "dagl_data_contrast")
public class DataContrast {

    /** 主键id */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 36)
    private String id;

    /** 关联关系id */
    @Column(name = "contrasting_id", length = 36)
    private String contrastingId;

    /** 源表字段 */
    @Column(name = "source_column", length = 50)
    private String sourceColumn;

    /** 目标表字段 */
    @Column(name = "target_column", length = 50)
    private String targetColumn;

    /** 源表字段类型 */
    @Column(name = "source_type", length = 10)
    private String sourceType;

    /** 目标表字段类型 */
    @Column(name = "target_type", length = 10)
    private String targetType;

    /** 源表字段长度 */
    @Column(name = "source_length", length = 10)
    private String sourceLength;

    /** 目标表字段长度 */
    @Column(name = "target_length", length = 10)
    private String targetLength;

    /** 源表字段中文名 */
    @Column(name = "source_column_chn_name", length = 50)
    private String sourceColumnChnName;

    /** 目标表字段中文名 */
    @Column(name = "target_column_chn_name", length = 50)
    private String targetColumnChnName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContrastingId() {
        return contrastingId;
    }

    public void setContrastingId(String contrastingId) {
        this.contrastingId = contrastingId;
    }

    public String getSourceColumn() {
        return sourceColumn;
    }

    public void setSourceColumn(String sourceColumn) {
        this.sourceColumn = sourceColumn;
    }

    public String getTargetColumn() {
        return targetColumn;
    }

    public void setTargetColumn(String targetColumn) {
        this.targetColumn = targetColumn;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getSourceLength() {
        return sourceLength;
    }

    public void setSourceLength(String sourceLength) {
        this.sourceLength = sourceLength;
    }

    public String getTargetLength() {
        return targetLength;
    }

    public void setTargetLength(String targetLength) {
        this.targetLength = targetLength;
    }

    public String getSourceColumnChnName() {
        return sourceColumnChnName;
    }

    public void setSourceColumnChnName(String sourceColumnChnName) {
        this.sourceColumnChnName = sourceColumnChnName;
    }

    public String getTargetColumnChnName() {
        return targetColumnChnName;
    }

    public void setTargetColumnChnName(String targetColumnChnName) {
        this.targetColumnChnName = targetColumnChnName;
    }

    public DataContrast() {
        super();
    }

    public DataContrast(String contrastingId, String sourceColumn, String targetColumn, String sourceType, String targetType, String sourceLength, String targetLength, String sourceColumnChnName, String targetColumnChnName) {
        super();
        this.contrastingId = contrastingId;
        this.sourceColumn = sourceColumn;
        this.targetColumn = targetColumn;
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.sourceLength = sourceLength;
        this.targetLength = targetLength;
        this.sourceColumnChnName = sourceColumnChnName;
        this.targetColumnChnName = targetColumnChnName;
    }
}
