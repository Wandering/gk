package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by clei on 15/1/9.
 */
public class AnswerPojo implements Serializable{

    private Long id;

    private String question;

    private Integer freeStatus;

    private Long createTime;

    private Long answerTime;

    private Integer isAnswer;

    private Integer isOpen;

    private Long userId;

    private String userName;

    private String userIcon;

    private Integer disableStatus;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getFreeStatus() {
        return freeStatus;
    }

    public void setFreeStatus(Integer freeStatus) {
        this.freeStatus = freeStatus;
    }

    public Long getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Long answerTime) {
        this.answerTime = answerTime;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }
}
