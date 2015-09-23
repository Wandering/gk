package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by clei on 15/1/9.
 */
public class QuestionDetailPojo implements Serializable{

    private String question;

    private Long expertUserId;

    private String expertUserName;

    private String expertUserIcon;

    private Long createTime;

    private String answer;

    private String isAnswer;

    private Long answerTime;

    private Long userId;

    private String userName;

    private String userIcon;

    private String disableExpertId;

    private Integer disableStatus;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Long answerTime) {
        this.answerTime = answerTime;
    }

    public Long getExpertUserId() {
        return expertUserId;
    }

    public void setExpertUserId(Long expertUserId) {
        this.expertUserId = expertUserId;
    }

    public String getExpertUserName() {
        return expertUserName;
    }

    public void setExpertUserName(String expertUserName) {
        this.expertUserName = expertUserName;
    }

    public String getExpertUserIcon() {
        return expertUserIcon;
    }

    public void setExpertUserIcon(String expertUserIcon) {
        this.expertUserIcon = expertUserIcon;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(String isAnswer) {
        this.isAnswer = isAnswer;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getDisableExpertId() {
        return disableExpertId;
    }

    public void setDisableExpertId(String disableExpertId) {
        this.disableExpertId = disableExpertId;
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
}
