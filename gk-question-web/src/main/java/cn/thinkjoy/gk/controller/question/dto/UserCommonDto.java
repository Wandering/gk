package cn.thinkjoy.gk.controller.question.dto;

import java.io.Serializable;

/**
 * 专家信息 Created by clei on 15/1/10.
 */
public class UserCommonDto implements Serializable {

	private Long userId;

	private String userName;

	private String userIcon;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

}
