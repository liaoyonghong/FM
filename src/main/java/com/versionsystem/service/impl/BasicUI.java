package com.versionsystem.service.impl;

import java.sql.Timestamp;
import java.util.Date;

public class BasicUI {

	protected Timestamp createDate;
	protected String createUser;
	protected Timestamp lastUpdateDate;
	protected String lastUpdateUser;

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public <T extends BasicUI> T init(String user) {
		setCreateUser(user);
		setCreateDate(new Timestamp(new Date().getTime()));

		return (T) update(user);
	}

	public <T extends BasicUI> T update(String user) {
		setLastUpdateDate(new Timestamp(new Date().getTime()));
		setLastUpdateUser(user);

		return (T) this;
	}

}
