package cn.thinkjoy.gk.domain;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by yangyongping on 2016/11/16.
 */
public class ExpertUser extends BaseDomain<Long>{
    //登录账号
    private String account;

    //密码
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
