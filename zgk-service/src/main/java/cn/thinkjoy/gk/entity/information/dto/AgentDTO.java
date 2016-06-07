package cn.thinkjoy.gk.entity.information.dto;


import cn.thinkjoy.gk.entity.information.domain.Agent;

/**
 * Created by admin on 2015/12/16.
 */
public class AgentDTO extends Agent {
    private String province;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
