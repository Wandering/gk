package cn.thinkjoy.gk.domain;

import cn.thinkjoy.common.domain.BaseDomain;

import java.util.List;

/**
 * Created by yangyongping on 16/9/19.
 */
public class Evaluation extends BaseDomain {
    //物理
    private Integer  wl;
    //化学
    private Integer  hx;
    //生物
    private Integer  sw;
    //地理
    private Integer  dl;
    //历史
    private Integer  ls;
    //政治
    private Integer  zz;
    //通用技术
    private Integer  ty;
    //时间
    private Long cdate;
    //用户id
    private Long userId;
    //测评结果(保留)
    private String details;

    public Integer getWl() {
        return wl;
    }

    public void setWl(Integer wl) {
        this.wl = wl;
    }

    public Integer getHx() {
        return hx;
    }

    public void setHx(Integer hx) {
        this.hx = hx;
    }

    public Integer getSw() {
        return sw;
    }

    public void setSw(Integer sw) {
        this.sw = sw;
    }

    public Integer getDl() {
        return dl;
    }

    public void setDl(Integer dl) {
        this.dl = dl;
    }

    public Integer getLs() {
        return ls;
    }

    public void setLs(Integer ls) {
        this.ls = ls;
    }

    public Integer getZz() {
        return zz;
    }

    public void setZz(Integer zz) {
        this.zz = zz;
    }

    public Integer getTy() {
        return ty;
    }

    public void setTy(Integer ty) {
        this.ty = ty;
    }

    public void setCdate(Long cdate) {
        this.cdate = cdate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCdate() {
        return cdate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
