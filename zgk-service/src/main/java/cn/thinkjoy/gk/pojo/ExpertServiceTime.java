package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by yangguorong on 16/11/2.
 */
public class ExpertServiceTime implements Serializable {

    private int id;
    /**
     * 日期ID
     */
    private int expertDayId;
    /**
     * 时间段
     */
    private String timeSegment;
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

    public int getExpertDayId() {
        return expertDayId;
    }

    public void setExpertDayId(int expertDayId) {
        this.expertDayId = expertDayId;
    }

    public String getTimeSegment() {
        return timeSegment;
    }

    public void setTimeSegment(String timeSegment) {
        this.timeSegment = timeSegment;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "ExpertServiceTime{" +
                "id=" + id +
                ", expertDayId=" + expertDayId +
                ", timeSegment='" + timeSegment + '\'' +
                ", isAvailable='" + isAvailable + '\'' +
                '}';
    }
}
