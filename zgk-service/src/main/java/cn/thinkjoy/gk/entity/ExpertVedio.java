package cn.thinkjoy.gk.entity;

import cn.thinkjoy.zgk.domain.BaseDomain;

/**
 * Created by zuohao on 16/10/19.
 */
public class ExpertVedio extends BaseDomain {

    private String expertId;
    private String vedioName;
    private String vedioUrl;
    private String createDate;

    public String getVedioName() {
        return vedioName;
    }

    public void setVedioName(String vedioName) {
        this.vedioName = vedioName;
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public String getVedioUrl() {
        return vedioUrl;
    }

    public void setVedioUrl(String vedioUrl) {
        this.vedioUrl = vedioUrl;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
