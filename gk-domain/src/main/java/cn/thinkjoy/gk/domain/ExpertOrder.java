package cn.thinkjoy.gk.domain;

import java.io.Serializable;

/**
 * Created by liusven on 2016/10/19.
 */
public class ExpertOrder implements Serializable
{
    private Long id;
    private String orderNo;
    private String orderStatus;
    private String userId;
    private String expertId;
    private String channel;
    private String serverType;
    private String serverPrice;
    private String contactPerson;
    private String contactPhone;
    private String contactQq;
    private Long expectBeginDate;
    private Long expectEndDate;
    private Long createDate;
    private String returnUrl;

    public String getReturnUrl()
    {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl)
    {
        this.returnUrl = returnUrl;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getExpertId()
    {
        return expertId;
    }

    public void setExpertId(String expertId)
    {
        this.expertId = expertId;
    }

    public String getServerType()
    {
        return serverType;
    }

    public void setServerType(String serverType)
    {
        this.serverType = serverType;
    }

    public String getServerPrice()
    {
        return serverPrice;
    }

    public void setServerPrice(String serverPrice)
    {
        this.serverPrice = serverPrice;
    }

    public String getContactPerson()
    {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson)
    {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone()
    {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }

    public String getContactQq()
    {
        return contactQq;
    }

    public void setContactQq(String contactQq)
    {
        this.contactQq = contactQq;
    }

    public Long getExpectBeginDate()
    {
        return expectBeginDate;
    }

    public void setExpectBeginDate(Long expectBeginDate)
    {
        this.expectBeginDate = expectBeginDate;
    }

    public Long getExpectEndDate()
    {
        return expectEndDate;
    }

    public void setExpectEndDate(Long expectEndDate)
    {
        this.expectEndDate = expectEndDate;
    }

    public Long getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Long createDate)
    {
        this.createDate = createDate;
    }

    public String getChannel()
    {
        return channel;
    }

    public void setChannel(String channel)
    {
        this.channel = channel;
    }
}
