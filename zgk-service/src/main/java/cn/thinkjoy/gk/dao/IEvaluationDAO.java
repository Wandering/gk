package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.domain.Evaluation;

/**
 * Created by yangyongping on 16/9/20.
 */
public interface IEvaluationDAO {
    /**
     * 保存测评信息
     * @param evaluation
     * @return
     */
    int insertEvaluation(Evaluation evaluation);


    /**
     * 查询最后一次测评信息
     * @param userId
     * @return
     */
    Evaluation queryLastEvaluation(Long userId);

    /**
     * 查询当前已经测评次数
     * @param userId
     * @return
     */
    int queryEvaluationCount(Long userId);
}
