package cn.thinkjoy.gk.pojo;

import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.gk.domain.ExpertUser;

/**
 * Created by yangyongping on 2016/11/16.
 */
public class ExpertUserDTO extends ExpertUser{
    //图片
    private String imageUrl;
    //用户名称
    private String expertName;
    //QQ
    private String qq;
    //微信
    private String weixin;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

}
