package cn.thinkjoy.gk.pojo;

import java.io.Serializable;

/**
 * Created by yhwang on 15/9/23.
 */
public class SubjectPojo implements Serializable{
    private Long subjectId;
    private String subjectName;
    private long areaId;

    public  SubjectPojo(Long subjectId,String subjectName,long areaId){
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.areaId = areaId;
    }

    public long getAreaId(){
        return areaId;
    }

    public void setAreaId(long areaId){
        this.areaId = areaId;
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
