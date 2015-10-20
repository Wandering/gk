package cn.thinkjoy.gk.controller.question.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by clei on 15/1/9.
 */
public class QuestionDetailDto extends UserCommonDto implements Serializable {

	private Long questionId;

	private List<QuestionContentDto> questions;

	private Integer disableNum;

	private Integer disableStatus;

	private Long createTime;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public List<QuestionContentDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionContentDto> questions) {
		this.questions = questions;
	}

	public Integer getDisableNum() {
		return disableNum;
	}

	public void setDisableNum(Integer disableNum) {
		this.disableNum = disableNum;
	}

	public Integer getDisableStatus() {
		return disableStatus;
	}

	public void setDisableStatus(Integer disableStatus) {
		this.disableStatus = disableStatus;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}