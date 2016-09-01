package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by douzy on 16/8/30.
 */
public class ReportForecastView implements Serializable {
    /**
     * 批次
     */
    private String batch;
    /**
     * 分数
     */
    private Integer score;
    /**
     * 省份
     */
    private String province;
    /**
     * 文 理
     */
    private Integer categorie;

    /**
     * 位次
     */
    private Integer precedence;

    /**
     * 线差
     */
    private Integer scoreDiff;
    /**
     * 院校ID
     */
    private Integer uid;

    /**
     * 排序
     */
    private String orderBy;

    /**
     * limit
     */
    private Integer limit;

    /**
     * 是否存在表关联
     */
    private boolean isJoin;

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getCategorie() {
        return categorie;
    }

    public void setCategorie(Integer categorie) {
        this.categorie = categorie;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPrecedence() {
        return precedence;
    }

    public void setPrecedence(Integer precedence) {
        this.precedence = precedence;
    }

    public Integer getScoreDiff() {
        return scoreDiff;
    }

    public void setScoreDiff(Integer scoreDiff) {
        this.scoreDiff = scoreDiff;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public boolean isJoin() {
        return isJoin;
    }

    public void setJoin(boolean isJoin) {
        this.isJoin = isJoin;
    }
}
