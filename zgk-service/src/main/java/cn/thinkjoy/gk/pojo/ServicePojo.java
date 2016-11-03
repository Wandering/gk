package cn.thinkjoy.gk.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zuohao on 16/10/20.
 */
public class ServicePojo implements Serializable {

    private String expertId;
    private String serviceTypeId;
    private String serviceTypeName;

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

}
