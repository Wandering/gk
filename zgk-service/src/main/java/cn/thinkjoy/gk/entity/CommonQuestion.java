package cn.thinkjoy.gk.entity;

import cn.thinkjoy.zgk.domain.BaseDomain;

/**
 * Created by zuohao on 16/10/19.
 */
public class CommonQuestion extends BaseDomain {

    private String questionDesc;

    private String questionOrder;

    private String createDate;

    private String specialtys;

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

    public String getSpecialtys() {
        return specialtys;
    }

    public void setSpecialtys(String specialtys) {
        this.specialtys = specialtys;
    }
}
