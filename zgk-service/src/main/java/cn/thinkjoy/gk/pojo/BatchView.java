package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by douzy on 16/3/14.
 */
public class BatchView implements Serializable,Comparable<BatchView> {
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

    /**
     * 文科压线生追加分数
     */
    private Integer wenPlus;
    /**
     * 理科压线生追加分数
     */
    private Integer liPlus;

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
