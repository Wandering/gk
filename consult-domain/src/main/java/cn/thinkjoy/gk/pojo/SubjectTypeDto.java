package cn.thinkjoy.gk.pojo;

import java.util.List;
import java.util.Map;

/**
 * Created by wpliu on 15/9/26.
 */
public class SubjectTypeDto {

    private Long id;
    private String name;
    private List<Map<String,Object>> majoredType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<String, Object>> getMajoredType() {
        return majoredType;
    }

    public void setMajoredType(List<Map<String, Object>> majoredType) {
        this.majoredType = majoredType;
    }
}
