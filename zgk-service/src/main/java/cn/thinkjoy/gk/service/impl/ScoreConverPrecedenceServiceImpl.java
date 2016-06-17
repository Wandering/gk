package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.common.ReportUtil;
import cn.thinkjoy.gk.dao.IScoreConverPrecedenceDao;
import cn.thinkjoy.gk.entity.ScoreConverPrecedence;
import cn.thinkjoy.gk.entity.ScoreMaxMin;
import cn.thinkjoy.gk.service.IScoreConverPrecedenceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by douzy on 16/5/17.
 */
@Service
public class ScoreConverPrecedenceServiceImpl implements IScoreConverPrecedenceService {

    @Resource
    IScoreConverPrecedenceDao iScoreConverPrecedenceDao;

    @Override
    public ScoreConverPrecedence selectPrecedenceByScore(Map map) {
        return iScoreConverPrecedenceDao.selectPrecedenceByScore(map);
    }

    @Override
    public ScoreMaxMin selectMaxScore(Map map) {
        return iScoreConverPrecedenceDao.selectMaxScore(map);
    }



    @Override
    public Integer converPrecedenceByScore(Integer score,String proCode,Integer cate,String batch) {
        Map scoreMap = new HashMap();
        scoreMap.put("tableName", ReportUtil.getOneScoreTableName(proCode, cate, batch));
        ScoreMaxMin scoreMaxMin = selectMaxScore(scoreMap);

        //超过最大及最小分数   按最大、最小分数走
        if (score >= scoreMaxMin.getMaxScore())
            score = scoreMaxMin.getMaxScore();
        if (score <= scoreMaxMin.getMinScore())
            score = scoreMaxMin.getMinScore();

        scoreMap.put("score", score);
        //根据分数 查找对应位次
        ScoreConverPrecedence converPrecedence = selectPrecedenceByScore(scoreMap);
        return converPrecedence == null ? 0 : converPrecedence.getLowPre();
    }

    /**
     * 校验用户位次准确性
     * @param score
     * @param proCode
     * @param cate
     * @param batch
     * @param precedence
     * @return
     */
    @Override
    public boolean validPrecedenceScoreMapper(Integer score,String proCode,Integer cate,String batch,Integer precedence) {
        Map scoreMap = new HashMap();
        scoreMap.put("tableName", ReportUtil.getOneScoreTableName(proCode, cate, batch));
        scoreMap.put("score", score);

        ScoreConverPrecedence scoreConverPrecedence = iScoreConverPrecedenceDao.selectPrecedenceByScore(scoreMap);

        if (scoreConverPrecedence == null)//一分一段找不到该分数对应的位次时,不予提示.
            return true;

        Integer heightPre = scoreConverPrecedence.getHeighPre(),
                lowPre = scoreConverPrecedence.getLowPre();

        //输入位次在最高为此与最低位次之间.
        if (precedence <= heightPre && precedence >= lowPre)
            return true;

        return false;
    }
}
