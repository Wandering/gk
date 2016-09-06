package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.entity.ScoreConverPrecedence;
import cn.thinkjoy.gk.entity.ScoreMaxMin;

import java.util.Map;

/**
 * Created by douzy on 16/5/17.
 */
public interface IScoreConverPrecedenceService {
    /**
     * 根据分数获取批次
     * @param map
     * @return
     */
    ScoreConverPrecedence selectPrecedenceByScore(Map map);

    /**
     * 获取最大及最小 分数
     * @param map
     * @return
     */
    ScoreMaxMin selectMaxScore(Map map);

    /**
     * 分数转换位次
     * @param score 分数
     * @param proCode 省份
     * @param cate    科类
     * @param batch   批次
     * @return
     */
    public Integer converPrecedenceByScore(Integer score,String proCode,Integer cate,String batch);

    /**
     * 分数转换位次
     * @param score 分数
     * @param proCode 省份
     * @param cate    科类
     * @param batch   批次
     * @return
     */
    public Integer converPrecedenceByScoreV2(Integer score,String proCode,Integer cate,String batch);


    /**
     * 校验用户位次准确性
     * @param score
     * @param proCode
     * @param cate
     * @param batch
     * @param precedence
     * @return
     */
    public boolean validPrecedenceScoreMapper(Integer score,String proCode,Integer cate,String batch,Integer precedence);
}

