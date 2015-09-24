package cn.thinkjoy.gk.controller.question.bean;

import cn.thinkjoy.gk.controller.question.dto.QuestionDto;

import java.util.List;

/**
 * Created by clei on 15/9/23.
 */
public class QuestionBean {

    private List<QuestionDto> questions;

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }
}
