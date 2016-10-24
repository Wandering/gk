package cn.thinkjoy.gk.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zuohao on 16/10/20.
 */
public class ServicePojo implements Serializable {

    private String expertId;
    private String serviceStyleId;
    private String serviceStyleName;
    private String serviceTypeId;
    private String serviceTypeName;
    private String servicePrice;

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public String getServiceStyleId() {
        return serviceStyleId;
    }

    public void setServiceStyleId(String serviceStyleId) {
        this.serviceStyleId = serviceStyleId;
    }

    public String getServiceStyleName() {
        return serviceStyleName;
    }

    public void setServiceStyleName(String serviceStyleName) {
        this.serviceStyleName = serviceStyleName;
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

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }
}
