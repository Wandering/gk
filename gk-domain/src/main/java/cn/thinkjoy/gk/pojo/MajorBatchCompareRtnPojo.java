package cn.thinkjoy.gk.pojo;

import cn.thinkjoy.gk.domain.MajorBatchCompareRtn;

import java.util.List;

/**
 * Created by yangyongping on 16/9/19.
 */
public class MajorBatchCompareRtnPojo {
    //选课1的批次信息
    List<MajorBatchCompareRtn> majorBatchCompareRtns1;
    //选课2的批次信息
    List<MajorBatchCompareRtn> majorBatchCompareRtns2;


    public List<MajorBatchCompareRtn> getMajorBatchCompareRtns1() {
        return majorBatchCompareRtns1;
    }

    public void setMajorBatchCompareRtns1(List<MajorBatchCompareRtn> majorBatchCompareRtns1) {
        this.majorBatchCompareRtns1 = majorBatchCompareRtns1;
    }

    public List<MajorBatchCompareRtn> getMajorBatchCompareRtns2() {
        return majorBatchCompareRtns2;
    }

    public void setMajorBatchCompareRtns2(List<MajorBatchCompareRtn> majorBatchCompareRtns2) {
        this.majorBatchCompareRtns2 = majorBatchCompareRtns2;
    }
}
