package cn.thinkjoy.gk.bean;

import cn.thinkjoy.gk.dto.QuestionDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by clei on 15/1/9.
 */
public class AnswerBean implements Serializable {

	private List<QuestionDto> answers;

	public List<QuestionDto> getAnswers() {
		return answers;
	}

	public void setAnswers(List<QuestionDto> answers) {
		this.answers = answers;
	}
}
