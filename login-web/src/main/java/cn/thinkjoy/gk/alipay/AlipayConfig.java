package cn.thinkjoy.gk.alipay;

/**
 * Created by liusven on 16/7/28.
 */
public class AlipayConfig
{
    // 合作身份者ID
    public static String partner = "2088221714051384";
    // 商户的私钥
    public static String key = "3aozokielh93hoaxnw1nnma3ezwozpuc";

    // 调试用，创建TXT日志文件夹路径
    public static String log_path = "/Users/liusven/Desktop";

    // 字符编码格式
    public static String input_charset = "utf-8";

    // 签名方式 不需修改
    public static String sign_type = "MD5";

    public static String return_url = "http://localhost:8080/login";

    public static String service = "alipay.auth.authorize";

    public static String target_service = "user.auth.quick.login";

    public static String APP_ID = "2016072801677796";

    public static String APP_PRIVATE_KEY =  "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM7kKSUraOasaSbT" +
                                            "OZ4+O558PRASGF1lGDgpxcZDefl/XL5XWAFjHK/CSa4vPz7nFLFilGI2AYXT7CcE" +
                                            "HDsnJh1l1t7TBoL3bIldzUSQ1BmT2C8eHJT5TLvXQPmYwSV9BixCA+sVAyletlPq" +
                                            "XXEJdnBWCP5K7FgtNrRuAaja7XmfAgMBAAECgYB+x5HvFQrTUBOflxBXyAsAs2E3" +
                                            "197WXHza7b4kIHU+Tq2mdh+XugR6L7S26Hz5LuGfUalcBXwpZhPwVLR4CyVvA9hI" +
                                            "p6sthWEKWB81jYUko/kLkh2czDsS6ERL357MV0cXu/ZN99KFkfwehLRUEhVZ+ub9" +
                                            "CtgRBNkZh/4SzKgoAQJBAPN4TSFJ47oVB/cPBWiZP+2QsL3XosQ5AMmmoTvPKy2D" +
                                            "ufDQceHF5kFxv3VUIsMvz0rrAcMeX4S6dIjYXmyc0z8CQQDZifAjozFkAYcoDyQm" +
                                            "bYbGHZPtXiUj/oWWiEQkyBW1LHk7sXjn90/vxMfbHLr1B7mfNBFtWyeYGiTCISoi" +
                                            "DqGhAkBChF6GNrq5zx8i936hyiS2Ee7Hnw9ADtbRQO4R+hKw16lISpqidT/oJ1yI" +
                                            "gJkSvJAkxrnvhe/QwmQuMvbxM5NfAkEAsdzmSz0TFQFq0IYQuJq0pydn25wuIc/o" +
                                            "ybuAe9JcbVV/ih8BDChZY2ExRyWmdtFqI5Ee7pqpNOOrSk5zdIUiAQJAG0lkJaGx" +
                                            "jv+EWcvKMFtHkEGOLtfWGay2oJ9SKk4Kf8xofSwC6UU830zVIlG+NBj+jqvXysMy" +
                                            "XuNkQgCdPKgZLw==";

    public static String APP_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDO5CklK2jmrGkm0zmePjuefD0QEhhdZRg4KcXGQ3n5f1y+V1gBYxyvwkmuLz8+5xSxYpRiNgGF0+wnBBw7JyYdZdbe0waC92yJXc1EkNQZk9gvHhyU+Uy710D5mMElfQYsQgPrFQMpXrZT6l1xCXZwVgj+SuxYLTa0bgGo2u15nwIDAQAB";

    public static String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

    public static String APP_AUTH_TOKEN = "201607BB780b716f25694d66846aed6d6ceafX38";
}
