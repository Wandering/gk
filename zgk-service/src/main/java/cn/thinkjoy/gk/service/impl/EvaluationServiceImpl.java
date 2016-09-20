package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.common.SubjectsUtil;
import cn.thinkjoy.gk.dao.IEvaluationDAO;
import cn.thinkjoy.gk.domain.Evaluation;
import cn.thinkjoy.gk.service.IEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yangyongping on 16/9/20.
 */
@Service("EvaluationServiceImpl")
public class EvaluationServiceImpl implements IEvaluationService {
    @Autowired
    private IEvaluationDAO evaluationDAO;
    /**
     * 保存测评结果
     *
     * @param evaluation
     * @return
     */
    @Override
    public boolean insertEvaluation(Evaluation evaluation) {

        return evaluationDAO.insertEvaluation(evaluation)>0;
    }

    /**
     * 查询最后一次测评结果
     *
     * @param userId
     * @return
     */
    @Override
    public Evaluation queryLastEvaluation(Long userId) {
        return evaluationDAO.queryLastEvaluation(userId);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public int queryEvaluationCount(Long userId) {
        return SubjectsUtil.EVALUATION_COUNT_2-evaluationDAO.queryEvaluationCount(userId);
    }
}
