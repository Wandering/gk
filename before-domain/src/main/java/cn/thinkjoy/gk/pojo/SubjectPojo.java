package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by yhwang on 15/9/23.
 */
public class SubjectPojo implements Serializable{
    private Long subjectId;
    private String subjectName;

    public  SubjectPojo(Long subjectId,String subjectName){
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
