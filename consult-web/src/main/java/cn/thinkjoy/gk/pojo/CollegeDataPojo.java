package cn.thinkjoy.gk.pojo;

import java.util.List;

import java.io.Serializable;

/**
 * Created by yhwang on 15/9/28.
 */
public class CollegeDataPojo implements Serializable{
    private int status;
    private String description;
    private List<CollegePojo> data;

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

    public List<CollegePojo> getData() {
        return data;
    }

    public void setData(List<CollegePojo> data) {
        this.data = data;
    }
}
