package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by douzy on 16/3/17.
 */
public class SelfReportResultView implements Serializable {
    /**
     * 梯度
     */
    private Integer sequence;

//    /**
//     * 梯度title
//     */
//    private String title;

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

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }

    public SelfReportUniversityView getSelfReportUniversityViewList() {
        return selfReportUniversityViewList;
    }

    public void setSelfReportUniversityViewList(SelfReportUniversityView selfReportUniversityViewList) {
        this.selfReportUniversityViewList = selfReportUniversityViewList;
    }
}
