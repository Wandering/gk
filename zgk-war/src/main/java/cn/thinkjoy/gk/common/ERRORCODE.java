package cn.thinkjoy.gk.common;

/**
 * Created by admin on 2016/1/4.
 */
public enum ERRORCODE {

    PARAM_NOTEXIST("0100001", "查询参数不存在"),
    IDISNOTNULL("0100002", "id不能为空"),
    RESOURCEOCCUPY("0100003", "资源被占用"),
    RESOURCEISNULL("0100005", "资源不存在"),
    DELETEEXCEPTION("0100006", "删除异常"),
    ADDEXCEPTION("0100004", "添加异常"),
    HASPREDICT("0100007", "您今天已经预测过了，请明天再来！"),
    ISLOGIN("0100008", "当前用户未登录！");
    /** The code. */
    private final String code;

    /** The message. */
    private final String message;

    /**
     * Instantiates a new error type.
     *
     * @param code
     *            the code
     * @param message
     *            the message
     */
    private ERRORCODE(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets the code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }


}
