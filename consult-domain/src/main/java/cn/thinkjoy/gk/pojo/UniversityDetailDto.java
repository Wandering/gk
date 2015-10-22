package cn.thinkjoy.gk.pojo;

/**
 * Created by clei on 15/9/25.
 */
public class UniversityDetailDto {

    private String id;

    private String name;

    private String subjection;

    private String dictName;

    private String type;

    private Integer planNum;

    private Integer enrollNum;

    private String lowestScore;

    private String lowestRanking;

    private String averageScore;

    private String averageScoresRanking;

    private String enrollIntro;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjection() {
        return subjection;
    }

    public void setSubjection(String subjection) {
        this.subjection = subjection;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPlanNum() {
        return planNum;
    }

    public void setPlanNum(Integer planNum) {
        this.planNum = planNum;
    }

    public Integer getEnrollNum() {
        return enrollNum;
    }

    public void setEnrollNum(Integer enrollNum) {
        this.enrollNum = enrollNum;
    }

    public String getLowestScore() {
        return lowestScore;
    }

    public void setLowestScore(String lowestScore) {
        this.lowestScore = lowestScore;
    }

    public String getLowestRanking() {
        return lowestRanking;
    }

    public void setLowestRanking(String lowestRanking) {
        this.lowestRanking = lowestRanking;
    }

    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    public String getAverageScoresRanking() {
        return averageScoresRanking;
    }

    public void setAverageScoresRanking(String averageScoresRanking) {
        this.averageScoresRanking = averageScoresRanking;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getEnrollIntro() {
        return enrollIntro;
    }

    public void setEnrollIntro(String enrollIntro) {
        this.enrollIntro = enrollIntro;
    }
}
