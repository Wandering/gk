package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by douzy on 16/3/14.
 */
public class BatchView implements Serializable {
    /**
     * 批次
     */
    private Integer batch;
    /**
     * 文科控制线
     */
    private Integer wenLine;
    /**
     * 理科控制线
     */
    private Integer liLine;

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public Integer getWenLine() {
        return wenLine;
    }

    public void setWenLine(Integer wenLine) {
        this.wenLine = wenLine;
    }

    public Integer getLiLine() {
        return liLine;
    }

    public void setLiLine(Integer liLine) {
        this.liLine = liLine;
    }
}
