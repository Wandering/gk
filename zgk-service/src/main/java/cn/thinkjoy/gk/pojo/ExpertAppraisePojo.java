package cn.thinkjoy.gk.pojo;

import cn.thinkjoy.zgk.domain.BaseDomain;

/**
 * Created by zuohao on 16/10/19.
 */
public class ExpertAppraisePojo extends BaseDomain {

    private String expertId;
    private String expertName;
    private String userId;
    private String userName;
    private String serverTypeName;
    private String userComments;
    private String isChecked;
    private String createDate;
    private String rate;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getServerTypeName() {
        return serverTypeName;
    }

    public void setServerTypeName(String serverTypeName) {
        this.serverTypeName = serverTypeName;
    }

    public String getUserComments() {
        return userComments;
    }

    public void setUserComments(String userComments) {
        this.userComments = userComments;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
