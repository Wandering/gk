package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.domain.Evaluation;

/**
 * Created by yangyongping on 16/9/20.
 */
public interface IEvaluationService {
    /**
     * 保存测评结果
     * @param evaluation
     * @return
     */
    boolean insertEvaluation(Evaluation evaluation);

    /**
     * 查询最后一次测评结果
     * @param userId
     * @return
     */
    Evaluation queryLastEvaluation(Long userId);

    /**
     *
     * @param userId
     * @return
     */
    int queryEvaluationCount(Long userId);
}
