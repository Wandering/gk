package cn.thinkjoy.gk.common;

/**
 * Created by yangguorong on 16/10/17.
 *
 * 异常返回类
 */
public enum ErrorCode {

    SUCCESS("0000000", "success"),
    SMS_CODE_FREQUENCY("0000001", "验证码获取过于频繁"),
    SMS_CODE_FAIL("0100002", "验证码发送失败,请重试"),
    ACCOUNT_ERROR("0000003","账号不存在"),
    ACCOUNT_NULL("0000004","账号为空"),
    PWD_ERROR("0000005","密码错误"),
    PWD_NULL("0000006","密码为空"),
    NEW_PWD_NULL("0000007","新密码为空"),
    PARAM_NULL("0000008","参数为空"),
    IMG_TOO_LONG("0000009","图片地址长度超过限制(255)"),
    CHECK_SMSCODE_NOT_EXIST("0400003", "验证码过期或不存在，请重新获取!"),
    ;

    private final String code;

    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }
}
