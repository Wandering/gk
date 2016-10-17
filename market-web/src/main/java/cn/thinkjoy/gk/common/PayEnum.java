package cn.thinkjoy.gk.common;

/**
 * Created by yangyongping on 2016/10/17.
 */
public enum PayEnum {
    Y("支付成功","Y"),
    N("支付失败","N");
    private final String name;
    private final String code;


    PayEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
