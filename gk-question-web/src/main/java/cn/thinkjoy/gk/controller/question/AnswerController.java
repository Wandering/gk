package cn.thinkjoy.gk.controller.question;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.controller.question.bean.QuestionAnswerBean;
import cn.thinkjoy.gk.controller.question.dto.AnswerDetailDto;
import cn.thinkjoy.gk.controller.question.dto.QuestionContentDto;
import cn.thinkjoy.gk.controller.question.dto.QuestionDetailDto;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.protocol.PageQuery;
import cn.thinkjoy.ss.api.IAnswerService;
import cn.thinkjoy.ss.api.IUserAccountService;
import cn.thinkjoy.ss.bean.QuestionDetailBean;
import cn.thinkjoy.ss.bean.war.UserAccountBean;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private IUserAccountService userAccountService;

    /**
     * 获取所有我的问题
     *
     * @return
     */
    @RequestMapping(value = "/findMyQuestion", method = RequestMethod.GET)
    @ResponseBody
    public List<QuestionAnswerBean> findMyQuestion(@RequestParam(value="keyword",required=false) String keyword,
                                                   @RequestParam(value="isAnswer",required=false) Integer isAnswer,
            PageQuery pageQuery) throws Exception {

        UserAccountPojo userAccountPojo = getUserAccountPojo();

        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }

        Integer startSize = pageQuery.getStartSize();

        if(startSize==null){
            startSize = 0;
        }

        Integer endSize = pageQuery.getEndSize();

        if(endSize==null){
            endSize = 10;
        }

        if(StringUtils.isEmpty(keyword)){
            keyword = null;
        }

//        String word = null;
//        if(!StringUtils.isEmpty(keyword)){
//            try {
//                word = (new String(keyword.getBytes("ISO-8859-1"),"UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
//            }
//        }

        if(pageQuery==null){
            LOGGER.info("====notice findAllAnswer PARAM_ERROR ");
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
        }

        UserAccountBean userAccountBean = userAccountService.findUserAccountBeanByToken(userAccountPojo.getId().toString(),7);

        long userId = userAccountBean.getId();

        List<QuestionDetailBean> questionDetailBeans = answerService.findAnswerPage(keyword,isAnswer,Long.valueOf(userId), startSize, endSize);

        List<QuestionAnswerBean> questionAnswerBeans = new ArrayList<QuestionAnswerBean>();

        if(null!=questionDetailBeans&&questionDetailBeans.size()>0) {

            QuestionAnswerBean questionAnswerBean = null;

            for(QuestionDetailBean questionDetail:questionDetailBeans) {

                questionAnswerBean = new QuestionAnswerBean();

                AnswerDetailDto answerDetailDto = new AnswerDetailDto();

                answerDetailDto.setAnswerTime(questionDetail.getAnswerTime());
                if (!StringUtils.isEmpty(questionDetail.getAnswer())) {
                    try {
                        answerDetailDto.setAnswers(JSON.parseArray(questionDetail.getAnswer(), QuestionContentDto.class));
                    } catch (Exception e) {
                        List<QuestionContentDto> questions = new ArrayList<QuestionContentDto>();
                        QuestionContentDto questionContentDto = new QuestionContentDto();
                        questionContentDto.setText(questionDetail.getAnswer());
                        questions.add(questionContentDto);
                        answerDetailDto.setAnswers(questions);
                    }
                } else {
                    answerDetailDto.setAnswers(null);
                }

                answerDetailDto.setUserName(questionDetail.getExpertUserName());
                answerDetailDto.setUserIcon(questionDetail.getExpertUserIcon());
                answerDetailDto.setUserId(questionDetail.getExpertUserId());

                questionAnswerBean.setAnswer(answerDetailDto);


                QuestionDetailDto questionDetailDto = new QuestionDetailDto();

                questionDetailDto.setUserId(questionDetail.getUserId());

                questionDetailDto.setUserIcon(questionDetail.getUserIcon());

                questionDetailDto.setUserName(questionDetail.getUserName());

                questionDetailDto.setCreateTime(questionDetail.getCreateTime());

                try {
                    questionDetailDto.setQuestions(JSON.parseArray(questionDetail.getQuestion(), QuestionContentDto.class));
                } catch (Exception e) {
                    List<QuestionContentDto> questions = new ArrayList<QuestionContentDto>();
                    QuestionContentDto questionContentDto = new QuestionContentDto();
                    questionContentDto.setText(questionDetail.getQuestion());
                    questions.add(questionContentDto);
                    questionDetailDto.setQuestions(questions);
                }
                int disableNum = 0;

                String disableExpertId = questionDetail.getDisableExpertId();

                if (!StringUtils.isEmpty(disableExpertId)) {
                    if (disableExpertId.indexOf(",") > -1) {
                        disableNum = disableExpertId.split(",").length;
                    } else {
                        disableNum++;
                    }
                }
                questionDetailDto.setDisableNum(disableNum);
                questionDetailDto.setDisableStatus(questionDetail.getDisableStatus());
                questionDetailDto.setCreateTime(questionDetail.getCreateTime());
                questionDetailDto.setQuestionId(questionDetail.getQuestionId());
                questionAnswerBean.setQuestion(questionDetailDto);

                questionAnswerBeans.add(questionAnswerBean);
            }

        }
        return questionAnswerBeans;
    }

}
