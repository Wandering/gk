package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by douzy on 16/3/14.
 */
public class BatchView implements Serializable,Comparable<BatchView> {
    /**
     * 批次
     */
    private String batch;
    /**
     * 文科控制线
     */
    private String wenLine;
    /**
     * 理科控制线
     */
    private String liLine;

    /**
     * 文科压线生追加分数
     */
    private Integer wenPlus;
    /**
     * 理科压线生追加分数
     */
    private Integer liPlus;

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getWenLine() {
        return wenLine;
    }

    public void setWenLine(String wenLine) {
        this.wenLine = wenLine;
    }

    public String getLiLine() {
        return liLine;
    }

    public void setLiLine(String liLine) {
        this.liLine = liLine;
    }

    public Integer getWenPlus() {
        return wenPlus;
    }

    public void setWenPlus(Integer wenPlus) {
        this.wenPlus = wenPlus;
    }

    public Integer getLiPlus() {
        return liPlus;
    }

    public void setLiPlus(Integer liPlus) {
        this.liPlus = liPlus;
    }

    @Override
    public int compareTo(BatchView batchView) {
        return batchView.getWenLine().compareTo(this.wenLine);
    }
}
