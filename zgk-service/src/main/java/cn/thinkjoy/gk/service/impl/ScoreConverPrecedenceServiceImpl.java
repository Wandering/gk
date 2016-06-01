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
        return converPrecedence == null ? 0 : converPrecedence.getAvgPre();
    }
}
