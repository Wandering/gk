package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.entity.UniversityInfoView;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/14.
 */
public interface IUniversityInfoService {
    /**
     * 院校清单List
     * @param map
     * @return
     */
    List<UniversityInfoView> selectUniversityInfo(Map map);

    /**
     * 根据分数及控制线 计算线差
     * @param batch 批次
     * @param score 分数
     * @param cate  科类
     * @param provinceCode 省份
     * @return
     */
    Integer getLineDiff(Integer batch,Integer score,Integer cate, String provinceCode);
}
