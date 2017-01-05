package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by zuohao on 16/9/19.
 */
public class ScoreAnalysisNumberPojo implements Serializable {

    private String analysisNumber;
    private String startRanking;
    private String endRanking;
    private String recommendUniversity;
    private String favoriteUniversity;

    public String getAnalysisNumber() {
        return analysisNumber;
    }

    public void setAnalysisNumber(String analysisNumber) {
        this.analysisNumber = analysisNumber;
    }

    public String getStartRanking() {
        return startRanking;
    }

    public void setStartRanking(String startRanking) {
        this.startRanking = startRanking;
    }

    public String getEndRanking() {
        return endRanking;
    }

    public void setEndRanking(String endRanking) {
        this.endRanking = endRanking;
    }

    public String getRecommendUniversity() {
        return recommendUniversity;
    }

    public void setRecommendUniversity(String recommendUniversity) {
        this.recommendUniversity = recommendUniversity;
    }

    public String getFavoriteUniversity() {
        return favoriteUniversity;
    }

    public void setFavoriteUniversity(String favoriteUniversity) {
        this.favoriteUniversity = favoriteUniversity;
    }
}
