package cn.thinkjoy.gk.entity.information.dto;


import cn.thinkjoy.gk.domain.GkinformationGkhot;

/**
 * Created by admin on 2015/12/7.
 */
public class GkinformationGkhotDTO extends GkinformationGkhot {
        /**省份名字**/
        private String province;

    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
