package cn.thinkjoy.gk.entity;

/**
 * Created by yangguorong on 16/11/23.
 */
public class ExpertChannelRequest {

    private String expertId;
    private String stuId;
    private int type;

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
