package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.entity.CheckBatchMsg;
import cn.thinkjoy.gk.entity.SystemParmas;
import cn.thinkjoy.gk.pojo.BatchView;
import cn.thinkjoy.gk.pojo.UniversityInfoParmasView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/14.
 */
public interface ISystemParmasService {
    /**
     * 获取系统参数
     * @param map
     * @return
     */
    public SystemParmas selectModel(Map map);

    /**
     * 获取系统参数
     * @param cate 科类
     * @param score 分数
     * @param provinceCode 省份
     * @return
     */
    List<BatchView> selectSystemParmas( Integer cate ,Integer score,String provinceCode);

    /**
     * 获取系统参数 --根据逻辑参数
     * @param cate 科类
     * @param score 分数
     * @param provinceCode 省份
     * @return
     */
    List<BatchView> selectSystemParmas( Integer cate ,Integer score,Integer sap,String provinceCode,Integer logicTrend);

    /**
     * 根据分数获取对应批次
     * @param score
     * @param provinceCode
     * @param cate
     * @return
     */
    String getBatchByScore(Integer score,String provinceCode,Integer cate);

    /**
     * 获取控制线
     * @param batch 批次
     * @param cate  科类
     * @param provinceCode 省份
     * @return
     */
    String getControleLine(String batch,int cate,String provinceCode);

    /**
     * 获取规则  key规则为: 省份-key
     * @param proCode
     * @param keyEnum
     * @return
     */
    SystemParmas getThresoldModel(String proCode,String keyEnum,Integer majorType);

    /**
     * 规则值拆分
     * @param configValue
     * @return
     */
    ArrayList<Integer> roleSplit(String configValue);

    /**
     * 获取规则对象  根据configKey
     * @param configKey
     * @return
     */
    SystemParmas getRoleByKey(String proCode,String configKey,Integer majorType);

    /**
     * 录取率规则
     * @param proCode
     * @return
     */
    ArrayList<Integer> getEnrollRate(String proCode,Integer majorType);

    /**
     * 利用率规则
     * @param proCode
     * @return
     */
    ArrayList<Integer> getUsedRate(String proCode,Integer majorType);

    /**
     * 获取批次控制线key
     * @param batch
     * @param provinceCode
     * @return
     */
    String getBatchKey(Integer batch,String provinceCode);

    /**
     *  获取当前位次符合的排名规则区间下标
     * @param proCode 省份Code
     * @param precedence  输入位次
     * @return
     */
    Integer getRankingRangeIndex(String proCode,Integer precedence,Integer majorType,String batch);

    /**
     * 获取当前位次符合的规则区间下标  By 批次
     * @param batch  批次
     * @param proCode  省份
     * @param majorType   科类
     * @return
     */
    Integer getRankingRangeIndex(String batch,String proCode,Integer majorType);

    /**
     * 算法逻辑走向
     * @param province
     * @param cate
     * @return
     */
    Integer getLogicTrend(String province,Integer cate);

    /**
     * 计算线差
     * @param batch
     * @param score
     * @param cate
     * @param provinceCode
     * @return
     */
    public Integer getLineDiff(String batch, Integer score, Integer cate, String provinceCode);

    /**
     * 是否为压线生 --位次法
     * @param parmasView
     * @return
     */
    public boolean isScoreSupplementary(UniversityInfoParmasView parmasView);

    /**
     * 是否为压线生 --线差法
     * @param parmasView
     * @return
     */
    public boolean isScoreSupplementaryLindDiff(UniversityInfoParmasView parmasView);

    /**
     * 批次选择提示
     * @param paramView
     * @return
     */
    public CheckBatchMsg checkBatchAlert(UniversityInfoParmasView paramView);

    /**
     * 获取线差规则 下标
     * @param lineDiff
     * @param proCode
     * @param majorType
     * @param batch
     * @return
     */
    public Integer getLineDiffRangeIndex(Integer lineDiff,String proCode,Integer majorType,String batch);
}
