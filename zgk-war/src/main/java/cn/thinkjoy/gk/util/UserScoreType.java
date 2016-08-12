package cn.thinkjoy.gk.util;

/**
 * Created by yangyongping on 16/8/11.
 */
public interface UserScoreType {
    //    成绩段
    //1、［满分＊80%，满分]
    int FULL_MARK_80_100 = 1;
    //2、［满分＊60%，满分＊80%）
    int FULL_MARK_60_80 = 2;
    //3、［满分＊40%，满分＊60%）
    int FULL_MARK_40_60 = 3;
    //4、［满分＊40%，0）
    int FULL_MARK_0_40 = 4;


    //    标示标签应用范围
    //    1、总分
    int LABEL_TOTAL = 1;
    //    2、语文
    int LABEL_YW = 2;
    //    3、数学
    int LABEL_SX = 3;
    //    4、外语
    int LABEL_WY = 4;
    //    5、物理
    int LABEL_WL = 5;
    //    6、化学
    int LABEL_HX = 6;
    //    7、生物
    int LABEL_SW = 7;
    //    8、政治
    int LABEL_ZZ = 8;
    //    9、历史
    int LABEL_LS = 9;
    //    10、地理
    int LABEL_DL = 10;
    //    11、通用标签
    int LABEL_ALL = 11;
    //    12、含科目通用标签
    int LABEL_SUB_ALL = 12;

    String TYPE_WEN="文";

    String TYPE_LI="理";


    /**
     * 满分＊90%，满分
     */
    String SUBJECT_DESC_1="成绩无人能及";
    int SUBJECT_TAG_1=1;
    /**
     * ［满分＊80%，90%）
     */
    String SUBJECT_DESC_2="成绩接近暴走";
    int SUBJECT_TAG_2=2;

    /**
     *［满分＊60%，80%）
     */
    String SUBJECT_DESC_3="成绩初露苗头";
    int SUBJECT_TAG_3=3;

    /**
     * ［满分＊40%，60%）
     */
    String SUBJECT_DESC_4="成绩岌岌可危";
    int SUBJECT_TAG_4=4;

    /**
     * ［0，满分＊40%）
     */
    String SUBJECT_DESC_5="成绩急需复活";
    int SUBJECT_TAG_5=5;

}
