package cn.thinkjoy.gk.pojo;

import cn.thinkjoy.gk.domain.ExpertReservationOrderDetail;

/**
 * Created by yangyongping on 2016/11/2.
 */
public class ExpertReservationOrderDetailDTO extends ExpertReservationOrderDetail {
    private String expertName;
    private String serviceName;
    private Integer isInto;

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getIsInto() {
        return isInto;
    }

    public void setIsInto(Integer isInto) {
        this.isInto = isInto;
    }
}
