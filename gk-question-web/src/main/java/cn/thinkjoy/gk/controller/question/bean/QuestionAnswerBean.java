package cn.thinkjoy.gk.controller.question.bean;

import cn.thinkjoy.gk.controller.question.dto.AnswerDetailDto;
import cn.thinkjoy.gk.controller.question.dto.QuestionDetailDto;
import cn.thinkjoy.gk.controller.question.dto.QuestionDto;

import java.util.List;

/**
 * Created by clei on 15/9/23.
 */
public class QuestionAnswerBean {

    private QuestionDetailDto question;

    private AnswerDetailDto answer;

    public QuestionDetailDto getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDetailDto question) {
        this.question = question;
    }

    public AnswerDetailDto getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerDetailDto answer) {
        this.answer = answer;
    }
}
