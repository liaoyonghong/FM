package com.versionsystem.persistence.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "DOWNLOAD_CUSTOMER")
@NamedQuery(name = "DownloadCustomer.findAll", query = "SELECT m FROM DownloadCustomer m")
public class DownloadCustomer implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String title;
	private String filename;
	private String filetype;
	private Date uploadtime;
	private Date updatetime;
	private String company;


	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid")
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "TITLE", unique = true, length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "FILENAME", unique = true, length = 200)
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "FILETYPE", length = 10)
	public String getFiletype() {
		return this.filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPLOADTIME")
	public Date getUploadtime() {
		return this.uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME")
	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "COMPANY")
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
