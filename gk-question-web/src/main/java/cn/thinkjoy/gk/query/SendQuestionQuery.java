package cn.thinkjoy.gk.query;



import java.io.Serializable;

/**
 * Created by clei on 15/1/9.
 */
public class SendQuestionQuery implements Serializable {

	private String question;

	private Long expertId;

	private Long questionId;

	private Integer expertFreeStatus;

	private Long disableExpertId;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
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
