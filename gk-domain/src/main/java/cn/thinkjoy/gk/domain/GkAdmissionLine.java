package cn.thinkjoy.gk.domain;

import cn.thinkjoy.common.domain.BaseDomain;

import java.util.Map;

/**
 * Created by admin on 2016/1/14.
 */
public class GkAdmissionLine extends BaseDomain {
    /**院校名称**/
    private String name;
    /** 学校是否为985，211等 */
    private String property;
    /**专业类别**/
    private String typename;
    /** 年份 */
    private Integer year;
    /**批次**/
    private String batchname;
    /** 隶属 */
    private String subjection;
    /** 录取最高分 */
    private Integer highestScore;
    /** 录取平均分 */
    private Integer averageScore;
    /** 录取最低分 */
    private Integer lowestScore;

    private Map<String,Object> propertys;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getBatchname() {
        return batchname;
    }

    public void setBatchname(String batchname) {
        this.batchname = batchname;
    }

    public String getSubjection() {
        return subjection;
    }

    public void setSubjection(String subjection) {
        this.subjection = subjection;
    }

    public Integer getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(Integer highestScore) {
        this.highestScore = highestScore;
    }

    public Integer getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Integer averageScore) {
        this.averageScore = averageScore;
    }

    public Integer getLowestScore() {
        return lowestScore;
    }

    public void setLowestScore(Integer lowestScore) {
        this.lowestScore = lowestScore;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Map<String, Object> getPropertys() {
        return propertys;
    }

    public void setPropertys(Map<String, Object> propertys) {
        this.propertys = propertys;
    }
}
