package cn.thinkjoy.gk.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by clei on 15/1/9.
 */
public class QuestionDto extends UserCommonDto implements Serializable {

    private Long questionId;

    private List<QuestionContentDto> questions;

    private Long createTime;

    private Integer isAnswer;

    private Integer isOpen;

    private Long answerTime;

    private Integer freeStatus;

    private Integer disableStatus;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public List<QuestionContentDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionContentDto> questions) {
        this.questions = questions;
    }

    public Long getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Long answerTime) {
        this.answerTime = answerTime;
    }

    public Integer getFreeStatus() {
        return freeStatus;
    }

    public void setFreeStatus(Integer freeStatus) {
        this.freeStatus = freeStatus;
    }

    public Integer getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(Integer isAnswer) {
        this.isAnswer = isAnswer;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getDisableStatus() {
        return disableStatus;
    }

    public void setDisableStatus(Integer disableStatus) {
        this.disableStatus = disableStatus;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }
}
