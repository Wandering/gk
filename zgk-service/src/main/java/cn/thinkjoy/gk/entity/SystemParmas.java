package cn.thinkjoy.gk.entity;

import cn.thinkjoy.common.domain.CreateBaseDomain;

/**
 * Created by douzy on 16/3/14.
 */
public class SystemParmas extends CreateBaseDomain<Long> {
    private Long id;
    private String provinceCode;
    private String configKey;
    private String configValue;
    private byte majorType;
    private Integer year;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public byte getMajorType() {
        return majorType;
    }

    public void setMajorType(byte majorType) {
        this.majorType = majorType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
