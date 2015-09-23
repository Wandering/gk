package cn.thinkjoy.gk.query;


import cn.thinkjoy.gk.dto.QuestionContentDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by clei on 15/1/9.
 */
public class SendQuestionQuery implements Serializable {

	private List<QuestionContentDto> questions;

	private Long expertId;

	private Long questionId;

	private Integer expertFreeStatus;

	private Long disableExpertId;

	public List<QuestionContentDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionContentDto> questions) {
		this.questions = questions;
	}

	public Long getExpertId() {
		return expertId;
	}

	public void setExpertId(Long expertId) {
		this.expertId = expertId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getDisableExpertId() {
		return disableExpertId;
	}

	public void setDisableExpertId(Long disableExpertId) {
		this.disableExpertId = disableExpertId;
	}

	public Integer getExpertFreeStatus() {
		return expertFreeStatus;
	}

	public void setExpertFreeStatus(Integer expertFreeStatus) {
		this.expertFreeStatus = expertFreeStatus;
	}
}
