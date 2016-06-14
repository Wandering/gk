package cn.thinkjoy.gk.entity;

import java.io.Serializable;

/**
 * Created by douzy on 16/5/17.
 */
public class ScoreMaxMin implements Serializable {
    /**
     * 最大分数
     */
    private Integer maxScore;
    /**
     * 最小分数
     */
    private Integer minScore;

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }
}
