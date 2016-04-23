package cn.thinkjoy.gk.common;

/**
 * Created by douzy on 16/3/14.
 */
public class ReportEnum {
    /**
     * 文理
     */
    public enum categories {
        WEN {
            @Override
            public int getValue() {
                return 1;
            }
        },
        LI {
            @Override
            public int getValue() {
                return 2;
            }
        };

        public abstract int getValue();
    }

    /**
     * 批次
     */
    public enum batch {
        ONE {
            @Override
            public int getValue() {
                return 1;
            }
        },
        TWO {
            @Override
            public int getValue() {
                return 2;
            }
        }, THREE {
            @Override
            public int getValue() {
                return 3;
            }
        };

        public abstract int getValue();
    }

    /**
     * 位次法搜索维度  - 0 院校排名   1 录取率排名
     */
    public enum RankDim {
        RANK {
            @Override
            public byte getValue() {
                return 0;
            }
        },
        ENROLLING {
            @Override
            public byte getValue() {
                return 1;
            }
        };

        public abstract byte getValue();
    }
}
