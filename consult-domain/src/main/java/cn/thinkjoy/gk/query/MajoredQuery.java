package cn.thinkjoy.gk.query;

/**
 * Created by wpliu on 15/9/25.
 */
public class MajoredQuery {
    private Integer batchTypeId;
    private String batchTypeName;
    private Integer subjectTypeId;
    private String subjectTypeName;
    private Integer majoredTypeId;
    private String majoredTypeName;
    private String searchName;
    private Integer pageNo;
    private Integer pageSize;

    public Integer getBatchTypeId() {
        return batchTypeId;
    }

    public void setBatchTypeId(Integer batchTypeId) {
        this.batchTypeId = batchTypeId;
    }

    public String getBatchTypeName() {
        return batchTypeName;
    }

    public void setBatchTypeName(String batchTypeName) {
        this.batchTypeName = batchTypeName;
    }

    public Integer getSubjectTypeId() {
        return subjectTypeId;
    }

    public void setSubjectTypeId(Integer subjectTypeId) {
        this.subjectTypeId = subjectTypeId;
    }

    public String getSubjectTypeName() {
        return subjectTypeName;
    }

    public void setSubjectTypeName(String subjectTypeName) {
        this.subjectTypeName = subjectTypeName;
    }

    public Integer getMajoredTypeId() {
        return majoredTypeId;
    }

    public void setMajoredTypeId(Integer majoredTypeId) {
        this.majoredTypeId = majoredTypeId;
    }

    public String getMajoredTypeName() {
        return majoredTypeName;
    }

    public void setMajoredTypeName(String majoredTypeName) {
        this.majoredTypeName = majoredTypeName;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
