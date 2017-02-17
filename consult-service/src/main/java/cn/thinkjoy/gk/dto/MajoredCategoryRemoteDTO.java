package cn.thinkjoy.gk.dto;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zuohao on 16/1/19.
 */
public class MajoredCategoryRemoteDTO implements Serializable {
    private String id;
    private String name;
    private List<cn.thinkjoy.gk.dto.MajoredCategoryRemoteDTO> childList = Lists.newArrayList();
    private Integer childNumber;
    private Integer majoredNumber;
    private String parentId;
    private String parentName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<cn.thinkjoy.gk.dto.MajoredCategoryRemoteDTO> getChildList() {
        return childList;
    }

    public void setChildList(List<cn.thinkjoy.gk.dto.MajoredCategoryRemoteDTO> childList) {
        this.childList = childList;
    }

    public Integer getChildNumber() {
        return childNumber;
    }

    public void setChildNumber(Integer childNumber) {
        this.childNumber = childNumber;
    }

    public Integer getMajoredNumber() {
        return majoredNumber;
    }

    public void setMajoredNumber(Integer majoredNumber) {
        this.majoredNumber = majoredNumber;
    }
}
