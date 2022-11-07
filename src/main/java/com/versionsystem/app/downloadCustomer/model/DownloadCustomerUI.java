package com.versionsystem.app.downloadCustomer.model;

import java.util.Date;
import java.util.List;

public class DownloadCustomerUI {
	private String id;
	private String title;
	private String filename;
	private String filetype;
	private Date uploadtime;
	private Date updatetime;
	private String company;
	private List<String> classes;

	public DownloadCustomerUI() {
	}

	public DownloadCustomerUI(String title, String filename, String company, List<String> classes) {
		this.title = title;
		this.filename = filename;
		this.company = company;
		this.classes = classes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public List<String> getClasses() {
		return classes;
	}

	public void setClasses(List<String> classes) {
		this.classes = classes;
	}
}
