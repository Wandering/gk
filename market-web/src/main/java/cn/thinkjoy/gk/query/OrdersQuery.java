package cn.thinkjoy.gk.query;

import java.io.Serializable;

/**
 * Created by clei on 15/7/2.
 */
public class OrdersQuery implements Serializable{
   
    private String channel;
    private String products;
    private String extra;
    private String returnUrl;
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}