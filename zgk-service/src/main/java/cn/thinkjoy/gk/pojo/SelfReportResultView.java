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
    private List<SelfReportUniversityView> selfReportUniversityViewList;

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public List<SelfReportUniversityView> getSelfReportUniversityViewList() {
        return selfReportUniversityViewList;
    }

    public void setSelfReportUniversityViewList(List<SelfReportUniversityView> selfReportUniversityViewList) {
        this.selfReportUniversityViewList = selfReportUniversityViewList;
    }
}
