package cn.thinkjoy.gk.pojo;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by yangyongping on 2016/11/17.
 */
public class ExpertOrderDTO extends BaseDomain{
    /**服务名称**/
    private String serviceName;
    /**服务状态**/
    private String serviceState;
    /**学生姓名**/
    private String customer;
    /**服务时间**/
    private String serviceTime;
    /**学生Id**/
    private String customerId;
    /**服务形式(QQ,微信,智高考网站)**/
    private String serviceType;


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceState() {
        return serviceState;
    }

    public void setServiceState(String serviceState) {
        this.serviceState = serviceState;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
