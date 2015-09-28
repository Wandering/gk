package cn.thinkjoy.gk.controller.before.pojo;

import java.util.List;

import java.io.Serializable;

/**
 * Created by yhwang on 15/9/28.
 */
public class ResultDataPojo implements Serializable{
    private int status;
    private String description;
    private List<CollegeDataPojo> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CollegeDataPojo> getData() {
        return data;
    }

    public void setData(List<CollegeDataPojo> data) {
        this.data = data;
    }
}
