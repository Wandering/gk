package cn.thinkjoy.gk.service.impl;


import cn.thinkjoy.gk.dao.IQuestionExDAO;
import cn.thinkjoy.gk.pojo.QuestionDetailPojo;
import cn.thinkjoy.gk.pojo.QuestionPojo;
import cn.thinkjoy.gk.service.IQuestionExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("QuestionExServiceImpl")
public class QuestionExServiceImpl implements IQuestionExService {

    @Autowired
    private IQuestionExDAO questionExDAO;

    @Override
    public List<QuestionPojo> findQuestionPage(Integer freeStatus, Integer isAnswer, Integer startSize, Integer endSize) {

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("isAnswer",isAnswer);
        params.put("freeStatus",freeStatus);
        int pageSize = endSize - startSize;
        params.put("startSize",startSize);
        params.put("endSize",pageSize);
        return questionExDAO.findQuestionPage(params);

    }

    @Override
    public int findQuestionCount(Integer freeStatus, Integer isOpen, Integer isAnswer) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("isAnswer",isAnswer);
        params.put("isOpen",isOpen);
        params.put("freeStatus",freeStatus);
        return questionExDAO.findQuestionCount(params);
    }

    @Override
    public QuestionDetailPojo findQuestionById(Long questionId) {
        Map<String,Object> params = new HashMap<String, Object>();

        params.put("questionId",questionId);

        return questionExDAO.findQuestionById(params);
    }
}
