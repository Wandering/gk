package cn.thinkjoy.gk.dto;


import java.io.Serializable;
import java.util.List;

/**
 * Created by clei on 15/1/9.
 */
public class AnswerDetailDto extends UserCommonDto implements Serializable {

	private List<QuestionContentDto> answers;

	private Long answerTime;

	public List<QuestionContentDto> getAnswers() {
		return answers;
	}

	public void setAnswers(List<QuestionContentDto> answers) {
		this.answers = answers;
	}

	public Long getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Long answerTime) {
		this.answerTime = answerTime;
	}
}
