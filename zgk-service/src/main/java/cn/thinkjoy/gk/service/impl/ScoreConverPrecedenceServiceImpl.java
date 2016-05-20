package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IScoreConverPrecedenceDao;
import cn.thinkjoy.gk.entity.ScoreConverPrecedence;
import cn.thinkjoy.gk.entity.ScoreMaxMin;
import cn.thinkjoy.gk.service.IScoreConverPrecedenceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
