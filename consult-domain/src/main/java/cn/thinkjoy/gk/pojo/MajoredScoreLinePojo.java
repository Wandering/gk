package cn.thinkjoy.gk.pojo;

/**
 * Created by zuohao on 15/11/9.
 */
public class MajoredScoreLinePojo {
    /** 学校Id */
    private long universityId;
    /** 专业Id */
    private long majoredId;
    /** 年份 */
    private String year;
    /** 批次 */
    private String enrollBatch;
    /** 科类 */
    private String subject;
    /** TODO 录取数，暂时没有提供 */
    private int enrollNumber;
    /** 录取最高分 */
    private int highestScore;
    /** TODO 录取最低分，暂时没有提供 */
    private int lowestScore;
    /** 录取平均分 */
    private int averageScore;

    public long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(long universityId) {
        this.universityId = universityId;
    }

    public long getMajoredId() {
        return majoredId;
    }

    public void setMajoredId(long majoredId) {
        this.majoredId = majoredId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEnrollBatch() {
        return enrollBatch;
    }

    public void setEnrollBatch(String enrollBatch) {
        this.enrollBatch = enrollBatch;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getEnrollNumber() {
        return enrollNumber;
    }

    public void setEnrollNumber(int enrollNumber) {
        this.enrollNumber = enrollNumber;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public int getLowestScore() {
        return lowestScore;
    }

    public void setLowestScore(int lowestScore) {
        this.lowestScore = lowestScore;
    }

    public int getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(int averageScore) {
        this.averageScore = averageScore;
    }
}
