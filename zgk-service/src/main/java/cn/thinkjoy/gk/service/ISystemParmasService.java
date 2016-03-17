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
    SystemParmas getThresoldModel(String proCode,String keyEnum);

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
    SystemParmas getRoleByKey(String configKey);

    /**
     * 录取率规则
     * @param proCode
     * @return
     */
    ArrayList<Integer> getEnrollRate(String proCode);

    /**
     * 利用率规则
     * @param proCode
     * @return
     */
    ArrayList<Integer> getUsedRate(String proCode);

    /**
     * 获取批次控制线key
     * @param batch
     * @param provinceCode
     * @return
     */
    String getBatchKey(Integer batch,String provinceCode);
}
