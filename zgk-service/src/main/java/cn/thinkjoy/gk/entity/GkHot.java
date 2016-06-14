package cn.thinkjoy.gk.entity;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by admin on 2016/1/4.
 */
public class GkHot extends BaseDomain {
    /**标题**/
    private String title;
    /**摘要**/
    private String subContent;
    /**内容是一个url**/
    private String content;
    /**热点时间**/
    private String hotDate;
    /**热点图片**/
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubContent() {
        return subContent;
    }

    public void setSubContent(String subContent) {
        this.subContent = subContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHotDate() {
        return hotDate;
    }

    public void setHotDate(String hotDate) {
        this.hotDate = hotDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
