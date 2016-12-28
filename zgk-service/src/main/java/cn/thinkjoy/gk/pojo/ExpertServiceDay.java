package cn.thinkjoy.gk.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangguorong on 16/11/2.
 */
public class ExpertServiceDay implements Serializable {

    private int id;
    /**
     * 专家ID
     */
    private int expertId;
    /**
     * 服务时间
     */
    private Date serviceDay;
    /**
     * 是否可选 1：可选，2：不可选
     */
    private String isAvailable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExpertId() {
        return expertId;
    }

    public void setExpertId(int expertId) {
        this.expertId = expertId;
    }

    public Date getServiceDay() {
        return serviceDay;
    }

    public void setServiceDay(Date serviceDay) {
        this.serviceDay = serviceDay;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "ExpertServiceDay{" +
                "id=" + id +
                ", expertId=" + expertId +
                ", serviceDay=" + serviceDay +
                ", isAvailable='" + isAvailable + '\'' +
                '}';
    }
}
