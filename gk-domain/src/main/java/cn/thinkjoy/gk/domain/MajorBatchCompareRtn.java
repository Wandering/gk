package cn.thinkjoy.gk.domain;

/**
 * Created by yangyongping on 16/9/19.
 */
public class MajorBatchCompareRtn {
    //批次
    private Integer batch;
    //批次名称
    private String batchName;
    //人数
    private Integer num;

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }
}
