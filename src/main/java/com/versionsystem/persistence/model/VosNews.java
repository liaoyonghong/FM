package com.versionsystem.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "VOS_NEWS")
@NamedQuery(name = "VosNews.findAll", query = "SELECT n FROM VosNews n")
public class VosNews implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="NEWS_ID")
	private long newsId;

	@Column(name = "NEWS_DATE")
	private Date newsDate;

	@Column(name = "NEWS_DETAIL")
	private String newsDetail;

	@Column(name = "EXPIRY_DATE")
	private Date expiryDate;

	@Column(name = "LAST_UPDATE_USER")
	private String lastUpdateUser;

	@Column(name = "LAST_UPDATE_DATE")
	private String lastUpdateDate;

	@Column(name = "SOLO_ALLOWED")
	private String soloAllowed;

	@Column(name = "KA_FLAG")
	private String kaFlag;

	@Column(name = "VTC_FLAG")
	private String vtcFlag;

	@Column(name = "USER_FLAG")
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
