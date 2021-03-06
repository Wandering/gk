package cn.thinkjoy.gk.entity;

import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.io.Serializable;

/**
 * Created by douzy on 16/3/25.
 */
public class RankingRoleParmas extends CreateBaseDomain<Long> implements Serializable {
    private Long id;
    /**
     * 对应位次段下标 从0 开始, 对应zgk_system_parmas表GX_VOLUNTEER_BATCH_PRECEDENCE
     */
    private Integer precedenceIndex;
    /**
     * 0:专业优先 1:院校优先
     */
    private byte whoFirst;
    /**
     * 搜索维度 0:院校排名  1：录取率
     */
    private byte whoDim;
    /**
     * 省份code
     */
    private String provinceCode;

    /**
     * 1：文 2：理
     */
    private byte majorType;
    /**
     * 院校排名规则参数
     */
    private String rankRuleParmas;
    /**
     * 录取率规则
     */
    private String enrollingRuleParmas;
    /**
     * 规则段limit
     */
    private String limitParmas;
    /**
     * 0:asc 1:desc
     */
    private byte orderParmas;

    /**
     * 0:位次法  1:分数补充法
     */
    private byte isScore;

    /**
     * 策略批次
     */
    private String batch;

    /**
     * 选择批次
     */
    private String chkBatch;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrecedenceIndex() {
        return precedenceIndex;
    }

    public void setPrecedenceIndex(Integer precedenceIndex) {
        this.precedenceIndex = precedenceIndex;
    }

    public byte getWhoFirst() {
        return whoFirst;
    }

    public void setWhoFirst(byte whoFirst) {
        this.whoFirst = whoFirst;
    }

    public byte getWhoDim() {
        return whoDim;
    }

    public void setWhoDim(byte whoDim) {
        this.whoDim = whoDim;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public byte getMajorType() {
        return majorType;
    }

    public void setMajorType(byte majorType) {
        this.majorType = majorType;
    }

    public String getRankRuleParmas() {
        return rankRuleParmas;
    }

    public void setRankRuleParmas(String rankRuleParmas) {
        this.rankRuleParmas = rankRuleParmas;
    }

    public String getEnrollingRuleParmas() {
        return enrollingRuleParmas;
    }

    public void setEnrollingRuleParmas(String enrollingRuleParmas) {
        this.enrollingRuleParmas = enrollingRuleParmas;
    }

    public String getLimitParmas() {
        return limitParmas;
    }

    public void setLimitParmas(String limitParmas) {
        this.limitParmas = limitParmas;
    }

    public byte getOrderParmas() {
        return orderParmas;
    }

    public void setOrderParmas(byte orderParmas) {
        this.orderParmas = orderParmas;
    }

    public byte getIsScore() {
        return isScore;
    }

    public void setIsScore(byte isScore) {
        this.isScore = isScore;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getChkBatch() {
        return chkBatch;
    }

    public void setChkBatch(String chkBatch) {
        this.chkBatch = chkBatch;
    }
}
