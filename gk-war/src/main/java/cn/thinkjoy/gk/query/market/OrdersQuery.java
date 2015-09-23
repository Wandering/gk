package cn.thinkjoy.gk.query.market;

import java.io.Serializable;

/**
 * Created by clei on 15/7/2.
 */
public class OrdersQuery implements Serializable{
   
    private String channel;
    private String remoteIp;
    private String products;
    private String userId;
    private String extra;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
