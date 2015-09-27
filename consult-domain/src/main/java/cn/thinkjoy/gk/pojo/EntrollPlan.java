package cn.thinkjoy.gk.pojo;

import java.util.List;

/**
 * Created by wpliu on 15/9/25.
 */
public class EntrollPlan {

    private String title;
    private List<PlanInfo> planInfos;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PlanInfo> getPlanInfos() {
        return planInfos;
    }

    public void setPlanInfos(List<PlanInfo> planInfos) {
        this.planInfos = planInfos;
    }
}
