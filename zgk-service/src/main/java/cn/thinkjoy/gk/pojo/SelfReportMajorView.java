package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * 专业
 * Created by douzy on 16/3/17.
 */
public class SelfReportMajorView implements Serializable {
    /**
     * 专业ID
     */
    private Integer id;
    /**
     * 专业名称
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
