package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by zuohao on 16/9/21.
 */
public class SexPercentPojo implements Serializable {

    private String sexName;

    private String percent;

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
