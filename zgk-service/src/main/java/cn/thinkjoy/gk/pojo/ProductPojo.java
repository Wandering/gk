package cn.thinkjoy.gk.pojo;

import cn.thinkjoy.gk.domain.ExpertProductService;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zuohao on 16/12/23.
 */
public class ProductPojo implements Serializable{

    private String productId;
    private String productName;
    private String price;
    private String cardBusinessType;
    private String cardOfficial;
    private String icon;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private List<ExpertProductService> expertProductServiceList;

    public String getCardBusinessType() {
        return cardBusinessType;
    }

    public void setCardBusinessType(String cardBusinessType) {
        this.cardBusinessType = cardBusinessType;
    }

    public String getCardOfficial() {
        return cardOfficial;
    }

    public void setCardOfficial(String cardOfficial) {
        this.cardOfficial = cardOfficial;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<ExpertProductService> getExpertProductServiceList() {
        return expertProductServiceList;
    }

    public void setExpertProductServiceList(List<ExpertProductService> expertProductServiceList) {
        this.expertProductServiceList = expertProductServiceList;
    }
}
