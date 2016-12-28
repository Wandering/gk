package cn.thinkjoy.gk.entity;

import cn.thinkjoy.zgk.domain.BaseDomain;

/**
 * Created by zuohao on 16/10/19.
 */
public class ExpertConfig extends BaseDomain {

    private String configKey;
    private String configValue;
    private String configDomain;

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

    public String getConfigDomain() {
        return configDomain;
    }

    public void setConfigDomain(String configDomain) {
        this.configDomain = configDomain;
    }
}
