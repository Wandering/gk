package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 *  智能填报主入口   参数POJO , 仅用于传递参数
 *
 * Created by douzy on 16/3/30.
 */
public class UniversityInfoParmasView implements Serializable {

    /**
     * 算法版本号  1: 线差&位次法   2: 排位法
     */
    private Integer version;
    /**
     * 分数
     */
    private Integer score;
    /**
     * 科类   1:文  2:理
     */
    private Integer categorie;
    /**
     * 省份code ~
     */
    private String province;
    /**
     * 批次  1 :一批 2: 二批 3: 高职 4:三批
     */
    private Integer batch;
    /**
     * 用户输入位次
     */
    private Integer precedence;
    /**
     * 专业优先or院校优先    0:专业 1:院校
     */
    private Integer first;


    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getCategorie() {
        return categorie;
    }

    public void setCategorie(Integer categorie) {
        this.categorie = categorie;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public Integer getPrecedence() {
        return precedence;
    }

    public void setPrecedence(Integer precedence) {
        this.precedence = precedence;
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }
}
