package cn.thinkjoy.gk.entity;

import cn.thinkjoy.zgk.domain.BaseDomain;

/**
 * Created by zuohao on 16/10/20.
 */
public class UserCommonQuestion extends BaseDomain {

    private String userId;
    private String commonQuestionId;
    private String createDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommonQuestionId() {
        return commonQuestionId;
    }

    public void setCommonQuestionId(String commonQuestionId) {
        this.commonQuestionId = commonQuestionId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
