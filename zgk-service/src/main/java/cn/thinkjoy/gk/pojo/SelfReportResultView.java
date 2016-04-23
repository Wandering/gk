package cn.thinkjoy.gk.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by douzy on 16/3/17.
 */
public class SelfReportResultView implements Serializable {
    /**
     * 梯度
     */
    private Integer sequence;

    /**
     * 院校集
     */
    private SelfReportUniversityView selfReportUniversityViewList;

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public SelfReportUniversityView getSelfReportUniversityViewList() {
        return selfReportUniversityViewList;
    }

    public void setSelfReportUniversityViewList(SelfReportUniversityView selfReportUniversityViewList) {
        this.selfReportUniversityViewList = selfReportUniversityViewList;
    }
}
