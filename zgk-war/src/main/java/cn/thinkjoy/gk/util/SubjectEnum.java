package cn.thinkjoy.gk.util;

/**
 * Created by yangyongping on 16/7/26.
 */
public enum SubjectEnum {
    yw("语文"),
    sx("数学"),
    wy("外语"),
    hx("化学"),
    sw("生物"),
    ls("历史"),
    dl("地理"),
    ty("通用技术"),
    wl("物理"),
    zz("政治"),
    语文("yw"),
    数学("sx"),
    外语("wy"),
    化学("hx"),
    生物("sw"),
    历史("ls"),
    地理("dl"),
    通用技术("ty"),
    物理("wl"),
    政治("zz");
    /** The code. */
    private final String sub;

    /**
     *
     * @param sub
     *            the code
     */
    private SubjectEnum(String sub) {
        this.sub = sub;
    }

    /**
     * Gets the sub.
     *
     * @return the sub
     */
    public String getSub() {
        return sub;
    }

    }
