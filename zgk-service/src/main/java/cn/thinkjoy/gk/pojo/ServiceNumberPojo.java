package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by zuohao on 16/10/20.
 */
public class ServiceNumberPojo implements Serializable {

    private String serviceId;
    private int serviceNumber;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public int getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(int serviceNumber) {
        this.serviceNumber = serviceNumber;
    }
}
