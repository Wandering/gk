package cn.thinkjoy.gk.entity;

import cn.thinkjoy.zgk.domain.BaseDomain;

/**
 * Created by zuohao on 16/10/19.
 */
public class CommonQuestion extends BaseDomain {

    private String questionDesc;

    private String questionOrder;

    private String createDate;

    private String specialitys;

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public String getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(String questionOrder) {
        this.questionOrder = questionOrder;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSpecialitys() {
        return specialitys;
    }

    public void setSpecialitys(String specialitys) {
        this.specialitys = specialitys;
    }
}
