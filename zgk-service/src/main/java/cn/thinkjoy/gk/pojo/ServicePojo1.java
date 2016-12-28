package cn.thinkjoy.gk.pojo;

import java.util.List;

/**
 * Created by zuohao on 16/10/24.
 */
public class ServicePojo1{
    private String serviceStyleId;
    private String serviceStyleName;
    private List<ServicePojo2> serviceTypeList;

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

    public List<ServicePojo2> getServiceTypeList() {
        return serviceTypeList;
    }

    public void setServiceTypeList(List<ServicePojo2> serviceTypeList) {
        this.serviceTypeList = serviceTypeList;
    }
}