package com.versionsystem.persistence.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MEMBER_LOGIN")
@NamedQuery(name = "MemberLogin.findAll", query = "SELECT u FROM MemberLogin u")
public class MemberLogin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "PASSWORD")
	private String password;

	private String status;

	@Column(name = "LAST_UPDATE_DATE")
	private String lastUpdateDate;

	@Column(name = "LAST_UPDATE_USER")
	private String lastUpdateUser;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
}