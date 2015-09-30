package cn.thinkjoy.gk.controller.question;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.constant.CookieConst;
import cn.thinkjoy.gk.controller.question.bean.QuestionAnswerBean;
import cn.thinkjoy.gk.controller.question.dto.AnswerDetailDto;
import cn.thinkjoy.gk.controller.question.dto.QuestionContentDto;
import cn.thinkjoy.gk.controller.question.dto.QuestionDetailDto;
import cn.thinkjoy.gk.controller.question.query.SendQuestionQuery;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.protocol.PageQuery;
import cn.thinkjoy.gk.util.CookieUtil;
import cn.thinkjoy.ss.api.IQuestionService;
import cn.thinkjoy.ss.bean.QuestionDetailBean;
import cn.thinkjoy.ss.domain.Question;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
@RequestMapping(value="/question")
public class QuestionController extends BaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private IQuestionService questionService;

    /**
     * 获取最新问题列表
     *
     * @return
     */
    @RequestMapping(value = "/newQuestion", method = RequestMethod.GET)
    @ResponseBody
    public List<QuestionAnswerBean> newQuestion(@RequestParam(value="keyword",required=false) String keyword,PageQuery pageQuery) {

        Integer startSize = pageQuery.getStartSize();

        if(startSize==null){
            startSize = 0;
        }

        Integer endSize = pageQuery.getEndSize();

        if(endSize==null){
            endSize = 10;
        }


        Integer freeStatus = null;

//        String word = null;
//        if(!StringUtils.isEmpty(keyword)){
//            try {
//                word = (new String(keyword.getBytes("ISO-8859-1"),"UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
//            }
//        }

        List<QuestionDetailBean> questionDetailBeans = questionService.findQuestionAnswerPage(keyword,freeStatus, null,1 , 7, startSize, endSize);

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

    /**
     * 获取热门问题列表
     *
     * @return
     */
    @RequestMapping(value = "/hotQuestion", method = RequestMethod.GET)
    @ResponseBody
    public List<QuestionAnswerBean> hotQuestion(@RequestParam(value="keyword",required=false) String keyword,PageQuery pageQuery) {

        Integer startSize = pageQuery.getStartSize();

        if(startSize==null){
            startSize = 0;
        }

        Integer endSize = pageQuery.getEndSize();

        if(endSize==null){
            endSize = 10;
        }


        Integer freeStatus = null;

//        String word = null;
//        if(!StringUtils.isEmpty(keyword)){
//            try {
//                word = (new String(keyword.getBytes("ISO-8859-1"),"UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
//            }
//        }

        List<QuestionDetailBean> questionDetailBeans = questionService.findHotQuestionAnswerPage(keyword,freeStatus, null,1 , 7, startSize, endSize);

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

    /**
     * 获取问题详情
     *
     *
     * @return
     */
    @RequestMapping(value = "/questionDetail", method = RequestMethod.GET)
    @ResponseBody
    public QuestionAnswerBean questionDetail(@RequestParam(value="id",required=false) Long questionId) {

        if(questionId==null){
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
        }

        cn.thinkjoy.ss.bean.QuestionDetailBean questionDetail = questionService.findQuestionById(questionId);

        QuestionAnswerBean questionAnswerBean = new QuestionAnswerBean();

        if(questionDetail!=null) {

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
            questionAnswerBean.setQuestion(questionDetailDto);

        }
        return questionAnswerBean;

    }

    /**
     * 添加专家问题
     *
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Integer question(SendQuestionQuery sendQuestionQuery) throws Exception {

        if(sendQuestionQuery==null){
            LOGGER.info("====notice question PARAM_ERROR ");
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
        }

        UserAccountPojo userAccountPojo = getUserAccountPojo();

        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }

        String value = CookieUtil.getCookieValue(request.getCookies(), CookieConst.SS_USER_COOKIE_NAME);


        long currentTime = System.currentTimeMillis();

        Integer freeStatus = 0;

        String questionHtml = sendQuestionQuery.getQuestion();

        Question expertQuestion = new Question();

        expertQuestion.setQuestion(htmlToJSON(questionHtml));

        expertQuestion.setQuestionHtml(questionHtml);

        expertQuestion.setCreateDate(currentTime);
        expertQuestion.setLastModDate(currentTime);
        expertQuestion.setIsOpen(0);
        expertQuestion.setUserId(Long.valueOf(value));
        expertQuestion.setDisableStatus(0);
        if(StringUtils.isEmpty(sendQuestionQuery.getExpertId())){
            expertQuestion.setExpertId(0L);
        }else{
            expertQuestion.setIsOpen(1);
            expertQuestion.setExpertId(sendQuestionQuery.getExpertId());
        }
        expertQuestion.setIsAnswer(0);
        expertQuestion.setTags("其他");
        expertQuestion.setFreeStatus(freeStatus);
        expertQuestion.setSourceType(7);
        expertQuestion.setBrowseNum(0L);

        int id = questionService.insert(expertQuestion);

        return id;
    }

    /**
     * 替换四个字节的字符 '\xF0\x9F\x98\x84\xF0\x9F）的解决方案 😁
     * @author ChenGuiYong
     * @data 2015年8月11日 上午10:31:50
     * @param content
     * @return
     */
    public static String removeFourChar(String content) {
        boolean flag = false;
        byte[] conbyte = content.getBytes();
        for (int i = 0; i < conbyte.length; i++) {
            if ((conbyte[i] & 0xF8) == 0xF0) {
                for (int j = 0; j < 4; j++) {
                    conbyte[i+j]=0x30;
                }
                i += 3;
            }
        }
        content = new String(conbyte);
        return content.replaceAll("0000", "");
    }

    /**
     * 更新问题
     *
     * @return
     */
    @RequestMapping(value = "/updateBrowseNum", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateBrowseNum(@RequestParam(value="id",required=false) Long id) {

        boolean flag = false;
        try {
            questionService.updateBrowseNum(id);
            flag = true;
        }catch(Exception e){
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }
        return flag;
    }

    private String htmlToJSON(String html){

        Document doc = Jsoup.parse(html);

        Elements imgs = doc.getElementsByTag("img");

        for(Element element : imgs){
            element.replaceWith(Jsoup.parse("[img]"+element.attributes().get("src")+"[href][img]"));
        }

        String[] contents = doc.text().split("\\[img]");

        List<QuestionContentDto> questionContentDtos = new ArrayList<QuestionContentDto>();

        QuestionContentDto questionContentDto = null;

        boolean createFlag = true;
        boolean imgFlag = false;
        boolean textFlag = false;
        int i =1;
        for(String content:contents){
            if(org.apache.commons.lang3.StringUtils.isEmpty(content)){
                i++;
                continue;
            }
            if(createFlag){
                questionContentDto = new QuestionContentDto();
                createFlag = false;
            }
            if(content.indexOf("[href]")>-1){
                questionContentDto.setImg(content.replace("[href]", ""));
                imgFlag = true;
            }else{
                questionContentDto.setText(content);
                textFlag = true;
            }
            if((imgFlag&&textFlag)||i==contents.length){
                createFlag = true;
                imgFlag = false;
                textFlag = false;
                questionContentDtos.add(questionContentDto);
            }
            i++;
        }

        if(questionContentDtos.size()>0) {
            return JSON.toJSONString(questionContentDtos);
        }
        return null;
    }
}

