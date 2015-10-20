package controller.pojo;

import java.io.Serializable;

/**
 * Created by yhwang on 15/9/28.
 */
public class CollegePojo implements Serializable{

    private Long m_university_code;//学校编码
    private String m_university_name;//学校名称

    public Long getM_university_code() {
        return m_university_code;
    }

    public void setM_university_code(Long m_university_code) {
        this.m_university_code = m_university_code;
    }

    public String getM_university_name() {
        return m_university_name;
    }

    public void setM_university_name(String m_university_name) {
        this.m_university_name = m_university_name;
    }
}
