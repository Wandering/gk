package cn.thinkjoy.gk.dto;

import cn.thinkjoy.gk.domain.PolicyInterpretation;

import java.util.List;

/**
 * 政策解读的一二级菜单
 */
public class PolicyInterpretationCategory {
    /** 批次id */
    Long id;
    /** 名称 */
    private String name;
    /** 批次下的分类 */
    private List<PolicyInterpretation> category;

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

    public List<PolicyInterpretation> getCategory() {
        return category;
    }

    public void setCategory(List<PolicyInterpretation> category) {
        this.category = category;
    }
}
