package cn.thinkjoy.gk.pojo;

import java.util.List;

/**
 * Created by wpliu on 15/9/25.
 */
public class MajoredDetailDto {

    private String similarMajor;
    private String mainCourse;
    private String workGuide;
    private List<MajoredRankDto> openUniversity;

    public String getSimilarMajor() {
        return similarMajor;
    }

    public void setSimilarMajor(String similarMajor) {
        this.similarMajor = similarMajor;
    }

    public String getMainCourse() {
        return mainCourse;
    }

    public void setMainCourse(String mainCourse) {
        this.mainCourse = mainCourse;
    }

    public String getWorkGuide() {
        return workGuide;
    }

    public void setWorkGuide(String workGuide) {
        this.workGuide = workGuide;
    }

    public List<MajoredRankDto> getOpenUniversity() {
        return openUniversity;
    }

    public void setOpenUniversity(List<MajoredRankDto> openUniversity) {
        this.openUniversity = openUniversity;
    }
}
