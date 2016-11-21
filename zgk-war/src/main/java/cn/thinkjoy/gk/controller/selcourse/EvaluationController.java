package cn.thinkjoy.gk.controller.selcourse;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.Evaluation;
import cn.thinkjoy.gk.domain.MajorDiffCompareRtn;
import cn.thinkjoy.gk.pojo.MajorBatchCompareRtnPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IAdviceCourseService;
import cn.thinkjoy.gk.service.IEvaluationService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 16/7/26.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/evaluation")
public class EvaluationController extends ZGKBaseController {

    @Autowired
    private IEvaluationService evaluationService;

    /**
     * 保存测评结果
     * @return
     */
    @RequestMapping(value = "/insertEvaluation", method = RequestMethod.POST)
    @ResponseBody
    public Object insertEvaluation(@RequestParam String evaluation) {
        isVip();
        Evaluation evaluationObj= JSON.parseObject(evaluation,Evaluation.class);
        if (evaluation==null)throw new BizException(ERRORCODE.EVALUATION_IS_NULL.getCode(),ERRORCODE.EVALUATION_IS_NULL.getMessage());
        evaluationObj.setUserId(Long.valueOf(getAccoutId()));
        evaluationObj.setCdate(System.currentTimeMillis());
        //取出数据
        return evaluationService.insertEvaluation(evaluationObj);
    }

    /**
     * 查询最后一次测评结果
     * @return
     */
    @RequestMapping(value = "/queryEvaluation", method = RequestMethod.GET)
    @ResponseBody
    public Object queryEvaluation() {

        //取出数据
        return evaluationService.queryLastEvaluation(Long.valueOf(getAccoutId()));
    }

    /**
     * 查询最后一次测评结果
     * @return
     */
    @RequestMapping(value = "/queryEvaluationByUid", method = RequestMethod.GET)
    @ResponseBody
    public Object queryEvaluationByUid(@RequestParam Long uid) {

        //取出数据
        return evaluationService.queryLastEvaluation(uid);
    }

    /**
     * 判断用户用户当前测评次数 (分为0,1,2次测评机会)
     * 用户必须登录
     * @return
     */
    @RequestMapping(value = "/queryEvaluationCount", method = RequestMethod.GET)
    @ResponseBody
    public Object queryEvaluationCount() {
        isVip();
        int count=evaluationService.queryEvaluationCount(Long.valueOf(getAccoutId()));
        //取出数据
        return count<0?0:count;
    }
}
