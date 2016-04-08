package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.entity.SystemParmas;
import cn.thinkjoy.gk.pojo.BatchView;

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
     * 获取控制线
     * @param batch 批次
     * @param cate  科类
     * @param provinceCode 省份
     * @return
     */
    Integer getControleLine(Integer batch,int cate,String provinceCode);

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
    Integer getRankingRangeIndex(String proCode,Integer precedence,Integer majorType);

    /**
     * 获取当前位次符合的规则区间下标  By 批次
     * @param batch  批次
     * @param proCode  省份
     * @param majorType   科类
     * @return
     */
    Integer getRankingRangeIndex(Integer batch,String proCode,Integer majorType);
}
