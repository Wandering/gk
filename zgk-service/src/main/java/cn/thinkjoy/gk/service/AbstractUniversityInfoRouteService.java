package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.entity.UniversityInfoView;

import java.util.List;
import java.util.Map;

/**
 * 院校清单规则路由---
 * 所有院校继承次抽象类  , 实现 线差法 及 位次法.
 * 具体线差于位次 实现的差异化 在接口中体现.
 *
 * Created by douzy on 16/3/23.
 */
public abstract class AbstractUniversityInfoRouteService {
    /**
     * 根据分数及控制线 计算线差
     * @param batch 批次
     * @param score 分数
     * @param cate  科类
     * @param provinceCode 省份
     * @return
     */
    public abstract Integer getLineDiff(Integer batch,Integer score,Integer cate, String provinceCode);
    /**
     * 线差法
     * 1.输入位次且位次>0  按照输入位次匹配临近值
     * 2.根据动态参数[录取率、利用率]初次筛选
     * 3.根据梯度规则进行二次筛选[梯度规则见DB]
     * 4.输出清单
     * @param map
     * @return
     */
    public abstract List<UniversityInfoView> selectUniversityInfo(Map map);

    /**
     * 位次法
     * 1.判断用户位次是否大于设置阀值[值见DB]
     * 2.大于阀值 走线差法
     * 3.小于阀值 根据位次规则输出清单[规则见DB]
     * @param map
     * @return
     */
    public abstract List<UniversityInfoView> selectUniversityInfoByRanking(Map map);
}
