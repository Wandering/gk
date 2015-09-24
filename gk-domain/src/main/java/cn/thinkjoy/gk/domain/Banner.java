package cn.thinkjoy.gk.domain;


import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by ZL on 2015/9/23.
 */
public class Banner extends BaseDomain{
    private Integer type;
    private String linkUrl;
    private String imageUrl;

    public Integer getType(){return type;}

    public void setType(Integer type) {this.type = type;}

    public String getLinkUrl(){return linkUrl;}

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getImageUrl() {return imageUrl;}

    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}
}
