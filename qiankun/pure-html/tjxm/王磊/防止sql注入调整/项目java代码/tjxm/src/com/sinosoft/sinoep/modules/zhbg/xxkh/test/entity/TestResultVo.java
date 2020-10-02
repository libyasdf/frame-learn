package com.sinosoft.sinoep.modules.zhbg.xxkh.test.entity;

import com.sinosoft.sinoep.modules.zhbg.xxkh.question.entity.Option;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 王富康
 * @Description //TODO 提交试卷后获取用户答题的结果拼接到试卷的vo
 * @Date 2018/9/13 20:44
 * @Param
 * @return
 **/
public class TestResultVo {

    /** 试题id */
    private String id;

    /** 创建人ID（也是申请人ID字段） */
    private String creUserId;

    /** 创建人名称（也是申请人名称字段） */
    private String creUserName;

    /** 创建部门ID */
    private String creDeptId;

    /** 创建部门名 */
    private String creDeptName;

    /** 创建人处室ID */
    private String creChushiId;

    /** 创建人处室名*/
    private String creChushiName;

    /** 创建人二级局ID */
    private String creJuId;

    /** 创建人二级局名 */
    private String creJuName;

    /** 逻辑删除 */
    private String visible;

    /** 创建时间 */
    private String creTime;

    /** 最后更新人ID */
    private String updateUserId;

    /** 最后更新人名 */
    private String updateUserName;

    /** 最后更新时间 */
    private String updateTime;

    /** 试题所属大类*/
    private String type;

    /** 试题所属小类*/
    private String nodeId;

    /** 难易程度  1：简单；2：一般；3：困难*/
    private String difficultyLevel;

    /** 题型 1：单选；2：多选；3：判断；4：填空；5：问答*/
    private String questionType;

    /** 题干,试题描述*/
    private String describe;

    /** 答案解析*/
    private String analysis;

    /** 试题状态 0：草稿；1：发布*/
    private String state;

    /** 操作 */
    private String cz = "";

    /** 是否被选中*/
    private  String isCheck;

    /** 答题管理的id*/
    private String answerId;

    /** 答题人填空，简答的答案*/
    private String optionContent;

    /** 答题人单选，多选，判断的答案*/
    private String optionId;

    /** 答题得分*/
    private String score;

    /** 试题对应的选项*/
    @Transient
    List<Option> list = new ArrayList<Option>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreUserId() {
        return creUserId;
    }

    public void setCreUserId(String creUserId) {
        this.creUserId = creUserId;
    }

    public String getCreUserName() {
        return creUserName;
    }

    public void setCreUserName(String creUserName) {
        this.creUserName = creUserName;
    }

    public String getCreDeptId() {
        return creDeptId;
    }

    public void setCreDeptId(String creDeptId) {
        this.creDeptId = creDeptId;
    }

    public String getCreDeptName() {
        return creDeptName;
    }

    public void setCreDeptName(String creDeptName) {
        this.creDeptName = creDeptName;
    }

    public String getCreChushiId() {
        return creChushiId;
    }

    public void setCreChushiId(String creChushiId) {
        this.creChushiId = creChushiId;
    }

    public String getCreChushiName() {
        return creChushiName;
    }

    public void setCreChushiName(String creChushiName) {
        this.creChushiName = creChushiName;
    }

    public String getCreJuId() {
        return creJuId;
    }

    public void setCreJuId(String creJuId) {
        this.creJuId = creJuId;
    }

    public String getCreJuName() {
        return creJuName;
    }

    public void setCreJuName(String creJuName) {
        this.creJuName = creJuName;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getCreTime() {
        return creTime;
    }

    public void setCreTime(String creTime) {
        this.creTime = creTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCz() {
        return cz;
    }

    public void setCz(String cz) {
        this.cz = cz;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<Option> getList() {
        return list;
    }

    public void setList(List<Option> list) {
        this.list = list;
    }
}
