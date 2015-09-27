package cn.thinkjoy.gk.dto;

import java.util.List;

/**
 * Created by wpliu on 15/9/26.
 */
public class BatchTypeDto {
    private Integer id;
    private String name;
    private List<SubjectTypeDto> subjectType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubjectTypeDto> getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(List<SubjectTypeDto> subjectType) {
        this.subjectType = subjectType;
    }
}
