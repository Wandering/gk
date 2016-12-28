package cn.thinkjoy.gk.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuohao on 16/10/14.
 */
public class Bases implements Comparable<Bases>{
    private String name;
    private int majorNumber;
    private int schoolNumber;
    private List<String> schoolList=new ArrayList<>();

    public List<String> getSchoolList() {
        return schoolList;
    }
    public void setSchoolList(List<String> schoolList) {
        this.schoolList = schoolList;
    }
    public Bases(String name, int majorNumber, int schoolNumber){
        this.name=name;
        this.majorNumber=majorNumber;
        this.schoolNumber=schoolNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getMajorNumber() {
        return majorNumber;
    }
    public void setMajorNumber(int majorNumber) {
        this.majorNumber = majorNumber;
    }
    public int getSchoolNumber() {
        return schoolNumber;
    }
    public void setSchoolNumber(int schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public int compareTo(Bases o) {
        if(this.majorNumber>o.majorNumber){
            return -1;
        }else if(this.majorNumber<o.majorNumber){
            return 1;
        }
        return 0;
    }
}