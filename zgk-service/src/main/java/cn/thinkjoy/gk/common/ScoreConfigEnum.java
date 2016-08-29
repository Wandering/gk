package cn.thinkjoy.gk.common;

/**
 * Created by yangyongping on 16/7/26.
 */
public enum ScoreConfigEnum {
    语文(2),
    数学(3),
    外语(4),
    物理(5),
    化学(6),
    生物(7),
    政治(8),
    历史(9),
    地理(10),
    通用技术(13),
    思想政治(14);
    /** The code. */
    private final Integer sub;

    /**
     *
     * @param sub
     *            the code
     */
    private ScoreConfigEnum(Integer sub) {
        this.sub = sub;
    }

    /**
     * Gets the sub.
     *
     * @return the sub
     */
    public Integer getSub() {
        return sub;
    }

    }
