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

    public static String APP_ID = "2016072801677705";

//    public static String APP_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALDgDVND5HC4c6EtVaARFO+zfC+rtWNkxY8NH64eyQHoshlYRSpg2u0bSugaxVG9CxQBcqakyW8yWEilIaTP+jeh8PixX/nAkB9wQr5K+Q8U2z+wFHAJ8zwMx2LWX6klZ+jJ78y2XG6iARMc0iiUWLVoBrVERzKojEhI+FfX4fJnAgMBAAECgYEAqQ8ujWS6R6d2FhuP2TQaj9Y9oA7+jCc40beYjUQtmp6BBMqjKZN0DeflXB6idbM8kH2lyXR+eMNlMOJtWuvTW5yj8DP2ORIzmwk1EXu6HSnBXr4AR7u8Dxs9fetsUbJx8OEKV6J8d8shfUHOCU9l7q2JZHwbTeUlaNP/UJu28dkCQQDXksPawQ/zuwXmoUYTaSwn/92ETN7howfD1d0yh01OYx661BnHcQIA1LjnAica3CTjD9rfZPtO8g6uM0czCwsjAkEA0gt4XVkP2Kgj3qjs1panks12PVtRRdp4BagADTvrnzo7FUUpIcAl9WJgxsYEZyssAdf+ZYWlg/gGYAeoGISB7QJADX1Uniq4mdLZMq5i2TIankInlXDN/aabBMksN046Ky6OrLg7CLzsmCq3i1UaJ1chQ8gpNRN3mmARRRtWWD5XqwJAUaTHunEd6bchijDbLmYM6NEYPq06IA7VTE2LB5nc8l1uDcx+OnI3zvPYbisWFwv+5RCR/+A8/9jukXEoc6892QJBAKWjNHrVEMW0IIUsG27XlKPQkBEvAbq5TmJDc044m0bT9LHjzRDpcUPl0x84FljfnPuwxevmPVmvxzfy3nmmAdE=";
    public static String APP_PRIVATE_KEY =  "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALDgDVND5HC4c6Et" +
                                            "VaARFO+zfC+rtWNkxY8NH64eyQHoshlYRSpg2u0bSugaxVG9CxQBcqakyW8yWEil" +
                                            "IaTP+jeh8PixX/nAkB9wQr5K+Q8U2z+wFHAJ8zwMx2LWX6klZ+jJ78y2XG6iARMc" +
                                            "0iiUWLVoBrVERzKojEhI+FfX4fJnAgMBAAECgYEAqQ8ujWS6R6d2FhuP2TQaj9Y9" +
                                            "oA7+jCc40beYjUQtmp6BBMqjKZN0DeflXB6idbM8kH2lyXR+eMNlMOJtWuvTW5yj" +
                                            "8DP2ORIzmwk1EXu6HSnBXr4AR7u8Dxs9fetsUbJx8OEKV6J8d8shfUHOCU9l7q2J" +
                                            "ZHwbTeUlaNP/UJu28dkCQQDXksPawQ/zuwXmoUYTaSwn/92ETN7howfD1d0yh01O" +
                                            "Yx661BnHcQIA1LjnAica3CTjD9rfZPtO8g6uM0czCwsjAkEA0gt4XVkP2Kgj3qjs" +
                                            "1panks12PVtRRdp4BagADTvrnzo7FUUpIcAl9WJgxsYEZyssAdf+ZYWlg/gGYAeo" +
                                            "GISB7QJADX1Uniq4mdLZMq5i2TIankInlXDN/aabBMksN046Ky6OrLg7CLzsmCq3" +
                                            "i1UaJ1chQ8gpNRN3mmARRRtWWD5XqwJAUaTHunEd6bchijDbLmYM6NEYPq06IA7V" +
                                            "TE2LB5nc8l1uDcx+OnI3zvPYbisWFwv+5RCR/+A8/9jukXEoc6892QJBAKWjNHrV" +
                                            "EMW0IIUsG27XlKPQkBEvAbq5TmJDc044m0bT9LHjzRDpcUPl0x84FljfnPuwxevm" +
                                            "PVmvxzfy3nmmAdE=";

    public static String APP_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCw4A1TQ+RwuHOhLVWgERTvs3wvq7VjZMWPDR+uHskB6LIZWEUqYNrtG0roGsVRvQsUAXKmpMlvMlhIpSGkz/o3ofD4sV/5wJAfcEK+SvkPFNs/sBRwCfM8DMdi1l+pJWfoye/MtlxuogETHNIolFi1aAa1REcyqIxISPhX1+HyZwIDAQAB";

    public static String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

    public static String APP_AUTH_TOKEN = "201607BB780b716f25694d66846aed6d6ceafX38";
}
