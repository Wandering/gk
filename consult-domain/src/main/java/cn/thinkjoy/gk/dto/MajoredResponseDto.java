package cn.thinkjoy.gk.dto;

import java.util.List;

/**
 * Created by wpliu on 15/9/25.
 */
public class MajoredResponseDto {

    private Integer pageCount;
    private Integer currentPage;
    private List<Subject> subjectList;

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }
}
