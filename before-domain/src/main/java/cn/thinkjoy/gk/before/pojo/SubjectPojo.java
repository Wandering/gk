package cn.thinkjoy.gk.before.pojo;

import java.io.Serializable;

/**
 * Created by yhwang on 15/9/23.
 */
public class SubjectPojo implements Serializable{
    private Long id;
    private String subjectName;

    public  SubjectPojo(Long id,String subjectName){
        this.id = id;
        this.subjectName = subjectName;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
