package cn.thinkjoy.gk.util;

/**
 * Created by yangyongping on 16/8/3.
 */
public enum  ScoreRankEnum {

    逃课大军(0),
    教师常客(1),
    校刊红人(2),
    三好学生(3),
    名垂校史(4);

    private final Integer sub;

    /**
     *
     * @param sub
     *            the code
     */
    private ScoreRankEnum(Integer sub) {
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
