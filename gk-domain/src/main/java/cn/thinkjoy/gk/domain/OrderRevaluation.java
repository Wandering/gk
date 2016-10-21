package cn.thinkjoy.gk.domain;

/**
 * Created by liusven on 2016/10/21.
 */
public class OrderRevaluation
{
    private String id;
    private String orderNo;
    private String evaluation;
    private String createDate;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
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

    public String getEvaluation()
    {
        return evaluation;
    }

    public void setEvaluation(String evaluation)
    {
        this.evaluation = evaluation;
    }

    public String getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }
}
