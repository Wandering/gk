package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.entity.UniversityInfoView;
import cn.thinkjoy.gk.pojo.UniversityInfoParmasView;

import java.util.List;
import java.util.Map;

/**
 * 院校清单规则路由---
 * 所有院校继承次抽象类  , 实现 线差法 及 位次法.
 * 具体线差于位次 实现的差异化 在接口中体现.
 *
 * Created by douzy on 16/3/23.
 */
public interface IBaseUniversityInfoService {
    /**
     * 根据分数及控制线 计算线差
     *
     * @param batch        批次
     * @param score        分数
     * @param cate         科类
     * @param provinceCode 省份
     * @return
     */
    public Integer getLineDiff(String batch, Integer score, Integer cate, String provinceCode);

    /**
     * 线差法
     * 1.输入位次且位次>0  按照输入位次匹配临近值
     * 2.根据动态参数[录取率、利用率]初次筛选
     * 3.根据梯度规则进行二次筛选[梯度规则见DB]
     * 4.输出清单
     *
     * @param map
     * @return
     */
    public List<UniversityInfoView> selectUniversityInfo(Map map);

    /**
     * 位次法
     * 1.判断用户位次是否大于设置阀值[值见DB]
     * 2.大于阀值 走线差法
     * 3.小于阀值 根据位次规则输出清单[规则见DB]
     *
     * @param map
     * @return
     */
    public List<UniversityInfoView> selectUniversityInfoByRanking(Map map);

    /**
     * 线差法
     * @param map
     * @return
     */
    public List<UniversityInfoView> selectUniversityInfoByLineDiff(Map map);

    /**
     * 分数补充法
     * 1.判断用户输入分数
     * 2.如果在批次限制范围 则走此分数法
     * 3.如果不在批次限制范围  则走位次法
     *
     * @param map
     * @return
     */
    public List<UniversityInfoView> selectUniversityInfoByScore(Map map);

    /**
     * 是否匹配分数补充法   --由此来判定算法的最终走向.   --位次
     * 1.判断用户输入分数
     * 2.是否匹配分数补充法  匹配 return true; 否则 result false
     *
     * @param universityInfoParmasView
     * @return
     */
    public boolean isScoreSupplementary(UniversityInfoParmasView universityInfoParmasView,String flag);


    /**
     * 是否匹配分数补充法   --由此来判定算法的最终走向.   --线差
     * 1.判断用户输入分数
     * 2.是否匹配分数补充法  匹配 return true; 否则 result false
     *
     * @param universityInfoParmasView
     * @return
     */
    public boolean isScoreSupplementaryLindDiff(UniversityInfoParmasView universityInfoParmasView,String flag);



    /**********************************难易预测&成绩分析**********************************/
    /**
     * 获取逻辑走向
     * @param key
     * @return
     */
    public boolean enrollingLogin(String key);
//
//    /**
//     * 位次录取率&线差录取率 差值
//     * @param preEnrolling
//     * @param scoreEnrolling
//     * @return
//     */
//    public Integer enrollingDiff(Integer preEnrolling ,Integer scoreEnrolling);
//
//    /**
//     * 位次
//     * @param preEnrolling
//     * @return
//     */
//    public Integer enrollingResult(Integer preEnrolling);
    /**********************************难易预测&成绩分析**********************************/


}
