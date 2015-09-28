package cn.thinkjoy.gk.pojo;

import java.util.List;

/**
 * Created by wpliu on 15/9/25.
 */
public class UniversityResponseDto {
    private Integer schoolCount;
    private Integer currentPage;
    private List<UniversityDto>  schoolList;

    public Integer getSchoolCount() {
        return schoolCount;
    }

    public void setSchoolCount(Integer schoolCount) {
        this.schoolCount = schoolCount;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<UniversityDto> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<UniversityDto> schoolList) {
        this.schoolList = schoolList;
    }
}
