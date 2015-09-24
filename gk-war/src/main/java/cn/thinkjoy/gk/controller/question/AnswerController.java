package cn.thinkjoy.gk.controller.question;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.controller.question.bean.AnswerBean;
import cn.thinkjoy.gk.controller.question.dto.QuestionContentDto;
import cn.thinkjoy.gk.controller.question.dto.QuestionDto;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.protocol.PageQuery;
import cn.thinkjoy.ss.api.IAnswerService;
import cn.thinkjoy.ss.bean.AnswerQuestionBean;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
@Scope("prototype")
@RequestMapping(value="/answer")
public class AnswerController extends BaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(AnswerController.class);

    @Autowired
    private IAnswerService answerService;

    /**
     * 获取所有我的问题
     *
     * @return
     */
    @RequestMapping(value = "/myQuestion", method = RequestMethod.GET)
    @ResponseBody
    public AnswerBean findMyQuestion(PageQuery pageQuery) throws Exception {

        Integer startSize = pageQuery.getStartSize();

        if(startSize==null){
            startSize = 0;
        }

        Integer endSize = pageQuery.getEndSize();

        if(endSize==null){
            endSize = 10;
        }

        UserAccountPojo account = getUserAccountPojo();

        if(pageQuery==null){
            LOGGER.info("====notice findAllAnswer PARAM_ERROR ");
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
        }

        Long userId = account.getId();

        List<AnswerQuestionBean> aswerQuestionBeans = answerService.findAnswerPage(userId, startSize, endSize);

        List<QuestionDto> hotQuestionDtos = new ArrayList<QuestionDto>();

        QuestionDto hotQuestionDto = null;
        for(AnswerQuestionBean aswerQuestionBean:aswerQuestionBeans){
            hotQuestionDto = new QuestionDto();
            hotQuestionDto.setUserId(aswerQuestionBean.getUserId());
            hotQuestionDto.setUserIcon(aswerQuestionBean.getUserIcon());
            hotQuestionDto.setFreeStatus(aswerQuestionBean.getFreeStatus());
            hotQuestionDto.setUserName(aswerQuestionBean.getUserName());
            hotQuestionDto.setAnswerTime(aswerQuestionBean.getAnswerTime());
            hotQuestionDto.setCreateTime(aswerQuestionBean.getCreateTime());
            hotQuestionDto.setIsAnswer(aswerQuestionBean.getIsAnswer());
            hotQuestionDto.setQuestionId(aswerQuestionBean.getId());
            hotQuestionDto.setDisableStatus(aswerQuestionBean.getDisableStatus());
            hotQuestionDto.setIsOpen(aswerQuestionBean.getIsOpen());
            try{
                hotQuestionDto.setQuestions(JSON.parseArray(aswerQuestionBean.getQuestion(), QuestionContentDto.class));
            }catch(Exception e){
                List<QuestionContentDto> questions = new ArrayList<QuestionContentDto>();
                QuestionContentDto questionContentDto = new QuestionContentDto();
                questionContentDto.setText(aswerQuestionBean.getQuestion());
                questions.add(questionContentDto);
                hotQuestionDto.setQuestions(questions);
            }
            hotQuestionDtos.add(hotQuestionDto);
        }

        AnswerBean answerBean = new AnswerBean();

        answerBean.setAnswers(hotQuestionDtos);

        return answerBean;
    }

}

