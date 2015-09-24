package cn.thinkjoy.gk.controller.question;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.controller.question.bean.QuestionAnswerBean;
import cn.thinkjoy.gk.controller.question.dto.AnswerDetailDto;
import cn.thinkjoy.gk.controller.question.dto.QuestionContentDto;
import cn.thinkjoy.gk.controller.question.dto.QuestionDetailDto;
import cn.thinkjoy.gk.controller.question.dto.QuestionDto;
import cn.thinkjoy.gk.controller.question.query.SendQuestionQuery;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.protocol.PageQuery;
import cn.thinkjoy.ss.api.IQuestionService;
import cn.thinkjoy.ss.bean.QuestionDetailBean;
import cn.thinkjoy.ss.domain.Question;
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
    public List<QuestionAnswerBean> newQuestion(PageQuery pageQuery) {

//        LOGGER.info("用户［" + userId + "］获取热门问题列表");
//        if (null == userId || userId == 0) {
//            LOGGER.info("====notice send AUTHENTICATION_FAIL ");
//            throw new BizException(ERRORCODE.AUTHENTICATION_FAIL.getCode(), ERRORCODE.AUTHENTICATION_FAIL.getMessage());
//        }

//        if(pageQuery==null){
//            LOGGER.info("====notice hotQuestion PARAM_ERROR ");
//            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
//        }

        Integer startSize = pageQuery.getStartSize();

        if(startSize==null){
            startSize = 0;
        }

        Integer endSize = pageQuery.getEndSize();

        if(endSize==null){
            endSize = 10;
        }


        UserAccountPojo userAccountBean = null;

        try{
            userAccountBean = getUserAccountPojo();
        }catch(Exception e){
            e.printStackTrace();
        }

        Integer freeStatus = 0;

        if(userAccountBean.getVipStatus()!=null&&userAccountBean.getVipStatus()==1){
            freeStatus = 1;
        }

//        if(userCredentials.getUserType()==1){
//            LOGGER.info("当前用户:"+userId+"为教师");
//            freeStatus = null;
//        }else{
//            List<ViperInfo> viperInfos = vipService.getViperInfoList(Long.valueOf(userId), VipConst.EXPERT_CODE);
//            if (null != viperInfos && viperInfos.size() > 0) {
//                LOGGER.info("当前用户:"+userId+"为VIP");
//                freeStatus = null;
//            }
//        }

        List<QuestionDetailBean> questionDetailBeans = questionService.findQuestionAnswerByPage(freeStatus, 1, null, 7, startSize, endSize);



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
    public List<QuestionAnswerBean> hotQuestion(PageQuery pageQuery) {

//        LOGGER.info("用户［" + userId + "］获取热门问题列表");
//        if (null == userId || userId == 0) {
//            LOGGER.info("====notice send AUTHENTICATION_FAIL ");
//            throw new BizException(ERRORCODE.AUTHENTICATION_FAIL.getCode(), ERRORCODE.AUTHENTICATION_FAIL.getMessage());
//        }

//        if(pageQuery==null){
//            LOGGER.info("====notice hotQuestion PARAM_ERROR ");
//            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
//        }

        Integer startSize = pageQuery.getStartSize();

        if(startSize==null){
            startSize = 0;
        }

        Integer endSize = pageQuery.getEndSize();

        if(endSize==null){
            endSize = 10;
        }


        UserAccountPojo userAccountBean = null;

        try{
            userAccountBean = getUserAccountPojo();
        }catch(Exception e){
            e.printStackTrace();
        }

        Integer freeStatus = 0;

        if(userAccountBean.getVipStatus()!=null&&userAccountBean.getVipStatus()==1){
            freeStatus = 1;
        }

//        if(userCredentials.getUserType()==1){
//            LOGGER.info("当前用户:"+userId+"为教师");
//            freeStatus = null;
//        }else{
//            List<ViperInfo> viperInfos = vipService.getViperInfoList(Long.valueOf(userId), VipConst.EXPERT_CODE);
//            if (null != viperInfos && viperInfos.size() > 0) {
//                LOGGER.info("当前用户:"+userId+"为VIP");
//                freeStatus = null;
//            }
//        }

        List<QuestionDetailBean> questionDetailBeans = questionService.findQuestionAnswerByPage(freeStatus, 1, null, 7, startSize, endSize);

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
            LOGGER.info("====notice questionDetail PARAM_ERROR ");
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

//        SendQuestionQuery sendQuestionQuery = requestT.getData();

        if(sendQuestionQuery==null){
            LOGGER.info("====notice question PARAM_ERROR ");
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
        }

//        Long expertId = sendQuestionQuery.getExpertId();

        UserAccountPojo account = getUserAccountPojo();

        Integer vipStatus = account.getVipStatus();

        Long userId = account.getId();

//        if(vipStatus==0){
//            LOGGER.info("当前用户:{"+userId+"}不是会员");
//            int count = answerExService.findAswerQuestionCount(userId);
//
//            if(count>0){
//                LOGGER.info("====notice question COMMIT_ERROR ");
//                throw new BizException("1000011", "您的免费提问机会已用完,开通会员,享受专家无限一对一服务");
//            }
//
//        }else{
//            LOGGER.info("当前用户:{"+userId+"}是会员");
//        }



        long currentTime = System.currentTimeMillis();



        Integer freeStatus = 0;

        LOGGER.info("vip状态:"+vipStatus);
        if (vipStatus==1) {
            LOGGER.info("当前用户:"+userId+"为VIP");
            freeStatus = 1;
        }

//        Long questionId = sendQuestionQuery.getQuestionId();
//
//        if(questionId!=null){
//
//            cn.thinkjoy.ss.bean.QuestionDetailBean qdb = questionService.findQuestionById(questionId);
//
//            Question expertQuestion = new Question();
//
//            String disableExpertId = qdb.getDisableExpertId();
//
//            if(!StringUtils.isEmpty(disableExpertId)&&disableExpertId.indexOf(",")>-1&&disableExpertId.split(",").length>=3){
//                throw new BizException("9999999", "同一问题,最多可重复换三次!");
//            }
//
//            List<String> disableExpertIds = new ArrayList<String>();
//            if(!StringUtils.isEmpty(sendQuestionQuery.getDisableExpertId())) {
//                disableExpertIds.add(sendQuestionQuery.getDisableExpertId()+"");
//            }
//            if(!StringUtils.isEmpty(disableExpertId)){
//                disableExpertIds.add(disableExpertId);
//            }
//            if(null!=disableExpertIds&&disableExpertIds.size()>0){
//                StringBuffer id = new StringBuffer("");
//                for(String deId :disableExpertIds){
//                    id.append(deId+",");
//                }
//                expertQuestion.setDisableExpertId(id.substring(0,id.length()-1));
//            }
//
//            expertQuestion.setQuestion(qdb.getQuestion());
//            expertQuestion.setCreateDate(currentTime);
//            expertQuestion.setLastModDate(currentTime);
//            expertQuestion.setIsOpen(0);
////            expertQuestion.setQuestionId(idBuilderService.getIdByBusinessCode(IdType.QUESTIONID, idBuilderService.getCountAsInt()));
//            expertQuestion.setUserId(Long.valueOf(userId));
//            expertQuestion.setStatus(2);
//            expertQuestion.setExpertId(Long.valueOf(0));
////            if(StringUtils.isEmpty(sendQuestionQuery.getExpertId())){
////                expertQuestion.setExpertId(0L);
////            }else{
////                expertQuestion.setIsOpen(1);
////                expertQuestion.setExpertId(sendQuestionQuery.getExpertId());
////            }
//            expertQuestion.setIsAnswer(0);
//            expertQuestion.setTags("其他");
//            expertQuestion.setFreeStatus(freeStatus);
//
//            expertQuestion.setDisableStatus(0);
//
//            expertQuestion.setIsAnswer(0);
//
////            expertQuestionExService.updateQuestionByQuestionId(expertQuestion);
//
//            int id = questionService.insert(expertQuestion);
//
////            Question expertQuestionUpdate = new Question();
////
////            expertQuestionUpdate.setId(questionId);
////
////            expertQuestionUpdate.setDisableStatus(1);
//
//            Map<String,Object> params = new HashMap<String,Object>();
//
//            params.put("id",questionId);
//
//            params.put("disableStatus",1);
//
//            questionService.updateMap(params);
//
//            return id;
//        }

        Question expertQuestion = new Question();

        List<QuestionContentDto> questionContentDtos = sendQuestionQuery.getQuestions();

//        StringBuffer question = new StringBuffer("<root>");
//        for(QuestionContentDto questionContentDto:questionContentDtos){
//            question.append("<object>");
//            question.append("<text>"+questionContentDto.getText()+"</text>");
//            question.append("<img>"+questionContentDto.getImg()+"</img>");
//            question.append("</object>");
//        }
//        question.append("</root>");

        String question = JSON.toJSONString(questionContentDtos);

//        Pattern pattern = Pattern.compile("/[\\u4E00-\\u9FB0]{2}/");

//        Pattern emoji = Pattern.compile("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]",
//                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE) ;

//        Matcher matcher = pattern.matcher(question);

        question = removeFourChar(question);
//        if(matcher.matches()){
//            throw new BizException("9999998", "不支持表情符!");
//        }


//        if(EmojiUtil.containsEmoji(question)){
//            throw new BizException("9999998", "不支持表情符!");
//        }
        expertQuestion.setQuestion(question);
        expertQuestion.setCreateDate(currentTime);
        expertQuestion.setLastModDate(currentTime);
        expertQuestion.setIsOpen(0);
//        expertQuestion.setQuestionId(idBuilderService.getIdByBusinessCode(IdType.QUESTIONID, idBuilderService.getCountAsInt()));
        expertQuestion.setUserId(Long.valueOf(userId));
//        expertQuestion.setStatus(2);
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
//        expertQuestion.setFreeStatus(0);
//        if(vipStatus){
//            expertQuestion.setFreeStatus(1);
//        }

        int id = questionService.insert(expertQuestion);

//        request.setAttribute("questionId",id);

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
    @RequestMapping(value = "/updateQuestion", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateQuestion(Question question) {

//        LOGGER.info("用户［" + userId + "］获取热门问题列表");
//        if (null == userId || userId == 0) {
//            LOGGER.info("====notice send AUTHENTICATION_FAIL ");
//            throw new BizException(ERRORCODE.AUTHENTICATION_FAIL.getCode(), ERRORCODE.AUTHENTICATION_FAIL.getMessage());
//        }

//        if(pageQuery==null){
//            LOGGER.info("====notice hotQuestion PARAM_ERROR ");
//            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(), ERRORCODE.PARAM_ERROR.getMessage());
//        }

        boolean flag = false;
        try {
            questionService.update(question);
            flag = true;
        }catch(Exception e){
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }
        return flag;
    }
}

