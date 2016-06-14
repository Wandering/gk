package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.entity.ScoreConverPrecedence;
import cn.thinkjoy.gk.entity.ScoreMaxMin;

import java.util.Map;

/**
 * Created by douzy on 16/5/17.
 */
public interface IScoreConverPrecedenceDao {

    ScoreConverPrecedence selectPrecedenceByScore(Map map);

    ScoreMaxMin selectMaxScore(Map map);
}
