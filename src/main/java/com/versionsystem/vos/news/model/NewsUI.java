package com.versionsystem.vos.news.model;

import javax.persistence.Column;
import java.sql.Date;

public class NewsUI {

	private long newsId;

	private Date newsDate;

	private String newsDetail;

	private Date expiryDate;

	private String lastUpdateUser;

	private String lastUpdateDate;

	private String soloAllowed;

	private String kaFlag;

	private String vtcFlag;

	private String userFlag;

	public long getNewsId() {
		return newsId;
	}

	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}

	public Date getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(Date newsDate) {
		this.newsDate = newsDate;
	}

	public String getNewsDetail() {
		return newsDetail;
	}

	public void setNewsDetail(String newsDetail) {
		this.newsDetail = newsDetail;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getSoloAllowed() {
		return soloAllowed;
	}

	public void setSoloAllowed(String soloAllowed) {
		this.soloAllowed = soloAllowed;
	}

	public String getKaFlag() {
		return kaFlag;
	}

	public void setKaFlag(String kaFlag) {
		this.kaFlag = kaFlag;
	}

	public String getVtcFlag() {
		return vtcFlag;
	}

	public void setVtcFlag(String vtcFlag) {
		this.vtcFlag = vtcFlag;
	}

	public String getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}
}
